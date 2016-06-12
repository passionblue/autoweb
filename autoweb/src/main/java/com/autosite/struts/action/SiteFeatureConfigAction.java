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

import com.autosite.db.SiteFeatureConfig;
import com.autosite.ds.SiteFeatureConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SiteFeatureConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class SiteFeatureConfigAction extends AutositeCoreAction {

    public SiteFeatureConfigAction(){
        m_ds = SiteFeatureConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SiteFeatureConfigForm _SiteFeatureConfigForm = (SiteFeatureConfigForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            SiteFeatureConfig _SiteFeatureConfig = m_ds.getById(cid);

            if (_SiteFeatureConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SiteFeatureConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteFeatureConfig.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _SiteFeatureConfigForm, _SiteFeatureConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "site_feature_config_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            SiteFeatureConfig _SiteFeatureConfig = m_ds.getById(cid);

            if (_SiteFeatureConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SiteFeatureConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteFeatureConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _SiteFeatureConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "site_feature_config");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            SiteFeatureConfig _SiteFeatureConfig = m_ds.getById(cid);

            if (_SiteFeatureConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SiteFeatureConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteFeatureConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _SiteFeatureConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_SiteFeatureConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _SiteFeatureConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "site_feature_config_home");    
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

            m_logger.info("Creating new SiteFeatureConfig" );
            SiteFeatureConfig _SiteFeatureConfig = new SiteFeatureConfig();   

            // Setting IDs for the object
            _SiteFeatureConfig.setSiteId(site.getId());

            _SiteFeatureConfig.setUserRegisterEnabled(WebParamUtil.getIntValue(_SiteFeatureConfigForm.getUserRegisterEnabled()));
            m_logger.debug("setting UserRegisterEnabled=" +_SiteFeatureConfigForm.getUserRegisterEnabled());
            _SiteFeatureConfig.setEcEnabled(WebParamUtil.getIntValue(_SiteFeatureConfigForm.getEcEnabled()));
            m_logger.debug("setting EcEnabled=" +_SiteFeatureConfigForm.getEcEnabled());
            _SiteFeatureConfig.setEmailSubsEnabled(WebParamUtil.getIntValue(_SiteFeatureConfigForm.getEmailSubsEnabled()));
            m_logger.debug("setting EmailSubsEnabled=" +_SiteFeatureConfigForm.getEmailSubsEnabled());
            _SiteFeatureConfig.setTimeCreated(WebParamUtil.getDateValue(_SiteFeatureConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SiteFeatureConfigForm.getTimeCreated());
            _SiteFeatureConfig.setTimeUpdated(WebParamUtil.getDateValue(_SiteFeatureConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SiteFeatureConfigForm.getTimeUpdated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _SiteFeatureConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_SiteFeatureConfig);
            try{
                m_actionExtent.afterAdd(request, response, _SiteFeatureConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "site_feature_config_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteFeatureConfigForm _SiteFeatureConfigForm, SiteFeatureConfig _SiteFeatureConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteFeatureConfig _SiteFeatureConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + SiteFeatureConfigDS.objectToString(_SiteFeatureConfig));

        _SiteFeatureConfig.setUserRegisterEnabled(WebParamUtil.getIntValue(_SiteFeatureConfigForm.getUserRegisterEnabled()));
        _SiteFeatureConfig.setEcEnabled(WebParamUtil.getIntValue(_SiteFeatureConfigForm.getEcEnabled()));
        _SiteFeatureConfig.setEmailSubsEnabled(WebParamUtil.getIntValue(_SiteFeatureConfigForm.getEmailSubsEnabled()));
        _SiteFeatureConfig.setTimeCreated(WebParamUtil.getDateValue(_SiteFeatureConfigForm.getTimeCreated()));
        _SiteFeatureConfig.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _SiteFeatureConfig);
        m_ds.update(_SiteFeatureConfig);
        m_actionExtent.afterUpdate(request, response, _SiteFeatureConfig);
        m_logger.debug("After update " + SiteFeatureConfigDS.objectToString(_SiteFeatureConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, SiteFeatureConfig _SiteFeatureConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteFeatureConfig _SiteFeatureConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userRegisterEnabled"))) {
            m_logger.debug("updating param userRegisterEnabled from " +_SiteFeatureConfig.getUserRegisterEnabled() + "->" + request.getParameter("userRegisterEnabled"));
                _SiteFeatureConfig.setUserRegisterEnabled(WebParamUtil.getIntValue(request.getParameter("userRegisterEnabled")));
            }
        if (!isMissing(request.getParameter("ecEnabled"))) {
            m_logger.debug("updating param ecEnabled from " +_SiteFeatureConfig.getEcEnabled() + "->" + request.getParameter("ecEnabled"));
                _SiteFeatureConfig.setEcEnabled(WebParamUtil.getIntValue(request.getParameter("ecEnabled")));
            }
        if (!isMissing(request.getParameter("emailSubsEnabled"))) {
            m_logger.debug("updating param emailSubsEnabled from " +_SiteFeatureConfig.getEmailSubsEnabled() + "->" + request.getParameter("emailSubsEnabled"));
                _SiteFeatureConfig.setEmailSubsEnabled(WebParamUtil.getIntValue(request.getParameter("emailSubsEnabled")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SiteFeatureConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _SiteFeatureConfig.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SiteFeatureConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
                _SiteFeatureConfig.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
            }

        m_actionExtent.beforeUpdate(request, response, _SiteFeatureConfig);
        m_ds.update(_SiteFeatureConfig);
        m_actionExtent.afterUpdate(request, response, _SiteFeatureConfig);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected SiteFeatureConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SiteFeatureConfigAction.class);
}
