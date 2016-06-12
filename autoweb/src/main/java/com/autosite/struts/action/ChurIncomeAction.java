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

import com.autosite.db.ChurIncome;
import com.autosite.ds.ChurIncomeDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ChurIncomeForm;
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

import com.autosite.ds.ChurMemberDS;
import com.autosite.db.ChurMember;
import com.autosite.ds.ChurIncomeItemDS;
import com.autosite.db.ChurIncomeItem;

import com.autosite.holder.ChurIncomeDataHolder;


public class ChurIncomeAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(ChurIncomeAction.class);

    public ChurIncomeAction(){
        m_ds = ChurIncomeDS.getInstance();
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

        ChurIncomeForm _ChurIncomeForm = (ChurIncomeForm) form;
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
        ChurIncomeDataHolder _ChurIncome = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ChurIncome = m_ds.getById(cid);
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
            //ChurIncome _ChurIncome = m_ds.getById(cid);

            if (_ChurIncome == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_ChurIncome.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurIncome.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_ChurIncome);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _ChurIncomeForm == null) {
    	            editField(request, response, _ChurIncome);
				} else {
    	            edit(request, response, _ChurIncomeForm, _ChurIncome);
				}
                if (returnObjects != null) returnObjects.put("target", _ChurIncome);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                ChurIncomeDataHolder o = m_ds.getById( _ChurIncome.getId(), true);

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
            //ChurIncome _ChurIncome = m_ds.getById(cid);

            if (_ChurIncome == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ChurIncome.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurIncome.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _ChurIncome);
                if (returnObjects != null) returnObjects.put("target", _ChurIncome);
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
            //ChurIncome _ChurIncome = m_ds.getById(cid);

            if (_ChurIncome == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ChurIncome.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurIncome.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _ChurIncome);
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

            m_ds.delete(_ChurIncome); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _ChurIncome);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _ChurIncome);
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


            m_logger.info("Creating new ChurIncome" );
            ChurIncomeDataHolder _ChurIncomeNew = new ChurIncomeDataHolder();   

            // Setting IDs for the object
            _ChurIncomeNew.setSiteId(site.getId());
			
            if ( _ChurIncomeForm == null) {
                setFields(request, response, _ChurIncomeNew);
            } else {

            _ChurIncomeNew.setYear(WebParamUtil.getIntegerValue(_ChurIncomeForm.getYear()));
            m_logger.debug("setting Year=" +_ChurIncomeForm.getYear());


            _ChurIncomeNew.setWeek(WebParamUtil.getStringValue(_ChurIncomeForm.getWeek()));
            m_logger.debug("setting Week=" +_ChurIncomeForm.getWeek());


            _ChurIncomeNew.setChurMemberId(WebParamUtil.getLongValue(_ChurIncomeForm.getChurMemberId()));
            m_logger.debug("setting ChurMemberId=" +_ChurIncomeForm.getChurMemberId());


            _ChurIncomeNew.setIncomeItemId(WebParamUtil.getLongValue(_ChurIncomeForm.getIncomeItemId()));
            m_logger.debug("setting IncomeItemId=" +_ChurIncomeForm.getIncomeItemId());


            _ChurIncomeNew.setAmmount(WebParamUtil.getDoubleValue(_ChurIncomeForm.getAmmount()));
            m_logger.debug("setting Ammount=" +_ChurIncomeForm.getAmmount());


            _ChurIncomeNew.setTimeCreated(WebParamUtil.getTimestampValue(_ChurIncomeForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ChurIncomeForm.getTimeCreated());

        	_ChurIncomeNew.setTimeCreated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_ChurIncomeNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ChurIncomeNew);
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
            
            if (_ChurIncomeNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ChurIncomeNew);
            if (returnObjects != null) returnObjects.put("target", _ChurIncomeNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ChurIncomeNew);
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
             _ChurIncome =  _ChurIncomeNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }





        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ChurIncomeForm _ChurIncomeForm, ChurIncomeDataHolder _ChurIncome) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurIncome _ChurIncome = m_ds.getById(cid);

        m_logger.debug("Before update " + ChurIncomeDS.objectToString(_ChurIncome));

        _ChurIncome.setYear(WebParamUtil.getIntegerValue(_ChurIncomeForm.getYear()));


        _ChurIncome.setWeek(WebParamUtil.getStringValue(_ChurIncomeForm.getWeek()));


        _ChurIncome.setChurMemberId(WebParamUtil.getLongValue(_ChurIncomeForm.getChurMemberId()));


        _ChurIncome.setIncomeItemId(WebParamUtil.getLongValue(_ChurIncomeForm.getIncomeItemId()));


        _ChurIncome.setAmmount(WebParamUtil.getDoubleValue(_ChurIncomeForm.getAmmount()));





        m_actionExtent.beforeUpdate(request, response, _ChurIncome);
        m_ds.update(_ChurIncome);
        m_actionExtent.afterUpdate(request, response, _ChurIncome);
        m_logger.debug("After update " + ChurIncomeDS.objectToString(_ChurIncome));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ChurIncomeDataHolder _ChurIncome) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurIncome _ChurIncome = m_ds.getById(cid);

        if (!isMissing(request.getParameter("year"))) {
            m_logger.debug("updating param year from " +_ChurIncome.getYear() + "->" + request.getParameter("year"));
            _ChurIncome.setYear(WebParamUtil.getIntegerValue(request.getParameter("year")));

        }
        if (!isMissing(request.getParameter("week"))) {
            m_logger.debug("updating param week from " +_ChurIncome.getWeek() + "->" + request.getParameter("week"));
            _ChurIncome.setWeek(WebParamUtil.getStringValue(request.getParameter("week")));

        }
        if (!isMissing(request.getParameter("churMemberId"))) {
            m_logger.debug("updating param churMemberId from " +_ChurIncome.getChurMemberId() + "->" + request.getParameter("churMemberId"));
            _ChurIncome.setChurMemberId(WebParamUtil.getLongValue(request.getParameter("churMemberId")));

        }
        if (!isMissing(request.getParameter("incomeItemId"))) {
            m_logger.debug("updating param incomeItemId from " +_ChurIncome.getIncomeItemId() + "->" + request.getParameter("incomeItemId"));
            _ChurIncome.setIncomeItemId(WebParamUtil.getLongValue(request.getParameter("incomeItemId")));

        }
        if (!isMissing(request.getParameter("ammount"))) {
            m_logger.debug("updating param ammount from " +_ChurIncome.getAmmount() + "->" + request.getParameter("ammount"));
            _ChurIncome.setAmmount(WebParamUtil.getDoubleValue(request.getParameter("ammount")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ChurIncome.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ChurIncome.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

        m_actionExtent.beforeUpdate(request, response, _ChurIncome);
        m_ds.update(_ChurIncome);
        m_actionExtent.afterUpdate(request, response, _ChurIncome);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ChurIncomeDataHolder _ChurIncome) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurIncome _ChurIncome = m_ds.getById(cid);

        if (!isMissing(request.getParameter("year"))) {
            m_logger.debug("updating param year from " +_ChurIncome.getYear() + "->" + request.getParameter("year"));
            _ChurIncome.setYear(WebParamUtil.getIntegerValue(request.getParameter("year")));

        }
        if (!isMissing(request.getParameter("week"))) {
            m_logger.debug("updating param week from " +_ChurIncome.getWeek() + "->" + request.getParameter("week"));
            _ChurIncome.setWeek(WebParamUtil.getStringValue(request.getParameter("week")));

        }
        if (!isMissing(request.getParameter("churMemberId"))) {
            m_logger.debug("updating param churMemberId from " +_ChurIncome.getChurMemberId() + "->" + request.getParameter("churMemberId"));
            _ChurIncome.setChurMemberId(WebParamUtil.getLongValue(request.getParameter("churMemberId")));

        }
        if (!isMissing(request.getParameter("incomeItemId"))) {
            m_logger.debug("updating param incomeItemId from " +_ChurIncome.getIncomeItemId() + "->" + request.getParameter("incomeItemId"));
            _ChurIncome.setIncomeItemId(WebParamUtil.getLongValue(request.getParameter("incomeItemId")));

        }
        if (!isMissing(request.getParameter("ammount"))) {
            m_logger.debug("updating param ammount from " +_ChurIncome.getAmmount() + "->" + request.getParameter("ammount"));
            _ChurIncome.setAmmount(WebParamUtil.getDoubleValue(request.getParameter("ammount")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ChurIncome.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ChurIncome.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ChurIncomeDataHolder _ChurIncome) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurIncome _ChurIncome = m_ds.getById(cid);

        if (!isMissing(request.getParameter("year"))) {
			return  JtStringUtil.valueOf(_ChurIncome.getYear());
        }
        if (!isMissing(request.getParameter("week"))) {
			return  JtStringUtil.valueOf(_ChurIncome.getWeek());
        }
        if (!isMissing(request.getParameter("churMemberId"))) {
			return  JtStringUtil.valueOf(_ChurIncome.getChurMemberId());
        }
        if (!isMissing(request.getParameter("incomeItemId"))) {
			return  JtStringUtil.valueOf(_ChurIncome.getIncomeItemId());
        }
        if (!isMissing(request.getParameter("ammount"))) {
			return  JtStringUtil.valueOf(_ChurIncome.getAmmount());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_ChurIncome.getTimeCreated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(ChurIncomeDataHolder _ChurIncome) throws Exception {
    }

    protected String getFieldByName(String fieldName, ChurIncomeDataHolder _ChurIncome) {
        if (fieldName == null || fieldName.equals("")|| _ChurIncome == null) return null;
        
        if (fieldName.equals("year")) {
            return WebUtil.display(_ChurIncome.getYear());
        }
        if (fieldName.equals("week")) {
            return WebUtil.display(_ChurIncome.getWeek());
        }
        if (fieldName.equals("churMemberId")) {
            return WebUtil.display(_ChurIncome.getChurMemberId());
        }
        if (fieldName.equals("incomeItemId")) {
            return WebUtil.display(_ChurIncome.getIncomeItemId());
        }
        if (fieldName.equals("ammount")) {
            return WebUtil.display(_ChurIncome.getAmmount());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_ChurIncome.getTimeCreated());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        ChurIncomeForm _ChurIncomeForm = (ChurIncomeForm) form;

		if(requestParams.containsKey("year"))
			_ChurIncomeForm.setYear((String)requestParams.get("year"));
		if(requestParams.containsKey("week"))
			_ChurIncomeForm.setWeek((String)requestParams.get("week"));
		if(requestParams.containsKey("churMemberId"))
			_ChurIncomeForm.setChurMemberId((String)requestParams.get("churMemberId"));
		if(requestParams.containsKey("incomeItemId"))
			_ChurIncomeForm.setIncomeItemId((String)requestParams.get("incomeItemId"));
		if(requestParams.containsKey("ammount"))
			_ChurIncomeForm.setAmmount((String)requestParams.get("ammount"));
		if(requestParams.containsKey("timeCreated"))
			_ChurIncomeForm.setTimeCreated((String)requestParams.get("timeCreated"));
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
        m_viewManager.registerView(getActionName(), "chur_income_home=NULL,/jsp/form_chur/churIncome_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "chur_income_list=NULL,/jsp/form_chur/churIncome_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "chur_income_form=NULL,/jsp/form_chur/churIncome_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "chur_income_ajax=NULL,/jsp/form_chur/churIncome_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "chur_income_home=NULL,/jsp/form_chur/churIncome_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "chur_income_list=NULL,/jsp/form_chur/churIncome_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "chur_income_form=NULL,/jsp/form_chur/churIncome_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "chur_income_ajax=NULL,/jsp/form_chur/churIncome_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected ChurIncomeDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
