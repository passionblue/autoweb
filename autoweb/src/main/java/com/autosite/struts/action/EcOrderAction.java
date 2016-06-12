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

import com.autosite.db.EcOrder;
import com.autosite.ds.EcOrderDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcOrderForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcOrderAction extends AutositeCoreAction {

    public EcOrderAction(){
        m_ds = EcOrderDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcOrderForm _EcOrderForm = (EcOrderForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcOrder _EcOrder = m_ds.getById(cid);

            if (_EcOrder == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcOrder.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcOrder.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcOrderForm, _EcOrder);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_order_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcOrder _EcOrder = m_ds.getById(cid);

            if (_EcOrder == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcOrder.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcOrder.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcOrder);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_order");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcOrder _EcOrder = m_ds.getById(cid);

            if (_EcOrder == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcOrder.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcOrder.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcOrder);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcOrder);
            try { 
                m_actionExtent.afterDelete(request, response, _EcOrder);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_order_home");    
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

            m_logger.info("Creating new EcOrder" );
            EcOrder _EcOrder = new EcOrder();   

            // Setting IDs for the object
            _EcOrder.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null)
                _EcOrder.setUserId(user.getId());

            _EcOrder.setAnonymousUserId(WebParamUtil.getLongValue(_EcOrderForm.getAnonymousUserId()));
            m_logger.debug("setting AnonymousUserId=" +_EcOrderForm.getAnonymousUserId());
            _EcOrder.setOrderNum(WebParamUtil.getStringValue(_EcOrderForm.getOrderNum()));
            m_logger.debug("setting OrderNum=" +_EcOrderForm.getOrderNum());
            _EcOrder.setOrderStatus(WebParamUtil.getIntValue(_EcOrderForm.getOrderStatus()));
            m_logger.debug("setting OrderStatus=" +_EcOrderForm.getOrderStatus());
            _EcOrder.setOrderTotal(WebParamUtil.getDoubleValue(_EcOrderForm.getOrderTotal()));
            m_logger.debug("setting OrderTotal=" +_EcOrderForm.getOrderTotal());
            _EcOrder.setTimeReceived(WebParamUtil.getDateValue(_EcOrderForm.getTimeReceived()));
            m_logger.debug("setting TimeReceived=" +_EcOrderForm.getTimeReceived());
            _EcOrder.setTimeApproved(WebParamUtil.getDateValue(_EcOrderForm.getTimeApproved()));
            m_logger.debug("setting TimeApproved=" +_EcOrderForm.getTimeApproved());
            _EcOrder.setTimeHalt(WebParamUtil.getDateValue(_EcOrderForm.getTimeHalt()));
            m_logger.debug("setting TimeHalt=" +_EcOrderForm.getTimeHalt());
            _EcOrder.setTimeCancelled(WebParamUtil.getDateValue(_EcOrderForm.getTimeCancelled()));
            m_logger.debug("setting TimeCancelled=" +_EcOrderForm.getTimeCancelled());
            _EcOrder.setTimeFulfilled(WebParamUtil.getDateValue(_EcOrderForm.getTimeFulfilled()));
            m_logger.debug("setting TimeFulfilled=" +_EcOrderForm.getTimeFulfilled());
            _EcOrder.setTimeShipped(WebParamUtil.getDateValue(_EcOrderForm.getTimeShipped()));
            m_logger.debug("setting TimeShipped=" +_EcOrderForm.getTimeShipped());
            _EcOrder.setTimeReturned(WebParamUtil.getDateValue(_EcOrderForm.getTimeReturned()));
            m_logger.debug("setting TimeReturned=" +_EcOrderForm.getTimeReturned());
            _EcOrder.setReProcess(WebParamUtil.getIntValue(_EcOrderForm.getReProcess()));
            m_logger.debug("setting ReProcess=" +_EcOrderForm.getReProcess());
            _EcOrder.setOrgOrderId(WebParamUtil.getLongValue(_EcOrderForm.getOrgOrderId()));
            m_logger.debug("setting OrgOrderId=" +_EcOrderForm.getOrgOrderId());
            _EcOrder.setApprovedBy(WebParamUtil.getStringValue(_EcOrderForm.getApprovedBy()));
            m_logger.debug("setting ApprovedBy=" +_EcOrderForm.getApprovedBy());
            _EcOrder.setFulfilledBy(WebParamUtil.getStringValue(_EcOrderForm.getFulfilledBy()));
            m_logger.debug("setting FulfilledBy=" +_EcOrderForm.getFulfilledBy());
            _EcOrder.setShippedBy(WebParamUtil.getStringValue(_EcOrderForm.getShippedBy()));
            m_logger.debug("setting ShippedBy=" +_EcOrderForm.getShippedBy());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcOrder);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcOrder);
            try{
                m_actionExtent.afterAdd(request, response, _EcOrder);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_order_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcOrderForm _EcOrderForm, EcOrder _EcOrder) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcOrder _EcOrder = m_ds.getById(cid);

        _EcOrder.setAnonymousUserId(WebParamUtil.getLongValue(_EcOrderForm.getAnonymousUserId()));
        _EcOrder.setOrderNum(WebParamUtil.getStringValue(_EcOrderForm.getOrderNum()));
        _EcOrder.setOrderStatus(WebParamUtil.getIntValue(_EcOrderForm.getOrderStatus()));
        _EcOrder.setOrderTotal(WebParamUtil.getDoubleValue(_EcOrderForm.getOrderTotal()));
        _EcOrder.setTimeReceived(WebParamUtil.getDateValue(_EcOrderForm.getTimeReceived()));
        _EcOrder.setTimeApproved(WebParamUtil.getDateValue(_EcOrderForm.getTimeApproved()));
        _EcOrder.setTimeHalt(WebParamUtil.getDateValue(_EcOrderForm.getTimeHalt()));
        _EcOrder.setTimeCancelled(WebParamUtil.getDateValue(_EcOrderForm.getTimeCancelled()));
        _EcOrder.setTimeFulfilled(WebParamUtil.getDateValue(_EcOrderForm.getTimeFulfilled()));
        _EcOrder.setTimeShipped(WebParamUtil.getDateValue(_EcOrderForm.getTimeShipped()));
        _EcOrder.setTimeReturned(WebParamUtil.getDateValue(_EcOrderForm.getTimeReturned()));
        _EcOrder.setReProcess(WebParamUtil.getIntValue(_EcOrderForm.getReProcess()));
        _EcOrder.setOrgOrderId(WebParamUtil.getLongValue(_EcOrderForm.getOrgOrderId()));
        _EcOrder.setApprovedBy(WebParamUtil.getStringValue(_EcOrderForm.getApprovedBy()));
        _EcOrder.setFulfilledBy(WebParamUtil.getStringValue(_EcOrderForm.getFulfilledBy()));
        _EcOrder.setShippedBy(WebParamUtil.getStringValue(_EcOrderForm.getShippedBy()));

        m_actionExtent.beforeUpdate(request, response, _EcOrder);
        m_ds.update(_EcOrder);
        m_actionExtent.afterUpdate(request, response, _EcOrder);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcOrder _EcOrder) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcOrder _EcOrder = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcOrder.getUserId() + "->" + request.getParameter("userId"));
                _EcOrder.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("anonymousUserId"))) {
            m_logger.debug("updating param anonymousUserId from " +_EcOrder.getAnonymousUserId() + "->" + request.getParameter("anonymousUserId"));
                _EcOrder.setAnonymousUserId(WebParamUtil.getLongValue(request.getParameter("anonymousUserId")));
            }
        if (!isMissing(request.getParameter("orderNum"))) {
            m_logger.debug("updating param orderNum from " +_EcOrder.getOrderNum() + "->" + request.getParameter("orderNum"));
                _EcOrder.setOrderNum(WebParamUtil.getStringValue(request.getParameter("orderNum")));
            }
        if (!isMissing(request.getParameter("orderStatus"))) {
            m_logger.debug("updating param orderStatus from " +_EcOrder.getOrderStatus() + "->" + request.getParameter("orderStatus"));
                _EcOrder.setOrderStatus(WebParamUtil.getIntValue(request.getParameter("orderStatus")));
            }
        if (!isMissing(request.getParameter("orderTotal"))) {
            m_logger.debug("updating param orderTotal from " +_EcOrder.getOrderTotal() + "->" + request.getParameter("orderTotal"));
                _EcOrder.setOrderTotal(WebParamUtil.getDoubleValue(request.getParameter("orderTotal")));
            }
        if (!isMissing(request.getParameter("timeReceived"))) {
            m_logger.debug("updating param timeReceived from " +_EcOrder.getTimeReceived() + "->" + request.getParameter("timeReceived"));
                _EcOrder.setTimeReceived(WebParamUtil.getDateValue(request.getParameter("timeReceived")));
            }
        if (!isMissing(request.getParameter("timeApproved"))) {
            m_logger.debug("updating param timeApproved from " +_EcOrder.getTimeApproved() + "->" + request.getParameter("timeApproved"));
                _EcOrder.setTimeApproved(WebParamUtil.getDateValue(request.getParameter("timeApproved")));
            }
        if (!isMissing(request.getParameter("timeHalt"))) {
            m_logger.debug("updating param timeHalt from " +_EcOrder.getTimeHalt() + "->" + request.getParameter("timeHalt"));
                _EcOrder.setTimeHalt(WebParamUtil.getDateValue(request.getParameter("timeHalt")));
            }
        if (!isMissing(request.getParameter("timeCancelled"))) {
            m_logger.debug("updating param timeCancelled from " +_EcOrder.getTimeCancelled() + "->" + request.getParameter("timeCancelled"));
                _EcOrder.setTimeCancelled(WebParamUtil.getDateValue(request.getParameter("timeCancelled")));
            }
        if (!isMissing(request.getParameter("timeFulfilled"))) {
            m_logger.debug("updating param timeFulfilled from " +_EcOrder.getTimeFulfilled() + "->" + request.getParameter("timeFulfilled"));
                _EcOrder.setTimeFulfilled(WebParamUtil.getDateValue(request.getParameter("timeFulfilled")));
            }
        if (!isMissing(request.getParameter("timeShipped"))) {
            m_logger.debug("updating param timeShipped from " +_EcOrder.getTimeShipped() + "->" + request.getParameter("timeShipped"));
                _EcOrder.setTimeShipped(WebParamUtil.getDateValue(request.getParameter("timeShipped")));
            }
        if (!isMissing(request.getParameter("timeReturned"))) {
            m_logger.debug("updating param timeReturned from " +_EcOrder.getTimeReturned() + "->" + request.getParameter("timeReturned"));
                _EcOrder.setTimeReturned(WebParamUtil.getDateValue(request.getParameter("timeReturned")));
            }
        if (!isMissing(request.getParameter("reProcess"))) {
            m_logger.debug("updating param reProcess from " +_EcOrder.getReProcess() + "->" + request.getParameter("reProcess"));
                _EcOrder.setReProcess(WebParamUtil.getIntValue(request.getParameter("reProcess")));
            }
        if (!isMissing(request.getParameter("orgOrderId"))) {
            m_logger.debug("updating param orgOrderId from " +_EcOrder.getOrgOrderId() + "->" + request.getParameter("orgOrderId"));
                _EcOrder.setOrgOrderId(WebParamUtil.getLongValue(request.getParameter("orgOrderId")));
            }
        if (!isMissing(request.getParameter("approvedBy"))) {
            m_logger.debug("updating param approvedBy from " +_EcOrder.getApprovedBy() + "->" + request.getParameter("approvedBy"));
                _EcOrder.setApprovedBy(WebParamUtil.getStringValue(request.getParameter("approvedBy")));
            }
        if (!isMissing(request.getParameter("fulfilledBy"))) {
            m_logger.debug("updating param fulfilledBy from " +_EcOrder.getFulfilledBy() + "->" + request.getParameter("fulfilledBy"));
                _EcOrder.setFulfilledBy(WebParamUtil.getStringValue(request.getParameter("fulfilledBy")));
            }
        if (!isMissing(request.getParameter("shippedBy"))) {
            m_logger.debug("updating param shippedBy from " +_EcOrder.getShippedBy() + "->" + request.getParameter("shippedBy"));
                _EcOrder.setShippedBy(WebParamUtil.getStringValue(request.getParameter("shippedBy")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcOrder);
        m_ds.update(_EcOrder);
        m_actionExtent.afterUpdate(request, response, _EcOrder);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcOrderDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcOrderAction.class);
}
