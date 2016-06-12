/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;
import java.util.List;


import com.autosite.holder.GenFormFlowDataHolder;

public interface GenFormFlowRepository extends PagingAndSortingRepository<GenFormFlowDataHolder> {

    GenFormFlowDataHolder save(GenFormFlowDataHolder customer);
    GenFormFlowDataHolder findOne(Long id);
//    List<GenFormFlowDataHolder> removeById(Long id);

    
}


