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

import com.autosite.db.SweepUserConfig;
import com.autosite.ds.SweepUserConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SweepUserConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class SweepUserConfigAction extends AutositeCoreAction {

    public SweepUserConfigAction(){
        m_ds = SweepUserConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SweepUserConfigForm _SweepUserConfigForm = (SweepUserConfigForm) form;
        HttpSession session = request.getSession();

        setPage(session, "sweep_user_config_home");

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
            SweepUserConfig _SweepUserConfig = m_ds.getById(cid);

            if (_SweepUserConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SweepUserConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepUserConfig.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _SweepUserConfigForm, _SweepUserConfig);
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
            SweepUserConfig _SweepUserConfig = m_ds.getById(cid);

            if (_SweepUserConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SweepUserConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepUserConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _SweepUserConfig);
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
            SweepUserConfig _SweepUserConfig = m_ds.getById(cid);

            if (_SweepUserConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SweepUserConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepUserConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _SweepUserConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_SweepUserConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _SweepUserConfig);
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

            m_logger.info("Creating new SweepUserConfig" );
            SweepUserConfig _SweepUserConfig = new SweepUserConfig();   

            // Setting IDs for the object
            _SweepUserConfig.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _SweepUserConfig.setUserId(user.getId());
            }

            _SweepUserConfig.setUserId(WebParamUtil.getLongValue(_SweepUserConfigForm.getUserId()));
            m_logger.debug("setting UserId=" +_SweepUserConfigForm.getUserId());
            _SweepUserConfig.setMaxSweepAllowed(WebParamUtil.getIntValue(_SweepUserConfigForm.getMaxSweepAllowed()));
            m_logger.debug("setting MaxSweepAllowed=" +_SweepUserConfigForm.getMaxSweepAllowed());

            
            try{
                m_actionExtent.beforeAdd(request, response, _SweepUserConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_SweepUserConfig);
            try{
                m_actionExtent.afterAdd(request, response, _SweepUserConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "sweep_user_config_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SweepUserConfigForm _SweepUserConfigForm, SweepUserConfig _SweepUserConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepUserConfig _SweepUserConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + SweepUserConfigDS.objectToString(_SweepUserConfig));

        _SweepUserConfig.setUserId(WebParamUtil.getLongValue(_SweepUserConfigForm.getUserId()));
        _SweepUserConfig.setMaxSweepAllowed(WebParamUtil.getIntValue(_SweepUserConfigForm.getMaxSweepAllowed()));

        m_actionExtent.beforeUpdate(request, response, _SweepUserConfig);
        m_ds.update(_SweepUserConfig);
        m_actionExtent.afterUpdate(request, response, _SweepUserConfig);
        m_logger.debug("After update " + SweepUserConfigDS.objectToString(_SweepUserConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, SweepUserConfig _SweepUserConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepUserConfig _SweepUserConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_SweepUserConfig.getUserId() + "->" + request.getParameter("userId"));
            _SweepUserConfig.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("maxSweepAllowed"))) {
            m_logger.debug("updating param maxSweepAllowed from " +_SweepUserConfig.getMaxSweepAllowed() + "->" + request.getParameter("maxSweepAllowed"));
            _SweepUserConfig.setMaxSweepAllowed(WebParamUtil.getIntValue(request.getParameter("maxSweepAllowed")));
        }

        m_actionExtent.beforeUpdate(request, response, _SweepUserConfig);
        m_ds.update(_SweepUserConfig);
        m_actionExtent.afterUpdate(request, response, _SweepUserConfig);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected SweepUserConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SweepUserConfigAction.class);
}
