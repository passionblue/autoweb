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
 * CleanerLocation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerLocation
 * @author MyEclipse Persistence Tools
 */
public class CleanerLocationDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerLocationDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String ADDRESS = "address";
    public static final String CITY_ZIP = "cityZip";
    public static final String PHONE = "phone";
    public static final String MANAGER_ID = "managerId";

    public void save(CleanerLocation transientInstance) {
        log.debug("saving CleanerLocation instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerLocation persistentInstance) {
        log.debug("deleting CleanerLocation instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerLocation findById(Long id) {
        log.debug("getting CleanerLocation instance with id: " + id);
        try {
            CleanerLocation instance = (CleanerLocation) getSession().get("com.autosite.db.CleanerLocation", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerLocation instance) {
        log.debug("finding CleanerLocation instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerLocation").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerLocation instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerLocation as model where model." + propertyName + "= ?";
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

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByCityZip(Object cityZip) {
        return findByProperty(CITY_ZIP, cityZip);
    }

    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
    }

    public List findByManagerId(Object managerId) {
        return findByProperty(MANAGER_ID, managerId);
    }

    public List findAll() {
        log.debug("finding all CleanerLocation instances");
        try {
            String queryString = "from CleanerLocation";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerLocation merge(CleanerLocation detachedInstance) {
        log.debug("merging CleanerLocation instance");
        try {
            CleanerLocation result = (CleanerLocation) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerLocation instance) {
        log.debug("attaching dirty CleanerLocation instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerLocation instance) {
        log.debug("attaching clean CleanerLocation instance");
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
