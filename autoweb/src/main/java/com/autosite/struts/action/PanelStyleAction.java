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

import com.autosite.db.PanelStyle;
import com.autosite.ds.PanelStyleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PanelStyleForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class PanelStyleAction extends AutositeCoreAction {

    public PanelStyleAction(){
	    m_ds = PanelStyleDS.getInstance();
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PanelStyleForm _PanelStyleForm = (PanelStyleForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelStyle _PanelStyle = m_ds.getById(cid);

            if (_PanelStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelStyle.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, _PanelStyleForm);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "panel_style_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelStyle _PanelStyle = m_ds.getById(cid);

            if (_PanelStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelStyle.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			setPage(session, "panel_style");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelStyle _PanelStyle = m_ds.getById(cid);

            if (_PanelStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelStyle.getSiteId()); 
                return mapping.findForward("default");
            }

        	m_ds.delete(_PanelStyle);
        	setPage(session, "panel_style_home");    
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

			m_logger.info("Creating new PanelStyle" );
			PanelStyle _PanelStyle = new PanelStyle();	

			// Setting IDs for the object
			_PanelStyle.setSiteId(site.getId());

            _PanelStyle.setPanelId(WebParamUtil.getLongValue(_PanelStyleForm.getPanelId()));
			m_logger.debug("setting PanelId=" +_PanelStyleForm.getPanelId());
            _PanelStyle.setStyleId(WebParamUtil.getLongValue(_PanelStyleForm.getStyleId()));
			m_logger.debug("setting StyleId=" +_PanelStyleForm.getStyleId());

			setPage(session, "panel_style_home");
            m_ds.put(_PanelStyle);

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, PanelStyleForm _PanelStyleForm) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        PanelStyle _PanelStyle = m_ds.getById(cid);

        _PanelStyle.setPanelId(WebParamUtil.getLongValue(_PanelStyleForm.getPanelId()));
        _PanelStyle.setStyleId(WebParamUtil.getLongValue(_PanelStyleForm.getStyleId()));
        m_ds.update(_PanelStyle);
    }

    protected void editField(HttpServletRequest request) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        PanelStyle _PanelStyle = m_ds.getById(cid);

        if (!isMissing(request.getParameter("panelId"))) {
            m_logger.debug("updating param panelId from " +_PanelStyle.getPanelId() + "->" + request.getParameter("panelId"));
            _PanelStyle.setPanelId(WebParamUtil.getLongValue(request.getParameter("panelId")));
        }
        if (!isMissing(request.getParameter("styleId"))) {
            m_logger.debug("updating param styleId from " +_PanelStyle.getStyleId() + "->" + request.getParameter("styleId"));
            _PanelStyle.setStyleId(WebParamUtil.getLongValue(request.getParameter("styleId")));
        }

        m_ds.update(_PanelStyle);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected PanelStyleDS m_ds;

    private static Logger m_logger = Logger.getLogger( PanelStyleAction.class);
}
