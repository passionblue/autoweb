package com.autosite.lab.mongo.core;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PagingAndSortingRepository<T> extends CrudRepository<T> {

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);
}
