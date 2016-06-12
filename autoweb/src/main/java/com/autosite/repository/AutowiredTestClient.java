package com.autosite.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.autosite.repository.mongo.AutositeTestApplicationConfig;
import com.autosite.repository.mongo.core.CleanerPickupDeliveryRepository;

public class AutowiredTestClient {

    @Autowired
    private CleanerPickupDeliveryRepository repository;

    public CleanerPickupDeliveryRepository getRepository() {
        return repository;
    }

    public void setRepository(CleanerPickupDeliveryRepository repository) {
        this.repository = repository;
    }
    
    public static void main(String[] args) {
        
        
        ApplicationContext m_context = new AnnotationConfigApplicationContext(AutositeTestApplicationConfig.class);
        
        AutowiredTestClient cliet = m_context.getBean(AutowiredTestClient.class);
        
        System.out.println(cliet.getRepository());
    }
}
