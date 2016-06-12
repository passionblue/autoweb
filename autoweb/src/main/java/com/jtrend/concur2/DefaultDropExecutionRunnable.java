package com.jtrend.concur2;

import java.util.concurrent.BlockingQueue;

public abstract class DefaultDropExecutionRunnable  implements  DropExecutionRunnable {
    
    protected BlockingQueue m_queue;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("##################### STARTED  ############################## " + this.getClass().getSimpleName());

        if (m_queue == null) return;
        
        while(true) {
            DropMessage message;
            try {
                message = (DropMessage) m_queue.take();
                processDropMessage(message);
            }
            catch (Exception e) {
                continue;
            }
        }
    }
    
    @Override
    public void setQueue(BlockingQueue queue) {
        m_queue = queue;
    }

}
