package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.SiteConfigStyle;
import com.autosite.db.StyleTheme;
import com.autosite.db.ThemeAggregator;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteConfigStyleDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.StyleThemeDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.theme.AutositeThemeService;
import com.autosite.theme.impl.AutositeThemeServiceImpl;
import com.autosite.util.StyleThemeUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

public class SiteConfigStyleActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteConfigStyleAction#xtent.beforeAdd");		
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle)baseDbOject;



        if (SiteConfigStyleDS.getInstance().getObjectBySiteId(_SiteConfigStyle.getSiteId()) != null) {
            m_logger.debug("The same value already exists." + _SiteConfigStyle.getSiteId());
            throw new ActionExtentException("The same value already exists.", "linkto_add");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SiteConfigStyleAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle)baseDbOject;

        StyleTheme styleTheme = StyleThemeDS.getInstance().getById(_SiteConfigStyle.getThemeId());
        
        if (styleTheme != null) { 
            SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(styleTheme.getSiteId());
            StyleThemeUtil.updateSiteConfig(styleTheme, siteConfig);
        }        

        Site site = SiteDS.getInstance().getById(_SiteConfigStyle.getSiteId());
        //StyleConfig for the site changed. update theme
        updateTheme(site, StyleThemeUtil.getThemeAggregatorForSite(_SiteConfigStyle.getSiteId()));
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteConfigStyleAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteConfigStyleAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle)baseDbOject;

        // Update site config due to change of theme. 
        StyleTheme styleTheme = StyleThemeDS.getInstance().getById(_SiteConfigStyle.getThemeId());
        if (styleTheme != null) { 
            SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(styleTheme.getSiteId());
            if ( siteConfig != null) {
                StyleThemeUtil.updateSiteConfig(styleTheme, siteConfig);
            } else {
                // TODO have to report. 
            }
        }        
    
        Site site = SiteDS.getInstance().getById(_SiteConfigStyle.getSiteId());
        updateTheme(site, StyleThemeUtil.getThemeAggregatorForSite(_SiteConfigStyle.getSiteId()));
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteConfigStyleAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteConfigStyleAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle)baseDbOject;
        
        
    }

    //SiteConfigStyle points to theme aggregator ID. If this changes, the various configs related to theme should be
    // updated and re-tuned.
    private void updateTheme( Site site, ThemeAggregator themeAggregator){
        
        AutositeThemeService theme = new AutositeThemeServiceImpl();
        theme.installTheme(site, themeAggregator);
    }


    //========================================================================================
    // Propprietary Commands For This Action
    //========================================================================================

    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        if ( cmd == null || cmd.length() == 0) return;
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("refreshTheme")) {
            processCommand_refreshTheme(request, response, cmd);
            return;
        }
    }



    private void processCommand_refreshTheme(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        HttpSession session = request.getSession();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        updateTheme(site, StyleThemeUtil.getThemeAggregatorForSite(site.getId()));
        m_logger.info("processCommand_refreshTheme for " + site.getSiteUrl());
    }    
    
    //========================================================================================
    // AJAX
    //========================================================================================
    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map ret = new HashMap();
        return ret;
    }

    //========================================================================================
    // Configuration Option
    //========================================================================================

//   public String getErrorPage()            {return "site_config_style_home";}
//   public String getWarningPage()      {return "site_config_style_home";}
//   public String getAfterAddPage()     {return "site_config_style_form";}
//   public String getAfterEditPage()        {return "site_config_style_form";}
//   public String getAfterEditFieldPage()   {return "site_config_style_form";}
//   public String getAfterDeletePage()  {return "site_config_style_home";}
//   public String getDefaultPage()      {return "site_config_style_home";}

   public String getErrorPage()          {return DefaultPageForwardManager.getInstance().getPageForwardTo("SiteConfigStyle", "action_error_forward_page", "site_config_style_home");}
   public String getWarningPage()        {return DefaultPageForwardManager.getInstance().getPageForwardTo("SiteConfigStyle", "action_warning_forward_page", "site_config_style_home");}
   public String getAfterAddPage()       {return DefaultPageForwardManager.getInstance().getPageForwardTo("SiteConfigStyle", "action_normal_add_forward_page", "site_config_style_form");}
   public String getAfterEditPage()      {return DefaultPageForwardManager.getInstance().getPageForwardTo("SiteConfigStyle", "action_normal_edit_forward_page", "site_config_style_form");}
   public String getAfterEditFieldPage() {return DefaultPageForwardManager.getInstance().getPageForwardTo("SiteConfigStyle", "action_normal_editfield_forward_page", "site_config_style_form");}
   public String getAfterDeletePage()    {return DefaultPageForwardManager.getInstance().getPageForwardTo("SiteConfigStyle", "action_normal_delete_forward_page", "site_config_style_home");}
   public String getDefaultPage()        {return DefaultPageForwardManager.getInstance().getPageForwardTo("SiteConfigStyle", "action_default_forward_page", "site_config_style_home");}
   public String getConfirmPage()        {return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private SiteConfigStyleDS m_ds = SiteConfigStyleDS.getInstance();
   private static Logger m_logger = Logger.getLogger( SiteConfigStyleActionExtent.class);
    
}
