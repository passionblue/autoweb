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
 * AutositeDefaults entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.AutositeDefaults
 * @author MyEclipse Persistence Tools
 */

public class AutositeDefaultsDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(AutositeDefaultsDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String STYLE_ID = "styleId";
    public static final String LINK_STYLE_ID = "linkStyleId";

    public void save(AutositeDefaults transientInstance) {
        log.debug("saving AutositeDefaults instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AutositeDefaults persistentInstance) {
        log.debug("deleting AutositeDefaults instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AutositeDefaults findById(Long id) {
        log.debug("getting AutositeDefaults instance with id: " + id);
        try {
            AutositeDefaults instance = (AutositeDefaults) getSession().get("com.autosite.db.AutositeDefaults", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AutositeDefaults instance) {
        log.debug("finding AutositeDefaults instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.AutositeDefaults").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AutositeDefaults instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AutositeDefaults as model where model." + propertyName + "= ?";
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

    public List findByStyleId(Object styleId) {
        return findByProperty(STYLE_ID, styleId);
    }

    public List findByLinkStyleId(Object linkStyleId) {
        return findByProperty(LINK_STYLE_ID, linkStyleId);
    }

    public List findAll() {
        log.debug("finding all AutositeDefaults instances");
        try {
            String queryString = "from AutositeDefaults";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AutositeDefaults merge(AutositeDefaults detachedInstance) {
        log.debug("merging AutositeDefaults instance");
        try {
            AutositeDefaults result = (AutositeDefaults) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AutositeDefaults instance) {
        log.debug("attaching dirty AutositeDefaults instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AutositeDefaults instance) {
        log.debug("attaching clean AutositeDefaults instance");
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
