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
 * BlogConfig entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.BlogConfig
 * @author MyEclipse Persistence Tools
 */

public class BlogConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlogConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String BLOG_MAIN_ID = "blogMainId";

    public void save(BlogConfig transientInstance) {
        log.debug("saving BlogConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlogConfig persistentInstance) {
        log.debug("deleting BlogConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlogConfig findById(Long id) {
        log.debug("getting BlogConfig instance with id: " + id);
        try {
            BlogConfig instance = (BlogConfig) getSession().get("com.autosite.db.BlogConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlogConfig instance) {
        log.debug("finding BlogConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlogConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlogConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlogConfig as model where model." + propertyName + "= ?";
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

    public List findByBlogMainId(Object blogMainId) {
        return findByProperty(BLOG_MAIN_ID, blogMainId);
    }

    public List findAll() {
        log.debug("finding all BlogConfig instances");
        try {
            String queryString = "from BlogConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlogConfig merge(BlogConfig detachedInstance) {
        log.debug("merging BlogConfig instance");
        try {
            BlogConfig result = (BlogConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlogConfig instance) {
        log.debug("attaching dirty BlogConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlogConfig instance) {
        log.debug("attaching clean BlogConfig instance");
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
