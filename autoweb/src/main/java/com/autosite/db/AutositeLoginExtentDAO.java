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
 * AutositeLoginExtent entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.AutositeLoginExtent
 * @author MyEclipse Persistence Tools
 */

public class AutositeLoginExtentDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(AutositeLoginExtentDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CLASS_NAME = "className";
    public static final String INACTIVE = "inactive";

    public void save(AutositeLoginExtent transientInstance) {
        log.debug("saving AutositeLoginExtent instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AutositeLoginExtent persistentInstance) {
        log.debug("deleting AutositeLoginExtent instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AutositeLoginExtent findById(Long id) {
        log.debug("getting AutositeLoginExtent instance with id: " + id);
        try {
            AutositeLoginExtent instance = (AutositeLoginExtent) getSession().get("com.autosite.db.AutositeLoginExtent", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AutositeLoginExtent instance) {
        log.debug("finding AutositeLoginExtent instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.AutositeLoginExtent").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AutositeLoginExtent instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AutositeLoginExtent as model where model." + propertyName + "= ?";
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

    public List findByClassName(Object className) {
        return findByProperty(CLASS_NAME, className);
    }

    public List findByInactive(Object inactive) {
        return findByProperty(INACTIVE, inactive);
    }

    public List findAll() {
        log.debug("finding all AutositeLoginExtent instances");
        try {
            String queryString = "from AutositeLoginExtent";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AutositeLoginExtent merge(AutositeLoginExtent detachedInstance) {
        log.debug("merging AutositeLoginExtent instance");
        try {
            AutositeLoginExtent result = (AutositeLoginExtent) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AutositeLoginExtent instance) {
        log.debug("attaching dirty AutositeLoginExtent instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AutositeLoginExtent instance) {
        log.debug("attaching clean AutositeLoginExtent instance");
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
