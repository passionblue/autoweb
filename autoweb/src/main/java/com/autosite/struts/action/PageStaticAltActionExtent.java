package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PageStaticAlt;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PageStaticAltDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PageStaticAltForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class PageStaticAltActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticAltAction#xtent.beforeAdd");		
        PageStaticAlt _PageStaticAlt = (PageStaticAlt)baseDbOject;

        if ( WebUtil.isNull(_PageStaticAlt.getPageAlias())){
            m_logger.debug("pageAlias is missing in this requres " + PageStaticAltDS.objectToString(_PageStaticAlt));
            throw new Exception("Page Alias is missing.");
        }
        if ( WebUtil.isNull(_PageStaticAlt.getPageUrl())){
            m_logger.debug("pageUrl is missing in this requres " + PageStaticAltDS.objectToString(_PageStaticAlt));
            throw new Exception("Page Url is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PageStaticAltAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PageStaticAlt _PageStaticAlt = (PageStaticAlt)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticAltAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PageStaticAlt _PageStaticAlt = (PageStaticAlt)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticAltAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PageStaticAlt _PageStaticAlt = (PageStaticAlt)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticAltAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PageStaticAlt _PageStaticAlt = (PageStaticAlt)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStaticAltAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PageStaticAlt _PageStaticAlt = (PageStaticAlt)baseDbOject;
    }

	private PageStaticAltDS m_ds = PageStaticAltDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PageStaticAltActionExtent.class);
    
}
