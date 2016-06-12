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

import com.autosite.db.ContentCategory;
import com.autosite.ds.ContentCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.ContentCategoryForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class ContentCategoryAction extends AutositeCoreAction {

    public ContentCategoryAction(){
        m_ds = ContentCategoryDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ContentCategoryForm _ContentCategoryForm = (ContentCategoryForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ContentCategory _ContentCategory = m_ds.getById(cid);

            if (_ContentCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentCategory.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _ContentCategoryForm, _ContentCategory);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "content_category_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ContentCategory _ContentCategory = m_ds.getById(cid);

            if (_ContentCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentCategory.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _ContentCategory);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "content_category");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ContentCategory _ContentCategory = m_ds.getById(cid);

            if (_ContentCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentCategory.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _ContentCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_ContentCategory);
            try { 
                m_actionExtent.afterDelete(request, response, _ContentCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "content_category_home");    
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

            m_logger.info("Creating new ContentCategory" );
            ContentCategory _ContentCategory = new ContentCategory();   

            // Setting IDs for the object
            _ContentCategory.setSiteId(site.getId());

            _ContentCategory.setPageId(WebParamUtil.getLongValue(_ContentCategoryForm.getPageId()));
            m_logger.debug("setting PageId=" +_ContentCategoryForm.getPageId());
            _ContentCategory.setCategory(WebParamUtil.getStringValue(_ContentCategoryForm.getCategory()));
            m_logger.debug("setting Category=" +_ContentCategoryForm.getCategory());
            _ContentCategory.setImageUrl(WebParamUtil.getStringValue(_ContentCategoryForm.getImageUrl()));
            m_logger.debug("setting ImageUrl=" +_ContentCategoryForm.getImageUrl());
            _ContentCategory.setTimeCreated(WebParamUtil.getDateValue(_ContentCategoryForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ContentCategoryForm.getTimeCreated());

			if ( m_ds.getByPageIdCategory(_ContentCategory.getPageId(), _ContentCategory.getCategory()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _ContentCategory.getCategory());
                return mapping.findForward("default");
            }
            
            try{
                m_actionExtent.beforeAdd(request, response, _ContentCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_ContentCategory);
            try{
                m_actionExtent.afterAdd(request, response, _ContentCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "content_category_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentCategoryForm _ContentCategoryForm, ContentCategory _ContentCategory) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        ContentCategory _ContentCategory = m_ds.getById(cid);

        m_logger.debug("Before update " + ContentCategoryDS.objectToString(_ContentCategory));

        _ContentCategory.setPageId(WebParamUtil.getLongValue(_ContentCategoryForm.getPageId()));
        _ContentCategory.setCategory(WebParamUtil.getStringValue(_ContentCategoryForm.getCategory()));
        _ContentCategory.setImageUrl(WebParamUtil.getStringValue(_ContentCategoryForm.getImageUrl()));
        _ContentCategory.setTimeCreated(WebParamUtil.getDateValue(_ContentCategoryForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _ContentCategory);
        m_ds.update(_ContentCategory);
        m_actionExtent.afterUpdate(request, response, _ContentCategory);
        m_logger.debug("After update " + ContentCategoryDS.objectToString(_ContentCategory));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ContentCategory _ContentCategory) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        ContentCategory _ContentCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_ContentCategory.getPageId() + "->" + request.getParameter("pageId"));
                _ContentCategory.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
            }
        if (!isMissing(request.getParameter("category"))) {
            m_logger.debug("updating param category from " +_ContentCategory.getCategory() + "->" + request.getParameter("category"));
                _ContentCategory.setCategory(WebParamUtil.getStringValue(request.getParameter("category")));
            }
        if (!isMissing(request.getParameter("imageUrl"))) {
            m_logger.debug("updating param imageUrl from " +_ContentCategory.getImageUrl() + "->" + request.getParameter("imageUrl"));
                _ContentCategory.setImageUrl(WebParamUtil.getStringValue(request.getParameter("imageUrl")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ContentCategory.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _ContentCategory.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _ContentCategory);
        m_ds.update(_ContentCategory);
        m_actionExtent.afterUpdate(request, response, _ContentCategory);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected ContentCategoryDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentCategoryAction.class);
}
