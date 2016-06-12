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
 * CleanerUser entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerUser
 * @author MyEclipse Persistence Tools
 */
public class CleanerUserDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerUserDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String AUTOSITE_USER_ID = "autositeUserId";
    public static final String INACTIVATED = "inactivated";

    public void save(CleanerUser transientInstance) {
        log.debug("saving CleanerUser instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerUser persistentInstance) {
        log.debug("deleting CleanerUser instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerUser findById(Long id) {
        log.debug("getting CleanerUser instance with id: " + id);
        try {
            CleanerUser instance = (CleanerUser) getSession().get("com.autosite.db.CleanerUser", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerUser instance) {
        log.debug("finding CleanerUser instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerUser").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerUser instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerUser as model where model." + propertyName + "= ?";
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

    public List findByAutositeUserId(Object autositeUserId) {
        return findByProperty(AUTOSITE_USER_ID, autositeUserId);
    }

    public List findByInactivated(Object inactivated) {
        return findByProperty(INACTIVATED, inactivated);
    }

    public List findAll() {
        log.debug("finding all CleanerUser instances");
        try {
            String queryString = "from CleanerUser";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerUser merge(CleanerUser detachedInstance) {
        log.debug("merging CleanerUser instance");
        try {
            CleanerUser result = (CleanerUser) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerUser instance) {
        log.debug("attaching dirty CleanerUser instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerUser instance) {
        log.debug("attaching clean CleanerUser instance");
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
