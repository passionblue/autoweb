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

import com.autosite.db.EcOrderPackage;
import com.autosite.ds.EcOrderPackageDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcOrderPackageForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcOrderPackageAction extends AutositeCoreAction {

    public EcOrderPackageAction(){
        m_ds = EcOrderPackageDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcOrderPackageForm _EcOrderPackageForm = (EcOrderPackageForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcOrderPackage _EcOrderPackage = m_ds.getById(cid);

            if (_EcOrderPackage == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcOrderPackage.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcOrderPackage.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcOrderPackageForm, _EcOrderPackage);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_order_package_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcOrderPackage _EcOrderPackage = m_ds.getById(cid);

            if (_EcOrderPackage == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcOrderPackage.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcOrderPackage.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcOrderPackage);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_order_package");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcOrderPackage _EcOrderPackage = m_ds.getById(cid);

            if (_EcOrderPackage == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcOrderPackage.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcOrderPackage.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcOrderPackage);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcOrderPackage);
            try { 
                m_actionExtent.afterDelete(request, response, _EcOrderPackage);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_order_package_home");    
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

            m_logger.info("Creating new EcOrderPackage" );
            EcOrderPackage _EcOrderPackage = new EcOrderPackage();   

            // Setting IDs for the object
            _EcOrderPackage.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null)
                _EcOrderPackage.setUserId(user.getId());

            _EcOrderPackage.setOrderId(WebParamUtil.getLongValue(_EcOrderPackageForm.getOrderId()));
            m_logger.debug("setting OrderId=" +_EcOrderPackageForm.getOrderId());
            _EcOrderPackage.setNumOrder(WebParamUtil.getIntValue(_EcOrderPackageForm.getNumOrder()));
            m_logger.debug("setting NumOrder=" +_EcOrderPackageForm.getNumOrder());
            _EcOrderPackage.setShipped(WebParamUtil.getIntValue(_EcOrderPackageForm.getShipped()));
            m_logger.debug("setting Shipped=" +_EcOrderPackageForm.getShipped());
            _EcOrderPackage.setTimeShipped(WebParamUtil.getDateValue(_EcOrderPackageForm.getTimeShipped()));
            m_logger.debug("setting TimeShipped=" +_EcOrderPackageForm.getTimeShipped());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcOrderPackage);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcOrderPackage);
            try{
                m_actionExtent.afterAdd(request, response, _EcOrderPackage);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_order_package_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcOrderPackageForm _EcOrderPackageForm, EcOrderPackage _EcOrderPackage) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcOrderPackage _EcOrderPackage = m_ds.getById(cid);

        _EcOrderPackage.setOrderId(WebParamUtil.getLongValue(_EcOrderPackageForm.getOrderId()));
        _EcOrderPackage.setNumOrder(WebParamUtil.getIntValue(_EcOrderPackageForm.getNumOrder()));
        _EcOrderPackage.setShipped(WebParamUtil.getIntValue(_EcOrderPackageForm.getShipped()));
        _EcOrderPackage.setTimeShipped(WebParamUtil.getDateValue(_EcOrderPackageForm.getTimeShipped()));

        m_actionExtent.beforeUpdate(request, response, _EcOrderPackage);
        m_ds.update(_EcOrderPackage);
        m_actionExtent.afterUpdate(request, response, _EcOrderPackage);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcOrderPackage _EcOrderPackage) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcOrderPackage _EcOrderPackage = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcOrderPackage.getUserId() + "->" + request.getParameter("userId"));
                _EcOrderPackage.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("orderId"))) {
            m_logger.debug("updating param orderId from " +_EcOrderPackage.getOrderId() + "->" + request.getParameter("orderId"));
                _EcOrderPackage.setOrderId(WebParamUtil.getLongValue(request.getParameter("orderId")));
            }
        if (!isMissing(request.getParameter("numOrder"))) {
            m_logger.debug("updating param numOrder from " +_EcOrderPackage.getNumOrder() + "->" + request.getParameter("numOrder"));
                _EcOrderPackage.setNumOrder(WebParamUtil.getIntValue(request.getParameter("numOrder")));
            }
        if (!isMissing(request.getParameter("shipped"))) {
            m_logger.debug("updating param shipped from " +_EcOrderPackage.getShipped() + "->" + request.getParameter("shipped"));
                _EcOrderPackage.setShipped(WebParamUtil.getIntValue(request.getParameter("shipped")));
            }
        if (!isMissing(request.getParameter("timeShipped"))) {
            m_logger.debug("updating param timeShipped from " +_EcOrderPackage.getTimeShipped() + "->" + request.getParameter("timeShipped"));
                _EcOrderPackage.setTimeShipped(WebParamUtil.getDateValue(request.getParameter("timeShipped")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcOrderPackage);
        m_ds.update(_EcOrderPackage);
        m_actionExtent.afterUpdate(request, response, _EcOrderPackage);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcOrderPackageDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcOrderPackageAction.class);
}
