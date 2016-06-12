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

import com.autosite.db.SiteObject;
import com.autosite.ds.SiteDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.SiteObjectForm;
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




public class SiteObjectAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(SiteObjectAction.class);

    public SiteObjectAction(){
        m_ds = SiteDS.getInstance();
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

        SiteObjectForm _SiteObjectForm = (SiteObjectForm) form;
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
        Site _Site = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _Site = m_ds.getById(cid);
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
            //Site _Site = m_ds.getById(cid);

            if (_Site == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }



            try {
                checkDepedenceIntegrity(_Site);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _SiteObjectForm == null) {
    	            editField(request, response, _Site);
				} else {
    	            edit(request, response, _SiteObjectForm, _Site);
				}
                if (returnObjects != null) returnObjects.put("target", _Site);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                Site o = m_ds.getById( _Site.getId());

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
            //Site _Site = m_ds.getById(cid);

            if (_Site == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try{
                editField(request, response, _Site);
                if (returnObjects != null) returnObjects.put("target", _Site);
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
            //Site _Site = m_ds.getById(cid);

            if (_Site == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }




            try {
                m_actionExtent.beforeDelete(request, response, _Site);
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

            m_ds.delete(_Site); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _Site);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _Site);
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


            m_logger.info("Creating new Site" );
            Site _SiteNew = new Site();   

            // Setting IDs for the object
			
            if ( _SiteObjectForm == null) {
                setFields(request, response, _SiteNew);
            } else {

            _SiteNew.setSiteUrl(WebParamUtil.getStringValue(_SiteObjectForm.getSiteUrl()));
            m_logger.debug("setting SiteUrl=" +_SiteObjectForm.getSiteUrl());


            _SiteNew.setAccountId(WebParamUtil.getLongValue(_SiteObjectForm.getAccountId()));
            m_logger.debug("setting AccountId=" +_SiteObjectForm.getAccountId());


            _SiteNew.setCreatedTime(WebParamUtil.getTimestampValue(_SiteObjectForm.getCreatedTime()));
            m_logger.debug("setting CreatedTime=" +_SiteObjectForm.getCreatedTime());

        	_SiteNew.setCreatedTime(new TimeNow());

            _SiteNew.setSiteGroup(WebParamUtil.getStringValue(_SiteObjectForm.getSiteGroup()));
            m_logger.debug("setting SiteGroup=" +_SiteObjectForm.getSiteGroup());


            _SiteNew.setRegistered(WebParamUtil.getIntegerValue(_SiteObjectForm.getRegistered()));
            m_logger.debug("setting Registered=" +_SiteObjectForm.getRegistered());


            _SiteNew.setOnSale(WebParamUtil.getIntegerValue(_SiteObjectForm.getOnSale()));
            m_logger.debug("setting OnSale=" +_SiteObjectForm.getOnSale());


            _SiteNew.setSuperAdminEnable(WebParamUtil.getIntegerValue(_SiteObjectForm.getSuperAdminEnable()));
            m_logger.debug("setting SuperAdminEnable=" +_SiteObjectForm.getSuperAdminEnable());


            _SiteNew.setSiteRegisterEnable(WebParamUtil.getIntegerValue(_SiteObjectForm.getSiteRegisterEnable()));
            m_logger.debug("setting SiteRegisterEnable=" +_SiteObjectForm.getSiteRegisterEnable());


            _SiteNew.setSubdomainEnable(WebParamUtil.getIntegerValue(_SiteObjectForm.getSubdomainEnable()));
            m_logger.debug("setting SubdomainEnable=" +_SiteObjectForm.getSubdomainEnable());


            _SiteNew.setSiteRegisterSite(WebParamUtil.getStringValue(_SiteObjectForm.getSiteRegisterSite()));
            m_logger.debug("setting SiteRegisterSite=" +_SiteObjectForm.getSiteRegisterSite());


            _SiteNew.setBaseSiteId(WebParamUtil.getLongValue(_SiteObjectForm.getBaseSiteId()));
            m_logger.debug("setting BaseSiteId=" +_SiteObjectForm.getBaseSiteId());


            _SiteNew.setSubsite(WebParamUtil.getIntegerValue(_SiteObjectForm.getSubsite()));
            m_logger.debug("setting Subsite=" +_SiteObjectForm.getSubsite());


            _SiteNew.setDisabled(WebParamUtil.getIntegerValue(_SiteObjectForm.getDisabled()));
            m_logger.debug("setting Disabled=" +_SiteObjectForm.getDisabled());


			}

            try {
                checkDepedenceIntegrity(_SiteNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _SiteNew);
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
            
            if (_SiteNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_SiteNew);
            if (returnObjects != null) returnObjects.put("target", _SiteNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _SiteNew);
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
             _Site =  _SiteNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }





        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteObjectForm _SiteObjectForm, Site _Site) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Site _Site = m_ds.getById(cid);

        m_logger.debug("Before update " + SiteDS.objectToString(_Site));

        _Site.setSiteUrl(WebParamUtil.getStringValue(_SiteObjectForm.getSiteUrl()));


        _Site.setAccountId(WebParamUtil.getLongValue(_SiteObjectForm.getAccountId()));


        _Site.setCreatedTime(WebParamUtil.getTimestampValue(_SiteObjectForm.getCreatedTime()));


        _Site.setSiteGroup(WebParamUtil.getStringValue(_SiteObjectForm.getSiteGroup()));


        _Site.setRegistered(WebParamUtil.getIntegerValue(_SiteObjectForm.getRegistered()));


        _Site.setOnSale(WebParamUtil.getIntegerValue(_SiteObjectForm.getOnSale()));


        _Site.setSuperAdminEnable(WebParamUtil.getIntegerValue(_SiteObjectForm.getSuperAdminEnable()));


        _Site.setSiteRegisterEnable(WebParamUtil.getIntegerValue(_SiteObjectForm.getSiteRegisterEnable()));


        _Site.setSubdomainEnable(WebParamUtil.getIntegerValue(_SiteObjectForm.getSubdomainEnable()));


        _Site.setSiteRegisterSite(WebParamUtil.getStringValue(_SiteObjectForm.getSiteRegisterSite()));


        _Site.setBaseSiteId(WebParamUtil.getLongValue(_SiteObjectForm.getBaseSiteId()));


        _Site.setSubsite(WebParamUtil.getIntegerValue(_SiteObjectForm.getSubsite()));


        _Site.setDisabled(WebParamUtil.getIntegerValue(_SiteObjectForm.getDisabled()));



        m_actionExtent.beforeUpdate(request, response, _Site);
        m_ds.update(_Site);
        m_actionExtent.afterUpdate(request, response, _Site);
        m_logger.debug("After update " + SiteDS.objectToString(_Site));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, Site _Site) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Site _Site = m_ds.getById(cid);

        if (!isMissing(request.getParameter("siteUrl"))) {
            m_logger.debug("updating param siteUrl from " +_Site.getSiteUrl() + "->" + request.getParameter("siteUrl"));
            _Site.setSiteUrl(WebParamUtil.getStringValue(request.getParameter("siteUrl")));

        }
        if (!isMissing(request.getParameter("accountId"))) {
            m_logger.debug("updating param accountId from " +_Site.getAccountId() + "->" + request.getParameter("accountId"));
            _Site.setAccountId(WebParamUtil.getLongValue(request.getParameter("accountId")));

        }
        if (!isMissing(request.getParameter("createdTime"))) {
            m_logger.debug("updating param createdTime from " +_Site.getCreatedTime() + "->" + request.getParameter("createdTime"));
            _Site.setCreatedTime(WebParamUtil.getTimestampValue(request.getParameter("createdTime")));

        }
        if (!isMissing(request.getParameter("siteGroup"))) {
            m_logger.debug("updating param siteGroup from " +_Site.getSiteGroup() + "->" + request.getParameter("siteGroup"));
            _Site.setSiteGroup(WebParamUtil.getStringValue(request.getParameter("siteGroup")));

        }
        if (!isMissing(request.getParameter("registered"))) {
            m_logger.debug("updating param registered from " +_Site.getRegistered() + "->" + request.getParameter("registered"));
            _Site.setRegistered(WebParamUtil.getIntegerValue(request.getParameter("registered")));

        }
        if (!isMissing(request.getParameter("onSale"))) {
            m_logger.debug("updating param onSale from " +_Site.getOnSale() + "->" + request.getParameter("onSale"));
            _Site.setOnSale(WebParamUtil.getIntegerValue(request.getParameter("onSale")));

        }
        if (!isMissing(request.getParameter("superAdminEnable"))) {
            m_logger.debug("updating param superAdminEnable from " +_Site.getSuperAdminEnable() + "->" + request.getParameter("superAdminEnable"));
            _Site.setSuperAdminEnable(WebParamUtil.getIntegerValue(request.getParameter("superAdminEnable")));

        }
        if (!isMissing(request.getParameter("siteRegisterEnable"))) {
            m_logger.debug("updating param siteRegisterEnable from " +_Site.getSiteRegisterEnable() + "->" + request.getParameter("siteRegisterEnable"));
            _Site.setSiteRegisterEnable(WebParamUtil.getIntegerValue(request.getParameter("siteRegisterEnable")));

        }
        if (!isMissing(request.getParameter("subdomainEnable"))) {
            m_logger.debug("updating param subdomainEnable from " +_Site.getSubdomainEnable() + "->" + request.getParameter("subdomainEnable"));
            _Site.setSubdomainEnable(WebParamUtil.getIntegerValue(request.getParameter("subdomainEnable")));

        }
        if (!isMissing(request.getParameter("siteRegisterSite"))) {
            m_logger.debug("updating param siteRegisterSite from " +_Site.getSiteRegisterSite() + "->" + request.getParameter("siteRegisterSite"));
            _Site.setSiteRegisterSite(WebParamUtil.getStringValue(request.getParameter("siteRegisterSite")));

        }
        if (!isMissing(request.getParameter("baseSiteId"))) {
            m_logger.debug("updating param baseSiteId from " +_Site.getBaseSiteId() + "->" + request.getParameter("baseSiteId"));
            _Site.setBaseSiteId(WebParamUtil.getLongValue(request.getParameter("baseSiteId")));

        }
        if (!isMissing(request.getParameter("subsite"))) {
            m_logger.debug("updating param subsite from " +_Site.getSubsite() + "->" + request.getParameter("subsite"));
            _Site.setSubsite(WebParamUtil.getIntegerValue(request.getParameter("subsite")));

        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_Site.getDisabled() + "->" + request.getParameter("disabled"));
            _Site.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }

        m_actionExtent.beforeUpdate(request, response, _Site);
        m_ds.update(_Site);
        m_actionExtent.afterUpdate(request, response, _Site);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, Site _Site) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Site _Site = m_ds.getById(cid);

        if (!isMissing(request.getParameter("siteUrl"))) {
            m_logger.debug("updating param siteUrl from " +_Site.getSiteUrl() + "->" + request.getParameter("siteUrl"));
            _Site.setSiteUrl(WebParamUtil.getStringValue(request.getParameter("siteUrl")));

        }
        if (!isMissing(request.getParameter("accountId"))) {
            m_logger.debug("updating param accountId from " +_Site.getAccountId() + "->" + request.getParameter("accountId"));
            _Site.setAccountId(WebParamUtil.getLongValue(request.getParameter("accountId")));

        }
        if (!isMissing(request.getParameter("createdTime"))) {
            m_logger.debug("updating param createdTime from " +_Site.getCreatedTime() + "->" + request.getParameter("createdTime"));
            _Site.setCreatedTime(WebParamUtil.getTimestampValue(request.getParameter("createdTime")));

        }
        if (!isMissing(request.getParameter("siteGroup"))) {
            m_logger.debug("updating param siteGroup from " +_Site.getSiteGroup() + "->" + request.getParameter("siteGroup"));
            _Site.setSiteGroup(WebParamUtil.getStringValue(request.getParameter("siteGroup")));

        }
        if (!isMissing(request.getParameter("registered"))) {
            m_logger.debug("updating param registered from " +_Site.getRegistered() + "->" + request.getParameter("registered"));
            _Site.setRegistered(WebParamUtil.getIntegerValue(request.getParameter("registered")));

        }
        if (!isMissing(request.getParameter("onSale"))) {
            m_logger.debug("updating param onSale from " +_Site.getOnSale() + "->" + request.getParameter("onSale"));
            _Site.setOnSale(WebParamUtil.getIntegerValue(request.getParameter("onSale")));

        }
        if (!isMissing(request.getParameter("superAdminEnable"))) {
            m_logger.debug("updating param superAdminEnable from " +_Site.getSuperAdminEnable() + "->" + request.getParameter("superAdminEnable"));
            _Site.setSuperAdminEnable(WebParamUtil.getIntegerValue(request.getParameter("superAdminEnable")));

        }
        if (!isMissing(request.getParameter("siteRegisterEnable"))) {
            m_logger.debug("updating param siteRegisterEnable from " +_Site.getSiteRegisterEnable() + "->" + request.getParameter("siteRegisterEnable"));
            _Site.setSiteRegisterEnable(WebParamUtil.getIntegerValue(request.getParameter("siteRegisterEnable")));

        }
        if (!isMissing(request.getParameter("subdomainEnable"))) {
            m_logger.debug("updating param subdomainEnable from " +_Site.getSubdomainEnable() + "->" + request.getParameter("subdomainEnable"));
            _Site.setSubdomainEnable(WebParamUtil.getIntegerValue(request.getParameter("subdomainEnable")));

        }
        if (!isMissing(request.getParameter("siteRegisterSite"))) {
            m_logger.debug("updating param siteRegisterSite from " +_Site.getSiteRegisterSite() + "->" + request.getParameter("siteRegisterSite"));
            _Site.setSiteRegisterSite(WebParamUtil.getStringValue(request.getParameter("siteRegisterSite")));

        }
        if (!isMissing(request.getParameter("baseSiteId"))) {
            m_logger.debug("updating param baseSiteId from " +_Site.getBaseSiteId() + "->" + request.getParameter("baseSiteId"));
            _Site.setBaseSiteId(WebParamUtil.getLongValue(request.getParameter("baseSiteId")));

        }
        if (!isMissing(request.getParameter("subsite"))) {
            m_logger.debug("updating param subsite from " +_Site.getSubsite() + "->" + request.getParameter("subsite"));
            _Site.setSubsite(WebParamUtil.getIntegerValue(request.getParameter("subsite")));

        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_Site.getDisabled() + "->" + request.getParameter("disabled"));
            _Site.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, Site _Site) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Site _Site = m_ds.getById(cid);

        if (!isMissing(request.getParameter("siteUrl"))) {
			return  JtStringUtil.valueOf(_Site.getSiteUrl());
        }
        if (!isMissing(request.getParameter("accountId"))) {
			return  JtStringUtil.valueOf(_Site.getAccountId());
        }
        if (!isMissing(request.getParameter("createdTime"))) {
			return  JtStringUtil.valueOf(_Site.getCreatedTime());
        }
        if (!isMissing(request.getParameter("siteGroup"))) {
			return  JtStringUtil.valueOf(_Site.getSiteGroup());
        }
        if (!isMissing(request.getParameter("registered"))) {
			return  JtStringUtil.valueOf(_Site.getRegistered());
        }
        if (!isMissing(request.getParameter("onSale"))) {
			return  JtStringUtil.valueOf(_Site.getOnSale());
        }
        if (!isMissing(request.getParameter("superAdminEnable"))) {
			return  JtStringUtil.valueOf(_Site.getSuperAdminEnable());
        }
        if (!isMissing(request.getParameter("siteRegisterEnable"))) {
			return  JtStringUtil.valueOf(_Site.getSiteRegisterEnable());
        }
        if (!isMissing(request.getParameter("subdomainEnable"))) {
			return  JtStringUtil.valueOf(_Site.getSubdomainEnable());
        }
        if (!isMissing(request.getParameter("siteRegisterSite"))) {
			return  JtStringUtil.valueOf(_Site.getSiteRegisterSite());
        }
        if (!isMissing(request.getParameter("baseSiteId"))) {
			return  JtStringUtil.valueOf(_Site.getBaseSiteId());
        }
        if (!isMissing(request.getParameter("subsite"))) {
			return  JtStringUtil.valueOf(_Site.getSubsite());
        }
        if (!isMissing(request.getParameter("disabled"))) {
			return  JtStringUtil.valueOf(_Site.getDisabled());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(Site _Site) throws Exception {
    }

    protected String getFieldByName(String fieldName, Site _Site) {
        if (fieldName == null || fieldName.equals("")|| _Site == null) return null;
        
        if (fieldName.equals("siteUrl")) {
            return WebUtil.display(_Site.getSiteUrl());
        }
        if (fieldName.equals("accountId")) {
            return WebUtil.display(_Site.getAccountId());
        }
        if (fieldName.equals("createdTime")) {
            return WebUtil.display(_Site.getCreatedTime());
        }
        if (fieldName.equals("siteGroup")) {
            return WebUtil.display(_Site.getSiteGroup());
        }
        if (fieldName.equals("registered")) {
            return WebUtil.display(_Site.getRegistered());
        }
        if (fieldName.equals("onSale")) {
            return WebUtil.display(_Site.getOnSale());
        }
        if (fieldName.equals("superAdminEnable")) {
            return WebUtil.display(_Site.getSuperAdminEnable());
        }
        if (fieldName.equals("siteRegisterEnable")) {
            return WebUtil.display(_Site.getSiteRegisterEnable());
        }
        if (fieldName.equals("subdomainEnable")) {
            return WebUtil.display(_Site.getSubdomainEnable());
        }
        if (fieldName.equals("siteRegisterSite")) {
            return WebUtil.display(_Site.getSiteRegisterSite());
        }
        if (fieldName.equals("baseSiteId")) {
            return WebUtil.display(_Site.getBaseSiteId());
        }
        if (fieldName.equals("subsite")) {
            return WebUtil.display(_Site.getSubsite());
        }
        if (fieldName.equals("disabled")) {
            return WebUtil.display(_Site.getDisabled());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        SiteObjectForm _SiteObjectForm = (SiteObjectForm) form;

		if(requestParams.containsKey("siteUrl"))
			_SiteObjectForm.setSiteUrl((String)requestParams.get("siteUrl"));
		if(requestParams.containsKey("accountId"))
			_SiteObjectForm.setAccountId((String)requestParams.get("accountId"));
		if(requestParams.containsKey("createdTime"))
			_SiteObjectForm.setCreatedTime((String)requestParams.get("createdTime"));
		if(requestParams.containsKey("siteGroup"))
			_SiteObjectForm.setSiteGroup((String)requestParams.get("siteGroup"));
		if(requestParams.containsKey("registered"))
			_SiteObjectForm.setRegistered((String)requestParams.get("registered"));
		if(requestParams.containsKey("onSale"))
			_SiteObjectForm.setOnSale((String)requestParams.get("onSale"));
		if(requestParams.containsKey("superAdminEnable"))
			_SiteObjectForm.setSuperAdminEnable((String)requestParams.get("superAdminEnable"));
		if(requestParams.containsKey("siteRegisterEnable"))
			_SiteObjectForm.setSiteRegisterEnable((String)requestParams.get("siteRegisterEnable"));
		if(requestParams.containsKey("subdomainEnable"))
			_SiteObjectForm.setSubdomainEnable((String)requestParams.get("subdomainEnable"));
		if(requestParams.containsKey("siteRegisterSite"))
			_SiteObjectForm.setSiteRegisterSite((String)requestParams.get("siteRegisterSite"));
		if(requestParams.containsKey("baseSiteId"))
			_SiteObjectForm.setBaseSiteId((String)requestParams.get("baseSiteId"));
		if(requestParams.containsKey("subsite"))
			_SiteObjectForm.setSubsite((String)requestParams.get("subsite"));
		if(requestParams.containsKey("disabled"))
			_SiteObjectForm.setDisabled((String)requestParams.get("disabled"));
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
        m_viewManager.registerView(getActionName(), "site_object_home=NULL,/jsp/form/siteObject_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "site_object_list=NULL,/jsp/form/siteObject_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "site_object_form=NULL,/jsp/form/siteObject_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "site_object_ajax=NULL,/jsp/form/siteObject_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "site_object_home=NULL,/jsp/form/siteObject_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "site_object_list=NULL,/jsp/form/siteObject_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "site_object_form=NULL,/jsp/form/siteObject_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "site_object_ajax=NULL,/jsp/form/siteObject_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected SiteDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
