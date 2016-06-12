package com.jtrend.concur.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.concur.SelfControlTask;
import com.jtrend.concur.WorkBasket;
import com.jtrend.concur.impl.DefaultWorkBasket;

public class DummyTask implements SelfControlTask{

    private static Logger m_logger = LoggerFactory.getLogger(DummyTask.class);
    
    private long val;
    
    public DummyTask(long val){
        this.val = val;
    }
    
    @Override
    public WorkBasket execute() {

        try {
            Thread.sleep(val);
        }
        catch (InterruptedException e) {
            m_logger.error("",e);
        }
        m_logger.debug("Dummy task completed");
        return new DefaultWorkBasket("DummyTask/" + System.currentTimeMillis() + "/" + val, null);
    }

    @Override
    public long getTimeLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getRepeatOnError() {
        // TODO Auto-generated method stub
        return 0;
    }

    
    
}
