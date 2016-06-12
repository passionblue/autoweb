package com.autosite.repository.mongo;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.repository.Repository;

import com.autosite.db.CleanerPickupDelivery;
import com.autosite.repository.AutositeRepositoryFactory;
import com.autosite.repository.IdGenerator;
import com.autosite.repository.mongo.core.CleanerPickupDeliveryRepository;


public class AutositeMongoRepositoryFactory implements AutositeRepositoryFactory {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeMongoRepositoryFactory.class);
    
    private static AutositeMongoRepositoryFactory m_instance = new AutositeMongoRepositoryFactory();
    private ApplicationContext m_context;
    
    public static AutositeMongoRepositoryFactory getInstance() {
        return m_instance;
    }

    private AutositeMongoRepositoryFactory() {
        m_context = new AnnotationConfigApplicationContext(AutositeMongoApplicationConfig.class);
    }
    
    @Override
    public Repository getRepository(String collectionName) {
        
        try {
            Repository repo = (Repository)m_context.getBean(collectionName + "Repository");
            return repo;
        }
        catch (BeansException e) {
            m_logger.error(e.getMessage(),e);
        }
        return null;
    }
    
    @Override
    public Repository getRepository(Class clazz) {
        Repository repo = null;
        try {
            repo =  (Repository) m_context.getBean(clazz);
        }
        catch (BeansException e) {
            m_logger.error(e.getMessage(),e);
        }
        return repo;
    }
    
    public static class AutositMongoRepositoryImpl {
        
        
        
    }
    
    
    public static void main(String[] args) {
        
        CleanerPickupDeliveryRepository repository = (CleanerPickupDeliveryRepository) AutositeMongoRepositoryFactory.getInstance().getRepository(CleanerPickupDeliveryRepository.class);
        
        CleanerPickupDelivery c = new CleanerPickupDelivery();
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            c.setId(IdGenerator.getInstance().nextSequence(CleanerPickupDelivery.class.getSimpleName()));
            c.setEmail("xxxdg");
            
            CleanerPickupDelivery d = (CleanerPickupDelivery)repository.save(c);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        
        
        
        System.out.println(repository.count());
        Iterable i = repository.findAll();
        
        for (Iterator iterator = i.iterator(); iterator.hasNext();) {
            CleanerPickupDelivery type = (CleanerPickupDelivery) iterator.next();
            
            System.out.println(type.getId());
        }
        
        
        
      List l = repository.findByEmail("xxxdg");
      System.out.println(l.size());
        
    }
}
