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

import com.autosite.db.EcAnonymousShippingInfo;
import com.autosite.ds.EcAnonymousShippingInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.EcAnonymousShippingInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcAnonymousShippingInfoAction extends AutositeCoreAction {

    public EcAnonymousShippingInfoAction(){
        m_ds = EcAnonymousShippingInfoDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcAnonymousShippingInfoForm _EcAnonymousShippingInfoForm = (EcAnonymousShippingInfoForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousShippingInfo _EcAnonymousShippingInfo = m_ds.getById(cid);

            if (_EcAnonymousShippingInfo == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousShippingInfo.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousShippingInfo.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _EcAnonymousShippingInfoForm, _EcAnonymousShippingInfo);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            //setPage(session, "ec_anonymous_shipping_info_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousShippingInfo _EcAnonymousShippingInfo = m_ds.getById(cid);

            if (_EcAnonymousShippingInfo == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousShippingInfo.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousShippingInfo.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _EcAnonymousShippingInfo);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
            //setPage(session, "ec_anonymous_shipping_info");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcAnonymousShippingInfo _EcAnonymousShippingInfo = m_ds.getById(cid);

            if (_EcAnonymousShippingInfo == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcAnonymousShippingInfo.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcAnonymousShippingInfo.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _EcAnonymousShippingInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.delete(_EcAnonymousShippingInfo);
            try { 
                m_actionExtent.afterDelete(request, response, _EcAnonymousShippingInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "ec_anonymous_shipping_info_home");    
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

            m_logger.info("Creating new EcAnonymousShippingInfo" );
            EcAnonymousShippingInfo _EcAnonymousShippingInfo = new EcAnonymousShippingInfo();   

            // Setting IDs for the object
            _EcAnonymousShippingInfo.setSiteId(site.getId());

            _EcAnonymousShippingInfo.setAnonymousUserId(WebParamUtil.getLongValue(_EcAnonymousShippingInfoForm.getAnonymousUserId()));
            m_logger.debug("setting AnonymousUserId=" +_EcAnonymousShippingInfoForm.getAnonymousUserId());
            _EcAnonymousShippingInfo.setFirstName(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getFirstName()));
            m_logger.debug("setting FirstName=" +_EcAnonymousShippingInfoForm.getFirstName());
            _EcAnonymousShippingInfo.setMiddleInitial(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getMiddleInitial()));
            m_logger.debug("setting MiddleInitial=" +_EcAnonymousShippingInfoForm.getMiddleInitial());
            _EcAnonymousShippingInfo.setLastName(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getLastName()));
            m_logger.debug("setting LastName=" +_EcAnonymousShippingInfoForm.getLastName());
            _EcAnonymousShippingInfo.setAddress1(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getAddress1()));
            m_logger.debug("setting Address1=" +_EcAnonymousShippingInfoForm.getAddress1());
            _EcAnonymousShippingInfo.setAddress2(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getAddress2()));
            m_logger.debug("setting Address2=" +_EcAnonymousShippingInfoForm.getAddress2());
            _EcAnonymousShippingInfo.setCity(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getCity()));
            m_logger.debug("setting City=" +_EcAnonymousShippingInfoForm.getCity());
            _EcAnonymousShippingInfo.setState(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getState()));
            m_logger.debug("setting State=" +_EcAnonymousShippingInfoForm.getState());
            _EcAnonymousShippingInfo.setZip(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getZip()));
            m_logger.debug("setting Zip=" +_EcAnonymousShippingInfoForm.getZip());
            _EcAnonymousShippingInfo.setCountry(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getCountry()));
            m_logger.debug("setting Country=" +_EcAnonymousShippingInfoForm.getCountry());
            _EcAnonymousShippingInfo.setSpecialInstruction(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getSpecialInstruction()));
            m_logger.debug("setting SpecialInstruction=" +_EcAnonymousShippingInfoForm.getSpecialInstruction());
            _EcAnonymousShippingInfo.setTimeCreated(WebParamUtil.getDateValue(_EcAnonymousShippingInfoForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_EcAnonymousShippingInfoForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _EcAnonymousShippingInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcAnonymousShippingInfo);
            try{
                m_actionExtent.afterAdd(request, response, _EcAnonymousShippingInfo);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            
            //setPage(session, "ec_anonymous_shipping_info_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcAnonymousShippingInfoForm _EcAnonymousShippingInfoForm, EcAnonymousShippingInfo _EcAnonymousShippingInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousShippingInfo _EcAnonymousShippingInfo = m_ds.getById(cid);

        _EcAnonymousShippingInfo.setAnonymousUserId(WebParamUtil.getLongValue(_EcAnonymousShippingInfoForm.getAnonymousUserId()));
        _EcAnonymousShippingInfo.setFirstName(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getFirstName()));
        _EcAnonymousShippingInfo.setMiddleInitial(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getMiddleInitial()));
        _EcAnonymousShippingInfo.setLastName(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getLastName()));
        _EcAnonymousShippingInfo.setAddress1(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getAddress1()));
        _EcAnonymousShippingInfo.setAddress2(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getAddress2()));
        _EcAnonymousShippingInfo.setCity(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getCity()));
        _EcAnonymousShippingInfo.setState(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getState()));
        _EcAnonymousShippingInfo.setZip(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getZip()));
        _EcAnonymousShippingInfo.setCountry(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getCountry()));
        _EcAnonymousShippingInfo.setSpecialInstruction(WebParamUtil.getStringValue(_EcAnonymousShippingInfoForm.getSpecialInstruction()));
        _EcAnonymousShippingInfo.setTimeCreated(WebParamUtil.getDateValue(_EcAnonymousShippingInfoForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousShippingInfo);
        m_ds.update(_EcAnonymousShippingInfo);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousShippingInfo);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcAnonymousShippingInfo _EcAnonymousShippingInfo) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcAnonymousShippingInfo _EcAnonymousShippingInfo = m_ds.getById(cid);

        if (!isMissing(request.getParameter("anonymousUserId"))) {
            m_logger.debug("updating param anonymousUserId from " +_EcAnonymousShippingInfo.getAnonymousUserId() + "->" + request.getParameter("anonymousUserId"));
                _EcAnonymousShippingInfo.setAnonymousUserId(WebParamUtil.getLongValue(request.getParameter("anonymousUserId")));
            }
        if (!isMissing(request.getParameter("firstName"))) {
            m_logger.debug("updating param firstName from " +_EcAnonymousShippingInfo.getFirstName() + "->" + request.getParameter("firstName"));
                _EcAnonymousShippingInfo.setFirstName(WebParamUtil.getStringValue(request.getParameter("firstName")));
            }
        if (!isMissing(request.getParameter("middleInitial"))) {
            m_logger.debug("updating param middleInitial from " +_EcAnonymousShippingInfo.getMiddleInitial() + "->" + request.getParameter("middleInitial"));
                _EcAnonymousShippingInfo.setMiddleInitial(WebParamUtil.getStringValue(request.getParameter("middleInitial")));
            }
        if (!isMissing(request.getParameter("lastName"))) {
            m_logger.debug("updating param lastName from " +_EcAnonymousShippingInfo.getLastName() + "->" + request.getParameter("lastName"));
                _EcAnonymousShippingInfo.setLastName(WebParamUtil.getStringValue(request.getParameter("lastName")));
            }
        if (!isMissing(request.getParameter("address1"))) {
            m_logger.debug("updating param address1 from " +_EcAnonymousShippingInfo.getAddress1() + "->" + request.getParameter("address1"));
                _EcAnonymousShippingInfo.setAddress1(WebParamUtil.getStringValue(request.getParameter("address1")));
            }
        if (!isMissing(request.getParameter("address2"))) {
            m_logger.debug("updating param address2 from " +_EcAnonymousShippingInfo.getAddress2() + "->" + request.getParameter("address2"));
                _EcAnonymousShippingInfo.setAddress2(WebParamUtil.getStringValue(request.getParameter("address2")));
            }
        if (!isMissing(request.getParameter("city"))) {
            m_logger.debug("updating param city from " +_EcAnonymousShippingInfo.getCity() + "->" + request.getParameter("city"));
                _EcAnonymousShippingInfo.setCity(WebParamUtil.getStringValue(request.getParameter("city")));
            }
        if (!isMissing(request.getParameter("state"))) {
            m_logger.debug("updating param state from " +_EcAnonymousShippingInfo.getState() + "->" + request.getParameter("state"));
                _EcAnonymousShippingInfo.setState(WebParamUtil.getStringValue(request.getParameter("state")));
            }
        if (!isMissing(request.getParameter("zip"))) {
            m_logger.debug("updating param zip from " +_EcAnonymousShippingInfo.getZip() + "->" + request.getParameter("zip"));
                _EcAnonymousShippingInfo.setZip(WebParamUtil.getStringValue(request.getParameter("zip")));
            }
        if (!isMissing(request.getParameter("country"))) {
            m_logger.debug("updating param country from " +_EcAnonymousShippingInfo.getCountry() + "->" + request.getParameter("country"));
                _EcAnonymousShippingInfo.setCountry(WebParamUtil.getStringValue(request.getParameter("country")));
            }
        if (!isMissing(request.getParameter("specialInstruction"))) {
            m_logger.debug("updating param specialInstruction from " +_EcAnonymousShippingInfo.getSpecialInstruction() + "->" + request.getParameter("specialInstruction"));
                _EcAnonymousShippingInfo.setSpecialInstruction(WebParamUtil.getStringValue(request.getParameter("specialInstruction")));
            }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcAnonymousShippingInfo.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _EcAnonymousShippingInfo.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
            }

        m_actionExtent.beforeUpdate(request, response, _EcAnonymousShippingInfo);
        m_ds.update(_EcAnonymousShippingInfo);
        m_actionExtent.afterUpdate(request, response, _EcAnonymousShippingInfo);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected EcAnonymousShippingInfoDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcAnonymousShippingInfoAction.class);
}
