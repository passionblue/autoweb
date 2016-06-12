package com.jtrend.concur2.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jtrend.concur2.DropMessage;

public class DefaultDropMessageImpl implements DropMessage{

    protected String m_title;

    public DefaultDropMessageImpl(String title){
        m_title = title;
    }
    public DefaultDropMessageImpl(){
        
    }
    
    protected Map<String, Object> m_properties  = new ConcurrentHashMap<String, Object>();
    
    @Override
    public void addProperty(String name, Object value) {
        m_properties.put(name, value);
    }

    @Override
    public String getTitle() {
        return m_title;
    }
    
    public String toString(){
        
        return "DropMessage [" + m_title + "] " + m_properties;
    }
    

}
