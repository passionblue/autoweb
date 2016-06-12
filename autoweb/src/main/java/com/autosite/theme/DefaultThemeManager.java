package com.autosite.theme;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.Site;
import com.autosite.db.StyleTheme;
import com.autosite.ds.SiteConfigDS;
import com.jtrend.struts.core.AbstractFileBasedConfigManager;
import com.seox.util.PropertiesLoader;

public class DefaultThemeManager extends AbstractFileBasedConfigManager {

    private static Logger m_logger = LoggerFactory.getLogger(DefaultThemeManager.class);
    
    private static Map<String, Map> m_themeRepo = new ConcurrentHashMap<String, Map>();
    private static DefaultThemeManager m_instance = new DefaultThemeManager();

    public static DefaultThemeManager getInstance() {
        return m_instance;
    }

    private DefaultThemeManager() {

        m_logger.info("################################## DefaultThemeManager ###########################################################");
        PropertiesLoader loader = PropertiesLoader.getInstance("/themes/themes.properties");

        String activeThemesList = loader.getProperty("active");
        String activeThemesNames [] = StringUtils.split(activeThemesList, ",");
        
        try {

            for (int i = 0; i < activeThemesNames.length; i++) {
                String themeName = activeThemesNames[i];
                loadFile(themeName, "/themes/"+themeName + ".properties");
            }
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }

    }
    
    @Override
    protected void processLine(String namespace, String key, String val) {
        
        Map themeFields = null;
        if  (!m_themeRepo.containsKey(namespace)){
            themeFields = new ConcurrentHashMap();
            m_themeRepo.put(namespace, themeFields);
        } else {
            themeFields = m_themeRepo.get(namespace);
        }
        
        if ( val != null && !val.trim().isEmpty()) {
            m_logger.debug("[" + namespace + "] " + key + " -> " + val );
            themeFields.put(key, val);
        }
        
    }

    @Override
    protected void processLine(String namdespace, String[] keyParts, String val) {
        // TODO Auto-generated method stub
        
    }
    
    public StyleTheme getThemeForSite(Site site){

        if (site == null) return null;
        
        SiteConfigDS.getDefaultSiteConfig();
        
        return null; 
    }

    
    // Need bounding
    public StyleTheme getThemeForSite(String themeName){
        
        
        return null;
    }    
    
    public  Map getThemeFields(String name){
        Map fields = m_themeRepo.get(name);
        
        return fields;
    }
    
    public Map getDefaultThemeFields(){
        return getThemeFields("default");
    }
    
    public static void main(String[] args) {
        DefaultThemeManager.getInstance();
    }
    
}
