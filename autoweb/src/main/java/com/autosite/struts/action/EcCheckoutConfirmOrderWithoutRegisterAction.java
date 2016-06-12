package com.autosite.struts.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcAnonymousPaymentInfo;
import com.autosite.db.EcAnonymousShippingInfo;
import com.autosite.db.EcAnonymousUserAccount;
import com.autosite.db.EcCheckoutAccountInfo;
import com.autosite.db.EcCheckoutPaymentWithoutRegister;
import com.autosite.db.EcOrder;
import com.autosite.db.EcOrderItem;
import com.autosite.db.Site;
import com.autosite.ds.EcAnonymousPaymentInfoDS;
import com.autosite.ds.EcAnonymousShippingInfoDS;
import com.autosite.ds.EcAnonymousUserAccountDS;
import com.autosite.ds.EcCheckoutAccountInfoDS;
import com.autosite.ds.EcCheckoutPaymentWithoutRegisterDS;
import com.autosite.ds.EcOrderDS;
import com.autosite.ds.EcOrderItemDS;
import com.autosite.ds.SiteDS;
import com.autosite.ec.EcCart;
import com.autosite.ec.EcCartItem;
import com.autosite.ec.EcCartManager;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.EcOrderUtil;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebParamUtil;

public class EcCheckoutConfirmOrderWithoutRegisterAction extends AutositeCoreAction {

    public EcCheckoutConfirmOrderWithoutRegisterAction(){
        m_orderDS = EcOrderDS.getInstance(); 
        
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        
        
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        WebProcess webProc = null;
        try {
            webProc = checkWebProcess(request, session);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(), e);
            return mapping.findForward("default");
        }

        AutositeSessionContext ctx = (AutositeSessionContext) getSessionContext(session);

        //=====================================================================
        // Get or Create Cart

        String rpcId = (String) session.getAttribute("k_RPCI");
        EcCart cart = EcCartManager.getCartMakeSure(ctx, rpcId, site.getId());

        if (cart == null) {
            sessionErrorText(session, "Error occurred while processing. Please try again");
            m_logger.info("****Could not get or create cart. Need To Investigate The Code****");
            return mapping.findForward("default");
        }

        m_logger.info("Cart found=" + cart);

        if (isMissing(request.getParameter("cartSerial"))){
            sessionErrorText(session, "Error occurred while processing. Please try again");
            m_logger.info("*ERROR* cart serial is missing in order confirm");
            return mapping.findForward("default");
        }

        String cartSerialPassed = request.getParameter("cartSerial");

        String cartSerialSaved = webProc.getArg(); 
        
        if ( !cartSerialPassed.equals(cartSerialSaved)){
            setPage(session, "ec_cart_main");
            sessionErrorText(session, "Error occurred while processing. Please try again");
            m_logger.info("*ERROR* saved cart serial differ from passed cart serial. saved=" + cartSerialSaved + " passed=" + cartSerialPassed);
            return mapping.findForward("default");
        }
        
        EcCheckoutAccountInfo accountInfo = EcCheckoutAccountInfoDS.getInstance().getObjectByCartSerial(cart.getSerial());
        EcCheckoutPaymentWithoutRegister payment = EcCheckoutPaymentWithoutRegisterDS.getInstance().getObjectByCartSerial(cart.getSerial());

        if (accountInfo == null) {
            sessionErrorText(session, "Error occurred while processing. Please try again");
            m_logger.info("*ERROR* EcCheckoutAccountInfo is missing for the cart serial " + cart.getSerial());
            return mapping.findForward("default");
        }
        
        if (payment == null) {
            sessionErrorText(session, "Error occurred while processing. Please try again");
            m_logger.info("*ERROR* EcCheckoutPaymentWithoutRegister is missing for the cart serial " + cart.getSerial());
            return mapping.findForward("default");
        }

        //=====================================================================
        // Create Anonymous User ID & Account
        
        EcAnonymousUserAccount anonAcc = new EcAnonymousUserAccount();
        anonAcc.setEmail(accountInfo.getEmail());
        anonAcc.setSiteId(site.getId());
        anonAcc.setSubscribed(accountInfo.getSubsEmail());
        anonAcc.setTimeCreated(new TimeNow());
        
        EcAnonymousUserAccountDS.getInstance().put(anonAcc);
        m_logger.debug("Anonymous account created. " + EcAnonymousUserAccountDS.objectToString(anonAcc));
        
        
        //=====================================================================
        // Create shipping info
        
        EcAnonymousShippingInfo shipInfo = new EcAnonymousShippingInfo();

        shipInfo.setSiteId(site.getId());
        shipInfo.setAnonymousUserId(anonAcc.getId());
        
