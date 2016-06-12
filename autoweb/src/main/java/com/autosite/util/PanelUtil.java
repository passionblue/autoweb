package com.autosite.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.autosite.db.Content;
import com.autosite.db.LinkStyleConfig;
import com.autosite.db.Page;
import com.autosite.db.PageContentConfig;
import com.autosite.db.Panel;
import com.autosite.db.PanelLinkStyle;
import com.autosite.db.PanelStyle;
import com.autosite.db.Site;
import com.autosite.db.StyleConfig;
import com.autosite.db.StyleSetContent;
import com.autosite.db.StyleTheme;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.ds.PageContentConfigDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.PanelDS;
import com.autosite.ds.PanelLinkStyleDS;
import com.autosite.ds.PanelStyleDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.ds.StyleSetContentDS;
import com.autosite.servlet.StyleUtil;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class PanelUtil {
    
    // Panel Types
    public static final int PANEL_DEFAULT = 0;
    public static final int PANEL_CONTENTS = 1;
    public static final int PANEL_MENU = 2;
    public static final int PANEL_HEADER = 3;
    public static final int PANEL_HEADER_MENU = 4;
    public static final int PANEL_HEADER_DIVIDER = 5;
    public static final int PANEL_FOOTER_MENU = 6;
    
    
    //Panel Type. These are also loaded from dropmenu configs. menus are in addPage

    public static final int PANEL_COL_MENU = 1;
    public static final int PANEL_COL_CONTENT_BOTTOM = 2;
    public static final int PANEL_COL_MID = 3;
    public static final int PANEL_COL_AD = 4;
    public static final int PANEL_COL_HEADER_BOTTOM = 5;
    public static final int PANEL_COL_HEADER_TOP = 6;
    public static final int PANEL_COL_CONTENT_TOP = 7;
    public static final int PANEL_COL_FOOTER = 9;
    
    public static final int PANEL_CONTENT_DEFAULT = 10;
    public static final int PANEL_CONTENT_MID = 11;
    
    public static final int PANEL_TOP_FRAME = 41;
    public static final int PANEL_FREE= 99;
    
    public static final int PANEL_BLOGGING = 21;
    
    public static final int PANEL_MENU_PAGE_CATEGORY = 31;
    public static final int PANEL_MENU_CONTENT_LINKS = 32;
    
    public static final int PANEL_CHURCH_TOPBOARD = 101;
    
    
    // Panel Position Id. getColumnNum() in Panel object
    public static final int POS_MENU_COLUMN = 1;
    public static final int POS_CONTENT_COLUMN = 2;
    public static final int POS_MID_COLUMN = 3;
    public static final int POS_AD_COLUMN = 4;
    public static final int POS_HEADER = 5;
    public static final int POS_CONTENT_TOP = 6;
    public static final int POS_TOP_FRAME = 7;
    
    
    public static final int PANEL_MENU_REGULAR      = 1;
    public static final int PANEL_MENU_DDLEVEL      = 2;
    public static final int PANEL_MENU_DDSMOOTH     = 3;
    public static final int PANEL_MENU_SUPERFISH    = 4;
    public static final int PANEL_MENU_SEXYDD       = 5;
    
    public static final int PANEL_ALIGN_VERTICAL        = 0;
    public static final int PANEL_ALIGN_HORIZONTAL      = 1;
    
    public static final int PANEL_WIDTH_DIV_WHOLE       = 1;
    public static final int PANEL_WIDTH_DIV_MENU        = 2;
    public static final int PANEL_WIDTH_DIV_CONTENT     = 3;
    public static final int PANEL_WIDTH_DIV_MID         = 4;
    public static final int PANEL_WIDTH_DIV_AD          = 5;
    
    
    public static String getPanelPage(Panel panel){
        return getPanelPage(panel.getPanelType(), panel.getPanelSubType());
    }
    
    public static String getPanelPage(int type) {
        return getPanelPage(type, 0);
    }
    
    public static String getPanelPage(int type, int subType) {

        switch (type) {
        case PANEL_CONTENTS:
            return "/jsp/layout/panel_new/panel.jsp";
        case PANEL_MENU:
            if (subType == 0) return "/jsp/layout/panel/panel_menu_mldd.jsp";
            if (subType == 1) return "/jsp/layout/panel_new/panel_menu.jsp";
            if (subType == 2) return "/jsp/layout/panel/panel_menu_mldd.jsp";
            //if (subType == 2) return "/jsp/layout/panel/panel_menu_accordian.jsp";
            if (subType == 3) return "/jsp/layout/panel_new/panel_menu.jsp";
        case PANEL_HEADER:
            return "/jsp/layout/panel/panel_header.jsp";
        case PANEL_HEADER_MENU:
            if (subType == 0) return "/jsp/layout/panel/panel_header_menu_mldd.jsp";
            if (subType == 1) return "/jsp/layout/panel_new/panel_header_menu.jsp";
            if (subType == 2) return "/jsp/layout/panel/panel_header_menu_sexydd.jsp";
            if (subType == 3) return "/jsp/layout/panel_new/panel_header_menu.jsp";

        case PANEL_HEADER_DIVIDER:
            return "/jsp/layout/panel/panel_divider.jsp";

//        case PANEL_FOOTER_MENU:
//            return "/jsp/layout/panel/panel_footer_dyn_menu.jsp";

        case PANEL_FOOTER_MENU:
            return "/jsp/layout/panel/panel_footer_dyn_menu.jsp";
            
        case PANEL_CONTENT_DEFAULT:
            return "/jsp/layout/panel/panel_dynamic_content.jsp";

        case PANEL_BLOGGING:
            if (subType == 0) return "/jsp/layout/panel/panel_header_menu_mldd.jsp";
            if (subType == 1) return "/jsp/layout/panel/panel_header_menu.jsp";

        case  PANEL_MENU_PAGE_CATEGORY:
            return "/jsp/layout/panel/sp/panel_menu_page_category.jsp";

        case  PANEL_MENU_CONTENT_LINKS:
            return "/jsp/layout/panel/sp/panel_content_links.jsp";
            
        case 99:
            return "/jsp/layout/panel/panel_free.jsp";
            
        case PANEL_CHURCH_TOPBOARD:
            return "/jsp/form_chur/panel/panel_static.jsp";
            
        default:
            return "/jsp/layout/panel/panel.jsp";
        }
    }

    public static StyleConfig getPanelStyleConfig(long panelId) {
        List panelStyles = PanelStyleDS.getInstance().getByPanelId(panelId);

        if (panelStyles.size() == 0) {
            return null;
        }

        if (panelStyles.size() > 1) {
            // ERROR
        }

        StyleConfigDS styleConfigDS = StyleConfigDS.getInstance();
        if (panelStyles.size() > 0) {
            PanelStyle panelStyle = (PanelStyle) panelStyles.get(0);
            StyleConfig styleConfig = (StyleConfig) styleConfigDS.getById(panelStyle.getStyleId());
            return styleConfig;
        }

        return null;
    }

    public static PanelStyle getPanelStyleObject(long panelId) {
        List panelStyles = PanelStyleDS.getInstance().getByPanelId(panelId);

        if (panelStyles.size() == 0) {
            return null;
        }

        if (panelStyles.size() > 1) {
            // ERROR
        }

        PanelStyle panelStyle = (PanelStyle) panelStyles.get(0);
        return panelStyle;
    }

    public static LinkStyleConfig getPanelLinkStyleConfig(long panelId) {
        List panelLinkStyles = PanelLinkStyleDS.getInstance().getByPanelId(panelId);

        if (panelLinkStyles.size() == 0) {
            return null;
        }

        if (panelLinkStyles.size() > 1) {
            // ERROR
        }

        LinkStyleConfigDS linkStyleConfigDS = LinkStyleConfigDS.getInstance();
        PanelLinkStyle linkPanelStyle = (PanelLinkStyle) panelLinkStyles.get(0);
        LinkStyleConfig linkStyleConfig = (LinkStyleConfig) linkStyleConfigDS.getById(linkPanelStyle.getStyleId());
        return linkStyleConfig;

    }
    
    public static PanelLinkStyle getPanelLinkStyleObject(long panelId) {
        List panelLinkStyles = PanelLinkStyleDS.getInstance().getByPanelId(panelId);

        if (panelLinkStyles.size() == 0) {
            return null;
        }

        if (panelLinkStyles.size() > 1) {
            // ERROR
        }

        PanelLinkStyle linkPanelStyle = (PanelLinkStyle) panelLinkStyles.get(0);
        return linkPanelStyle;
    }

//===============================================================================================================================
// Getting Panel Style Suffix
//===============================================================================================================================
    
    public static String getPanelTitleStyleSuffix(Panel panel){
        return getPanelTitleStyleSuffix(panel, "");
    }

    public static String getPanelTitleStyleSuffix(Panel panel, String defaultStr){
        
        if ( panel == null) return defaultStr;
        
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());

        if ( theme != null) {
            if ( panel.getPanelType() == PanelUtil.PANEL_CONTENT_DEFAULT || panel.getPanelType() == PanelUtil.PANEL_CONTENT_MID ){
                return theme.getTitle() + "-C-" + panel.getPanelType();
            } else {
                return theme.getTitle();
            }
        }
        
        return defaultStr;
    }    
    
    
    // 1. Check if styleConfig is associated
    // 2. If so, returns panel ID. 
    // 3. If not, return code returned by StyleUtil.createStyleSetId(panel);
    
    public static String getPanelStyleSuffix(long panelId){
        Panel panel = PanelDS.getInstance().getById(panelId);
        return getPanelStyleSuffix(panel);
    }

    public static String getPanelStyleSuffix(Panel panel){
        
        String defaultStr = getPanelStyleDefaultString(panel);
        if ( panel == null) return defaultStr;
        
        
        StyleConfig styleConfig = PanelUtil.getPanelStyleConfig(panel.getId());
        if (styleConfig != null) 
            return ""+panel.getId();
        
        // Style Set
        if (WebUtil.isNotNull(panel.getStyleDefaultCode()))
            return StyleUtil.createStyleSetId(panel);
            
        // Theme Check
        // Finally check the theme whether this site has a site-wide theme. 
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
        
        if (theme != null){
            return StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType());
        }
        
        return defaultStr;
    }    

    public static String getPanelLinkStyleSuffix(Panel panel){
        String defaultStr = getPanelStyleDefaultString(panel);
        if ( panel == null) return defaultStr;

        LinkStyleConfig styleConfig = PanelUtil.getPanelLinkStyleConfig(panel.getId());
        if (styleConfig != null) 
            return ""+panel.getId();
        
        // Style Set
        if (WebUtil.isNotNull(panel.getStyleDefaultCode()))
            return StyleUtil.createStyleSetId(panel);
            
        // Theme Check
        // Finally check the theme whether this site has a site-wide theme. 
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
        
        if (theme != null){
            return StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType());
        }
        
        return defaultStr;
    }
    
    
    public static String getPanelMenuStyleSuffix(long panelId){
        return getPanelStyleSuffix(panelId);
    }

    public static String getPanelMenuStyleSuffix(Panel panel){
        return getPanelStyleSuffix(panel);
    }
    
    public static String getPanelMenuLinkStyleSuffix(long panelId){
        Panel panel = PanelDS.getInstance().getById(panelId);
        return getPanelMenuLinkStyleSuffix(panel);
    }
    
    public static String getPanelMenuLinkStyleSuffix(Panel panel){
        if ( panel == null) return "";
        LinkStyleConfig styleConfig = PanelUtil.getPanelLinkStyleConfig(panel.getId());
        if (styleConfig != null) 
            return ""+panel.getId();
        
        // Style Set
        if (WebUtil.isNotNull(panel.getStyleDefaultCode()))
            return StyleUtil.createStyleSetId(panel);
            
        // Theme Check
        // Finally check the theme whether this site has a site-wide theme. 
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
        
        if (theme != null){
            return StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType());
        }
        
        return "";
    }
    
