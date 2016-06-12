package com.autosite.servlet;

import org.apache.log4j.Logger;

import com.autosite.SiteConfigTransient;
import com.autosite.db.LinkStyleConfig;
import com.autosite.db.Panel;
import com.autosite.db.SiteConfig;
import com.autosite.db.SiteConfigStyle;
import com.autosite.db.StyleConfig;
import com.autosite.db.StyleTheme;
import com.autosite.ds.SiteConfigStyleDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.ds.StyleThemeDS;
import com.autosite.util.PanelUtil;
import com.autosite.util.SiteStyleUtil;
import com.jtrend.util.WebUtil;

public class StyleUtil {

    
    private static Logger m_logger = Logger.getLogger(StyleUtil.class);
    
    public static String makeBackgroundStyle(StyleConfig config){
        return makeBackgroundStyle(config.getBgColor(), config.getBgImage(), config.getBgRepeat(), config.getBgAttach(), config.getBgPosition());
    }    
    
    public static String makeBackgroundStyle(String color, String image, String repeat, String attach, String position){

        StringBuffer buf = new StringBuffer();
        boolean hasData = false;
        
        buf.append("background: ");

        if (color != null && color.length() >0 ) {
            buf.append(color).append(" ");;
            hasData = true;
        } else {
            buf.append("transparent ");
        }

        if (image != null && image.length() > 0 ) {
            buf.append("url(" + image + ") ");
            hasData = true;
        } 
        
        if (repeat != null && repeat.length() > 0 ) {
            buf.append(repeat).append(" ");
            hasData = true;
        } else {
            buf.append("no-repeat ");
        }
        
        if (attach != null && attach.length() > 0 ) {
            buf.append(attach).append(" ");
            hasData = true;
        } else {
            buf.append("scroll ");
        }
        
        if (position != null && position.length() > 0 ) {
            buf.append(position).append(" ");
            hasData = true;
        } else {
            buf.append("top left ");
        }
        
        if (!hasData) return "";
        else return buf.toString() + "; ";
    }

    public static String makeBorderStyle(StyleConfig config){
        if (config == null) return "";
        return makeBorderStyle(null, config.getBorderWidth(), config.getBorderStyle(), config.getBorderColor());
    }    
    
    public static String makeBorderStyle(String borderDirection, StyleConfig config){
        if (config == null) return "";
        return makeBorderStyle(borderDirection, config.getBorderWidth(), config.getBorderStyle(), config.getBorderColor());
    }    

    public static String makeBorderStyle(String width, String style, String color){
        return makeBorderStyle(null, width, style, color);
    }    
    public static String makeBorderStyle(String borderDirection, String width, String style, String color){
        
        StringBuffer buf = new StringBuffer();
        boolean hasData = false;
        
        buf.append("border");
        
        if ( borderDirection != null && borderDirection.length() > 0){
            buf.append("-").append(borderDirection);
        }
        
        buf.append(":");
        
        if (width != null && width.length() >0 ){ 
            buf.append(width).append("px ");
            hasData = true;
        } else
            buf.append("0px").append(" ");

        if (style != null && style.length() >0 ) {
            buf.append(style).append(" ");
            hasData = true;
        } else
            buf.append("solid").append(" ");

        if (color != null && color.length() >0 ) { 
            buf.append(color).append(" ");
            hasData = true;
        }else
            buf.append("black").append(" ");

        if (!hasData) return "";
        else return buf.toString() + "; ";
    }
    
    //Combined border style string into valid syntaxed string
    // input could be like. see example 
    // input   1px solid red  --> return border: 1px solid red;
    // input  -top: 2px solid red,-bottom: 1px solid blue -> border-top: 2px solid red; border-bottom:1px solid blue;
    public static String makeBorderStyle(String combined){
        
        StringBuffer buf = new StringBuffer();
        
        String sides[] = combined.split(",");
        
        for(int i = 0; i < sides.length;i++){
            
            String parts[] = sides[i].split(":");

            if ( parts.length == 1) {
                buf.append("border:").append(parts[0]).append(";");
            } else if (parts.length == 2){
                
                if (parts[0].startsWith("-"))
                    buf.append("border").append(parts[0]).append(":").append(parts[1]).append(";");
                else
                    buf.append("border-").append(parts[0]).append(":").append(parts[1]).append(";");
            } else {
               m_logger.warn("!!! Invalid border style configs!!! " + combined);
            }
        }
        
        
        return buf.toString(); 
        
    }
    
    
    
    public static String makeFontStyle(String family, String size, String style, String variant, String weight){
        StringBuffer buf = new StringBuffer();
        
        buf.append("font: ");
        boolean hasData = false;
        
        if (style != null && style.length() >0 ) {  
            buf.append(style).append(" ");
            hasData = true;
        } else 
            buf.append("normal").append(" ");

        if (variant != null && variant.length() >0 ){ 
            buf.append(variant).append(" ");
            hasData = true;
        } else 
            buf.append("normal").append(" ");

        if (weight != null && weight.length() >0 ){ 
            buf.append(weight).append(" ");
            hasData = true;
        } else 
            buf.append("normal").append(" ");

        if (size != null && size.length() >0 ) {
            buf.append(size).append("px ");
            hasData = true;
        } else 
            buf.append("inherit").append(" ");

        if (family != null && family.length() >0 ){ 
            buf.append(family).append(" ");
            hasData = true;
        } else 
            buf.append("inherit").append(" ");

        if (!hasData) return "";
        else return buf.toString() + "; ";
        
    }

