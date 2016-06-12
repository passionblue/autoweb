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
 * SiteSuggest entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SiteSuggest
 * @author MyEclipse Persistence Tools
 */

public class SiteSuggestDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SiteSuggestDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CATEGORY = "category";
    public static final String SUGGEST = "suggest";
    public static final String RESOLVED = "resolved";

    public void save(SiteSuggest transientInstance) {
        log.debug("saving SiteSuggest instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SiteSuggest persistentInstance) {
        log.debug("deleting SiteSuggest instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SiteSuggest findById(Long id) {
        log.debug("getting SiteSuggest instance with id: " + id);
        try {
            SiteSuggest instance = (SiteSuggest) getSession().get("com.autosite.db.SiteSuggest", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SiteSuggest instance) {
        log.debug("finding SiteSuggest instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SiteSuggest").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SiteSuggest instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SiteSuggest as model where model." + propertyName + "= ?";
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

    public List findByCategory(Object category) {
        return findByProperty(CATEGORY, category);
    }

    public List findBySuggest(Object suggest) {
        return findByProperty(SUGGEST, suggest);
    }

    public List findByResolved(Object resolved) {
        return findByProperty(RESOLVED, resolved);
    }

    public List findAll() {
        log.debug("finding all SiteSuggest instances");
        try {
            String queryString = "from SiteSuggest";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SiteSuggest merge(SiteSuggest detachedInstance) {
        log.debug("merging SiteSuggest instance");
        try {
            SiteSuggest result = (SiteSuggest) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SiteSuggest instance) {
        log.debug("attaching dirty SiteSuggest instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SiteSuggest instance) {
        log.debug("attaching clean SiteSuggest instance");
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
