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

import com.autosite.db.EcCheckoutPaymentWithoutRegister;
import com.autosite.ds.EcCheckoutPaymentWithoutRegisterDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.ec.EcCart;
import com.autosite.ec.EcCartManager;

import com.autosite.struts.form.EcCheckoutPaymentWithoutRegisterForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcCheckoutPaymentWithoutRegisterAction extends AutositeCoreAction {

    public EcCheckoutPaymentWithoutRegisterAction(){
        m_ds = EcCheckoutPaymentWithoutRegisterDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcCheckoutPaymentWithoutRegisterForm _EcCheckoutPaymentWithoutRegisterForm = (EcCheckoutPaymentWithoutRegisterForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        setPage(session, "ec_checkout_payment_without_register");
        
        WebProcess webProc = null;
        try {
//            webProc = checkWebProcess(request, session);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(), e);
            return mapping.findForward("default");
        }

        String cartSerial = WebParamUtil.getStringValue(request.getParameter("cartSerial"));
        EcCart cart = EcCartManager.getInstance().getByCartSerial(cartSerial);
        if ( cart == null|| cart.getCartItems().size() == 0 ) {
            sessionErrorText(session, "Cart is empty. Please re-enter into the shopping cart.");
            setPage(session, "ec_cart_main");
            return mapping.findForward("default");
        }
        
        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = EcCheckoutPaymentWithoutRegisterDS.getInstance().getObjectByCartSerial(cartSerial);   
        
        // Setting IDs for the object
        _EcCheckoutPaymentWithoutRegister.setSiteId(site.getId());

//        AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
//        AutositeUser user = ctx.getUserObject();
//        if (user != null)
//            _EcCheckoutPaymentWithoutRegister.setUserId(user.getId());

        _EcCheckoutPaymentWithoutRegister.setCartSerial(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getCartSerial()));
        m_logger.debug("setting CartSerial=" +_EcCheckoutPaymentWithoutRegisterForm.getCartSerial());
        _EcCheckoutPaymentWithoutRegister.setPaymentType(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getPaymentType()));
        m_logger.debug("setting PaymentType=" +_EcCheckoutPaymentWithoutRegisterForm.getPaymentType());
        _EcCheckoutPaymentWithoutRegister.setCardType(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getCardType()));
        m_logger.debug("setting CardType=" +_EcCheckoutPaymentWithoutRegisterForm.getCardType());
        _EcCheckoutPaymentWithoutRegister.setPaymentNum(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getPaymentNum()));
        m_logger.debug("setting PaymentNum=" +_EcCheckoutPaymentWithoutRegisterForm.getPaymentNum());
        _EcCheckoutPaymentWithoutRegister.setExpireMonth(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getExpireMonth()));
        m_logger.debug("setting ExpireMonth=" +_EcCheckoutPaymentWithoutRegisterForm.getExpireMonth());
        _EcCheckoutPaymentWithoutRegister.setExpireYear(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getExpireYear()));
        m_logger.debug("setting ExpireYear=" +_EcCheckoutPaymentWithoutRegisterForm.getExpireYear());
        _EcCheckoutPaymentWithoutRegister.setCcv(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getCcv()));
        m_logger.debug("setting Ccv=" +_EcCheckoutPaymentWithoutRegisterForm.getCcv());

        
        try{
            m_actionExtent.beforeAdd(request, response, _EcCheckoutPaymentWithoutRegister);
        }
        catch (Exception e) {
            sessionErrorText(session, e.getMessage());
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }
        m_ds.put(_EcCheckoutPaymentWithoutRegister);
        try{
            m_actionExtent.afterAdd(request, response, _EcCheckoutPaymentWithoutRegister);
        }
        catch (Exception e) {
            sessionErrorText(session, e.getMessage());
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }
        
        setPage(session, "ec_checkout_confirm_without_register");
