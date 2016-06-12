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
 * BlogComment entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.BlogComment
 * @author MyEclipse Persistence Tools
 */

public class BlogCommentDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlogCommentDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String BLOG_MAIN_ID = "blogMainId";
    public static final String BLOG_POST_ID = "blogPostId";
    public static final String COMMENT = "comment";
    public static final String RATING = "rating";
    public static final String IPADDRESS = "ipaddress";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String WEBSITE = "website";
    public static final String EMAIL = "email";

    public void save(BlogComment transientInstance) {
        log.debug("saving BlogComment instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlogComment persistentInstance) {
        log.debug("deleting BlogComment instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlogComment findById(Long id) {
        log.debug("getting BlogComment instance with id: " + id);
        try {
            BlogComment instance = (BlogComment) getSession().get("com.autosite.db.BlogComment", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlogComment instance) {
        log.debug("finding BlogComment instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlogComment").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlogComment instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlogComment as model where model." + propertyName + "= ?";
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

    public List findByBlogPostId(Object blogPostId) {
        return findByProperty(BLOG_POST_ID, blogPostId);
    }

    public List findByComment(Object comment) {
        return findByProperty(COMMENT, comment);
    }

    public List findByRating(Object rating) {
        return findByProperty(RATING, rating);
    }

    public List findByIpaddress(Object ipaddress) {
        return findByProperty(IPADDRESS, ipaddress);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByPassword(Object password) {
        return findByProperty(PASSWORD, password);
    }

    public List findByWebsite(Object website) {
        return findByProperty(WEBSITE, website);
    }

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findAll() {
        log.debug("finding all BlogComment instances");
        try {
            String queryString = "from BlogComment";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlogComment merge(BlogComment detachedInstance) {
        log.debug("merging BlogComment instance");
        try {
            BlogComment result = (BlogComment) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlogComment instance) {
        log.debug("attaching dirty BlogComment instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlogComment instance) {
        log.debug("attaching clean BlogComment instance");
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
