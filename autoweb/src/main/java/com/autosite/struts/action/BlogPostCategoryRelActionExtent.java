package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.BlogPostCategoryRel;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.BlogPostCategoryRelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.BlogPostCategoryRelForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class BlogPostCategoryRelActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostCategoryRelAction#xtent.beforeAdd");		
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#BlogPostCategoryRelAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostCategoryRelAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostCategoryRelAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostCategoryRelAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogPostCategoryRelAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel)baseDbOject;
    }

	private BlogPostCategoryRelDS m_ds = BlogPostCategoryRelDS.getInstance();
    private static Logger m_logger = Logger.getLogger( BlogPostCategoryRelActionExtent.class);
    
}
