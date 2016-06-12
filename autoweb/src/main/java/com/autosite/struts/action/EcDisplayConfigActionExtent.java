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

import com.autosite.db.EcDisplayConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcDisplayConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcDisplayConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcDisplayConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcDisplayConfigAction#xtent.beforeAdd");		
        EcDisplayConfig _EcDisplayConfig = (EcDisplayConfig)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcDisplayConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcDisplayConfig _EcDisplayConfig = (EcDisplayConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcDisplayConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcDisplayConfig _EcDisplayConfig = (EcDisplayConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcDisplayConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcDisplayConfig _EcDisplayConfig = (EcDisplayConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcDisplayConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcDisplayConfig _EcDisplayConfig = (EcDisplayConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcDisplayConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcDisplayConfig _EcDisplayConfig = (EcDisplayConfig)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( EcDisplayConfigActionExtent.class);
    
}
