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

import com.autosite.db.AutositeLoginExtent;
import com.autosite.ds.AutositeLoginExtentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.AutositeLoginExtentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class AutositeLoginExtentAction extends AutositeCoreAction {

    public AutositeLoginExtentAction(){
        m_ds = AutositeLoginExtentDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        AutositeLoginExtentForm _AutositeLoginExtentForm = (AutositeLoginExtentForm) form;
        HttpSession session = request.getSession();

        setPage(session, "autosite_login_extent_home");

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
            AutositeLoginExtent _AutositeLoginExtent = m_ds.getById(cid);

            if (_AutositeLoginExtent == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeLoginExtent.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeLoginExtent.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _AutositeLoginExtentForm, _AutositeLoginExtent);
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
            AutositeLoginExtent _AutositeLoginExtent = m_ds.getById(cid);

            if (_AutositeLoginExtent == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeLoginExtent.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeLoginExtent.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _AutositeLoginExtent);
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
            AutositeLoginExtent _AutositeLoginExtent = m_ds.getById(cid);

            if (_AutositeLoginExtent == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeLoginExtent.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeLoginExtent.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _AutositeLoginExtent);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_AutositeLoginExtent);
            try { 
                m_actionExtent.afterDelete(request, response, _AutositeLoginExtent);
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

            m_logger.info("Creating new AutositeLoginExtent" );
            AutositeLoginExtent _AutositeLoginExtent = new AutositeLoginExtent();   

            // Setting IDs for the object
            _AutositeLoginExtent.setSiteId(site.getId());


            
            try{
                m_actionExtent.beforeAdd(request, response, _AutositeLoginExtent);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_AutositeLoginExtent);
            try{
                m_actionExtent.afterAdd(request, response, _AutositeLoginExtent);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "autosite_login_extent_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, AutositeLoginExtentForm _AutositeLoginExtentForm, AutositeLoginExtent _AutositeLoginExtent) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeLoginExtent _AutositeLoginExtent = m_ds.getById(cid);

        m_logger.debug("Before update " + AutositeLoginExtentDS.objectToString(_AutositeLoginExtent));


        m_actionExtent.beforeUpdate(request, response, _AutositeLoginExtent);
        m_ds.update(_AutositeLoginExtent);
        m_actionExtent.afterUpdate(request, response, _AutositeLoginExtent);
        m_logger.debug("After update " + AutositeLoginExtentDS.objectToString(_AutositeLoginExtent));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeLoginExtent _AutositeLoginExtent) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeLoginExtent _AutositeLoginExtent = m_ds.getById(cid);


        m_actionExtent.beforeUpdate(request, response, _AutositeLoginExtent);
        m_ds.update(_AutositeLoginExtent);
        m_actionExtent.afterUpdate(request, response, _AutositeLoginExtent);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected AutositeLoginExtentDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( AutositeLoginExtentAction.class);
}
