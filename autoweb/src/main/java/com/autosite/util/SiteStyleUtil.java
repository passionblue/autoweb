package com.autosite.util;

import org.apache.log4j.Logger;

import com.autosite.db.SiteConfigStyle;
import com.autosite.db.StyleTheme;
import com.autosite.ds.SiteConfigStyleDS;
import com.autosite.ds.StyleThemeDS;

@Deprecated

public class SiteStyleUtil {
    
    public static StyleTheme getTheme(long siteId){
        SiteConfigStyle siteStyle = SiteConfigStyleDS.getInstance().getObjectBySiteId(siteId);
        if (siteStyle!= null) {
            return StyleThemeDS.getInstance().getById(siteStyle.getThemeId());
        }
        return null;
    }
    
    private static Logger m_logger = Logger.getLogger(SiteStyleUtil.class);
}
