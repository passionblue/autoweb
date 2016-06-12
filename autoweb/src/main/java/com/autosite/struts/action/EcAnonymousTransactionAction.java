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

import com.autosite.db.EcAnonymousTransaction;
import com.autosite.ds.EcAnonymousTransactionDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcAnonymousTransactionForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcAnonymousTransactionAction extends AutositeCoreAction {

    public EcAnonymousTransactionAction(){
        m_ds = EcAnonymousTransactionDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcAnonymousTransactionForm _EcAnonymousTransactionForm = (EcAnonymousTransactionForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousTransaction _EcAnonymousTransaction = m_ds.getById(cid);

            if (_EcAnonymousTransaction == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousTransaction.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousTransaction.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcAnonymousTransactionForm, _EcAnonymousTransaction);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_anonymous_transaction_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousTransaction _EcAnonymousTransaction = m_ds.getById(cid);

            if (_EcAnonymousTransaction == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousTransaction.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousTransaction.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcAnonymousTransaction);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_anonymous_transaction");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousTransaction _EcAnonymousTransaction = m_ds.getById(cid);

            if (_EcAnonymousTransaction == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousTransaction.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousTransaction.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcAnonymousTransaction);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcAnonymousTransaction);
            try { 
                m_actionExtent.afterDelete(request, response, _EcAnonymousTransaction);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_anonymous_transaction_home");    
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

            m_logger.info("Creating new EcAnonymousTransaction" );
            EcAnonymousTransaction _EcAnonymousTransaction = new EcAnonymousTransaction();   

            // Setting IDs for the object
            _EcAnonymousTransaction.setSiteId(site.getId());

            _EcAnonymousTransaction.setAnonymousUserId(WebParamUtil.getLongValue(_EcAnonymousTransactionForm.getAnonymousUserId()));
            m_logger.debug("setting AnonymousUserId=" +_EcAnonymousTransactionForm.getAnonymousUserId());
            _EcAnonymousTransaction.setOrderId(WebParamUtil.getLongValue(_EcAnonymousTransactionForm.getOrderId()));
            m_logger.debug("setting OrderId=" +_EcAnonymousTransactionForm.getOrderId());
            _EcAnonymousTransaction.setPaymentInfoId(WebParamUtil.getLongValue(_EcAnonymousTransactionForm.getPaymentInfoId()));
            m_logger.debug("setting PaymentInfoId=" +_EcAnonymousTransactionForm.getPaymentInfoId());
            _EcAnonymousTransaction.setAmount(WebParamUtil.getDoubleValue(_EcAnonymousTransactionForm.getAmount()));
            m_logger.debug("setting Amount=" +_EcAnonymousTransactionForm.getAmount());
            _EcAnonymousTransaction.setTransactionType(WebParamUtil.getIntValue(_EcAnonymousTransactionForm.getTransactionType()));
            m_logger.debug("setting TransactionType=" +_EcAnonymousTransactionForm.getTransactionType());
            _EcAnonymousTransaction.setResult(WebParamUtil.getIntValue(_EcAnonymousTransactionForm.getResult()));
            m_logger.debug("setting Result=" +_EcAnonymousTransactionForm.getResult());
            _EcAnonymousTransaction.setTimeProcessed(WebParamUtil.getDateValue(_EcAnonymousTransactionForm.getTimeProcessed()));
            m_logger.debug("setting TimeProcessed=" +_EcAnonymousTransactionForm.getTimeProcessed());
            _EcAnonymousTransaction.setReturnCode(WebParamUtil.getStringValue(_EcAnonymousTransactionForm.getReturnCode()));
            m_logger.debug("setting ReturnCode=" +_EcAnonymousTransactionForm.getReturnCode());
            _EcAnonymousTransaction.setReturnMsg(WebParamUtil.getStringValue(_EcAnonymousTransactionForm.getReturnMsg()));
            m_logger.debug("setting ReturnMsg=" +_EcAnonymousTransactionForm.getReturnMsg());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcAnonymousTransaction);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcAnonymousTransaction);
            try{
                m_actionExtent.afterAdd(request, response, _EcAnonymousTransaction);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_anonymous_transaction_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcAnonymousTransactionForm _EcAnonymousTransactionForm, EcAnonymousTransaction _EcAnonymousTransaction) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousTransaction _EcAnonymousTransaction = m_ds.getById(cid);

        _EcAnonymousTransaction.setAnonymousUserId(WebParamUtil.getLongValue(_EcAnonymousTransactionForm.getAnonymousUserId()));
        _EcAnonymousTransaction.setOrderId(WebParamUtil.getLongValue(_EcAnonymousTransactionForm.getOrderId()));
        _EcAnonymousTransaction.setPaymentInfoId(WebParamUtil.getLongValue(_EcAnonymousTransactionForm.getPaymentInfoId()));
        _EcAnonymousTransaction.setAmount(WebParamUtil.getDoubleValue(_EcAnonymousTransactionForm.getAmount()));
        _EcAnonymousTransaction.setTransactionType(WebParamUtil.getIntValue(_EcAnonymousTransactionForm.getTransactionType()));
        _EcAnonymousTransaction.setResult(WebParamUtil.getIntValue(_EcAnonymousTransactionForm.getResult()));
        _EcAnonymousTransaction.setTimeProcessed(WebParamUtil.getDateValue(_EcAnonymousTransactionForm.getTimeProcessed()));
        _EcAnonymousTransaction.setReturnCode(WebParamUtil.getStringValue(_EcAnonymousTransactionForm.getReturnCode()));
        _EcAnonymousTransaction.setReturnMsg(WebParamUtil.getStringValue(_EcAnonymousTransactionForm.getReturnMsg()));

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousTransaction);
        m_ds.update(_EcAnonymousTransaction);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousTransaction);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcAnonymousTransaction _EcAnonymousTransaction) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousTransaction _EcAnonymousTransaction = m_ds.getById(cid);

        if (!isMissing(request.getParameter("anonymousUserId"))) {
            m_logger.debug("updating param anonymousUserId from " +_EcAnonymousTransaction.getAnonymousUserId() + "->" + request.getParameter("anonymousUserId"));
                _EcAnonymousTransaction.setAnonymousUserId(WebParamUtil.getLongValue(request.getParameter("anonymousUserId")));
            }
        if (!isMissing(request.getParameter("orderId"))) {
            m_logger.debug("updating param orderId from " +_EcAnonymousTransaction.getOrderId() + "->" + request.getParameter("orderId"));
                _EcAnonymousTransaction.setOrderId(WebParamUtil.getLongValue(request.getParameter("orderId")));
            }
        if (!isMissing(request.getParameter("paymentInfoId"))) {
            m_logger.debug("updating param paymentInfoId from " +_EcAnonymousTransaction.getPaymentInfoId() + "->" + request.getParameter("paymentInfoId"));
                _EcAnonymousTransaction.setPaymentInfoId(WebParamUtil.getLongValue(request.getParameter("paymentInfoId")));
            }
        if (!isMissing(request.getParameter("amount"))) {
            m_logger.debug("updating param amount from " +_EcAnonymousTransaction.getAmount() + "->" + request.getParameter("amount"));
                _EcAnonymousTransaction.setAmount(WebParamUtil.getDoubleValue(request.getParameter("amount")));
            }
        if (!isMissing(request.getParameter("transactionType"))) {
            m_logger.debug("updating param transactionType from " +_EcAnonymousTransaction.getTransactionType() + "->" + request.getParameter("transactionType"));
                _EcAnonymousTransaction.setTransactionType(WebParamUtil.getIntValue(request.getParameter("transactionType")));
            }
        if (!isMissing(request.getParameter("result"))) {
            m_logger.debug("updating param result from " +_EcAnonymousTransaction.getResult() + "->" + request.getParameter("result"));
                _EcAnonymousTransaction.setResult(WebParamUtil.getIntValue(request.getParameter("result")));
            }
        if (!isMissing(request.getParameter("timeProcessed"))) {
            m_logger.debug("updating param timeProcessed from " +_EcAnonymousTransaction.getTimeProcessed() + "->" + request.getParameter("timeProcessed"));
                _EcAnonymousTransaction.setTimeProcessed(WebParamUtil.getDateValue(request.getParameter("timeProcessed")));
            }
        if (!isMissing(request.getParameter("returnCode"))) {
            m_logger.debug("updating param returnCode from " +_EcAnonymousTransaction.getReturnCode() + "->" + request.getParameter("returnCode"));
                _EcAnonymousTransaction.setReturnCode(WebParamUtil.getStringValue(request.getParameter("returnCode")));
            }
        if (!isMissing(request.getParameter("returnMsg"))) {
            m_logger.debug("updating param returnMsg from " +_EcAnonymousTransaction.getReturnMsg() + "->" + request.getParameter("returnMsg"));
                _EcAnonymousTransaction.setReturnMsg(WebParamUtil.getStringValue(request.getParameter("returnMsg")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousTransaction);
        m_ds.update(_EcAnonymousTransaction);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousTransaction);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcAnonymousTransactionDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcAnonymousTransactionAction.class);
}
