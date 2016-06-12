/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:46 EDT 2015

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

import com.autosite.db.CleanerPickupDeliveryConfig;
import com.autosite.ds.CleanerPickupDeliveryConfigDS;
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
import com.autosite.struts.form.CleanerPickupDeliveryConfigForm;
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


import com.autosite.holder.CleanerPickupDeliveryConfigDataHolder;

/*
Generated: Sun Mar 15 13:54:46 EDT 2015
*/

public class CleanerPickupDeliveryConfigAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerPickupDeliveryConfigAction.class);

    public CleanerPickupDeliveryConfigAction(){
        m_ds = CleanerPickupDeliveryConfigDS.getInstance();
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

        CleanerPickupDeliveryConfigForm _CleanerPickupDeliveryConfigForm = (CleanerPickupDeliveryConfigForm) form;
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
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerPickupDeliveryConfig = m_ds.getById(cid);
		}



        boolean addCommandRoutedToEdit = false;

        
        // added by enforceSingleRecordPerSite switch in AutoGen
        // This site enforces the sing record per site. If there is one m
        if (isActionCmdAdd(request)) {
            List recordsForTheSite  = CleanerPickupDeliveryConfigDS.getInstance().getBySiteId(site.getId());
            if ( recordsForTheSite.size() > 0) { //This should be counted as only 1. If not something not right.
                if ( recordsForTheSite.size() > 1) {
                    m_logger.warn("There must only single record per site for CleanerPickupDeliveryConfig. But " + recordsForTheSite.size() + " records found");
                }
    
                _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig)recordsForTheSite.get(0);
                m_logger.debug("Add command received but CleanerPickupDeliveryConfig enforces single record per site. So existing record will be updated id " + _CleanerPickupDeliveryConfig.getId());

        		_CleanerPickupDeliveryConfig.setLocationId(WebParamUtil.getLongValue(_CleanerPickupDeliveryConfigForm.getLocationId()));
        		_CleanerPickupDeliveryConfig.setApplyAllLocations(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getApplyAllLocations()));
        		_CleanerPickupDeliveryConfig.setDisableWebRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisableWebRequest()));
        		_CleanerPickupDeliveryConfig.setDisallowAnonymousRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisallowAnonymousRequest()));
        		_CleanerPickupDeliveryConfig.setRequireCustomerRegister(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerRegister()));
        		_CleanerPickupDeliveryConfig.setRequireCustomerLogin(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerLogin()));
                addCommandRoutedToEdit = true;
            }        
        }        

		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request) || addCommandRoutedToEdit) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = m_ds.getById(cid);

            if (_CleanerPickupDeliveryConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerPickupDeliveryConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDeliveryConfig.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerPickupDeliveryConfig);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerPickupDeliveryConfigForm == null) {
    	            editField(request, response, _CleanerPickupDeliveryConfig);
				} else {
    	            edit(request, response, _CleanerPickupDeliveryConfigForm, _CleanerPickupDeliveryConfig);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryConfig);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerPickupDeliveryConfig o = m_ds.getById( _CleanerPickupDeliveryConfig.getId(), true);

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
            //CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = m_ds.getById(cid);

            if (_CleanerPickupDeliveryConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerPickupDeliveryConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDeliveryConfig.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerPickupDeliveryConfig);
                if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryConfig);
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
            //CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = m_ds.getById(cid);

            if (_CleanerPickupDeliveryConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerPickupDeliveryConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDeliveryConfig.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerPickupDeliveryConfig);
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

            m_ds.delete(_CleanerPickupDeliveryConfig); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryConfig);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerPickupDeliveryConfig);
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


            m_logger.info("Creating new CleanerPickupDeliveryConfig" );
            CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfigNew = new CleanerPickupDeliveryConfig();   

            // Setting IDs for the object
            _CleanerPickupDeliveryConfigNew.setSiteId(site.getId());
			
            if ( _CleanerPickupDeliveryConfigForm == null) {
                setFields(request, response, _CleanerPickupDeliveryConfigNew);
            } else {

            _CleanerPickupDeliveryConfigNew.setLocationId(WebParamUtil.getLongValue(_CleanerPickupDeliveryConfigForm.getLocationId()));
            m_logger.debug("setting LocationId=" +_CleanerPickupDeliveryConfigForm.getLocationId());


            _CleanerPickupDeliveryConfigNew.setApplyAllLocations(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getApplyAllLocations()));
            m_logger.debug("setting ApplyAllLocations=" +_CleanerPickupDeliveryConfigForm.getApplyAllLocations());


            _CleanerPickupDeliveryConfigNew.setDisableWebRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisableWebRequest()));
            m_logger.debug("setting DisableWebRequest=" +_CleanerPickupDeliveryConfigForm.getDisableWebRequest());


            _CleanerPickupDeliveryConfigNew.setDisallowAnonymousRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisallowAnonymousRequest()));
            m_logger.debug("setting DisallowAnonymousRequest=" +_CleanerPickupDeliveryConfigForm.getDisallowAnonymousRequest());


            _CleanerPickupDeliveryConfigNew.setRequireCustomerRegister(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerRegister()));
            m_logger.debug("setting RequireCustomerRegister=" +_CleanerPickupDeliveryConfigForm.getRequireCustomerRegister());


            _CleanerPickupDeliveryConfigNew.setRequireCustomerLogin(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerLogin()));
            m_logger.debug("setting RequireCustomerLogin=" +_CleanerPickupDeliveryConfigForm.getRequireCustomerLogin());


			}

            try {
                checkDepedenceIntegrity(_CleanerPickupDeliveryConfigNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerPickupDeliveryConfigNew);
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
            
            if (_CleanerPickupDeliveryConfigNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerPickupDeliveryConfigNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryConfigNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerPickupDeliveryConfigNew);
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
             _CleanerPickupDeliveryConfig =  _CleanerPickupDeliveryConfigNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerPickupDeliveryConfig, "cleaner-pickup-delivery-config" );



        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new CleanerPickupDeliveryConfig();
    }

    protected AbstractDataHolder createModelDataHolder() {
        return new CleanerPickupDeliveryConfigDataHolder();
    }



    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        CleanerPickupDeliveryConfigForm _CleanerPickupDeliveryConfigForm = (CleanerPickupDeliveryConfigForm) form;
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) dataObject;

        m_logger.debug("Before update " + CleanerPickupDeliveryConfigDS.objectToString(_CleanerPickupDeliveryConfig));

        _CleanerPickupDeliveryConfig.setLocationId(WebParamUtil.getLongValue(_CleanerPickupDeliveryConfigForm.getLocationId()));


        _CleanerPickupDeliveryConfig.setApplyAllLocations(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getApplyAllLocations()));


        _CleanerPickupDeliveryConfig.setDisableWebRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisableWebRequest()));


        _CleanerPickupDeliveryConfig.setDisallowAnonymousRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisallowAnonymousRequest()));


        _CleanerPickupDeliveryConfig.setRequireCustomerRegister(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerRegister()));


        _CleanerPickupDeliveryConfig.setRequireCustomerLogin(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerLogin()));



        m_actionExtent.beforeUpdate(request, response, _CleanerPickupDeliveryConfig);
        m_ds.update(_CleanerPickupDeliveryConfig);
        m_actionExtent.afterUpdate(request, response, _CleanerPickupDeliveryConfig);
        m_logger.debug("After update " + CleanerPickupDeliveryConfigDS.objectToString(_CleanerPickupDeliveryConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) dataObject;

        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerPickupDeliveryConfig.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerPickupDeliveryConfig.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("applyAllLocations"))) {
            m_logger.debug("updating param applyAllLocations from " +_CleanerPickupDeliveryConfig.getApplyAllLocations() + "->" + request.getParameter("applyAllLocations"));
            _CleanerPickupDeliveryConfig.setApplyAllLocations(WebParamUtil.getIntegerValue(request.getParameter("applyAllLocations")));

        }
        if (!isMissing(request.getParameter("disableWebRequest"))) {
            m_logger.debug("updating param disableWebRequest from " +_CleanerPickupDeliveryConfig.getDisableWebRequest() + "->" + request.getParameter("disableWebRequest"));
            _CleanerPickupDeliveryConfig.setDisableWebRequest(WebParamUtil.getIntegerValue(request.getParameter("disableWebRequest")));

        }
        if (!isMissing(request.getParameter("disallowAnonymousRequest"))) {
            m_logger.debug("updating param disallowAnonymousRequest from " +_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest() + "->" + request.getParameter("disallowAnonymousRequest"));
            _CleanerPickupDeliveryConfig.setDisallowAnonymousRequest(WebParamUtil.getIntegerValue(request.getParameter("disallowAnonymousRequest")));

        }
        if (!isMissing(request.getParameter("requireCustomerRegister"))) {
            m_logger.debug("updating param requireCustomerRegister from " +_CleanerPickupDeliveryConfig.getRequireCustomerRegister() + "->" + request.getParameter("requireCustomerRegister"));
            _CleanerPickupDeliveryConfig.setRequireCustomerRegister(WebParamUtil.getIntegerValue(request.getParameter("requireCustomerRegister")));

        }
        if (!isMissing(request.getParameter("requireCustomerLogin"))) {
            m_logger.debug("updating param requireCustomerLogin from " +_CleanerPickupDeliveryConfig.getRequireCustomerLogin() + "->" + request.getParameter("requireCustomerLogin"));
            _CleanerPickupDeliveryConfig.setRequireCustomerLogin(WebParamUtil.getIntegerValue(request.getParameter("requireCustomerLogin")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerPickupDeliveryConfig);
        m_ds.update(_CleanerPickupDeliveryConfig);
        m_actionExtent.afterUpdate(request, response, _CleanerPickupDeliveryConfig);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) dataObject;

        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerPickupDeliveryConfig.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerPickupDeliveryConfig.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("applyAllLocations"))) {
            m_logger.debug("updating param applyAllLocations from " +_CleanerPickupDeliveryConfig.getApplyAllLocations() + "->" + request.getParameter("applyAllLocations"));
            _CleanerPickupDeliveryConfig.setApplyAllLocations(WebParamUtil.getIntegerValue(request.getParameter("applyAllLocations")));

        }
        if (!isMissing(request.getParameter("disableWebRequest"))) {
            m_logger.debug("updating param disableWebRequest from " +_CleanerPickupDeliveryConfig.getDisableWebRequest() + "->" + request.getParameter("disableWebRequest"));
            _CleanerPickupDeliveryConfig.setDisableWebRequest(WebParamUtil.getIntegerValue(request.getParameter("disableWebRequest")));

        }
        if (!isMissing(request.getParameter("disallowAnonymousRequest"))) {
            m_logger.debug("updating param disallowAnonymousRequest from " +_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest() + "->" + request.getParameter("disallowAnonymousRequest"));
            _CleanerPickupDeliveryConfig.setDisallowAnonymousRequest(WebParamUtil.getIntegerValue(request.getParameter("disallowAnonymousRequest")));

        }
        if (!isMissing(request.getParameter("requireCustomerRegister"))) {
            m_logger.debug("updating param requireCustomerRegister from " +_CleanerPickupDeliveryConfig.getRequireCustomerRegister() + "->" + request.getParameter("requireCustomerRegister"));
            _CleanerPickupDeliveryConfig.setRequireCustomerRegister(WebParamUtil.getIntegerValue(request.getParameter("requireCustomerRegister")));

        }
        if (!isMissing(request.getParameter("requireCustomerLogin"))) {
            m_logger.debug("updating param requireCustomerLogin from " +_CleanerPickupDeliveryConfig.getRequireCustomerLogin() + "->" + request.getParameter("requireCustomerLogin"));
            _CleanerPickupDeliveryConfig.setRequireCustomerLogin(WebParamUtil.getIntegerValue(request.getParameter("requireCustomerLogin")));

        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        CleanerPickupDeliveryConfigForm _CleanerPickupDeliveryConfigForm = (CleanerPickupDeliveryConfigForm) form;
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) dataObject;

            _CleanerPickupDeliveryConfig.setLocationId(WebParamUtil.getLongValue(_CleanerPickupDeliveryConfigForm.getLocationId()));
            m_logger.debug("setting LocationId=" +_CleanerPickupDeliveryConfigForm.getLocationId());


            _CleanerPickupDeliveryConfig.setApplyAllLocations(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getApplyAllLocations()));
            m_logger.debug("setting ApplyAllLocations=" +_CleanerPickupDeliveryConfigForm.getApplyAllLocations());


            _CleanerPickupDeliveryConfig.setDisableWebRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisableWebRequest()));
            m_logger.debug("setting DisableWebRequest=" +_CleanerPickupDeliveryConfigForm.getDisableWebRequest());


            _CleanerPickupDeliveryConfig.setDisallowAnonymousRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getDisallowAnonymousRequest()));
            m_logger.debug("setting DisallowAnonymousRequest=" +_CleanerPickupDeliveryConfigForm.getDisallowAnonymousRequest());


            _CleanerPickupDeliveryConfig.setRequireCustomerRegister(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerRegister()));
            m_logger.debug("setting RequireCustomerRegister=" +_CleanerPickupDeliveryConfigForm.getRequireCustomerRegister());


            _CleanerPickupDeliveryConfig.setRequireCustomerLogin(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryConfigForm.getRequireCustomerLogin()));
            m_logger.debug("setting RequireCustomerLogin=" +_CleanerPickupDeliveryConfigForm.getRequireCustomerLogin());



    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = m_ds.getById(cid);
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) dataObject;

        if (!isMissing(request.getParameter("locationId"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryConfig.getLocationId());
        }
        if (!isMissing(request.getParameter("applyAllLocations"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryConfig.getApplyAllLocations());
        }
        if (!isMissing(request.getParameter("disableWebRequest"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryConfig.getDisableWebRequest());
        }
        if (!isMissing(request.getParameter("disallowAnonymousRequest"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest());
        }
        if (!isMissing(request.getParameter("requireCustomerRegister"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryConfig.getRequireCustomerRegister());
        }
        if (!isMissing(request.getParameter("requireCustomerLogin"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryConfig.getRequireCustomerLogin());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig) {
        if (fieldName == null || fieldName.equals("")|| _CleanerPickupDeliveryConfig == null) return null;
        
        if (fieldName.equals("locationId")) {
            return WebUtil.display(_CleanerPickupDeliveryConfig.getLocationId());
        }
        if (fieldName.equals("applyAllLocations")) {
            return WebUtil.display(_CleanerPickupDeliveryConfig.getApplyAllLocations());
        }
        if (fieldName.equals("disableWebRequest")) {
            return WebUtil.display(_CleanerPickupDeliveryConfig.getDisableWebRequest());
        }
        if (fieldName.equals("disallowAnonymousRequest")) {
            return WebUtil.display(_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest());
        }
        if (fieldName.equals("requireCustomerRegister")) {
            return WebUtil.display(_CleanerPickupDeliveryConfig.getRequireCustomerRegister());
        }
        if (fieldName.equals("requireCustomerLogin")) {
            return WebUtil.display(_CleanerPickupDeliveryConfig.getRequireCustomerLogin());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerPickupDeliveryConfigForm _CleanerPickupDeliveryConfigForm = (CleanerPickupDeliveryConfigForm) form;

		if(requestParams.containsKey("locationId"))
			_CleanerPickupDeliveryConfigForm.setLocationId((String)requestParams.get("locationId"));
		if(requestParams.containsKey("applyAllLocations"))
			_CleanerPickupDeliveryConfigForm.setApplyAllLocations((String)requestParams.get("applyAllLocations"));
		if(requestParams.containsKey("disableWebRequest"))
			_CleanerPickupDeliveryConfigForm.setDisableWebRequest((String)requestParams.get("disableWebRequest"));
		if(requestParams.containsKey("disallowAnonymousRequest"))
			_CleanerPickupDeliveryConfigForm.setDisallowAnonymousRequest((String)requestParams.get("disallowAnonymousRequest"));
		if(requestParams.containsKey("requireCustomerRegister"))
			_CleanerPickupDeliveryConfigForm.setRequireCustomerRegister((String)requestParams.get("requireCustomerRegister"));
		if(requestParams.containsKey("requireCustomerLogin"))
			_CleanerPickupDeliveryConfigForm.setRequireCustomerLogin((String)requestParams.get("requireCustomerLogin"));
    }


    protected boolean loginRequired() {
        return true;
    }

    // SynchRequired is controlled by config file conf/synk.properties
    // take a look at add the actiongroup or action name to the list
    public boolean isSynchRequired(){

        return synkRequired;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_config_home=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_config_list=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_config_form=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_config_ajax=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_config_home=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_config_list=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_config_form=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_config_ajax=NULL,/jsp/form_cleaner/cleanerPickupDeliveryConfig_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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

    public String getActionGroupName(){ return "Cleaner";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
//    protected CleanerPickupDeliveryConfigDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
