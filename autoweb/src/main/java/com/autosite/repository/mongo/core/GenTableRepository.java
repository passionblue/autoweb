/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;
import java.util.List;


import com.autosite.db.GenTable;

public interface GenTableRepository extends PagingAndSortingRepository<GenTable> {

    GenTable save(GenTable customer);
    GenTable findOne(Long id);
    List<GenTable> removeById(Long id);

    List<GenTable> findById(Long value);
    List<GenTable> findBySiteId(Long value);
    List<GenTable> findByCountry(String value);
    List<GenTable> findByAge(Integer value);
    List<GenTable> findByDisabled(Integer value);
    List<GenTable> findByComments(String value);
    
}


