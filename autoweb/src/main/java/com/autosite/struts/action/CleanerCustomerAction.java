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

import com.autosite.db.CleanerCustomer;
import com.autosite.ds.CleanerCustomerDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerCustomerForm;
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




public class CleanerCustomerAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerCustomerAction.class);

    public CleanerCustomerAction(){
        m_ds = CleanerCustomerDS.getInstance();
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

        CleanerCustomerForm _CleanerCustomerForm = (CleanerCustomerForm) form;
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
        CleanerCustomer _CleanerCustomer = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerCustomer = m_ds.getById(cid);
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
            //CleanerCustomer _CleanerCustomer = m_ds.getById(cid);

            if (_CleanerCustomer == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerCustomer.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerCustomer.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerCustomer);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerCustomerForm == null) {
    	            editField(request, response, _CleanerCustomer);
				} else {
    	            edit(request, response, _CleanerCustomerForm, _CleanerCustomer);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerCustomer);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerCustomer o = m_ds.getById( _CleanerCustomer.getId(), true);

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
            //CleanerCustomer _CleanerCustomer = m_ds.getById(cid);

            if (_CleanerCustomer == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerCustomer.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerCustomer.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerCustomer);
                if (returnObjects != null) returnObjects.put("target", _CleanerCustomer);
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
            //CleanerCustomer _CleanerCustomer = m_ds.getById(cid);

            if (_CleanerCustomer == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerCustomer.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerCustomer.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerCustomer);
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

            m_ds.delete(_CleanerCustomer); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerCustomer);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerCustomer);
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


            m_logger.info("Creating new CleanerCustomer" );
            CleanerCustomer _CleanerCustomerNew = new CleanerCustomer();   

            // Setting IDs for the object
            _CleanerCustomerNew.setSiteId(site.getId());
			
            if ( _CleanerCustomerForm == null) {
                setFields(request, response, _CleanerCustomerNew);
            } else {

            _CleanerCustomerNew.setAutositeUserId(WebParamUtil.getLongValue(_CleanerCustomerForm.getAutositeUserId()));
            m_logger.debug("setting AutositeUserId=" +_CleanerCustomerForm.getAutositeUserId());


            _CleanerCustomerNew.setEmail(WebParamUtil.getStringValue(_CleanerCustomerForm.getEmail()));
            m_logger.debug("setting Email=" +_CleanerCustomerForm.getEmail());


            _CleanerCustomerNew.setPhone(WebParamUtil.getStringValue(_CleanerCustomerForm.getPhone()));
            m_logger.debug("setting Phone=" +_CleanerCustomerForm.getPhone());


            _CleanerCustomerNew.setPhone2(WebParamUtil.getStringValue(_CleanerCustomerForm.getPhone2()));
            m_logger.debug("setting Phone2=" +_CleanerCustomerForm.getPhone2());


            _CleanerCustomerNew.setPhonePreferred(WebParamUtil.getIntegerValue(_CleanerCustomerForm.getPhonePreferred()));
            m_logger.debug("setting PhonePreferred=" +_CleanerCustomerForm.getPhonePreferred());


            _CleanerCustomerNew.setTitle(WebParamUtil.getStringValue(_CleanerCustomerForm.getTitle()));
            m_logger.debug("setting Title=" +_CleanerCustomerForm.getTitle());


            _CleanerCustomerNew.setLastName(WebParamUtil.getStringValue(_CleanerCustomerForm.getLastName()));
            m_logger.debug("setting LastName=" +_CleanerCustomerForm.getLastName());


            _CleanerCustomerNew.setFirstName(WebParamUtil.getStringValue(_CleanerCustomerForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_CleanerCustomerForm.getFirstName());


            _CleanerCustomerNew.setAddress(WebParamUtil.getStringValue(_CleanerCustomerForm.getAddress()));
            m_logger.debug("setting Address=" +_CleanerCustomerForm.getAddress());


            _CleanerCustomerNew.setApt(WebParamUtil.getStringValue(_CleanerCustomerForm.getApt()));
            m_logger.debug("setting Apt=" +_CleanerCustomerForm.getApt());


            _CleanerCustomerNew.setCity(WebParamUtil.getStringValue(_CleanerCustomerForm.getCity()));
            m_logger.debug("setting City=" +_CleanerCustomerForm.getCity());


            _CleanerCustomerNew.setState(WebParamUtil.getStringValue(_CleanerCustomerForm.getState()));
            m_logger.debug("setting State=" +_CleanerCustomerForm.getState());


            _CleanerCustomerNew.setZip(WebParamUtil.getStringValue(_CleanerCustomerForm.getZip()));
            m_logger.debug("setting Zip=" +_CleanerCustomerForm.getZip());


            _CleanerCustomerNew.setCustomerType(WebParamUtil.getStringValue(_CleanerCustomerForm.getCustomerType()));
            m_logger.debug("setting CustomerType=" +_CleanerCustomerForm.getCustomerType());


            _CleanerCustomerNew.setPayType(WebParamUtil.getStringValue(_CleanerCustomerForm.getPayType()));
            m_logger.debug("setting PayType=" +_CleanerCustomerForm.getPayType());


            _CleanerCustomerNew.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerCustomerForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerCustomerForm.getTimeCreated());

        	_CleanerCustomerNew.setTimeCreated(new TimeNow());

            _CleanerCustomerNew.setRemoteIp(WebParamUtil.getStringValue(_CleanerCustomerForm.getRemoteIp()));
            m_logger.debug("setting RemoteIp=" +_CleanerCustomerForm.getRemoteIp());


            _CleanerCustomerNew.setNote(WebParamUtil.getStringValue(_CleanerCustomerForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerCustomerForm.getNote());


            _CleanerCustomerNew.setPickupNote(WebParamUtil.getStringValue(_CleanerCustomerForm.getPickupNote()));
            m_logger.debug("setting PickupNote=" +_CleanerCustomerForm.getPickupNote());


            _CleanerCustomerNew.setDeliveryNote(WebParamUtil.getStringValue(_CleanerCustomerForm.getDeliveryNote()));
            m_logger.debug("setting DeliveryNote=" +_CleanerCustomerForm.getDeliveryNote());


            _CleanerCustomerNew.setDisabled(WebParamUtil.getIntegerValue(_CleanerCustomerForm.getDisabled()));
            m_logger.debug("setting Disabled=" +_CleanerCustomerForm.getDisabled());


            _CleanerCustomerNew.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerCustomerForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_CleanerCustomerForm.getTimeUpdated());

        	_CleanerCustomerNew.setTimeUpdated(new TimeNow());

            _CleanerCustomerNew.setPickupDeliveryDisallowed(WebParamUtil.getIntegerValue(_CleanerCustomerForm.getPickupDeliveryDisallowed()));
            m_logger.debug("setting PickupDeliveryDisallowed=" +_CleanerCustomerForm.getPickupDeliveryDisallowed());


            _CleanerCustomerNew.setHandleInst(WebParamUtil.getStringValue(_CleanerCustomerForm.getHandleInst()));
            m_logger.debug("setting HandleInst=" +_CleanerCustomerForm.getHandleInst());


			}

            try {
                checkDepedenceIntegrity(_CleanerCustomerNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerCustomerNew);
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
            
            if (_CleanerCustomerNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerCustomerNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerCustomerNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerCustomerNew);
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
             _CleanerCustomer =  _CleanerCustomerNew;
            

        } else if ( hasRequestValue(request, "synch", "true")  || hasRequestValue(request, "cmd", "synch") ) {
            if (!haveAccessToCommand(session, "synch" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "synch", _CleanerCustomer);
                if (returnObjects != null &&  _CleanerCustomer!= null ) returnObjects.put("target", _CleanerCustomer);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "synch", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "synch", "error"));
                return mapping.findForward("default");
            }

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerCustomer, "cleaner-customer" );



        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerCustomerForm _CleanerCustomerForm, CleanerCustomer _CleanerCustomer) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomer _CleanerCustomer = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerCustomerDS.objectToString(_CleanerCustomer));

        _CleanerCustomer.setAutositeUserId(WebParamUtil.getLongValue(_CleanerCustomerForm.getAutositeUserId()));


        _CleanerCustomer.setEmail(WebParamUtil.getStringValue(_CleanerCustomerForm.getEmail()));


        _CleanerCustomer.setPhone(WebParamUtil.getStringValue(_CleanerCustomerForm.getPhone()));


        _CleanerCustomer.setPhone2(WebParamUtil.getStringValue(_CleanerCustomerForm.getPhone2()));


        _CleanerCustomer.setPhonePreferred(WebParamUtil.getIntegerValue(_CleanerCustomerForm.getPhonePreferred()));


        _CleanerCustomer.setTitle(WebParamUtil.getStringValue(_CleanerCustomerForm.getTitle()));


        _CleanerCustomer.setLastName(WebParamUtil.getStringValue(_CleanerCustomerForm.getLastName()));


        _CleanerCustomer.setFirstName(WebParamUtil.getStringValue(_CleanerCustomerForm.getFirstName()));


        _CleanerCustomer.setAddress(WebParamUtil.getStringValue(_CleanerCustomerForm.getAddress()));


        _CleanerCustomer.setApt(WebParamUtil.getStringValue(_CleanerCustomerForm.getApt()));


        _CleanerCustomer.setCity(WebParamUtil.getStringValue(_CleanerCustomerForm.getCity()));


        _CleanerCustomer.setState(WebParamUtil.getStringValue(_CleanerCustomerForm.getState()));


        _CleanerCustomer.setZip(WebParamUtil.getStringValue(_CleanerCustomerForm.getZip()));


        _CleanerCustomer.setCustomerType(WebParamUtil.getStringValue(_CleanerCustomerForm.getCustomerType()));


        _CleanerCustomer.setPayType(WebParamUtil.getStringValue(_CleanerCustomerForm.getPayType()));




        _CleanerCustomer.setRemoteIp(WebParamUtil.getStringValue(_CleanerCustomerForm.getRemoteIp()));


        _CleanerCustomer.setNote(WebParamUtil.getStringValue(_CleanerCustomerForm.getNote()));


        _CleanerCustomer.setPickupNote(WebParamUtil.getStringValue(_CleanerCustomerForm.getPickupNote()));


        _CleanerCustomer.setDeliveryNote(WebParamUtil.getStringValue(_CleanerCustomerForm.getDeliveryNote()));


        _CleanerCustomer.setDisabled(WebParamUtil.getIntegerValue(_CleanerCustomerForm.getDisabled()));


        _CleanerCustomer.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerCustomerForm.getTimeUpdated()));

        _CleanerCustomer.setTimeUpdated(new TimeNow());

        _CleanerCustomer.setPickupDeliveryDisallowed(WebParamUtil.getIntegerValue(_CleanerCustomerForm.getPickupDeliveryDisallowed()));


        _CleanerCustomer.setHandleInst(WebParamUtil.getStringValue(_CleanerCustomerForm.getHandleInst()));



        m_actionExtent.beforeUpdate(request, response, _CleanerCustomer);
        m_ds.update(_CleanerCustomer);
        m_actionExtent.afterUpdate(request, response, _CleanerCustomer);
        m_logger.debug("After update " + CleanerCustomerDS.objectToString(_CleanerCustomer));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerCustomer _CleanerCustomer) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomer _CleanerCustomer = m_ds.getById(cid);

        if (!isMissing(request.getParameter("autositeUserId"))) {
            m_logger.debug("updating param autositeUserId from " +_CleanerCustomer.getAutositeUserId() + "->" + request.getParameter("autositeUserId"));
            _CleanerCustomer.setAutositeUserId(WebParamUtil.getLongValue(request.getParameter("autositeUserId")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_CleanerCustomer.getEmail() + "->" + request.getParameter("email"));
            _CleanerCustomer.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_CleanerCustomer.getPhone() + "->" + request.getParameter("phone"));
            _CleanerCustomer.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));

        }
        if (!isMissing(request.getParameter("phone2"))) {
            m_logger.debug("updating param phone2 from " +_CleanerCustomer.getPhone2() + "->" + request.getParameter("phone2"));
            _CleanerCustomer.setPhone2(WebParamUtil.getStringValue(request.getParameter("phone2")));

        }
        if (!isMissing(request.getParameter("phonePreferred"))) {
            m_logger.debug("updating param phonePreferred from " +_CleanerCustomer.getPhonePreferred() + "->" + request.getParameter("phonePreferred"));
            _CleanerCustomer.setPhonePreferred(WebParamUtil.getIntegerValue(request.getParameter("phonePreferred")));

        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_CleanerCustomer.getTitle() + "->" + request.getParameter("title"));
            _CleanerCustomer.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_CleanerCustomer.getLastName() + "->" + request.getParameter("lastName"));
            _CleanerCustomer.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));

        }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_CleanerCustomer.getFirstName() + "->" + request.getParameter("firstName"));
            _CleanerCustomer.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));

        }
        if (!isMissing(request.getParameter("address"))) {
            m_logger.debug("updating param address from " +_CleanerCustomer.getAddress() + "->" + request.getParameter("address"));
            _CleanerCustomer.setAddress(WebParamUtil.getStringValue(request.getParameter("address")));

        }
        if (!isMissing(request.getParameter("apt"))) {
            m_logger.debug("updating param apt from " +_CleanerCustomer.getApt() + "->" + request.getParameter("apt"));
            _CleanerCustomer.setApt(WebParamUtil.getStringValue(request.getParameter("apt")));

        }
        if (!isMissing(request.getParameter("city"))) {
            m_logger.debug("updating param city from " +_CleanerCustomer.getCity() + "->" + request.getParameter("city"));
            _CleanerCustomer.setCity(WebParamUtil.getStringValue(request.getParameter("city")));

        }
        if (!isMissing(request.getParameter("state"))) {
            m_logger.debug("updating param state from " +_CleanerCustomer.getState() + "->" + request.getParameter("state"));
            _CleanerCustomer.setState(WebParamUtil.getStringValue(request.getParameter("state")));

        }
        if (!isMissing(request.getParameter("zip"))) {
            m_logger.debug("updating param zip from " +_CleanerCustomer.getZip() + "->" + request.getParameter("zip"));
            _CleanerCustomer.setZip(WebParamUtil.getStringValue(request.getParameter("zip")));

        }
        if (!isMissing(request.getParameter("customerType"))) {
            m_logger.debug("updating param customerType from " +_CleanerCustomer.getCustomerType() + "->" + request.getParameter("customerType"));
            _CleanerCustomer.setCustomerType(WebParamUtil.getStringValue(request.getParameter("customerType")));

        }
        if (!isMissing(request.getParameter("payType"))) {
            m_logger.debug("updating param payType from " +_CleanerCustomer.getPayType() + "->" + request.getParameter("payType"));
            _CleanerCustomer.setPayType(WebParamUtil.getStringValue(request.getParameter("payType")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerCustomer.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerCustomer.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("remoteIp"))) {
            m_logger.debug("updating param remoteIp from " +_CleanerCustomer.getRemoteIp() + "->" + request.getParameter("remoteIp"));
            _CleanerCustomer.setRemoteIp(WebParamUtil.getStringValue(request.getParameter("remoteIp")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerCustomer.getNote() + "->" + request.getParameter("note"));
            _CleanerCustomer.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("pickupNote"))) {
            m_logger.debug("updating param pickupNote from " +_CleanerCustomer.getPickupNote() + "->" + request.getParameter("pickupNote"));
            _CleanerCustomer.setPickupNote(WebParamUtil.getStringValue(request.getParameter("pickupNote")));

        }
        if (!isMissing(request.getParameter("deliveryNote"))) {
            m_logger.debug("updating param deliveryNote from " +_CleanerCustomer.getDeliveryNote() + "->" + request.getParameter("deliveryNote"));
            _CleanerCustomer.setDeliveryNote(WebParamUtil.getStringValue(request.getParameter("deliveryNote")));

        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_CleanerCustomer.getDisabled() + "->" + request.getParameter("disabled"));
            _CleanerCustomer.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerCustomer.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerCustomer.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerCustomer.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("pickupDeliveryDisallowed"))) {
            m_logger.debug("updating param pickupDeliveryDisallowed from " +_CleanerCustomer.getPickupDeliveryDisallowed() + "->" + request.getParameter("pickupDeliveryDisallowed"));
            _CleanerCustomer.setPickupDeliveryDisallowed(WebParamUtil.getIntegerValue(request.getParameter("pickupDeliveryDisallowed")));

        }
        if (!isMissing(request.getParameter("handleInst"))) {
            m_logger.debug("updating param handleInst from " +_CleanerCustomer.getHandleInst() + "->" + request.getParameter("handleInst"));
            _CleanerCustomer.setHandleInst(WebParamUtil.getStringValue(request.getParameter("handleInst")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerCustomer);
        m_ds.update(_CleanerCustomer);
        m_actionExtent.afterUpdate(request, response, _CleanerCustomer);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerCustomer _CleanerCustomer) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomer _CleanerCustomer = m_ds.getById(cid);

        if (!isMissing(request.getParameter("autositeUserId"))) {
            m_logger.debug("updating param autositeUserId from " +_CleanerCustomer.getAutositeUserId() + "->" + request.getParameter("autositeUserId"));
            _CleanerCustomer.setAutositeUserId(WebParamUtil.getLongValue(request.getParameter("autositeUserId")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_CleanerCustomer.getEmail() + "->" + request.getParameter("email"));
            _CleanerCustomer.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_CleanerCustomer.getPhone() + "->" + request.getParameter("phone"));
            _CleanerCustomer.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));

        }
        if (!isMissing(request.getParameter("phone2"))) {
            m_logger.debug("updating param phone2 from " +_CleanerCustomer.getPhone2() + "->" + request.getParameter("phone2"));
            _CleanerCustomer.setPhone2(WebParamUtil.getStringValue(request.getParameter("phone2")));

        }
        if (!isMissing(request.getParameter("phonePreferred"))) {
            m_logger.debug("updating param phonePreferred from " +_CleanerCustomer.getPhonePreferred() + "->" + request.getParameter("phonePreferred"));
            _CleanerCustomer.setPhonePreferred(WebParamUtil.getIntegerValue(request.getParameter("phonePreferred")));

        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_CleanerCustomer.getTitle() + "->" + request.getParameter("title"));
            _CleanerCustomer.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_CleanerCustomer.getLastName() + "->" + request.getParameter("lastName"));
            _CleanerCustomer.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));

        }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_CleanerCustomer.getFirstName() + "->" + request.getParameter("firstName"));
            _CleanerCustomer.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));

        }
        if (!isMissing(request.getParameter("address"))) {
            m_logger.debug("updating param address from " +_CleanerCustomer.getAddress() + "->" + request.getParameter("address"));
            _CleanerCustomer.setAddress(WebParamUtil.getStringValue(request.getParameter("address")));

        }
        if (!isMissing(request.getParameter("apt"))) {
            m_logger.debug("updating param apt from " +_CleanerCustomer.getApt() + "->" + request.getParameter("apt"));
            _CleanerCustomer.setApt(WebParamUtil.getStringValue(request.getParameter("apt")));

        }
        if (!isMissing(request.getParameter("city"))) {
            m_logger.debug("updating param city from " +_CleanerCustomer.getCity() + "->" + request.getParameter("city"));
            _CleanerCustomer.setCity(WebParamUtil.getStringValue(request.getParameter("city")));

        }
        if (!isMissing(request.getParameter("state"))) {
            m_logger.debug("updating param state from " +_CleanerCustomer.getState() + "->" + request.getParameter("state"));
            _CleanerCustomer.setState(WebParamUtil.getStringValue(request.getParameter("state")));

        }
        if (!isMissing(request.getParameter("zip"))) {
            m_logger.debug("updating param zip from " +_CleanerCustomer.getZip() + "->" + request.getParameter("zip"));
            _CleanerCustomer.setZip(WebParamUtil.getStringValue(request.getParameter("zip")));

        }
        if (!isMissing(request.getParameter("customerType"))) {
            m_logger.debug("updating param customerType from " +_CleanerCustomer.getCustomerType() + "->" + request.getParameter("customerType"));
            _CleanerCustomer.setCustomerType(WebParamUtil.getStringValue(request.getParameter("customerType")));

        }
        if (!isMissing(request.getParameter("payType"))) {
            m_logger.debug("updating param payType from " +_CleanerCustomer.getPayType() + "->" + request.getParameter("payType"));
            _CleanerCustomer.setPayType(WebParamUtil.getStringValue(request.getParameter("payType")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerCustomer.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerCustomer.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("remoteIp"))) {
            m_logger.debug("updating param remoteIp from " +_CleanerCustomer.getRemoteIp() + "->" + request.getParameter("remoteIp"));
            _CleanerCustomer.setRemoteIp(WebParamUtil.getStringValue(request.getParameter("remoteIp")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerCustomer.getNote() + "->" + request.getParameter("note"));
            _CleanerCustomer.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("pickupNote"))) {
            m_logger.debug("updating param pickupNote from " +_CleanerCustomer.getPickupNote() + "->" + request.getParameter("pickupNote"));
            _CleanerCustomer.setPickupNote(WebParamUtil.getStringValue(request.getParameter("pickupNote")));

        }
        if (!isMissing(request.getParameter("deliveryNote"))) {
            m_logger.debug("updating param deliveryNote from " +_CleanerCustomer.getDeliveryNote() + "->" + request.getParameter("deliveryNote"));
            _CleanerCustomer.setDeliveryNote(WebParamUtil.getStringValue(request.getParameter("deliveryNote")));

        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_CleanerCustomer.getDisabled() + "->" + request.getParameter("disabled"));
            _CleanerCustomer.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerCustomer.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerCustomer.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerCustomer.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("pickupDeliveryDisallowed"))) {
            m_logger.debug("updating param pickupDeliveryDisallowed from " +_CleanerCustomer.getPickupDeliveryDisallowed() + "->" + request.getParameter("pickupDeliveryDisallowed"));
            _CleanerCustomer.setPickupDeliveryDisallowed(WebParamUtil.getIntegerValue(request.getParameter("pickupDeliveryDisallowed")));

        }
        if (!isMissing(request.getParameter("handleInst"))) {
            m_logger.debug("updating param handleInst from " +_CleanerCustomer.getHandleInst() + "->" + request.getParameter("handleInst"));
            _CleanerCustomer.setHandleInst(WebParamUtil.getStringValue(request.getParameter("handleInst")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerCustomer _CleanerCustomer) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomer _CleanerCustomer = m_ds.getById(cid);

        if (!isMissing(request.getParameter("autositeUserId"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getAutositeUserId());
        }
        if (!isMissing(request.getParameter("email"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getEmail());
        }
        if (!isMissing(request.getParameter("phone"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getPhone());
        }
        if (!isMissing(request.getParameter("phone2"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getPhone2());
        }
        if (!isMissing(request.getParameter("phonePreferred"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getPhonePreferred());
        }
        if (!isMissing(request.getParameter("title"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getTitle());
        }
        if (!isMissing(request.getParameter("lastName"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getLastName());
        }
        if (!isMissing(request.getParameter("firstName"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getFirstName());
        }
        if (!isMissing(request.getParameter("address"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getAddress());
        }
        if (!isMissing(request.getParameter("apt"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getApt());
        }
        if (!isMissing(request.getParameter("city"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getCity());
        }
        if (!isMissing(request.getParameter("state"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getState());
        }
        if (!isMissing(request.getParameter("zip"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getZip());
        }
        if (!isMissing(request.getParameter("customerType"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getCustomerType());
        }
        if (!isMissing(request.getParameter("payType"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getPayType());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getTimeCreated());
        }
        if (!isMissing(request.getParameter("remoteIp"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getRemoteIp());
        }
        if (!isMissing(request.getParameter("note"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getNote());
        }
        if (!isMissing(request.getParameter("pickupNote"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getPickupNote());
        }
        if (!isMissing(request.getParameter("deliveryNote"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getDeliveryNote());
        }
        if (!isMissing(request.getParameter("disabled"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getDisabled());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getTimeUpdated());
        }
        if (!isMissing(request.getParameter("pickupDeliveryDisallowed"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getPickupDeliveryDisallowed());
        }
        if (!isMissing(request.getParameter("handleInst"))) {
			return  JtStringUtil.valueOf(_CleanerCustomer.getHandleInst());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerCustomer _CleanerCustomer) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerCustomer _CleanerCustomer) {
        if (fieldName == null || fieldName.equals("")|| _CleanerCustomer == null) return null;
        
        if (fieldName.equals("autositeUserId")) {
            return WebUtil.display(_CleanerCustomer.getAutositeUserId());
        }
        if (fieldName.equals("email")) {
            return WebUtil.display(_CleanerCustomer.getEmail());
        }
        if (fieldName.equals("phone")) {
            return WebUtil.display(_CleanerCustomer.getPhone());
        }
        if (fieldName.equals("phone2")) {
            return WebUtil.display(_CleanerCustomer.getPhone2());
        }
        if (fieldName.equals("phonePreferred")) {
            return WebUtil.display(_CleanerCustomer.getPhonePreferred());
        }
        if (fieldName.equals("title")) {
            return WebUtil.display(_CleanerCustomer.getTitle());
        }
        if (fieldName.equals("lastName")) {
            return WebUtil.display(_CleanerCustomer.getLastName());
        }
        if (fieldName.equals("firstName")) {
            return WebUtil.display(_CleanerCustomer.getFirstName());
        }
        if (fieldName.equals("address")) {
            return WebUtil.display(_CleanerCustomer.getAddress());
        }
        if (fieldName.equals("apt")) {
            return WebUtil.display(_CleanerCustomer.getApt());
        }
        if (fieldName.equals("city")) {
            return WebUtil.display(_CleanerCustomer.getCity());
        }
        if (fieldName.equals("state")) {
            return WebUtil.display(_CleanerCustomer.getState());
        }
        if (fieldName.equals("zip")) {
            return WebUtil.display(_CleanerCustomer.getZip());
        }
        if (fieldName.equals("customerType")) {
            return WebUtil.display(_CleanerCustomer.getCustomerType());
        }
        if (fieldName.equals("payType")) {
            return WebUtil.display(_CleanerCustomer.getPayType());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_CleanerCustomer.getTimeCreated());
        }
        if (fieldName.equals("remoteIp")) {
            return WebUtil.display(_CleanerCustomer.getRemoteIp());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_CleanerCustomer.getNote());
        }
        if (fieldName.equals("pickupNote")) {
            return WebUtil.display(_CleanerCustomer.getPickupNote());
        }
        if (fieldName.equals("deliveryNote")) {
            return WebUtil.display(_CleanerCustomer.getDeliveryNote());
        }
        if (fieldName.equals("disabled")) {
            return WebUtil.display(_CleanerCustomer.getDisabled());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_CleanerCustomer.getTimeUpdated());
        }
        if (fieldName.equals("pickupDeliveryDisallowed")) {
            return WebUtil.display(_CleanerCustomer.getPickupDeliveryDisallowed());
        }
        if (fieldName.equals("handleInst")) {
            return WebUtil.display(_CleanerCustomer.getHandleInst());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerCustomerForm _CleanerCustomerForm = (CleanerCustomerForm) form;

		if(requestParams.containsKey("autositeUserId"))
			_CleanerCustomerForm.setAutositeUserId((String)requestParams.get("autositeUserId"));
		if(requestParams.containsKey("email"))
			_CleanerCustomerForm.setEmail((String)requestParams.get("email"));
		if(requestParams.containsKey("phone"))
			_CleanerCustomerForm.setPhone((String)requestParams.get("phone"));
		if(requestParams.containsKey("phone2"))
			_CleanerCustomerForm.setPhone2((String)requestParams.get("phone2"));
		if(requestParams.containsKey("phonePreferred"))
			_CleanerCustomerForm.setPhonePreferred((String)requestParams.get("phonePreferred"));
		if(requestParams.containsKey("title"))
			_CleanerCustomerForm.setTitle((String)requestParams.get("title"));
		if(requestParams.containsKey("lastName"))
			_CleanerCustomerForm.setLastName((String)requestParams.get("lastName"));
		if(requestParams.containsKey("firstName"))
			_CleanerCustomerForm.setFirstName((String)requestParams.get("firstName"));
		if(requestParams.containsKey("address"))
			_CleanerCustomerForm.setAddress((String)requestParams.get("address"));
		if(requestParams.containsKey("apt"))
			_CleanerCustomerForm.setApt((String)requestParams.get("apt"));
		if(requestParams.containsKey("city"))
			_CleanerCustomerForm.setCity((String)requestParams.get("city"));
		if(requestParams.containsKey("state"))
			_CleanerCustomerForm.setState((String)requestParams.get("state"));
		if(requestParams.containsKey("zip"))
			_CleanerCustomerForm.setZip((String)requestParams.get("zip"));
		if(requestParams.containsKey("customerType"))
			_CleanerCustomerForm.setCustomerType((String)requestParams.get("customerType"));
		if(requestParams.containsKey("payType"))
			_CleanerCustomerForm.setPayType((String)requestParams.get("payType"));
		if(requestParams.containsKey("timeCreated"))
			_CleanerCustomerForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("remoteIp"))
			_CleanerCustomerForm.setRemoteIp((String)requestParams.get("remoteIp"));
		if(requestParams.containsKey("note"))
			_CleanerCustomerForm.setNote((String)requestParams.get("note"));
		if(requestParams.containsKey("pickupNote"))
			_CleanerCustomerForm.setPickupNote((String)requestParams.get("pickupNote"));
		if(requestParams.containsKey("deliveryNote"))
			_CleanerCustomerForm.setDeliveryNote((String)requestParams.get("deliveryNote"));
		if(requestParams.containsKey("disabled"))
			_CleanerCustomerForm.setDisabled((String)requestParams.get("disabled"));
		if(requestParams.containsKey("timeUpdated"))
			_CleanerCustomerForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
		if(requestParams.containsKey("pickupDeliveryDisallowed"))
			_CleanerCustomerForm.setPickupDeliveryDisallowed((String)requestParams.get("pickupDeliveryDisallowed"));
		if(requestParams.containsKey("handleInst"))
			_CleanerCustomerForm.setHandleInst((String)requestParams.get("handleInst"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_customer_home=NULL,/jsp/form_cleaner/cleanerCustomer_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_customer_list=NULL,/jsp/form_cleaner/cleanerCustomer_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_customer_form=NULL,/jsp/form_cleaner/cleanerCustomer_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_customer_ajax=NULL,/jsp/form_cleaner/cleanerCustomer_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_home=NULL,/jsp/form_cleaner/cleanerCustomer_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_list=NULL,/jsp/form_cleaner/cleanerCustomer_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_form=NULL,/jsp/form_cleaner/cleanerCustomer_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_ajax=NULL,/jsp/form_cleaner/cleanerCustomer_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerCustomerDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
