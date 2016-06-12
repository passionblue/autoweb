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
 * BlogPostCategoryRel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.BlogPostCategoryRel
 * @author MyEclipse Persistence Tools
 */

public class BlogPostCategoryRelDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlogPostCategoryRelDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String BLOG_POST_ID = "blogPostId";
    public static final String BLOG_CATEGORY_ID = "blogCategoryId";

    public void save(BlogPostCategoryRel transientInstance) {
        log.debug("saving BlogPostCategoryRel instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlogPostCategoryRel persistentInstance) {
        log.debug("deleting BlogPostCategoryRel instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlogPostCategoryRel findById(Long id) {
        log.debug("getting BlogPostCategoryRel instance with id: " + id);
        try {
            BlogPostCategoryRel instance = (BlogPostCategoryRel) getSession().get("com.autosite.db.BlogPostCategoryRel", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlogPostCategoryRel instance) {
        log.debug("finding BlogPostCategoryRel instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlogPostCategoryRel").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlogPostCategoryRel instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlogPostCategoryRel as model where model." + propertyName + "= ?";
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

    public List findByBlogPostId(Object blogPostId) {
        return findByProperty(BLOG_POST_ID, blogPostId);
    }

    public List findByBlogCategoryId(Object blogCategoryId) {
        return findByProperty(BLOG_CATEGORY_ID, blogCategoryId);
    }

    public List findAll() {
        log.debug("finding all BlogPostCategoryRel instances");
        try {
            String queryString = "from BlogPostCategoryRel";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlogPostCategoryRel merge(BlogPostCategoryRel detachedInstance) {
        log.debug("merging BlogPostCategoryRel instance");
        try {
            BlogPostCategoryRel result = (BlogPostCategoryRel) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlogPostCategoryRel instance) {
        log.debug("attaching dirty BlogPostCategoryRel instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlogPostCategoryRel instance) {
        log.debug("attaching clean BlogPostCategoryRel instance");
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
