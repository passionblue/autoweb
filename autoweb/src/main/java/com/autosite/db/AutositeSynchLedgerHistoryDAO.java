package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * AutositeSynchLedgerHistory entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.autosite.db.AutositeSynchLedgerHistory
 * @author MyEclipse Persistence Tools
 */
public class AutositeSynchLedgerHistoryDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(AutositeSynchLedgerHistoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String DEVICE_ID = "deviceId";
    public static final String ORIGINAL_LEDGER_ID = "originalLedgerId";
    public static final String SCOPE = "scope";
    public static final String TARGET = "target";
    public static final String REMOTE_TOKEN = "remoteToken";
    public static final String OBJECT_ID = "objectId";
    public static final String SYNCH_ID = "synchId";
    public static final String SYNCH_NUM = "synchNum";

    public void save(AutositeSynchLedgerHistory transientInstance) {
        log.debug("saving AutositeSynchLedgerHistory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AutositeSynchLedgerHistory persistentInstance) {
        log.debug("deleting AutositeSynchLedgerHistory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AutositeSynchLedgerHistory findById(Long id) {
        log.debug("getting AutositeSynchLedgerHistory instance with id: " + id);
        try {
            AutositeSynchLedgerHistory instance = (AutositeSynchLedgerHistory) getSession().get("com.autosite.db.AutositeSynchLedgerHistory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AutositeSynchLedgerHistory instance) {
        log.debug("finding AutositeSynchLedgerHistory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.AutositeSynchLedgerHistory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AutositeSynchLedgerHistory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AutositeSynchLedgerHistory as model where model." + propertyName + "= ?";
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

    public List findByDeviceId(Object deviceId) {
        return findByProperty(DEVICE_ID, deviceId);
    }

    public List findByOriginalLedgerId(Object originalLedgerId) {
        return findByProperty(ORIGINAL_LEDGER_ID, originalLedgerId);
    }

    public List findByScope(Object scope) {
        return findByProperty(SCOPE, scope);
    }

    public List findByTarget(Object target) {
        return findByProperty(TARGET, target);
    }

    public List findByRemoteToken(Object remoteToken) {
        return findByProperty(REMOTE_TOKEN, remoteToken);
    }

    public List findByObjectId(Object objectId) {
        return findByProperty(OBJECT_ID, objectId);
    }

    public List findBySynchId(Object synchId) {
        return findByProperty(SYNCH_ID, synchId);
    }

    public List findBySynchNum(Object synchNum) {
        return findByProperty(SYNCH_NUM, synchNum);
    }

    public List findAll() {
        log.debug("finding all AutositeSynchLedgerHistory instances");
        try {
            String queryString = "from AutositeSynchLedgerHistory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AutositeSynchLedgerHistory merge(AutositeSynchLedgerHistory detachedInstance) {
        log.debug("merging AutositeSynchLedgerHistory instance");
        try {
            AutositeSynchLedgerHistory result = (AutositeSynchLedgerHistory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AutositeSynchLedgerHistory instance) {
        log.debug("attaching dirty AutositeSynchLedgerHistory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AutositeSynchLedgerHistory instance) {
        log.debug("attaching clean AutositeSynchLedgerHistory instance");
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
