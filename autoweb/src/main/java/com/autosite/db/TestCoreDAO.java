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
 * TestCore entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.TestCore
 * @author MyEclipse Persistence Tools
 */

public class TestCoreDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(TestCoreDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String DATA = "data";

    public void save(TestCore transientInstance) {
        log.debug("saving TestCore instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TestCore persistentInstance) {
        log.debug("deleting TestCore instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public TestCore findById(Long id) {
        log.debug("getting TestCore instance with id: " + id);
        try {
            TestCore instance = (TestCore) getSession().get("com.autosite.db.TestCore", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(TestCore instance) {
        log.debug("finding TestCore instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.TestCore").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding TestCore instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from TestCore as model where model." + propertyName + "= ?";
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

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findAll() {
        log.debug("finding all TestCore instances");
        try {
            String queryString = "from TestCore";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public TestCore merge(TestCore detachedInstance) {
        log.debug("merging TestCore instance");
        try {
            TestCore result = (TestCore) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TestCore instance) {
        log.debug("attaching dirty TestCore instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(TestCore instance) {
        log.debug("attaching clean TestCore instance");
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
