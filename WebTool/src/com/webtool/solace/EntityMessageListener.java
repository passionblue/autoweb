package com.webtool.solace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface EntityMessageListener<T> {
    
    void onMessage(T entity);

}
