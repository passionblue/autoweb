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

import com.autosite.db.ForumMain;
import com.autosite.ds.ForumMainDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.ForumMainForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class ForumMainAction extends AutositeCoreAction {

    public ForumMainAction(){
        m_ds = ForumMainDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ForumMainForm _ForumMainForm = (ForumMainForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ForumMain _ForumMain = m_ds.getById(cid);

            if (_ForumMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ForumMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ForumMain.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _ForumMainForm, _ForumMain);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "forum_main_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ForumMain _ForumMain = m_ds.getById(cid);

            if (_ForumMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ForumMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ForumMain.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _ForumMain);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "forum_main");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ForumMain _ForumMain = m_ds.getById(cid);

            if (_ForumMain == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ForumMain.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ForumMain.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _ForumMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_ForumMain);
            try { 
                m_actionExtent.afterDelete(request, response, _ForumMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "forum_main_home");    
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

            m_logger.info("Creating new ForumMain" );
            ForumMain _ForumMain = new ForumMain();   

            // Setting IDs for the object
            _ForumMain.setSiteId(site.getId());

            _ForumMain.setTitle(WebParamUtil.getStringValue(_ForumMainForm.getTitle()));
            m_logger.debug("setting Title=" +_ForumMainForm.getTitle());
            _ForumMain.setTimeCreated(WebParamUtil.getDateValue(_ForumMainForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ForumMainForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _ForumMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_ForumMain);
            try{
                m_actionExtent.afterAdd(request, response, _ForumMain);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "forum_main_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ForumMainForm _ForumMainForm, ForumMain _ForumMain) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        ForumMain _ForumMain = m_ds.getById(cid);

        m_logger.debug("Before update " + ForumMainDS.objectToString(_ForumMain));

        _ForumMain.setTitle(WebParamUtil.getStringValue(_ForumMainForm.getTitle()));
        _ForumMain.setTimeCreated(WebParamUtil.getDateValue(_ForumMainForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _ForumMain);
        m_ds.update(_ForumMain);
        m_actionExtent.afterUpdate(request, response, _ForumMain);
        m_logger.debug("After update " + ForumMainDS.objectToString(_ForumMain));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ForumMain _ForumMain) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        ForumMain _ForumMain = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_ForumMain.getTitle() + "->" + request.getParameter("title"));
                _ForumMain.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ForumMain.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _ForumMain.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _ForumMain);
        m_ds.update(_ForumMain);
        m_actionExtent.afterUpdate(request, response, _ForumMain);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected ForumMainDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ForumMainAction.class);
}
