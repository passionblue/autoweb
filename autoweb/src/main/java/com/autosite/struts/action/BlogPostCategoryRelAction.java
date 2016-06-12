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

import com.autosite.db.BlogPostCategoryRel;
import com.autosite.ds.BlogPostCategoryRelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.BlogPostCategoryRelForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class BlogPostCategoryRelAction extends AutositeCoreAction {

    public BlogPostCategoryRelAction(){
        m_ds = BlogPostCategoryRelDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BlogPostCategoryRelForm _BlogPostCategoryRelForm = (BlogPostCategoryRelForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogPostCategoryRel _BlogPostCategoryRel = m_ds.getById(cid);

            if (_BlogPostCategoryRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogPostCategoryRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogPostCategoryRel.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _BlogPostCategoryRelForm, _BlogPostCategoryRel);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "blog_post_category_rel_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogPostCategoryRel _BlogPostCategoryRel = m_ds.getById(cid);

            if (_BlogPostCategoryRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogPostCategoryRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogPostCategoryRel.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _BlogPostCategoryRel);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "blog_post_category_rel");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogPostCategoryRel _BlogPostCategoryRel = m_ds.getById(cid);

            if (_BlogPostCategoryRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogPostCategoryRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogPostCategoryRel.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _BlogPostCategoryRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_BlogPostCategoryRel);
            try { 
                m_actionExtent.afterDelete(request, response, _BlogPostCategoryRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "blog_post_category_rel_home");    
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

            m_logger.info("Creating new BlogPostCategoryRel" );
            BlogPostCategoryRel _BlogPostCategoryRel = new BlogPostCategoryRel();   

            // Setting IDs for the object
            _BlogPostCategoryRel.setSiteId(site.getId());

            _BlogPostCategoryRel.setBlogPostId(WebParamUtil.getLongValue(_BlogPostCategoryRelForm.getBlogPostId()));
            m_logger.debug("setting BlogPostId=" +_BlogPostCategoryRelForm.getBlogPostId());
            _BlogPostCategoryRel.setBlogCategoryId(WebParamUtil.getLongValue(_BlogPostCategoryRelForm.getBlogCategoryId()));
            m_logger.debug("setting BlogCategoryId=" +_BlogPostCategoryRelForm.getBlogCategoryId());
            _BlogPostCategoryRel.setTimeCreated(WebParamUtil.getDateValue(_BlogPostCategoryRelForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_BlogPostCategoryRelForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _BlogPostCategoryRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_BlogPostCategoryRel);
            try{
                m_actionExtent.afterAdd(request, response, _BlogPostCategoryRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "blog_post_category_rel_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, BlogPostCategoryRelForm _BlogPostCategoryRelForm, BlogPostCategoryRel _BlogPostCategoryRel) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogPostCategoryRel _BlogPostCategoryRel = m_ds.getById(cid);

        m_logger.debug("Before update " + BlogPostCategoryRelDS.objectToString(_BlogPostCategoryRel));

        _BlogPostCategoryRel.setTimeCreated(WebParamUtil.getDateValue(_BlogPostCategoryRelForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _BlogPostCategoryRel);
        m_ds.update(_BlogPostCategoryRel);
        m_actionExtent.afterUpdate(request, response, _BlogPostCategoryRel);
        m_logger.debug("After update " + BlogPostCategoryRelDS.objectToString(_BlogPostCategoryRel));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, BlogPostCategoryRel _BlogPostCategoryRel) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogPostCategoryRel _BlogPostCategoryRel = m_ds.getById(cid);

        if (!isMissing(request.getParameter("blogPostId"))) {
            m_logger.debug("updating param blogPostId from " +_BlogPostCategoryRel.getBlogPostId() + "->" + request.getParameter("blogPostId"));
                _BlogPostCategoryRel.setBlogPostId(WebParamUtil.getLongValue(request.getParameter("blogPostId")));
            }
        if (!isMissing(request.getParameter("blogCategoryId"))) {
            m_logger.debug("updating param blogCategoryId from " +_BlogPostCategoryRel.getBlogCategoryId() + "->" + request.getParameter("blogCategoryId"));
                _BlogPostCategoryRel.setBlogCategoryId(WebParamUtil.getLongValue(request.getParameter("blogCategoryId")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_BlogPostCategoryRel.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _BlogPostCategoryRel.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _BlogPostCategoryRel);
        m_ds.update(_BlogPostCategoryRel);
        m_actionExtent.afterUpdate(request, response, _BlogPostCategoryRel);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected BlogPostCategoryRelDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( BlogPostCategoryRelAction.class);
}
