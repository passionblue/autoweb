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

import com.autosite.db.EcAnonymousPaymentInfo;
import com.autosite.ds.EcAnonymousPaymentInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcAnonymousPaymentInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcAnonymousPaymentInfoAction extends AutositeCoreAction {

    public EcAnonymousPaymentInfoAction(){
        m_ds = EcAnonymousPaymentInfoDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcAnonymousPaymentInfoForm _EcAnonymousPaymentInfoForm = (EcAnonymousPaymentInfoForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = m_ds.getById(cid);

            if (_EcAnonymousPaymentInfo == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousPaymentInfo.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousPaymentInfo.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcAnonymousPaymentInfoForm, _EcAnonymousPaymentInfo);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_anonymous_payment_info_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = m_ds.getById(cid);

            if (_EcAnonymousPaymentInfo == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousPaymentInfo.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousPaymentInfo.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcAnonymousPaymentInfo);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_anonymous_payment_info");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = m_ds.getById(cid);

            if (_EcAnonymousPaymentInfo == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousPaymentInfo.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousPaymentInfo.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcAnonymousPaymentInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcAnonymousPaymentInfo);
            try { 
                m_actionExtent.afterDelete(request, response, _EcAnonymousPaymentInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_anonymous_payment_info_home");    
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

            m_logger.info("Creating new EcAnonymousPaymentInfo" );
            EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = new EcAnonymousPaymentInfo();   

            // Setting IDs for the object
            _EcAnonymousPaymentInfo.setSiteId(site.getId());

            _EcAnonymousPaymentInfo.setAnonymousUserId(WebParamUtil.getLongValue(_EcAnonymousPaymentInfoForm.getAnonymousUserId()));
            m_logger.debug("setting AnonymousUserId=" +_EcAnonymousPaymentInfoForm.getAnonymousUserId());
            _EcAnonymousPaymentInfo.setFirstName(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_EcAnonymousPaymentInfoForm.getFirstName());
            _EcAnonymousPaymentInfo.setMiddleInitial(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getMiddleInitial()));
            m_logger.debug("setting MiddleInitial=" +_EcAnonymousPaymentInfoForm.getMiddleInitial());
            _EcAnonymousPaymentInfo.setLastName(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getLastName()));
            m_logger.debug("setting LastName=" +_EcAnonymousPaymentInfoForm.getLastName());
            _EcAnonymousPaymentInfo.setAddress1(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getAddress1()));
            m_logger.debug("setting Address1=" +_EcAnonymousPaymentInfoForm.getAddress1());
            _EcAnonymousPaymentInfo.setAddress2(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getAddress2()));
            m_logger.debug("setting Address2=" +_EcAnonymousPaymentInfoForm.getAddress2());
            _EcAnonymousPaymentInfo.setCity(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getCity()));
            m_logger.debug("setting City=" +_EcAnonymousPaymentInfoForm.getCity());
            _EcAnonymousPaymentInfo.setState(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getState()));
            m_logger.debug("setting State=" +_EcAnonymousPaymentInfoForm.getState());
            _EcAnonymousPaymentInfo.setZip(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getZip()));
            m_logger.debug("setting Zip=" +_EcAnonymousPaymentInfoForm.getZip());
            _EcAnonymousPaymentInfo.setCountry(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getCountry()));
            m_logger.debug("setting Country=" +_EcAnonymousPaymentInfoForm.getCountry());
            _EcAnonymousPaymentInfo.setPaymentType(WebParamUtil.getIntValue(_EcAnonymousPaymentInfoForm.getPaymentType()));
            m_logger.debug("setting PaymentType=" +_EcAnonymousPaymentInfoForm.getPaymentType());
            _EcAnonymousPaymentInfo.setPaymentNum(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getPaymentNum()));
            m_logger.debug("setting PaymentNum=" +_EcAnonymousPaymentInfoForm.getPaymentNum());
            _EcAnonymousPaymentInfo.setPaymentExpireMonth(WebParamUtil.getIntValue(_EcAnonymousPaymentInfoForm.getPaymentExpireMonth()));
            m_logger.debug("setting PaymentExpireMonth=" +_EcAnonymousPaymentInfoForm.getPaymentExpireMonth());
            _EcAnonymousPaymentInfo.setPaymentExpireYear(WebParamUtil.getIntValue(_EcAnonymousPaymentInfoForm.getPaymentExpireYear()));
            m_logger.debug("setting PaymentExpireYear=" +_EcAnonymousPaymentInfoForm.getPaymentExpireYear());
            _EcAnonymousPaymentInfo.setPaymentExtraNum(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getPaymentExtraNum()));
            m_logger.debug("setting PaymentExtraNum=" +_EcAnonymousPaymentInfoForm.getPaymentExtraNum());
            _EcAnonymousPaymentInfo.setTimeCreated(WebParamUtil.getDateValue(_EcAnonymousPaymentInfoForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_EcAnonymousPaymentInfoForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcAnonymousPaymentInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcAnonymousPaymentInfo);
            try{
                m_actionExtent.afterAdd(request, response, _EcAnonymousPaymentInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_anonymous_payment_info_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcAnonymousPaymentInfoForm _EcAnonymousPaymentInfoForm, EcAnonymousPaymentInfo _EcAnonymousPaymentInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = m_ds.getById(cid);

        _EcAnonymousPaymentInfo.setAnonymousUserId(WebParamUtil.getLongValue(_EcAnonymousPaymentInfoForm.getAnonymousUserId()));
        _EcAnonymousPaymentInfo.setFirstName(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getFirstName()));
        _EcAnonymousPaymentInfo.setMiddleInitial(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getMiddleInitial()));
        _EcAnonymousPaymentInfo.setLastName(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getLastName()));
        _EcAnonymousPaymentInfo.setAddress1(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getAddress1()));
        _EcAnonymousPaymentInfo.setAddress2(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getAddress2()));
        _EcAnonymousPaymentInfo.setCity(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getCity()));
        _EcAnonymousPaymentInfo.setState(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getState()));
        _EcAnonymousPaymentInfo.setZip(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getZip()));
        _EcAnonymousPaymentInfo.setCountry(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getCountry()));
        _EcAnonymousPaymentInfo.setPaymentType(WebParamUtil.getIntValue(_EcAnonymousPaymentInfoForm.getPaymentType()));
        _EcAnonymousPaymentInfo.setPaymentNum(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getPaymentNum()));
        _EcAnonymousPaymentInfo.setPaymentExpireMonth(WebParamUtil.getIntValue(_EcAnonymousPaymentInfoForm.getPaymentExpireMonth()));
        _EcAnonymousPaymentInfo.setPaymentExpireYear(WebParamUtil.getIntValue(_EcAnonymousPaymentInfoForm.getPaymentExpireYear()));
        _EcAnonymousPaymentInfo.setPaymentExtraNum(WebParamUtil.getStringValue(_EcAnonymousPaymentInfoForm.getPaymentExtraNum()));
        _EcAnonymousPaymentInfo.setTimeCreated(WebParamUtil.getDateValue(_EcAnonymousPaymentInfoForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousPaymentInfo);
        m_ds.update(_EcAnonymousPaymentInfo);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousPaymentInfo);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcAnonymousPaymentInfo _EcAnonymousPaymentInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = m_ds.getById(cid);

        if (!isMissing(request.getParameter("anonymousUserId"))) {
            m_logger.debug("updating param anonymousUserId from " +_EcAnonymousPaymentInfo.getAnonymousUserId() + "->" + request.getParameter("anonymousUserId"));
                _EcAnonymousPaymentInfo.setAnonymousUserId(WebParamUtil.getLongValue(request.getParameter("anonymousUserId")));
            }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_EcAnonymousPaymentInfo.getFirstName() + "->" + request.getParameter("firstName"));
                _EcAnonymousPaymentInfo.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));
            }
        if (!isMissing(request.getParameter("middleInitial"))) {
            m_logger.debug("updating param middleInitial from " +_EcAnonymousPaymentInfo.getMiddleInitial() + "->" + request.getParameter("middleInitial"));
                _EcAnonymousPaymentInfo.setMiddleInitial(WebParamUtil.getStringValue(request.getParameter("middleInitial")));
            }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_EcAnonymousPaymentInfo.getLastName() + "->" + request.getParameter("lastName"));
                _EcAnonymousPaymentInfo.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));
            }
        if (!isMissing(request.getParameter("address1"))) {
            m_logger.debug("updating param address1 from " +_EcAnonymousPaymentInfo.getAddress1() + "->" + request.getParameter("address1"));
                _EcAnonymousPaymentInfo.setAddress1(WebParamUtil.getStringValue(request.getParameter("address1")));
            }
        if (!isMissing(request.getParameter("address2"))) {
            m_logger.debug("updating param address2 from " +_EcAnonymousPaymentInfo.getAddress2() + "->" + request.getParameter("address2"));
                _EcAnonymousPaymentInfo.setAddress2(WebParamUtil.getStringValue(request.getParameter("address2")));
            }
        if (!isMissing(request.getParameter("city"))) {
            m_logger.debug("updating param city from " +_EcAnonymousPaymentInfo.getCity() + "->" + request.getParameter("city"));
                _EcAnonymousPaymentInfo.setCity(WebParamUtil.getStringValue(request.getParameter("city")));
            }
        if (!isMissing(request.getParameter("state"))) {
            m_logger.debug("updating param state from " +_EcAnonymousPaymentInfo.getState() + "->" + request.getParameter("state"));
                _EcAnonymousPaymentInfo.setState(WebParamUtil.getStringValue(request.getParameter("state")));
            }
        if (!isMissing(request.getParameter("zip"))) {
            m_logger.debug("updating param zip from " +_EcAnonymousPaymentInfo.getZip() + "->" + request.getParameter("zip"));
                _EcAnonymousPaymentInfo.setZip(WebParamUtil.getStringValue(request.getParameter("zip")));
            }
        if (!isMissing(request.getParameter("country"))) {
            m_logger.debug("updating param country from " +_EcAnonymousPaymentInfo.getCountry() + "->" + request.getParameter("country"));
                _EcAnonymousPaymentInfo.setCountry(WebParamUtil.getStringValue(request.getParameter("country")));
            }
        if (!isMissing(request.getParameter("paymentType"))) {
            m_logger.debug("updating param paymentType from " +_EcAnonymousPaymentInfo.getPaymentType() + "->" + request.getParameter("paymentType"));
                _EcAnonymousPaymentInfo.setPaymentType(WebParamUtil.getIntValue(request.getParameter("paymentType")));
            }
        if (!isMissing(request.getParameter("paymentNum"))) {
            m_logger.debug("updating param paymentNum from " +_EcAnonymousPaymentInfo.getPaymentNum() + "->" + request.getParameter("paymentNum"));
                _EcAnonymousPaymentInfo.setPaymentNum(WebParamUtil.getStringValue(request.getParameter("paymentNum")));
            }
        if (!isMissing(request.getParameter("paymentExpireMonth"))) {
            m_logger.debug("updating param paymentExpireMonth from " +_EcAnonymousPaymentInfo.getPaymentExpireMonth() + "->" + request.getParameter("paymentExpireMonth"));
                _EcAnonymousPaymentInfo.setPaymentExpireMonth(WebParamUtil.getIntValue(request.getParameter("paymentExpireMonth")));
            }
        if (!isMissing(request.getParameter("paymentExpireYear"))) {
            m_logger.debug("updating param paymentExpireYear from " +_EcAnonymousPaymentInfo.getPaymentExpireYear() + "->" + request.getParameter("paymentExpireYear"));
                _EcAnonymousPaymentInfo.setPaymentExpireYear(WebParamUtil.getIntValue(request.getParameter("paymentExpireYear")));
            }
        if (!isMissing(request.getParameter("paymentExtraNum"))) {
            m_logger.debug("updating param paymentExtraNum from " +_EcAnonymousPaymentInfo.getPaymentExtraNum() + "->" + request.getParameter("paymentExtraNum"));
                _EcAnonymousPaymentInfo.setPaymentExtraNum(WebParamUtil.getStringValue(request.getParameter("paymentExtraNum")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcAnonymousPaymentInfo.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _EcAnonymousPaymentInfo.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousPaymentInfo);
        m_ds.update(_EcAnonymousPaymentInfo);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousPaymentInfo);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcAnonymousPaymentInfoDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcAnonymousPaymentInfoAction.class);
}
