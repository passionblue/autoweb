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

import com.autosite.ds.CleanerRegisterStartDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerRegisterStartForm;
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


import com.autosite.holder.CleanerRegisterStartDataHolder;


public class CleanerRegisterStartAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerRegisterStartAction.class);

    public CleanerRegisterStartAction(){
        m_ds = CleanerRegisterStartDS.getInstance();
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

        CleanerRegisterStartForm _CleanerRegisterStartForm = (CleanerRegisterStartForm) form;
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
        CleanerRegisterStartDataHolder _CleanerRegisterStart = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerRegisterStart = m_ds.getById(cid);
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
            //CleanerRegisterStart _CleanerRegisterStart = m_ds.getById(cid);

            if (_CleanerRegisterStart == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerRegisterStart.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerRegisterStart.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "CleanerRegisterGroup", "CleanerRegisterStart");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_CleanerRegisterStart);
            } else {
                storable.setDataHolder(_CleanerRegisterStart);
            }
            updateSessionStorable(session, "CleanerRegisterGroup", "CleanerRegisterStart",storable);

            try {
                checkDepedenceIntegrity(_CleanerRegisterStart);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerRegisterStartForm == null) {
    	            editField(request, response, _CleanerRegisterStart);
				} else {
    	            edit(request, response, _CleanerRegisterStartForm, _CleanerRegisterStart);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerRegisterStart);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerRegisterStartDataHolder o = m_ds.getById( _CleanerRegisterStart.getId(), true);

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
            //CleanerRegisterStart _CleanerRegisterStart = m_ds.getById(cid);

            if (_CleanerRegisterStart == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerRegisterStart.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerRegisterStart.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "CleanerRegisterGroup", "CleanerRegisterStart");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_CleanerRegisterStart);
            } else {
                storable.setDataHolder(_CleanerRegisterStart);
            }
            updateSessionStorable(session, "CleanerRegisterGroup", "CleanerRegisterStart",storable);

            try{
                editField(request, response, _CleanerRegisterStart);
                if (returnObjects != null) returnObjects.put("target", _CleanerRegisterStart);
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
            //CleanerRegisterStart _CleanerRegisterStart = m_ds.getById(cid);

            if (_CleanerRegisterStart == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerRegisterStart.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerRegisterStart.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            removeSessionStorable(session, "CleanerRegisterGroup", "CleanerRegisterStart");


            try {
                m_actionExtent.beforeDelete(request, response, _CleanerRegisterStart);
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

            m_ds.delete(_CleanerRegisterStart); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerRegisterStart);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerRegisterStart);
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


            m_logger.info("Creating new CleanerRegisterStart" );
            CleanerRegisterStartDataHolder _CleanerRegisterStartNew = new CleanerRegisterStartDataHolder();   

            // Setting IDs for the object
            _CleanerRegisterStartNew.setSiteId(site.getId());
			
            if ( _CleanerRegisterStartForm == null) {
                setFields(request, response, _CleanerRegisterStartNew);
            } else {

            _CleanerRegisterStartNew.setSiteTitle(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getSiteTitle()));
            m_logger.debug("setting SiteTitle=" +_CleanerRegisterStartForm.getSiteTitle());


            _CleanerRegisterStartNew.setSiteName(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getSiteName()));
            m_logger.debug("setting SiteName=" +_CleanerRegisterStartForm.getSiteName());


            _CleanerRegisterStartNew.setUsername(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getUsername()));
            m_logger.debug("setting Username=" +_CleanerRegisterStartForm.getUsername());


            _CleanerRegisterStartNew.setEmail(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getEmail()));
            m_logger.debug("setting Email=" +_CleanerRegisterStartForm.getEmail());


            _CleanerRegisterStartNew.setPassword(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getPassword()));
            m_logger.debug("setting Password=" +_CleanerRegisterStartForm.getPassword());


            _CleanerRegisterStartNew.setPasswordRepeat(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getPasswordRepeat()));
            m_logger.debug("setting PasswordRepeat=" +_CleanerRegisterStartForm.getPasswordRepeat());


            _CleanerRegisterStartNew.setLocation(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getLocation()));
            m_logger.debug("setting Location=" +_CleanerRegisterStartForm.getLocation());


            _CleanerRegisterStartNew.setCreatedSiteUrl(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getCreatedSiteUrl()));
            m_logger.debug("setting CreatedSiteUrl=" +_CleanerRegisterStartForm.getCreatedSiteUrl());


			}
            //Handle SessionStorable
            SessionStorableDataHolderWrapper newHolderWrapper = new SessionStorableDataHolderWrapper(_CleanerRegisterStartNew);
            addSessionStorable(session, "CleanerRegisterGroup", "CleanerRegisterStart", newHolderWrapper);

            try {
                checkDepedenceIntegrity(_CleanerRegisterStartNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerRegisterStartNew);
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
            
            if (_CleanerRegisterStartNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerRegisterStartNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerRegisterStartNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerRegisterStartNew);
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
             _CleanerRegisterStart =  _CleanerRegisterStartNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }





        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerRegisterStartForm _CleanerRegisterStartForm, CleanerRegisterStartDataHolder _CleanerRegisterStart) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerRegisterStart _CleanerRegisterStart = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerRegisterStartDS.objectToString(_CleanerRegisterStart));

        _CleanerRegisterStart.setSiteTitle(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getSiteTitle()));


        _CleanerRegisterStart.setSiteName(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getSiteName()));


        _CleanerRegisterStart.setUsername(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getUsername()));


        _CleanerRegisterStart.setEmail(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getEmail()));


        _CleanerRegisterStart.setPassword(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getPassword()));


        _CleanerRegisterStart.setPasswordRepeat(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getPasswordRepeat()));


        _CleanerRegisterStart.setLocation(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getLocation()));


        _CleanerRegisterStart.setCreatedSiteUrl(WebParamUtil.getStringValue(_CleanerRegisterStartForm.getCreatedSiteUrl()));



        m_actionExtent.beforeUpdate(request, response, _CleanerRegisterStart);
        m_ds.update(_CleanerRegisterStart);
        m_actionExtent.afterUpdate(request, response, _CleanerRegisterStart);
        m_logger.debug("After update " + CleanerRegisterStartDS.objectToString(_CleanerRegisterStart));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerRegisterStartDataHolder _CleanerRegisterStart) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerRegisterStart _CleanerRegisterStart = m_ds.getById(cid);

        if (!isMissing(request.getParameter("siteTitle"))) {
            m_logger.debug("updating param siteTitle from " +_CleanerRegisterStart.getSiteTitle() + "->" + request.getParameter("siteTitle"));
            _CleanerRegisterStart.setSiteTitle(WebParamUtil.getStringValue(request.getParameter("siteTitle")));

        }
        if (!isMissing(request.getParameter("siteName"))) {
            m_logger.debug("updating param siteName from " +_CleanerRegisterStart.getSiteName() + "->" + request.getParameter("siteName"));
            _CleanerRegisterStart.setSiteName(WebParamUtil.getStringValue(request.getParameter("siteName")));

        }
        if (!isMissing(request.getParameter("username"))) {
            m_logger.debug("updating param username from " +_CleanerRegisterStart.getUsername() + "->" + request.getParameter("username"));
            _CleanerRegisterStart.setUsername(WebParamUtil.getStringValue(request.getParameter("username")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_CleanerRegisterStart.getEmail() + "->" + request.getParameter("email"));
            _CleanerRegisterStart.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("password"))) {
            m_logger.debug("updating param password from " +_CleanerRegisterStart.getPassword() + "->" + request.getParameter("password"));
            _CleanerRegisterStart.setPassword(WebParamUtil.getStringValue(request.getParameter("password")));

        }
        if (!isMissing(request.getParameter("passwordRepeat"))) {
            m_logger.debug("updating param passwordRepeat from " +_CleanerRegisterStart.getPasswordRepeat() + "->" + request.getParameter("passwordRepeat"));
            _CleanerRegisterStart.setPasswordRepeat(WebParamUtil.getStringValue(request.getParameter("passwordRepeat")));

        }
        if (!isMissing(request.getParameter("location"))) {
            m_logger.debug("updating param location from " +_CleanerRegisterStart.getLocation() + "->" + request.getParameter("location"));
            _CleanerRegisterStart.setLocation(WebParamUtil.getStringValue(request.getParameter("location")));

        }
        if (!isMissing(request.getParameter("createdSiteUrl"))) {
            m_logger.debug("updating param createdSiteUrl from " +_CleanerRegisterStart.getCreatedSiteUrl() + "->" + request.getParameter("createdSiteUrl"));
            _CleanerRegisterStart.setCreatedSiteUrl(WebParamUtil.getStringValue(request.getParameter("createdSiteUrl")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerRegisterStart);
        m_ds.update(_CleanerRegisterStart);
        m_actionExtent.afterUpdate(request, response, _CleanerRegisterStart);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerRegisterStartDataHolder _CleanerRegisterStart) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerRegisterStart _CleanerRegisterStart = m_ds.getById(cid);

        if (!isMissing(request.getParameter("siteTitle"))) {
            m_logger.debug("updating param siteTitle from " +_CleanerRegisterStart.getSiteTitle() + "->" + request.getParameter("siteTitle"));
            _CleanerRegisterStart.setSiteTitle(WebParamUtil.getStringValue(request.getParameter("siteTitle")));

        }
        if (!isMissing(request.getParameter("siteName"))) {
            m_logger.debug("updating param siteName from " +_CleanerRegisterStart.getSiteName() + "->" + request.getParameter("siteName"));
            _CleanerRegisterStart.setSiteName(WebParamUtil.getStringValue(request.getParameter("siteName")));

        }
        if (!isMissing(request.getParameter("username"))) {
            m_logger.debug("updating param username from " +_CleanerRegisterStart.getUsername() + "->" + request.getParameter("username"));
            _CleanerRegisterStart.setUsername(WebParamUtil.getStringValue(request.getParameter("username")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_CleanerRegisterStart.getEmail() + "->" + request.getParameter("email"));
            _CleanerRegisterStart.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("password"))) {
            m_logger.debug("updating param password from " +_CleanerRegisterStart.getPassword() + "->" + request.getParameter("password"));
            _CleanerRegisterStart.setPassword(WebParamUtil.getStringValue(request.getParameter("password")));

        }
        if (!isMissing(request.getParameter("passwordRepeat"))) {
            m_logger.debug("updating param passwordRepeat from " +_CleanerRegisterStart.getPasswordRepeat() + "->" + request.getParameter("passwordRepeat"));
            _CleanerRegisterStart.setPasswordRepeat(WebParamUtil.getStringValue(request.getParameter("passwordRepeat")));

        }
        if (!isMissing(request.getParameter("location"))) {
            m_logger.debug("updating param location from " +_CleanerRegisterStart.getLocation() + "->" + request.getParameter("location"));
            _CleanerRegisterStart.setLocation(WebParamUtil.getStringValue(request.getParameter("location")));

        }
        if (!isMissing(request.getParameter("createdSiteUrl"))) {
            m_logger.debug("updating param createdSiteUrl from " +_CleanerRegisterStart.getCreatedSiteUrl() + "->" + request.getParameter("createdSiteUrl"));
            _CleanerRegisterStart.setCreatedSiteUrl(WebParamUtil.getStringValue(request.getParameter("createdSiteUrl")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerRegisterStartDataHolder _CleanerRegisterStart) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerRegisterStart _CleanerRegisterStart = m_ds.getById(cid);

        if (!isMissing(request.getParameter("siteTitle"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getSiteTitle());
        }
        if (!isMissing(request.getParameter("siteName"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getSiteName());
        }
        if (!isMissing(request.getParameter("username"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getUsername());
        }
        if (!isMissing(request.getParameter("email"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getEmail());
        }
        if (!isMissing(request.getParameter("password"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getPassword());
        }
        if (!isMissing(request.getParameter("passwordRepeat"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getPasswordRepeat());
        }
        if (!isMissing(request.getParameter("location"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getLocation());
        }
        if (!isMissing(request.getParameter("createdSiteUrl"))) {
			return  JtStringUtil.valueOf(_CleanerRegisterStart.getCreatedSiteUrl());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerRegisterStartDataHolder _CleanerRegisterStart) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerRegisterStartDataHolder _CleanerRegisterStart) {
        if (fieldName == null || fieldName.equals("")|| _CleanerRegisterStart == null) return null;
        
        if (fieldName.equals("siteTitle")) {
            return WebUtil.display(_CleanerRegisterStart.getSiteTitle());
        }
        if (fieldName.equals("siteName")) {
            return WebUtil.display(_CleanerRegisterStart.getSiteName());
        }
        if (fieldName.equals("username")) {
            return WebUtil.display(_CleanerRegisterStart.getUsername());
        }
        if (fieldName.equals("email")) {
            return WebUtil.display(_CleanerRegisterStart.getEmail());
        }
        if (fieldName.equals("password")) {
            return WebUtil.display(_CleanerRegisterStart.getPassword());
        }
        if (fieldName.equals("passwordRepeat")) {
            return WebUtil.display(_CleanerRegisterStart.getPasswordRepeat());
        }
        if (fieldName.equals("location")) {
            return WebUtil.display(_CleanerRegisterStart.getLocation());
        }
        if (fieldName.equals("createdSiteUrl")) {
            return WebUtil.display(_CleanerRegisterStart.getCreatedSiteUrl());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerRegisterStartForm _CleanerRegisterStartForm = (CleanerRegisterStartForm) form;

		if(requestParams.containsKey("siteTitle"))
			_CleanerRegisterStartForm.setSiteTitle((String)requestParams.get("siteTitle"));
		if(requestParams.containsKey("siteName"))
			_CleanerRegisterStartForm.setSiteName((String)requestParams.get("siteName"));
		if(requestParams.containsKey("username"))
			_CleanerRegisterStartForm.setUsername((String)requestParams.get("username"));
		if(requestParams.containsKey("email"))
			_CleanerRegisterStartForm.setEmail((String)requestParams.get("email"));
		if(requestParams.containsKey("password"))
			_CleanerRegisterStartForm.setPassword((String)requestParams.get("password"));
		if(requestParams.containsKey("passwordRepeat"))
			_CleanerRegisterStartForm.setPasswordRepeat((String)requestParams.get("passwordRepeat"));
		if(requestParams.containsKey("location"))
			_CleanerRegisterStartForm.setLocation((String)requestParams.get("location"));
		if(requestParams.containsKey("createdSiteUrl"))
			_CleanerRegisterStartForm.setCreatedSiteUrl((String)requestParams.get("createdSiteUrl"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return false;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_register_start_home=NULL,/jsp/form_cleaner/cleanerRegisterStart_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_register_start_list=NULL,/jsp/form_cleaner/cleanerRegisterStart_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_register_start_form=NULL,/jsp/form_cleaner/cleanerRegisterStart_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_register_start_ajax=NULL,/jsp/form_cleaner/cleanerRegisterStart_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_register_start_home=NULL,/jsp/form_cleaner/cleanerRegisterStart_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_register_start_list=NULL,/jsp/form_cleaner/cleanerRegisterStart_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_register_start_form=NULL,/jsp/form_cleaner/cleanerRegisterStart_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_register_start_ajax=NULL,/jsp/form_cleaner/cleanerRegisterStart_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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

    protected String getSessionStorableGroup() {
        return "CleanerRegisterGroup";
    }

	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
    protected CleanerRegisterStartDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
