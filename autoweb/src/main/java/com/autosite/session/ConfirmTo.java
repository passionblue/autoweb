package com.autosite.session;

import java.util.HashMap;
import java.util.Map;

public class ConfirmTo {

    private String m_key;
    private String m_uri;
    private String m_query;
    private boolean m_confirmed;
    private long m_confirmedTime;
    private long m_createdTime;
    private Map m_extParams = new HashMap();
    
    private String m_sessionKey;
    
    
    public String getSessionKey() {
        return m_sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        m_sessionKey = sessionKey;
    }

    public Map getExtParams() {
        return m_extParams;
    }

    public void setExtParams(Map extParams) {
        if ( extParams != null)
            m_extParams.putAll(extParams);
    }

    public ConfirmTo(String uri, String query) {
        super();
        m_key = "CONFTO_"+System.currentTimeMillis();
        m_uri = uri;
        m_query = query;
        m_createdTime = System.currentTimeMillis();
        
    }
    
    public String getKey(){
        return m_key;
    }
    
    public String getUri() {
        return m_uri;
    }
    public void setUri(String uri) {
        m_uri = uri;
    }
    public String getQuery() {
        return m_query;
    }
    public void setQuery(String query) {
        m_query = query;
    }
    
    public String getConfirmToRequest() {
        String ret= m_uri;
        if ( m_query == null || m_query.length() == 0){
            return ret + "?confTo=" + m_key;
        } else {
            return ret + "?" + m_query+"&confTo=" + m_key;
        }
    }
    
    public void setConfirmed(){
        m_confirmed = true;
        m_confirmedTime = System.currentTimeMillis();
       
    }

    public boolean confirmed(){
        return m_confirmed;
    }

    public long getConfirmedTime(){
        return m_confirmedTime;
    }

    public String toString(){
        return "ConfirmTo Object: " + m_key + ",uri=" + m_uri + ",query=" + m_query + ",confirmed=" + m_confirmed;
    }
    
}
