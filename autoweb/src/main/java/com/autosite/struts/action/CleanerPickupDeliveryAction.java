/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:44 EDT 2015

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

import com.autosite.db.CleanerPickupDelivery;
import com.autosite.ds.CleanerPickupDeliveryDS;
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
import com.autosite.struts.form.CleanerPickupDeliveryForm;
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


import com.autosite.holder.CleanerPickupDeliveryDataHolder;

/*
Generated: Sun Mar 15 13:54:44 EDT 2015
*/

public class CleanerPickupDeliveryAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerPickupDeliveryAction.class);

    public CleanerPickupDeliveryAction(){
        m_ds = CleanerPickupDeliveryDS.getInstance();
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

        CleanerPickupDeliveryForm _CleanerPickupDeliveryForm = (CleanerPickupDeliveryForm) form;
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
        CleanerPickupDelivery _CleanerPickupDelivery = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerPickupDelivery = m_ds.getById(cid);
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
            //CleanerPickupDelivery _CleanerPickupDelivery = m_ds.getById(cid);

            if (_CleanerPickupDelivery == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerPickupDelivery.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDelivery.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerPickupDelivery);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerPickupDeliveryForm == null) {
    	            editField(request, response, _CleanerPickupDelivery);
				} else {
    	            edit(request, response, _CleanerPickupDeliveryForm, _CleanerPickupDelivery);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerPickupDelivery);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerPickupDelivery o = m_ds.getById( _CleanerPickupDelivery.getId(), true);

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
            //CleanerPickupDelivery _CleanerPickupDelivery = m_ds.getById(cid);

            if (_CleanerPickupDelivery == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerPickupDelivery.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDelivery.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerPickupDelivery);
                if (returnObjects != null) returnObjects.put("target", _CleanerPickupDelivery);
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
            //CleanerPickupDelivery _CleanerPickupDelivery = m_ds.getById(cid);

            if (_CleanerPickupDelivery == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerPickupDelivery.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDelivery.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerPickupDelivery);
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

            m_ds.delete(_CleanerPickupDelivery); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerPickupDelivery);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerPickupDelivery);
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


            m_logger.info("Creating new CleanerPickupDelivery" );
            CleanerPickupDelivery _CleanerPickupDeliveryNew = new CleanerPickupDelivery();   

            // Setting IDs for the object
            _CleanerPickupDeliveryNew.setSiteId(site.getId());
			
            if ( _CleanerPickupDeliveryForm == null) {
                setFields(request, response, _CleanerPickupDeliveryNew);
            } else {

            _CleanerPickupDeliveryNew.setLocationId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getLocationId()));
            m_logger.debug("setting LocationId=" +_CleanerPickupDeliveryForm.getLocationId());


            _CleanerPickupDeliveryNew.setCustomerId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getCustomerId()));
            m_logger.debug("setting CustomerId=" +_CleanerPickupDeliveryForm.getCustomerId());


            _CleanerPickupDeliveryNew.setTicketId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getTicketId()));
            m_logger.debug("setting TicketId=" +_CleanerPickupDeliveryForm.getTicketId());


            _CleanerPickupDeliveryNew.setTicketUid(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getTicketUid()));
            m_logger.debug("setting TicketUid=" +_CleanerPickupDeliveryForm.getTicketUid());


            _CleanerPickupDeliveryNew.setPickupTicket(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupTicket()));
            m_logger.debug("setting PickupTicket=" +_CleanerPickupDeliveryForm.getPickupTicket());


            _CleanerPickupDeliveryNew.setCheckinTicketForDelivery(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCheckinTicketForDelivery()));
            m_logger.debug("setting CheckinTicketForDelivery=" +_CleanerPickupDeliveryForm.getCheckinTicketForDelivery());


            _CleanerPickupDeliveryNew.setIsDeliveryRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsDeliveryRequest()));
            m_logger.debug("setting IsDeliveryRequest=" +_CleanerPickupDeliveryForm.getIsDeliveryRequest());


            _CleanerPickupDeliveryNew.setIsWebRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsWebRequest()));
            m_logger.debug("setting IsWebRequest=" +_CleanerPickupDeliveryForm.getIsWebRequest());


            _CleanerPickupDeliveryNew.setIsRecurringRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsRecurringRequest()));
            m_logger.debug("setting IsRecurringRequest=" +_CleanerPickupDeliveryForm.getIsRecurringRequest());


            _CleanerPickupDeliveryNew.setIsReceiveReady(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsReceiveReady()));
            m_logger.debug("setting IsReceiveReady=" +_CleanerPickupDeliveryForm.getIsReceiveReady());


            _CleanerPickupDeliveryNew.setIsReceiveComplete(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsReceiveComplete()));
            m_logger.debug("setting IsReceiveComplete=" +_CleanerPickupDeliveryForm.getIsReceiveComplete());


            _CleanerPickupDeliveryNew.setRecurId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getRecurId()));
            m_logger.debug("setting RecurId=" +_CleanerPickupDeliveryForm.getRecurId());


            _CleanerPickupDeliveryNew.setCancelled(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getCancelled()));
            m_logger.debug("setting Cancelled=" +_CleanerPickupDeliveryForm.getCancelled());


            _CleanerPickupDeliveryNew.setCompleted(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getCompleted()));
            m_logger.debug("setting Completed=" +_CleanerPickupDeliveryForm.getCompleted());


            _CleanerPickupDeliveryNew.setCustomerName(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCustomerName()));
            m_logger.debug("setting CustomerName=" +_CleanerPickupDeliveryForm.getCustomerName());


            _CleanerPickupDeliveryNew.setAddress(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getAddress()));
            m_logger.debug("setting Address=" +_CleanerPickupDeliveryForm.getAddress());


            _CleanerPickupDeliveryNew.setAptNumber(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getAptNumber()));
            m_logger.debug("setting AptNumber=" +_CleanerPickupDeliveryForm.getAptNumber());


            _CleanerPickupDeliveryNew.setPhone(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPhone()));
            m_logger.debug("setting Phone=" +_CleanerPickupDeliveryForm.getPhone());


            _CleanerPickupDeliveryNew.setEmail(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getEmail()));
            m_logger.debug("setting Email=" +_CleanerPickupDeliveryForm.getEmail());


            _CleanerPickupDeliveryNew.setAckReceiveMethod(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getAckReceiveMethod()));
            m_logger.debug("setting AckReceiveMethod=" +_CleanerPickupDeliveryForm.getAckReceiveMethod());


            _CleanerPickupDeliveryNew.setCustomerInstruction(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCustomerInstruction()));
            m_logger.debug("setting CustomerInstruction=" +_CleanerPickupDeliveryForm.getCustomerInstruction());


            _CleanerPickupDeliveryNew.setPickupDeliveryByDay(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getPickupDeliveryByDay()));
            m_logger.debug("setting PickupDeliveryByDay=" +_CleanerPickupDeliveryForm.getPickupDeliveryByDay());


            _CleanerPickupDeliveryNew.setPickupDeliveryByTime(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupDeliveryByTime()));
            m_logger.debug("setting PickupDeliveryByTime=" +_CleanerPickupDeliveryForm.getPickupDeliveryByTime());


            _CleanerPickupDeliveryNew.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerPickupDeliveryForm.getTimeCreated());

        	_CleanerPickupDeliveryNew.setTimeCreated(new TimeNow());

            _CleanerPickupDeliveryNew.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_CleanerPickupDeliveryForm.getTimeUpdated());

        	_CleanerPickupDeliveryNew.setTimeUpdated(new TimeNow());

            _CleanerPickupDeliveryNew.setTimeAcked(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeAcked()));
            m_logger.debug("setting TimeAcked=" +_CleanerPickupDeliveryForm.getTimeAcked());


            _CleanerPickupDeliveryNew.setAckedByUserId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getAckedByUserId()));
            m_logger.debug("setting AckedByUserId=" +_CleanerPickupDeliveryForm.getAckedByUserId());


            _CleanerPickupDeliveryNew.setTimeReady(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeReady()));
            m_logger.debug("setting TimeReady=" +_CleanerPickupDeliveryForm.getTimeReady());


            _CleanerPickupDeliveryNew.setTimeNotified(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeNotified()));
            m_logger.debug("setting TimeNotified=" +_CleanerPickupDeliveryForm.getTimeNotified());


            _CleanerPickupDeliveryNew.setTimeCompleted(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeCompleted()));
            m_logger.debug("setting TimeCompleted=" +_CleanerPickupDeliveryForm.getTimeCompleted());


            _CleanerPickupDeliveryNew.setNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerPickupDeliveryForm.getNote());


            _CleanerPickupDeliveryNew.setPickupNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupNote()));
            m_logger.debug("setting PickupNote=" +_CleanerPickupDeliveryForm.getPickupNote());


            _CleanerPickupDeliveryNew.setDeliveryNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getDeliveryNote()));
            m_logger.debug("setting DeliveryNote=" +_CleanerPickupDeliveryForm.getDeliveryNote());


			}

            try {
                checkDepedenceIntegrity(_CleanerPickupDeliveryNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerPickupDeliveryNew);
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
            
            if (_CleanerPickupDeliveryNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerPickupDeliveryNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerPickupDeliveryNew);
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
             _CleanerPickupDelivery =  _CleanerPickupDeliveryNew;
            

        } else if ( hasRequestValue(request, "confirmToRequest", "true")  || hasRequestValue(request, "cmd", "confirmToRequest") ) {
            if (!haveAccessToCommand(session, "confirmToRequest" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "confirmToRequest", _CleanerPickupDelivery);
                if (returnObjects != null &&  _CleanerPickupDelivery!= null ) returnObjects.put("target", _CleanerPickupDelivery);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "confirmToRequest", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "confirmToRequest", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "cancelRequest", "true")  || hasRequestValue(request, "cmd", "cancelRequest") ) {
            if (!haveAccessToCommand(session, "cancelRequest" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "cancelRequest", _CleanerPickupDelivery);
                if (returnObjects != null &&  _CleanerPickupDelivery!= null ) returnObjects.put("target", _CleanerPickupDelivery);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "cancelRequest", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "cancelRequest", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "setReadyToRequest", "true")  || hasRequestValue(request, "cmd", "setReadyToRequest") ) {
            if (!haveAccessToCommand(session, "setReadyToRequest" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "setReadyToRequest", _CleanerPickupDelivery);
                if (returnObjects != null &&  _CleanerPickupDelivery!= null ) returnObjects.put("target", _CleanerPickupDelivery);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "setReadyToRequest", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "setReadyToRequest", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "setCompletedToRequest", "true")  || hasRequestValue(request, "cmd", "setCompletedToRequest") ) {
            if (!haveAccessToCommand(session, "setCompletedToRequest" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "setCompletedToRequest", _CleanerPickupDelivery);
                if (returnObjects != null &&  _CleanerPickupDelivery!= null ) returnObjects.put("target", _CleanerPickupDelivery);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "setCompletedToRequest", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "setCompletedToRequest", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "sendCustomNotification", "true")  || hasRequestValue(request, "cmd", "sendCustomNotification") ) {
            if (!haveAccessToCommand(session, "sendCustomNotification" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "sendCustomNotification", _CleanerPickupDelivery);
                if (returnObjects != null &&  _CleanerPickupDelivery!= null ) returnObjects.put("target", _CleanerPickupDelivery);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "sendCustomNotification", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "sendCustomNotification", "error"));
                return mapping.findForward("default");
            }

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerPickupDelivery, "cleaner-pickup-delivery" );


        if ( hasRequestValue(request, "subcmd", "brushUp") ) {
            if (!haveAccessToCommand(session, "brushUp" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "brushUp", _CleanerPickupDelivery);

			} catch (Exception e) {

				// 0508. It is first implementation of sub commands that can do on the top of basic Add/Update/Delete
				// Dont know whether I should return if something happens

	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

				//processPageForAction(request, "brushUp", "error", getErrorPage(), getSentPage(request));
                //setPage(session,
                //        ((ActionExtentException)e).getForwardPage(),
                //        pageManager.isInternalForward(getActionName(), "brushUp", "error"));

                return mapping.findForward("default");
            }
		}

        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new CleanerPickupDelivery();
    }

    protected AbstractDataHolder createModelDataHolder() {
        return new CleanerPickupDeliveryDataHolder();
    }



    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        CleanerPickupDeliveryForm _CleanerPickupDeliveryForm = (CleanerPickupDeliveryForm) form;
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) dataObject;

        m_logger.debug("Before update " + CleanerPickupDeliveryDS.objectToString(_CleanerPickupDelivery));

        _CleanerPickupDelivery.setLocationId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getLocationId()));


        _CleanerPickupDelivery.setCustomerId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getCustomerId()));


        _CleanerPickupDelivery.setTicketId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getTicketId()));


        _CleanerPickupDelivery.setTicketUid(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getTicketUid()));


        _CleanerPickupDelivery.setPickupTicket(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupTicket()));


        _CleanerPickupDelivery.setCheckinTicketForDelivery(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCheckinTicketForDelivery()));


        _CleanerPickupDelivery.setIsDeliveryRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsDeliveryRequest()));


        _CleanerPickupDelivery.setIsWebRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsWebRequest()));


        _CleanerPickupDelivery.setIsRecurringRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsRecurringRequest()));


        _CleanerPickupDelivery.setIsReceiveReady(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsReceiveReady()));


        _CleanerPickupDelivery.setIsReceiveComplete(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsReceiveComplete()));


        _CleanerPickupDelivery.setRecurId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getRecurId()));


        _CleanerPickupDelivery.setCancelled(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getCancelled()));


        _CleanerPickupDelivery.setCompleted(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getCompleted()));


        _CleanerPickupDelivery.setCustomerName(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCustomerName()));


        _CleanerPickupDelivery.setAddress(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getAddress()));


        _CleanerPickupDelivery.setAptNumber(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getAptNumber()));


        _CleanerPickupDelivery.setPhone(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPhone()));


        _CleanerPickupDelivery.setEmail(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getEmail()));


        _CleanerPickupDelivery.setAckReceiveMethod(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getAckReceiveMethod()));


        _CleanerPickupDelivery.setCustomerInstruction(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCustomerInstruction()));


        _CleanerPickupDelivery.setPickupDeliveryByDay(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getPickupDeliveryByDay()));


        _CleanerPickupDelivery.setPickupDeliveryByTime(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupDeliveryByTime()));




        _CleanerPickupDelivery.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeUpdated()));

        _CleanerPickupDelivery.setTimeUpdated(new TimeNow());

        _CleanerPickupDelivery.setTimeAcked(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeAcked()));


        _CleanerPickupDelivery.setAckedByUserId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getAckedByUserId()));


        _CleanerPickupDelivery.setTimeReady(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeReady()));


        _CleanerPickupDelivery.setTimeNotified(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeNotified()));


        _CleanerPickupDelivery.setTimeCompleted(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeCompleted()));


        _CleanerPickupDelivery.setNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getNote()));


        _CleanerPickupDelivery.setPickupNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupNote()));


        _CleanerPickupDelivery.setDeliveryNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getDeliveryNote()));



        m_actionExtent.beforeUpdate(request, response, _CleanerPickupDelivery);
        m_ds.update(_CleanerPickupDelivery);
        m_actionExtent.afterUpdate(request, response, _CleanerPickupDelivery);
        m_logger.debug("After update " + CleanerPickupDeliveryDS.objectToString(_CleanerPickupDelivery));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) dataObject;

        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerPickupDelivery.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerPickupDelivery.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("customerId"))) {
            m_logger.debug("updating param customerId from " +_CleanerPickupDelivery.getCustomerId() + "->" + request.getParameter("customerId"));
            _CleanerPickupDelivery.setCustomerId(WebParamUtil.getLongValue(request.getParameter("customerId")));

        }
        if (!isMissing(request.getParameter("ticketId"))) {
            m_logger.debug("updating param ticketId from " +_CleanerPickupDelivery.getTicketId() + "->" + request.getParameter("ticketId"));
            _CleanerPickupDelivery.setTicketId(WebParamUtil.getLongValue(request.getParameter("ticketId")));

        }
        if (!isMissing(request.getParameter("ticketUid"))) {
            m_logger.debug("updating param ticketUid from " +_CleanerPickupDelivery.getTicketUid() + "->" + request.getParameter("ticketUid"));
            _CleanerPickupDelivery.setTicketUid(WebParamUtil.getStringValue(request.getParameter("ticketUid")));

        }
        if (!isMissing(request.getParameter("pickupTicket"))) {
            m_logger.debug("updating param pickupTicket from " +_CleanerPickupDelivery.getPickupTicket() + "->" + request.getParameter("pickupTicket"));
            _CleanerPickupDelivery.setPickupTicket(WebParamUtil.getStringValue(request.getParameter("pickupTicket")));

        }
        if (!isMissing(request.getParameter("checkinTicketForDelivery"))) {
            m_logger.debug("updating param checkinTicketForDelivery from " +_CleanerPickupDelivery.getCheckinTicketForDelivery() + "->" + request.getParameter("checkinTicketForDelivery"));
            _CleanerPickupDelivery.setCheckinTicketForDelivery(WebParamUtil.getStringValue(request.getParameter("checkinTicketForDelivery")));

        }
        if (!isMissing(request.getParameter("isDeliveryRequest"))) {
            m_logger.debug("updating param isDeliveryRequest from " +_CleanerPickupDelivery.getIsDeliveryRequest() + "->" + request.getParameter("isDeliveryRequest"));
            _CleanerPickupDelivery.setIsDeliveryRequest(WebParamUtil.getIntegerValue(request.getParameter("isDeliveryRequest")));

        }
        if (!isMissing(request.getParameter("isWebRequest"))) {
            m_logger.debug("updating param isWebRequest from " +_CleanerPickupDelivery.getIsWebRequest() + "->" + request.getParameter("isWebRequest"));
            _CleanerPickupDelivery.setIsWebRequest(WebParamUtil.getIntegerValue(request.getParameter("isWebRequest")));

        }
        if (!isMissing(request.getParameter("isRecurringRequest"))) {
            m_logger.debug("updating param isRecurringRequest from " +_CleanerPickupDelivery.getIsRecurringRequest() + "->" + request.getParameter("isRecurringRequest"));
            _CleanerPickupDelivery.setIsRecurringRequest(WebParamUtil.getIntegerValue(request.getParameter("isRecurringRequest")));

        }
        if (!isMissing(request.getParameter("isReceiveReady"))) {
            m_logger.debug("updating param isReceiveReady from " +_CleanerPickupDelivery.getIsReceiveReady() + "->" + request.getParameter("isReceiveReady"));
            _CleanerPickupDelivery.setIsReceiveReady(WebParamUtil.getIntegerValue(request.getParameter("isReceiveReady")));

        }
        if (!isMissing(request.getParameter("isReceiveComplete"))) {
            m_logger.debug("updating param isReceiveComplete from " +_CleanerPickupDelivery.getIsReceiveComplete() + "->" + request.getParameter("isReceiveComplete"));
            _CleanerPickupDelivery.setIsReceiveComplete(WebParamUtil.getIntegerValue(request.getParameter("isReceiveComplete")));

        }
        if (!isMissing(request.getParameter("recurId"))) {
            m_logger.debug("updating param recurId from " +_CleanerPickupDelivery.getRecurId() + "->" + request.getParameter("recurId"));
            _CleanerPickupDelivery.setRecurId(WebParamUtil.getLongValue(request.getParameter("recurId")));

        }
        if (!isMissing(request.getParameter("cancelled"))) {
            m_logger.debug("updating param cancelled from " +_CleanerPickupDelivery.getCancelled() + "->" + request.getParameter("cancelled"));
            _CleanerPickupDelivery.setCancelled(WebParamUtil.getIntegerValue(request.getParameter("cancelled")));

        }
        if (!isMissing(request.getParameter("completed"))) {
            m_logger.debug("updating param completed from " +_CleanerPickupDelivery.getCompleted() + "->" + request.getParameter("completed"));
            _CleanerPickupDelivery.setCompleted(WebParamUtil.getIntegerValue(request.getParameter("completed")));

        }
        if (!isMissing(request.getParameter("customerName"))) {
            m_logger.debug("updating param customerName from " +_CleanerPickupDelivery.getCustomerName() + "->" + request.getParameter("customerName"));
            _CleanerPickupDelivery.setCustomerName(WebParamUtil.getStringValue(request.getParameter("customerName")));

        }
        if (!isMissing(request.getParameter("address"))) {
            m_logger.debug("updating param address from " +_CleanerPickupDelivery.getAddress() + "->" + request.getParameter("address"));
            _CleanerPickupDelivery.setAddress(WebParamUtil.getStringValue(request.getParameter("address")));

        }
        if (!isMissing(request.getParameter("aptNumber"))) {
            m_logger.debug("updating param aptNumber from " +_CleanerPickupDelivery.getAptNumber() + "->" + request.getParameter("aptNumber"));
            _CleanerPickupDelivery.setAptNumber(WebParamUtil.getStringValue(request.getParameter("aptNumber")));

        }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_CleanerPickupDelivery.getPhone() + "->" + request.getParameter("phone"));
            _CleanerPickupDelivery.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_CleanerPickupDelivery.getEmail() + "->" + request.getParameter("email"));
            _CleanerPickupDelivery.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("ackReceiveMethod"))) {
            m_logger.debug("updating param ackReceiveMethod from " +_CleanerPickupDelivery.getAckReceiveMethod() + "->" + request.getParameter("ackReceiveMethod"));
            _CleanerPickupDelivery.setAckReceiveMethod(WebParamUtil.getIntegerValue(request.getParameter("ackReceiveMethod")));

        }
        if (!isMissing(request.getParameter("customerInstruction"))) {
            m_logger.debug("updating param customerInstruction from " +_CleanerPickupDelivery.getCustomerInstruction() + "->" + request.getParameter("customerInstruction"));
            _CleanerPickupDelivery.setCustomerInstruction(WebParamUtil.getStringValue(request.getParameter("customerInstruction")));

        }
        if (!isMissing(request.getParameter("pickupDeliveryByDay"))) {
            m_logger.debug("updating param pickupDeliveryByDay from " +_CleanerPickupDelivery.getPickupDeliveryByDay() + "->" + request.getParameter("pickupDeliveryByDay"));
            _CleanerPickupDelivery.setPickupDeliveryByDay(WebParamUtil.getIntegerValue(request.getParameter("pickupDeliveryByDay")));

        }
        if (!isMissing(request.getParameter("pickupDeliveryByTime"))) {
            m_logger.debug("updating param pickupDeliveryByTime from " +_CleanerPickupDelivery.getPickupDeliveryByTime() + "->" + request.getParameter("pickupDeliveryByTime"));
            _CleanerPickupDelivery.setPickupDeliveryByTime(WebParamUtil.getStringValue(request.getParameter("pickupDeliveryByTime")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerPickupDelivery.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerPickupDelivery.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerPickupDelivery.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerPickupDelivery.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerPickupDelivery.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("timeAcked"))) {
            m_logger.debug("updating param timeAcked from " +_CleanerPickupDelivery.getTimeAcked() + "->" + request.getParameter("timeAcked"));
            _CleanerPickupDelivery.setTimeAcked(WebParamUtil.getTimestampValue(request.getParameter("timeAcked")));

        }
        if (!isMissing(request.getParameter("ackedByUserId"))) {
            m_logger.debug("updating param ackedByUserId from " +_CleanerPickupDelivery.getAckedByUserId() + "->" + request.getParameter("ackedByUserId"));
            _CleanerPickupDelivery.setAckedByUserId(WebParamUtil.getLongValue(request.getParameter("ackedByUserId")));

        }
        if (!isMissing(request.getParameter("timeReady"))) {
            m_logger.debug("updating param timeReady from " +_CleanerPickupDelivery.getTimeReady() + "->" + request.getParameter("timeReady"));
            _CleanerPickupDelivery.setTimeReady(WebParamUtil.getTimestampValue(request.getParameter("timeReady")));

        }
        if (!isMissing(request.getParameter("timeNotified"))) {
            m_logger.debug("updating param timeNotified from " +_CleanerPickupDelivery.getTimeNotified() + "->" + request.getParameter("timeNotified"));
            _CleanerPickupDelivery.setTimeNotified(WebParamUtil.getTimestampValue(request.getParameter("timeNotified")));

        }
        if (!isMissing(request.getParameter("timeCompleted"))) {
            m_logger.debug("updating param timeCompleted from " +_CleanerPickupDelivery.getTimeCompleted() + "->" + request.getParameter("timeCompleted"));
            _CleanerPickupDelivery.setTimeCompleted(WebParamUtil.getTimestampValue(request.getParameter("timeCompleted")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerPickupDelivery.getNote() + "->" + request.getParameter("note"));
            _CleanerPickupDelivery.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("pickupNote"))) {
            m_logger.debug("updating param pickupNote from " +_CleanerPickupDelivery.getPickupNote() + "->" + request.getParameter("pickupNote"));
            _CleanerPickupDelivery.setPickupNote(WebParamUtil.getStringValue(request.getParameter("pickupNote")));

        }
        if (!isMissing(request.getParameter("deliveryNote"))) {
            m_logger.debug("updating param deliveryNote from " +_CleanerPickupDelivery.getDeliveryNote() + "->" + request.getParameter("deliveryNote"));
            _CleanerPickupDelivery.setDeliveryNote(WebParamUtil.getStringValue(request.getParameter("deliveryNote")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerPickupDelivery);
        m_ds.update(_CleanerPickupDelivery);
        m_actionExtent.afterUpdate(request, response, _CleanerPickupDelivery);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) dataObject;

        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerPickupDelivery.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerPickupDelivery.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("customerId"))) {
            m_logger.debug("updating param customerId from " +_CleanerPickupDelivery.getCustomerId() + "->" + request.getParameter("customerId"));
            _CleanerPickupDelivery.setCustomerId(WebParamUtil.getLongValue(request.getParameter("customerId")));

        }
        if (!isMissing(request.getParameter("ticketId"))) {
            m_logger.debug("updating param ticketId from " +_CleanerPickupDelivery.getTicketId() + "->" + request.getParameter("ticketId"));
            _CleanerPickupDelivery.setTicketId(WebParamUtil.getLongValue(request.getParameter("ticketId")));

        }
        if (!isMissing(request.getParameter("ticketUid"))) {
            m_logger.debug("updating param ticketUid from " +_CleanerPickupDelivery.getTicketUid() + "->" + request.getParameter("ticketUid"));
            _CleanerPickupDelivery.setTicketUid(WebParamUtil.getStringValue(request.getParameter("ticketUid")));

        }
        if (!isMissing(request.getParameter("pickupTicket"))) {
            m_logger.debug("updating param pickupTicket from " +_CleanerPickupDelivery.getPickupTicket() + "->" + request.getParameter("pickupTicket"));
            _CleanerPickupDelivery.setPickupTicket(WebParamUtil.getStringValue(request.getParameter("pickupTicket")));

        }
        if (!isMissing(request.getParameter("checkinTicketForDelivery"))) {
            m_logger.debug("updating param checkinTicketForDelivery from " +_CleanerPickupDelivery.getCheckinTicketForDelivery() + "->" + request.getParameter("checkinTicketForDelivery"));
            _CleanerPickupDelivery.setCheckinTicketForDelivery(WebParamUtil.getStringValue(request.getParameter("checkinTicketForDelivery")));

        }
        if (!isMissing(request.getParameter("isDeliveryRequest"))) {
            m_logger.debug("updating param isDeliveryRequest from " +_CleanerPickupDelivery.getIsDeliveryRequest() + "->" + request.getParameter("isDeliveryRequest"));
            _CleanerPickupDelivery.setIsDeliveryRequest(WebParamUtil.getIntegerValue(request.getParameter("isDeliveryRequest")));

        }
        if (!isMissing(request.getParameter("isWebRequest"))) {
            m_logger.debug("updating param isWebRequest from " +_CleanerPickupDelivery.getIsWebRequest() + "->" + request.getParameter("isWebRequest"));
            _CleanerPickupDelivery.setIsWebRequest(WebParamUtil.getIntegerValue(request.getParameter("isWebRequest")));

        }
        if (!isMissing(request.getParameter("isRecurringRequest"))) {
            m_logger.debug("updating param isRecurringRequest from " +_CleanerPickupDelivery.getIsRecurringRequest() + "->" + request.getParameter("isRecurringRequest"));
            _CleanerPickupDelivery.setIsRecurringRequest(WebParamUtil.getIntegerValue(request.getParameter("isRecurringRequest")));

        }
        if (!isMissing(request.getParameter("isReceiveReady"))) {
            m_logger.debug("updating param isReceiveReady from " +_CleanerPickupDelivery.getIsReceiveReady() + "->" + request.getParameter("isReceiveReady"));
            _CleanerPickupDelivery.setIsReceiveReady(WebParamUtil.getIntegerValue(request.getParameter("isReceiveReady")));

        }
        if (!isMissing(request.getParameter("isReceiveComplete"))) {
            m_logger.debug("updating param isReceiveComplete from " +_CleanerPickupDelivery.getIsReceiveComplete() + "->" + request.getParameter("isReceiveComplete"));
            _CleanerPickupDelivery.setIsReceiveComplete(WebParamUtil.getIntegerValue(request.getParameter("isReceiveComplete")));

        }
        if (!isMissing(request.getParameter("recurId"))) {
            m_logger.debug("updating param recurId from " +_CleanerPickupDelivery.getRecurId() + "->" + request.getParameter("recurId"));
            _CleanerPickupDelivery.setRecurId(WebParamUtil.getLongValue(request.getParameter("recurId")));

        }
        if (!isMissing(request.getParameter("cancelled"))) {
            m_logger.debug("updating param cancelled from " +_CleanerPickupDelivery.getCancelled() + "->" + request.getParameter("cancelled"));
            _CleanerPickupDelivery.setCancelled(WebParamUtil.getIntegerValue(request.getParameter("cancelled")));

        }
        if (!isMissing(request.getParameter("completed"))) {
            m_logger.debug("updating param completed from " +_CleanerPickupDelivery.getCompleted() + "->" + request.getParameter("completed"));
            _CleanerPickupDelivery.setCompleted(WebParamUtil.getIntegerValue(request.getParameter("completed")));

        }
        if (!isMissing(request.getParameter("customerName"))) {
            m_logger.debug("updating param customerName from " +_CleanerPickupDelivery.getCustomerName() + "->" + request.getParameter("customerName"));
            _CleanerPickupDelivery.setCustomerName(WebParamUtil.getStringValue(request.getParameter("customerName")));

        }
        if (!isMissing(request.getParameter("address"))) {
            m_logger.debug("updating param address from " +_CleanerPickupDelivery.getAddress() + "->" + request.getParameter("address"));
            _CleanerPickupDelivery.setAddress(WebParamUtil.getStringValue(request.getParameter("address")));

        }
        if (!isMissing(request.getParameter("aptNumber"))) {
            m_logger.debug("updating param aptNumber from " +_CleanerPickupDelivery.getAptNumber() + "->" + request.getParameter("aptNumber"));
            _CleanerPickupDelivery.setAptNumber(WebParamUtil.getStringValue(request.getParameter("aptNumber")));

        }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_CleanerPickupDelivery.getPhone() + "->" + request.getParameter("phone"));
            _CleanerPickupDelivery.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));

        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_CleanerPickupDelivery.getEmail() + "->" + request.getParameter("email"));
            _CleanerPickupDelivery.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));

        }
        if (!isMissing(request.getParameter("ackReceiveMethod"))) {
            m_logger.debug("updating param ackReceiveMethod from " +_CleanerPickupDelivery.getAckReceiveMethod() + "->" + request.getParameter("ackReceiveMethod"));
            _CleanerPickupDelivery.setAckReceiveMethod(WebParamUtil.getIntegerValue(request.getParameter("ackReceiveMethod")));

        }
        if (!isMissing(request.getParameter("customerInstruction"))) {
            m_logger.debug("updating param customerInstruction from " +_CleanerPickupDelivery.getCustomerInstruction() + "->" + request.getParameter("customerInstruction"));
            _CleanerPickupDelivery.setCustomerInstruction(WebParamUtil.getStringValue(request.getParameter("customerInstruction")));

        }
        if (!isMissing(request.getParameter("pickupDeliveryByDay"))) {
            m_logger.debug("updating param pickupDeliveryByDay from " +_CleanerPickupDelivery.getPickupDeliveryByDay() + "->" + request.getParameter("pickupDeliveryByDay"));
            _CleanerPickupDelivery.setPickupDeliveryByDay(WebParamUtil.getIntegerValue(request.getParameter("pickupDeliveryByDay")));

        }
        if (!isMissing(request.getParameter("pickupDeliveryByTime"))) {
            m_logger.debug("updating param pickupDeliveryByTime from " +_CleanerPickupDelivery.getPickupDeliveryByTime() + "->" + request.getParameter("pickupDeliveryByTime"));
            _CleanerPickupDelivery.setPickupDeliveryByTime(WebParamUtil.getStringValue(request.getParameter("pickupDeliveryByTime")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerPickupDelivery.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerPickupDelivery.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerPickupDelivery.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerPickupDelivery.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerPickupDelivery.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("timeAcked"))) {
            m_logger.debug("updating param timeAcked from " +_CleanerPickupDelivery.getTimeAcked() + "->" + request.getParameter("timeAcked"));
            _CleanerPickupDelivery.setTimeAcked(WebParamUtil.getTimestampValue(request.getParameter("timeAcked")));

        }
        if (!isMissing(request.getParameter("ackedByUserId"))) {
            m_logger.debug("updating param ackedByUserId from " +_CleanerPickupDelivery.getAckedByUserId() + "->" + request.getParameter("ackedByUserId"));
            _CleanerPickupDelivery.setAckedByUserId(WebParamUtil.getLongValue(request.getParameter("ackedByUserId")));

        }
        if (!isMissing(request.getParameter("timeReady"))) {
            m_logger.debug("updating param timeReady from " +_CleanerPickupDelivery.getTimeReady() + "->" + request.getParameter("timeReady"));
            _CleanerPickupDelivery.setTimeReady(WebParamUtil.getTimestampValue(request.getParameter("timeReady")));

        }
        if (!isMissing(request.getParameter("timeNotified"))) {
            m_logger.debug("updating param timeNotified from " +_CleanerPickupDelivery.getTimeNotified() + "->" + request.getParameter("timeNotified"));
            _CleanerPickupDelivery.setTimeNotified(WebParamUtil.getTimestampValue(request.getParameter("timeNotified")));

        }
        if (!isMissing(request.getParameter("timeCompleted"))) {
            m_logger.debug("updating param timeCompleted from " +_CleanerPickupDelivery.getTimeCompleted() + "->" + request.getParameter("timeCompleted"));
            _CleanerPickupDelivery.setTimeCompleted(WebParamUtil.getTimestampValue(request.getParameter("timeCompleted")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerPickupDelivery.getNote() + "->" + request.getParameter("note"));
            _CleanerPickupDelivery.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("pickupNote"))) {
            m_logger.debug("updating param pickupNote from " +_CleanerPickupDelivery.getPickupNote() + "->" + request.getParameter("pickupNote"));
            _CleanerPickupDelivery.setPickupNote(WebParamUtil.getStringValue(request.getParameter("pickupNote")));

        }
        if (!isMissing(request.getParameter("deliveryNote"))) {
            m_logger.debug("updating param deliveryNote from " +_CleanerPickupDelivery.getDeliveryNote() + "->" + request.getParameter("deliveryNote"));
            _CleanerPickupDelivery.setDeliveryNote(WebParamUtil.getStringValue(request.getParameter("deliveryNote")));

        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        CleanerPickupDeliveryForm _CleanerPickupDeliveryForm = (CleanerPickupDeliveryForm) form;
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) dataObject;

            _CleanerPickupDelivery.setLocationId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getLocationId()));
            m_logger.debug("setting LocationId=" +_CleanerPickupDeliveryForm.getLocationId());


            _CleanerPickupDelivery.setCustomerId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getCustomerId()));
            m_logger.debug("setting CustomerId=" +_CleanerPickupDeliveryForm.getCustomerId());


            _CleanerPickupDelivery.setTicketId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getTicketId()));
            m_logger.debug("setting TicketId=" +_CleanerPickupDeliveryForm.getTicketId());


            _CleanerPickupDelivery.setTicketUid(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getTicketUid()));
            m_logger.debug("setting TicketUid=" +_CleanerPickupDeliveryForm.getTicketUid());


            _CleanerPickupDelivery.setPickupTicket(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupTicket()));
            m_logger.debug("setting PickupTicket=" +_CleanerPickupDeliveryForm.getPickupTicket());


            _CleanerPickupDelivery.setCheckinTicketForDelivery(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCheckinTicketForDelivery()));
            m_logger.debug("setting CheckinTicketForDelivery=" +_CleanerPickupDeliveryForm.getCheckinTicketForDelivery());


            _CleanerPickupDelivery.setIsDeliveryRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsDeliveryRequest()));
            m_logger.debug("setting IsDeliveryRequest=" +_CleanerPickupDeliveryForm.getIsDeliveryRequest());


            _CleanerPickupDelivery.setIsWebRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsWebRequest()));
            m_logger.debug("setting IsWebRequest=" +_CleanerPickupDeliveryForm.getIsWebRequest());


            _CleanerPickupDelivery.setIsRecurringRequest(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsRecurringRequest()));
            m_logger.debug("setting IsRecurringRequest=" +_CleanerPickupDeliveryForm.getIsRecurringRequest());


            _CleanerPickupDelivery.setIsReceiveReady(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsReceiveReady()));
            m_logger.debug("setting IsReceiveReady=" +_CleanerPickupDeliveryForm.getIsReceiveReady());


            _CleanerPickupDelivery.setIsReceiveComplete(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getIsReceiveComplete()));
            m_logger.debug("setting IsReceiveComplete=" +_CleanerPickupDeliveryForm.getIsReceiveComplete());


            _CleanerPickupDelivery.setRecurId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getRecurId()));
            m_logger.debug("setting RecurId=" +_CleanerPickupDeliveryForm.getRecurId());


            _CleanerPickupDelivery.setCancelled(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getCancelled()));
            m_logger.debug("setting Cancelled=" +_CleanerPickupDeliveryForm.getCancelled());


            _CleanerPickupDelivery.setCompleted(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getCompleted()));
            m_logger.debug("setting Completed=" +_CleanerPickupDeliveryForm.getCompleted());


            _CleanerPickupDelivery.setCustomerName(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCustomerName()));
            m_logger.debug("setting CustomerName=" +_CleanerPickupDeliveryForm.getCustomerName());


            _CleanerPickupDelivery.setAddress(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getAddress()));
            m_logger.debug("setting Address=" +_CleanerPickupDeliveryForm.getAddress());


            _CleanerPickupDelivery.setAptNumber(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getAptNumber()));
            m_logger.debug("setting AptNumber=" +_CleanerPickupDeliveryForm.getAptNumber());


            _CleanerPickupDelivery.setPhone(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPhone()));
            m_logger.debug("setting Phone=" +_CleanerPickupDeliveryForm.getPhone());


            _CleanerPickupDelivery.setEmail(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getEmail()));
            m_logger.debug("setting Email=" +_CleanerPickupDeliveryForm.getEmail());


            _CleanerPickupDelivery.setAckReceiveMethod(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getAckReceiveMethod()));
            m_logger.debug("setting AckReceiveMethod=" +_CleanerPickupDeliveryForm.getAckReceiveMethod());


            _CleanerPickupDelivery.setCustomerInstruction(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getCustomerInstruction()));
            m_logger.debug("setting CustomerInstruction=" +_CleanerPickupDeliveryForm.getCustomerInstruction());


            _CleanerPickupDelivery.setPickupDeliveryByDay(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryForm.getPickupDeliveryByDay()));
            m_logger.debug("setting PickupDeliveryByDay=" +_CleanerPickupDeliveryForm.getPickupDeliveryByDay());


            _CleanerPickupDelivery.setPickupDeliveryByTime(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupDeliveryByTime()));
            m_logger.debug("setting PickupDeliveryByTime=" +_CleanerPickupDeliveryForm.getPickupDeliveryByTime());


            _CleanerPickupDelivery.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerPickupDeliveryForm.getTimeCreated());

        	_CleanerPickupDelivery.setTimeCreated(new TimeNow());

            _CleanerPickupDelivery.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_CleanerPickupDeliveryForm.getTimeUpdated());

        	_CleanerPickupDelivery.setTimeUpdated(new TimeNow());

            _CleanerPickupDelivery.setTimeAcked(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeAcked()));
            m_logger.debug("setting TimeAcked=" +_CleanerPickupDeliveryForm.getTimeAcked());


            _CleanerPickupDelivery.setAckedByUserId(WebParamUtil.getLongValue(_CleanerPickupDeliveryForm.getAckedByUserId()));
            m_logger.debug("setting AckedByUserId=" +_CleanerPickupDeliveryForm.getAckedByUserId());


            _CleanerPickupDelivery.setTimeReady(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeReady()));
            m_logger.debug("setting TimeReady=" +_CleanerPickupDeliveryForm.getTimeReady());


            _CleanerPickupDelivery.setTimeNotified(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeNotified()));
            m_logger.debug("setting TimeNotified=" +_CleanerPickupDeliveryForm.getTimeNotified());


            _CleanerPickupDelivery.setTimeCompleted(WebParamUtil.getTimestampValue(_CleanerPickupDeliveryForm.getTimeCompleted()));
            m_logger.debug("setting TimeCompleted=" +_CleanerPickupDeliveryForm.getTimeCompleted());


            _CleanerPickupDelivery.setNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerPickupDeliveryForm.getNote());


            _CleanerPickupDelivery.setPickupNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getPickupNote()));
            m_logger.debug("setting PickupNote=" +_CleanerPickupDeliveryForm.getPickupNote());


            _CleanerPickupDelivery.setDeliveryNote(WebParamUtil.getStringValue(_CleanerPickupDeliveryForm.getDeliveryNote()));
            m_logger.debug("setting DeliveryNote=" +_CleanerPickupDeliveryForm.getDeliveryNote());



    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerPickupDelivery _CleanerPickupDelivery = m_ds.getById(cid);
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) dataObject;

        if (!isMissing(request.getParameter("locationId"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getLocationId());
        }
        if (!isMissing(request.getParameter("customerId"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getCustomerId());
        }
        if (!isMissing(request.getParameter("ticketId"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTicketId());
        }
        if (!isMissing(request.getParameter("ticketUid"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTicketUid());
        }
        if (!isMissing(request.getParameter("pickupTicket"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getPickupTicket());
        }
        if (!isMissing(request.getParameter("checkinTicketForDelivery"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getCheckinTicketForDelivery());
        }
        if (!isMissing(request.getParameter("isDeliveryRequest"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getIsDeliveryRequest());
        }
        if (!isMissing(request.getParameter("isWebRequest"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getIsWebRequest());
        }
        if (!isMissing(request.getParameter("isRecurringRequest"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getIsRecurringRequest());
        }
        if (!isMissing(request.getParameter("isReceiveReady"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getIsReceiveReady());
        }
        if (!isMissing(request.getParameter("isReceiveComplete"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getIsReceiveComplete());
        }
        if (!isMissing(request.getParameter("recurId"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getRecurId());
        }
        if (!isMissing(request.getParameter("cancelled"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getCancelled());
        }
        if (!isMissing(request.getParameter("completed"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getCompleted());
        }
        if (!isMissing(request.getParameter("customerName"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getCustomerName());
        }
        if (!isMissing(request.getParameter("address"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getAddress());
        }
        if (!isMissing(request.getParameter("aptNumber"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getAptNumber());
        }
        if (!isMissing(request.getParameter("phone"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getPhone());
        }
        if (!isMissing(request.getParameter("email"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getEmail());
        }
        if (!isMissing(request.getParameter("ackReceiveMethod"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getAckReceiveMethod());
        }
        if (!isMissing(request.getParameter("customerInstruction"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getCustomerInstruction());
        }
        if (!isMissing(request.getParameter("pickupDeliveryByDay"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getPickupDeliveryByDay());
        }
        if (!isMissing(request.getParameter("pickupDeliveryByTime"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getPickupDeliveryByTime());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTimeUpdated());
        }
        if (!isMissing(request.getParameter("timeAcked"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTimeAcked());
        }
        if (!isMissing(request.getParameter("ackedByUserId"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getAckedByUserId());
        }
        if (!isMissing(request.getParameter("timeReady"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTimeReady());
        }
        if (!isMissing(request.getParameter("timeNotified"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTimeNotified());
        }
        if (!isMissing(request.getParameter("timeCompleted"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getTimeCompleted());
        }
        if (!isMissing(request.getParameter("note"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getNote());
        }
        if (!isMissing(request.getParameter("pickupNote"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getPickupNote());
        }
        if (!isMissing(request.getParameter("deliveryNote"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDelivery.getDeliveryNote());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, CleanerPickupDelivery _CleanerPickupDelivery) {
        if (fieldName == null || fieldName.equals("")|| _CleanerPickupDelivery == null) return null;
        
        if (fieldName.equals("locationId")) {
            return WebUtil.display(_CleanerPickupDelivery.getLocationId());
        }
        if (fieldName.equals("customerId")) {
            return WebUtil.display(_CleanerPickupDelivery.getCustomerId());
        }
        if (fieldName.equals("ticketId")) {
            return WebUtil.display(_CleanerPickupDelivery.getTicketId());
        }
        if (fieldName.equals("ticketUid")) {
            return WebUtil.display(_CleanerPickupDelivery.getTicketUid());
        }
        if (fieldName.equals("pickupTicket")) {
            return WebUtil.display(_CleanerPickupDelivery.getPickupTicket());
        }
        if (fieldName.equals("checkinTicketForDelivery")) {
            return WebUtil.display(_CleanerPickupDelivery.getCheckinTicketForDelivery());
        }
        if (fieldName.equals("isDeliveryRequest")) {
            return WebUtil.display(_CleanerPickupDelivery.getIsDeliveryRequest());
        }
        if (fieldName.equals("isWebRequest")) {
            return WebUtil.display(_CleanerPickupDelivery.getIsWebRequest());
        }
        if (fieldName.equals("isRecurringRequest")) {
            return WebUtil.display(_CleanerPickupDelivery.getIsRecurringRequest());
        }
        if (fieldName.equals("isReceiveReady")) {
            return WebUtil.display(_CleanerPickupDelivery.getIsReceiveReady());
        }
        if (fieldName.equals("isReceiveComplete")) {
            return WebUtil.display(_CleanerPickupDelivery.getIsReceiveComplete());
        }
        if (fieldName.equals("recurId")) {
            return WebUtil.display(_CleanerPickupDelivery.getRecurId());
        }
        if (fieldName.equals("cancelled")) {
            return WebUtil.display(_CleanerPickupDelivery.getCancelled());
        }
        if (fieldName.equals("completed")) {
            return WebUtil.display(_CleanerPickupDelivery.getCompleted());
        }
        if (fieldName.equals("customerName")) {
            return WebUtil.display(_CleanerPickupDelivery.getCustomerName());
        }
        if (fieldName.equals("address")) {
            return WebUtil.display(_CleanerPickupDelivery.getAddress());
        }
        if (fieldName.equals("aptNumber")) {
            return WebUtil.display(_CleanerPickupDelivery.getAptNumber());
        }
        if (fieldName.equals("phone")) {
            return WebUtil.display(_CleanerPickupDelivery.getPhone());
        }
        if (fieldName.equals("email")) {
            return WebUtil.display(_CleanerPickupDelivery.getEmail());
        }
        if (fieldName.equals("ackReceiveMethod")) {
            return WebUtil.display(_CleanerPickupDelivery.getAckReceiveMethod());
        }
        if (fieldName.equals("customerInstruction")) {
            return WebUtil.display(_CleanerPickupDelivery.getCustomerInstruction());
        }
        if (fieldName.equals("pickupDeliveryByDay")) {
            return WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByDay());
        }
        if (fieldName.equals("pickupDeliveryByTime")) {
            return WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByTime());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_CleanerPickupDelivery.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_CleanerPickupDelivery.getTimeUpdated());
        }
        if (fieldName.equals("timeAcked")) {
            return WebUtil.display(_CleanerPickupDelivery.getTimeAcked());
        }
        if (fieldName.equals("ackedByUserId")) {
            return WebUtil.display(_CleanerPickupDelivery.getAckedByUserId());
        }
        if (fieldName.equals("timeReady")) {
            return WebUtil.display(_CleanerPickupDelivery.getTimeReady());
        }
        if (fieldName.equals("timeNotified")) {
            return WebUtil.display(_CleanerPickupDelivery.getTimeNotified());
        }
        if (fieldName.equals("timeCompleted")) {
            return WebUtil.display(_CleanerPickupDelivery.getTimeCompleted());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_CleanerPickupDelivery.getNote());
        }
        if (fieldName.equals("pickupNote")) {
            return WebUtil.display(_CleanerPickupDelivery.getPickupNote());
        }
        if (fieldName.equals("deliveryNote")) {
            return WebUtil.display(_CleanerPickupDelivery.getDeliveryNote());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerPickupDeliveryForm _CleanerPickupDeliveryForm = (CleanerPickupDeliveryForm) form;

		if(requestParams.containsKey("locationId"))
			_CleanerPickupDeliveryForm.setLocationId((String)requestParams.get("locationId"));
		if(requestParams.containsKey("customerId"))
			_CleanerPickupDeliveryForm.setCustomerId((String)requestParams.get("customerId"));
		if(requestParams.containsKey("ticketId"))
			_CleanerPickupDeliveryForm.setTicketId((String)requestParams.get("ticketId"));
		if(requestParams.containsKey("ticketUid"))
			_CleanerPickupDeliveryForm.setTicketUid((String)requestParams.get("ticketUid"));
		if(requestParams.containsKey("pickupTicket"))
			_CleanerPickupDeliveryForm.setPickupTicket((String)requestParams.get("pickupTicket"));
		if(requestParams.containsKey("checkinTicketForDelivery"))
			_CleanerPickupDeliveryForm.setCheckinTicketForDelivery((String)requestParams.get("checkinTicketForDelivery"));
		if(requestParams.containsKey("isDeliveryRequest"))
			_CleanerPickupDeliveryForm.setIsDeliveryRequest((String)requestParams.get("isDeliveryRequest"));
		if(requestParams.containsKey("isWebRequest"))
			_CleanerPickupDeliveryForm.setIsWebRequest((String)requestParams.get("isWebRequest"));
		if(requestParams.containsKey("isRecurringRequest"))
			_CleanerPickupDeliveryForm.setIsRecurringRequest((String)requestParams.get("isRecurringRequest"));
		if(requestParams.containsKey("isReceiveReady"))
			_CleanerPickupDeliveryForm.setIsReceiveReady((String)requestParams.get("isReceiveReady"));
		if(requestParams.containsKey("isReceiveComplete"))
			_CleanerPickupDeliveryForm.setIsReceiveComplete((String)requestParams.get("isReceiveComplete"));
		if(requestParams.containsKey("recurId"))
			_CleanerPickupDeliveryForm.setRecurId((String)requestParams.get("recurId"));
		if(requestParams.containsKey("cancelled"))
			_CleanerPickupDeliveryForm.setCancelled((String)requestParams.get("cancelled"));
		if(requestParams.containsKey("completed"))
			_CleanerPickupDeliveryForm.setCompleted((String)requestParams.get("completed"));
		if(requestParams.containsKey("customerName"))
			_CleanerPickupDeliveryForm.setCustomerName((String)requestParams.get("customerName"));
		if(requestParams.containsKey("address"))
			_CleanerPickupDeliveryForm.setAddress((String)requestParams.get("address"));
		if(requestParams.containsKey("aptNumber"))
			_CleanerPickupDeliveryForm.setAptNumber((String)requestParams.get("aptNumber"));
		if(requestParams.containsKey("phone"))
			_CleanerPickupDeliveryForm.setPhone((String)requestParams.get("phone"));
		if(requestParams.containsKey("email"))
			_CleanerPickupDeliveryForm.setEmail((String)requestParams.get("email"));
		if(requestParams.containsKey("ackReceiveMethod"))
			_CleanerPickupDeliveryForm.setAckReceiveMethod((String)requestParams.get("ackReceiveMethod"));
		if(requestParams.containsKey("customerInstruction"))
			_CleanerPickupDeliveryForm.setCustomerInstruction((String)requestParams.get("customerInstruction"));
		if(requestParams.containsKey("pickupDeliveryByDay"))
			_CleanerPickupDeliveryForm.setPickupDeliveryByDay((String)requestParams.get("pickupDeliveryByDay"));
		if(requestParams.containsKey("pickupDeliveryByTime"))
			_CleanerPickupDeliveryForm.setPickupDeliveryByTime((String)requestParams.get("pickupDeliveryByTime"));
		if(requestParams.containsKey("timeCreated"))
			_CleanerPickupDeliveryForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_CleanerPickupDeliveryForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
		if(requestParams.containsKey("timeAcked"))
			_CleanerPickupDeliveryForm.setTimeAcked((String)requestParams.get("timeAcked"));
		if(requestParams.containsKey("ackedByUserId"))
			_CleanerPickupDeliveryForm.setAckedByUserId((String)requestParams.get("ackedByUserId"));
		if(requestParams.containsKey("timeReady"))
			_CleanerPickupDeliveryForm.setTimeReady((String)requestParams.get("timeReady"));
		if(requestParams.containsKey("timeNotified"))
			_CleanerPickupDeliveryForm.setTimeNotified((String)requestParams.get("timeNotified"));
		if(requestParams.containsKey("timeCompleted"))
			_CleanerPickupDeliveryForm.setTimeCompleted((String)requestParams.get("timeCompleted"));
		if(requestParams.containsKey("note"))
			_CleanerPickupDeliveryForm.setNote((String)requestParams.get("note"));
		if(requestParams.containsKey("pickupNote"))
			_CleanerPickupDeliveryForm.setPickupNote((String)requestParams.get("pickupNote"));
		if(requestParams.containsKey("deliveryNote"))
			_CleanerPickupDeliveryForm.setDeliveryNote((String)requestParams.get("deliveryNote"));
    }


    protected boolean loginRequired() {
        return false;
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
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_home=NULL,/jsp/form_cleaner/cleanerPickupDelivery_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_list=NULL,/jsp/form_cleaner/cleanerPickupDelivery_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_form=NULL,/jsp/form_cleaner/cleanerPickupDelivery_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_pickup_delivery_ajax=NULL,/jsp/form_cleaner/cleanerPickupDelivery_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_home=NULL,/jsp/form_cleaner/cleanerPickupDelivery_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_list=NULL,/jsp/form_cleaner/cleanerPickupDelivery_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_form=NULL,/jsp/form_cleaner/cleanerPickupDelivery_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_pickup_delivery_ajax=NULL,/jsp/form_cleaner/cleanerPickupDelivery_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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

    public String getActionGroupName(){ return "Cleaner";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
//    protected CleanerPickupDeliveryDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
