package com.autosite.util;

import com.autosite.db.SiteHeader;
import com.jtrend.util.WebUtil;

public class SiteHeaderUtil {
    
    public static final int SITE_HEADER_DEFAULT = 0;
    public static final int SITE_HEADER_SCRIPT_INCLUDE = 0;
    public static final int SITE_HEADER_SCRIPT_TEXT = 0;
    public static final int SITE_HEADER_STYLE_INCLUDE = 0;
    public static final int SITE_HEADER_STYLE_TEXT = 0;
    
    public static String display(SiteHeader siteHeader){

        if (siteHeader == null) return "";
        
        if (siteHeader.getAsIs() == 1) return WebUtil.display(siteHeader.getIncludeText());
        
        if ( siteHeader.getIncludeType() == SITE_HEADER_SCRIPT_INCLUDE ) {

            return "<script type=\"text/javascript\" src=\"" + siteHeader.getIncludeText() + "\"> </script>";
        } else if ( siteHeader.getIncludeType() == SITE_HEADER_SCRIPT_TEXT ) {
            return "<script type=\"text/javascript\">" +  siteHeader.getIncludeText() + "</script>";
        } else if ( siteHeader.getIncludeType() == SITE_HEADER_STYLE_INCLUDE ) {
            return "<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"" +siteHeader.getIncludeText() + "\"/>";
        } else if ( siteHeader.getIncludeType() == SITE_HEADER_STYLE_TEXT ) {
            return "<style type=\"text/css\">" +siteHeader.getIncludeText() + "</style>"; 
        }
        
        return WebUtil.display(siteHeader.getIncludeText());
    }
}
