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

import com.autosite.db.ThemeStyles;
import com.autosite.ds.ThemeStylesDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ThemeStylesForm;
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




public class ThemeStylesAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(ThemeStylesAction.class);

    public ThemeStylesAction(){
        m_ds = ThemeStylesDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        ThemeStylesForm _ThemeStylesForm = (ThemeStylesForm) form;
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


        ThemeStyles _ThemeStyles = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ThemeStyles = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ThemeStyles _ThemeStyles = m_ds.getById(cid);

            if (_ThemeStyles == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_ThemeStyles.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ThemeStyles.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_ThemeStyles);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _ThemeStylesForm, _ThemeStyles);
                if (returnObjects != null) returnObjects.put("target", _ThemeStyles);
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
            //ThemeStyles _ThemeStyles = m_ds.getById(cid);

            if (_ThemeStyles == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ThemeStyles.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ThemeStyles.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _ThemeStyles);
                if (returnObjects != null) returnObjects.put("target", _ThemeStyles);
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
            //ThemeStyles _ThemeStyles = m_ds.getById(cid);

            if (_ThemeStyles == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_ThemeStyles.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ThemeStyles.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _ThemeStyles);
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

            m_ds.delete(_ThemeStyles); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _ThemeStyles);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _ThemeStyles);
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


            m_logger.info("Creating new ThemeStyles" );
            ThemeStyles _ThemeStylesNew = new ThemeStyles();   

            // Setting IDs for the object
            _ThemeStylesNew.setSiteId(site.getId());

            _ThemeStylesNew.setBodyWidth(WebParamUtil.getIntegerValue(_ThemeStylesForm.getBodyWidth()));
            m_logger.debug("setting BodyWidth=" +_ThemeStylesForm.getBodyWidth());
            _ThemeStylesNew.setBodyAlign(WebParamUtil.getStringValue(_ThemeStylesForm.getBodyAlign()));
            m_logger.debug("setting BodyAlign=" +_ThemeStylesForm.getBodyAlign());
            _ThemeStylesNew.setBodyBackground(WebParamUtil.getStringValue(_ThemeStylesForm.getBodyBackground()));
            m_logger.debug("setting BodyBackground=" +_ThemeStylesForm.getBodyBackground());
            _ThemeStylesNew.setTimeCreated(WebParamUtil.getTimestampValue(_ThemeStylesForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ThemeStylesForm.getTimeCreated());
            _ThemeStylesNew.setTimeUpdated(WebParamUtil.getTimestampValue(_ThemeStylesForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ThemeStylesForm.getTimeUpdated());


            try {
                checkDepedenceIntegrity(_ThemeStylesNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ThemeStylesNew);
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
            
            if (_ThemeStylesNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ThemeStylesNew);
            if (returnObjects != null) returnObjects.put("target", _ThemeStyles);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ThemeStylesNew);
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, ThemeStylesForm _ThemeStylesForm, ThemeStyles _ThemeStyles) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ThemeStyles _ThemeStyles = m_ds.getById(cid);

        m_logger.debug("Before update " + ThemeStylesDS.objectToString(_ThemeStyles));

        _ThemeStyles.setBodyWidth(WebParamUtil.getIntegerValue(_ThemeStylesForm.getBodyWidth()));
        _ThemeStyles.setBodyAlign(WebParamUtil.getStringValue(_ThemeStylesForm.getBodyAlign()));
        _ThemeStyles.setBodyBackground(WebParamUtil.getStringValue(_ThemeStylesForm.getBodyBackground()));
        _ThemeStyles.setTimeUpdated(WebParamUtil.getTimestampValue(_ThemeStylesForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _ThemeStyles);
        m_ds.update(_ThemeStyles);
        m_actionExtent.afterUpdate(request, response, _ThemeStyles);
        m_logger.debug("After update " + ThemeStylesDS.objectToString(_ThemeStyles));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ThemeStyles _ThemeStyles) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ThemeStyles _ThemeStyles = m_ds.getById(cid);

        if (!isMissing(request.getParameter("bodyWidth"))) {
            m_logger.debug("updating param bodyWidth from " +_ThemeStyles.getBodyWidth() + "->" + request.getParameter("bodyWidth"));
            _ThemeStyles.setBodyWidth(WebParamUtil.getIntegerValue(request.getParameter("bodyWidth")));

        }
        if (!isMissing(request.getParameter("bodyAlign"))) {
            m_logger.debug("updating param bodyAlign from " +_ThemeStyles.getBodyAlign() + "->" + request.getParameter("bodyAlign"));
            _ThemeStyles.setBodyAlign(WebParamUtil.getStringValue(request.getParameter("bodyAlign")));

        }
        if (!isMissing(request.getParameter("bodyBackground"))) {
            m_logger.debug("updating param bodyBackground from " +_ThemeStyles.getBodyBackground() + "->" + request.getParameter("bodyBackground"));
            _ThemeStyles.setBodyBackground(WebParamUtil.getStringValue(request.getParameter("bodyBackground")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ThemeStyles.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ThemeStyles.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_ThemeStyles.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _ThemeStyles.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _ThemeStyles.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _ThemeStyles);
        m_ds.update(_ThemeStyles);
        m_actionExtent.afterUpdate(request, response, _ThemeStyles);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ThemeStyles _ThemeStyles) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ThemeStyles _ThemeStyles = m_ds.getById(cid);

        if (!isMissing(request.getParameter("bodyWidth"))) {
			return  JtStringUtil.valueOf(_ThemeStyles.getBodyWidth());
        }
        if (!isMissing(request.getParameter("bodyAlign"))) {
			return  JtStringUtil.valueOf(_ThemeStyles.getBodyAlign());
        }
        if (!isMissing(request.getParameter("bodyBackground"))) {
			return  JtStringUtil.valueOf(_ThemeStyles.getBodyBackground());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_ThemeStyles.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_ThemeStyles.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(ThemeStyles _ThemeStyles) throws Exception {
    }

    protected String getFieldByName(String fieldName, ThemeStyles _ThemeStyles) {
        if (fieldName == null || fieldName.equals("")|| _ThemeStyles == null) return null;
        
        if (fieldName.equals("bodyWidth")) {
            return WebUtil.display(_ThemeStyles.getBodyWidth());
        }
        if (fieldName.equals("bodyAlign")) {
            return WebUtil.display(_ThemeStyles.getBodyAlign());
        }
        if (fieldName.equals("bodyBackground")) {
            return WebUtil.display(_ThemeStyles.getBodyBackground());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_ThemeStyles.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_ThemeStyles.getTimeUpdated());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        ThemeStyles target = null;
        
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
                target = (ThemeStyles) working.get("target");
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
            ThemeStyles _ThemeStyles = ThemeStylesDS.getInstance().getById(id);
            if (_ThemeStyles == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ThemeStyles);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ThemeStyles _ThemeStyles = ThemeStylesDS.getInstance().getById(id);
            if ( _ThemeStyles == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _ThemeStyles);
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

            buf.append("<div id=\"themeStyles-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ThemeStyles _ThemeStyles = (ThemeStyles) iterator.next();

                buf.append("<div id=\"themeStyles-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
                    buf.append("<div id=\"themeStyles-ajax-bodyWidth\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeStyles.getBodyWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
                    buf.append("<div id=\"themeStyles-ajax-bodyAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeStyles.getBodyAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyBackground")) {
                    buf.append("<div id=\"themeStyles-ajax-bodyBackground\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeStyles.getBodyBackground()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"themeStyles-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeStyles.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"themeStyles-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ThemeStyles.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyBackground")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Background");
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
                ThemeStyles _ThemeStyles = (ThemeStyles) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeStyles.getBodyWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeStyles.getBodyAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyBackground")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeStyles.getBodyBackground()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeStyles.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ThemeStyles.getTimeUpdated()));

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
	                ThemeStyles _ThemeStyles = (ThemeStyles) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) 
			            json.put("bodyWidth", ""+_ThemeStyles.getBodyWidth());
		            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) 
			            json.put("bodyAlign", ""+_ThemeStyles.getBodyAlign());
		            if ( ignoreFieldSet || fieldSet.contains("bodyBackground")) 
			            json.put("bodyBackground", ""+_ThemeStyles.getBodyBackground());
		            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
			            json.put("timeCreated", ""+_ThemeStyles.getTimeCreated());
		            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
			            json.put("timeUpdated", ""+_ThemeStyles.getTimeUpdated());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                ThemeStyles _ThemeStyles = list.size() >=1?(ThemeStyles) list.get(0): null; 

				if ( _ThemeStyles != null) {
		            top.put("id", ""+_ThemeStyles.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonBodyWidth = new JSONObject();
		            jsonBodyWidth.put("name", "bodyWidth");
		            jsonBodyWidth.put("value", ""+_ThemeStyles.getBodyWidth());
		            array.put(jsonBodyWidth);
		            JSONObject jsonBodyAlign = new JSONObject();
		            jsonBodyAlign.put("name", "bodyAlign");
		            jsonBodyAlign.put("value", ""+_ThemeStyles.getBodyAlign());
		            array.put(jsonBodyAlign);
		            JSONObject jsonBodyBackground = new JSONObject();
		            jsonBodyBackground.put("name", "bodyBackground");
		            jsonBodyBackground.put("value", ""+_ThemeStyles.getBodyBackground());
		            array.put(jsonBodyBackground);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_ThemeStyles.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_ThemeStyles.getTimeUpdated());
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
            buf.append("sendFormAjax('/themeStylesAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/themeStylesAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
                String value = WebUtil.display(request.getParameter("bodyWidth"));

                if ( forceHiddenSet.contains("bodyWidth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyWidth\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Width</div>");
            buf.append("<INPUT NAME=\"bodyWidth\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
                String value = WebUtil.display(request.getParameter("bodyAlign"));

                if ( forceHiddenSet.contains("bodyAlign")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyAlign\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Align</div>");
            buf.append("<INPUT NAME=\"bodyAlign\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyBackground")) {
                String value = WebUtil.display(request.getParameter("bodyBackground"));

                if ( forceHiddenSet.contains("bodyBackground")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyBackground\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Background</div>");
            buf.append("<INPUT NAME=\"bodyBackground\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_themeStyles(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayThemeStyles\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_themeStyles(){\n";
            importedScripts +=     "xmlhttpPostXX('themeStylesFormAddDis','/themeStylesAction.html', 'resultDisplayThemeStyles', '${ajax_response_fields}', responseCallback_themeStyles);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_themeStyles(){\n";
            importedScripts +=     "clearFormXX('themeStylesFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_themeStyles(){\n";
            importedScripts +=     "backToXX('themeStylesFormAddDis','resultDisplayThemeStyles');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"themeStylesFormAddDis\" method=\"POST\" action=\"/themeStylesAction.html\" id=\"themeStylesFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Body Width</div>");
        buf.append("<input class=\"field\" id=\"bodyWidth\" type=\"text\" size=\"70\" name=\"bodyWidth\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Align</div>");
        buf.append("<input class=\"field\" id=\"bodyAlign\" type=\"text\" size=\"70\" name=\"bodyAlign\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Background</div>");
        buf.append("<input class=\"field\" id=\"bodyBackground\" type=\"text\" size=\"70\" name=\"bodyBackground\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Created</div>");
        buf.append("<input class=\"field\" id=\"timeCreated\" type=\"text\" size=\"70\" name=\"timeCreated\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Updated</div>");
        buf.append("<input class=\"field\" id=\"timeUpdated\" type=\"text\" size=\"70\" name=\"timeUpdated\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_themeStyles()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_themeStyles()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayThemeStyles\"></span>");
			buf.append("<a href=\"javascript:showform_themeStyles()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, ThemeStyles target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            ThemeStyles _ThemeStyles = null; 
            List list = ThemeStylesDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _ThemeStyles = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _ThemeStyles = (ThemeStyles) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ThemeStyles = (ThemeStyles) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ThemeStyles = ThemeStylesDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_ThemeStyles);

        } else {
            
            List list = ThemeStylesDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        ThemeStylesForm _ThemeStylesForm = (ThemeStylesForm) form;

		if(requestParams.containsKey("bodyWidth"))
			_ThemeStylesForm.setBodyWidth((String)requestParams.get("bodyWidth"));
		if(requestParams.containsKey("bodyAlign"))
			_ThemeStylesForm.setBodyAlign((String)requestParams.get("bodyAlign"));
		if(requestParams.containsKey("bodyBackground"))
			_ThemeStylesForm.setBodyBackground((String)requestParams.get("bodyBackground"));
		if(requestParams.containsKey("timeCreated"))
			_ThemeStylesForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_ThemeStylesForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
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
    
    protected ThemeStylesDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
