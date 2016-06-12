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

import com.autosite.db.BlogCategory;
import com.autosite.ds.BlogCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.BlogCategoryForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class BlogCategoryAction extends AutositeCoreAction {

    public BlogCategoryAction(){
        m_ds = BlogCategoryDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BlogCategoryForm _BlogCategoryForm = (BlogCategoryForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogCategory _BlogCategory = m_ds.getById(cid);

            if (_BlogCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogCategory.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _BlogCategoryForm, _BlogCategory);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "blog_category_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogCategory _BlogCategory = m_ds.getById(cid);

            if (_BlogCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogCategory.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _BlogCategory);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "blog_category");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogCategory _BlogCategory = m_ds.getById(cid);

            if (_BlogCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogCategory.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _BlogCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_BlogCategory);
            try { 
                m_actionExtent.afterDelete(request, response, _BlogCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "blog_category_home");    
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

            m_logger.info("Creating new BlogCategory" );
            BlogCategory _BlogCategory = new BlogCategory();   

            // Setting IDs for the object
            _BlogCategory.setSiteId(site.getId());

            _BlogCategory.setBlogMainId(WebParamUtil.getLongValue(_BlogCategoryForm.getBlogMainId()));
            m_logger.debug("setting BlogMainId=" +_BlogCategoryForm.getBlogMainId());
            _BlogCategory.setParentCategoryId(WebParamUtil.getLongValue(_BlogCategoryForm.getParentCategoryId()));
            m_logger.debug("setting ParentCategoryId=" +_BlogCategoryForm.getParentCategoryId());
            _BlogCategory.setTitle(WebParamUtil.getStringValue(_BlogCategoryForm.getTitle()));
            m_logger.debug("setting Title=" +_BlogCategoryForm.getTitle());
            _BlogCategory.setHide(WebParamUtil.getIntValue(_BlogCategoryForm.getHide()));
            m_logger.debug("setting Hide=" +_BlogCategoryForm.getHide());
            _BlogCategory.setImageUrl1(WebParamUtil.getStringValue(_BlogCategoryForm.getImageUrl1()));
            m_logger.debug("setting ImageUrl1=" +_BlogCategoryForm.getImageUrl1());
            _BlogCategory.setImageUrl2(WebParamUtil.getStringValue(_BlogCategoryForm.getImageUrl2()));
            m_logger.debug("setting ImageUrl2=" +_BlogCategoryForm.getImageUrl2());
            _BlogCategory.setTimeCreated(WebParamUtil.getDateValue(_BlogCategoryForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_BlogCategoryForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _BlogCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_BlogCategory);
            try{
                m_actionExtent.afterAdd(request, response, _BlogCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "blog_category_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, BlogCategoryForm _BlogCategoryForm, BlogCategory _BlogCategory) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogCategory _BlogCategory = m_ds.getById(cid);

        _BlogCategory.setBlogMainId(WebParamUtil.getLongValue(_BlogCategoryForm.getBlogMainId()));
        _BlogCategory.setParentCategoryId(WebParamUtil.getLongValue(_BlogCategoryForm.getParentCategoryId()));
        _BlogCategory.setTitle(WebParamUtil.getStringValue(_BlogCategoryForm.getTitle()));
        _BlogCategory.setHide(WebParamUtil.getIntValue(_BlogCategoryForm.getHide()));
        _BlogCategory.setImageUrl1(WebParamUtil.getStringValue(_BlogCategoryForm.getImageUrl1()));
        _BlogCategory.setImageUrl2(WebParamUtil.getStringValue(_BlogCategoryForm.getImageUrl2()));
        _BlogCategory.setTimeCreated(WebParamUtil.getDateValue(_BlogCategoryForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _BlogCategory);
        m_ds.update(_BlogCategory);
        m_actionExtent.afterUpdate(request, response, _BlogCategory);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, BlogCategory _BlogCategory) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogCategory _BlogCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("blogMainId"))) {
            m_logger.debug("updating param blogMainId from " +_BlogCategory.getBlogMainId() + "->" + request.getParameter("blogMainId"));
                _BlogCategory.setBlogMainId(WebParamUtil.getLongValue(request.getParameter("blogMainId")));
            }
        if (!isMissing(request.getParameter("parentCategoryId"))) {
            m_logger.debug("updating param parentCategoryId from " +_BlogCategory.getParentCategoryId() + "->" + request.getParameter("parentCategoryId"));
                _BlogCategory.setParentCategoryId(WebParamUtil.getLongValue(request.getParameter("parentCategoryId")));
            }
        if (!isMissing(request.getParameter("rootCategoryId"))) {
            m_logger.debug("updating param rootCategoryId from " +_BlogCategory.getRootCategoryId() + "->" + request.getParameter("rootCategoryId"));
                _BlogCategory.setRootCategoryId(WebParamUtil.getLongValue(request.getParameter("rootCategoryId")));
            }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_BlogCategory.getTitle() + "->" + request.getParameter("title"));
                _BlogCategory.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));
            }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_BlogCategory.getHide() + "->" + request.getParameter("hide"));
                _BlogCategory.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
            }
        if (!isMissing(request.getParameter("imageUrl1"))) {
            m_logger.debug("updating param imageUrl1 from " +_BlogCategory.getImageUrl1() + "->" + request.getParameter("imageUrl1"));
                _BlogCategory.setImageUrl1(WebParamUtil.getStringValue(request.getParameter("imageUrl1")));
            }
        if (!isMissing(request.getParameter("imageUrl2"))) {
            m_logger.debug("updating param imageUrl2 from " +_BlogCategory.getImageUrl2() + "->" + request.getParameter("imageUrl2"));
                _BlogCategory.setImageUrl2(WebParamUtil.getStringValue(request.getParameter("imageUrl2")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_BlogCategory.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _BlogCategory.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _BlogCategory);
        m_ds.update(_BlogCategory);
        m_actionExtent.afterUpdate(request, response, _BlogCategory);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected BlogCategoryDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( BlogCategoryAction.class);
}
