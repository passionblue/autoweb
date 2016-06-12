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

import com.autosite.db.CleanerTicketItem;
import com.autosite.ds.CleanerTicketItemDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerTicketItemForm;
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




public class CleanerTicketItemAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerTicketItemAction.class);

    public CleanerTicketItemAction(){
        m_ds = CleanerTicketItemDS.getInstance();
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

        CleanerTicketItemForm _CleanerTicketItemForm = (CleanerTicketItemForm) form;
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


        CleanerTicketItem _CleanerTicketItem = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerTicketItem = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerTicketItem _CleanerTicketItem = m_ds.getById(cid);

            if (_CleanerTicketItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerTicketItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerTicketItem.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerTicketItem);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerTicketItemForm == null) {
    	            editField(request, response, _CleanerTicketItem);
				} else {
    	            edit(request, response, _CleanerTicketItemForm, _CleanerTicketItem);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerTicketItem);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerTicketItem o = m_ds.getById( _CleanerTicketItem.getId(), true);

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
            //CleanerTicketItem _CleanerTicketItem = m_ds.getById(cid);

            if (_CleanerTicketItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerTicketItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerTicketItem.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerTicketItem);
                if (returnObjects != null) returnObjects.put("target", _CleanerTicketItem);
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
            //CleanerTicketItem _CleanerTicketItem = m_ds.getById(cid);

            if (_CleanerTicketItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerTicketItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerTicketItem.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerTicketItem);
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

            m_ds.delete(_CleanerTicketItem); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerTicketItem);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerTicketItem);
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


            m_logger.info("Creating new CleanerTicketItem" );
            CleanerTicketItem _CleanerTicketItemNew = new CleanerTicketItem();   

            // Setting IDs for the object
            _CleanerTicketItemNew.setSiteId(site.getId());
			
            if ( _CleanerTicketItemForm == null) {
                setFields(request, response, _CleanerTicketItemNew);
            } else {

            _CleanerTicketItemNew.setTicketId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getTicketId()));
            m_logger.debug("setting TicketId=" +_CleanerTicketItemForm.getTicketId());


            _CleanerTicketItemNew.setParentTicketId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getParentTicketId()));
            m_logger.debug("setting ParentTicketId=" +_CleanerTicketItemForm.getParentTicketId());


            _CleanerTicketItemNew.setProductId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getProductId()));
            m_logger.debug("setting ProductId=" +_CleanerTicketItemForm.getProductId());


            _CleanerTicketItemNew.setSubtotalAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getSubtotalAmount()));
            m_logger.debug("setting SubtotalAmount=" +_CleanerTicketItemForm.getSubtotalAmount());


            _CleanerTicketItemNew.setTotalAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getTotalAmount()));
            m_logger.debug("setting TotalAmount=" +_CleanerTicketItemForm.getTotalAmount());


            _CleanerTicketItemNew.setDiscountId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getDiscountId()));
            m_logger.debug("setting DiscountId=" +_CleanerTicketItemForm.getDiscountId());


            _CleanerTicketItemNew.setTotalDiscountAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getTotalDiscountAmount()));
            m_logger.debug("setting TotalDiscountAmount=" +_CleanerTicketItemForm.getTotalDiscountAmount());


            _CleanerTicketItemNew.setSpecialDiscountAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getSpecialDiscountAmount()));
            m_logger.debug("setting SpecialDiscountAmount=" +_CleanerTicketItemForm.getSpecialDiscountAmount());


            _CleanerTicketItemNew.setNote(WebParamUtil.getStringValue(_CleanerTicketItemForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerTicketItemForm.getNote());


            _CleanerTicketItemNew.setTimeCreated(WebParamUtil.getTimestampValue(_CleanerTicketItemForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_CleanerTicketItemForm.getTimeCreated());

        	_CleanerTicketItemNew.setTimeCreated(new TimeNow());

            _CleanerTicketItemNew.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerTicketItemForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_CleanerTicketItemForm.getTimeUpdated());

        	_CleanerTicketItemNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_CleanerTicketItemNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerTicketItemNew);
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
            
            if (_CleanerTicketItemNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerTicketItemNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerTicketItemNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerTicketItemNew);
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
             _CleanerTicketItem =  _CleanerTicketItemNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerTicketItem, "cleaner-ticket-item" );
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerTicketItemForm _CleanerTicketItemForm, CleanerTicketItem _CleanerTicketItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicketItem _CleanerTicketItem = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerTicketItemDS.objectToString(_CleanerTicketItem));

        _CleanerTicketItem.setTicketId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getTicketId()));


        _CleanerTicketItem.setParentTicketId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getParentTicketId()));


        _CleanerTicketItem.setProductId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getProductId()));


        _CleanerTicketItem.setSubtotalAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getSubtotalAmount()));


        _CleanerTicketItem.setTotalAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getTotalAmount()));


        _CleanerTicketItem.setDiscountId(WebParamUtil.getLongValue(_CleanerTicketItemForm.getDiscountId()));


        _CleanerTicketItem.setTotalDiscountAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getTotalDiscountAmount()));


        _CleanerTicketItem.setSpecialDiscountAmount(WebParamUtil.getDoubleValue(_CleanerTicketItemForm.getSpecialDiscountAmount()));


        _CleanerTicketItem.setNote(WebParamUtil.getStringValue(_CleanerTicketItemForm.getNote()));




        _CleanerTicketItem.setTimeUpdated(WebParamUtil.getTimestampValue(_CleanerTicketItemForm.getTimeUpdated()));

        _CleanerTicketItem.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _CleanerTicketItem);
        m_ds.update(_CleanerTicketItem);
        m_actionExtent.afterUpdate(request, response, _CleanerTicketItem);
        m_logger.debug("After update " + CleanerTicketItemDS.objectToString(_CleanerTicketItem));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerTicketItem _CleanerTicketItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicketItem _CleanerTicketItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ticketId"))) {
            m_logger.debug("updating param ticketId from " +_CleanerTicketItem.getTicketId() + "->" + request.getParameter("ticketId"));
            _CleanerTicketItem.setTicketId(WebParamUtil.getLongValue(request.getParameter("ticketId")));

        }
        if (!isMissing(request.getParameter("parentTicketId"))) {
            m_logger.debug("updating param parentTicketId from " +_CleanerTicketItem.getParentTicketId() + "->" + request.getParameter("parentTicketId"));
            _CleanerTicketItem.setParentTicketId(WebParamUtil.getLongValue(request.getParameter("parentTicketId")));

        }
        if (!isMissing(request.getParameter("productId"))) {
            m_logger.debug("updating param productId from " +_CleanerTicketItem.getProductId() + "->" + request.getParameter("productId"));
            _CleanerTicketItem.setProductId(WebParamUtil.getLongValue(request.getParameter("productId")));

        }
        if (!isMissing(request.getParameter("subtotalAmount"))) {
            m_logger.debug("updating param subtotalAmount from " +_CleanerTicketItem.getSubtotalAmount() + "->" + request.getParameter("subtotalAmount"));
            _CleanerTicketItem.setSubtotalAmount(WebParamUtil.getDoubleValue(request.getParameter("subtotalAmount")));

        }
        if (!isMissing(request.getParameter("totalAmount"))) {
            m_logger.debug("updating param totalAmount from " +_CleanerTicketItem.getTotalAmount() + "->" + request.getParameter("totalAmount"));
            _CleanerTicketItem.setTotalAmount(WebParamUtil.getDoubleValue(request.getParameter("totalAmount")));

        }
        if (!isMissing(request.getParameter("discountId"))) {
            m_logger.debug("updating param discountId from " +_CleanerTicketItem.getDiscountId() + "->" + request.getParameter("discountId"));
            _CleanerTicketItem.setDiscountId(WebParamUtil.getLongValue(request.getParameter("discountId")));

        }
        if (!isMissing(request.getParameter("totalDiscountAmount"))) {
            m_logger.debug("updating param totalDiscountAmount from " +_CleanerTicketItem.getTotalDiscountAmount() + "->" + request.getParameter("totalDiscountAmount"));
            _CleanerTicketItem.setTotalDiscountAmount(WebParamUtil.getDoubleValue(request.getParameter("totalDiscountAmount")));

        }
        if (!isMissing(request.getParameter("specialDiscountAmount"))) {
            m_logger.debug("updating param specialDiscountAmount from " +_CleanerTicketItem.getSpecialDiscountAmount() + "->" + request.getParameter("specialDiscountAmount"));
            _CleanerTicketItem.setSpecialDiscountAmount(WebParamUtil.getDoubleValue(request.getParameter("specialDiscountAmount")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerTicketItem.getNote() + "->" + request.getParameter("note"));
            _CleanerTicketItem.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerTicketItem.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerTicketItem.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerTicketItem.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerTicketItem.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerTicketItem.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _CleanerTicketItem);
        m_ds.update(_CleanerTicketItem);
        m_actionExtent.afterUpdate(request, response, _CleanerTicketItem);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerTicketItem _CleanerTicketItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicketItem _CleanerTicketItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ticketId"))) {
            m_logger.debug("updating param ticketId from " +_CleanerTicketItem.getTicketId() + "->" + request.getParameter("ticketId"));
            _CleanerTicketItem.setTicketId(WebParamUtil.getLongValue(request.getParameter("ticketId")));

        }
        if (!isMissing(request.getParameter("parentTicketId"))) {
            m_logger.debug("updating param parentTicketId from " +_CleanerTicketItem.getParentTicketId() + "->" + request.getParameter("parentTicketId"));
            _CleanerTicketItem.setParentTicketId(WebParamUtil.getLongValue(request.getParameter("parentTicketId")));

        }
        if (!isMissing(request.getParameter("productId"))) {
            m_logger.debug("updating param productId from " +_CleanerTicketItem.getProductId() + "->" + request.getParameter("productId"));
            _CleanerTicketItem.setProductId(WebParamUtil.getLongValue(request.getParameter("productId")));

        }
        if (!isMissing(request.getParameter("subtotalAmount"))) {
            m_logger.debug("updating param subtotalAmount from " +_CleanerTicketItem.getSubtotalAmount() + "->" + request.getParameter("subtotalAmount"));
            _CleanerTicketItem.setSubtotalAmount(WebParamUtil.getDoubleValue(request.getParameter("subtotalAmount")));

        }
        if (!isMissing(request.getParameter("totalAmount"))) {
            m_logger.debug("updating param totalAmount from " +_CleanerTicketItem.getTotalAmount() + "->" + request.getParameter("totalAmount"));
            _CleanerTicketItem.setTotalAmount(WebParamUtil.getDoubleValue(request.getParameter("totalAmount")));

        }
        if (!isMissing(request.getParameter("discountId"))) {
            m_logger.debug("updating param discountId from " +_CleanerTicketItem.getDiscountId() + "->" + request.getParameter("discountId"));
            _CleanerTicketItem.setDiscountId(WebParamUtil.getLongValue(request.getParameter("discountId")));

        }
        if (!isMissing(request.getParameter("totalDiscountAmount"))) {
            m_logger.debug("updating param totalDiscountAmount from " +_CleanerTicketItem.getTotalDiscountAmount() + "->" + request.getParameter("totalDiscountAmount"));
            _CleanerTicketItem.setTotalDiscountAmount(WebParamUtil.getDoubleValue(request.getParameter("totalDiscountAmount")));

        }
        if (!isMissing(request.getParameter("specialDiscountAmount"))) {
            m_logger.debug("updating param specialDiscountAmount from " +_CleanerTicketItem.getSpecialDiscountAmount() + "->" + request.getParameter("specialDiscountAmount"));
            _CleanerTicketItem.setSpecialDiscountAmount(WebParamUtil.getDoubleValue(request.getParameter("specialDiscountAmount")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerTicketItem.getNote() + "->" + request.getParameter("note"));
            _CleanerTicketItem.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_CleanerTicketItem.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _CleanerTicketItem.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_CleanerTicketItem.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _CleanerTicketItem.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _CleanerTicketItem.setTimeUpdated(new TimeNow());
        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerTicketItem _CleanerTicketItem) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerTicketItem _CleanerTicketItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ticketId"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getTicketId());
        }
        if (!isMissing(request.getParameter("parentTicketId"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getParentTicketId());
        }
        if (!isMissing(request.getParameter("productId"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getProductId());
        }
        if (!isMissing(request.getParameter("subtotalAmount"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getSubtotalAmount());
        }
        if (!isMissing(request.getParameter("totalAmount"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getTotalAmount());
        }
        if (!isMissing(request.getParameter("discountId"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getDiscountId());
        }
        if (!isMissing(request.getParameter("totalDiscountAmount"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getTotalDiscountAmount());
        }
        if (!isMissing(request.getParameter("specialDiscountAmount"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getSpecialDiscountAmount());
        }
        if (!isMissing(request.getParameter("note"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getNote());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_CleanerTicketItem.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerTicketItem _CleanerTicketItem) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerTicketItem _CleanerTicketItem) {
        if (fieldName == null || fieldName.equals("")|| _CleanerTicketItem == null) return null;
        
        if (fieldName.equals("ticketId")) {
            return WebUtil.display(_CleanerTicketItem.getTicketId());
        }
        if (fieldName.equals("parentTicketId")) {
            return WebUtil.display(_CleanerTicketItem.getParentTicketId());
        }
        if (fieldName.equals("productId")) {
            return WebUtil.display(_CleanerTicketItem.getProductId());
        }
        if (fieldName.equals("subtotalAmount")) {
            return WebUtil.display(_CleanerTicketItem.getSubtotalAmount());
        }
        if (fieldName.equals("totalAmount")) {
            return WebUtil.display(_CleanerTicketItem.getTotalAmount());
        }
        if (fieldName.equals("discountId")) {
            return WebUtil.display(_CleanerTicketItem.getDiscountId());
        }
        if (fieldName.equals("totalDiscountAmount")) {
            return WebUtil.display(_CleanerTicketItem.getTotalDiscountAmount());
        }
        if (fieldName.equals("specialDiscountAmount")) {
            return WebUtil.display(_CleanerTicketItem.getSpecialDiscountAmount());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_CleanerTicketItem.getNote());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_CleanerTicketItem.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_CleanerTicketItem.getTimeUpdated());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerTicketItemForm _CleanerTicketItemForm = (CleanerTicketItemForm) form;

		if(requestParams.containsKey("ticketId"))
			_CleanerTicketItemForm.setTicketId((String)requestParams.get("ticketId"));
		if(requestParams.containsKey("parentTicketId"))
			_CleanerTicketItemForm.setParentTicketId((String)requestParams.get("parentTicketId"));
		if(requestParams.containsKey("productId"))
			_CleanerTicketItemForm.setProductId((String)requestParams.get("productId"));
		if(requestParams.containsKey("subtotalAmount"))
			_CleanerTicketItemForm.setSubtotalAmount((String)requestParams.get("subtotalAmount"));
		if(requestParams.containsKey("totalAmount"))
			_CleanerTicketItemForm.setTotalAmount((String)requestParams.get("totalAmount"));
		if(requestParams.containsKey("discountId"))
			_CleanerTicketItemForm.setDiscountId((String)requestParams.get("discountId"));
		if(requestParams.containsKey("totalDiscountAmount"))
			_CleanerTicketItemForm.setTotalDiscountAmount((String)requestParams.get("totalDiscountAmount"));
		if(requestParams.containsKey("specialDiscountAmount"))
			_CleanerTicketItemForm.setSpecialDiscountAmount((String)requestParams.get("specialDiscountAmount"));
		if(requestParams.containsKey("note"))
			_CleanerTicketItemForm.setNote((String)requestParams.get("note"));
		if(requestParams.containsKey("timeCreated"))
			_CleanerTicketItemForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_CleanerTicketItemForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_ticket_item_home=NULL,/jsp/form_cleaner/cleanerTicketItem_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_ticket_item_list=NULL,/jsp/form_cleaner/cleanerTicketItem_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_ticket_item_form=NULL,/jsp/form_cleaner/cleanerTicketItem_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_ticket_item_ajax=NULL,/jsp/form_cleaner/cleanerTicketItem_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_item_home=NULL,/jsp/form_cleaner/cleanerTicketItem_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_item_list=NULL,/jsp/form_cleaner/cleanerTicketItem_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_item_form=NULL,/jsp/form_cleaner/cleanerTicketItem_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_ticket_item_ajax=NULL,/jsp/form_cleaner/cleanerTicketItem_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerTicketItemDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