        shipInfo.setFirstName(accountInfo.getFirstName());
        shipInfo.setMiddleInitial(accountInfo.getMiddleInitial());
        shipInfo.setLastName(accountInfo.getLastName());
        shipInfo.setAddress1(accountInfo.getAddress1());
        shipInfo.setAddress2(accountInfo.getAddress2());
        shipInfo.setCity(accountInfo.getCity());
        shipInfo.setState(accountInfo.getStateProvince());
        shipInfo.setCountry(accountInfo.getCountryRegion());
        shipInfo.setZip(accountInfo.getZip());
        shipInfo.setPhone(EcOrderUtil.formatPhone(accountInfo.getPhone()));
        shipInfo.setSpecialInstruction("");
        shipInfo.setTimeCreated(new TimeNow());
        
        EcAnonymousShippingInfoDS.getInstance().put(shipInfo);
        m_logger.debug("EcAnonymousShippingInfo created. " + EcAnonymousShippingInfoDS.objectToString(shipInfo));

        //=====================================================================
        // Create payment info
        
        EcAnonymousPaymentInfo payInfo = new EcAnonymousPaymentInfo();
        
        payInfo.setSiteId(site.getId());
        payInfo.setAnonymousUserId(anonAcc.getId());
        
        if (accountInfo.getUseBilling() == 1){
            payInfo.setFirstName(accountInfo.getFirstName());
            payInfo.setMiddleInitial(accountInfo.getMiddleInitial());
            payInfo.setLastName(accountInfo.getLastName());
            payInfo.setAddress1(accountInfo.getAddress1());
            payInfo.setAddress2(accountInfo.getAddress2());
            payInfo.setCity(accountInfo.getCity());
            payInfo.setState(accountInfo.getStateProvince());
            payInfo.setCountry(accountInfo.getCountryRegion());
            payInfo.setZip(accountInfo.getZip());
            payInfo.setPhone(EcOrderUtil.formatPhone(accountInfo.getPhone()));
            
        } else {
            payInfo.setFirstName(accountInfo.getBillingFirstName());
            payInfo.setMiddleInitial(accountInfo.getBillingMiddleInitial());
            payInfo.setLastName(accountInfo.getBillingLastName());
            payInfo.setAddress1(accountInfo.getBillingAddress1());
            payInfo.setAddress2(accountInfo.getBillingAddress2());
            payInfo.setCity(accountInfo.getBillingCity());
            payInfo.setState(accountInfo.getBillingState());
            payInfo.setCountry(accountInfo.getBillingCountry());
            payInfo.setZip(accountInfo.getBillingZip());
            payInfo.setPhone(EcOrderUtil.formatPhone(accountInfo.getBillingPhone()));
        }
        
        payInfo.setPaymentType(EcOrderUtil.getPaymentType(payment.getPaymentType()));
        payInfo.setCardType(payment.getCardType());
        payInfo.setPaymentNum(payment.getPaymentNum());
        payInfo.setPaymentExpireYear(WebParamUtil.getIntValue(payment.getExpireYear()));
        payInfo.setPaymentExpireMonth(WebParamUtil.getIntValue(payment.getExpireMonth()));
        payInfo.setPaymentExtraNum(payment.getCcv());
        payInfo.setTimeCreated(new TimeNow());
        
        EcAnonymousPaymentInfoDS.getInstance().put(payInfo);
        m_logger.debug("EcAnonymousPaymentInfo created. " + EcAnonymousPaymentInfoDS.objectToString(payInfo));
        
        //=====================================================================
        // copy cart to orders and persist
        
        EcOrder order = EcOrderUtil.convertToAnonymousOrder(site.getId(), cart);
        
        EcOrder anySameOrder = m_orderDS.getObjectByOrderNum(order.getOrderNum());
        
        if (anySameOrder != null) {
            sessionErrorText(session, "Error occurred while processing. Please try again");
            m_logger.info("*ERROR* Order id exists. orderNum=" + order.getOrderNum());
            return mapping.findForward("default");
        }

        order.setAnonymousUserId(anonAcc.getId());
        
        m_orderDS.put(order);
        m_logger.debug("EcOrder created. " + EcOrderDS.objectToString(order));
        
        //=====================================================================
        // Copy and Persist cart items
        List cartItems = cart.getCartItems();
        
        for (Iterator iterator = cartItems.iterator(); iterator.hasNext();) {
            EcCartItem cartItem = (EcCartItem) iterator.next();
            
            EcOrderItem ordItem = EcOrderUtil.convertToOrderItem(order, cartItem);
            ordItem.setSiteId(site.getId());
            EcOrderItemDS.getInstance().put(ordItem);
            
            m_logger.info("EcOrderItem Saved. " + EcOrderItemDS.objectToString(ordItem));
        }
        
        
        //=====================================================================
        // Clean-up
        
        EcCartManager.getInstance().removeCart(site.getId(), cart.getRPCI());
        EcCheckoutAccountInfoDS.getInstance().delete(accountInfo);
        EcCheckoutPaymentWithoutRegisterDS.getInstance().delete(payment);
        
        webProc.complete();
        
        setPage(session, "ec_checkout_receipt");
        return mapping.findForward("default");
    }

    protected boolean loginRequired() {
        return false;
    }

    protected EcCheckoutAccountInfoDS m_ds;
    protected EcOrderDS m_orderDS;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcCheckoutConfirmOrderWithoutRegisterAction.class);
}
