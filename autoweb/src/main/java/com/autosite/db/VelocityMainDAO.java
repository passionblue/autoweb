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
 * VelocityMain entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.VelocityMain
 * @author MyEclipse Persistence Tools
 */

public class VelocityMainDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(VelocityMainDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ID = "pageId";
    public static final String DATA = "data";
    public static final String DATA2 = "data2";
    public static final String TITLE = "title";
    public static final String AGE = "age";

    public void save(VelocityMain transientInstance) {
        log.debug("saving VelocityMain instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(VelocityMain persistentInstance) {
        log.debug("deleting VelocityMain instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public VelocityMain findById(Long id) {
        log.debug("getting VelocityMain instance with id: " + id);
        try {
            VelocityMain instance = (VelocityMain) getSession().get("com.autosite.db.VelocityMain", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(VelocityMain instance) {
        log.debug("finding VelocityMain instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.VelocityMain").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding VelocityMain instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from VelocityMain as model where model." + propertyName + "= ?";
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

    public List findByPageId(Object pageId) {
        return findByProperty(PAGE_ID, pageId);
    }

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findByData2(Object data2) {
        return findByProperty(DATA2, data2);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByAge(Object age) {
        return findByProperty(AGE, age);
    }

    public List findAll() {
        log.debug("finding all VelocityMain instances");
        try {
            String queryString = "from VelocityMain";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public VelocityMain merge(VelocityMain detachedInstance) {
        log.debug("merging VelocityMain instance");
        try {
            VelocityMain result = (VelocityMain) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(VelocityMain instance) {
        log.debug("attaching dirty VelocityMain instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(VelocityMain instance) {
        log.debug("attaching clean VelocityMain instance");
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
