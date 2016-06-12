/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:41 EDT 2015

*/


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

import com.autosite.db.ExpenseCategory;
import com.autosite.ds.ExpenseCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.AutositeDataObject;
import com.autosite.holder.AbstractDataHolder;

import com.autosite.struts.action.core.AutositeActionBase;
import com.autosite.struts.form.ExpenseCategoryForm;
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


import com.autosite.holder.ExpenseCategoryDataHolder;

/*
Generated: Sun Jul 12 20:19:41 EDT 2015
*/

public class ExpenseCategoryAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(ExpenseCategoryAction.class);

    public ExpenseCategoryAction(){
        m_ds = ExpenseCategoryDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
		registerDefaultViewsForAction();
    }

/*
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        ExpenseCategoryForm _ExpenseCategoryForm = (ExpenseCategoryForm) form;
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
        ExpenseCategory _ExpenseCategory = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ExpenseCategory = m_ds.getById(cid);
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
            //ExpenseCategory _ExpenseCategory = m_ds.getById(cid);

            if (_ExpenseCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_ExpenseCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ExpenseCategory.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_ExpenseCategory);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _ExpenseCategoryForm == null) {
    	            editField(request, response, _ExpenseCategory);
				} else {
    	            edit(request, response, _ExpenseCategoryForm, _ExpenseCategory);
				}
                if (returnObjects != null) returnObjects.put("target", _ExpenseCategory);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                ExpenseCategory o = m_ds.getById( _ExpenseCategory.getId(), true);

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
            //ExpenseCategory _ExpenseCategory = m_ds.getById(cid);

            if (_ExpenseCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ExpenseCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ExpenseCategory.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _ExpenseCategory);
                if (returnObjects != null) returnObjects.put("target", _ExpenseCategory);
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
            //ExpenseCategory _ExpenseCategory = m_ds.getById(cid);

            if (_ExpenseCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ExpenseCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ExpenseCategory.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _ExpenseCategory);
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

            m_ds.delete(_ExpenseCategory); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _ExpenseCategory);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _ExpenseCategory);
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


            m_logger.info("Creating new ExpenseCategory" );
            ExpenseCategory _ExpenseCategoryNew = new ExpenseCategory();   

            // Setting IDs for the object
            _ExpenseCategoryNew.setSiteId(site.getId());
			
            if ( _ExpenseCategoryForm == null) {
                setFields(request, response, _ExpenseCategoryNew);
            } else {

            _ExpenseCategoryNew.setExpenseCategory(WebParamUtil.getStringValue(_ExpenseCategoryForm.getExpenseCategory()));
            m_logger.debug("setting ExpenseCategory=" +_ExpenseCategoryForm.getExpenseCategory());


            _ExpenseCategoryNew.setTimeCreated(WebParamUtil.getTimestampValue(_ExpenseCategoryForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ExpenseCategoryForm.getTimeCreated());

        	_ExpenseCategoryNew.setTimeCreated(new TimeNow());

            _ExpenseCategoryNew.setTimeUpdated(WebParamUtil.getTimestampValue(_ExpenseCategoryForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ExpenseCategoryForm.getTimeUpdated());

        	_ExpenseCategoryNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_ExpenseCategoryNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ExpenseCategoryNew);
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
            
            if (_ExpenseCategoryNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ExpenseCategoryNew);
            if (returnObjects != null) returnObjects.put("target", _ExpenseCategoryNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ExpenseCategoryNew);
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
             _ExpenseCategory =  _ExpenseCategoryNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _ExpenseCategory, "cleaner-ticket" );



        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new ExpenseCategory();
    }

    protected AbstractDataHolder createModelDataHolder() {
        return new ExpenseCategoryDataHolder();
    }



    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        ExpenseCategoryForm _ExpenseCategoryForm = (ExpenseCategoryForm) form;
        ExpenseCategory _ExpenseCategory = (ExpenseCategory) dataObject;

        m_logger.debug("Before update " + ExpenseCategoryDS.objectToString(_ExpenseCategory));

        _ExpenseCategory.setExpenseCategory(WebParamUtil.getStringValue(_ExpenseCategoryForm.getExpenseCategory()));




        _ExpenseCategory.setTimeUpdated(WebParamUtil.getTimestampValue(_ExpenseCategoryForm.getTimeUpdated()));

        _ExpenseCategory.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _ExpenseCategory);
        m_ds.update(_ExpenseCategory);
        m_actionExtent.afterUpdate(request, response, _ExpenseCategory);
        m_logger.debug("After update " + ExpenseCategoryDS.objectToString(_ExpenseCategory));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        ExpenseCategory _ExpenseCategory = (ExpenseCategory) dataObject;

        if (!isMissing(request.getParameter("expenseCategory"))) {
            m_logger.debug("updating param expenseCategory from " +_ExpenseCategory.getExpenseCategory() + "->" + request.getParameter("expenseCategory"));
            _ExpenseCategory.setExpenseCategory(WebParamUtil.getStringValue(request.getParameter("expenseCategory")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ExpenseCategory.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ExpenseCategory.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_ExpenseCategory.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _ExpenseCategory.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _ExpenseCategory.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _ExpenseCategory);
        m_ds.update(_ExpenseCategory);
        m_actionExtent.afterUpdate(request, response, _ExpenseCategory);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        ExpenseCategory _ExpenseCategory = (ExpenseCategory) dataObject;

        if (!isMissing(request.getParameter("expenseCategory"))) {
            m_logger.debug("updating param expenseCategory from " +_ExpenseCategory.getExpenseCategory() + "->" + request.getParameter("expenseCategory"));
            _ExpenseCategory.setExpenseCategory(WebParamUtil.getStringValue(request.getParameter("expenseCategory")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ExpenseCategory.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ExpenseCategory.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_ExpenseCategory.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _ExpenseCategory.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _ExpenseCategory.setTimeUpdated(new TimeNow());
        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        ExpenseCategoryForm _ExpenseCategoryForm = (ExpenseCategoryForm) form;
        ExpenseCategory _ExpenseCategory = (ExpenseCategory) dataObject;

            _ExpenseCategory.setExpenseCategory(WebParamUtil.getStringValue(_ExpenseCategoryForm.getExpenseCategory()));
            m_logger.debug("setting ExpenseCategory=" +_ExpenseCategoryForm.getExpenseCategory());


            _ExpenseCategory.setTimeCreated(WebParamUtil.getTimestampValue(_ExpenseCategoryForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ExpenseCategoryForm.getTimeCreated());

        	_ExpenseCategory.setTimeCreated(new TimeNow());

            _ExpenseCategory.setTimeUpdated(WebParamUtil.getTimestampValue(_ExpenseCategoryForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ExpenseCategoryForm.getTimeUpdated());

        	_ExpenseCategory.setTimeUpdated(new TimeNow());


    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ExpenseCategory _ExpenseCategory = m_ds.getById(cid);
        ExpenseCategory _ExpenseCategory = (ExpenseCategory) dataObject;

        if (!isMissing(request.getParameter("expenseCategory"))) {
			return  JtStringUtil.valueOf(_ExpenseCategory.getExpenseCategory());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_ExpenseCategory.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_ExpenseCategory.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, ExpenseCategory _ExpenseCategory) {
        if (fieldName == null || fieldName.equals("")|| _ExpenseCategory == null) return null;
        
        if (fieldName.equals("expenseCategory")) {
            return WebUtil.display(_ExpenseCategory.getExpenseCategory());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_ExpenseCategory.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_ExpenseCategory.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        ExpenseCategoryForm _ExpenseCategoryForm = (ExpenseCategoryForm) form;

		if(requestParams.containsKey("expenseCategory"))
			_ExpenseCategoryForm.setExpenseCategory((String)requestParams.get("expenseCategory"));
		if(requestParams.containsKey("timeCreated"))
			_ExpenseCategoryForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_ExpenseCategoryForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    // SynchRequired is controlled by config file conf/synk.properties
    // take a look at add the actiongroup or action name to the list
    public boolean isSynchRequired(){

        return synkRequired;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "expense_category_home=NULL,/jsp/form_expense/expenseCategory_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "expense_category_list=NULL,/jsp/form_expense/expenseCategory_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "expense_category_form=NULL,/jsp/form_expense/expenseCategory_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "expense_category_ajax=NULL,/jsp/form_expense/expenseCategory_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "expense_category_home=NULL,/jsp/form_expense/expenseCategory_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "expense_category_list=NULL,/jsp/form_expense/expenseCategory_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "expense_category_form=NULL,/jsp/form_expense/expenseCategory_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "expense_category_ajax=NULL,/jsp/form_expense/expenseCategory_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
//    protected ExpenseCategoryDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
