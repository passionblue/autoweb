package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ForumCategory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ForumCategory
 * @author MyEclipse Persistence Tools
 */

public class ForumCategoryDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ForumCategoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CATEGORY = "category";

    public void save(ForumCategory transientInstance) {
        log.debug("saving ForumCategory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ForumCategory persistentInstance) {
        log.debug("deleting ForumCategory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ForumCategory findById(Long id) {
        log.debug("getting ForumCategory instance with id: " + id);
        try {
            ForumCategory instance = (ForumCategory) getSession().get("com.autosite.db.ForumCategory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ForumCategory instance) {
        log.debug("finding ForumCategory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ForumCategory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ForumCategory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ForumCategory as model where model." + propertyName + "= ?";
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

    public List findAll() {
        log.debug("finding all ForumCategory instances");
        try {
            String queryString = "from ForumCategory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ForumCategory merge(ForumCategory detachedInstance) {
        log.debug("merging ForumCategory instance");
        try {
            ForumCategory result = (ForumCategory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ForumCategory instance) {
        log.debug("attaching dirty ForumCategory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ForumCategory instance) {
        log.debug("attaching clean ForumCategory instance");
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
