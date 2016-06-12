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
 * SiteConfigStyle entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SiteConfigStyle
 * @author MyEclipse Persistence Tools
 */

public class SiteConfigStyleDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SiteConfigStyleDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String THEME_ID = "themeId";
    public static final String CSS_INDEX = "cssIndex";
    public static final String CSS_IMPORT = "cssImport";
    public static final String LAYOUT_INDEX = "layoutIndex";

    public void save(SiteConfigStyle transientInstance) {
        log.debug("saving SiteConfigStyle instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SiteConfigStyle persistentInstance) {
        log.debug("deleting SiteConfigStyle instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SiteConfigStyle findById(Long id) {
        log.debug("getting SiteConfigStyle instance with id: " + id);
        try {
            SiteConfigStyle instance = (SiteConfigStyle) getSession().get("com.autosite.db.SiteConfigStyle", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SiteConfigStyle instance) {
        log.debug("finding SiteConfigStyle instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SiteConfigStyle").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SiteConfigStyle instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SiteConfigStyle as model where model." + propertyName + "= ?";
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

    public List findByThemeId(Object themeId) {
        return findByProperty(THEME_ID, themeId);
    }

    public List findByCssIndex(Object cssIndex) {
        return findByProperty(CSS_INDEX, cssIndex);
    }

    public List findByCssImport(Object cssImport) {
        return findByProperty(CSS_IMPORT, cssImport);
    }

    public List findByLayoutIndex(Object layoutIndex) {
        return findByProperty(LAYOUT_INDEX, layoutIndex);
    }

    public List findAll() {
        log.debug("finding all SiteConfigStyle instances");
        try {
            String queryString = "from SiteConfigStyle";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SiteConfigStyle merge(SiteConfigStyle detachedInstance) {
        log.debug("merging SiteConfigStyle instance");
        try {
            SiteConfigStyle result = (SiteConfigStyle) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SiteConfigStyle instance) {
        log.debug("attaching dirty SiteConfigStyle instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SiteConfigStyle instance) {
        log.debug("attaching clean SiteConfigStyle instance");
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
