package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeUser;
import com.autosite.db.EcCheckoutAccountInfo;
import com.autosite.db.Site;
import com.autosite.ds.EcCheckoutAccountInfoDS;
import com.autosite.ds.SiteDS;
import com.autosite.ec.EcCart;
import com.autosite.ec.EcCartManager;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.EcCheckoutAccountInfoForm;
import com.autosite.util.UserUtil;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebParamUtil;

public class EcCheckoutAccountInfoAction extends AutositeCoreAction {

    public EcCheckoutAccountInfoAction(){
        m_ds = EcCheckoutAccountInfoDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcCheckoutAccountInfoForm _EcCheckoutAccountInfoForm = (EcCheckoutAccountInfoForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        // Default if any info is not correct
        setPage(session, "ec_checkout_account_info"); 

        //=======================================================================================
        if (hasRequestValue(request, "cmd", "without")){
        
            WebProcess webProc = null;
            try {
//                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
                return mapping.findForward("default");
            }

            m_logger.info("Creating new EcCheckoutAccountInfo" );
            EcCheckoutAccountInfo _EcCheckoutAccountInfo = new EcCheckoutAccountInfo();   

            String cartSerial = WebParamUtil.getStringValue(request.getParameter("cartSerial"));
            EcCart cart = EcCartManager.getInstance().getByCartSerial(cartSerial);
            if ( cart == null|| cart.getCartItems().size() == 0 ) {
                sessionErrorText(session, "Cart is empty. Please re-enter into the shopping cart.");
                setPage(session, "ec_cart_main");
                return mapping.findForward("default");
            }

            _EcCheckoutAccountInfo = EcCheckoutAccountInfoDS.getInstance().getObjectByCartSerial(cartSerial);
            if (_EcCheckoutAccountInfo == null){
                _EcCheckoutAccountInfo = new EcCheckoutAccountInfo(); 
                _EcCheckoutAccountInfo.setCartSerial(cartSerial);
                EcCheckoutAccountInfoDS.getInstance().put(_EcCheckoutAccountInfo);
            }
            
            // Setting IDs for the object
            _EcCheckoutAccountInfo.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null)
                _EcCheckoutAccountInfo.setUserId(user.getId());

            _EcCheckoutAccountInfo.setEmail(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getEmail()));
            m_logger.debug("setting Email=" +_EcCheckoutAccountInfoForm.getEmail());
            _EcCheckoutAccountInfo.setEmailRetype(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getEmailRetype()));
            m_logger.debug("setting EmailRetype=" +_EcCheckoutAccountInfoForm.getEmailRetype());
            _EcCheckoutAccountInfo.setFirstName(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_EcCheckoutAccountInfoForm.getFirstName());
            _EcCheckoutAccountInfo.setMiddleInitial(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getMiddleInitial()));
            m_logger.debug("setting MiddleInitial=" +_EcCheckoutAccountInfoForm.getMiddleInitial());
            _EcCheckoutAccountInfo.setLastName(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getLastName()));
            m_logger.debug("setting LastName=" +_EcCheckoutAccountInfoForm.getLastName());
            _EcCheckoutAccountInfo.setAddress1(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getAddress1()));
            m_logger.debug("setting Address1=" +_EcCheckoutAccountInfoForm.getAddress1());
            _EcCheckoutAccountInfo.setAddress2(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getAddress2()));
            m_logger.debug("setting Address2=" +_EcCheckoutAccountInfoForm.getAddress2());
            _EcCheckoutAccountInfo.setCity(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getCity()));
            m_logger.debug("setting City=" +_EcCheckoutAccountInfoForm.getCity());
            _EcCheckoutAccountInfo.setCountryRegion(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getCountryRegion()));
            m_logger.debug("setting CountryRegion=" +_EcCheckoutAccountInfoForm.getCountryRegion());
            _EcCheckoutAccountInfo.setStateProvince(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getStateProvince()));
            m_logger.debug("setting StateProvince=" +_EcCheckoutAccountInfoForm.getStateProvince());
            _EcCheckoutAccountInfo.setZip(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getZip()));
            m_logger.debug("setting Zip=" +_EcCheckoutAccountInfoForm.getZip());
            _EcCheckoutAccountInfo.setPhone(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getPhone()));
            m_logger.debug("setting Phone=" +_EcCheckoutAccountInfoForm.getPhone());
            _EcCheckoutAccountInfo.setUseBilling(WebParamUtil.getCheckboxValue(_EcCheckoutAccountInfoForm.getUseBilling()));
            m_logger.debug("setting UseBilling=" +_EcCheckoutAccountInfoForm.getUseBilling());
            _EcCheckoutAccountInfo.setBillingFirstName(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingFirstName()));
            m_logger.debug("setting BillingFirstName=" +_EcCheckoutAccountInfoForm.getBillingFirstName());
            _EcCheckoutAccountInfo.setBillingMiddleInitial(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingMiddleInitial()));
            m_logger.debug("setting BillingMiddleInitial=" +_EcCheckoutAccountInfoForm.getBillingMiddleInitial());
            _EcCheckoutAccountInfo.setBillingLastName(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingLastName()));
            m_logger.debug("setting BillingLastName=" +_EcCheckoutAccountInfoForm.getBillingLastName());
            _EcCheckoutAccountInfo.setBillingAddress1(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingAddress1()));
            m_logger.debug("setting BillingAddress1=" +_EcCheckoutAccountInfoForm.getBillingAddress1());
            _EcCheckoutAccountInfo.setBillingAddress2(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingAddress2()));
            m_logger.debug("setting BillingAddress2=" +_EcCheckoutAccountInfoForm.getBillingAddress2());
            _EcCheckoutAccountInfo.setBillingCity(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingCity()));
            m_logger.debug("setting BillingCity=" +_EcCheckoutAccountInfoForm.getBillingCity());
            _EcCheckoutAccountInfo.setBillingCountry(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingCountry()));
            m_logger.debug("setting BillingCountry=" +_EcCheckoutAccountInfoForm.getBillingCountry());
            _EcCheckoutAccountInfo.setBillingState(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingState()));
            m_logger.debug("setting BillingState=" +_EcCheckoutAccountInfoForm.getBillingState());
            _EcCheckoutAccountInfo.setBillingZip(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingZip()));
            m_logger.debug("setting BillingZip=" +_EcCheckoutAccountInfoForm.getBillingZip());
            _EcCheckoutAccountInfo.setBillingPhone(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getBillingPhone()));
            m_logger.debug("setting BillingPhone=" +_EcCheckoutAccountInfoForm.getBillingPhone());
            _EcCheckoutAccountInfo.setTermAgree(WebParamUtil.getCheckboxValue(_EcCheckoutAccountInfoForm.getTermAgree()));
            m_logger.debug("setting TermAgree=" +_EcCheckoutAccountInfoForm.getTermAgree());
            _EcCheckoutAccountInfo.setSubsEmail(WebParamUtil.getCheckboxValue(_EcCheckoutAccountInfoForm.getSubsEmail()));
            m_logger.debug("setting SubsEmail=" +_EcCheckoutAccountInfoForm.getSubsEmail());
            _EcCheckoutAccountInfo.setPassword(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getPassword()));
            m_logger.debug("setting Password=" +_EcCheckoutAccountInfoForm.getPassword());
            _EcCheckoutAccountInfo.setPasswordRetype(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getPasswordRetype()));
            m_logger.debug("setting PasswordRetype=" +_EcCheckoutAccountInfoForm.getPasswordRetype());
            _EcCheckoutAccountInfo.setReturnEmail(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getReturnEmail()));
            m_logger.debug("setting ReturnEmail=" +_EcCheckoutAccountInfoForm.getReturnEmail());
            _EcCheckoutAccountInfo.setReturnPassword(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getReturnPassword()));
            m_logger.debug("setting ReturnPassword=" +_EcCheckoutAccountInfoForm.getReturnPassword());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcCheckoutAccountInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcCheckoutAccountInfo);
            try{
                m_actionExtent.afterAdd(request, response, _EcCheckoutAccountInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            setPage(session, "ec_checkout_payment_without_register");
//            webProc.complete();
            
         //=======================================================================================
        }else if (hasRequestValue(request, "cmd", "ret")){
            String returnUser = request.getParameter("return_email");
            String returnPassword = request.getParameter("return_password");
            
            AutositeUser user = UserUtil.dbAuthenticate(site, returnUser, returnPassword);
            if (user != null){
                try {
                    setLogin(session, user);
                    setPage(session,"ec_checkout_payment_return_customer"); 
                }
                catch (Exception e) {
                    sessionErrorText(session, "Internal error occurred. Please try again.");
                    m_logger.error(e.getMessage(),e);
                }
            } else {
                sessionErrorText(session, "Login failed. Please try again");
            }
        }
        
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcCheckoutAccountInfoForm _EcCheckoutAccountInfoForm, EcCheckoutAccountInfo _EcCheckoutAccountInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcCheckoutAccountInfo _EcCheckoutAccountInfo = m_ds.getById(cid);

        _EcCheckoutAccountInfo.setEmail(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getEmail()));
        _EcCheckoutAccountInfo.setEmailRetype(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getEmailRetype()));
        _EcCheckoutAccountInfo.setFirstName(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getFirstName()));
        _EcCheckoutAccountInfo.setLastName(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getLastName()));
        _EcCheckoutAccountInfo.setAddress1(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getAddress1()));
        _EcCheckoutAccountInfo.setAddress2(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getAddress2()));
        _EcCheckoutAccountInfo.setCity(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getCity()));
        _EcCheckoutAccountInfo.setCountryRegion(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getCountryRegion()));
        _EcCheckoutAccountInfo.setStateProvince(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getStateProvince()));
        _EcCheckoutAccountInfo.setZip(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getZip()));
        _EcCheckoutAccountInfo.setPhone(WebParamUtil.getStringValue(_EcCheckoutAccountInfoForm.getPhone()));
        _EcCheckoutAccountInfo.setUseBilling(WebParamUtil.getCheckboxValue(_EcCheckoutAccountInfoForm.getUseBilling()));

        m_actionExtent.beforeUpdate(request, response, _EcCheckoutAccountInfo);
        m_ds.update(_EcCheckoutAccountInfo);
        m_actionExtent.afterUpdate(request, response, _EcCheckoutAccountInfo);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcCheckoutAccountInfo _EcCheckoutAccountInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcCheckoutAccountInfo _EcCheckoutAccountInfo = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcCheckoutAccountInfo.getUserId() + "->" + request.getParameter("userId"));
                _EcCheckoutAccountInfo.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
            }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_EcCheckoutAccountInfo.getEmail() + "->" + request.getParameter("email"));
                _EcCheckoutAccountInfo.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));
            }
        if (!isMissing(request.getParameter("emailRetype"))) {
            m_logger.debug("updating param emailRetype from " +_EcCheckoutAccountInfo.getEmailRetype() + "->" + request.getParameter("emailRetype"));
                _EcCheckoutAccountInfo.setEmailRetype(WebParamUtil.getStringValue(request.getParameter("emailRetype")));
            }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_EcCheckoutAccountInfo.getFirstName() + "->" + request.getParameter("firstName"));
                _EcCheckoutAccountInfo.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));
            }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_EcCheckoutAccountInfo.getLastName() + "->" + request.getParameter("lastName"));
                _EcCheckoutAccountInfo.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));
            }
        if (!isMissing(request.getParameter("address1"))) {
            m_logger.debug("updating param address1 from " +_EcCheckoutAccountInfo.getAddress1() + "->" + request.getParameter("address1"));
                _EcCheckoutAccountInfo.setAddress1(WebParamUtil.getStringValue(request.getParameter("address1")));
            }
        if (!isMissing(request.getParameter("address2"))) {
            m_logger.debug("updating param address2 from " +_EcCheckoutAccountInfo.getAddress2() + "->" + request.getParameter("address2"));
                _EcCheckoutAccountInfo.setAddress2(WebParamUtil.getStringValue(request.getParameter("address2")));
            }
        if (!isMissing(request.getParameter("city"))) {
            m_logger.debug("updating param city from " +_EcCheckoutAccountInfo.getCity() + "->" + request.getParameter("city"));
                _EcCheckoutAccountInfo.setCity(WebParamUtil.getStringValue(request.getParameter("city")));
            }
        if (!isMissing(request.getParameter("countryRegion"))) {
            m_logger.debug("updating param countryRegion from " +_EcCheckoutAccountInfo.getCountryRegion() + "->" + request.getParameter("countryRegion"));
                _EcCheckoutAccountInfo.setCountryRegion(WebParamUtil.getStringValue(request.getParameter("countryRegion")));
            }
        if (!isMissing(request.getParameter("stateProvince"))) {
            m_logger.debug("updating param stateProvince from " +_EcCheckoutAccountInfo.getStateProvince() + "->" + request.getParameter("stateProvince"));
                _EcCheckoutAccountInfo.setStateProvince(WebParamUtil.getStringValue(request.getParameter("stateProvince")));
            }
        if (!isMissing(request.getParameter("zip"))) {
            m_logger.debug("updating param zip from " +_EcCheckoutAccountInfo.getZip() + "->" + request.getParameter("zip"));
                _EcCheckoutAccountInfo.setZip(WebParamUtil.getStringValue(request.getParameter("zip")));
            }
        if (!isMissing(request.getParameter("phone"))) {
            m_logger.debug("updating param phone from " +_EcCheckoutAccountInfo.getPhone() + "->" + request.getParameter("phone"));
                _EcCheckoutAccountInfo.setPhone(WebParamUtil.getStringValue(request.getParameter("phone")));
            }
        if (!isMissing(request.getParameter("useBilling"))) {
            m_logger.debug("updating param useBilling from " +_EcCheckoutAccountInfo.getUseBilling() + "->" + request.getParameter("useBilling"));
                _EcCheckoutAccountInfo.setUseBilling(WebParamUtil.getIntValue(request.getParameter("useBilling")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcCheckoutAccountInfo);
        m_ds.update(_EcCheckoutAccountInfo);
        m_actionExtent.afterUpdate(request, response, _EcCheckoutAccountInfo);
    }


    protected boolean loginRequired() {
        return false;
    }

    protected EcCheckoutAccountInfoDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcCheckoutAccountInfoAction.class);
}
