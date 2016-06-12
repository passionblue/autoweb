package com.jtrend.concur2;

import com.jtrend.concur2.impl.DefaultDropProcessorImpl;

public class DropProcessorFactory {

    private static DropProcessorFactory m_instance = new DropProcessorFactory();

    public static DropProcessorFactory getInstance() {
        return m_instance;
    }

    private DropProcessorFactory() {

    }
    
    
    public DropInterface createWith(DropExecutionRunnable executor){
        
        return new DefaultDropProcessorImpl(executor);
    }
   
}
