package com.autosite.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.ResourceInclusion;
import com.autosite.db.Site;
import com.autosite.ds.ResourceInclusionDS;
import com.jtrend.struts.core.AbstractFileBasedConfigManager;

public class ResourceInclusionManager extends AbstractFileBasedConfigManager {

    private static Logger m_logger = LoggerFactory.getLogger(ResourceInclusionManager.class);

    private Map<String, StringBuilder> m_resourceItems = new ConcurrentHashMap<String, StringBuilder>(); 
    
    private static ResourceInclusionManager m_instance = new ResourceInclusionManager();
    
    private String m_currentTitle;
    
    public static ResourceInclusionManager getInstance() {
        return m_instance;
    }

    private ResourceInclusionManager() {
        try {
            loadFile("/conf/layout-inclusion.properties");
        }
        catch (Exception e) {
            m_logger.error("",e);
        }
    }

    @Override
    protected void processLine(String namdespace, String key, String val) {
    }

    @Override

    protected void processLine(String namdespace, String[] keyParts, String val) {
    }
    
    @Override
    protected void processLine(String line) {
        
        if ( line == null) return;
        if (line.trim().isEmpty()) {
            return;
        }
        
        if (line.trim().startsWith("#")) {
            return;
        }
        
        if ( line.startsWith("[") && line.endsWith("]")){
            m_currentTitle = line.substring(1, line.length()-1);
            m_logger.debug("Starting title [" + m_currentTitle + "]");
        } else {
            StringBuilder builder = m_resourceItems.get(m_currentTitle);
            
            if ( builder == null) {
                builder = new StringBuilder();
                m_resourceItems.put(m_currentTitle, builder);
            }
            builder.append(line).append("\n");
        }
    }

    public boolean include(Site site, String appKey, String reourceKey){
        
        ResourceInclusion inclusion = ResourceInclusionDS.getInstance().getBySiteIdToName(site.getId(), reourceKey);
        if (inclusion != null && inclusion.getInclude() == 0)
            return false;
        return true;
    }
    
    public  String get(String reourceKey){
        if ( m_resourceItems.containsKey(reourceKey))
            return m_resourceItems.get(reourceKey).toString();
        
        return null;
    }
    
    
    public static void main(String[] args) {

        System.out.println(ResourceInclusionManager.getInstance().get("common-all"));
    }
}
