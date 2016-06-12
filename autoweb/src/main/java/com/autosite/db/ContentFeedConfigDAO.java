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
 * ContentFeedConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ContentFeedConfig
 * @author MyEclipse Persistence Tools
 */

public class ContentFeedConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ContentFeedConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String FEED_CATEGORY = "feedCategory";
    public static final String FEED_TYPE = "feedType";
    public static final String DISPLAY_TYPE = "displayType";

    public void save(ContentFeedConfig transientInstance) {
        log.debug("saving ContentFeedConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ContentFeedConfig persistentInstance) {
        log.debug("deleting ContentFeedConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ContentFeedConfig findById(Long id) {
        log.debug("getting ContentFeedConfig instance with id: " + id);
        try {
            ContentFeedConfig instance = (ContentFeedConfig) getSession().get("com.autosite.db.ContentFeedConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ContentFeedConfig instance) {
        log.debug("finding ContentFeedConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ContentFeedConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ContentFeedConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ContentFeedConfig as model where model." + propertyName + "= ?";
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

    public List findByFeedCategory(Object feedCategory) {
        return findByProperty(FEED_CATEGORY, feedCategory);
    }

    public List findByFeedType(Object feedType) {
        return findByProperty(FEED_TYPE, feedType);
    }

    public List findByDisplayType(Object displayType) {
        return findByProperty(DISPLAY_TYPE, displayType);
    }

    public List findAll() {
        log.debug("finding all ContentFeedConfig instances");
        try {
            String queryString = "from ContentFeedConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ContentFeedConfig merge(ContentFeedConfig detachedInstance) {
        log.debug("merging ContentFeedConfig instance");
        try {
            ContentFeedConfig result = (ContentFeedConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ContentFeedConfig instance) {
        log.debug("attaching dirty ContentFeedConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ContentFeedConfig instance) {
        log.debug("attaching clean ContentFeedConfig instance");
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
