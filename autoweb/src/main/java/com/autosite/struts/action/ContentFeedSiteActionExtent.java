package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.ContentFeedSite;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ContentFeedSiteDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ContentFeedSiteForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class ContentFeedSiteActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedSiteAction#xtent.beforeAdd");		
        ContentFeedSite _ContentFeedSite = (ContentFeedSite)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ContentFeedSiteAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ContentFeedSite _ContentFeedSite = (ContentFeedSite)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedSiteAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ContentFeedSite _ContentFeedSite = (ContentFeedSite)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedSiteAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ContentFeedSite _ContentFeedSite = (ContentFeedSite)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedSiteAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ContentFeedSite _ContentFeedSite = (ContentFeedSite)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedSiteAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ContentFeedSite _ContentFeedSite = (ContentFeedSite)baseDbOject;
    }

	private ContentFeedSiteDS m_ds = ContentFeedSiteDS.getInstance();
    private static Logger m_logger = Logger.getLogger( ContentFeedSiteActionExtent.class);
    
}
