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
 * EcWishList entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.EcWishList
 * @author MyEclipse Persistence Tools
 */

public class EcWishListDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcWishListDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String PRODUCT_ID = "productId";
    public static final String SIZE_VARIATION = "sizeVariation";
    public static final String COLOR_VARIATION = "colorVariation";
    public static final String SAVED_PRICE = "savedPrice";

    public void save(EcWishList transientInstance) {
        log.debug("saving EcWishList instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcWishList persistentInstance) {
        log.debug("deleting EcWishList instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcWishList findById(Long id) {
        log.debug("getting EcWishList instance with id: " + id);
        try {
            EcWishList instance = (EcWishList) getSession().get("com.autosite.db.EcWishList", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcWishList instance) {
        log.debug("finding EcWishList instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcWishList").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcWishList instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcWishList as model where model." + propertyName + "= ?";
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

    public List findByProductId(Object productId) {
        return findByProperty(PRODUCT_ID, productId);
    }

    public List findBySizeVariation(Object sizeVariation) {
        return findByProperty(SIZE_VARIATION, sizeVariation);
    }

    public List findByColorVariation(Object colorVariation) {
        return findByProperty(COLOR_VARIATION, colorVariation);
    }

    public List findBySavedPrice(Object savedPrice) {
        return findByProperty(SAVED_PRICE, savedPrice);
    }

    public List findAll() {
        log.debug("finding all EcWishList instances");
        try {
            String queryString = "from EcWishList";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcWishList merge(EcWishList detachedInstance) {
        log.debug("merging EcWishList instance");
        try {
            EcWishList result = (EcWishList) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcWishList instance) {
        log.debug("attaching dirty EcWishList instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcWishList instance) {
        log.debug("attaching clean EcWishList instance");
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
