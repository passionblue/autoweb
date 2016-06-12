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

import com.autosite.db.SweepRegister;
import com.autosite.ds.SweepRegisterDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SweepRegisterForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class SweepRegisterAction extends AutositeCoreAction {

    public SweepRegisterAction(){
        m_ds = SweepRegisterDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SweepRegisterForm _SweepRegisterForm = (SweepRegisterForm) form;
        HttpSession session = request.getSession();

        setPage(session, "sweep_worldcup_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "sweep_worldcup_join");
            return mapping.findForward("default");
        }




        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            setPage(session, "sweep_worldcup_join");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            sessionErrorText(session, "Internal error occurred.");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "sweep_worldcup_join");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            SweepRegister _SweepRegister = m_ds.getById(cid);

            if (_SweepRegister == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            setPage(session, "sweep_worldcup_join");
                return mapping.findForward("default");
            }

            if (_SweepRegister.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepRegister.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "sweep_worldcup_join");
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _SweepRegisterForm, _SweepRegister);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            setPage(session, "sweep_worldcup_join");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            SweepRegister _SweepRegister = m_ds.getById(cid);

            if (_SweepRegister == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
			//Default error page but will be overridden by exception specific error page
            setPage(session, "sweep_worldcup_join");
                return mapping.findForward("default");
            }

            if (_SweepRegister.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepRegister.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "sweep_worldcup_join");
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _SweepRegister);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "sweep_worldcup_join");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            SweepRegister _SweepRegister = m_ds.getById(cid);

            if (_SweepRegister == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "sweep_worldcup_join");
                return mapping.findForward("default");
            }

            if (_SweepRegister.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepRegister.getSiteId()); 
			//Default error page but will be overridden by exception specific error page
            setPage(session, "sweep_worldcup_join");
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _SweepRegister);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
			//Default error page but will be overridden by exception specific error page
            setPage(session, "sweep_worldcup_join");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_SweepRegister);
            try { 
                m_actionExtent.afterDelete(request, response, _SweepRegister);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "sweep_worldcup_join");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            setPage(session, "home");
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

            m_logger.info("Creating new SweepRegister" );
            SweepRegister _SweepRegister = new SweepRegister();   

            // Setting IDs for the object
            _SweepRegister.setSiteId(site.getId());

            _SweepRegister.setEmail(WebParamUtil.getStringValue(_SweepRegisterForm.getEmail()));
            m_logger.debug("setting Email=" +_SweepRegisterForm.getEmail());
            _SweepRegister.setPassword(WebParamUtil.getStringValue(_SweepRegisterForm.getPassword()));
            m_logger.debug("setting Password=" +_SweepRegisterForm.getPassword());
            _SweepRegister.setSex(WebParamUtil.getIntValue(_SweepRegisterForm.getSex()));
            m_logger.debug("setting Sex=" +_SweepRegisterForm.getSex());
            _SweepRegister.setAgeRange(WebParamUtil.getIntValue(_SweepRegisterForm.getAgeRange()));
            m_logger.debug("setting AgeRange=" +_SweepRegisterForm.getAgeRange());
            _SweepRegister.setAgreeTerms(WebParamUtil.getCheckboxValue(_SweepRegisterForm.getAgreeTerms()));
            m_logger.debug("setting AgreeTerms=" +_SweepRegisterForm.getAgreeTerms());
            _SweepRegister.setTimeCreated(WebParamUtil.getDateValue(_SweepRegisterForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SweepRegisterForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _SweepRegister);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, "sweep_worldcup_join");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_SweepRegister);
            try{
                m_actionExtent.afterAdd(request, response, _SweepRegister);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, "sweep_worldcup_join");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "sweep_register_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SweepRegisterForm _SweepRegisterForm, SweepRegister _SweepRegister) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepRegister _SweepRegister = m_ds.getById(cid);

        m_logger.debug("Before update " + SweepRegisterDS.objectToString(_SweepRegister));

        _SweepRegister.setPassword(WebParamUtil.getStringValue(_SweepRegisterForm.getPassword()));
        _SweepRegister.setSex(WebParamUtil.getIntValue(_SweepRegisterForm.getSex()));
        _SweepRegister.setAgeRange(WebParamUtil.getIntValue(_SweepRegisterForm.getAgeRange()));
        _SweepRegister.setAgreeTerms(WebParamUtil.getCheckboxValue(_SweepRegisterForm.getAgreeTerms()));
        _SweepRegister.setTimeCreated(WebParamUtil.getDateValue(_SweepRegisterForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _SweepRegister);
        m_ds.update(_SweepRegister);
        m_actionExtent.afterUpdate(request, response, _SweepRegister);
        m_logger.debug("After update " + SweepRegisterDS.objectToString(_SweepRegister));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, SweepRegister _SweepRegister) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepRegister _SweepRegister = m_ds.getById(cid);

        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_SweepRegister.getEmail() + "->" + request.getParameter("email"));
            _SweepRegister.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));
        }
        if (!isMissing(request.getParameter("password"))) {
            m_logger.debug("updating param password from " +_SweepRegister.getPassword() + "->" + request.getParameter("password"));
            _SweepRegister.setPassword(WebParamUtil.getStringValue(request.getParameter("password")));
        }
        if (!isMissing(request.getParameter("sex"))) {
            m_logger.debug("updating param sex from " +_SweepRegister.getSex() + "->" + request.getParameter("sex"));
            _SweepRegister.setSex(WebParamUtil.getIntValue(request.getParameter("sex")));
        }
        if (!isMissing(request.getParameter("ageRange"))) {
            m_logger.debug("updating param ageRange from " +_SweepRegister.getAgeRange() + "->" + request.getParameter("ageRange"));
            _SweepRegister.setAgeRange(WebParamUtil.getIntValue(request.getParameter("ageRange")));
        }
        if (!isMissing(request.getParameter("agreeTerms"))) {
            m_logger.debug("updating param agreeTerms from " +_SweepRegister.getAgreeTerms() + "->" + request.getParameter("agreeTerms"));
            _SweepRegister.setAgreeTerms(WebParamUtil.getCheckboxValue(request.getParameter("agreeTerms")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SweepRegister.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SweepRegister.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _SweepRegister);
        m_ds.update(_SweepRegister);
        m_actionExtent.afterUpdate(request, response, _SweepRegister);
    }


    protected boolean loginRequired() {
        return false;
    }
    
    protected SweepRegisterDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SweepRegisterAction.class);
}
