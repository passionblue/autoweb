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

import com.autosite.db.CleanerUser;
import com.autosite.ds.CleanerUserDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerUserForm;
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




public class CleanerUserAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerUserAction.class);

    public CleanerUserAction(){
        m_ds = CleanerUserDS.getInstance();
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

        CleanerUserForm _CleanerUserForm = (CleanerUserForm) form;
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


        CleanerUser _CleanerUser = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerUser = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerUser _CleanerUser = m_ds.getById(cid);

            if (_CleanerUser == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerUser.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerUser.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerUser);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerUserForm == null) {
    	            editField(request, response, _CleanerUser);
				} else {
    	            edit(request, response, _CleanerUserForm, _CleanerUser);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerUser);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerUser o = m_ds.getById( _CleanerUser.getId(), true);

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
            //CleanerUser _CleanerUser = m_ds.getById(cid);

            if (_CleanerUser == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerUser.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerUser.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerUser);
                if (returnObjects != null) returnObjects.put("target", _CleanerUser);
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
            //CleanerUser _CleanerUser = m_ds.getById(cid);

            if (_CleanerUser == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerUser.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerUser.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerUser);
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

            m_ds.delete(_CleanerUser); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerUser);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerUser);
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


            m_logger.info("Creating new CleanerUser" );
            CleanerUser _CleanerUserNew = new CleanerUser();   

            // Setting IDs for the object
            _CleanerUserNew.setSiteId(site.getId());
			
            if ( _CleanerUserForm == null) {
                setFields(request, response, _CleanerUserNew);
            } else {

            _CleanerUserNew.setAutositeUserId(WebParamUtil.getLongValue(_CleanerUserForm.getAutositeUserId()));
            m_logger.debug("setting AutositeUserId=" +_CleanerUserForm.getAutositeUserId());


            _CleanerUserNew.setInactivated(WebParamUtil.getIntegerValue(_CleanerUserForm.getInactivated()));
            m_logger.debug("setting Inactivated=" +_CleanerUserForm.getInactivated());


            _CleanerUserNew.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerUserForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerUserForm.getTimeCreated());

        	_CleanerUserNew.setTimeCreated(new TimeNow());

            _CleanerUserNew.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerUserForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_CleanerUserForm.getTimeUpdated());

        	_CleanerUserNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_CleanerUserNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerUserNew);
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
            
            if (_CleanerUserNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerUserNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerUser);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerUserNew);
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerUserForm _CleanerUserForm, CleanerUser _CleanerUser) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerUser _CleanerUser = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerUserDS.objectToString(_CleanerUser));

        _CleanerUser.setAutositeUserId(WebParamUtil.getLongValue(_CleanerUserForm.getAutositeUserId()));


        _CleanerUser.setInactivated(WebParamUtil.getIntegerValue(_CleanerUserForm.getInactivated()));




        _CleanerUser.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerUserForm.getTimeUpdated()));

        _CleanerUser.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _CleanerUser);
        m_ds.update(_CleanerUser);
        m_actionExtent.afterUpdate(request, response, _CleanerUser);
        m_logger.debug("After update " + CleanerUserDS.objectToString(_CleanerUser));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerUser _CleanerUser) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerUser _CleanerUser = m_ds.getById(cid);

        if (!isMissing(request.getParameter("autositeUserId"))) {
            m_logger.debug("updating param autositeUserId from " +_CleanerUser.getAutositeUserId() + "->" + request.getParameter("autositeUserId"));
            _CleanerUser.setAutositeUserId(WebParamUtil.getLongValue(request.getParameter("autositeUserId")));

        }
        if (!isMissing(request.getParameter("inactivated"))) {
            m_logger.debug("updating param inactivated from " +_CleanerUser.getInactivated() + "->" + request.getParameter("inactivated"));
            _CleanerUser.setInactivated(WebParamUtil.getIntegerValue(request.getParameter("inactivated")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerUser.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerUser.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerUser.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerUser.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerUser.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _CleanerUser);
        m_ds.update(_CleanerUser);
        m_actionExtent.afterUpdate(request, response, _CleanerUser);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerUser _CleanerUser) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerUser _CleanerUser = m_ds.getById(cid);

        if (!isMissing(request.getParameter("autositeUserId"))) {
            m_logger.debug("updating param autositeUserId from " +_CleanerUser.getAutositeUserId() + "->" + request.getParameter("autositeUserId"));
            _CleanerUser.setAutositeUserId(WebParamUtil.getLongValue(request.getParameter("autositeUserId")));

        }
        if (!isMissing(request.getParameter("inactivated"))) {
            m_logger.debug("updating param inactivated from " +_CleanerUser.getInactivated() + "->" + request.getParameter("inactivated"));
            _CleanerUser.setInactivated(WebParamUtil.getIntegerValue(request.getParameter("inactivated")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerUser.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerUser.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerUser.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerUser.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerUser.setTimeUpdated(new TimeNow());
        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerUser _CleanerUser) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerUser _CleanerUser = m_ds.getById(cid);

        if (!isMissing(request.getParameter("autositeUserId"))) {
			return  JtStringUtil.valueOf(_CleanerUser.getAutositeUserId());
        }
        if (!isMissing(request.getParameter("inactivated"))) {
			return  JtStringUtil.valueOf(_CleanerUser.getInactivated());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_CleanerUser.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_CleanerUser.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerUser _CleanerUser) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerUser _CleanerUser) {
        if (fieldName == null || fieldName.equals("")|| _CleanerUser == null) return null;
        
        if (fieldName.equals("autositeUserId")) {
            return WebUtil.display(_CleanerUser.getAutositeUserId());
        }
        if (fieldName.equals("inactivated")) {
            return WebUtil.display(_CleanerUser.getInactivated());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_CleanerUser.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_CleanerUser.getTimeUpdated());
        }
		return null;
    }


   
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerUserForm _CleanerUserForm = (CleanerUserForm) form;

		if(requestParams.containsKey("autositeUserId"))
			_CleanerUserForm.setAutositeUserId((String)requestParams.get("autositeUserId"));
		if(requestParams.containsKey("inactivated"))
			_CleanerUserForm.setInactivated((String)requestParams.get("inactivated"));
		if(requestParams.containsKey("timeCreated"))
			_CleanerUserForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_CleanerUserForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_user_home=NULL,/jsp/form_cleaner/cleanerUser_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_user_list=NULL,/jsp/form_cleaner/cleanerUser_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_user_form=NULL,/jsp/form_cleaner/cleanerUser_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_user_ajax=NULL,/jsp/form_cleaner/cleanerUser_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_user_home=NULL,/jsp/form_cleaner/cleanerUser_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_user_list=NULL,/jsp/form_cleaner/cleanerUser_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_user_form=NULL,/jsp/form_cleaner/cleanerUser_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_user_ajax=NULL,/jsp/form_cleaner/cleanerUser_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerUserDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
