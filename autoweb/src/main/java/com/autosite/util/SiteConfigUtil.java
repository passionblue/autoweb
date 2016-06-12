package com.autosite.util;

import java.util.Map;

import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.theme.DefaultThemeManager;
import com.jtrend.util.WebUtil;

public class SiteConfigUtil {
    
    public static SiteConfig getDefaultSiteConfig() {
        
        DefaultThemeManager themeManager = DefaultThemeManager.getInstance();
        
        Map fields = themeManager.getDefaultThemeFields();
        
        SiteConfig config = new SiteConfig();

//        config.setColorBorders("#ffffff");
//        config.setColorSubmenuBg("#e1ebfd");
//        config.setHomeColCount(5);
//        config.setWidth(1000);
//        config.setMenuWidth(125);
//        config.setAdWidth(125);
//        config.setMenuReverse(0);
//        config.setMidColumnWidth(250);
//        config.setShowMidColumn(0);
//        config.setShowMenuColumn(1);
//        config.setShowAdColumn(1);
//
//        config.setShowTopMenu(1);
//
//        config.setShowHomeMenu(1);
//        config.setShowHomeMid(0);
//        config.setShowHomeAd(1);
//
//        config.setUseWysiwygContent(1);
//        config.setUseWysiwygPost(0);
//        
//        config.setBackgroundColor("#ffffff");
//        
//        config.setFrontDisplayFeed(1);

        config.setColorBorders("#ffffff");
        config.setColorSubmenuBg("#e1ebfd");
        config.setHomeColCount(5);
        config.setWidth(1000);
        config.setMenuWidth(125);
        config.setAdWidth(125);
        config.setMenuReverse(0);
        config.setMidColumnWidth(250);
        config.setShowMidColumn(0);
        config.setShowMenuColumn(1);
        config.setShowAdColumn(1);

        config.setShowTopMenu(1);

        config.setShowHomeMenu(1);
        config.setShowHomeMid(0);
        config.setShowHomeAd(1);

        config.setUseWysiwygContent(1);
        config.setUseWysiwygPost(0);
        
        config.setBackgroundColor("#ffffff");
        
        config.setFrontDisplayFeed(1);
        config.setUnderlyingDynamicPage(1);
        
        return config;
    }
    
    
    // If mysite.basesite.com, it returns "mysite" only for the child site. for the base site, returns null;
    public static String getSitePrefixForSubsite(Site site) {
        
        if (WebUtil.isNull(site.getSiteRegisterSite())) {
            return null;
        }
        
        String siteUrl = site.getSiteUrl();
        String baseUrl = site.getSiteRegisterSite();
        
        return siteUrl.replaceAll("."+baseUrl, "");
    }
}
