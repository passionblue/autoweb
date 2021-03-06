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

import com.autosite.db.PanelLinkStyle;
import com.autosite.ds.PanelLinkStyleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PanelLinkStyleForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class PanelLinkStyleAction extends AutositeCoreAction {

    public PanelLinkStyleAction(){
	    m_ds = PanelLinkStyleDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PanelLinkStyleForm _PanelLinkStyleForm = (PanelLinkStyleForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelLinkStyle _PanelLinkStyle = m_ds.getById(cid);

            if (_PanelLinkStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelLinkStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelLinkStyle.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _PanelLinkStyleForm, _PanelLinkStyle);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "panel_link_style_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelLinkStyle _PanelLinkStyle = m_ds.getById(cid);

            if (_PanelLinkStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelLinkStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelLinkStyle.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _PanelLinkStyle);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			setPage(session, "panel_link_style");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelLinkStyle _PanelLinkStyle = m_ds.getById(cid);

            if (_PanelLinkStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelLinkStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelLinkStyle.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _PanelLinkStyle);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before deletion.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_PanelLinkStyle);
            try { 
	        	m_actionExtent.afterDelete(request, response, _PanelLinkStyle);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after deletion process.");
                m_logger.error(e.getMessage(),e);
            }

        	setPage(session, "panel_link_style_home");    
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

			m_logger.info("Creating new PanelLinkStyle" );
			PanelLinkStyle _PanelLinkStyle = new PanelLinkStyle();	

			// Setting IDs for the object
			_PanelLinkStyle.setSiteId(site.getId());

            _PanelLinkStyle.setPanelId(WebParamUtil.getLongValue(_PanelLinkStyleForm.getPanelId()));
			m_logger.debug("setting PanelId=" +_PanelLinkStyleForm.getPanelId());
            _PanelLinkStyle.setStyleId(WebParamUtil.getLongValue(_PanelLinkStyleForm.getStyleId()));
			m_logger.debug("setting StyleId=" +_PanelLinkStyleForm.getStyleId());
            _PanelLinkStyle.setTimeCreated(WebParamUtil.getDateValue(_PanelLinkStyleForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_PanelLinkStyleForm.getTimeCreated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _PanelLinkStyle);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_PanelLinkStyle);
			try{
		        m_actionExtent.afterAdd(request, response, _PanelLinkStyle);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            setPage(session, "panel_link_style_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, PanelLinkStyleForm _PanelLinkStyleForm, PanelLinkStyle _PanelLinkStyle) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        PanelLinkStyle _PanelLinkStyle = m_ds.getById(cid);

        _PanelLinkStyle.setPanelId(WebParamUtil.getLongValue(_PanelLinkStyleForm.getPanelId()));
        _PanelLinkStyle.setStyleId(WebParamUtil.getLongValue(_PanelLinkStyleForm.getStyleId()));
        _PanelLinkStyle.setTimeCreated(WebParamUtil.getDateValue(_PanelLinkStyleForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _PanelLinkStyle);
        m_ds.update(_PanelLinkStyle);
        m_actionExtent.afterUpdate(request, response, _PanelLinkStyle);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, PanelLinkStyle _PanelLinkStyle) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        PanelLinkStyle _PanelLinkStyle = m_ds.getById(cid);

        if (!isMissing(request.getParameter("panelId"))) {
            m_logger.debug("updating param panelId from " +_PanelLinkStyle.getPanelId() + "->" + request.getParameter("panelId"));
            _PanelLinkStyle.setPanelId(WebParamUtil.getLongValue(request.getParameter("panelId")));
        }
        if (!isMissing(request.getParameter("styleId"))) {
            m_logger.debug("updating param styleId from " +_PanelLinkStyle.getStyleId() + "->" + request.getParameter("styleId"));
            _PanelLinkStyle.setStyleId(WebParamUtil.getLongValue(request.getParameter("styleId")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PanelLinkStyle.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PanelLinkStyle.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _PanelLinkStyle);
        m_ds.update(_PanelLinkStyle);
        m_actionExtent.afterUpdate(request, response, _PanelLinkStyle);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected PanelLinkStyleDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PanelLinkStyleAction.class);
}
