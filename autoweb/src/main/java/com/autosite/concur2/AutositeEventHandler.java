package com.autosite.concur2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.concur2.DefaultDropExecutionRunnable;
import com.jtrend.concur2.DropMessage;

public class AutositeEventHandler extends DefaultDropExecutionRunnable {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeEventHandler.class);
    
    @Override
    public void processDropMessage(DropMessage dropMessage) {

        m_logger.info(" ## EVENT ## " + dropMessage);

    }
    
}
