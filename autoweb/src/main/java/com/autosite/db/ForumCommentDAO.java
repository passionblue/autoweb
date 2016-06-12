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
 * ForumComment entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ForumComment
 * @author MyEclipse Persistence Tools
 */

public class ForumCommentDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ForumCommentDAO.class);
    // property constants
    public static final String POST_ID = "postId";
    public static final String USER_ID = "userId";
    public static final String COMMENT = "comment";

    public void save(ForumComment transientInstance) {
        log.debug("saving ForumComment instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ForumComment persistentInstance) {
        log.debug("deleting ForumComment instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ForumComment findById(Long id) {
        log.debug("getting ForumComment instance with id: " + id);
        try {
            ForumComment instance = (ForumComment) getSession().get("com.autosite.db.ForumComment", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ForumComment instance) {
        log.debug("finding ForumComment instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ForumComment").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ForumComment instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ForumComment as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPostId(Object postId) {
        return findByProperty(POST_ID, postId);
    }

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByComment(Object comment) {
        return findByProperty(COMMENT, comment);
    }

    public List findAll() {
        log.debug("finding all ForumComment instances");
        try {
            String queryString = "from ForumComment";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ForumComment merge(ForumComment detachedInstance) {
        log.debug("merging ForumComment instance");
        try {
            ForumComment result = (ForumComment) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ForumComment instance) {
        log.debug("attaching dirty ForumComment instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ForumComment instance) {
        log.debug("attaching clean ForumComment instance");
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
