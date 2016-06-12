package com.autosite.synch.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



import java.util.concurrent.atomic.AtomicLong;

import com.autosite.synch.SynkHandlerFactory;
import com.autosite.synch.SynkModHandler;
import com.autosite.synch.SynkUpdateHandler;

public class SynkHanlderFactoryImpl implements SynkHandlerFactory {

    
    private Map<String, SynkHandlerImpl> handlers = new ConcurrentHashMap<>();
    private Map<String, AtomicLong> indexTracker = new ConcurrentHashMap<>();
    
    @Override
    public SynkUpdateHandler getUpdateHandler(String deviceId, String namespace, String ip) {
        
        SynkUpdateHandler handler = getUp(deviceId, namespace);
        ((SynkHandlerImpl) handler).setIp(ip);
        return handler;
    }

    @Override
    public SynkModHandler getModHandler(String deviceId, String namespace, String ip) {
        SynkModHandler handler = getMod(deviceId, namespace);
        ((SynkHandlerImpl) handler).setIp(ip);
        return handler;
    }
    
    private SynkModHandler getMod(String deviceId, String namespace){
        
        String key = getKey(deviceId, namespace);
        
        SynkHandlerImpl handler = handlers.get(key);
        if ( handler == null ){
            handler = new SynkHandlerImpl(namespace, deviceId);
            handler.setIndexTracker(indexTracker);
            handlers.put(key, handler);
        }
        
        return handler;
    }
    
    
    private SynkUpdateHandler getUp(String deviceId, String namespace){
        
        String key = getKey(deviceId, namespace);
        
        SynkHandlerImpl handler = handlers.get(key);
        if ( handler == null ){
            handler = new SynkHandlerImpl(namespace, deviceId);
            handler.setIndexTracker(indexTracker);
            handlers.put(key, handler);
        }
        
        return handler;
    }
    
    private String getKey(String deviceId, String namespace){
        return deviceId + "@" + namespace;
    }
    
    
    private static SynkHanlderFactoryImpl m_instance = new SynkHanlderFactoryImpl();

    public static SynkHanlderFactoryImpl getInstance() {
        return m_instance;
    }

    private SynkHanlderFactoryImpl() {

    }

}
