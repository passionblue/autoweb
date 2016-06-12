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

import com.autosite.db.PageStaticAlt;
import com.autosite.ds.PageStaticAltDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.PageStaticAltForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PageStaticAltAction extends AutositeCoreAction {

    public PageStaticAltAction(){
        m_ds = PageStaticAltDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PageStaticAltForm _PageStaticAltForm = (PageStaticAltForm) form;
        HttpSession session = request.getSession();

        setPage(session, "page_static_alt_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
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
            PageStaticAlt _PageStaticAlt = m_ds.getById(cid);

            if (_PageStaticAlt == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStaticAlt.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStaticAlt.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _PageStaticAltForm, _PageStaticAlt);
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
            PageStaticAlt _PageStaticAlt = m_ds.getById(cid);

            if (_PageStaticAlt == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStaticAlt.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStaticAlt.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PageStaticAlt);
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
            PageStaticAlt _PageStaticAlt = m_ds.getById(cid);

            if (_PageStaticAlt == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStaticAlt.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStaticAlt.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PageStaticAlt);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PageStaticAlt);
            try { 
                m_actionExtent.afterDelete(request, response, _PageStaticAlt);
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

            m_logger.info("Creating new PageStaticAlt" );
            PageStaticAlt _PageStaticAlt = new PageStaticAlt();   

            // Setting IDs for the object
            _PageStaticAlt.setSiteId(site.getId());

            _PageStaticAlt.setPageAlias(WebParamUtil.getStringValue(_PageStaticAltForm.getPageAlias()));
            m_logger.debug("setting PageAlias=" +_PageStaticAltForm.getPageAlias());
            _PageStaticAlt.setPageUrl(WebParamUtil.getStringValue(_PageStaticAltForm.getPageUrl()));
            m_logger.debug("setting PageUrl=" +_PageStaticAltForm.getPageUrl());
            _PageStaticAlt.setMenuPage(WebParamUtil.getStringValue(_PageStaticAltForm.getMenuPage()));
            m_logger.debug("setting MenuPage=" +_PageStaticAltForm.getMenuPage());
            _PageStaticAlt.setErrorPage(WebParamUtil.getStringValue(_PageStaticAltForm.getErrorPage()));
            m_logger.debug("setting ErrorPage=" +_PageStaticAltForm.getErrorPage());
            _PageStaticAlt.setLoginRequired(WebParamUtil.getIntValue(_PageStaticAltForm.getLoginRequired()));
            m_logger.debug("setting LoginRequired=" +_PageStaticAltForm.getLoginRequired());
            _PageStaticAlt.setViewProc(WebParamUtil.getStringValue(_PageStaticAltForm.getViewProc()));
            m_logger.debug("setting ViewProc=" +_PageStaticAltForm.getViewProc());
            _PageStaticAlt.setDynamicContent(WebParamUtil.getIntValue(_PageStaticAltForm.getDynamicContent()));
            m_logger.debug("setting DynamicContent=" +_PageStaticAltForm.getDynamicContent());
            _PageStaticAlt.setHideMenu(WebParamUtil.getIntValue(_PageStaticAltForm.getHideMenu()));
            m_logger.debug("setting HideMenu=" +_PageStaticAltForm.getHideMenu());
            _PageStaticAlt.setHideMid(WebParamUtil.getIntValue(_PageStaticAltForm.getHideMid()));
            m_logger.debug("setting HideMid=" +_PageStaticAltForm.getHideMid());
            _PageStaticAlt.setHideAd(WebParamUtil.getIntValue(_PageStaticAltForm.getHideAd()));
            m_logger.debug("setting HideAd=" +_PageStaticAltForm.getHideAd());
            _PageStaticAlt.setTimeCreated(WebParamUtil.getDateValue(_PageStaticAltForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PageStaticAltForm.getTimeCreated());

			// Checking the uniqueness by configuration. 
			if ( m_ds.getBySiteIdPageAlias(_PageStaticAlt.getSiteId(), _PageStaticAlt.getPageAlias()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _PageStaticAlt.getPageAlias());
                return mapping.findForward("default");
            }
            
            try{
                m_actionExtent.beforeAdd(request, response, _PageStaticAlt);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_PageStaticAlt);
            try{
                m_actionExtent.afterAdd(request, response, _PageStaticAlt);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "page_static_alt_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PageStaticAltForm _PageStaticAltForm, PageStaticAlt _PageStaticAlt) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageStaticAlt _PageStaticAlt = m_ds.getById(cid);

        m_logger.debug("Before update " + PageStaticAltDS.objectToString(_PageStaticAlt));

        _PageStaticAlt.setPageAlias(WebParamUtil.getStringValue(_PageStaticAltForm.getPageAlias()));
        _PageStaticAlt.setPageUrl(WebParamUtil.getStringValue(_PageStaticAltForm.getPageUrl()));
        _PageStaticAlt.setMenuPage(WebParamUtil.getStringValue(_PageStaticAltForm.getMenuPage()));
        _PageStaticAlt.setErrorPage(WebParamUtil.getStringValue(_PageStaticAltForm.getErrorPage()));
        _PageStaticAlt.setLoginRequired(WebParamUtil.getIntValue(_PageStaticAltForm.getLoginRequired()));
        _PageStaticAlt.setViewProc(WebParamUtil.getStringValue(_PageStaticAltForm.getViewProc()));
        _PageStaticAlt.setDynamicContent(WebParamUtil.getIntValue(_PageStaticAltForm.getDynamicContent()));
        _PageStaticAlt.setHideMenu(WebParamUtil.getIntValue(_PageStaticAltForm.getHideMenu()));
        _PageStaticAlt.setHideMid(WebParamUtil.getIntValue(_PageStaticAltForm.getHideMid()));
        _PageStaticAlt.setHideAd(WebParamUtil.getIntValue(_PageStaticAltForm.getHideAd()));
        _PageStaticAlt.setTimeCreated(WebParamUtil.getDateValue(_PageStaticAltForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _PageStaticAlt);
        m_ds.update(_PageStaticAlt);
        m_actionExtent.afterUpdate(request, response, _PageStaticAlt);
        m_logger.debug("After update " + PageStaticAltDS.objectToString(_PageStaticAlt));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PageStaticAlt _PageStaticAlt) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageStaticAlt _PageStaticAlt = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageAlias"))) {
            m_logger.debug("updating param pageAlias from " +_PageStaticAlt.getPageAlias() + "->" + request.getParameter("pageAlias"));
                _PageStaticAlt.setPageAlias(WebParamUtil.getStringValue(request.getParameter("pageAlias")));
            }
        if (!isMissing(request.getParameter("pageUrl"))) {
            m_logger.debug("updating param pageUrl from " +_PageStaticAlt.getPageUrl() + "->" + request.getParameter("pageUrl"));
                _PageStaticAlt.setPageUrl(WebParamUtil.getStringValue(request.getParameter("pageUrl")));
            }
        if (!isMissing(request.getParameter("menuPage"))) {
            m_logger.debug("updating param menuPage from " +_PageStaticAlt.getMenuPage() + "->" + request.getParameter("menuPage"));
                _PageStaticAlt.setMenuPage(WebParamUtil.getStringValue(request.getParameter("menuPage")));
            }
        if (!isMissing(request.getParameter("errorPage"))) {
            m_logger.debug("updating param errorPage from " +_PageStaticAlt.getErrorPage() + "->" + request.getParameter("errorPage"));
                _PageStaticAlt.setErrorPage(WebParamUtil.getStringValue(request.getParameter("errorPage")));
            }
        if (!isMissing(request.getParameter("loginRequired"))) {
            m_logger.debug("updating param loginRequired from " +_PageStaticAlt.getLoginRequired() + "->" + request.getParameter("loginRequired"));
                _PageStaticAlt.setLoginRequired(WebParamUtil.getIntValue(request.getParameter("loginRequired")));
            }
        if (!isMissing(request.getParameter("viewProc"))) {
            m_logger.debug("updating param viewProc from " +_PageStaticAlt.getViewProc() + "->" + request.getParameter("viewProc"));
                _PageStaticAlt.setViewProc(WebParamUtil.getStringValue(request.getParameter("viewProc")));
            }
        if (!isMissing(request.getParameter("dynamicContent"))) {
            m_logger.debug("updating param dynamicContent from " +_PageStaticAlt.getDynamicContent() + "->" + request.getParameter("dynamicContent"));
                _PageStaticAlt.setDynamicContent(WebParamUtil.getIntValue(request.getParameter("dynamicContent")));
            }
        if (!isMissing(request.getParameter("hideMenu"))) {
            m_logger.debug("updating param hideMenu from " +_PageStaticAlt.getHideMenu() + "->" + request.getParameter("hideMenu"));
                _PageStaticAlt.setHideMenu(WebParamUtil.getIntValue(request.getParameter("hideMenu")));
            }
        if (!isMissing(request.getParameter("hideMid"))) {
            m_logger.debug("updating param hideMid from " +_PageStaticAlt.getHideMid() + "->" + request.getParameter("hideMid"));
                _PageStaticAlt.setHideMid(WebParamUtil.getIntValue(request.getParameter("hideMid")));
            }
        if (!isMissing(request.getParameter("hideAd"))) {
            m_logger.debug("updating param hideAd from " +_PageStaticAlt.getHideAd() + "->" + request.getParameter("hideAd"));
                _PageStaticAlt.setHideAd(WebParamUtil.getIntValue(request.getParameter("hideAd")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PageStaticAlt.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _PageStaticAlt.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _PageStaticAlt);
        m_ds.update(_PageStaticAlt);
        m_actionExtent.afterUpdate(request, response, _PageStaticAlt);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected PageStaticAltDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PageStaticAltAction.class);
}
