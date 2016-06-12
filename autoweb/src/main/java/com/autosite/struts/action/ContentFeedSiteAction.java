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

import com.autosite.db.ContentFeedSite;
import com.autosite.ds.ContentFeedSiteDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.ContentFeedSiteForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class ContentFeedSiteAction extends AutositeCoreAction {

    public ContentFeedSiteAction(){
        m_ds = ContentFeedSiteDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ContentFeedSiteForm _ContentFeedSiteForm = (ContentFeedSiteForm) form;
        HttpSession session = request.getSession();

        setPage(session, "content_feed_site_home");

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
            ContentFeedSite _ContentFeedSite = m_ds.getById(cid);

            if (_ContentFeedSite == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedSite.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedSite.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _ContentFeedSiteForm, _ContentFeedSite);
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
            ContentFeedSite _ContentFeedSite = m_ds.getById(cid);

            if (_ContentFeedSite == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedSite.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedSite.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _ContentFeedSite);
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
            ContentFeedSite _ContentFeedSite = m_ds.getById(cid);

            if (_ContentFeedSite == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedSite.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedSite.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _ContentFeedSite);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_ContentFeedSite);
            try { 
                m_actionExtent.afterDelete(request, response, _ContentFeedSite);
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

            m_logger.info("Creating new ContentFeedSite" );
            ContentFeedSite _ContentFeedSite = new ContentFeedSite();   

            // Setting IDs for the object
            _ContentFeedSite.setSiteId(site.getId());

            _ContentFeedSite.setContentFeedId(WebParamUtil.getLongValue(_ContentFeedSiteForm.getContentFeedId()));
            m_logger.debug("setting ContentFeedId=" +_ContentFeedSiteForm.getContentFeedId());
            _ContentFeedSite.setDisplayType(WebParamUtil.getIntValue(_ContentFeedSiteForm.getDisplayType()));
            m_logger.debug("setting DisplayType=" +_ContentFeedSiteForm.getDisplayType());
            _ContentFeedSite.setTimeCreated(WebParamUtil.getDateValue(_ContentFeedSiteForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ContentFeedSiteForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _ContentFeedSite);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_ContentFeedSite);
            try{
                m_actionExtent.afterAdd(request, response, _ContentFeedSite);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "content_feed_site_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentFeedSiteForm _ContentFeedSiteForm, ContentFeedSite _ContentFeedSite) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentFeedSite _ContentFeedSite = m_ds.getById(cid);

        m_logger.debug("Before update " + ContentFeedSiteDS.objectToString(_ContentFeedSite));

        _ContentFeedSite.setContentFeedId(WebParamUtil.getLongValue(_ContentFeedSiteForm.getContentFeedId()));
        _ContentFeedSite.setDisplayType(WebParamUtil.getIntValue(_ContentFeedSiteForm.getDisplayType()));
        _ContentFeedSite.setTimeCreated(WebParamUtil.getDateValue(_ContentFeedSiteForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _ContentFeedSite);
        m_ds.update(_ContentFeedSite);
        m_actionExtent.afterUpdate(request, response, _ContentFeedSite);
        m_logger.debug("After update " + ContentFeedSiteDS.objectToString(_ContentFeedSite));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ContentFeedSite _ContentFeedSite) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentFeedSite _ContentFeedSite = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentFeedId"))) {
            m_logger.debug("updating param contentFeedId from " +_ContentFeedSite.getContentFeedId() + "->" + request.getParameter("contentFeedId"));
            _ContentFeedSite.setContentFeedId(WebParamUtil.getLongValue(request.getParameter("contentFeedId")));
        }
        if (!isMissing(request.getParameter("displayType"))) {
            m_logger.debug("updating param displayType from " +_ContentFeedSite.getDisplayType() + "->" + request.getParameter("displayType"));
            _ContentFeedSite.setDisplayType(WebParamUtil.getIntValue(request.getParameter("displayType")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ContentFeedSite.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ContentFeedSite.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _ContentFeedSite);
        m_ds.update(_ContentFeedSite);
        m_actionExtent.afterUpdate(request, response, _ContentFeedSite);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected ContentFeedSiteDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentFeedSiteAction.class);
}
