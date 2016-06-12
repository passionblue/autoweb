package com.autosite.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.Site;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.struts.core.AbstractFileBasedConfigManager;

public class DefaultActionMapper extends AbstractFileBasedConfigManager {

    private static Logger m_logger = LoggerFactory.getLogger(DefaultActionMapper.class);

    private Map<String, String> m_properties = new ConcurrentHashMap<String, String>(); 
    protected Map<String, AutositeCoreAction> m_actionClassRegistered = new ConcurrentHashMap<String, AutositeCoreAction>();

    private static DefaultActionMapper m_instance = new DefaultActionMapper();

    public static DefaultActionMapper getInstance() {
        return m_instance;
    }

    private DefaultActionMapper() {
        try {
            loadFile("/conf/action.properties");
        }
        catch (Exception e) {
            m_logger.error("",e);
        }
    }

    @Override
    protected void processLine(String namdespace, String key, String val) {
        m_properties.put(key, val);
        try {
            AutositeCoreAction action = (AutositeCoreAction)Class.forName(val).newInstance();
            m_actionClassRegistered.put(key, action);
            m_logger.info("New Action registered " + key + " class " +val);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage() + " key=" + key + " value=" + val,e);
        }
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
    
    public AutositeCoreAction getCoreAction(String key){
        return m_actionClassRegistered.get(key);
    }

    public void register(String key, AutositeCoreAction action){
        m_actionClassRegistered.put(key, action);
    }
    
    public static void main(String[] args) {
        
        DefaultActionMapper loader = new DefaultActionMapper();
        
    }
}