//    public static String getPanelMenuNavStyleSuffix(long panelId){
//        Panel panel = PanelDS.getInstance().getById(panelId);
//        if (panel == null|| panel.getStyleString() == null || panel.getStyleString().length()<=0) {
//            return "";
//        }
//        return ""+panelId;
//    }
//
//    public static String getPanelMenuNavStyleSuffix(Panel panel){
//        if ( panel == null) return "";
//        StyleConfig styleConfig = PanelUtil.getPanelStyleConfig(panel.getId());
//        if (styleConfig == null) {
//            if (WebUtil.isNotNull(panel.getStyleDefaultCode()))
//                return StyleUtil.createStyleSetId(panel);
//            else
//            return "";
//        }
//        return ""+panel.getId();
//    }
    
    
    public static String getPanelSmoothMenuLinkStyleSuffix(long panelId){
        LinkStyleConfig styleConfig = PanelUtil.getPanelLinkStyleConfig(panelId);
        if (styleConfig == null) {
            return "";
        }
        return "panel"+panelId;
    }

    
    public static String getPanelStyleDefaultString(Panel panel){
        if ( panel == null) return "default";
        
        switch(panel.getPanelType()){
        case PANEL_MENU: return "";
        case PANEL_HEADER_MENU: return "";
        case PANEL_FOOTER_MENU: return "";
        
        case PANEL_MENU_CONTENT_LINKS: return "defaultcontentlinks";
        default: return "default";
        }
    }
    
  //===============================================================================================================================
 // Getting Panel Style Suffix
 //===============================================================================================================================
    
    
    // Cotent style suffix getter for data and list data
    public static String getPanelContListFrameStyleSuffix(Panel panel){
        return getPanelContListFrameStyleSuffix(panel, "");
    }
    
    public static String getPanelContListFrameStyleSuffix(Panel panel, String defaultStr){
        if ( panel == null) return defaultStr;
            
        // Theme Check
        // Finally check the theme whether this site has a site-wide theme. 
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
        
        if (theme != null){
            return StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType()) + "-cnt-frame";
        }
        
        return defaultStr;
    }
    
    
    public static String getPanelContListSubjectStyleSuffix(Panel panel){
        return getPanelContListSubjectStyleSuffix(panel, "");
    }
    
    public static String getPanelContListSubjectStyleSuffix(Panel panel, String defaultStr){
        if ( panel == null) return defaultStr;
            
        // Theme Check
        // Finally check the theme whether this site has a site-wide theme. 
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
        
        if (theme != null){
            return StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType()) + "-list-cnt-sub";
        }
        
        return defaultStr;
    }

    public static String getPanelContListDataStyleSuffix(Panel panel){
        return getPanelContListDataStyleSuffix(panel, "");
    }
    
    public static String getPanelContListDataStyleSuffix(Panel panel, String defaultStr){
        if ( panel == null) return defaultStr;
        
        // Theme Check
        // Finally check the theme whether this site has a site-wide theme. 
        StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
        
        if (theme != null){
            return StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType()) + "-list-cnt-data";
        }
        
        return defaultStr;
    }
    
    // kind 1=frame 2=subject 3=body
    public static String getSingleContentStyleSuffix(Content content){
        if (content == null) return "";
        
        PageContentConfig c = PageContentConfigDS.getInstance().getObjectByContentId(content.getId());
        StyleSetContent styleSet = StyleSetContentDS.getInstance().getById(c==null?0:c.getContentStyleSetId());

        StyleConfig styleConfig = null;
        if (styleSet!= null) {
            return ""; // This content will be styled by content style set. div will be prefixed not being suffixed. 
//            switch(kind){
//            case 1: styleConfig = StyleConfigDS.getInstance().getById(styleSet.getFrameStyleId()); break;
//            case 2: styleConfig = StyleConfigDS.getInstance().getById(styleSet.getSubjectStyleId()); break;
//            case 3: styleConfig = StyleConfigDS.getInstance().getById(styleSet.getDataStyleId()); break;
//            }
//            
//            if (styleConfig != null)
//                return kindTokStr + styleConfig.getId();
        }
        
        StyleTheme theme = SiteStyleUtil.getTheme(content.getSiteId());
        
        if (theme != null){
//            switch(kind){
//            case 1: styleConfig = StyleConfigDS.getInstance().getById(theme.getSingleFrameStyleId()); break;
//            case 2: styleConfig = StyleConfigDS.getInstance().getById(theme.getSingleSubjectStyleId()); break;
//            case 3: styleConfig = StyleConfigDS.getInstance().getById(theme.getSingleDataStyleId()); break;
//            }
            return "-" + theme.getTitle();
        }
        
        return "";
    }

    
    
    
