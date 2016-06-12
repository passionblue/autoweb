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

import com.autosite.db.ResourceList;
import com.autosite.ds.ResourceListDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ResourceListForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.UploadResourceManager;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class ResourceListAction extends AutositeCoreAction {

    public ResourceListAction(){
	    m_ds = ResourceListDS.getInstance();
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ResourceListForm _ResourceListForm = (ResourceListForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ResourceList _ResourceList = m_ds.getById(cid);

            if (_ResourceList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ResourceList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ResourceList.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, _ResourceListForm);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "resource_list_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ResourceList _ResourceList = m_ds.getById(cid);

            if (_ResourceList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ResourceList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ResourceList.getSiteId()); 
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
			setPage(session, "resource_list");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ResourceList _ResourceList = m_ds.getById(cid);

            if (_ResourceList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ResourceList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ResourceList.getSiteId()); 
                return mapping.findForward("default");
            }

            
            //>>START<<
            
            try {
                UploadResourceManager.Instance().deleteFile(_ResourceList.getUrl());
            }
            catch (Exception e) {
                sessionErrorText(session, "Error occurred while deleting the uploaded resource.");
                setPage(session, "fileup");    
                return mapping.findForward("default");
            }
            //>>END<<
            
        	m_ds.delete(_ResourceList);
        	sessionTopText(session, "Delete succeeded!");
        	setPage(session, "fileup");    
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

			m_logger.info("Creating new ResourceList" );
			ResourceList _ResourceList = new ResourceList();	

			// Setting IDs for the object
			_ResourceList.setSiteId(site.getId());

            _ResourceList.setUrl(WebParamUtil.getStringValue(_ResourceListForm.getUrl()));
			m_logger.debug("setting Url=" +_ResourceListForm.getUrl());
            _ResourceList.setOriginalName(WebParamUtil.getStringValue(_ResourceListForm.getOriginalName()));
			m_logger.debug("setting OriginalName=" +_ResourceListForm.getOriginalName());
            _ResourceList.setSize(WebParamUtil.getIntValue(_ResourceListForm.getSize()));
			m_logger.debug("setting Size=" +_ResourceListForm.getSize());
            _ResourceList.setResourceType(WebParamUtil.getIntValue(_ResourceListForm.getResourceType()));
			m_logger.debug("setting ResourceType=" +_ResourceListForm.getResourceType());
            _ResourceList.setTimeCreaeted(WebParamUtil.getDateValue(_ResourceListForm.getTimeCreaeted()));
			m_logger.debug("setting TimeCreaeted=" +_ResourceListForm.getTimeCreaeted());
            _ResourceList.setTimeUpdated(WebParamUtil.getDateValue(_ResourceListForm.getTimeUpdated()));
			m_logger.debug("setting TimeUpdated=" +_ResourceListForm.getTimeUpdated());

			setPage(session, "resource_list_home");
            m_ds.put(_ResourceList);

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, ResourceListForm _ResourceListForm) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        ResourceList _ResourceList = m_ds.getById(cid);

        _ResourceList.setUrl(WebParamUtil.getStringValue(_ResourceListForm.getUrl()));
        _ResourceList.setOriginalName(WebParamUtil.getStringValue(_ResourceListForm.getOriginalName()));
        _ResourceList.setSize(WebParamUtil.getIntValue(_ResourceListForm.getSize()));
        _ResourceList.setResourceType(WebParamUtil.getIntValue(_ResourceListForm.getResourceType()));
        _ResourceList.setTimeCreaeted(WebParamUtil.getDateValue(_ResourceListForm.getTimeCreaeted()));
        _ResourceList.setTimeUpdated(WebParamUtil.getDateValue(_ResourceListForm.getTimeUpdated()));
        m_ds.update(_ResourceList);
    }

    protected void editField(HttpServletRequest request) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        ResourceList _ResourceList = m_ds.getById(cid);

        if (!isMissing(request.getParameter("url"))) {
            m_logger.debug("updating param url from " +_ResourceList.getUrl() + "->" + request.getParameter("url"));
            _ResourceList.setUrl(WebParamUtil.getStringValue(request.getParameter("url")));
        }
        if (!isMissing(request.getParameter("originalName"))) {
            m_logger.debug("updating param originalName from " +_ResourceList.getOriginalName() + "->" + request.getParameter("originalName"));
            _ResourceList.setOriginalName(WebParamUtil.getStringValue(request.getParameter("originalName")));
        }
        if (!isMissing(request.getParameter("size"))) {
            m_logger.debug("updating param size from " +_ResourceList.getSize() + "->" + request.getParameter("size"));
            _ResourceList.setSize(WebParamUtil.getIntValue(request.getParameter("size")));
        }
        if (!isMissing(request.getParameter("resourceType"))) {
            m_logger.debug("updating param resourceType from " +_ResourceList.getResourceType() + "->" + request.getParameter("resourceType"));
            _ResourceList.setResourceType(WebParamUtil.getIntValue(request.getParameter("resourceType")));
        }
        if (!isMissing(request.getParameter("timeCreaeted"))) {
            m_logger.debug("updating param timeCreaeted from " +_ResourceList.getTimeCreaeted() + "->" + request.getParameter("timeCreaeted"));
            _ResourceList.setTimeCreaeted(WebParamUtil.getDateValue(request.getParameter("timeCreaeted")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_ResourceList.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _ResourceList.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_ds.update(_ResourceList);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected ResourceListDS m_ds;

    private static Logger m_logger = Logger.getLogger( ResourceListAction.class);
}
