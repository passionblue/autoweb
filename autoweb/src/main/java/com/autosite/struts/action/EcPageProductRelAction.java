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

import com.autosite.db.EcPageProductRel;
import com.autosite.ds.EcPageProductRelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcPageProductRelForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcPageProductRelAction extends AutositeCoreAction {

    public EcPageProductRelAction(){
        m_ds = EcPageProductRelDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcPageProductRelForm _EcPageProductRelForm = (EcPageProductRelForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcPageProductRel _EcPageProductRel = m_ds.getById(cid);

            if (_EcPageProductRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcPageProductRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcPageProductRel.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcPageProductRelForm, _EcPageProductRel);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_page_product_rel_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcPageProductRel _EcPageProductRel = m_ds.getById(cid);

            if (_EcPageProductRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcPageProductRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcPageProductRel.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcPageProductRel);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_page_product_rel");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcPageProductRel _EcPageProductRel = m_ds.getById(cid);

            if (_EcPageProductRel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcPageProductRel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcPageProductRel.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcPageProductRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcPageProductRel);
            try { 
                m_actionExtent.afterDelete(request, response, _EcPageProductRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_page_product_rel_home");    
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

            m_logger.info("Creating new EcPageProductRel" );
            EcPageProductRel _EcPageProductRel = new EcPageProductRel();   

            // Setting IDs for the object
            _EcPageProductRel.setSiteId(site.getId());

            _EcPageProductRel.setProductId(WebParamUtil.getLongValue(_EcPageProductRelForm.getProductId()));
            m_logger.debug("setting ProductId=" +_EcPageProductRelForm.getProductId());
            _EcPageProductRel.setCategoryId(WebParamUtil.getLongValue(_EcPageProductRelForm.getCategoryId()));
            m_logger.debug("setting CategoryId=" +_EcPageProductRelForm.getCategoryId());
            _EcPageProductRel.setHide(WebParamUtil.getIntValue(_EcPageProductRelForm.getHide()));
            m_logger.debug("setting Hide=" +_EcPageProductRelForm.getHide());
            _EcPageProductRel.setMainCategory(WebParamUtil.getIntValue(_EcPageProductRelForm.getMainCategory()));
            m_logger.debug("setting MainCategory=" +_EcPageProductRelForm.getMainCategory());
            _EcPageProductRel.setTimeCreated(WebParamUtil.getDateValue(_EcPageProductRelForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_EcPageProductRelForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcPageProductRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcPageProductRel);
            try{
                m_actionExtent.afterAdd(request, response, _EcPageProductRel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_page_product_rel_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcPageProductRelForm _EcPageProductRelForm, EcPageProductRel _EcPageProductRel) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcPageProductRel _EcPageProductRel = m_ds.getById(cid);

        _EcPageProductRel.setProductId(WebParamUtil.getLongValue(_EcPageProductRelForm.getProductId()));
        _EcPageProductRel.setCategoryId(WebParamUtil.getLongValue(_EcPageProductRelForm.getCategoryId()));
        _EcPageProductRel.setHide(WebParamUtil.getIntValue(_EcPageProductRelForm.getHide()));
        _EcPageProductRel.setMainCategory(WebParamUtil.getIntValue(_EcPageProductRelForm.getMainCategory()));
        _EcPageProductRel.setTimeCreated(WebParamUtil.getDateValue(_EcPageProductRelForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _EcPageProductRel);
        m_ds.update(_EcPageProductRel);
        m_actionExtent.afterUpdate(request, response, _EcPageProductRel);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcPageProductRel _EcPageProductRel) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcPageProductRel _EcPageProductRel = m_ds.getById(cid);

        if (!isMissing(request.getParameter("productId"))) {
            m_logger.debug("updating param productId from " +_EcPageProductRel.getProductId() + "->" + request.getParameter("productId"));
                _EcPageProductRel.setProductId(WebParamUtil.getLongValue(request.getParameter("productId")));
            }
        if (!isMissing(request.getParameter("categoryId"))) {
            m_logger.debug("updating param categoryId from " +_EcPageProductRel.getCategoryId() + "->" + request.getParameter("categoryId"));
                _EcPageProductRel.setCategoryId(WebParamUtil.getLongValue(request.getParameter("categoryId")));
            }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_EcPageProductRel.getHide() + "->" + request.getParameter("hide"));
                _EcPageProductRel.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
            }
        if (!isMissing(request.getParameter("mainCategory"))) {
            m_logger.debug("updating param mainCategory from " +_EcPageProductRel.getMainCategory() + "->" + request.getParameter("mainCategory"));
                _EcPageProductRel.setMainCategory(WebParamUtil.getIntValue(request.getParameter("mainCategory")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcPageProductRel.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _EcPageProductRel.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcPageProductRel);
        m_ds.update(_EcPageProductRel);
        m_actionExtent.afterUpdate(request, response, _EcPageProductRel);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcPageProductRelDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcPageProductRelAction.class);
}
