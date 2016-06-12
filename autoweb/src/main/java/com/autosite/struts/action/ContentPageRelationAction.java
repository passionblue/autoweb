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

import com.autosite.db.ContentPageRelation;
import com.autosite.ds.ContentPageRelationDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ContentPageRelationForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class ContentPageRelationAction extends AutositeCoreAction {

    public ContentPageRelationAction(){
	    m_ds = ContentPageRelationDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ContentPageRelationForm _ContentPageRelationForm = (ContentPageRelationForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ContentPageRelation _ContentPageRelation = m_ds.getById(cid);

            if (_ContentPageRelation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

			try{
	            edit(request, response, _ContentPageRelationForm, _ContentPageRelation);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "content_page_relation_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ContentPageRelation _ContentPageRelation = m_ds.getById(cid);

            if (_ContentPageRelation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }


			try{
	            editField(request, response, _ContentPageRelation);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			setPage(session, "content_page_relation");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            ContentPageRelation _ContentPageRelation = m_ds.getById(cid);

            if (_ContentPageRelation == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }


            try {
	        	m_actionExtent.beforeDelete(request, response, _ContentPageRelation);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before deletion.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_ContentPageRelation);
            try { 
	        	m_actionExtent.afterDelete(request, response, _ContentPageRelation);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after deletion process.");
                m_logger.error(e.getMessage(),e);
            }

        	setPage(session, "content_page_relation_home");    
        }
        else {


			m_logger.info("Creating new ContentPageRelation" );
			ContentPageRelation _ContentPageRelation = new ContentPageRelation();	

			// Setting IDs for the object

            _ContentPageRelation.setContentId(WebParamUtil.getLongValue(_ContentPageRelationForm.getContentId()));
			m_logger.debug("setting ContentId=" +_ContentPageRelationForm.getContentId());
            _ContentPageRelation.setPageId(WebParamUtil.getLongValue(_ContentPageRelationForm.getPageId()));
			m_logger.debug("setting PageId=" +_ContentPageRelationForm.getPageId());
            _ContentPageRelation.setTimeCreated(WebParamUtil.getDateValue(_ContentPageRelationForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_ContentPageRelationForm.getTimeCreated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _ContentPageRelation);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_ContentPageRelation);
			try{
		        m_actionExtent.afterAdd(request, response, _ContentPageRelation);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            setPage(session, "content_page_relation_home");

        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentPageRelationForm _ContentPageRelationForm, ContentPageRelation _ContentPageRelation) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        ContentPageRelation _ContentPageRelation = m_ds.getById(cid);

        _ContentPageRelation.setContentId(WebParamUtil.getLongValue(_ContentPageRelationForm.getContentId()));
        _ContentPageRelation.setPageId(WebParamUtil.getLongValue(_ContentPageRelationForm.getPageId()));
        _ContentPageRelation.setTimeCreated(WebParamUtil.getDateValue(_ContentPageRelationForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _ContentPageRelation);
        m_ds.update(_ContentPageRelation);
        m_actionExtent.afterUpdate(request, response, _ContentPageRelation);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, ContentPageRelation _ContentPageRelation) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        ContentPageRelation _ContentPageRelation = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentId"))) {
            m_logger.debug("updating param contentId from " +_ContentPageRelation.getContentId() + "->" + request.getParameter("contentId"));
            _ContentPageRelation.setContentId(WebParamUtil.getLongValue(request.getParameter("contentId")));
        }
        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_ContentPageRelation.getPageId() + "->" + request.getParameter("pageId"));
            _ContentPageRelation.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ContentPageRelation.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ContentPageRelation.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _ContentPageRelation);
        m_ds.update(_ContentPageRelation);
        m_actionExtent.afterUpdate(request, response, _ContentPageRelation);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected ContentPageRelationDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentPageRelationAction.class);
}
