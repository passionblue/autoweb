package com.autosite.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.SiteConfigTransient;
import com.autosite.app.AutoSiteInitiator;
import com.autosite.db.LinkStyleConfig;
import com.autosite.db.Panel;
import com.autosite.db.PanelLinkStyle;
import com.autosite.db.PanelStyle;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.SiteConfigStyle;
import com.autosite.db.StyleConfig;
import com.autosite.db.StyleSet;
import com.autosite.db.StyleTheme;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.ds.PanelDS;
import com.autosite.ds.PanelLinkStyleDS;
import com.autosite.ds.PanelStyleDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteConfigStyleDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.ds.StyleSetDS;
import com.autosite.ds.StyleThemeDS;
import com.autosite.util.PanelUtil;
import com.autosite.util.PanelUtil2;
import com.autosite.util.StyleConfigUtil;
import com.jtrend.util.WebUtil;

//XXX removed during migration import sun.security.krb5.internal.LoginOptions;

//This is main style servet for  cscr

public class CustomScriptsServlet  extends AbstractAutositeServlet {
    
    protected boolean m_debug = AutositeGlobals.m_servletDebug;

    public CustomScriptsServlet() 
    {
        if ( m_debug) m_logger.debug("################################");
        new AutoSiteInitiator().init();
        
        siteDS = SiteDS.getInstance();
        siteConfigDS = SiteConfigDS.getInstance();
        panelDS = PanelDS.getInstance();
        linkStyleConfigDS = LinkStyleConfigDS.getInstance();

        m_logger.debug("###############################################################################################");                
//        m_logger.debug(this.getClass().getClassLoader().);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        if ( m_debug) m_logger.debug("################################ START CustomScriptsServlet ##########################################################################");
        long start = System.currentTimeMillis();
        Site site = siteDS.registerSite(request.getServerName());
        SiteConfig siteConfig = siteConfigDS.getSiteConfigBySiteId(site.getId());
        SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

        if (siteConfig == null) 
            siteConfig = SiteConfigDS.getDefaultSiteConfig();
        
        //FileUtil.loadFileToString(filename);
        
        printHeaders(request);
        
        //This type should match to type defined in <link> on IE. I think that is IE change in the recent version. 
        response.setContentType("text/css;");

        //out.println("body { background:  url(/images/main_bg.jpg) no-repeat scroll center top; }");

        StringBuffer buffer = new StringBuffer();
        
        String styleString = StyleUtil.makeBackgroundStyle(siteConfig.getBackgroundColor(), siteConfig.getBackgroundImage(), siteConfig.getBackgroundRepeat(), 
                           siteConfig.getBackgroundAttach(), siteConfig.getBackgroundPosition());
        buffer.append("body {" + styleString + "}").append("\n");

        int dividerWidth    = (siteConfig.getHomeColCount()>0?siteConfig.getHomeColCount():5);
        int contentWidth    = StyleUtil.calculateContentWidth(siteConfigTrans, siteConfig);
        int wholeWidth      = siteConfig.getWidth() + siteConfigTrans.getWidthOffset();
        
        // Wrapper styles
        if (siteConfig.getAbsolutePosition() == 2) {
            buffer.append("div#out-frame { width: 100%; }");
            buffer.append("div#leftSpace {float:none; display:none; width: 0px; left: -100px;}\n");
            buffer.append("div#rightSpace {float:none; display:none; width: 0px; left: -100px;}\n");
            buffer.append("div#wrapper { width: 100%; }");

            buffer.append("div#header-wrapper   { width: 100%; }");
            buffer.append("div#content-wrapper  { width: 100%; }");
            buffer.append("div#footer-wrapper   { width: 100%; }");
            
            buffer.append("div#content-wrapper div#menu-section-frame { width: " + siteConfig.getMenuWidth() + "px; }");
            buffer.append("div#content-wrapper div#mid-section-frame { width: " + siteConfig.getMidColumnWidth() + "px; }");
            buffer.append("div#content-wrapper div#ad-section-frame { width: " + siteConfig.getAdWidth() + "px; float: right;}");
            
        } else if (siteConfig.getAbsolutePosition() == 1) {
            buffer.append("div#out-frame { width: " + (wholeWidth+100) + "px; }");
            buffer.append("div#leftSpace {float:none; width:1px; left: -100px;}\n");
            buffer.append("div#rightSpace {float:none; width:1px; left: -100px;}\n");
            buffer.append("div#wrapper { position: absolute; top:"+siteConfig.getAbsoluteTop()+"px; left:"+siteConfig.getAbsoluteLeft()+"px; ");          
            
            if ( WebUtil.isNotNull(siteConfig.getContentBgColor())){
                buffer.append("background-color: "+siteConfig.getContentBgColor()+"; ");
            } else {
                buffer.append("background-color: white; ");
            }
            buffer.append("}").append("\n"); 
            buffer.append("div#wrapper { width: " + (wholeWidth) + "px; }\n");

            buffer.append("div#content-wrapper div#menu-section-frame{ width: " + siteConfig.getMenuWidth() + "px; }");
            buffer.append("div#content-wrapper div#mid-section-frame{ width: " + siteConfig.getMidColumnWidth() + "px; }");
            buffer.append("div#content-wrapper div#ad-section-frame{ width: " + siteConfig.getAdWidth() + "px; }");
            buffer.append("div#content-wrapper div#content-section{ width: " + contentWidth + "px; }");
            
        } else {
            buffer.append("div#out-frame { width: " + (wholeWidth+100) + "px; }");
            buffer.append("div#out-frame { margin-left: auto; margin-right: auto; height:100% }").append("\n");  
            buffer.append("div#leftSpace {float:left;width:50px;}\n");
            buffer.append("div#rightSpace {float:left;width:50px;}\n");
            buffer.append("div#wrapper { margin-top: 0px; margin-left: auto; margin-right: auto; float:left; ");  

            if ( WebUtil.isNotNull(siteConfig.getContentBgColor())){
                buffer.append("background-color: "+siteConfig.getContentBgColor()+"; ");
            } else {
                buffer.append("background-color: white; ");
            }
            buffer.append("}").append("\n"); 
            buffer.append("div#wrapper { width: " + (wholeWidth) + "px; }\n");

            buffer.append("div#content-wrapper div#menu-section-frame{ width: " + siteConfig.getMenuWidth() + "px; }");
            buffer.append("div#content-wrapper div#mid-section-frame{ width: " + siteConfig.getMidColumnWidth() + "px; }");
            buffer.append("div#content-wrapper div#ad-section-frame{ width: " + siteConfig.getAdWidth() + "px; }");
            buffer.append("div#content-wrapper div#content-section-frame{ width: " + contentWidth + "px; }");
        }
        
        
        String divColor = (siteConfig.getColorBorders() == null? "white" : siteConfig.getColorBorders());
        buffer.append("div#vertical-divider { width: "+dividerWidth+"px; height: 100px; border: 0px; background-color: "+divColor+"; float: left;}").append("\n");
        
        
        // Site Width configurations
        
        
        buffer.append("div#header-wrapper div#paneldefault { width: " + (wholeWidth-2) + "px; }").append("\n");
        buffer.append("div#footer-wrapper div#paneldefault { width: " + (wholeWidth-2) + "px; }").append("\n");
        buffer.append("div#content-wrapper div#menu-section-frame div#paneldefault { width: " + (siteConfig.getMenuWidth()-0) + "px; }").append("\n");
        buffer.append("div#content-wrapper div#mid-section-frame div#paneldefault { width: " + (siteConfig.getMidColumnWidth()-0) + "px; }").append("\n");
        buffer.append("div#content-wrapper div#ad-section-frame div#paneldefault { width: " + (siteConfig.getAdWidth()-0) + "px; }").append("\n");

//      buffer.append("div#content-wrapper div#content-section div#paneldefault { width: " + (contentWidth -2) + "px; }").append("\n");
//      buffer.append("div#content-wrapper div#content-section div#main-content div#paneldefault { width: " + (contentWidth -2) + "px; }").append("\n");

        //I dont know somehow, panel side is 2 pixels short. because of this. I dont remember why I take two out. am trying without minu 2. 0304
        buffer.append("div#content-wrapper div#content-section div#paneldefault { width: " + (contentWidth) + "px; }").append("\n");
        buffer.append("div#content-wrapper div#content-section div#main-content div#paneldefault { width: " + (contentWidth) + "px; }").append("\n");
        
        PrintWriter out = response.getWriter();

        out.println("/* =================================== Wrappers  ========================================== */");
        out.println(buffer.toString()); // Constructed ABove
        if ( m_debug)  m_logger.debug(">> " + buffer.toString());
        
        out.println("/* =================================== Panel Specific ============================================== */");
        printPanelStyle(site.getId(), request, out);

        // For Style set
//        out.println("/* =================================== Style Sets ================================================== */");
//        printStyleSets2(site.getId(), request, out);
        
        // For Style set
//        out.println("/* =================================== Theme ================================================== */");
//        printTheme(site.getId(), request, out);
        

        
        // Vertical Menu Styles
        out.println("/* =================================== Menu Styles ================================================= */");
        LinkStyleConfig styleConfig = linkStyleConfigDS.getObjectByStyleKey(StyleConfigUtil.getDefaultVerticalMenuStyleKey(site.getId()));
        String mlddMenuConfig = "";
        if ( styleConfig == null) 
            mlddMenuConfig = MlddMenuStyleUtil.makeDefaultVerticalMlddMenuConfig("s"+site.getId());
        else
            mlddMenuConfig = MlddMenuStyleUtil.makeMlddVerticalMenuConfig("s"+site.getId(), styleConfig);
        
        out.println(mlddMenuConfig);

        // Horizontal Menu Styles
        out.println("/* =======Horizontal Menu Styles  ============================================== */");
        
        LinkStyleConfig styleConfigH = linkStyleConfigDS.getObjectByStyleKey(StyleConfigUtil.getDefaultHorizontalMenuStyleKey(site.getId()));
        
        String mlddMenuConfigH = "";
        
        if ( styleConfigH == null) 
            mlddMenuConfigH = MlddMenuStyleUtil.makeDefaultHorizontalMlddMenuConfig("s"+site.getId());
        else
            mlddMenuConfigH = MlddMenuStyleUtil.makeMlddHorizontalMenuConfig("s"+site.getId(), styleConfigH);
        
        out.println(mlddMenuConfigH);
        
        // ddSmoothMenu -- Not using
        //String ddsmoothmenuStr = makeDefaultMenuConfig("site"+site.getId());
        // out.println(ddsmoothmenuStr);

        //ddsmoothmenuStr = makeDefaultMenuConfig("");
        //out.println(ddsmoothmenuStr);
        
        //MlddMenu default

        
        out.println("/* =============== siteConfigStyle.css_import used overrides styles in default configs ============================================== */");

        List siteConfigStyles = SiteConfigStyleDS.getInstance().getBySiteId(site.getId());
        if ( siteConfigStyles != null && siteConfigStyles.size() > 0) {
            SiteConfigStyle siteConfigStyle = (SiteConfigStyle) siteConfigStyles.get(0);
            if ( siteConfigStyle.getCssImport() != null ) out.println(siteConfigStyle.getCssImport());
        }
        
        long end = System.currentTimeMillis();
        
        if ( m_debug) m_logger.debug("################################ END ########################################################################## " + (end-start));
    }

    
    protected StyleConfig getStyleForPanel(Panel panel, StyleTheme theme, boolean linkStyle, boolean dataStyle){
        
        
        return null;
    }
    
