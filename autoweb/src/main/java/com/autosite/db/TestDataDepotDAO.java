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
 * TestDataDepot entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.TestDataDepot
 * @author MyEclipse Persistence Tools
 */

public class TestDataDepotDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(TestDataDepotDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String TITLE = "title";
    public static final String DATA = "data";
    public static final String TYPE = "type";

    public void save(TestDataDepot transientInstance) {
        log.debug("saving TestDataDepot instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TestDataDepot persistentInstance) {
        log.debug("deleting TestDataDepot instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public TestDataDepot findById(Long id) {
        log.debug("getting TestDataDepot instance with id: " + id);
        try {
            TestDataDepot instance = (TestDataDepot) getSession().get("com.autosite.db.TestDataDepot", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(TestDataDepot instance) {
        log.debug("finding TestDataDepot instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.TestDataDepot").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding TestDataDepot instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from TestDataDepot as model where model." + propertyName + "= ?";
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

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findAll() {
        log.debug("finding all TestDataDepot instances");
        try {
            String queryString = "from TestDataDepot";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public TestDataDepot merge(TestDataDepot detachedInstance) {
        log.debug("merging TestDataDepot instance");
        try {
            TestDataDepot result = (TestDataDepot) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TestDataDepot instance) {
        log.debug("attaching dirty TestDataDepot instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(TestDataDepot instance) {
        log.debug("attaching clean TestDataDepot instance");
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
