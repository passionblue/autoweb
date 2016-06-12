package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * ThemeStyles entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ThemeStyles
 * @author MyEclipse Persistence Tools
 */

public class ThemeStylesDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(ThemeStylesDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String BODY_WIDTH = "bodyWidth";
    public static final String BODY_ALIGN = "bodyAlign";
    public static final String BODY_BACKGROUND = "bodyBackground";

    public void save(ThemeStyles transientInstance) {
        log.debug("saving ThemeStyles instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ThemeStyles persistentInstance) {
        log.debug("deleting ThemeStyles instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ThemeStyles findById(Long id) {
        log.debug("getting ThemeStyles instance with id: " + id);
        try {
            ThemeStyles instance = (ThemeStyles) getSession().get("com.autosite.db.ThemeStyles", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ThemeStyles instance) {
        log.debug("finding ThemeStyles instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ThemeStyles").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ThemeStyles instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ThemeStyles as model where model." + propertyName + "= ?";
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

    public List findByBodyWidth(Object bodyWidth) {
        return findByProperty(BODY_WIDTH, bodyWidth);
    }

    public List findByBodyAlign(Object bodyAlign) {
        return findByProperty(BODY_ALIGN, bodyAlign);
    }

    public List findByBodyBackground(Object bodyBackground) {
        return findByProperty(BODY_BACKGROUND, bodyBackground);
    }

    public List findAll() {
        log.debug("finding all ThemeStyles instances");
        try {
            String queryString = "from ThemeStyles";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ThemeStyles merge(ThemeStyles detachedInstance) {
        log.debug("merging ThemeStyles instance");
        try {
            ThemeStyles result = (ThemeStyles) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ThemeStyles instance) {
        log.debug("attaching dirty ThemeStyles instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ThemeStyles instance) {
        log.debug("attaching clean ThemeStyles instance");
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
