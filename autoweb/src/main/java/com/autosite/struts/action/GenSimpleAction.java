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

import com.autosite.db.GenSimple;
import com.autosite.ds.GenSimpleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.GenSimpleForm;
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


import com.autosite.holder.GenSimpleDataHolder;


public class GenSimpleAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(GenSimpleAction.class);

    public GenSimpleAction(){
        m_ds = GenSimpleDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        GenSimpleForm _GenSimpleForm = (GenSimpleForm) form;
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
            hasRequestValue(request, "add", "true")||
            hasRequestValue(request, "act", "add")||
            hasRequestValue(request, "move", "true")||
            hasRequestValue(request, "act", "move")||
            hasRequestValue(request, "edit", "true")||
            hasRequestValue(request, "act", "register")||
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


        GenSimpleDataHolder _GenSimple = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _GenSimple = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //GenSimple _GenSimple = m_ds.getById(cid);

            if (_GenSimple == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_GenSimple.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenSimple.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "TESTGROUP", "GenSimple");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_GenSimple);
            } else {
                storable.setDataHolder(_GenSimple);
            }
            updateSessionStorable(session, "TESTGROUP", "GenSimple",storable);

            try {
                checkDepedenceIntegrity(_GenSimple);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _GenSimpleForm, _GenSimple);
                if (returnObjects != null) returnObjects.put("target", _GenSimple);
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
            //GenSimple _GenSimple = m_ds.getById(cid);

            if (_GenSimple == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenSimple.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenSimple.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "TESTGROUP", "GenSimple");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_GenSimple);
            } else {
                storable.setDataHolder(_GenSimple);
            }
            updateSessionStorable(session, "TESTGROUP", "GenSimple",storable);

            try{
                editField(request, response, _GenSimple);
                if (returnObjects != null) returnObjects.put("target", _GenSimple);
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
            //GenSimple _GenSimple = m_ds.getById(cid);

            if (_GenSimple == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenSimple.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenSimple.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            removeSessionStorable(session, "TESTGROUP", "GenSimple");


            try {
                m_actionExtent.beforeDelete(request, response, _GenSimple);
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

            m_ds.delete(_GenSimple); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _GenSimple);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _GenSimple);
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


            m_logger.info("Creating new GenSimple" );
            GenSimpleDataHolder _GenSimpleNew = new GenSimpleDataHolder();   

            // Setting IDs for the object
            _GenSimpleNew.setSiteId(site.getId());

            _GenSimpleNew.setData(WebParamUtil.getStringValue(_GenSimpleForm.getData()));
            m_logger.debug("setting Data=" +_GenSimpleForm.getData());
            _GenSimpleNew.setActive(WebParamUtil.getIntegerValue(_GenSimpleForm.getActive()));
            m_logger.debug("setting Active=" +_GenSimpleForm.getActive());
            _GenSimpleNew.setExtString(WebParamUtil.getStringValue(_GenSimpleForm.getExtString()));
            m_logger.debug("setting ExtString=" +_GenSimpleForm.getExtString());
            _GenSimpleNew.setExtInt(WebParamUtil.getIntegerValue(_GenSimpleForm.getExtInt()));
            m_logger.debug("setting ExtInt=" +_GenSimpleForm.getExtInt());

            //Handle SessionStorable
            SessionStorableDataHolderWrapper newHolderWrapper = new SessionStorableDataHolderWrapper(_GenSimpleNew);
            addSessionStorable(session, "TESTGROUP", "GenSimple", newHolderWrapper);

            try {
                checkDepedenceIntegrity(_GenSimpleNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _GenSimpleNew);
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
            
            if (_GenSimpleNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_GenSimpleNew);
            if (returnObjects != null) returnObjects.put("target", _GenSimple);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _GenSimpleNew);
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
            

        } else if (hasRequestValue(request, "move", "true")) {
            if (!haveAccessToCommand(session, "move" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "move");
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "move", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "move", "error"));
                return mapping.findForward("default");
            }

        } else if (hasRequestValue(request, "cancel", "true")) {
            if (!haveAccessToCommand(session, "cancel" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "cancel");
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "cancel", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "cancel", "error"));
                return mapping.findForward("default");
            }

        } else if (hasRequestValue(request, "register", "true")) {
            if (!haveAccessToCommand(session, "register" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "register");
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "register", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "register", "error"));
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, GenSimpleForm _GenSimpleForm, GenSimpleDataHolder _GenSimple) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        GenSimple _GenSimple = m_ds.getById(cid);

        m_logger.debug("Before update " + GenSimpleDS.objectToString(_GenSimple));

        _GenSimple.setData(WebParamUtil.getStringValue(_GenSimpleForm.getData()));
        _GenSimple.setActive(WebParamUtil.getIntegerValue(_GenSimpleForm.getActive()));
        _GenSimple.setExtString(WebParamUtil.getStringValue(_GenSimpleForm.getExtString()));
        _GenSimple.setExtInt(WebParamUtil.getIntegerValue(_GenSimpleForm.getExtInt()));

        m_actionExtent.beforeUpdate(request, response, _GenSimple);
        m_ds.update(_GenSimple);
        m_actionExtent.afterUpdate(request, response, _GenSimple);
        m_logger.debug("After update " + GenSimpleDS.objectToString(_GenSimple));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, GenSimpleDataHolder _GenSimple) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        GenSimple _GenSimple = m_ds.getById(cid);

        if (!isMissing(request.getParameter("data"))) {
            m_logger.debug("updating param data from " +_GenSimple.getData() + "->" + request.getParameter("data"));
            _GenSimple.setData(WebParamUtil.getStringValue(request.getParameter("data")));

        }
        if (!isMissing(request.getParameter("active"))) {
            m_logger.debug("updating param active from " +_GenSimple.getActive() + "->" + request.getParameter("active"));
            _GenSimple.setActive(WebParamUtil.getIntegerValue(request.getParameter("active")));

        }
        if (!isMissing(request.getParameter("extString"))) {
            m_logger.debug("updating param extString from " +_GenSimple.getExtString() + "->" + request.getParameter("extString"));
            _GenSimple.setExtString(WebParamUtil.getStringValue(request.getParameter("extString")));

        }
        if (!isMissing(request.getParameter("extInt"))) {
            m_logger.debug("updating param extInt from " +_GenSimple.getExtInt() + "->" + request.getParameter("extInt"));
            _GenSimple.setExtInt(WebParamUtil.getIntegerValue(request.getParameter("extInt")));

        }

        m_actionExtent.beforeUpdate(request, response, _GenSimple);
        m_ds.update(_GenSimple);
        m_actionExtent.afterUpdate(request, response, _GenSimple);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, GenSimpleDataHolder _GenSimple) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        GenSimple _GenSimple = m_ds.getById(cid);

        if (!isMissing(request.getParameter("data"))) {
			return  JtStringUtil.valueOf(_GenSimple.getData());
        }
        if (!isMissing(request.getParameter("active"))) {
			return  JtStringUtil.valueOf(_GenSimple.getActive());
        }
        if (!isMissing(request.getParameter("extString"))) {
			return  JtStringUtil.valueOf(_GenSimple.getExtString());
        }
        if (!isMissing(request.getParameter("extInt"))) {
			return  JtStringUtil.valueOf(_GenSimple.getExtInt());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(GenSimpleDataHolder _GenSimple) throws Exception {
    }

    protected String getFieldByName(String fieldName, GenSimpleDataHolder _GenSimple) {
        if (fieldName == null || fieldName.equals("")|| _GenSimple == null) return null;
        
        if (fieldName.equals("data")) {
            return WebUtil.display(_GenSimple.getData());
        }
        if (fieldName.equals("active")) {
            return WebUtil.display(_GenSimple.getActive());
        }
        if (fieldName.equals("extString")) {
            return WebUtil.display(_GenSimple.getExtString());
        }
        if (fieldName.equals("extInt")) {
            return WebUtil.display(_GenSimple.getExtInt());
        }
		return null;
    }

    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        GenSimpleForm _GenSimpleForm = (GenSimpleForm) form;

		if(requestParams.containsKey("data"))
			_GenSimpleForm.setData((String)requestParams.get("data"));
		if(requestParams.containsKey("active"))
			_GenSimpleForm.setActive((String)requestParams.get("active"));
		if(requestParams.containsKey("extString"))
			_GenSimpleForm.setExtString((String)requestParams.get("extString"));
		if(requestParams.containsKey("extInt"))
			_GenSimpleForm.setExtInt((String)requestParams.get("extInt"));
    }


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

    public String getActionGroupName(){ return "Test2";} 

    protected String getSessionStorableGroup() {
        return "TESTGROUP";
    }

	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
    protected GenSimpleDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
