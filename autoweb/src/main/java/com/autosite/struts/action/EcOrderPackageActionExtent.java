package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcOrderPackage;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcOrderPackageDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcOrderPackageForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcOrderPackageActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderPackageAction#xtent.beforeAdd");		
        EcOrderPackage _EcOrderPackage = (EcOrderPackage)baseDbOject;

        if ( WebUtil.isNull(_EcOrderPackage.getOrderId())){
            m_logger.debug("orderId is missing in this requres " + EcOrderPackageDS.objectToString(_EcOrderPackage));
            throw new Exception("orderId is missing.");
        }
        if ( WebUtil.isNull(_EcOrderPackage.getNumOrder())){
            m_logger.debug("numOrder is missing in this requres " + EcOrderPackageDS.objectToString(_EcOrderPackage));
            throw new Exception("numOrder is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcOrderPackageAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcOrderPackage _EcOrderPackage = (EcOrderPackage)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderPackageAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcOrderPackage _EcOrderPackage = (EcOrderPackage)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderPackageAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcOrderPackage _EcOrderPackage = (EcOrderPackage)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderPackageAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcOrderPackage _EcOrderPackage = (EcOrderPackage)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderPackageAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcOrderPackage _EcOrderPackage = (EcOrderPackage)baseDbOject;
    }

	private EcOrderPackageDS m_ds = EcOrderPackageDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcOrderPackageActionExtent.class);
    
}
