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

import com.autosite.db.ContentFeedConfig;
import com.autosite.ds.ContentFeedConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.ContentFeedConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class ContentFeedConfigAction extends AutositeCoreAction {

    public ContentFeedConfigAction(){
        m_ds = ContentFeedConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ContentFeedConfigForm _ContentFeedConfigForm = (ContentFeedConfigForm) form;
        HttpSession session = request.getSession();

        setPage(session, "content_feed_config_home");

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
            ContentFeedConfig _ContentFeedConfig = m_ds.getById(cid);

            if (_ContentFeedConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedConfig.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _ContentFeedConfigForm, _ContentFeedConfig);
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
            ContentFeedConfig _ContentFeedConfig = m_ds.getById(cid);

            if (_ContentFeedConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _ContentFeedConfig);
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
            ContentFeedConfig _ContentFeedConfig = m_ds.getById(cid);

            if (_ContentFeedConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _ContentFeedConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_ContentFeedConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _ContentFeedConfig);
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

            m_logger.info("Creating new ContentFeedConfig" );
            ContentFeedConfig _ContentFeedConfig = new ContentFeedConfig();   

            // Setting IDs for the object
            _ContentFeedConfig.setSiteId(site.getId());

            _ContentFeedConfig.setFeedCategory(WebParamUtil.getStringValue(_ContentFeedConfigForm.getFeedCategory()));
            m_logger.debug("setting FeedCategory=" +_ContentFeedConfigForm.getFeedCategory());
            _ContentFeedConfig.setFeedType(WebParamUtil.getIntValue(_ContentFeedConfigForm.getFeedType()));
            m_logger.debug("setting FeedType=" +_ContentFeedConfigForm.getFeedType());
            _ContentFeedConfig.setDisplayType(WebParamUtil.getIntValue(_ContentFeedConfigForm.getDisplayType()));
            m_logger.debug("setting DisplayType=" +_ContentFeedConfigForm.getDisplayType());
            _ContentFeedConfig.setTimeCreated(WebParamUtil.getDateValue(_ContentFeedConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ContentFeedConfigForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _ContentFeedConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_ContentFeedConfig);
            try{
                m_actionExtent.afterAdd(request, response, _ContentFeedConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "content_feed_config_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentFeedConfigForm _ContentFeedConfigForm, ContentFeedConfig _ContentFeedConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentFeedConfig _ContentFeedConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + ContentFeedConfigDS.objectToString(_ContentFeedConfig));

        _ContentFeedConfig.setFeedCategory(WebParamUtil.getStringValue(_ContentFeedConfigForm.getFeedCategory()));
        _ContentFeedConfig.setFeedType(WebParamUtil.getIntValue(_ContentFeedConfigForm.getFeedType()));
        _ContentFeedConfig.setDisplayType(WebParamUtil.getIntValue(_ContentFeedConfigForm.getDisplayType()));
        _ContentFeedConfig.setTimeCreated(WebParamUtil.getDateValue(_ContentFeedConfigForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _ContentFeedConfig);
        m_ds.update(_ContentFeedConfig);
        m_actionExtent.afterUpdate(request, response, _ContentFeedConfig);
        m_logger.debug("After update " + ContentFeedConfigDS.objectToString(_ContentFeedConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ContentFeedConfig _ContentFeedConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentFeedConfig _ContentFeedConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("feedCategory"))) {
            m_logger.debug("updating param feedCategory from " +_ContentFeedConfig.getFeedCategory() + "->" + request.getParameter("feedCategory"));
            _ContentFeedConfig.setFeedCategory(WebParamUtil.getStringValue(request.getParameter("feedCategory")));
        }
        if (!isMissing(request.getParameter("feedType"))) {
            m_logger.debug("updating param feedType from " +_ContentFeedConfig.getFeedType() + "->" + request.getParameter("feedType"));
            _ContentFeedConfig.setFeedType(WebParamUtil.getIntValue(request.getParameter("feedType")));
        }
        if (!isMissing(request.getParameter("displayType"))) {
            m_logger.debug("updating param displayType from " +_ContentFeedConfig.getDisplayType() + "->" + request.getParameter("displayType"));
            _ContentFeedConfig.setDisplayType(WebParamUtil.getIntValue(request.getParameter("displayType")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ContentFeedConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ContentFeedConfig.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _ContentFeedConfig);
        m_ds.update(_ContentFeedConfig);
        m_actionExtent.afterUpdate(request, response, _ContentFeedConfig);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected ContentFeedConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentFeedConfigAction.class);
}
