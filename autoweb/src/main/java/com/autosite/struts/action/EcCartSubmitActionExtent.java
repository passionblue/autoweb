package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.EcProduct;
import com.autosite.struts.action.core.AutositeActionExtent;

public class EcCartSubmitActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCartSubmitActionExtent#xtent.beforeAdd");		
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcCartSubmitActionExtent.afterAdd. id=" + baseDbOject.getId());		
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCartSubmitActionExtent.beforeUpdate. id=" + baseDbOject.getId());		
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCartSubmitActionExtent#xtent.afterUpdate. id=" + baseDbOject.getId());		
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCartSubmitActionExtent#xtent.beforeDelete. id=" + baseDbOject.getId());		
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCartSubmitActionExtent#xtent.afterDelete. id=" + baseDbOject.getId());		
    }

    private static Logger m_logger = Logger.getLogger( EcCartSubmitActionExtent.class);
    
}
