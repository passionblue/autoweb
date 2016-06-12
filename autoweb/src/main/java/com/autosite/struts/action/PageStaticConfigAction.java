package com.autosite.struts.action;

import java.util.Date;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PageStaticConfig;
import com.autosite.ds.PageStaticConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.PageStaticConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PageStaticConfigAction extends AutositeCoreAction {

    public PageStaticConfigAction(){
        m_ds = PageStaticConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PageStaticConfigForm _PageStaticConfigForm = (PageStaticConfigForm) form;
        HttpSession session = request.getSession();

        setPage(session, "page_static_config_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }




        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        if (context == null || !context.isSuperAdmin()) accessTestOkay = false;
        
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

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            PageStaticConfig _PageStaticConfig = m_ds.getById(cid);

            if (_PageStaticConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStaticConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStaticConfig.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _PageStaticConfigForm, _PageStaticConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            PageStaticConfig _PageStaticConfig = m_ds.getById(cid);

            if (_PageStaticConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStaticConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStaticConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PageStaticConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            PageStaticConfig _PageStaticConfig = m_ds.getById(cid);

            if (_PageStaticConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStaticConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStaticConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PageStaticConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PageStaticConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _PageStaticConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        }
        else {

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
                return mapping.findForward("default");
            }

            m_logger.info("Creating new PageStaticConfig" );
            PageStaticConfig _PageStaticConfig = new PageStaticConfig();   

            // Setting IDs for the object
            _PageStaticConfig.setSiteId(site.getId());

            _PageStaticConfig.setPageAlias(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageAlias()));
            m_logger.debug("setting PageAlias=" +_PageStaticConfigForm.getPageAlias());
            _PageStaticConfig.setPageCss(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageCss()));
            m_logger.debug("setting PageCss=" +_PageStaticConfigForm.getPageCss());
            _PageStaticConfig.setPageScript(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageScript()));
            m_logger.debug("setting PageScript=" +_PageStaticConfigForm.getPageScript());
            _PageStaticConfig.setPageCssImports(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageCssImports()));
            m_logger.debug("setting PageCssImports=" +_PageStaticConfigForm.getPageCssImports());
            _PageStaticConfig.setPageScriptImports(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageScriptImports()));
            m_logger.debug("setting PageScriptImports=" +_PageStaticConfigForm.getPageScriptImports());
            _PageStaticConfig.setHideMenu(WebParamUtil.getIntValue(_PageStaticConfigForm.getHideMenu()));
            m_logger.debug("setting HideMenu=" +_PageStaticConfigForm.getHideMenu());
            _PageStaticConfig.setHideMid(WebParamUtil.getIntValue(_PageStaticConfigForm.getHideMid()));
            m_logger.debug("setting HideMid=" +_PageStaticConfigForm.getHideMid());
            _PageStaticConfig.setHideAd(WebParamUtil.getIntValue(_PageStaticConfigForm.getHideAd()));
            m_logger.debug("setting HideAd=" +_PageStaticConfigForm.getHideAd());
            _PageStaticConfig.setTimeCreated(WebParamUtil.getDateValue(_PageStaticConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PageStaticConfigForm.getTimeCreated());
            _PageStaticConfig.setTimeUpdated(WebParamUtil.getDateValue(_PageStaticConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PageStaticConfigForm.getTimeUpdated());

			// Checking the uniqueness by configuration. 
			if ( m_ds.getBySiteIdPageAlias(_PageStaticConfig.getSiteId(), _PageStaticConfig.getPageAlias()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _PageStaticConfig.getPageAlias());
                return mapping.findForward("default");
            }
            
            try{
                m_actionExtent.beforeAdd(request, response, _PageStaticConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_PageStaticConfig);
            try{
                m_actionExtent.afterAdd(request, response, _PageStaticConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "page_static_config_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PageStaticConfigForm _PageStaticConfigForm, PageStaticConfig _PageStaticConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageStaticConfig _PageStaticConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + PageStaticConfigDS.objectToString(_PageStaticConfig));

        _PageStaticConfig.setPageAlias(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageAlias()));
        _PageStaticConfig.setPageCss(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageCss()));
        _PageStaticConfig.setPageScript(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageScript()));
        _PageStaticConfig.setPageCssImports(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageCssImports()));
        _PageStaticConfig.setPageScriptImports(WebParamUtil.getStringValue(_PageStaticConfigForm.getPageScriptImports()));
        _PageStaticConfig.setHideMenu(WebParamUtil.getIntValue(_PageStaticConfigForm.getHideMenu()));
        _PageStaticConfig.setHideMid(WebParamUtil.getIntValue(_PageStaticConfigForm.getHideMid()));
        _PageStaticConfig.setHideAd(WebParamUtil.getIntValue(_PageStaticConfigForm.getHideAd()));
        _PageStaticConfig.setTimeCreated(WebParamUtil.getDateValue(_PageStaticConfigForm.getTimeCreated()));
        _PageStaticConfig.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _PageStaticConfig);
        m_ds.update(_PageStaticConfig);
        m_actionExtent.afterUpdate(request, response, _PageStaticConfig);
        m_logger.debug("After update " + PageStaticConfigDS.objectToString(_PageStaticConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PageStaticConfig _PageStaticConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageStaticConfig _PageStaticConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageAlias"))) {
            m_logger.debug("updating param pageAlias from " +_PageStaticConfig.getPageAlias() + "->" + request.getParameter("pageAlias"));
            _PageStaticConfig.setPageAlias(WebParamUtil.getStringValue(request.getParameter("pageAlias")));
        }
        if (!isMissing(request.getParameter("pageCss"))) {
            m_logger.debug("updating param pageCss from " +_PageStaticConfig.getPageCss() + "->" + request.getParameter("pageCss"));
            _PageStaticConfig.setPageCss(WebParamUtil.getStringValue(request.getParameter("pageCss")));
        }
        if (!isMissing(request.getParameter("pageScript"))) {
            m_logger.debug("updating param pageScript from " +_PageStaticConfig.getPageScript() + "->" + request.getParameter("pageScript"));
            _PageStaticConfig.setPageScript(WebParamUtil.getStringValue(request.getParameter("pageScript")));
        }
        if (!isMissing(request.getParameter("pageCssImports"))) {
            m_logger.debug("updating param pageCssImports from " +_PageStaticConfig.getPageCssImports() + "->" + request.getParameter("pageCssImports"));
            _PageStaticConfig.setPageCssImports(WebParamUtil.getStringValue(request.getParameter("pageCssImports")));
        }
        if (!isMissing(request.getParameter("pageScriptImports"))) {
            m_logger.debug("updating param pageScriptImports from " +_PageStaticConfig.getPageScriptImports() + "->" + request.getParameter("pageScriptImports"));
            _PageStaticConfig.setPageScriptImports(WebParamUtil.getStringValue(request.getParameter("pageScriptImports")));
        }
        if (!isMissing(request.getParameter("hideMenu"))) {
            m_logger.debug("updating param hideMenu from " +_PageStaticConfig.getHideMenu() + "->" + request.getParameter("hideMenu"));
            _PageStaticConfig.setHideMenu(WebParamUtil.getIntValue(request.getParameter("hideMenu")));
        }
        if (!isMissing(request.getParameter("hideMid"))) {
            m_logger.debug("updating param hideMid from " +_PageStaticConfig.getHideMid() + "->" + request.getParameter("hideMid"));
            _PageStaticConfig.setHideMid(WebParamUtil.getIntValue(request.getParameter("hideMid")));
        }
        if (!isMissing(request.getParameter("hideAd"))) {
            m_logger.debug("updating param hideAd from " +_PageStaticConfig.getHideAd() + "->" + request.getParameter("hideAd"));
            _PageStaticConfig.setHideAd(WebParamUtil.getIntValue(request.getParameter("hideAd")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PageStaticConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PageStaticConfig.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_PageStaticConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _PageStaticConfig.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _PageStaticConfig);
        m_ds.update(_PageStaticConfig);
        m_actionExtent.afterUpdate(request, response, _PageStaticConfig);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected PageStaticConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PageStaticConfigAction.class);
}
