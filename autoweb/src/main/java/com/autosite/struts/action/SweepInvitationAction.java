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

import com.autosite.db.SweepInvitation;
import com.autosite.ds.SweepInvitationDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SweepInvitationForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class SweepInvitationAction extends AutositeCoreAction {

    public SweepInvitationAction(){
        m_ds = SweepInvitationDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SweepInvitationForm _SweepInvitationForm = (SweepInvitationForm) form;
        HttpSession session = request.getSession();

        setPage(session, "select_team");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "tell_friend");
            return mapping.findForward("default");
        }




        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            setPage(session, "tell_friend");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            sessionErrorText(session, "Internal error occurred.");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "tell_friend");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            SweepInvitation _SweepInvitation = m_ds.getById(cid);

            if (_SweepInvitation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            setPage(session, "tell_friend");
                return mapping.findForward("default");
            }

            if (_SweepInvitation.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepInvitation.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "tell_friend");
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _SweepInvitationForm, _SweepInvitation);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            setPage(session, "tell_friend");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            SweepInvitation _SweepInvitation = m_ds.getById(cid);

            if (_SweepInvitation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
			//Default error page but will be overridden by exception specific error page
            setPage(session, "tell_friend");
                return mapping.findForward("default");
            }

            if (_SweepInvitation.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepInvitation.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "tell_friend");
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _SweepInvitation);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "tell_friend");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            SweepInvitation _SweepInvitation = m_ds.getById(cid);

            if (_SweepInvitation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "tell_friend");
                return mapping.findForward("default");
            }

            if (_SweepInvitation.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepInvitation.getSiteId()); 
			//Default error page but will be overridden by exception specific error page
            setPage(session, "tell_friend");
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _SweepInvitation);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
			//Default error page but will be overridden by exception specific error page
            setPage(session, "tell_friend");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_SweepInvitation);
            try { 
                m_actionExtent.afterDelete(request, response, _SweepInvitation);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "tell_friend");
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

            m_logger.info("Creating new SweepInvitation" );
            SweepInvitation _SweepInvitation = new SweepInvitation();   

            // Setting IDs for the object
            _SweepInvitation.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _SweepInvitation.setUserId(user.getId());
            }

            _SweepInvitation.setEmail(WebParamUtil.getStringValue(_SweepInvitationForm.getEmail()));
            m_logger.debug("setting Email=" +_SweepInvitationForm.getEmail());
            _SweepInvitation.setMessage(WebParamUtil.getStringValue(_SweepInvitationForm.getMessage()));
            m_logger.debug("setting Message=" +_SweepInvitationForm.getMessage());
            _SweepInvitation.setInvitationSent(WebParamUtil.getIntValue(_SweepInvitationForm.getInvitationSent()));
            m_logger.debug("setting InvitationSent=" +_SweepInvitationForm.getInvitationSent());
            _SweepInvitation.setTimeCreated(WebParamUtil.getDateValue(_SweepInvitationForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SweepInvitationForm.getTimeCreated());
            _SweepInvitation.setTimeSent(WebParamUtil.getDateValue(_SweepInvitationForm.getTimeSent()));
            m_logger.debug("setting TimeSent=" +_SweepInvitationForm.getTimeSent());

			// Checking the uniqueness by configuration. 
			if ( m_ds.getByUserIdEmail(_SweepInvitation.getUserId(), _SweepInvitation.getEmail()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _SweepInvitation.getEmail());
                return mapping.findForward("default");
            }
            
            try{
                m_actionExtent.beforeAdd(request, response, _SweepInvitation);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, "tell_friend");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_SweepInvitation);
            try{
                m_actionExtent.afterAdd(request, response, _SweepInvitation);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, "tell_friend");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "sweep_invitation_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SweepInvitationForm _SweepInvitationForm, SweepInvitation _SweepInvitation) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepInvitation _SweepInvitation = m_ds.getById(cid);

        m_logger.debug("Before update " + SweepInvitationDS.objectToString(_SweepInvitation));

        _SweepInvitation.setEmail(WebParamUtil.getStringValue(_SweepInvitationForm.getEmail()));
        _SweepInvitation.setMessage(WebParamUtil.getStringValue(_SweepInvitationForm.getMessage()));
        _SweepInvitation.setInvitationSent(WebParamUtil.getIntValue(_SweepInvitationForm.getInvitationSent()));
        _SweepInvitation.setTimeCreated(WebParamUtil.getDateValue(_SweepInvitationForm.getTimeCreated()));
        _SweepInvitation.setTimeSent(WebParamUtil.getDateValue(_SweepInvitationForm.getTimeSent()));

        m_actionExtent.beforeUpdate(request, response, _SweepInvitation);
        m_ds.update(_SweepInvitation);
        m_actionExtent.afterUpdate(request, response, _SweepInvitation);
        m_logger.debug("After update " + SweepInvitationDS.objectToString(_SweepInvitation));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, SweepInvitation _SweepInvitation) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepInvitation _SweepInvitation = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_SweepInvitation.getUserId() + "->" + request.getParameter("userId"));
            _SweepInvitation.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_SweepInvitation.getEmail() + "->" + request.getParameter("email"));
            _SweepInvitation.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));
        }
        if (!isMissing(request.getParameter("message"))) {
            m_logger.debug("updating param message from " +_SweepInvitation.getMessage() + "->" + request.getParameter("message"));
            _SweepInvitation.setMessage(WebParamUtil.getStringValue(request.getParameter("message")));
        }
        if (!isMissing(request.getParameter("invitationSent"))) {
            m_logger.debug("updating param invitationSent from " +_SweepInvitation.getInvitationSent() + "->" + request.getParameter("invitationSent"));
            _SweepInvitation.setInvitationSent(WebParamUtil.getIntValue(request.getParameter("invitationSent")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SweepInvitation.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SweepInvitation.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeSent"))) {
            m_logger.debug("updating param timeSent from " +_SweepInvitation.getTimeSent() + "->" + request.getParameter("timeSent"));
            _SweepInvitation.setTimeSent(WebParamUtil.getDateValue(request.getParameter("timeSent")));
        }

        m_actionExtent.beforeUpdate(request, response, _SweepInvitation);
        m_ds.update(_SweepInvitation);
        m_actionExtent.afterUpdate(request, response, _SweepInvitation);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected SweepInvitationDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SweepInvitationAction.class);
}
