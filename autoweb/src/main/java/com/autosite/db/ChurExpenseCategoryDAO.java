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
 * ChurExpenseCategory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ChurExpenseCategory
 * @author MyEclipse Persistence Tools
 */

public class ChurExpenseCategoryDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurExpenseCategoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String EXPENSE_CATEGORY = "expenseCategory";
    public static final String DISPLAY = "display";

    public void save(ChurExpenseCategory transientInstance) {
        log.debug("saving ChurExpenseCategory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurExpenseCategory persistentInstance) {
        log.debug("deleting ChurExpenseCategory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurExpenseCategory findById(Long id) {
        log.debug("getting ChurExpenseCategory instance with id: " + id);
        try {
            ChurExpenseCategory instance = (ChurExpenseCategory) getSession().get("com.autosite.db.ChurExpenseCategory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurExpenseCategory instance) {
        log.debug("finding ChurExpenseCategory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurExpenseCategory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurExpenseCategory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurExpenseCategory as model where model." + propertyName + "= ?";
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

    public List findByExpenseCategory(Object expenseCategory) {
        return findByProperty(EXPENSE_CATEGORY, expenseCategory);
    }

    public List findByDisplay(Object display) {
        return findByProperty(DISPLAY, display);
    }

    public List findAll() {
        log.debug("finding all ChurExpenseCategory instances");
        try {
            String queryString = "from ChurExpenseCategory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurExpenseCategory merge(ChurExpenseCategory detachedInstance) {
        log.debug("merging ChurExpenseCategory instance");
        try {
            ChurExpenseCategory result = (ChurExpenseCategory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurExpenseCategory instance) {
        log.debug("attaching dirty ChurExpenseCategory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurExpenseCategory instance) {
        log.debug("attaching clean ChurExpenseCategory instance");
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
