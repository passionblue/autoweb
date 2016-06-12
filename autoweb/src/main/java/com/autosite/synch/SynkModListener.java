package com.autosite.synch;

import java.util.List;

/**
 * was trying to make something to notify for mod from central but still in thinking
 * 
 * @param <T>
 */
public interface SynkModListener<T> {

    
    void onMod(T obj, long stamp, String sourceDeviceId); // TODO do i need update handler??
    
    void onSnapshot(List<T> snapshot);
}
