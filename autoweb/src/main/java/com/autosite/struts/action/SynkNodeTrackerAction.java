/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:17 EST 2015

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

import com.autosite.db.SynkNodeTracker;
import com.autosite.ds.SynkNodeTrackerDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.AutositeDataObject;

import com.autosite.struts.action.core.AutositeActionBase;
import com.autosite.struts.form.SynkNodeTrackerForm;
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



/*
Generated: Sat Feb 14 00:17:17 EST 2015
*/

public class SynkNodeTrackerAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(SynkNodeTrackerAction.class);

    public SynkNodeTrackerAction(){
        m_ds = SynkNodeTrackerDS.getInstance();
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

        SynkNodeTrackerForm _SynkNodeTrackerForm = (SynkNodeTrackerForm) form;
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
        SynkNodeTracker _SynkNodeTracker = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _SynkNodeTracker = m_ds.getById(cid);
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
            //SynkNodeTracker _SynkNodeTracker = m_ds.getById(cid);

            if (_SynkNodeTracker == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_SynkNodeTracker.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNodeTracker.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_SynkNodeTracker);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _SynkNodeTrackerForm == null) {
    	            editField(request, response, _SynkNodeTracker);
				} else {
    	            edit(request, response, _SynkNodeTrackerForm, _SynkNodeTracker);
				}
                if (returnObjects != null) returnObjects.put("target", _SynkNodeTracker);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                SynkNodeTracker o = m_ds.getById( _SynkNodeTracker.getId(), true);

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
            //SynkNodeTracker _SynkNodeTracker = m_ds.getById(cid);

            if (_SynkNodeTracker == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SynkNodeTracker.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNodeTracker.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _SynkNodeTracker);
                if (returnObjects != null) returnObjects.put("target", _SynkNodeTracker);
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
            //SynkNodeTracker _SynkNodeTracker = m_ds.getById(cid);

            if (_SynkNodeTracker == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SynkNodeTracker.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNodeTracker.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _SynkNodeTracker);
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

            m_ds.delete(_SynkNodeTracker); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _SynkNodeTracker);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _SynkNodeTracker);
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


            m_logger.info("Creating new SynkNodeTracker" );
            SynkNodeTracker _SynkNodeTrackerNew = new SynkNodeTracker();   

            // Setting IDs for the object
            _SynkNodeTrackerNew.setSiteId(site.getId());
			
            if ( _SynkNodeTrackerForm == null) {
                setFields(request, response, _SynkNodeTrackerNew);
            } else {

            _SynkNodeTrackerNew.setNamespace(WebParamUtil.getStringValue(_SynkNodeTrackerForm.getNamespace()));
            m_logger.debug("setting Namespace=" +_SynkNodeTrackerForm.getNamespace());


            _SynkNodeTrackerNew.setDeviceId(WebParamUtil.getStringValue(_SynkNodeTrackerForm.getDeviceId()));
            m_logger.debug("setting DeviceId=" +_SynkNodeTrackerForm.getDeviceId());


            _SynkNodeTrackerNew.setRemote(WebParamUtil.getIntegerValue(_SynkNodeTrackerForm.getRemote()));
            m_logger.debug("setting Remote=" +_SynkNodeTrackerForm.getRemote());


            _SynkNodeTrackerNew.setStamp(WebParamUtil.getLongValue(_SynkNodeTrackerForm.getStamp()));
            m_logger.debug("setting Stamp=" +_SynkNodeTrackerForm.getStamp());


            _SynkNodeTrackerNew.setTimeCreated(WebParamUtil.getTimestampValue(_SynkNodeTrackerForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SynkNodeTrackerForm.getTimeCreated());

        	_SynkNodeTrackerNew.setTimeCreated(new TimeNow());

            _SynkNodeTrackerNew.setTimeUpdated(WebParamUtil.getTimestampValue(_SynkNodeTrackerForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SynkNodeTrackerForm.getTimeUpdated());

        	_SynkNodeTrackerNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_SynkNodeTrackerNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _SynkNodeTrackerNew);
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
            
            if (_SynkNodeTrackerNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_SynkNodeTrackerNew);
            if (returnObjects != null) returnObjects.put("target", _SynkNodeTrackerNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _SynkNodeTrackerNew);
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
             _SynkNodeTracker =  _SynkNodeTrackerNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _SynkNodeTracker, "cleaner-ticket" );



        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new SynkNodeTracker();
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        SynkNodeTrackerForm _SynkNodeTrackerForm = (SynkNodeTrackerForm) form;
        SynkNodeTracker _SynkNodeTracker = (SynkNodeTracker) dataObject;

        m_logger.debug("Before update " + SynkNodeTrackerDS.objectToString(_SynkNodeTracker));

        _SynkNodeTracker.setNamespace(WebParamUtil.getStringValue(_SynkNodeTrackerForm.getNamespace()));


        _SynkNodeTracker.setDeviceId(WebParamUtil.getStringValue(_SynkNodeTrackerForm.getDeviceId()));


        _SynkNodeTracker.setRemote(WebParamUtil.getIntegerValue(_SynkNodeTrackerForm.getRemote()));


        _SynkNodeTracker.setStamp(WebParamUtil.getLongValue(_SynkNodeTrackerForm.getStamp()));




        _SynkNodeTracker.setTimeUpdated(WebParamUtil.getTimestampValue(_SynkNodeTrackerForm.getTimeUpdated()));

        _SynkNodeTracker.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _SynkNodeTracker);
        m_ds.update(_SynkNodeTracker);
        m_actionExtent.afterUpdate(request, response, _SynkNodeTracker);
        m_logger.debug("After update " + SynkNodeTrackerDS.objectToString(_SynkNodeTracker));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SynkNodeTracker _SynkNodeTracker = (SynkNodeTracker) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
            m_logger.debug("updating param namespace from " +_SynkNodeTracker.getNamespace() + "->" + request.getParameter("namespace"));
            _SynkNodeTracker.setNamespace(WebParamUtil.getStringValue(request.getParameter("namespace")));

        }
        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_SynkNodeTracker.getDeviceId() + "->" + request.getParameter("deviceId"));
            _SynkNodeTracker.setDeviceId(WebParamUtil.getStringValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("remote"))) {
            m_logger.debug("updating param remote from " +_SynkNodeTracker.getRemote() + "->" + request.getParameter("remote"));
            _SynkNodeTracker.setRemote(WebParamUtil.getIntegerValue(request.getParameter("remote")));

        }
        if (!isMissing(request.getParameter("stamp"))) {
            m_logger.debug("updating param stamp from " +_SynkNodeTracker.getStamp() + "->" + request.getParameter("stamp"));
            _SynkNodeTracker.setStamp(WebParamUtil.getLongValue(request.getParameter("stamp")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SynkNodeTracker.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SynkNodeTracker.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SynkNodeTracker.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SynkNodeTracker.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _SynkNodeTracker.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _SynkNodeTracker);
        m_ds.update(_SynkNodeTracker);
        m_actionExtent.afterUpdate(request, response, _SynkNodeTracker);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SynkNodeTracker _SynkNodeTracker = (SynkNodeTracker) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
            m_logger.debug("updating param namespace from " +_SynkNodeTracker.getNamespace() + "->" + request.getParameter("namespace"));
            _SynkNodeTracker.setNamespace(WebParamUtil.getStringValue(request.getParameter("namespace")));

        }
        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_SynkNodeTracker.getDeviceId() + "->" + request.getParameter("deviceId"));
            _SynkNodeTracker.setDeviceId(WebParamUtil.getStringValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("remote"))) {
            m_logger.debug("updating param remote from " +_SynkNodeTracker.getRemote() + "->" + request.getParameter("remote"));
            _SynkNodeTracker.setRemote(WebParamUtil.getIntegerValue(request.getParameter("remote")));

        }
        if (!isMissing(request.getParameter("stamp"))) {
            m_logger.debug("updating param stamp from " +_SynkNodeTracker.getStamp() + "->" + request.getParameter("stamp"));
            _SynkNodeTracker.setStamp(WebParamUtil.getLongValue(request.getParameter("stamp")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SynkNodeTracker.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SynkNodeTracker.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SynkNodeTracker.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SynkNodeTracker.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _SynkNodeTracker.setTimeUpdated(new TimeNow());
        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        SynkNodeTrackerForm _SynkNodeTrackerForm = (SynkNodeTrackerForm) form;
        SynkNodeTracker _SynkNodeTracker = (SynkNodeTracker) dataObject;

            _SynkNodeTracker.setNamespace(WebParamUtil.getStringValue(_SynkNodeTrackerForm.getNamespace()));
            m_logger.debug("setting Namespace=" +_SynkNodeTrackerForm.getNamespace());


            _SynkNodeTracker.setDeviceId(WebParamUtil.getStringValue(_SynkNodeTrackerForm.getDeviceId()));
            m_logger.debug("setting DeviceId=" +_SynkNodeTrackerForm.getDeviceId());


            _SynkNodeTracker.setRemote(WebParamUtil.getIntegerValue(_SynkNodeTrackerForm.getRemote()));
            m_logger.debug("setting Remote=" +_SynkNodeTrackerForm.getRemote());


            _SynkNodeTracker.setStamp(WebParamUtil.getLongValue(_SynkNodeTrackerForm.getStamp()));
            m_logger.debug("setting Stamp=" +_SynkNodeTrackerForm.getStamp());


            _SynkNodeTracker.setTimeCreated(WebParamUtil.getTimestampValue(_SynkNodeTrackerForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SynkNodeTrackerForm.getTimeCreated());

        	_SynkNodeTracker.setTimeCreated(new TimeNow());

            _SynkNodeTracker.setTimeUpdated(WebParamUtil.getTimestampValue(_SynkNodeTrackerForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SynkNodeTrackerForm.getTimeUpdated());

        	_SynkNodeTracker.setTimeUpdated(new TimeNow());


    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SynkNodeTracker _SynkNodeTracker = m_ds.getById(cid);
        SynkNodeTracker _SynkNodeTracker = (SynkNodeTracker) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
			return  JtStringUtil.valueOf(_SynkNodeTracker.getNamespace());
        }
        if (!isMissing(request.getParameter("deviceId"))) {
			return  JtStringUtil.valueOf(_SynkNodeTracker.getDeviceId());
        }
        if (!isMissing(request.getParameter("remote"))) {
			return  JtStringUtil.valueOf(_SynkNodeTracker.getRemote());
        }
        if (!isMissing(request.getParameter("stamp"))) {
			return  JtStringUtil.valueOf(_SynkNodeTracker.getStamp());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_SynkNodeTracker.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_SynkNodeTracker.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, SynkNodeTracker _SynkNodeTracker) {
        if (fieldName == null || fieldName.equals("")|| _SynkNodeTracker == null) return null;
        
        if (fieldName.equals("namespace")) {
            return WebUtil.display(_SynkNodeTracker.getNamespace());
        }
        if (fieldName.equals("deviceId")) {
            return WebUtil.display(_SynkNodeTracker.getDeviceId());
        }
        if (fieldName.equals("remote")) {
            return WebUtil.display(_SynkNodeTracker.getRemote());
        }
        if (fieldName.equals("stamp")) {
            return WebUtil.display(_SynkNodeTracker.getStamp());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_SynkNodeTracker.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_SynkNodeTracker.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        SynkNodeTrackerForm _SynkNodeTrackerForm = (SynkNodeTrackerForm) form;

		if(requestParams.containsKey("namespace"))
			_SynkNodeTrackerForm.setNamespace((String)requestParams.get("namespace"));
		if(requestParams.containsKey("deviceId"))
			_SynkNodeTrackerForm.setDeviceId((String)requestParams.get("deviceId"));
		if(requestParams.containsKey("remote"))
			_SynkNodeTrackerForm.setRemote((String)requestParams.get("remote"));
		if(requestParams.containsKey("stamp"))
			_SynkNodeTrackerForm.setStamp((String)requestParams.get("stamp"));
		if(requestParams.containsKey("timeCreated"))
			_SynkNodeTrackerForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_SynkNodeTrackerForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "synk_node_tracker_home=NULL,/jsp/form/synkNodeTracker_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_node_tracker_list=NULL,/jsp/form/synkNodeTracker_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_node_tracker_form=NULL,/jsp/form/synkNodeTracker_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_node_tracker_ajax=NULL,/jsp/form/synkNodeTracker_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_home=NULL,/jsp/form/synkNodeTracker_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_list=NULL,/jsp/form/synkNodeTracker_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_form=NULL,/jsp/form/synkNodeTracker_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_ajax=NULL,/jsp/form/synkNodeTracker_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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

    public String getActionGroupName(){ return "Synk";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
//    protected SynkNodeTrackerDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
