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

import com.autosite.db.PanelPageConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PanelPageConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PanelPageConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class PanelPageConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelPageConfigAction#xtent.beforeAdd");		
        PanelPageConfig _PanelPageConfig = (PanelPageConfig)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PanelPageConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PanelPageConfig _PanelPageConfig = (PanelPageConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelPageConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PanelPageConfig _PanelPageConfig = (PanelPageConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelPageConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PanelPageConfig _PanelPageConfig = (PanelPageConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelPageConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PanelPageConfig _PanelPageConfig = (PanelPageConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelPageConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PanelPageConfig _PanelPageConfig = (PanelPageConfig)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( PanelPageConfigActionExtent.class);
    
}
