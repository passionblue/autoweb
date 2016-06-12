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

import com.autosite.db.SiteRegStart;
import com.autosite.ds.SiteRegStartDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SiteRegStartForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class SiteRegStartAction extends AutositeCoreAction {

    public SiteRegStartAction(){
        m_ds = SiteRegStartDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SiteRegStartForm _SiteRegStartForm = (SiteRegStartForm) form;
        HttpSession session = request.getSession();

        setPage(session, "site_reg_start");
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        m_logger.info("Creating new SiteRegStart" );
        SiteRegStart _SiteRegStart = new SiteRegStart();   

        // Setting IDs for the object

        //>>START<<
        //_SiteRegStart.setTargetDomain(WebParamUtil.getStringValue(_SiteRegStartForm.getTargetDomain()));
        //m_logger.debug("setting TargetDomain=" +_SiteRegStartForm.getTargetDomain());
        
        if (isMissing(_SiteRegStartForm.getTargetDomain())){
            sessionErrorText(session, "Domain name was not set. Please enter the domain name to set up");
            return mapping.findForward("default");
        }
        
        _SiteRegStart.setTargetDomain(SiteDS.getCommonUrl(WebParamUtil.getStringValue(_SiteRegStartForm.getTargetDomain())));
        m_logger.debug("setting TargetDomain=" +_SiteRegStart.getTargetDomain());
        //>>END<<
        
        try{
            m_actionExtent.beforeAdd(request, response, _SiteRegStart);
        }
        catch (Exception e) {
            sessionErrorText(session, e.getMessage());
            m_logger.error(e.getMessage(),e);
            if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
            return mapping.findForward("default");
        }
        m_ds.put(_SiteRegStart);
        try{
            m_actionExtent.afterAdd(request, response, _SiteRegStart);
        }
        catch (Exception e) {
            sessionErrorText(session, e.getMessage());
            m_logger.error(e.getMessage(),e);
            if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
            return mapping.findForward("default");
        }
            
		setPage(session, "site_reg_account_and_service_info");
        
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteRegStartForm _SiteRegStartForm, SiteRegStart _SiteRegStart) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteRegStart _SiteRegStart = m_ds.getById(cid);

        _SiteRegStart.setTargetDomain(WebParamUtil.getStringValue(_SiteRegStartForm.getTargetDomain()));

        m_actionExtent.beforeUpdate(request, response, _SiteRegStart);
        m_ds.update(_SiteRegStart);
        m_actionExtent.afterUpdate(request, response, _SiteRegStart);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, SiteRegStart _SiteRegStart) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteRegStart _SiteRegStart = m_ds.getById(cid);

        if (!isMissing(request.getParameter("targetDomain"))) {
            m_logger.debug("updating param targetDomain from " +_SiteRegStart.getTargetDomain() + "->" + request.getParameter("targetDomain"));
                _SiteRegStart.setTargetDomain(WebParamUtil.getStringValue(request.getParameter("targetDomain")));
            }

        m_actionExtent.beforeUpdate(request, response, _SiteRegStart);
        m_ds.update(_SiteRegStart);
        m_actionExtent.afterUpdate(request, response, _SiteRegStart);
    }

    protected boolean loginRequired() {
        return false;
    }

    protected SiteRegStartDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SiteRegStartAction.class);
}
