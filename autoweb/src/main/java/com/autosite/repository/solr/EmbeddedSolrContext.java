package com.autosite.repository.solr;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactoryBean;

@Configuration
@EnableSolrRepositories("net.petrikainulainen.spring.datasolr.todo.repository.solr")
@Profile("dev")
@PropertySource("classpath:application.properties")
public class EmbeddedSolrContext {
 
    @Resource
    private Environment environment;
 
    @Bean
    public EmbeddedSolrServerFactoryBean solrServerFactoryBean() {
        EmbeddedSolrServerFactoryBean factory = new EmbeddedSolrServerFactoryBean();
 
        factory.setSolrHome(environment.getRequiredProperty("solr.solr.home"));
 
        return factory;
    }
 
    @Bean
    public SolrTemplate solrTemplate() throws Exception {
        return new SolrTemplate(solrServerFactoryBean().getObject());
    }
}