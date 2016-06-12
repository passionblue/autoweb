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

import com.autosite.db.AutositeUser;
import com.autosite.ds.AutositeUserDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.AutositeUserForm;
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




public class AutositeUserAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeUserAction.class);

    public AutositeUserAction(){
        m_ds = AutositeUserDS.getInstance();
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

        AutositeUserForm _AutositeUserForm = (AutositeUserForm) form;
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


        AutositeUser _AutositeUser = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _AutositeUser = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //AutositeUser _AutositeUser = m_ds.getById(cid);

            if (_AutositeUser == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_AutositeUser.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeUser.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_AutositeUser);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _AutositeUserForm == null) {
    	            editField(request, response, _AutositeUser);
				} else {
    	            edit(request, response, _AutositeUserForm, _AutositeUser);
				}
                if (returnObjects != null) returnObjects.put("target", _AutositeUser);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                AutositeUser o = m_ds.getById( _AutositeUser.getId(), true);

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
            //AutositeUser _AutositeUser = m_ds.getById(cid);

            if (_AutositeUser == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeUser.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeUser.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _AutositeUser);
                if (returnObjects != null) returnObjects.put("target", _AutositeUser);
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
            //AutositeUser _AutositeUser = m_ds.getById(cid);

            if (_AutositeUser == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeUser.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeUser.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _AutositeUser);
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

            m_ds.delete(_AutositeUser); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _AutositeUser);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _AutositeUser);
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


            m_logger.info("Creating new AutositeUser" );
            AutositeUser _AutositeUserNew = new AutositeUser();   

            // Setting IDs for the object
            _AutositeUserNew.setSiteId(site.getId());
			
            if ( _AutositeUserForm == null) {
                setFields(request, response, _AutositeUserNew);
            } else {

            _AutositeUserNew.setUsername(WebParamUtil.getStringValue(_AutositeUserForm.getUsername()));
            m_logger.debug("setting Username=" +_AutositeUserForm.getUsername());


            _AutositeUserNew.setPassword(WebParamUtil.getStringValue(_AutositeUserForm.getPassword()));
            m_logger.debug("setting Password=" +_AutositeUserForm.getPassword());


            _AutositeUserNew.setEmail(WebParamUtil.getStringValue(_AutositeUserForm.getEmail()));
            m_logger.debug("setting Email=" +_AutositeUserForm.getEmail());


            _AutositeUserNew.setUserType(WebParamUtil.getIntegerValue(_AutositeUserForm.getUserType()));
            m_logger.debug("setting UserType=" +_AutositeUserForm.getUserType());


            _AutositeUserNew.setFirstName(WebParamUtil.getStringValue(_AutositeUserForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_AutositeUserForm.getFirstName());


            _AutositeUserNew.setLastName(WebParamUtil.getStringValue(_AutositeUserForm.getLastName()));
            m_logger.debug("setting LastName=" +_AutositeUserForm.getLastName());


            _AutositeUserNew.setNickname(WebParamUtil.getStringValue(_AutositeUserForm.getNickname()));
            m_logger.debug("setting Nickname=" +_AutositeUserForm.getNickname());


            _AutositeUserNew.setTimeCreated(WebParamUtil.getTimestampValue(_AutositeUserForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_AutositeUserForm.getTimeCreated());

        	_AutositeUserNew.setTimeCreated(new TimeNow());

            _AutositeUserNew.setTimeUpdated(WebParamUtil.getTimestampValue(_AutositeUserForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_AutositeUserForm.getTimeUpdated());

        	_AutositeUserNew.setTimeUpdated(new TimeNow());

            _AutositeUserNew.setDisabled(WebParamUtil.getIntegerValue(_AutositeUserForm.getDisabled()));
            m_logger.debug("setting Disabled=" +_AutositeUserForm.getDisabled());


            _AutositeUserNew.setTimeDisabled(WebParamUtil.getTimestampValue(_AutositeUserForm.getTimeDisabled()));
            m_logger.debug("setting TimeDisabled=" +_AutositeUserForm.getTimeDisabled());


            _AutositeUserNew.setConfirmed(WebParamUtil.getIntegerValue(_AutositeUserForm.getConfirmed()));
            m_logger.debug("setting Confirmed=" +_AutositeUserForm.getConfirmed());


            _AutositeUserNew.setTimeConfirmed(WebParamUtil.getTimestampValue(_AutositeUserForm.getTimeConfirmed()));
            m_logger.debug("setting TimeConfirmed=" +_AutositeUserForm.getTimeConfirmed());


            _AutositeUserNew.setPagelessSession(WebParamUtil.getIntegerValue(_AutositeUserForm.getPagelessSession()));
            m_logger.debug("setting PagelessSession=" +_AutositeUserForm.getPagelessSession());


            _AutositeUserNew.setOpt1(WebParamUtil.getIntegerValue(_AutositeUserForm.getOpt1()));
            m_logger.debug("setting Opt1=" +_AutositeUserForm.getOpt1());


            _AutositeUserNew.setOpt2(WebParamUtil.getStringValue(_AutositeUserForm.getOpt2()));
            m_logger.debug("setting Opt2=" +_AutositeUserForm.getOpt2());


			}

            try {
                checkDepedenceIntegrity(_AutositeUserNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _AutositeUserNew);
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
            
            if (_AutositeUserNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_AutositeUserNew);
            if (returnObjects != null) returnObjects.put("target", _AutositeUser);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _AutositeUserNew);
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, AutositeUserForm _AutositeUserForm, AutositeUser _AutositeUser) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeUser _AutositeUser = m_ds.getById(cid);

        m_logger.debug("Before update " + AutositeUserDS.objectToString(_AutositeUser));

        _AutositeUser.setUsername(WebParamUtil.getStringValue(_AutositeUserForm.getUsername()));


        _AutositeUser.setPassword(WebParamUtil.getStringValue(_AutositeUserForm.getPassword()));


        _AutositeUser.setEmail(WebParamUtil.getStringValue(_AutositeUserForm.getEmail()));


        _AutositeUser.setUserType(WebParamUtil.getIntegerValue(_AutositeUserForm.getUserType()));


        _AutositeUser.setFirstName(WebParamUtil.getStringValue(_AutositeUserForm.getFirstName()));


        _AutositeUser.setLastName(WebParamUtil.getStringValue(_AutositeUserForm.getLastName()));


        _AutositeUser.setNickname(WebParamUtil.getStringValue(_AutositeUserForm.getNickname()));




        _AutositeUser.setTimeUpdated(WebParamUtil.getTimestampValue(_AutositeUserForm.getTimeUpdated()));

        _AutositeUser.setTimeUpdated(new TimeNow());

        _AutositeUser.setDisabled(WebParamUtil.getIntegerValue(_AutositeUserForm.getDisabled()));


        _AutositeUser.setTimeDisabled(WebParamUtil.getTimestampValue(_AutositeUserForm.getTimeDisabled()));


        _AutositeUser.setConfirmed(WebParamUtil.getIntegerValue(_AutositeUserForm.getConfirmed()));


        _AutositeUser.setTimeConfirmed(WebParamUtil.getTimestampValue(_AutositeUserForm.getTimeConfirmed()));


        _AutositeUser.setPagelessSession(WebParamUtil.getIntegerValue(_AutositeUserForm.getPagelessSession()));


        _AutositeUser.setOpt1(WebParamUtil.getIntegerValue(_AutositeUserForm.getOpt1()));


        _AutositeUser.setOpt2(WebParamUtil.getStringValue(_AutositeUserForm.getOpt2()));



        m_actionExtent.beforeUpdate(request, response, _AutositeUser);
        m_ds.update(_AutositeUser);
        m_actionExtent.afterUpdate(request, response, _AutositeUser);
        m_logger.debug("After update " + AutositeUserDS.objectToString(_AutositeUser));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeUser _AutositeUser) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeUser _AutositeUser = m_ds.getById(cid);

        if (!isMissing(request.getParameter("username"))) {
            m_logger.debug("updating param username from " +_AutositeUser.getUsername() + "->" + request.getParameter("username"));
            _AutositeUser.setUsername(WebParamUtil.getStringValue(request.getParameter("username")));

        }
        if (!isMissing(request.getParameter("password"))) {
            m_logger.debug("updating param password from " +_AutositeUser.getPassword() + "->" + request.getParameter("password"));
            _AutositeUser.setPassword(WebParamUtil.getStringValue(request.getParameter("password")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_AutositeUser.getEmail() + "->" + request.getParameter("email"));
            _AutositeUser.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("userType"))) {
            m_logger.debug("updating param userType from " +_AutositeUser.getUserType() + "->" + request.getParameter("userType"));
            _AutositeUser.setUserType(WebParamUtil.getIntegerValue(request.getParameter("userType")));

        }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_AutositeUser.getFirstName() + "->" + request.getParameter("firstName"));
            _AutositeUser.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));

        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_AutositeUser.getLastName() + "->" + request.getParameter("lastName"));
            _AutositeUser.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));

        }
        if (!isMissing(request.getParameter("nickname"))) {
            m_logger.debug("updating param nickname from " +_AutositeUser.getNickname() + "->" + request.getParameter("nickname"));
            _AutositeUser.setNickname(WebParamUtil.getStringValue(request.getParameter("nickname")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeUser.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeUser.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_AutositeUser.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _AutositeUser.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _AutositeUser.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_AutositeUser.getDisabled() + "->" + request.getParameter("disabled"));
            _AutositeUser.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }
        if (!isMissing(request.getParameter("timeDisabled"))) {
            m_logger.debug("updating param timeDisabled from " +_AutositeUser.getTimeDisabled() + "->" + request.getParameter("timeDisabled"));
            _AutositeUser.setTimeDisabled(WebParamUtil.getTimestampValue(request.getParameter("timeDisabled")));

        }
        if (!isMissing(request.getParameter("confirmed"))) {
            m_logger.debug("updating param confirmed from " +_AutositeUser.getConfirmed() + "->" + request.getParameter("confirmed"));
            _AutositeUser.setConfirmed(WebParamUtil.getIntegerValue(request.getParameter("confirmed")));

        }
        if (!isMissing(request.getParameter("timeConfirmed"))) {
            m_logger.debug("updating param timeConfirmed from " +_AutositeUser.getTimeConfirmed() + "->" + request.getParameter("timeConfirmed"));
            _AutositeUser.setTimeConfirmed(WebParamUtil.getTimestampValue(request.getParameter("timeConfirmed")));

        }
        if (!isMissing(request.getParameter("pagelessSession"))) {
            m_logger.debug("updating param pagelessSession from " +_AutositeUser.getPagelessSession() + "->" + request.getParameter("pagelessSession"));
            _AutositeUser.setPagelessSession(WebParamUtil.getIntegerValue(request.getParameter("pagelessSession")));

        }
        if (!isMissing(request.getParameter("opt1"))) {
            m_logger.debug("updating param opt1 from " +_AutositeUser.getOpt1() + "->" + request.getParameter("opt1"));
            _AutositeUser.setOpt1(WebParamUtil.getIntegerValue(request.getParameter("opt1")));

        }
        if (!isMissing(request.getParameter("opt2"))) {
            m_logger.debug("updating param opt2 from " +_AutositeUser.getOpt2() + "->" + request.getParameter("opt2"));
            _AutositeUser.setOpt2(WebParamUtil.getStringValue(request.getParameter("opt2")));

        }

        m_actionExtent.beforeUpdate(request, response, _AutositeUser);
        m_ds.update(_AutositeUser);
        m_actionExtent.afterUpdate(request, response, _AutositeUser);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeUser _AutositeUser) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeUser _AutositeUser = m_ds.getById(cid);

        if (!isMissing(request.getParameter("username"))) {
            m_logger.debug("updating param username from " +_AutositeUser.getUsername() + "->" + request.getParameter("username"));
            _AutositeUser.setUsername(WebParamUtil.getStringValue(request.getParameter("username")));

        }
        if (!isMissing(request.getParameter("password"))) {
            m_logger.debug("updating param password from " +_AutositeUser.getPassword() + "->" + request.getParameter("password"));
            _AutositeUser.setPassword(WebParamUtil.getStringValue(request.getParameter("password")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_AutositeUser.getEmail() + "->" + request.getParameter("email"));
            _AutositeUser.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("userType"))) {
            m_logger.debug("updating param userType from " +_AutositeUser.getUserType() + "->" + request.getParameter("userType"));
            _AutositeUser.setUserType(WebParamUtil.getIntegerValue(request.getParameter("userType")));

        }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_AutositeUser.getFirstName() + "->" + request.getParameter("firstName"));
            _AutositeUser.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));

        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_AutositeUser.getLastName() + "->" + request.getParameter("lastName"));
            _AutositeUser.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));

        }
        if (!isMissing(request.getParameter("nickname"))) {
            m_logger.debug("updating param nickname from " +_AutositeUser.getNickname() + "->" + request.getParameter("nickname"));
            _AutositeUser.setNickname(WebParamUtil.getStringValue(request.getParameter("nickname")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeUser.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeUser.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_AutositeUser.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _AutositeUser.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _AutositeUser.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_AutositeUser.getDisabled() + "->" + request.getParameter("disabled"));
            _AutositeUser.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }
        if (!isMissing(request.getParameter("timeDisabled"))) {
            m_logger.debug("updating param timeDisabled from " +_AutositeUser.getTimeDisabled() + "->" + request.getParameter("timeDisabled"));
            _AutositeUser.setTimeDisabled(WebParamUtil.getTimestampValue(request.getParameter("timeDisabled")));

        }
        if (!isMissing(request.getParameter("confirmed"))) {
            m_logger.debug("updating param confirmed from " +_AutositeUser.getConfirmed() + "->" + request.getParameter("confirmed"));
            _AutositeUser.setConfirmed(WebParamUtil.getIntegerValue(request.getParameter("confirmed")));

        }
        if (!isMissing(request.getParameter("timeConfirmed"))) {
            m_logger.debug("updating param timeConfirmed from " +_AutositeUser.getTimeConfirmed() + "->" + request.getParameter("timeConfirmed"));
            _AutositeUser.setTimeConfirmed(WebParamUtil.getTimestampValue(request.getParameter("timeConfirmed")));

        }
        if (!isMissing(request.getParameter("pagelessSession"))) {
            m_logger.debug("updating param pagelessSession from " +_AutositeUser.getPagelessSession() + "->" + request.getParameter("pagelessSession"));
            _AutositeUser.setPagelessSession(WebParamUtil.getIntegerValue(request.getParameter("pagelessSession")));

        }
        if (!isMissing(request.getParameter("opt1"))) {
            m_logger.debug("updating param opt1 from " +_AutositeUser.getOpt1() + "->" + request.getParameter("opt1"));
            _AutositeUser.setOpt1(WebParamUtil.getIntegerValue(request.getParameter("opt1")));

        }
        if (!isMissing(request.getParameter("opt2"))) {
            m_logger.debug("updating param opt2 from " +_AutositeUser.getOpt2() + "->" + request.getParameter("opt2"));
            _AutositeUser.setOpt2(WebParamUtil.getStringValue(request.getParameter("opt2")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeUser _AutositeUser) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeUser _AutositeUser = m_ds.getById(cid);

        if (!isMissing(request.getParameter("username"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getUsername());
        }
        if (!isMissing(request.getParameter("password"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getPassword());
        }
        if (!isMissing(request.getParameter("email"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getEmail());
        }
        if (!isMissing(request.getParameter("userType"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getUserType());
        }
        if (!isMissing(request.getParameter("firstName"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getFirstName());
        }
        if (!isMissing(request.getParameter("lastName"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getLastName());
        }
        if (!isMissing(request.getParameter("nickname"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getNickname());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getTimeUpdated());
        }
        if (!isMissing(request.getParameter("disabled"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getDisabled());
        }
        if (!isMissing(request.getParameter("timeDisabled"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getTimeDisabled());
        }
        if (!isMissing(request.getParameter("confirmed"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getConfirmed());
        }
        if (!isMissing(request.getParameter("timeConfirmed"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getTimeConfirmed());
        }
        if (!isMissing(request.getParameter("pagelessSession"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getPagelessSession());
        }
        if (!isMissing(request.getParameter("opt1"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getOpt1());
        }
        if (!isMissing(request.getParameter("opt2"))) {
			return  JtStringUtil.valueOf(_AutositeUser.getOpt2());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeUser _AutositeUser) throws Exception {
    }

    protected String getFieldByName(String fieldName, AutositeUser _AutositeUser) {
        if (fieldName == null || fieldName.equals("")|| _AutositeUser == null) return null;
        
        if (fieldName.equals("username")) {
            return WebUtil.display(_AutositeUser.getUsername());
        }
        if (fieldName.equals("password")) {
            return WebUtil.display(_AutositeUser.getPassword());
        }
        if (fieldName.equals("email")) {
            return WebUtil.display(_AutositeUser.getEmail());
        }
        if (fieldName.equals("userType")) {
            return WebUtil.display(_AutositeUser.getUserType());
        }
        if (fieldName.equals("firstName")) {
            return WebUtil.display(_AutositeUser.getFirstName());
        }
        if (fieldName.equals("lastName")) {
            return WebUtil.display(_AutositeUser.getLastName());
        }
        if (fieldName.equals("nickname")) {
            return WebUtil.display(_AutositeUser.getNickname());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_AutositeUser.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_AutositeUser.getTimeUpdated());
        }
        if (fieldName.equals("disabled")) {
            return WebUtil.display(_AutositeUser.getDisabled());
        }
        if (fieldName.equals("timeDisabled")) {
            return WebUtil.display(_AutositeUser.getTimeDisabled());
        }
        if (fieldName.equals("confirmed")) {
            return WebUtil.display(_AutositeUser.getConfirmed());
        }
        if (fieldName.equals("timeConfirmed")) {
            return WebUtil.display(_AutositeUser.getTimeConfirmed());
        }
        if (fieldName.equals("pagelessSession")) {
            return WebUtil.display(_AutositeUser.getPagelessSession());
        }
        if (fieldName.equals("opt1")) {
            return WebUtil.display(_AutositeUser.getOpt1());
        }
        if (fieldName.equals("opt2")) {
            return WebUtil.display(_AutositeUser.getOpt2());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        AutositeUserForm _AutositeUserForm = (AutositeUserForm) form;

		if(requestParams.containsKey("username"))
			_AutositeUserForm.setUsername((String)requestParams.get("username"));
		if(requestParams.containsKey("password"))
			_AutositeUserForm.setPassword((String)requestParams.get("password"));
		if(requestParams.containsKey("email"))
			_AutositeUserForm.setEmail((String)requestParams.get("email"));
		if(requestParams.containsKey("userType"))
			_AutositeUserForm.setUserType((String)requestParams.get("userType"));
		if(requestParams.containsKey("firstName"))
			_AutositeUserForm.setFirstName((String)requestParams.get("firstName"));
		if(requestParams.containsKey("lastName"))
			_AutositeUserForm.setLastName((String)requestParams.get("lastName"));
		if(requestParams.containsKey("nickname"))
			_AutositeUserForm.setNickname((String)requestParams.get("nickname"));
		if(requestParams.containsKey("timeCreated"))
			_AutositeUserForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_AutositeUserForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
		if(requestParams.containsKey("disabled"))
			_AutositeUserForm.setDisabled((String)requestParams.get("disabled"));
		if(requestParams.containsKey("timeDisabled"))
			_AutositeUserForm.setTimeDisabled((String)requestParams.get("timeDisabled"));
		if(requestParams.containsKey("confirmed"))
			_AutositeUserForm.setConfirmed((String)requestParams.get("confirmed"));
		if(requestParams.containsKey("timeConfirmed"))
			_AutositeUserForm.setTimeConfirmed((String)requestParams.get("timeConfirmed"));
		if(requestParams.containsKey("pagelessSession"))
			_AutositeUserForm.setPagelessSession((String)requestParams.get("pagelessSession"));
		if(requestParams.containsKey("opt1"))
			_AutositeUserForm.setOpt1((String)requestParams.get("opt1"));
		if(requestParams.containsKey("opt2"))
			_AutositeUserForm.setOpt2((String)requestParams.get("opt2"));
    }


    protected boolean loginRequired() {
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "autosite_user_home=NULL,/jsp/form/autositeUser_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_user_list=NULL,/jsp/form/autositeUser_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_user_form=NULL,/jsp/form/autositeUser_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_user_ajax=NULL,/jsp/form/autositeUser_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "autosite_user_home=NULL,/jsp/form/autositeUser_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_user_list=NULL,/jsp/form/autositeUser_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_user_form=NULL,/jsp/form/autositeUser_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_user_ajax=NULL,/jsp/form/autositeUser_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected AutositeUserDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
