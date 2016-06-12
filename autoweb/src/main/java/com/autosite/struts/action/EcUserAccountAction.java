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

import com.autosite.db.EcUserAccount;
import com.autosite.ds.EcUserAccountDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcUserAccountForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcUserAccountAction extends AutositeCoreAction {

    public EcUserAccountAction(){
        m_ds = EcUserAccountDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcUserAccountForm _EcUserAccountForm = (EcUserAccountForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcUserAccount _EcUserAccount = m_ds.getById(cid);

            if (_EcUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcUserAccountForm, _EcUserAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_user_account_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcUserAccount _EcUserAccount = m_ds.getById(cid);

            if (_EcUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcUserAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_user_account");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcUserAccount _EcUserAccount = m_ds.getById(cid);

            if (_EcUserAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcUserAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcUserAccount.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcUserAccount);
            try { 
                m_actionExtent.afterDelete(request, response, _EcUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_user_account_home");    
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

            m_logger.info("Creating new EcUserAccount" );
            EcUserAccount _EcUserAccount = new EcUserAccount();   

            // Setting IDs for the object
            _EcUserAccount.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null)
                _EcUserAccount.setUserId(user.getId());

            _EcUserAccount.setUserId(WebParamUtil.getLongValue(_EcUserAccountForm.getUserId()));
            m_logger.debug("setting UserId=" +_EcUserAccountForm.getUserId());
            _EcUserAccount.setFirstName(WebParamUtil.getStringValue(_EcUserAccountForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_EcUserAccountForm.getFirstName());
            _EcUserAccount.setLastName(WebParamUtil.getStringValue(_EcUserAccountForm.getLastName()));
            m_logger.debug("setting LastName=" +_EcUserAccountForm.getLastName());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcUserAccount);
            try{
                m_actionExtent.afterAdd(request, response, _EcUserAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_user_account_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcUserAccountForm _EcUserAccountForm, EcUserAccount _EcUserAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcUserAccount _EcUserAccount = m_ds.getById(cid);

        _EcUserAccount.setUserId(WebParamUtil.getLongValue(_EcUserAccountForm.getUserId()));
        _EcUserAccount.setFirstName(WebParamUtil.getStringValue(_EcUserAccountForm.getFirstName()));
        _EcUserAccount.setLastName(WebParamUtil.getStringValue(_EcUserAccountForm.getLastName()));

        m_actionExtent.beforeUpdate(request, response, _EcUserAccount);
        m_ds.update(_EcUserAccount);
        m_actionExtent.afterUpdate(request, response, _EcUserAccount);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcUserAccount _EcUserAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcUserAccount _EcUserAccount = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcUserAccount.getUserId() + "->" + request.getParameter("userId"));
                _EcUserAccount.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_EcUserAccount.getFirstName() + "->" + request.getParameter("firstName"));
                _EcUserAccount.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));
            }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_EcUserAccount.getLastName() + "->" + request.getParameter("lastName"));
                _EcUserAccount.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcUserAccount);
        m_ds.update(_EcUserAccount);
        m_actionExtent.afterUpdate(request, response, _EcUserAccount);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcUserAccountDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcUserAccountAction.class);
}
