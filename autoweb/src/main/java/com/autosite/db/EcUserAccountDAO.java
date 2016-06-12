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
 * EcUserAccount entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcUserAccount
 * @author MyEclipse Persistence Tools
 */

public class EcUserAccountDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcUserAccountDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    public void save(EcUserAccount transientInstance) {
        log.debug("saving EcUserAccount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcUserAccount persistentInstance) {
        log.debug("deleting EcUserAccount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcUserAccount findById(Long id) {
        log.debug("getting EcUserAccount instance with id: " + id);
        try {
            EcUserAccount instance = (EcUserAccount) getSession().get("com.autosite.db.EcUserAccount", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcUserAccount instance) {
        log.debug("finding EcUserAccount instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcUserAccount").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcUserAccount instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcUserAccount as model where model." + propertyName + "= ?";
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

    public List findByFirstName(Object firstName) {
        return findByProperty(FIRST_NAME, firstName);
    }

    public List findByLastName(Object lastName) {
        return findByProperty(LAST_NAME, lastName);
    }

    public List findAll() {
        log.debug("finding all EcUserAccount instances");
        try {
            String queryString = "from EcUserAccount";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcUserAccount merge(EcUserAccount detachedInstance) {
        log.debug("merging EcUserAccount instance");
        try {
            EcUserAccount result = (EcUserAccount) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcUserAccount instance) {
        log.debug("attaching dirty EcUserAccount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcUserAccount instance) {
        log.debug("attaching clean EcUserAccount instance");
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
