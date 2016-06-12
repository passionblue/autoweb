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

public class PanelUtil2 {

    public static StyleConfig getPanelStyleConfigByPositionColumn(Panel panel) {
        List list = StyleConfigDS.getInstance().getBySiteIdToStyleUseList(panel.getSiteId(), 3);
        if ( list == null || list.size() == 0) return null;
        return (StyleConfig) list.get(0);
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
    
    public static String getPanelStyleDefaultString(Panel panel){
        if ( panel == null) return "-null";
        return "-"+IdScrambler.scramble(panel.getId());

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
        
        
        StyleConfig styleConfig = PanelUtil2.getPanelStyleConfig(panel.getId());
        if (styleConfig != null) 
            return defaultStr + "-psc-"+styleConfig.getId(); 
        
        // Style Set ( didnt change during the makerover
//        if (WebUtil.isNotNull(panel.getStyleDefaultCode()))
//            return StyleUtil.createStyleSetId(panel);
            
        // Theme Check
        // Finally check the theme whether this site has a site-wide theme. 
        //StyleTheme theme = SiteStyleUtil.getTheme(panel.getSiteId());
//        if (theme != null){
//            return StyleUtil.createThemeId(theme.getTitle(), panel.getColumnNum(), panel.getPanelType());
//        }

        // Section style check
        StyleConfig sectionStyleConfig = getPanelStyleConfigByPositionColumn(panel);
        if ( styleConfig != null)
            return  defaultStr + "-csc-"+styleConfig.getId(); 
        
        
        return defaultStr;
    }    
    
    
    public static String getPanelLinkStyleSuffix(Panel panel){
        String defaultStr = getPanelStyleDefaultString(panel);
        if ( panel == null) return defaultStr;

        LinkStyleConfig styleConfig = PanelUtil2.getPanelLinkStyleConfig(panel.getId());
        if (styleConfig != null) 
            return defaultStr + "-plsc-"+styleConfig.getId();
        
        // Style Set
//        if (WebUtil.isNotNull(panel.getStyleDefaultCode()))
//            return StyleUtil.createStyleSetId(panel);
            
        //Section link style
        
        
        return defaultStr;
    }
    
    
    public static void main(String[] args) {
        
    }
}
