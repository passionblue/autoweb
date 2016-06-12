/* 
Template last modification history:


Source Generated: Sun Mar 15 01:49:44 EDT 2015

*/


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

import com.autosite.db.GenMain;
import com.autosite.ds.GenMainDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.AutositeDataObject;
import com.autosite.holder.AbstractDataHolder;

import com.autosite.struts.action.core.AutositeActionBase;
import com.autosite.struts.form.GenMainForm;
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


import com.autosite.holder.GenMainDataHolder;

/*
Generated: Sun Mar 15 01:49:44 EDT 2015
*/

public class GenMainAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(GenMainAction.class);

    public GenMainAction(){
        m_ds = GenMainDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
		registerDefaultViewsForAction();
    }

/*
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        GenMainForm _GenMainForm = (GenMainForm) form;
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

		//===================================================================================================================
		// Check if needs confirmTo step
        if ( !isAjaxRequest(request) && (
            hasRequestValue(request, "cancel", "true")||
            hasRequestValue(request, "act", "cancel")||
            hasRequestValue(request, "XXXXXXXXXXX", "true"))) // This line is just added for template. if you dont see any command above, add command in template field
        {    
            if (forwardConfirmTo(request))
                return mapping.findForward("default");
        
        }


		//===================================================================================================================
		// Check the user has permission to run this action 
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


		//===================================================================================================================
		// Find object if parameter has "id" field
        GenMain _GenMain = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _GenMain = m_ds.getById(cid);
		}



        boolean addCommandRoutedToEdit = false;


		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request) || addCommandRoutedToEdit) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //GenMain _GenMain = m_ds.getById(cid);

            if (_GenMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_GenMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenMain.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_GenMain);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _GenMainForm == null) {
    	            editField(request, response, _GenMain);
				} else {
    	            edit(request, response, _GenMainForm, _GenMain);
				}
                if (returnObjects != null) returnObjects.put("target", _GenMain);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                GenMain o = m_ds.getById( _GenMain.getId(), true);

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

            //return mapping.findForward("default");
    
		// ================== EDIT FIELD =====================================================================================
        } else if (isActionCmdEditfield(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //GenMain _GenMain = m_ds.getById(cid);

            if (_GenMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenMain.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _GenMain);
                if (returnObjects != null) returnObjects.put("target", _GenMain);
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
            //return mapping.findForward("default");

		// ================== DEL =====================================================================================
        } else if (isActionCmdDelete(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //GenMain _GenMain = m_ds.getById(cid);

            if (_GenMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenMain.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _GenMain);
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

            m_ds.delete(_GenMain); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _GenMain);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _GenMain);
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


            m_logger.info("Creating new GenMain" );
            GenMain _GenMainNew = new GenMain();   

            // Setting IDs for the object
            _GenMainNew.setSiteId(site.getId());
			
            if ( _GenMainForm == null) {
                setFields(request, response, _GenMainNew);
            } else {

            _GenMainNew.setActive(WebParamUtil.getIntegerValue(_GenMainForm.getActive()));
            m_logger.debug("setting Active=" +_GenMainForm.getActive());


            _GenMainNew.setValue(WebParamUtil.getIntegerValue(_GenMainForm.getValue()));
            m_logger.debug("setting Value=" +_GenMainForm.getValue());


            _GenMainNew.setData(WebParamUtil.getStringValue(_GenMainForm.getData()));
            m_logger.debug("setting Data=" +_GenMainForm.getData());


            _GenMainNew.setRequired(WebParamUtil.getStringValue(_GenMainForm.getRequired()));
            m_logger.debug("setting Required=" +_GenMainForm.getRequired());


            _GenMainNew.setTimeCreated(WebParamUtil.getTimestampValue(_GenMainForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_GenMainForm.getTimeCreated());

        	_GenMainNew.setTimeCreated(new TimeNow());

            _GenMainNew.setTimeUpdated(WebParamUtil.getTimestampValue(_GenMainForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_GenMainForm.getTimeUpdated());

        	_GenMainNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_GenMainNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _GenMainNew);
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
            
            if (_GenMainNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_GenMainNew);
            if (returnObjects != null) returnObjects.put("target", _GenMainNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _GenMainNew);
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
             _GenMain =  _GenMainNew;
            

        } else if ( hasRequestValue(request, "move", "true")  || hasRequestValue(request, "cmd", "move") ) {
            if (!haveAccessToCommand(session, "move" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "move", _GenMain);
                if (returnObjects != null &&  _GenMain!= null ) returnObjects.put("target", _GenMain);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "move", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "move", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "register", "true")  || hasRequestValue(request, "cmd", "register") ) {
            if (!haveAccessToCommand(session, "register" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "register", _GenMain);
                if (returnObjects != null &&  _GenMain!= null ) returnObjects.put("target", _GenMain);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "register", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "register", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "cancel", "true")  || hasRequestValue(request, "cmd", "cancel") ) {
            if (!haveAccessToCommand(session, "cancel" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "cancel", _GenMain);
                if (returnObjects != null &&  _GenMain!= null ) returnObjects.put("target", _GenMain);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "cancel", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "cancel", "error"));
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
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new GenMain();
    }

    protected AbstractDataHolder createModelDataHolder() {
        return new GenMainDataHolder();
    }



    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        GenMainForm _GenMainForm = (GenMainForm) form;
        GenMain _GenMain = (GenMain) dataObject;

        m_logger.debug("Before update " + GenMainDS.objectToString(_GenMain));

        _GenMain.setActive(WebParamUtil.getIntegerValue(_GenMainForm.getActive()));


        _GenMain.setValue(WebParamUtil.getIntegerValue(_GenMainForm.getValue()));


        _GenMain.setData(WebParamUtil.getStringValue(_GenMainForm.getData()));


        _GenMain.setRequired(WebParamUtil.getStringValue(_GenMainForm.getRequired()));




        _GenMain.setTimeUpdated(WebParamUtil.getTimestampValue(_GenMainForm.getTimeUpdated()));

        _GenMain.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _GenMain);
        m_ds.update(_GenMain);
        m_actionExtent.afterUpdate(request, response, _GenMain);
        m_logger.debug("After update " + GenMainDS.objectToString(_GenMain));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        GenMain _GenMain = (GenMain) dataObject;

        if (!isMissing(request.getParameter("active"))) {
            m_logger.debug("updating param active from " +_GenMain.getActive() + "->" + request.getParameter("active"));
            _GenMain.setActive(WebParamUtil.getIntegerValue(request.getParameter("active")));

        }
        if (!isMissing(request.getParameter("value"))) {
            m_logger.debug("updating param value from " +_GenMain.getValue() + "->" + request.getParameter("value"));
            _GenMain.setValue(WebParamUtil.getIntegerValue(request.getParameter("value")));

        }
        if (!isMissing(request.getParameter("data"))) {
            m_logger.debug("updating param data from " +_GenMain.getData() + "->" + request.getParameter("data"));
            _GenMain.setData(WebParamUtil.getStringValue(request.getParameter("data")));

        }
        if (!isMissing(request.getParameter("required"))) {
            m_logger.debug("updating param required from " +_GenMain.getRequired() + "->" + request.getParameter("required"));
            _GenMain.setRequired(WebParamUtil.getStringValue(request.getParameter("required")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_GenMain.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _GenMain.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_GenMain.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _GenMain.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _GenMain.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _GenMain);
        m_ds.update(_GenMain);
        m_actionExtent.afterUpdate(request, response, _GenMain);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        GenMain _GenMain = (GenMain) dataObject;

        if (!isMissing(request.getParameter("active"))) {
            m_logger.debug("updating param active from " +_GenMain.getActive() + "->" + request.getParameter("active"));
            _GenMain.setActive(WebParamUtil.getIntegerValue(request.getParameter("active")));

        }
        if (!isMissing(request.getParameter("value"))) {
            m_logger.debug("updating param value from " +_GenMain.getValue() + "->" + request.getParameter("value"));
            _GenMain.setValue(WebParamUtil.getIntegerValue(request.getParameter("value")));

        }
        if (!isMissing(request.getParameter("data"))) {
            m_logger.debug("updating param data from " +_GenMain.getData() + "->" + request.getParameter("data"));
            _GenMain.setData(WebParamUtil.getStringValue(request.getParameter("data")));

        }
        if (!isMissing(request.getParameter("required"))) {
            m_logger.debug("updating param required from " +_GenMain.getRequired() + "->" + request.getParameter("required"));
            _GenMain.setRequired(WebParamUtil.getStringValue(request.getParameter("required")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_GenMain.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _GenMain.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_GenMain.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _GenMain.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _GenMain.setTimeUpdated(new TimeNow());
        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        GenMainForm _GenMainForm = (GenMainForm) form;
        GenMain _GenMain = (GenMain) dataObject;

            _GenMain.setActive(WebParamUtil.getIntegerValue(_GenMainForm.getActive()));
            m_logger.debug("setting Active=" +_GenMainForm.getActive());


            _GenMain.setValue(WebParamUtil.getIntegerValue(_GenMainForm.getValue()));
            m_logger.debug("setting Value=" +_GenMainForm.getValue());


            _GenMain.setData(WebParamUtil.getStringValue(_GenMainForm.getData()));
            m_logger.debug("setting Data=" +_GenMainForm.getData());


            _GenMain.setRequired(WebParamUtil.getStringValue(_GenMainForm.getRequired()));
            m_logger.debug("setting Required=" +_GenMainForm.getRequired());


            _GenMain.setTimeCreated(WebParamUtil.getTimestampValue(_GenMainForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_GenMainForm.getTimeCreated());

        	_GenMain.setTimeCreated(new TimeNow());

            _GenMain.setTimeUpdated(WebParamUtil.getTimestampValue(_GenMainForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_GenMainForm.getTimeUpdated());

        	_GenMain.setTimeUpdated(new TimeNow());


    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        GenMain _GenMain = m_ds.getById(cid);
        GenMain _GenMain = (GenMain) dataObject;

        if (!isMissing(request.getParameter("active"))) {
			return  JtStringUtil.valueOf(_GenMain.getActive());
        }
        if (!isMissing(request.getParameter("value"))) {
			return  JtStringUtil.valueOf(_GenMain.getValue());
        }
        if (!isMissing(request.getParameter("data"))) {
			return  JtStringUtil.valueOf(_GenMain.getData());
        }
        if (!isMissing(request.getParameter("required"))) {
			return  JtStringUtil.valueOf(_GenMain.getRequired());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_GenMain.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_GenMain.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, GenMain _GenMain) {
        if (fieldName == null || fieldName.equals("")|| _GenMain == null) return null;
        
        if (fieldName.equals("active")) {
            return WebUtil.display(_GenMain.getActive());
        }
        if (fieldName.equals("value")) {
            return WebUtil.display(_GenMain.getValue());
        }
        if (fieldName.equals("data")) {
            return WebUtil.display(_GenMain.getData());
        }
        if (fieldName.equals("required")) {
            return WebUtil.display(_GenMain.getRequired());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_GenMain.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_GenMain.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        GenMainForm _GenMainForm = (GenMainForm) form;

		if(requestParams.containsKey("active"))
			_GenMainForm.setActive((String)requestParams.get("active"));
		if(requestParams.containsKey("value"))
			_GenMainForm.setValue((String)requestParams.get("value"));
		if(requestParams.containsKey("data"))
			_GenMainForm.setData((String)requestParams.get("data"));
		if(requestParams.containsKey("required"))
			_GenMainForm.setRequired((String)requestParams.get("required"));
		if(requestParams.containsKey("timeCreated"))
			_GenMainForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_GenMainForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){

        return synkRequired;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "gen_main_home=NULL,/jsp/form/genMain_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_main_list=NULL,/jsp/form/genMain_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_main_form=NULL,/jsp/form/genMain_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_main_ajax=NULL,/jsp/form/genMain_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "gen_main_home=NULL,/jsp/form/genMain_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_main_list=NULL,/jsp/form/genMain_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_main_form=NULL,/jsp/form/genMain_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_main_ajax=NULL,/jsp/form/genMain_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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

    public String getActionGroupName(){ return "Test2";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
//    protected GenMainDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
