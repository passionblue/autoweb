package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcCheckoutAccountInfo;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.EmailSubs;
import com.autosite.ds.EcCheckoutAccountInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcCheckoutAccountInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.EmailSubscriptionUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebUtil;

public class EcCheckoutAccountInfoActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutAccountInfoAction#xtent.beforeAdd");		
        EcCheckoutAccountInfo _EcCheckoutAccountInfo = (EcCheckoutAccountInfo)baseDbOject;

        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getEmail())){
            m_logger.debug("email is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("email is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getEmailRetype())){
            m_logger.debug("emailRetype is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("emailRetype is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getFirstName())){
            m_logger.debug("firstName is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("firstName is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getLastName())){
            m_logger.debug("lastName is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("lastName is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getAddress1())){
            m_logger.debug("address1 is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("address1 is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getCity())){
            m_logger.debug("city is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("city is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getCountryRegion())){
            m_logger.debug("countryRegion is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("countryRegion is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getStateProvince())){
            m_logger.debug("stateProvince is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("stateProvince is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getZip())){
            m_logger.debug("zip is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("zip is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutAccountInfo.getPhone())){
            m_logger.debug("phone is missing in this requres " + EcCheckoutAccountInfoDS.objectToString(_EcCheckoutAccountInfo));
            throw new Exception("phone is missing.");
        }
        
        //========================= Extra Validation ==========================================================
        
        if (!_EcCheckoutAccountInfo.getEmail().equals(_EcCheckoutAccountInfo.getEmailRetype())){
            m_logger.debug("Emails do not match. please re-enter. em=" +_EcCheckoutAccountInfo.getEmail() + ", re=" + _EcCheckoutAccountInfo.getEmailRetype());
            throw new Exception("Emails do not match. please re-enter");
        }
        
        if (_EcCheckoutAccountInfo.getTermAgree() == 0 ) {
            m_logger.debug("Term agree not checked");
            throw new Exception("Please check the term agreement");
        }

        //========================= Extra Validation ==========================================================
        if (_EcCheckoutAccountInfo.getSubsEmail() == 1 ) {
            m_logger.debug("Email has been listed as a sub. email " + _EcCheckoutAccountInfo.getEmail());
            EmailSubscriptionUtil.registerEmailForSubscription(_EcCheckoutAccountInfo.getSiteId(), _EcCheckoutAccountInfo.getFirstName(),
                                                               _EcCheckoutAccountInfo.getLastName(), _EcCheckoutAccountInfo.getEmail(), "Anonymous Shopper");
        } 
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcCheckoutAccountInfoAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcCheckoutAccountInfo _EcCheckoutAccountInfo = (EcCheckoutAccountInfo)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutAccountInfoAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcCheckoutAccountInfo _EcCheckoutAccountInfo = (EcCheckoutAccountInfo)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutAccountInfoAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcCheckoutAccountInfo _EcCheckoutAccountInfo = (EcCheckoutAccountInfo)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutAccountInfoAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcCheckoutAccountInfo _EcCheckoutAccountInfo = (EcCheckoutAccountInfo)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutAccountInfoAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcCheckoutAccountInfo _EcCheckoutAccountInfo = (EcCheckoutAccountInfo)baseDbOject;
    }

	private EcCheckoutAccountInfoDS m_ds = EcCheckoutAccountInfoDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcCheckoutAccountInfoActionExtent.class);
    
}
