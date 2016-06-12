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

import com.autosite.db.ContentConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ContentConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ContentConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class ContentConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentConfigAction#xtent.beforeAdd");		
        ContentConfig _ContentConfig = (ContentConfig)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ContentConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ContentConfig _ContentConfig = (ContentConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ContentConfig _ContentConfig = (ContentConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ContentConfig _ContentConfig = (ContentConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ContentConfig _ContentConfig = (ContentConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ContentConfig _ContentConfig = (ContentConfig)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private ContentConfigDS m_ds = ContentConfigDS.getInstance();
    private static Logger m_logger = Logger.getLogger( ContentConfigActionExtent.class);
    
}
