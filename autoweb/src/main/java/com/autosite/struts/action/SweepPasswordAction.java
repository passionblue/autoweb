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

import com.autosite.db.SweepPassword;
import com.autosite.ds.SweepPasswordDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SweepPasswordForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class SweepPasswordAction extends AutositeCoreAction {

    public SweepPasswordAction(){
        m_ds = SweepPasswordDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SweepPasswordForm _SweepPasswordForm = (SweepPasswordForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }


            m_logger.info("Creating new SweepPassword" );
            SweepPassword _SweepPassword = new SweepPassword();   

            // Setting IDs for the object

            _SweepPassword.setSendPasswordEmail(WebParamUtil.getStringValue(_SweepPasswordForm.getSendPasswordEmail()));
            m_logger.debug("setting SendPasswordEmail=" +_SweepPasswordForm.getSendPasswordEmail());
            _SweepPassword.setOldPassword(WebParamUtil.getStringValue(_SweepPasswordForm.getOldPassword()));
            m_logger.debug("setting OldPassword=" +_SweepPasswordForm.getOldPassword());
            _SweepPassword.setNewPassword(WebParamUtil.getStringValue(_SweepPasswordForm.getNewPassword()));
            m_logger.debug("setting NewPassword=" +_SweepPasswordForm.getNewPassword());
            _SweepPassword.setPasswordRetype(WebParamUtil.getStringValue(_SweepPasswordForm.getPasswordRetype()));
            m_logger.debug("setting PasswordRetype=" +_SweepPasswordForm.getPasswordRetype());

            
            try{
                m_actionExtent.beforeAdd(request, response, _SweepPassword);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //m_ds.put(_SweepPassword);
            try{
                m_actionExtent.afterAdd(request, response, _SweepPassword);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
        
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, SweepPasswordForm _SweepPasswordForm, SweepPassword _SweepPassword) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SweepPassword _SweepPassword = m_ds.getById(cid);

        _SweepPassword.setSendPasswordEmail(WebParamUtil.getStringValue(_SweepPasswordForm.getSendPasswordEmail()));
        _SweepPassword.setOldPassword(WebParamUtil.getStringValue(_SweepPasswordForm.getOldPassword()));
        _SweepPassword.setNewPassword(WebParamUtil.getStringValue(_SweepPasswordForm.getNewPassword()));
        _SweepPassword.setPasswordRetype(WebParamUtil.getStringValue(_SweepPasswordForm.getPasswordRetype()));

        m_actionExtent.beforeUpdate(request, response, _SweepPassword);
        //m_ds.update(_SweepPassword);
        m_actionExtent.afterUpdate(request, response, _SweepPassword);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, SweepPassword _SweepPassword) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SweepPassword _SweepPassword = m_ds.getById(cid);

        if (!isMissing(request.getParameter("sendPasswordEmail"))) {
            m_logger.debug("updating param sendPasswordEmail from " +_SweepPassword.getSendPasswordEmail() + "->" + request.getParameter("sendPasswordEmail"));
                _SweepPassword.setSendPasswordEmail(WebParamUtil.getStringValue(request.getParameter("sendPasswordEmail")));
            }
        if (!isMissing(request.getParameter("oldPassword"))) {
            m_logger.debug("updating param oldPassword from " +_SweepPassword.getOldPassword() + "->" + request.getParameter("oldPassword"));
                _SweepPassword.setOldPassword(WebParamUtil.getStringValue(request.getParameter("oldPassword")));
            }
        if (!isMissing(request.getParameter("newPassword"))) {
            m_logger.debug("updating param newPassword from " +_SweepPassword.getNewPassword() + "->" + request.getParameter("newPassword"));
                _SweepPassword.setNewPassword(WebParamUtil.getStringValue(request.getParameter("newPassword")));
            }
        if (!isMissing(request.getParameter("passwordRetype"))) {
            m_logger.debug("updating param passwordRetype from " +_SweepPassword.getPasswordRetype() + "->" + request.getParameter("passwordRetype"));
                _SweepPassword.setPasswordRetype(WebParamUtil.getStringValue(request.getParameter("passwordRetype")));
            }

        m_actionExtent.beforeUpdate(request, response, _SweepPassword);
        //m_ds.update(_SweepPassword);
        m_actionExtent.afterUpdate(request, response, _SweepPassword);
    }

    protected boolean loginRequired() {
        return false;
    }

    protected SweepPasswordDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SweepPasswordAction.class);
}