//        webProc.complete();
        
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcCheckoutPaymentWithoutRegisterForm _EcCheckoutPaymentWithoutRegisterForm, EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = m_ds.getById(cid);

        _EcCheckoutPaymentWithoutRegister.setCartSerial(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getCartSerial()));
        _EcCheckoutPaymentWithoutRegister.setPaymentType(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getPaymentType()));
        _EcCheckoutPaymentWithoutRegister.setCardType(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getCardType()));
        _EcCheckoutPaymentWithoutRegister.setPaymentNum(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getPaymentNum()));
        _EcCheckoutPaymentWithoutRegister.setExpireMonth(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getExpireMonth()));
        _EcCheckoutPaymentWithoutRegister.setExpireYear(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getExpireYear()));
        _EcCheckoutPaymentWithoutRegister.setCcv(WebParamUtil.getStringValue(_EcCheckoutPaymentWithoutRegisterForm.getCcv()));

        m_actionExtent.beforeUpdate(request, response, _EcCheckoutPaymentWithoutRegister);
        m_ds.update(_EcCheckoutPaymentWithoutRegister);
        m_actionExtent.afterUpdate(request, response, _EcCheckoutPaymentWithoutRegister);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcCheckoutPaymentWithoutRegister.getUserId() + "->" + request.getParameter("userId"));
                _EcCheckoutPaymentWithoutRegister.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("cartSerial"))) {
            m_logger.debug("updating param cartSerial from " +_EcCheckoutPaymentWithoutRegister.getCartSerial() + "->" + request.getParameter("cartSerial"));
                _EcCheckoutPaymentWithoutRegister.setCartSerial(WebParamUtil.getStringValue(request.getParameter("cartSerial")));
            }
        if (!isMissing(request.getParameter("paymentType"))) {
            m_logger.debug("updating param paymentType from " +_EcCheckoutPaymentWithoutRegister.getPaymentType() + "->" + request.getParameter("paymentType"));
                _EcCheckoutPaymentWithoutRegister.setPaymentType(WebParamUtil.getStringValue(request.getParameter("paymentType")));
            }
        if (!isMissing(request.getParameter("cardType"))) {
            m_logger.debug("updating param cardType from " +_EcCheckoutPaymentWithoutRegister.getCardType() + "->" + request.getParameter("cardType"));
                _EcCheckoutPaymentWithoutRegister.setCardType(WebParamUtil.getStringValue(request.getParameter("cardType")));
            }
        if (!isMissing(request.getParameter("paymentNum"))) {
            m_logger.debug("updating param paymentNum from " +_EcCheckoutPaymentWithoutRegister.getPaymentNum() + "->" + request.getParameter("paymentNum"));
                _EcCheckoutPaymentWithoutRegister.setPaymentNum(WebParamUtil.getStringValue(request.getParameter("paymentNum")));
            }
        if (!isMissing(request.getParameter("expireMonth"))) {
            m_logger.debug("updating param expireMonth from " +_EcCheckoutPaymentWithoutRegister.getExpireMonth() + "->" + request.getParameter("expireMonth"));
                _EcCheckoutPaymentWithoutRegister.setExpireMonth(WebParamUtil.getStringValue(request.getParameter("expireMonth")));
            }
        if (!isMissing(request.getParameter("expireYear"))) {
            m_logger.debug("updating param expireYear from " +_EcCheckoutPaymentWithoutRegister.getExpireYear() + "->" + request.getParameter("expireYear"));
                _EcCheckoutPaymentWithoutRegister.setExpireYear(WebParamUtil.getStringValue(request.getParameter("expireYear")));
            }
        if (!isMissing(request.getParameter("ccv"))) {
            m_logger.debug("updating param ccv from " +_EcCheckoutPaymentWithoutRegister.getCcv() + "->" + request.getParameter("ccv"));
                _EcCheckoutPaymentWithoutRegister.setCcv(WebParamUtil.getStringValue(request.getParameter("ccv")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcCheckoutPaymentWithoutRegister);
        m_ds.update(_EcCheckoutPaymentWithoutRegister);
        m_actionExtent.afterUpdate(request, response, _EcCheckoutPaymentWithoutRegister);
    }


    protected boolean loginRequired() {
        return false;
    }

    protected EcCheckoutPaymentWithoutRegisterDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcCheckoutPaymentWithoutRegisterAction.class);
}
