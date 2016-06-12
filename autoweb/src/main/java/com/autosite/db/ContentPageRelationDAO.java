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
 * ContentPageRelation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ContentPageRelation
 * @author MyEclipse Persistence Tools
 */

public class ContentPageRelationDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ContentPageRelationDAO.class);
    // property constants
    public static final String CONTENT_ID = "contentId";
    public static final String PAGE_ID = "pageId";

    public void save(ContentPageRelation transientInstance) {
        log.debug("saving ContentPageRelation instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ContentPageRelation persistentInstance) {
        log.debug("deleting ContentPageRelation instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ContentPageRelation findById(Long id) {
        log.debug("getting ContentPageRelation instance with id: " + id);
        try {
            ContentPageRelation instance = (ContentPageRelation) getSession().get("com.autosite.db.ContentPageRelation", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ContentPageRelation instance) {
        log.debug("finding ContentPageRelation instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ContentPageRelation").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ContentPageRelation instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ContentPageRelation as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByContentId(Object contentId) {
        return findByProperty(CONTENT_ID, contentId);
    }

    public List findByPageId(Object pageId) {
        return findByProperty(PAGE_ID, pageId);
    }

    public List findAll() {
        log.debug("finding all ContentPageRelation instances");
        try {
            String queryString = "from ContentPageRelation";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ContentPageRelation merge(ContentPageRelation detachedInstance) {
        log.debug("merging ContentPageRelation instance");
        try {
            ContentPageRelation result = (ContentPageRelation) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ContentPageRelation instance) {
        log.debug("attaching dirty ContentPageRelation instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ContentPageRelation instance) {
        log.debug("attaching clean ContentPageRelation instance");
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
