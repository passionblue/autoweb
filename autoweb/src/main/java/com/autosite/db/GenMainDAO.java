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
 * GenMain entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.GenMain
 * @author MyEclipse Persistence Tools
 */
public class GenMainDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(GenMainDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String ACTIVE = "active";
    public static final String VALUE = "value";
    public static final String DATA = "data";
    public static final String REQUIRED = "required";

    public void save(GenMain transientInstance) {
        log.debug("saving GenMain instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(GenMain persistentInstance) {
        log.debug("deleting GenMain instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public GenMain findById(Long id) {
        log.debug("getting GenMain instance with id: " + id);
        try {
            GenMain instance = (GenMain) getSession().get("com.autosite.db.GenMain", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(GenMain instance) {
        log.debug("finding GenMain instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.GenMain").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding GenMain instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GenMain as model where model." + propertyName + "= ?";
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

    public List findByActive(Object active) {
        return findByProperty(ACTIVE, active);
    }

    public List findByValue(Object value) {
        return findByProperty(VALUE, value);
    }

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findByRequired(Object required) {
        return findByProperty(REQUIRED, required);
    }

    public List findAll() {
        log.debug("finding all GenMain instances");
        try {
            String queryString = "from GenMain";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public GenMain merge(GenMain detachedInstance) {
        log.debug("merging GenMain instance");
        try {
            GenMain result = (GenMain) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(GenMain instance) {
        log.debug("attaching dirty GenMain instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(GenMain instance) {
        log.debug("attaching clean GenMain instance");
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
