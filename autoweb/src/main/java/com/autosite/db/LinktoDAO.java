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
 * Linkto entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.Linkto
 * @author MyEclipse Persistence Tools
 */

public class LinktoDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(LinktoDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String LINK_KEY = "linkKey";
    public static final String LINK_TARGET = "linkTarget";
    public static final String DISABLE = "disable";

    public void save(Linkto transientInstance) {
        log.debug("saving Linkto instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Linkto persistentInstance) {
        log.debug("deleting Linkto instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Linkto findById(Long id) {
        log.debug("getting Linkto instance with id: " + id);
        try {
            Linkto instance = (Linkto) getSession().get("com.autosite.db.Linkto", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Linkto instance) {
        log.debug("finding Linkto instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Linkto").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Linkto instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Linkto as model where model." + propertyName + "= ?";
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

    public List findByLinkKey(Object linkKey) {
        return findByProperty(LINK_KEY, linkKey);
    }

    public List findByLinkTarget(Object linkTarget) {
        return findByProperty(LINK_TARGET, linkTarget);
    }

    public List findByDisable(Object disable) {
        return findByProperty(DISABLE, disable);
    }

    public List findAll() {
        log.debug("finding all Linkto instances");
        try {
            String queryString = "from Linkto";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Linkto merge(Linkto detachedInstance) {
        log.debug("merging Linkto instance");
        try {
            Linkto result = (Linkto) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Linkto instance) {
        log.debug("attaching dirty Linkto instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Linkto instance) {
        log.debug("attaching clean Linkto instance");
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
