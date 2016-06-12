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
 * ChurIncomeCategory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ChurIncomeCategory
 * @author MyEclipse Persistence Tools
 */

public class ChurIncomeCategoryDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurIncomeCategoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String INCOME_CATEGORY = "incomeCategory";
    public static final String DISPLAY = "display";

    public void save(ChurIncomeCategory transientInstance) {
        log.debug("saving ChurIncomeCategory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurIncomeCategory persistentInstance) {
        log.debug("deleting ChurIncomeCategory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurIncomeCategory findById(Long id) {
        log.debug("getting ChurIncomeCategory instance with id: " + id);
        try {
            ChurIncomeCategory instance = (ChurIncomeCategory) getSession().get("com.autosite.db.ChurIncomeCategory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurIncomeCategory instance) {
        log.debug("finding ChurIncomeCategory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurIncomeCategory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurIncomeCategory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurIncomeCategory as model where model." + propertyName + "= ?";
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

    public List findByIncomeCategory(Object incomeCategory) {
        return findByProperty(INCOME_CATEGORY, incomeCategory);
    }

    public List findByDisplay(Object display) {
        return findByProperty(DISPLAY, display);
    }

    public List findAll() {
        log.debug("finding all ChurIncomeCategory instances");
        try {
            String queryString = "from ChurIncomeCategory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurIncomeCategory merge(ChurIncomeCategory detachedInstance) {
        log.debug("merging ChurIncomeCategory instance");
        try {
            ChurIncomeCategory result = (ChurIncomeCategory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurIncomeCategory instance) {
        log.debug("attaching dirty ChurIncomeCategory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurIncomeCategory instance) {
        log.debug("attaching clean ChurIncomeCategory instance");
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
