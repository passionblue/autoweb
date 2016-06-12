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

import com.autosite.db.CleanerProduct;
import com.autosite.ds.CleanerProductDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerProductForm;
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




public class CleanerProductAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerProductAction.class);

    public CleanerProductAction(){
        m_ds = CleanerProductDS.getInstance();
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

        CleanerProductForm _CleanerProductForm = (CleanerProductForm) form;
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


        CleanerProduct _CleanerProduct = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerProduct = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerProduct _CleanerProduct = m_ds.getById(cid);

            if (_CleanerProduct == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerProduct.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerProduct.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerProduct);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerProductForm == null) {
    	            editField(request, response, _CleanerProduct);
				} else {
    	            edit(request, response, _CleanerProductForm, _CleanerProduct);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerProduct);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerProduct o = m_ds.getById( _CleanerProduct.getId(), true);

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
            //CleanerProduct _CleanerProduct = m_ds.getById(cid);

            if (_CleanerProduct == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerProduct.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerProduct.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerProduct);
                if (returnObjects != null) returnObjects.put("target", _CleanerProduct);
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
            //CleanerProduct _CleanerProduct = m_ds.getById(cid);

            if (_CleanerProduct == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerProduct.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerProduct.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerProduct);
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

            m_ds.delete(_CleanerProduct); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerProduct);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerProduct);
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


            m_logger.info("Creating new CleanerProduct" );
            CleanerProduct _CleanerProductNew = new CleanerProduct();   

            // Setting IDs for the object
            _CleanerProductNew.setSiteId(site.getId());
			
            if ( _CleanerProductForm == null) {
                setFields(request, response, _CleanerProductNew);
            } else {

            _CleanerProductNew.setGarmentTypeId(WebParamUtil.getLongValue(_CleanerProductForm.getGarmentTypeId()));
            m_logger.debug("setting GarmentTypeId=" +_CleanerProductForm.getGarmentTypeId());


            _CleanerProductNew.setGarmentServiceId(WebParamUtil.getLongValue(_CleanerProductForm.getGarmentServiceId()));
            m_logger.debug("setting GarmentServiceId=" +_CleanerProductForm.getGarmentServiceId());


            _CleanerProductNew.setRegularPrice(WebParamUtil.getDoubleValue(_CleanerProductForm.getRegularPrice()));
            m_logger.debug("setting RegularPrice=" +_CleanerProductForm.getRegularPrice());


            _CleanerProductNew.setNote(WebParamUtil.getStringValue(_CleanerProductForm.getNote()));
            m_logger.debug("setting Note=" +_CleanerProductForm.getNote());


			}

            try {
                checkDepedenceIntegrity(_CleanerProductNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerProductNew);
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
            
            if (_CleanerProductNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerProductNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerProductNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerProductNew);
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
             _CleanerProduct =  _CleanerProductNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerProduct, "cleaner-product" );
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerProductForm _CleanerProductForm, CleanerProduct _CleanerProduct) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerProduct _CleanerProduct = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerProductDS.objectToString(_CleanerProduct));

        _CleanerProduct.setGarmentTypeId(WebParamUtil.getLongValue(_CleanerProductForm.getGarmentTypeId()));


        _CleanerProduct.setGarmentServiceId(WebParamUtil.getLongValue(_CleanerProductForm.getGarmentServiceId()));


        _CleanerProduct.setRegularPrice(WebParamUtil.getDoubleValue(_CleanerProductForm.getRegularPrice()));


        _CleanerProduct.setNote(WebParamUtil.getStringValue(_CleanerProductForm.getNote()));



        m_actionExtent.beforeUpdate(request, response, _CleanerProduct);
        m_ds.update(_CleanerProduct);
        m_actionExtent.afterUpdate(request, response, _CleanerProduct);
        m_logger.debug("After update " + CleanerProductDS.objectToString(_CleanerProduct));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerProduct _CleanerProduct) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerProduct _CleanerProduct = m_ds.getById(cid);

        if (!isMissing(request.getParameter("garmentTypeId"))) {
            m_logger.debug("updating param garmentTypeId from " +_CleanerProduct.getGarmentTypeId() + "->" + request.getParameter("garmentTypeId"));
            _CleanerProduct.setGarmentTypeId(WebParamUtil.getLongValue(request.getParameter("garmentTypeId")));

        }
        if (!isMissing(request.getParameter("garmentServiceId"))) {
            m_logger.debug("updating param garmentServiceId from " +_CleanerProduct.getGarmentServiceId() + "->" + request.getParameter("garmentServiceId"));
            _CleanerProduct.setGarmentServiceId(WebParamUtil.getLongValue(request.getParameter("garmentServiceId")));

        }
        if (!isMissing(request.getParameter("regularPrice"))) {
            m_logger.debug("updating param regularPrice from " +_CleanerProduct.getRegularPrice() + "->" + request.getParameter("regularPrice"));
            _CleanerProduct.setRegularPrice(WebParamUtil.getDoubleValue(request.getParameter("regularPrice")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerProduct.getNote() + "->" + request.getParameter("note"));
            _CleanerProduct.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerProduct);
        m_ds.update(_CleanerProduct);
        m_actionExtent.afterUpdate(request, response, _CleanerProduct);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerProduct _CleanerProduct) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerProduct _CleanerProduct = m_ds.getById(cid);

        if (!isMissing(request.getParameter("garmentTypeId"))) {
            m_logger.debug("updating param garmentTypeId from " +_CleanerProduct.getGarmentTypeId() + "->" + request.getParameter("garmentTypeId"));
            _CleanerProduct.setGarmentTypeId(WebParamUtil.getLongValue(request.getParameter("garmentTypeId")));

        }
        if (!isMissing(request.getParameter("garmentServiceId"))) {
            m_logger.debug("updating param garmentServiceId from " +_CleanerProduct.getGarmentServiceId() + "->" + request.getParameter("garmentServiceId"));
            _CleanerProduct.setGarmentServiceId(WebParamUtil.getLongValue(request.getParameter("garmentServiceId")));

        }
        if (!isMissing(request.getParameter("regularPrice"))) {
            m_logger.debug("updating param regularPrice from " +_CleanerProduct.getRegularPrice() + "->" + request.getParameter("regularPrice"));
            _CleanerProduct.setRegularPrice(WebParamUtil.getDoubleValue(request.getParameter("regularPrice")));

        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_CleanerProduct.getNote() + "->" + request.getParameter("note"));
            _CleanerProduct.setNote(WebParamUtil.getStringValue(request.getParameter("note")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerProduct _CleanerProduct) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerProduct _CleanerProduct = m_ds.getById(cid);

        if (!isMissing(request.getParameter("garmentTypeId"))) {
			return  JtStringUtil.valueOf(_CleanerProduct.getGarmentTypeId());
        }
        if (!isMissing(request.getParameter("garmentServiceId"))) {
			return  JtStringUtil.valueOf(_CleanerProduct.getGarmentServiceId());
        }
        if (!isMissing(request.getParameter("regularPrice"))) {
			return  JtStringUtil.valueOf(_CleanerProduct.getRegularPrice());
        }
        if (!isMissing(request.getParameter("note"))) {
			return  JtStringUtil.valueOf(_CleanerProduct.getNote());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerProduct _CleanerProduct) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerProduct _CleanerProduct) {
        if (fieldName == null || fieldName.equals("")|| _CleanerProduct == null) return null;
        
        if (fieldName.equals("garmentTypeId")) {
            return WebUtil.display(_CleanerProduct.getGarmentTypeId());
        }
        if (fieldName.equals("garmentServiceId")) {
            return WebUtil.display(_CleanerProduct.getGarmentServiceId());
        }
        if (fieldName.equals("regularPrice")) {
            return WebUtil.display(_CleanerProduct.getRegularPrice());
        }
        if (fieldName.equals("note")) {
            return WebUtil.display(_CleanerProduct.getNote());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerProductForm _CleanerProductForm = (CleanerProductForm) form;

		if(requestParams.containsKey("garmentTypeId"))
			_CleanerProductForm.setGarmentTypeId((String)requestParams.get("garmentTypeId"));
		if(requestParams.containsKey("garmentServiceId"))
			_CleanerProductForm.setGarmentServiceId((String)requestParams.get("garmentServiceId"));
		if(requestParams.containsKey("regularPrice"))
			_CleanerProductForm.setRegularPrice((String)requestParams.get("regularPrice"));
		if(requestParams.containsKey("note"))
			_CleanerProductForm.setNote((String)requestParams.get("note"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_product_home=NULL,/jsp/form_cleaner/cleanerProduct_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_product_list=NULL,/jsp/form_cleaner/cleanerProduct_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_product_form=NULL,/jsp/form_cleaner/cleanerProduct_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_product_ajax=NULL,/jsp/form_cleaner/cleanerProduct_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_product_home=NULL,/jsp/form_cleaner/cleanerProduct_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_product_list=NULL,/jsp/form_cleaner/cleanerProduct_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_product_form=NULL,/jsp/form_cleaner/cleanerProduct_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_product_ajax=NULL,/jsp/form_cleaner/cleanerProduct_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerProductDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
