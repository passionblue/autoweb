package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.BlogConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.BlogConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.BlogConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class BlogConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogConfigAction#xtent.beforeAdd");		
        BlogConfig _BlogConfig = (BlogConfig)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#BlogConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        BlogConfig _BlogConfig = (BlogConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        BlogConfig _BlogConfig = (BlogConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        BlogConfig _BlogConfig = (BlogConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        BlogConfig _BlogConfig = (BlogConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        BlogConfig _BlogConfig = (BlogConfig)baseDbOject;
    }

	private BlogConfigDS m_ds = BlogConfigDS.getInstance();
    private static Logger m_logger = Logger.getLogger( BlogConfigActionExtent.class);
    
}