    public static String makeMarginPaddingStyle(String fieldString, String str){
        if (str == null) return "";
        int pos = str.indexOf(":");
        
        if ( pos >= 0) return fieldString + str;
        
        return fieldString + ":" + str;
    }
    

    //####################################################################################################################################
    //####################################################################################################################################
    //####################################################################################################################################
    
    public static String makePanelStyle(long panelId, int panelPosition, int panelType, StyleConfig styleConfig, SiteConfig siteConfig, Panel panel) {
        return makePanelStyle(String.valueOf(panelId), panelPosition, panelType, styleConfig, siteConfig, panel);
    }    
    public static String makePanelStyle(String uniqueId, int panelPosition, int panelType, StyleConfig styleConfig, SiteConfig siteConfig, Panel panel) {
        return makePanelStyle(uniqueId, panelPosition, panelType, styleConfig, siteConfig, panel, false);
    }    
    public static String makePanelStyle(String uniqueId, int panelPosition, int panelType, StyleConfig styleConfig, SiteConfig siteConfig, Panel panel, boolean forTitle) {
        
        if ( styleConfig == null || panel == null) {
            m_logger.info("Panel or StyleConfig is null.Aborting style creation for " + uniqueId);
        }
        
        StringBuffer buf = new StringBuffer();
        
        String panelStylePrefixByPosition = getPanelStylePrefrix(panelPosition);
        buf.append(panelStylePrefixByPosition);
        
        if (forTitle){
            // paneltitle is all class only. 
            buf.append("div.panelTitle" + uniqueId).append(" {");
            buf.append("padding:").append(styleConfig.getPadding()).append("; ");
            
        } else {
        
            if ( panelType == PanelUtil.PANEL_HEADER_MENU) {
             
                // Padding is embedded into ul because it makes div overflow if specified in outer dev. But margin should in dev to be effective. 
                if (styleConfig.getPadding() != null && styleConfig.getPadding().length() >0) {
                    buf.append("div#header-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" {");
                    buf.append("padding:").append(styleConfig.getPadding()).append(";} ");
                }
    
                buf.append("div#header-menu" + uniqueId).append(" {");
      
                if ( styleConfig.getHeight() > 0) {
                    buf.append("height: ").append(styleConfig.getHeight()).append("px; "); // some how I need this for list. otherwise, borders collapased on list menu
                }
              
            } else if ( panelType == PanelUtil.PANEL_MENU) {
              
                // Padding is embedded into ul because it makes div overflow if specified in outer dev. But margin should in dev to be effective. 
              if (styleConfig.getPadding() != null && styleConfig.getPadding().length() >0) {
                  buf.append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" {");
                  buf.append("padding:").append(styleConfig.getPadding()).append(";} ");
              }
    
              buf.append("div#side-menu" + uniqueId).append(" {");

            } else if ( panelType == PanelUtil.PANEL_CONTENT_DEFAULT ||panelType == PanelUtil.PANEL_CONTENT_MID ) {
                //For content panel, there could be various styles so unique prefix also passed along
                buf.append("div#" + uniqueId).append(" {");
                
                if (styleConfig.getPadding() != null && styleConfig.getPadding().length() >0)
                    buf.append("padding:").append(styleConfig.getPadding()).append("; ");
            } else {
    
                buf.append("div#panel" + uniqueId).append(" {");
    
                if (styleConfig.getPadding() != null && styleConfig.getPadding().length() >0)
                    buf.append("padding:").append(styleConfig.getPadding()).append("; ");
            }

        }
        
        
        // width is column width - border
        int parentWidth = getParentFrameWidth(panelPosition, siteConfig);

        if (styleConfig.getBorderWidth() != null && styleConfig.getBorderWidth().length() > 0){
            int borderWidth = Integer.parseInt(styleConfig.getBorderWidth());
            if (styleConfig.getBorderDirection() != null && styleConfig.getBorderDirection().length() > 0){
                if ( styleConfig.getBorderDirection().indexOf("left") >=0 ) 
                    parentWidth -= borderWidth;

                if ( styleConfig.getBorderDirection().indexOf("right") >=0 ) 
                    parentWidth -= borderWidth;
                
            } else {
                parentWidth -= (borderWidth*2);
            }
        }
        
        
        // Width has been removed. Width is very hard to calculate when padding is more than 0px in side content bar. 
        // Based on css spec, display:block automatically puts line brake. So I replace width with display:block it looks good for the testing. 
        // Now able to set padding for the panel block. /8/12/10  
        
        buf.append("display: block; ");
        //buf.append("width:" + parentWidth).append("px; ");
        
        
        if ( panelType == PanelUtil.PANEL_HEADER_MENU) {
            //int panelHeight = (panel.getPanelHeight()==0?20:panel.getPanelHeight());
            //buf.append("height:" + panelHeight).append("px; ");
        }
        
        if ( panelType == PanelUtil.PANEL_MENU) {
            // no panel ehight for the side menu
            //int panelHeight = (panel.getPanelHeight()==0?20:panel.getPanelHeight());
            //buf.append("height:" + panelHeight).append("px; ");
        }

        if ( styleConfig.getListStyleType() == null |styleConfig.getListStyleType().length() == 1)
            buf.append("list-style-type: none; ");
        else 
            buf.append("list-style-type: "+ styleConfig.getListStyleType() +"; ");
        
        if (styleConfig.getMargin() != null && styleConfig.getMargin().length() >0)
            buf.append("margin:").append(styleConfig.getMargin()).append("; ");

        if (styleConfig.getTextAlign() != null && styleConfig.getTextAlign().length() >0)
            buf.append("text-align:").append(styleConfig.getTextAlign()).append("; ");

        if (styleConfig.getFloating() != null && styleConfig.getFloating().length() >0)
            buf.append("float:").append(styleConfig.getFloating()).append("; ");
        
        
        if ( styleConfig.getBorderDirection() != null && styleConfig.getBorderDirection().length() >0){
            buf.append(makeBorderStyle(styleConfig.getBorderDirection())).append(" ");
        } else {
            buf.append(makeBorderStyle(styleConfig.getBorderWidth(), styleConfig.getBorderStyle(), styleConfig.getBorderColor()));
        }
        
        buf.append(makeBackgroundStyle(styleConfig.getBgColor(), styleConfig.getBgImage(), styleConfig.getBgRepeat(), styleConfig.getBgAttach(), styleConfig.getBgPosition()));
        buf.append(makeFontStyle(styleConfig.getFontFamily(), styleConfig.getFontSize(), styleConfig.getFontStyle(), styleConfig.getFontVariant(), styleConfig.getFontWeight()));

        if (styleConfig.getExtraStyleStr() != null && styleConfig.getExtraStyleStr().length() >0)
            buf.append(styleConfig.getExtraStyleStr()).append("; ");
        
        buf.append("}\n");
        
        if (forTitle){
        
            buf.append(panelStylePrefixByPosition);
            if ( WebUtil.isNotNull(styleConfig.getLink())){
                buf.append("div.panelTitle" + uniqueId).append(" a{");
                buf.append(styleConfig.getLink()).append(";").append("}\n");
            }
    
            if ( WebUtil.isNotNull(styleConfig.getLinkHover())){
                buf.append("div.panelTitle" + uniqueId).append(" a:hover{");
                buf.append(styleConfig.getLinkHover()).append(";").append("}\n");
            }
    
            if ( WebUtil.isNotNull(styleConfig.getLinkActive())){
                buf.append("div.panelTitle" + uniqueId).append(" a:active{");
                buf.append(styleConfig.getLinkActive()).append(";").append("}\n");
            }
        
        } else {
            if ( panelType == PanelUtil.PANEL_CONTENT_DEFAULT ||panelType == PanelUtil.PANEL_CONTENT_MID ) {
                
                if ( WebUtil.isNotNull(styleConfig.getLink())){
                    buf.append(panelStylePrefixByPosition);
                    buf.append("div#" + uniqueId).append(" a{");
                    buf.append(styleConfig.getLink()).append(";").append("}\n");
                }
        
                if ( WebUtil.isNotNull(styleConfig.getLinkHover())){
                    buf.append(panelStylePrefixByPosition);
                    buf.append("div#" + uniqueId).append(" a:hover{");
                    buf.append(styleConfig.getLinkHover()).append(";").append("}\n");
                }
        
                if ( WebUtil.isNotNull(styleConfig.getLinkActive())){
                    buf.append(panelStylePrefixByPosition).append("div#" + uniqueId).append(" a.active,");
                    buf.append(panelStylePrefixByPosition).append("div#" + uniqueId).append(" a:activeP");
                    buf.append(styleConfig.getLinkActive()).append(";").append("}\n");
                }
            }
        }
        
        return buf.toString();
    }
    public static String makePanelDataItemStyle(StyleConfig styleConfig) {
        return makePanelDataItemStyle(styleConfig, null);
    }
    public static String makePanelDataItemStyle(StyleConfig styleConfig, String defaultCode) {
        
        if (styleConfig == null) return "";
        
        StringBuffer buf = new StringBuffer();
        
//        buf.append("width:" + parentWidth).append("px; ");

        buf.append("div#").append(getPanelDataStringDivId(defaultCode, styleConfig)).append(" {");        
        
        if ( styleConfig.getListStyleType() == null |styleConfig.getListStyleType().length() == 1)
            buf.append("list-style-type: none; ");
        else 
            buf.append("list-style-type: "+ styleConfig.getListStyleType() +"; ");
        
        if (styleConfig.getMargin() != null && styleConfig.getMargin().length() >0)
            buf.append("margin:").append(styleConfig.getMargin()).append("; ");

        if (styleConfig.getPadding() != null && styleConfig.getPadding().length() >0)
            buf.append("padding:").append(styleConfig.getPadding()).append("; ");

        if (styleConfig.getTextAlign() != null && styleConfig.getTextAlign().length() >0)
            buf.append("text-align:").append(styleConfig.getTextAlign()).append("; ");

        if (styleConfig.getFloating() != null && styleConfig.getFloating().length() >0)
            buf.append("float:").append(styleConfig.getFloating()).append("; ");
        
        
        if ( styleConfig.getBorderDirection() != null && styleConfig.getBorderDirection().length() >0){
            buf.append(makeBorderStyle(styleConfig.getBorderDirection())).append(" ");
        } else {
            buf.append(makeBorderStyle(styleConfig.getBorderWidth(), styleConfig.getBorderStyle(), styleConfig.getBorderColor()));
        }
        
        buf.append(makeBackgroundStyle(styleConfig.getBgColor(), styleConfig.getBgImage(), styleConfig.getBgRepeat(), styleConfig.getBgAttach(), styleConfig.getBgPosition()));
        buf.append(makeFontStyle(styleConfig.getFontFamily(), styleConfig.getFontSize(), styleConfig.getFontStyle(), styleConfig.getFontVariant(), styleConfig.getFontWeight()));

        if (styleConfig.getHeight()>0)
            buf.append("height:").append(styleConfig.getHeight()).append("px;");

        if (styleConfig.getWidth()>0)
            buf.append("width:").append(styleConfig.getWidth()).append("px;");
        
        if (styleConfig.getExtraStyleStr() != null && styleConfig.getExtraStyleStr().length() >0)
            buf.append(styleConfig.getExtraStyleStr()).append("; ");
        
        buf.append("}");

        return buf.toString();
    }
    
    // print li for menu style
    public static String makePanelNavStyleString(Panel panel, String panelStyleSuffix){
        StringBuffer buf = new StringBuffer();

        if ( panel.getPanelType() == PanelUtil.PANEL_HEADER_MENU) {
            buf.append(getPanelStylePrefrix(panel.getColumnNum()));
            buf.append("div#header-menu" + panelStyleSuffix).append(" ul#nav" + panelStyleSuffix).append(" li#nav-item" + panel.getId()+" {");
            buf.append("float: left; ");
            if (panel.getStyleString() != null)
                buf.append(panel.getStyleString());
            buf.append("; }");
        } else if ( panel.getPanelType() == PanelUtil.PANEL_MENU && panel.getStyleString() != null) {
            buf.append(getPanelStylePrefrix(panel.getColumnNum()));
            buf.append("div#side-menu" + panelStyleSuffix).append(" ul#nav" + panelStyleSuffix).append(" li#nav-item" + panel.getId()+" {");
            if (panel.getStyleString() != null)
                buf.append(panel.getStyleString());
            buf.append("; }");
        }        
        
        return buf.toString();
    }
    
    public static String makePanelLinkStyle(long panelId, int panelPosition, int panelType, String panelStyleSuffix, String panelNavStyleSuffix, LinkStyleConfig styleConfig, SiteConfig siteConfig, Panel panel) {
        return makePanelLinkStyle(String.valueOf(panelId), panelPosition, panelType, panelStyleSuffix, panelNavStyleSuffix, styleConfig, siteConfig, panel);
    }
    
    public static String makePanelLinkStyle(String uniqueId, int panelPosition, int panelType, String panelStyleSuffix, String panelNavStyleSuffix, LinkStyleConfig styleConfig, SiteConfig siteConfig, Panel panel) {
        StringBuffer buf = new StringBuffer();
        
        if ( panelType == PanelUtil.PANEL_HEADER_MENU) {

            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#header-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" {list-style-type:none; margin: 0px 0px 0px 0px;}\n");

            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#header-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+"{ float: left; margin: 0px 0px 0px 0px;}\n");
            
            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#header-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a {");
        
        } else if ( panelType == PanelUtil.PANEL_MENU) {

            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" {list-style-type:none; margin: 0px 0px 0px 0px;}\n");

            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+"{margin: 0px 0px 0px 0px;}\n");

            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+"{ }\n");

            
            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a {");

        } else {
            if (panel != null && panel.getStyleString() != null) {
                buf.append(getPanelStylePrefrix(panelPosition));
                buf.append("div#link-content"+uniqueId).append(" {").append(panel.getStyleString()).append(" }\n");
            }
            buf.append(getPanelStylePrefrix(panelPosition));
            buf.append("div#link-content"+uniqueId).append(" a {");
        }

