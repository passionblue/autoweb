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

import com.autosite.db.AutositeDefaults;
import com.autosite.ds.AutositeDefaultsDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.AutositeDefaultsForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class AutositeDefaultsAction extends AutositeCoreAction {

    public AutositeDefaultsAction(){
	    m_ds = AutositeDefaultsDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        AutositeDefaultsForm _AutositeDefaultsForm = (AutositeDefaultsForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            AutositeDefaults _AutositeDefaults = m_ds.getById(cid);

            if (_AutositeDefaults == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeDefaults.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeDefaults.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _AutositeDefaultsForm, _AutositeDefaults);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "autosite_defaults_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            AutositeDefaults _AutositeDefaults = m_ds.getById(cid);

            if (_AutositeDefaults == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeDefaults.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeDefaults.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _AutositeDefaults);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			setPage(session, "autosite_defaults");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            AutositeDefaults _AutositeDefaults = m_ds.getById(cid);

            if (_AutositeDefaults == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_AutositeDefaults.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeDefaults.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _AutositeDefaults);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before deletion.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_AutositeDefaults);
            try { 
	        	m_actionExtent.afterDelete(request, response, _AutositeDefaults);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after deletion process.");
                m_logger.error(e.getMessage(),e);
            }

        	setPage(session, "autosite_defaults_home");    
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

			m_logger.info("Creating new AutositeDefaults" );
			AutositeDefaults _AutositeDefaults = new AutositeDefaults();	

			// Setting IDs for the object
			_AutositeDefaults.setSiteId(site.getId());

            _AutositeDefaults.setStyleId(WebParamUtil.getLongValue(_AutositeDefaultsForm.getStyleId()));
			m_logger.debug("setting StyleId=" +_AutositeDefaultsForm.getStyleId());
            _AutositeDefaults.setLinkStyleId(WebParamUtil.getLongValue(_AutositeDefaultsForm.getLinkStyleId()));
			m_logger.debug("setting LinkStyleId=" +_AutositeDefaultsForm.getLinkStyleId());
            _AutositeDefaults.setTimeCreated(WebParamUtil.getDateValue(_AutositeDefaultsForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_AutositeDefaultsForm.getTimeCreated());
            _AutositeDefaults.setTimeUpdated(WebParamUtil.getDateValue(_AutositeDefaultsForm.getTimeUpdated()));
			m_logger.debug("setting TimeUpdated=" +_AutositeDefaultsForm.getTimeUpdated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _AutositeDefaults);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_AutositeDefaults);
			try{
		        m_actionExtent.afterAdd(request, response, _AutositeDefaults);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            setPage(session, "autosite_defaults_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, AutositeDefaultsForm _AutositeDefaultsForm, AutositeDefaults _AutositeDefaults) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        AutositeDefaults _AutositeDefaults = m_ds.getById(cid);

        _AutositeDefaults.setStyleId(WebParamUtil.getLongValue(_AutositeDefaultsForm.getStyleId()));
        _AutositeDefaults.setLinkStyleId(WebParamUtil.getLongValue(_AutositeDefaultsForm.getLinkStyleId()));
        _AutositeDefaults.setTimeCreated(WebParamUtil.getDateValue(_AutositeDefaultsForm.getTimeCreated()));
        _AutositeDefaults.setTimeUpdated(WebParamUtil.getDateValue(_AutositeDefaultsForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _AutositeDefaults);
        m_ds.update(_AutositeDefaults);
        m_actionExtent.afterUpdate(request, response, _AutositeDefaults);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDefaults _AutositeDefaults) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        AutositeDefaults _AutositeDefaults = m_ds.getById(cid);

        if (!isMissing(request.getParameter("styleId"))) {
            m_logger.debug("updating param styleId from " +_AutositeDefaults.getStyleId() + "->" + request.getParameter("styleId"));
            _AutositeDefaults.setStyleId(WebParamUtil.getLongValue(request.getParameter("styleId")));
        }
        if (!isMissing(request.getParameter("linkStyleId"))) {
            m_logger.debug("updating param linkStyleId from " +_AutositeDefaults.getLinkStyleId() + "->" + request.getParameter("linkStyleId"));
            _AutositeDefaults.setLinkStyleId(WebParamUtil.getLongValue(request.getParameter("linkStyleId")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeDefaults.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeDefaults.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_AutositeDefaults.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _AutositeDefaults.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _AutositeDefaults);
        m_ds.update(_AutositeDefaults);
        m_actionExtent.afterUpdate(request, response, _AutositeDefaults);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected AutositeDefaultsDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( AutositeDefaultsAction.class);
}
