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
 * ChurIncomeType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ChurIncomeType
 * @author MyEclipse Persistence Tools
 */

public class ChurIncomeTypeDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurIncomeTypeDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String INCOME_TYPE = "incomeType";

    public void save(ChurIncomeType transientInstance) {
        log.debug("saving ChurIncomeType instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurIncomeType persistentInstance) {
        log.debug("deleting ChurIncomeType instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurIncomeType findById(Long id) {
        log.debug("getting ChurIncomeType instance with id: " + id);
        try {
            ChurIncomeType instance = (ChurIncomeType) getSession().get("com.autosite.db.ChurIncomeType", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurIncomeType instance) {
        log.debug("finding ChurIncomeType instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurIncomeType").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurIncomeType instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurIncomeType as model where model." + propertyName + "= ?";
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

    public List findByIncomeType(Object incomeType) {
        return findByProperty(INCOME_TYPE, incomeType);
    }

    public List findAll() {
        log.debug("finding all ChurIncomeType instances");
        try {
            String queryString = "from ChurIncomeType";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurIncomeType merge(ChurIncomeType detachedInstance) {
        log.debug("merging ChurIncomeType instance");
        try {
            ChurIncomeType result = (ChurIncomeType) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurIncomeType instance) {
        log.debug("attaching dirty ChurIncomeType instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurIncomeType instance) {
        log.debug("attaching clean ChurIncomeType instance");
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
