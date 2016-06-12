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
 * BlogCategory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.BlogCategory
 * @author MyEclipse Persistence Tools
 */

public class BlogCategoryDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlogCategoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String BLOG_MAIN_ID = "blogMainId";
    public static final String PARENT_CATEGORY_ID = "parentCategoryId";
    public static final String ROOT_CATEGORY_ID = "rootCategoryId";
    public static final String TITLE = "title";
    public static final String HIDE = "hide";
    public static final String IMAGE_URL1 = "imageUrl1";
    public static final String IMAGE_URL2 = "imageUrl2";

    public void save(BlogCategory transientInstance) {
        log.debug("saving BlogCategory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlogCategory persistentInstance) {
        log.debug("deleting BlogCategory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlogCategory findById(Long id) {
        log.debug("getting BlogCategory instance with id: " + id);
        try {
            BlogCategory instance = (BlogCategory) getSession().get("com.autosite.db.BlogCategory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlogCategory instance) {
        log.debug("finding BlogCategory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlogCategory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlogCategory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlogCategory as model where model." + propertyName + "= ?";
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

    public List findByParentCategoryId(Object parentCategoryId) {
        return findByProperty(PARENT_CATEGORY_ID, parentCategoryId);
    }

    public List findByRootCategoryId(Object rootCategoryId) {
        return findByProperty(ROOT_CATEGORY_ID, rootCategoryId);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findByImageUrl1(Object imageUrl1) {
        return findByProperty(IMAGE_URL1, imageUrl1);
    }

    public List findByImageUrl2(Object imageUrl2) {
        return findByProperty(IMAGE_URL2, imageUrl2);
    }

    public List findAll() {
        log.debug("finding all BlogCategory instances");
        try {
            String queryString = "from BlogCategory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlogCategory merge(BlogCategory detachedInstance) {
        log.debug("merging BlogCategory instance");
        try {
            BlogCategory result = (BlogCategory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlogCategory instance) {
        log.debug("attaching dirty BlogCategory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlogCategory instance) {
        log.debug("attaching clean BlogCategory instance");
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
