/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:44 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;

import com.autosite.db.CleanerPickupDelivery;
import java.util.List;

public interface CleanerPickupDeliveryRepository extends PagingAndSortingRepository<CleanerPickupDelivery> {

    CleanerPickupDelivery save(CleanerPickupDelivery customer);
    CleanerPickupDelivery findOne(Long id);
    List<CleanerPickupDelivery> removeById(Long id);

    List<CleanerPickupDelivery> findById(Long value);
    List<CleanerPickupDelivery> findBySiteId(Long value);
    List<CleanerPickupDelivery> findByLocationId(Long value);
    List<CleanerPickupDelivery> findByCustomerId(Long value);
    List<CleanerPickupDelivery> findByTicketId(Long value);
    List<CleanerPickupDelivery> findByTicketUid(String value);
    List<CleanerPickupDelivery> findByPickupTicket(String value);
    List<CleanerPickupDelivery> findByCheckinTicketForDelivery(String value);
    List<CleanerPickupDelivery> findByIsDeliveryRequest(Integer value);
    List<CleanerPickupDelivery> findByIsWebRequest(Integer value);
    List<CleanerPickupDelivery> findByIsRecurringRequest(Integer value);
    List<CleanerPickupDelivery> findByIsReceiveReady(Integer value);
    List<CleanerPickupDelivery> findByIsReceiveComplete(Integer value);
    List<CleanerPickupDelivery> findByRecurId(Long value);
    List<CleanerPickupDelivery> findByCancelled(Integer value);
    List<CleanerPickupDelivery> findByCompleted(Integer value);
    List<CleanerPickupDelivery> findByCustomerName(String value);
    List<CleanerPickupDelivery> findByAddress(String value);
    List<CleanerPickupDelivery> findByAptNumber(String value);
    List<CleanerPickupDelivery> findByPhone(String value);
    List<CleanerPickupDelivery> findByEmail(String value);
    List<CleanerPickupDelivery> findByAckReceiveMethod(Integer value);
    List<CleanerPickupDelivery> findByCustomerInstruction(String value);
    List<CleanerPickupDelivery> findByPickupDeliveryByDay(Integer value);
    List<CleanerPickupDelivery> findByPickupDeliveryByTime(String value);
    List<CleanerPickupDelivery> findByAckedByUserId(Long value);
    List<CleanerPickupDelivery> findByNote(String value);
    List<CleanerPickupDelivery> findByPickupNote(String value);
    List<CleanerPickupDelivery> findByDeliveryNote(String value);
    
}


