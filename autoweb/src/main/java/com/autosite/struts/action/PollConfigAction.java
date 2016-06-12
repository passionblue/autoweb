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

import com.autosite.db.PollConfig;
import com.autosite.ds.PollConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.PollConfigForm;
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




public class PollConfigAction extends AutositeCoreAction {

    public PollConfigAction(){
        m_ds = PollConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        PollConfigForm _PollConfigForm = (PollConfigForm) form;
        HttpSession session = request.getSession();

        setPage(session, "poll_config_home");

        Site site = getSite(request);
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
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
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        PollConfig _PollConfig = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true") && isThere("id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PollConfig = m_ds.getById(cid);
		}



        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollConfig _PollConfig = m_ds.getById(cid);

            if (_PollConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollConfig.getSiteId()); 
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_PollConfig);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PollConfigForm, _PollConfig);
                if (returnObjects != null) returnObjects.put("target", _PollConfig);
				//setPageForward(session, getAfterEditPage());
				setPage(session, null, getAfterEditPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_edit_forward_page" ) );

            }

            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollConfig _PollConfig = m_ds.getById(cid);

            if (_PollConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollConfig.getSiteId()); 
                return mapping.findForward("default");
            }


            try{
                editField(request, response, _PollConfig);
                if (returnObjects != null) returnObjects.put("target", _PollConfig);
				setPage(session, getAfterEditFieldPage());
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, getAfterEditFieldPage());
            setPage(session, null, getAfterEditFieldPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_editfield_forward_page" ) );
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollConfig _PollConfig = m_ds.getById(cid);

            if (_PollConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollConfig.getSiteId()); 
                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _PollConfig);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            m_ds.delete(_PollConfig); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _PollConfig);
			setPageForward(session, getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _PollConfig);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new PollConfig" );
            PollConfig _PollConfigNew = new PollConfig();   

            // Setting IDs for the object
            _PollConfigNew.setSiteId(site.getId());

            _PollConfigNew.setPollId(WebParamUtil.getLongValue(_PollConfigForm.getPollId()));
            m_logger.debug("setting PollId=" +_PollConfigForm.getPollId());
            _PollConfigNew.setImageThumbHeight(WebParamUtil.getIntValue(_PollConfigForm.getImageThumbHeight()));
            m_logger.debug("setting ImageThumbHeight=" +_PollConfigForm.getImageThumbHeight());
            _PollConfigNew.setImageThumbWidth(WebParamUtil.getIntValue(_PollConfigForm.getImageThumbWidth()));
            m_logger.debug("setting ImageThumbWidth=" +_PollConfigForm.getImageThumbWidth());
            _PollConfigNew.setImageAlignVertical(WebParamUtil.getIntValue(_PollConfigForm.getImageAlignVertical()));
            m_logger.debug("setting ImageAlignVertical=" +_PollConfigForm.getImageAlignVertical());
            _PollConfigNew.setBackground(WebParamUtil.getStringValue(_PollConfigForm.getBackground()));
            m_logger.debug("setting Background=" +_PollConfigForm.getBackground());
            _PollConfigNew.setTimeCreated(WebParamUtil.getTimestampValue(_PollConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PollConfigForm.getTimeCreated());
            _PollConfigNew.setTimeUpdated(WebParamUtil.getTimestampValue(_PollConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PollConfigForm.getTimeUpdated());


            try {
                checkDepedenceIntegrity(_PollConfigNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _PollConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PollConfigNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PollConfigNew);
            if (returnObjects != null) returnObjects.put("target", _PollConfig);

            //setPageForward(request, getActionExtent().getAfterAddPage());
            setPage(session, null, getActionExtent().getAfterAddPage(), false,  DefaultPageForwardManager.getInstance().isPageForwardTo(getActionName(),"action_normal_add_forward_page" ) );

            try{
                m_actionExtent.afterAdd(request, response, _PollConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "poll_config_home");


        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PollConfigForm _PollConfigForm, PollConfig _PollConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollConfig _PollConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + PollConfigDS.objectToString(_PollConfig));

        _PollConfig.setPollId(WebParamUtil.getLongValue(_PollConfigForm.getPollId()));
        _PollConfig.setImageThumbHeight(WebParamUtil.getIntValue(_PollConfigForm.getImageThumbHeight()));
        _PollConfig.setImageThumbWidth(WebParamUtil.getIntValue(_PollConfigForm.getImageThumbWidth()));
        _PollConfig.setImageAlignVertical(WebParamUtil.getIntValue(_PollConfigForm.getImageAlignVertical()));
        _PollConfig.setBackground(WebParamUtil.getStringValue(_PollConfigForm.getBackground()));
        _PollConfig.setTimeUpdated(WebParamUtil.getTimestampValue(_PollConfigForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _PollConfig);
        m_ds.update(_PollConfig);
        m_actionExtent.afterUpdate(request, response, _PollConfig);
        m_logger.debug("After update " + PollConfigDS.objectToString(_PollConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PollConfig _PollConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollConfig _PollConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pollId"))) {
            m_logger.debug("updating param pollId from " +_PollConfig.getPollId() + "->" + request.getParameter("pollId"));
            _PollConfig.setPollId(WebParamUtil.getLongValue(request.getParameter("pollId")));

        }
        if (!isMissing(request.getParameter("imageThumbHeight"))) {
            m_logger.debug("updating param imageThumbHeight from " +_PollConfig.getImageThumbHeight() + "->" + request.getParameter("imageThumbHeight"));
            _PollConfig.setImageThumbHeight(WebParamUtil.getIntValue(request.getParameter("imageThumbHeight")));

        }
        if (!isMissing(request.getParameter("imageThumbWidth"))) {
            m_logger.debug("updating param imageThumbWidth from " +_PollConfig.getImageThumbWidth() + "->" + request.getParameter("imageThumbWidth"));
            _PollConfig.setImageThumbWidth(WebParamUtil.getIntValue(request.getParameter("imageThumbWidth")));

        }
        if (!isMissing(request.getParameter("imageAlignVertical"))) {
            m_logger.debug("updating param imageAlignVertical from " +_PollConfig.getImageAlignVertical() + "->" + request.getParameter("imageAlignVertical"));
            _PollConfig.setImageAlignVertical(WebParamUtil.getIntValue(request.getParameter("imageAlignVertical")));

        }
        if (!isMissing(request.getParameter("background"))) {
            m_logger.debug("updating param background from " +_PollConfig.getBackground() + "->" + request.getParameter("background"));
            _PollConfig.setBackground(WebParamUtil.getStringValue(request.getParameter("background")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PollConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PollConfig.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_PollConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _PollConfig.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        }

        m_actionExtent.beforeUpdate(request, response, _PollConfig);
        m_ds.update(_PollConfig);
        m_actionExtent.afterUpdate(request, response, _PollConfig);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, PollConfig _PollConfig) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollConfig _PollConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pollId"))) {
			return String.valueOf(_PollConfig.getPollId());
        }
        if (!isMissing(request.getParameter("imageThumbHeight"))) {
			return String.valueOf(_PollConfig.getImageThumbHeight());
        }
        if (!isMissing(request.getParameter("imageThumbWidth"))) {
			return String.valueOf(_PollConfig.getImageThumbWidth());
        }
        if (!isMissing(request.getParameter("imageAlignVertical"))) {
			return String.valueOf(_PollConfig.getImageAlignVertical());
        }
        if (!isMissing(request.getParameter("background"))) {
			return String.valueOf(_PollConfig.getBackground());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_PollConfig.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_PollConfig.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(PollConfig _PollConfig) throws Exception {
    }

    protected String getFieldByName(String fieldName, PollConfig _PollConfig) {
        if (fieldName == null || fieldName.equals("")|| _PollConfig == null) return null;
        
        if (fieldName.equals("pollId")) {
            return WebUtil.display(_PollConfig.getPollId());
        }
        if (fieldName.equals("imageThumbHeight")) {
            return WebUtil.display(_PollConfig.getImageThumbHeight());
        }
        if (fieldName.equals("imageThumbWidth")) {
            return WebUtil.display(_PollConfig.getImageThumbWidth());
        }
        if (fieldName.equals("imageAlignVertical")) {
            return WebUtil.display(_PollConfig.getImageAlignVertical());
        }
        if (fieldName.equals("background")) {
            return WebUtil.display(_PollConfig.getBackground());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_PollConfig.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_PollConfig.getTimeUpdated());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        PollConfig target = null;
        
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
                target = (PollConfig) working.get("target");
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
            PollConfig _PollConfig = PollConfigDS.getInstance().getById(id);
            if (_PollConfig == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _PollConfig);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            PollConfig _PollConfig = PollConfigDS.getInstance().getById(id);
            if ( _PollConfig == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _PollConfig);
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

            buf.append("<div id=\"pollConfig-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                PollConfig _PollConfig = (PollConfig) iterator.next();

                buf.append("<div id=\"pollConfig-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("imageThumbHeight")) {
                    buf.append("<div id=\"pollConfig-ajax-imageThumbHeight\" "+itemClass+">");
                    buf.append(WebUtil.display(_PollConfig.getImageThumbHeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("imageThumbWidth")) {
                    buf.append("<div id=\"pollConfig-ajax-imageThumbWidth\" "+itemClass+">");
                    buf.append(WebUtil.display(_PollConfig.getImageThumbWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("imageAlignVertical")) {
                    buf.append("<div id=\"pollConfig-ajax-imageAlignVertical\" "+itemClass+">");
                    buf.append(WebUtil.display(_PollConfig.getImageAlignVertical()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("background")) {
                    buf.append("<div id=\"pollConfig-ajax-background\" "+itemClass+">");
                    buf.append(WebUtil.display(_PollConfig.getBackground()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"pollConfig-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_PollConfig.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"pollConfig-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_PollConfig.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("imageThumbHeight")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Thumb Height");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("imageThumbWidth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Thumb Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("imageAlignVertical")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Align Vertical");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("background")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Background");
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
                PollConfig _PollConfig = (PollConfig) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("imageThumbHeight")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollConfig.getImageThumbHeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("imageThumbWidth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollConfig.getImageThumbWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("imageAlignVertical")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_PollConfig.getImageAlignVertical()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollConfigAction.html?ef=true&id="+ _PollConfig.getId()+"&imageAlignVertical=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollConfigAction.html?ef=true&id="+ _PollConfig.getId()+"&imageAlignVertical=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("background")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollConfig.getBackground()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollConfig.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollConfig.getTimeUpdated()));

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
	                PollConfig _PollConfig = (PollConfig) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("pollId")) 
			            json.put("pollId", ""+_PollConfig.getPollId());
		            if ( ignoreFieldSet || fieldSet.contains("imageThumbHeight")) 
			            json.put("imageThumbHeight", ""+_PollConfig.getImageThumbHeight());
		            if ( ignoreFieldSet || fieldSet.contains("imageThumbWidth")) 
			            json.put("imageThumbWidth", ""+_PollConfig.getImageThumbWidth());
		            if ( ignoreFieldSet || fieldSet.contains("imageAlignVertical")) 
			            json.put("imageAlignVertical", ""+_PollConfig.getImageAlignVertical());
		            if ( ignoreFieldSet || fieldSet.contains("background")) 
			            json.put("background", ""+_PollConfig.getBackground());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                PollConfig _PollConfig = list.size() >=1?(PollConfig) list.get(0): null; 

				if ( _PollConfig != null) {
		            top.put("id", ""+_PollConfig.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonPollId = new JSONObject();
		            jsonPollId.put("name", "pollId");
		            jsonPollId.put("value", ""+_PollConfig.getPollId());
		            array.put(jsonPollId);
		            JSONObject jsonImageThumbHeight = new JSONObject();
		            jsonImageThumbHeight.put("name", "imageThumbHeight");
		            jsonImageThumbHeight.put("value", ""+_PollConfig.getImageThumbHeight());
		            array.put(jsonImageThumbHeight);
		            JSONObject jsonImageThumbWidth = new JSONObject();
		            jsonImageThumbWidth.put("name", "imageThumbWidth");
		            jsonImageThumbWidth.put("value", ""+_PollConfig.getImageThumbWidth());
		            array.put(jsonImageThumbWidth);
		            JSONObject jsonImageAlignVertical = new JSONObject();
		            jsonImageAlignVertical.put("name", "imageAlignVertical");
		            jsonImageAlignVertical.put("value", ""+_PollConfig.getImageAlignVertical());
		            array.put(jsonImageAlignVertical);
		            JSONObject jsonBackground = new JSONObject();
		            jsonBackground.put("name", "background");
		            jsonBackground.put("value", ""+_PollConfig.getBackground());
		            array.put(jsonBackground);

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
            buf.append("sendFormAjax('/pollConfigAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/pollConfigAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("pollId")) {
                String value = WebUtil.display(request.getParameter("pollId"));

                if ( forceHiddenSet.contains("pollId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pollId\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pollId\"  value=\""+value+"\">");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("imageThumbHeight")) {
                String value = WebUtil.display(request.getParameter("imageThumbHeight"));

                if ( forceHiddenSet.contains("imageThumbHeight")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imageThumbHeight\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Thumb Height</div>");
            buf.append("<INPUT NAME=\"imageThumbHeight\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("imageThumbWidth")) {
                String value = WebUtil.display(request.getParameter("imageThumbWidth"));

                if ( forceHiddenSet.contains("imageThumbWidth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imageThumbWidth\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Thumb Width</div>");
            buf.append("<INPUT NAME=\"imageThumbWidth\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("imageAlignVertical")) {
                String value = WebUtil.display(request.getParameter("imageAlignVertical"));

                if ( forceHiddenSet.contains("imageAlignVertical")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imageAlignVertical\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Align Vertical</div>");
            buf.append("<select name=\"imageAlignVertical\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("background")) {
                String value = WebUtil.display(request.getParameter("background"));

                if ( forceHiddenSet.contains("background")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"background\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Background</div>");
            buf.append("<INPUT NAME=\"background\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_pollConfig(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayPollConfig\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_pollConfig(){\n";
            importedScripts +=     "xmlhttpPostXX('pollConfigFormAddDis','/pollConfigAction.html', 'resultDisplayPollConfig', '${ajax_response_fields}', responseCallback_pollConfig);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_pollConfig(){\n";
            importedScripts +=     "clearFormXX('pollConfigFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_pollConfig(){\n";
            importedScripts +=     "backToXX('pollConfigFormAddDis','resultDisplayPollConfig');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"pollConfigFormAddDis\" method=\"POST\" action=\"/pollConfigAction.html\" id=\"pollConfigFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"pollId\" NAME=\"pollId\" value=\"\" />");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Thumb Height</div>");
        buf.append("<input class=\"field\" id=\"imageThumbHeight\" type=\"text\" size=\"70\" name=\"imageThumbHeight\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Thumb Width</div>");
        buf.append("<input class=\"field\" id=\"imageThumbWidth\" type=\"text\" size=\"70\" name=\"imageThumbWidth\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Align Vertical</div>");
        buf.append("<select name=\"imageAlignVertical\" id=\"imageAlignVertical\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Background</div>");
        buf.append("<input class=\"field\" id=\"background\" type=\"text\" size=\"70\" name=\"background\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_pollConfig()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_pollConfig()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayPollConfig\"></span>");
			buf.append("<a href=\"javascript:showform_pollConfig()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, PollConfig target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            PollConfig _PollConfig = null; 
            List list = PollConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _PollConfig = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _PollConfig = (PollConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _PollConfig = (PollConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _PollConfig = PollConfigDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_PollConfig);

        } else {
            
            List list = PollConfigDS.getInstance().getBySiteId(site.getId());
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

    public String getActionGroupName(){ return "$action_group_name";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
    protected PollConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PollConfigAction.class);





}
