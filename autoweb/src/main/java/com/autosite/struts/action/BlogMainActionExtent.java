package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.BlogMain;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.BlogMainDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.BlogMainForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class BlogMainActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogMainAction#xtent.beforeAdd");		
        BlogMain _BlogMain = (BlogMain)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#BlogMainAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        BlogMain _BlogMain = (BlogMain)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogMainAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        BlogMain _BlogMain = (BlogMain)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogMainAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        BlogMain _BlogMain = (BlogMain)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogMainAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        BlogMain _BlogMain = (BlogMain)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogMainAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        BlogMain _BlogMain = (BlogMain)baseDbOject;
    }

	private BlogMainDS m_ds = BlogMainDS.getInstance();
    private static Logger m_logger = Logger.getLogger( BlogMainActionExtent.class);
    
}
