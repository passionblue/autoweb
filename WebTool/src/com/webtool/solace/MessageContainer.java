package com.webtool.solace;
import java.util.List;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class MessageContainer<T> {

    private static Logger m_logger = LoggerFactory.getLogger(MessageContainer.class);


    private CircularFifoQueue<T> queue;
    
    //Default Constructor
    public MessageContainer() {
    }

    
    public void addAll(List<T> all) {
//        queue.add(all);
    }

    public void get() {
        
    }
    
}
