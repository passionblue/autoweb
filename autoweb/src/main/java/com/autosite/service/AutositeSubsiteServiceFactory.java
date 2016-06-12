package com.autosite.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.autosite.db.Site;

public class AutositeSubsiteServiceFactory {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeSubsiteServiceFactory.class);
    
    private static AutositeSubsiteServiceFactory m_instance;

    public static synchronized AutositeSubsiteServiceFactory getInstance() {
        
        if ( m_instance == null)  {
        
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-subSiteService.xml");
            m_instance = (AutositeSubsiteServiceFactory) context.getBean("subSiteServiceFactory");
            m_logger.debug("AutositeSubsiteServiceFactory created");
        }
        return m_instance;
    }

    private AutositeSubsiteServiceFactory() {

    }

    private Map<String, AutositeSubsiteService> subSiteServicePool = new ConcurrentHashMap();
    
    
    public AutositeSubsiteService getSubSiteService(String url){
        return subSiteServicePool.get(url);
    }

    public Map<String, AutositeSubsiteService> getSubSiteServicePool() {
        return subSiteServicePool;
    }

    public void setSubSiteServicePool(Map<String, AutositeSubsiteService> subSiteServicePool) {
        this.subSiteServicePool = subSiteServicePool;
    }
    
    
    
    
}
