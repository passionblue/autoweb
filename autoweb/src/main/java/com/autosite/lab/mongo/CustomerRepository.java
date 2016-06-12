package com.autosite.lab.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<BaseData, String> {

    public BaseData findByFirstName(String firstName);
    public List<BaseData> findByLastName(String lastName);

}
