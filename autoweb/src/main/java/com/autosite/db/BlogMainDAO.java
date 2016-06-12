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
 * BlogMain entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.BlogMain
 * @author MyEclipse Persistence Tools
 */

public class BlogMainDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlogMainDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String BLOG_NAME = "blogName";
    public static final String BLOG_TITLE = "blogTitle";
    public static final String MAIN_DESIGN_SELECT = "mainDesignSelect";
    public static final String USE_CUSTOM_DESIGN = "useCustomDesign";
    public static final String CUSTOM_DESIGN_FILE = "customDesignFile";

    public void save(BlogMain transientInstance) {
        log.debug("saving BlogMain instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlogMain persistentInstance) {
        log.debug("deleting BlogMain instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlogMain findById(Long id) {
        log.debug("getting BlogMain instance with id: " + id);
        try {
            BlogMain instance = (BlogMain) getSession().get("com.autosite.db.BlogMain", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlogMain instance) {
        log.debug("finding BlogMain instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlogMain").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlogMain instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlogMain as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByBlogName(Object blogName) {
        return findByProperty(BLOG_NAME, blogName);
    }

    public List findByBlogTitle(Object blogTitle) {
        return findByProperty(BLOG_TITLE, blogTitle);
    }

    public List findByMainDesignSelect(Object mainDesignSelect) {
        return findByProperty(MAIN_DESIGN_SELECT, mainDesignSelect);
    }

    public List findByUseCustomDesign(Object useCustomDesign) {
        return findByProperty(USE_CUSTOM_DESIGN, useCustomDesign);
    }

    public List findByCustomDesignFile(Object customDesignFile) {
        return findByProperty(CUSTOM_DESIGN_FILE, customDesignFile);
    }

    public List findAll() {
        log.debug("finding all BlogMain instances");
        try {
            String queryString = "from BlogMain";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlogMain merge(BlogMain detachedInstance) {
        log.debug("merging BlogMain instance");
        try {
            BlogMain result = (BlogMain) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlogMain instance) {
        log.debug("attaching dirty BlogMain instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlogMain instance) {
        log.debug("attaching clean BlogMain instance");
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
