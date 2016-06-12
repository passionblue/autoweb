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
 * CleanerProduct entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerProduct
 * @author MyEclipse Persistence Tools
 */
public class CleanerProductDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerProductDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String GARMENT_TYPE_ID = "garmentTypeId";
    public static final String GARMENT_SERVICE_ID = "garmentServiceId";
    public static final String REGULAR_PRICE = "regularPrice";
    public static final String NOTE = "note";

    public void save(CleanerProduct transientInstance) {
        log.debug("saving CleanerProduct instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerProduct persistentInstance) {
        log.debug("deleting CleanerProduct instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerProduct findById(Long id) {
        log.debug("getting CleanerProduct instance with id: " + id);
        try {
            CleanerProduct instance = (CleanerProduct) getSession().get("com.autosite.db.CleanerProduct", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerProduct instance) {
        log.debug("finding CleanerProduct instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerProduct").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerProduct instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerProduct as model where model." + propertyName + "= ?";
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

    public List findByGarmentTypeId(Object garmentTypeId) {
        return findByProperty(GARMENT_TYPE_ID, garmentTypeId);
    }

    public List findByGarmentServiceId(Object garmentServiceId) {
        return findByProperty(GARMENT_SERVICE_ID, garmentServiceId);
    }

    public List findByRegularPrice(Object regularPrice) {
        return findByProperty(REGULAR_PRICE, regularPrice);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findAll() {
        log.debug("finding all CleanerProduct instances");
        try {
            String queryString = "from CleanerProduct";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerProduct merge(CleanerProduct detachedInstance) {
        log.debug("merging CleanerProduct instance");
        try {
            CleanerProduct result = (CleanerProduct) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerProduct instance) {
        log.debug("attaching dirty CleanerProduct instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerProduct instance) {
        log.debug("attaching clean CleanerProduct instance");
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