        if (styleConfig.getFont() != null && styleConfig.getFont().length() >0)
            buf.append("font: ").append(styleConfig.getFont()).append("; ");
        
        if (styleConfig.getHeight() != null && styleConfig.getHeight().length() >0)
            buf.append("height: ").append(styleConfig.getHeight()).append("; ");
        
        if (styleConfig.getWidth() != null && styleConfig.getWidth().length() >0)
            buf.append("width: ").append(styleConfig.getWidth()).append("; ");

        if (styleConfig.getDisplay() != null && styleConfig.getDisplay().length() >0)
            buf.append("display: ").append(styleConfig.getDisplay()).append("; ");
        
        if (styleConfig.getBorder() != null && styleConfig.getBorder().length() >0)
            buf.append(makeBorderStyle(styleConfig.getBorder())).append(" ");
//            buf.append("border: ").append(styleConfig.getBorder()).append("; ");
        if (styleConfig.getBackground() != null && styleConfig.getBackground().length() >0)
            buf.append("background: ").append(styleConfig.getBackground()).append("; ");

        if (styleConfig.getColor() != null && styleConfig.getColor().length() >0)
            buf.append("color: ").append(styleConfig.getColor()).append("; ");

        if (styleConfig.getTextDecoration() != null && styleConfig.getTextDecoration().length() >0)
            buf.append("text-decoration: ").append(styleConfig.getTextDecoration()).append("; ");
        
