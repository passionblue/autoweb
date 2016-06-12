package com.autosite.repository.solr.autorepo;


import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;


public interface CleanerPickupDeliveryRepository extends SolrCrudRepository<CleanerPickupDeliveryX, Long> {

    CleanerPickupDeliveryX save(CleanerPickupDeliveryX customer);
    CleanerPickupDeliveryX findOne(Long id);
    List<CleanerPickupDeliveryX> removeById(Long id);

    List<CleanerPickupDeliveryX> findById(Long value);
    List<CleanerPickupDeliveryX> findBySiteId(Long value);
    List<CleanerPickupDeliveryX> findByLocationId(Long value);
    List<CleanerPickupDeliveryX> findByCustomerId(Long value);
    List<CleanerPickupDeliveryX> findByTicketId(Long value);
    List<CleanerPickupDeliveryX> findByTicketUid(String value);
    List<CleanerPickupDeliveryX> findByPickupTicket(String value);
    
}


