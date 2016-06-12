package com.autosite.repository;

import org.springframework.data.repository.Repository;

public interface AutositeRepositoryFactory {

    Repository getRepository(String collectionName);
    Repository getRepository(Class clazz);
}
