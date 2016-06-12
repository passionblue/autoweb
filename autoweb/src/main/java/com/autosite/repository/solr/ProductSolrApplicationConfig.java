package com.autosite.repository.solr;

import java.net.MalformedURLException;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.support.SolrRepositoryFactory;

import com.autosite.repository.solr.core.CustomSolrRepositoryImpl;
import com.autosite.repository.solr.core.DerivedSolrProductRepository;
import com.autosite.repository.solr.core.SolrProductRepository;


@Configuration
//@EnableSolrRepositories(basePackages = { "com.autosite.repository.solr.autorepo" }, multicoreSupport = true)
//@EnableSolrRepositories("com.autosite.repository.solr.core")
public class ProductSolrApplicationConfig {

    private static Logger m_logger = LoggerFactory.getLogger(ProductSolrApplicationConfig.class);
    
    private @Resource
    Environment env;

    @Bean
    public SolrServer solrServer() throws MalformedURLException, IllegalStateException {
//      return new HttpSolrServer(env.getRequiredProperty("solr.host"));
      return new HttpSolrServer("http://localhost:8984/solr");
    }

    @Bean
    public SolrTemplate solrTemplate(SolrServer server) throws Exception {
        m_logger.debug("solrTemplate");
        return new SolrTemplate(server);
    }
    
/*    
    @Bean
    public HttpSolrServerFactoryBean solrServerFactoryBean() {
        HttpSolrServerFactoryBean factory = new HttpSolrServerFactoryBean();
 
        factory.setUrl("http://localhost:8984/solr");
 
        return factory;
    }*/

    
    @Bean
    public SolrProductRepository solrProductRepository() throws Exception {
        m_logger.debug("solrProductRepository");
        return new com.autosite.repository.solr.core.SolrProductRepository( new SolrTemplate(solrServer()));
    }
    
    @Bean
    public DerivedSolrProductRepository derivedSolrProductRepository() {
        
        m_logger.debug("derivedSolrProductRepository");
        SolrOperations s = null;
        try {
            s = new SolrTemplate(solrServer());
            DerivedSolrProductRepository repo = new SolrRepositoryFactory(s).getRepository(DerivedSolrProductRepository.class, new CustomSolrRepositoryImpl(s));

            return repo;
        }
        catch (Exception e) {
        }
        
        
        return null;
    }
    
    
}
