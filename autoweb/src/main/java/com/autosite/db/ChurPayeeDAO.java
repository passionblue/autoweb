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
 * ChurPayee entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.ChurPayee
 * @author MyEclipse Persistence Tools
 */

public class ChurPayeeDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurPayeeDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String TITLE = "title";
    public static final String REMARK = "remark";

    public void save(ChurPayee transientInstance) {
        log.debug("saving ChurPayee instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurPayee persistentInstance) {
        log.debug("deleting ChurPayee instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurPayee findById(Long id) {
        log.debug("getting ChurPayee instance with id: " + id);
        try {
            ChurPayee instance = (ChurPayee) getSession().get("com.autosite.db.ChurPayee", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurPayee instance) {
        log.debug("finding ChurPayee instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurPayee").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurPayee instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurPayee as model where model." + propertyName + "= ?";
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

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByRemark(Object remark) {
        return findByProperty(REMARK, remark);
    }

    public List findAll() {
        log.debug("finding all ChurPayee instances");
        try {
            String queryString = "from ChurPayee";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurPayee merge(ChurPayee detachedInstance) {
        log.debug("merging ChurPayee instance");
        try {
            ChurPayee result = (ChurPayee) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurPayee instance) {
        log.debug("attaching dirty ChurPayee instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurPayee instance) {
        log.debug("attaching clean ChurPayee instance");
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
