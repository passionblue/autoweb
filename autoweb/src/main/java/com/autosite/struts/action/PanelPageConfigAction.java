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

import com.autosite.db.PanelPageConfig;
import com.autosite.ds.PanelPageConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PanelPageConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class PanelPageConfigAction extends AutositeCoreAction {

    public PanelPageConfigAction(){
	    m_ds = PanelPageConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PanelPageConfigForm _PanelPageConfigForm = (PanelPageConfigForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelPageConfig _PanelPageConfig = m_ds.getById(cid);

            if (_PanelPageConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelPageConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelPageConfig.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _PanelPageConfigForm, _PanelPageConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "panel_page_config_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelPageConfig _PanelPageConfig = m_ds.getById(cid);

            if (_PanelPageConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelPageConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelPageConfig.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _PanelPageConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			setPage(session, "panel_page_config");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            PanelPageConfig _PanelPageConfig = m_ds.getById(cid);

            if (_PanelPageConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelPageConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelPageConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _PanelPageConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before deletion.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_PanelPageConfig);
            try { 
	        	m_actionExtent.afterDelete(request, response, _PanelPageConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after deletion process.");
                m_logger.error(e.getMessage(),e);
            }

        	setPage(session, "panel_page_config_home");    
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

			m_logger.info("Creating new PanelPageConfig" );
			PanelPageConfig _PanelPageConfig = new PanelPageConfig();	

			// Setting IDs for the object
			_PanelPageConfig.setSiteId(site.getId());

            _PanelPageConfig.setPanelId(WebParamUtil.getLongValue(_PanelPageConfigForm.getPanelId()));
			m_logger.debug("setting PanelId=" +_PanelPageConfigForm.getPanelId());
            _PanelPageConfig.setPageDisplaySummary(WebParamUtil.getIntValue(_PanelPageConfigForm.getPageDisplaySummary()));
			m_logger.debug("setting PageDisplaySummary=" +_PanelPageConfigForm.getPageDisplaySummary());
            _PanelPageConfig.setTimeCreated(WebParamUtil.getDateValue(_PanelPageConfigForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_PanelPageConfigForm.getTimeCreated());
            _PanelPageConfig.setTimeUpdated(WebParamUtil.getDateValue(_PanelPageConfigForm.getTimeUpdated()));
			m_logger.debug("setting TimeUpdated=" +_PanelPageConfigForm.getTimeUpdated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _PanelPageConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_PanelPageConfig);
			try{
		        m_actionExtent.afterAdd(request, response, _PanelPageConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            setPage(session, "panel_page_config_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, PanelPageConfigForm _PanelPageConfigForm, PanelPageConfig _PanelPageConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        PanelPageConfig _PanelPageConfig = m_ds.getById(cid);

        _PanelPageConfig.setPanelId(WebParamUtil.getLongValue(_PanelPageConfigForm.getPanelId()));
        _PanelPageConfig.setPageDisplaySummary(WebParamUtil.getIntValue(_PanelPageConfigForm.getPageDisplaySummary()));
        _PanelPageConfig.setTimeCreated(WebParamUtil.getDateValue(_PanelPageConfigForm.getTimeCreated()));
        _PanelPageConfig.setTimeUpdated(WebParamUtil.getDateValue(_PanelPageConfigForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _PanelPageConfig);
        m_ds.update(_PanelPageConfig);
        m_actionExtent.afterUpdate(request, response, _PanelPageConfig);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, PanelPageConfig _PanelPageConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        PanelPageConfig _PanelPageConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("panelId"))) {
            m_logger.debug("updating param panelId from " +_PanelPageConfig.getPanelId() + "->" + request.getParameter("panelId"));
            _PanelPageConfig.setPanelId(WebParamUtil.getLongValue(request.getParameter("panelId")));
        }
        if (!isMissing(request.getParameter("pageDisplaySummary"))) {
            m_logger.debug("updating param pageDisplaySummary from " +_PanelPageConfig.getPageDisplaySummary() + "->" + request.getParameter("pageDisplaySummary"));
            _PanelPageConfig.setPageDisplaySummary(WebParamUtil.getIntValue(request.getParameter("pageDisplaySummary")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PanelPageConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PanelPageConfig.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_PanelPageConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _PanelPageConfig.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _PanelPageConfig);
        m_ds.update(_PanelPageConfig);
        m_actionExtent.afterUpdate(request, response, _PanelPageConfig);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected PanelPageConfigDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PanelPageConfigAction.class);
}