    protected void printTheme(long siteId, HttpServletRequest request, PrintWriter out){
        
        SiteConfig siteConfig = siteConfigDS.getSiteConfigBySiteId(siteId);

        SiteConfigStyle siteStyle = SiteConfigStyleDS.getInstance().getObjectBySiteId(siteId);
        if (siteStyle == null) return ;
        
        StyleTheme theme = StyleThemeDS.getInstance().getById(siteStyle.getThemeId());
        if (theme == null) return;
        
        List panels = PanelDS.getInstance().getBySiteId(siteId);

        Set idsPrinted = new HashSet();

        // Content Single frame style configurations. 
        // Single Frame
        out.println("/* Theme Style for contents */");
        StyleConfig contentSingStyleConfig = null;
        contentSingStyleConfig  = StyleConfigDS.getInstance().getById(theme.getSingleFrameStyleId());
        if ( contentSingStyleConfig != null) {
            
            String styleString = StyleUtil.makeCustomStyle("cntSingleFrame-" + theme.getTitle(), contentSingStyleConfig, siteConfig);
            out.println(styleString);
        }

        // Single subject
        contentSingStyleConfig  = StyleConfigDS.getInstance().getById(theme.getSingleSubjectStyleId());
        if ( contentSingStyleConfig != null) {
            
            String styleString = StyleUtil.makeCustomStyle("cntSingleSubject-" + theme.getTitle(), contentSingStyleConfig, siteConfig);
            out.println(styleString);
        }

        // Single data
        contentSingStyleConfig  = StyleConfigDS.getInstance().getById(theme.getSingleDataStyleId());
        if ( contentSingStyleConfig != null) {
            
            String styleString = StyleUtil.makeCustomStyle("cntSingleData-" + theme.getTitle(), contentSingStyleConfig, siteConfig);
            out.println(styleString);
        }
        
        out.println("/* Theme Style for Panels */");

        for (Iterator iterator = panels.iterator(); iterator.hasNext();) {
            Panel panel = (Panel) iterator.next();
            
            String styleKey = StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType());
            
            if ( panel.getPanelType() == PanelUtil.PANEL_CONTENT_DEFAULT || panel.getPanelType() == PanelUtil.PANEL_CONTENT_MID ){
                if ( !idsPrinted.contains(styleKey)){
                    StyleConfig styleConfig = null;
                    
                    out.println("/* Panel " + panel.getId() + " */");
                    styleConfig  = StyleConfigDS.getInstance().getById(theme.getContentPanelStyleId());
                    if (styleConfig != null) {
                        String styleString = StyleUtil.makePanelStyle("panel"+styleKey,  panel.getColumnNum(),  panel.getPanelType(), styleConfig, siteConfig, panel);
                        out.println(styleString);
                    }

                    out.println("/* PanelTitle " + panel.getId() + " */");
                    styleConfig  = StyleConfigDS.getInstance().getById(theme.getContentPanelTitleStyleId());
                    if ( styleConfig != null) {
                        String suffix = PanelUtil.getPanelTitleStyleSuffix(panel);
                        String styleString = StyleUtil.makePanelStyle(suffix,  panel.getColumnNum(),  panel.getPanelType(), styleConfig, siteConfig, panel, true);
                        out.println(styleString);
                    }
                    
                    out.println("/* PanelFrame " + panel.getId() + " */");
                    styleConfig  = StyleConfigDS.getInstance().getById(theme.getListFrameStyleId());
                    if ( styleConfig != null) {
                        
                        String styleString = StyleUtil.makePanelStyle("cntContFrame" + styleKey+"-cnt-frame",  panel.getColumnNum(),  panel.getPanelType(), styleConfig, siteConfig, panel);
                        out.println(styleString);
                    }
                    
                    // List subject
                    styleConfig  = StyleConfigDS.getInstance().getById(theme.getListSubjectStyleId());
                    if ( styleConfig != null) {
                        
                        String styleString = StyleUtil.makePanelStyle("cntListContSubject" + styleKey+"-list-cnt-sub",  panel.getColumnNum(),  panel.getPanelType(), styleConfig, siteConfig, panel);
                        out.println(styleString);
                    }
                    
                    // List data
                    styleConfig  = StyleConfigDS.getInstance().getById(theme.getListDataStyleId());
                    if ( styleConfig != null) {
                        
                        String styleString = StyleUtil.makePanelStyle("cntListContData"+styleKey+"-list-cnt-data",  panel.getColumnNum(),  panel.getPanelType(), styleConfig, siteConfig, panel);
                        out.println(styleString);
                    }
                    
                    idsPrinted.add(styleKey);
                }

            } else {
                if ( !idsPrinted.contains(styleKey)){
              
                    StyleConfig styleConfig  = StyleConfigDS.getInstance().getById(theme.getPanelStyleId());
                    if (styleConfig != null) {
                        String styleString = StyleUtil.makePanelStyle(styleKey,  panel.getColumnNum(),  panel.getPanelType(), styleConfig, siteConfig, panel);
                        out.println(styleString);
                    }
              
                    String linkStyleKey = StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType());
                    LinkStyleConfig linkStyleConfig  = LinkStyleConfigDS.getInstance().getById(theme.getPanelLinkStyleId());
                    if ( linkStyleConfig != null) {
                        String linkStyleString = StyleUtil.makePanelLinkStyle(linkStyleKey,  panel.getColumnNum(), panel.getPanelType(), theme.getTitle(), "", linkStyleConfig, siteConfig, panel);
                        out.println(linkStyleString);
                    }                
                    
                    styleConfig  = StyleConfigDS.getInstance().getById(theme.getPanelDataStyleId());
                    String dataStyleKey = StyleUtil.getPanelDataStringDivId(panel, styleConfig);
                    if ( styleConfig != null) {
                        String styleString = StyleUtil.makePanelDataItemStyle(styleConfig);
                        out.println(styleString);
                    }
                    
                    // Panel Title class
                    // Panel title has only class and can have only different style in case of theme. id would be theme id itself. 
                    styleConfig  = StyleConfigDS.getInstance().getById(theme.getPanelTitleStyleId());
                    String suffix = PanelUtil.getPanelTitleStyleSuffix(panel);
                    if ( styleConfig != null) {
                        String styleString = StyleUtil.makePanelStyle(suffix,  panel.getColumnNum(),  panel.getPanelType(), styleConfig, siteConfig, panel, true);
                        out.println(styleString);
                    }
                    
                    idsPrinted.add(styleKey);
                }
            }
        }
        
        
    }
    
    protected void printStyleSets2(long siteId, HttpServletRequest request, PrintWriter out){

        SiteConfig siteConfig = siteConfigDS.getSiteConfigBySiteId(siteId);

        SiteConfigStyle siteStyle = SiteConfigStyleDS.getInstance().getObjectBySiteId(siteId);
        if (siteStyle == null) return ;
        
        StyleTheme theme = StyleThemeDS.getInstance().getById(siteStyle.getThemeId());
        if (theme == null) return;
        
        List panels = PanelDS.getInstance().getBySiteId(siteId);

        Set idsPrinted = new HashSet();
        Set idsLinkPrinted = new HashSet();
        Set idsDataPrinted = new HashSet();
        
        
        for (Iterator iterator = panels.iterator(); iterator.hasNext();) {
            Panel panel = (Panel) iterator.next();
            
            if ( WebUtil.isNull(panel.getStyleDefaultCode()))
                continue;
            
            StyleSet styleSet = StyleSetDS.getInstance().getBySiteIdToName(panel.getSiteId(), panel.getStyleDefaultCode());
            
            if (styleSet == null){
                m_logger.info("StyleSet not Found set in a panel. panelId=" + panel.getId() + " styleset name=" + panel.getStyleDefaultCode());
                continue;
            }
            
            
            
            
            String styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), panel.getColumnNum(), panel.getPanelType());

            if (! idsPrinted.contains(styleSetStyleId)){
                StyleConfig styleConfig = StyleConfigDS.getInstance().getById(styleSet.getStyleId());
                String styleString = StyleUtil.makePanelStyle(styleSetStyleId, panel.getColumnNum(), panel.getPanelType(), styleConfig, siteConfig, null);
                out.println(styleString);
                idsPrinted.add(styleSetStyleId);
            }

            String styleSetStyleIdLink = StyleUtil.createStyleSetId(styleSet.getName(), panel.getColumnNum(), panel.getPanelType());
            
            if (! idsLinkPrinted.contains(styleSetStyleIdLink)){
                LinkStyleConfig styleLinkConfig = LinkStyleConfigDS.getInstance().getById(styleSet.getLinkStyleId());
                String linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleIdLink,  panel.getColumnNum(), panel.getPanelType(), styleSet.getName(), "", styleLinkConfig, siteConfig, null);
                out.println(linkStyleString);
                idsLinkPrinted.add(styleSetStyleIdLink);
            }            

            String styleSetStyleIdData = StyleUtil.createStyleSetId(styleSet.getName(), 0, panel.getPanelType()); // position is 0 means applicable all places
            
            if (! idsDataPrinted.contains(styleSetStyleIdData)){
                StyleConfig styleDataConfig = StyleConfigDS.getInstance().getById(styleSet.getDataStyleId());
                String dataStyleString = StyleUtil.makePanelDataItemStyle(styleDataConfig, styleSetStyleIdData);
                out.println(dataStyleString);
                idsDataPrinted.add(styleSetStyleIdData);
            }
        }        
        
    }   
    
    
    protected void printStyleSets(long siteId, HttpServletRequest request, PrintWriter out){
        
        StyleSetDS ds = StyleSetDS.getInstance();
        StyleConfigDS styleDS = StyleConfigDS.getInstance();
        LinkStyleConfigDS linkStyleDS = LinkStyleConfigDS.getInstance();
        SiteConfig siteConfig = siteConfigDS.getSiteConfigBySiteId(siteId);
        
        
        List list = ds.getBySiteId(siteId);
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            StyleSet styleSet = (StyleSet) iterator.next();


            // Panel-level Style
            StyleConfig styleConfig = styleDS.getById(styleSet.getStyleId());

            if ( styleConfig != null){
                
                String styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 1, PanelUtil.PANEL_CONTENTS);
                String styleString = StyleUtil.makePanelStyle(styleSetStyleId, 1, PanelUtil.PANEL_CONTENTS, styleConfig, siteConfig, null);
                out.println(styleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 1, PanelUtil.PANEL_MENU);
                styleString = StyleUtil.makePanelStyle(styleSetStyleId, 1, PanelUtil.PANEL_MENU, styleConfig, siteConfig, null);
                out.println(styleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 2, PanelUtil.PANEL_CONTENTS);
                styleString = StyleUtil.makePanelStyle(styleSetStyleId, 2, PanelUtil.PANEL_CONTENTS, styleConfig, siteConfig, null);
                out.println(styleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 3, PanelUtil.PANEL_CONTENTS);
                styleString = StyleUtil.makePanelStyle(styleSetStyleId, 3, PanelUtil.PANEL_CONTENTS, styleConfig, siteConfig, null);
                out.println(styleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 4, PanelUtil.PANEL_CONTENTS);
                styleString = StyleUtil.makePanelStyle(styleSetStyleId, 4, PanelUtil.PANEL_CONTENTS, styleConfig, siteConfig, null);
                out.println(styleString);
                
                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 5, PanelUtil.PANEL_CONTENTS);
                styleString = StyleUtil.makePanelStyle(styleSetStyleId, 5, PanelUtil.PANEL_CONTENTS, styleConfig, siteConfig, null);
                out.println(styleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 5, PanelUtil.PANEL_HEADER_MENU);
                styleString = StyleUtil.makePanelStyle(styleSetStyleId, 5, PanelUtil.PANEL_HEADER_MENU, styleConfig, siteConfig, null);
                out.println(styleString);
                
                
                if (m_debug) m_logger.debug("StyleSet [" + styleSet.getName() + "] StyleString=" + styleString);
            } else {
                m_logger.error("StyleConfig not found for " + styleSet.getStyleId() + " in StyleSet " + styleSet.getId());
            }

            // Link Item Style
            LinkStyleConfig linkStyleConfig = linkStyleDS.getById(styleSet.getLinkStyleId());
            if (linkStyleConfig != null){
                String styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 1, PanelUtil.PANEL_CONTENTS);
                String linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  1, PanelUtil.PANEL_CONTENTS, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
                out.println(linkStyleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 1, PanelUtil.PANEL_MENU);
                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  1, PanelUtil.PANEL_MENU, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
                out.println(linkStyleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 2, PanelUtil.PANEL_CONTENTS);
                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  2, PanelUtil.PANEL_CONTENTS, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
                out.println(linkStyleString);

