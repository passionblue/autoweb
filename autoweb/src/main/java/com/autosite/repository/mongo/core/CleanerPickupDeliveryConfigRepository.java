/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:46 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;

import com.autosite.db.CleanerPickupDeliveryConfig;
import java.util.List;

public interface CleanerPickupDeliveryConfigRepository extends PagingAndSortingRepository<CleanerPickupDeliveryConfig> {

    CleanerPickupDeliveryConfig save(CleanerPickupDeliveryConfig customer);
    CleanerPickupDeliveryConfig findOne(Long id);
    List<CleanerPickupDeliveryConfig> removeById(Long id);

    List<CleanerPickupDeliveryConfig> findById(Long value);
    List<CleanerPickupDeliveryConfig> findBySiteId(Long value);
    List<CleanerPickupDeliveryConfig> findByLocationId(Long value);
    List<CleanerPickupDeliveryConfig> findByApplyAllLocations(Integer value);
    List<CleanerPickupDeliveryConfig> findByDisableWebRequest(Integer value);
    List<CleanerPickupDeliveryConfig> findByDisallowAnonymousRequest(Integer value);
    List<CleanerPickupDeliveryConfig> findByRequireCustomerRegister(Integer value);
    List<CleanerPickupDeliveryConfig> findByRequireCustomerLogin(Integer value);
    
}


