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

import com.autosite.db.CleanerPickupDeliveryRecur;
import com.autosite.ds.CleanerPickupDeliveryRecurDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.CleanerPickupDeliveryRecurForm;
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




public class CleanerPickupDeliveryRecurAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(CleanerPickupDeliveryRecurAction.class);

    public CleanerPickupDeliveryRecurAction(){
        m_ds = CleanerPickupDeliveryRecurDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        CleanerPickupDeliveryRecurForm _CleanerPickupDeliveryRecurForm = (CleanerPickupDeliveryRecurForm) form;
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


        CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _CleanerPickupDeliveryRecur = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = m_ds.getById(cid);

            if (_CleanerPickupDeliveryRecur == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_CleanerPickupDeliveryRecur.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDeliveryRecur.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_CleanerPickupDeliveryRecur);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _CleanerPickupDeliveryRecurForm, _CleanerPickupDeliveryRecur);
                if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryRecur);
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
            //CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = m_ds.getById(cid);

            if (_CleanerPickupDeliveryRecur == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerPickupDeliveryRecur.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDeliveryRecur.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _CleanerPickupDeliveryRecur);
                if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryRecur);
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
            //CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = m_ds.getById(cid);

            if (_CleanerPickupDeliveryRecur == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_CleanerPickupDeliveryRecur.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _CleanerPickupDeliveryRecur.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _CleanerPickupDeliveryRecur);
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

            m_ds.delete(_CleanerPickupDeliveryRecur); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryRecur);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _CleanerPickupDeliveryRecur);
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


            m_logger.info("Creating new CleanerPickupDeliveryRecur" );
            CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecurNew = new CleanerPickupDeliveryRecur();   

            // Setting IDs for the object
            _CleanerPickupDeliveryRecurNew.setSiteId(site.getId());

            _CleanerPickupDeliveryRecurNew.setCustomerId(WebParamUtil.getLongValue(_CleanerPickupDeliveryRecurForm.getCustomerId()));
            m_logger.debug("setting CustomerId=" +_CleanerPickupDeliveryRecurForm.getCustomerId());
            _CleanerPickupDeliveryRecurNew.setWeekday(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryRecurForm.getWeekday()));
            m_logger.debug("setting Weekday=" +_CleanerPickupDeliveryRecurForm.getWeekday());
            _CleanerPickupDeliveryRecurNew.setTimeHhdd(WebParamUtil.getStringValue(_CleanerPickupDeliveryRecurForm.getTimeHhdd()));
            m_logger.debug("setting TimeHhdd=" +_CleanerPickupDeliveryRecurForm.getTimeHhdd());


            try {
                checkDepedenceIntegrity(_CleanerPickupDeliveryRecurNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _CleanerPickupDeliveryRecurNew);
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
            
            if (_CleanerPickupDeliveryRecurNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_CleanerPickupDeliveryRecurNew);
            if (returnObjects != null) returnObjects.put("target", _CleanerPickupDeliveryRecur);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _CleanerPickupDeliveryRecurNew);
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


    protected void edit(HttpServletRequest request, HttpServletResponse response, CleanerPickupDeliveryRecurForm _CleanerPickupDeliveryRecurForm, CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = m_ds.getById(cid);

        m_logger.debug("Before update " + CleanerPickupDeliveryRecurDS.objectToString(_CleanerPickupDeliveryRecur));

        _CleanerPickupDeliveryRecur.setCustomerId(WebParamUtil.getLongValue(_CleanerPickupDeliveryRecurForm.getCustomerId()));
        _CleanerPickupDeliveryRecur.setWeekday(WebParamUtil.getIntegerValue(_CleanerPickupDeliveryRecurForm.getWeekday()));
        _CleanerPickupDeliveryRecur.setTimeHhdd(WebParamUtil.getStringValue(_CleanerPickupDeliveryRecurForm.getTimeHhdd()));

        m_actionExtent.beforeUpdate(request, response, _CleanerPickupDeliveryRecur);
        m_ds.update(_CleanerPickupDeliveryRecur);
        m_actionExtent.afterUpdate(request, response, _CleanerPickupDeliveryRecur);
        m_logger.debug("After update " + CleanerPickupDeliveryRecurDS.objectToString(_CleanerPickupDeliveryRecur));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = m_ds.getById(cid);

        if (!isMissing(request.getParameter("customerId"))) {
            m_logger.debug("updating param customerId from " +_CleanerPickupDeliveryRecur.getCustomerId() + "->" + request.getParameter("customerId"));
            _CleanerPickupDeliveryRecur.setCustomerId(WebParamUtil.getLongValue(request.getParameter("customerId")));

        }
        if (!isMissing(request.getParameter("weekday"))) {
            m_logger.debug("updating param weekday from " +_CleanerPickupDeliveryRecur.getWeekday() + "->" + request.getParameter("weekday"));
            _CleanerPickupDeliveryRecur.setWeekday(WebParamUtil.getIntegerValue(request.getParameter("weekday")));

        }
        if (!isMissing(request.getParameter("timeHhdd"))) {
            m_logger.debug("updating param timeHhdd from " +_CleanerPickupDeliveryRecur.getTimeHhdd() + "->" + request.getParameter("timeHhdd"));
            _CleanerPickupDeliveryRecur.setTimeHhdd(WebParamUtil.getStringValue(request.getParameter("timeHhdd")));

        }

        m_actionExtent.beforeUpdate(request, response, _CleanerPickupDeliveryRecur);
        m_ds.update(_CleanerPickupDeliveryRecur);
        m_actionExtent.afterUpdate(request, response, _CleanerPickupDeliveryRecur);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = m_ds.getById(cid);

        if (!isMissing(request.getParameter("customerId"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryRecur.getCustomerId());
        }
        if (!isMissing(request.getParameter("weekday"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryRecur.getWeekday());
        }
        if (!isMissing(request.getParameter("timeHhdd"))) {
			return  JtStringUtil.valueOf(_CleanerPickupDeliveryRecur.getTimeHhdd());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur) throws Exception {
    }

    protected String getFieldByName(String fieldName, CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur) {
        if (fieldName == null || fieldName.equals("")|| _CleanerPickupDeliveryRecur == null) return null;
        
        if (fieldName.equals("customerId")) {
            return WebUtil.display(_CleanerPickupDeliveryRecur.getCustomerId());
        }
        if (fieldName.equals("weekday")) {
            return WebUtil.display(_CleanerPickupDeliveryRecur.getWeekday());
        }
        if (fieldName.equals("timeHhdd")) {
            return WebUtil.display(_CleanerPickupDeliveryRecur.getTimeHhdd());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        CleanerPickupDeliveryRecur target = null;
        
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
                target = (CleanerPickupDeliveryRecur) working.get("target");
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
            CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = CleanerPickupDeliveryRecurDS.getInstance().getById(id);
            if (_CleanerPickupDeliveryRecur == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerPickupDeliveryRecur);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = CleanerPickupDeliveryRecurDS.getInstance().getById(id);
            if ( _CleanerPickupDeliveryRecur == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerPickupDeliveryRecur);
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

            buf.append("<div id=\"cleanerPickupDeliveryRecur-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) iterator.next();

                buf.append("<div id=\"cleanerPickupDeliveryRecur-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
                    buf.append("<div id=\"cleanerPickupDeliveryRecur-ajax-customerId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDeliveryRecur.getCustomerId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("weekday")) {
                    buf.append("<div id=\"cleanerPickupDeliveryRecur-ajax-weekday\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDeliveryRecur.getWeekday()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeHhdd")) {
                    buf.append("<div id=\"cleanerPickupDeliveryRecur-ajax-timeHhdd\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDeliveryRecur.getTimeHhdd()));
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
            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Customer Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("weekday")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Weekday");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeHhdd")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Hhdd");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDeliveryRecur.getCustomerId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("weekday")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDeliveryRecur.getWeekday()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeHhdd")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDeliveryRecur.getTimeHhdd()));

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
	                CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("customerId")) 
			            json.put("customerId", ""+_CleanerPickupDeliveryRecur.getCustomerId());
		            if ( ignoreFieldSet || fieldSet.contains("weekday")) 
			            json.put("weekday", ""+_CleanerPickupDeliveryRecur.getWeekday());
		            if ( ignoreFieldSet || fieldSet.contains("timeHhdd")) 
			            json.put("timeHhdd", ""+_CleanerPickupDeliveryRecur.getTimeHhdd());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = list.size() >=1?(CleanerPickupDeliveryRecur) list.get(0): null; 

				if ( _CleanerPickupDeliveryRecur != null) {
		            top.put("id", ""+_CleanerPickupDeliveryRecur.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonCustomerId = new JSONObject();
		            jsonCustomerId.put("name", "customerId");
		            jsonCustomerId.put("value", ""+_CleanerPickupDeliveryRecur.getCustomerId());
		            array.put(jsonCustomerId);
		            JSONObject jsonWeekday = new JSONObject();
		            jsonWeekday.put("name", "weekday");
		            jsonWeekday.put("value", ""+_CleanerPickupDeliveryRecur.getWeekday());
		            array.put(jsonWeekday);
		            JSONObject jsonTimeHhdd = new JSONObject();
		            jsonTimeHhdd.put("name", "timeHhdd");
		            jsonTimeHhdd.put("value", ""+_CleanerPickupDeliveryRecur.getTimeHhdd());
		            array.put(jsonTimeHhdd);

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
            buf.append("sendFormAjax('/cleanerPickupDeliveryRecurAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerPickupDeliveryRecurAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
                String value = WebUtil.display(request.getParameter("customerId"));

                if ( forceHiddenSet.contains("customerId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"customerId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Customer Id</div>");
            buf.append("<INPUT NAME=\"customerId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("weekday")) {
                String value = WebUtil.display(request.getParameter("weekday"));

                if ( forceHiddenSet.contains("weekday")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"weekday\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Weekday</div>");
            buf.append("<INPUT NAME=\"weekday\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeHhdd")) {
                String value = WebUtil.display(request.getParameter("timeHhdd"));

                if ( forceHiddenSet.contains("timeHhdd")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeHhdd\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Hhdd</div>");
            buf.append("<INPUT NAME=\"timeHhdd\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_cleanerPickupDeliveryRecur(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerPickupDeliveryRecur\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerPickupDeliveryRecur(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerPickupDeliveryRecurFormAddDis','/cleanerPickupDeliveryRecurAction.html', 'resultDisplayCleanerPickupDeliveryRecur', '${ajax_response_fields}', responseCallback_cleanerPickupDeliveryRecur);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerPickupDeliveryRecur(){\n";
            importedScripts +=     "clearFormXX('cleanerPickupDeliveryRecurFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerPickupDeliveryRecur(){\n";
            importedScripts +=     "backToXX('cleanerPickupDeliveryRecurFormAddDis','resultDisplayCleanerPickupDeliveryRecur');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerPickupDeliveryRecurFormAddDis\" method=\"POST\" action=\"/cleanerPickupDeliveryRecurAction.html\" id=\"cleanerPickupDeliveryRecurFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Customer Id</div>");
        buf.append("<input class=\"field\" id=\"customerId\" type=\"text\" size=\"70\" name=\"customerId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Weekday</div>");
        buf.append("<input class=\"field\" id=\"weekday\" type=\"text\" size=\"70\" name=\"weekday\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Hhdd</div>");
        buf.append("<input class=\"field\" id=\"timeHhdd\" type=\"text\" size=\"70\" name=\"timeHhdd\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerPickupDeliveryRecur()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerPickupDeliveryRecur()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerPickupDeliveryRecur\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerPickupDeliveryRecur()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, CleanerPickupDeliveryRecur target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = null; 
            List list = CleanerPickupDeliveryRecurDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerPickupDeliveryRecur = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerPickupDeliveryRecur = CleanerPickupDeliveryRecurDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerPickupDeliveryRecur);

        } else {
            
            List list = CleanerPickupDeliveryRecurDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        CleanerPickupDeliveryRecurForm _CleanerPickupDeliveryRecurForm = (CleanerPickupDeliveryRecurForm) form;

		if(requestParams.containsKey("customerId"))
			_CleanerPickupDeliveryRecurForm.setCustomerId((String)requestParams.get("customerId"));
		if(requestParams.containsKey("weekday"))
			_CleanerPickupDeliveryRecurForm.setWeekday((String)requestParams.get("weekday"));
		if(requestParams.containsKey("timeHhdd"))
			_CleanerPickupDeliveryRecurForm.setTimeHhdd((String)requestParams.get("timeHhdd"));
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
    
    protected CleanerPickupDeliveryRecurDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
