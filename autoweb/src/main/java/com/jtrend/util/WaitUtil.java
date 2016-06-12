package com.jtrend.util;

public class WaitUtil {
    
    public static void waitFor(long msec){
        try { Thread.sleep(msec);}catch (Exception e) {}
    }
    
    @SuppressWarnings("finally")
    public static Object lockOn(long msec){
        Object lock = new Object();
        
        synchronized (lock) {
            try {
                lock.wait(msec);
            }
            catch (InterruptedException e) {
            }
            finally{
                return lock;
            }
        }
    }
    
    @SuppressWarnings("finally")
    public static Object lockOn(){
        Object lock = new Object();
        
        synchronized (lock) {
            try {
                lock.wait();
            }
            catch (InterruptedException e) {
            }
            finally{
                return lock;
            }
        }
    }    
    
}