        if (styleConfig.getTextIndent() != null && styleConfig.getTextIndent().length() >0)
            buf.append("text-indent: ").append(styleConfig.getTextIndent()).append("; ");
        
        if (styleConfig.getTextAlign() != null && styleConfig.getTextAlign().length() >0)
            buf.append("text-align: ").append(styleConfig.getTextAlign()).append("; ");
        
        if (styleConfig.getVerticalAlign() != null && styleConfig.getVerticalAlign().length() >0)
            buf.append("vertical-align: ").append(styleConfig.getVerticalAlign()).append("; ");

        if (styleConfig.getMargin() != null && styleConfig.getMargin().length() >0) {
            buf.append(makeMarginPaddingStyle("margin", styleConfig.getMargin())).append("; ");
        }

        if (styleConfig.getPadding() != null && styleConfig.getPadding().length() >0) {
            buf.append(makeMarginPaddingStyle("padding", styleConfig.getPadding())).append("; ");
        }
        
        if (styleConfig.getExtraStyle() != null && styleConfig.getExtraStyle().length() >0)
            buf.append(styleConfig.getExtraStyle()).append("; ");
        
        
        buf.append("}\n");
        
        if ( panelType == PanelUtil.PANEL_HEADER_MENU) {
            
            buf.append(getPanelStylePrefrix(panelPosition)).append("div#header-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a:hover,");
            buf.append(getPanelStylePrefrix(panelPosition)).append("div#header-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a:active,");
            buf.append(getPanelStylePrefrix(panelPosition)).append("div#header-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a.active{");
        } else if ( panelType == PanelUtil.PANEL_MENU) {
            buf.append(getPanelStylePrefrix(panelPosition)).append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a:hover,");
            buf.append(getPanelStylePrefrix(panelPosition)).append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a:active,");
            buf.append(getPanelStylePrefrix(panelPosition)).append("div#side-menu" + uniqueId).append(" ul#nav" + uniqueId).append(" li#nav-item"+uniqueId+" a.active{");
        } else {

            if (WebUtil.isTrue(styleConfig.getActiveUse())){
                buf.append(getPanelStylePrefrix(panelPosition)).append("div#link-content"+uniqueId).append(" a:hover {");
            } else {
                buf.append(getPanelStylePrefrix(panelPosition)).append("div#link-content"+uniqueId).append(" a:hover,");
                buf.append(getPanelStylePrefrix(panelPosition)).append("div#link-content"+uniqueId).append(" a:active,");
                buf.append(getPanelStylePrefrix(panelPosition)).append("div#link-content"+uniqueId).append(" a.active{");
            }
        }

