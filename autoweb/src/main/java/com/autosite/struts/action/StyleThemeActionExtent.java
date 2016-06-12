package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.SiteConfig;
import com.autosite.db.SiteConfigStyle;
import com.autosite.db.StyleTheme;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteConfigStyleDS;
import com.autosite.ds.StyleThemeDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.StyleThemeUtil;
import com.jtrend.util.WebUtil;

public class StyleThemeActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#StyleThemeAction#xtent.beforeAdd");        
        StyleTheme _StyleTheme = (StyleTheme)baseDbOject;

        if ( WebUtil.isNull(_StyleTheme.getTitle())){
            m_logger.debug("title is missing in this requres " + StyleThemeDS.objectToString(_StyleTheme));
            throw new ActionExtentException("Title is missing.", "");
        }


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
        m_logger.debug("#StyleThemeAction#xtent.afterAdd. id=" + baseDbOject.getId());      
        StyleTheme _StyleTheme = (StyleTheme)baseDbOject;

    
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#StyleThemeAction#xtent.beforeUpdate. id=" + baseDbOject.getId());      
        StyleTheme _StyleTheme = (StyleTheme)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#StyleThemeAction#xtent.afterUpdate. id=" + baseDbOject.getId());       
        StyleTheme _StyleTheme = (StyleTheme)baseDbOject;
    
        // If this theme is associated with any site, should update site config. 
        
        List siteStyleConfigs = SiteConfigStyleDS.getInstance().getAll();
        
        for (Iterator iterator = siteStyleConfigs.iterator(); iterator.hasNext();) {
            SiteConfigStyle siteConfigStyle = (SiteConfigStyle) iterator.next();
            
            if ( siteConfigStyle.getThemeId() == _StyleTheme.getId()){
                SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(siteConfigStyle.getSiteId());
                StyleThemeUtil.updateSiteConfig(_StyleTheme, siteConfig);
            }
        }
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#StyleThemeAction#xtent.beforeDelete. id=" + baseDbOject.getId());      
        StyleTheme _StyleTheme = (StyleTheme)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#StyleThemeAction#xtent.afterDelete. id=" + baseDbOject.getId());       
        StyleTheme _StyleTheme = (StyleTheme)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map ret = new HashMap();
        return ret;
    }

    private StyleThemeDS m_ds = StyleThemeDS.getInstance();
    private static Logger m_logger = Logger.getLogger( StyleThemeActionExtent.class);
    
}
