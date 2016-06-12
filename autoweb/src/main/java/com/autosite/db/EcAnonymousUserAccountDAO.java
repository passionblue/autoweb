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
 * EcAnonymousUserAccount entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcAnonymousUserAccount
 * @author MyEclipse Persistence Tools
 */

public class EcAnonymousUserAccountDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcAnonymousUserAccountDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String EMAIL = "email";
    public static final String SUBSCRIBED = "subscribed";

    public void save(EcAnonymousUserAccount transientInstance) {
        log.debug("saving EcAnonymousUserAccount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcAnonymousUserAccount persistentInstance) {
        log.debug("deleting EcAnonymousUserAccount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcAnonymousUserAccount findById(Long id) {
        log.debug("getting EcAnonymousUserAccount instance with id: " + id);
        try {
            EcAnonymousUserAccount instance = (EcAnonymousUserAccount) getSession().get("com.autosite.db.EcAnonymousUserAccount", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcAnonymousUserAccount instance) {
        log.debug("finding EcAnonymousUserAccount instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcAnonymousUserAccount").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcAnonymousUserAccount instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcAnonymousUserAccount as model where model." + propertyName + "= ?";
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

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findBySubscribed(Object subscribed) {
        return findByProperty(SUBSCRIBED, subscribed);
    }

    public List findAll() {
        log.debug("finding all EcAnonymousUserAccount instances");
        try {
            String queryString = "from EcAnonymousUserAccount";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcAnonymousUserAccount merge(EcAnonymousUserAccount detachedInstance) {
        log.debug("merging EcAnonymousUserAccount instance");
        try {
            EcAnonymousUserAccount result = (EcAnonymousUserAccount) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcAnonymousUserAccount instance) {
        log.debug("attaching dirty EcAnonymousUserAccount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcAnonymousUserAccount instance) {
        log.debug("attaching clean EcAnonymousUserAccount instance");
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
