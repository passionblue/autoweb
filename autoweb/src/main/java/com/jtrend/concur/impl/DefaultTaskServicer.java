package com.jtrend.concur.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.concur.ComTask;
import com.jtrend.concur.SelfControlTask;
import com.jtrend.concur.TaskObserver;
import com.jtrend.concur.TaskServicer;
import com.jtrend.concur.WorkBasket;
import com.jtrend.concur.WorkHandle;

public class DefaultTaskServicer implements TaskServicer {

    private static Logger m_logger = LoggerFactory.getLogger(DefaultTaskServicer.class);
    
    private ExecutorService m_executorService = null;
    
    private Map<String, TaskContainer> m_containerMap = new ConcurrentHashMap<String, TaskContainer>();
    
    public DefaultTaskServicer(){
        this(10);
    }
    
    
    public DefaultTaskServicer(int numThreads){
        m_executorService = Executors.newFixedThreadPool(numThreads);
        new TaskCleaner().start();
    }
            
    
    
    public WorkHandle dropAndWait(ComTask task) {
        
        String id = push(task, -1);
        
        TaskContainer t = m_containerMap.get(id);
        SimpleWorkHandle handle = new SimpleWorkHandle(id);
        handle.setStatus(t.getStatus());
        
        return handle;
    }

    public WorkHandle dropAndWait(ComTask task, long wait) {
        String id = push(task, wait);
        
        TaskContainer t = m_containerMap.get(id);
        SimpleWorkHandle handle = new SimpleWorkHandle(id);
        handle.setStatus(t.getStatus());
        
        return handle;
    }

    public WorkHandle dropAndGo(ComTask task) {
        String id = push(task, 0);
        
        TaskContainer t = m_containerMap.get(id);
        SimpleWorkHandle handle = new SimpleWorkHandle(id);
        handle.setStatus(t.getStatus());
        
        return handle;
    }

    public WorkHandle dropAndGo(ComTask task, TaskObserver observer) {
        String id = push(task, observer, 0);
        
        TaskContainer t = m_containerMap.get(id);
        SimpleWorkHandle handle = new SimpleWorkHandle(id);
        handle.setStatus(t.getStatus());
        
        return handle;
    }

    public WorkHandle update(WorkHandle handle) {
        
        TaskContainer t = m_containerMap.get(handle.getHandleId());
        SimpleWorkHandle newHandle = new SimpleWorkHandle(handle);
        newHandle.setStatus(t.getStatus());
        
        return newHandle;
    }

    public WorkBasket getNow(WorkHandle handle) {
        TaskContainer t = m_containerMap.get(handle.getHandleId());
        
        if ( t== null) return null;
        
//        try {
//            t.getLock().await();
//        }
//        catch (InterruptedException e) {
//            m_logger.error("",e);
//        }
        
        return t.getBasket();
    }

    public WorkBasket waitNow(WorkHandle handle, long wait) {
        TaskContainer t = m_containerMap.get(handle.getHandleId());
        
        if ( t== null) return null;
        
        try {
            t.getLock().await(wait, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e) {
            m_logger.error("",e);
        }
        
        return t.getBasket();
    }

    public void interrup(WorkHandle handle) {
        TaskContainer t = m_containerMap.get(handle.getHandleId());
        t.getFuture().cancel(true);
    }
    
    public void close(){
        m_executorService.shutdown();
    }

    public void closeNow(){
        m_executorService.shutdownNow();
    }

    private String push(ComTask task, long wait){
        return push(task, null, wait);
    }
    
    private String push(ComTask task, TaskObserver observer, long wait){
        String id = "ID-" + System.nanoTime();
        CountDownLatch doneSignal = new CountDownLatch(1);
        
        TaskContainer container = new TaskContainer(task, doneSignal, id);
        container.setObserver(observer);
        m_containerMap.put(id, container);
        
        try{
            Future f = m_executorService.submit(container);
            container.setFuture(f);
            doneSignal.await(wait, TimeUnit.MILLISECONDS);
            
        } catch( InterruptedException e){
            m_logger.error(e.getMessage(), e);
        }
        return id;
        
    }   
    
    /**
     * 
     *
     */
    static class TaskContainer implements Callable<WorkBasket>{
        
        private String          m_handleId;
        private WorkBasket      m_basket;
        private volatile int    m_status = WorkHandle.STATUS_PENDING;
        private TaskObserver    m_observer;

        private CountDownLatch  m_lock;
        private ComTask         m_task;
        private Future          m_future;
        
        private long startTime;
        private long endTime;
        
        TaskContainer(ComTask task, CountDownLatch lock, String id){
            m_task = task;
            m_lock = lock;
            m_handleId = id;
            m_logger.info("TaskContainer Created " + id + " task Class " + task.getClass().getSimpleName() );
        }
        
        public WorkBasket call() throws Exception {
            
            int repeat = 1;
            
            if ( m_task instanceof SelfControlTask){
                if (((SelfControlTask)m_task).getRepeatOnError() > 1)
                    repeat = ((SelfControlTask)m_task).getRepeatOnError();
            }
            
            startTime = System.currentTimeMillis();
            m_logger.debug("Task "+ m_handleId +" started at " + startTime);
            for (int i = 0; i < repeat; i++) {
                try {
                    m_status = WorkHandle.STATUS_IN_PROGRESS;
                    m_basket = m_task.execute();
                    m_status = WorkHandle.STATUS_COMPLETED;
                }
                catch (Exception e) {
                    m_logger.error("",e);
                }
            }
            endTime = System.currentTimeMillis();
            m_logger.debug("Task "+ m_handleId +" ended at " + endTime + " duration " + (endTime-startTime) + " status " + m_status);
            
            m_lock.countDown();
            if ( m_observer != null) {
                try {
                    if ( m_status == WorkHandle.STATUS_ERROR){
                        m_observer.onError(m_basket);
                    } else {
                        m_observer.onComplete(m_basket);
                    }
                }
                catch (Exception e) {
                    m_logger.error("",e);
                }
            }
            
            return m_basket;
        }

        public ComTask getTask() {
            return m_task;
        }

        public void setTask(ComTask task) {
            m_task = task;
        }

        public WorkBasket getBasket() {
            return m_basket;
        }

        public void setBasket(WorkBasket basket) {
            m_basket = basket;
        }

        public int getStatus() {
            return m_status;
        }

        public void setStatus(int status) {
            m_status = status;
        }

        public TaskObserver getObserver() {
            return m_observer;
        }

        public void setObserver(TaskObserver observer) {
            m_observer = observer;
        }

        public CountDownLatch getLock() {
            return m_lock;
        }

        public void setLock(CountDownLatch lock) {
            m_lock = lock;
        }

        public Future getFuture() {
            return m_future;
        }

        public void setFuture(Future future) {
            m_future = future;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }
    
    class TaskCleaner extends Thread {

        @Override
        public void run() {

            while(true){
                if (  m_containerMap.size() > 0)
                    m_logger.debug("Checking expired containers....(" + m_containerMap.size() + ")");
                
                List keys = new ArrayList(m_containerMap.keySet());
                
                for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                    String id = (String) iterator.next();
                    
                    TaskContainer container = m_containerMap.get(id);
                    
                    if ( container.getEndTime() > 0 && System.currentTimeMillis() - container.getEndTime() > 5000){
                        m_containerMap.remove(id);
                        
                        m_logger.debug("Container removed " + id + " task ended at " + container.getEndTime());
                    }
                    
                }
                
                
                try {
                    Thread.sleep(5000);
                }
                catch (Exception e) {
                }
                
            }
        }
    }
    
}
