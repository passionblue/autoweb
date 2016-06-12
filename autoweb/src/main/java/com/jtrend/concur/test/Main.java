package com.jtrend.concur.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.concur.ComTask;
import com.jtrend.concur.TaskObserver;
import com.jtrend.concur.WorkBasket;
import com.jtrend.concur.WorkHandle;
import com.jtrend.concur.impl.DefaultTaskServicer;
import com.jtrend.concur.tasks.DummyTask;

public class Main implements TaskObserver {

    private static Logger m_logger = LoggerFactory.getLogger(Main.class);
    
    @Override
    public void onError(WorkBasket basket) {
        m_logger.debug("onError");
    }

    @Override
    public void onComplete(WorkBasket basket) {
        m_logger.debug("onComplete ");
    }
    public static void main(String[] args) {
        DefaultTaskServicer servicer = new DefaultTaskServicer();
        
        ComTask t = new DummyTask(10000);
        ComTask t2 = new DummyTask(13000);
        ComTask t3 = new DummyTask(15000);
           
        WorkHandle handle = servicer.dropAndGo(t);
        WorkHandle handle2 = servicer.dropAndGo(t2);
        WorkHandle handle3 = servicer.dropAndGo(t3);
        
        //WorkBasket b = servicer.getNow(handle);
        
        while(true){
            WorkBasket b2 = servicer.getNow(handle2);
            if (b2 != null) break;
            try {
                Thread.sleep(500);
            }
            catch (Exception e) {
            }            
        }
        
        WorkBasket b2 = servicer.getNow(handle2);
        
        System.out.println(b2.getResult());
    }
    
    public static void main2(String[] args) {

        
        DefaultTaskServicer servicer = new DefaultTaskServicer();
        
        ComTask t = new DummyTask(10000);
        
        WorkHandle handle = servicer.dropAndGo(t, new Main());
        
        System.out.println(handle);
        
        System.out.println(servicer.update(handle));

        
        System.out.println("Waiting for work " + servicer.waitNow(handle, 3000));
        
        //servicer.interrup(handle);
        System.out.println(servicer.update(handle));
        
        try {
            Thread.sleep(20000);
        }
        catch (Exception e) {
        }
        System.out.println(servicer.update(handle));
        
        servicer.close();
    }
}
