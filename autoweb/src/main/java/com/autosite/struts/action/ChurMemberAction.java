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

import com.autosite.db.ChurMember;
import com.autosite.ds.ChurMemberDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ChurMemberForm;
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




public class ChurMemberAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(ChurMemberAction.class);

    public ChurMemberAction(){
        m_ds = ChurMemberDS.getInstance();
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

        ChurMemberForm _ChurMemberForm = (ChurMemberForm) form;
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
        ChurMember _ChurMember = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ChurMember = m_ds.getById(cid);
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
            //ChurMember _ChurMember = m_ds.getById(cid);

            if (_ChurMember == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_ChurMember.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurMember.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_ChurMember);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _ChurMemberForm == null) {
    	            editField(request, response, _ChurMember);
				} else {
    	            edit(request, response, _ChurMemberForm, _ChurMember);
				}
                if (returnObjects != null) returnObjects.put("target", _ChurMember);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                ChurMember o = m_ds.getById( _ChurMember.getId(), true);

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
            //ChurMember _ChurMember = m_ds.getById(cid);

            if (_ChurMember == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ChurMember.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurMember.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _ChurMember);
                if (returnObjects != null) returnObjects.put("target", _ChurMember);
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
            //ChurMember _ChurMember = m_ds.getById(cid);

            if (_ChurMember == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ChurMember.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurMember.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _ChurMember);
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

            m_ds.delete(_ChurMember); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _ChurMember);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _ChurMember);
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

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
	            if (throwException) throw e;
                return mapping.findForward("default");
            }

            m_logger.info("Creating new ChurMember" );
            ChurMember _ChurMemberNew = new ChurMember();   

            // Setting IDs for the object
            _ChurMemberNew.setSiteId(site.getId());
			
            if ( _ChurMemberForm == null) {
                setFields(request, response, _ChurMemberNew);
            } else {

            _ChurMemberNew.setFullName(WebParamUtil.getStringValue(_ChurMemberForm.getFullName()));
            m_logger.debug("setting FullName=" +_ChurMemberForm.getFullName());


            _ChurMemberNew.setFirstName(WebParamUtil.getStringValue(_ChurMemberForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_ChurMemberForm.getFirstName());


            _ChurMemberNew.setLastName(WebParamUtil.getStringValue(_ChurMemberForm.getLastName()));
            m_logger.debug("setting LastName=" +_ChurMemberForm.getLastName());


            _ChurMemberNew.setTitle(WebParamUtil.getStringValue(_ChurMemberForm.getTitle()));
            m_logger.debug("setting Title=" +_ChurMemberForm.getTitle());


            _ChurMemberNew.setOtherName(WebParamUtil.getStringValue(_ChurMemberForm.getOtherName()));
            m_logger.debug("setting OtherName=" +_ChurMemberForm.getOtherName());


            _ChurMemberNew.setHousehold(WebParamUtil.getIntegerValue(_ChurMemberForm.getHousehold()));
            m_logger.debug("setting Household=" +_ChurMemberForm.getHousehold());


            _ChurMemberNew.setHouseholdId(WebParamUtil.getLongValue(_ChurMemberForm.getHouseholdId()));
            m_logger.debug("setting HouseholdId=" +_ChurMemberForm.getHouseholdId());


            _ChurMemberNew.setIsGroup(WebParamUtil.getIntegerValue(_ChurMemberForm.getIsGroup()));
            m_logger.debug("setting IsGroup=" +_ChurMemberForm.getIsGroup());


            _ChurMemberNew.setIsGuest(WebParamUtil.getIntegerValue(_ChurMemberForm.getIsGuest()));
            m_logger.debug("setting IsGuest=" +_ChurMemberForm.getIsGuest());


            _ChurMemberNew.setIsSpeaker(WebParamUtil.getIntegerValue(_ChurMemberForm.getIsSpeaker()));
            m_logger.debug("setting IsSpeaker=" +_ChurMemberForm.getIsSpeaker());


            _ChurMemberNew.setTimeCreated(WebParamUtil.getTimestampValue(_ChurMemberForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ChurMemberForm.getTimeCreated());

        	_ChurMemberNew.setTimeCreated(new TimeNow());

            _ChurMemberNew.setListIndex(WebParamUtil.getIntegerValue(_ChurMemberForm.getListIndex()));
            m_logger.debug("setting ListIndex=" +_ChurMemberForm.getListIndex());


			}

            try {
                checkDepedenceIntegrity(_ChurMemberNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ChurMemberNew);
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
            
            if (_ChurMemberNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ChurMemberNew);
            if (returnObjects != null) returnObjects.put("target", _ChurMemberNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ChurMemberNew);
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
             _ChurMember =  _ChurMemberNew;
            
            webProc.complete();

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }





        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ChurMemberForm _ChurMemberForm, ChurMember _ChurMember) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurMember _ChurMember = m_ds.getById(cid);

        m_logger.debug("Before update " + ChurMemberDS.objectToString(_ChurMember));

        _ChurMember.setFullName(WebParamUtil.getStringValue(_ChurMemberForm.getFullName()));


        _ChurMember.setFirstName(WebParamUtil.getStringValue(_ChurMemberForm.getFirstName()));


        _ChurMember.setLastName(WebParamUtil.getStringValue(_ChurMemberForm.getLastName()));


        _ChurMember.setTitle(WebParamUtil.getStringValue(_ChurMemberForm.getTitle()));


        _ChurMember.setOtherName(WebParamUtil.getStringValue(_ChurMemberForm.getOtherName()));


        _ChurMember.setHousehold(WebParamUtil.getIntegerValue(_ChurMemberForm.getHousehold()));


        _ChurMember.setHouseholdId(WebParamUtil.getLongValue(_ChurMemberForm.getHouseholdId()));


        _ChurMember.setIsGroup(WebParamUtil.getIntegerValue(_ChurMemberForm.getIsGroup()));


        _ChurMember.setIsGuest(WebParamUtil.getIntegerValue(_ChurMemberForm.getIsGuest()));


        _ChurMember.setIsSpeaker(WebParamUtil.getIntegerValue(_ChurMemberForm.getIsSpeaker()));




        _ChurMember.setListIndex(WebParamUtil.getIntegerValue(_ChurMemberForm.getListIndex()));



        m_actionExtent.beforeUpdate(request, response, _ChurMember);
        m_ds.update(_ChurMember);
        m_actionExtent.afterUpdate(request, response, _ChurMember);
        m_logger.debug("After update " + ChurMemberDS.objectToString(_ChurMember));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ChurMember _ChurMember) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurMember _ChurMember = m_ds.getById(cid);

        if (!isMissing(request.getParameter("fullName"))) {
            m_logger.debug("updating param fullName from " +_ChurMember.getFullName() + "->" + request.getParameter("fullName"));
            _ChurMember.setFullName(WebParamUtil.getStringValue(request.getParameter("fullName")));

        }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_ChurMember.getFirstName() + "->" + request.getParameter("firstName"));
            _ChurMember.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));

        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_ChurMember.getLastName() + "->" + request.getParameter("lastName"));
            _ChurMember.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));

        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_ChurMember.getTitle() + "->" + request.getParameter("title"));
            _ChurMember.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("otherName"))) {
            m_logger.debug("updating param otherName from " +_ChurMember.getOtherName() + "->" + request.getParameter("otherName"));
            _ChurMember.setOtherName(WebParamUtil.getStringValue(request.getParameter("otherName")));

        }
        if (!isMissing(request.getParameter("household"))) {
            m_logger.debug("updating param household from " +_ChurMember.getHousehold() + "->" + request.getParameter("household"));
            _ChurMember.setHousehold(WebParamUtil.getIntegerValue(request.getParameter("household")));

        }
        if (!isMissing(request.getParameter("householdId"))) {
            m_logger.debug("updating param householdId from " +_ChurMember.getHouseholdId() + "->" + request.getParameter("householdId"));
            _ChurMember.setHouseholdId(WebParamUtil.getLongValue(request.getParameter("householdId")));

        }
        if (!isMissing(request.getParameter("isGroup"))) {
            m_logger.debug("updating param isGroup from " +_ChurMember.getIsGroup() + "->" + request.getParameter("isGroup"));
            _ChurMember.setIsGroup(WebParamUtil.getIntegerValue(request.getParameter("isGroup")));

        }
        if (!isMissing(request.getParameter("isGuest"))) {
            m_logger.debug("updating param isGuest from " +_ChurMember.getIsGuest() + "->" + request.getParameter("isGuest"));
            _ChurMember.setIsGuest(WebParamUtil.getIntegerValue(request.getParameter("isGuest")));

        }
        if (!isMissing(request.getParameter("isSpeaker"))) {
            m_logger.debug("updating param isSpeaker from " +_ChurMember.getIsSpeaker() + "->" + request.getParameter("isSpeaker"));
            _ChurMember.setIsSpeaker(WebParamUtil.getIntegerValue(request.getParameter("isSpeaker")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ChurMember.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ChurMember.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("listIndex"))) {
            m_logger.debug("updating param listIndex from " +_ChurMember.getListIndex() + "->" + request.getParameter("listIndex"));
            _ChurMember.setListIndex(WebParamUtil.getIntegerValue(request.getParameter("listIndex")));

        }

        m_actionExtent.beforeUpdate(request, response, _ChurMember);
        m_ds.update(_ChurMember);
        m_actionExtent.afterUpdate(request, response, _ChurMember);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ChurMember _ChurMember) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurMember _ChurMember = m_ds.getById(cid);

        if (!isMissing(request.getParameter("fullName"))) {
            m_logger.debug("updating param fullName from " +_ChurMember.getFullName() + "->" + request.getParameter("fullName"));
            _ChurMember.setFullName(WebParamUtil.getStringValue(request.getParameter("fullName")));

        }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_ChurMember.getFirstName() + "->" + request.getParameter("firstName"));
            _ChurMember.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));

        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_ChurMember.getLastName() + "->" + request.getParameter("lastName"));
            _ChurMember.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));

        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_ChurMember.getTitle() + "->" + request.getParameter("title"));
            _ChurMember.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("otherName"))) {
            m_logger.debug("updating param otherName from " +_ChurMember.getOtherName() + "->" + request.getParameter("otherName"));
            _ChurMember.setOtherName(WebParamUtil.getStringValue(request.getParameter("otherName")));

        }
        if (!isMissing(request.getParameter("household"))) {
            m_logger.debug("updating param household from " +_ChurMember.getHousehold() + "->" + request.getParameter("household"));
            _ChurMember.setHousehold(WebParamUtil.getIntegerValue(request.getParameter("household")));

        }
        if (!isMissing(request.getParameter("householdId"))) {
            m_logger.debug("updating param householdId from " +_ChurMember.getHouseholdId() + "->" + request.getParameter("householdId"));
            _ChurMember.setHouseholdId(WebParamUtil.getLongValue(request.getParameter("householdId")));

        }
        if (!isMissing(request.getParameter("isGroup"))) {
            m_logger.debug("updating param isGroup from " +_ChurMember.getIsGroup() + "->" + request.getParameter("isGroup"));
            _ChurMember.setIsGroup(WebParamUtil.getIntegerValue(request.getParameter("isGroup")));

        }
        if (!isMissing(request.getParameter("isGuest"))) {
            m_logger.debug("updating param isGuest from " +_ChurMember.getIsGuest() + "->" + request.getParameter("isGuest"));
            _ChurMember.setIsGuest(WebParamUtil.getIntegerValue(request.getParameter("isGuest")));

        }
        if (!isMissing(request.getParameter("isSpeaker"))) {
            m_logger.debug("updating param isSpeaker from " +_ChurMember.getIsSpeaker() + "->" + request.getParameter("isSpeaker"));
            _ChurMember.setIsSpeaker(WebParamUtil.getIntegerValue(request.getParameter("isSpeaker")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ChurMember.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ChurMember.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("listIndex"))) {
            m_logger.debug("updating param listIndex from " +_ChurMember.getListIndex() + "->" + request.getParameter("listIndex"));
            _ChurMember.setListIndex(WebParamUtil.getIntegerValue(request.getParameter("listIndex")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ChurMember _ChurMember) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurMember _ChurMember = m_ds.getById(cid);

        if (!isMissing(request.getParameter("fullName"))) {
			return  JtStringUtil.valueOf(_ChurMember.getFullName());
        }
        if (!isMissing(request.getParameter("firstName"))) {
			return  JtStringUtil.valueOf(_ChurMember.getFirstName());
        }
        if (!isMissing(request.getParameter("lastName"))) {
			return  JtStringUtil.valueOf(_ChurMember.getLastName());
        }
        if (!isMissing(request.getParameter("title"))) {
			return  JtStringUtil.valueOf(_ChurMember.getTitle());
        }
        if (!isMissing(request.getParameter("otherName"))) {
			return  JtStringUtil.valueOf(_ChurMember.getOtherName());
        }
        if (!isMissing(request.getParameter("household"))) {
			return  JtStringUtil.valueOf(_ChurMember.getHousehold());
        }
        if (!isMissing(request.getParameter("householdId"))) {
			return  JtStringUtil.valueOf(_ChurMember.getHouseholdId());
        }
        if (!isMissing(request.getParameter("isGroup"))) {
			return  JtStringUtil.valueOf(_ChurMember.getIsGroup());
        }
        if (!isMissing(request.getParameter("isGuest"))) {
			return  JtStringUtil.valueOf(_ChurMember.getIsGuest());
        }
        if (!isMissing(request.getParameter("isSpeaker"))) {
			return  JtStringUtil.valueOf(_ChurMember.getIsSpeaker());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_ChurMember.getTimeCreated());
        }
        if (!isMissing(request.getParameter("listIndex"))) {
			return  JtStringUtil.valueOf(_ChurMember.getListIndex());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(ChurMember _ChurMember) throws Exception {
    }

    protected String getFieldByName(String fieldName, ChurMember _ChurMember) {
        if (fieldName == null || fieldName.equals("")|| _ChurMember == null) return null;
        
        if (fieldName.equals("fullName")) {
            return WebUtil.display(_ChurMember.getFullName());
        }
        if (fieldName.equals("firstName")) {
            return WebUtil.display(_ChurMember.getFirstName());
        }
        if (fieldName.equals("lastName")) {
            return WebUtil.display(_ChurMember.getLastName());
        }
        if (fieldName.equals("title")) {
            return WebUtil.display(_ChurMember.getTitle());
        }
        if (fieldName.equals("otherName")) {
            return WebUtil.display(_ChurMember.getOtherName());
        }
        if (fieldName.equals("household")) {
            return WebUtil.display(_ChurMember.getHousehold());
        }
        if (fieldName.equals("householdId")) {
            return WebUtil.display(_ChurMember.getHouseholdId());
        }
        if (fieldName.equals("isGroup")) {
            return WebUtil.display(_ChurMember.getIsGroup());
        }
        if (fieldName.equals("isGuest")) {
            return WebUtil.display(_ChurMember.getIsGuest());
        }
        if (fieldName.equals("isSpeaker")) {
            return WebUtil.display(_ChurMember.getIsSpeaker());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_ChurMember.getTimeCreated());
        }
        if (fieldName.equals("listIndex")) {
            return WebUtil.display(_ChurMember.getListIndex());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        ChurMemberForm _ChurMemberForm = (ChurMemberForm) form;

		if(requestParams.containsKey("fullName"))
			_ChurMemberForm.setFullName((String)requestParams.get("fullName"));
		if(requestParams.containsKey("firstName"))
			_ChurMemberForm.setFirstName((String)requestParams.get("firstName"));
		if(requestParams.containsKey("lastName"))
			_ChurMemberForm.setLastName((String)requestParams.get("lastName"));
		if(requestParams.containsKey("title"))
			_ChurMemberForm.setTitle((String)requestParams.get("title"));
		if(requestParams.containsKey("otherName"))
			_ChurMemberForm.setOtherName((String)requestParams.get("otherName"));
		if(requestParams.containsKey("household"))
			_ChurMemberForm.setHousehold((String)requestParams.get("household"));
		if(requestParams.containsKey("householdId"))
			_ChurMemberForm.setHouseholdId((String)requestParams.get("householdId"));
		if(requestParams.containsKey("isGroup"))
			_ChurMemberForm.setIsGroup((String)requestParams.get("isGroup"));
		if(requestParams.containsKey("isGuest"))
			_ChurMemberForm.setIsGuest((String)requestParams.get("isGuest"));
		if(requestParams.containsKey("isSpeaker"))
			_ChurMemberForm.setIsSpeaker((String)requestParams.get("isSpeaker"));
		if(requestParams.containsKey("timeCreated"))
			_ChurMemberForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("listIndex"))
			_ChurMemberForm.setListIndex((String)requestParams.get("listIndex"));
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
        m_viewManager.registerView(getActionName(), "chur_member_home=NULL,/jsp/form_chur/churMember_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "chur_member_list=NULL,/jsp/form_chur/churMember_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "chur_member_form=NULL,/jsp/form_chur/churMember_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "chur_member_ajax=NULL,/jsp/form_chur/churMember_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "chur_member_home=NULL,/jsp/form_chur/churMember_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "chur_member_list=NULL,/jsp/form_chur/churMember_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "chur_member_form=NULL,/jsp/form_chur/churMember_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "chur_member_ajax=NULL,/jsp/form_chur/churMember_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected ChurMemberDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
