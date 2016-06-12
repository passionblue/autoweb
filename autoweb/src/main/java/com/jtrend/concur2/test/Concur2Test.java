package com.jtrend.concur2.test;

import com.jtrend.concur2.DefaultDropExecutionRunnable;
import com.jtrend.concur2.DropInterface;
import com.jtrend.concur2.DropMessage;
import com.jtrend.concur2.DropProcessorFactory;
import com.jtrend.concur2.impl.DefaultDropMessageImpl;

public class Concur2Test extends DefaultDropExecutionRunnable {
    
    @Override
    public void processDropMessage(DropMessage dropMessage) {
        System.out.println("-----------------------------------> " + dropMessage);
    }

    public static void main(String[] args) throws Exception {
        
        DropInterface dropInterface = DropProcessorFactory.getInstance().createWith(new Concur2Test());
        
        Thread.sleep(5000);
        
        DropMessage dropMessage = new DefaultDropMessageImpl();
        
        dropInterface.dropAndGo(dropMessage);
        
        while(true){
            Thread.sleep(5000);
            dropInterface.dropAndGo(dropMessage);
        }        
    }
}
