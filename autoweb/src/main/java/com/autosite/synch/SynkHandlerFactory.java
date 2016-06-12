package com.autosite.synch;


public interface SynkHandlerFactory {

    SynkUpdateHandler getUpdateHandler(String deviceId, String namespace, String ip);
    
    SynkModHandler getModHandler(String deviceId, String namespace, String ip);
    
}
