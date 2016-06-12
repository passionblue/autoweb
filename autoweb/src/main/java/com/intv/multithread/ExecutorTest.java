package com.intv.multithread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorTest implements Runnable {

    int m_i;
    public ExecutorTest(int i ){
        m_i = i;
    }
    
    public static void main(String[] args) {

        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

        
        for (int i = 0;i<10;i++){
            pool.execute(new ExecutorTest(i));
            System.out.println("Added " + i + "@" + System.nanoTime());
        }
    }

    public void run(){
        
        try {
            System.out.println("Entering " +m_i +"--" + Thread.currentThread().getName() + " @" + System.nanoTime());
            Thread.sleep(5000);
            System.out.println("Exiting " +m_i +"--" + Thread.currentThread().getName() + " @" + System.nanoTime());
        }
        catch (Exception e) {
        }
    }
}
