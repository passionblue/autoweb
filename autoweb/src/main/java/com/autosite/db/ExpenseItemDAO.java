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
 * ExpenseItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ExpenseItem
 * @author MyEclipse Persistence Tools
 */
public class ExpenseItemDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(ExpenseItemDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String EXPENSE_CATEGORY_ID = "expenseCategoryId";
    public static final String EXPENSE_ITEM = "expenseItem";

    public void save(ExpenseItem transientInstance) {
        log.debug("saving ExpenseItem instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ExpenseItem persistentInstance) {
        log.debug("deleting ExpenseItem instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ExpenseItem findById(Long id) {
        log.debug("getting ExpenseItem instance with id: " + id);
        try {
            ExpenseItem instance = (ExpenseItem) getSession().get("com.autosite.db.ExpenseItem", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ExpenseItem instance) {
        log.debug("finding ExpenseItem instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ExpenseItem").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ExpenseItem instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ExpenseItem as model where model." + propertyName + "= ?";
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

    public List findByExpenseCategoryId(Object expenseCategoryId) {
        return findByProperty(EXPENSE_CATEGORY_ID, expenseCategoryId);
    }

    public List findByExpenseItem(Object expenseItem) {
        return findByProperty(EXPENSE_ITEM, expenseItem);
    }

    public List findAll() {
        log.debug("finding all ExpenseItem instances");
        try {
            String queryString = "from ExpenseItem";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ExpenseItem merge(ExpenseItem detachedInstance) {
        log.debug("merging ExpenseItem instance");
        try {
            ExpenseItem result = (ExpenseItem) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ExpenseItem instance) {
        log.debug("attaching dirty ExpenseItem instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ExpenseItem instance) {
        log.debug("attaching clean ExpenseItem instance");
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
