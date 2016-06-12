package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jtrend.util.RequestUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.jtrend.util.JtStringUtil;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
                    
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.DevNote;
import com.autosite.ds.DevNoteDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.DevNoteForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.access.AccessConfigManager;
import com.autosite.util.access.AccessDef;
import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.struts.core.DefaultPageForwardManager;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check




public class DevNoteAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(DevNoteAction.class);

    public DevNoteAction(){
        m_ds = DevNoteDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        DevNoteForm _DevNoteForm = (DevNoteForm) form;
        HttpSession session = request.getSession();
        DefaultPageForwardManager pageManager = DefaultPageForwardManager.getInstance();
        String sentPage = getSentPage(request);

        setPage(session, getDefaultPage());

        Site site = getSite(request);
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");

            //Default error page but will be overridden by exception specific error page
            //setPage(session, getDefaultPage());
            //setPage(session,
            //        pageManager.getPageForwardToByCommand(getActionName(), "general", "error", sentPage != null ? sentPage: getErrorPage()),
            //        pageManager.isInternalForward(getActionName(), "general", "error"));
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }

        // Check if needs confirmTo step

        if ( !isAjaxRequest(request) && (
            hasRequestValue(request, "XXXXXXXXXXX", "true")))
        {    
            if (forwardConfirmTo(request))
                return mapping.findForward("default");
        
        }


        // Check permissions 
        if (!haveAccessToUpdate(session) ){
            sessionErrorText(session, "Permission error occurred.");
            processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));

            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }

        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");

            //Default error page but will be overridden by exception specific error page
            //setPage(session, getErrorPage());
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        DevNote _DevNote = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _DevNote = m_ds.getById(cid);
        }



        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //DevNote _DevNote = m_ds.getById(cid);

            if (_DevNote == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                //setPage(session,
                //        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getErrorPage()),
                //        pageManager.isInternalForward(getActionName(), "edit", "error"));
                processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_DevNote.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _DevNote.getSiteId()); 
                //Default error page but will be overridden by exception specific error page
                //setPage(session, null, getAfterEditPage(), false,  pageManager.isPageForwardTo(getActionName(),"action_normal_edit_forward_page" ) );
                setPage(session, 
                        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
                        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_DevNote);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _DevNoteForm, _DevNote);
                if (returnObjects != null) returnObjects.put("target", _DevNote);
                //setPageForward(session, getAfterEditPage());
                //setPage(session, null, getAfterEditPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_edit_forward_page" ) );
            }

            catch (Exception e) {
                m_logger.error("Error occurred", e);
                if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                //Default error page but will be overridden by exception specific error page
                //setPage(session,
                //        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()),
                //        pageManager.isInternalForward(getActionName(), "edit", "error"));
                processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session, ((ActionExtentException)e).getForwardPage(), 
                            pageManager.isInternalForward(getActionName(), "edit", "error"));
                return mapping.findForward("default");
            }

            //Default error page but will be overridden by exception specific error page
            //setPage(session, 
            //        pageManager.getPageForwardToByCommand(getActionName(), "edit", "success", getAfterEditPage()), 
            //        pageManager.isInternalForward(getActionName(), "edit", "success"));
            processPageForAction(request, "edit", "success", getAfterEditPage());

            return mapping.findForward("default");
    
        } else if (isActionCmdEditfield(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //DevNote _DevNote = m_ds.getById(cid);

            if (_DevNote == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

                //Default error page but will be overridden by exception specific error page
                //setPage(session, getErrorPage());
                //setPage(session,
                //        pageManager.getPageForwardToByCommand(getActionName(), "editfield", "error", getErrorPage()),
                //        pageManager.isInternalForward(getActionName(), "editfield", "error"));
                processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_DevNote.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _DevNote.getSiteId()); 

                //Default error page but will be overridden by exception specific error page
                //setPage(session, getErrorPage());
                //setPage(session,
                //        pageManager.getPageForwardToByCommand(getActionName(), "editfield", "error", getErrorPage()),
                //        pageManager.isInternalForward(getActionName(), "editfield", "error"));
                processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _DevNote);
                if (returnObjects != null) returnObjects.put("target", _DevNote);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                //Default error page but will be overridden by exception specific error page
                //setPage(session, getErrorPage());
                //setPage(session,
                //        pageManager.getPageForwardToByCommand(getActionName(), "editfield", "error", getErrorPage()),
                //        pageManager.isInternalForward(getActionName(), "editfield", "error"));
                processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "editfield", "error"));
                return mapping.findForward("default");
            }

            //setPage(session, getAfterEditFieldPage());
            //setPage(session, 
            //        pageManager.getPageForwardToByCommand(getActionName(), "editfield", "success", getAfterEditPage()), 
            //        pageManager.isInternalForward(getActionName(), "editfield", "success"));
            processPageForAction(request, "editfield", "success", getAfterEditPage());
            return mapping.findForward("default");

        } else if (isActionCmdDelete(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //DevNote _DevNote = m_ds.getById(cid);

            if (_DevNote == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

                //Default error page but will be overridden by exception specific error page
                //setPage(session, getErrorPage());
                //setPage(session,
                //        pageManager.getPageForwardToByCommand(getActionName(), "delete", "error", getErrorPage()),
                //        pageManager.isInternalForward(getActionName(), "delete", "error"));
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_DevNote.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _DevNote.getSiteId()); 

                //setPage(session, getErrorPage());
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _DevNote);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

                //Default error page but will be overridden by exception specific error page
                //setPage(session, getErrorPage());
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "delete", "error"));

                return mapping.findForward("default");
            }

            m_ds.delete(_DevNote); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _DevNote);
            //setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _DevNote);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "delete", "error"));

                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }

        } else if (isActionCmdAdd(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request)) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 


            m_logger.info("Creating new DevNote" );
            DevNote _DevNoteNew = new DevNote();   

            // Setting IDs for the object
            _DevNoteNew.setSiteId(site.getId());

            _DevNoteNew.setNoteType(WebParamUtil.getIntValue(_DevNoteForm.getNoteType()));
            m_logger.debug("setting NoteType=" +_DevNoteForm.getNoteType());
            _DevNoteNew.setCompleted(WebParamUtil.getIntValue(_DevNoteForm.getCompleted()));
            m_logger.debug("setting Completed=" +_DevNoteForm.getCompleted());
            _DevNoteNew.setCategory(WebParamUtil.getStringValue(_DevNoteForm.getCategory()));
            m_logger.debug("setting Category=" +_DevNoteForm.getCategory());
            _DevNoteNew.setSubject(WebParamUtil.getStringValue(_DevNoteForm.getSubject()));
            m_logger.debug("setting Subject=" +_DevNoteForm.getSubject());
            _DevNoteNew.setNote(WebParamUtil.getStringValue(_DevNoteForm.getNote()));
            m_logger.debug("setting Note=" +_DevNoteForm.getNote());
            _DevNoteNew.setTags(WebParamUtil.getStringValue(_DevNoteForm.getTags()));
            m_logger.debug("setting Tags=" +_DevNoteForm.getTags());
            _DevNoteNew.setTimeCreated(WebParamUtil.getTimestampValue(_DevNoteForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_DevNoteForm.getTimeCreated());
            _DevNoteNew.setTimeUpdated(WebParamUtil.getTimestampValue(_DevNoteForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_DevNoteForm.getTimeUpdated());


            try {
                checkDepedenceIntegrity(_DevNoteNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _DevNoteNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

                //setPage(session, getErrorPage());
                processPageForAction(request, "add", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "add", "error"));
                return mapping.findForward("default");
            }
            
            if (_DevNoteNew.skipPersist())
                m_logger.info("Skipping new object by skipPersist()");
            else                
                m_ds.put(_DevNoteNew);
            if (returnObjects != null) returnObjects.put("target", _DevNote);

            //setPageForward(request, getActionExtent().getAfterAddPage());
            //setPage(session, null, getActionExtent().getAfterAddPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_add_forward_page" ) );
            //setPage(session, 
            //        pageManager.getPageForwardToByCommand(getActionName(), "add", "success", getAfterAddPage()), 
            //        pageManager.isInternalForward(getActionName(), "add", "success"));
            processPageForAction(request, "add", "success", getAfterAddPage());


            try{
                m_actionExtent.afterAdd(request, response, _DevNoteNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

                //setPage(session, getErrorPage());
                processPageForAction(request, "add", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "add", "error"));
                return mapping.findForward("default");
            }
            
            //setPage(session, "dev_note_home");


        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, DevNoteForm _DevNoteForm, DevNote _DevNote) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        DevNote _DevNote = m_ds.getById(cid);

        m_logger.debug("Before update " + DevNoteDS.objectToString(_DevNote));

        _DevNote.setNoteType(WebParamUtil.getIntValue(_DevNoteForm.getNoteType()));
        _DevNote.setCompleted(WebParamUtil.getIntValue(_DevNoteForm.getCompleted()));
        _DevNote.setCategory(WebParamUtil.getStringValue(_DevNoteForm.getCategory()));
        _DevNote.setSubject(WebParamUtil.getStringValue(_DevNoteForm.getSubject()));
        _DevNote.setNote(WebParamUtil.getStringValue(_DevNoteForm.getNote()));
        _DevNote.setTags(WebParamUtil.getStringValue(_DevNoteForm.getTags()));
        _DevNote.setTimeUpdated(WebParamUtil.getTimestampValue(_DevNoteForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _DevNote);
        m_ds.update(_DevNote);
        m_actionExtent.afterUpdate(request, response, _DevNote);
        m_logger.debug("After update " + DevNoteDS.objectToString(_DevNote));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, DevNote _DevNote) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        DevNote _DevNote = m_ds.getById(cid);

        if (!isMissing(request.getParameter("noteType"))) {
            m_logger.debug("updating param noteType from " +_DevNote.getNoteType() + "->" + request.getParameter("noteType"));
            _DevNote.setNoteType(WebParamUtil.getIntValue(request.getParameter("noteType")));

        }
        if (!isMissing(request.getParameter("completed"))) {
            m_logger.debug("updating param completed from " +_DevNote.getCompleted() + "->" + request.getParameter("completed"));
            _DevNote.setCompleted(WebParamUtil.getIntValue(request.getParameter("completed")));

        }
        if (!isMissing(request.getParameter("category"))) {
            m_logger.debug("updating param category from " +_DevNote.getCategory() + "->" + request.getParameter("category"));
            _DevNote.setCategory(WebParamUtil.getStringValue(request.getParameter("category")));

        }
        if (!isMissing(request.getParameter("subject"))) {
            m_logger.debug("updating param subject from " +_DevNote.getSubject() + "->" + request.getParameter("subject"));
            _DevNote.setSubject(WebParamUtil.getStringValue(request.getParameter("subject")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_DevNote.getNote() + "->" + request.getParameter("note"));
            _DevNote.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("tags"))) {
            m_logger.debug("updating param tags from " +_DevNote.getTags() + "->" + request.getParameter("tags"));
            _DevNote.setTags(WebParamUtil.getStringValue(request.getParameter("tags")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_DevNote.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _DevNote.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_DevNote.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _DevNote.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        }

        m_actionExtent.beforeUpdate(request, response, _DevNote);
        m_ds.update(_DevNote);
        m_actionExtent.afterUpdate(request, response, _DevNote);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, DevNote _DevNote) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        DevNote _DevNote = m_ds.getById(cid);

        if (!isMissing(request.getParameter("noteType"))) {
            return  JtStringUtil.valueOf(_DevNote.getNoteType());
        }
        if (!isMissing(request.getParameter("completed"))) {
            return  JtStringUtil.valueOf(_DevNote.getCompleted());
        }
        if (!isMissing(request.getParameter("category"))) {
            return  JtStringUtil.valueOf(_DevNote.getCategory());
        }
        if (!isMissing(request.getParameter("subject"))) {
            return  JtStringUtil.valueOf(_DevNote.getSubject());
        }
        if (!isMissing(request.getParameter("note"))) {
            return  JtStringUtil.valueOf(_DevNote.getNote());
        }
        if (!isMissing(request.getParameter("tags"))) {
            return  JtStringUtil.valueOf(_DevNote.getTags());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            return  JtStringUtil.valueOf(_DevNote.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            return  JtStringUtil.valueOf(_DevNote.getTimeUpdated());
        }
        return null;
    }

    protected void checkDepedenceIntegrity(DevNote _DevNote) throws Exception {
    }

    protected String getFieldByName(String fieldName, DevNote _DevNote) {
        if (fieldName == null || fieldName.equals("")|| _DevNote == null) return null;
        
        if (fieldName.equals("noteType")) {
            return WebUtil.display(_DevNote.getNoteType());
        }
        if (fieldName.equals("completed")) {
            return WebUtil.display(_DevNote.getCompleted());
        }
        if (fieldName.equals("category")) {
            return WebUtil.display(_DevNote.getCategory());
        }
        if (fieldName.equals("subject")) {
            return WebUtil.display(_DevNote.getSubject());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_DevNote.getNote());
        }
        if (fieldName.equals("tags")) {
            return WebUtil.display(_DevNote.getTags());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_DevNote.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_DevNote.getTimeUpdated());
        }
        return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        DevNote target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (DevNote) working.get("target");
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        // ================================================================================
        // Response Processing 
        // ================================================================================

        if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // If there is no error, following message will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") )
                ret.put("__value", "Successfully deleted.");
            if (hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") )
                ret.put("__value", "Successfully created.");
            if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef"))     
                ret.put("__value", "Successfully changed.");
            if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") )
                ret.put("__value", "Successfully changed.");
            else 
                ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getcode")){
            // If there is no error, nothing will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getstatus arg = " + request.getParameter("ajaxOutArg"));

        } else if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            DevNote _DevNote = DevNoteDS.getInstance().getById(id);
            if (_DevNote == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _DevNote);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            DevNote _DevNote = DevNoteDS.getInstance().getById(id);
            if ( _DevNote == null) {
                ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _DevNote);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();

            buf.append("<div id=\"devNote-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                DevNote _DevNote = (DevNote) iterator.next();

                buf.append("<div id=\"devNote-ajax-item\" "+listClass+">");

                if ( ignoreFieldSet || fieldSet.contains("noteType")) {
                    buf.append("<div id=\"devNote-ajax-noteType\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getNoteType()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("completed")) {
                    buf.append("<div id=\"devNote-ajax-completed\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getCompleted()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("category")) {
                    buf.append("<div id=\"devNote-ajax-category\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getCategory()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("subject")) {
                    buf.append("<div id=\"devNote-ajax-subject\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getSubject()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"devNote-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getNote()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("tags")) {
                    buf.append("<div id=\"devNote-ajax-tags\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getTags()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"devNote-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getTimeCreated()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"devNote-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getTimeUpdated()));
                    buf.append("</div>");

                }
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("noteType")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Note Type");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("completed")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Completed");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("category")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Category");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("subject")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Subject");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("note")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Note");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("tags")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Tags");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Time Created");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Time Updated");
                buf.append("</td>");
            }
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                DevNote _DevNote = (DevNote) iterator.next();

                buf.append("<tr "+ trStyleStr +" >");


                if ( ignoreFieldSet || fieldSet.contains("noteType")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getNoteType()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("completed")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getCompleted()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("category")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getCategory()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("subject")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getSubject()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getNote()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("tags")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getTags()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getTimeCreated()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevNote.getTimeUpdated()));

                    buf.append("</td>");
                }
                buf.append("</tr>");
            }
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            if (returnList) {

                top.put("count", ""+list.size());
                JSONArray array = new JSONArray();

                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    DevNote _DevNote = (DevNote) iterator.next();

                    JSONObject json = new JSONObject();

                    // Fields
                    if ( ignoreFieldSet || fieldSet.contains("noteType")) 
                        json.put("noteType", ""+_DevNote.getNoteType());
                    if ( ignoreFieldSet || fieldSet.contains("completed")) 
                        json.put("completed", ""+_DevNote.getCompleted());
                    if ( ignoreFieldSet || fieldSet.contains("category")) 
                        json.put("category", ""+_DevNote.getCategory());
                    if ( ignoreFieldSet || fieldSet.contains("subject")) 
                        json.put("subject", ""+_DevNote.getSubject());
                    if ( ignoreFieldSet || fieldSet.contains("note")) 
                        json.put("note", ""+_DevNote.getNote());
                    if ( ignoreFieldSet || fieldSet.contains("tags")) 
                        json.put("tags", ""+_DevNote.getTags());
                    array.put(json);
                }

                top.put("list", array);

            } else {

                DevNote _DevNote = list.size() >=1?(DevNote) list.get(0): null; 

                if ( _DevNote != null) {
                    top.put("id", ""+_DevNote.getId());
                    JSONArray array = new JSONArray();

                    // Fields
                    JSONObject jsonNoteType = new JSONObject();
                    jsonNoteType.put("name", "noteType");
                    jsonNoteType.put("value", ""+_DevNote.getNoteType());
                    array.put(jsonNoteType);
                    JSONObject jsonCompleted = new JSONObject();
                    jsonCompleted.put("name", "completed");
                    jsonCompleted.put("value", ""+_DevNote.getCompleted());
                    array.put(jsonCompleted);
                    JSONObject jsonCategory = new JSONObject();
                    jsonCategory.put("name", "category");
                    jsonCategory.put("value", ""+_DevNote.getCategory());
                    array.put(jsonCategory);
                    JSONObject jsonSubject = new JSONObject();
                    jsonSubject.put("name", "subject");
                    jsonSubject.put("value", ""+_DevNote.getSubject());
                    array.put(jsonSubject);
                    JSONObject jsonNote = new JSONObject();
                    jsonNote.put("name", "note");
                    jsonNote.put("value", ""+_DevNote.getNote());
                    array.put(jsonNote);
                    JSONObject jsonTags = new JSONObject();
                    jsonTags.put("name", "tags");
                    jsonTags.put("value", ""+_DevNote.getTags());
                    array.put(jsonTags);

                    top.put("fields", array);
                }
            }

            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));

            // Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuilder buf = new StringBuilder();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);


            buf.append("<script type=\"text/javascript\">");
            //buf.append("<!--");
            buf.append("function sendForm_"+ randNum + "(){");
            buf.append("sendFormAjax('/devNoteAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/devNoteAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("noteType")) {
                String value = WebUtil.display(request.getParameter("noteType"));

                if ( forceHiddenSet.contains("noteType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"noteType\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Note Type</div>");
            buf.append("<INPUT NAME=\"noteType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("completed")) {
                String value = WebUtil.display(request.getParameter("completed"));

                if ( forceHiddenSet.contains("completed")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"completed\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Completed</div>");
            buf.append("<INPUT NAME=\"completed\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("category")) {
                String value = WebUtil.display(request.getParameter("category"));

                if ( forceHiddenSet.contains("category")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"category\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Category</div>");
            buf.append("<INPUT NAME=\"category\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("subject")) {
                String value = WebUtil.display(request.getParameter("subject"));

                if ( forceHiddenSet.contains("subject")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subject\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subject</div>");
            buf.append("<INPUT NAME=\"subject\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("note")) {
                String value = WebUtil.display(request.getParameter("note"));

                if ( forceHiddenSet.contains("note")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"note\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Note</div>");
            buf.append("<TEXTAREA NAME=\"note\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("tags")) {
                String value = WebUtil.display(request.getParameter("tags"));

                if ( forceHiddenSet.contains("tags")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"tags\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Tags</div>");
            buf.append("<INPUT NAME=\"tags\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                String value = WebUtil.display(request.getParameter("timeCreated"));

                if ( forceHiddenSet.contains("timeCreated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCreated\"  value=\""+value+"\">");
                } else {

            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                String value = WebUtil.display(request.getParameter("timeUpdated"));

                if ( forceHiddenSet.contains("timeUpdated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeUpdated\"  value=\""+value+"\">");
                } else {

            buf.append("<br/>");
            }
            }

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult"+randNum+"\"></span>");
            buf.append("<a id=\"ajaxSubmit"+randNum+"\" href=\"javascript:sendForm_"+ randNum + "();\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2") || (hasRequestValue(request, "ajaxOut", "getscriptform"))){

            //This form is called by script such as e.g. <script type="text/javascript" src="/blockListAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>
            // inline_script will be attached to provide functionlities. 
            // This form will be used inside the same site to provide embedded page using <script> tags. But Refer to Poll "inline_script_poll" to 
            // send no-ajax submission. General no-ajax submission is not yet supported. 

            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
            StringBuilder buf = new StringBuilder();
            Site site = SiteDS.getInstance().registerSite(request.getServerName());

            String importedScripts = null;
            try {
                importedScripts = FileUtil.loadCodesToString("./inline_script.txt");
                m_logger.debug(importedScripts);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                importedScripts = "";
            }

            importedScripts += "\n";
            importedScripts += "function responseCallback_devNote(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayDevNote\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_devNote(){\n";
            importedScripts +=     "xmlhttpPostXX('devNoteFormAddDis','/devNoteAction.html', 'resultDisplayDevNote', '${ajax_response_fields}', responseCallback_devNote);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_devNote(){\n";
            importedScripts +=     "clearFormXX('devNoteFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_devNote(){\n";
            importedScripts +=     "backToXX('devNoteFormAddDis','resultDisplayDevNote');\n";
            importedScripts += "}\n";

            buf.append(importedScripts);
            buf.append("\n");

            buf.append("document.write('");
            buf.append("<form name=\"devNoteFormAddDis\" method=\"POST\" action=\"/devNoteAction.html\" id=\"devNoteFormAddDis\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

        buf.append("<div class=\"ajaxFormLabel\"> Note Type</div>");
        buf.append("<input class=\"field\" id=\"noteType\" type=\"text\" size=\"70\" name=\"noteType\" value=\"\"/> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Completed</div>");
        buf.append("<input class=\"field\" id=\"completed\" type=\"text\" size=\"70\" name=\"completed\" value=\"\"/> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Category</div>");
        buf.append("<input class=\"field\" id=\"category\" type=\"text\" size=\"70\" name=\"category\" value=\"\"/> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Subject</div>");
        buf.append("<input class=\"field\" id=\"subject\" type=\"text\" size=\"70\" name=\"subject\" value=\"\"/> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
        buf.append("<TEXTAREA id=\"note\" class=\"field\" NAME=\"note\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Tags</div>");
        buf.append("<input class=\"field\" id=\"tags\" type=\"text\" size=\"70\" name=\"tags\" value=\"\"/> <span></span>");
        buf.append("<br/>");


            buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_devNote()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_devNote()\">Clear Form</a><br>");
            //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
            buf.append("</form>");
            buf.append("<span id=\"resultDisplayDevNote\"></span>");
            buf.append("<a href=\"javascript:showform_devNote()\">Show form</a><br>");
            buf.append("');");

            ret.put("__value", buf.toString());
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        return ret;
    }


    // Prepares data to be returned in ajax.
    protected List prepareReturnData(HttpServletRequest request, DevNote target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            DevNote _DevNote = null; 
            List list = DevNoteDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _DevNote = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _DevNote = (DevNote) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _DevNote = (DevNote) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _DevNote = DevNoteDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_DevNote);

        } else {
            
            List list = DevNoteDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


    protected boolean loginRequired() {
        return true;
    }

    //  // Configuration Option
    //
    protected String getErrorPage(){return m_actionExtent.getErrorPage();}
    protected String getWarningPage(){return m_actionExtent.getWarningPage();}
    protected String getAfterAddPage(){return m_actionExtent.getAfterAddPage();}
    protected String getAfterEditPage(){return m_actionExtent.getAfterEditPage();}
    protected String getAfterEditFieldPage(){return m_actionExtent.getAfterEditFieldPage();}
    protected String getAfterDeletePage(){return m_actionExtent.getAfterDeletePage();}
    protected String getDefaultPage(){return m_actionExtent.getDefaultPage();}
    protected String getConfirmPage(){return m_actionExtent.getConfirmPage();}

    public String getActionGroupName(){ return "AutositeApp";} 


    // This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
        if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User; 
    }
    
    protected DevNoteDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
