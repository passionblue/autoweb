package com.autosite.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.autosite.db.Site;

public class AutositeUserServiceFactory {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeUserServiceFactory.class);
    
    private static AutositeUserServiceFactory m_instance;

    public static synchronized AutositeUserServiceFactory getInstance() {
        
        if ( m_instance == null)  {
        
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-userService.xml");
            m_instance = (AutositeUserServiceFactory) context.getBean("userServiceFactory");
            m_logger.debug("AutositeUserServiceFactory created");
        }
        return m_instance;
    }

    private AutositeUserServiceFactory() {

    }

    private Map<String, AutositeUserService> userServicePool = new ConcurrentHashMap();
    
    public AutositeUserService getUserService(Site site, String name){
        
        String nameAndSiteKey = site.getSiteUrl().toUpperCase() + ":" + name;
        
        m_logger.debug("SiteNameKey " + nameAndSiteKey + " name " + name);
        if ( userServicePool.containsKey(nameAndSiteKey) )
            return userServicePool.get(nameAndSiteKey);
        else if ( userServicePool.containsKey(name) )
            return userServicePool.get(name);
        else
            return userServicePool.get("default");
    }
    public AutositeUserService getUserService(){
        return userServicePool.get("default");
    }
    public AutositeUserService getUserService(String name){
        
        if ( userServicePool.containsKey(name) )
            return userServicePool.get(name);
        else 
            return userServicePool.get("default");
    }

    public Map<String, AutositeUserService> getUserServicePool() {
        return userServicePool;
    }

    public void setUserServicePool(Map<String, AutositeUserService> userServicePool) {
        this.userServicePool = userServicePool;
    }
    
    
    
    
}
