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

import com.autosite.db.SiteHeader;
import com.autosite.ds.SiteHeaderDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.SiteHeaderForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
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




public class SiteHeaderAction extends AutositeCoreAction {

    public SiteHeaderAction(){
        m_ds = SiteHeaderDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        SiteHeaderForm _SiteHeaderForm = (SiteHeaderForm) form;
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

        ActionType actionType = ActionType.Update;
        if (!haveAccessToUpdate(session) ){
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


        SiteHeader _SiteHeader = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true") && isThere("id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _SiteHeader = m_ds.getById(cid);
		}



        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //SiteHeader _SiteHeader = m_ds.getById(cid);

            if (_SiteHeader == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_SiteHeader.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteHeader.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_SiteHeader);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _SiteHeaderForm, _SiteHeader);
                if (returnObjects != null) returnObjects.put("target", _SiteHeader);
				//setPageForward(session, getAfterEditPage());
				setPage(session, null, getAfterEditPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_edit_forward_page" ) );

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
            //SiteHeader _SiteHeader = m_ds.getById(cid);

            if (_SiteHeader == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
			//Default error page but will be overridden by exception specific error page
            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_SiteHeader.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteHeader.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }


            try{
                editField(request, response, _SiteHeader);
                if (returnObjects != null) returnObjects.put("target", _SiteHeader);
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

            //setPage(session, getAfterEditFieldPage());
            setPage(session, null, getAfterEditFieldPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_editfield_forward_page" ) );
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //SiteHeader _SiteHeader = m_ds.getById(cid);

            if (_SiteHeader == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
				//Default error page but will be overridden by exception specific error page
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_SiteHeader.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteHeader.getSiteId()); 
	            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _SiteHeader);
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

            m_ds.delete(_SiteHeader); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _SiteHeader);
			setPageForward(session, getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _SiteHeader);
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


            m_logger.info("Creating new SiteHeader" );
            SiteHeader _SiteHeaderNew = new SiteHeader();   

            // Setting IDs for the object
            _SiteHeaderNew.setSiteId(site.getId());

            _SiteHeaderNew.setAsIs(WebParamUtil.getIntValue(_SiteHeaderForm.getAsIs()));
            m_logger.debug("setting AsIs=" +_SiteHeaderForm.getAsIs());
            _SiteHeaderNew.setIncludeType(WebParamUtil.getIntValue(_SiteHeaderForm.getIncludeType()));
            m_logger.debug("setting IncludeType=" +_SiteHeaderForm.getIncludeType());
            _SiteHeaderNew.setIncludeText(WebParamUtil.getStringValue(_SiteHeaderForm.getIncludeText()));
            m_logger.debug("setting IncludeText=" +_SiteHeaderForm.getIncludeText());
            _SiteHeaderNew.setTimeCreated(WebParamUtil.getTimestampValue(_SiteHeaderForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SiteHeaderForm.getTimeCreated());
            _SiteHeaderNew.setTimeUpdated(WebParamUtil.getTimestampValue(_SiteHeaderForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SiteHeaderForm.getTimeUpdated());


            try {
                checkDepedenceIntegrity(_SiteHeaderNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _SiteHeaderNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_SiteHeaderNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_SiteHeaderNew);
            if (returnObjects != null) returnObjects.put("target", _SiteHeader);

            //setPageForward(request, getActionExtent().getAfterAddPage());
            setPage(session, null, getActionExtent().getAfterAddPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_add_forward_page" ) );

            try{
                m_actionExtent.afterAdd(request, response, _SiteHeaderNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "site_header_home");


        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            setPage(session, getErrorPage());
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteHeaderForm _SiteHeaderForm, SiteHeader _SiteHeader) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SiteHeader _SiteHeader = m_ds.getById(cid);

        m_logger.debug("Before update " + SiteHeaderDS.objectToString(_SiteHeader));

        _SiteHeader.setAsIs(WebParamUtil.getIntValue(_SiteHeaderForm.getAsIs()));
        _SiteHeader.setIncludeType(WebParamUtil.getIntValue(_SiteHeaderForm.getIncludeType()));
        _SiteHeader.setIncludeText(WebParamUtil.getStringValue(_SiteHeaderForm.getIncludeText()));
        _SiteHeader.setTimeUpdated(WebParamUtil.getTimestampValue(_SiteHeaderForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _SiteHeader);
        m_ds.update(_SiteHeader);
        m_actionExtent.afterUpdate(request, response, _SiteHeader);
        m_logger.debug("After update " + SiteHeaderDS.objectToString(_SiteHeader));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, SiteHeader _SiteHeader) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SiteHeader _SiteHeader = m_ds.getById(cid);

        if (!isMissing(request.getParameter("asIs"))) {
            m_logger.debug("updating param asIs from " +_SiteHeader.getAsIs() + "->" + request.getParameter("asIs"));
            _SiteHeader.setAsIs(WebParamUtil.getIntValue(request.getParameter("asIs")));

        }
        if (!isMissing(request.getParameter("includeType"))) {
            m_logger.debug("updating param includeType from " +_SiteHeader.getIncludeType() + "->" + request.getParameter("includeType"));
            _SiteHeader.setIncludeType(WebParamUtil.getIntValue(request.getParameter("includeType")));

        }
        if (!isMissing(request.getParameter("includeText"))) {
            m_logger.debug("updating param includeText from " +_SiteHeader.getIncludeText() + "->" + request.getParameter("includeText"));
            _SiteHeader.setIncludeText(WebParamUtil.getStringValue(request.getParameter("includeText")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SiteHeader.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SiteHeader.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SiteHeader.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SiteHeader.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _SiteHeader.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _SiteHeader);
        m_ds.update(_SiteHeader);
        m_actionExtent.afterUpdate(request, response, _SiteHeader);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, SiteHeader _SiteHeader) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SiteHeader _SiteHeader = m_ds.getById(cid);

        if (!isMissing(request.getParameter("asIs"))) {
			return String.valueOf(_SiteHeader.getAsIs());
        }
        if (!isMissing(request.getParameter("includeType"))) {
			return String.valueOf(_SiteHeader.getIncludeType());
        }
        if (!isMissing(request.getParameter("includeText"))) {
			return String.valueOf(_SiteHeader.getIncludeText());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_SiteHeader.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_SiteHeader.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(SiteHeader _SiteHeader) throws Exception {
    }

    protected String getFieldByName(String fieldName, SiteHeader _SiteHeader) {
        if (fieldName == null || fieldName.equals("")|| _SiteHeader == null) return null;
        
        if (fieldName.equals("asIs")) {
            return WebUtil.display(_SiteHeader.getAsIs());
        }
        if (fieldName.equals("includeType")) {
            return WebUtil.display(_SiteHeader.getIncludeType());
        }
        if (fieldName.equals("includeText")) {
            return WebUtil.display(_SiteHeader.getIncludeText());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_SiteHeader.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_SiteHeader.getTimeUpdated());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        SiteHeader target = null;
        
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
                target = (SiteHeader) working.get("target");
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
            SiteHeader _SiteHeader = SiteHeaderDS.getInstance().getById(id);
            if (_SiteHeader == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _SiteHeader);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            SiteHeader _SiteHeader = SiteHeaderDS.getInstance().getById(id);
            if ( _SiteHeader == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _SiteHeader);
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

            buf.append("<div id=\"siteHeader-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                SiteHeader _SiteHeader = (SiteHeader) iterator.next();

                buf.append("<div id=\"siteHeader-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("asIs")) {
                    buf.append("<div id=\"siteHeader-ajax-asIs\" "+itemClass+">");
                    buf.append(WebUtil.display(_SiteHeader.getAsIs()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("includeType")) {
                    buf.append("<div id=\"siteHeader-ajax-includeType\" "+itemClass+">");
                    buf.append(WebUtil.display(_SiteHeader.getIncludeType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("includeText")) {
                    buf.append("<div id=\"siteHeader-ajax-includeText\" "+itemClass+">");
                    buf.append(WebUtil.display(_SiteHeader.getIncludeText()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"siteHeader-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_SiteHeader.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"siteHeader-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_SiteHeader.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("asIs")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("As Is");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("includeType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Include Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("includeText")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Include Text");
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
                SiteHeader _SiteHeader = (SiteHeader) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("asIs")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_SiteHeader.getAsIs()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteHeaderAction.html?ef=true&id="+ _SiteHeader.getId()+"&asIs=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteHeaderAction.html?ef=true&id="+ _SiteHeader.getId()+"&asIs=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("includeType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SiteHeader.getIncludeType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("includeText")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SiteHeader.getIncludeText()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SiteHeader.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SiteHeader.getTimeUpdated()));

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
	                SiteHeader _SiteHeader = (SiteHeader) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("asIs")) 
			            json.put("asIs", ""+_SiteHeader.getAsIs());
		            if ( ignoreFieldSet || fieldSet.contains("includeType")) 
			            json.put("includeType", ""+_SiteHeader.getIncludeType());
		            if ( ignoreFieldSet || fieldSet.contains("includeText")) 
			            json.put("includeText", ""+_SiteHeader.getIncludeText());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                SiteHeader _SiteHeader = list.size() >=1?(SiteHeader) list.get(0): null; 

				if ( _SiteHeader != null) {
		            top.put("id", ""+_SiteHeader.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonAsIs = new JSONObject();
		            jsonAsIs.put("name", "asIs");
		            jsonAsIs.put("value", ""+_SiteHeader.getAsIs());
		            array.put(jsonAsIs);
		            JSONObject jsonIncludeType = new JSONObject();
		            jsonIncludeType.put("name", "includeType");
		            jsonIncludeType.put("value", ""+_SiteHeader.getIncludeType());
		            array.put(jsonIncludeType);
		            JSONObject jsonIncludeText = new JSONObject();
		            jsonIncludeText.put("name", "includeText");
		            jsonIncludeText.put("value", ""+_SiteHeader.getIncludeText());
		            array.put(jsonIncludeText);

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
            buf.append("sendFormAjax('/siteHeaderAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/siteHeaderAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("asIs")) {
                String value = WebUtil.display(request.getParameter("asIs"));

                if ( forceHiddenSet.contains("asIs")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"asIs\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">As Is</div>");
            buf.append("<select name=\"asIs\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("includeType")) {
                String value = WebUtil.display(request.getParameter("includeType"));

                if ( forceHiddenSet.contains("includeType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"includeType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Include Type</div>");
            buf.append("<select id=\"requiredField\" name=\"includeType\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

        	buf.append("<option value=\"0\" >Default</option>");
        	buf.append("<option value=\"1\" >ScriptLink</option>");
        	buf.append("<option value=\"2\" >ScriptText</option>");
        	buf.append("<option value=\"3\" >StyleLink</option>");
        	buf.append("<option value=\"4\" >StyleText</option>");

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("includeText")) {
                String value = WebUtil.display(request.getParameter("includeText"));

                if ( forceHiddenSet.contains("includeText")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"includeText\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Include Text</div>");
            buf.append("<TEXTAREA NAME=\"includeText\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                String value = WebUtil.display(request.getParameter("timeCreated"));

                if ( forceHiddenSet.contains("timeCreated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCreated\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                String value = WebUtil.display(request.getParameter("timeUpdated"));

                if ( forceHiddenSet.contains("timeUpdated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeUpdated\"  value=\""+value+"\">");
                } else {

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
            importedScripts += "function responseCallback_siteHeader(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplaySiteHeader\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_siteHeader(){\n";
            importedScripts +=     "xmlhttpPostXX('siteHeaderFormAddDis','/siteHeaderAction.html', 'resultDisplaySiteHeader', '${ajax_response_fields}', responseCallback_siteHeader);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_siteHeader(){\n";
            importedScripts +=     "clearFormXX('siteHeaderFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_siteHeader(){\n";
            importedScripts +=     "backToXX('siteHeaderFormAddDis','resultDisplaySiteHeader');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"siteHeaderFormAddDis\" method=\"POST\" action=\"/siteHeaderAction.html\" id=\"siteHeaderFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> As Is</div>");
        buf.append("<select name=\"asIs\" id=\"asIs\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Include Type</div>");
        buf.append("<select class=\"field\" name=\"includeType\" id=\"includeType\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<!--option value=\"XX\" >XX</option-->");
        buf.append("<option value=\"0\" >Default</option>");
        buf.append("<option value=\"1\" >ScriptLink</option>");
        buf.append("<option value=\"2\" >ScriptText</option>");
        buf.append("<option value=\"3\" >StyleLink</option>");
        buf.append("<option value=\"4\" >StyleText</option>");
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Include Text</div>");
		buf.append("<TEXTAREA id=\"includeText\" class=\"field\" NAME=\"includeText\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_siteHeader()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_siteHeader()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplaySiteHeader\"></span>");
			buf.append("<a href=\"javascript:showform_siteHeader()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, SiteHeader target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            SiteHeader _SiteHeader = null; 
            List list = SiteHeaderDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _SiteHeader = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _SiteHeader = (SiteHeader) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _SiteHeader = (SiteHeader) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _SiteHeader = SiteHeaderDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_SiteHeader);

        } else {
            
            List list = SiteHeaderDS.getInstance().getBySiteId(site.getId());
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

    public String getActionGroupName(){ return "SiteHeader";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        if (true) return AccessDef.SystemRole.Super;
        return AccessDef.SystemRole.User;
    }
    
    protected SiteHeaderDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SiteHeaderAction.class);





}
