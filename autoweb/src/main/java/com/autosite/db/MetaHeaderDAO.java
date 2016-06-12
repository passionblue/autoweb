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
 * MetaHeader entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.MetaHeader
 * @author MyEclipse Persistence Tools
 */

public class MetaHeaderDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(MetaHeaderDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String SOURCE = "source";
    public static final String DETAIL_ID = "detailId";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String HTTP_EQUIV = "httpEquiv";

    public void save(MetaHeader transientInstance) {
        log.debug("saving MetaHeader instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(MetaHeader persistentInstance) {
        log.debug("deleting MetaHeader instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public MetaHeader findById(Long id) {
        log.debug("getting MetaHeader instance with id: " + id);
        try {
            MetaHeader instance = (MetaHeader) getSession().get("com.autosite.db.MetaHeader", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(MetaHeader instance) {
        log.debug("finding MetaHeader instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.MetaHeader").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding MetaHeader instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from MetaHeader as model where model." + propertyName + "= ?";
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

    public List findBySource(Object source) {
        return findByProperty(SOURCE, source);
    }

    public List findByDetailId(Object detailId) {
        return findByProperty(DETAIL_ID, detailId);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByValue(Object value) {
        return findByProperty(VALUE, value);
    }

    public List findByHttpEquiv(Object httpEquiv) {
        return findByProperty(HTTP_EQUIV, httpEquiv);
    }

    public List findAll() {
        log.debug("finding all MetaHeader instances");
        try {
            String queryString = "from MetaHeader";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public MetaHeader merge(MetaHeader detachedInstance) {
        log.debug("merging MetaHeader instance");
        try {
            MetaHeader result = (MetaHeader) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MetaHeader instance) {
        log.debug("attaching dirty MetaHeader instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(MetaHeader instance) {
        log.debug("attaching clean MetaHeader instance");
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
