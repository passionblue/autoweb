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
 * ContentFeedSite entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ContentFeedSite
 * @author MyEclipse Persistence Tools
 */

public class ContentFeedSiteDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ContentFeedSiteDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CONTENT_FEED_ID = "contentFeedId";
    public static final String DISPLAY_TYPE = "displayType";

    public void save(ContentFeedSite transientInstance) {
        log.debug("saving ContentFeedSite instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ContentFeedSite persistentInstance) {
        log.debug("deleting ContentFeedSite instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ContentFeedSite findById(Long id) {
        log.debug("getting ContentFeedSite instance with id: " + id);
        try {
            ContentFeedSite instance = (ContentFeedSite) getSession().get("com.autosite.db.ContentFeedSite", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ContentFeedSite instance) {
        log.debug("finding ContentFeedSite instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ContentFeedSite").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ContentFeedSite instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ContentFeedSite as model where model." + propertyName + "= ?";
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

    public List findByContentFeedId(Object contentFeedId) {
        return findByProperty(CONTENT_FEED_ID, contentFeedId);
    }

    public List findByDisplayType(Object displayType) {
        return findByProperty(DISPLAY_TYPE, displayType);
    }

    public List findAll() {
        log.debug("finding all ContentFeedSite instances");
        try {
            String queryString = "from ContentFeedSite";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ContentFeedSite merge(ContentFeedSite detachedInstance) {
        log.debug("merging ContentFeedSite instance");
        try {
            ContentFeedSite result = (ContentFeedSite) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ContentFeedSite instance) {
        log.debug("attaching dirty ContentFeedSite instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ContentFeedSite instance) {
        log.debug("attaching clean ContentFeedSite instance");
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
