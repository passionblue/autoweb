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

import com.autosite.db.ChurExpenseItem;
import com.autosite.ds.ChurExpenseItemDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ChurExpenseItemForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check

import com.autosite.ds.ChurExpenseCategoryDS;
import com.autosite.db.ChurExpenseCategory;



public class ChurExpenseItemAction extends AutositeCoreAction {

    public ChurExpenseItemAction(){
        m_ds = ChurExpenseItemDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        ChurExpenseItemForm _ChurExpenseItemForm = (ChurExpenseItemForm) form;
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


        ChurExpenseItem _ChurExpenseItem = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true") && isThere("id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ChurExpenseItem = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ChurExpenseItem _ChurExpenseItem = m_ds.getById(cid);

            if (_ChurExpenseItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_ChurExpenseItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurExpenseItem.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_ChurExpenseItem);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _ChurExpenseItemForm, _ChurExpenseItem);
                if (returnObjects != null) returnObjects.put("target", _ChurExpenseItem);
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
            //ChurExpenseItem _ChurExpenseItem = m_ds.getById(cid);

            if (_ChurExpenseItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
			//Default error page but will be overridden by exception specific error page
            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_ChurExpenseItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurExpenseItem.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }


            try{
                editField(request, response, _ChurExpenseItem);
                if (returnObjects != null) returnObjects.put("target", _ChurExpenseItem);
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
            //ChurExpenseItem _ChurExpenseItem = m_ds.getById(cid);

            if (_ChurExpenseItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_ChurExpenseItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ChurExpenseItem.getSiteId()); 
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _ChurExpenseItem);
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

