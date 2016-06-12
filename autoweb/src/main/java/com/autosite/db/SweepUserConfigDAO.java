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
 * SweepUserConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SweepUserConfig
 * @author MyEclipse Persistence Tools
 */

public class SweepUserConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SweepUserConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String MAX_SWEEP_ALLOWED = "maxSweepAllowed";

    public void save(SweepUserConfig transientInstance) {
        log.debug("saving SweepUserConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SweepUserConfig persistentInstance) {
        log.debug("deleting SweepUserConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SweepUserConfig findById(Long id) {
        log.debug("getting SweepUserConfig instance with id: " + id);
        try {
            SweepUserConfig instance = (SweepUserConfig) getSession().get("com.autosite.db.SweepUserConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SweepUserConfig instance) {
        log.debug("finding SweepUserConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SweepUserConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SweepUserConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SweepUserConfig as model where model." + propertyName + "= ?";
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

    public List findByMaxSweepAllowed(Object maxSweepAllowed) {
        return findByProperty(MAX_SWEEP_ALLOWED, maxSweepAllowed);
    }

    public List findAll() {
        log.debug("finding all SweepUserConfig instances");
        try {
            String queryString = "from SweepUserConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SweepUserConfig merge(SweepUserConfig detachedInstance) {
        log.debug("merging SweepUserConfig instance");
        try {
            SweepUserConfig result = (SweepUserConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SweepUserConfig instance) {
        log.debug("attaching dirty SweepUserConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SweepUserConfig instance) {
        log.debug("attaching clean SweepUserConfig instance");
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
