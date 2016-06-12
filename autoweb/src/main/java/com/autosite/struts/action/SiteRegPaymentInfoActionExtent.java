package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.SiteRegPaymentInfo;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.SiteRegPaymentInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.session.SiteRegStore;
import com.autosite.struts.form.SiteRegPaymentInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class SiteRegPaymentInfoActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegPaymentInfoAction#xtent.beforeAdd");		
        SiteRegPaymentInfo _SiteRegPaymentInfo = (SiteRegPaymentInfo)baseDbOject;
        HttpSession session = request.getSession();

        boolean skip = WebParamUtil.getBooleanValue(_SiteRegPaymentInfo.getSkip());
        
        if (!skip){

            if ( WebUtil.isNull(_SiteRegPaymentInfo.getFirstName())){
                m_logger.debug("firstName is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("First Name is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getLastName())){
                m_logger.debug("lastName is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Last Name is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getAddress1())){
                m_logger.debug("address1 is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Address1 is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getCity())){
                m_logger.debug("city is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("City is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getCountryRegion())){
                m_logger.debug("countryRegion is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Country Region is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getStateProvince())){
                m_logger.debug("stateProvince is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("State Province is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getZip())){
                m_logger.debug("zip is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Zip is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getPhone())){
                m_logger.debug("phone is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Phone is missing.");
            }
            
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getPaymentType())){
                m_logger.debug("paymentType is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Payment Type is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getCardType())){
                m_logger.debug("cardType is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Card Type is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getPaymentNum())){
                m_logger.debug("paymentNum is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Payment Num is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getExpireMonth())){
                m_logger.debug("expireMonth is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Expire Month is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getExpireYear())){
                m_logger.debug("expireYear is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Expire Year is missing.");
            }
            if ( WebUtil.isNull(_SiteRegPaymentInfo.getCcv())){
                m_logger.debug("ccv is missing in this requres " + SiteRegPaymentInfoDS.objectToString(_SiteRegPaymentInfo));
                throw new Exception("Ccv is missing.");
            }
        }

        SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute(SiteRegStore.getSessionKey());
        if (siteRegStore == null) {
            m_logger.info("Site Reg Store is missing in session. Forwarding the site to start page");
            throw new ActionExtentException("session expires. Please start over the registration", "site_reg_start");
        }
        
        siteRegStore.setPayInfo(_SiteRegPaymentInfo);
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SiteRegPaymentInfoAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SiteRegPaymentInfo _SiteRegPaymentInfo = (SiteRegPaymentInfo)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegPaymentInfoAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SiteRegPaymentInfo _SiteRegPaymentInfo = (SiteRegPaymentInfo)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegPaymentInfoAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SiteRegPaymentInfo _SiteRegPaymentInfo = (SiteRegPaymentInfo)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegPaymentInfoAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SiteRegPaymentInfo _SiteRegPaymentInfo = (SiteRegPaymentInfo)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegPaymentInfoAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SiteRegPaymentInfo _SiteRegPaymentInfo = (SiteRegPaymentInfo)baseDbOject;
    }

	private SiteRegPaymentInfoDS m_ds = SiteRegPaymentInfoDS.getInstance();
    private static Logger m_logger = Logger.getLogger( SiteRegPaymentInfoActionExtent.class);
    
}
