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

import com.autosite.db.Page;
import com.autosite.ds.PageDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.PageForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class PageAction extends AutositeCoreAction {

    public PageAction(){
        m_ds = PageDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PageForm _PageForm = (PageForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            Page _Page = m_ds.getById(cid);

            if (_Page == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Page.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Page.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _PageForm, _Page);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "page_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            Page _Page = m_ds.getById(cid);

            if (_Page == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Page.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Page.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _Page);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "page");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            Page _Page = m_ds.getById(cid);

            if (_Page == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Page.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Page.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _Page);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_Page);
            try { 
                m_actionExtent.afterDelete(request, response, _Page);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "page_home");    
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

            m_logger.info("Creating new Page" );
            Page _Page = new Page();   

            // Setting IDs for the object
            _Page.setSiteId(site.getId());

            _Page.setMenuPanelId(WebParamUtil.getLongValue(_PageForm.getMenuPanelId()));
            m_logger.debug("setting MenuPanelId=" +_PageForm.getMenuPanelId());
            _Page.setParentId(WebParamUtil.getLongValue(_PageForm.getParentId()));
            m_logger.debug("setting ParentId=" +_PageForm.getParentId());
            _Page.setPageName(WebParamUtil.getStringValue(_PageForm.getPageName()));
            m_logger.debug("setting PageName=" +_PageForm.getPageName());
            _Page.setPageMenuTitle(WebParamUtil.getStringValue(_PageForm.getPageMenuTitle()));
            m_logger.debug("setting PageMenuTitle=" +_PageForm.getPageMenuTitle());
            _Page.setHide(WebParamUtil.getIntValue(_PageForm.getHide()));
            m_logger.debug("setting Hide=" +_PageForm.getHide());
            _Page.setCreatedTime(WebParamUtil.getDateValue(_PageForm.getCreatedTime()));
            m_logger.debug("setting CreatedTime=" +_PageForm.getCreatedTime());
            _Page.setSiteUrl(WebParamUtil.getStringValue(_PageForm.getSiteUrl()));
            m_logger.debug("setting SiteUrl=" +_PageForm.getSiteUrl());
            _Page.setPageColCount(WebParamUtil.getIntValue(_PageForm.getPageColCount()));
            m_logger.debug("setting PageColCount=" +_PageForm.getPageColCount());
            _Page.setPageKeywords(WebParamUtil.getStringValue(_PageForm.getPageKeywords()));
            m_logger.debug("setting PageKeywords=" +_PageForm.getPageKeywords());
            _Page.setPageViewType(WebParamUtil.getIntValue(_PageForm.getPageViewType()));
            m_logger.debug("setting PageViewType=" +_PageForm.getPageViewType());
            _Page.setUnderlyingPage(WebParamUtil.getStringValue(_PageForm.getUnderlyingPage()));
            m_logger.debug("setting UnderlyingPage=" +_PageForm.getUnderlyingPage());
            _Page.setHeaderPage(WebParamUtil.getIntValue(_PageForm.getHeaderPage()));
            m_logger.debug("setting HeaderPage=" +_PageForm.getHeaderPage());
            _Page.setShowPageExclusiveOnly(WebParamUtil.getIntValue(_PageForm.getShowPageExclusiveOnly()));
            m_logger.debug("setting ShowPageExclusiveOnly=" +_PageForm.getShowPageExclusiveOnly());
            _Page.setAlt1(WebParamUtil.getStringValue(_PageForm.getAlt1()));
            m_logger.debug("setting Alt1=" +_PageForm.getAlt1());
            _Page.setAlt2(WebParamUtil.getStringValue(_PageForm.getAlt2()));
            m_logger.debug("setting Alt2=" +_PageForm.getAlt2());
            _Page.setAlt3(WebParamUtil.getStringValue(_PageForm.getAlt3()));
            m_logger.debug("setting Alt3=" +_PageForm.getAlt3());

            
            try{
                m_actionExtent.beforeAdd(request, response, _Page);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_Page);
            try{
                m_actionExtent.afterAdd(request, response, _Page);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "page_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, PageForm _PageForm, Page _Page) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        Page _Page = m_ds.getById(cid);

        _Page.setMenuPanelId(WebParamUtil.getLongValue(_PageForm.getMenuPanelId()));
        _Page.setParentId(WebParamUtil.getLongValue(_PageForm.getParentId()));
        _Page.setPageName(WebParamUtil.getStringValue(_PageForm.getPageName()));
        _Page.setPageMenuTitle(WebParamUtil.getStringValue(_PageForm.getPageMenuTitle()));
        _Page.setHide(WebParamUtil.getIntValue(_PageForm.getHide()));
        _Page.setCreatedTime(WebParamUtil.getDateValue(_PageForm.getCreatedTime()));
        _Page.setSiteUrl(WebParamUtil.getStringValue(_PageForm.getSiteUrl()));
        _Page.setPageColCount(WebParamUtil.getIntValue(_PageForm.getPageColCount()));
        _Page.setPageKeywords(WebParamUtil.getStringValue(_PageForm.getPageKeywords()));
        _Page.setPageViewType(WebParamUtil.getIntValue(_PageForm.getPageViewType()));
        _Page.setUnderlyingPage(WebParamUtil.getStringValue(_PageForm.getUnderlyingPage()));
        _Page.setHeaderPage(WebParamUtil.getIntValue(_PageForm.getHeaderPage()));
        _Page.setShowPageExclusiveOnly(WebParamUtil.getIntValue(_PageForm.getShowPageExclusiveOnly()));
        _Page.setAlt1(WebParamUtil.getStringValue(_PageForm.getAlt1()));
        _Page.setAlt2(WebParamUtil.getStringValue(_PageForm.getAlt2()));
        _Page.setAlt3(WebParamUtil.getStringValue(_PageForm.getAlt3()));

        m_actionExtent.beforeUpdate(request, response, _Page);
        m_ds.update(_Page);
        m_actionExtent.afterUpdate(request, response, _Page);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, Page _Page) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        Page _Page = m_ds.getById(cid);

        if (!isMissing(request.getParameter("menuPanelId"))) {
            m_logger.debug("updating param menuPanelId from " +_Page.getMenuPanelId() + "->" + request.getParameter("menuPanelId"));
                _Page.setMenuPanelId(WebParamUtil.getLongValue(request.getParameter("menuPanelId")));
            }
        if (!isMissing(request.getParameter("parentId"))) {
            m_logger.debug("updating param parentId from " +_Page.getParentId() + "->" + request.getParameter("parentId"));
                _Page.setParentId(WebParamUtil.getLongValue(request.getParameter("parentId")));
            }
        if (!isMissing(request.getParameter("pageName"))) {
            m_logger.debug("updating param pageName from " +_Page.getPageName() + "->" + request.getParameter("pageName"));
                _Page.setPageName(WebParamUtil.getStringValue(request.getParameter("pageName")));
            }
        if (!isMissing(request.getParameter("pageMenuTitle"))) {
            m_logger.debug("updating param pageMenuTitle from " +_Page.getPageMenuTitle() + "->" + request.getParameter("pageMenuTitle"));
                _Page.setPageMenuTitle(WebParamUtil.getStringValue(request.getParameter("pageMenuTitle")));
            }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_Page.getHide() + "->" + request.getParameter("hide"));
                _Page.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
            }
        if (!isMissing(request.getParameter("createdTime"))) {
            m_logger.debug("updating param createdTime from " +_Page.getCreatedTime() + "->" + request.getParameter("createdTime"));
                _Page.setCreatedTime(WebParamUtil.getDateValue(request.getParameter("createdTime")));
            }
        if (!isMissing(request.getParameter("siteUrl"))) {
            m_logger.debug("updating param siteUrl from " +_Page.getSiteUrl() + "->" + request.getParameter("siteUrl"));
                _Page.setSiteUrl(WebParamUtil.getStringValue(request.getParameter("siteUrl")));
            }
        if (!isMissing(request.getParameter("pageColCount"))) {
            m_logger.debug("updating param pageColCount from " +_Page.getPageColCount() + "->" + request.getParameter("pageColCount"));
                _Page.setPageColCount(WebParamUtil.getIntValue(request.getParameter("pageColCount")));
            }
        if (!isMissing(request.getParameter("pageKeywords"))) {
            m_logger.debug("updating param pageKeywords from " +_Page.getPageKeywords() + "->" + request.getParameter("pageKeywords"));
                _Page.setPageKeywords(WebParamUtil.getStringValue(request.getParameter("pageKeywords")));
            }
        if (!isMissing(request.getParameter("pageViewType"))) {
            m_logger.debug("updating param pageViewType from " +_Page.getPageViewType() + "->" + request.getParameter("pageViewType"));
                _Page.setPageViewType(WebParamUtil.getIntValue(request.getParameter("pageViewType")));
            }
        if (!isMissing(request.getParameter("underlyingPage"))) {
            m_logger.debug("updating param underlyingPage from " +_Page.getUnderlyingPage() + "->" + request.getParameter("underlyingPage"));
                _Page.setUnderlyingPage(WebParamUtil.getStringValue(request.getParameter("underlyingPage")));
            }
        if (!isMissing(request.getParameter("headerPage"))) {
            m_logger.debug("updating param headerPage from " +_Page.getHeaderPage() + "->" + request.getParameter("headerPage"));
                _Page.setHeaderPage(WebParamUtil.getIntValue(request.getParameter("headerPage")));
            }
        if (!isMissing(request.getParameter("showPageExclusiveOnly"))) {
            m_logger.debug("updating param showPageExclusiveOnly from " +_Page.getShowPageExclusiveOnly() + "->" + request.getParameter("showPageExclusiveOnly"));
                _Page.setShowPageExclusiveOnly(WebParamUtil.getIntValue(request.getParameter("showPageExclusiveOnly")));
            }
        if (!isMissing(request.getParameter("alt1"))) {
            m_logger.debug("updating param alt1 from " +_Page.getAlt1() + "->" + request.getParameter("alt1"));
                _Page.setAlt1(WebParamUtil.getStringValue(request.getParameter("alt1")));
            }
        if (!isMissing(request.getParameter("alt2"))) {
            m_logger.debug("updating param alt2 from " +_Page.getAlt2() + "->" + request.getParameter("alt2"));
                _Page.setAlt2(WebParamUtil.getStringValue(request.getParameter("alt2")));
            }
        if (!isMissing(request.getParameter("alt3"))) {
            m_logger.debug("updating param alt3 from " +_Page.getAlt3() + "->" + request.getParameter("alt3"));
                _Page.setAlt3(WebParamUtil.getStringValue(request.getParameter("alt3")));
            }

        m_actionExtent.beforeUpdate(request, response, _Page);
        m_ds.update(_Page);
        m_actionExtent.afterUpdate(request, response, _Page);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected PageDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PageAction.class);
}
