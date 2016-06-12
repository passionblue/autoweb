package com.autosite.util;

import com.autosite.db.SiteConfig;
import com.autosite.db.SiteConfigStyle;
import com.autosite.db.StyleTheme;
import com.autosite.db.ThemeAggregator;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteConfigStyleDS;
import com.autosite.ds.ThemeAggregatorDS;

public class StyleThemeUtil {
    
    public static void updateSiteConfig(StyleTheme styleTheme, SiteConfig siteConfig){
        
        if ( siteConfig != null) {
            siteConfig.setBackgroundColor(styleTheme.getBodyBgColor());
            siteConfig.setBackgroundAttach(styleTheme.getBodyBgAttach());
            siteConfig.setBackgroundImage(styleTheme.getBodyBgImage());
            siteConfig.setBackgroundRepeat(styleTheme.getBodyBgRepeat());
            siteConfig.setBackgroundPosition(styleTheme.getBodyBgPosition());
            
            siteConfig.setContentBgColor(styleTheme.getContentBgColor());
            siteConfig.setAbsolutePosition(styleTheme.getUseAbsolute());
            siteConfig.setAbsoluteTop(styleTheme.getAbsoluteTop());
            siteConfig.setAbsoluteLeft(styleTheme.getAbsoluteLeft());
            
            SiteConfigDS.getInstance().update(siteConfig);
        } else {
            // TODO have to report. 
        }
        
    }
    
    public static SiteConfigStyle getSiteConfigStyleForSite(long siteid){
        return SiteConfigStyleDS.getInstance().getObjectBySiteId(siteid);
    }

    public static ThemeAggregator getThemeAggregatorForSite(long siteid){

        SiteConfigStyle s = getSiteConfigStyleForSite(siteid);
        if ( s!= null) {
            return ThemeAggregatorDS.getInstance().getById(s.getThemeId());
        }
        
        return null;
    }
    
    
}
