package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.ContentCategory;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ContentCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ContentCategoryForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class ContentCategoryActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentCategoryAction#xtent.beforeAdd");		
        ContentCategory _ContentCategory = (ContentCategory)baseDbOject;

        if ( WebUtil.isNull(_ContentCategory.getCategory())){
            m_logger.debug("category is missing in this requres " + ContentCategoryDS.objectToString(_ContentCategory));
            throw new Exception("Category is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ContentCategoryAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ContentCategory _ContentCategory = (ContentCategory)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentCategoryAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ContentCategory _ContentCategory = (ContentCategory)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentCategoryAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ContentCategory _ContentCategory = (ContentCategory)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentCategoryAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ContentCategory _ContentCategory = (ContentCategory)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentCategoryAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ContentCategory _ContentCategory = (ContentCategory)baseDbOject;
    }

	private ContentCategoryDS m_ds = ContentCategoryDS.getInstance();
    private static Logger m_logger = Logger.getLogger( ContentCategoryActionExtent.class);
    
}