//===============================================================================================================================
// Getting Panel Style Suffix
//===============================================================================================================================
    
    public static StyleConfig getDefaultPagePanelStyleConfig(){
        StyleConfig ret = new StyleConfig();
        
        ret.setBorderColor("green");
        ret.setBorderStyle("solid");
        ret.setBorderWidth("1");
        
        return ret;
    }
    
    public static boolean isSidePanel(Panel panel){
        if (panel == null) return false;
        
        switch(panel.getColumnNum()){
        case 1: case 3: case 4: return true;
        default : return false;
        }
        
    }

    public static boolean isHideByPage(Panel panel, Page dynPage){
        boolean hideByPageOnlyOption = false;
        
        long pageId  = (dynPage!=null?dynPage.getId():0);
        
        // Check if this panel is hidden by page view option for panel. 
        if (panel.getPageOnly() == 1 && panel.getPageId() != pageId && dynPage != null){
            if (!PanelUtil.isSameGroupView(panel, dynPage.getId()))
                hideByPageOnlyOption = true;    
        }
      
        return hideByPageOnlyOption;
    }
    
    
    public static boolean isHideByPageExclusive(Panel panel, Page dynPage){

        boolean showPageOnlyExclusively = false;
        if (dynPage != null && PanelUtil.isSidePanel(panel)) {
            showPageOnlyExclusively = WebParamUtil.getBooleanValue(dynPage.getShowPageExclusiveOnly()); 
        }
        
        if (showPageOnlyExclusively){
            if (panel.getPageOnly() != 1 || panel.getPageId() != dynPage.getId()){
                return true;
            }
        }

        return false;
    }
    
    public static boolean isMenuPanel(Panel panel){
        return  panel.getPanelType() == PanelUtil.PANEL_HEADER_MENU ||  panel.getPanelType() == PanelUtil.PANEL_MENU;
    }
    
    public static boolean isHideByPrintableFormat(HttpServletRequest request, Panel panel){
        
        if (WebUtil.isPritintable(request)){
            if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
                return true;
        } else {
            if (panel.getShowOnlyPrint() == 1)
                return true;
        }   
        return false;

    }
    
    public static boolean isSameGroupView(Panel panel, long targetPageId){
        
        long pageId = panel.getPageId();
        
        if (!WebUtil.isTrue(panel.getPageOnlyGroup())){
            return false;
        }
        
        List panelsWithPageOnlyView = PanelDS.getInstance().getByPageId(pageId);
        PageDS pageDS = PageDS.getInstance();
        
        for (Iterator iterator = panelsWithPageOnlyView.iterator(); iterator.hasNext();) {
            Panel otherPanel = (Panel) iterator.next();
            
            if (otherPanel.getPageOnly() == 0) continue;
            
            List pages = pageDS.getByMenuPanelId(otherPanel.getId());
            for (Iterator iterator2 = pages.iterator(); iterator2.hasNext();) {
                Page page = (Page) iterator2.next();
                
                if (page.getId() == targetPageId) return true;
            }
        }
        
        return false;
    }
    
    
    public static boolean showNow(HttpSession session, HttpServletRequest request, Panel panel){

        // Show Hidden, if no showhidden attr, set it true by default. can be turned off with cmd_hideHidden cmd.  
        boolean showHidden = false;
        
        if (session.getAttribute("showHidden") == null) {
            session.setAttribute("showHidden", "true");
            showHidden = true;
        } else {
            String sh = (String) session.getAttribute("showHidden");
            if (sh.equalsIgnoreCase("true")) showHidden = true;
            else showHidden = false;        
        }       

        if (showHidden) {
            String cmdHideHidden = (String) request.getParameter("cmd_hideHidden");
            if (cmdHideHidden != null ) { 
                showHidden = false;
                session.removeAttribute("showHidden");
            }
        } else {
            String cmdShowHidden = (String) request.getParameter("cmd_showHidden");
            if (cmdShowHidden != null ) { 
                showHidden = true;
                session.setAttribute("showHidden", "true");
            }
        }
        
        if ( panel.getHide() == 1 && !showHidden) return true;
        return false;

    }

    // create panel quickly, 
    public static Panel createPanel(Site site, int colum, int panelType, int subType, int align){
        Panel panel = new Panel();

        panel.setSiteId(site.getId());
        panel.setColumnNum(colum);
        panel.setPanelType(panelType);
        panel.setPanelSubType(subType);
        panel.setAlign(align);
        panel.setTimeCreated(new TimeNow());
        
        PanelDS.getInstance().put(panel);

        return panel;
    }
    
    
    public static void main(String[] args) {
        
        Panel panel = PanelDS.getInstance().getById((long)305);
        
        System.out.println(isSameGroupView(panel, 634));
        
    }
}
