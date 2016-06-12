package com.autosite.struts.action;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PageConfig;
import com.autosite.ds.PageConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PageConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PageConfigAction extends AutositeCoreAction {

    public PageConfigAction(){
        m_ds = PageConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PageConfigForm _PageConfigForm = (PageConfigForm) form;
        HttpSession session = request.getSession();

        setPage(session, "page_config_home");

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
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        PageConfig _PageConfig = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PageConfig = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PageConfig _PageConfig = m_ds.getById(cid);

            if (_PageConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PageConfigForm, _PageConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PageConfig _PageConfig = m_ds.getById(cid);

            if (_PageConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PageConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PageConfig _PageConfig = m_ds.getById(cid);

            if (_PageConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PageConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PageConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _PageConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new PageConfig" );
            PageConfig _PageConfigNew = new PageConfig();   

            // Setting IDs for the object
            _PageConfigNew.setSiteId(site.getId());

            _PageConfigNew.setPageId(WebParamUtil.getLongValue(_PageConfigForm.getPageId()));
            m_logger.debug("setting PageId=" +_PageConfigForm.getPageId());
            _PageConfigNew.setSortType(WebParamUtil.getIntValue(_PageConfigForm.getSortType()));
            m_logger.debug("setting SortType=" +_PageConfigForm.getSortType());
            _PageConfigNew.setArrangeType(WebParamUtil.getIntValue(_PageConfigForm.getArrangeType()));
            m_logger.debug("setting ArrangeType=" +_PageConfigForm.getArrangeType());
            _PageConfigNew.setPageCss(WebParamUtil.getStringValue(_PageConfigForm.getPageCss()));
            m_logger.debug("setting PageCss=" +_PageConfigForm.getPageCss());
            _PageConfigNew.setPageScript(WebParamUtil.getStringValue(_PageConfigForm.getPageScript()));
            m_logger.debug("setting PageScript=" +_PageConfigForm.getPageScript());
            _PageConfigNew.setPageCssImports(WebParamUtil.getStringValue(_PageConfigForm.getPageCssImports()));
            m_logger.debug("setting PageCssImports=" +_PageConfigForm.getPageCssImports());
            _PageConfigNew.setPageScriptImports(WebParamUtil.getStringValue(_PageConfigForm.getPageScriptImports()));
            m_logger.debug("setting PageScriptImports=" +_PageConfigForm.getPageScriptImports());
            _PageConfigNew.setHideMenu(WebParamUtil.getIntValue(_PageConfigForm.getHideMenu()));
            m_logger.debug("setting HideMenu=" +_PageConfigForm.getHideMenu());
            _PageConfigNew.setHideMid(WebParamUtil.getIntValue(_PageConfigForm.getHideMid()));
            m_logger.debug("setting HideMid=" +_PageConfigForm.getHideMid());
            _PageConfigNew.setHideAd(WebParamUtil.getIntValue(_PageConfigForm.getHideAd()));
            m_logger.debug("setting HideAd=" +_PageConfigForm.getHideAd());
            _PageConfigNew.setStyleId(WebParamUtil.getLongValue(_PageConfigForm.getStyleId()));
            m_logger.debug("setting StyleId=" +_PageConfigForm.getStyleId());
            _PageConfigNew.setContentStyleSetId(WebParamUtil.getLongValue(_PageConfigForm.getContentStyleSetId()));
            m_logger.debug("setting ContentStyleSetId=" +_PageConfigForm.getContentStyleSetId());
            _PageConfigNew.setHeaderMeta(WebParamUtil.getStringValue(_PageConfigForm.getHeaderMeta()));
            m_logger.debug("setting HeaderMeta=" +_PageConfigForm.getHeaderMeta());
            _PageConfigNew.setHeaderLink(WebParamUtil.getStringValue(_PageConfigForm.getHeaderLink()));
            m_logger.debug("setting HeaderLink=" +_PageConfigForm.getHeaderLink());
            _PageConfigNew.setTimeCreated(WebParamUtil.getDateValue(_PageConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PageConfigForm.getTimeCreated());
            _PageConfigNew.setTimeUpdated(WebParamUtil.getDateValue(_PageConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PageConfigForm.getTimeUpdated());

            try{
                m_actionExtent.beforeAdd(request, response, _PageConfigNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PageConfigNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PageConfigNew);
            try{
                m_actionExtent.afterAdd(request, response, _PageConfigNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "page_config_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PageConfigForm _PageConfigForm, PageConfig _PageConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageConfig _PageConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + PageConfigDS.objectToString(_PageConfig));

        _PageConfig.setPageId(WebParamUtil.getLongValue(_PageConfigForm.getPageId()));
        _PageConfig.setSortType(WebParamUtil.getIntValue(_PageConfigForm.getSortType()));
        _PageConfig.setArrangeType(WebParamUtil.getIntValue(_PageConfigForm.getArrangeType()));
        _PageConfig.setPageCss(WebParamUtil.getStringValue(_PageConfigForm.getPageCss()));
        _PageConfig.setPageScript(WebParamUtil.getStringValue(_PageConfigForm.getPageScript()));
        _PageConfig.setPageCssImports(WebParamUtil.getStringValue(_PageConfigForm.getPageCssImports()));
        _PageConfig.setPageScriptImports(WebParamUtil.getStringValue(_PageConfigForm.getPageScriptImports()));
        _PageConfig.setHideMenu(WebParamUtil.getIntValue(_PageConfigForm.getHideMenu()));
        _PageConfig.setHideMid(WebParamUtil.getIntValue(_PageConfigForm.getHideMid()));
        _PageConfig.setHideAd(WebParamUtil.getIntValue(_PageConfigForm.getHideAd()));
        _PageConfig.setStyleId(WebParamUtil.getLongValue(_PageConfigForm.getStyleId()));
        _PageConfig.setContentStyleSetId(WebParamUtil.getLongValue(_PageConfigForm.getContentStyleSetId()));
        _PageConfig.setHeaderMeta(WebParamUtil.getStringValue(_PageConfigForm.getHeaderMeta()));
        _PageConfig.setHeaderLink(WebParamUtil.getStringValue(_PageConfigForm.getHeaderLink()));
        _PageConfig.setTimeCreated(WebParamUtil.getDateValue(_PageConfigForm.getTimeCreated()));
        _PageConfig.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _PageConfig);
        m_ds.update(_PageConfig);
        m_actionExtent.afterUpdate(request, response, _PageConfig);
        m_logger.debug("After update " + PageConfigDS.objectToString(_PageConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PageConfig _PageConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageConfig _PageConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_PageConfig.getPageId() + "->" + request.getParameter("pageId"));
            _PageConfig.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("sortType"))) {
            m_logger.debug("updating param sortType from " +_PageConfig.getSortType() + "->" + request.getParameter("sortType"));
            _PageConfig.setSortType(WebParamUtil.getIntValue(request.getParameter("sortType")));
        }
        if (!isMissing(request.getParameter("arrangeType"))) {
            m_logger.debug("updating param arrangeType from " +_PageConfig.getArrangeType() + "->" + request.getParameter("arrangeType"));
            _PageConfig.setArrangeType(WebParamUtil.getIntValue(request.getParameter("arrangeType")));
        }
        if (!isMissing(request.getParameter("pageCss"))) {
            m_logger.debug("updating param pageCss from " +_PageConfig.getPageCss() + "->" + request.getParameter("pageCss"));
            _PageConfig.setPageCss(WebParamUtil.getStringValue(request.getParameter("pageCss")));
        }
        if (!isMissing(request.getParameter("pageScript"))) {
            m_logger.debug("updating param pageScript from " +_PageConfig.getPageScript() + "->" + request.getParameter("pageScript"));
            _PageConfig.setPageScript(WebParamUtil.getStringValue(request.getParameter("pageScript")));
        }
        if (!isMissing(request.getParameter("pageCssImports"))) {
            m_logger.debug("updating param pageCssImports from " +_PageConfig.getPageCssImports() + "->" + request.getParameter("pageCssImports"));
            _PageConfig.setPageCssImports(WebParamUtil.getStringValue(request.getParameter("pageCssImports")));
        }
        if (!isMissing(request.getParameter("pageScriptImports"))) {
            m_logger.debug("updating param pageScriptImports from " +_PageConfig.getPageScriptImports() + "->" + request.getParameter("pageScriptImports"));
            _PageConfig.setPageScriptImports(WebParamUtil.getStringValue(request.getParameter("pageScriptImports")));
        }
        if (!isMissing(request.getParameter("hideMenu"))) {
            m_logger.debug("updating param hideMenu from " +_PageConfig.getHideMenu() + "->" + request.getParameter("hideMenu"));
            _PageConfig.setHideMenu(WebParamUtil.getIntValue(request.getParameter("hideMenu")));
        }
        if (!isMissing(request.getParameter("hideMid"))) {
            m_logger.debug("updating param hideMid from " +_PageConfig.getHideMid() + "->" + request.getParameter("hideMid"));
            _PageConfig.setHideMid(WebParamUtil.getIntValue(request.getParameter("hideMid")));
        }
        if (!isMissing(request.getParameter("hideAd"))) {
            m_logger.debug("updating param hideAd from " +_PageConfig.getHideAd() + "->" + request.getParameter("hideAd"));
            _PageConfig.setHideAd(WebParamUtil.getIntValue(request.getParameter("hideAd")));
        }
        if (!isMissing(request.getParameter("styleId"))) {
            m_logger.debug("updating param styleId from " +_PageConfig.getStyleId() + "->" + request.getParameter("styleId"));
            _PageConfig.setStyleId(WebParamUtil.getLongValue(request.getParameter("styleId")));
        }
        if (!isMissing(request.getParameter("contentStyleSetId"))) {
            m_logger.debug("updating param contentStyleSetId from " +_PageConfig.getContentStyleSetId() + "->" + request.getParameter("contentStyleSetId"));
            _PageConfig.setContentStyleSetId(WebParamUtil.getLongValue(request.getParameter("contentStyleSetId")));
        }
        if (!isMissing(request.getParameter("headerMeta"))) {
            m_logger.debug("updating param headerMeta from " +_PageConfig.getHeaderMeta() + "->" + request.getParameter("headerMeta"));
            _PageConfig.setHeaderMeta(WebParamUtil.getStringValue(request.getParameter("headerMeta")));
        }
        if (!isMissing(request.getParameter("headerLink"))) {
            m_logger.debug("updating param headerLink from " +_PageConfig.getHeaderLink() + "->" + request.getParameter("headerLink"));
            _PageConfig.setHeaderLink(WebParamUtil.getStringValue(request.getParameter("headerLink")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PageConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PageConfig.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_PageConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _PageConfig.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _PageConfig);
        m_ds.update(_PageConfig);
        m_actionExtent.afterUpdate(request, response, _PageConfig);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, PageConfig _PageConfig) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageConfig _PageConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
			return String.valueOf(_PageConfig.getPageId());
        }
        if (!isMissing(request.getParameter("sortType"))) {
			return String.valueOf(_PageConfig.getSortType());
        }
        if (!isMissing(request.getParameter("arrangeType"))) {
			return String.valueOf(_PageConfig.getArrangeType());
        }
        if (!isMissing(request.getParameter("pageCss"))) {
			return String.valueOf(_PageConfig.getPageCss());
        }
        if (!isMissing(request.getParameter("pageScript"))) {
			return String.valueOf(_PageConfig.getPageScript());
        }
        if (!isMissing(request.getParameter("pageCssImports"))) {
			return String.valueOf(_PageConfig.getPageCssImports());
        }
        if (!isMissing(request.getParameter("pageScriptImports"))) {
			return String.valueOf(_PageConfig.getPageScriptImports());
        }
        if (!isMissing(request.getParameter("hideMenu"))) {
			return String.valueOf(_PageConfig.getHideMenu());
        }
        if (!isMissing(request.getParameter("hideMid"))) {
			return String.valueOf(_PageConfig.getHideMid());
        }
        if (!isMissing(request.getParameter("hideAd"))) {
			return String.valueOf(_PageConfig.getHideAd());
        }
        if (!isMissing(request.getParameter("styleId"))) {
			return String.valueOf(_PageConfig.getStyleId());
        }
        if (!isMissing(request.getParameter("contentStyleSetId"))) {
			return String.valueOf(_PageConfig.getContentStyleSetId());
        }
        if (!isMissing(request.getParameter("headerMeta"))) {
			return String.valueOf(_PageConfig.getHeaderMeta());
        }
        if (!isMissing(request.getParameter("headerLink"))) {
			return String.valueOf(_PageConfig.getHeaderLink());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_PageConfig.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_PageConfig.getTimeUpdated());
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
                ex(mapping, form, request, response);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            PageConfig _PageConfig = PageConfigDS.getInstance().getById(id);
            if (_PageConfig == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _PageConfig);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected PageConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PageConfigAction.class);
}
