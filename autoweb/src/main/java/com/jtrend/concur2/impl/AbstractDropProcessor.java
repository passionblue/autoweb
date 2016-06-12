package com.jtrend.concur2.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.jtrend.concur2.DropInterface;
import com.jtrend.concur2.DropMessage;

public class AbstractDropProcessor implements DropInterface {
    
    protected BlockingQueue<DropMessage> m_blockingQueue = new LinkedBlockingQueue<DropMessage>();
    
    @Override
    public void dropAndGo(DropMessage dropMessage) {
        m_blockingQueue.offer(dropMessage);
    }

}
