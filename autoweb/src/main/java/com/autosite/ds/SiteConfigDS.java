package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.autosite.db.SiteConfig;
import com.autosite.db.SiteConfigDAO;
import com.autosite.theme.DefaultThemeManager;
import com.autosite.util.SiteConfigUtil;
import com.jtrend.service.DomainStore;

public class SiteConfigDS extends AbstractDS implements DomainStore {

    protected Map m_siteIdToMap;

    private static Logger m_logger = Logger.getLogger(SiteConfigDS.class);
    private static SiteConfigDS m_SiteConfigDS = null;

    public static synchronized SiteConfigDS getInstance() {
        if (m_SiteConfigDS == null) {
            m_SiteConfigDS = new SiteConfigDS();
        }
        return m_SiteConfigDS;
    }

    protected SiteConfigDS() {
        m_dao = new SiteConfigDAO();
        m_idToMap = new HashMap();
        m_siteIdToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e);
        }
    }

    public void updateMaps(Object obj, boolean del) {
        SiteConfig siteConfig = (SiteConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(siteConfig.getId()));
            m_siteIdToMap.remove(new Long(siteConfig.getSiteId()));
            m_logger.debug("SiteConfig removed from DS " + siteConfig.getId());

        }
        else {
            m_idToMap.put(new Long(siteConfig.getId()), siteConfig);
            m_siteIdToMap.put(new Long(siteConfig.getSiteId()), siteConfig);
            
            m_logger.debug("SiteConfig added to DS " + siteConfig.getId());
        }
    }
    
    public SiteConfig getById(Long id) {
        return (SiteConfig) m_idToMap.get(id);
    }
    
    public SiteConfig getSiteConfigBySiteId(long siteId) {

        return (SiteConfig) m_siteIdToMap.get(new Long(siteId));
    }
    
    public static void main(String[] args) throws Exception {


        SiteConfigDS ds = new SiteConfigDS();
        

        SiteConfig siteConfig =  ds.getSiteConfigBySiteId(29);
       
        
    }
    
    public static SiteConfig getDefaultSiteConfig() {
        
        return SiteConfigUtil.getDefaultSiteConfig();
        
/*        DefaultThemeManager themeManager = DefaultThemeManager.getInstance();
        
        //themeManager.get
        
        
        SiteConfig config = new SiteConfig();
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
        
        return config;
*/    }
    
}
