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
import com.autosite.repository.solr.core.ProductRepository;
import com.autosite.repository.solr.core.SolrProductRepository;
import com.autosite.repository.solr.model.Product;


public class ProductSolrRepositoryFactory implements AutositeRepositoryFactory {

    private static Logger m_logger = LoggerFactory.getLogger(ProductSolrRepositoryFactory.class);
    
    private static ProductSolrRepositoryFactory m_instance = new ProductSolrRepositoryFactory();
    private ApplicationContext m_context;
    
    
    public static ProductSolrRepositoryFactory getInstance() {
        return m_instance;
    }

    private ProductSolrRepositoryFactory() {
        m_context = new AnnotationConfigApplicationContext(ProductSolrApplicationConfig.class);
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
            repo =  (Repository)m_context.getBean(clazz);
        }
        catch (BeansException e) {
            m_logger.error(e.getMessage(),e);
        }
        return repo;
        }
    
    public static class AutositMongoRepositoryImpl {
        
        
        
    }

    public static void main(String[] args) throws Exception {

        ProductSolrRepositoryFactory repoFactory = ProductSolrRepositoryFactory.getInstance();
        
        
        System.out.println(repoFactory);
        
        Product d = new Product();
        d.setName("bjoi");
        
        ProductRepository repo = (ProductRepository)repoFactory.getRepository(SolrProductRepository.class);
        repo.save(d);
    }
    

}
