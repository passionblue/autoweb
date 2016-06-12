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

import com.autosite.db.EcCategory;
import com.autosite.ds.EcCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcCategoryForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcCategoryAction extends AutositeCoreAction {

    public EcCategoryAction(){
	    m_ds = EcCategoryDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcCategoryForm _EcCategoryForm = (EcCategoryForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcCategory _EcCategory = m_ds.getById(cid);

            if (_EcCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcCategory.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _EcCategoryForm, _EcCategory);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			//setPage(session, "ec_category_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcCategory _EcCategory = m_ds.getById(cid);

            if (_EcCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcCategory.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _EcCategory);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			//setPage(session, "ec_category");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcCategory _EcCategory = m_ds.getById(cid);

            if (_EcCategory == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcCategory.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcCategory.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _EcCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before deletion.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_EcCategory);
            try { 
	        	m_actionExtent.afterDelete(request, response, _EcCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after deletion process.");
                m_logger.error(e.getMessage(),e);
            }

        	//setPage(session, "ec_category_home");    
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

			m_logger.info("Creating new EcCategory" );
			EcCategory _EcCategory = new EcCategory();	

			// Setting IDs for the object
			_EcCategory.setSiteId(site.getId());

            _EcCategory.setParentId(WebParamUtil.getLongValue(_EcCategoryForm.getParentId()));
			m_logger.debug("setting ParentId=" +_EcCategoryForm.getParentId());
            _EcCategory.setPageId(WebParamUtil.getLongValue(_EcCategoryForm.getPageId()));
			m_logger.debug("setting PageId=" +_EcCategoryForm.getPageId());
            _EcCategory.setCategoryName(WebParamUtil.getStringValue(_EcCategoryForm.getCategoryName()));
			m_logger.debug("setting CategoryName=" +_EcCategoryForm.getCategoryName());
            _EcCategory.setImageUrl(WebParamUtil.getStringValue(_EcCategoryForm.getImageUrl()));
			m_logger.debug("setting ImageUrl=" +_EcCategoryForm.getImageUrl());
            _EcCategory.setCategoryDescription(WebParamUtil.getStringValue(_EcCategoryForm.getCategoryDescription()));
			m_logger.debug("setting CategoryDescription=" +_EcCategoryForm.getCategoryDescription());
            _EcCategory.setAlt1(WebParamUtil.getStringValue(_EcCategoryForm.getAlt1()));
			m_logger.debug("setting Alt1=" +_EcCategoryForm.getAlt1());
            _EcCategory.setAlt2(WebParamUtil.getStringValue(_EcCategoryForm.getAlt2()));
			m_logger.debug("setting Alt2=" +_EcCategoryForm.getAlt2());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _EcCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcCategory);
			try{
		        m_actionExtent.afterAdd(request, response, _EcCategory);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            //setPage(session, "ec_category_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcCategoryForm _EcCategoryForm, EcCategory _EcCategory) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcCategory _EcCategory = m_ds.getById(cid);

        _EcCategory.setParentId(WebParamUtil.getLongValue(_EcCategoryForm.getParentId()));
        _EcCategory.setPageId(WebParamUtil.getLongValue(_EcCategoryForm.getPageId()));
        _EcCategory.setCategoryName(WebParamUtil.getStringValue(_EcCategoryForm.getCategoryName()));
        _EcCategory.setImageUrl(WebParamUtil.getStringValue(_EcCategoryForm.getImageUrl()));
        _EcCategory.setCategoryDescription(WebParamUtil.getStringValue(_EcCategoryForm.getCategoryDescription()));
        _EcCategory.setAlt1(WebParamUtil.getStringValue(_EcCategoryForm.getAlt1()));
        _EcCategory.setAlt2(WebParamUtil.getStringValue(_EcCategoryForm.getAlt2()));

        m_actionExtent.beforeUpdate(request, response, _EcCategory);
        m_ds.update(_EcCategory);
        m_actionExtent.afterUpdate(request, response, _EcCategory);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcCategory _EcCategory) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcCategory _EcCategory = m_ds.getById(cid);

        if (!isMissing(request.getParameter("parentId"))) {
            m_logger.debug("updating param parentId from " +_EcCategory.getParentId() + "->" + request.getParameter("parentId"));
            _EcCategory.setParentId(WebParamUtil.getLongValue(request.getParameter("parentId")));
        }
        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_EcCategory.getPageId() + "->" + request.getParameter("pageId"));
            _EcCategory.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("categoryName"))) {
            m_logger.debug("updating param categoryName from " +_EcCategory.getCategoryName() + "->" + request.getParameter("categoryName"));
            _EcCategory.setCategoryName(WebParamUtil.getStringValue(request.getParameter("categoryName")));
        }
        if (!isMissing(request.getParameter("imageUrl"))) {
            m_logger.debug("updating param imageUrl from " +_EcCategory.getImageUrl() + "->" + request.getParameter("imageUrl"));
            _EcCategory.setImageUrl(WebParamUtil.getStringValue(request.getParameter("imageUrl")));
        }
        if (!isMissing(request.getParameter("categoryDescription"))) {
            m_logger.debug("updating param categoryDescription from " +_EcCategory.getCategoryDescription() + "->" + request.getParameter("categoryDescription"));
            _EcCategory.setCategoryDescription(WebParamUtil.getStringValue(request.getParameter("categoryDescription")));
        }
        if (!isMissing(request.getParameter("alt1"))) {
            m_logger.debug("updating param alt1 from " +_EcCategory.getAlt1() + "->" + request.getParameter("alt1"));
            _EcCategory.setAlt1(WebParamUtil.getStringValue(request.getParameter("alt1")));
        }
        if (!isMissing(request.getParameter("alt2"))) {
            m_logger.debug("updating param alt2 from " +_EcCategory.getAlt2() + "->" + request.getParameter("alt2"));
            _EcCategory.setAlt2(WebParamUtil.getStringValue(request.getParameter("alt2")));
        }

        m_actionExtent.beforeUpdate(request, response, _EcCategory);
        m_ds.update(_EcCategory);
        m_actionExtent.afterUpdate(request, response, _EcCategory);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected EcCategoryDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcCategoryAction.class);
}
