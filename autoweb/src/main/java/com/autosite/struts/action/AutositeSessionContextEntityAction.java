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

import com.autosite.db.AutositeSessionContextEntity;
import com.autosite.ds.AutositeSessionContextEntityDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.AutositeSessionContextEntityForm;
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




public class AutositeSessionContextEntityAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeSessionContextEntityAction.class);

    public AutositeSessionContextEntityAction(){
        m_ds = AutositeSessionContextEntityDS.getInstance();
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

        AutositeSessionContextEntityForm _AutositeSessionContextEntityForm = (AutositeSessionContextEntityForm) form;
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


        AutositeSessionContextEntity _AutositeSessionContextEntity = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _AutositeSessionContextEntity = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //AutositeSessionContextEntity _AutositeSessionContextEntity = m_ds.getById(cid);

            if (_AutositeSessionContextEntity == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_AutositeSessionContextEntity.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeSessionContextEntity.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_AutositeSessionContextEntity);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _AutositeSessionContextEntityForm == null) {
    	            editField(request, response, _AutositeSessionContextEntity);
				} else {
    	            edit(request, response, _AutositeSessionContextEntityForm, _AutositeSessionContextEntity);
				}
                if (returnObjects != null) returnObjects.put("target", _AutositeSessionContextEntity);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                AutositeSessionContextEntity o = m_ds.getById( _AutositeSessionContextEntity.getId(), true);

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
            //AutositeSessionContextEntity _AutositeSessionContextEntity = m_ds.getById(cid);

            if (_AutositeSessionContextEntity == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeSessionContextEntity.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeSessionContextEntity.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _AutositeSessionContextEntity);
                if (returnObjects != null) returnObjects.put("target", _AutositeSessionContextEntity);
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
            //AutositeSessionContextEntity _AutositeSessionContextEntity = m_ds.getById(cid);

            if (_AutositeSessionContextEntity == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeSessionContextEntity.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeSessionContextEntity.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _AutositeSessionContextEntity);
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

            m_ds.delete(_AutositeSessionContextEntity); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _AutositeSessionContextEntity);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _AutositeSessionContextEntity);
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


            m_logger.info("Creating new AutositeSessionContextEntity" );
            AutositeSessionContextEntity _AutositeSessionContextEntityNew = new AutositeSessionContextEntity();   

            // Setting IDs for the object
            _AutositeSessionContextEntityNew.setSiteId(site.getId());
			
            if ( _AutositeSessionContextEntityForm == null) {
                setFields(request, response, _AutositeSessionContextEntityNew);
            } else {

            _AutositeSessionContextEntityNew.setSerial(WebParamUtil.getStringValue(_AutositeSessionContextEntityForm.getSerial()));
            m_logger.debug("setting Serial=" +_AutositeSessionContextEntityForm.getSerial());


            _AutositeSessionContextEntityNew.setIsLogin(WebParamUtil.getIntegerValue(_AutositeSessionContextEntityForm.getIsLogin()));
            m_logger.debug("setting IsLogin=" +_AutositeSessionContextEntityForm.getIsLogin());


            _AutositeSessionContextEntityNew.setTimeLogin(WebParamUtil.getTimestampValue(_AutositeSessionContextEntityForm.getTimeLogin()));
            m_logger.debug("setting TimeLogin=" +_AutositeSessionContextEntityForm.getTimeLogin());


            _AutositeSessionContextEntityNew.setTimeLastAccess(WebParamUtil.getTimestampValue(_AutositeSessionContextEntityForm.getTimeLastAccess()));
            m_logger.debug("setting TimeLastAccess=" +_AutositeSessionContextEntityForm.getTimeLastAccess());


            _AutositeSessionContextEntityNew.setLoginUserId(WebParamUtil.getLongValue(_AutositeSessionContextEntityForm.getLoginUserId()));
            m_logger.debug("setting LoginUserId=" +_AutositeSessionContextEntityForm.getLoginUserId());


            _AutositeSessionContextEntityNew.setSessionType(WebParamUtil.getIntegerValue(_AutositeSessionContextEntityForm.getSessionType()));
            m_logger.debug("setting SessionType=" +_AutositeSessionContextEntityForm.getSessionType());


            _AutositeSessionContextEntityNew.setRemoteDeviceId(WebParamUtil.getLongValue(_AutositeSessionContextEntityForm.getRemoteDeviceId()));
            m_logger.debug("setting RemoteDeviceId=" +_AutositeSessionContextEntityForm.getRemoteDeviceId());


            _AutositeSessionContextEntityNew.setRemoteIp(WebParamUtil.getStringValue(_AutositeSessionContextEntityForm.getRemoteIp()));
            m_logger.debug("setting RemoteIp=" +_AutositeSessionContextEntityForm.getRemoteIp());


            _AutositeSessionContextEntityNew.setTimeCreated(WebParamUtil.getTimestampValue(_AutositeSessionContextEntityForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_AutositeSessionContextEntityForm.getTimeCreated());

        	_AutositeSessionContextEntityNew.setTimeCreated(new TimeNow());

            _AutositeSessionContextEntityNew.setTimeUpdated(WebParamUtil.getTimestampValue(_AutositeSessionContextEntityForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_AutositeSessionContextEntityForm.getTimeUpdated());

        	_AutositeSessionContextEntityNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_AutositeSessionContextEntityNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _AutositeSessionContextEntityNew);
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
            
            if (_AutositeSessionContextEntityNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_AutositeSessionContextEntityNew);
            if (returnObjects != null) returnObjects.put("target", _AutositeSessionContextEntityNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _AutositeSessionContextEntityNew);
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, AutositeSessionContextEntityForm _AutositeSessionContextEntityForm, AutositeSessionContextEntity _AutositeSessionContextEntity) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSessionContextEntity _AutositeSessionContextEntity = m_ds.getById(cid);

        m_logger.debug("Before update " + AutositeSessionContextEntityDS.objectToString(_AutositeSessionContextEntity));

        _AutositeSessionContextEntity.setSerial(WebParamUtil.getStringValue(_AutositeSessionContextEntityForm.getSerial()));


        _AutositeSessionContextEntity.setIsLogin(WebParamUtil.getIntegerValue(_AutositeSessionContextEntityForm.getIsLogin()));


        _AutositeSessionContextEntity.setTimeLogin(WebParamUtil.getTimestampValue(_AutositeSessionContextEntityForm.getTimeLogin()));


        _AutositeSessionContextEntity.setTimeLastAccess(WebParamUtil.getTimestampValue(_AutositeSessionContextEntityForm.getTimeLastAccess()));


        _AutositeSessionContextEntity.setLoginUserId(WebParamUtil.getLongValue(_AutositeSessionContextEntityForm.getLoginUserId()));


        _AutositeSessionContextEntity.setSessionType(WebParamUtil.getIntegerValue(_AutositeSessionContextEntityForm.getSessionType()));


        _AutositeSessionContextEntity.setRemoteDeviceId(WebParamUtil.getLongValue(_AutositeSessionContextEntityForm.getRemoteDeviceId()));


        _AutositeSessionContextEntity.setRemoteIp(WebParamUtil.getStringValue(_AutositeSessionContextEntityForm.getRemoteIp()));




        _AutositeSessionContextEntity.setTimeUpdated(WebParamUtil.getTimestampValue(_AutositeSessionContextEntityForm.getTimeUpdated()));

        _AutositeSessionContextEntity.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _AutositeSessionContextEntity);
        m_ds.update(_AutositeSessionContextEntity);
        m_actionExtent.afterUpdate(request, response, _AutositeSessionContextEntity);
        m_logger.debug("After update " + AutositeSessionContextEntityDS.objectToString(_AutositeSessionContextEntity));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeSessionContextEntity _AutositeSessionContextEntity) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSessionContextEntity _AutositeSessionContextEntity = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serial"))) {
            m_logger.debug("updating param serial from " +_AutositeSessionContextEntity.getSerial() + "->" + request.getParameter("serial"));
            _AutositeSessionContextEntity.setSerial(WebParamUtil.getStringValue(request.getParameter("serial")));

        }
        if (!isMissing(request.getParameter("isLogin"))) {
            m_logger.debug("updating param isLogin from " +_AutositeSessionContextEntity.getIsLogin() + "->" + request.getParameter("isLogin"));
            _AutositeSessionContextEntity.setIsLogin(WebParamUtil.getIntegerValue(request.getParameter("isLogin")));

        }
        if (!isMissing(request.getParameter("timeLogin"))) {
            m_logger.debug("updating param timeLogin from " +_AutositeSessionContextEntity.getTimeLogin() + "->" + request.getParameter("timeLogin"));
            _AutositeSessionContextEntity.setTimeLogin(WebParamUtil.getTimestampValue(request.getParameter("timeLogin")));

        }
        if (!isMissing(request.getParameter("timeLastAccess"))) {
            m_logger.debug("updating param timeLastAccess from " +_AutositeSessionContextEntity.getTimeLastAccess() + "->" + request.getParameter("timeLastAccess"));
            _AutositeSessionContextEntity.setTimeLastAccess(WebParamUtil.getTimestampValue(request.getParameter("timeLastAccess")));

        }
        if (!isMissing(request.getParameter("loginUserId"))) {
            m_logger.debug("updating param loginUserId from " +_AutositeSessionContextEntity.getLoginUserId() + "->" + request.getParameter("loginUserId"));
            _AutositeSessionContextEntity.setLoginUserId(WebParamUtil.getLongValue(request.getParameter("loginUserId")));

        }
        if (!isMissing(request.getParameter("sessionType"))) {
            m_logger.debug("updating param sessionType from " +_AutositeSessionContextEntity.getSessionType() + "->" + request.getParameter("sessionType"));
            _AutositeSessionContextEntity.setSessionType(WebParamUtil.getIntegerValue(request.getParameter("sessionType")));

        }
        if (!isMissing(request.getParameter("remoteDeviceId"))) {
            m_logger.debug("updating param remoteDeviceId from " +_AutositeSessionContextEntity.getRemoteDeviceId() + "->" + request.getParameter("remoteDeviceId"));
            _AutositeSessionContextEntity.setRemoteDeviceId(WebParamUtil.getLongValue(request.getParameter("remoteDeviceId")));

        }
        if (!isMissing(request.getParameter("remoteIp"))) {
            m_logger.debug("updating param remoteIp from " +_AutositeSessionContextEntity.getRemoteIp() + "->" + request.getParameter("remoteIp"));
            _AutositeSessionContextEntity.setRemoteIp(WebParamUtil.getStringValue(request.getParameter("remoteIp")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeSessionContextEntity.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeSessionContextEntity.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_AutositeSessionContextEntity.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _AutositeSessionContextEntity.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _AutositeSessionContextEntity.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _AutositeSessionContextEntity);
        m_ds.update(_AutositeSessionContextEntity);
        m_actionExtent.afterUpdate(request, response, _AutositeSessionContextEntity);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeSessionContextEntity _AutositeSessionContextEntity) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSessionContextEntity _AutositeSessionContextEntity = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serial"))) {
            m_logger.debug("updating param serial from " +_AutositeSessionContextEntity.getSerial() + "->" + request.getParameter("serial"));
            _AutositeSessionContextEntity.setSerial(WebParamUtil.getStringValue(request.getParameter("serial")));

        }
        if (!isMissing(request.getParameter("isLogin"))) {
            m_logger.debug("updating param isLogin from " +_AutositeSessionContextEntity.getIsLogin() + "->" + request.getParameter("isLogin"));
            _AutositeSessionContextEntity.setIsLogin(WebParamUtil.getIntegerValue(request.getParameter("isLogin")));

        }
        if (!isMissing(request.getParameter("timeLogin"))) {
            m_logger.debug("updating param timeLogin from " +_AutositeSessionContextEntity.getTimeLogin() + "->" + request.getParameter("timeLogin"));
            _AutositeSessionContextEntity.setTimeLogin(WebParamUtil.getTimestampValue(request.getParameter("timeLogin")));

        }
        if (!isMissing(request.getParameter("timeLastAccess"))) {
            m_logger.debug("updating param timeLastAccess from " +_AutositeSessionContextEntity.getTimeLastAccess() + "->" + request.getParameter("timeLastAccess"));
            _AutositeSessionContextEntity.setTimeLastAccess(WebParamUtil.getTimestampValue(request.getParameter("timeLastAccess")));

        }
        if (!isMissing(request.getParameter("loginUserId"))) {
            m_logger.debug("updating param loginUserId from " +_AutositeSessionContextEntity.getLoginUserId() + "->" + request.getParameter("loginUserId"));
            _AutositeSessionContextEntity.setLoginUserId(WebParamUtil.getLongValue(request.getParameter("loginUserId")));

        }
        if (!isMissing(request.getParameter("sessionType"))) {
            m_logger.debug("updating param sessionType from " +_AutositeSessionContextEntity.getSessionType() + "->" + request.getParameter("sessionType"));
            _AutositeSessionContextEntity.setSessionType(WebParamUtil.getIntegerValue(request.getParameter("sessionType")));

        }
        if (!isMissing(request.getParameter("remoteDeviceId"))) {
            m_logger.debug("updating param remoteDeviceId from " +_AutositeSessionContextEntity.getRemoteDeviceId() + "->" + request.getParameter("remoteDeviceId"));
            _AutositeSessionContextEntity.setRemoteDeviceId(WebParamUtil.getLongValue(request.getParameter("remoteDeviceId")));

        }
        if (!isMissing(request.getParameter("remoteIp"))) {
            m_logger.debug("updating param remoteIp from " +_AutositeSessionContextEntity.getRemoteIp() + "->" + request.getParameter("remoteIp"));
            _AutositeSessionContextEntity.setRemoteIp(WebParamUtil.getStringValue(request.getParameter("remoteIp")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeSessionContextEntity.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeSessionContextEntity.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_AutositeSessionContextEntity.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _AutositeSessionContextEntity.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _AutositeSessionContextEntity.setTimeUpdated(new TimeNow());
        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeSessionContextEntity _AutositeSessionContextEntity) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSessionContextEntity _AutositeSessionContextEntity = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serial"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getSerial());
        }
        if (!isMissing(request.getParameter("isLogin"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getIsLogin());
        }
        if (!isMissing(request.getParameter("timeLogin"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getTimeLogin());
        }
        if (!isMissing(request.getParameter("timeLastAccess"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getTimeLastAccess());
        }
        if (!isMissing(request.getParameter("loginUserId"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getLoginUserId());
        }
        if (!isMissing(request.getParameter("sessionType"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getSessionType());
        }
        if (!isMissing(request.getParameter("remoteDeviceId"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getRemoteDeviceId());
        }
        if (!isMissing(request.getParameter("remoteIp"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getRemoteIp());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_AutositeSessionContextEntity.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeSessionContextEntity _AutositeSessionContextEntity) throws Exception {
    }

    protected String getFieldByName(String fieldName, AutositeSessionContextEntity _AutositeSessionContextEntity) {
        if (fieldName == null || fieldName.equals("")|| _AutositeSessionContextEntity == null) return null;
        
        if (fieldName.equals("serial")) {
            return WebUtil.display(_AutositeSessionContextEntity.getSerial());
        }
        if (fieldName.equals("isLogin")) {
            return WebUtil.display(_AutositeSessionContextEntity.getIsLogin());
        }
        if (fieldName.equals("timeLogin")) {
            return WebUtil.display(_AutositeSessionContextEntity.getTimeLogin());
        }
        if (fieldName.equals("timeLastAccess")) {
            return WebUtil.display(_AutositeSessionContextEntity.getTimeLastAccess());
        }
        if (fieldName.equals("loginUserId")) {
            return WebUtil.display(_AutositeSessionContextEntity.getLoginUserId());
        }
        if (fieldName.equals("sessionType")) {
            return WebUtil.display(_AutositeSessionContextEntity.getSessionType());
        }
        if (fieldName.equals("remoteDeviceId")) {
            return WebUtil.display(_AutositeSessionContextEntity.getRemoteDeviceId());
        }
        if (fieldName.equals("remoteIp")) {
            return WebUtil.display(_AutositeSessionContextEntity.getRemoteIp());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_AutositeSessionContextEntity.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_AutositeSessionContextEntity.getTimeUpdated());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        AutositeSessionContextEntityForm _AutositeSessionContextEntityForm = (AutositeSessionContextEntityForm) form;

		if(requestParams.containsKey("serial"))
			_AutositeSessionContextEntityForm.setSerial((String)requestParams.get("serial"));
		if(requestParams.containsKey("isLogin"))
			_AutositeSessionContextEntityForm.setIsLogin((String)requestParams.get("isLogin"));
		if(requestParams.containsKey("timeLogin"))
			_AutositeSessionContextEntityForm.setTimeLogin((String)requestParams.get("timeLogin"));
		if(requestParams.containsKey("timeLastAccess"))
			_AutositeSessionContextEntityForm.setTimeLastAccess((String)requestParams.get("timeLastAccess"));
		if(requestParams.containsKey("loginUserId"))
			_AutositeSessionContextEntityForm.setLoginUserId((String)requestParams.get("loginUserId"));
		if(requestParams.containsKey("sessionType"))
			_AutositeSessionContextEntityForm.setSessionType((String)requestParams.get("sessionType"));
		if(requestParams.containsKey("remoteDeviceId"))
			_AutositeSessionContextEntityForm.setRemoteDeviceId((String)requestParams.get("remoteDeviceId"));
		if(requestParams.containsKey("remoteIp"))
			_AutositeSessionContextEntityForm.setRemoteIp((String)requestParams.get("remoteIp"));
		if(requestParams.containsKey("timeCreated"))
			_AutositeSessionContextEntityForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_AutositeSessionContextEntityForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "autosite_session_context_entity_home=NULL,/jsp/form/autositeSessionContextEntity_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_session_context_entity_list=NULL,/jsp/form/autositeSessionContextEntity_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_session_context_entity_form=NULL,/jsp/form/autositeSessionContextEntity_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_session_context_entity_ajax=NULL,/jsp/form/autositeSessionContextEntity_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "autosite_session_context_entity_home=NULL,/jsp/form/autositeSessionContextEntity_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_session_context_entity_list=NULL,/jsp/form/autositeSessionContextEntity_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_session_context_entity_form=NULL,/jsp/form/autositeSessionContextEntity_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_session_context_entity_ajax=NULL,/jsp/form/autositeSessionContextEntity_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected AutositeSessionContextEntityDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
