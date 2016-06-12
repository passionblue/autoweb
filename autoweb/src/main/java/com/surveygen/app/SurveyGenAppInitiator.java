package com.surveygen.app;

import org.apache.log4j.Logger;

import com.autosite.content.ContentSilo;
import com.jtrend.struts.core.model.ApplicationInitiator;

public class SurveyGenAppInitiator implements ApplicationInitiator {

    public synchronized void init() {
        
            m_logger.debug("SurveyGenAppInitiator.initApp()");

            //SurveyGenViewManager.getInstance().initPageView();
            ContentSilo.getInstance();
    }
    
    private static Logger m_logger = Logger.getLogger(SurveyGenAppInitiator.class);
}
