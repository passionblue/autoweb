package com.autosite.repository.solr;

import java.net.MalformedURLException;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;


@Configuration
//@EnableSolrRepositories(basePackages = { "com.autosite.repository.solr.autorepo" }, multicoreSupport = true)
@EnableSolrRepositories("com.autosite.repository.solr.autorepo")
public class AutositeSolrApplicationConfig {

    
    
    
    private @Resource
    Environment env;

    @Bean
    public SolrServer solrServer() throws MalformedURLException, IllegalStateException {
//      return new HttpSolrServer(env.getRequiredProperty("solr.host"));
      return new HttpSolrServer("http://localhost:8984/solr");
    }

    @Bean
    public SolrTemplate solrTemplate(SolrServer server) throws Exception {
        return new SolrTemplate(server);
    }

}
