package com.jtrend.concur2;

import java.util.concurrent.BlockingQueue;

public interface DropExecutionRunnable extends Runnable {
    public void processDropMessage(DropMessage dropMessage);
    public void setQueue(BlockingQueue queue);
}
