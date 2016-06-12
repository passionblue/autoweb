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

import com.autosite.db.ThemeAggregator;
import com.autosite.ds.ThemeAggregatorDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ThemeAggregatorForm;
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

import com.autosite.ds.ThemeAggregatorDS;
import com.autosite.db.ThemeAggregator;



public class ThemeAggregatorAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(ThemeAggregatorAction.class);

    public ThemeAggregatorAction(){
        m_ds = ThemeAggregatorDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        ThemeAggregatorForm _ThemeAggregatorForm = (ThemeAggregatorForm) form;
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


        ThemeAggregator _ThemeAggregator = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ThemeAggregator = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ThemeAggregator _ThemeAggregator = m_ds.getById(cid);

            if (_ThemeAggregator == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_ThemeAggregator.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ThemeAggregator.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_ThemeAggregator);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _ThemeAggregatorForm, _ThemeAggregator);
                if (returnObjects != null) returnObjects.put("target", _ThemeAggregator);
            }

            catch (Exception e) {
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
            //ThemeAggregator _ThemeAggregator = m_ds.getById(cid);

            if (_ThemeAggregator == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ThemeAggregator.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ThemeAggregator.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _ThemeAggregator);
                if (returnObjects != null) returnObjects.put("target", _ThemeAggregator);
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
            //ThemeAggregator _ThemeAggregator = m_ds.getById(cid);

            if (_ThemeAggregator == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ThemeAggregator.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ThemeAggregator.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _ThemeAggregator);
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

            m_ds.delete(_ThemeAggregator); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _ThemeAggregator);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _ThemeAggregator);
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


            m_logger.info("Creating new ThemeAggregator" );
            ThemeAggregator _ThemeAggregatorNew = new ThemeAggregator();   

            // Setting IDs for the object
            _ThemeAggregatorNew.setSiteId(site.getId());

            _ThemeAggregatorNew.setThemeName(WebParamUtil.getStringValue(_ThemeAggregatorForm.getThemeName()));
            m_logger.debug("setting ThemeName=" +_ThemeAggregatorForm.getThemeName());
            _ThemeAggregatorNew.setLayoutPage(WebParamUtil.getStringValue(_ThemeAggregatorForm.getLayoutPage()));
            m_logger.debug("setting LayoutPage=" +_ThemeAggregatorForm.getLayoutPage());
            _ThemeAggregatorNew.setCssIndex(WebParamUtil.getStringValue(_ThemeAggregatorForm.getCssIndex()));
            m_logger.debug("setting CssIndex=" +_ThemeAggregatorForm.getCssIndex());
            _ThemeAggregatorNew.setThemeStyleId(WebParamUtil.getLongValue(_ThemeAggregatorForm.getThemeStyleId()));
            m_logger.debug("setting ThemeStyleId=" +_ThemeAggregatorForm.getThemeStyleId());
            _ThemeAggregatorNew.setTimeCreated(WebParamUtil.getTimestampValue(_ThemeAggregatorForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ThemeAggregatorForm.getTimeCreated());
            _ThemeAggregatorNew.setTimeUpdated(WebParamUtil.getTimestampValue(_ThemeAggregatorForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ThemeAggregatorForm.getTimeUpdated());


            try {
                checkDepedenceIntegrity(_ThemeAggregatorNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ThemeAggregatorNew);
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
            
            if (_ThemeAggregatorNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ThemeAggregatorNew);
            if (returnObjects != null) returnObjects.put("target", _ThemeAggregator);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ThemeAggregatorNew);
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, ThemeAggregatorForm _ThemeAggregatorForm, ThemeAggregator _ThemeAggregator) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ThemeAggregator _ThemeAggregator = m_ds.getById(cid);

        m_logger.debug("Before update " + ThemeAggregatorDS.objectToString(_ThemeAggregator));

        _ThemeAggregator.setThemeName(WebParamUtil.getStringValue(_ThemeAggregatorForm.getThemeName()));
        _ThemeAggregator.setLayoutPage(WebParamUtil.getStringValue(_ThemeAggregatorForm.getLayoutPage()));
        _ThemeAggregator.setCssIndex(WebParamUtil.getStringValue(_ThemeAggregatorForm.getCssIndex()));
        _ThemeAggregator.setThemeStyleId(WebParamUtil.getLongValue(_ThemeAggregatorForm.getThemeStyleId()));
        _ThemeAggregator.setTimeUpdated(WebParamUtil.getTimestampValue(_ThemeAggregatorForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _ThemeAggregator);
        m_ds.update(_ThemeAggregator);
        m_actionExtent.afterUpdate(request, response, _ThemeAggregator);
        m_logger.debug("After update " + ThemeAggregatorDS.objectToString(_ThemeAggregator));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ThemeAggregator _ThemeAggregator) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ThemeAggregator _ThemeAggregator = m_ds.getById(cid);

        if (!isMissing(request.getParameter("themeName"))) {
            m_logger.debug("updating param themeName from " +_ThemeAggregator.getThemeName() + "->" + request.getParameter("themeName"));
            _ThemeAggregator.setThemeName(WebParamUtil.getStringValue(request.getParameter("themeName")));

        }
        if (!isMissing(request.getParameter("layoutPage"))) {
            m_logger.debug("updating param layoutPage from " +_ThemeAggregator.getLayoutPage() + "->" + request.getParameter("layoutPage"));
            _ThemeAggregator.setLayoutPage(WebParamUtil.getStringValue(request.getParameter("layoutPage")));

        }
        if (!isMissing(request.getParameter("cssIndex"))) {
            m_logger.debug("updating param cssIndex from " +_ThemeAggregator.getCssIndex() + "->" + request.getParameter("cssIndex"));
            _ThemeAggregator.setCssIndex(WebParamUtil.getStringValue(request.getParameter("cssIndex")));

        }
        if (!isMissing(request.getParameter("themeStyleId"))) {
            m_logger.debug("updating param themeStyleId from " +_ThemeAggregator.getThemeStyleId() + "->" + request.getParameter("themeStyleId"));
            _ThemeAggregator.setThemeStyleId(WebParamUtil.getLongValue(request.getParameter("themeStyleId")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ThemeAggregator.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ThemeAggregator.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_ThemeAggregator.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _ThemeAggregator.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        }

        m_actionExtent.beforeUpdate(request, response, _ThemeAggregator);
        m_ds.update(_ThemeAggregator);
        m_actionExtent.afterUpdate(request, response, _ThemeAggregator);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ThemeAggregator _ThemeAggregator) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ThemeAggregator _ThemeAggregator = m_ds.getById(cid);

        if (!isMissing(request.getParameter("themeName"))) {
			return  JtStringUtil.valueOf(_ThemeAggregator.getThemeName());
        }
        if (!isMissing(request.getParameter("layoutPage"))) {
			return  JtStringUtil.valueOf(_ThemeAggregator.getLayoutPage());
        }
        if (!isMissing(request.getParameter("cssIndex"))) {
			return  JtStringUtil.valueOf(_ThemeAggregator.getCssIndex());
        }
        if (!isMissing(request.getParameter("themeStyleId"))) {
			return  JtStringUtil.valueOf(_ThemeAggregator.getThemeStyleId());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_ThemeAggregator.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_ThemeAggregator.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(ThemeAggregator _ThemeAggregator) throws Exception {
    }

    protected String getFieldByName(String fieldName, ThemeAggregator _ThemeAggregator) {
        if (fieldName == null || fieldName.equals("")|| _ThemeAggregator == null) return null;
        
        if (fieldName.equals("themeName")) {
            return WebUtil.display(_ThemeAggregator.getThemeName());
        }
        if (fieldName.equals("layoutPage")) {
            return WebUtil.display(_ThemeAggregator.getLayoutPage());
        }
        if (fieldName.equals("cssIndex")) {
            return WebUtil.display(_ThemeAggregator.getCssIndex());
        }
        if (fieldName.equals("themeStyleId")) {
            return WebUtil.display(_ThemeAggregator.getThemeStyleId());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_ThemeAggregator.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_ThemeAggregator.getTimeUpdated());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        ThemeAggregator target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (ThemeAggregator) working.get("target");
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        // ================================================================================
        // Response Processing 
        // ================================================================================

        if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // If there is no error, following message will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") )
                ret.put("__value", "Successfully deleted.");
            if (hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") )
                ret.put("__value", "Successfully created.");
            if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef"))     
                ret.put("__value", "Successfully changed.");
            if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") )
                ret.put("__value", "Successfully changed.");
            else 
                ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getcode")){
            // If there is no error, nothing will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getstatus arg = " + request.getParameter("ajaxOutArg"));

        } else if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ThemeAggregator _ThemeAggregator = ThemeAggregatorDS.getInstance().getById(id);
            if (_ThemeAggregator == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ThemeAggregator);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ThemeAggregator _ThemeAggregator = ThemeAggregatorDS.getInstance().getById(id);
            if ( _ThemeAggregator == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _ThemeAggregator);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();

            buf.append("<div id=\"themeAggregator-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ThemeAggregator _ThemeAggregator = (ThemeAggregator) iterator.next();

                buf.append("<div id=\"themeAggregator-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("themeName")) {
                    buf.append("<div id=\"themeAggregator-ajax-themeName\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeAggregator.getThemeName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("layoutPage")) {
                    buf.append("<div id=\"themeAggregator-ajax-layoutPage\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeAggregator.getLayoutPage()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("cssIndex")) {
                    buf.append("<div id=\"themeAggregator-ajax-cssIndex\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeAggregator.getCssIndex()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("themeStyleId")) {
                    buf.append("<div id=\"themeAggregator-ajax-themeStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeAggregator.getThemeStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"themeAggregator-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeAggregator.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"themeAggregator-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeAggregator.getTimeUpdated()));
                    buf.append("</div>");

				}
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("themeName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Theme Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("layoutPage")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Layout Page");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("cssIndex")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Css Index");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("themeStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Theme Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Updated");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ThemeAggregator _ThemeAggregator = (ThemeAggregator) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("themeName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeAggregator.getThemeName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("layoutPage")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeAggregator.getLayoutPage()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("cssIndex")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeAggregator.getCssIndex()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("themeStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeAggregator.getThemeStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeAggregator.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeAggregator.getTimeUpdated()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                ThemeAggregator _ThemeAggregator = (ThemeAggregator) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("themeName")) 
			            json.put("themeName", ""+_ThemeAggregator.getThemeName());
		            if ( ignoreFieldSet || fieldSet.contains("layoutPage")) 
			            json.put("layoutPage", ""+_ThemeAggregator.getLayoutPage());
		            if ( ignoreFieldSet || fieldSet.contains("cssIndex")) 
			            json.put("cssIndex", ""+_ThemeAggregator.getCssIndex());
		            if ( ignoreFieldSet || fieldSet.contains("themeStyleId")) 
			            json.put("themeStyleId", ""+_ThemeAggregator.getThemeStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
			            json.put("timeCreated", ""+_ThemeAggregator.getTimeCreated());
		            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
			            json.put("timeUpdated", ""+_ThemeAggregator.getTimeUpdated());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                ThemeAggregator _ThemeAggregator = list.size() >=1?(ThemeAggregator) list.get(0): null; 

				if ( _ThemeAggregator != null) {
		            top.put("id", ""+_ThemeAggregator.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonThemeName = new JSONObject();
		            jsonThemeName.put("name", "themeName");
		            jsonThemeName.put("value", ""+_ThemeAggregator.getThemeName());
		            array.put(jsonThemeName);
		            JSONObject jsonLayoutPage = new JSONObject();
		            jsonLayoutPage.put("name", "layoutPage");
		            jsonLayoutPage.put("value", ""+_ThemeAggregator.getLayoutPage());
		            array.put(jsonLayoutPage);
		            JSONObject jsonCssIndex = new JSONObject();
		            jsonCssIndex.put("name", "cssIndex");
		            jsonCssIndex.put("value", ""+_ThemeAggregator.getCssIndex());
		            array.put(jsonCssIndex);
		            JSONObject jsonThemeStyleId = new JSONObject();
		            jsonThemeStyleId.put("name", "themeStyleId");
		            jsonThemeStyleId.put("value", ""+_ThemeAggregator.getThemeStyleId());
		            array.put(jsonThemeStyleId);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_ThemeAggregator.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_ThemeAggregator.getTimeUpdated());
		            array.put(jsonTimeUpdated);

	    	        top.put("fields", array);
				}
			}

            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));

			// Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuilder buf = new StringBuilder();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);


            buf.append("<script type=\"text/javascript\">");
            //buf.append("<!--");
            buf.append("function sendForm_"+ randNum + "(){");
            buf.append("sendFormAjax('/themeAggregatorAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/themeAggregatorAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("themeName")) {
                String value = WebUtil.display(request.getParameter("themeName"));

                if ( forceHiddenSet.contains("themeName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"themeName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Theme Name</div>");
            buf.append("<INPUT NAME=\"themeName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("layoutPage")) {
                String value = WebUtil.display(request.getParameter("layoutPage"));

                if ( forceHiddenSet.contains("layoutPage")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"layoutPage\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Layout Page</div>");
            buf.append("<INPUT NAME=\"layoutPage\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("cssIndex")) {
                String value = WebUtil.display(request.getParameter("cssIndex"));

                if ( forceHiddenSet.contains("cssIndex")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"cssIndex\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Css Index</div>");
            buf.append("<INPUT NAME=\"cssIndex\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("themeStyleId")) {
                String value = WebUtil.display(request.getParameter("themeStyleId"));

                if ( forceHiddenSet.contains("themeStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"themeStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Theme Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"themeStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("ThemeAggregatorTheme Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                String value = WebUtil.display(request.getParameter("timeCreated"));

                if ( forceHiddenSet.contains("timeCreated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCreated\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Created</div>");
            buf.append("<INPUT NAME=\"timeCreated\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                String value = WebUtil.display(request.getParameter("timeUpdated"));

                if ( forceHiddenSet.contains("timeUpdated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeUpdated\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Updated</div>");
            buf.append("<INPUT NAME=\"timeUpdated\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult"+randNum+"\"></span>");
            buf.append("<a id=\"ajaxSubmit"+randNum+"\" href=\"javascript:sendForm_"+ randNum + "();\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2") || (hasRequestValue(request, "ajaxOut", "getscriptform"))){

			//This form is called by script such as e.g. <script type="text/javascript" src="/blockListAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>
			// inline_script will be attached to provide functionlities. 
			// This form will be used inside the same site to provide embedded page using <script> tags. But Refer to Poll "inline_script_poll" to 
			// send no-ajax submission. General no-ajax submission is not yet supported. 

            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
	        StringBuilder buf = new StringBuilder();
			Site site = SiteDS.getInstance().registerSite(request.getServerName());

            String importedScripts = null;
            try {
                importedScripts = FileUtil.loadCodesToString("./inline_script.txt");
                m_logger.debug(importedScripts);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                importedScripts = "";
            }

            importedScripts += "\n";
            importedScripts += "function responseCallback_themeAggregator(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayThemeAggregator\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_themeAggregator(){\n";
            importedScripts +=     "xmlhttpPostXX('themeAggregatorFormAddDis','/themeAggregatorAction.html', 'resultDisplayThemeAggregator', '${ajax_response_fields}', responseCallback_themeAggregator);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_themeAggregator(){\n";
            importedScripts +=     "clearFormXX('themeAggregatorFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_themeAggregator(){\n";
            importedScripts +=     "backToXX('themeAggregatorFormAddDis','resultDisplayThemeAggregator');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"themeAggregatorFormAddDis\" method=\"POST\" action=\"/themeAggregatorAction.html\" id=\"themeAggregatorFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Theme Name</div>");
        buf.append("<input class=\"field\" id=\"themeName\" type=\"text\" size=\"70\" name=\"themeName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Layout Page</div>");
        buf.append("<input class=\"field\" id=\"layoutPage\" type=\"text\" size=\"70\" name=\"layoutPage\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Css Index</div>");
        buf.append("<input class=\"field\" id=\"cssIndex\" type=\"text\" size=\"70\" name=\"cssIndex\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Theme Style Id</div>");
        buf.append("<select class=\"field\" name=\"themeStyleId\" id=\"themeStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listThemeAggregator_themeStyleId = ThemeAggregatorDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listThemeAggregator_themeStyleId.iterator(); iter.hasNext();){
		ThemeAggregator _obj = (ThemeAggregator) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getId() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Created</div>");
        buf.append("<input class=\"field\" id=\"timeCreated\" type=\"text\" size=\"70\" name=\"timeCreated\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Updated</div>");
        buf.append("<input class=\"field\" id=\"timeUpdated\" type=\"text\" size=\"70\" name=\"timeUpdated\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_themeAggregator()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_themeAggregator()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayThemeAggregator\"></span>");
			buf.append("<a href=\"javascript:showform_themeAggregator()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        return ret;
    }


	// Prepares data to be returned in ajax.
    protected List prepareReturnData(HttpServletRequest request, ThemeAggregator target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            ThemeAggregator _ThemeAggregator = null; 
            List list = ThemeAggregatorDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _ThemeAggregator = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _ThemeAggregator = (ThemeAggregator) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ThemeAggregator = (ThemeAggregator) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ThemeAggregator = ThemeAggregatorDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_ThemeAggregator);

        } else {
            
            List list = ThemeAggregatorDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        ThemeAggregatorForm _ThemeAggregatorForm = (ThemeAggregatorForm) form;

		if(requestParams.containsKey("themeName"))
			_ThemeAggregatorForm.setThemeName((String)requestParams.get("themeName"));
		if(requestParams.containsKey("layoutPage"))
			_ThemeAggregatorForm.setLayoutPage((String)requestParams.get("layoutPage"));
		if(requestParams.containsKey("cssIndex"))
			_ThemeAggregatorForm.setCssIndex((String)requestParams.get("cssIndex"));
		if(requestParams.containsKey("themeStyleId"))
			_ThemeAggregatorForm.setThemeStyleId((String)requestParams.get("themeStyleId"));
		if(requestParams.containsKey("timeCreated"))
			_ThemeAggregatorForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_ThemeAggregatorForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
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
    
    protected ThemeAggregatorDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
