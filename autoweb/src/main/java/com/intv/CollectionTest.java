package com.intv;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CollectionTest {

    public static void main(String[] args) throws Exception {
        // Arrays.hashCode(null);

        // ThreadPoolExecutor

        System.out.println(Executors.newCachedThreadPool().getClass().getName());
        System.out.println(Executors.newFixedThreadPool(10).getClass().getName());
        System.out.println(Executors.newScheduledThreadPool(1).getClass().getName());

        //calllableExample(args);
        cyclicBarrierExample();
        
        // CollectionTest.class.isInstance(null);

        Map t = new TreeMap();

        System.out.println(Map.class.isInstance(t));
        System.out.println(TreeMap.class.isInstance(t));

        if (t instanceof Map)
            System.out.println("true");
        if (t instanceof TreeMap)
            System.out.println("true");

        System.out.println(128 >>> 2);

        System.out.println(-128 >>> 2);
        System.out.println(-128 >> 2);

    }

    // private List list = new ArrayList();
    //    
    // public void run_produce() {
    // while (true) {
    // Object justProduced = getRequestFromNetwork();
    // synchronized (list) {
    // while (list.size() == MAX)
    // // queue "full"
    // try {
    // System.out.println("Producer WAITING");
    // list.wait(); // Limit the size
    // }
    // catch (InterruptedException ex) {
    // System.out.println("Producer INTERRUPTED");
    // }
    // list.addFirst(justProduced);
    // list.notifyAll(); // must own the lock
    // System.out.println("Produced 1; List size now " + list.size());
    // if (done)
    // break;
    // // yield(); // Useful for green threads & demo programs.
    // }
    // }
    // }
    //
    // public void run_consume() {
    // while (true) {
    // Object obj = null;
    // synchronized (list) {
    // while (list.size() == 0) {
    // try {
    // System.out.println("CONSUMER WAITING");
    // list.wait(); // must own the lock
    // }
    // catch (InterruptedException ex) {
    // System.out.println("CONSUMER INTERRUPTED");
    // }
    // }
    // obj = list.removeLast();
    // list.notifyAll();
    // int len = list.size();
    // System.out.println("List size now " + len);
    // if (done)
    // break;
    // }
    // // process(obj); // Outside synch section (could take time)
    // // yield(); DITTO
    // }
    // }

    // ###########################################################################################################
    // Callable example
    // ###########################################################################################################

    public static class WordLengthCallable implements Callable {
        private String word;

        public WordLengthCallable(String word) {
            this.word = word;
        }

        public Integer call() {
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }
            return Integer.valueOf(word.length());
        }
    }

    public static void calllableExample(String args[]) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Set<Future<Integer>> set = new HashSet<Future<Integer>>();

        args = new String[] { "xx", "xxx" };

        for (String word : args) {
            Callable<Integer> callable = new WordLengthCallable(word);
            Future<Integer> future = pool.submit(callable);
            set.add(future);
        }

        int sum = 0;
        for (Future<Integer> future : set) {
            System.out.println("before");
            sum += future.get();
            System.out.println("after");
        }

        System.out.printf("The sum of lengths is %s%n", sum);
        System.exit(sum);
    }

    // ###########################################################################################################
    // Cyclic example
    // ###########################################################################################################
    
    public static void cyclicBarrierExample(){
        Solver solv = new Solver(null);
        try {
            solv.get().await();
        }
        catch (InterruptedException e) {
        }
        catch (BrokenBarrierException e) {
        }
        System.out.println("xxx");
    }
    
}

class Solver {
    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    CyclicBarrier get(){
        return barrier;
    }
    
    class Worker implements Runnable {
        int myRow;

        Worker(int row) {
            myRow = row;
        }

        public void run() {

            System.out.println("worker working....");
            
            long s = (long)(5000 * Math.random());
            
            try {
                Thread.sleep(s);
                System.out.println("worker done");
                barrier.await();
                System.out.println("exiting");
            }
            catch (InterruptedException ex) {
                return;
            }
            catch (BrokenBarrierException ex) {
                return;
            }
        }
    }

    public Solver(float[][] matrix) {
      data = matrix;
      N = 3;
      barrier = new CyclicBarrier(N,
                                  new Runnable() {
                                    public void run() {
                                        System.out.println("Barrier operation started....");
                                    }
                                  });
      for (int i = 0; i < N-1; ++i)
        new Thread(new Worker(i)).start();
      
      
    }
}
