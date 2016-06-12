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

import com.autosite.db.RegisterSimple;
import com.autosite.ds.RegisterSimpleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.RegisterSimpleForm;
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




public class RegisterSimpleAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(RegisterSimpleAction.class);

    public RegisterSimpleAction(){
        m_ds = RegisterSimpleDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        RegisterSimpleForm _RegisterSimpleForm = (RegisterSimpleForm) form;
        HttpSession session = request.getSession();
        
        DefaultPageForwardManager pageManager = DefaultPageForwardManager.getInstance(getSite(request).getSiteUrl());
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


        RegisterSimple _RegisterSimple = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _RegisterSimple = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //RegisterSimple _RegisterSimple = m_ds.getById(cid);

            if (_RegisterSimple == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_RegisterSimple.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _RegisterSimple.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_RegisterSimple);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _RegisterSimpleForm, _RegisterSimple);
                if (returnObjects != null) returnObjects.put("target", _RegisterSimple);
            }

            catch (Exception e) {
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
            //RegisterSimple _RegisterSimple = m_ds.getById(cid);

            if (_RegisterSimple == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_RegisterSimple.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _RegisterSimple.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _RegisterSimple);
                if (returnObjects != null) returnObjects.put("target", _RegisterSimple);
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
            
            System.out.println("XXXXXXXXXXXXXXXXX   " + pageManager.getServerId());
            
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //RegisterSimple _RegisterSimple = m_ds.getById(cid);

            if (_RegisterSimple == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_RegisterSimple.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _RegisterSimple.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _RegisterSimple);
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

            m_ds.delete(_RegisterSimple); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _RegisterSimple);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _RegisterSimple);
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

		        } else if (isActionCmdAdd(request)) {

		            System.out.println("XXXXXXXXXXXXXXXXX add  " + pageManager.getServerId());
        
		    if (!haveAccessToCommand(session,getActionCmd(request)) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 


            m_logger.info("Creating new RegisterSimple" );
            RegisterSimple _RegisterSimpleNew = new RegisterSimple();   

            // Setting IDs for the object
            _RegisterSimpleNew.setSiteId(site.getId());

            _RegisterSimpleNew.setFirstName(WebParamUtil.getStringValue(_RegisterSimpleForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_RegisterSimpleForm.getFirstName());
            _RegisterSimpleNew.setLastName(WebParamUtil.getStringValue(_RegisterSimpleForm.getLastName()));
            m_logger.debug("setting LastName=" +_RegisterSimpleForm.getLastName());
            _RegisterSimpleNew.setEmail(WebParamUtil.getStringValue(_RegisterSimpleForm.getEmail()));
            m_logger.debug("setting Email=" +_RegisterSimpleForm.getEmail());
            _RegisterSimpleNew.setUsername(WebParamUtil.getStringValue(_RegisterSimpleForm.getUsername()));
            m_logger.debug("setting Username=" +_RegisterSimpleForm.getUsername());
            _RegisterSimpleNew.setPassword(WebParamUtil.getStringValue(_RegisterSimpleForm.getPassword()));
            m_logger.debug("setting Password=" +_RegisterSimpleForm.getPassword());
            _RegisterSimpleNew.setUserType(WebParamUtil.getIntegerValue(_RegisterSimpleForm.getUserType()));
            m_logger.debug("setting UserType=" +_RegisterSimpleForm.getUserType());
            _RegisterSimpleNew.setBirthYear(WebParamUtil.getIntegerValue(_RegisterSimpleForm.getBirthYear()));
            m_logger.debug("setting BirthYear=" +_RegisterSimpleForm.getBirthYear());
            _RegisterSimpleNew.setBirthMonth(WebParamUtil.getIntegerValue(_RegisterSimpleForm.getBirthMonth()));
            m_logger.debug("setting BirthMonth=" +_RegisterSimpleForm.getBirthMonth());
            _RegisterSimpleNew.setBirthDay(WebParamUtil.getIntegerValue(_RegisterSimpleForm.getBirthDay()));
            m_logger.debug("setting BirthDay=" +_RegisterSimpleForm.getBirthDay());
            _RegisterSimpleNew.setTimeCreated(WebParamUtil.getTimestampValue(_RegisterSimpleForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_RegisterSimpleForm.getTimeCreated());
            _RegisterSimpleNew.setTimeUpdated(WebParamUtil.getTimestampValue(_RegisterSimpleForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_RegisterSimpleForm.getTimeUpdated());


            try {
                checkDepedenceIntegrity(_RegisterSimpleNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _RegisterSimpleNew);
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
            
            if (_RegisterSimpleNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_RegisterSimpleNew);
            if (returnObjects != null) returnObjects.put("target", _RegisterSimple);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _RegisterSimpleNew);
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, RegisterSimpleForm _RegisterSimpleForm, RegisterSimple _RegisterSimple) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        RegisterSimple _RegisterSimple = m_ds.getById(cid);

        m_logger.debug("Before update " + RegisterSimpleDS.objectToString(_RegisterSimple));

        _RegisterSimple.setFirstName(WebParamUtil.getStringValue(_RegisterSimpleForm.getFirstName()));
        _RegisterSimple.setLastName(WebParamUtil.getStringValue(_RegisterSimpleForm.getLastName()));
        _RegisterSimple.setEmail(WebParamUtil.getStringValue(_RegisterSimpleForm.getEmail()));
        _RegisterSimple.setUsername(WebParamUtil.getStringValue(_RegisterSimpleForm.getUsername()));
        _RegisterSimple.setPassword(WebParamUtil.getStringValue(_RegisterSimpleForm.getPassword()));
        _RegisterSimple.setBirthYear(WebParamUtil.getIntegerValue(_RegisterSimpleForm.getBirthYear()));
        _RegisterSimple.setBirthMonth(WebParamUtil.getIntegerValue(_RegisterSimpleForm.getBirthMonth()));
        _RegisterSimple.setBirthDay(WebParamUtil.getIntegerValue(_RegisterSimpleForm.getBirthDay()));
        _RegisterSimple.setTimeUpdated(WebParamUtil.getTimestampValue(_RegisterSimpleForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _RegisterSimple);
        m_ds.update(_RegisterSimple);
        m_actionExtent.afterUpdate(request, response, _RegisterSimple);
        m_logger.debug("After update " + RegisterSimpleDS.objectToString(_RegisterSimple));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, RegisterSimple _RegisterSimple) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        RegisterSimple _RegisterSimple = m_ds.getById(cid);

        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_RegisterSimple.getFirstName() + "->" + request.getParameter("firstName"));
            _RegisterSimple.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));

        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_RegisterSimple.getLastName() + "->" + request.getParameter("lastName"));
            _RegisterSimple.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_RegisterSimple.getEmail() + "->" + request.getParameter("email"));
            _RegisterSimple.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("username"))) {
            m_logger.debug("updating param username from " +_RegisterSimple.getUsername() + "->" + request.getParameter("username"));
            _RegisterSimple.setUsername(WebParamUtil.getStringValue(request.getParameter("username")));

        }
        if (!isMissing(request.getParameter("password"))) {
            m_logger.debug("updating param password from " +_RegisterSimple.getPassword() + "->" + request.getParameter("password"));
            _RegisterSimple.setPassword(WebParamUtil.getStringValue(request.getParameter("password")));

        }
        if (!isMissing(request.getParameter("birthYear"))) {
            m_logger.debug("updating param birthYear from " +_RegisterSimple.getBirthYear() + "->" + request.getParameter("birthYear"));
            _RegisterSimple.setBirthYear(WebParamUtil.getIntegerValue(request.getParameter("birthYear")));

        }
        if (!isMissing(request.getParameter("birthMonth"))) {
            m_logger.debug("updating param birthMonth from " +_RegisterSimple.getBirthMonth() + "->" + request.getParameter("birthMonth"));
            _RegisterSimple.setBirthMonth(WebParamUtil.getIntegerValue(request.getParameter("birthMonth")));

        }
        if (!isMissing(request.getParameter("birthDay"))) {
            m_logger.debug("updating param birthDay from " +_RegisterSimple.getBirthDay() + "->" + request.getParameter("birthDay"));
            _RegisterSimple.setBirthDay(WebParamUtil.getIntegerValue(request.getParameter("birthDay")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_RegisterSimple.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _RegisterSimple.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_RegisterSimple.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _RegisterSimple.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        }

        m_actionExtent.beforeUpdate(request, response, _RegisterSimple);
        m_ds.update(_RegisterSimple);
        m_actionExtent.afterUpdate(request, response, _RegisterSimple);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, RegisterSimple _RegisterSimple) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        RegisterSimple _RegisterSimple = m_ds.getById(cid);

        if (!isMissing(request.getParameter("firstName"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getFirstName());
        }
        if (!isMissing(request.getParameter("lastName"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getLastName());
        }
        if (!isMissing(request.getParameter("email"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getEmail());
        }
        if (!isMissing(request.getParameter("username"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getUsername());
        }
        if (!isMissing(request.getParameter("password"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getPassword());
        }
        if (!isMissing(request.getParameter("birthYear"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getBirthYear());
        }
        if (!isMissing(request.getParameter("birthMonth"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getBirthMonth());
        }
        if (!isMissing(request.getParameter("birthDay"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getBirthDay());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_RegisterSimple.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(RegisterSimple _RegisterSimple) throws Exception {
    }

    protected String getFieldByName(String fieldName, RegisterSimple _RegisterSimple) {
        if (fieldName == null || fieldName.equals("")|| _RegisterSimple == null) return null;
        
        if (fieldName.equals("firstName")) {
            return WebUtil.display(_RegisterSimple.getFirstName());
        }
        if (fieldName.equals("lastName")) {
            return WebUtil.display(_RegisterSimple.getLastName());
        }
        if (fieldName.equals("email")) {
            return WebUtil.display(_RegisterSimple.getEmail());
        }
        if (fieldName.equals("username")) {
            return WebUtil.display(_RegisterSimple.getUsername());
        }
        if (fieldName.equals("password")) {
            return WebUtil.display(_RegisterSimple.getPassword());
        }
        if (fieldName.equals("birthYear")) {
            return WebUtil.display(_RegisterSimple.getBirthYear());
        }
        if (fieldName.equals("birthMonth")) {
            return WebUtil.display(_RegisterSimple.getBirthMonth());
        }
        if (fieldName.equals("birthDay")) {
            return WebUtil.display(_RegisterSimple.getBirthDay());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_RegisterSimple.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_RegisterSimple.getTimeUpdated());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        RegisterSimple target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (RegisterSimple) working.get("target");
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        // ================================================================================
        // Response Processing 
        // ================================================================================

        if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // If there is no error, following message will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") )
                ret.put("__value", "Successfully deleted.");
            if (hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") )
                ret.put("__value", "Successfully created.");
            if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef"))     
                ret.put("__value", "Successfully changed.");
            if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") )
                ret.put("__value", "Successfully changed.");
            else 
                ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getcode")){
            // If there is no error, nothing will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getstatus arg = " + request.getParameter("ajaxOutArg"));

        } else if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            RegisterSimple _RegisterSimple = RegisterSimpleDS.getInstance().getById(id);
            if (_RegisterSimple == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _RegisterSimple);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            RegisterSimple _RegisterSimple = RegisterSimpleDS.getInstance().getById(id);
            if ( _RegisterSimple == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _RegisterSimple);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();

            buf.append("<div id=\"registerSimple-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                RegisterSimple _RegisterSimple = (RegisterSimple) iterator.next();

                buf.append("<div id=\"registerSimple-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
                    buf.append("<div id=\"registerSimple-ajax-firstName\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getFirstName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
                    buf.append("<div id=\"registerSimple-ajax-lastName\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getLastName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("email")) {
                    buf.append("<div id=\"registerSimple-ajax-email\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getEmail()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("username")) {
                    buf.append("<div id=\"registerSimple-ajax-username\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getUsername()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("password")) {
                    buf.append("<div id=\"registerSimple-ajax-password\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getPassword()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
                    buf.append("<div id=\"registerSimple-ajax-birthYear\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getBirthYear()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
                    buf.append("<div id=\"registerSimple-ajax-birthMonth\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getBirthMonth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
                    buf.append("<div id=\"registerSimple-ajax-birthDay\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getBirthDay()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"registerSimple-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"registerSimple-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getTimeUpdated()));
                    buf.append("</div>");

				}
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("First Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Last Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("email")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Email");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("username")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Username");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("password")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Password");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Birth Year");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Birth Month");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Birth Day");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Updated");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                RegisterSimple _RegisterSimple = (RegisterSimple) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getFirstName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getLastName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("email")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getEmail()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("username")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getUsername()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("password")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getPassword()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getBirthYear()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getBirthMonth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getBirthDay()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getTimeUpdated()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                RegisterSimple _RegisterSimple = (RegisterSimple) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("firstName")) 
			            json.put("firstName", ""+_RegisterSimple.getFirstName());
		            if ( ignoreFieldSet || fieldSet.contains("lastName")) 
			            json.put("lastName", ""+_RegisterSimple.getLastName());
		            if ( ignoreFieldSet || fieldSet.contains("email")) 
			            json.put("email", ""+_RegisterSimple.getEmail());
		            if ( ignoreFieldSet || fieldSet.contains("username")) 
			            json.put("username", ""+_RegisterSimple.getUsername());
		            if ( ignoreFieldSet || fieldSet.contains("password")) 
			            json.put("password", ""+_RegisterSimple.getPassword());
		            if ( ignoreFieldSet || fieldSet.contains("birthYear")) 
			            json.put("birthYear", ""+_RegisterSimple.getBirthYear());
		            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) 
			            json.put("birthMonth", ""+_RegisterSimple.getBirthMonth());
		            if ( ignoreFieldSet || fieldSet.contains("birthDay")) 
			            json.put("birthDay", ""+_RegisterSimple.getBirthDay());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                RegisterSimple _RegisterSimple = list.size() >=1?(RegisterSimple) list.get(0): null; 

				if ( _RegisterSimple != null) {
		            top.put("id", ""+_RegisterSimple.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonFirstName = new JSONObject();
		            jsonFirstName.put("name", "firstName");
		            jsonFirstName.put("value", ""+_RegisterSimple.getFirstName());
		            array.put(jsonFirstName);
		            JSONObject jsonLastName = new JSONObject();
		            jsonLastName.put("name", "lastName");
		            jsonLastName.put("value", ""+_RegisterSimple.getLastName());
		            array.put(jsonLastName);
		            JSONObject jsonEmail = new JSONObject();
		            jsonEmail.put("name", "email");
		            jsonEmail.put("value", ""+_RegisterSimple.getEmail());
		            array.put(jsonEmail);
		            JSONObject jsonUsername = new JSONObject();
		            jsonUsername.put("name", "username");
		            jsonUsername.put("value", ""+_RegisterSimple.getUsername());
		            array.put(jsonUsername);
		            JSONObject jsonPassword = new JSONObject();
		            jsonPassword.put("name", "password");
		            jsonPassword.put("value", ""+_RegisterSimple.getPassword());
		            array.put(jsonPassword);
		            JSONObject jsonBirthYear = new JSONObject();
		            jsonBirthYear.put("name", "birthYear");
		            jsonBirthYear.put("value", ""+_RegisterSimple.getBirthYear());
		            array.put(jsonBirthYear);
		            JSONObject jsonBirthMonth = new JSONObject();
		            jsonBirthMonth.put("name", "birthMonth");
		            jsonBirthMonth.put("value", ""+_RegisterSimple.getBirthMonth());
		            array.put(jsonBirthMonth);
		            JSONObject jsonBirthDay = new JSONObject();
		            jsonBirthDay.put("name", "birthDay");
		            jsonBirthDay.put("value", ""+_RegisterSimple.getBirthDay());
		            array.put(jsonBirthDay);

	    	        top.put("fields", array);
				}
			}

            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));

			// Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuilder buf = new StringBuilder();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);


            buf.append("<script type=\"text/javascript\">");
            //buf.append("<!--");
            buf.append("function sendForm_"+ randNum + "(){");
            buf.append("sendFormAjax('/registerSimpleAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/registerSimpleAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
                String value = WebUtil.display(request.getParameter("firstName"));

                if ( forceHiddenSet.contains("firstName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"firstName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">First Name</div>");
            buf.append("<INPUT NAME=\"firstName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
                String value = WebUtil.display(request.getParameter("lastName"));

                if ( forceHiddenSet.contains("lastName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"lastName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Last Name</div>");
            buf.append("<INPUT NAME=\"lastName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("email")) {
                String value = WebUtil.display(request.getParameter("email"));

                if ( forceHiddenSet.contains("email")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"email\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Email</div>");
            buf.append("<INPUT NAME=\"email\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("username")) {
                String value = WebUtil.display(request.getParameter("username"));

                if ( forceHiddenSet.contains("username")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"username\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Username</div>");
            buf.append("<INPUT NAME=\"username\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("password")) {
                String value = WebUtil.display(request.getParameter("password"));

                if ( forceHiddenSet.contains("password")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"password\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Password</div>");
            buf.append("<INPUT NAME=\"password\" type=\"password\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
                String value = WebUtil.display(request.getParameter("birthYear"));

                if ( forceHiddenSet.contains("birthYear")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthYear\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthYear\"  value=\""+value+"\">");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
                String value = WebUtil.display(request.getParameter("birthMonth"));

                if ( forceHiddenSet.contains("birthMonth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthMonth\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthMonth\"  value=\""+value+"\">");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
                String value = WebUtil.display(request.getParameter("birthDay"));

                if ( forceHiddenSet.contains("birthDay")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthDay\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthDay\"  value=\""+value+"\">");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                String value = WebUtil.display(request.getParameter("timeCreated"));

                if ( forceHiddenSet.contains("timeCreated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCreated\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                String value = WebUtil.display(request.getParameter("timeUpdated"));

                if ( forceHiddenSet.contains("timeUpdated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeUpdated\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult"+randNum+"\"></span>");
            buf.append("<a id=\"ajaxSubmit"+randNum+"\" href=\"javascript:sendForm_"+ randNum + "();\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2") || (hasRequestValue(request, "ajaxOut", "getscriptform"))){

			//This form is called by script such as e.g. <script type="text/javascript" src="/blockListAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>
			// inline_script will be attached to provide functionlities. 
			// This form will be used inside the same site to provide embedded page using <script> tags. But Refer to Poll "inline_script_poll" to 
			// send no-ajax submission. General no-ajax submission is not yet supported. 

            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
	        StringBuilder buf = new StringBuilder();
			Site site = SiteDS.getInstance().registerSite(request.getServerName());

            String importedScripts = null;
            try {
                importedScripts = FileUtil.loadCodesToString("./inline_script.txt");
                m_logger.debug(importedScripts);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                importedScripts = "";
            }

            importedScripts += "\n";
            importedScripts += "function responseCallback_registerSimple(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayRegisterSimple\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_registerSimple(){\n";
            importedScripts +=     "xmlhttpPostXX('registerSimpleFormAddDis','/registerSimpleAction.html', 'resultDisplayRegisterSimple', '${ajax_response_fields}', responseCallback_registerSimple);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_registerSimple(){\n";
            importedScripts +=     "clearFormXX('registerSimpleFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_registerSimple(){\n";
            importedScripts +=     "backToXX('registerSimpleFormAddDis','resultDisplayRegisterSimple');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"registerSimpleFormAddDis\" method=\"POST\" action=\"/registerSimpleAction.html\" id=\"registerSimpleFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> First Name</div>");
        buf.append("<input class=\"field\" id=\"firstName\" type=\"text\" size=\"70\" name=\"firstName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Last Name</div>");
        buf.append("<input class=\"field\" id=\"lastName\" type=\"text\" size=\"70\" name=\"lastName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Email</div>");
        buf.append("<input class=\"field\" id=\"email\" type=\"text\" size=\"70\" name=\"email\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Username</div>");
        buf.append("<input class=\"field\" id=\"username\" type=\"text\" size=\"70\" name=\"username\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Password</div>");
        buf.append("<input class=\"field\" id=\"password\" type=\"password\" size=\"70\" name=\"password\" value=\"\"/><span></span>");
		buf.append("<br/>");
    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"birthYear\" NAME=\"birthYear\" value=\"\" />");
		buf.append("<br/>");
    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"birthMonth\" NAME=\"birthMonth\" value=\"\" />");
		buf.append("<br/>");
    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"birthDay\" NAME=\"birthDay\" value=\"\" />");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_registerSimple()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_registerSimple()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayRegisterSimple\"></span>");
			buf.append("<a href=\"javascript:showform_registerSimple()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        return ret;
    }


	// Prepares data to be returned in ajax.
    protected List prepareReturnData(HttpServletRequest request, RegisterSimple target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            RegisterSimple _RegisterSimple = null; 
            List list = RegisterSimpleDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _RegisterSimple = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _RegisterSimple = (RegisterSimple) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _RegisterSimple = (RegisterSimple) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _RegisterSimple = RegisterSimpleDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_RegisterSimple);

        } else {
            
            List list = RegisterSimpleDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


    protected boolean loginRequired() {
        return true;
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

    public String getActionGroupName(){ return "AutositeApp";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
    protected RegisterSimpleDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
