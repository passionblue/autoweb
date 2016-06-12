package com.autosite.session;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


public class ConfirmRegisterManager {

    protected ConcurrentHashMap m_confirmToByKeyMap = new ConcurrentHashMap();
    
    private static ConfirmRegisterManager m_instance = new ConfirmRegisterManager();

    public static ConfirmRegisterManager getInstance() {
        return m_instance;
    }

    private ConfirmRegisterManager() {

    }
    
    public  ConfirmTo find(String sessionKey, String key){
        if ( key == null) return null;
        
        ConfirmTo ret = (ConfirmTo) m_confirmToByKeyMap.get(key);
        if (ret == null ||  !ret.getSessionKey().equals(sessionKey))
            return null;
        
        return ret;
    }
    
    public ConfirmTo registerNew(String sessionKey, String uri, String query){
        ConfirmTo n =  new ConfirmTo(uri, query);
        n.setSessionKey(sessionKey);
        m_confirmToByKeyMap.put(n.getKey(), n);
        m_logger.info("New ConfirmTo registered " + n);
        return n;
    }
    
    private static Logger m_logger = Logger.getLogger(ConfirmRegisterManager.class);
}
