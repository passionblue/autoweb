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
 * AccountSiteRel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.AccountSiteRel
 * @author MyEclipse Persistence Tools
 */

public class AccountSiteRelDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(AccountSiteRelDAO.class);
    // property constants
    public static final String ACCOUNT_ID = "accountId";
    public static final String SITE_ID = "siteId";

    public void save(AccountSiteRel transientInstance) {
        log.debug("saving AccountSiteRel instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AccountSiteRel persistentInstance) {
        log.debug("deleting AccountSiteRel instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AccountSiteRel findById(Long id) {
        log.debug("getting AccountSiteRel instance with id: " + id);
        try {
            AccountSiteRel instance = (AccountSiteRel) getSession().get("com.autosite.db.AccountSiteRel", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AccountSiteRel instance) {
        log.debug("finding AccountSiteRel instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.AccountSiteRel").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AccountSiteRel instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AccountSiteRel as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByAccountId(Object accountId) {
        return findByProperty(ACCOUNT_ID, accountId);
    }

    public List findBySiteId(Object siteId) {
        return findByProperty(SITE_ID, siteId);
    }

    public List findAll() {
        log.debug("finding all AccountSiteRel instances");
        try {
            String queryString = "from AccountSiteRel";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AccountSiteRel merge(AccountSiteRel detachedInstance) {
        log.debug("merging AccountSiteRel instance");
        try {
            AccountSiteRel result = (AccountSiteRel) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AccountSiteRel instance) {
        log.debug("attaching dirty AccountSiteRel instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AccountSiteRel instance) {
        log.debug("attaching clean AccountSiteRel instance");
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
