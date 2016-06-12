package com.autosite.util;

import com.autosite.db.LinkStyleConfig;
import com.autosite.db.PageConfig;
import com.autosite.db.StyleConfig;
import com.jtrend.util.WebUtil;

public class StyleConfigUtil {
    
    public static final int STYLE_USE_DEFAULT = 0;
    public static final int STYLE_USE_CUSTOM = 1;
    

    public static StyleConfig getDefault(){
    
        StyleConfig ret = new StyleConfig();
        
        ret.setFontFamily("Verdana");
        ret.setFontSize("11");
        ret.setFontStyle("normal");
        ret.setFontVariant("normal");
        ret.setFontWeight("normal");
        ret.setBorderWidth("0");
        ret.setBorderColor("black");
        ret.setBorderStyle("solid");
        ret.setMargin("0px 0px 0px 0px");
        ret.setPadding("0px 0px 0px 0px");
        ret.setListStyleType("none");
        ret.setListStylePosition("inside");
        
        return ret;
    }

    public static LinkStyleConfig getDefaultLinkStyle(){
        
        LinkStyleConfig ret = new LinkStyleConfig();

        ret.setHeight("");           
        ret.setWidth("");           
        ret.setDisplay("block");           
        ret.setBorder("0px dotted blue");           
        ret.setBackground("white none inherit inherit inherit");           
        ret.setColor("#909090");           
        ret.setTextDecoration("none");           
        ret.setTextAlign("center");           
        ret.setVerticalAlign("");           
        ret.setTextIndent("");        
        ret.setMargin("0px 0px 0px 0px");
        ret.setPadding("5px 15px 5px 15px");
        ret.setExtraStyle("");           
        ret.setHovHeight("");           
        ret.setHovWidth("");           
        ret.setHovDisplay("block");           
        ret.setHovBorder("0px dotted blue");           
        ret.setHovBackground("white none inherit inherit inherit");           
        ret.setHovColor("#909090");           
        ret.setHovTextDecoration("");           
        ret.setHovTextAlign("center");           
        ret.setHovVerticalAlign("");           
        ret.setHovTextIndent("");           
        ret.setHovMargin("0");
        ret.setHovPadding("5px 15px 5px 15px");
        ret.setHovExtraStyle("");
        
        return ret;
    }
    
    
    /*
     * Config key is set from SiteConfig and used for H/V menu CustomScriptsServlet. see MlddMenuStyleUtil.java too.
     */
    public static String getDefaultStyleKey(long siteId){
        return "SiteDefault:" + siteId;
    }
    public static String getDefaultLinkStyleKey(long siteId){
        return "SiteDefaultLink:"+siteId;
    }
    public static String getDefaultHorizontalMenuStyleKey(long siteId){
        return "SiteDefaulHMenu:" + siteId;
    }
    public static String getDefaultVerticalMenuStyleKey(long siteId){
        return "SiteDefaultVMenu:"+siteId;
    }
    
    
    public static String getPageImports(PageConfig pageConfig){
        
        StringBuffer buf = new StringBuffer();
        
        if ( pageConfig == null) return ""; 
        
        if (!WebUtil.isNull(pageConfig.getPageCssImports())){
            String cssImportString = pageConfig.getPageCssImports();
            String imports[] = cssImportString.split(",");
            for (int i = 0; i < imports.length; i++) {
                String str = imports[i].trim();
                buf.append("<link rel=\"stylesheet\" type=\"text/css\"  href=\"" + str +"\" />").append("\n");
            }
        }
        
        if (!WebUtil.isNull(pageConfig.getPageScriptImports())){
            

            String scriptImportString = pageConfig.getPageCssImports();
            String imports[] = scriptImportString.split(",");
            for (int i = 0; i < imports.length; i++) {
                String str = imports[i].trim();
                if ( str != null & !str.equals(""))
                    buf.append("<script type=\"text/javascript\" src=\"" + str +"\"></script>").append("\n");
            }
        }
        
        return buf.toString();
    }
    

    // Default Content Styles
    
    public static StyleConfig getDefaultContentDataStyle(){
        
        StyleConfig ret = new StyleConfig();
        
        ret.setFontFamily("Verdana");
        ret.setFontSize("11");
        ret.setFontStyle("normal");
        ret.setFontVariant("normal");
        ret.setFontWeight("normal");
        ret.setBorderWidth("0");
        ret.setBorderColor("black");
        ret.setBorderStyle("solid");
        ret.setMargin("0px 0px 0px 0px");
        ret.setPadding("0px 0px 0px 0px");
        ret.setListStyleType("none");
        ret.setListStylePosition("inside");
        
        return ret;
    }    
    
    public static StyleConfig getDefaultContentSubjectStyle(){
        
        StyleConfig ret = new StyleConfig();
        
        ret.setFontFamily("Verdana");
        ret.setFontSize("11");
        ret.setFontStyle("normal");
        ret.setFontVariant("normal");
        ret.setFontWeight("normal");
        ret.setBorderWidth("0");
        ret.setBorderColor("black");
        ret.setBorderStyle("solid");
        ret.setMargin("0px 0px 0px 0px");
        ret.setPadding("0px 0px 0px 0px");
        ret.setListStyleType("none");
        ret.setListStylePosition("inside");
        
        return ret;
    }    
    
    public static String getContListSubjectKey(long siteId){
        return "__STYLE_CONTENT_LIST_SUBJECT-" + siteId;
    }

    public static String getContListDataKey(long siteId){
        return "__STYLE_CONTENT_LIST_DATA-" + siteId;
    }

    public static String getContSingleSubjectKey(long siteId){
        return "__STYLE_CONTENT_SINGLE_PAGE_SUBJECT-" + siteId;
    }

    public static String getContSingleDataKey(long siteId){
        return "__STYLE_CONTENT_SINGLE_PAGE_DATA-" + siteId;
    }
    
}
