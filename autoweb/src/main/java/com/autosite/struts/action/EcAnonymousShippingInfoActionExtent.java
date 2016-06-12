package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcAnonymousShippingInfo;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcAnonymousShippingInfoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcAnonymousShippingInfoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcAnonymousShippingInfoActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousShippingInfoAction#xtent.beforeAdd");		
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcAnonymousShippingInfoAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousShippingInfoAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousShippingInfoAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousShippingInfoAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousShippingInfoAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo)baseDbOject;
    }

	private EcAnonymousShippingInfoDS m_ds = EcAnonymousShippingInfoDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcAnonymousShippingInfoActionExtent.class);
    
}
