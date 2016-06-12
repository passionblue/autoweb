package com.autosite.theme.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.ThemeAggregator;
import com.autosite.db.ThemeStyles;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.ThemeStylesDS;
import com.autosite.theme.AutositeThemeService;

public class AutositeThemeServiceImpl implements AutositeThemeService {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeThemeServiceImpl.class);
    
    @Override
    public void installTheme(Site site, ThemeAggregator themeAggregator) {
        installTheme(site, themeAggregator, false);
    }

    @Override
    public void installTheme(Site site, ThemeAggregator themeAggregator, boolean initialSetup) {

        if ( site == null || themeAggregator == null) {
            m_logger.warn("siet or aggregator is null, returning");
            return;
        }
        
        ThemeStyles themeStyles = ThemeStylesDS.getInstance().getById(themeAggregator.getThemeStyleId());

        if ( themeStyles == null){
            m_logger.warn("ThemeStyles is null, doing nothing. " + themeAggregator.getThemeStyleId());
            return;
        }
        
        SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
        siteConfig.setWidth(themeStyles.getBodyWidth());
        
        SiteConfigDS.getInstance().update(siteConfig);

        
        if ( initialSetup ) {
            
        }

        m_logger.info("Theme installation succeeded for site " + site.getSiteUrl() + " with them " + themeAggregator.getThemeName());
    }

    @Override
    public void uninstallTheme(Site site, ThemeAggregator themeAggregator) {
        if ( site == null || themeAggregator == null) {
            m_logger.info("siet or aggregator is null, returning");
            return;
        }
        ThemeStyles themeStyles = ThemeStylesDS.getInstance().getById(themeAggregator.getThemeStyleId());
    }

    
    private void installInitialSetup(){
        //Pages
        
        
        
    }
}
