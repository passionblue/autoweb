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

import com.autosite.db.ContentFeedRel;
import com.autosite.ds.ContentFeedRelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.ContentFeedRelForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class ContentFeedRelAction extends AutositeCoreAction {

    public ContentFeedRelAction(){
        m_ds = ContentFeedRelDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ContentFeedRelForm _ContentFeedRelForm = (ContentFeedRelForm) form;
        HttpSession session = request.getSession();

        setPage(session, "content_feed_rel_home");

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
            ContentFeedRel _ContentFeedRel = m_ds.getById(cid);

            if (_ContentFeedRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedRel.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _ContentFeedRelForm, _ContentFeedRel);
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
            ContentFeedRel _ContentFeedRel = m_ds.getById(cid);

            if (_ContentFeedRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedRel.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _ContentFeedRel);
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
            ContentFeedRel _ContentFeedRel = m_ds.getById(cid);

            if (_ContentFeedRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentFeedRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentFeedRel.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _ContentFeedRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_ContentFeedRel);
            try { 
                m_actionExtent.afterDelete(request, response, _ContentFeedRel);
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

            m_logger.info("Creating new ContentFeedRel" );
            ContentFeedRel _ContentFeedRel = new ContentFeedRel();   

            // Setting IDs for the object
            _ContentFeedRel.setSiteId(site.getId());

            _ContentFeedRel.setContentFeedId(WebParamUtil.getLongValue(_ContentFeedRelForm.getContentFeedId()));
            m_logger.debug("setting ContentFeedId=" +_ContentFeedRelForm.getContentFeedId());
            _ContentFeedRel.setContentId(WebParamUtil.getLongValue(_ContentFeedRelForm.getContentId()));
            m_logger.debug("setting ContentId=" +_ContentFeedRelForm.getContentId());
            _ContentFeedRel.setTimeCreated(WebParamUtil.getDateValue(_ContentFeedRelForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ContentFeedRelForm.getTimeCreated());

			// Checking the uniqueness by configuration. 
			if ( m_ds.getByContentFeedIdContentId(_ContentFeedRel.getContentFeedId(), _ContentFeedRel.getContentId()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _ContentFeedRel.getContentId());
                return mapping.findForward("default");
            }
            
            try{
                m_actionExtent.beforeAdd(request, response, _ContentFeedRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_ContentFeedRel);
            try{
                m_actionExtent.afterAdd(request, response, _ContentFeedRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "content_feed_rel_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentFeedRelForm _ContentFeedRelForm, ContentFeedRel _ContentFeedRel) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentFeedRel _ContentFeedRel = m_ds.getById(cid);

        m_logger.debug("Before update " + ContentFeedRelDS.objectToString(_ContentFeedRel));

        _ContentFeedRel.setContentFeedId(WebParamUtil.getLongValue(_ContentFeedRelForm.getContentFeedId()));
        _ContentFeedRel.setContentId(WebParamUtil.getLongValue(_ContentFeedRelForm.getContentId()));
        _ContentFeedRel.setTimeCreated(WebParamUtil.getDateValue(_ContentFeedRelForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _ContentFeedRel);
        m_ds.update(_ContentFeedRel);
        m_actionExtent.afterUpdate(request, response, _ContentFeedRel);
        m_logger.debug("After update " + ContentFeedRelDS.objectToString(_ContentFeedRel));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ContentFeedRel _ContentFeedRel) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentFeedRel _ContentFeedRel = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentFeedId"))) {
            m_logger.debug("updating param contentFeedId from " +_ContentFeedRel.getContentFeedId() + "->" + request.getParameter("contentFeedId"));
            _ContentFeedRel.setContentFeedId(WebParamUtil.getLongValue(request.getParameter("contentFeedId")));
        }
        if (!isMissing(request.getParameter("contentId"))) {
            m_logger.debug("updating param contentId from " +_ContentFeedRel.getContentId() + "->" + request.getParameter("contentId"));
            _ContentFeedRel.setContentId(WebParamUtil.getLongValue(request.getParameter("contentId")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ContentFeedRel.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ContentFeedRel.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _ContentFeedRel);
        m_ds.update(_ContentFeedRel);
        m_actionExtent.afterUpdate(request, response, _ContentFeedRel);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected ContentFeedRelDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentFeedRelAction.class);
}