        if (styleConfig.getHovFont() != null && styleConfig.getHovFont().length() >0)
            buf.append("font: ").append(styleConfig.getHovFont()).append("; ");

        if (styleConfig.getHovHeight() != null && styleConfig.getHovHeight().length() >0)
            buf.append("height: ").append(styleConfig.getHovHeight()).append("; ");
        
        if (styleConfig.getHovWidth() != null && styleConfig.getHovWidth().length() >0)
            buf.append("width: ").append(styleConfig.getHovWidth()).append("; ");

        if (styleConfig.getHovDisplay() != null && styleConfig.getHovDisplay().length() >0)
            buf.append("display: ").append(styleConfig.getHovDisplay()).append("; ");
        
        if (styleConfig.getHovBorder() != null && styleConfig.getHovBorder().length() >0)
            buf.append(makeBorderStyle(styleConfig.getHovBorder())).append(" ");
//            buf.append("border: ").append(styleConfig.getHovBorder()).append("; ");

        if (styleConfig.getHovBackground() != null && styleConfig.getHovBackground().length() >0)
            buf.append("background: ").append(styleConfig.getHovBackground()).append("; ");

        if (styleConfig.getHovColor() != null && styleConfig.getHovColor().length() >0)
            buf.append("color: ").append(styleConfig.getHovColor()).append("; ");

        if (styleConfig.getHovTextDecoration() != null && styleConfig.getHovTextDecoration().length() >0)
            buf.append("text-decoration: ").append(styleConfig.getHovTextDecoration()).append("; ");
        
        if (styleConfig.getHovTextIndent() != null && styleConfig.getHovTextIndent().length() >0)
            buf.append("text-indent: ").append(styleConfig.getHovTextIndent()).append("; ");

        if (styleConfig.getHovTextAlign() != null && styleConfig.getHovTextAlign().length() >0)
            buf.append("text-align: ").append(styleConfig.getHovTextAlign()).append("; ");
        
        if (styleConfig.getHovVerticalAlign() != null && styleConfig.getHovVerticalAlign().length() >0)
            buf.append("vertical-align: ").append(styleConfig.getHovVerticalAlign()).append("; ");

        if (styleConfig.getHovMargin() != null && styleConfig.getHovMargin().length() >0) {
            buf.append(makeMarginPaddingStyle("margin", styleConfig.getHovMargin())).append("; ");
        }

        if (styleConfig.getHovPadding() != null && styleConfig.getHovPadding().length() >0) {
            buf.append(makeMarginPaddingStyle("padding", styleConfig.getHovPadding())).append("; ");
        }
        
        if (styleConfig.getHovExtraStyle() != null && styleConfig.getHovExtraStyle().length() >1)
            buf.append(styleConfig.getHovExtraStyle()).append("; ");
        
