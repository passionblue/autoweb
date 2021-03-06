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
 * DisplayOrder entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.DisplayOrder
 * @author MyEclipse Persistence Tools
 */

public class DisplayOrderDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(DisplayOrderDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String ORDER_KEY = "orderKey";
    public static final String ORDER_LIST = "orderList";

    public void save(DisplayOrder transientInstance) {
        log.debug("saving DisplayOrder instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DisplayOrder persistentInstance) {
        log.debug("deleting DisplayOrder instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public DisplayOrder findById(Long id) {
        log.debug("getting DisplayOrder instance with id: " + id);
        try {
            DisplayOrder instance = (DisplayOrder) getSession().get("com.autosite.db.DisplayOrder", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(DisplayOrder instance) {
        log.debug("finding DisplayOrder instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.DisplayOrder").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding DisplayOrder instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from DisplayOrder as model where model." + propertyName + "= ?";
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

    public List findByOrderKey(Object orderKey) {
        return findByProperty(ORDER_KEY, orderKey);
    }

    public List findByOrderList(Object orderList) {
        return findByProperty(ORDER_LIST, orderList);
    }

    public List findAll() {
        log.debug("finding all DisplayOrder instances");
        try {
            String queryString = "from DisplayOrder";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public DisplayOrder merge(DisplayOrder detachedInstance) {
        log.debug("merging DisplayOrder instance");
        try {
            DisplayOrder result = (DisplayOrder) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DisplayOrder instance) {
        log.debug("attaching dirty DisplayOrder instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(DisplayOrder instance) {
        log.debug("attaching clean DisplayOrder instance");
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
