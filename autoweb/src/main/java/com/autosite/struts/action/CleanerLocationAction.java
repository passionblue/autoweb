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

import com.autosite.db.CleanerLocation;
import com.autosite.ds.CleanerLocationDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerLocationForm;
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




public class CleanerLocationAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerLocationAction.class);

    public CleanerLocationAction(){
        m_ds = CleanerLocationDS.getInstance();
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

        CleanerLocationForm _CleanerLocationForm = (CleanerLocationForm) form;
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


        CleanerLocation _CleanerLocation = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerLocation = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerLocation _CleanerLocation = m_ds.getById(cid);

            if (_CleanerLocation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerLocation.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerLocation.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerLocation);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _CleanerLocationForm == null) {
    	            editField(request, response, _CleanerLocation);
				} else {
    	            edit(request, response, _CleanerLocationForm, _CleanerLocation);
				}
                if (returnObjects != null) returnObjects.put("target", _CleanerLocation);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                CleanerLocation o = m_ds.getById( _CleanerLocation.getId(), true);

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

            return mapping.findForward("default");
    
		// ================== EDIT FIELD =====================================================================================
        } else if (isActionCmdEditfield(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerLocation _CleanerLocation = m_ds.getById(cid);

            if (_CleanerLocation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerLocation.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerLocation.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerLocation);
                if (returnObjects != null) returnObjects.put("target", _CleanerLocation);
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
            return mapping.findForward("default");

		// ================== DEL =====================================================================================
        } else if (isActionCmdDelete(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerLocation _CleanerLocation = m_ds.getById(cid);

            if (_CleanerLocation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerLocation.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerLocation.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerLocation);
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

            m_ds.delete(_CleanerLocation); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerLocation);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerLocation);
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


            m_logger.info("Creating new CleanerLocation" );
            CleanerLocation _CleanerLocationNew = new CleanerLocation();   

            // Setting IDs for the object
            _CleanerLocationNew.setSiteId(site.getId());
			
            if ( _CleanerLocationForm == null) {
                setFields(request, response, _CleanerLocationNew);
            } else {

            _CleanerLocationNew.setAddress(WebParamUtil.getStringValue(_CleanerLocationForm.getAddress()));
            m_logger.debug("setting Address=" +_CleanerLocationForm.getAddress());


            _CleanerLocationNew.setCityZip(WebParamUtil.getStringValue(_CleanerLocationForm.getCityZip()));
            m_logger.debug("setting CityZip=" +_CleanerLocationForm.getCityZip());


            _CleanerLocationNew.setPhone(WebParamUtil.getStringValue(_CleanerLocationForm.getPhone()));
            m_logger.debug("setting Phone=" +_CleanerLocationForm.getPhone());


            _CleanerLocationNew.setManagerId(WebParamUtil.getLongValue(_CleanerLocationForm.getManagerId()));
            m_logger.debug("setting ManagerId=" +_CleanerLocationForm.getManagerId());


			}

            try {
                checkDepedenceIntegrity(_CleanerLocationNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerLocationNew);
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
            
            if (_CleanerLocationNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerLocationNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerLocation);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerLocationNew);
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
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerLocationForm _CleanerLocationForm, CleanerLocation _CleanerLocation) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocation _CleanerLocation = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerLocationDS.objectToString(_CleanerLocation));

        _CleanerLocation.setAddress(WebParamUtil.getStringValue(_CleanerLocationForm.getAddress()));


        _CleanerLocation.setCityZip(WebParamUtil.getStringValue(_CleanerLocationForm.getCityZip()));


        _CleanerLocation.setPhone(WebParamUtil.getStringValue(_CleanerLocationForm.getPhone()));


        _CleanerLocation.setManagerId(WebParamUtil.getLongValue(_CleanerLocationForm.getManagerId()));



        m_actionExtent.beforeUpdate(request, response, _CleanerLocation);
        m_ds.update(_CleanerLocation);
        m_actionExtent.afterUpdate(request, response, _CleanerLocation);
        m_logger.debug("After update " + CleanerLocationDS.objectToString(_CleanerLocation));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerLocation _CleanerLocation) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocation _CleanerLocation = m_ds.getById(cid);

        if (!isMissing(request.getParameter("address"))) {
            m_logger.debug("updating param address from " +_CleanerLocation.getAddress() + "->" + request.getParameter("address"));
            _CleanerLocation.setAddress(WebParamUtil.getStringValue(request.getParameter("address")));

        }
        if (!isMissing(request.getParameter("cityZip"))) {
            m_logger.debug("updating param cityZip from " +_CleanerLocation.getCityZip() + "->" + request.getParameter("cityZip"));
            _CleanerLocation.setCityZip(WebParamUtil.getStringValue(request.getParameter("cityZip")));

        }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_CleanerLocation.getPhone() + "->" + request.getParameter("phone"));
            _CleanerLocation.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));

        }
        if (!isMissing(request.getParameter("managerId"))) {
            m_logger.debug("updating param managerId from " +_CleanerLocation.getManagerId() + "->" + request.getParameter("managerId"));
            _CleanerLocation.setManagerId(WebParamUtil.getLongValue(request.getParameter("managerId")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerLocation);
        m_ds.update(_CleanerLocation);
        m_actionExtent.afterUpdate(request, response, _CleanerLocation);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, CleanerLocation _CleanerLocation) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocation _CleanerLocation = m_ds.getById(cid);

        if (!isMissing(request.getParameter("address"))) {
            m_logger.debug("updating param address from " +_CleanerLocation.getAddress() + "->" + request.getParameter("address"));
            _CleanerLocation.setAddress(WebParamUtil.getStringValue(request.getParameter("address")));

        }
        if (!isMissing(request.getParameter("cityZip"))) {
            m_logger.debug("updating param cityZip from " +_CleanerLocation.getCityZip() + "->" + request.getParameter("cityZip"));
            _CleanerLocation.setCityZip(WebParamUtil.getStringValue(request.getParameter("cityZip")));

        }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_CleanerLocation.getPhone() + "->" + request.getParameter("phone"));
            _CleanerLocation.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));

        }
        if (!isMissing(request.getParameter("managerId"))) {
            m_logger.debug("updating param managerId from " +_CleanerLocation.getManagerId() + "->" + request.getParameter("managerId"));
            _CleanerLocation.setManagerId(WebParamUtil.getLongValue(request.getParameter("managerId")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerLocation _CleanerLocation) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerLocation _CleanerLocation = m_ds.getById(cid);

        if (!isMissing(request.getParameter("address"))) {
			return  JtStringUtil.valueOf(_CleanerLocation.getAddress());
        }
        if (!isMissing(request.getParameter("cityZip"))) {
			return  JtStringUtil.valueOf(_CleanerLocation.getCityZip());
        }
        if (!isMissing(request.getParameter("phone"))) {
			return  JtStringUtil.valueOf(_CleanerLocation.getPhone());
        }
        if (!isMissing(request.getParameter("managerId"))) {
			return  JtStringUtil.valueOf(_CleanerLocation.getManagerId());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerLocation _CleanerLocation) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerLocation _CleanerLocation) {
        if (fieldName == null || fieldName.equals("")|| _CleanerLocation == null) return null;
        
        if (fieldName.equals("address")) {
            return WebUtil.display(_CleanerLocation.getAddress());
        }
        if (fieldName.equals("cityZip")) {
            return WebUtil.display(_CleanerLocation.getCityZip());
        }
        if (fieldName.equals("phone")) {
            return WebUtil.display(_CleanerLocation.getPhone());
        }
        if (fieldName.equals("managerId")) {
            return WebUtil.display(_CleanerLocation.getManagerId());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerLocationForm _CleanerLocationForm = (CleanerLocationForm) form;

		if(requestParams.containsKey("address"))
			_CleanerLocationForm.setAddress((String)requestParams.get("address"));
		if(requestParams.containsKey("cityZip"))
			_CleanerLocationForm.setCityZip((String)requestParams.get("cityZip"));
		if(requestParams.containsKey("phone"))
			_CleanerLocationForm.setPhone((String)requestParams.get("phone"));
		if(requestParams.containsKey("managerId"))
			_CleanerLocationForm.setManagerId((String)requestParams.get("managerId"));
    }


    protected boolean loginRequired() {
        return true;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "cleaner_location_home=NULL,/jsp/form_cleaner/cleanerLocation_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_location_list=NULL,/jsp/form_cleaner/cleanerLocation_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_location_form=NULL,/jsp/form_cleaner/cleanerLocation_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "cleaner_location_ajax=NULL,/jsp/form_cleaner/cleanerLocation_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_home=NULL,/jsp/form_cleaner/cleanerLocation_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_list=NULL,/jsp/form_cleaner/cleanerLocation_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_form=NULL,/jsp/form_cleaner/cleanerLocation_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "cleaner_location_ajax=NULL,/jsp/form_cleaner/cleanerLocation_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected CleanerLocationDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
