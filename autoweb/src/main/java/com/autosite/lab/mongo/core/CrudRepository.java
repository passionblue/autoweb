package com.autosite.lab.mongo.core;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * spring data mongo long Could not lookup mapping metadata for domain class java.lang.Object!
 * This can be solved by annotating the PageableIdsRepositorywith @NoRepositoryBean
 * 
 */

@NoRepositoryBean
public interface CrudRepository<T> extends Repository<T, Long> {

    Iterable<T> findAll();          

    Long count();                   

    void delete(T entity);          

    boolean exists(Long primaryKey);  

    // … more functionality omitted.
}

