package com.autosite.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.Site;
import com.jtrend.struts.core.AbstractFileBasedConfigManager;

public class DefaultFilePropertiesLoader extends AbstractFileBasedConfigManager {

    private static Logger m_logger = LoggerFactory.getLogger(DefaultFilePropertiesLoader.class);

    private Map<String, String> m_properties = new ConcurrentHashMap<String, String>(); 

    public DefaultFilePropertiesLoader(String filename) {
        try {
            //loadFile("/conf/action-routing.properties");
            loadFile(filename);
        }
        catch (Exception e) {
            m_logger.error("",e);
        }
    }

    @Override
    protected void processLine(String namdespace, String key, String val) {
        m_properties.put(key, val);
    }

    @Override

    protected void processLine(String namdespace, String[] keyParts, String val) {
    }
    
    @Override
    protected void processLine(String line) {
    }

    public boolean include(Site site, String appKey, String reourceKey){
        return true;
    }
    
    public  String get(String reourceKey){
        return m_properties.get(reourceKey);
    }
    
    public Set getEntries(){
        return m_properties.entrySet();
    }
    
    public static void main(String[] args) {
        
        DefaultFilePropertiesLoader loader = new DefaultFilePropertiesLoader("/conf/action.properties");
        
        for (Iterator iterator = loader.getEntries().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            
            System.out.println(entry.getKey());
            
        }
    }
}
