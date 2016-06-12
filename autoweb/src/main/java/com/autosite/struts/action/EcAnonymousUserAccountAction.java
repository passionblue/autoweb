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

import com.autosite.db.EcAnonymousUserAccount;
import com.autosite.ds.EcAnonymousUserAccountDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcAnonymousUserAccountForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcAnonymousUserAccountAction extends AutositeCoreAction {

    public EcAnonymousUserAccountAction(){
        m_ds = EcAnonymousUserAccountDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcAnonymousUserAccountForm _EcAnonymousUserAccountForm = (EcAnonymousUserAccountForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousUserAccount _EcAnonymousUserAccount = m_ds.getById(cid);

            if (_EcAnonymousUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcAnonymousUserAccountForm, _EcAnonymousUserAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_anonymous_user_account_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousUserAccount _EcAnonymousUserAccount = m_ds.getById(cid);

            if (_EcAnonymousUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcAnonymousUserAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_anonymous_user_account");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousUserAccount _EcAnonymousUserAccount = m_ds.getById(cid);

            if (_EcAnonymousUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcAnonymousUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcAnonymousUserAccount);
            try { 
                m_actionExtent.afterDelete(request, response, _EcAnonymousUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_anonymous_user_account_home");    
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

            m_logger.info("Creating new EcAnonymousUserAccount" );
            EcAnonymousUserAccount _EcAnonymousUserAccount = new EcAnonymousUserAccount();   

            // Setting IDs for the object
            _EcAnonymousUserAccount.setSiteId(site.getId());

            _EcAnonymousUserAccount.setEmail(WebParamUtil.getStringValue(_EcAnonymousUserAccountForm.getEmail()));
            m_logger.debug("setting Email=" +_EcAnonymousUserAccountForm.getEmail());
            _EcAnonymousUserAccount.setSubscribed(WebParamUtil.getIntValue(_EcAnonymousUserAccountForm.getSubscribed()));
            m_logger.debug("setting Subscribed=" +_EcAnonymousUserAccountForm.getSubscribed());
            _EcAnonymousUserAccount.setTimeCreated(WebParamUtil.getDateValue(_EcAnonymousUserAccountForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_EcAnonymousUserAccountForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcAnonymousUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcAnonymousUserAccount);
            try{
                m_actionExtent.afterAdd(request, response, _EcAnonymousUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_anonymous_user_account_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcAnonymousUserAccountForm _EcAnonymousUserAccountForm, EcAnonymousUserAccount _EcAnonymousUserAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousUserAccount _EcAnonymousUserAccount = m_ds.getById(cid);

        _EcAnonymousUserAccount.setEmail(WebParamUtil.getStringValue(_EcAnonymousUserAccountForm.getEmail()));
        _EcAnonymousUserAccount.setSubscribed(WebParamUtil.getIntValue(_EcAnonymousUserAccountForm.getSubscribed()));
        _EcAnonymousUserAccount.setTimeCreated(WebParamUtil.getDateValue(_EcAnonymousUserAccountForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousUserAccount);
        m_ds.update(_EcAnonymousUserAccount);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousUserAccount);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcAnonymousUserAccount _EcAnonymousUserAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousUserAccount _EcAnonymousUserAccount = m_ds.getById(cid);

        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_EcAnonymousUserAccount.getEmail() + "->" + request.getParameter("email"));
                _EcAnonymousUserAccount.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));
            }
        if (!isMissing(request.getParameter("subscribed"))) {
            m_logger.debug("updating param subscribed from " +_EcAnonymousUserAccount.getSubscribed() + "->" + request.getParameter("subscribed"));
                _EcAnonymousUserAccount.setSubscribed(WebParamUtil.getIntValue(request.getParameter("subscribed")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcAnonymousUserAccount.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _EcAnonymousUserAccount.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousUserAccount);
        m_ds.update(_EcAnonymousUserAccount);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousUserAccount);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcAnonymousUserAccountDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcAnonymousUserAccountAction.class);
}
