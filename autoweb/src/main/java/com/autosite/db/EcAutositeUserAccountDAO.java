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
 * EcAutositeUserAccount entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcAutositeUserAccount
 * @author MyEclipse Persistence Tools
 */

public class EcAutositeUserAccountDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcAutositeUserAccountDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String SUBSCRIBED = "subscribed";

    public void save(EcAutositeUserAccount transientInstance) {
        log.debug("saving EcAutositeUserAccount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcAutositeUserAccount persistentInstance) {
        log.debug("deleting EcAutositeUserAccount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcAutositeUserAccount findById(Long id) {
        log.debug("getting EcAutositeUserAccount instance with id: " + id);
        try {
            EcAutositeUserAccount instance = (EcAutositeUserAccount) getSession().get("com.autosite.db.EcAutositeUserAccount", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcAutositeUserAccount instance) {
        log.debug("finding EcAutositeUserAccount instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcAutositeUserAccount").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcAutositeUserAccount instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcAutositeUserAccount as model where model." + propertyName + "= ?";
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

    public List findBySubscribed(Object subscribed) {
        return findByProperty(SUBSCRIBED, subscribed);
    }

    public List findAll() {
        log.debug("finding all EcAutositeUserAccount instances");
        try {
            String queryString = "from EcAutositeUserAccount";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcAutositeUserAccount merge(EcAutositeUserAccount detachedInstance) {
        log.debug("merging EcAutositeUserAccount instance");
        try {
            EcAutositeUserAccount result = (EcAutositeUserAccount) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcAutositeUserAccount instance) {
        log.debug("attaching dirty EcAutositeUserAccount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcAutositeUserAccount instance) {
        log.debug("attaching clean EcAutositeUserAccount instance");
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
