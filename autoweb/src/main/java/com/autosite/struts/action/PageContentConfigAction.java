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

import com.autosite.db.PageContentConfig;
import com.autosite.ds.PageContentConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;

import com.autosite.struts.form.PageContentConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


public class PageContentConfigAction extends AutositeCoreAction {

    public PageContentConfigAction(){
        m_ds = PageContentConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        PageContentConfigForm _PageContentConfigForm = (PageContentConfigForm) form;
        HttpSession session = request.getSession();

        setPage(session, "page_content_config_home");

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


        PageContentConfig _PageContentConfig = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PageContentConfig = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PageContentConfig _PageContentConfig = m_ds.getById(cid);

            if (_PageContentConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageContentConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageContentConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_PageContentConfig);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PageContentConfigForm, _PageContentConfig);
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
            //PageContentConfig _PageContentConfig = m_ds.getById(cid);

            if (_PageContentConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageContentConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageContentConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PageContentConfig);
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
            //PageContentConfig _PageContentConfig = m_ds.getById(cid);

            if (_PageContentConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageContentConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageContentConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PageContentConfig);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PageContentConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _PageContentConfig);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new PageContentConfig" );
            PageContentConfig _PageContentConfigNew = new PageContentConfig();   

            // Setting IDs for the object
            _PageContentConfigNew.setSiteId(site.getId());

            _PageContentConfigNew.setContentId(WebParamUtil.getLongValue(_PageContentConfigForm.getContentId()));
            m_logger.debug("setting ContentId=" +_PageContentConfigForm.getContentId());
            _PageContentConfigNew.setContentType(WebParamUtil.getIntValue(_PageContentConfigForm.getContentType()));
            m_logger.debug("setting ContentType=" +_PageContentConfigForm.getContentType());
            _PageContentConfigNew.setSortType(WebParamUtil.getIntValue(_PageContentConfigForm.getSortType()));
            m_logger.debug("setting SortType=" +_PageContentConfigForm.getSortType());
            _PageContentConfigNew.setArrangeType(WebParamUtil.getIntValue(_PageContentConfigForm.getArrangeType()));
            m_logger.debug("setting ArrangeType=" +_PageContentConfigForm.getArrangeType());
            _PageContentConfigNew.setPageCss(WebParamUtil.getStringValue(_PageContentConfigForm.getPageCss()));
            m_logger.debug("setting PageCss=" +_PageContentConfigForm.getPageCss());
            _PageContentConfigNew.setPageScript(WebParamUtil.getStringValue(_PageContentConfigForm.getPageScript()));
            m_logger.debug("setting PageScript=" +_PageContentConfigForm.getPageScript());
            _PageContentConfigNew.setPageCssImports(WebParamUtil.getStringValue(_PageContentConfigForm.getPageCssImports()));
            m_logger.debug("setting PageCssImports=" +_PageContentConfigForm.getPageCssImports());
            _PageContentConfigNew.setPageScriptImports(WebParamUtil.getStringValue(_PageContentConfigForm.getPageScriptImports()));
            m_logger.debug("setting PageScriptImports=" +_PageContentConfigForm.getPageScriptImports());
            _PageContentConfigNew.setHideMenu(WebParamUtil.getIntValue(_PageContentConfigForm.getHideMenu()));
            m_logger.debug("setting HideMenu=" +_PageContentConfigForm.getHideMenu());
            _PageContentConfigNew.setHideMid(WebParamUtil.getIntValue(_PageContentConfigForm.getHideMid()));
            m_logger.debug("setting HideMid=" +_PageContentConfigForm.getHideMid());
            _PageContentConfigNew.setHideAd(WebParamUtil.getIntValue(_PageContentConfigForm.getHideAd()));
            m_logger.debug("setting HideAd=" +_PageContentConfigForm.getHideAd());
            _PageContentConfigNew.setStyleId(WebParamUtil.getLongValue(_PageContentConfigForm.getStyleId()));
            m_logger.debug("setting StyleId=" +_PageContentConfigForm.getStyleId());
            _PageContentConfigNew.setContentStyleSetId(WebParamUtil.getLongValue(_PageContentConfigForm.getContentStyleSetId()));
            m_logger.debug("setting ContentStyleSetId=" +_PageContentConfigForm.getContentStyleSetId());
            _PageContentConfigNew.setHeaderMeta(WebParamUtil.getStringValue(_PageContentConfigForm.getHeaderMeta()));
            m_logger.debug("setting HeaderMeta=" +_PageContentConfigForm.getHeaderMeta());
            _PageContentConfigNew.setHeaderLink(WebParamUtil.getStringValue(_PageContentConfigForm.getHeaderLink()));
            m_logger.debug("setting HeaderLink=" +_PageContentConfigForm.getHeaderLink());
            _PageContentConfigNew.setTimeCreated(WebParamUtil.getDateValue(_PageContentConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PageContentConfigForm.getTimeCreated());
            _PageContentConfigNew.setTimeUpdated(WebParamUtil.getDateValue(_PageContentConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PageContentConfigForm.getTimeUpdated());

            try {
                checkDepedenceIntegrity(_PageContentConfigNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


			// Checking the uniqueness by configuration. 
			if ( m_ds.getByContentTypeContentId(_PageContentConfigNew.getContentType(), _PageContentConfigNew.getContentId()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _PageContentConfigNew.getContentId());
                return mapping.findForward("default");
            }
            try{
                m_actionExtent.beforeAdd(request, response, _PageContentConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PageContentConfigNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PageContentConfigNew);
            try{
                m_actionExtent.afterAdd(request, response, _PageContentConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "page_content_config_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PageContentConfigForm _PageContentConfigForm, PageContentConfig _PageContentConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageContentConfig _PageContentConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + PageContentConfigDS.objectToString(_PageContentConfig));

        _PageContentConfig.setContentId(WebParamUtil.getLongValue(_PageContentConfigForm.getContentId()));
        _PageContentConfig.setContentType(WebParamUtil.getIntValue(_PageContentConfigForm.getContentType()));
        _PageContentConfig.setSortType(WebParamUtil.getIntValue(_PageContentConfigForm.getSortType()));
        _PageContentConfig.setArrangeType(WebParamUtil.getIntValue(_PageContentConfigForm.getArrangeType()));
        _PageContentConfig.setPageCss(WebParamUtil.getStringValue(_PageContentConfigForm.getPageCss()));
        _PageContentConfig.setPageScript(WebParamUtil.getStringValue(_PageContentConfigForm.getPageScript()));
        _PageContentConfig.setPageCssImports(WebParamUtil.getStringValue(_PageContentConfigForm.getPageCssImports()));
        _PageContentConfig.setPageScriptImports(WebParamUtil.getStringValue(_PageContentConfigForm.getPageScriptImports()));
        _PageContentConfig.setHideMenu(WebParamUtil.getIntValue(_PageContentConfigForm.getHideMenu()));
        _PageContentConfig.setHideMid(WebParamUtil.getIntValue(_PageContentConfigForm.getHideMid()));
        _PageContentConfig.setHideAd(WebParamUtil.getIntValue(_PageContentConfigForm.getHideAd()));
        _PageContentConfig.setStyleId(WebParamUtil.getLongValue(_PageContentConfigForm.getStyleId()));
        _PageContentConfig.setContentStyleSetId(WebParamUtil.getLongValue(_PageContentConfigForm.getContentStyleSetId()));
        _PageContentConfig.setHeaderMeta(WebParamUtil.getStringValue(_PageContentConfigForm.getHeaderMeta()));
        _PageContentConfig.setHeaderLink(WebParamUtil.getStringValue(_PageContentConfigForm.getHeaderLink()));
        _PageContentConfig.setTimeUpdated(WebParamUtil.getDateValue(_PageContentConfigForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _PageContentConfig);
        m_ds.update(_PageContentConfig);
        m_actionExtent.afterUpdate(request, response, _PageContentConfig);
        m_logger.debug("After update " + PageContentConfigDS.objectToString(_PageContentConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PageContentConfig _PageContentConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageContentConfig _PageContentConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentId"))) {
            m_logger.debug("updating param contentId from " +_PageContentConfig.getContentId() + "->" + request.getParameter("contentId"));
            _PageContentConfig.setContentId(WebParamUtil.getLongValue(request.getParameter("contentId")));
        }
        if (!isMissing(request.getParameter("contentType"))) {
            m_logger.debug("updating param contentType from " +_PageContentConfig.getContentType() + "->" + request.getParameter("contentType"));
            _PageContentConfig.setContentType(WebParamUtil.getIntValue(request.getParameter("contentType")));
        }
        if (!isMissing(request.getParameter("sortType"))) {
            m_logger.debug("updating param sortType from " +_PageContentConfig.getSortType() + "->" + request.getParameter("sortType"));
            _PageContentConfig.setSortType(WebParamUtil.getIntValue(request.getParameter("sortType")));
        }
        if (!isMissing(request.getParameter("arrangeType"))) {
            m_logger.debug("updating param arrangeType from " +_PageContentConfig.getArrangeType() + "->" + request.getParameter("arrangeType"));
            _PageContentConfig.setArrangeType(WebParamUtil.getIntValue(request.getParameter("arrangeType")));
        }
        if (!isMissing(request.getParameter("pageCss"))) {
            m_logger.debug("updating param pageCss from " +_PageContentConfig.getPageCss() + "->" + request.getParameter("pageCss"));
            _PageContentConfig.setPageCss(WebParamUtil.getStringValue(request.getParameter("pageCss")));
        }
        if (!isMissing(request.getParameter("pageScript"))) {
            m_logger.debug("updating param pageScript from " +_PageContentConfig.getPageScript() + "->" + request.getParameter("pageScript"));
            _PageContentConfig.setPageScript(WebParamUtil.getStringValue(request.getParameter("pageScript")));
        }
        if (!isMissing(request.getParameter("pageCssImports"))) {
            m_logger.debug("updating param pageCssImports from " +_PageContentConfig.getPageCssImports() + "->" + request.getParameter("pageCssImports"));
            _PageContentConfig.setPageCssImports(WebParamUtil.getStringValue(request.getParameter("pageCssImports")));
        }
        if (!isMissing(request.getParameter("pageScriptImports"))) {
            m_logger.debug("updating param pageScriptImports from " +_PageContentConfig.getPageScriptImports() + "->" + request.getParameter("pageScriptImports"));
            _PageContentConfig.setPageScriptImports(WebParamUtil.getStringValue(request.getParameter("pageScriptImports")));
        }
        if (!isMissing(request.getParameter("hideMenu"))) {
            m_logger.debug("updating param hideMenu from " +_PageContentConfig.getHideMenu() + "->" + request.getParameter("hideMenu"));
            _PageContentConfig.setHideMenu(WebParamUtil.getIntValue(request.getParameter("hideMenu")));
        }
        if (!isMissing(request.getParameter("hideMid"))) {
            m_logger.debug("updating param hideMid from " +_PageContentConfig.getHideMid() + "->" + request.getParameter("hideMid"));
            _PageContentConfig.setHideMid(WebParamUtil.getIntValue(request.getParameter("hideMid")));
        }
        if (!isMissing(request.getParameter("hideAd"))) {
            m_logger.debug("updating param hideAd from " +_PageContentConfig.getHideAd() + "->" + request.getParameter("hideAd"));
            _PageContentConfig.setHideAd(WebParamUtil.getIntValue(request.getParameter("hideAd")));
        }
        if (!isMissing(request.getParameter("styleId"))) {
            m_logger.debug("updating param styleId from " +_PageContentConfig.getStyleId() + "->" + request.getParameter("styleId"));
            _PageContentConfig.setStyleId(WebParamUtil.getLongValue(request.getParameter("styleId")));
        }
        if (!isMissing(request.getParameter("contentStyleSetId"))) {
            m_logger.debug("updating param contentStyleSetId from " +_PageContentConfig.getContentStyleSetId() + "->" + request.getParameter("contentStyleSetId"));
            _PageContentConfig.setContentStyleSetId(WebParamUtil.getLongValue(request.getParameter("contentStyleSetId")));
        }
        if (!isMissing(request.getParameter("headerMeta"))) {
            m_logger.debug("updating param headerMeta from " +_PageContentConfig.getHeaderMeta() + "->" + request.getParameter("headerMeta"));
            _PageContentConfig.setHeaderMeta(WebParamUtil.getStringValue(request.getParameter("headerMeta")));
        }
        if (!isMissing(request.getParameter("headerLink"))) {
            m_logger.debug("updating param headerLink from " +_PageContentConfig.getHeaderLink() + "->" + request.getParameter("headerLink"));
            _PageContentConfig.setHeaderLink(WebParamUtil.getStringValue(request.getParameter("headerLink")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PageContentConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PageContentConfig.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_PageContentConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _PageContentConfig.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _PageContentConfig.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _PageContentConfig);
        m_ds.update(_PageContentConfig);
        m_actionExtent.afterUpdate(request, response, _PageContentConfig);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, PageContentConfig _PageContentConfig) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageContentConfig _PageContentConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentId"))) {
			return String.valueOf(_PageContentConfig.getContentId());
        }
        if (!isMissing(request.getParameter("contentType"))) {
			return String.valueOf(_PageContentConfig.getContentType());
        }
        if (!isMissing(request.getParameter("sortType"))) {
			return String.valueOf(_PageContentConfig.getSortType());
        }
        if (!isMissing(request.getParameter("arrangeType"))) {
			return String.valueOf(_PageContentConfig.getArrangeType());
        }
        if (!isMissing(request.getParameter("pageCss"))) {
			return String.valueOf(_PageContentConfig.getPageCss());
        }
        if (!isMissing(request.getParameter("pageScript"))) {
			return String.valueOf(_PageContentConfig.getPageScript());
        }
        if (!isMissing(request.getParameter("pageCssImports"))) {
			return String.valueOf(_PageContentConfig.getPageCssImports());
        }
        if (!isMissing(request.getParameter("pageScriptImports"))) {
			return String.valueOf(_PageContentConfig.getPageScriptImports());
        }
        if (!isMissing(request.getParameter("hideMenu"))) {
			return String.valueOf(_PageContentConfig.getHideMenu());
        }
        if (!isMissing(request.getParameter("hideMid"))) {
			return String.valueOf(_PageContentConfig.getHideMid());
        }
        if (!isMissing(request.getParameter("hideAd"))) {
			return String.valueOf(_PageContentConfig.getHideAd());
        }
        if (!isMissing(request.getParameter("styleId"))) {
			return String.valueOf(_PageContentConfig.getStyleId());
        }
        if (!isMissing(request.getParameter("contentStyleSetId"))) {
			return String.valueOf(_PageContentConfig.getContentStyleSetId());
        }
        if (!isMissing(request.getParameter("headerMeta"))) {
			return String.valueOf(_PageContentConfig.getHeaderMeta());
        }
        if (!isMissing(request.getParameter("headerLink"))) {
			return String.valueOf(_PageContentConfig.getHeaderLink());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_PageContentConfig.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_PageContentConfig.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(PageContentConfig _PageContentConfig) throws Exception {
    }

    protected String getFieldByName(String fieldName, PageContentConfig _PageContentConfig) {
        if (fieldName == null || fieldName.equals("")|| _PageContentConfig == null) return null;
        
        if (fieldName.equals("contentId")) {
            return WebUtil.display(_PageContentConfig.getContentId());
        }
        if (fieldName.equals("contentType")) {
            return WebUtil.display(_PageContentConfig.getContentType());
        }
        if (fieldName.equals("sortType")) {
            return WebUtil.display(_PageContentConfig.getSortType());
        }
        if (fieldName.equals("arrangeType")) {
            return WebUtil.display(_PageContentConfig.getArrangeType());
        }
        if (fieldName.equals("pageCss")) {
            return WebUtil.display(_PageContentConfig.getPageCss());
        }
        if (fieldName.equals("pageScript")) {
            return WebUtil.display(_PageContentConfig.getPageScript());
        }
        if (fieldName.equals("pageCssImports")) {
            return WebUtil.display(_PageContentConfig.getPageCssImports());
        }
        if (fieldName.equals("pageScriptImports")) {
            return WebUtil.display(_PageContentConfig.getPageScriptImports());
        }
        if (fieldName.equals("hideMenu")) {
            return WebUtil.display(_PageContentConfig.getHideMenu());
        }
        if (fieldName.equals("hideMid")) {
            return WebUtil.display(_PageContentConfig.getHideMid());
        }
        if (fieldName.equals("hideAd")) {
            return WebUtil.display(_PageContentConfig.getHideAd());
        }
        if (fieldName.equals("styleId")) {
            return WebUtil.display(_PageContentConfig.getStyleId());
        }
        if (fieldName.equals("contentStyleSetId")) {
            return WebUtil.display(_PageContentConfig.getContentStyleSetId());
        }
        if (fieldName.equals("headerMeta")) {
            return WebUtil.display(_PageContentConfig.getHeaderMeta());
        }
        if (fieldName.equals("headerLink")) {
            return WebUtil.display(_PageContentConfig.getHeaderLink());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_PageContentConfig.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_PageContentConfig.getTimeUpdated());
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
            PageContentConfig _PageContentConfig = PageContentConfigDS.getInstance().getById(id);
            if (_PageContentConfig == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _PageContentConfig);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            PageContentConfig _PageContentConfig = PageContentConfigDS.getInstance().getById(id);
            if ( _PageContentConfig == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _PageContentConfig);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/pageContentConfigAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("contentId")) {
                String value = WebUtil.display(request.getParameter("contentId"));

                if ( forceHiddenSet.contains("contentId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Id</div>");
            buf.append("<INPUT NAME=\"contentId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
                String value = WebUtil.display(request.getParameter("contentType"));

                if ( forceHiddenSet.contains("contentType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Type</div>");
            buf.append("<select id=\"requiredField\" name=\"contentType\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

        	buf.append("<option value=\"1\" >Default</option>");
        	buf.append("<option value=\"2\" >Blog</option>");
        	buf.append("<option value=\"3\" >Forum Content</option>");
        	buf.append("<option value=\"4\" >TBD</option>");
        	buf.append("<option value=\"5\" >TBD</option>");

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("sortType")) {
                String value = WebUtil.display(request.getParameter("sortType"));

                if ( forceHiddenSet.contains("sortType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"sortType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Sort Type</div>");
            buf.append("<INPUT NAME=\"sortType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("arrangeType")) {
                String value = WebUtil.display(request.getParameter("arrangeType"));

                if ( forceHiddenSet.contains("arrangeType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"arrangeType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Arrange Type</div>");
            buf.append("<INPUT NAME=\"arrangeType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pageCss")) {
                String value = WebUtil.display(request.getParameter("pageCss"));

                if ( forceHiddenSet.contains("pageCss")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pageCss\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Page Css</div>");
            buf.append("<INPUT NAME=\"pageCss\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pageScript")) {
                String value = WebUtil.display(request.getParameter("pageScript"));

                if ( forceHiddenSet.contains("pageScript")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pageScript\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Page Script</div>");
            buf.append("<INPUT NAME=\"pageScript\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pageCssImports")) {
                String value = WebUtil.display(request.getParameter("pageCssImports"));

                if ( forceHiddenSet.contains("pageCssImports")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pageCssImports\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Page Css Imports</div>");
            buf.append("<INPUT NAME=\"pageCssImports\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pageScriptImports")) {
                String value = WebUtil.display(request.getParameter("pageScriptImports"));

                if ( forceHiddenSet.contains("pageScriptImports")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pageScriptImports\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Page Script Imports</div>");
            buf.append("<INPUT NAME=\"pageScriptImports\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hideMenu")) {
                String value = WebUtil.display(request.getParameter("hideMenu"));

                if ( forceHiddenSet.contains("hideMenu")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hideMenu\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide Menu</div>");
            buf.append("<select name=\"hideMenu\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hideMid")) {
                String value = WebUtil.display(request.getParameter("hideMid"));

                if ( forceHiddenSet.contains("hideMid")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hideMid\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide Mid</div>");
            buf.append("<select name=\"hideMid\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hideAd")) {
                String value = WebUtil.display(request.getParameter("hideAd"));

                if ( forceHiddenSet.contains("hideAd")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hideAd\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide Ad</div>");
            buf.append("<select name=\"hideAd\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("styleId")) {
                String value = WebUtil.display(request.getParameter("styleId"));

                if ( forceHiddenSet.contains("styleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"styleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Style Id</div>");
            buf.append("<INPUT NAME=\"styleId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentStyleSetId")) {
                String value = WebUtil.display(request.getParameter("contentStyleSetId"));

                if ( forceHiddenSet.contains("contentStyleSetId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentStyleSetId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Style Set Id</div>");
            buf.append("<INPUT NAME=\"contentStyleSetId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
                String value = WebUtil.display(request.getParameter("headerMeta"));

                if ( forceHiddenSet.contains("headerMeta")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"headerMeta\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Header Meta</div>");
            buf.append("<INPUT NAME=\"headerMeta\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
                String value = WebUtil.display(request.getParameter("headerLink"));

                if ( forceHiddenSet.contains("headerLink")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"headerLink\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Header Link</div>");
            buf.append("<INPUT NAME=\"headerLink\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/pageContentConfigAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
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
            importedScripts += "    document.getElementById(\"resultDisplayPageContentConfig\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest(){\n";
            importedScripts +=     "xmlhttpPostXX('pageContentConfigFormAddDis','/pageContentConfigAction.html', 'resultDisplayPageContentConfig', '${ajax_response_fields}', responseCallback);\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"pageContentConfigFormAddDis\" method=\"POST\" action=\"/pageContentConfigAction.html\" id=\"pageContentConfigFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Content Id</div>");
        buf.append("<input class=\"field\" id=\"contentId\" type=\"text\" size=\"70\" name=\"contentId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Type</div>");
        buf.append("<select class=\"field\" name=\"contentType\" id=\"contentType\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<!--option value=\"XX\" >XX</option-->");
        buf.append("<option value=\"1\" >Default</option>");
        buf.append("<option value=\"2\" >Blog</option>");
        buf.append("<option value=\"3\" >Forum Content</option>");
        buf.append("<option value=\"4\" >TBD</option>");
        buf.append("<option value=\"5\" >TBD</option>");
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Sort Type</div>");
        buf.append("<input class=\"field\" id=\"sortType\" type=\"text\" size=\"70\" name=\"sortType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Arrange Type</div>");
        buf.append("<input class=\"field\" id=\"arrangeType\" type=\"text\" size=\"70\" name=\"arrangeType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Page Css</div>");
        buf.append("<input class=\"field\" id=\"pageCss\" type=\"text\" size=\"70\" name=\"pageCss\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Page Script</div>");
        buf.append("<input class=\"field\" id=\"pageScript\" type=\"text\" size=\"70\" name=\"pageScript\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Page Css Imports</div>");
        buf.append("<input class=\"field\" id=\"pageCssImports\" type=\"text\" size=\"70\" name=\"pageCssImports\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Page Script Imports</div>");
        buf.append("<input class=\"field\" id=\"pageScriptImports\" type=\"text\" size=\"70\" name=\"pageScriptImports\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Hide Menu</div>");
        buf.append("<select name=\"hideMenu\" id=\"hideMenu\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Hide Mid</div>");
        buf.append("<select name=\"hideMid\" id=\"hideMid\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Hide Ad</div>");
        buf.append("<select name=\"hideAd\" id=\"hideAd\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Style Id</div>");
        buf.append("<input class=\"field\" id=\"styleId\" type=\"text\" size=\"70\" name=\"styleId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Style Set Id</div>");
        buf.append("<input class=\"field\" id=\"contentStyleSetId\" type=\"text\" size=\"70\" name=\"contentStyleSetId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Header Meta</div>");
        buf.append("<input class=\"field\" id=\"headerMeta\" type=\"text\" size=\"70\" name=\"headerMeta\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Header Link</div>");
        buf.append("<input class=\"field\" id=\"headerLink\" type=\"text\" size=\"70\" name=\"headerLink\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest()\">Submit</a><br>");
			buf.append("<a href=\"javascript:clearFormXX(\\'pageContentConfigFormAddDis\\')\">Clear Form</a><br>");
		    buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayPageContentConfig\"></span>");
			buf.append("<a href=\"javascript:backToXX(\\'pageContentConfigFormAddDis\\',\\'resultDisplayPageContentConfig\\')\">show back</a><br>");
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

            buf.append("<div id=\"pageContentConfig-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                PageContentConfig _PageContentConfig = (PageContentConfig) iterator.next();

                buf.append("<div id=\"pageContentConfig-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("contentId")) {
                    buf.append("<div id=\"pageContentConfig-ajax-contentId\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getContentId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
                    buf.append("<div id=\"pageContentConfig-ajax-contentType\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getContentType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("sortType")) {
                    buf.append("<div id=\"pageContentConfig-ajax-sortType\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getSortType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("arrangeType")) {
                    buf.append("<div id=\"pageContentConfig-ajax-arrangeType\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getArrangeType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pageCss")) {
                    buf.append("<div id=\"pageContentConfig-ajax-pageCss\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getPageCss()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pageScript")) {
                    buf.append("<div id=\"pageContentConfig-ajax-pageScript\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getPageScript()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pageCssImports")) {
                    buf.append("<div id=\"pageContentConfig-ajax-pageCssImports\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getPageCssImports()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pageScriptImports")) {
                    buf.append("<div id=\"pageContentConfig-ajax-pageScriptImports\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getPageScriptImports()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hideMenu")) {
                    buf.append("<div id=\"pageContentConfig-ajax-hideMenu\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getHideMenu()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hideMid")) {
                    buf.append("<div id=\"pageContentConfig-ajax-hideMid\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getHideMid()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hideAd")) {
                    buf.append("<div id=\"pageContentConfig-ajax-hideAd\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getHideAd()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("styleId")) {
                    buf.append("<div id=\"pageContentConfig-ajax-styleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentStyleSetId")) {
                    buf.append("<div id=\"pageContentConfig-ajax-contentStyleSetId\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getContentStyleSetId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
                    buf.append("<div id=\"pageContentConfig-ajax-headerMeta\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getHeaderMeta()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
                    buf.append("<div id=\"pageContentConfig-ajax-headerLink\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getHeaderLink()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"pageContentConfig-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"pageContentConfig-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_PageContentConfig.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("contentId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("sortType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Sort Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("arrangeType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Arrange Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pageCss")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Page Css");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pageScript")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Page Script");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pageCssImports")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Page Css Imports");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pageScriptImports")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Page Script Imports");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hideMenu")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide Menu");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hideMid")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide Mid");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hideAd")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide Ad");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("styleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentStyleSetId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Style Set Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Header Meta");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Header Link");
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
                PageContentConfig _PageContentConfig = (PageContentConfig) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("contentId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getContentId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getContentType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("sortType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getSortType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("arrangeType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getArrangeType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pageCss")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getPageCss()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pageScript")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getPageScript()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pageCssImports")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getPageCssImports()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pageScriptImports")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getPageScriptImports()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hideMenu")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_PageContentConfig.getHideMenu()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pageContentConfigAction.html?ef=true&id="+ _PageContentConfig.getId()+"&hideMenu=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pageContentConfigAction.html?ef=true&id="+ _PageContentConfig.getId()+"&hideMenu=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hideMid")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_PageContentConfig.getHideMid()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pageContentConfigAction.html?ef=true&id="+ _PageContentConfig.getId()+"&hideMid=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pageContentConfigAction.html?ef=true&id="+ _PageContentConfig.getId()+"&hideMid=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hideAd")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_PageContentConfig.getHideAd()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pageContentConfigAction.html?ef=true&id="+ _PageContentConfig.getId()+"&hideAd=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pageContentConfigAction.html?ef=true&id="+ _PageContentConfig.getId()+"&hideAd=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("styleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentStyleSetId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getContentStyleSetId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getHeaderMeta()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getHeaderLink()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PageContentConfig.getTimeUpdated()));

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
            PageContentConfig _PageContentConfig = null; 
            List list = PageContentConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _PageContentConfig = (PageContentConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _PageContentConfig = (PageContentConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _PageContentConfig = PageContentConfigDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last PageContentConfig=" + _PageContentConfig.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _PageContentConfig = (PageContentConfig) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("contentId")) 
			            json.put("contentId", ""+_PageContentConfig.getContentId());
		            if ( ignoreFieldSet || fieldSet.contains("contentType")) 
			            json.put("contentType", ""+_PageContentConfig.getContentType());
		            if ( ignoreFieldSet || fieldSet.contains("sortType")) 
			            json.put("sortType", ""+_PageContentConfig.getSortType());
		            if ( ignoreFieldSet || fieldSet.contains("arrangeType")) 
			            json.put("arrangeType", ""+_PageContentConfig.getArrangeType());
		            if ( ignoreFieldSet || fieldSet.contains("pageCss")) 
			            json.put("pageCss", ""+_PageContentConfig.getPageCss());
		            if ( ignoreFieldSet || fieldSet.contains("pageScript")) 
			            json.put("pageScript", ""+_PageContentConfig.getPageScript());
		            if ( ignoreFieldSet || fieldSet.contains("pageCssImports")) 
			            json.put("pageCssImports", ""+_PageContentConfig.getPageCssImports());
		            if ( ignoreFieldSet || fieldSet.contains("pageScriptImports")) 
			            json.put("pageScriptImports", ""+_PageContentConfig.getPageScriptImports());
		            if ( ignoreFieldSet || fieldSet.contains("hideMenu")) 
			            json.put("hideMenu", ""+_PageContentConfig.getHideMenu());
		            if ( ignoreFieldSet || fieldSet.contains("hideMid")) 
			            json.put("hideMid", ""+_PageContentConfig.getHideMid());
		            if ( ignoreFieldSet || fieldSet.contains("hideAd")) 
			            json.put("hideAd", ""+_PageContentConfig.getHideAd());
		            if ( ignoreFieldSet || fieldSet.contains("styleId")) 
			            json.put("styleId", ""+_PageContentConfig.getStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("contentStyleSetId")) 
			            json.put("contentStyleSetId", ""+_PageContentConfig.getContentStyleSetId());
		            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) 
			            json.put("headerMeta", ""+_PageContentConfig.getHeaderMeta());
		            if ( ignoreFieldSet || fieldSet.contains("headerLink")) 
			            json.put("headerLink", ""+_PageContentConfig.getHeaderLink());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_PageContentConfig.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonContentId = new JSONObject();
	            jsonContentId.put("name", "contentId");
	            jsonContentId.put("value", ""+_PageContentConfig.getContentId());
	            array.put(jsonContentId);

	            JSONObject jsonContentType = new JSONObject();
	            jsonContentType.put("name", "contentType");
	            jsonContentType.put("value", ""+_PageContentConfig.getContentType());
	            array.put(jsonContentType);

	            JSONObject jsonSortType = new JSONObject();
	            jsonSortType.put("name", "sortType");
	            jsonSortType.put("value", ""+_PageContentConfig.getSortType());
	            array.put(jsonSortType);

	            JSONObject jsonArrangeType = new JSONObject();
	            jsonArrangeType.put("name", "arrangeType");
	            jsonArrangeType.put("value", ""+_PageContentConfig.getArrangeType());
	            array.put(jsonArrangeType);

	            JSONObject jsonPageCss = new JSONObject();
	            jsonPageCss.put("name", "pageCss");
	            jsonPageCss.put("value", ""+_PageContentConfig.getPageCss());
	            array.put(jsonPageCss);

	            JSONObject jsonPageScript = new JSONObject();
	            jsonPageScript.put("name", "pageScript");
	            jsonPageScript.put("value", ""+_PageContentConfig.getPageScript());
	            array.put(jsonPageScript);

	            JSONObject jsonPageCssImports = new JSONObject();
	            jsonPageCssImports.put("name", "pageCssImports");
	            jsonPageCssImports.put("value", ""+_PageContentConfig.getPageCssImports());
	            array.put(jsonPageCssImports);

	            JSONObject jsonPageScriptImports = new JSONObject();
	            jsonPageScriptImports.put("name", "pageScriptImports");
	            jsonPageScriptImports.put("value", ""+_PageContentConfig.getPageScriptImports());
	            array.put(jsonPageScriptImports);

	            JSONObject jsonHideMenu = new JSONObject();
	            jsonHideMenu.put("name", "hideMenu");
	            jsonHideMenu.put("value", ""+_PageContentConfig.getHideMenu());
	            array.put(jsonHideMenu);

	            JSONObject jsonHideMid = new JSONObject();
	            jsonHideMid.put("name", "hideMid");
	            jsonHideMid.put("value", ""+_PageContentConfig.getHideMid());
	            array.put(jsonHideMid);

	            JSONObject jsonHideAd = new JSONObject();
	            jsonHideAd.put("name", "hideAd");
	            jsonHideAd.put("value", ""+_PageContentConfig.getHideAd());
	            array.put(jsonHideAd);

	            JSONObject jsonStyleId = new JSONObject();
	            jsonStyleId.put("name", "styleId");
	            jsonStyleId.put("value", ""+_PageContentConfig.getStyleId());
	            array.put(jsonStyleId);

	            JSONObject jsonContentStyleSetId = new JSONObject();
	            jsonContentStyleSetId.put("name", "contentStyleSetId");
	            jsonContentStyleSetId.put("value", ""+_PageContentConfig.getContentStyleSetId());
	            array.put(jsonContentStyleSetId);

	            JSONObject jsonHeaderMeta = new JSONObject();
	            jsonHeaderMeta.put("name", "headerMeta");
	            jsonHeaderMeta.put("value", ""+_PageContentConfig.getHeaderMeta());
	            array.put(jsonHeaderMeta);

	            JSONObject jsonHeaderLink = new JSONObject();
	            jsonHeaderLink.put("name", "headerLink");
	            jsonHeaderLink.put("value", ""+_PageContentConfig.getHeaderLink());
	            array.put(jsonHeaderLink);


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
            PageContentConfig _PageContentConfig = null; 
            List list = PageContentConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _PageContentConfig = (PageContentConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _PageContentConfig = (PageContentConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _PageContentConfig = PageContentConfigDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_PageContentConfig);

        } else {
            
            List list = PageContentConfigDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected PageContentConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PageContentConfigAction.class);
}
