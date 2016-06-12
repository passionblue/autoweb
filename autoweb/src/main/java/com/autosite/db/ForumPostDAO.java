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
 * ForumPost entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.ForumPost
 * @author MyEclipse Persistence Tools
 */

public class ForumPostDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ForumPostDAO.class);
    // property constants
    public static final String USER_ID = "userId";
    public static final String SUBJECT_ID = "subjectId";
    public static final String POST_ID = "postId";
    public static final String DATA = "data";

    public void save(ForumPost transientInstance) {
        log.debug("saving ForumPost instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ForumPost persistentInstance) {
        log.debug("deleting ForumPost instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ForumPost findById(Long id) {
        log.debug("getting ForumPost instance with id: " + id);
        try {
            ForumPost instance = (ForumPost) getSession().get("com.autosite.db.ForumPost", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ForumPost instance) {
        log.debug("finding ForumPost instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ForumPost").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ForumPost instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ForumPost as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findBySubjectId(Object subjectId) {
        return findByProperty(SUBJECT_ID, subjectId);
    }

    public List findByPostId(Object postId) {
        return findByProperty(POST_ID, postId);
    }

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findAll() {
        log.debug("finding all ForumPost instances");
        try {
            String queryString = "from ForumPost";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ForumPost merge(ForumPost detachedInstance) {
        log.debug("merging ForumPost instance");
        try {
            ForumPost result = (ForumPost) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ForumPost instance) {
        log.debug("attaching dirty ForumPost instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ForumPost instance) {
        log.debug("attaching clean ForumPost instance");
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
