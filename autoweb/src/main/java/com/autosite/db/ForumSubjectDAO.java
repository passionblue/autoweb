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
 * ForumSubject entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ForumSubject
 * @author MyEclipse Persistence Tools
 */

public class ForumSubjectDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ForumSubjectDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String SUBJECT = "subject";

    public void save(ForumSubject transientInstance) {
        log.debug("saving ForumSubject instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ForumSubject persistentInstance) {
        log.debug("deleting ForumSubject instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ForumSubject findById(Long id) {
        log.debug("getting ForumSubject instance with id: " + id);
        try {
            ForumSubject instance = (ForumSubject) getSession().get("com.autosite.db.ForumSubject", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ForumSubject instance) {
        log.debug("finding ForumSubject instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ForumSubject").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ForumSubject instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ForumSubject as model where model." + propertyName + "= ?";
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

    public List findByCategoryId(Object categoryId) {
        return findByProperty(CATEGORY_ID, categoryId);
    }

    public List findBySubject(Object subject) {
        return findByProperty(SUBJECT, subject);
    }

    public List findAll() {
        log.debug("finding all ForumSubject instances");
        try {
            String queryString = "from ForumSubject";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ForumSubject merge(ForumSubject detachedInstance) {
        log.debug("merging ForumSubject instance");
        try {
            ForumSubject result = (ForumSubject) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ForumSubject instance) {
        log.debug("attaching dirty ForumSubject instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ForumSubject instance) {
        log.debug("attaching clean ForumSubject instance");
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
