package com.surveygen.db;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserProps entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.surveygen.db.UserProps
 * @author MyEclipse Persistence Tools
 */

public class UserPropsDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(UserPropsDAO.class);
    // property constants
    public static final String USER_ID = "userId";
    public static final String PROP_KEY = "propKey";
    public static final String PROP_VALUE = "propValue";

    public void save(UserProps transientInstance) {
        log.debug("saving UserProps instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(UserProps persistentInstance) {
        log.debug("deleting UserProps instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public UserProps findById(Long id) {
        log.debug("getting UserProps instance with id: " + id);
        try {
            UserProps instance = (UserProps) getSession().get("com.surveygen.db.UserProps", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(UserProps instance) {
        log.debug("finding UserProps instance by example");
        try {
            List results = getSession().createCriteria("com.surveygen.db.UserProps").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding UserProps instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from UserProps as model where model." + propertyName + "= ?";
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

    public List findByPropKey(Object propKey) {
        return findByProperty(PROP_KEY, propKey);
    }

    public List findByPropValue(Object propValue) {
        return findByProperty(PROP_VALUE, propValue);
    }

    public List findAll() {
        log.debug("finding all UserProps instances");
        try {
            String queryString = "from UserProps";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public UserProps merge(UserProps detachedInstance) {
        log.debug("merging UserProps instance");
        try {
            UserProps result = (UserProps) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserProps instance) {
        log.debug("attaching dirty UserProps instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(UserProps instance) {
        log.debug("attaching clean UserProps instance");
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
