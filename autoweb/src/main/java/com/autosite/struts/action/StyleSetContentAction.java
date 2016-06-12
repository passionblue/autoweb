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
					
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.StyleSetContent;
import com.autosite.ds.StyleSetContentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;

import com.autosite.struts.form.StyleSetContentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check

import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;



public class StyleSetContentAction extends AutositeCoreAction {

    public StyleSetContentAction(){
        m_ds = StyleSetContentDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        StyleSetContentForm _StyleSetContentForm = (StyleSetContentForm) form;
        HttpSession session = request.getSession();

        setPage(session, "style_set_content_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        
        if (!accessTestOkay ){
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


        StyleSetContent _StyleSetContent = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _StyleSetContent = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //StyleSetContent _StyleSetContent = m_ds.getById(cid);

            if (_StyleSetContent == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleSetContent.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleSetContent.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_StyleSetContent);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _StyleSetContentForm, _StyleSetContent);
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
            //StyleSetContent _StyleSetContent = m_ds.getById(cid);

            if (_StyleSetContent == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleSetContent.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleSetContent.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _StyleSetContent);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //StyleSetContent _StyleSetContent = m_ds.getById(cid);

            if (_StyleSetContent == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleSetContent.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleSetContent.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _StyleSetContent);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_StyleSetContent);
            try { 
                m_actionExtent.afterDelete(request, response, _StyleSetContent);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
	            if (throwException) throw e;
                return mapping.findForward("default");
            }

            m_logger.info("Creating new StyleSetContent" );
            StyleSetContent _StyleSetContentNew = new StyleSetContent();   

            // Setting IDs for the object
            _StyleSetContentNew.setSiteId(site.getId());

            _StyleSetContentNew.setName(WebParamUtil.getStringValue(_StyleSetContentForm.getName()));
            m_logger.debug("setting Name=" +_StyleSetContentForm.getName());
            _StyleSetContentNew.setIdPrefix(WebParamUtil.getStringValue(_StyleSetContentForm.getIdPrefix()));
            m_logger.debug("setting IdPrefix=" +_StyleSetContentForm.getIdPrefix());
            _StyleSetContentNew.setListFrameStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getListFrameStyleId()));
            m_logger.debug("setting ListFrameStyleId=" +_StyleSetContentForm.getListFrameStyleId());
            _StyleSetContentNew.setListSubjectStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getListSubjectStyleId()));
            m_logger.debug("setting ListSubjectStyleId=" +_StyleSetContentForm.getListSubjectStyleId());
            _StyleSetContentNew.setListDataStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getListDataStyleId()));
            m_logger.debug("setting ListDataStyleId=" +_StyleSetContentForm.getListDataStyleId());
            _StyleSetContentNew.setFrameStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getFrameStyleId()));
            m_logger.debug("setting FrameStyleId=" +_StyleSetContentForm.getFrameStyleId());
            _StyleSetContentNew.setSubjectStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getSubjectStyleId()));
            m_logger.debug("setting SubjectStyleId=" +_StyleSetContentForm.getSubjectStyleId());
            _StyleSetContentNew.setDataStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getDataStyleId()));
            m_logger.debug("setting DataStyleId=" +_StyleSetContentForm.getDataStyleId());
            _StyleSetContentNew.setTimeCreated(WebParamUtil.getDateValue(_StyleSetContentForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_StyleSetContentForm.getTimeCreated());
            _StyleSetContentNew.setTimeUpdated(WebParamUtil.getDateValue(_StyleSetContentForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_StyleSetContentForm.getTimeUpdated());

            try {
                checkDepedenceIntegrity(_StyleSetContentNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _StyleSetContentNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_StyleSetContentNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_StyleSetContentNew);
            try{
                m_actionExtent.afterAdd(request, response, _StyleSetContentNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "style_set_content_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, StyleSetContentForm _StyleSetContentForm, StyleSetContent _StyleSetContent) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleSetContent _StyleSetContent = m_ds.getById(cid);

        m_logger.debug("Before update " + StyleSetContentDS.objectToString(_StyleSetContent));

        _StyleSetContent.setName(WebParamUtil.getStringValue(_StyleSetContentForm.getName()));
        _StyleSetContent.setIdPrefix(WebParamUtil.getStringValue(_StyleSetContentForm.getIdPrefix()));
        _StyleSetContent.setListFrameStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getListFrameStyleId()));
        _StyleSetContent.setListSubjectStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getListSubjectStyleId()));
        _StyleSetContent.setListDataStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getListDataStyleId()));
        _StyleSetContent.setFrameStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getFrameStyleId()));
        _StyleSetContent.setSubjectStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getSubjectStyleId()));
        _StyleSetContent.setDataStyleId(WebParamUtil.getLongValue(_StyleSetContentForm.getDataStyleId()));
        _StyleSetContent.setTimeUpdated(WebParamUtil.getDateValue(_StyleSetContentForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _StyleSetContent);
        m_ds.update(_StyleSetContent);
        m_actionExtent.afterUpdate(request, response, _StyleSetContent);
        m_logger.debug("After update " + StyleSetContentDS.objectToString(_StyleSetContent));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, StyleSetContent _StyleSetContent) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleSetContent _StyleSetContent = m_ds.getById(cid);

        if (!isMissing(request.getParameter("name"))) {
            m_logger.debug("updating param name from " +_StyleSetContent.getName() + "->" + request.getParameter("name"));
            _StyleSetContent.setName(WebParamUtil.getStringValue(request.getParameter("name")));
        }
        if (!isMissing(request.getParameter("idPrefix"))) {
            m_logger.debug("updating param idPrefix from " +_StyleSetContent.getIdPrefix() + "->" + request.getParameter("idPrefix"));
            _StyleSetContent.setIdPrefix(WebParamUtil.getStringValue(request.getParameter("idPrefix")));
        }
        if (!isMissing(request.getParameter("listFrameStyleId"))) {
            m_logger.debug("updating param listFrameStyleId from " +_StyleSetContent.getListFrameStyleId() + "->" + request.getParameter("listFrameStyleId"));
            _StyleSetContent.setListFrameStyleId(WebParamUtil.getLongValue(request.getParameter("listFrameStyleId")));
        }
        if (!isMissing(request.getParameter("listSubjectStyleId"))) {
            m_logger.debug("updating param listSubjectStyleId from " +_StyleSetContent.getListSubjectStyleId() + "->" + request.getParameter("listSubjectStyleId"));
            _StyleSetContent.setListSubjectStyleId(WebParamUtil.getLongValue(request.getParameter("listSubjectStyleId")));
        }
        if (!isMissing(request.getParameter("listDataStyleId"))) {
            m_logger.debug("updating param listDataStyleId from " +_StyleSetContent.getListDataStyleId() + "->" + request.getParameter("listDataStyleId"));
            _StyleSetContent.setListDataStyleId(WebParamUtil.getLongValue(request.getParameter("listDataStyleId")));
        }
        if (!isMissing(request.getParameter("frameStyleId"))) {
            m_logger.debug("updating param frameStyleId from " +_StyleSetContent.getFrameStyleId() + "->" + request.getParameter("frameStyleId"));
            _StyleSetContent.setFrameStyleId(WebParamUtil.getLongValue(request.getParameter("frameStyleId")));
        }
        if (!isMissing(request.getParameter("subjectStyleId"))) {
            m_logger.debug("updating param subjectStyleId from " +_StyleSetContent.getSubjectStyleId() + "->" + request.getParameter("subjectStyleId"));
            _StyleSetContent.setSubjectStyleId(WebParamUtil.getLongValue(request.getParameter("subjectStyleId")));
        }
        if (!isMissing(request.getParameter("dataStyleId"))) {
            m_logger.debug("updating param dataStyleId from " +_StyleSetContent.getDataStyleId() + "->" + request.getParameter("dataStyleId"));
            _StyleSetContent.setDataStyleId(WebParamUtil.getLongValue(request.getParameter("dataStyleId")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_StyleSetContent.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _StyleSetContent.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_StyleSetContent.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _StyleSetContent.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _StyleSetContent.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _StyleSetContent);
        m_ds.update(_StyleSetContent);
        m_actionExtent.afterUpdate(request, response, _StyleSetContent);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, StyleSetContent _StyleSetContent) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleSetContent _StyleSetContent = m_ds.getById(cid);

        if (!isMissing(request.getParameter("name"))) {
			return String.valueOf(_StyleSetContent.getName());
        }
        if (!isMissing(request.getParameter("idPrefix"))) {
			return String.valueOf(_StyleSetContent.getIdPrefix());
        }
        if (!isMissing(request.getParameter("listFrameStyleId"))) {
			return String.valueOf(_StyleSetContent.getListFrameStyleId());
        }
        if (!isMissing(request.getParameter("listSubjectStyleId"))) {
			return String.valueOf(_StyleSetContent.getListSubjectStyleId());
        }
        if (!isMissing(request.getParameter("listDataStyleId"))) {
			return String.valueOf(_StyleSetContent.getListDataStyleId());
        }
        if (!isMissing(request.getParameter("frameStyleId"))) {
			return String.valueOf(_StyleSetContent.getFrameStyleId());
        }
        if (!isMissing(request.getParameter("subjectStyleId"))) {
			return String.valueOf(_StyleSetContent.getSubjectStyleId());
        }
        if (!isMissing(request.getParameter("dataStyleId"))) {
			return String.valueOf(_StyleSetContent.getDataStyleId());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_StyleSetContent.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_StyleSetContent.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(StyleSetContent _StyleSetContent) throws Exception {
    }

    protected String getFieldByName(String fieldName, StyleSetContent _StyleSetContent) {
        if (fieldName == null || fieldName.equals("")|| _StyleSetContent == null) return null;
        
        if (fieldName.equals("name")) {
            return WebUtil.display(_StyleSetContent.getName());
        }
        if (fieldName.equals("idPrefix")) {
            return WebUtil.display(_StyleSetContent.getIdPrefix());
        }
        if (fieldName.equals("listFrameStyleId")) {
            return WebUtil.display(_StyleSetContent.getListFrameStyleId());
        }
        if (fieldName.equals("listSubjectStyleId")) {
            return WebUtil.display(_StyleSetContent.getListSubjectStyleId());
        }
        if (fieldName.equals("listDataStyleId")) {
            return WebUtil.display(_StyleSetContent.getListDataStyleId());
        }
        if (fieldName.equals("frameStyleId")) {
            return WebUtil.display(_StyleSetContent.getFrameStyleId());
        }
        if (fieldName.equals("subjectStyleId")) {
            return WebUtil.display(_StyleSetContent.getSubjectStyleId());
        }
        if (fieldName.equals("dataStyleId")) {
            return WebUtil.display(_StyleSetContent.getDataStyleId());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_StyleSetContent.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_StyleSetContent.getTimeUpdated());
        }
		return null;
    }



    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        
        //         // Request Processing 
        // 
        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                ex(mapping, form, request, response, true);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing gethtml getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            StyleSetContent _StyleSetContent = StyleSetContentDS.getInstance().getById(id);
            if (_StyleSetContent == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _StyleSetContent);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            StyleSetContent _StyleSetContent = StyleSetContentDS.getInstance().getById(id);
            if ( _StyleSetContent == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _StyleSetContent);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));

			// Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuffer buf = new StringBuffer();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/styleSetContentAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("name")) {
                String value = WebUtil.display(request.getParameter("name"));

                if ( forceHiddenSet.contains("name")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"name\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Name</div>");
            buf.append("<INPUT NAME=\"name\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("idPrefix")) {
                String value = WebUtil.display(request.getParameter("idPrefix"));

                if ( forceHiddenSet.contains("idPrefix")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"idPrefix\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Id Prefix</div>");
            buf.append("<INPUT NAME=\"idPrefix\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
                String value = WebUtil.display(request.getParameter("listFrameStyleId"));

                if ( forceHiddenSet.contains("listFrameStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listFrameStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Frame Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"listFrameStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleSetContentList Frame Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
                String value = WebUtil.display(request.getParameter("listSubjectStyleId"));

                if ( forceHiddenSet.contains("listSubjectStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listSubjectStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Subject Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"listSubjectStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleSetContentList Subject Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
                String value = WebUtil.display(request.getParameter("listDataStyleId"));

                if ( forceHiddenSet.contains("listDataStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listDataStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Data Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"listDataStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleSetContentList Data Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("frameStyleId")) {
                String value = WebUtil.display(request.getParameter("frameStyleId"));

                if ( forceHiddenSet.contains("frameStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"frameStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Frame Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"frameStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleSetContentFrame Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
                String value = WebUtil.display(request.getParameter("subjectStyleId"));

                if ( forceHiddenSet.contains("subjectStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subjectStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subject Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"subjectStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleSetContentSubject Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
                String value = WebUtil.display(request.getParameter("dataStyleId"));

                if ( forceHiddenSet.contains("dataStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"dataStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Data Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"dataStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleSetContentData Style IdOption");
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
            buf.append("<span id=\"ajaxSubmitResult\"></span>");
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/styleSetContentAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2")){
            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
	        StringBuffer buf = new StringBuffer();
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
            importedScripts += "function responseCallback(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayStyleSetContent\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest(){\n";
            importedScripts +=     "xmlhttpPostXX('styleSetContentFormAddDis','/styleSetContentAction.html', 'resultDisplayStyleSetContent', '${ajax_response_fields}', responseCallback);\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"styleSetContentFormAddDis\" method=\"POST\" action=\"/styleSetContentAction.html\" id=\"styleSetContentFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Name</div>");
        buf.append("<input class=\"requiredField\" id=\"name\" type=\"text\" size=\"70\" name=\"name\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Id Prefix</div>");
        buf.append("<input class=\"requiredField\" id=\"idPrefix\" type=\"text\" size=\"70\" name=\"idPrefix\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Frame Style Id</div>");
        buf.append("<select class=\"field\" name=\"listFrameStyleId\" id=\"listFrameStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_listFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Subject Style Id</div>");
        buf.append("<select class=\"field\" name=\"listSubjectStyleId\" id=\"listSubjectStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_listSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Data Style Id</div>");
        buf.append("<select class=\"field\" name=\"listDataStyleId\" id=\"listDataStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_listDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Frame Style Id</div>");
        buf.append("<select class=\"field\" name=\"frameStyleId\" id=\"frameStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_frameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_frameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Subject Style Id</div>");
        buf.append("<select class=\"field\" name=\"subjectStyleId\" id=\"subjectStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_subjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_subjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Data Style Id</div>");
        buf.append("<select class=\"field\" name=\"dataStyleId\" id=\"dataStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_dataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_dataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest()\">Submit</a><br>");
			buf.append("<a href=\"javascript:clearFormXX(\\'styleSetContentFormAddDis\\')\">Clear Form</a><br>");
		    buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayStyleSetContent\"></span>");
			buf.append("<a href=\"javascript:backToXX(\\'styleSetContentFormAddDis\\',\\'resultDisplayStyleSetContent\\')\">show back</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            m_logger.debug("Ajax Processing gethtml getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into. 
            ret.put("__value", "Successfully received.");
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();

            buf.append("<div id=\"styleSetContent-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                StyleSetContent _StyleSetContent = (StyleSetContent) iterator.next();

                buf.append("<div id=\"styleSetContent-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("name")) {
                    buf.append("<div id=\"styleSetContent-ajax-name\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("idPrefix")) {
                    buf.append("<div id=\"styleSetContent-ajax-idPrefix\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getIdPrefix()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
                    buf.append("<div id=\"styleSetContent-ajax-listFrameStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getListFrameStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
                    buf.append("<div id=\"styleSetContent-ajax-listSubjectStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getListSubjectStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
                    buf.append("<div id=\"styleSetContent-ajax-listDataStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getListDataStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("frameStyleId")) {
                    buf.append("<div id=\"styleSetContent-ajax-frameStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getFrameStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
                    buf.append("<div id=\"styleSetContent-ajax-subjectStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getSubjectStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
                    buf.append("<div id=\"styleSetContent-ajax-dataStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getDataStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"styleSetContent-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"styleSetContent-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleSetContent.getTimeUpdated()));
                    buf.append("</div>");

				}
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("name")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("idPrefix")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Id Prefix");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Frame Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Subject Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Data Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("frameStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Frame Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Subject Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Data Style Id");
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
                StyleSetContent _StyleSetContent = (StyleSetContent) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("name")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("idPrefix")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getIdPrefix()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getListFrameStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getListSubjectStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getListDataStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("frameStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getFrameStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getSubjectStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getDataStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleSetContent.getTimeUpdated()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            StyleSetContent _StyleSetContent = null; 
            List list = StyleSetContentDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _StyleSetContent = (StyleSetContent) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _StyleSetContent = (StyleSetContent) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _StyleSetContent = StyleSetContentDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last StyleSetContent=" + _StyleSetContent.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _StyleSetContent = (StyleSetContent) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("name")) 
			            json.put("name", ""+_StyleSetContent.getName());
		            if ( ignoreFieldSet || fieldSet.contains("idPrefix")) 
			            json.put("idPrefix", ""+_StyleSetContent.getIdPrefix());
		            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) 
			            json.put("listFrameStyleId", ""+_StyleSetContent.getListFrameStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) 
			            json.put("listSubjectStyleId", ""+_StyleSetContent.getListSubjectStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) 
			            json.put("listDataStyleId", ""+_StyleSetContent.getListDataStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("frameStyleId")) 
			            json.put("frameStyleId", ""+_StyleSetContent.getFrameStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) 
			            json.put("subjectStyleId", ""+_StyleSetContent.getSubjectStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) 
			            json.put("dataStyleId", ""+_StyleSetContent.getDataStyleId());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_StyleSetContent.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonName = new JSONObject();
	            jsonName.put("name", "name");
	            jsonName.put("value", ""+_StyleSetContent.getName());
	            array.put(jsonName);

	            JSONObject jsonIdPrefix = new JSONObject();
	            jsonIdPrefix.put("name", "idPrefix");
	            jsonIdPrefix.put("value", ""+_StyleSetContent.getIdPrefix());
	            array.put(jsonIdPrefix);

	            JSONObject jsonListFrameStyleId = new JSONObject();
	            jsonListFrameStyleId.put("name", "listFrameStyleId");
	            jsonListFrameStyleId.put("value", ""+_StyleSetContent.getListFrameStyleId());
	            array.put(jsonListFrameStyleId);

	            JSONObject jsonListSubjectStyleId = new JSONObject();
	            jsonListSubjectStyleId.put("name", "listSubjectStyleId");
	            jsonListSubjectStyleId.put("value", ""+_StyleSetContent.getListSubjectStyleId());
	            array.put(jsonListSubjectStyleId);

	            JSONObject jsonListDataStyleId = new JSONObject();
	            jsonListDataStyleId.put("name", "listDataStyleId");
	            jsonListDataStyleId.put("value", ""+_StyleSetContent.getListDataStyleId());
	            array.put(jsonListDataStyleId);

	            JSONObject jsonFrameStyleId = new JSONObject();
	            jsonFrameStyleId.put("name", "frameStyleId");
	            jsonFrameStyleId.put("value", ""+_StyleSetContent.getFrameStyleId());
	            array.put(jsonFrameStyleId);

	            JSONObject jsonSubjectStyleId = new JSONObject();
	            jsonSubjectStyleId.put("name", "subjectStyleId");
	            jsonSubjectStyleId.put("value", ""+_StyleSetContent.getSubjectStyleId());
	            array.put(jsonSubjectStyleId);

	            JSONObject jsonDataStyleId = new JSONObject();
	            jsonDataStyleId.put("name", "dataStyleId");
	            jsonDataStyleId.put("value", ""+_StyleSetContent.getDataStyleId());
	            array.put(jsonDataStyleId);


    	        top.put("fields", array);
			}


            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
            
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
    protected List prepareReturnData(HttpServletRequest request){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && request.getParameter("ajaxOut").startsWith("getlist");

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            StyleSetContent _StyleSetContent = null; 
            List list = StyleSetContentDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _StyleSetContent = (StyleSetContent) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _StyleSetContent = (StyleSetContent) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _StyleSetContent = StyleSetContentDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_StyleSetContent);

        } else {
            
            List list = StyleSetContentDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected StyleSetContentDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( StyleSetContentAction.class);
}
