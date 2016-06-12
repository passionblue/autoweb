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

import com.autosite.db.AutositeRemoteDevice;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.AutositeRemoteDeviceForm;
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




public class AutositeRemoteDeviceAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeRemoteDeviceAction.class);

    public AutositeRemoteDeviceAction(){
        m_ds = AutositeRemoteDeviceDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
		registerDefaultViewsForAction();
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        AutositeRemoteDeviceForm _AutositeRemoteDeviceForm = (AutositeRemoteDeviceForm) form;
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


        AutositeRemoteDevice _AutositeRemoteDevice = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _AutositeRemoteDevice = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //AutositeRemoteDevice _AutositeRemoteDevice = m_ds.getById(cid);

            if (_AutositeRemoteDevice == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_AutositeRemoteDevice.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeRemoteDevice.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_AutositeRemoteDevice);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _AutositeRemoteDeviceForm == null) {
    	            editField(request, response, _AutositeRemoteDevice);
				} else {
    	            edit(request, response, _AutositeRemoteDeviceForm, _AutositeRemoteDevice);
				}
                if (returnObjects != null) returnObjects.put("target", _AutositeRemoteDevice);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                AutositeRemoteDevice o = m_ds.getById( _AutositeRemoteDevice.getId(), true);

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
            //AutositeRemoteDevice _AutositeRemoteDevice = m_ds.getById(cid);

            if (_AutositeRemoteDevice == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeRemoteDevice.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeRemoteDevice.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _AutositeRemoteDevice);
                if (returnObjects != null) returnObjects.put("target", _AutositeRemoteDevice);
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
            //AutositeRemoteDevice _AutositeRemoteDevice = m_ds.getById(cid);

            if (_AutositeRemoteDevice == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeRemoteDevice.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeRemoteDevice.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _AutositeRemoteDevice);
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

            m_ds.delete(_AutositeRemoteDevice); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _AutositeRemoteDevice);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _AutositeRemoteDevice);
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


            m_logger.info("Creating new AutositeRemoteDevice" );
            AutositeRemoteDevice _AutositeRemoteDeviceNew = new AutositeRemoteDevice();   

            // Setting IDs for the object
            _AutositeRemoteDeviceNew.setSiteId(site.getId());
			
            if ( _AutositeRemoteDeviceForm == null) {
                setFields(request, response, _AutositeRemoteDeviceNew);
            } else {

            _AutositeRemoteDeviceNew.setDeviceId(WebParamUtil.getStringValue(_AutositeRemoteDeviceForm.getDeviceId()));
            m_logger.debug("setting DeviceId=" +_AutositeRemoteDeviceForm.getDeviceId());


            _AutositeRemoteDeviceNew.setDeviceType(WebParamUtil.getIntegerValue(_AutositeRemoteDeviceForm.getDeviceType()));
            m_logger.debug("setting DeviceType=" +_AutositeRemoteDeviceForm.getDeviceType());


            _AutositeRemoteDeviceNew.setTimeCreated(WebParamUtil.getTimestampValue(_AutositeRemoteDeviceForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_AutositeRemoteDeviceForm.getTimeCreated());

        	_AutositeRemoteDeviceNew.setTimeCreated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_AutositeRemoteDeviceNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _AutositeRemoteDeviceNew);
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
            
            if (_AutositeRemoteDeviceNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_AutositeRemoteDeviceNew);
            if (returnObjects != null) returnObjects.put("target", _AutositeRemoteDeviceNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _AutositeRemoteDeviceNew);
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
             _AutositeRemoteDevice =  _AutositeRemoteDeviceNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _AutositeRemoteDevice, "${ios_scopePrefix}" );
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, AutositeRemoteDeviceForm _AutositeRemoteDeviceForm, AutositeRemoteDevice _AutositeRemoteDevice) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeRemoteDevice _AutositeRemoteDevice = m_ds.getById(cid);

        m_logger.debug("Before update " + AutositeRemoteDeviceDS.objectToString(_AutositeRemoteDevice));

        _AutositeRemoteDevice.setDeviceId(WebParamUtil.getStringValue(_AutositeRemoteDeviceForm.getDeviceId()));


        _AutositeRemoteDevice.setDeviceType(WebParamUtil.getIntegerValue(_AutositeRemoteDeviceForm.getDeviceType()));





        m_actionExtent.beforeUpdate(request, response, _AutositeRemoteDevice);
        m_ds.update(_AutositeRemoteDevice);
        m_actionExtent.afterUpdate(request, response, _AutositeRemoteDevice);
        m_logger.debug("After update " + AutositeRemoteDeviceDS.objectToString(_AutositeRemoteDevice));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeRemoteDevice _AutositeRemoteDevice) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeRemoteDevice _AutositeRemoteDevice = m_ds.getById(cid);

        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_AutositeRemoteDevice.getDeviceId() + "->" + request.getParameter("deviceId"));
            _AutositeRemoteDevice.setDeviceId(WebParamUtil.getStringValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("deviceType"))) {
            m_logger.debug("updating param deviceType from " +_AutositeRemoteDevice.getDeviceType() + "->" + request.getParameter("deviceType"));
            _AutositeRemoteDevice.setDeviceType(WebParamUtil.getIntegerValue(request.getParameter("deviceType")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeRemoteDevice.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeRemoteDevice.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

        m_actionExtent.beforeUpdate(request, response, _AutositeRemoteDevice);
        m_ds.update(_AutositeRemoteDevice);
        m_actionExtent.afterUpdate(request, response, _AutositeRemoteDevice);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeRemoteDevice _AutositeRemoteDevice) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeRemoteDevice _AutositeRemoteDevice = m_ds.getById(cid);

        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_AutositeRemoteDevice.getDeviceId() + "->" + request.getParameter("deviceId"));
            _AutositeRemoteDevice.setDeviceId(WebParamUtil.getStringValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("deviceType"))) {
            m_logger.debug("updating param deviceType from " +_AutositeRemoteDevice.getDeviceType() + "->" + request.getParameter("deviceType"));
            _AutositeRemoteDevice.setDeviceType(WebParamUtil.getIntegerValue(request.getParameter("deviceType")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeRemoteDevice.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeRemoteDevice.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeRemoteDevice _AutositeRemoteDevice) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeRemoteDevice _AutositeRemoteDevice = m_ds.getById(cid);

        if (!isMissing(request.getParameter("deviceId"))) {
			return  JtStringUtil.valueOf(_AutositeRemoteDevice.getDeviceId());
        }
        if (!isMissing(request.getParameter("deviceType"))) {
			return  JtStringUtil.valueOf(_AutositeRemoteDevice.getDeviceType());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_AutositeRemoteDevice.getTimeCreated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeRemoteDevice _AutositeRemoteDevice) throws Exception {
    }

    protected String getFieldByName(String fieldName, AutositeRemoteDevice _AutositeRemoteDevice) {
        if (fieldName == null || fieldName.equals("")|| _AutositeRemoteDevice == null) return null;
        
        if (fieldName.equals("deviceId")) {
            return WebUtil.display(_AutositeRemoteDevice.getDeviceId());
        }
        if (fieldName.equals("deviceType")) {
            return WebUtil.display(_AutositeRemoteDevice.getDeviceType());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_AutositeRemoteDevice.getTimeCreated());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        AutositeRemoteDeviceForm _AutositeRemoteDeviceForm = (AutositeRemoteDeviceForm) form;

		if(requestParams.containsKey("deviceId"))
			_AutositeRemoteDeviceForm.setDeviceId((String)requestParams.get("deviceId"));
		if(requestParams.containsKey("deviceType"))
			_AutositeRemoteDeviceForm.setDeviceType((String)requestParams.get("deviceType"));
		if(requestParams.containsKey("timeCreated"))
			_AutositeRemoteDeviceForm.setTimeCreated((String)requestParams.get("timeCreated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "autosite_remote_device_home=NULL,/jsp/form/autositeRemoteDevice_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_remote_device_list=NULL,/jsp/form/autositeRemoteDevice_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_remote_device_form=NULL,/jsp/form/autositeRemoteDevice_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_remote_device_ajax=NULL,/jsp/form/autositeRemoteDevice_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "autosite_remote_device_home=NULL,/jsp/form/autositeRemoteDevice_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_remote_device_list=NULL,/jsp/form/autositeRemoteDevice_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_remote_device_form=NULL,/jsp/form/autositeRemoteDevice_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_remote_device_ajax=NULL,/jsp/form/autositeRemoteDevice_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected AutositeRemoteDeviceDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
