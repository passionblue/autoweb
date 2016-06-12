package com.autosite.repository.solr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.repository.Repository;

import com.autosite.repository.AutositeRepositoryFactory;
import com.autosite.repository.solr.autorepo.CleanerPickupDeliveryRepository;
import com.autosite.repository.solr.autorepo.CleanerPickupDeliveryX;
import com.autosite.repository.solr.model.Product;


public class AutositeSolrRepositoryFactory implements AutositeRepositoryFactory {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeSolrRepositoryFactory.class);
    
    private static AutositeSolrRepositoryFactory m_instance = new AutositeSolrRepositoryFactory();
    private ApplicationContext m_context;
    
    
    public static AutositeSolrRepositoryFactory getInstance() {
        return m_instance;
    }

    private AutositeSolrRepositoryFactory() {
        m_context = new AnnotationConfigApplicationContext(AutositeSolrApplicationConfig.class);
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
            repo = (Repository) m_context.getBean(clazz);
        }
        catch (BeansException e) {
            m_logger.error(e.getMessage(),e);
        }
        return repo;
    }
    
    public static class AutositMongoRepositoryImpl {
        
        
        
    }


    
    public static void main(String[] args) throws Exception {

        AutositeSolrRepositoryFactory repoFactory = AutositeSolrRepositoryFactory.getInstance();
        
        
        System.out.println(repoFactory);
        
        CleanerPickupDeliveryX d = new CleanerPickupDeliveryX();
        d.setPickupTicket("asdf");
        
        CleanerPickupDeliveryRepository repo = (CleanerPickupDeliveryRepository)repoFactory.getRepository(CleanerPickupDeliveryRepository.class);
        repo.save(d);
    }
}
