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

import com.autosite.db.CleanerCustomerNotification;
import com.autosite.ds.CleanerCustomerNotificationDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerCustomerNotificationForm;
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




public class CleanerCustomerNotificationAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerCustomerNotificationAction.class);

    public CleanerCustomerNotificationAction(){
        m_ds = CleanerCustomerNotificationDS.getInstance();
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

        CleanerCustomerNotificationForm _CleanerCustomerNotificationForm = (CleanerCustomerNotificationForm) form;
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
        CleanerCustomerNotification _CleanerCustomerNotification = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerCustomerNotification = m_ds.getById(cid);
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
            //CleanerCustomerNotification _CleanerCustomerNotification = m_ds.getById(cid);

            if (_CleanerCustomerNotification == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerCustomerNotification.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerCustomerNotification.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerCustomerNotification);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerCustomerNotificationForm == null) {
    	            editField(request, response, _CleanerCustomerNotification);
				} else {
    	            edit(request, response, _CleanerCustomerNotificationForm, _CleanerCustomerNotification);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerCustomerNotification);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerCustomerNotification o = m_ds.getById( _CleanerCustomerNotification.getId(), true);

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
            //CleanerCustomerNotification _CleanerCustomerNotification = m_ds.getById(cid);

            if (_CleanerCustomerNotification == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerCustomerNotification.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerCustomerNotification.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerCustomerNotification);
                if (returnObjects != null) returnObjects.put("target", _CleanerCustomerNotification);
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
            //CleanerCustomerNotification _CleanerCustomerNotification = m_ds.getById(cid);

            if (_CleanerCustomerNotification == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerCustomerNotification.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerCustomerNotification.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerCustomerNotification);
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

            m_ds.delete(_CleanerCustomerNotification); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerCustomerNotification);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerCustomerNotification);
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


            m_logger.info("Creating new CleanerCustomerNotification" );
            CleanerCustomerNotification _CleanerCustomerNotificationNew = new CleanerCustomerNotification();   

            // Setting IDs for the object
            _CleanerCustomerNotificationNew.setSiteId(site.getId());
			
            if ( _CleanerCustomerNotificationForm == null) {
                setFields(request, response, _CleanerCustomerNotificationNew);
            } else {

            _CleanerCustomerNotificationNew.setCustomerId(WebParamUtil.getLongValue(_CleanerCustomerNotificationForm.getCustomerId()));
            m_logger.debug("setting CustomerId=" +_CleanerCustomerNotificationForm.getCustomerId());


            _CleanerCustomerNotificationNew.setReasonforId(WebParamUtil.getLongValue(_CleanerCustomerNotificationForm.getReasonforId()));
            m_logger.debug("setting ReasonforId=" +_CleanerCustomerNotificationForm.getReasonforId());


            _CleanerCustomerNotificationNew.setReasonforTarget(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getReasonforTarget()));
            m_logger.debug("setting ReasonforTarget=" +_CleanerCustomerNotificationForm.getReasonforTarget());


            _CleanerCustomerNotificationNew.setNotificationType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getNotificationType()));
            m_logger.debug("setting NotificationType=" +_CleanerCustomerNotificationForm.getNotificationType());


            _CleanerCustomerNotificationNew.setSourceType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getSourceType()));
            m_logger.debug("setting SourceType=" +_CleanerCustomerNotificationForm.getSourceType());


            _CleanerCustomerNotificationNew.setTriggerType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getTriggerType()));
            m_logger.debug("setting TriggerType=" +_CleanerCustomerNotificationForm.getTriggerType());


            _CleanerCustomerNotificationNew.setIsRetransmit(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getIsRetransmit()));
            m_logger.debug("setting IsRetransmit=" +_CleanerCustomerNotificationForm.getIsRetransmit());


            _CleanerCustomerNotificationNew.setMethodType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getMethodType()));
            m_logger.debug("setting MethodType=" +_CleanerCustomerNotificationForm.getMethodType());


            _CleanerCustomerNotificationNew.setTemplateType(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getTemplateType()));
            m_logger.debug("setting TemplateType=" +_CleanerCustomerNotificationForm.getTemplateType());


            _CleanerCustomerNotificationNew.setContent(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getContent()));
            m_logger.debug("setting Content=" +_CleanerCustomerNotificationForm.getContent());


            _CleanerCustomerNotificationNew.setDestination(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getDestination()));
            m_logger.debug("setting Destination=" +_CleanerCustomerNotificationForm.getDestination());


            _CleanerCustomerNotificationNew.setReference(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getReference()));
            m_logger.debug("setting Reference=" +_CleanerCustomerNotificationForm.getReference());


            _CleanerCustomerNotificationNew.setTimeScheduled(WebParamUtil.getTimestampValue(_CleanerCustomerNotificationForm.getTimeScheduled()));
            m_logger.debug("setting TimeScheduled=" +_CleanerCustomerNotificationForm.getTimeScheduled());


            _CleanerCustomerNotificationNew.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerCustomerNotificationForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerCustomerNotificationForm.getTimeCreated());

        	_CleanerCustomerNotificationNew.setTimeCreated(new TimeNow());

            _CleanerCustomerNotificationNew.setTimeSent(WebParamUtil.getTimestampValue(_CleanerCustomerNotificationForm.getTimeSent()));
            m_logger.debug("setting TimeSent=" +_CleanerCustomerNotificationForm.getTimeSent());


			}

            try {
                checkDepedenceIntegrity(_CleanerCustomerNotificationNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerCustomerNotificationNew);
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
            
            if (_CleanerCustomerNotificationNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerCustomerNotificationNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerCustomerNotificationNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerCustomerNotificationNew);
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
             _CleanerCustomerNotification =  _CleanerCustomerNotificationNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerCustomerNotificationForm _CleanerCustomerNotificationForm, CleanerCustomerNotification _CleanerCustomerNotification) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomerNotification _CleanerCustomerNotification = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerCustomerNotificationDS.objectToString(_CleanerCustomerNotification));

        _CleanerCustomerNotification.setCustomerId(WebParamUtil.getLongValue(_CleanerCustomerNotificationForm.getCustomerId()));


        _CleanerCustomerNotification.setReasonforId(WebParamUtil.getLongValue(_CleanerCustomerNotificationForm.getReasonforId()));


        _CleanerCustomerNotification.setReasonforTarget(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getReasonforTarget()));


        _CleanerCustomerNotification.setNotificationType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getNotificationType()));


        _CleanerCustomerNotification.setSourceType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getSourceType()));


        _CleanerCustomerNotification.setTriggerType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getTriggerType()));


        _CleanerCustomerNotification.setIsRetransmit(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getIsRetransmit()));


        _CleanerCustomerNotification.setMethodType(WebParamUtil.getIntegerValue(_CleanerCustomerNotificationForm.getMethodType()));


        _CleanerCustomerNotification.setTemplateType(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getTemplateType()));


        _CleanerCustomerNotification.setContent(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getContent()));


        _CleanerCustomerNotification.setDestination(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getDestination()));


        _CleanerCustomerNotification.setReference(WebParamUtil.getStringValue(_CleanerCustomerNotificationForm.getReference()));


        _CleanerCustomerNotification.setTimeScheduled(WebParamUtil.getTimestampValue(_CleanerCustomerNotificationForm.getTimeScheduled()));




        _CleanerCustomerNotification.setTimeSent(WebParamUtil.getTimestampValue(_CleanerCustomerNotificationForm.getTimeSent()));



        m_actionExtent.beforeUpdate(request, response, _CleanerCustomerNotification);
        m_ds.update(_CleanerCustomerNotification);
        m_actionExtent.afterUpdate(request, response, _CleanerCustomerNotification);
        m_logger.debug("After update " + CleanerCustomerNotificationDS.objectToString(_CleanerCustomerNotification));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerCustomerNotification _CleanerCustomerNotification) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomerNotification _CleanerCustomerNotification = m_ds.getById(cid);

        if (!isMissing(request.getParameter("customerId"))) {
            m_logger.debug("updating param customerId from " +_CleanerCustomerNotification.getCustomerId() + "->" + request.getParameter("customerId"));
            _CleanerCustomerNotification.setCustomerId(WebParamUtil.getLongValue(request.getParameter("customerId")));

        }
        if (!isMissing(request.getParameter("reasonforId"))) {
            m_logger.debug("updating param reasonforId from " +_CleanerCustomerNotification.getReasonforId() + "->" + request.getParameter("reasonforId"));
            _CleanerCustomerNotification.setReasonforId(WebParamUtil.getLongValue(request.getParameter("reasonforId")));

        }
        if (!isMissing(request.getParameter("reasonforTarget"))) {
            m_logger.debug("updating param reasonforTarget from " +_CleanerCustomerNotification.getReasonforTarget() + "->" + request.getParameter("reasonforTarget"));
            _CleanerCustomerNotification.setReasonforTarget(WebParamUtil.getStringValue(request.getParameter("reasonforTarget")));

        }
        if (!isMissing(request.getParameter("notificationType"))) {
            m_logger.debug("updating param notificationType from " +_CleanerCustomerNotification.getNotificationType() + "->" + request.getParameter("notificationType"));
            _CleanerCustomerNotification.setNotificationType(WebParamUtil.getIntegerValue(request.getParameter("notificationType")));

        }
        if (!isMissing(request.getParameter("sourceType"))) {
            m_logger.debug("updating param sourceType from " +_CleanerCustomerNotification.getSourceType() + "->" + request.getParameter("sourceType"));
            _CleanerCustomerNotification.setSourceType(WebParamUtil.getIntegerValue(request.getParameter("sourceType")));

        }
        if (!isMissing(request.getParameter("triggerType"))) {
            m_logger.debug("updating param triggerType from " +_CleanerCustomerNotification.getTriggerType() + "->" + request.getParameter("triggerType"));
            _CleanerCustomerNotification.setTriggerType(WebParamUtil.getIntegerValue(request.getParameter("triggerType")));

        }
        if (!isMissing(request.getParameter("isRetransmit"))) {
            m_logger.debug("updating param isRetransmit from " +_CleanerCustomerNotification.getIsRetransmit() + "->" + request.getParameter("isRetransmit"));
            _CleanerCustomerNotification.setIsRetransmit(WebParamUtil.getIntegerValue(request.getParameter("isRetransmit")));

        }
        if (!isMissing(request.getParameter("methodType"))) {
            m_logger.debug("updating param methodType from " +_CleanerCustomerNotification.getMethodType() + "->" + request.getParameter("methodType"));
            _CleanerCustomerNotification.setMethodType(WebParamUtil.getIntegerValue(request.getParameter("methodType")));

        }
        if (!isMissing(request.getParameter("templateType"))) {
            m_logger.debug("updating param templateType from " +_CleanerCustomerNotification.getTemplateType() + "->" + request.getParameter("templateType"));
            _CleanerCustomerNotification.setTemplateType(WebParamUtil.getStringValue(request.getParameter("templateType")));

        }
        if (!isMissing(request.getParameter("content"))) {
            m_logger.debug("updating param content from " +_CleanerCustomerNotification.getContent() + "->" + request.getParameter("content"));
            _CleanerCustomerNotification.setContent(WebParamUtil.getStringValue(request.getParameter("content")));

        }
        if (!isMissing(request.getParameter("destination"))) {
            m_logger.debug("updating param destination from " +_CleanerCustomerNotification.getDestination() + "->" + request.getParameter("destination"));
            _CleanerCustomerNotification.setDestination(WebParamUtil.getStringValue(request.getParameter("destination")));

        }
        if (!isMissing(request.getParameter("reference"))) {
            m_logger.debug("updating param reference from " +_CleanerCustomerNotification.getReference() + "->" + request.getParameter("reference"));
            _CleanerCustomerNotification.setReference(WebParamUtil.getStringValue(request.getParameter("reference")));

        }
        if (!isMissing(request.getParameter("timeScheduled"))) {
            m_logger.debug("updating param timeScheduled from " +_CleanerCustomerNotification.getTimeScheduled() + "->" + request.getParameter("timeScheduled"));
            _CleanerCustomerNotification.setTimeScheduled(WebParamUtil.getTimestampValue(request.getParameter("timeScheduled")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerCustomerNotification.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerCustomerNotification.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeSent"))) {
            m_logger.debug("updating param timeSent from " +_CleanerCustomerNotification.getTimeSent() + "->" + request.getParameter("timeSent"));
            _CleanerCustomerNotification.setTimeSent(WebParamUtil.getTimestampValue(request.getParameter("timeSent")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerCustomerNotification);
        m_ds.update(_CleanerCustomerNotification);
        m_actionExtent.afterUpdate(request, response, _CleanerCustomerNotification);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerCustomerNotification _CleanerCustomerNotification) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomerNotification _CleanerCustomerNotification = m_ds.getById(cid);

        if (!isMissing(request.getParameter("customerId"))) {
            m_logger.debug("updating param customerId from " +_CleanerCustomerNotification.getCustomerId() + "->" + request.getParameter("customerId"));
            _CleanerCustomerNotification.setCustomerId(WebParamUtil.getLongValue(request.getParameter("customerId")));

        }
        if (!isMissing(request.getParameter("reasonforId"))) {
            m_logger.debug("updating param reasonforId from " +_CleanerCustomerNotification.getReasonforId() + "->" + request.getParameter("reasonforId"));
            _CleanerCustomerNotification.setReasonforId(WebParamUtil.getLongValue(request.getParameter("reasonforId")));

        }
        if (!isMissing(request.getParameter("reasonforTarget"))) {
            m_logger.debug("updating param reasonforTarget from " +_CleanerCustomerNotification.getReasonforTarget() + "->" + request.getParameter("reasonforTarget"));
            _CleanerCustomerNotification.setReasonforTarget(WebParamUtil.getStringValue(request.getParameter("reasonforTarget")));

        }
        if (!isMissing(request.getParameter("notificationType"))) {
            m_logger.debug("updating param notificationType from " +_CleanerCustomerNotification.getNotificationType() + "->" + request.getParameter("notificationType"));
            _CleanerCustomerNotification.setNotificationType(WebParamUtil.getIntegerValue(request.getParameter("notificationType")));

        }
        if (!isMissing(request.getParameter("sourceType"))) {
            m_logger.debug("updating param sourceType from " +_CleanerCustomerNotification.getSourceType() + "->" + request.getParameter("sourceType"));
            _CleanerCustomerNotification.setSourceType(WebParamUtil.getIntegerValue(request.getParameter("sourceType")));

        }
        if (!isMissing(request.getParameter("triggerType"))) {
            m_logger.debug("updating param triggerType from " +_CleanerCustomerNotification.getTriggerType() + "->" + request.getParameter("triggerType"));
            _CleanerCustomerNotification.setTriggerType(WebParamUtil.getIntegerValue(request.getParameter("triggerType")));

        }
        if (!isMissing(request.getParameter("isRetransmit"))) {
            m_logger.debug("updating param isRetransmit from " +_CleanerCustomerNotification.getIsRetransmit() + "->" + request.getParameter("isRetransmit"));
            _CleanerCustomerNotification.setIsRetransmit(WebParamUtil.getIntegerValue(request.getParameter("isRetransmit")));

        }
        if (!isMissing(request.getParameter("methodType"))) {
            m_logger.debug("updating param methodType from " +_CleanerCustomerNotification.getMethodType() + "->" + request.getParameter("methodType"));
            _CleanerCustomerNotification.setMethodType(WebParamUtil.getIntegerValue(request.getParameter("methodType")));

        }
        if (!isMissing(request.getParameter("templateType"))) {
            m_logger.debug("updating param templateType from " +_CleanerCustomerNotification.getTemplateType() + "->" + request.getParameter("templateType"));
            _CleanerCustomerNotification.setTemplateType(WebParamUtil.getStringValue(request.getParameter("templateType")));

        }
        if (!isMissing(request.getParameter("content"))) {
            m_logger.debug("updating param content from " +_CleanerCustomerNotification.getContent() + "->" + request.getParameter("content"));
            _CleanerCustomerNotification.setContent(WebParamUtil.getStringValue(request.getParameter("content")));

        }
        if (!isMissing(request.getParameter("destination"))) {
            m_logger.debug("updating param destination from " +_CleanerCustomerNotification.getDestination() + "->" + request.getParameter("destination"));
            _CleanerCustomerNotification.setDestination(WebParamUtil.getStringValue(request.getParameter("destination")));

        }
        if (!isMissing(request.getParameter("reference"))) {
            m_logger.debug("updating param reference from " +_CleanerCustomerNotification.getReference() + "->" + request.getParameter("reference"));
            _CleanerCustomerNotification.setReference(WebParamUtil.getStringValue(request.getParameter("reference")));

        }
        if (!isMissing(request.getParameter("timeScheduled"))) {
            m_logger.debug("updating param timeScheduled from " +_CleanerCustomerNotification.getTimeScheduled() + "->" + request.getParameter("timeScheduled"));
            _CleanerCustomerNotification.setTimeScheduled(WebParamUtil.getTimestampValue(request.getParameter("timeScheduled")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerCustomerNotification.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerCustomerNotification.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeSent"))) {
            m_logger.debug("updating param timeSent from " +_CleanerCustomerNotification.getTimeSent() + "->" + request.getParameter("timeSent"));
            _CleanerCustomerNotification.setTimeSent(WebParamUtil.getTimestampValue(request.getParameter("timeSent")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerCustomerNotification _CleanerCustomerNotification) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerCustomerNotification _CleanerCustomerNotification = m_ds.getById(cid);

        if (!isMissing(request.getParameter("customerId"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getCustomerId());
        }
        if (!isMissing(request.getParameter("reasonforId"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getReasonforId());
        }
        if (!isMissing(request.getParameter("reasonforTarget"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getReasonforTarget());
        }
        if (!isMissing(request.getParameter("notificationType"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getNotificationType());
        }
        if (!isMissing(request.getParameter("sourceType"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getSourceType());
        }
        if (!isMissing(request.getParameter("triggerType"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getTriggerType());
        }
        if (!isMissing(request.getParameter("isRetransmit"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getIsRetransmit());
        }
        if (!isMissing(request.getParameter("methodType"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getMethodType());
        }
        if (!isMissing(request.getParameter("templateType"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getTemplateType());
        }
        if (!isMissing(request.getParameter("content"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getContent());
        }
        if (!isMissing(request.getParameter("destination"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getDestination());
        }
        if (!isMissing(request.getParameter("reference"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getReference());
        }
        if (!isMissing(request.getParameter("timeScheduled"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getTimeScheduled());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeSent"))) {
			return  JtStringUtil.valueOf(_CleanerCustomerNotification.getTimeSent());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerCustomerNotification _CleanerCustomerNotification) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerCustomerNotification _CleanerCustomerNotification) {
        if (fieldName == null || fieldName.equals("")|| _CleanerCustomerNotification == null) return null;
        
        if (fieldName.equals("customerId")) {
            return WebUtil.display(_CleanerCustomerNotification.getCustomerId());
        }
        if (fieldName.equals("reasonforId")) {
            return WebUtil.display(_CleanerCustomerNotification.getReasonforId());
        }
        if (fieldName.equals("reasonforTarget")) {
            return WebUtil.display(_CleanerCustomerNotification.getReasonforTarget());
        }
        if (fieldName.equals("notificationType")) {
            return WebUtil.display(_CleanerCustomerNotification.getNotificationType());
        }
        if (fieldName.equals("sourceType")) {
            return WebUtil.display(_CleanerCustomerNotification.getSourceType());
        }
        if (fieldName.equals("triggerType")) {
            return WebUtil.display(_CleanerCustomerNotification.getTriggerType());
        }
        if (fieldName.equals("isRetransmit")) {
            return WebUtil.display(_CleanerCustomerNotification.getIsRetransmit());
        }
        if (fieldName.equals("methodType")) {
            return WebUtil.display(_CleanerCustomerNotification.getMethodType());
        }
        if (fieldName.equals("templateType")) {
            return WebUtil.display(_CleanerCustomerNotification.getTemplateType());
        }
        if (fieldName.equals("content")) {
            return WebUtil.display(_CleanerCustomerNotification.getContent());
        }
        if (fieldName.equals("destination")) {
            return WebUtil.display(_CleanerCustomerNotification.getDestination());
        }
        if (fieldName.equals("reference")) {
            return WebUtil.display(_CleanerCustomerNotification.getReference());
        }
        if (fieldName.equals("timeScheduled")) {
            return WebUtil.display(_CleanerCustomerNotification.getTimeScheduled());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_CleanerCustomerNotification.getTimeCreated());
        }
        if (fieldName.equals("timeSent")) {
            return WebUtil.display(_CleanerCustomerNotification.getTimeSent());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerCustomerNotificationForm _CleanerCustomerNotificationForm = (CleanerCustomerNotificationForm) form;

		if(requestParams.containsKey("customerId"))
			_CleanerCustomerNotificationForm.setCustomerId((String)requestParams.get("customerId"));
		if(requestParams.containsKey("reasonforId"))
			_CleanerCustomerNotificationForm.setReasonforId((String)requestParams.get("reasonforId"));
		if(requestParams.containsKey("reasonforTarget"))
			_CleanerCustomerNotificationForm.setReasonforTarget((String)requestParams.get("reasonforTarget"));
		if(requestParams.containsKey("notificationType"))
			_CleanerCustomerNotificationForm.setNotificationType((String)requestParams.get("notificationType"));
		if(requestParams.containsKey("sourceType"))
			_CleanerCustomerNotificationForm.setSourceType((String)requestParams.get("sourceType"));
		if(requestParams.containsKey("triggerType"))
			_CleanerCustomerNotificationForm.setTriggerType((String)requestParams.get("triggerType"));
		if(requestParams.containsKey("isRetransmit"))
			_CleanerCustomerNotificationForm.setIsRetransmit((String)requestParams.get("isRetransmit"));
		if(requestParams.containsKey("methodType"))
			_CleanerCustomerNotificationForm.setMethodType((String)requestParams.get("methodType"));
		if(requestParams.containsKey("templateType"))
			_CleanerCustomerNotificationForm.setTemplateType((String)requestParams.get("templateType"));
		if(requestParams.containsKey("content"))
			_CleanerCustomerNotificationForm.setContent((String)requestParams.get("content"));
		if(requestParams.containsKey("destination"))
			_CleanerCustomerNotificationForm.setDestination((String)requestParams.get("destination"));
		if(requestParams.containsKey("reference"))
			_CleanerCustomerNotificationForm.setReference((String)requestParams.get("reference"));
		if(requestParams.containsKey("timeScheduled"))
			_CleanerCustomerNotificationForm.setTimeScheduled((String)requestParams.get("timeScheduled"));
		if(requestParams.containsKey("timeCreated"))
			_CleanerCustomerNotificationForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeSent"))
			_CleanerCustomerNotificationForm.setTimeSent((String)requestParams.get("timeSent"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return false;
    }
    public boolean isPagelessSessionOnly(){
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_customer_notification_home=NULL,/jsp/form_cleaner/cleanerCustomerNotification_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_customer_notification_list=NULL,/jsp/form_cleaner/cleanerCustomerNotification_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_customer_notification_form=NULL,/jsp/form_cleaner/cleanerCustomerNotification_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_customer_notification_ajax=NULL,/jsp/form_cleaner/cleanerCustomerNotification_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_notification_home=NULL,/jsp/form_cleaner/cleanerCustomerNotification_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_notification_list=NULL,/jsp/form_cleaner/cleanerCustomerNotification_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_notification_form=NULL,/jsp/form_cleaner/cleanerCustomerNotification_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_customer_notification_ajax=NULL,/jsp/form_cleaner/cleanerCustomerNotification_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerCustomerNotificationDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
