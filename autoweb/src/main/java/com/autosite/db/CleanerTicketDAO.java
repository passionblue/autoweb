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
 * CleanerTicket entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerTicket
 * @author MyEclipse Persistence Tools
 */
public class CleanerTicketDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerTicketDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String SERIAL = "serial";
    public static final String PARENT_TICKET_ID = "parentTicketId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String ENTER_USER_ID = "enterUserId";
    public static final String LOCATION_ID = "locationId";
    public static final String NOTE = "note";
    public static final String COMPLETED = "completed";
    public static final String ONHOLD = "onhold";
    public static final String ORIGINAL_TICKET_ID = "originalTicketId";
    public static final String RETURNED = "returned";
    public static final String RETURNED_REASON_TEXT = "returnedReasonText";
    public static final String RETURNED_NOTE = "returnedNote";
    public static final String TOTAL_CHARGE = "totalCharge";
    public static final String FINAL_CHARGE = "finalCharge";
    public static final String DISCOUNT_ID = "discountId";
    public static final String DISCOUNT_AMOUNT = "discountAmount";
    public static final String DISCOUNT_NOTE = "discountNote";

    public void save(CleanerTicket transientInstance) {
        log.debug("saving CleanerTicket instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerTicket persistentInstance) {
        log.debug("deleting CleanerTicket instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerTicket findById(Long id) {
        log.debug("getting CleanerTicket instance with id: " + id);
        try {
            CleanerTicket instance = (CleanerTicket) getSession().get("com.autosite.db.CleanerTicket", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerTicket instance) {
        log.debug("finding CleanerTicket instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerTicket").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerTicket instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerTicket as model where model." + propertyName + "= ?";
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

    public List findBySerial(Object serial) {
        return findByProperty(SERIAL, serial);
    }

    public List findByParentTicketId(Object parentTicketId) {
        return findByProperty(PARENT_TICKET_ID, parentTicketId);
    }

    public List findByCustomerId(Object customerId) {
        return findByProperty(CUSTOMER_ID, customerId);
    }

    public List findByEnterUserId(Object enterUserId) {
        return findByProperty(ENTER_USER_ID, enterUserId);
    }

    public List findByLocationId(Object locationId) {
        return findByProperty(LOCATION_ID, locationId);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByCompleted(Object completed) {
        return findByProperty(COMPLETED, completed);
    }

    public List findByOnhold(Object onhold) {
        return findByProperty(ONHOLD, onhold);
    }

    public List findByOriginalTicketId(Object originalTicketId) {
        return findByProperty(ORIGINAL_TICKET_ID, originalTicketId);
    }

    public List findByReturned(Object returned) {
        return findByProperty(RETURNED, returned);
    }

    public List findByReturnedReasonText(Object returnedReasonText) {
        return findByProperty(RETURNED_REASON_TEXT, returnedReasonText);
    }

    public List findByReturnedNote(Object returnedNote) {
        return findByProperty(RETURNED_NOTE, returnedNote);
    }

    public List findByTotalCharge(Object totalCharge) {
        return findByProperty(TOTAL_CHARGE, totalCharge);
    }

    public List findByFinalCharge(Object finalCharge) {
        return findByProperty(FINAL_CHARGE, finalCharge);
    }

    public List findByDiscountId(Object discountId) {
        return findByProperty(DISCOUNT_ID, discountId);
    }

    public List findByDiscountAmount(Object discountAmount) {
        return findByProperty(DISCOUNT_AMOUNT, discountAmount);
    }

    public List findByDiscountNote(Object discountNote) {
        return findByProperty(DISCOUNT_NOTE, discountNote);
    }

    public List findAll() {
        log.debug("finding all CleanerTicket instances");
        try {
            String queryString = "from CleanerTicket";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerTicket merge(CleanerTicket detachedInstance) {
        log.debug("merging CleanerTicket instance");
        try {
            CleanerTicket result = (CleanerTicket) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerTicket instance) {
        log.debug("attaching dirty CleanerTicket instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerTicket instance) {
        log.debug("attaching clean CleanerTicket instance");
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
