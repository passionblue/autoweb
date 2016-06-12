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

import com.autosite.db.SiteRegPaymentInfo;
import com.autosite.ds.SiteRegPaymentInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.SiteRegPaymentInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class SiteRegPaymentInfoAction extends AutositeCoreAction {

    public SiteRegPaymentInfoAction(){
        m_ds = SiteRegPaymentInfoDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SiteRegPaymentInfoForm _SiteRegPaymentInfoForm = (SiteRegPaymentInfoForm) form;
        HttpSession session = request.getSession();

            setPage(session, "site_reg_payment_info");
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }


            m_logger.info("Creating new SiteRegPaymentInfo" );
            SiteRegPaymentInfo _SiteRegPaymentInfo = new SiteRegPaymentInfo();   

            // Setting IDs for the object

            _SiteRegPaymentInfo.setTargetDomain(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getTargetDomain()));
            m_logger.debug("setting TargetDomain=" +_SiteRegPaymentInfoForm.getTargetDomain());
            _SiteRegPaymentInfo.setPaymentType(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getPaymentType()));
            m_logger.debug("setting PaymentType=" +_SiteRegPaymentInfoForm.getPaymentType());
            _SiteRegPaymentInfo.setCardType(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getCardType()));
            m_logger.debug("setting CardType=" +_SiteRegPaymentInfoForm.getCardType());
            _SiteRegPaymentInfo.setPaymentNum(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getPaymentNum()));
            m_logger.debug("setting PaymentNum=" +_SiteRegPaymentInfoForm.getPaymentNum());
            _SiteRegPaymentInfo.setExpireMonth(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getExpireMonth()));
            m_logger.debug("setting ExpireMonth=" +_SiteRegPaymentInfoForm.getExpireMonth());
            _SiteRegPaymentInfo.setExpireYear(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getExpireYear()));
            m_logger.debug("setting ExpireYear=" +_SiteRegPaymentInfoForm.getExpireYear());
            _SiteRegPaymentInfo.setCcv(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getCcv()));
            m_logger.debug("setting Ccv=" +_SiteRegPaymentInfoForm.getCcv());
            _SiteRegPaymentInfo.setSkip(WebParamUtil.getCheckboxValue(_SiteRegPaymentInfoForm.getSkip()));
            m_logger.debug("setting Skip=" +_SiteRegPaymentInfoForm.getSkip());

            
            try{
                m_actionExtent.beforeAdd(request, response, _SiteRegPaymentInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_SiteRegPaymentInfo);
            try{
                m_actionExtent.afterAdd(request, response, _SiteRegPaymentInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
		setPage(session, "site_reg_confirm");

        
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteRegPaymentInfoForm _SiteRegPaymentInfoForm, SiteRegPaymentInfo _SiteRegPaymentInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteRegPaymentInfo _SiteRegPaymentInfo = m_ds.getById(cid);

        _SiteRegPaymentInfo.setTargetDomain(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getTargetDomain()));
        _SiteRegPaymentInfo.setPaymentType(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getPaymentType()));
        _SiteRegPaymentInfo.setCardType(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getCardType()));
        _SiteRegPaymentInfo.setPaymentNum(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getPaymentNum()));
        _SiteRegPaymentInfo.setExpireMonth(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getExpireMonth()));
        _SiteRegPaymentInfo.setExpireYear(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getExpireYear()));
        _SiteRegPaymentInfo.setCcv(WebParamUtil.getStringValue(_SiteRegPaymentInfoForm.getCcv()));
        _SiteRegPaymentInfo.setSkip(WebParamUtil.getCheckboxValue(_SiteRegPaymentInfoForm.getSkip()));

        m_actionExtent.beforeUpdate(request, response, _SiteRegPaymentInfo);
        m_ds.update(_SiteRegPaymentInfo);
        m_actionExtent.afterUpdate(request, response, _SiteRegPaymentInfo);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, SiteRegPaymentInfo _SiteRegPaymentInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        SiteRegPaymentInfo _SiteRegPaymentInfo = m_ds.getById(cid);

        
        if (!isMissing(request.getParameter("targetDomain"))) {
            m_logger.debug("updating param targetDomain from " +_SiteRegPaymentInfo.getTargetDomain() + "->" + request.getParameter("targetDomain"));
                _SiteRegPaymentInfo.setTargetDomain(WebParamUtil.getStringValue(request.getParameter("targetDomain")));
            }
        if (!isMissing(request.getParameter("paymentType"))) {
            m_logger.debug("updating param paymentType from " +_SiteRegPaymentInfo.getPaymentType() + "->" + request.getParameter("paymentType"));
                _SiteRegPaymentInfo.setPaymentType(WebParamUtil.getStringValue(request.getParameter("paymentType")));
            }
        if (!isMissing(request.getParameter("cardType"))) {
            m_logger.debug("updating param cardType from " +_SiteRegPaymentInfo.getCardType() + "->" + request.getParameter("cardType"));
                _SiteRegPaymentInfo.setCardType(WebParamUtil.getStringValue(request.getParameter("cardType")));
            }
        if (!isMissing(request.getParameter("paymentNum"))) {
            m_logger.debug("updating param paymentNum from " +_SiteRegPaymentInfo.getPaymentNum() + "->" + request.getParameter("paymentNum"));
                _SiteRegPaymentInfo.setPaymentNum(WebParamUtil.getStringValue(request.getParameter("paymentNum")));
            }
        if (!isMissing(request.getParameter("expireMonth"))) {
            m_logger.debug("updating param expireMonth from " +_SiteRegPaymentInfo.getExpireMonth() + "->" + request.getParameter("expireMonth"));
                _SiteRegPaymentInfo.setExpireMonth(WebParamUtil.getStringValue(request.getParameter("expireMonth")));
            }
        if (!isMissing(request.getParameter("expireYear"))) {
            m_logger.debug("updating param expireYear from " +_SiteRegPaymentInfo.getExpireYear() + "->" + request.getParameter("expireYear"));
                _SiteRegPaymentInfo.setExpireYear(WebParamUtil.getStringValue(request.getParameter("expireYear")));
            }
        if (!isMissing(request.getParameter("ccv"))) {
            m_logger.debug("updating param ccv from " +_SiteRegPaymentInfo.getCcv() + "->" + request.getParameter("ccv"));
                _SiteRegPaymentInfo.setCcv(WebParamUtil.getStringValue(request.getParameter("ccv")));
            }
        if (!isMissing(request.getParameter("skip"))) {
            m_logger.debug("updating param skip from " +_SiteRegPaymentInfo.getSkip() + "->" + request.getParameter("skip"));
                _SiteRegPaymentInfo.setSkip(WebParamUtil.getIntValue(request.getParameter("skip")));
            }
        
        m_actionExtent.beforeUpdate(request, response, _SiteRegPaymentInfo);
        m_ds.update(_SiteRegPaymentInfo);
        m_actionExtent.afterUpdate(request, response, _SiteRegPaymentInfo);
    }

    protected boolean loginRequired() {
        return false;
    }

    protected SiteRegPaymentInfoDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SiteRegPaymentInfoAction.class);
}
