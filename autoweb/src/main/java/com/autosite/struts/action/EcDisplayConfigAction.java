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

import com.autosite.db.EcDisplayConfig;
import com.autosite.ds.EcDisplayConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcDisplayConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcDisplayConfigAction extends AutositeCoreAction {

    public EcDisplayConfigAction(){
	    m_ds = EcDisplayConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcDisplayConfigForm _EcDisplayConfigForm = (EcDisplayConfigForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            sessionErrorText(session, "Internal error occurred during before deletion.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }
        
        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcDisplayConfig _EcDisplayConfig = m_ds.getById(cid);

            if (_EcDisplayConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcDisplayConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcDisplayConfig.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _EcDisplayConfigForm, _EcDisplayConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "ec_display_config_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcDisplayConfig _EcDisplayConfig = m_ds.getById(cid);

            if (_EcDisplayConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcDisplayConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcDisplayConfig.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _EcDisplayConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			setPage(session, "ec_display_config");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcDisplayConfig _EcDisplayConfig = m_ds.getById(cid);

            if (_EcDisplayConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcDisplayConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcDisplayConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _EcDisplayConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before deletion.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_EcDisplayConfig);
            try { 
	        	m_actionExtent.afterDelete(request, response, _EcDisplayConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after deletion process.");
                m_logger.error(e.getMessage(),e);
            }

        	setPage(session, "ec_display_config_home");    
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

			m_logger.info("Creating new EcDisplayConfig" );
			EcDisplayConfig _EcDisplayConfig = new EcDisplayConfig();	

			// Setting IDs for the object
			_EcDisplayConfig.setSiteId(site.getId());

            _EcDisplayConfig.setColumnCount(WebParamUtil.getIntValue(_EcDisplayConfigForm.getColumnCount()));
			m_logger.debug("setting ColumnCount=" +_EcDisplayConfigForm.getColumnCount());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _EcDisplayConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcDisplayConfig);
			try{
		        m_actionExtent.afterAdd(request, response, _EcDisplayConfig);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            setPage(session, "ec_display_config_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcDisplayConfigForm _EcDisplayConfigForm, EcDisplayConfig _EcDisplayConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcDisplayConfig _EcDisplayConfig = m_ds.getById(cid);

        _EcDisplayConfig.setColumnCount(WebParamUtil.getIntValue(_EcDisplayConfigForm.getColumnCount()));

        m_actionExtent.beforeUpdate(request, response, _EcDisplayConfig);
        m_ds.update(_EcDisplayConfig);
        m_actionExtent.afterUpdate(request, response, _EcDisplayConfig);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcDisplayConfig _EcDisplayConfig) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcDisplayConfig _EcDisplayConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("columnCount"))) {
            m_logger.debug("updating param columnCount from " +_EcDisplayConfig.getColumnCount() + "->" + request.getParameter("columnCount"));
            _EcDisplayConfig.setColumnCount(WebParamUtil.getIntValue(request.getParameter("columnCount")));
        }

        m_actionExtent.beforeUpdate(request, response, _EcDisplayConfig);
        m_ds.update(_EcDisplayConfig);
        m_actionExtent.afterUpdate(request, response, _EcDisplayConfig);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected EcDisplayConfigDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcDisplayConfigAction.class);
}
