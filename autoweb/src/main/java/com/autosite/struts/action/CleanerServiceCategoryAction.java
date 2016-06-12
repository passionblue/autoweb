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

import com.autosite.db.CleanerServiceCategory;
import com.autosite.ds.CleanerServiceCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerServiceCategoryForm;
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




public class CleanerServiceCategoryAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerServiceCategoryAction.class);

    public CleanerServiceCategoryAction(){
        m_ds = CleanerServiceCategoryDS.getInstance();
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

        CleanerServiceCategoryForm _CleanerServiceCategoryForm = (CleanerServiceCategoryForm) form;
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


        CleanerServiceCategory _CleanerServiceCategory = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerServiceCategory = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerServiceCategory _CleanerServiceCategory = m_ds.getById(cid);

            if (_CleanerServiceCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerServiceCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceCategory.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerServiceCategory);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerServiceCategoryForm == null) {
    	            editField(request, response, _CleanerServiceCategory);
				} else {
    	            edit(request, response, _CleanerServiceCategoryForm, _CleanerServiceCategory);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerServiceCategory);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerServiceCategory o = m_ds.getById( _CleanerServiceCategory.getId(), true);

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
            //CleanerServiceCategory _CleanerServiceCategory = m_ds.getById(cid);

            if (_CleanerServiceCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerServiceCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceCategory.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerServiceCategory);
                if (returnObjects != null) returnObjects.put("target", _CleanerServiceCategory);
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
            //CleanerServiceCategory _CleanerServiceCategory = m_ds.getById(cid);

            if (_CleanerServiceCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerServiceCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerServiceCategory.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerServiceCategory);
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

            m_ds.delete(_CleanerServiceCategory); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerServiceCategory);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerServiceCategory);
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


            m_logger.info("Creating new CleanerServiceCategory" );
            CleanerServiceCategory _CleanerServiceCategoryNew = new CleanerServiceCategory();   

            // Setting IDs for the object
            _CleanerServiceCategoryNew.setSiteId(site.getId());
			
            if ( _CleanerServiceCategoryForm == null) {
                setFields(request, response, _CleanerServiceCategoryNew);
            } else {

            _CleanerServiceCategoryNew.setTitle(WebParamUtil.getStringValue(_CleanerServiceCategoryForm.getTitle()));
            m_logger.debug("setting Title=" +_CleanerServiceCategoryForm.getTitle());


            _CleanerServiceCategoryNew.setImagePath(WebParamUtil.getStringValue(_CleanerServiceCategoryForm.getImagePath()));
            m_logger.debug("setting ImagePath=" +_CleanerServiceCategoryForm.getImagePath());


            _CleanerServiceCategoryNew.setImagePathLocal(WebParamUtil.getStringValue(_CleanerServiceCategoryForm.getImagePathLocal()));
            m_logger.debug("setting ImagePathLocal=" +_CleanerServiceCategoryForm.getImagePathLocal());


			}

            try {
                checkDepedenceIntegrity(_CleanerServiceCategoryNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerServiceCategoryNew);
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
            
            if (_CleanerServiceCategoryNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerServiceCategoryNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerServiceCategoryNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerServiceCategoryNew);
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
             _CleanerServiceCategory =  _CleanerServiceCategoryNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _CleanerServiceCategory, "cleaner-service-category" );
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerServiceCategoryForm _CleanerServiceCategoryForm, CleanerServiceCategory _CleanerServiceCategory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceCategory _CleanerServiceCategory = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerServiceCategoryDS.objectToString(_CleanerServiceCategory));

        _CleanerServiceCategory.setTitle(WebParamUtil.getStringValue(_CleanerServiceCategoryForm.getTitle()));


        _CleanerServiceCategory.setImagePath(WebParamUtil.getStringValue(_CleanerServiceCategoryForm.getImagePath()));


        _CleanerServiceCategory.setImagePathLocal(WebParamUtil.getStringValue(_CleanerServiceCategoryForm.getImagePathLocal()));



        m_actionExtent.beforeUpdate(request, response, _CleanerServiceCategory);
        m_ds.update(_CleanerServiceCategory);
        m_actionExtent.afterUpdate(request, response, _CleanerServiceCategory);
        m_logger.debug("After update " + CleanerServiceCategoryDS.objectToString(_CleanerServiceCategory));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerServiceCategory _CleanerServiceCategory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceCategory _CleanerServiceCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_CleanerServiceCategory.getTitle() + "->" + request.getParameter("title"));
            _CleanerServiceCategory.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("imagePath"))) {
            m_logger.debug("updating param imagePath from " +_CleanerServiceCategory.getImagePath() + "->" + request.getParameter("imagePath"));
            _CleanerServiceCategory.setImagePath(WebParamUtil.getStringValue(request.getParameter("imagePath")));

        }
        if (!isMissing(request.getParameter("imagePathLocal"))) {
            m_logger.debug("updating param imagePathLocal from " +_CleanerServiceCategory.getImagePathLocal() + "->" + request.getParameter("imagePathLocal"));
            _CleanerServiceCategory.setImagePathLocal(WebParamUtil.getStringValue(request.getParameter("imagePathLocal")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerServiceCategory);
        m_ds.update(_CleanerServiceCategory);
        m_actionExtent.afterUpdate(request, response, _CleanerServiceCategory);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerServiceCategory _CleanerServiceCategory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceCategory _CleanerServiceCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_CleanerServiceCategory.getTitle() + "->" + request.getParameter("title"));
            _CleanerServiceCategory.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));

        }
        if (!isMissing(request.getParameter("imagePath"))) {
            m_logger.debug("updating param imagePath from " +_CleanerServiceCategory.getImagePath() + "->" + request.getParameter("imagePath"));
            _CleanerServiceCategory.setImagePath(WebParamUtil.getStringValue(request.getParameter("imagePath")));

        }
        if (!isMissing(request.getParameter("imagePathLocal"))) {
            m_logger.debug("updating param imagePathLocal from " +_CleanerServiceCategory.getImagePathLocal() + "->" + request.getParameter("imagePathLocal"));
            _CleanerServiceCategory.setImagePathLocal(WebParamUtil.getStringValue(request.getParameter("imagePathLocal")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerServiceCategory _CleanerServiceCategory) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerServiceCategory _CleanerServiceCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
			return  JtStringUtil.valueOf(_CleanerServiceCategory.getTitle());
        }
        if (!isMissing(request.getParameter("imagePath"))) {
			return  JtStringUtil.valueOf(_CleanerServiceCategory.getImagePath());
        }
        if (!isMissing(request.getParameter("imagePathLocal"))) {
			return  JtStringUtil.valueOf(_CleanerServiceCategory.getImagePathLocal());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerServiceCategory _CleanerServiceCategory) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerServiceCategory _CleanerServiceCategory) {
        if (fieldName == null || fieldName.equals("")|| _CleanerServiceCategory == null) return null;
        
        if (fieldName.equals("title")) {
            return WebUtil.display(_CleanerServiceCategory.getTitle());
        }
        if (fieldName.equals("imagePath")) {
            return WebUtil.display(_CleanerServiceCategory.getImagePath());
        }
        if (fieldName.equals("imagePathLocal")) {
            return WebUtil.display(_CleanerServiceCategory.getImagePathLocal());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerServiceCategoryForm _CleanerServiceCategoryForm = (CleanerServiceCategoryForm) form;

		if(requestParams.containsKey("title"))
			_CleanerServiceCategoryForm.setTitle((String)requestParams.get("title"));
		if(requestParams.containsKey("imagePath"))
			_CleanerServiceCategoryForm.setImagePath((String)requestParams.get("imagePath"));
		if(requestParams.containsKey("imagePathLocal"))
			_CleanerServiceCategoryForm.setImagePathLocal((String)requestParams.get("imagePathLocal"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_service_category_home=NULL,/jsp/form_cleaner/cleanerServiceCategory_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_service_category_list=NULL,/jsp/form_cleaner/cleanerServiceCategory_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_service_category_form=NULL,/jsp/form_cleaner/cleanerServiceCategory_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_service_category_ajax=NULL,/jsp/form_cleaner/cleanerServiceCategory_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_category_home=NULL,/jsp/form_cleaner/cleanerServiceCategory_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_category_list=NULL,/jsp/form_cleaner/cleanerServiceCategory_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_category_form=NULL,/jsp/form_cleaner/cleanerServiceCategory_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_service_category_ajax=NULL,/jsp/form_cleaner/cleanerServiceCategory_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerServiceCategoryDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
