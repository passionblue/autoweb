package com.jtrend.session;

import java.util.HashMap;
import java.util.Map;

public class SessionContextFactory {

    public static SessionContextFactory m_instance = new SessionContextFactory();
    
    private Map m_map;
    
    SessionContextFactory() {
        m_map = new HashMap();
    }
    
    public void put(String key, SessionContext session) {
        m_map.put(key, session);
    }
    
    public SessionContext getSessionContext(String key) {
        return (SessionContext) m_map.get(key);
    }
    
    public static SessionContextFactory getInstance() {
        return m_instance;
    }
    
    
    
    
}
