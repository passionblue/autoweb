package com.autosite.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.CleanerPickupDeliveryConfig;
import com.autosite.db.Page;
import com.autosite.db.Panel;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.SiteConfigStyle;
import com.autosite.db.ThemeAggregator;
import com.autosite.ds.CleanerPickupDeliveryConfigDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteConfigStyleDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.ThemeAggregatorDS;
import com.autosite.service.AutositeSubsiteService;
import com.autosite.struts.action.ActionExtentException;
import com.autosite.theme.AutositeThemeService;
import com.autosite.theme.impl.AutositeThemeServiceImpl; 
import com.autosite.util.DynPageUtil;
import com.autosite.util.MenuItemUtil;
import com.autosite.util.PanelUtil;
import com.autosite.util.StyleThemeUtil;
import com.jtrend.util.WebParamUtil;

public class AutositeCleanersSubsiteServiceImpl implements AutositeSubsiteService {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeCleanersSubsiteServiceImpl.class);
    
    @Override
    public Site createSubSite(Site baseSite, String subname) throws Exception{
        return createCleanerSubSite(baseSite, subname);
    }

    public Site createCleanerSubSite(Site baseSite, String subname) throws Exception{
        
        if ( !isGoodSubsiteName(subname) ) {
            throw new ActionExtentException("Subname should not have space inside");
        }
        
        if ( ! isAvailable(getSubsiteUrl(baseSite, subname) )) {
            ActionExtentException ex =  new ActionExtentException(subname + " is not available");
            ex.setErrorCode("10");
            throw ex;
        }
        
        try {
            
            m_logger.info("== Creating sub site on " + baseSite.getSiteUrl() + " =======================================================");
            
            // Site object
            Site createdSite = SiteDS.getInstance().registerSite(getSubsiteUrl(baseSite, subname), true, true);
            m_logger.info("Sub site URL " + getSubsiteUrl(baseSite, subname) + " ID= " + createdSite.getId());
            
            // SiteConfig 
            SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(createdSite.getId());
            if ( siteConfig == null) {
                siteConfig = SiteConfigDS.getDefaultSiteConfig();
            }
            
            //TODO.. do somethign with siteConfig
            siteConfig.setWidth(1000);
            
            siteConfig.setFrontDisplayFeed(0);
            siteConfig.setUnderlyingDynamicPage(1);
            siteConfig.setSiteId(createdSite.getId());
            
            siteConfig.setShowMenuColumn(WebParamUtil.getIntValue(false));
            siteConfig.setAdWidth(150);
            siteConfig.setTimeCreatd(new Timestamp(System.currentTimeMillis()));
            siteConfig.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
            
            SiteConfigDS.getInstance().put(siteConfig);
            m_logger.info("SiteConfig created for " + createdSite.getSiteUrl() + " " + createdSite.getId());


            
            
            // Install default themes ==== TODO this has to be improved
            
            // TODO Find the themeAggregator for the site.
            
            SiteConfigStyle siteConfigStyle = new SiteConfigStyle();
            siteConfigStyle.setSiteId(createdSite.getId());
            siteConfigStyle.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            siteConfigStyle.setTimeUpdated(new Timestamp(System.currentTimeMillis()));


            //Setting theme for the site. If no theme, do nothing. 
            List themeAggs = ThemeAggregatorDS.getInstance().getAll();
            if (themeAggs.size() > 0) {
                ThemeAggregator defaultAgg = (ThemeAggregator)themeAggs.get(0);
                siteConfigStyle.setThemeId(defaultAgg.getId()); // TODO where come. Should be configurable with patterns. ID for ThemeAggregator
            }
            
            SiteConfigStyleDS.getInstance().put(siteConfigStyle); 
            m_logger.info("SiteConfigStyle created for " + createdSite.getSiteUrl() + " " + createdSite.getId());
                
            // Install Theme set by siteConfigStyle above
            ThemeAggregator themeAggregator = StyleThemeUtil.getThemeAggregatorForSite(createdSite.getId());
            
            if ( themeAggregator != null) {
                AutositeThemeService themeService = new AutositeThemeServiceImpl();
                themeService.installTheme(createdSite, themeAggregator, true);
                m_logger.info("Theme Installed for " + createdSite.getSiteUrl() + " " + createdSite.getId() + " with theme " + themeAggregator.getThemeName() );
            } else {
                m_logger.warn("Theme aggregator for the site not found. Theme will not be installed for the site. " + createdSite.getSiteUrl());
            }
            
            //Home page
            Page homePage = PageDS.getInstance().getBySiteIdPageName(createdSite.getId(), "XHOME");
            if (homePage == null ) {
                // Create new page 
                Page page = new Page();
                
                page.setPageName("XHOME");
                page.setPageMenuTitle("XHOME");
                page.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                page.setSiteUrl(createdSite.getSiteUrl());
                page.setSiteId(createdSite.getId());
                page.setUnderlyingPage("home_subsite"); // Better be set configurably

                PageDS.getInstance().put(page); 
                
                m_logger.info("XHOME dynPage created for the site " + createdSite.getSiteUrl());
                homePage = page;
                
            } else {
                homePage.setUnderlyingPage("home_subsite");
                m_logger.info("XHOME dynPage set with underlyingPage home_subsite");
            }

            
            //Other pages 
            //TODO how to configure these as a theme or configurable sets from outside?????
            List pages = new ArrayList();

            Page aboutPage          = DynPageUtil.createAndSaveDynPage(createdSite, "about", "About", "dynamic_menu_content_three");
            Page locationPage       = DynPageUtil.createAndSaveDynPage(createdSite, "location", "Location", "dynamic_menu_content_three");
            Page servicePage        = DynPageUtil.createAndSaveDynPage(createdSite, "service", "Service", "dynamic_menu_content_three");
            Page pickupDeliveryPage = DynPageUtil.createAndSaveDynPage(createdSite, "pickup", "Schedule Pickup/Delivery", "custom_cleaner_pickup_form");

            pages.add(homePage);
            pages.add(aboutPage);
            pages.add(locationPage);
            pages.add(servicePage);
            pages.add(pickupDeliveryPage);
            
            CleanerPickupDeliveryConfig pickupDelvieryConfig = new CleanerPickupDeliveryConfig();
            pickupDelvieryConfig.setSiteId(createdSite.getId());
            CleanerPickupDeliveryConfigDS.getInstance().put(pickupDelvieryConfig);
            
            //Create Menu Header Panel
            Panel panel = PanelUtil.createPanel(createdSite, 
                                                PanelUtil.PANEL_COL_HEADER_BOTTOM, 
                                                PanelUtil.PANEL_HEADER_MENU,
                                                PanelUtil.PANEL_MENU_REGULAR,
                                                PanelUtil.PANEL_ALIGN_HORIZONTAL);
            
            //Creating menu items for pages created. this would set the ordering too.
            List menuItems = MenuItemUtil.createMenuItems(panel, pages);
            
            m_logger.info("== Completed creating sub site on " + baseSite.getSiteUrl() + " =======================================================");
            
            return createdSite;
            
        }
        catch (Exception e) {
            //Rollback something
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAvailable(String subdomain) {
        
        return SiteDS.getInstance().getSiteByUrl(subdomain) ==  null;
    }
    
    private boolean isGoodSubsiteName(String subdomain) {

        if( subdomain == null || subdomain.trim().isEmpty()) {
            return false;
        }
        
        if ( subdomain.indexOf(" ") > 0 ){
            return false;
        }
        
        return true;
        
    }
    
    
    
    protected String getSubsiteUrl(Site site, String subname){
        return subname + "." + site.getSiteUrl();
    }
    
    
    private void createCleanerSubsite(){
        
    }
}
