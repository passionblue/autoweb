package com.autosite.mail;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutositeMailServiceFactory {
    
    public static final String DEFAULT_EMAIL_KEY = "_DEFAULT_";
    
    private static AutositeMailServiceFactory m_instance ;

    public static synchronized AutositeMailServiceFactory getInstance() {
        if ( m_instance == null)  {
            
            ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-mail.xml");
            m_instance = (AutositeMailServiceFactory) context.getBean("mailSenderFactory");
        }
        return m_instance;
    }

    private AutositeMailServiceFactory() {

    }
    
    private Map<String, AutositeMailService> m_mailServicePool = new ConcurrentHashMap();


    
    public Map<String, AutositeMailService> getMailServicePool() {
        return m_mailServicePool;
    }

    public void setMailServicePool(Map<String, AutositeMailService> mailServicePool) {
        m_mailServicePool = mailServicePool;
    }

    public AutositeMailService getAutositeService(String emailKey){
        if ( m_mailServicePool.containsKey(emailKey) ) 
            return m_mailServicePool.get(emailKey);
        
        return m_mailServicePool.get(DEFAULT_EMAIL_KEY);
    }    
    
}
