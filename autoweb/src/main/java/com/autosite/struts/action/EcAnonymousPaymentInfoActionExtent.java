package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcAnonymousPaymentInfo;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcAnonymousPaymentInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcAnonymousPaymentInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcAnonymousPaymentInfoActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousPaymentInfoAction#xtent.beforeAdd");		
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcAnonymousPaymentInfoAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousPaymentInfoAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousPaymentInfoAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousPaymentInfoAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousPaymentInfoAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo)baseDbOject;
    }

	private EcAnonymousPaymentInfoDS m_ds = EcAnonymousPaymentInfoDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcAnonymousPaymentInfoActionExtent.class);
    
}
