package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PageStaticConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PageStaticConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PageStaticConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class PageStaticConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticConfigAction#xtent.beforeAdd");		
        PageStaticConfig _PageStaticConfig = (PageStaticConfig)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PageStaticConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PageStaticConfig _PageStaticConfig = (PageStaticConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PageStaticConfig _PageStaticConfig = (PageStaticConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PageStaticConfig _PageStaticConfig = (PageStaticConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PageStaticConfig _PageStaticConfig = (PageStaticConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PageStaticConfig _PageStaticConfig = (PageStaticConfig)baseDbOject;
    }

	private PageStaticConfigDS m_ds = PageStaticConfigDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PageStaticConfigActionExtent.class);
    
}
