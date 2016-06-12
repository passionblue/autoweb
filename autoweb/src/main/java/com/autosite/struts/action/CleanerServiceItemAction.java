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

import com.autosite.db.CleanerServiceItem;
import com.autosite.ds.CleanerServiceItemDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerServiceItemForm;
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




public class CleanerServiceItemAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerServiceItemAction.class);

    public CleanerServiceItemAction(){
        m_ds = CleanerServiceItemDS.getInstance();
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

        CleanerServiceItemForm _CleanerServiceItemForm = (CleanerServiceItemForm) form;
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


        CleanerServiceItem _CleanerServiceItem = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerServiceItem = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerServiceItem _CleanerServiceItem = m_ds.getById(cid);

            if (_CleanerServiceItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerServiceItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceItem.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerServiceItem);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerServiceItemForm == null) {
    	            editField(request, response, _CleanerServiceItem);
				} else {
    	            edit(request, response, _CleanerServiceItemForm, _CleanerServiceItem);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerServiceItem);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerServiceItem o = m_ds.getById( _CleanerServiceItem.getId(), true);

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
            //CleanerServiceItem _CleanerServiceItem = m_ds.getById(cid);

            if (_CleanerServiceItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerServiceItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceItem.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerServiceItem);
                if (returnObjects != null) returnObjects.put("target", _CleanerServiceItem);
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
            //CleanerServiceItem _CleanerServiceItem = m_ds.getById(cid);

            if (_CleanerServiceItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerServiceItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceItem.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerServiceItem);
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

            m_ds.delete(_CleanerServiceItem); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerServiceItem);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerServiceItem);
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


            m_logger.info("Creating new CleanerServiceItem" );
            CleanerServiceItem _CleanerServiceItemNew = new CleanerServiceItem();   

            // Setting IDs for the object
            _CleanerServiceItemNew.setSiteId(site.getId());
			
            if ( _CleanerServiceItemForm == null) {
                setFields(request, response, _CleanerServiceItemNew);
            } else {

            _CleanerServiceItemNew.setServiceId(WebParamUtil.getLongValue(_CleanerServiceItemForm.getServiceId()));
            m_logger.debug("setting ServiceId=" +_CleanerServiceItemForm.getServiceId());


            _CleanerServiceItemNew.setServiceItemId(WebParamUtil.getLongValue(_CleanerServiceItemForm.getServiceItemId()));
            m_logger.debug("setting ServiceItemId=" +_CleanerServiceItemForm.getServiceItemId());


            _CleanerServiceItemNew.setItemType(WebParamUtil.getIntegerValue(_CleanerServiceItemForm.getItemType()));
            m_logger.debug("setting ItemType=" +_CleanerServiceItemForm.getItemType());


            _CleanerServiceItemNew.setTitle(WebParamUtil.getStringValue(_CleanerServiceItemForm.getTitle()));
            m_logger.debug("setting Title=" +_CleanerServiceItemForm.getTitle());


            _CleanerServiceItemNew.setImagePath(WebParamUtil.getStringValue(_CleanerServiceItemForm.getImagePath()));
            m_logger.debug("setting ImagePath=" +_CleanerServiceItemForm.getImagePath());


            _CleanerServiceItemNew.setImagePathLocal(WebParamUtil.getStringValue(_CleanerServiceItemForm.getImagePathLocal()));
            m_logger.debug("setting ImagePathLocal=" +_CleanerServiceItemForm.getImagePathLocal());


            _CleanerServiceItemNew.setBasePrice(WebParamUtil.getDoubleValue(_CleanerServiceItemForm.getBasePrice()));
            m_logger.debug("setting BasePrice=" +_CleanerServiceItemForm.getBasePrice());


            _CleanerServiceItemNew.setNote(WebParamUtil.getStringValue(_CleanerServiceItemForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerServiceItemForm.getNote());


			}

            try {
                checkDepedenceIntegrity(_CleanerServiceItemNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerServiceItemNew);
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
            
            if (_CleanerServiceItemNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerServiceItemNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerServiceItemNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerServiceItemNew);
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
             _CleanerServiceItem =  _CleanerServiceItemNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerServiceItem, "cleaner-service-item" );
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerServiceItemForm _CleanerServiceItemForm, CleanerServiceItem _CleanerServiceItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceItem _CleanerServiceItem = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerServiceItemDS.objectToString(_CleanerServiceItem));

        _CleanerServiceItem.setServiceId(WebParamUtil.getLongValue(_CleanerServiceItemForm.getServiceId()));


        _CleanerServiceItem.setServiceItemId(WebParamUtil.getLongValue(_CleanerServiceItemForm.getServiceItemId()));


        _CleanerServiceItem.setItemType(WebParamUtil.getIntegerValue(_CleanerServiceItemForm.getItemType()));


        _CleanerServiceItem.setTitle(WebParamUtil.getStringValue(_CleanerServiceItemForm.getTitle()));


        _CleanerServiceItem.setImagePath(WebParamUtil.getStringValue(_CleanerServiceItemForm.getImagePath()));


        _CleanerServiceItem.setImagePathLocal(WebParamUtil.getStringValue(_CleanerServiceItemForm.getImagePathLocal()));


        _CleanerServiceItem.setBasePrice(WebParamUtil.getDoubleValue(_CleanerServiceItemForm.getBasePrice()));


        _CleanerServiceItem.setNote(WebParamUtil.getStringValue(_CleanerServiceItemForm.getNote()));



        m_actionExtent.beforeUpdate(request, response, _CleanerServiceItem);
        m_ds.update(_CleanerServiceItem);
        m_actionExtent.afterUpdate(request, response, _CleanerServiceItem);
        m_logger.debug("After update " + CleanerServiceItemDS.objectToString(_CleanerServiceItem));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerServiceItem _CleanerServiceItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceItem _CleanerServiceItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serviceId"))) {
            m_logger.debug("updating param serviceId from " +_CleanerServiceItem.getServiceId() + "->" + request.getParameter("serviceId"));
            _CleanerServiceItem.setServiceId(WebParamUtil.getLongValue(request.getParameter("serviceId")));

        }
        if (!isMissing(request.getParameter("serviceItemId"))) {
            m_logger.debug("updating param serviceItemId from " +_CleanerServiceItem.getServiceItemId() + "->" + request.getParameter("serviceItemId"));
            _CleanerServiceItem.setServiceItemId(WebParamUtil.getLongValue(request.getParameter("serviceItemId")));

        }
        if (!isMissing(request.getParameter("itemType"))) {
            m_logger.debug("updating param itemType from " +_CleanerServiceItem.getItemType() + "->" + request.getParameter("itemType"));
            _CleanerServiceItem.setItemType(WebParamUtil.getIntegerValue(request.getParameter("itemType")));

        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_CleanerServiceItem.getTitle() + "->" + request.getParameter("title"));
            _CleanerServiceItem.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("imagePath"))) {
            m_logger.debug("updating param imagePath from " +_CleanerServiceItem.getImagePath() + "->" + request.getParameter("imagePath"));
            _CleanerServiceItem.setImagePath(WebParamUtil.getStringValue(request.getParameter("imagePath")));

        }
        if (!isMissing(request.getParameter("imagePathLocal"))) {
            m_logger.debug("updating param imagePathLocal from " +_CleanerServiceItem.getImagePathLocal() + "->" + request.getParameter("imagePathLocal"));
            _CleanerServiceItem.setImagePathLocal(WebParamUtil.getStringValue(request.getParameter("imagePathLocal")));

        }
        if (!isMissing(request.getParameter("basePrice"))) {
            m_logger.debug("updating param basePrice from " +_CleanerServiceItem.getBasePrice() + "->" + request.getParameter("basePrice"));
            _CleanerServiceItem.setBasePrice(WebParamUtil.getDoubleValue(request.getParameter("basePrice")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerServiceItem.getNote() + "->" + request.getParameter("note"));
            _CleanerServiceItem.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerServiceItem);
        m_ds.update(_CleanerServiceItem);
        m_actionExtent.afterUpdate(request, response, _CleanerServiceItem);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerServiceItem _CleanerServiceItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceItem _CleanerServiceItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serviceId"))) {
            m_logger.debug("updating param serviceId from " +_CleanerServiceItem.getServiceId() + "->" + request.getParameter("serviceId"));
            _CleanerServiceItem.setServiceId(WebParamUtil.getLongValue(request.getParameter("serviceId")));

        }
        if (!isMissing(request.getParameter("serviceItemId"))) {
            m_logger.debug("updating param serviceItemId from " +_CleanerServiceItem.getServiceItemId() + "->" + request.getParameter("serviceItemId"));
            _CleanerServiceItem.setServiceItemId(WebParamUtil.getLongValue(request.getParameter("serviceItemId")));

        }
        if (!isMissing(request.getParameter("itemType"))) {
            m_logger.debug("updating param itemType from " +_CleanerServiceItem.getItemType() + "->" + request.getParameter("itemType"));
            _CleanerServiceItem.setItemType(WebParamUtil.getIntegerValue(request.getParameter("itemType")));

        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_CleanerServiceItem.getTitle() + "->" + request.getParameter("title"));
            _CleanerServiceItem.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("imagePath"))) {
            m_logger.debug("updating param imagePath from " +_CleanerServiceItem.getImagePath() + "->" + request.getParameter("imagePath"));
            _CleanerServiceItem.setImagePath(WebParamUtil.getStringValue(request.getParameter("imagePath")));

        }
        if (!isMissing(request.getParameter("imagePathLocal"))) {
            m_logger.debug("updating param imagePathLocal from " +_CleanerServiceItem.getImagePathLocal() + "->" + request.getParameter("imagePathLocal"));
            _CleanerServiceItem.setImagePathLocal(WebParamUtil.getStringValue(request.getParameter("imagePathLocal")));

        }
        if (!isMissing(request.getParameter("basePrice"))) {
            m_logger.debug("updating param basePrice from " +_CleanerServiceItem.getBasePrice() + "->" + request.getParameter("basePrice"));
            _CleanerServiceItem.setBasePrice(WebParamUtil.getDoubleValue(request.getParameter("basePrice")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerServiceItem.getNote() + "->" + request.getParameter("note"));
            _CleanerServiceItem.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerServiceItem _CleanerServiceItem) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceItem _CleanerServiceItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("serviceId"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getServiceId());
        }
        if (!isMissing(request.getParameter("serviceItemId"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getServiceItemId());
        }
        if (!isMissing(request.getParameter("itemType"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getItemType());
        }
        if (!isMissing(request.getParameter("title"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getTitle());
        }
        if (!isMissing(request.getParameter("imagePath"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getImagePath());
        }
        if (!isMissing(request.getParameter("imagePathLocal"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getImagePathLocal());
        }
        if (!isMissing(request.getParameter("basePrice"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getBasePrice());
        }
        if (!isMissing(request.getParameter("note"))) {
			return  JtStringUtil.valueOf(_CleanerServiceItem.getNote());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerServiceItem _CleanerServiceItem) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerServiceItem _CleanerServiceItem) {
        if (fieldName == null || fieldName.equals("")|| _CleanerServiceItem == null) return null;
        
        if (fieldName.equals("serviceId")) {
            return WebUtil.display(_CleanerServiceItem.getServiceId());
        }
        if (fieldName.equals("serviceItemId")) {
            return WebUtil.display(_CleanerServiceItem.getServiceItemId());
        }
        if (fieldName.equals("itemType")) {
            return WebUtil.display(_CleanerServiceItem.getItemType());
        }
        if (fieldName.equals("title")) {
            return WebUtil.display(_CleanerServiceItem.getTitle());
        }
        if (fieldName.equals("imagePath")) {
            return WebUtil.display(_CleanerServiceItem.getImagePath());
        }
        if (fieldName.equals("imagePathLocal")) {
            return WebUtil.display(_CleanerServiceItem.getImagePathLocal());
        }
        if (fieldName.equals("basePrice")) {
            return WebUtil.display(_CleanerServiceItem.getBasePrice());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_CleanerServiceItem.getNote());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerServiceItemForm _CleanerServiceItemForm = (CleanerServiceItemForm) form;

		if(requestParams.containsKey("serviceId"))
			_CleanerServiceItemForm.setServiceId((String)requestParams.get("serviceId"));
		if(requestParams.containsKey("serviceItemId"))
			_CleanerServiceItemForm.setServiceItemId((String)requestParams.get("serviceItemId"));
		if(requestParams.containsKey("itemType"))
			_CleanerServiceItemForm.setItemType((String)requestParams.get("itemType"));
		if(requestParams.containsKey("title"))
			_CleanerServiceItemForm.setTitle((String)requestParams.get("title"));
		if(requestParams.containsKey("imagePath"))
			_CleanerServiceItemForm.setImagePath((String)requestParams.get("imagePath"));
		if(requestParams.containsKey("imagePathLocal"))
			_CleanerServiceItemForm.setImagePathLocal((String)requestParams.get("imagePathLocal"));
		if(requestParams.containsKey("basePrice"))
			_CleanerServiceItemForm.setBasePrice((String)requestParams.get("basePrice"));
		if(requestParams.containsKey("note"))
			_CleanerServiceItemForm.setNote((String)requestParams.get("note"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_service_item_home=NULL,/jsp/form_cleaner/cleanerServiceItem_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_service_item_list=NULL,/jsp/form_cleaner/cleanerServiceItem_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_service_item_form=NULL,/jsp/form_cleaner/cleanerServiceItem_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_service_item_ajax=NULL,/jsp/form_cleaner/cleanerServiceItem_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_item_home=NULL,/jsp/form_cleaner/cleanerServiceItem_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_item_list=NULL,/jsp/form_cleaner/cleanerServiceItem_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_item_form=NULL,/jsp/form_cleaner/cleanerServiceItem_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_item_ajax=NULL,/jsp/form_cleaner/cleanerServiceItem_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerServiceItemDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
