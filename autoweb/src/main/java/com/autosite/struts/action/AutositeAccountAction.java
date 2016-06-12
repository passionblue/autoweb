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

import com.autosite.db.AutositeAccount;
import com.autosite.ds.AutositeAccountDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.AutositeAccountForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class AutositeAccountAction extends AutositeCoreAction {

    public AutositeAccountAction(){
	    m_ds = AutositeAccountDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        AutositeAccountForm _AutositeAccountForm = (AutositeAccountForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            AutositeAccount _AutositeAccount = m_ds.getById(cid);

            if (_AutositeAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeAccount.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _AutositeAccountForm, _AutositeAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			//setPage(session, "autosite_account_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            AutositeAccount _AutositeAccount = m_ds.getById(cid);

            if (_AutositeAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeAccount.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _AutositeAccount);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			//setPage(session, "autosite_account");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            AutositeAccount _AutositeAccount = m_ds.getById(cid);

            if (_AutositeAccount == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeAccount.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeAccount.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _AutositeAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_AutositeAccount);
            try { 
	        	m_actionExtent.afterDelete(request, response, _AutositeAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

        	//setPage(session, "autosite_account_home");    
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

			m_logger.info("Creating new AutositeAccount" );
			AutositeAccount _AutositeAccount = new AutositeAccount();	

			// Setting IDs for the object
			_AutositeAccount.setSiteId(site.getId());

                _AutositeAccount.setAccountNum(WebParamUtil.getStringValue(_AutositeAccountForm.getAccountNum()));
    			m_logger.debug("setting AccountNum=" +_AutositeAccountForm.getAccountNum());
                _AutositeAccount.setEnabled(WebParamUtil.getIntValue(_AutositeAccountForm.getEnabled()));
    			m_logger.debug("setting Enabled=" +_AutositeAccountForm.getEnabled());
                _AutositeAccount.setEmailConfirmed(WebParamUtil.getIntValue(_AutositeAccountForm.getEmailConfirmed()));
    			m_logger.debug("setting EmailConfirmed=" +_AutositeAccountForm.getEmailConfirmed());
                _AutositeAccount.setTimeConfirmed(WebParamUtil.getDateValue(_AutositeAccountForm.getTimeConfirmed()));
    			m_logger.debug("setting TimeConfirmed=" +_AutositeAccountForm.getTimeConfirmed());
                _AutositeAccount.setTimeCreated(WebParamUtil.getDateValue(_AutositeAccountForm.getTimeCreated()));
    			m_logger.debug("setting TimeCreated=" +_AutositeAccountForm.getTimeCreated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _AutositeAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_AutositeAccount);
			try{
		        m_actionExtent.afterAdd(request, response, _AutositeAccount);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            //setPage(session, "autosite_account_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, AutositeAccountForm _AutositeAccountForm, AutositeAccount _AutositeAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        AutositeAccount _AutositeAccount = m_ds.getById(cid);

            _AutositeAccount.setAccountNum(WebParamUtil.getStringValue(_AutositeAccountForm.getAccountNum()));
	            _AutositeAccount.setEnabled(WebParamUtil.getIntValue(_AutositeAccountForm.getEnabled()));
	            _AutositeAccount.setEmailConfirmed(WebParamUtil.getIntValue(_AutositeAccountForm.getEmailConfirmed()));
	            _AutositeAccount.setTimeConfirmed(WebParamUtil.getDateValue(_AutositeAccountForm.getTimeConfirmed()));
	            _AutositeAccount.setTimeCreated(WebParamUtil.getDateValue(_AutositeAccountForm.getTimeCreated()));
	
        m_actionExtent.beforeUpdate(request, response, _AutositeAccount);
        m_ds.update(_AutositeAccount);
        m_actionExtent.afterUpdate(request, response, _AutositeAccount);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeAccount _AutositeAccount) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        AutositeAccount _AutositeAccount = m_ds.getById(cid);

        if (!isMissing(request.getParameter("accountNum"))) {
            m_logger.debug("updating param accountNum from " +_AutositeAccount.getAccountNum() + "->" + request.getParameter("accountNum"));
                _AutositeAccount.setAccountNum(WebParamUtil.getStringValue(request.getParameter("accountNum")));
	        }
        if (!isMissing(request.getParameter("enabled"))) {
            m_logger.debug("updating param enabled from " +_AutositeAccount.getEnabled() + "->" + request.getParameter("enabled"));
                _AutositeAccount.setEnabled(WebParamUtil.getIntValue(request.getParameter("enabled")));
	        }
        if (!isMissing(request.getParameter("emailConfirmed"))) {
            m_logger.debug("updating param emailConfirmed from " +_AutositeAccount.getEmailConfirmed() + "->" + request.getParameter("emailConfirmed"));
                _AutositeAccount.setEmailConfirmed(WebParamUtil.getIntValue(request.getParameter("emailConfirmed")));
	        }
        if (!isMissing(request.getParameter("timeConfirmed"))) {
            m_logger.debug("updating param timeConfirmed from " +_AutositeAccount.getTimeConfirmed() + "->" + request.getParameter("timeConfirmed"));
                _AutositeAccount.setTimeConfirmed(WebParamUtil.getDateValue(request.getParameter("timeConfirmed")));
	        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeAccount.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _AutositeAccount.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
	        }

        m_actionExtent.beforeUpdate(request, response, _AutositeAccount);
        m_ds.update(_AutositeAccount);
        m_actionExtent.afterUpdate(request, response, _AutositeAccount);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected AutositeAccountDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( AutositeAccountAction.class);
}
