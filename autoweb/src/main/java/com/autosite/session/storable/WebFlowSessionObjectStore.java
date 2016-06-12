package com.autosite.session.storable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.jtrend.session.SessionStorable;

public class WebFlowSessionObjectStore extends AutositeSessionObjectStore {
    
    private static Logger m_logger = Logger.getLogger(WebFlowSessionObjectStore.class);
    protected String m_groupName = "NOT_SET";
    protected Map m_storables = new ConcurrentHashMap();
    protected List m_storablesList = new ArrayList();
    protected long m_timestamp;
    
    public WebFlowSessionObjectStore(String groupName) {
        m_groupName = groupName;
        m_timestamp = System.currentTimeMillis();
        m_logger.info("SessionObjectStore Created " + groupName);
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
        m_storablesList.add(storable);
    }
    
    public SessionStorable getStorable(String name) {
        return (SessionStorable) m_storables.get(name);
    }
    
    
    public SessionStorable getByStep(int stepNum){
        return (SessionStorable) m_storablesList.get(stepNum-1);
    }
    
}
