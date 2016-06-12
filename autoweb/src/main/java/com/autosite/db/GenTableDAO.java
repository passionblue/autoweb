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
 * GenTable entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.GenTable
 * @author MyEclipse Persistence Tools
 */

public class GenTableDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(GenTableDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String COUNTRY = "country";
    public static final String AGE = "age";
    public static final String DISABLED = "disabled";
    public static final String COMMENTS = "comments";

    public void save(GenTable transientInstance) {
        log.debug("saving GenTable instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(GenTable persistentInstance) {
        log.debug("deleting GenTable instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public GenTable findById(Long id) {
        log.debug("getting GenTable instance with id: " + id);
        try {
            GenTable instance = (GenTable) getSession().get("com.autosite.db.GenTable", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(GenTable instance) {
        log.debug("finding GenTable instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.GenTable").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding GenTable instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GenTable as model where model." + propertyName + "= ?";
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

    public List findByCountry(Object country) {
        return findByProperty(COUNTRY, country);
    }

    public List findByAge(Object age) {
        return findByProperty(AGE, age);
    }

    public List findByDisabled(Object disabled) {
        return findByProperty(DISABLED, disabled);
    }

    public List findByComments(Object comments) {
        return findByProperty(COMMENTS, comments);
    }

    public List findAll() {
        log.debug("finding all GenTable instances");
        try {
            String queryString = "from GenTable";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public GenTable merge(GenTable detachedInstance) {
        log.debug("merging GenTable instance");
        try {
            GenTable result = (GenTable) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(GenTable instance) {
        log.debug("attaching dirty GenTable instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(GenTable instance) {
        log.debug("attaching clean GenTable instance");
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
