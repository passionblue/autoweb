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
 * GenMainCompMap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.GenMainCompMap
 * @author MyEclipse Persistence Tools
 */

public class GenMainCompMapDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(GenMainCompMapDAO.class);
    // property constants
    public static final String MAIN_ID = "mainId";
    public static final String COMP_ID = "compId";

    public void save(GenMainCompMap transientInstance) {
        log.debug("saving GenMainCompMap instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(GenMainCompMap persistentInstance) {
        log.debug("deleting GenMainCompMap instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public GenMainCompMap findById(Long id) {
        log.debug("getting GenMainCompMap instance with id: " + id);
        try {
            GenMainCompMap instance = (GenMainCompMap) getSession().get("com.autosite.db.GenMainCompMap", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(GenMainCompMap instance) {
        log.debug("finding GenMainCompMap instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.GenMainCompMap").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding GenMainCompMap instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GenMainCompMap as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByMainId(Object mainId) {
        return findByProperty(MAIN_ID, mainId);
    }

    public List findByCompId(Object compId) {
        return findByProperty(COMP_ID, compId);
    }

    public List findAll() {
        log.debug("finding all GenMainCompMap instances");
        try {
            String queryString = "from GenMainCompMap";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public GenMainCompMap merge(GenMainCompMap detachedInstance) {
        log.debug("merging GenMainCompMap instance");
        try {
            GenMainCompMap result = (GenMainCompMap) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(GenMainCompMap instance) {
        log.debug("attaching dirty GenMainCompMap instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(GenMainCompMap instance) {
        log.debug("attaching clean GenMainCompMap instance");
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
