package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.Constants;
import com.autosite.db.AutositeAccount;
import com.autosite.db.AutositeAccountPaymentInfo;
import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.db.SiteRegAccountServiceInfo;
import com.autosite.db.SiteRegPaymentInfo;
import com.autosite.ds.AutositeAccountDS;
import com.autosite.ds.AutositeUserDS;
import com.autosite.ds.SiteDS;
import com.autosite.session.SiteRegStore;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.SiteRegConfirmForm;
import com.autosite.util.SiteAccountManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class SiteRegConfirmAction extends AutositeCoreAction {

    public SiteRegConfirmAction() {
        m_actionExtent = (AutositeActionExtent) getActionExtent();
        if (m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SiteRegConfirmForm _SiteRegConfirmForm = (SiteRegConfirmForm) form;
        HttpSession session = request.getSession();

        setPage(session, "site_reg_confirm");
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }
        
        WebProcess webProc = null;
        try {
            webProc = checkWebProcess(request, session);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(), e);
            return mapping.findForward("default");
        }

        SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute(SiteRegStore.getSessionKey());
        
        // Validation 
        if ( siteRegStore == null) {
            sessionErrorText(session, "Session has expired, please start over the registration");
            setPage(session, "site_reg_start");
            return mapping.findForward("default");
        }

        if (isMissing(request.getParameter("targetDomain"))){
            sessionErrorText(session, "Internal error occurred. Please register again");
            setPage(session, "site_reg_start");
            return mapping.findForward("default");
        }
        
        if ( !request.getParameter("targetDomain").trim().equalsIgnoreCase(siteRegStore.getStart().getTargetDomain())){
            sessionErrorText(session, "Internal error occurred. Please register again");
            setPage(session, "site_reg_start");
            return mapping.findForward("default");
        }

        
        SiteRegAccountServiceInfo accInfo =   siteRegStore.getAccServiceInfo();
        SiteRegPaymentInfo payInfo = siteRegStore.getPayInfo();

        // Add Site 

        boolean isSuccess = true;
        Site targetDomainSite = SiteDS.getInstance().getSiteByUrl(siteRegStore.getStart().getTargetDomain());
        
        if (targetDomainSite == null) {
            targetDomainSite = SiteDS.getInstance().registerSite(siteRegStore.getStart().getTargetDomain());
        }
        
        // User Add
        
        AutositeUser user = new AutositeUser();
        user.setSiteId(targetDomainSite.getId());
        user.setEmail(accInfo.getEmail());
        user.setPassword("random");
        user.setUsername(accInfo.getEmail());
        user.setUserType(Constants.UserSiteAdmin);
        user.setTimeCreated(new TimeNow());
        user.setTimeUpdated(new TimeNow());
        user.setDisabled(0);
        
        AutositeUserDS.getInstance().put(user);
        
        // Set up account
        long accountId = site.getAccountId();
        if (AutositeAccountDS.getInstance().getById(accountId) != null) {
            isSuccess = false;
            m_logger.info("Account already exisits for " + siteRegStore.getDomain() + " accountId=" + accountId);
        }

        AutositeAccount newAccount = new AutositeAccount();

        newAccount.setAccountNum(SiteAccountManager.createAccountNum());
        newAccount.setEnabled(1);
        newAccount.setSiteId(targetDomainSite.getId());
        newAccount.setTimeCreated(new TimeNow());
        newAccount.setCompany(accInfo.getCompany());
        newAccount.setEmail(accInfo.getEmail());
        newAccount.setFirstName(accInfo.getFirstName());
        newAccount.setLastName(accInfo.getLastName());
        newAccount.setPhone(accInfo.getPhone());
        newAccount.setAccountOwnerId(user.getId());

        AutositeAccountDS.getInstance().put(newAccount);
        
        m_logger.info("Account created for " + targetDomainSite.getSiteUrl() + ", id=" + newAccount.getId());
        
        targetDomainSite.setAccountId(newAccount.getId());
        targetDomainSite.setRegistered(1);
        SiteDS.getInstance().update(targetDomainSite);

        // Payment Info
        
        if ( payInfo.getSkip() == 0 ){
            
            AutositeAccountPaymentInfo accPayInfo = new AutositeAccountPaymentInfo();
            accPayInfo.setPaymentName(payInfo.getCardType());
            
        } else {
            m_logger.info("Payment is not provided during the registartion");
        }
        
        setPage(session, "site_reg_welcome_and_instruction");

        webProc.complete();

        return mapping.findForward("default");
    }

    protected boolean loginRequired() {
        return false;
    }

    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger(SiteRegConfirmAction.class);
}
