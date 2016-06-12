package com.autosite.struts.action;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PageStyle;
import com.autosite.ds.PageStyleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PageStyleForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PageStyleAction extends AutositeCoreAction {

    public PageStyleAction(){
        m_ds = PageStyleDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PageStyleForm _PageStyleForm = (PageStyleForm) form;
        HttpSession session = request.getSession();

        setPage(session, "page_style_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        PageStyle _PageStyle = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PageStyle = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PageStyle _PageStyle = m_ds.getById(cid);

            if (_PageStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStyle.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PageStyleForm, _PageStyle);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PageStyle _PageStyle = m_ds.getById(cid);

            if (_PageStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStyle.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PageStyle);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PageStyle _PageStyle = m_ds.getById(cid);

            if (_PageStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PageStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PageStyle.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PageStyle);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PageStyle);
            try { 
                m_actionExtent.afterDelete(request, response, _PageStyle);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
                return mapping.findForward("default");
            }

            m_logger.info("Creating new PageStyle" );
            PageStyle _PageStyleNew = new PageStyle();   

            // Setting IDs for the object
            _PageStyleNew.setSiteId(site.getId());

            _PageStyleNew.setPageId(WebParamUtil.getLongValue(_PageStyleForm.getPageId()));
            m_logger.debug("setting PageId=" +_PageStyleForm.getPageId());
            _PageStyleNew.setStyleId(WebParamUtil.getLongValue(_PageStyleForm.getStyleId()));
            m_logger.debug("setting StyleId=" +_PageStyleForm.getStyleId());

            try{
                m_actionExtent.beforeAdd(request, response, _PageStyleNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PageStyleNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PageStyleNew);
            try{
                m_actionExtent.afterAdd(request, response, _PageStyleNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "page_style_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PageStyleForm _PageStyleForm, PageStyle _PageStyle) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageStyle _PageStyle = m_ds.getById(cid);

        m_logger.debug("Before update " + PageStyleDS.objectToString(_PageStyle));

        _PageStyle.setPageId(WebParamUtil.getLongValue(_PageStyleForm.getPageId()));
        _PageStyle.setStyleId(WebParamUtil.getLongValue(_PageStyleForm.getStyleId()));

        m_actionExtent.beforeUpdate(request, response, _PageStyle);
        m_ds.update(_PageStyle);
        m_actionExtent.afterUpdate(request, response, _PageStyle);
        m_logger.debug("After update " + PageStyleDS.objectToString(_PageStyle));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PageStyle _PageStyle) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageStyle _PageStyle = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_PageStyle.getPageId() + "->" + request.getParameter("pageId"));
            _PageStyle.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("styleId"))) {
            m_logger.debug("updating param styleId from " +_PageStyle.getStyleId() + "->" + request.getParameter("styleId"));
            _PageStyle.setStyleId(WebParamUtil.getLongValue(request.getParameter("styleId")));
        }

        m_actionExtent.beforeUpdate(request, response, _PageStyle);
        m_ds.update(_PageStyle);
        m_actionExtent.afterUpdate(request, response, _PageStyle);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, PageStyle _PageStyle) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PageStyle _PageStyle = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
			return String.valueOf(_PageStyle.getPageId());
        }
        if (!isMissing(request.getParameter("styleId"))) {
			return String.valueOf(_PageStyle.getStyleId());
        }
		return null;
    }


    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        
        //         // Request Processing 
        // 
        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                ex(mapping, form, request, response);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            PageStyle _PageStyle = PageStyleDS.getInstance().getById(id);
            if (_PageStyle == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _PageStyle);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected PageStyleDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PageStyleAction.class);
}
