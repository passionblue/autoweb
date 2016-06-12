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
 * ChurExpense entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ChurExpense
 * @author MyEclipse Persistence Tools
 */

public class ChurExpenseDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurExpenseDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String YEAR = "year";
    public static final String WEEK = "week";
    public static final String EXPENSE_ITEM_ID = "expenseItemId";
    public static final String PAYEE_ID = "payeeId";
    public static final String AMOUNT = "amount";
    public static final String IS_CASH = "isCash";
    public static final String CHECK_NUMBER = "checkNumber";
    public static final String CHECK_CLEARED = "checkCleared";
    public static final String COMMENT = "comment";
    public static final String CANCELLED = "cancelled";

    public void save(ChurExpense transientInstance) {
        log.debug("saving ChurExpense instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurExpense persistentInstance) {
        log.debug("deleting ChurExpense instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurExpense findById(Long id) {
        log.debug("getting ChurExpense instance with id: " + id);
        try {
            ChurExpense instance = (ChurExpense) getSession().get("com.autosite.db.ChurExpense", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurExpense instance) {
        log.debug("finding ChurExpense instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurExpense").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurExpense instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurExpense as model where model." + propertyName + "= ?";
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

    public List findByYear(Object year) {
        return findByProperty(YEAR, year);
    }

    public List findByWeek(Object week) {
        return findByProperty(WEEK, week);
    }

    public List findByExpenseItemId(Object expenseItemId) {
        return findByProperty(EXPENSE_ITEM_ID, expenseItemId);
    }

    public List findByPayeeId(Object payeeId) {
        return findByProperty(PAYEE_ID, payeeId);
    }

    public List findByAmount(Object amount) {
        return findByProperty(AMOUNT, amount);
    }

    public List findByIsCash(Object isCash) {
        return findByProperty(IS_CASH, isCash);
    }

    public List findByCheckNumber(Object checkNumber) {
        return findByProperty(CHECK_NUMBER, checkNumber);
    }

    public List findByCheckCleared(Object checkCleared) {
        return findByProperty(CHECK_CLEARED, checkCleared);
    }

    public List findByComment(Object comment) {
        return findByProperty(COMMENT, comment);
    }

    public List findByCancelled(Object cancelled) {
        return findByProperty(CANCELLED, cancelled);
    }

    public List findAll() {
        log.debug("finding all ChurExpense instances");
        try {
            String queryString = "from ChurExpense";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurExpense merge(ChurExpense detachedInstance) {
        log.debug("merging ChurExpense instance");
        try {
            ChurExpense result = (ChurExpense) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurExpense instance) {
        log.debug("attaching dirty ChurExpense instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurExpense instance) {
        log.debug("attaching clean ChurExpense instance");
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
