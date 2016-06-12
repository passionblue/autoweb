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
 * GenExtra entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.GenExtra
 * @author MyEclipse Persistence Tools
 */

public class GenExtraDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(GenExtraDAO.class);
    // property constants
    public static final String MAIN_ID = "mainId";
    public static final String SUB_ID = "subId";
    public static final String EXTRA_VALUE = "extraValue";
    public static final String EXTRA_DATA = "extraData";

    public void save(GenExtra transientInstance) {
        log.debug("saving GenExtra instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(GenExtra persistentInstance) {
        log.debug("deleting GenExtra instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public GenExtra findById(Long id) {
        log.debug("getting GenExtra instance with id: " + id);
        try {
            GenExtra instance = (GenExtra) getSession().get("com.autosite.db.GenExtra", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(GenExtra instance) {
        log.debug("finding GenExtra instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.GenExtra").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding GenExtra instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GenExtra as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByMainId(Object mainId) {
        return findByProperty(MAIN_ID, mainId);
    }

    public List findBySubId(Object subId) {
        return findByProperty(SUB_ID, subId);
    }

    public List findByExtraValue(Object extraValue) {
        return findByProperty(EXTRA_VALUE, extraValue);
    }

    public List findByExtraData(Object extraData) {
        return findByProperty(EXTRA_DATA, extraData);
    }

    public List findAll() {
        log.debug("finding all GenExtra instances");
        try {
            String queryString = "from GenExtra";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public GenExtra merge(GenExtra detachedInstance) {
        log.debug("merging GenExtra instance");
        try {
            GenExtra result = (GenExtra) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(GenExtra instance) {
        log.debug("attaching dirty GenExtra instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(GenExtra instance) {
        log.debug("attaching clean GenExtra instance");
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
