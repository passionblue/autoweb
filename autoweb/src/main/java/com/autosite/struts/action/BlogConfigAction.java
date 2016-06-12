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

import com.autosite.db.BlogConfig;
import com.autosite.ds.BlogConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.BlogConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class BlogConfigAction extends AutositeCoreAction {

    public BlogConfigAction(){
        m_ds = BlogConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BlogConfigForm _BlogConfigForm = (BlogConfigForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogConfig _BlogConfig = m_ds.getById(cid);

            if (_BlogConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogConfig.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _BlogConfigForm, _BlogConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "blog_config_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogConfig _BlogConfig = m_ds.getById(cid);

            if (_BlogConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _BlogConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "blog_config");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            BlogConfig _BlogConfig = m_ds.getById(cid);

            if (_BlogConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _BlogConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_BlogConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _BlogConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "blog_config_home");    
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

            m_logger.info("Creating new BlogConfig" );
            BlogConfig _BlogConfig = new BlogConfig();   

            // Setting IDs for the object
            _BlogConfig.setSiteId(site.getId());

            _BlogConfig.setBlogMainId(WebParamUtil.getLongValue(_BlogConfigForm.getBlogMainId()));
            m_logger.debug("setting BlogMainId=" +_BlogConfigForm.getBlogMainId());

            
            try{
                m_actionExtent.beforeAdd(request, response, _BlogConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_BlogConfig);
            try{
                m_actionExtent.afterAdd(request, response, _BlogConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "blog_config_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, BlogConfigForm _BlogConfigForm, BlogConfig _BlogConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogConfig _BlogConfig = m_ds.getById(cid);

        _BlogConfig.setBlogMainId(WebParamUtil.getLongValue(_BlogConfigForm.getBlogMainId()));

        m_actionExtent.beforeUpdate(request, response, _BlogConfig);
        m_ds.update(_BlogConfig);
        m_actionExtent.afterUpdate(request, response, _BlogConfig);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, BlogConfig _BlogConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        BlogConfig _BlogConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("blogMainId"))) {
            m_logger.debug("updating param blogMainId from " +_BlogConfig.getBlogMainId() + "->" + request.getParameter("blogMainId"));
                _BlogConfig.setBlogMainId(WebParamUtil.getLongValue(request.getParameter("blogMainId")));
            }

        m_actionExtent.beforeUpdate(request, response, _BlogConfig);
        m_ds.update(_BlogConfig);
        m_actionExtent.afterUpdate(request, response, _BlogConfig);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected BlogConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( BlogConfigAction.class);
}
