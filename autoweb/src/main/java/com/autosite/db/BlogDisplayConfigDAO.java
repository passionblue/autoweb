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
 * BlogDisplayConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.BlogDisplayConfig
 * @author MyEclipse Persistence Tools
 */

public class BlogDisplayConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlogDisplayConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String BLOG_MAIN_ID = "blogMainId";

    public void save(BlogDisplayConfig transientInstance) {
        log.debug("saving BlogDisplayConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlogDisplayConfig persistentInstance) {
        log.debug("deleting BlogDisplayConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlogDisplayConfig findById(Long id) {
        log.debug("getting BlogDisplayConfig instance with id: " + id);
        try {
            BlogDisplayConfig instance = (BlogDisplayConfig) getSession().get("com.autosite.db.BlogDisplayConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlogDisplayConfig instance) {
        log.debug("finding BlogDisplayConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlogDisplayConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlogDisplayConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlogDisplayConfig as model where model." + propertyName + "= ?";
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
        log.debug("finding all BlogDisplayConfig instances");
        try {
            String queryString = "from BlogDisplayConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlogDisplayConfig merge(BlogDisplayConfig detachedInstance) {
        log.debug("merging BlogDisplayConfig instance");
        try {
            BlogDisplayConfig result = (BlogDisplayConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlogDisplayConfig instance) {
        log.debug("attaching dirty BlogDisplayConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlogDisplayConfig instance) {
        log.debug("attaching clean BlogDisplayConfig instance");
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
