/* 
Template last modification history:


Source Generated: Sun Jul 12 20:40:22 EDT 2015

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

import com.autosite.db.Expense;
import com.autosite.ds.ExpenseDS;
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
import com.autosite.struts.form.ExpenseForm;
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

import com.autosite.ds.ExpenseItemDS;
import com.autosite.db.ExpenseItem;

import com.autosite.holder.ExpenseDataHolder;

/*
Generated: Sun Jul 12 20:40:22 EDT 2015
*/

public class ExpenseAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(ExpenseAction.class);

    public ExpenseAction(){
        m_ds = ExpenseDS.getInstance();
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

        ExpenseForm _ExpenseForm = (ExpenseForm) form;
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
        Expense _Expense = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _Expense = m_ds.getById(cid);
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
            //Expense _Expense = m_ds.getById(cid);

            if (_Expense == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_Expense.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Expense.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_Expense);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _ExpenseForm == null) {
    	            editField(request, response, _Expense);
				} else {
    	            edit(request, response, _ExpenseForm, _Expense);
				}
                if (returnObjects != null) returnObjects.put("target", _Expense);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                Expense o = m_ds.getById( _Expense.getId(), true);

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
            //Expense _Expense = m_ds.getById(cid);

            if (_Expense == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_Expense.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Expense.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _Expense);
                if (returnObjects != null) returnObjects.put("target", _Expense);
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
            //Expense _Expense = m_ds.getById(cid);

            if (_Expense == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_Expense.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Expense.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _Expense);
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

            m_ds.delete(_Expense); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _Expense);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _Expense);
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


            m_logger.info("Creating new Expense" );
            Expense _ExpenseNew = new Expense();   

            // Setting IDs for the object
            _ExpenseNew.setSiteId(site.getId());
			
            if ( _ExpenseForm == null) {
                setFields(request, response, _ExpenseNew);
            } else {

            _ExpenseNew.setExpenseItemId(WebParamUtil.getLongValue(_ExpenseForm.getExpenseItemId()));
            m_logger.debug("setting ExpenseItemId=" +_ExpenseForm.getExpenseItemId());


            _ExpenseNew.setAmount(WebParamUtil.getLongValue(_ExpenseForm.getAmount()));
            m_logger.debug("setting Amount=" +_ExpenseForm.getAmount());


            _ExpenseNew.setDescription(WebParamUtil.getStringValue(_ExpenseForm.getDescription()));
            m_logger.debug("setting Description=" +_ExpenseForm.getDescription());


            _ExpenseNew.setPayMethod(WebParamUtil.getStringValue(_ExpenseForm.getPayMethod()));
            m_logger.debug("setting PayMethod=" +_ExpenseForm.getPayMethod());


            _ExpenseNew.setNotExpense(WebParamUtil.getIntegerValue(_ExpenseForm.getNotExpense()));
            m_logger.debug("setting NotExpense=" +_ExpenseForm.getNotExpense());


            _ExpenseNew.setReference(WebParamUtil.getStringValue(_ExpenseForm.getReference()));
            m_logger.debug("setting Reference=" +_ExpenseForm.getReference());


            _ExpenseNew.setDateExpense(WebParamUtil.getStringValue(_ExpenseForm.getDateExpense()));
            m_logger.debug("setting DateExpense=" +_ExpenseForm.getDateExpense());


            _ExpenseNew.setTimeCreated(WebParamUtil.getTimestampValue(_ExpenseForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ExpenseForm.getTimeCreated());

        	_ExpenseNew.setTimeCreated(new TimeNow());

            _ExpenseNew.setTimeUpdated(WebParamUtil.getTimestampValue(_ExpenseForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ExpenseForm.getTimeUpdated());

        	_ExpenseNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_ExpenseNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ExpenseNew);
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
            
            if (_ExpenseNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ExpenseNew);
            if (returnObjects != null) returnObjects.put("target", _ExpenseNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ExpenseNew);
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
             _Expense =  _ExpenseNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _Expense, "cleaner-ticket" );



        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new Expense();
    }

    protected AbstractDataHolder createModelDataHolder() {
        return new ExpenseDataHolder();
    }



    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        ExpenseForm _ExpenseForm = (ExpenseForm) form;
        Expense _Expense = (Expense) dataObject;

        m_logger.debug("Before update " + ExpenseDS.objectToString(_Expense));

        _Expense.setExpenseItemId(WebParamUtil.getLongValue(_ExpenseForm.getExpenseItemId()));


        _Expense.setAmount(WebParamUtil.getLongValue(_ExpenseForm.getAmount()));


        _Expense.setDescription(WebParamUtil.getStringValue(_ExpenseForm.getDescription()));


        _Expense.setPayMethod(WebParamUtil.getStringValue(_ExpenseForm.getPayMethod()));


        _Expense.setNotExpense(WebParamUtil.getIntegerValue(_ExpenseForm.getNotExpense()));


        _Expense.setReference(WebParamUtil.getStringValue(_ExpenseForm.getReference()));


        _Expense.setDateExpense(WebParamUtil.getStringValue(_ExpenseForm.getDateExpense()));




        _Expense.setTimeUpdated(WebParamUtil.getTimestampValue(_ExpenseForm.getTimeUpdated()));

        _Expense.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _Expense);
        m_ds.update(_Expense);
        m_actionExtent.afterUpdate(request, response, _Expense);
        m_logger.debug("After update " + ExpenseDS.objectToString(_Expense));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        Expense _Expense = (Expense) dataObject;

        if (!isMissing(request.getParameter("expenseItemId"))) {
            m_logger.debug("updating param expenseItemId from " +_Expense.getExpenseItemId() + "->" + request.getParameter("expenseItemId"));
            _Expense.setExpenseItemId(WebParamUtil.getLongValue(request.getParameter("expenseItemId")));

        }
        if (!isMissing(request.getParameter("amount"))) {
            m_logger.debug("updating param amount from " +_Expense.getAmount() + "->" + request.getParameter("amount"));
            _Expense.setAmount(WebParamUtil.getLongValue(request.getParameter("amount")));

        }
        if (!isMissing(request.getParameter("description"))) {
            m_logger.debug("updating param description from " +_Expense.getDescription() + "->" + request.getParameter("description"));
            _Expense.setDescription(WebParamUtil.getStringValue(request.getParameter("description")));

        }
        if (!isMissing(request.getParameter("payMethod"))) {
            m_logger.debug("updating param payMethod from " +_Expense.getPayMethod() + "->" + request.getParameter("payMethod"));
            _Expense.setPayMethod(WebParamUtil.getStringValue(request.getParameter("payMethod")));

        }
        if (!isMissing(request.getParameter("notExpense"))) {
            m_logger.debug("updating param notExpense from " +_Expense.getNotExpense() + "->" + request.getParameter("notExpense"));
            _Expense.setNotExpense(WebParamUtil.getIntegerValue(request.getParameter("notExpense")));

        }
        if (!isMissing(request.getParameter("reference"))) {
            m_logger.debug("updating param reference from " +_Expense.getReference() + "->" + request.getParameter("reference"));
            _Expense.setReference(WebParamUtil.getStringValue(request.getParameter("reference")));

        }
        if (!isMissing(request.getParameter("dateExpense"))) {
            m_logger.debug("updating param dateExpense from " +_Expense.getDateExpense() + "->" + request.getParameter("dateExpense"));
            _Expense.setDateExpense(WebParamUtil.getStringValue(request.getParameter("dateExpense")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_Expense.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _Expense.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_Expense.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _Expense.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _Expense.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _Expense);
        m_ds.update(_Expense);
        m_actionExtent.afterUpdate(request, response, _Expense);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        Expense _Expense = (Expense) dataObject;

        if (!isMissing(request.getParameter("expenseItemId"))) {
            m_logger.debug("updating param expenseItemId from " +_Expense.getExpenseItemId() + "->" + request.getParameter("expenseItemId"));
            _Expense.setExpenseItemId(WebParamUtil.getLongValue(request.getParameter("expenseItemId")));

        }
        if (!isMissing(request.getParameter("amount"))) {
            m_logger.debug("updating param amount from " +_Expense.getAmount() + "->" + request.getParameter("amount"));
            _Expense.setAmount(WebParamUtil.getLongValue(request.getParameter("amount")));

        }
        if (!isMissing(request.getParameter("description"))) {
            m_logger.debug("updating param description from " +_Expense.getDescription() + "->" + request.getParameter("description"));
            _Expense.setDescription(WebParamUtil.getStringValue(request.getParameter("description")));

        }
        if (!isMissing(request.getParameter("payMethod"))) {
            m_logger.debug("updating param payMethod from " +_Expense.getPayMethod() + "->" + request.getParameter("payMethod"));
            _Expense.setPayMethod(WebParamUtil.getStringValue(request.getParameter("payMethod")));

        }
        if (!isMissing(request.getParameter("notExpense"))) {
            m_logger.debug("updating param notExpense from " +_Expense.getNotExpense() + "->" + request.getParameter("notExpense"));
            _Expense.setNotExpense(WebParamUtil.getIntegerValue(request.getParameter("notExpense")));

        }
        if (!isMissing(request.getParameter("reference"))) {
            m_logger.debug("updating param reference from " +_Expense.getReference() + "->" + request.getParameter("reference"));
            _Expense.setReference(WebParamUtil.getStringValue(request.getParameter("reference")));

        }
        if (!isMissing(request.getParameter("dateExpense"))) {
            m_logger.debug("updating param dateExpense from " +_Expense.getDateExpense() + "->" + request.getParameter("dateExpense"));
            _Expense.setDateExpense(WebParamUtil.getStringValue(request.getParameter("dateExpense")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_Expense.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _Expense.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_Expense.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _Expense.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _Expense.setTimeUpdated(new TimeNow());
        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        ExpenseForm _ExpenseForm = (ExpenseForm) form;
        Expense _Expense = (Expense) dataObject;

            _Expense.setExpenseItemId(WebParamUtil.getLongValue(_ExpenseForm.getExpenseItemId()));
            m_logger.debug("setting ExpenseItemId=" +_ExpenseForm.getExpenseItemId());


            _Expense.setAmount(WebParamUtil.getLongValue(_ExpenseForm.getAmount()));
            m_logger.debug("setting Amount=" +_ExpenseForm.getAmount());


            _Expense.setDescription(WebParamUtil.getStringValue(_ExpenseForm.getDescription()));
            m_logger.debug("setting Description=" +_ExpenseForm.getDescription());


            _Expense.setPayMethod(WebParamUtil.getStringValue(_ExpenseForm.getPayMethod()));
            m_logger.debug("setting PayMethod=" +_ExpenseForm.getPayMethod());


            _Expense.setNotExpense(WebParamUtil.getIntegerValue(_ExpenseForm.getNotExpense()));
            m_logger.debug("setting NotExpense=" +_ExpenseForm.getNotExpense());


            _Expense.setReference(WebParamUtil.getStringValue(_ExpenseForm.getReference()));
            m_logger.debug("setting Reference=" +_ExpenseForm.getReference());


            _Expense.setDateExpense(WebParamUtil.getStringValue(_ExpenseForm.getDateExpense()));
            m_logger.debug("setting DateExpense=" +_ExpenseForm.getDateExpense());


            _Expense.setTimeCreated(WebParamUtil.getTimestampValue(_ExpenseForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ExpenseForm.getTimeCreated());

        	_Expense.setTimeCreated(new TimeNow());

            _Expense.setTimeUpdated(WebParamUtil.getTimestampValue(_ExpenseForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ExpenseForm.getTimeUpdated());

        	_Expense.setTimeUpdated(new TimeNow());


    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Expense _Expense = m_ds.getById(cid);
        Expense _Expense = (Expense) dataObject;

        if (!isMissing(request.getParameter("expenseItemId"))) {
			return  JtStringUtil.valueOf(_Expense.getExpenseItemId());
        }
        if (!isMissing(request.getParameter("amount"))) {
			return  JtStringUtil.valueOf(_Expense.getAmount());
        }
        if (!isMissing(request.getParameter("description"))) {
			return  JtStringUtil.valueOf(_Expense.getDescription());
        }
        if (!isMissing(request.getParameter("payMethod"))) {
			return  JtStringUtil.valueOf(_Expense.getPayMethod());
        }
        if (!isMissing(request.getParameter("notExpense"))) {
			return  JtStringUtil.valueOf(_Expense.getNotExpense());
        }
        if (!isMissing(request.getParameter("reference"))) {
			return  JtStringUtil.valueOf(_Expense.getReference());
        }
        if (!isMissing(request.getParameter("dateExpense"))) {
			return  JtStringUtil.valueOf(_Expense.getDateExpense());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_Expense.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_Expense.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, Expense _Expense) {
        if (fieldName == null || fieldName.equals("")|| _Expense == null) return null;
        
        if (fieldName.equals("expenseItemId")) {
            return WebUtil.display(_Expense.getExpenseItemId());
        }
        if (fieldName.equals("amount")) {
            return WebUtil.display(_Expense.getAmount());
        }
        if (fieldName.equals("description")) {
            return WebUtil.display(_Expense.getDescription());
        }
        if (fieldName.equals("payMethod")) {
            return WebUtil.display(_Expense.getPayMethod());
        }
        if (fieldName.equals("notExpense")) {
            return WebUtil.display(_Expense.getNotExpense());
        }
        if (fieldName.equals("reference")) {
            return WebUtil.display(_Expense.getReference());
        }
        if (fieldName.equals("dateExpense")) {
            return WebUtil.display(_Expense.getDateExpense());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_Expense.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_Expense.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        ExpenseForm _ExpenseForm = (ExpenseForm) form;

		if(requestParams.containsKey("expenseItemId"))
			_ExpenseForm.setExpenseItemId((String)requestParams.get("expenseItemId"));
		if(requestParams.containsKey("amount"))
			_ExpenseForm.setAmount((String)requestParams.get("amount"));
		if(requestParams.containsKey("description"))
			_ExpenseForm.setDescription((String)requestParams.get("description"));
		if(requestParams.containsKey("payMethod"))
			_ExpenseForm.setPayMethod((String)requestParams.get("payMethod"));
		if(requestParams.containsKey("notExpense"))
			_ExpenseForm.setNotExpense((String)requestParams.get("notExpense"));
		if(requestParams.containsKey("reference"))
			_ExpenseForm.setReference((String)requestParams.get("reference"));
		if(requestParams.containsKey("dateExpense"))
			_ExpenseForm.setDateExpense((String)requestParams.get("dateExpense"));
		if(requestParams.containsKey("timeCreated"))
			_ExpenseForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_ExpenseForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
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
        m_viewManager.registerView(getActionName(), "expense_home=NULL,/jsp/form_expense/expense_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "expense_list=NULL,/jsp/form_expense/expense_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "expense_form=NULL,/jsp/form_expense/expense_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "expense_ajax=NULL,/jsp/form_expense/expense_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "expense_home=NULL,/jsp/form_expense/expense_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "expense_list=NULL,/jsp/form_expense/expense_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "expense_form=NULL,/jsp/form_expense/expense_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "expense_ajax=NULL,/jsp/form_expense/expense_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
//    protected ExpenseDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
