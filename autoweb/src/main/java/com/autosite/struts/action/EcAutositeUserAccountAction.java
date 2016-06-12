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

import com.autosite.db.EcAutositeUserAccount;
import com.autosite.ds.EcAutositeUserAccountDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcAutositeUserAccountForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcAutositeUserAccountAction extends AutositeCoreAction {

    public EcAutositeUserAccountAction(){
        m_ds = EcAutositeUserAccountDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcAutositeUserAccountForm _EcAutositeUserAccountForm = (EcAutositeUserAccountForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAutositeUserAccount _EcAutositeUserAccount = m_ds.getById(cid);

            if (_EcAutositeUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAutositeUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAutositeUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcAutositeUserAccountForm, _EcAutositeUserAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_autosite_user_account_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAutositeUserAccount _EcAutositeUserAccount = m_ds.getById(cid);

            if (_EcAutositeUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAutositeUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAutositeUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcAutositeUserAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_autosite_user_account");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAutositeUserAccount _EcAutositeUserAccount = m_ds.getById(cid);

            if (_EcAutositeUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAutositeUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAutositeUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcAutositeUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcAutositeUserAccount);
            try { 
                m_actionExtent.afterDelete(request, response, _EcAutositeUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_autosite_user_account_home");    
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

            m_logger.info("Creating new EcAutositeUserAccount" );
            EcAutositeUserAccount _EcAutositeUserAccount = new EcAutositeUserAccount();   

            // Setting IDs for the object
            _EcAutositeUserAccount.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null)
                _EcAutositeUserAccount.setUserId(user.getId());

            _EcAutositeUserAccount.setUserId(WebParamUtil.getLongValue(_EcAutositeUserAccountForm.getUserId()));
            m_logger.debug("setting UserId=" +_EcAutositeUserAccountForm.getUserId());
            _EcAutositeUserAccount.setFirstName(WebParamUtil.getStringValue(_EcAutositeUserAccountForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_EcAutositeUserAccountForm.getFirstName());
            _EcAutositeUserAccount.setLastName(WebParamUtil.getStringValue(_EcAutositeUserAccountForm.getLastName()));
            m_logger.debug("setting LastName=" +_EcAutositeUserAccountForm.getLastName());
            _EcAutositeUserAccount.setSubscribed(WebParamUtil.getIntValue(_EcAutositeUserAccountForm.getSubscribed()));
            m_logger.debug("setting Subscribed=" +_EcAutositeUserAccountForm.getSubscribed());
            _EcAutositeUserAccount.setTimeCreated(WebParamUtil.getDateValue(_EcAutositeUserAccountForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_EcAutositeUserAccountForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcAutositeUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcAutositeUserAccount);
            try{
                m_actionExtent.afterAdd(request, response, _EcAutositeUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_autosite_user_account_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcAutositeUserAccountForm _EcAutositeUserAccountForm, EcAutositeUserAccount _EcAutositeUserAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAutositeUserAccount _EcAutositeUserAccount = m_ds.getById(cid);

        _EcAutositeUserAccount.setUserId(WebParamUtil.getLongValue(_EcAutositeUserAccountForm.getUserId()));
        _EcAutositeUserAccount.setFirstName(WebParamUtil.getStringValue(_EcAutositeUserAccountForm.getFirstName()));
        _EcAutositeUserAccount.setLastName(WebParamUtil.getStringValue(_EcAutositeUserAccountForm.getLastName()));
        _EcAutositeUserAccount.setSubscribed(WebParamUtil.getIntValue(_EcAutositeUserAccountForm.getSubscribed()));
        _EcAutositeUserAccount.setTimeCreated(WebParamUtil.getDateValue(_EcAutositeUserAccountForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _EcAutositeUserAccount);
        m_ds.update(_EcAutositeUserAccount);
        m_actionExtent.afterUpdate(request, response, _EcAutositeUserAccount);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcAutositeUserAccount _EcAutositeUserAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAutositeUserAccount _EcAutositeUserAccount = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcAutositeUserAccount.getUserId() + "->" + request.getParameter("userId"));
                _EcAutositeUserAccount.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_EcAutositeUserAccount.getFirstName() + "->" + request.getParameter("firstName"));
                _EcAutositeUserAccount.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));
            }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_EcAutositeUserAccount.getLastName() + "->" + request.getParameter("lastName"));
                _EcAutositeUserAccount.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));
            }
        if (!isMissing(request.getParameter("subscribed"))) {
            m_logger.debug("updating param subscribed from " +_EcAutositeUserAccount.getSubscribed() + "->" + request.getParameter("subscribed"));
                _EcAutositeUserAccount.setSubscribed(WebParamUtil.getIntValue(request.getParameter("subscribed")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcAutositeUserAccount.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _EcAutositeUserAccount.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcAutositeUserAccount);
        m_ds.update(_EcAutositeUserAccount);
        m_actionExtent.afterUpdate(request, response, _EcAutositeUserAccount);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcAutositeUserAccountDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcAutositeUserAccountAction.class);
}
