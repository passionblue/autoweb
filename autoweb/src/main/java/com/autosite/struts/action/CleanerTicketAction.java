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

import com.autosite.db.CleanerTicket;
import com.autosite.ds.CleanerTicketDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerTicketForm;
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




public class CleanerTicketAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerTicketAction.class);

    public CleanerTicketAction(){
        m_ds = CleanerTicketDS.getInstance();
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

        CleanerTicketForm _CleanerTicketForm = (CleanerTicketForm) form;
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


        CleanerTicket _CleanerTicket = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerTicket = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerTicket _CleanerTicket = m_ds.getById(cid);

            if (_CleanerTicket == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerTicket.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerTicket.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerTicket);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerTicketForm == null) {
    	            editField(request, response, _CleanerTicket);
				} else {
    	            edit(request, response, _CleanerTicketForm, _CleanerTicket);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerTicket);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerTicket o = m_ds.getById( _CleanerTicket.getId(), true);

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
            //CleanerTicket _CleanerTicket = m_ds.getById(cid);

            if (_CleanerTicket == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerTicket.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerTicket.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerTicket);
                if (returnObjects != null) returnObjects.put("target", _CleanerTicket);
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
            //CleanerTicket _CleanerTicket = m_ds.getById(cid);

            if (_CleanerTicket == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerTicket.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerTicket.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerTicket);
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

            m_ds.delete(_CleanerTicket); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerTicket);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerTicket);
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


            m_logger.info("Creating new CleanerTicket" );
            CleanerTicket _CleanerTicketNew = new CleanerTicket();   

            // Setting IDs for the object
            _CleanerTicketNew.setSiteId(site.getId());
			
            if ( _CleanerTicketForm == null) {
                setFields(request, response, _CleanerTicketNew);
            } else {

            _CleanerTicketNew.setSerial(WebParamUtil.getStringValue(_CleanerTicketForm.getSerial()));
            m_logger.debug("setting Serial=" +_CleanerTicketForm.getSerial());


            _CleanerTicketNew.setParentTicketId(WebParamUtil.getLongValue(_CleanerTicketForm.getParentTicketId()));
            m_logger.debug("setting ParentTicketId=" +_CleanerTicketForm.getParentTicketId());


            _CleanerTicketNew.setCustomerId(WebParamUtil.getLongValue(_CleanerTicketForm.getCustomerId()));
            m_logger.debug("setting CustomerId=" +_CleanerTicketForm.getCustomerId());


            _CleanerTicketNew.setEnterUserId(WebParamUtil.getLongValue(_CleanerTicketForm.getEnterUserId()));
            m_logger.debug("setting EnterUserId=" +_CleanerTicketForm.getEnterUserId());


            _CleanerTicketNew.setLocationId(WebParamUtil.getLongValue(_CleanerTicketForm.getLocationId()));
            m_logger.debug("setting LocationId=" +_CleanerTicketForm.getLocationId());


            _CleanerTicketNew.setNote(WebParamUtil.getStringValue(_CleanerTicketForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerTicketForm.getNote());


            _CleanerTicketNew.setCompleted(WebParamUtil.getIntegerValue(_CleanerTicketForm.getCompleted()));
            m_logger.debug("setting Completed=" +_CleanerTicketForm.getCompleted());


            _CleanerTicketNew.setOnhold(WebParamUtil.getIntegerValue(_CleanerTicketForm.getOnhold()));
            m_logger.debug("setting Onhold=" +_CleanerTicketForm.getOnhold());


            _CleanerTicketNew.setOriginalTicketId(WebParamUtil.getLongValue(_CleanerTicketForm.getOriginalTicketId()));
            m_logger.debug("setting OriginalTicketId=" +_CleanerTicketForm.getOriginalTicketId());


            _CleanerTicketNew.setReturned(WebParamUtil.getIntegerValue(_CleanerTicketForm.getReturned()));
            m_logger.debug("setting Returned=" +_CleanerTicketForm.getReturned());


            _CleanerTicketNew.setReturnedReasonText(WebParamUtil.getStringValue(_CleanerTicketForm.getReturnedReasonText()));
            m_logger.debug("setting ReturnedReasonText=" +_CleanerTicketForm.getReturnedReasonText());


            _CleanerTicketNew.setReturnedNote(WebParamUtil.getStringValue(_CleanerTicketForm.getReturnedNote()));
            m_logger.debug("setting ReturnedNote=" +_CleanerTicketForm.getReturnedNote());


            _CleanerTicketNew.setTotalCharge(WebParamUtil.getDoubleValue(_CleanerTicketForm.getTotalCharge()));
            m_logger.debug("setting TotalCharge=" +_CleanerTicketForm.getTotalCharge());


            _CleanerTicketNew.setFinalCharge(WebParamUtil.getDoubleValue(_CleanerTicketForm.getFinalCharge()));
            m_logger.debug("setting FinalCharge=" +_CleanerTicketForm.getFinalCharge());


            _CleanerTicketNew.setDiscountId(WebParamUtil.getLongValue(_CleanerTicketForm.getDiscountId()));
            m_logger.debug("setting DiscountId=" +_CleanerTicketForm.getDiscountId());


            _CleanerTicketNew.setDiscountAmount(WebParamUtil.getDoubleValue(_CleanerTicketForm.getDiscountAmount()));
            m_logger.debug("setting DiscountAmount=" +_CleanerTicketForm.getDiscountAmount());


            _CleanerTicketNew.setDiscountNote(WebParamUtil.getStringValue(_CleanerTicketForm.getDiscountNote()));
            m_logger.debug("setting DiscountNote=" +_CleanerTicketForm.getDiscountNote());


            _CleanerTicketNew.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerTicketForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerTicketForm.getTimeCreated());

        	_CleanerTicketNew.setTimeCreated(new TimeNow());

            _CleanerTicketNew.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerTicketForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_CleanerTicketForm.getTimeUpdated());

        	_CleanerTicketNew.setTimeUpdated(new TimeNow());

            _CleanerTicketNew.setTimeCompleted(WebParamUtil.getTimestampValue(_CleanerTicketForm.getTimeCompleted()));
            m_logger.debug("setting TimeCompleted=" +_CleanerTicketForm.getTimeCompleted());


            _CleanerTicketNew.setTimeOnhold(WebParamUtil.getTimestampValue(_CleanerTicketForm.getTimeOnhold()));
            m_logger.debug("setting TimeOnhold=" +_CleanerTicketForm.getTimeOnhold());


			}

            try {
                checkDepedenceIntegrity(_CleanerTicketNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerTicketNew);
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
            
            if (_CleanerTicketNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerTicketNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerTicketNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerTicketNew);
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
             _CleanerTicket =  _CleanerTicketNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerTicket, "cleaner-customer" );
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerTicketForm _CleanerTicketForm, CleanerTicket _CleanerTicket) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicket _CleanerTicket = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerTicketDS.objectToString(_CleanerTicket));

        _CleanerTicket.setSerial(WebParamUtil.getStringValue(_CleanerTicketForm.getSerial()));


        _CleanerTicket.setParentTicketId(WebParamUtil.getLongValue(_CleanerTicketForm.getParentTicketId()));


        _CleanerTicket.setCustomerId(WebParamUtil.getLongValue(_CleanerTicketForm.getCustomerId()));


        _CleanerTicket.setEnterUserId(WebParamUtil.getLongValue(_CleanerTicketForm.getEnterUserId()));


        _CleanerTicket.setLocationId(WebParamUtil.getLongValue(_CleanerTicketForm.getLocationId()));


        _CleanerTicket.setNote(WebParamUtil.getStringValue(_CleanerTicketForm.getNote()));


        _CleanerTicket.setCompleted(WebParamUtil.getIntegerValue(_CleanerTicketForm.getCompleted()));


        _CleanerTicket.setOnhold(WebParamUtil.getIntegerValue(_CleanerTicketForm.getOnhold()));


        _CleanerTicket.setOriginalTicketId(WebParamUtil.getLongValue(_CleanerTicketForm.getOriginalTicketId()));


        _CleanerTicket.setReturned(WebParamUtil.getIntegerValue(_CleanerTicketForm.getReturned()));


        _CleanerTicket.setReturnedReasonText(WebParamUtil.getStringValue(_CleanerTicketForm.getReturnedReasonText()));


        _CleanerTicket.setReturnedNote(WebParamUtil.getStringValue(_CleanerTicketForm.getReturnedNote()));


        _CleanerTicket.setTotalCharge(WebParamUtil.getDoubleValue(_CleanerTicketForm.getTotalCharge()));


        _CleanerTicket.setFinalCharge(WebParamUtil.getDoubleValue(_CleanerTicketForm.getFinalCharge()));


        _CleanerTicket.setDiscountId(WebParamUtil.getLongValue(_CleanerTicketForm.getDiscountId()));


        _CleanerTicket.setDiscountAmount(WebParamUtil.getDoubleValue(_CleanerTicketForm.getDiscountAmount()));


        _CleanerTicket.setDiscountNote(WebParamUtil.getStringValue(_CleanerTicketForm.getDiscountNote()));




        _CleanerTicket.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerTicketForm.getTimeUpdated()));

        _CleanerTicket.setTimeUpdated(new TimeNow());

        _CleanerTicket.setTimeCompleted(WebParamUtil.getTimestampValue(_CleanerTicketForm.getTimeCompleted()));


        _CleanerTicket.setTimeOnhold(WebParamUtil.getTimestampValue(_CleanerTicketForm.getTimeOnhold()));



        m_actionExtent.beforeUpdate(request, response, _CleanerTicket);
        m_ds.update(_CleanerTicket);
        m_actionExtent.afterUpdate(request, response, _CleanerTicket);
        m_logger.debug("After update " + CleanerTicketDS.objectToString(_CleanerTicket));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerTicket _CleanerTicket) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicket _CleanerTicket = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serial"))) {
            m_logger.debug("updating param serial from " +_CleanerTicket.getSerial() + "->" + request.getParameter("serial"));
            _CleanerTicket.setSerial(WebParamUtil.getStringValue(request.getParameter("serial")));

        }
        if (!isMissing(request.getParameter("parentTicketId"))) {
            m_logger.debug("updating param parentTicketId from " +_CleanerTicket.getParentTicketId() + "->" + request.getParameter("parentTicketId"));
            _CleanerTicket.setParentTicketId(WebParamUtil.getLongValue(request.getParameter("parentTicketId")));

        }
        if (!isMissing(request.getParameter("customerId"))) {
            m_logger.debug("updating param customerId from " +_CleanerTicket.getCustomerId() + "->" + request.getParameter("customerId"));
            _CleanerTicket.setCustomerId(WebParamUtil.getLongValue(request.getParameter("customerId")));

        }
        if (!isMissing(request.getParameter("enterUserId"))) {
            m_logger.debug("updating param enterUserId from " +_CleanerTicket.getEnterUserId() + "->" + request.getParameter("enterUserId"));
            _CleanerTicket.setEnterUserId(WebParamUtil.getLongValue(request.getParameter("enterUserId")));

        }
        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerTicket.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerTicket.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerTicket.getNote() + "->" + request.getParameter("note"));
            _CleanerTicket.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("completed"))) {
            m_logger.debug("updating param completed from " +_CleanerTicket.getCompleted() + "->" + request.getParameter("completed"));
            _CleanerTicket.setCompleted(WebParamUtil.getIntegerValue(request.getParameter("completed")));

        }
        if (!isMissing(request.getParameter("onhold"))) {
            m_logger.debug("updating param onhold from " +_CleanerTicket.getOnhold() + "->" + request.getParameter("onhold"));
            _CleanerTicket.setOnhold(WebParamUtil.getIntegerValue(request.getParameter("onhold")));

        }
        if (!isMissing(request.getParameter("originalTicketId"))) {
            m_logger.debug("updating param originalTicketId from " +_CleanerTicket.getOriginalTicketId() + "->" + request.getParameter("originalTicketId"));
            _CleanerTicket.setOriginalTicketId(WebParamUtil.getLongValue(request.getParameter("originalTicketId")));

        }
        if (!isMissing(request.getParameter("returned"))) {
            m_logger.debug("updating param returned from " +_CleanerTicket.getReturned() + "->" + request.getParameter("returned"));
            _CleanerTicket.setReturned(WebParamUtil.getIntegerValue(request.getParameter("returned")));

        }
        if (!isMissing(request.getParameter("returnedReasonText"))) {
            m_logger.debug("updating param returnedReasonText from " +_CleanerTicket.getReturnedReasonText() + "->" + request.getParameter("returnedReasonText"));
            _CleanerTicket.setReturnedReasonText(WebParamUtil.getStringValue(request.getParameter("returnedReasonText")));

        }
        if (!isMissing(request.getParameter("returnedNote"))) {
            m_logger.debug("updating param returnedNote from " +_CleanerTicket.getReturnedNote() + "->" + request.getParameter("returnedNote"));
            _CleanerTicket.setReturnedNote(WebParamUtil.getStringValue(request.getParameter("returnedNote")));

        }
        if (!isMissing(request.getParameter("totalCharge"))) {
            m_logger.debug("updating param totalCharge from " +_CleanerTicket.getTotalCharge() + "->" + request.getParameter("totalCharge"));
            _CleanerTicket.setTotalCharge(WebParamUtil.getDoubleValue(request.getParameter("totalCharge")));

        }
        if (!isMissing(request.getParameter("finalCharge"))) {
            m_logger.debug("updating param finalCharge from " +_CleanerTicket.getFinalCharge() + "->" + request.getParameter("finalCharge"));
            _CleanerTicket.setFinalCharge(WebParamUtil.getDoubleValue(request.getParameter("finalCharge")));

        }
        if (!isMissing(request.getParameter("discountId"))) {
            m_logger.debug("updating param discountId from " +_CleanerTicket.getDiscountId() + "->" + request.getParameter("discountId"));
            _CleanerTicket.setDiscountId(WebParamUtil.getLongValue(request.getParameter("discountId")));

        }
        if (!isMissing(request.getParameter("discountAmount"))) {
            m_logger.debug("updating param discountAmount from " +_CleanerTicket.getDiscountAmount() + "->" + request.getParameter("discountAmount"));
            _CleanerTicket.setDiscountAmount(WebParamUtil.getDoubleValue(request.getParameter("discountAmount")));

        }
        if (!isMissing(request.getParameter("discountNote"))) {
            m_logger.debug("updating param discountNote from " +_CleanerTicket.getDiscountNote() + "->" + request.getParameter("discountNote"));
            _CleanerTicket.setDiscountNote(WebParamUtil.getStringValue(request.getParameter("discountNote")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerTicket.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerTicket.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerTicket.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerTicket.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerTicket.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("timeCompleted"))) {
            m_logger.debug("updating param timeCompleted from " +_CleanerTicket.getTimeCompleted() + "->" + request.getParameter("timeCompleted"));
            _CleanerTicket.setTimeCompleted(WebParamUtil.getTimestampValue(request.getParameter("timeCompleted")));

        }
        if (!isMissing(request.getParameter("timeOnhold"))) {
            m_logger.debug("updating param timeOnhold from " +_CleanerTicket.getTimeOnhold() + "->" + request.getParameter("timeOnhold"));
            _CleanerTicket.setTimeOnhold(WebParamUtil.getTimestampValue(request.getParameter("timeOnhold")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerTicket);
        m_ds.update(_CleanerTicket);
        m_actionExtent.afterUpdate(request, response, _CleanerTicket);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerTicket _CleanerTicket) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicket _CleanerTicket = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serial"))) {
            m_logger.debug("updating param serial from " +_CleanerTicket.getSerial() + "->" + request.getParameter("serial"));
            _CleanerTicket.setSerial(WebParamUtil.getStringValue(request.getParameter("serial")));

        }
        if (!isMissing(request.getParameter("parentTicketId"))) {
            m_logger.debug("updating param parentTicketId from " +_CleanerTicket.getParentTicketId() + "->" + request.getParameter("parentTicketId"));
            _CleanerTicket.setParentTicketId(WebParamUtil.getLongValue(request.getParameter("parentTicketId")));

        }
        if (!isMissing(request.getParameter("customerId"))) {
            m_logger.debug("updating param customerId from " +_CleanerTicket.getCustomerId() + "->" + request.getParameter("customerId"));
            _CleanerTicket.setCustomerId(WebParamUtil.getLongValue(request.getParameter("customerId")));

        }
        if (!isMissing(request.getParameter("enterUserId"))) {
            m_logger.debug("updating param enterUserId from " +_CleanerTicket.getEnterUserId() + "->" + request.getParameter("enterUserId"));
            _CleanerTicket.setEnterUserId(WebParamUtil.getLongValue(request.getParameter("enterUserId")));

        }
        if (!isMissing(request.getParameter("locationId"))) {
            m_logger.debug("updating param locationId from " +_CleanerTicket.getLocationId() + "->" + request.getParameter("locationId"));
            _CleanerTicket.setLocationId(WebParamUtil.getLongValue(request.getParameter("locationId")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerTicket.getNote() + "->" + request.getParameter("note"));
            _CleanerTicket.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("completed"))) {
            m_logger.debug("updating param completed from " +_CleanerTicket.getCompleted() + "->" + request.getParameter("completed"));
            _CleanerTicket.setCompleted(WebParamUtil.getIntegerValue(request.getParameter("completed")));

        }
        if (!isMissing(request.getParameter("onhold"))) {
            m_logger.debug("updating param onhold from " +_CleanerTicket.getOnhold() + "->" + request.getParameter("onhold"));
            _CleanerTicket.setOnhold(WebParamUtil.getIntegerValue(request.getParameter("onhold")));

        }
        if (!isMissing(request.getParameter("originalTicketId"))) {
            m_logger.debug("updating param originalTicketId from " +_CleanerTicket.getOriginalTicketId() + "->" + request.getParameter("originalTicketId"));
            _CleanerTicket.setOriginalTicketId(WebParamUtil.getLongValue(request.getParameter("originalTicketId")));

        }
        if (!isMissing(request.getParameter("returned"))) {
            m_logger.debug("updating param returned from " +_CleanerTicket.getReturned() + "->" + request.getParameter("returned"));
            _CleanerTicket.setReturned(WebParamUtil.getIntegerValue(request.getParameter("returned")));

        }
        if (!isMissing(request.getParameter("returnedReasonText"))) {
            m_logger.debug("updating param returnedReasonText from " +_CleanerTicket.getReturnedReasonText() + "->" + request.getParameter("returnedReasonText"));
            _CleanerTicket.setReturnedReasonText(WebParamUtil.getStringValue(request.getParameter("returnedReasonText")));

        }
        if (!isMissing(request.getParameter("returnedNote"))) {
            m_logger.debug("updating param returnedNote from " +_CleanerTicket.getReturnedNote() + "->" + request.getParameter("returnedNote"));
            _CleanerTicket.setReturnedNote(WebParamUtil.getStringValue(request.getParameter("returnedNote")));

        }
        if (!isMissing(request.getParameter("totalCharge"))) {
            m_logger.debug("updating param totalCharge from " +_CleanerTicket.getTotalCharge() + "->" + request.getParameter("totalCharge"));
            _CleanerTicket.setTotalCharge(WebParamUtil.getDoubleValue(request.getParameter("totalCharge")));

        }
        if (!isMissing(request.getParameter("finalCharge"))) {
            m_logger.debug("updating param finalCharge from " +_CleanerTicket.getFinalCharge() + "->" + request.getParameter("finalCharge"));
            _CleanerTicket.setFinalCharge(WebParamUtil.getDoubleValue(request.getParameter("finalCharge")));

        }
        if (!isMissing(request.getParameter("discountId"))) {
            m_logger.debug("updating param discountId from " +_CleanerTicket.getDiscountId() + "->" + request.getParameter("discountId"));
            _CleanerTicket.setDiscountId(WebParamUtil.getLongValue(request.getParameter("discountId")));

        }
        if (!isMissing(request.getParameter("discountAmount"))) {
            m_logger.debug("updating param discountAmount from " +_CleanerTicket.getDiscountAmount() + "->" + request.getParameter("discountAmount"));
            _CleanerTicket.setDiscountAmount(WebParamUtil.getDoubleValue(request.getParameter("discountAmount")));

        }
        if (!isMissing(request.getParameter("discountNote"))) {
            m_logger.debug("updating param discountNote from " +_CleanerTicket.getDiscountNote() + "->" + request.getParameter("discountNote"));
            _CleanerTicket.setDiscountNote(WebParamUtil.getStringValue(request.getParameter("discountNote")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerTicket.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerTicket.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerTicket.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerTicket.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerTicket.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("timeCompleted"))) {
            m_logger.debug("updating param timeCompleted from " +_CleanerTicket.getTimeCompleted() + "->" + request.getParameter("timeCompleted"));
            _CleanerTicket.setTimeCompleted(WebParamUtil.getTimestampValue(request.getParameter("timeCompleted")));

        }
        if (!isMissing(request.getParameter("timeOnhold"))) {
            m_logger.debug("updating param timeOnhold from " +_CleanerTicket.getTimeOnhold() + "->" + request.getParameter("timeOnhold"));
            _CleanerTicket.setTimeOnhold(WebParamUtil.getTimestampValue(request.getParameter("timeOnhold")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerTicket _CleanerTicket) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicket _CleanerTicket = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serial"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getSerial());
        }
        if (!isMissing(request.getParameter("parentTicketId"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getParentTicketId());
        }
        if (!isMissing(request.getParameter("customerId"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getCustomerId());
        }
        if (!isMissing(request.getParameter("enterUserId"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getEnterUserId());
        }
        if (!isMissing(request.getParameter("locationId"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getLocationId());
        }
        if (!isMissing(request.getParameter("note"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getNote());
        }
        if (!isMissing(request.getParameter("completed"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getCompleted());
        }
        if (!isMissing(request.getParameter("onhold"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getOnhold());
        }
        if (!isMissing(request.getParameter("originalTicketId"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getOriginalTicketId());
        }
        if (!isMissing(request.getParameter("returned"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getReturned());
        }
        if (!isMissing(request.getParameter("returnedReasonText"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getReturnedReasonText());
        }
        if (!isMissing(request.getParameter("returnedNote"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getReturnedNote());
        }
        if (!isMissing(request.getParameter("totalCharge"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getTotalCharge());
        }
        if (!isMissing(request.getParameter("finalCharge"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getFinalCharge());
        }
        if (!isMissing(request.getParameter("discountId"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getDiscountId());
        }
        if (!isMissing(request.getParameter("discountAmount"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getDiscountAmount());
        }
        if (!isMissing(request.getParameter("discountNote"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getDiscountNote());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getTimeUpdated());
        }
        if (!isMissing(request.getParameter("timeCompleted"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getTimeCompleted());
        }
        if (!isMissing(request.getParameter("timeOnhold"))) {
			return  JtStringUtil.valueOf(_CleanerTicket.getTimeOnhold());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerTicket _CleanerTicket) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerTicket _CleanerTicket) {
        if (fieldName == null || fieldName.equals("")|| _CleanerTicket == null) return null;
        
        if (fieldName.equals("serial")) {
            return WebUtil.display(_CleanerTicket.getSerial());
        }
        if (fieldName.equals("parentTicketId")) {
            return WebUtil.display(_CleanerTicket.getParentTicketId());
        }
        if (fieldName.equals("customerId")) {
            return WebUtil.display(_CleanerTicket.getCustomerId());
        }
        if (fieldName.equals("enterUserId")) {
            return WebUtil.display(_CleanerTicket.getEnterUserId());
        }
        if (fieldName.equals("locationId")) {
            return WebUtil.display(_CleanerTicket.getLocationId());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_CleanerTicket.getNote());
        }
        if (fieldName.equals("completed")) {
            return WebUtil.display(_CleanerTicket.getCompleted());
        }
        if (fieldName.equals("onhold")) {
            return WebUtil.display(_CleanerTicket.getOnhold());
        }
        if (fieldName.equals("originalTicketId")) {
            return WebUtil.display(_CleanerTicket.getOriginalTicketId());
        }
        if (fieldName.equals("returned")) {
            return WebUtil.display(_CleanerTicket.getReturned());
        }
        if (fieldName.equals("returnedReasonText")) {
            return WebUtil.display(_CleanerTicket.getReturnedReasonText());
        }
        if (fieldName.equals("returnedNote")) {
            return WebUtil.display(_CleanerTicket.getReturnedNote());
        }
        if (fieldName.equals("totalCharge")) {
            return WebUtil.display(_CleanerTicket.getTotalCharge());
        }
        if (fieldName.equals("finalCharge")) {
            return WebUtil.display(_CleanerTicket.getFinalCharge());
        }
        if (fieldName.equals("discountId")) {
            return WebUtil.display(_CleanerTicket.getDiscountId());
        }
        if (fieldName.equals("discountAmount")) {
            return WebUtil.display(_CleanerTicket.getDiscountAmount());
        }
        if (fieldName.equals("discountNote")) {
            return WebUtil.display(_CleanerTicket.getDiscountNote());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_CleanerTicket.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_CleanerTicket.getTimeUpdated());
        }
        if (fieldName.equals("timeCompleted")) {
            return WebUtil.display(_CleanerTicket.getTimeCompleted());
        }
        if (fieldName.equals("timeOnhold")) {
            return WebUtil.display(_CleanerTicket.getTimeOnhold());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerTicketForm _CleanerTicketForm = (CleanerTicketForm) form;

		if(requestParams.containsKey("serial"))
			_CleanerTicketForm.setSerial((String)requestParams.get("serial"));
		if(requestParams.containsKey("parentTicketId"))
			_CleanerTicketForm.setParentTicketId((String)requestParams.get("parentTicketId"));
		if(requestParams.containsKey("customerId"))
			_CleanerTicketForm.setCustomerId((String)requestParams.get("customerId"));
		if(requestParams.containsKey("enterUserId"))
			_CleanerTicketForm.setEnterUserId((String)requestParams.get("enterUserId"));
		if(requestParams.containsKey("locationId"))
			_CleanerTicketForm.setLocationId((String)requestParams.get("locationId"));
		if(requestParams.containsKey("note"))
			_CleanerTicketForm.setNote((String)requestParams.get("note"));
		if(requestParams.containsKey("completed"))
			_CleanerTicketForm.setCompleted((String)requestParams.get("completed"));
		if(requestParams.containsKey("onhold"))
			_CleanerTicketForm.setOnhold((String)requestParams.get("onhold"));
		if(requestParams.containsKey("originalTicketId"))
			_CleanerTicketForm.setOriginalTicketId((String)requestParams.get("originalTicketId"));
		if(requestParams.containsKey("returned"))
			_CleanerTicketForm.setReturned((String)requestParams.get("returned"));
		if(requestParams.containsKey("returnedReasonText"))
			_CleanerTicketForm.setReturnedReasonText((String)requestParams.get("returnedReasonText"));
		if(requestParams.containsKey("returnedNote"))
			_CleanerTicketForm.setReturnedNote((String)requestParams.get("returnedNote"));
		if(requestParams.containsKey("totalCharge"))
			_CleanerTicketForm.setTotalCharge((String)requestParams.get("totalCharge"));
		if(requestParams.containsKey("finalCharge"))
			_CleanerTicketForm.setFinalCharge((String)requestParams.get("finalCharge"));
		if(requestParams.containsKey("discountId"))
			_CleanerTicketForm.setDiscountId((String)requestParams.get("discountId"));
		if(requestParams.containsKey("discountAmount"))
			_CleanerTicketForm.setDiscountAmount((String)requestParams.get("discountAmount"));
		if(requestParams.containsKey("discountNote"))
			_CleanerTicketForm.setDiscountNote((String)requestParams.get("discountNote"));
		if(requestParams.containsKey("timeCreated"))
			_CleanerTicketForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_CleanerTicketForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
		if(requestParams.containsKey("timeCompleted"))
			_CleanerTicketForm.setTimeCompleted((String)requestParams.get("timeCompleted"));
		if(requestParams.containsKey("timeOnhold"))
			_CleanerTicketForm.setTimeOnhold((String)requestParams.get("timeOnhold"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_ticket_home=NULL,/jsp/form_cleaner/cleanerTicket_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_ticket_list=NULL,/jsp/form_cleaner/cleanerTicket_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_ticket_form=NULL,/jsp/form_cleaner/cleanerTicket_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_ticket_ajax=NULL,/jsp/form_cleaner/cleanerTicket_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_home=NULL,/jsp/form_cleaner/cleanerTicket_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_list=NULL,/jsp/form_cleaner/cleanerTicket_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_form=NULL,/jsp/form_cleaner/cleanerTicket_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_ajax=NULL,/jsp/form_cleaner/cleanerTicket_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerTicketDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
