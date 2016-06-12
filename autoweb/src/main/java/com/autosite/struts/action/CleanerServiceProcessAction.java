package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.jtrend.util.JtStringUtil;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import com.jtrend.util.RequestUtil;
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

import com.autosite.db.CleanerServiceProcess;
import com.autosite.ds.CleanerServiceProcessDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerServiceProcessForm;
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




public class CleanerServiceProcessAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerServiceProcessAction.class);

    public CleanerServiceProcessAction(){
        m_ds = CleanerServiceProcessDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        CleanerServiceProcessForm _CleanerServiceProcessForm = (CleanerServiceProcessForm) form;
        HttpSession session = request.getSession();
        DefaultPageForwardManager pageManager = DefaultPageForwardManager.getInstance();
        String sentPage = getSentPage(request);

        setPage(session, getDefaultPage()); //TODO changed all setPage to  processPageForAction() in other places. dont know what to do here

        Site site = getSite(request);
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }

		// Check if needs confirmTo step
        if ( !isAjaxRequest(request) && (
            hasRequestValue(request, "XXXXXXXXXXX", "true"))) // This line is just added for template. if you dont see any command above, add command in template field
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
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        CleanerServiceProcess _CleanerServiceProcess = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerServiceProcess = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerServiceProcess _CleanerServiceProcess = m_ds.getById(cid);

            if (_CleanerServiceProcess == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerServiceProcess.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceProcess.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerServiceProcess);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _CleanerServiceProcessForm, _CleanerServiceProcess);
                if (returnObjects != null) returnObjects.put("target", _CleanerServiceProcess);
            }

            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session, ((ActionExtentException)e).getForwardPage(), 
                            pageManager.isInternalForward(getActionName(), "edit", "error"));
                return mapping.findForward("default");
            }

			//Default error page but will be overridden by exception specific error page
            processPageForAction(request, "edit", "success", getAfterEditPage());

            return mapping.findForward("default");
    
		// ================== EDIT FIELD =====================================================================================
        } else if (isActionCmdEditfield(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerServiceProcess _CleanerServiceProcess = m_ds.getById(cid);

            if (_CleanerServiceProcess == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerServiceProcess.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceProcess.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerServiceProcess);
                if (returnObjects != null) returnObjects.put("target", _CleanerServiceProcess);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "editfield", "error"));
                return mapping.findForward("default");
            }

            processPageForAction(request, "editfield", "success", getAfterEditPage());
            return mapping.findForward("default");

		// ================== DEL =====================================================================================
        } else if (isActionCmdDelete(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerServiceProcess _CleanerServiceProcess = m_ds.getById(cid);

            if (_CleanerServiceProcess == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerServiceProcess.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceProcess.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerServiceProcess);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "delete", "error"));

                return mapping.findForward("default");
            }

            m_ds.delete(_CleanerServiceProcess); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerServiceProcess);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerServiceProcess);
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

		// ================== ADD =====================================================================================
        } else if (isActionCmdAdd(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request)) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 


            m_logger.info("Creating new CleanerServiceProcess" );
            CleanerServiceProcess _CleanerServiceProcessNew = new CleanerServiceProcess();   

            // Setting IDs for the object
            _CleanerServiceProcessNew.setSiteId(site.getId());

            _CleanerServiceProcessNew.setTicketId(WebParamUtil.getLongValue(_CleanerServiceProcessForm.getTicketId()));
            m_logger.debug("setting TicketId=" +_CleanerServiceProcessForm.getTicketId());
            _CleanerServiceProcessNew.setProcessUserId(WebParamUtil.getLongValue(_CleanerServiceProcessForm.getProcessUserId()));
            m_logger.debug("setting ProcessUserId=" +_CleanerServiceProcessForm.getProcessUserId());
            _CleanerServiceProcessNew.setProcessType(WebParamUtil.getLongValue(_CleanerServiceProcessForm.getProcessType()));
            m_logger.debug("setting ProcessType=" +_CleanerServiceProcessForm.getProcessType());
            _CleanerServiceProcessNew.setTimeStarted(WebParamUtil.getTimestampValue(_CleanerServiceProcessForm.getTimeStarted()));
            m_logger.debug("setting TimeStarted=" +_CleanerServiceProcessForm.getTimeStarted());
            _CleanerServiceProcessNew.setTimeEnded(WebParamUtil.getTimestampValue(_CleanerServiceProcessForm.getTimeEnded()));
            m_logger.debug("setting TimeEnded=" +_CleanerServiceProcessForm.getTimeEnded());
            _CleanerServiceProcessNew.setNote(WebParamUtil.getStringValue(_CleanerServiceProcessForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerServiceProcessForm.getNote());


            try {
                checkDepedenceIntegrity(_CleanerServiceProcessNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerServiceProcessNew);
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
            
            if (_CleanerServiceProcessNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerServiceProcessNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerServiceProcess);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerServiceProcessNew);
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
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerServiceProcessForm _CleanerServiceProcessForm, CleanerServiceProcess _CleanerServiceProcess) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceProcess _CleanerServiceProcess = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerServiceProcessDS.objectToString(_CleanerServiceProcess));

        _CleanerServiceProcess.setTicketId(WebParamUtil.getLongValue(_CleanerServiceProcessForm.getTicketId()));
        _CleanerServiceProcess.setProcessUserId(WebParamUtil.getLongValue(_CleanerServiceProcessForm.getProcessUserId()));
        _CleanerServiceProcess.setProcessType(WebParamUtil.getLongValue(_CleanerServiceProcessForm.getProcessType()));
        _CleanerServiceProcess.setTimeStarted(WebParamUtil.getTimestampValue(_CleanerServiceProcessForm.getTimeStarted()));
        _CleanerServiceProcess.setTimeEnded(WebParamUtil.getTimestampValue(_CleanerServiceProcessForm.getTimeEnded()));
        _CleanerServiceProcess.setNote(WebParamUtil.getStringValue(_CleanerServiceProcessForm.getNote()));

        m_actionExtent.beforeUpdate(request, response, _CleanerServiceProcess);
        m_ds.update(_CleanerServiceProcess);
        m_actionExtent.afterUpdate(request, response, _CleanerServiceProcess);
        m_logger.debug("After update " + CleanerServiceProcessDS.objectToString(_CleanerServiceProcess));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerServiceProcess _CleanerServiceProcess) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceProcess _CleanerServiceProcess = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ticketId"))) {
            m_logger.debug("updating param ticketId from " +_CleanerServiceProcess.getTicketId() + "->" + request.getParameter("ticketId"));
            _CleanerServiceProcess.setTicketId(WebParamUtil.getLongValue(request.getParameter("ticketId")));

        }
        if (!isMissing(request.getParameter("processUserId"))) {
            m_logger.debug("updating param processUserId from " +_CleanerServiceProcess.getProcessUserId() + "->" + request.getParameter("processUserId"));
            _CleanerServiceProcess.setProcessUserId(WebParamUtil.getLongValue(request.getParameter("processUserId")));

        }
        if (!isMissing(request.getParameter("processType"))) {
            m_logger.debug("updating param processType from " +_CleanerServiceProcess.getProcessType() + "->" + request.getParameter("processType"));
            _CleanerServiceProcess.setProcessType(WebParamUtil.getLongValue(request.getParameter("processType")));

        }
        if (!isMissing(request.getParameter("timeStarted"))) {
            m_logger.debug("updating param timeStarted from " +_CleanerServiceProcess.getTimeStarted() + "->" + request.getParameter("timeStarted"));
            _CleanerServiceProcess.setTimeStarted(WebParamUtil.getTimestampValue(request.getParameter("timeStarted")));

        }
        if (!isMissing(request.getParameter("timeEnded"))) {
            m_logger.debug("updating param timeEnded from " +_CleanerServiceProcess.getTimeEnded() + "->" + request.getParameter("timeEnded"));
            _CleanerServiceProcess.setTimeEnded(WebParamUtil.getTimestampValue(request.getParameter("timeEnded")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerServiceProcess.getNote() + "->" + request.getParameter("note"));
            _CleanerServiceProcess.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerServiceProcess);
        m_ds.update(_CleanerServiceProcess);
        m_actionExtent.afterUpdate(request, response, _CleanerServiceProcess);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerServiceProcess _CleanerServiceProcess) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceProcess _CleanerServiceProcess = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ticketId"))) {
			return  JtStringUtil.valueOf(_CleanerServiceProcess.getTicketId());
        }
        if (!isMissing(request.getParameter("processUserId"))) {
			return  JtStringUtil.valueOf(_CleanerServiceProcess.getProcessUserId());
        }
        if (!isMissing(request.getParameter("processType"))) {
			return  JtStringUtil.valueOf(_CleanerServiceProcess.getProcessType());
        }
        if (!isMissing(request.getParameter("timeStarted"))) {
			return  JtStringUtil.valueOf(_CleanerServiceProcess.getTimeStarted());
        }
        if (!isMissing(request.getParameter("timeEnded"))) {
			return  JtStringUtil.valueOf(_CleanerServiceProcess.getTimeEnded());
        }
        if (!isMissing(request.getParameter("note"))) {
			return  JtStringUtil.valueOf(_CleanerServiceProcess.getNote());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerServiceProcess _CleanerServiceProcess) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerServiceProcess _CleanerServiceProcess) {
        if (fieldName == null || fieldName.equals("")|| _CleanerServiceProcess == null) return null;
        
        if (fieldName.equals("ticketId")) {
            return WebUtil.display(_CleanerServiceProcess.getTicketId());
        }
        if (fieldName.equals("processUserId")) {
            return WebUtil.display(_CleanerServiceProcess.getProcessUserId());
        }
        if (fieldName.equals("processType")) {
            return WebUtil.display(_CleanerServiceProcess.getProcessType());
        }
        if (fieldName.equals("timeStarted")) {
            return WebUtil.display(_CleanerServiceProcess.getTimeStarted());
        }
        if (fieldName.equals("timeEnded")) {
            return WebUtil.display(_CleanerServiceProcess.getTimeEnded());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_CleanerServiceProcess.getNote());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        CleanerServiceProcess target = null;
        
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
                target = (CleanerServiceProcess) working.get("target");
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
            CleanerServiceProcess _CleanerServiceProcess = CleanerServiceProcessDS.getInstance().getById(id);
            if (_CleanerServiceProcess == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerServiceProcess);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerServiceProcess _CleanerServiceProcess = CleanerServiceProcessDS.getInstance().getById(id);
            if ( _CleanerServiceProcess == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerServiceProcess);
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

            buf.append("<div id=\"cleanerServiceProcess-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerServiceProcess _CleanerServiceProcess = (CleanerServiceProcess) iterator.next();

                buf.append("<div id=\"cleanerServiceProcess-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
                    buf.append("<div id=\"cleanerServiceProcess-ajax-ticketId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceProcess.getTicketId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("processUserId")) {
                    buf.append("<div id=\"cleanerServiceProcess-ajax-processUserId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceProcess.getProcessUserId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("processType")) {
                    buf.append("<div id=\"cleanerServiceProcess-ajax-processType\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceProcess.getProcessType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeStarted")) {
                    buf.append("<div id=\"cleanerServiceProcess-ajax-timeStarted\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceProcess.getTimeStarted()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeEnded")) {
                    buf.append("<div id=\"cleanerServiceProcess-ajax-timeEnded\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceProcess.getTimeEnded()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"cleanerServiceProcess-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceProcess.getNote()));
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
            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Ticket Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("processUserId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Process User Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("processType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Process Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeStarted")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Started");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeEnded")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Ended");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerServiceProcess _CleanerServiceProcess = (CleanerServiceProcess) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceProcess.getTicketId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("processUserId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceProcess.getProcessUserId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("processType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceProcess.getProcessType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeStarted")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceProcess.getTimeStarted()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeEnded")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceProcess.getTimeEnded()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceProcess.getNote()));

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
	                CleanerServiceProcess _CleanerServiceProcess = (CleanerServiceProcess) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("ticketId")) 
			            json.put("ticketId", ""+_CleanerServiceProcess.getTicketId());
		            if ( ignoreFieldSet || fieldSet.contains("processUserId")) 
			            json.put("processUserId", ""+_CleanerServiceProcess.getProcessUserId());
		            if ( ignoreFieldSet || fieldSet.contains("processType")) 
			            json.put("processType", ""+_CleanerServiceProcess.getProcessType());
		            if ( ignoreFieldSet || fieldSet.contains("timeStarted")) 
			            json.put("timeStarted", ""+_CleanerServiceProcess.getTimeStarted());
		            if ( ignoreFieldSet || fieldSet.contains("timeEnded")) 
			            json.put("timeEnded", ""+_CleanerServiceProcess.getTimeEnded());
		            if ( ignoreFieldSet || fieldSet.contains("note")) 
			            json.put("note", ""+_CleanerServiceProcess.getNote());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                CleanerServiceProcess _CleanerServiceProcess = list.size() >=1?(CleanerServiceProcess) list.get(0): null; 

				if ( _CleanerServiceProcess != null) {
		            top.put("id", ""+_CleanerServiceProcess.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonTicketId = new JSONObject();
		            jsonTicketId.put("name", "ticketId");
		            jsonTicketId.put("value", ""+_CleanerServiceProcess.getTicketId());
		            array.put(jsonTicketId);
		            JSONObject jsonProcessUserId = new JSONObject();
		            jsonProcessUserId.put("name", "processUserId");
		            jsonProcessUserId.put("value", ""+_CleanerServiceProcess.getProcessUserId());
		            array.put(jsonProcessUserId);
		            JSONObject jsonProcessType = new JSONObject();
		            jsonProcessType.put("name", "processType");
		            jsonProcessType.put("value", ""+_CleanerServiceProcess.getProcessType());
		            array.put(jsonProcessType);
		            JSONObject jsonTimeStarted = new JSONObject();
		            jsonTimeStarted.put("name", "timeStarted");
		            jsonTimeStarted.put("value", ""+_CleanerServiceProcess.getTimeStarted());
		            array.put(jsonTimeStarted);
		            JSONObject jsonTimeEnded = new JSONObject();
		            jsonTimeEnded.put("name", "timeEnded");
		            jsonTimeEnded.put("value", ""+_CleanerServiceProcess.getTimeEnded());
		            array.put(jsonTimeEnded);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerServiceProcess.getNote());
		            array.put(jsonNote);

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
            buf.append("sendFormAjax('/cleanerServiceProcessAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerServiceProcessAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
                String value = WebUtil.display(request.getParameter("ticketId"));

                if ( forceHiddenSet.contains("ticketId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ticketId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ticket Id</div>");
            buf.append("<INPUT NAME=\"ticketId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("processUserId")) {
                String value = WebUtil.display(request.getParameter("processUserId"));

                if ( forceHiddenSet.contains("processUserId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"processUserId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Process User Id</div>");
            buf.append("<INPUT NAME=\"processUserId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("processType")) {
                String value = WebUtil.display(request.getParameter("processType"));

                if ( forceHiddenSet.contains("processType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"processType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Process Type</div>");
            buf.append("<INPUT NAME=\"processType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeStarted")) {
                String value = WebUtil.display(request.getParameter("timeStarted"));

                if ( forceHiddenSet.contains("timeStarted")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeStarted\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Started</div>");
            buf.append("<INPUT NAME=\"timeStarted\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeEnded")) {
                String value = WebUtil.display(request.getParameter("timeEnded"));

                if ( forceHiddenSet.contains("timeEnded")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeEnded\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Ended</div>");
            buf.append("<INPUT NAME=\"timeEnded\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("note")) {
                String value = WebUtil.display(request.getParameter("note"));

                if ( forceHiddenSet.contains("note")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"note\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Note</div>");
            buf.append("<INPUT NAME=\"note\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_cleanerServiceProcess(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerServiceProcess\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerServiceProcess(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerServiceProcessFormAddDis','/cleanerServiceProcessAction.html', 'resultDisplayCleanerServiceProcess', '${ajax_response_fields}', responseCallback_cleanerServiceProcess);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerServiceProcess(){\n";
            importedScripts +=     "clearFormXX('cleanerServiceProcessFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerServiceProcess(){\n";
            importedScripts +=     "backToXX('cleanerServiceProcessFormAddDis','resultDisplayCleanerServiceProcess');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerServiceProcessFormAddDis\" method=\"POST\" action=\"/cleanerServiceProcessAction.html\" id=\"cleanerServiceProcessFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Ticket Id</div>");
        buf.append("<input class=\"field\" id=\"ticketId\" type=\"text\" size=\"70\" name=\"ticketId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Process User Id</div>");
        buf.append("<input class=\"field\" id=\"processUserId\" type=\"text\" size=\"70\" name=\"processUserId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Process Type</div>");
        buf.append("<input class=\"field\" id=\"processType\" type=\"text\" size=\"70\" name=\"processType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Started</div>");
        buf.append("<input class=\"field\" id=\"timeStarted\" type=\"text\" size=\"70\" name=\"timeStarted\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Ended</div>");
        buf.append("<input class=\"field\" id=\"timeEnded\" type=\"text\" size=\"70\" name=\"timeEnded\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
        buf.append("<input class=\"field\" id=\"note\" type=\"text\" size=\"70\" name=\"note\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerServiceProcess()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerServiceProcess()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerServiceProcess\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerServiceProcess()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, CleanerServiceProcess target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerServiceProcess _CleanerServiceProcess = null; 
            List list = CleanerServiceProcessDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerServiceProcess = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerServiceProcess = (CleanerServiceProcess) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerServiceProcess = (CleanerServiceProcess) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerServiceProcess = CleanerServiceProcessDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerServiceProcess);

        } else {
            
            List list = CleanerServiceProcessDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerServiceProcessForm _CleanerServiceProcessForm = (CleanerServiceProcessForm) form;

		if(requestParams.containsKey("ticketId"))
			_CleanerServiceProcessForm.setTicketId((String)requestParams.get("ticketId"));
		if(requestParams.containsKey("processUserId"))
			_CleanerServiceProcessForm.setProcessUserId((String)requestParams.get("processUserId"));
		if(requestParams.containsKey("processType"))
			_CleanerServiceProcessForm.setProcessType((String)requestParams.get("processType"));
		if(requestParams.containsKey("timeStarted"))
			_CleanerServiceProcessForm.setTimeStarted((String)requestParams.get("timeStarted"));
		if(requestParams.containsKey("timeEnded"))
			_CleanerServiceProcessForm.setTimeEnded((String)requestParams.get("timeEnded"));
		if(requestParams.containsKey("note"))
			_CleanerServiceProcessForm.setNote((String)requestParams.get("note"));
    }


    protected boolean loginRequired() {
        return true;
    }

	//	// Configuration Option
	//
    protected String getErrorPage(){return m_actionExtent.getErrorPage();}
    protected String getWarningPage(){return m_actionExtent.getWarningPage();}
    protected String getAfterAddPage(){return m_actionExtent.getAfterAddPage();}
    protected String getAfterEditPage(){return m_actionExtent.getAfterEditPage();}
    protected String getAfterEditFieldPage(){return m_actionExtent.getAfterEditFieldPage();}
    protected String getAfterDeletePage(){return m_actionExtent.getAfterDeletePage();}
    protected String getDefaultPage(){return m_actionExtent.getDefaultPage();}
    protected String getConfirmPage(){return m_actionExtent.getConfirmPage();}

    public String getActionGroupName(){ return "ChurApp";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
    protected CleanerServiceProcessDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
