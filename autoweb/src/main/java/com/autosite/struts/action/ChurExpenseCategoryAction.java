package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jtrend.util.RequestUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.jtrend.util.JtStringUtil;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
					
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.ChurExpenseCategory;
import com.autosite.ds.ChurExpenseCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ChurExpenseCategoryForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check




public class ChurExpenseCategoryAction extends AutositeCoreAction {

    public ChurExpenseCategoryAction(){
        m_ds = ChurExpenseCategoryDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        ChurExpenseCategoryForm _ChurExpenseCategoryForm = (ChurExpenseCategoryForm) form;
        HttpSession session = request.getSession();

        setPage(session, getDefaultPage());

        Site site = getSite(request);
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
			//Default error page but will be overridden by exception specific error page
            setPage(session, getDefaultPage());
            return mapping.findForward("default");
        }

		// Check if needs confirmTo step

        if ( !isAjaxRequest(request) && (
            hasRequestValue(request, "XXXXXXXXXXX", "true")))
        {    
            if (forwardConfirmTo(request))
                return mapping.findForward("default");
        
//            String confirmToKey = request.getParameter("confTo");
//            
//            m_logger.debug("ConfirmTo Key=" + confirmToKey);
//            if ( ConfirmRegisterManager.getInstance().find(confirmToKey) != null &&  ConfirmRegisterManager.getInstance().find(confirmToKey).confirmed() ){
//                m_logger.debug("ConfirmTo has been confirmed. Will proceed");
//            } else {
//                String paramStr = RequestUtil.getParameterString(request, "&");
//                ConfirmTo newConfirmTo = ConfirmRegisterManager.getInstance().registerNew(request.getRequestURI(), paramStr);
//                request.setAttribute("confTo", newConfirmTo);
//                setPage(session, "confirm_required");
//                session.setAttribute("k_ignore_embedded_page", "true");
//                return mapping.findForward("default");
//            }
        }


		// Check permissions 
        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            setPage(session, getErrorPage());
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
            setPage(session, getErrorPage());
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        ChurExpenseCategory _ChurExpenseCategory = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true") && isThere("id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ChurExpenseCategory = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ChurExpenseCategory _ChurExpenseCategory = m_ds.getById(cid);

