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

import com.autosite.db.BlogMain;
import com.autosite.ds.BlogMainDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.BlogMainForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class BlogMainAction extends AutositeCoreAction {

    public BlogMainAction(){
        m_ds = BlogMainDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BlogMainForm _BlogMainForm = (BlogMainForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogMain _BlogMain = m_ds.getById(cid);

            if (_BlogMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogMain.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _BlogMainForm, _BlogMain);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "blog_main_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogMain _BlogMain = m_ds.getById(cid);

            if (_BlogMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogMain.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _BlogMain);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "blog_main");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogMain _BlogMain = m_ds.getById(cid);

            if (_BlogMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogMain.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _BlogMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_BlogMain);
            try { 
                m_actionExtent.afterDelete(request, response, _BlogMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "blog_main_home");    
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

            m_logger.info("Creating new BlogMain" );
            BlogMain _BlogMain = new BlogMain();   

            // Setting IDs for the object
            _BlogMain.setSiteId(site.getId());

            _BlogMain.setUserId(WebParamUtil.getLongValue(_BlogMainForm.getUserId()));
            m_logger.debug("setting UserId=" +_BlogMainForm.getUserId());
            _BlogMain.setBlogName(WebParamUtil.getStringValue(_BlogMainForm.getBlogName()));
            m_logger.debug("setting BlogName=" +_BlogMainForm.getBlogName());
            _BlogMain.setBlogTitle(WebParamUtil.getStringValue(_BlogMainForm.getBlogTitle()));
            m_logger.debug("setting BlogTitle=" +_BlogMainForm.getBlogTitle());
            _BlogMain.setTimeCreated(WebParamUtil.getDateValue(_BlogMainForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_BlogMainForm.getTimeCreated());
            _BlogMain.setMainDesignSelect(WebParamUtil.getIntValue(_BlogMainForm.getMainDesignSelect()));
            m_logger.debug("setting MainDesignSelect=" +_BlogMainForm.getMainDesignSelect());
            _BlogMain.setUseCustomDesign(WebParamUtil.getIntValue(_BlogMainForm.getUseCustomDesign()));
            m_logger.debug("setting UseCustomDesign=" +_BlogMainForm.getUseCustomDesign());
            _BlogMain.setCustomDesignFile(WebParamUtil.getStringValue(_BlogMainForm.getCustomDesignFile()));
            m_logger.debug("setting CustomDesignFile=" +_BlogMainForm.getCustomDesignFile());

            
            try{
                m_actionExtent.beforeAdd(request, response, _BlogMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_BlogMain);
            try{
                m_actionExtent.afterAdd(request, response, _BlogMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "blog_main_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, BlogMainForm _BlogMainForm, BlogMain _BlogMain) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogMain _BlogMain = m_ds.getById(cid);

        _BlogMain.setUserId(WebParamUtil.getLongValue(_BlogMainForm.getUserId()));
        _BlogMain.setBlogName(WebParamUtil.getStringValue(_BlogMainForm.getBlogName()));
        _BlogMain.setBlogTitle(WebParamUtil.getStringValue(_BlogMainForm.getBlogTitle()));
        _BlogMain.setTimeCreated(WebParamUtil.getDateValue(_BlogMainForm.getTimeCreated()));
        _BlogMain.setMainDesignSelect(WebParamUtil.getIntValue(_BlogMainForm.getMainDesignSelect()));
        _BlogMain.setUseCustomDesign(WebParamUtil.getIntValue(_BlogMainForm.getUseCustomDesign()));
        _BlogMain.setCustomDesignFile(WebParamUtil.getStringValue(_BlogMainForm.getCustomDesignFile()));

        m_actionExtent.beforeUpdate(request, response, _BlogMain);
        m_ds.update(_BlogMain);
        m_actionExtent.afterUpdate(request, response, _BlogMain);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, BlogMain _BlogMain) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogMain _BlogMain = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_BlogMain.getUserId() + "->" + request.getParameter("userId"));
                _BlogMain.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("blogName"))) {
            m_logger.debug("updating param blogName from " +_BlogMain.getBlogName() + "->" + request.getParameter("blogName"));
                _BlogMain.setBlogName(WebParamUtil.getStringValue(request.getParameter("blogName")));
            }
        if (!isMissing(request.getParameter("blogTitle"))) {
            m_logger.debug("updating param blogTitle from " +_BlogMain.getBlogTitle() + "->" + request.getParameter("blogTitle"));
                _BlogMain.setBlogTitle(WebParamUtil.getStringValue(request.getParameter("blogTitle")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_BlogMain.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _BlogMain.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }
        if (!isMissing(request.getParameter("mainDesignSelect"))) {
            m_logger.debug("updating param mainDesignSelect from " +_BlogMain.getMainDesignSelect() + "->" + request.getParameter("mainDesignSelect"));
                _BlogMain.setMainDesignSelect(WebParamUtil.getIntValue(request.getParameter("mainDesignSelect")));
            }
        if (!isMissing(request.getParameter("useCustomDesign"))) {
            m_logger.debug("updating param useCustomDesign from " +_BlogMain.getUseCustomDesign() + "->" + request.getParameter("useCustomDesign"));
                _BlogMain.setUseCustomDesign(WebParamUtil.getIntValue(request.getParameter("useCustomDesign")));
            }
        if (!isMissing(request.getParameter("customDesignFile"))) {
            m_logger.debug("updating param customDesignFile from " +_BlogMain.getCustomDesignFile() + "->" + request.getParameter("customDesignFile"));
                _BlogMain.setCustomDesignFile(WebParamUtil.getStringValue(request.getParameter("customDesignFile")));
            }

        m_actionExtent.beforeUpdate(request, response, _BlogMain);
        m_ds.update(_BlogMain);
        m_actionExtent.afterUpdate(request, response, _BlogMain);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected BlogMainDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( BlogMainAction.class);
}
