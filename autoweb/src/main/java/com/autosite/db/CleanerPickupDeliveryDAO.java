package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * CleanerPickupDelivery entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerPickupDelivery
 * @author MyEclipse Persistence Tools
 */
public class CleanerPickupDeliveryDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerPickupDeliveryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String LOCATION_ID = "locationId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String TICKET_ID = "ticketId";
    public static final String TICKET_UID = "ticketUid";
    public static final String PICKUP_TICKET = "pickupTicket";
    public static final String CHECKIN_TICKET_FOR_DELIVERY = "checkinTicketForDelivery";
    public static final String IS_DELIVERY_REQUEST = "isDeliveryRequest";
    public static final String IS_WEB_REQUEST = "isWebRequest";
    public static final String IS_RECURRING_REQUEST = "isRecurringRequest";
    public static final String IS_RECEIVE_READY = "isReceiveReady";
    public static final String IS_RECEIVE_COMPLETE = "isReceiveComplete";
    public static final String RECUR_ID = "recurId";
    public static final String CANCELLED = "cancelled";
    public static final String COMPLETED = "completed";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String ADDRESS = "address";
    public static final String APT_NUMBER = "aptNumber";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String ACK_RECEIVE_METHOD = "ackReceiveMethod";
    public static final String CUSTOMER_INSTRUCTION = "customerInstruction";
    public static final String PICKUP_DELIVERY_BY_DAY = "pickupDeliveryByDay";
    public static final String PICKUP_DELIVERY_BY_TIME = "pickupDeliveryByTime";
    public static final String ACKED_BY_USER_ID = "ackedByUserId";
    public static final String NOTE = "note";
    public static final String PICKUP_NOTE = "pickupNote";
    public static final String DELIVERY_NOTE = "deliveryNote";

    public void save(CleanerPickupDelivery transientInstance) {
        log.debug("saving CleanerPickupDelivery instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerPickupDelivery persistentInstance) {
        log.debug("deleting CleanerPickupDelivery instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerPickupDelivery findById(Long id) {
        log.debug("getting CleanerPickupDelivery instance with id: " + id);
        try {
            CleanerPickupDelivery instance = (CleanerPickupDelivery) getSession().get("com.autosite.db.CleanerPickupDelivery", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerPickupDelivery instance) {
        log.debug("finding CleanerPickupDelivery instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerPickupDelivery").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerPickupDelivery instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerPickupDelivery as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySiteId(Object siteId) {
        return findByProperty(SITE_ID, siteId);
    }

    public List findByLocationId(Object locationId) {
        return findByProperty(LOCATION_ID, locationId);
    }

    public List findByCustomerId(Object customerId) {
        return findByProperty(CUSTOMER_ID, customerId);
    }

    public List findByTicketId(Object ticketId) {
        return findByProperty(TICKET_ID, ticketId);
    }

    public List findByTicketUid(Object ticketUid) {
        return findByProperty(TICKET_UID, ticketUid);
    }

    public List findByPickupTicket(Object pickupTicket) {
        return findByProperty(PICKUP_TICKET, pickupTicket);
    }

    public List findByCheckinTicketForDelivery(Object checkinTicketForDelivery) {
        return findByProperty(CHECKIN_TICKET_FOR_DELIVERY, checkinTicketForDelivery);
    }

    public List findByIsDeliveryRequest(Object isDeliveryRequest) {
        return findByProperty(IS_DELIVERY_REQUEST, isDeliveryRequest);
    }

    public List findByIsWebRequest(Object isWebRequest) {
        return findByProperty(IS_WEB_REQUEST, isWebRequest);
    }

    public List findByIsRecurringRequest(Object isRecurringRequest) {
        return findByProperty(IS_RECURRING_REQUEST, isRecurringRequest);
    }

    public List findByIsReceiveReady(Object isReceiveReady) {
        return findByProperty(IS_RECEIVE_READY, isReceiveReady);
    }

    public List findByIsReceiveComplete(Object isReceiveComplete) {
        return findByProperty(IS_RECEIVE_COMPLETE, isReceiveComplete);
    }

    public List findByRecurId(Object recurId) {
        return findByProperty(RECUR_ID, recurId);
    }

    public List findByCancelled(Object cancelled) {
        return findByProperty(CANCELLED, cancelled);
    }

    public List findByCompleted(Object completed) {
        return findByProperty(COMPLETED, completed);
    }

    public List findByCustomerName(Object customerName) {
        return findByProperty(CUSTOMER_NAME, customerName);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByAptNumber(Object aptNumber) {
        return findByProperty(APT_NUMBER, aptNumber);
    }

    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
    }

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findByAckReceiveMethod(Object ackReceiveMethod) {
        return findByProperty(ACK_RECEIVE_METHOD, ackReceiveMethod);
    }

    public List findByCustomerInstruction(Object customerInstruction) {
        return findByProperty(CUSTOMER_INSTRUCTION, customerInstruction);
    }

    public List findByPickupDeliveryByDay(Object pickupDeliveryByDay) {
        return findByProperty(PICKUP_DELIVERY_BY_DAY, pickupDeliveryByDay);
    }

    public List findByPickupDeliveryByTime(Object pickupDeliveryByTime) {
        return findByProperty(PICKUP_DELIVERY_BY_TIME, pickupDeliveryByTime);
    }

    public List findByAckedByUserId(Object ackedByUserId) {
        return findByProperty(ACKED_BY_USER_ID, ackedByUserId);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByPickupNote(Object pickupNote) {
        return findByProperty(PICKUP_NOTE, pickupNote);
    }

    public List findByDeliveryNote(Object deliveryNote) {
        return findByProperty(DELIVERY_NOTE, deliveryNote);
    }

    public List findAll() {
        log.debug("finding all CleanerPickupDelivery instances");
        try {
            String queryString = "from CleanerPickupDelivery";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerPickupDelivery merge(CleanerPickupDelivery detachedInstance) {
        log.debug("merging CleanerPickupDelivery instance");
        try {
            CleanerPickupDelivery result = (CleanerPickupDelivery) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerPickupDelivery instance) {
        log.debug("attaching dirty CleanerPickupDelivery instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerPickupDelivery instance) {
        log.debug("attaching clean CleanerPickupDelivery instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
