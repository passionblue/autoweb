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
 * BlockList entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.BlockList
 * @author MyEclipse Persistence Tools
 */

public class BlockListDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlockListDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String IP_DATA = "ipData";
    public static final String RANGE_CHECK = "rangeCheck";
    public static final String REASON_CODE = "reasonCode";

    public void save(BlockList transientInstance) {
        log.debug("saving BlockList instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlockList persistentInstance) {
        log.debug("deleting BlockList instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlockList findById(Long id) {
        log.debug("getting BlockList instance with id: " + id);
        try {
            BlockList instance = (BlockList) getSession().get("com.autosite.db.BlockList", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlockList instance) {
        log.debug("finding BlockList instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlockList").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlockList instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlockList as model where model." + propertyName + "= ?";
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

    public List findByIpData(Object ipData) {
        return findByProperty(IP_DATA, ipData);
    }

    public List findByRangeCheck(Object rangeCheck) {
        return findByProperty(RANGE_CHECK, rangeCheck);
    }

    public List findByReasonCode(Object reasonCode) {
        return findByProperty(REASON_CODE, reasonCode);
    }

    public List findAll() {
        log.debug("finding all BlockList instances");
        try {
            String queryString = "from BlockList";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlockList merge(BlockList detachedInstance) {
        log.debug("merging BlockList instance");
        try {
            BlockList result = (BlockList) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlockList instance) {
        log.debug("attaching dirty BlockList instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlockList instance) {
        log.debug("attaching clean BlockList instance");
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
