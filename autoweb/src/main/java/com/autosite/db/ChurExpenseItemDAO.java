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
 * ChurExpenseItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ChurExpenseItem
 * @author MyEclipse Persistence Tools
 */

public class ChurExpenseItemDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurExpenseItemDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String EXPENSE_ITEM = "expenseItem";
    public static final String DISPLAY = "display";

    public void save(ChurExpenseItem transientInstance) {
        log.debug("saving ChurExpenseItem instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurExpenseItem persistentInstance) {
        log.debug("deleting ChurExpenseItem instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurExpenseItem findById(Long id) {
        log.debug("getting ChurExpenseItem instance with id: " + id);
        try {
            ChurExpenseItem instance = (ChurExpenseItem) getSession().get("com.autosite.db.ChurExpenseItem", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurExpenseItem instance) {
        log.debug("finding ChurExpenseItem instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurExpenseItem").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurExpenseItem instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurExpenseItem as model where model." + propertyName + "= ?";
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

    public List findByCategoryId(Object categoryId) {
        return findByProperty(CATEGORY_ID, categoryId);
    }

    public List findByExpenseItem(Object expenseItem) {
        return findByProperty(EXPENSE_ITEM, expenseItem);
    }

    public List findByDisplay(Object display) {
        return findByProperty(DISPLAY, display);
    }

    public List findAll() {
        log.debug("finding all ChurExpenseItem instances");
        try {
            String queryString = "from ChurExpenseItem";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurExpenseItem merge(ChurExpenseItem detachedInstance) {
        log.debug("merging ChurExpenseItem instance");
        try {
            ChurExpenseItem result = (ChurExpenseItem) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurExpenseItem instance) {
        log.debug("attaching dirty ChurExpenseItem instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurExpenseItem instance) {
        log.debug("attaching clean ChurExpenseItem instance");
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
