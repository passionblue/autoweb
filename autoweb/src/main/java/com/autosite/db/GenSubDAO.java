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
 * GenSub entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.GenSub
 * @author MyEclipse Persistence Tools
 */

public class GenSubDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(GenSubDAO.class);
    // property constants
    public static final String MAIN_ID = "mainId";
    public static final String STR_KEY = "strKey";
    public static final String SUB_DATA = "subData";

    public void save(GenSub transientInstance) {
        log.debug("saving GenSub instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(GenSub persistentInstance) {
        log.debug("deleting GenSub instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public GenSub findById(Long id) {
        log.debug("getting GenSub instance with id: " + id);
        try {
            GenSub instance = (GenSub) getSession().get("com.autosite.db.GenSub", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(GenSub instance) {
        log.debug("finding GenSub instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.GenSub").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding GenSub instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GenSub as model where model." + propertyName + "= ?";
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

    public List findByStrKey(Object strKey) {
        return findByProperty(STR_KEY, strKey);
    }

    public List findBySubData(Object subData) {
        return findByProperty(SUB_DATA, subData);
    }

    public List findAll() {
        log.debug("finding all GenSub instances");
        try {
            String queryString = "from GenSub";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public GenSub merge(GenSub detachedInstance) {
        log.debug("merging GenSub instance");
        try {
            GenSub result = (GenSub) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(GenSub instance) {
        log.debug("attaching dirty GenSub instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(GenSub instance) {
        log.debug("attaching clean GenSub instance");
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
