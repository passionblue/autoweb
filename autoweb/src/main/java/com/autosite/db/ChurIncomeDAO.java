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
 * ChurIncome entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.ChurIncome
 * @author MyEclipse Persistence Tools
 */

public class ChurIncomeDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurIncomeDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String YEAR = "year";
    public static final String WEEK = "week";
    public static final String CHUR_MEMBER_ID = "churMemberId";
    public static final String INCOME_ITEM_ID = "incomeItemId";
    public static final String AMMOUNT = "ammount";

    public void save(ChurIncome transientInstance) {
        log.debug("saving ChurIncome instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurIncome persistentInstance) {
        log.debug("deleting ChurIncome instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurIncome findById(Long id) {
        log.debug("getting ChurIncome instance with id: " + id);
        try {
            ChurIncome instance = (ChurIncome) getSession().get("com.autosite.db.ChurIncome", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurIncome instance) {
        log.debug("finding ChurIncome instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurIncome").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurIncome instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurIncome as model where model." + propertyName + "= ?";
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

    public List findByChurMemberId(Object churMemberId) {
        return findByProperty(CHUR_MEMBER_ID, churMemberId);
    }

    public List findByIncomeItemId(Object incomeItemId) {
        return findByProperty(INCOME_ITEM_ID, incomeItemId);
    }

    public List findByAmmount(Object ammount) {
        return findByProperty(AMMOUNT, ammount);
    }

    public List findAll() {
        log.debug("finding all ChurIncome instances");
        try {
            String queryString = "from ChurIncome";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurIncome merge(ChurIncome detachedInstance) {
        log.debug("merging ChurIncome instance");
        try {
            ChurIncome result = (ChurIncome) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurIncome instance) {
        log.debug("attaching dirty ChurIncome instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurIncome instance) {
        log.debug("attaching clean ChurIncome instance");
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
