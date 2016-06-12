package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.EcCategory;
import com.autosite.ds.EcCategoryDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebUtil;

public class EcCategoryActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCategoryAction#xtent.beforeAdd");		
        EcCategory _EcCategory = (EcCategory)baseDbOject;
        
        long pageId = _EcCategory.getPageId();
        EcCategory cat =EcCategoryDS.getInstance().getObjectByPageId(pageId);
        
        if (cat != null) {
            m_logger.error("Category already set for this page." + pageId + " category name=" + cat.getCategoryName());
            throw new Exception("Category " + cat.getCategoryName() + " already set for this page");
        }
        
        if ( WebUtil.isNull(_EcCategory.getCategoryName())){
            throw new Exception("Category Name is required");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcCategoryAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcCategory _EcCategory = (EcCategory)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCategoryAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcCategory _EcCategory = (EcCategory)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCategoryAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcCategory _EcCategory = (EcCategory)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCategoryAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcCategory _EcCategory = (EcCategory)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcCategoryAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcCategory _EcCategory = (EcCategory)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( EcCategoryActionExtent.class);
    
}
