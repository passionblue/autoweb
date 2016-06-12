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

import com.autosite.db.EcWishList;
import com.autosite.ds.EcWishListDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcWishListForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcWishListAction extends AutositeCoreAction {

    public EcWishListAction(){
        m_ds = EcWishListDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcWishListForm _EcWishListForm = (EcWishListForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcWishList _EcWishList = m_ds.getById(cid);

            if (_EcWishList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcWishList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcWishList.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcWishListForm, _EcWishList);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_wish_list_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcWishList _EcWishList = m_ds.getById(cid);

            if (_EcWishList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcWishList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcWishList.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcWishList);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_wish_list");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcWishList _EcWishList = m_ds.getById(cid);

            if (_EcWishList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcWishList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcWishList.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcWishList);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcWishList);
            try { 
                m_actionExtent.afterDelete(request, response, _EcWishList);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_wish_list_home");    
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

            m_logger.info("Creating new EcWishList" );
            EcWishList _EcWishList = new EcWishList();   

            // Setting IDs for the object
            _EcWishList.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null)
                _EcWishList.setUserId(user.getId());

            _EcWishList.setProductId(WebParamUtil.getLongValue(_EcWishListForm.getProductId()));
            m_logger.debug("setting ProductId=" +_EcWishListForm.getProductId());
            _EcWishList.setSizeVariation(WebParamUtil.getStringValue(_EcWishListForm.getSizeVariation()));
            m_logger.debug("setting SizeVariation=" +_EcWishListForm.getSizeVariation());
            _EcWishList.setColorVariation(WebParamUtil.getStringValue(_EcWishListForm.getColorVariation()));
            m_logger.debug("setting ColorVariation=" +_EcWishListForm.getColorVariation());
            _EcWishList.setSavedPrice(WebParamUtil.getDoubleValue(_EcWishListForm.getSavedPrice()));
            m_logger.debug("setting SavedPrice=" +_EcWishListForm.getSavedPrice());
            _EcWishList.setTimeCreated(WebParamUtil.getDateValue(_EcWishListForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_EcWishListForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcWishList);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcWishList);
            try{
                m_actionExtent.afterAdd(request, response, _EcWishList);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_wish_list_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcWishListForm _EcWishListForm, EcWishList _EcWishList) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcWishList _EcWishList = m_ds.getById(cid);

        _EcWishList.setProductId(WebParamUtil.getLongValue(_EcWishListForm.getProductId()));
        _EcWishList.setSizeVariation(WebParamUtil.getStringValue(_EcWishListForm.getSizeVariation()));
        _EcWishList.setColorVariation(WebParamUtil.getStringValue(_EcWishListForm.getColorVariation()));
        _EcWishList.setSavedPrice(WebParamUtil.getDoubleValue(_EcWishListForm.getSavedPrice()));
        _EcWishList.setTimeCreated(WebParamUtil.getDateValue(_EcWishListForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _EcWishList);
        m_ds.update(_EcWishList);
        m_actionExtent.afterUpdate(request, response, _EcWishList);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcWishList _EcWishList) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcWishList _EcWishList = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcWishList.getUserId() + "->" + request.getParameter("userId"));
                _EcWishList.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("productId"))) {
            m_logger.debug("updating param productId from " +_EcWishList.getProductId() + "->" + request.getParameter("productId"));
                _EcWishList.setProductId(WebParamUtil.getLongValue(request.getParameter("productId")));
            }
        if (!isMissing(request.getParameter("sizeVariation"))) {
            m_logger.debug("updating param sizeVariation from " +_EcWishList.getSizeVariation() + "->" + request.getParameter("sizeVariation"));
                _EcWishList.setSizeVariation(WebParamUtil.getStringValue(request.getParameter("sizeVariation")));
            }
        if (!isMissing(request.getParameter("colorVariation"))) {
            m_logger.debug("updating param colorVariation from " +_EcWishList.getColorVariation() + "->" + request.getParameter("colorVariation"));
                _EcWishList.setColorVariation(WebParamUtil.getStringValue(request.getParameter("colorVariation")));
            }
        if (!isMissing(request.getParameter("savedPrice"))) {
            m_logger.debug("updating param savedPrice from " +_EcWishList.getSavedPrice() + "->" + request.getParameter("savedPrice"));
                _EcWishList.setSavedPrice(WebParamUtil.getDoubleValue(request.getParameter("savedPrice")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcWishList.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _EcWishList.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcWishList);
        m_ds.update(_EcWishList);
        m_actionExtent.afterUpdate(request, response, _EcWishList);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcWishListDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcWishListAction.class);
}
