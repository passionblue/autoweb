/*
 * Created on Dec 8, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.util;

public class DelayedTaskPool {

}

/*
package com.jtrend.seox5.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgroups.util.Queue;
import org.jgroups.util.QueueClosedException;
import org.jgroups.util.ReusableThread;
import org.jgroups.util.ThreadPool;

public class TPTest {

    String m_owner;
    ThreadPool m_threadPool = new ThreadPool(100);
    Queue m_taskQueue = new Queue();

    Map m_results = new HashMap();
    Set m_working = new HashSet();
    Object m_lock = new Object();
    
    Thread m_pollThread;
    
    public TPTest(String owner) {
        m_owner = owner;
        m_pollThread = new PollThread(m_lock, m_taskQueue, m_threadPool);
        m_pollThread.start();
    }
    
    public synchronized void registerTask(String name, TPTask task, long maxTime, long waitTime ) {
        
        if (m_working.contains(name)) {
            return;
        }
        
        try {
            
            WorkOrder wo = new WorkOrder();
            wo.m_name = task.getName();
            
            m_working.add(wo);
            m_taskQueue.add(task);
        }
        catch (QueueClosedException e) {
        }
        
    }
    
    
    public synchronized Object getResult(String name, long waitTime) {
        
        
        long start = System.currentTimeMillis();
        
        while(true) {

            synchronized (m_results) {
                Object ret =  m_results.get(name);
                if (ret != null) {
                    m_results.remove(name);
                    return ret;
                }
            }            
            
            try {
                Thread.sleep(100);
            }
            catch (Exception e) {
            }

            if (waitTime > System.currentTimeMillis() - start) {
                return null;
            }
        }
    }
    
    class WorkOrder {
        
        String m_name;
        long m_startTime;
    }
    
    
    public static void main(String[] args) {

        ThreadPool tp = new ThreadPool(10);

        for (int i = 0; i < 100; i++) {

            ReusableThread t = tp.getThread();
            
            if ( t == null) {
                try {
                    Thread.sleep(1000);
                    System.out.println("waiting waitng ");
                    i--;
                    continue;
                }
                catch (Exception e) {
                }
            }
            System.out.println("makeing " + i);
            t.assignTask(new MyThread(i));
            t.start();
        }
    }
}

class MyThread implements Runnable{
    int m;
    MyThread(int i) {
        m = i;
    }

    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println("Exting " + m);
        }
        catch (Exception e) {
        }
    }
}

class PollThread extends Thread{
    
    Object m_lock;
    Queue m_queue;
    ThreadPool m_tp;
    
    PollThread(Object lock, Queue queue, ThreadPool tp) {
        m_lock = lock;
        m_queue = queue;
        m_tp = tp;
    }

    public void run() {
        
        while(true) {

            try {
                TPTask task = (TPTask) m_queue.peek();
                
                if (task != null ) {
                    
                }
            }
            catch (QueueClosedException e1) {

            }

            synchronized (m_lock) {
                try {
                    m_lock.wait(100);
                }
                catch (InterruptedException e) {
                }
            }
        }
    }
    
    
    
}


package com.jtrend.seox5.util;

public interface TPTask extends Runnable {

    public String getName();
}
*/