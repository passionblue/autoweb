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

import com.autosite.db.RequestHistory;
import com.autosite.ds.RequestHistoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.RequestHistoryForm;
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




public class RequestHistoryAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(RequestHistoryAction.class);

    public RequestHistoryAction(){
        m_ds = RequestHistoryDS.getInstance();
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

        RequestHistoryForm _RequestHistoryForm = (RequestHistoryForm) form;
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
        RequestHistory _RequestHistory = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _RequestHistory = m_ds.getById(cid);
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
            //RequestHistory _RequestHistory = m_ds.getById(cid);

            if (_RequestHistory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_RequestHistory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _RequestHistory.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_RequestHistory);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _RequestHistoryForm == null) {
    	            editField(request, response, _RequestHistory);
				} else {
    	            edit(request, response, _RequestHistoryForm, _RequestHistory);
				}
                if (returnObjects != null) returnObjects.put("target", _RequestHistory);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                RequestHistory o = m_ds.getById( _RequestHistory.getId(), true);

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
            //RequestHistory _RequestHistory = m_ds.getById(cid);

            if (_RequestHistory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_RequestHistory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _RequestHistory.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _RequestHistory);
                if (returnObjects != null) returnObjects.put("target", _RequestHistory);
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
            //RequestHistory _RequestHistory = m_ds.getById(cid);

            if (_RequestHistory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_RequestHistory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _RequestHistory.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _RequestHistory);
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

            m_ds.delete(_RequestHistory); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _RequestHistory);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _RequestHistory);
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


            m_logger.info("Creating new RequestHistory" );
            RequestHistory _RequestHistoryNew = new RequestHistory();   

            // Setting IDs for the object
            _RequestHistoryNew.setSiteId(site.getId());
			
            if ( _RequestHistoryForm == null) {
                setFields(request, response, _RequestHistoryNew);
            } else {

            _RequestHistoryNew.setForwardSiteId(WebParamUtil.getLongValue(_RequestHistoryForm.getForwardSiteId()));
            m_logger.debug("setting ForwardSiteId=" +_RequestHistoryForm.getForwardSiteId());


            _RequestHistoryNew.setIsDropped(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsDropped()));
            m_logger.debug("setting IsDropped=" +_RequestHistoryForm.getIsDropped());


            _RequestHistoryNew.setIsPageless(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsPageless()));
            m_logger.debug("setting IsPageless=" +_RequestHistoryForm.getIsPageless());


            _RequestHistoryNew.setIsLogin(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsLogin()));
            m_logger.debug("setting IsLogin=" +_RequestHistoryForm.getIsLogin());


            _RequestHistoryNew.setIsAjax(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsAjax()));
            m_logger.debug("setting IsAjax=" +_RequestHistoryForm.getIsAjax());


            _RequestHistoryNew.setIsRobot(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsRobot()));
            m_logger.debug("setting IsRobot=" +_RequestHistoryForm.getIsRobot());


            _RequestHistoryNew.setUserid(WebParamUtil.getStringValue(_RequestHistoryForm.getUserid()));
            m_logger.debug("setting Userid=" +_RequestHistoryForm.getUserid());


            _RequestHistoryNew.setUserAgent(WebParamUtil.getStringValue(_RequestHistoryForm.getUserAgent()));
            m_logger.debug("setting UserAgent=" +_RequestHistoryForm.getUserAgent());


            _RequestHistoryNew.setRefer(WebParamUtil.getStringValue(_RequestHistoryForm.getRefer()));
            m_logger.debug("setting Refer=" +_RequestHistoryForm.getRefer());


            _RequestHistoryNew.setRobot(WebParamUtil.getStringValue(_RequestHistoryForm.getRobot()));
            m_logger.debug("setting Robot=" +_RequestHistoryForm.getRobot());


            _RequestHistoryNew.setRemoteIp(WebParamUtil.getStringValue(_RequestHistoryForm.getRemoteIp()));
            m_logger.debug("setting RemoteIp=" +_RequestHistoryForm.getRemoteIp());


            _RequestHistoryNew.setSiteUrl(WebParamUtil.getStringValue(_RequestHistoryForm.getSiteUrl()));
            m_logger.debug("setting SiteUrl=" +_RequestHistoryForm.getSiteUrl());


            _RequestHistoryNew.setUri(WebParamUtil.getStringValue(_RequestHistoryForm.getUri()));
            m_logger.debug("setting Uri=" +_RequestHistoryForm.getUri());


            _RequestHistoryNew.setQuery(WebParamUtil.getStringValue(_RequestHistoryForm.getQuery()));
            m_logger.debug("setting Query=" +_RequestHistoryForm.getQuery());


            _RequestHistoryNew.setRpci(WebParamUtil.getStringValue(_RequestHistoryForm.getRpci()));
            m_logger.debug("setting Rpci=" +_RequestHistoryForm.getRpci());


            _RequestHistoryNew.setSessionId(WebParamUtil.getStringValue(_RequestHistoryForm.getSessionId()));
            m_logger.debug("setting SessionId=" +_RequestHistoryForm.getSessionId());


            _RequestHistoryNew.setTimeCreated(WebParamUtil.getTimestampValue(_RequestHistoryForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_RequestHistoryForm.getTimeCreated());

        	_RequestHistoryNew.setTimeCreated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_RequestHistoryNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _RequestHistoryNew);
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
            
            if (_RequestHistoryNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_RequestHistoryNew);
            if (returnObjects != null) returnObjects.put("target", _RequestHistoryNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _RequestHistoryNew);
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
             _RequestHistory =  _RequestHistoryNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }





        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, RequestHistoryForm _RequestHistoryForm, RequestHistory _RequestHistory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        RequestHistory _RequestHistory = m_ds.getById(cid);

        m_logger.debug("Before update " + RequestHistoryDS.objectToString(_RequestHistory));

        _RequestHistory.setForwardSiteId(WebParamUtil.getLongValue(_RequestHistoryForm.getForwardSiteId()));


        _RequestHistory.setIsDropped(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsDropped()));


        _RequestHistory.setIsPageless(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsPageless()));


        _RequestHistory.setIsLogin(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsLogin()));


        _RequestHistory.setIsAjax(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsAjax()));


        _RequestHistory.setIsRobot(WebParamUtil.getIntegerValue(_RequestHistoryForm.getIsRobot()));


        _RequestHistory.setUserid(WebParamUtil.getStringValue(_RequestHistoryForm.getUserid()));


        _RequestHistory.setUserAgent(WebParamUtil.getStringValue(_RequestHistoryForm.getUserAgent()));


        _RequestHistory.setRefer(WebParamUtil.getStringValue(_RequestHistoryForm.getRefer()));


        _RequestHistory.setRobot(WebParamUtil.getStringValue(_RequestHistoryForm.getRobot()));


        _RequestHistory.setRemoteIp(WebParamUtil.getStringValue(_RequestHistoryForm.getRemoteIp()));


        _RequestHistory.setSiteUrl(WebParamUtil.getStringValue(_RequestHistoryForm.getSiteUrl()));


        _RequestHistory.setUri(WebParamUtil.getStringValue(_RequestHistoryForm.getUri()));


        _RequestHistory.setQuery(WebParamUtil.getStringValue(_RequestHistoryForm.getQuery()));


        _RequestHistory.setRpci(WebParamUtil.getStringValue(_RequestHistoryForm.getRpci()));


        _RequestHistory.setSessionId(WebParamUtil.getStringValue(_RequestHistoryForm.getSessionId()));





        m_actionExtent.beforeUpdate(request, response, _RequestHistory);
        m_ds.update(_RequestHistory);
        m_actionExtent.afterUpdate(request, response, _RequestHistory);
        m_logger.debug("After update " + RequestHistoryDS.objectToString(_RequestHistory));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, RequestHistory _RequestHistory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        RequestHistory _RequestHistory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("forwardSiteId"))) {
            m_logger.debug("updating param forwardSiteId from " +_RequestHistory.getForwardSiteId() + "->" + request.getParameter("forwardSiteId"));
            _RequestHistory.setForwardSiteId(WebParamUtil.getLongValue(request.getParameter("forwardSiteId")));

        }
        if (!isMissing(request.getParameter("isDropped"))) {
            m_logger.debug("updating param isDropped from " +_RequestHistory.getIsDropped() + "->" + request.getParameter("isDropped"));
            _RequestHistory.setIsDropped(WebParamUtil.getIntegerValue(request.getParameter("isDropped")));

        }
        if (!isMissing(request.getParameter("isPageless"))) {
            m_logger.debug("updating param isPageless from " +_RequestHistory.getIsPageless() + "->" + request.getParameter("isPageless"));
            _RequestHistory.setIsPageless(WebParamUtil.getIntegerValue(request.getParameter("isPageless")));

        }
        if (!isMissing(request.getParameter("isLogin"))) {
            m_logger.debug("updating param isLogin from " +_RequestHistory.getIsLogin() + "->" + request.getParameter("isLogin"));
            _RequestHistory.setIsLogin(WebParamUtil.getIntegerValue(request.getParameter("isLogin")));

        }
        if (!isMissing(request.getParameter("isAjax"))) {
            m_logger.debug("updating param isAjax from " +_RequestHistory.getIsAjax() + "->" + request.getParameter("isAjax"));
            _RequestHistory.setIsAjax(WebParamUtil.getIntegerValue(request.getParameter("isAjax")));

        }
        if (!isMissing(request.getParameter("isRobot"))) {
            m_logger.debug("updating param isRobot from " +_RequestHistory.getIsRobot() + "->" + request.getParameter("isRobot"));
            _RequestHistory.setIsRobot(WebParamUtil.getIntegerValue(request.getParameter("isRobot")));

        }
        if (!isMissing(request.getParameter("userid"))) {
            m_logger.debug("updating param userid from " +_RequestHistory.getUserid() + "->" + request.getParameter("userid"));
            _RequestHistory.setUserid(WebParamUtil.getStringValue(request.getParameter("userid")));

        }
        if (!isMissing(request.getParameter("userAgent"))) {
            m_logger.debug("updating param userAgent from " +_RequestHistory.getUserAgent() + "->" + request.getParameter("userAgent"));
            _RequestHistory.setUserAgent(WebParamUtil.getStringValue(request.getParameter("userAgent")));

        }
        if (!isMissing(request.getParameter("refer"))) {
            m_logger.debug("updating param refer from " +_RequestHistory.getRefer() + "->" + request.getParameter("refer"));
            _RequestHistory.setRefer(WebParamUtil.getStringValue(request.getParameter("refer")));

        }
        if (!isMissing(request.getParameter("robot"))) {
            m_logger.debug("updating param robot from " +_RequestHistory.getRobot() + "->" + request.getParameter("robot"));
            _RequestHistory.setRobot(WebParamUtil.getStringValue(request.getParameter("robot")));

        }
        if (!isMissing(request.getParameter("remoteIp"))) {
            m_logger.debug("updating param remoteIp from " +_RequestHistory.getRemoteIp() + "->" + request.getParameter("remoteIp"));
            _RequestHistory.setRemoteIp(WebParamUtil.getStringValue(request.getParameter("remoteIp")));

        }
        if (!isMissing(request.getParameter("siteUrl"))) {
            m_logger.debug("updating param siteUrl from " +_RequestHistory.getSiteUrl() + "->" + request.getParameter("siteUrl"));
            _RequestHistory.setSiteUrl(WebParamUtil.getStringValue(request.getParameter("siteUrl")));

        }
        if (!isMissing(request.getParameter("uri"))) {
            m_logger.debug("updating param uri from " +_RequestHistory.getUri() + "->" + request.getParameter("uri"));
            _RequestHistory.setUri(WebParamUtil.getStringValue(request.getParameter("uri")));

        }
        if (!isMissing(request.getParameter("query"))) {
            m_logger.debug("updating param query from " +_RequestHistory.getQuery() + "->" + request.getParameter("query"));
            _RequestHistory.setQuery(WebParamUtil.getStringValue(request.getParameter("query")));

        }
        if (!isMissing(request.getParameter("rpci"))) {
            m_logger.debug("updating param rpci from " +_RequestHistory.getRpci() + "->" + request.getParameter("rpci"));
            _RequestHistory.setRpci(WebParamUtil.getStringValue(request.getParameter("rpci")));

        }
        if (!isMissing(request.getParameter("sessionId"))) {
            m_logger.debug("updating param sessionId from " +_RequestHistory.getSessionId() + "->" + request.getParameter("sessionId"));
            _RequestHistory.setSessionId(WebParamUtil.getStringValue(request.getParameter("sessionId")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_RequestHistory.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _RequestHistory.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

        m_actionExtent.beforeUpdate(request, response, _RequestHistory);
        m_ds.update(_RequestHistory);
        m_actionExtent.afterUpdate(request, response, _RequestHistory);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, RequestHistory _RequestHistory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        RequestHistory _RequestHistory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("forwardSiteId"))) {
            m_logger.debug("updating param forwardSiteId from " +_RequestHistory.getForwardSiteId() + "->" + request.getParameter("forwardSiteId"));
            _RequestHistory.setForwardSiteId(WebParamUtil.getLongValue(request.getParameter("forwardSiteId")));

        }
        if (!isMissing(request.getParameter("isDropped"))) {
            m_logger.debug("updating param isDropped from " +_RequestHistory.getIsDropped() + "->" + request.getParameter("isDropped"));
            _RequestHistory.setIsDropped(WebParamUtil.getIntegerValue(request.getParameter("isDropped")));

        }
        if (!isMissing(request.getParameter("isPageless"))) {
            m_logger.debug("updating param isPageless from " +_RequestHistory.getIsPageless() + "->" + request.getParameter("isPageless"));
            _RequestHistory.setIsPageless(WebParamUtil.getIntegerValue(request.getParameter("isPageless")));

        }
        if (!isMissing(request.getParameter("isLogin"))) {
            m_logger.debug("updating param isLogin from " +_RequestHistory.getIsLogin() + "->" + request.getParameter("isLogin"));
            _RequestHistory.setIsLogin(WebParamUtil.getIntegerValue(request.getParameter("isLogin")));

        }
        if (!isMissing(request.getParameter("isAjax"))) {
            m_logger.debug("updating param isAjax from " +_RequestHistory.getIsAjax() + "->" + request.getParameter("isAjax"));
            _RequestHistory.setIsAjax(WebParamUtil.getIntegerValue(request.getParameter("isAjax")));

        }
        if (!isMissing(request.getParameter("isRobot"))) {
            m_logger.debug("updating param isRobot from " +_RequestHistory.getIsRobot() + "->" + request.getParameter("isRobot"));
            _RequestHistory.setIsRobot(WebParamUtil.getIntegerValue(request.getParameter("isRobot")));

        }
        if (!isMissing(request.getParameter("userid"))) {
            m_logger.debug("updating param userid from " +_RequestHistory.getUserid() + "->" + request.getParameter("userid"));
            _RequestHistory.setUserid(WebParamUtil.getStringValue(request.getParameter("userid")));

        }
        if (!isMissing(request.getParameter("userAgent"))) {
            m_logger.debug("updating param userAgent from " +_RequestHistory.getUserAgent() + "->" + request.getParameter("userAgent"));
            _RequestHistory.setUserAgent(WebParamUtil.getStringValue(request.getParameter("userAgent")));

        }
        if (!isMissing(request.getParameter("refer"))) {
            m_logger.debug("updating param refer from " +_RequestHistory.getRefer() + "->" + request.getParameter("refer"));
            _RequestHistory.setRefer(WebParamUtil.getStringValue(request.getParameter("refer")));

        }
        if (!isMissing(request.getParameter("robot"))) {
            m_logger.debug("updating param robot from " +_RequestHistory.getRobot() + "->" + request.getParameter("robot"));
            _RequestHistory.setRobot(WebParamUtil.getStringValue(request.getParameter("robot")));

        }
        if (!isMissing(request.getParameter("remoteIp"))) {
            m_logger.debug("updating param remoteIp from " +_RequestHistory.getRemoteIp() + "->" + request.getParameter("remoteIp"));
            _RequestHistory.setRemoteIp(WebParamUtil.getStringValue(request.getParameter("remoteIp")));

        }
        if (!isMissing(request.getParameter("siteUrl"))) {
            m_logger.debug("updating param siteUrl from " +_RequestHistory.getSiteUrl() + "->" + request.getParameter("siteUrl"));
            _RequestHistory.setSiteUrl(WebParamUtil.getStringValue(request.getParameter("siteUrl")));

        }
        if (!isMissing(request.getParameter("uri"))) {
            m_logger.debug("updating param uri from " +_RequestHistory.getUri() + "->" + request.getParameter("uri"));
            _RequestHistory.setUri(WebParamUtil.getStringValue(request.getParameter("uri")));

        }
        if (!isMissing(request.getParameter("query"))) {
            m_logger.debug("updating param query from " +_RequestHistory.getQuery() + "->" + request.getParameter("query"));
            _RequestHistory.setQuery(WebParamUtil.getStringValue(request.getParameter("query")));

        }
        if (!isMissing(request.getParameter("rpci"))) {
            m_logger.debug("updating param rpci from " +_RequestHistory.getRpci() + "->" + request.getParameter("rpci"));
            _RequestHistory.setRpci(WebParamUtil.getStringValue(request.getParameter("rpci")));

        }
        if (!isMissing(request.getParameter("sessionId"))) {
            m_logger.debug("updating param sessionId from " +_RequestHistory.getSessionId() + "->" + request.getParameter("sessionId"));
            _RequestHistory.setSessionId(WebParamUtil.getStringValue(request.getParameter("sessionId")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_RequestHistory.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _RequestHistory.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, RequestHistory _RequestHistory) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        RequestHistory _RequestHistory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("forwardSiteId"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getForwardSiteId());
        }
        if (!isMissing(request.getParameter("isDropped"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getIsDropped());
        }
        if (!isMissing(request.getParameter("isPageless"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getIsPageless());
        }
        if (!isMissing(request.getParameter("isLogin"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getIsLogin());
        }
        if (!isMissing(request.getParameter("isAjax"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getIsAjax());
        }
        if (!isMissing(request.getParameter("isRobot"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getIsRobot());
        }
        if (!isMissing(request.getParameter("userid"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getUserid());
        }
        if (!isMissing(request.getParameter("userAgent"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getUserAgent());
        }
        if (!isMissing(request.getParameter("refer"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getRefer());
        }
        if (!isMissing(request.getParameter("robot"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getRobot());
        }
        if (!isMissing(request.getParameter("remoteIp"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getRemoteIp());
        }
        if (!isMissing(request.getParameter("siteUrl"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getSiteUrl());
        }
        if (!isMissing(request.getParameter("uri"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getUri());
        }
        if (!isMissing(request.getParameter("query"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getQuery());
        }
        if (!isMissing(request.getParameter("rpci"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getRpci());
        }
        if (!isMissing(request.getParameter("sessionId"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getSessionId());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_RequestHistory.getTimeCreated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(RequestHistory _RequestHistory) throws Exception {
    }

    protected String getFieldByName(String fieldName, RequestHistory _RequestHistory) {
        if (fieldName == null || fieldName.equals("")|| _RequestHistory == null) return null;
        
        if (fieldName.equals("forwardSiteId")) {
            return WebUtil.display(_RequestHistory.getForwardSiteId());
        }
        if (fieldName.equals("isDropped")) {
            return WebUtil.display(_RequestHistory.getIsDropped());
        }
        if (fieldName.equals("isPageless")) {
            return WebUtil.display(_RequestHistory.getIsPageless());
        }
        if (fieldName.equals("isLogin")) {
            return WebUtil.display(_RequestHistory.getIsLogin());
        }
        if (fieldName.equals("isAjax")) {
            return WebUtil.display(_RequestHistory.getIsAjax());
        }
        if (fieldName.equals("isRobot")) {
            return WebUtil.display(_RequestHistory.getIsRobot());
        }
        if (fieldName.equals("userid")) {
            return WebUtil.display(_RequestHistory.getUserid());
        }
        if (fieldName.equals("userAgent")) {
            return WebUtil.display(_RequestHistory.getUserAgent());
        }
        if (fieldName.equals("refer")) {
            return WebUtil.display(_RequestHistory.getRefer());
        }
        if (fieldName.equals("robot")) {
            return WebUtil.display(_RequestHistory.getRobot());
        }
        if (fieldName.equals("remoteIp")) {
            return WebUtil.display(_RequestHistory.getRemoteIp());
        }
        if (fieldName.equals("siteUrl")) {
            return WebUtil.display(_RequestHistory.getSiteUrl());
        }
        if (fieldName.equals("uri")) {
            return WebUtil.display(_RequestHistory.getUri());
        }
        if (fieldName.equals("query")) {
            return WebUtil.display(_RequestHistory.getQuery());
        }
        if (fieldName.equals("rpci")) {
            return WebUtil.display(_RequestHistory.getRpci());
        }
        if (fieldName.equals("sessionId")) {
            return WebUtil.display(_RequestHistory.getSessionId());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_RequestHistory.getTimeCreated());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        RequestHistoryForm _RequestHistoryForm = (RequestHistoryForm) form;

		if(requestParams.containsKey("forwardSiteId"))
			_RequestHistoryForm.setForwardSiteId((String)requestParams.get("forwardSiteId"));
		if(requestParams.containsKey("isDropped"))
			_RequestHistoryForm.setIsDropped((String)requestParams.get("isDropped"));
		if(requestParams.containsKey("isPageless"))
			_RequestHistoryForm.setIsPageless((String)requestParams.get("isPageless"));
		if(requestParams.containsKey("isLogin"))
			_RequestHistoryForm.setIsLogin((String)requestParams.get("isLogin"));
		if(requestParams.containsKey("isAjax"))
			_RequestHistoryForm.setIsAjax((String)requestParams.get("isAjax"));
		if(requestParams.containsKey("isRobot"))
			_RequestHistoryForm.setIsRobot((String)requestParams.get("isRobot"));
		if(requestParams.containsKey("userid"))
			_RequestHistoryForm.setUserid((String)requestParams.get("userid"));
		if(requestParams.containsKey("userAgent"))
			_RequestHistoryForm.setUserAgent((String)requestParams.get("userAgent"));
		if(requestParams.containsKey("refer"))
			_RequestHistoryForm.setRefer((String)requestParams.get("refer"));
		if(requestParams.containsKey("robot"))
			_RequestHistoryForm.setRobot((String)requestParams.get("robot"));
		if(requestParams.containsKey("remoteIp"))
			_RequestHistoryForm.setRemoteIp((String)requestParams.get("remoteIp"));
		if(requestParams.containsKey("siteUrl"))
			_RequestHistoryForm.setSiteUrl((String)requestParams.get("siteUrl"));
		if(requestParams.containsKey("uri"))
			_RequestHistoryForm.setUri((String)requestParams.get("uri"));
		if(requestParams.containsKey("query"))
			_RequestHistoryForm.setQuery((String)requestParams.get("query"));
		if(requestParams.containsKey("rpci"))
			_RequestHistoryForm.setRpci((String)requestParams.get("rpci"));
		if(requestParams.containsKey("sessionId"))
			_RequestHistoryForm.setSessionId((String)requestParams.get("sessionId"));
		if(requestParams.containsKey("timeCreated"))
			_RequestHistoryForm.setTimeCreated((String)requestParams.get("timeCreated"));
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
        m_viewManager.registerView(getActionName(), "request_history_home=NULL,/jsp/form/requestHistory_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "request_history_list=NULL,/jsp/form/requestHistory_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "request_history_form=NULL,/jsp/form/requestHistory_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "request_history_ajax=NULL,/jsp/form/requestHistory_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "request_history_home=NULL,/jsp/form/requestHistory_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "request_history_list=NULL,/jsp/form/requestHistory_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "request_history_form=NULL,/jsp/form/requestHistory_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "request_history_ajax=NULL,/jsp/form/requestHistory_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected RequestHistoryDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
