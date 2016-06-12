package com.autosite.viewproc;

import org.apache.log4j.Logger;

import com.autosite.db.Page;
import com.autosite.db.PageStaticAlt;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.ds.PageDS;
import com.autosite.ds.PageStaticAltDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;
import com.jtrend.struts.core.ViewExtent;
import com.jtrend.util.WebUtil;

public class ViewExtentProvider implements ViewExtent{

    protected SiteConfigDS m_siteConfig = SiteConfigDS.getInstance();
    protected PageStaticAltDS m_staticAlt = PageStaticAltDS.getInstance();
    public ViewExtentProvider() {
       
    }

    public String getAlternateUrl(String alias, String server) {
        m_logger.debug("Looking for alt URL for " + alias + " site " + server);
        if (server == null) return null;
        
        Site site = SiteDS.getInstance().getSiteByUrl(server);
        if ( site == null) return null;

        
        PageStaticAlt alt = m_staticAlt.getBySiteIdPageAlias(site.getId(), alias);
        
        if (alt == null || WebUtil.isNull(alt.getPageUrl())){
            return null;
        }

        m_logger.debug("View alter URL find " + alt.getPageUrl());
        return alt.getPageUrl();
    }    
    
    // This is static extention defined in view file. 
    // Extension is defined by having a period separate name  e.g. home.xxxx
    // Currently only used for home.
    public String getAlternateFor(String alias, String server) {
        
        m_logger.debug("Looking for alt for " + alias + " site " + server);
        
        if (server == null) return null;
        
        Site site = SiteDS.getInstance().getSiteByUrl(server);
        if ( site == null) {
            m_logger.debug("Site not found by " + server);
            return null;
        }
        
        SiteConfig siteConfig = m_siteConfig.getSiteConfigBySiteId(site.getId());

        // In case Home is set to dynamic page, now get underlying alias for dynamic config.

        if ( siteConfig != null && WebUtil.isTrue(siteConfig.getUnderlyingDynamicPage())){
            m_logger.debug("getUnderlyingDynamicPage=" + siteConfig.getUnderlyingDynamicPage());
            
            Page page = PageDS.getInstance().getBySiteIdPageName(site.getId(), "XHOME");
            if (page == null || WebUtil.isNull(page.getUnderlyingPage())){
                
                return "dynamic_menu_content";
            } else {
                return page.getUnderlyingPage();
            }
        }
        
        String underlyingHome = (siteConfig == null? null: siteConfig.getUnderlyingHome());
        
        if (underlyingHome == null || underlyingHome.isEmpty()){
            return null;
        }

        m_logger.debug("View alter find " + underlyingHome);
        return underlyingHome;
    }

    public String getSiteName() {
        return "Autosite";
    }
    
    private static Logger m_logger = Logger.getLogger(ViewExtentProvider.class);
}
