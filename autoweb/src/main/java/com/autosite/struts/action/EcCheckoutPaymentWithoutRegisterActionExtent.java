package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcCheckoutPaymentWithoutRegister;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcCheckoutPaymentWithoutRegisterDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcCheckoutPaymentWithoutRegisterForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcCheckoutPaymentWithoutRegisterActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutPaymentWithoutRegisterAction#xtent.beforeAdd");		
        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = (EcCheckoutPaymentWithoutRegister)baseDbOject;

        if ( WebUtil.isNull(_EcCheckoutPaymentWithoutRegister.getPaymentType())){
            m_logger.debug("paymentType is missing in this requres " + EcCheckoutPaymentWithoutRegisterDS.objectToString(_EcCheckoutPaymentWithoutRegister));
            throw new Exception("Payment Type is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutPaymentWithoutRegister.getCardType())){
            m_logger.debug("cardType is missing in this requres " + EcCheckoutPaymentWithoutRegisterDS.objectToString(_EcCheckoutPaymentWithoutRegister));
            throw new Exception("Card Type is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutPaymentWithoutRegister.getPaymentNum())){
            m_logger.debug("paymentNum is missing in this requres " + EcCheckoutPaymentWithoutRegisterDS.objectToString(_EcCheckoutPaymentWithoutRegister));
            throw new Exception("Payment Num is missing.");
        }
        if ( WebUtil.isNull(_EcCheckoutPaymentWithoutRegister.getCcv())){
            m_logger.debug("ccv is missing in this requres " + EcCheckoutPaymentWithoutRegisterDS.objectToString(_EcCheckoutPaymentWithoutRegister));
            throw new Exception("Ccv is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcCheckoutPaymentWithoutRegisterAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = (EcCheckoutPaymentWithoutRegister)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutPaymentWithoutRegisterAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = (EcCheckoutPaymentWithoutRegister)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutPaymentWithoutRegisterAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = (EcCheckoutPaymentWithoutRegister)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutPaymentWithoutRegisterAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = (EcCheckoutPaymentWithoutRegister)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCheckoutPaymentWithoutRegisterAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcCheckoutPaymentWithoutRegister _EcCheckoutPaymentWithoutRegister = (EcCheckoutPaymentWithoutRegister)baseDbOject;
    }

	private EcCheckoutPaymentWithoutRegisterDS m_ds = EcCheckoutPaymentWithoutRegisterDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcCheckoutPaymentWithoutRegisterActionExtent.class);
    
}
