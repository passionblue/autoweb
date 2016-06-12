package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.SiteFeatureConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.SiteFeatureConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.SiteFeatureConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class SiteFeatureConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteFeatureConfigAction#xtent.beforeAdd");		
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SiteFeatureConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteFeatureConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteFeatureConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteFeatureConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteFeatureConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig)baseDbOject;
    }

	private SiteFeatureConfigDS m_ds = SiteFeatureConfigDS.getInstance();
    private static Logger m_logger = Logger.getLogger( SiteFeatureConfigActionExtent.class);
    
}
