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
 * ContentFeedRel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ContentFeedRel
 * @author MyEclipse Persistence Tools
 */

public class ContentFeedRelDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ContentFeedRelDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CONTENT_FEED_ID = "contentFeedId";
    public static final String CONTENT_ID = "contentId";

    public void save(ContentFeedRel transientInstance) {
        log.debug("saving ContentFeedRel instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ContentFeedRel persistentInstance) {
        log.debug("deleting ContentFeedRel instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ContentFeedRel findById(Long id) {
        log.debug("getting ContentFeedRel instance with id: " + id);
        try {
            ContentFeedRel instance = (ContentFeedRel) getSession().get("com.autosite.db.ContentFeedRel", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ContentFeedRel instance) {
        log.debug("finding ContentFeedRel instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ContentFeedRel").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ContentFeedRel instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ContentFeedRel as model where model." + propertyName + "= ?";
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

    public List findByContentId(Object contentId) {
        return findByProperty(CONTENT_ID, contentId);
    }

    public List findAll() {
        log.debug("finding all ContentFeedRel instances");
        try {
            String queryString = "from ContentFeedRel";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ContentFeedRel merge(ContentFeedRel detachedInstance) {
        log.debug("merging ContentFeedRel instance");
        try {
            ContentFeedRel result = (ContentFeedRel) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ContentFeedRel instance) {
        log.debug("attaching dirty ContentFeedRel instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ContentFeedRel instance) {
        log.debug("attaching clean ContentFeedRel instance");
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
