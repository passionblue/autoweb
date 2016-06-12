package com.intv.multithread;

import java.util.ArrayList;
import java.util.List;

public class ConsumerProducer {

    final static int MAX = 10;

    List b = new ArrayList();

    class Consumer extends Thread {
        public void run() {

            while (true) {
                
                Object obj = null;
                synchronized (b) {
                    if (b.size() > 0) {
                        obj = b.get(0);
                        b.notifyAll();
                    }
                    else {
                        try {
                            b.wait();
                        }
                        catch (InterruptedException e) {
                        }
                    }
                }
                
                if (obj != null) {
                    //TODO
                }
            }
        }

        public void run2() {

            while (true) {
                Object obj = null;

                synchronized (b) {
                    try {
                        while( b.size() == 0) 
                            b.wait();
                    }
                    catch (InterruptedException e) {
                        continue;
                    }

                    obj = b.get(0);
                    b.notifyAll();
                }
                
                if (obj != null) {
                    //TODO
                }
            }
        }
    
    }

    class Producer extends Thread {
        public void run() {
            Object obj = new Object();

            synchronized (b) {
                if (b.size() == MAX) {
                    try {
                        b.wait();
                    }
                    catch (InterruptedException e) {
                    }
                }
                else {
                    b.add(obj);
                    b.notifyAll();
                }
            }
        }
    }

}

class ConsumerProducer2 {

    final static int MAX = 10;

    List b = new ArrayList();

    class Consumer extends Thread {
        public void run() {

            while (true) {
                
                Object obj = null;
                synchronized (b) {
                    if (b.size() > 0) {
                        obj = b.get(0);
                        b.notifyAll();
                    }
                    else {
                        try {
                            b.wait();
                        }
                        catch (InterruptedException e) {
                        }
                    }
                }
                
                if (obj != null) {
                    //TODO
                }
            }
        }
    }

    class Producer extends Thread {
        public void run() {
            Object obj = new Object();

            synchronized (b) {
                if (b.size() == MAX) {
                    try {
                        b.wait();
                    }
                    catch (InterruptedException e) {
                    }
                }
                else {
                    b.add(obj);
                    b.notifyAll();
                }
            }
        }
    }

}
