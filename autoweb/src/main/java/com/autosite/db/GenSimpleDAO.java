package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * GenSimple entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.GenSimple
 * @author MyEclipse Persistence Tools
 */

public class GenSimpleDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(GenSimpleDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String DATA = "data";
    public static final String ACTIVE = "active";

    public void save(GenSimple transientInstance) {
        log.debug("saving GenSimple instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(GenSimple persistentInstance) {
        log.debug("deleting GenSimple instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public GenSimple findById(Long id) {
        log.debug("getting GenSimple instance with id: " + id);
        try {
            GenSimple instance = (GenSimple) getSession().get("com.autosite.db.GenSimple", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(GenSimple instance) {
        log.debug("finding GenSimple instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.GenSimple").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding GenSimple instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GenSimple as model where model." + propertyName + "= ?";
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

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findByActive(Object active) {
        return findByProperty(ACTIVE, active);
    }

    public List findAll() {
        log.debug("finding all GenSimple instances");
        try {
            String queryString = "from GenSimple";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public GenSimple merge(GenSimple detachedInstance) {
        log.debug("merging GenSimple instance");
        try {
            GenSimple result = (GenSimple) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(GenSimple instance) {
        log.debug("attaching dirty GenSimple instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(GenSimple instance) {
        log.debug("attaching clean GenSimple instance");
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
