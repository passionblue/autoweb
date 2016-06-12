package com.jtrend.concur2.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jtrend.concur2.DropExecutionRunnable;

public class DefaultDropProcessorImpl extends AbstractDropProcessor{

    protected ExecutorService m_executorService;
    

    public DefaultDropProcessorImpl(DropExecutionRunnable executorService){
        executorService.setQueue(m_blockingQueue);
        
        m_executorService = Executors.newFixedThreadPool(1);
        m_executorService.execute(executorService);
        
    }

    
    
    
}
