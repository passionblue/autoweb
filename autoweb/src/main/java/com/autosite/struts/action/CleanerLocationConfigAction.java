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

import com.autosite.db.CleanerLocationConfig;
import com.autosite.ds.CleanerLocationConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerLocationConfigForm;
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




public class CleanerLocationConfigAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerLocationConfigAction.class);

    public CleanerLocationConfigAction(){
        m_ds = CleanerLocationConfigDS.getInstance();
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

        CleanerLocationConfigForm _CleanerLocationConfigForm = (CleanerLocationConfigForm) form;
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
        CleanerLocationConfig _CleanerLocationConfig = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerLocationConfig = m_ds.getById(cid);
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
            //CleanerLocationConfig _CleanerLocationConfig = m_ds.getById(cid);

            if (_CleanerLocationConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerLocationConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerLocationConfig.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerLocationConfig);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerLocationConfigForm == null) {
    	            editField(request, response, _CleanerLocationConfig);
				} else {
    	            edit(request, response, _CleanerLocationConfigForm, _CleanerLocationConfig);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerLocationConfig);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerLocationConfig o = m_ds.getById( _CleanerLocationConfig.getId(), true);

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
            //CleanerLocationConfig _CleanerLocationConfig = m_ds.getById(cid);

            if (_CleanerLocationConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerLocationConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerLocationConfig.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerLocationConfig);
                if (returnObjects != null) returnObjects.put("target", _CleanerLocationConfig);
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
            //CleanerLocationConfig _CleanerLocationConfig = m_ds.getById(cid);

            if (_CleanerLocationConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerLocationConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerLocationConfig.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerLocationConfig);
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

            m_ds.delete(_CleanerLocationConfig); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerLocationConfig);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerLocationConfig);
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


            m_logger.info("Creating new CleanerLocationConfig" );
            CleanerLocationConfig _CleanerLocationConfigNew = new CleanerLocationConfig();   

            // Setting IDs for the object
            _CleanerLocationConfigNew.setSiteId(site.getId());
			
            if ( _CleanerLocationConfigForm == null) {
                setFields(request, response, _CleanerLocationConfigNew);
            } else {

            _CleanerLocationConfigNew.setLocationId(WebParamUtil.getLongValue(_CleanerLocationConfigForm.getLocationId()));
            m_logger.debug("setting LocationId=" +_CleanerLocationConfigForm.getLocationId());


            _CleanerLocationConfigNew.setOpenHourWeekday(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getOpenHourWeekday()));
            m_logger.debug("setting OpenHourWeekday=" +_CleanerLocationConfigForm.getOpenHourWeekday());


            _CleanerLocationConfigNew.setCloseHourWeekday(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getCloseHourWeekday()));
            m_logger.debug("setting CloseHourWeekday=" +_CleanerLocationConfigForm.getCloseHourWeekday());


            _CleanerLocationConfigNew.setOpenHourSat(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getOpenHourSat()));
            m_logger.debug("setting OpenHourSat=" +_CleanerLocationConfigForm.getOpenHourSat());


            _CleanerLocationConfigNew.setCloseHourSat(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getCloseHourSat()));
            m_logger.debug("setting CloseHourSat=" +_CleanerLocationConfigForm.getCloseHourSat());


            _CleanerLocationConfigNew.setOpenHourSun(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getOpenHourSun()));
            m_logger.debug("setting OpenHourSun=" +_CleanerLocationConfigForm.getOpenHourSun());


            _CleanerLocationConfigNew.setCloseHourSun(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getCloseHourSun()));
            m_logger.debug("setting CloseHourSun=" +_CleanerLocationConfigForm.getCloseHourSun());


            _CleanerLocationConfigNew.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerLocationConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerLocationConfigForm.getTimeCreated());

        	_CleanerLocationConfigNew.setTimeCreated(new TimeNow());

            _CleanerLocationConfigNew.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerLocationConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_CleanerLocationConfigForm.getTimeUpdated());

        	_CleanerLocationConfigNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_CleanerLocationConfigNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerLocationConfigNew);
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
            
            if (_CleanerLocationConfigNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerLocationConfigNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerLocationConfigNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerLocationConfigNew);
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
             _CleanerLocationConfig =  _CleanerLocationConfigNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerLocationConfig, "cleaner-location-config" );
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerLocationConfigForm _CleanerLocationConfigForm, CleanerLocationConfig _CleanerLocationConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocationConfig _CleanerLocationConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerLocationConfigDS.objectToString(_CleanerLocationConfig));

        _CleanerLocationConfig.setLocationId(WebParamUtil.getLongValue(_CleanerLocationConfigForm.getLocationId()));


        _CleanerLocationConfig.setOpenHourWeekday(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getOpenHourWeekday()));


        _CleanerLocationConfig.setCloseHourWeekday(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getCloseHourWeekday()));


        _CleanerLocationConfig.setOpenHourSat(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getOpenHourSat()));


        _CleanerLocationConfig.setCloseHourSat(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getCloseHourSat()));


        _CleanerLocationConfig.setOpenHourSun(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getOpenHourSun()));


        _CleanerLocationConfig.setCloseHourSun(WebParamUtil.getStringValue(_CleanerLocationConfigForm.getCloseHourSun()));




        _CleanerLocationConfig.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerLocationConfigForm.getTimeUpdated()));

        _CleanerLocationConfig.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _CleanerLocationConfig);
        m_ds.update(_CleanerLocationConfig);
        m_actionExtent.afterUpdate(request, response, _CleanerLocationConfig);
        m_logger.debug("After update " + CleanerLocationConfigDS.objectToString(_CleanerLocationConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerLocationConfig _CleanerLocationConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocationConfig _CleanerLocationConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerLocationConfig.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerLocationConfig.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("openHourWeekday"))) {
            m_logger.debug("updating param openHourWeekday from " +_CleanerLocationConfig.getOpenHourWeekday() + "->" + request.getParameter("openHourWeekday"));
            _CleanerLocationConfig.setOpenHourWeekday(WebParamUtil.getStringValue(request.getParameter("openHourWeekday")));

        }
        if (!isMissing(request.getParameter("closeHourWeekday"))) {
            m_logger.debug("updating param closeHourWeekday from " +_CleanerLocationConfig.getCloseHourWeekday() + "->" + request.getParameter("closeHourWeekday"));
            _CleanerLocationConfig.setCloseHourWeekday(WebParamUtil.getStringValue(request.getParameter("closeHourWeekday")));

        }
        if (!isMissing(request.getParameter("openHourSat"))) {
            m_logger.debug("updating param openHourSat from " +_CleanerLocationConfig.getOpenHourSat() + "->" + request.getParameter("openHourSat"));
            _CleanerLocationConfig.setOpenHourSat(WebParamUtil.getStringValue(request.getParameter("openHourSat")));

        }
        if (!isMissing(request.getParameter("closeHourSat"))) {
            m_logger.debug("updating param closeHourSat from " +_CleanerLocationConfig.getCloseHourSat() + "->" + request.getParameter("closeHourSat"));
            _CleanerLocationConfig.setCloseHourSat(WebParamUtil.getStringValue(request.getParameter("closeHourSat")));

        }
        if (!isMissing(request.getParameter("openHourSun"))) {
            m_logger.debug("updating param openHourSun from " +_CleanerLocationConfig.getOpenHourSun() + "->" + request.getParameter("openHourSun"));
            _CleanerLocationConfig.setOpenHourSun(WebParamUtil.getStringValue(request.getParameter("openHourSun")));

        }
        if (!isMissing(request.getParameter("closeHourSun"))) {
            m_logger.debug("updating param closeHourSun from " +_CleanerLocationConfig.getCloseHourSun() + "->" + request.getParameter("closeHourSun"));
            _CleanerLocationConfig.setCloseHourSun(WebParamUtil.getStringValue(request.getParameter("closeHourSun")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerLocationConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerLocationConfig.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerLocationConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerLocationConfig.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerLocationConfig.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _CleanerLocationConfig);
        m_ds.update(_CleanerLocationConfig);
        m_actionExtent.afterUpdate(request, response, _CleanerLocationConfig);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerLocationConfig _CleanerLocationConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocationConfig _CleanerLocationConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerLocationConfig.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerLocationConfig.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("openHourWeekday"))) {
            m_logger.debug("updating param openHourWeekday from " +_CleanerLocationConfig.getOpenHourWeekday() + "->" + request.getParameter("openHourWeekday"));
            _CleanerLocationConfig.setOpenHourWeekday(WebParamUtil.getStringValue(request.getParameter("openHourWeekday")));

        }
        if (!isMissing(request.getParameter("closeHourWeekday"))) {
            m_logger.debug("updating param closeHourWeekday from " +_CleanerLocationConfig.getCloseHourWeekday() + "->" + request.getParameter("closeHourWeekday"));
            _CleanerLocationConfig.setCloseHourWeekday(WebParamUtil.getStringValue(request.getParameter("closeHourWeekday")));

        }
        if (!isMissing(request.getParameter("openHourSat"))) {
            m_logger.debug("updating param openHourSat from " +_CleanerLocationConfig.getOpenHourSat() + "->" + request.getParameter("openHourSat"));
            _CleanerLocationConfig.setOpenHourSat(WebParamUtil.getStringValue(request.getParameter("openHourSat")));

        }
        if (!isMissing(request.getParameter("closeHourSat"))) {
            m_logger.debug("updating param closeHourSat from " +_CleanerLocationConfig.getCloseHourSat() + "->" + request.getParameter("closeHourSat"));
            _CleanerLocationConfig.setCloseHourSat(WebParamUtil.getStringValue(request.getParameter("closeHourSat")));

        }
        if (!isMissing(request.getParameter("openHourSun"))) {
            m_logger.debug("updating param openHourSun from " +_CleanerLocationConfig.getOpenHourSun() + "->" + request.getParameter("openHourSun"));
            _CleanerLocationConfig.setOpenHourSun(WebParamUtil.getStringValue(request.getParameter("openHourSun")));

        }
        if (!isMissing(request.getParameter("closeHourSun"))) {
            m_logger.debug("updating param closeHourSun from " +_CleanerLocationConfig.getCloseHourSun() + "->" + request.getParameter("closeHourSun"));
            _CleanerLocationConfig.setCloseHourSun(WebParamUtil.getStringValue(request.getParameter("closeHourSun")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerLocationConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerLocationConfig.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerLocationConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerLocationConfig.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerLocationConfig.setTimeUpdated(new TimeNow());
        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerLocationConfig _CleanerLocationConfig) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocationConfig _CleanerLocationConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("locationId"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getLocationId());
        }
        if (!isMissing(request.getParameter("openHourWeekday"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getOpenHourWeekday());
        }
        if (!isMissing(request.getParameter("closeHourWeekday"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getCloseHourWeekday());
        }
        if (!isMissing(request.getParameter("openHourSat"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getOpenHourSat());
        }
        if (!isMissing(request.getParameter("closeHourSat"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getCloseHourSat());
        }
        if (!isMissing(request.getParameter("openHourSun"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getOpenHourSun());
        }
        if (!isMissing(request.getParameter("closeHourSun"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getCloseHourSun());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_CleanerLocationConfig.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerLocationConfig _CleanerLocationConfig) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerLocationConfig _CleanerLocationConfig) {
        if (fieldName == null || fieldName.equals("")|| _CleanerLocationConfig == null) return null;
        
        if (fieldName.equals("locationId")) {
            return WebUtil.display(_CleanerLocationConfig.getLocationId());
        }
        if (fieldName.equals("openHourWeekday")) {
            return WebUtil.display(_CleanerLocationConfig.getOpenHourWeekday());
        }
        if (fieldName.equals("closeHourWeekday")) {
            return WebUtil.display(_CleanerLocationConfig.getCloseHourWeekday());
        }
        if (fieldName.equals("openHourSat")) {
            return WebUtil.display(_CleanerLocationConfig.getOpenHourSat());
        }
        if (fieldName.equals("closeHourSat")) {
            return WebUtil.display(_CleanerLocationConfig.getCloseHourSat());
        }
        if (fieldName.equals("openHourSun")) {
            return WebUtil.display(_CleanerLocationConfig.getOpenHourSun());
        }
        if (fieldName.equals("closeHourSun")) {
            return WebUtil.display(_CleanerLocationConfig.getCloseHourSun());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_CleanerLocationConfig.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_CleanerLocationConfig.getTimeUpdated());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerLocationConfigForm _CleanerLocationConfigForm = (CleanerLocationConfigForm) form;

		if(requestParams.containsKey("locationId"))
			_CleanerLocationConfigForm.setLocationId((String)requestParams.get("locationId"));
		if(requestParams.containsKey("openHourWeekday"))
			_CleanerLocationConfigForm.setOpenHourWeekday((String)requestParams.get("openHourWeekday"));
		if(requestParams.containsKey("closeHourWeekday"))
			_CleanerLocationConfigForm.setCloseHourWeekday((String)requestParams.get("closeHourWeekday"));
		if(requestParams.containsKey("openHourSat"))
			_CleanerLocationConfigForm.setOpenHourSat((String)requestParams.get("openHourSat"));
		if(requestParams.containsKey("closeHourSat"))
			_CleanerLocationConfigForm.setCloseHourSat((String)requestParams.get("closeHourSat"));
		if(requestParams.containsKey("openHourSun"))
			_CleanerLocationConfigForm.setOpenHourSun((String)requestParams.get("openHourSun"));
		if(requestParams.containsKey("closeHourSun"))
			_CleanerLocationConfigForm.setCloseHourSun((String)requestParams.get("closeHourSun"));
		if(requestParams.containsKey("timeCreated"))
			_CleanerLocationConfigForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_CleanerLocationConfigForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
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
        m_viewManager.registerView(getActionName(), "cleaner_location_config_home=NULL,/jsp/form_cleaner/cleanerLocationConfig_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_location_config_list=NULL,/jsp/form_cleaner/cleanerLocationConfig_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_location_config_form=NULL,/jsp/form_cleaner/cleanerLocationConfig_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_location_config_ajax=NULL,/jsp/form_cleaner/cleanerLocationConfig_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_config_home=NULL,/jsp/form_cleaner/cleanerLocationConfig_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_config_list=NULL,/jsp/form_cleaner/cleanerLocationConfig_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_config_form=NULL,/jsp/form_cleaner/cleanerLocationConfig_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_config_ajax=NULL,/jsp/form_cleaner/cleanerLocationConfig_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerLocationConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
