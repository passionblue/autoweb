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

import com.autosite.db.SiteRegAccountServiceInfo;
import com.autosite.ds.SiteRegAccountServiceInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SiteRegAccountServiceInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class SiteRegAccountServiceInfoAction extends AutositeCoreAction {

    public SiteRegAccountServiceInfoAction(){
        m_ds = SiteRegAccountServiceInfoDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SiteRegAccountServiceInfoForm _SiteRegAccountServiceInfoForm = (SiteRegAccountServiceInfoForm) form;
        HttpSession session = request.getSession();

            setPage(session, "site_reg_account_and_service_info");
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }


            m_logger.info("Creating new SiteRegAccountServiceInfo" );
            SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = new SiteRegAccountServiceInfo();   

            // Setting IDs for the object

            _SiteRegAccountServiceInfo.setEmail(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getEmail()));
            m_logger.debug("setting Email=" +_SiteRegAccountServiceInfoForm.getEmail());
            _SiteRegAccountServiceInfo.setEmailRetype(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getEmailRetype()));
            m_logger.debug("setting EmailRetype=" +_SiteRegAccountServiceInfoForm.getEmailRetype());
            _SiteRegAccountServiceInfo.setCompany(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getCompany()));
            m_logger.debug("setting Company=" +_SiteRegAccountServiceInfoForm.getCompany());
            _SiteRegAccountServiceInfo.setFirstName(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_SiteRegAccountServiceInfoForm.getFirstName());
            _SiteRegAccountServiceInfo.setLastName(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getLastName()));
            m_logger.debug("setting LastName=" +_SiteRegAccountServiceInfoForm.getLastName());
            _SiteRegAccountServiceInfo.setPhone(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getPhone()));
            m_logger.debug("setting Phone=" +_SiteRegAccountServiceInfoForm.getPhone());
 
            
            try{
                m_actionExtent.beforeAdd(request, response, _SiteRegAccountServiceInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_SiteRegAccountServiceInfo);
            try{
                m_actionExtent.afterAdd(request, response, _SiteRegAccountServiceInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
		setPage(session, "site_reg_payment_info");

        
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteRegAccountServiceInfoForm _SiteRegAccountServiceInfoForm, SiteRegAccountServiceInfo _SiteRegAccountServiceInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = m_ds.getById(cid);

        _SiteRegAccountServiceInfo.setEmail(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getEmail()));
        _SiteRegAccountServiceInfo.setEmailRetype(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getEmailRetype()));
        _SiteRegAccountServiceInfo.setCompany(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getCompany()));
        _SiteRegAccountServiceInfo.setFirstName(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getFirstName()));
        _SiteRegAccountServiceInfo.setLastName(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getLastName()));
        _SiteRegAccountServiceInfo.setPhone(WebParamUtil.getStringValue(_SiteRegAccountServiceInfoForm.getPhone()));

        m_actionExtent.beforeUpdate(request, response, _SiteRegAccountServiceInfo);
        m_ds.update(_SiteRegAccountServiceInfo);
        m_actionExtent.afterUpdate(request, response, _SiteRegAccountServiceInfo);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, SiteRegAccountServiceInfo _SiteRegAccountServiceInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = m_ds.getById(cid);

        if (!isMissing(request.getParameter("targetDomain"))) {
            m_logger.debug("updating param targetDomain from " +_SiteRegAccountServiceInfo.getTargetDomain() + "->" + request.getParameter("targetDomain"));
                _SiteRegAccountServiceInfo.setTargetDomain(WebParamUtil.getStringValue(request.getParameter("targetDomain")));
            }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_SiteRegAccountServiceInfo.getEmail() + "->" + request.getParameter("email"));
                _SiteRegAccountServiceInfo.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));
            }
        if (!isMissing(request.getParameter("emailRetype"))) {
            m_logger.debug("updating param emailRetype from " +_SiteRegAccountServiceInfo.getEmailRetype() + "->" + request.getParameter("emailRetype"));
                _SiteRegAccountServiceInfo.setEmailRetype(WebParamUtil.getStringValue(request.getParameter("emailRetype")));
            }
        if (!isMissing(request.getParameter("company"))) {
            m_logger.debug("updating param company from " +_SiteRegAccountServiceInfo.getCompany() + "->" + request.getParameter("company"));
                _SiteRegAccountServiceInfo.setCompany(WebParamUtil.getStringValue(request.getParameter("company")));
            }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_SiteRegAccountServiceInfo.getFirstName() + "->" + request.getParameter("firstName"));
                _SiteRegAccountServiceInfo.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));
            }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_SiteRegAccountServiceInfo.getLastName() + "->" + request.getParameter("lastName"));
                _SiteRegAccountServiceInfo.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));
            }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_SiteRegAccountServiceInfo.getPhone() + "->" + request.getParameter("phone"));
                _SiteRegAccountServiceInfo.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));
            }

        m_actionExtent.beforeUpdate(request, response, _SiteRegAccountServiceInfo);
        m_ds.update(_SiteRegAccountServiceInfo);
        m_actionExtent.afterUpdate(request, response, _SiteRegAccountServiceInfo);
    }

    protected boolean loginRequired() {
        return false;
    }

    protected SiteRegAccountServiceInfoDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SiteRegAccountServiceInfoAction.class);
}
