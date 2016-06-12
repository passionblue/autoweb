package com.autosite.struts.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PageContentConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PageContentConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PageContentConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class PageContentConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageContentConfigAction#xtent.beforeAdd");		
        PageContentConfig _PageContentConfig = (PageContentConfig)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PageContentConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PageContentConfig _PageContentConfig = (PageContentConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageContentConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PageContentConfig _PageContentConfig = (PageContentConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageContentConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PageContentConfig _PageContentConfig = (PageContentConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageContentConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PageContentConfig _PageContentConfig = (PageContentConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageContentConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PageContentConfig _PageContentConfig = (PageContentConfig)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private PageContentConfigDS m_ds = PageContentConfigDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PageContentConfigActionExtent.class);
    
}
