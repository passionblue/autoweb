package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * EcOrderPackage entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcOrderPackage
 * @author MyEclipse Persistence Tools
 */

public class EcOrderPackageDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcOrderPackageDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String ORDER_ID = "orderId";
    public static final String NUM_ORDER = "numOrder";
    public static final String SHIPPED = "shipped";

    public void save(EcOrderPackage transientInstance) {
        log.debug("saving EcOrderPackage instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcOrderPackage persistentInstance) {
        log.debug("deleting EcOrderPackage instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcOrderPackage findById(Long id) {
        log.debug("getting EcOrderPackage instance with id: " + id);
        try {
            EcOrderPackage instance = (EcOrderPackage) getSession().get("com.autosite.db.EcOrderPackage", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcOrderPackage instance) {
        log.debug("finding EcOrderPackage instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcOrderPackage").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcOrderPackage instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcOrderPackage as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByOrderId(Object orderId) {
        return findByProperty(ORDER_ID, orderId);
    }

    public List findByNumOrder(Object numOrder) {
        return findByProperty(NUM_ORDER, numOrder);
    }

    public List findByShipped(Object shipped) {
        return findByProperty(SHIPPED, shipped);
    }

    public List findAll() {
        log.debug("finding all EcOrderPackage instances");
        try {
            String queryString = "from EcOrderPackage";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcOrderPackage merge(EcOrderPackage detachedInstance) {
        log.debug("merging EcOrderPackage instance");
        try {
            EcOrderPackage result = (EcOrderPackage) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcOrderPackage instance) {
        log.debug("attaching dirty EcOrderPackage instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcOrderPackage instance) {
        log.debug("attaching clean EcOrderPackage instance");
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
