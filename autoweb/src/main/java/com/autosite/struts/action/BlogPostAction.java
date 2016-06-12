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

import com.autosite.db.BlogPost;
import com.autosite.ds.BlogPostDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.BlogPostForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class BlogPostAction extends AutositeCoreAction {

    public BlogPostAction(){
        m_ds = BlogPostDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BlogPostForm _BlogPostForm = (BlogPostForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogPost _BlogPost = m_ds.getById(cid);

            if (_BlogPost == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogPost.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogPost.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _BlogPostForm, _BlogPost);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "blog_post_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogPost _BlogPost = m_ds.getById(cid);

            if (_BlogPost == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogPost.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogPost.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _BlogPost);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "blog_post");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogPost _BlogPost = m_ds.getById(cid);

            if (_BlogPost == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogPost.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogPost.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _BlogPost);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_BlogPost);
            try { 
                m_actionExtent.afterDelete(request, response, _BlogPost);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "blog_post_home");    
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

            m_logger.info("Creating new BlogPost" );
            BlogPost _BlogPost = new BlogPost();   

            // Setting IDs for the object
            _BlogPost.setSiteId(site.getId());

            _BlogPost.setBlogMainId(WebParamUtil.getLongValue(_BlogPostForm.getBlogMainId()));
            m_logger.debug("setting BlogMainId=" +_BlogPostForm.getBlogMainId());
            _BlogPost.setCategoryId(WebParamUtil.getLongValue(_BlogPostForm.getCategoryId()));
            m_logger.debug("setting CategoryId=" +_BlogPostForm.getCategoryId());
            _BlogPost.setSubject(WebParamUtil.getStringValue(_BlogPostForm.getSubject()));
            m_logger.debug("setting Subject=" +_BlogPostForm.getSubject());
            _BlogPost.setContent(WebParamUtil.getStringValue(_BlogPostForm.getContent()));
            m_logger.debug("setting Content=" +_BlogPostForm.getContent());
            _BlogPost.setPostType(WebParamUtil.getIntValue(_BlogPostForm.getPostType()));
            m_logger.debug("setting PostType=" +_BlogPostForm.getPostType());
            _BlogPost.setAuthor(WebParamUtil.getStringValue(_BlogPostForm.getAuthor()));
            m_logger.debug("setting Author=" +_BlogPostForm.getAuthor());
            _BlogPost.setContentImage(WebParamUtil.getStringValue(_BlogPostForm.getContentImage()));
            m_logger.debug("setting ContentImage=" +_BlogPostForm.getContentImage());
            _BlogPost.setImageUrl1(WebParamUtil.getStringValue(_BlogPostForm.getImageUrl1()));
            m_logger.debug("setting ImageUrl1=" +_BlogPostForm.getImageUrl1());
            _BlogPost.setImageUrl2(WebParamUtil.getStringValue(_BlogPostForm.getImageUrl2()));
            m_logger.debug("setting ImageUrl2=" +_BlogPostForm.getImageUrl2());
            _BlogPost.setTags(WebParamUtil.getStringValue(_BlogPostForm.getTags()));
            m_logger.debug("setting Tags=" +_BlogPostForm.getTags());
            _BlogPost.setShorcutUrl(WebParamUtil.getStringValue(_BlogPostForm.getShorcutUrl()));
            m_logger.debug("setting ShorcutUrl=" +_BlogPostForm.getShorcutUrl());
            _BlogPost.setHide(WebParamUtil.getIntValue(_BlogPostForm.getHide()));
            m_logger.debug("setting Hide=" +_BlogPostForm.getHide());
            _BlogPost.setTimeCreated(WebParamUtil.getDateValue(_BlogPostForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_BlogPostForm.getTimeCreated());
            _BlogPost.setTimeUpdated(WebParamUtil.getDateValue(_BlogPostForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_BlogPostForm.getTimeUpdated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _BlogPost);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_BlogPost);
            try{
                m_actionExtent.afterAdd(request, response, _BlogPost);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "blog_post_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, BlogPostForm _BlogPostForm, BlogPost _BlogPost) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogPost _BlogPost = m_ds.getById(cid);

        m_logger.debug("Before update " + BlogPostDS.objectToString(_BlogPost));

        _BlogPost.setCategoryId(WebParamUtil.getLongValue(_BlogPostForm.getCategoryId()));
        _BlogPost.setSubject(WebParamUtil.getStringValue(_BlogPostForm.getSubject()));
        _BlogPost.setContent(WebParamUtil.getStringValue(_BlogPostForm.getContent()));
        _BlogPost.setPostType(WebParamUtil.getIntValue(_BlogPostForm.getPostType()));
        _BlogPost.setAuthor(WebParamUtil.getStringValue(_BlogPostForm.getAuthor()));
        _BlogPost.setContentImage(WebParamUtil.getStringValue(_BlogPostForm.getContentImage()));
        _BlogPost.setImageUrl1(WebParamUtil.getStringValue(_BlogPostForm.getImageUrl1()));
        _BlogPost.setImageUrl2(WebParamUtil.getStringValue(_BlogPostForm.getImageUrl2()));
        _BlogPost.setTags(WebParamUtil.getStringValue(_BlogPostForm.getTags()));
        _BlogPost.setShorcutUrl(WebParamUtil.getStringValue(_BlogPostForm.getShorcutUrl()));
        _BlogPost.setHide(WebParamUtil.getIntValue(_BlogPostForm.getHide()));
        _BlogPost.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _BlogPost);
        m_ds.update(_BlogPost);
        m_actionExtent.afterUpdate(request, response, _BlogPost);
        m_logger.debug("After update " + BlogPostDS.objectToString(_BlogPost));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, BlogPost _BlogPost) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogPost _BlogPost = m_ds.getById(cid);

        if (!isMissing(request.getParameter("blogMainId"))) {
            m_logger.debug("updating param blogMainId from " +_BlogPost.getBlogMainId() + "->" + request.getParameter("blogMainId"));
                _BlogPost.setBlogMainId(WebParamUtil.getLongValue(request.getParameter("blogMainId")));
            }
        if (!isMissing(request.getParameter("categoryId"))) {
            m_logger.debug("updating param categoryId from " +_BlogPost.getCategoryId() + "->" + request.getParameter("categoryId"));
                _BlogPost.setCategoryId(WebParamUtil.getLongValue(request.getParameter("categoryId")));
            }
        if (!isMissing(request.getParameter("subject"))) {
            m_logger.debug("updating param subject from " +_BlogPost.getSubject() + "->" + request.getParameter("subject"));
                _BlogPost.setSubject(WebParamUtil.getStringValue(request.getParameter("subject")));
            }
        if (!isMissing(request.getParameter("content"))) {
            m_logger.debug("updating param content from " +_BlogPost.getContent() + "->" + request.getParameter("content"));
                _BlogPost.setContent(WebParamUtil.getStringValue(request.getParameter("content")));
            }
        if (!isMissing(request.getParameter("postType"))) {
            m_logger.debug("updating param postType from " +_BlogPost.getPostType() + "->" + request.getParameter("postType"));
                _BlogPost.setPostType(WebParamUtil.getIntValue(request.getParameter("postType")));
            }
        if (!isMissing(request.getParameter("author"))) {
            m_logger.debug("updating param author from " +_BlogPost.getAuthor() + "->" + request.getParameter("author"));
                _BlogPost.setAuthor(WebParamUtil.getStringValue(request.getParameter("author")));
            }
        if (!isMissing(request.getParameter("contentImage"))) {
            m_logger.debug("updating param contentImage from " +_BlogPost.getContentImage() + "->" + request.getParameter("contentImage"));
                _BlogPost.setContentImage(WebParamUtil.getStringValue(request.getParameter("contentImage")));
            }
        if (!isMissing(request.getParameter("imageUrl1"))) {
            m_logger.debug("updating param imageUrl1 from " +_BlogPost.getImageUrl1() + "->" + request.getParameter("imageUrl1"));
                _BlogPost.setImageUrl1(WebParamUtil.getStringValue(request.getParameter("imageUrl1")));
            }
        if (!isMissing(request.getParameter("imageUrl2"))) {
            m_logger.debug("updating param imageUrl2 from " +_BlogPost.getImageUrl2() + "->" + request.getParameter("imageUrl2"));
                _BlogPost.setImageUrl2(WebParamUtil.getStringValue(request.getParameter("imageUrl2")));
            }
        if (!isMissing(request.getParameter("tags"))) {
            m_logger.debug("updating param tags from " +_BlogPost.getTags() + "->" + request.getParameter("tags"));
                _BlogPost.setTags(WebParamUtil.getStringValue(request.getParameter("tags")));
            }
        if (!isMissing(request.getParameter("shorcutUrl"))) {
            m_logger.debug("updating param shorcutUrl from " +_BlogPost.getShorcutUrl() + "->" + request.getParameter("shorcutUrl"));
                _BlogPost.setShorcutUrl(WebParamUtil.getStringValue(request.getParameter("shorcutUrl")));
            }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_BlogPost.getHide() + "->" + request.getParameter("hide"));
                _BlogPost.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
            }
        if (!isMissing(request.getParameter("postYear"))) {
            m_logger.debug("updating param postYear from " +_BlogPost.getPostYear() + "->" + request.getParameter("postYear"));
                _BlogPost.setPostYear(WebParamUtil.getIntValue(request.getParameter("postYear")));
            }
        if (!isMissing(request.getParameter("postMonth"))) {
            m_logger.debug("updating param postMonth from " +_BlogPost.getPostMonth() + "->" + request.getParameter("postMonth"));
                _BlogPost.setPostMonth(WebParamUtil.getIntValue(request.getParameter("postMonth")));
            }
        if (!isMissing(request.getParameter("postDay"))) {
            m_logger.debug("updating param postDay from " +_BlogPost.getPostDay() + "->" + request.getParameter("postDay"));
                _BlogPost.setPostDay(WebParamUtil.getIntValue(request.getParameter("postDay")));
            }
        if (!isMissing(request.getParameter("postYearmonth"))) {
            m_logger.debug("updating param postYearmonth from " +_BlogPost.getPostYearmonth() + "->" + request.getParameter("postYearmonth"));
                _BlogPost.setPostYearmonth(WebParamUtil.getIntValue(request.getParameter("postYearmonth")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_BlogPost.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _BlogPost.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_BlogPost.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
                _BlogPost.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
            }

        m_actionExtent.beforeUpdate(request, response, _BlogPost);
        m_ds.update(_BlogPost);
        m_actionExtent.afterUpdate(request, response, _BlogPost);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected BlogPostDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( BlogPostAction.class);
}