            m_ds.delete(_ChurExpenseItem); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _ChurExpenseItem);
			setPageForward(session, getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _ChurExpenseItem);
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


            m_logger.info("Creating new ChurExpenseItem" );
            ChurExpenseItem _ChurExpenseItemNew = new ChurExpenseItem();   

            // Setting IDs for the object
            _ChurExpenseItemNew.setSiteId(site.getId());

            _ChurExpenseItemNew.setCategoryId(WebParamUtil.getLongValue(_ChurExpenseItemForm.getCategoryId()));
            m_logger.debug("setting CategoryId=" +_ChurExpenseItemForm.getCategoryId());
            _ChurExpenseItemNew.setExpenseItem(WebParamUtil.getStringValue(_ChurExpenseItemForm.getExpenseItem()));
            m_logger.debug("setting ExpenseItem=" +_ChurExpenseItemForm.getExpenseItem());
            _ChurExpenseItemNew.setDisplay(WebParamUtil.getStringValue(_ChurExpenseItemForm.getDisplay()));
            m_logger.debug("setting Display=" +_ChurExpenseItemForm.getDisplay());


            try {
                checkDepedenceIntegrity(_ChurExpenseItemNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ChurExpenseItemNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_ChurExpenseItemNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ChurExpenseItemNew);
            if (returnObjects != null) returnObjects.put("target", _ChurExpenseItem);
			setPage(session, getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _ChurExpenseItemNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "chur_expense_item_home");


        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            setPage(session, getErrorPage());
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ChurExpenseItemForm _ChurExpenseItemForm, ChurExpenseItem _ChurExpenseItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurExpenseItem _ChurExpenseItem = m_ds.getById(cid);

        m_logger.debug("Before update " + ChurExpenseItemDS.objectToString(_ChurExpenseItem));

        _ChurExpenseItem.setCategoryId(WebParamUtil.getLongValue(_ChurExpenseItemForm.getCategoryId()));
        _ChurExpenseItem.setExpenseItem(WebParamUtil.getStringValue(_ChurExpenseItemForm.getExpenseItem()));
        _ChurExpenseItem.setDisplay(WebParamUtil.getStringValue(_ChurExpenseItemForm.getDisplay()));

        m_actionExtent.beforeUpdate(request, response, _ChurExpenseItem);
        m_ds.update(_ChurExpenseItem);
        m_actionExtent.afterUpdate(request, response, _ChurExpenseItem);
        m_logger.debug("After update " + ChurExpenseItemDS.objectToString(_ChurExpenseItem));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ChurExpenseItem _ChurExpenseItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurExpenseItem _ChurExpenseItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("categoryId"))) {
            m_logger.debug("updating param categoryId from " +_ChurExpenseItem.getCategoryId() + "->" + request.getParameter("categoryId"));
            _ChurExpenseItem.setCategoryId(WebParamUtil.getLongValue(request.getParameter("categoryId")));

        }
        if (!isMissing(request.getParameter("expenseItem"))) {
            m_logger.debug("updating param expenseItem from " +_ChurExpenseItem.getExpenseItem() + "->" + request.getParameter("expenseItem"));
            _ChurExpenseItem.setExpenseItem(WebParamUtil.getStringValue(request.getParameter("expenseItem")));

        }
        if (!isMissing(request.getParameter("display"))) {
            m_logger.debug("updating param display from " +_ChurExpenseItem.getDisplay() + "->" + request.getParameter("display"));
            _ChurExpenseItem.setDisplay(WebParamUtil.getStringValue(request.getParameter("display")));

        }

        m_actionExtent.beforeUpdate(request, response, _ChurExpenseItem);
        m_ds.update(_ChurExpenseItem);
        m_actionExtent.afterUpdate(request, response, _ChurExpenseItem);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ChurExpenseItem _ChurExpenseItem) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ChurExpenseItem _ChurExpenseItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("categoryId"))) {
			return String.valueOf(_ChurExpenseItem.getCategoryId());
        }
        if (!isMissing(request.getParameter("expenseItem"))) {
			return String.valueOf(_ChurExpenseItem.getExpenseItem());
        }
        if (!isMissing(request.getParameter("display"))) {
			return String.valueOf(_ChurExpenseItem.getDisplay());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(ChurExpenseItem _ChurExpenseItem) throws Exception {
    }

    protected String getFieldByName(String fieldName, ChurExpenseItem _ChurExpenseItem) {
        if (fieldName == null || fieldName.equals("")|| _ChurExpenseItem == null) return null;
        
        if (fieldName.equals("categoryId")) {
            return WebUtil.display(_ChurExpenseItem.getCategoryId());
        }
        if (fieldName.equals("expenseItem")) {
            return WebUtil.display(_ChurExpenseItem.getExpenseItem());
        }
        if (fieldName.equals("display")) {
            return WebUtil.display(_ChurExpenseItem.getDisplay());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        ChurExpenseItem target = null;
        
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
                target = (ChurExpenseItem) working.get("target");
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
            ChurExpenseItem _ChurExpenseItem = ChurExpenseItemDS.getInstance().getById(id);
            if (_ChurExpenseItem == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ChurExpenseItem);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ChurExpenseItem _ChurExpenseItem = ChurExpenseItemDS.getInstance().getById(id);
            if ( _ChurExpenseItem == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _ChurExpenseItem);
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

            buf.append("<div id=\"churExpenseItem-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem) iterator.next();

                buf.append("<div id=\"churExpenseItem-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
                    buf.append("<div id=\"churExpenseItem-ajax-categoryId\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpenseItem.getCategoryId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("expenseItem")) {
                    buf.append("<div id=\"churExpenseItem-ajax-expenseItem\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpenseItem.getExpenseItem()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("display")) {
                    buf.append("<div id=\"churExpenseItem-ajax-display\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpenseItem.getDisplay()));
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
            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Category Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("expenseItem")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Expense Item");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("display")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Display");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpenseItem.getCategoryId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("expenseItem")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpenseItem.getExpenseItem()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("display")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpenseItem.getDisplay()));

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
	                ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("categoryId")) 
			            json.put("categoryId", ""+_ChurExpenseItem.getCategoryId());
		            if ( ignoreFieldSet || fieldSet.contains("expenseItem")) 
			            json.put("expenseItem", ""+_ChurExpenseItem.getExpenseItem());
		            if ( ignoreFieldSet || fieldSet.contains("display")) 
			            json.put("display", ""+_ChurExpenseItem.getDisplay());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                ChurExpenseItem _ChurExpenseItem = list.size() >=1?(ChurExpenseItem) list.get(0): null; 

				if ( _ChurExpenseItem != null) {
		            top.put("id", ""+_ChurExpenseItem.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonCategoryId = new JSONObject();
		            jsonCategoryId.put("name", "categoryId");
		            jsonCategoryId.put("value", ""+_ChurExpenseItem.getCategoryId());
		            array.put(jsonCategoryId);
		            JSONObject jsonExpenseItem = new JSONObject();
		            jsonExpenseItem.put("name", "expenseItem");
		            jsonExpenseItem.put("value", ""+_ChurExpenseItem.getExpenseItem());
		            array.put(jsonExpenseItem);
		            JSONObject jsonDisplay = new JSONObject();
		            jsonDisplay.put("name", "display");
		            jsonDisplay.put("value", ""+_ChurExpenseItem.getDisplay());
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
            buf.append("sendFormAjax('/churExpenseItemAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/churExpenseItemAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
                String value = WebUtil.display(request.getParameter("categoryId"));

                if ( forceHiddenSet.contains("categoryId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"categoryId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Category Id</div>");
            buf.append("<select id=\"requiredField\" name=\"categoryId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("ChurExpenseItemCategory IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("expenseItem")) {
                String value = WebUtil.display(request.getParameter("expenseItem"));

                if ( forceHiddenSet.contains("expenseItem")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"expenseItem\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Expense Item</div>");
            buf.append("<INPUT NAME=\"expenseItem\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_churExpenseItem(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayChurExpenseItem\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_churExpenseItem(){\n";
            importedScripts +=     "xmlhttpPostXX('churExpenseItemFormAddDis','/churExpenseItemAction.html', 'resultDisplayChurExpenseItem', '${ajax_response_fields}', responseCallback_churExpenseItem);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_churExpenseItem(){\n";
            importedScripts +=     "clearFormXX('churExpenseItemFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_churExpenseItem(){\n";
            importedScripts +=     "backToXX('churExpenseItemFormAddDis','resultDisplayChurExpenseItem');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"churExpenseItemFormAddDis\" method=\"POST\" action=\"/churExpenseItemAction.html\" id=\"churExpenseItemFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Category Id</div>");
        buf.append("<select class=\"field\" name=\"categoryId\" id=\"categoryId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listChurExpenseCategory_categoryId = ChurExpenseCategoryDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurExpenseCategory_categoryId.iterator(); iter.hasNext();){
		ChurExpenseCategory _obj = (ChurExpenseCategory) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getExpenseCategory() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Expense Item</div>");
        buf.append("<input class=\"field\" id=\"expenseItem\" type=\"text\" size=\"70\" name=\"expenseItem\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Display</div>");
        buf.append("<input class=\"field\" id=\"display\" type=\"text\" size=\"70\" name=\"display\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_churExpenseItem()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_churExpenseItem()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayChurExpenseItem\"></span>");
			buf.append("<a href=\"javascript:showform_churExpenseItem()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, ChurExpenseItem target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            ChurExpenseItem _ChurExpenseItem = null; 
            List list = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _ChurExpenseItem = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _ChurExpenseItem = (ChurExpenseItem) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ChurExpenseItem = (ChurExpenseItem) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ChurExpenseItem = ChurExpenseItemDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_ChurExpenseItem);

        } else {
            
            List list = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());
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
    
    protected ChurExpenseItemDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ChurExpenseItemAction.class);





}
