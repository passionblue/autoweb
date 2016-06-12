package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.BlogCategory;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.BlogCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.BlogCategoryForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class BlogCategoryActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCategoryAction#xtent.beforeAdd");		
        BlogCategory _BlogCategory = (BlogCategory)baseDbOject;

        if ( WebUtil.isNull(_BlogCategory.getTitle())){
            m_logger.debug("title is missing in this requres " + BlogCategoryDS.objectToString(_BlogCategory));
            throw new Exception("Title is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#BlogCategoryAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        BlogCategory _BlogCategory = (BlogCategory)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCategoryAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        BlogCategory _BlogCategory = (BlogCategory)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCategoryAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        BlogCategory _BlogCategory = (BlogCategory)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCategoryAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        BlogCategory _BlogCategory = (BlogCategory)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCategoryAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        BlogCategory _BlogCategory = (BlogCategory)baseDbOject;
    }

	private BlogCategoryDS m_ds = BlogCategoryDS.getInstance();
    private static Logger m_logger = Logger.getLogger( BlogCategoryActionExtent.class);
    
}