        buf.append("}\n");

        
        if (WebUtil.isTrue(styleConfig.getActiveUse())){

            buf.append(getPanelStylePrefrix(panelPosition)).append("div#link-content"+uniqueId).append(" a:active,");
            buf.append(getPanelStylePrefrix(panelPosition)).append("div#link-content"+uniqueId).append(" a.active,{");
        
            if (WebUtil.isNotNull(styleConfig.getActiveBorder())){
                buf.append(makeBorderStyle(styleConfig.getActiveBorder())).append(" ");
            }
            
            if (WebUtil.isNotNull(styleConfig.getActiveBackground())){
                buf.append("background: ").append(styleConfig.getActiveBackground()).append("; ");
            }
            
            
            if (WebUtil.isNotNull(styleConfig.getActiveColor())){
                buf.append("color: ").append(styleConfig.getActiveColor()).append("; ");
            }
            
            if (WebUtil.isNotNull(styleConfig.getActiveTextDecoration())){
                buf.append("text-decoration: ").append(styleConfig.getActiveTextDecoration()).append("; ");
            }
            
            if (WebUtil.isNotNull(styleConfig.getActiveMargin())){
                buf.append(makeMarginPaddingStyle("margin", styleConfig.getActiveMargin())).append("; ");
                
            }
            
            if (WebUtil.isNotNull(styleConfig.getActivePadding())){
                buf.append(makeMarginPaddingStyle("padding", styleConfig.getActivePadding())).append("; ");
                
            }
            
            if (WebUtil.isNotNull(styleConfig.getActiveExtraStyle())){
                buf.append(styleConfig.getActiveExtraStyle()).append("; ");
            }
            buf.append("}\n");
        }        
        
        
        return buf.toString();
    }    

    public static String makeCustomStyle(String id, StyleConfig styleConfig, SiteConfig siteConfig) {
        return makeCustomStyle(id, -1, styleConfig, siteConfig, true);
    }    

    public static String makeCustomStyle(String id, StyleConfig styleConfig, SiteConfig siteConfig, boolean isStyleClass) {
        return makeCustomStyle(id, -1, styleConfig, siteConfig, isStyleClass);
    }    

    public static String makeCustomStyle(String id, int position, StyleConfig styleConfig, SiteConfig siteConfig) {
        return makeCustomStyle(id, position, styleConfig, siteConfig, false); 
    }    
    
    public static String makeCustomStyle(String id, int position, StyleConfig styleConfig, SiteConfig siteConfig, boolean isStyleClass) {
        StringBuffer buf = new StringBuffer();

        if (isStyleClass)
            buf.append("div.").append(id).append(" {");
        else 
            buf.append("div#").append(id).append(" {");
        
        if (position > 0){
            int parentWidth = getParentFrameWidth(position, siteConfig);
    
            if (styleConfig.getBorderWidth() != null && styleConfig.getBorderWidth().length() > 0){
                parentWidth -= (Integer.parseInt(styleConfig.getBorderWidth())*2);
            }
            
            buf.append("width:" + parentWidth).append("px; ");
            //buf.append("height:" + panelHeight).append("px; ");
        }

        if ( WebUtil.isNotNull(styleConfig.getTextAlign())){
            buf.append(styleConfig.getTextAlign()).append(";");
        }
        
        if (styleConfig.getMargin() != null && styleConfig.getMargin().length() >0)
            buf.append("margin:").append(styleConfig.getMargin()).append("; ");

        if (styleConfig.getPadding() != null && styleConfig.getPadding().length() >0)
            buf.append("padding:").append(styleConfig.getPadding()).append("; ");

        if (styleConfig.getHeight() > 0)
            buf.append("height:").append(styleConfig.getHeight()).append("px; ");
        
        if (styleConfig.getWidth() > 0)
            buf.append("width:").append(styleConfig.getWidth()).append("px; ");
        
        buf.append(makeBorderStyle(styleConfig.getBorderWidth(), styleConfig.getBorderStyle(), styleConfig.getBorderColor()));
        buf.append(makeBackgroundStyle(styleConfig.getBgColor(), styleConfig.getBgImage(), styleConfig.getBgRepeat(), styleConfig.getBgAttach(), styleConfig.getBgPosition()));
        buf.append(makeFontStyle(styleConfig.getFontFamily(), styleConfig.getFontSize(), styleConfig.getFontStyle(), styleConfig.getFontVariant(), styleConfig.getFontWeight()));

        if ( WebUtil.isNotNull(styleConfig.getExtraStyleStr())){
            buf.append(styleConfig.getExtraStyleStr()).append(";");
        }

            
        
        
        
        buf.append("}\n");

        
        if ( WebUtil.isNotNull(styleConfig.getLink())){
            if (isStyleClass)
                buf.append("div.").append(id).append(" a{");
            else 
                buf.append("div#").append(id).append(" a{");
            buf.append(styleConfig.getLink()).append(";").append("}\n");
        }

        if ( WebUtil.isNotNull(styleConfig.getLinkHover())){
            if (isStyleClass)
                buf.append("div.").append(id).append(" a:hover{");
            else 
                buf.append("div#").append(id).append(" a:hover{");
            buf.append(styleConfig.getLinkHover()).append(";").append("}\n");
        }

        if ( WebUtil.isNotNull(styleConfig.getLinkActive())){
            if (isStyleClass)
                buf.append("div.").append(id).append(" a:active{");
            else 
                buf.append("div#").append(id).append(" a:active{");
            buf.append(styleConfig.getLinkActive()).append(";").append("}\n");
        }
        
        
        
        return buf.toString();
    }
    
    
    
    //####################################################################################################################################
    //####################################################################################################################################
    //####################################################################################################################################
    
    private static String getPanelStylePrefrix(int panelPosition){
        switch(panelPosition){
        case 1: return "div#content-wrapper div#menu-section ";
        case 2: return "div#content-wrapper div#content-section ";
        case 3: return "div#content-wrapper div#mid-section ";
        case 4: return "div#content-wrapper div#ad-section ";
        
        case 5: return "div#header-wrapper ";
        case 6: return "div#header-wrapper ";
        case 7: return "div#content-wrapper div#content-section ";
        case 8: return "div#footer-wrapper ";
        case 9: return "div#footer-wrapper ";
        case 10: return "div#content-wrapper div#content-section div#main-content "; // paage panel
        case 11: return "div#content-wrapper div#mid-section div#mid-content "; // paage panel

        case 21: return "div#content-wrapper div#menu-section "; // Feed Menu section
        case 22: return "div#content-wrapper div#ad-section "; // Feed Ad section
        
        default: return "";
        }
    }
    
    private static int getParentFrameWidth(int panelPosition, SiteConfig siteConfig){
        SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(siteConfig.getSiteId());

        switch(panelPosition){
        case 1: return siteConfig.getMenuWidth();
        case 2: return calculateContentWidth(siteConfigTrans, siteConfig);
        case 3: return siteConfig.getMidColumnWidth();
        case 4: return siteConfig.getAdWidth();
        
        case 5: return siteConfig.getWidth() + siteConfigTrans.getWidthOffset();
        case 6: return siteConfig.getWidth() + siteConfigTrans.getWidthOffset();
        case 7: return calculateContentWidth(siteConfigTrans, siteConfig);
        case 8: return calculateContentWidth(siteConfigTrans, siteConfig);
        case 9: return siteConfig.getWidth() + siteConfigTrans.getWidthOffset();
        case 10: return calculateContentWidth(siteConfigTrans, siteConfig);
        case 11: return siteConfig.getMidColumnWidth();
        
        default: return siteConfig.getMenuWidth();
        }
    }
    
    private static int getParentFrameWidthByDiv(int panelPosition, SiteConfig siteConfig){
        SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(siteConfig.getSiteId());

        switch(panelPosition){
        case PanelUtil.PANEL_WIDTH_DIV_WHOLE :  return siteConfig.getWidth() + siteConfigTrans.getWidthOffset(); 
        case PanelUtil.PANEL_WIDTH_DIV_CONTENT : return calculateContentWidth(siteConfigTrans, siteConfig);
        case PanelUtil.PANEL_WIDTH_DIV_MID : return siteConfig.getMidColumnWidth();
        case PanelUtil.PANEL_WIDTH_DIV_AD : return siteConfig.getAdWidth();
        case PanelUtil.PANEL_WIDTH_DIV_MENU :
            default: return siteConfig.getMenuWidth();
        }
    }
    
    public static int getPanelWidthDiv(int panelPosition){

        switch(panelPosition){
        case 0: return 0; // Width Not Applicable
        case 1: return PanelUtil.PANEL_WIDTH_DIV_MENU;
        case 2: return PanelUtil.PANEL_WIDTH_DIV_CONTENT;
        case 3: return PanelUtil.PANEL_WIDTH_DIV_MID;
        case 4: return PanelUtil.PANEL_WIDTH_DIV_AD;
        
        case 5: return PanelUtil.PANEL_WIDTH_DIV_WHOLE;
        case 6: return PanelUtil.PANEL_WIDTH_DIV_WHOLE;
        case 7: return PanelUtil.PANEL_WIDTH_DIV_CONTENT;
        case 8: return PanelUtil.PANEL_WIDTH_DIV_CONTENT;
        case 9: return PanelUtil.PANEL_WIDTH_DIV_WHOLE;
        case 10: return PanelUtil.PANEL_WIDTH_DIV_CONTENT;
        case 11: return PanelUtil.PANEL_WIDTH_DIV_MID;
        case 21: return PanelUtil.PANEL_WIDTH_DIV_MENU;
        case 22: return PanelUtil.PANEL_WIDTH_DIV_AD;
        
        default: return PanelUtil.PANEL_WIDTH_DIV_MENU;
        }
    }

    private static String getPanelStylePrefrixByType(int panelType){
        switch(panelType){
        case 1: return "";
        case 2: return "";
        case 3: return "";
        case 4: return "div#header-menu ";
        default : return "";
        }
    }
    
    // Obsolete
    public static int calculateContentWidth(SiteConfig siteConfig){
        SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(siteConfig.getSiteId());

        if(true){
            return calculateContentWidth(siteConfigTrans, siteConfig);
        }
        
        int ret = siteConfig.getWidth();
        
        if (siteConfig.getShowMenuColumn() == 1) 
            ret -= siteConfig.getMenuWidth();
        
        if (siteConfig.getShowMidColumn() == 1) 
            ret -= siteConfig.getMidColumnWidth();

        if (siteConfig.getShowAdColumn() == 1) 
            ret -= siteConfig.getAdWidth();

        ret -= 5*calculateNumDivider(siteConfig);
        
        return ret;
    }

    public static int calculateContentWidth(SiteConfigTransient siteConfigTrans, SiteConfig siteConfig){
        int ret = siteConfig.getWidth() + siteConfigTrans.getWidthOffset();
        int numDivider = 0;
        
        if (!siteConfigTrans.isHideMenu()) { 
            ret -= siteConfig.getMenuWidth();
            numDivider++;
        }
        
        if (!siteConfigTrans.isHideMid()){ 
            ret -= siteConfig.getMidColumnWidth();
            numDivider++;
        }

        if (!siteConfigTrans.isHideAd()){ 
            ret -= siteConfig.getAdWidth();
            numDivider++;
        }

        ret -= siteConfig.getHomeColCount()*numDivider;
        
        return ret;
    }    
    
    public static int calculateNumDivider(SiteConfig siteConfig){
        return siteConfig.getShowAdColumn() + siteConfig.getShowMidColumn() + siteConfig.getShowMenuColumn();
    }

    public static int calculateNumDivider(SiteConfigTransient siteConfigTrans){
        
        int ret = 0;
        if (!siteConfigTrans.isHideMenu()) ret++;
        if (!siteConfigTrans.isHideMid()) ret++;
        if (!siteConfigTrans.isHideAd()) ret++;
        
        return ret;
    }
    
    public static int calculateNumDivider(boolean showMenu, boolean showMid, boolean showAd){
        
        int ret = 0;
        if (showMenu) ret++;
        if (showMid) ret++;
        if (showAd) ret++;
        
        return ret;
    }
    
    // return div ID for data item style. DataItem Style is set in two place (1) Panel Config (2) Style Set
    public static String getPanelDataStringDivId(String styleDefaultCode, StyleConfig styleConfig){
        if ( styleConfig == null && styleDefaultCode == null ) return "__panelDataStyle";
        
        if ( WebUtil.isNull(styleDefaultCode)){
            return "__panelDataStyle" + styleConfig.getId();
        } else {
            return "__panelDataStyle" + styleDefaultCode;
        }
    }
    
    // find the style id for data div. 
    // starts from narrowest scope and move on to wider scope. If there is none to match, return empty string.
    public static String getPanelDataStringDivId(Panel panel, StyleConfig styleConfig){
        if ( styleConfig == null && panel == null ) return "__panelDataStyle";

        // associated style. is set from styleStrintg2() field of panel. 
        if (styleConfig!= null ){
            return "__panelDataStyle" + styleConfig.getId();
        }        

        // Default code is key for associated style set. 
        if ( !WebUtil.isNull(panel.getStyleDefaultCode())){
            String id = createStyleSetId(panel.getStyleDefaultCode(), 0, panel.getPanelType()); 
            return "__panelDataStyle" + id;
        }
        
        // Finally check the theme whether this site has a site-wide theme. 
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
        
        if (theme != null){
            if (StyleConfigDS.getInstance().getById(theme.getPanelDataStyleId())!= null)
                return "__panelDataStyle" + theme.getPanelDataStyleId();
        }
        
        return "";
    }
    
    public static String createStyleSetId(Panel panel){
        return createStyleSetId(panel.getStyleDefaultCode(), panel.getColumnNum(), panel.getPanelType());
    }
    
    public static String createStyleSetId(String setName, int position, int type){

        if ( position == 0) return setName + "-0";
        
        int widthDiv = StyleUtil.getPanelWidthDiv(position);
        
        switch(type){
        case PanelUtil.PANEL_MENU: 
            return setName + "-" + widthDiv + "-VMenu";
        case PanelUtil.PANEL_HEADER_MENU : 
            return setName + "-" + widthDiv + "-HMenu";
        case PanelUtil.PANEL_CONTENT_DEFAULT:
            return setName + "-" + widthDiv + "-CPanel";
        case PanelUtil.PANEL_CONTENT_MID:
            return setName + "-" + widthDiv + "-MPanel";
        default: 
            return setName + "-" + widthDiv;
        }
    }

    public static String createThemeId(String themeName, int position, int type){

        if ( position == 0) return themeName + "-0";
        
        int widthDiv = StyleUtil.getPanelWidthDiv(position);
        
        switch(type){
        case PanelUtil.PANEL_MENU: 
            return themeName + "-" + widthDiv + "-VMenu";
        case PanelUtil.PANEL_HEADER_MENU : 
            return themeName + "-" + widthDiv + "-HMenu";
        case PanelUtil.PANEL_CONTENT_DEFAULT:
            return themeName + "-" + widthDiv + "-CPanel";
        case PanelUtil.PANEL_CONTENT_MID:
            return themeName + "-" + widthDiv + "-MPanel";
        default: 
            return themeName + "-" + widthDiv;
        }
    }
    
    
    public static void main(String[] args) {
        System.out.println(makeBorderStyle("1px solid red"));
        System.out.println(makeBorderStyle("bottom:1px solid red"));
        System.out.println(makeBorderStyle("bottom:1px solid red,top:2px solid blue"));
        System.out.println(makeBorderStyle("-bottom:1px solid red,-top:2px solid blue"));
    }
    
}
