package com.autosite.session.storable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.jtrend.session.SessionStorable;

abstract public class AutositeSessionObjectStore {

    protected String    m_name;
    protected Map       m_storables = new ConcurrentHashMap();
    protected String    m_groupName = "NOT_SET";
    protected long      m_timestamp;
    protected int       m_scope;
    
    public AutositeSessionObjectStore(){
        
    }
    public void reset() {
        m_storables = new ConcurrentHashMap();
        m_logger.info("Session Object Store reset");
    }
    
    public void removeStorable(String name){
        m_storables.remove(name);
    }
    
    public void addStorable(String name, SessionStorable storable){
        m_logger.info("Storable Added to " + m_groupName + " name:" + name + " - " + storable);
        m_storables.put(name, storable);
    }
    
    public SessionStorable getStorable(String name) {
        return (SessionStorable) m_storables.get(name);
    }

    
    
    public long getTimestamp() {
        return m_timestamp;
    }
    public void setTimestamp(long timestamp) {
        m_timestamp = timestamp;
    }
    public int getScope() {
        return m_scope;
    }
    public void setScope(int scope) {
        m_scope = scope;
    }
    @Override
    public String toString() {
        
        List names = new ArrayList(m_storables.entrySet());
        
        StringBuilder builder = new StringBuilder();
        
        for (Iterator iterator = names.iterator(); iterator.hasNext();) {
            Map.Entry<String, String> object = (Map.Entry<String, String>) iterator.next();
            
            builder.append(object.getKey()).append("-");
        }
        
        return builder.toString();
    }

    
    
    private static Logger m_logger = Logger.getLogger(AutositeSessionObjectStore.class);
}
