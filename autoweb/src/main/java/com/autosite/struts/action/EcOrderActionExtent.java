package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcOrder;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcOrderDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcOrderForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcOrderActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderAction#xtent.beforeAdd");		
        EcOrder _EcOrder = (EcOrder)baseDbOject;

        if ( WebUtil.isNull(_EcOrder.getOrderNum())){
            m_logger.debug("orderNum is missing in this requres " + EcOrderDS.objectToString(_EcOrder));
            throw new Exception("Order Num is missing.");
        }
        if ( WebUtil.isNull(_EcOrder.getOrderStatus())){
            m_logger.debug("orderStatus is missing in this requres " + EcOrderDS.objectToString(_EcOrder));
            throw new Exception("Order Status is missing.");
        }
        if ( WebUtil.isNull(_EcOrder.getOrderTotal())){
            m_logger.debug("orderTotal is missing in this requres " + EcOrderDS.objectToString(_EcOrder));
            throw new Exception("Order Total is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcOrderAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcOrder _EcOrder = (EcOrder)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcOrder _EcOrder = (EcOrder)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcOrder _EcOrder = (EcOrder)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcOrder _EcOrder = (EcOrder)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcOrderAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcOrder _EcOrder = (EcOrder)baseDbOject;
    }

	private EcOrderDS m_ds = EcOrderDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcOrderActionExtent.class);
    
}
