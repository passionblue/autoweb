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
 * SiteHeader entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.SiteHeader
 * @author MyEclipse Persistence Tools
 */

public class SiteHeaderDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SiteHeaderDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String AS_IS = "asIs";
    public static final String INCLUDE_TYPE = "includeType";
    public static final String INCLUDE_TEXT = "includeText";

    public void save(SiteHeader transientInstance) {
        log.debug("saving SiteHeader instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SiteHeader persistentInstance) {
        log.debug("deleting SiteHeader instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SiteHeader findById(Long id) {
        log.debug("getting SiteHeader instance with id: " + id);
        try {
            SiteHeader instance = (SiteHeader) getSession().get("com.autosite.db.SiteHeader", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SiteHeader instance) {
        log.debug("finding SiteHeader instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SiteHeader").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SiteHeader instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SiteHeader as model where model." + propertyName + "= ?";
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

    public List findByAsIs(Object asIs) {
        return findByProperty(AS_IS, asIs);
    }

    public List findByIncludeType(Object includeType) {
        return findByProperty(INCLUDE_TYPE, includeType);
    }

    public List findByIncludeText(Object includeText) {
        return findByProperty(INCLUDE_TEXT, includeText);
    }

    public List findAll() {
        log.debug("finding all SiteHeader instances");
        try {
            String queryString = "from SiteHeader";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SiteHeader merge(SiteHeader detachedInstance) {
        log.debug("merging SiteHeader instance");
        try {
            SiteHeader result = (SiteHeader) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SiteHeader instance) {
        log.debug("attaching dirty SiteHeader instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SiteHeader instance) {
        log.debug("attaching clean SiteHeader instance");
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
