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
 * CleanerPickupDeliveryConfig entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.autosite.db.CleanerPickupDeliveryConfig
 * @author MyEclipse Persistence Tools
 */
public class CleanerPickupDeliveryConfigDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerPickupDeliveryConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String LOCATION_ID = "locationId";
    public static final String APPLY_ALL_LOCATIONS = "applyAllLocations";
    public static final String DISABLE_WEB_REQUEST = "disableWebRequest";
    public static final String DISALLOW_ANONYMOUS_REQUEST = "disallowAnonymousRequest";
    public static final String REQUIRE_CUSTOMER_REGISTER = "requireCustomerRegister";
    public static final String REQUIRE_CUSTOMER_LOGIN = "requireCustomerLogin";

    public void save(CleanerPickupDeliveryConfig transientInstance) {
        log.debug("saving CleanerPickupDeliveryConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerPickupDeliveryConfig persistentInstance) {
        log.debug("deleting CleanerPickupDeliveryConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerPickupDeliveryConfig findById(Long id) {
        log.debug("getting CleanerPickupDeliveryConfig instance with id: " + id);
        try {
            CleanerPickupDeliveryConfig instance = (CleanerPickupDeliveryConfig) getSession().get("com.autosite.db.CleanerPickupDeliveryConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerPickupDeliveryConfig instance) {
        log.debug("finding CleanerPickupDeliveryConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerPickupDeliveryConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerPickupDeliveryConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerPickupDeliveryConfig as model where model." + propertyName + "= ?";
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

    public List findByApplyAllLocations(Object applyAllLocations) {
        return findByProperty(APPLY_ALL_LOCATIONS, applyAllLocations);
    }

    public List findByDisableWebRequest(Object disableWebRequest) {
        return findByProperty(DISABLE_WEB_REQUEST, disableWebRequest);
    }

    public List findByDisallowAnonymousRequest(Object disallowAnonymousRequest) {
        return findByProperty(DISALLOW_ANONYMOUS_REQUEST, disallowAnonymousRequest);
    }

    public List findByRequireCustomerRegister(Object requireCustomerRegister) {
        return findByProperty(REQUIRE_CUSTOMER_REGISTER, requireCustomerRegister);
    }

    public List findByRequireCustomerLogin(Object requireCustomerLogin) {
        return findByProperty(REQUIRE_CUSTOMER_LOGIN, requireCustomerLogin);
    }

    public List findAll() {
        log.debug("finding all CleanerPickupDeliveryConfig instances");
        try {
            String queryString = "from CleanerPickupDeliveryConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerPickupDeliveryConfig merge(CleanerPickupDeliveryConfig detachedInstance) {
        log.debug("merging CleanerPickupDeliveryConfig instance");
        try {
            CleanerPickupDeliveryConfig result = (CleanerPickupDeliveryConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerPickupDeliveryConfig instance) {
        log.debug("attaching dirty CleanerPickupDeliveryConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerPickupDeliveryConfig instance) {
        log.debug("attaching clean CleanerPickupDeliveryConfig instance");
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
