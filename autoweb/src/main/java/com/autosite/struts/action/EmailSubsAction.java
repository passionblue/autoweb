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

import com.autosite.db.EmailSubs;
import com.autosite.ds.EmailSubsDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EmailSubsForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EmailSubsAction extends AutositeCoreAction {

    public EmailSubsAction(){
	    m_ds = EmailSubsDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EmailSubsForm _EmailSubsForm = (EmailSubsForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EmailSubs _EmailSubs = m_ds.getById(cid);

            if (_EmailSubs == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EmailSubs.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EmailSubs.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _EmailSubsForm, _EmailSubs);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			//setPage(session, "email_subs_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EmailSubs _EmailSubs = m_ds.getById(cid);

            if (_EmailSubs == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EmailSubs.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EmailSubs.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _EmailSubs);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			//setPage(session, "email_subs");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EmailSubs _EmailSubs = m_ds.getById(cid);

            if (_EmailSubs == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EmailSubs.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EmailSubs.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _EmailSubs);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_EmailSubs);
            try { 
	        	m_actionExtent.afterDelete(request, response, _EmailSubs);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

        	//setPage(session, "email_subs_home");    
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

			m_logger.info("Creating new EmailSubs" );
			EmailSubs _EmailSubs = new EmailSubs();	

			// Setting IDs for the object
			_EmailSubs.setSiteId(site.getId());

            _EmailSubs.setSubject(WebParamUtil.getStringValue(_EmailSubsForm.getSubject()));
			m_logger.debug("setting Subject=" +_EmailSubsForm.getSubject());
            _EmailSubs.setEmail(WebParamUtil.getStringValue(_EmailSubsForm.getEmail()));
			m_logger.debug("setting Email=" +_EmailSubsForm.getEmail());
            _EmailSubs.setFirstName(WebParamUtil.getStringValue(_EmailSubsForm.getFirstName()));
			m_logger.debug("setting FirstName=" +_EmailSubsForm.getFirstName());
            _EmailSubs.setLastName(WebParamUtil.getStringValue(_EmailSubsForm.getLastName()));
			m_logger.debug("setting LastName=" +_EmailSubsForm.getLastName());
            _EmailSubs.setConfirmed(WebParamUtil.getIntValue(_EmailSubsForm.getConfirmed()));
			m_logger.debug("setting Confirmed=" +_EmailSubsForm.getConfirmed());
            _EmailSubs.setDisabled(WebParamUtil.getIntValue(_EmailSubsForm.getDisabled()));
			m_logger.debug("setting Disabled=" +_EmailSubsForm.getDisabled());
            _EmailSubs.setCheckOpt1(WebParamUtil.getIntValue(_EmailSubsForm.getCheckOpt1()));
			m_logger.debug("setting CheckOpt1=" +_EmailSubsForm.getCheckOpt1());
            _EmailSubs.setCheckOpt2(WebParamUtil.getIntValue(_EmailSubsForm.getCheckOpt2()));
			m_logger.debug("setting CheckOpt2=" +_EmailSubsForm.getCheckOpt2());
            _EmailSubs.setExtraInfo(WebParamUtil.getStringValue(_EmailSubsForm.getExtraInfo()));
			m_logger.debug("setting ExtraInfo=" +_EmailSubsForm.getExtraInfo());
            _EmailSubs.setTimeCreated(WebParamUtil.getDateValue(_EmailSubsForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_EmailSubsForm.getTimeCreated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _EmailSubs);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EmailSubs);
			try{
		        m_actionExtent.afterAdd(request, response, _EmailSubs);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            //setPage(session, "email_subs_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EmailSubsForm _EmailSubsForm, EmailSubs _EmailSubs) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EmailSubs _EmailSubs = m_ds.getById(cid);

        _EmailSubs.setSubject(WebParamUtil.getStringValue(_EmailSubsForm.getSubject()));
        _EmailSubs.setEmail(WebParamUtil.getStringValue(_EmailSubsForm.getEmail()));
        _EmailSubs.setFirstName(WebParamUtil.getStringValue(_EmailSubsForm.getFirstName()));
        _EmailSubs.setLastName(WebParamUtil.getStringValue(_EmailSubsForm.getLastName()));
        _EmailSubs.setConfirmed(WebParamUtil.getIntValue(_EmailSubsForm.getConfirmed()));
        _EmailSubs.setDisabled(WebParamUtil.getIntValue(_EmailSubsForm.getDisabled()));
        _EmailSubs.setCheckOpt1(WebParamUtil.getIntValue(_EmailSubsForm.getCheckOpt1()));
        _EmailSubs.setCheckOpt2(WebParamUtil.getIntValue(_EmailSubsForm.getCheckOpt2()));
        _EmailSubs.setExtraInfo(WebParamUtil.getStringValue(_EmailSubsForm.getExtraInfo()));
        _EmailSubs.setTimeCreated(WebParamUtil.getDateValue(_EmailSubsForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _EmailSubs);
        m_ds.update(_EmailSubs);
        m_actionExtent.afterUpdate(request, response, _EmailSubs);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EmailSubs _EmailSubs) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EmailSubs _EmailSubs = m_ds.getById(cid);

        if (!isMissing(request.getParameter("subject"))) {
            m_logger.debug("updating param subject from " +_EmailSubs.getSubject() + "->" + request.getParameter("subject"));
            _EmailSubs.setSubject(WebParamUtil.getStringValue(request.getParameter("subject")));
        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_EmailSubs.getEmail() + "->" + request.getParameter("email"));
            _EmailSubs.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));
        }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_EmailSubs.getFirstName() + "->" + request.getParameter("firstName"));
            _EmailSubs.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));
        }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_EmailSubs.getLastName() + "->" + request.getParameter("lastName"));
            _EmailSubs.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));
        }
        if (!isMissing(request.getParameter("confirmed"))) {
            m_logger.debug("updating param confirmed from " +_EmailSubs.getConfirmed() + "->" + request.getParameter("confirmed"));
            _EmailSubs.setConfirmed(WebParamUtil.getIntValue(request.getParameter("confirmed")));
        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_EmailSubs.getDisabled() + "->" + request.getParameter("disabled"));
            _EmailSubs.setDisabled(WebParamUtil.getIntValue(request.getParameter("disabled")));
        }
        if (!isMissing(request.getParameter("checkOpt1"))) {
            m_logger.debug("updating param checkOpt1 from " +_EmailSubs.getCheckOpt1() + "->" + request.getParameter("checkOpt1"));
            _EmailSubs.setCheckOpt1(WebParamUtil.getIntValue(request.getParameter("checkOpt1")));
        }
        if (!isMissing(request.getParameter("checkOpt2"))) {
            m_logger.debug("updating param checkOpt2 from " +_EmailSubs.getCheckOpt2() + "->" + request.getParameter("checkOpt2"));
            _EmailSubs.setCheckOpt2(WebParamUtil.getIntValue(request.getParameter("checkOpt2")));
        }
        if (!isMissing(request.getParameter("extraInfo"))) {
            m_logger.debug("updating param extraInfo from " +_EmailSubs.getExtraInfo() + "->" + request.getParameter("extraInfo"));
            _EmailSubs.setExtraInfo(WebParamUtil.getStringValue(request.getParameter("extraInfo")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EmailSubs.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _EmailSubs.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _EmailSubs);
        m_ds.update(_EmailSubs);
        m_actionExtent.afterUpdate(request, response, _EmailSubs);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected EmailSubsDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EmailSubsAction.class);
}