            if (_ChurExpenseCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_ChurExpenseCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurExpenseCategory.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_ChurExpenseCategory);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _ChurExpenseCategoryForm, _ChurExpenseCategory);
                if (returnObjects != null) returnObjects.put("target", _ChurExpenseCategory);
				setPage(session, getAfterEditPage());
            }

            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

			//Default error page but will be overridden by exception specific error page
            setPage(session, getAfterEditPage());
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ChurExpenseCategory _ChurExpenseCategory = m_ds.getById(cid);

            if (_ChurExpenseCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
			//Default error page but will be overridden by exception specific error page
            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_ChurExpenseCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurExpenseCategory.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }


            try{
                editField(request, response, _ChurExpenseCategory);
                if (returnObjects != null) returnObjects.put("target", _ChurExpenseCategory);
				setPage(session, getAfterEditFieldPage());
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
			//Default error page but will be overridden by exception specific error page
            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            setPage(session, getAfterEditFieldPage());
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ChurExpenseCategory _ChurExpenseCategory = m_ds.getById(cid);

            if (_ChurExpenseCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_ChurExpenseCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurExpenseCategory.getSiteId()); 
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _ChurExpenseCategory);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            m_ds.delete(_ChurExpenseCategory); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _ChurExpenseCategory);
			setPageForward(session, getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _ChurExpenseCategory);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            setPage(session, getAfterDeletePage());
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new ChurExpenseCategory" );
            ChurExpenseCategory _ChurExpenseCategoryNew = new ChurExpenseCategory();   

            // Setting IDs for the object
            _ChurExpenseCategoryNew.setSiteId(site.getId());

            _ChurExpenseCategoryNew.setExpenseCategory(WebParamUtil.getStringValue(_ChurExpenseCategoryForm.getExpenseCategory()));
            m_logger.debug("setting ExpenseCategory=" +_ChurExpenseCategoryForm.getExpenseCategory());
            _ChurExpenseCategoryNew.setDisplay(WebParamUtil.getStringValue(_ChurExpenseCategoryForm.getDisplay()));
            m_logger.debug("setting Display=" +_ChurExpenseCategoryForm.getDisplay());


            try {
                checkDepedenceIntegrity(_ChurExpenseCategoryNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ChurExpenseCategoryNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_ChurExpenseCategoryNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ChurExpenseCategoryNew);
            if (returnObjects != null) returnObjects.put("target", _ChurExpenseCategory);
			setPage(session, getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ChurExpenseCategoryNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "chur_expense_category_home");


        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            setPage(session, getErrorPage());
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ChurExpenseCategoryForm _ChurExpenseCategoryForm, ChurExpenseCategory _ChurExpenseCategory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurExpenseCategory _ChurExpenseCategory = m_ds.getById(cid);

        m_logger.debug("Before update " + ChurExpenseCategoryDS.objectToString(_ChurExpenseCategory));

        _ChurExpenseCategory.setExpenseCategory(WebParamUtil.getStringValue(_ChurExpenseCategoryForm.getExpenseCategory()));
        _ChurExpenseCategory.setDisplay(WebParamUtil.getStringValue(_ChurExpenseCategoryForm.getDisplay()));

        m_actionExtent.beforeUpdate(request, response, _ChurExpenseCategory);
        m_ds.update(_ChurExpenseCategory);
        m_actionExtent.afterUpdate(request, response, _ChurExpenseCategory);
        m_logger.debug("After update " + ChurExpenseCategoryDS.objectToString(_ChurExpenseCategory));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ChurExpenseCategory _ChurExpenseCategory) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurExpenseCategory _ChurExpenseCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("expenseCategory"))) {
            m_logger.debug("updating param expenseCategory from " +_ChurExpenseCategory.getExpenseCategory() + "->" + request.getParameter("expenseCategory"));
            _ChurExpenseCategory.setExpenseCategory(WebParamUtil.getStringValue(request.getParameter("expenseCategory")));

        }
        if (!isMissing(request.getParameter("display"))) {
            m_logger.debug("updating param display from " +_ChurExpenseCategory.getDisplay() + "->" + request.getParameter("display"));
            _ChurExpenseCategory.setDisplay(WebParamUtil.getStringValue(request.getParameter("display")));

        }

        m_actionExtent.beforeUpdate(request, response, _ChurExpenseCategory);
        m_ds.update(_ChurExpenseCategory);
        m_actionExtent.afterUpdate(request, response, _ChurExpenseCategory);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ChurExpenseCategory _ChurExpenseCategory) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurExpenseCategory _ChurExpenseCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("expenseCategory"))) {
			return String.valueOf(_ChurExpenseCategory.getExpenseCategory());
        }
        if (!isMissing(request.getParameter("display"))) {
			return String.valueOf(_ChurExpenseCategory.getDisplay());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(ChurExpenseCategory _ChurExpenseCategory) throws Exception {
    }

    protected String getFieldByName(String fieldName, ChurExpenseCategory _ChurExpenseCategory) {
        if (fieldName == null || fieldName.equals("")|| _ChurExpenseCategory == null) return null;
        
        if (fieldName.equals("expenseCategory")) {
            return WebUtil.display(_ChurExpenseCategory.getExpenseCategory());
        }
        if (fieldName.equals("display")) {
            return WebUtil.display(_ChurExpenseCategory.getDisplay());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        ChurExpenseCategory target = null;
        
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
                target = (ChurExpenseCategory) working.get("target");
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
            ChurExpenseCategory _ChurExpenseCategory = ChurExpenseCategoryDS.getInstance().getById(id);
            if (_ChurExpenseCategory == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ChurExpenseCategory);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ChurExpenseCategory _ChurExpenseCategory = ChurExpenseCategoryDS.getInstance().getById(id);
            if ( _ChurExpenseCategory == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _ChurExpenseCategory);
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

            buf.append("<div id=\"churExpenseCategory-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory) iterator.next();

                buf.append("<div id=\"churExpenseCategory-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("expenseCategory")) {
                    buf.append("<div id=\"churExpenseCategory-ajax-expenseCategory\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpenseCategory.getExpenseCategory()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("display")) {
                    buf.append("<div id=\"churExpenseCategory-ajax-display\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpenseCategory.getDisplay()));
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
            if ( ignoreFieldSet || fieldSet.contains("expenseCategory")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Expense Category");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("display")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Display");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("expenseCategory")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpenseCategory.getExpenseCategory()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("display")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpenseCategory.getDisplay()));

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
	                ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("expenseCategory")) 
			            json.put("expenseCategory", ""+_ChurExpenseCategory.getExpenseCategory());
		            if ( ignoreFieldSet || fieldSet.contains("display")) 
			            json.put("display", ""+_ChurExpenseCategory.getDisplay());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                ChurExpenseCategory _ChurExpenseCategory = list.size() >=1?(ChurExpenseCategory) list.get(0): null; 

				if ( _ChurExpenseCategory != null) {
		            top.put("id", ""+_ChurExpenseCategory.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonExpenseCategory = new JSONObject();
		            jsonExpenseCategory.put("name", "expenseCategory");
		            jsonExpenseCategory.put("value", ""+_ChurExpenseCategory.getExpenseCategory());
		            array.put(jsonExpenseCategory);
		            JSONObject jsonDisplay = new JSONObject();
		            jsonDisplay.put("name", "display");
		            jsonDisplay.put("value", ""+_ChurExpenseCategory.getDisplay());
		            array.put(jsonDisplay);

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
            buf.append("sendFormAjax('/churExpenseCategoryAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/churExpenseCategoryAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("expenseCategory")) {
                String value = WebUtil.display(request.getParameter("expenseCategory"));

                if ( forceHiddenSet.contains("expenseCategory")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"expenseCategory\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Expense Category</div>");
            buf.append("<INPUT NAME=\"expenseCategory\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("display")) {
                String value = WebUtil.display(request.getParameter("display"));

                if ( forceHiddenSet.contains("display")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"display\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Display</div>");
            buf.append("<INPUT NAME=\"display\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_churExpenseCategory(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayChurExpenseCategory\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_churExpenseCategory(){\n";
            importedScripts +=     "xmlhttpPostXX('churExpenseCategoryFormAddDis','/churExpenseCategoryAction.html', 'resultDisplayChurExpenseCategory', '${ajax_response_fields}', responseCallback_churExpenseCategory);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_churExpenseCategory(){\n";
            importedScripts +=     "clearFormXX('churExpenseCategoryFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_churExpenseCategory(){\n";
            importedScripts +=     "backToXX('churExpenseCategoryFormAddDis','resultDisplayChurExpenseCategory');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"churExpenseCategoryFormAddDis\" method=\"POST\" action=\"/churExpenseCategoryAction.html\" id=\"churExpenseCategoryFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Expense Category</div>");
        buf.append("<input class=\"field\" id=\"expenseCategory\" type=\"text\" size=\"70\" name=\"expenseCategory\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Display</div>");
        buf.append("<input class=\"field\" id=\"display\" type=\"text\" size=\"70\" name=\"display\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_churExpenseCategory()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_churExpenseCategory()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayChurExpenseCategory\"></span>");
			buf.append("<a href=\"javascript:showform_churExpenseCategory()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, ChurExpenseCategory target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            ChurExpenseCategory _ChurExpenseCategory = null; 
            List list = ChurExpenseCategoryDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _ChurExpenseCategory = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _ChurExpenseCategory = (ChurExpenseCategory) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ChurExpenseCategory = (ChurExpenseCategory) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ChurExpenseCategory = ChurExpenseCategoryDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_ChurExpenseCategory);

        } else {
            
            List list = ChurExpenseCategoryDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


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
    
    protected ChurExpenseCategoryDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ChurExpenseCategoryAction.class);





}
