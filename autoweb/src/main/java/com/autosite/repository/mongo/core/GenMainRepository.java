/* 
Template last modification history:


Source Generated: Sun Mar 15 01:49:44 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;

import com.autosite.db.GenMain;
import java.util.List;

public interface GenMainRepository extends PagingAndSortingRepository<GenMain> {

    GenMain save(GenMain customer);
    GenMain findOne(Long id);
    List<GenMain> removeById(Long id);

    List<GenMain> findById(Long value);
    List<GenMain> findBySiteId(Long value);
    List<GenMain> findByActive(Integer value);
    List<GenMain> findByValue(Integer value);
    List<GenMain> findByData(String value);
    List<GenMain> findByRequired(String value);
    
}


