package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * CleanerPickupDeliveryRecur entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.autosite.db.CleanerPickupDeliveryRecur
 * @author MyEclipse Persistence Tools
 */
public class CleanerPickupDeliveryRecurDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerPickupDeliveryRecurDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String WEEKDAY = "weekday";
    public static final String TIME_HHDD = "timeHhdd";

    public void save(CleanerPickupDeliveryRecur transientInstance) {
        log.debug("saving CleanerPickupDeliveryRecur instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerPickupDeliveryRecur persistentInstance) {
        log.debug("deleting CleanerPickupDeliveryRecur instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerPickupDeliveryRecur findById(Long id) {
        log.debug("getting CleanerPickupDeliveryRecur instance with id: " + id);
        try {
            CleanerPickupDeliveryRecur instance = (CleanerPickupDeliveryRecur) getSession().get("com.autosite.db.CleanerPickupDeliveryRecur", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerPickupDeliveryRecur instance) {
        log.debug("finding CleanerPickupDeliveryRecur instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerPickupDeliveryRecur").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerPickupDeliveryRecur instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerPickupDeliveryRecur as model where model." + propertyName + "= ?";
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

    public List findByCustomerId(Object customerId) {
        return findByProperty(CUSTOMER_ID, customerId);
    }

    public List findByWeekday(Object weekday) {
        return findByProperty(WEEKDAY, weekday);
    }

    public List findByTimeHhdd(Object timeHhdd) {
        return findByProperty(TIME_HHDD, timeHhdd);
    }

    public List findAll() {
        log.debug("finding all CleanerPickupDeliveryRecur instances");
        try {
            String queryString = "from CleanerPickupDeliveryRecur";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerPickupDeliveryRecur merge(CleanerPickupDeliveryRecur detachedInstance) {
        log.debug("merging CleanerPickupDeliveryRecur instance");
        try {
            CleanerPickupDeliveryRecur result = (CleanerPickupDeliveryRecur) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerPickupDeliveryRecur instance) {
        log.debug("attaching dirty CleanerPickupDeliveryRecur instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerPickupDeliveryRecur instance) {
        log.debug("attaching clean CleanerPickupDeliveryRecur instance");
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
