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

import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SiteForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class SiteAction extends AutositeCoreAction {

    public SiteAction(){
        m_ds = SiteDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SiteForm _SiteForm = (SiteForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (!haveAccessToCommand(session, getActionCmd(request) ) ){
            sessionErrorText(session, "Permission error occurred.");
            processPageForAction(request, "access", getActionCmd(request), "error", getSentPage(request));
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
            Site _Site = m_ds.getById(cid);

            if (_Site == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _SiteForm, _Site);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "site_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            Site _Site = m_ds.getById(cid);

            if (_Site == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }


            try{
                editField(request, response, _Site);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "site");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            Site _Site = m_ds.getById(cid);

            if (_Site == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }


            try {
                m_actionExtent.beforeDelete(request, response, _Site);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_Site);
            try { 
                m_actionExtent.afterDelete(request, response, _Site);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "site_home");    
        }
        else {


            m_logger.info("Creating new Site" );
            Site _Site = new Site();   

            // Setting IDs for the object

            _Site.setSiteUrl(WebParamUtil.getStringValue(_SiteForm.getSiteUrl()));
            m_logger.debug("setting SiteUrl=" +_SiteForm.getSiteUrl());
            _Site.setAccountId(WebParamUtil.getLongValue(_SiteForm.getAccountId()));
            m_logger.debug("setting AccountId=" +_SiteForm.getAccountId());
            _Site.setCreatedTime(WebParamUtil.getDateValue(_SiteForm.getCreatedTime()));
            m_logger.debug("setting CreatedTime=" +_SiteForm.getCreatedTime());
            _Site.setRegistered(WebParamUtil.getIntValue(_SiteForm.getRegistered()));
            m_logger.debug("setting Registered=" +_SiteForm.getRegistered());
            _Site.setOnSale(WebParamUtil.getIntValue(_SiteForm.getOnSale()));
            m_logger.debug("setting OnSale=" +_SiteForm.getOnSale());
            _Site.setSuperAdminEnable(WebParamUtil.getIntValue(_SiteForm.getSuperAdminEnable()));
            m_logger.debug("setting SuperAdminEnable=" +_SiteForm.getSuperAdminEnable());
            _Site.setSiteRegisterEnable(WebParamUtil.getIntValue(_SiteForm.getSiteRegisterEnable()));
            m_logger.debug("setting SiteRegisterEnable=" +_SiteForm.getSiteRegisterEnable());
            _Site.setSubdomainEnable(WebParamUtil.getIntValue(_SiteForm.getSubdomainEnable()));
            m_logger.debug("setting SubdomainEnable=" +_SiteForm.getSubdomainEnable());
            _Site.setSiteRegisterSite(WebParamUtil.getStringValue(_SiteForm.getSiteRegisterSite()));
            m_logger.debug("setting SiteRegisterSite=" +_SiteForm.getSiteRegisterSite());
            _Site.setDisabled(WebParamUtil.getIntValue(_SiteForm.getDisabled()));
            m_logger.debug("setting Disabled=" +_SiteForm.getDisabled());

            
            try{
                m_actionExtent.beforeAdd(request, response, _Site);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_Site);
            try{
                m_actionExtent.afterAdd(request, response, _Site);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "site_home");

        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteForm _SiteForm, Site _Site) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Site _Site = m_ds.getById(cid);

        m_logger.debug("Before update " + SiteDS.objectToString(_Site));

        _Site.setSiteUrl(WebParamUtil.getStringValue(_SiteForm.getSiteUrl()));
        _Site.setAccountId(WebParamUtil.getLongValue(_SiteForm.getAccountId()));
        _Site.setCreatedTime(WebParamUtil.getDateValue(_SiteForm.getCreatedTime()));
        _Site.setRegistered(WebParamUtil.getIntValue(_SiteForm.getRegistered()));
        _Site.setOnSale(WebParamUtil.getIntValue(_SiteForm.getOnSale()));
        _Site.setSuperAdminEnable(WebParamUtil.getIntValue(_SiteForm.getSuperAdminEnable()));
        _Site.setSiteRegisterEnable(WebParamUtil.getIntValue(_SiteForm.getSiteRegisterEnable()));
        _Site.setSubdomainEnable(WebParamUtil.getIntValue(_SiteForm.getSubdomainEnable()));
        _Site.setSiteRegisterSite(WebParamUtil.getStringValue(_SiteForm.getSiteRegisterSite()));

        m_actionExtent.beforeUpdate(request, response, _Site);
        m_ds.update(_Site);
        m_actionExtent.afterUpdate(request, response, _Site);
        m_logger.debug("After update " + SiteDS.objectToString(_Site));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, Site _Site) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Site _Site = m_ds.getById(cid);

        if (!isMissing(request.getParameter("siteUrl"))) {
            m_logger.debug("updating param siteUrl from " +_Site.getSiteUrl() + "->" + request.getParameter("siteUrl"));
                _Site.setSiteUrl(WebParamUtil.getStringValue(request.getParameter("siteUrl")));
            }
        if (!isMissing(request.getParameter("accountId"))) {
            m_logger.debug("updating param accountId from " +_Site.getAccountId() + "->" + request.getParameter("accountId"));
                _Site.setAccountId(WebParamUtil.getLongValue(request.getParameter("accountId")));
            }
        if (!isMissing(request.getParameter("createdTime"))) {
            m_logger.debug("updating param createdTime from " +_Site.getCreatedTime() + "->" + request.getParameter("createdTime"));
                _Site.setCreatedTime(WebParamUtil.getDateValue(request.getParameter("createdTime")));
            }
        if (!isMissing(request.getParameter("siteGroup"))) {
            m_logger.debug("updating param siteGroup from " +_Site.getSiteGroup() + "->" + request.getParameter("siteGroup"));
                _Site.setSiteGroup(WebParamUtil.getStringValue(request.getParameter("siteGroup")));
            }
        if (!isMissing(request.getParameter("registered"))) {
            m_logger.debug("updating param registered from " +_Site.getRegistered() + "->" + request.getParameter("registered"));
                _Site.setRegistered(WebParamUtil.getIntValue(request.getParameter("registered")));
            }
        if (!isMissing(request.getParameter("onSale"))) {
            m_logger.debug("updating param onSale from " +_Site.getOnSale() + "->" + request.getParameter("onSale"));
                _Site.setOnSale(WebParamUtil.getIntValue(request.getParameter("onSale")));
            }
        if (!isMissing(request.getParameter("superAdminEnable"))) {
            m_logger.debug("updating param superAdminEnable from " +_Site.getSuperAdminEnable() + "->" + request.getParameter("superAdminEnable"));
                _Site.setSuperAdminEnable(WebParamUtil.getIntValue(request.getParameter("superAdminEnable")));
            }
        if (!isMissing(request.getParameter("siteRegisterEnable"))) {
            m_logger.debug("updating param siteRegisterEnable from " +_Site.getSiteRegisterEnable() + "->" + request.getParameter("siteRegisterEnable"));
                _Site.setSiteRegisterEnable(WebParamUtil.getIntValue(request.getParameter("siteRegisterEnable")));
            }
        if (!isMissing(request.getParameter("subdomainEnable"))) {
            m_logger.debug("updating param subdomainEnable from " +_Site.getSubdomainEnable() + "->" + request.getParameter("subdomainEnable"));
                _Site.setSubdomainEnable(WebParamUtil.getIntValue(request.getParameter("subdomainEnable")));
            }
        if (!isMissing(request.getParameter("siteRegisterSite"))) {
            m_logger.debug("updating param siteRegisterSite from " +_Site.getSiteRegisterSite() + "->" + request.getParameter("siteRegisterSite"));
                _Site.setSiteRegisterSite(WebParamUtil.getStringValue(request.getParameter("siteRegisterSite")));
            }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_Site.getDisabled() + "->" + request.getParameter("disabled"));
                _Site.setDisabled(WebParamUtil.getIntValue(request.getParameter("disabled")));
            }

        m_actionExtent.beforeUpdate(request, response, _Site);
        m_ds.update(_Site);
        m_actionExtent.afterUpdate(request, response, _Site);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected SiteDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SiteAction.class);
}
