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
 * Expense entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.Expense
 * @author MyEclipse Persistence Tools
 */
public class ExpenseDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(ExpenseDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String EXPENSE_ITEM_ID = "expenseItemId";
    public static final String AMOUNT = "amount";
    public static final String DESCRIPTION = "description";
    public static final String PAY_METHOD = "payMethod";
    public static final String NOT_EXPENSE = "notExpense";
    public static final String REFERENCE = "reference";
    public static final String DATE_EXPENSE = "dateExpense";

    public void save(Expense transientInstance) {
        log.debug("saving Expense instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Expense persistentInstance) {
        log.debug("deleting Expense instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Expense findById(Long id) {
        log.debug("getting Expense instance with id: " + id);
        try {
            Expense instance = (Expense) getSession().get("com.autosite.db.Expense", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Expense instance) {
        log.debug("finding Expense instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Expense").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Expense instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Expense as model where model." + propertyName + "= ?";
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

    public List findByExpenseItemId(Object expenseItemId) {
        return findByProperty(EXPENSE_ITEM_ID, expenseItemId);
    }

    public List findByAmount(Object amount) {
        return findByProperty(AMOUNT, amount);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByPayMethod(Object payMethod) {
        return findByProperty(PAY_METHOD, payMethod);
    }

    public List findByNotExpense(Object notExpense) {
        return findByProperty(NOT_EXPENSE, notExpense);
    }

    public List findByReference(Object reference) {
        return findByProperty(REFERENCE, reference);
    }

    public List findByDateExpense(Object dateExpense) {
        return findByProperty(DATE_EXPENSE, dateExpense);
    }

    public List findAll() {
        log.debug("finding all Expense instances");
        try {
            String queryString = "from Expense";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Expense merge(Expense detachedInstance) {
        log.debug("merging Expense instance");
        try {
            Expense result = (Expense) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Expense instance) {
        log.debug("attaching dirty Expense instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Expense instance) {
        log.debug("attaching clean Expense instance");
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