//                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 2, PanelUtil.PANEL_MENU);
//                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  2, PanelUtil.PANEL_MENU, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
//                out.println(linkStyleString);
                
                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 3, PanelUtil.PANEL_CONTENTS);
                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  3, PanelUtil.PANEL_CONTENTS, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
                out.println(linkStyleString);

//                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 3, PanelUtil.PANEL_MENU);
//                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  3, PanelUtil.PANEL_MENU, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
//                out.println(linkStyleString);
                
                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 4, PanelUtil.PANEL_CONTENTS);
                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  4, PanelUtil.PANEL_CONTENTS, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
                out.println(linkStyleString);

//                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 4, PanelUtil.PANEL_MENU);
//                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  4, PanelUtil.PANEL_MENU, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
//                out.println(linkStyleString);
                
                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 5, PanelUtil.PANEL_CONTENTS);
                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  5, PanelUtil.PANEL_CONTENTS, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
                out.println(linkStyleString);

                styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 5, PanelUtil.PANEL_HEADER_MENU);
                linkStyleString = StyleUtil.makePanelLinkStyle(styleSetStyleId,  5, PanelUtil.PANEL_HEADER_MENU, styleSet.getName(), "", linkStyleConfig, siteConfig, null);
                out.println(linkStyleString);
                
                if (m_debug) m_logger.debug("StyleSet [" + styleSet.getName() + "] LinkStyleString=" + linkStyleString);
            } else {
                m_logger.error("linkStyleConfig not found for " + styleSet.getLinkStyleId() + " in StyleSet " + styleSet.getId());
            }
            
            
            // Data Item Style
            StyleConfig styleDataConfig = styleDS.getById(styleSet.getDataStyleId());

            if ( styleConfig != null){
                String styleSetStyleId = StyleUtil.createStyleSetId(styleSet.getName(), 0, PanelUtil.PANEL_MENU); // position is 0 means applicable all places

                String dataStyleString = StyleUtil.makePanelDataItemStyle(styleDataConfig, styleSetStyleId);
                out.println(dataStyleString);
                if (m_debug) m_logger.debug("StyleSet [" + styleSet.getName() + "] DataStyleString=" + dataStyleString);
            } else {
                m_logger.error("DataStyleConfig not found for " + styleSet.getDataStyleId() + " in StyleSet " + styleSet.getId());
            }
        }
    }
    
    protected void printPanelStyle(long siteId, HttpServletRequest request, PrintWriter out){

        //Regular Style for Panels
        List styles = PanelStyleDS.getInstance().getBySiteId(siteId);
        
        StyleConfigDS styleDS = StyleConfigDS.getInstance();
        SiteConfig siteConfig = siteConfigDS.getSiteConfigBySiteId(siteId);
        
        out.println("/* Regular Style */");
        for (Iterator iterator = styles.iterator(); iterator.hasNext();) {
            PanelStyle panelStyle = (PanelStyle) iterator.next();
            
            long styleId = panelStyle.getStyleId();
            
            StyleConfig styleConfig = styleDS.getById(styleId);
            
            if (styleConfig != null){
                Panel panel = panelDS.getById(panelStyle.getPanelId());
                if (panel != null && panel.getHide() != 1) {
                    String strylId = PanelUtil2.getPanelStyleDefaultString(panel) + "-psc-"+styleConfig.getId(); 
                    String panelStyleString = StyleUtil.makePanelStyle(strylId, panel.getColumnNum(), panel.getPanelType(), styleConfig, siteConfig, panel);
                    out.println(panelStyleString);
                }
            }
        }

        // Link Styles for Panels
        List linkStyles = PanelLinkStyleDS.getInstance().getBySiteId(siteId);
        LinkStyleConfigDS linkStyleDS = LinkStyleConfigDS.getInstance();
        out.println("/* Link Style */");

        for (Iterator iterator = linkStyles.iterator(); iterator.hasNext();) {
            PanelLinkStyle panelLinkStyle = (PanelLinkStyle) iterator.next();
            
            long styleId = panelLinkStyle.getStyleId();
            
            LinkStyleConfig linkStyleConfig = linkStyleDS.getById(styleId);
            if (linkStyleConfig != null){
                if ( m_debug) m_logger.debug("###################### linkStyleConfig = " + linkStyleConfig.getId());
                Panel panel = panelDS.getById(panelLinkStyle.getPanelId());
                
                if (panel != null  && panel.getHide() != 1) {
                    
                    String strylId =  PanelUtil2.getPanelStyleDefaultString(panel) + "-plsc-"+linkStyleConfig.getId(); 
                    String panelStyleSuffix = PanelUtil.getPanelMenuStyleSuffix(panel.getId());
                    String panelNavStyleSuffix = (panel.getStyleString() == null || panel.getStyleString().length()<=1)?"":""+panel.getId();
                    
                    String panelStyleString = StyleUtil.makePanelLinkStyle(strylId,  panel.getColumnNum(), panel.getPanelType(),panelStyleSuffix, panelNavStyleSuffix, linkStyleConfig, siteConfig, panel);
                    out.println(panelStyleString);
                    
                    if ( panel.getPanelType() == PanelUtil.PANEL_MENU) {
//                        String ddsmoothmenuStr = makeSmoothMenuConfig("panel"+panel.getId(), linkStyleConfig);
//                        out.println(ddsmoothmenuStr);
//                        String ddsmoothmenuStr = makeMlddMenuConfig("panel"+panel.getId(), linkStyleConfig);
//                        out.println(ddsmoothmenuStr);
                    }
                }
            }
        }
        
        // Panels to print nav style strings only. 
        List panels = panelDS.getBySiteId(siteId);
        out.println("/* Nav Style */");
        
        for (Iterator iterator = panels.iterator(); iterator.hasNext();) {
            Panel panel = (Panel) iterator.next();
            
            if ( panel.getHide() == 1) continue;
            if ( ! PanelUtil.isMenuPanel(panel) ) continue;
            String panelStyleSuffix = PanelUtil.getPanelMenuStyleSuffix(panel.getId());
            String panelNavStyleString = StyleUtil.makePanelNavStyleString(panel, panelStyleSuffix);
            out.println(panelNavStyleString);
            
        }

        // Print entry styles stored in styleConfigString 2 
        panels = panelDS.getBySiteId(siteId);
        Set configs = new HashSet();
        out.println("/* DATA ITEM STYLE specified in getStyleString2() Style. This is set by StyleString2 menu */");

        for (Iterator iterator = panels.iterator(); iterator.hasNext();) {
            Panel panel = (Panel) iterator.next();
            
            if (panel.getHide() == 1) continue;
            if (WebUtil.isNull(panel.getStyleString2())) continue;

            StyleConfig styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(panel.getStyleString2());
            if (styleConfig == null) continue;
            if ( configs.contains(new Long(styleConfig.getId()))) continue;
            
            String panelDataItemStyleStr = StyleUtil.makePanelDataItemStyle(styleConfig);

            if (m_debug) m_logger.debug("DataItem style Config for " + panel.getId()+ ":" + panelDataItemStyleStr);
            out.println(panelDataItemStyleStr);
            configs.add(new Long(styleConfig.getId()));
        }
        
        
    }

    protected String makeDefaultMenuConfig(String configKeyId){
        LinkStyleConfig config = new LinkStyleConfig();
        config.setBackground("#e0e0e0");
        config.setColor("white");
        config.setHovBackground("maroon");
        config.setHovColor("white");
        
        return makeSmoothMenuConfig(configKeyId, config);
    }
    
    protected String makeSmoothMenuConfig(String configKeyId, LinkStyleConfig config){
        StringBuffer buf = new StringBuffer();

        String configSelector = "." + configKeyId + "ddsmoothmenu-v";

        buf.append(configSelector).append(" ul{margin: 0; padding: 0; list-style-type: none; border-bottom: 1px solid #ccc; }").append("\n");
        buf.append(configSelector).append(" ul{width: 175px; font: bold 12px Verdana; }").append("\n");
        buf.append(configSelector).append(" ul li{ position: relative; }").append("\n");
        buf.append(configSelector).append(" ul li a{ display: block; overflow: auto; color: "+ config.getColor()+"; text-decoration: none; padding: 6px; border-bottom: 1px solid #778; border-right: 1px solid #778; }").append("\n");

        buf.append(configSelector).append(" ul li a:link,").append(configSelector).append(" ul li a:visited{background: "+ config.getBackground()+"; color: "+ config.getColor()+"; }").append("\n");
        buf.append(configSelector).append(" ul li a:active,").append(configSelector).append(" ul li a.active{background: "+ config.getHovBackground()+"; color: "+ config.getHovColor()+";}").append("\n");
        buf.append(configSelector).append(" ul li a.selected{background: "+ config.getHovBackground()+"; color: "+ config.getHovColor()+";}").append("\n");
        buf.append(configSelector).append(" ul li a:hover{background: "+ config.getHovBackground()+"; color: "+ config.getHovColor()+"; }").append("\n");
        buf.append(configSelector).append(" ul li ul{position: absolute; width: 170px; top: 0; font-weight: normal; visibility: hidden;}").append("\n");
         
        // Holly Hack for IE

        buf.append("* html ").append(configSelector).append(" ul li { float: left; height: 1%; }").append("\n");
        buf.append("* html ").append(configSelector).append(" ul li a { height: 1%; }").append("\n");
        
        
/*        
        buf.append(configSelector).append(" ul{margin: 0; padding: 0; list-style-type: none; border-bottom: 1px solid #ccc; }").append("\n");
        buf.append(configSelector).append(" ul{width: 175px; font: bold 12px Verdana; }").append("\n");
        buf.append(configSelector).append(" ul li{ position: relative; }").append("\n");
        buf.append(configSelector).append(" ul li a{ display: block; overflow: auto; color: white; text-decoration: none; padding: 6px; border-bottom: 1px solid #778; border-right: 1px solid #778; }").append("\n");

        buf.append(configSelector).append(" ul li a:link,").append(configSelector).append(" ul li a:visited{background: #414141; color: white; }").append("\n");
        buf.append(configSelector).append(" ul li a:active,").append(configSelector).append(" ul li a.active{background: maroon; color: white;}").append("\n");
        buf.append(configSelector).append(" ul li a.selected{background: blue; color: white;}").append("\n");
        buf.append(configSelector).append(" ul li a:hover{background: black; color: white; }").append("\n");
        buf.append(configSelector).append(" ul li ul{position: absolute; width: 170px; top: 0; font-weight: normal; visibility: hidden;}").append("\n");
         
        // Holly Hack for IE

        buf.append("* html ").append(configSelector).append(" ul li { float: left; height: 1%; }").append("\n");
        buf.append("* html ").append(configSelector).append(" ul li a { height: 1%; }").append("\n");
*/

        configSelector = "." + configKeyId + "ddsmoothmenu";
        buf.append(configSelector).append("{font: bold 12px Verdana; background: #414141; width: 1000px;}").append("\n");
        buf.append(configSelector).append(" ul{z-index:0; margin: 0; padding: 0; list-style-type: none;}").append("\n");
        buf.append(configSelector).append(" ul li{position: relative; display: inline; float: left;}").append("\n");
        buf.append(configSelector).append(" ul li a{display: block; background: #414141; color: white; padding: 8px 10px; border-right: 1px solid #778; color: #2d2b2b; text-decoration: none;}").append("\n");
        buf.append(configSelector).append(" ul li a:link, ").append(configSelector).append(" ul li a:visited{color: white;}").append("\n");
        buf.append(configSelector).append(" ul li a.selected{background: black; color: white;}").append("\n");
        buf.append(configSelector).append(" ul li a:hover{background: black; color: white;}").append("\n");
        buf.append(configSelector).append(" ul li ul{position: absolute; left: 0; display: none; visibility: hidden;}").append("\n");
        buf.append(configSelector).append(" ul li ul li{display: list-item; float: none;}").append("\n");
        buf.append(configSelector).append(" ul li ul li ul{top: 0;}").append("\n");
        buf.append(configSelector).append(" ul li ul li a{font: normal 13px Verdana; width: 160px; padding: 5px; margin: 0; border-top-width: 0; border-bottom: 1px solid gray;}").append("\n");
        buf.append("* html ").append(configSelector).append("{height: 1%;}").append("\n");
        buf.append("* html ").append(configSelector).append(" ul li a{display: inline-block;}").append("\n");


        /* ######### CSS classes applied to down and right arrow images  ######### */

        buf.append(".downarrowclass{position: absolute; top: 12px; right: 7px;}").append("\n");
        buf.append(".rightarrowclass{position: absolute; top: 6px; right: 5px;}").append("\n");

        /* ######### CSS for shadow added to sub menus  ######### */

        buf.append(".ddshadow{position: absolute; left: 0; top: 0; width: 0; height: 0; background: silver;}").append("\n");
        buf.append(".toplevelshadow{ opacity: 0.8; }").append("\n");        
        
        return buf.toString();
    }

    
    
    protected String makeSexyDDMenuConfig(String configKeyId, LinkStyleConfig config){
        StringBuffer buf = new StringBuffer();
        String configSelector = "#menuh" + configKeyId;
        
        buf.append(configSelector).append("ul.sd-topnav {  list-style: none;   padding: 0px;   margin: 0;  float: left;    width: 1200px;  background: #222;   font-size: 1.2em;   background: url(/menuscripts/sexydd/topnav_bg.gif) repeat-x; }");
        buf.append(configSelector).append("ul.sd-topnav li {   float: left;    margin: 0;  padding: 0 15px 0 0;    position: relative;}");
        buf.append(configSelector).append("ul.sd-topnav li a { padding: 10px 5px;  color: #fff;    display: block; text-decoration: none;  float: left;}");
        buf.append(configSelector).append("ul.sd-topnav li a:hover {   background: url(/menuscripts/sexydd/topnav_hover.gif) no-repeat center top; }");
        buf.append(configSelector).append("ul.sd-topnav li span { width: 17px; height: 35px;   float: left;    background: url(/menuscripts/sexydd/subnav_btn.gif) no-repeat center top;}");
        buf.append(configSelector).append("ul.sd-topnav li span.sd-subhover {  background-position: center bottom; cursor: pointer;}"); 
        buf.append(configSelector).append("ul.sd-topnav li ul.sd-subnav {  list-style: none;   position: absolute; left: 0;    top: 35px;  background: #333;   margin: 0;  padding: 0; display: none;  float: left;    width: 170px;   border: 1px solid #111;}");
        buf.append(configSelector).append("ul.sd-topnav li ul.sd-subnav li {   margin: 0;  padding: 0; border-top: 1px solid #252525; border-bottom: 1px solid #444; clear: both;  width: 170px;}");
        buf.append(configSelector).append("html ul.sd-topnav li ul.sd-subnav li a {    float: left;    width: 145px;   background: #333 url(/menuscripts/sexydd/dropdown_linkbg.gif) no-repeat 10px center;    padding-left: 20px;}");
        buf.append(configSelector).append("html ul.sd-topnav li ul.sd-subnav li a:hover {background: #222 url(/menuscripts/sexydd/dropdown_linkbg.gif) no-repeat 10px center;}");
        
        return buf.toString();
    }    
    
    public static void main(String[] args) {

        long siteId = 29;
        List styles = PanelStyleDS.getInstance().getBySiteId(siteId);
        
        StyleConfigDS styleDS = StyleConfigDS.getInstance();
        SiteConfigDS.getInstance();
        SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(siteId);
        
        for (Iterator iterator = styles.iterator(); iterator.hasNext();) {
            PanelStyle panelStyle = (PanelStyle) iterator.next();
            
            long styleId = panelStyle.getStyleId();
            
            StyleConfig styleConfig = styleDS.getById(styleId);
            
            if (styleConfig != null){
                Panel panel = PanelDS.getInstance().getById(panelStyle.getPanelId());
                if (panel != null && panel.getHide() != 1) {
                    String panelStyleString = StyleUtil.makePanelStyle(panelStyle.getPanelId(), panel.getColumnNum(), panel.getPanelType(), styleConfig, siteConfig, panel);
                    System.out.println(panelStyleString);
                }
            }
        }
        
    }
    
    private static Logger m_logger = Logger.getLogger(CustomScriptsServlet.class);
    
    
    protected SiteDS siteDS;
    protected SiteConfigDS siteConfigDS;
    protected PanelDS panelDS;
    protected LinkStyleConfigDS linkStyleConfigDS;
}
