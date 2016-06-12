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
 * ThemeAggregator entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ThemeAggregator
 * @author MyEclipse Persistence Tools
 */

public class ThemeAggregatorDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(ThemeAggregatorDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String THEME_NAME = "themeName";
    public static final String LAYOUT_PAGE = "layoutPage";
    public static final String CSS_INDEX = "cssIndex";
    public static final String THEME_STYLE_ID = "themeStyleId";

    public void save(ThemeAggregator transientInstance) {
        log.debug("saving ThemeAggregator instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ThemeAggregator persistentInstance) {
        log.debug("deleting ThemeAggregator instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ThemeAggregator findById(Long id) {
        log.debug("getting ThemeAggregator instance with id: " + id);
        try {
            ThemeAggregator instance = (ThemeAggregator) getSession().get("com.autosite.db.ThemeAggregator", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ThemeAggregator instance) {
        log.debug("finding ThemeAggregator instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ThemeAggregator").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ThemeAggregator instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ThemeAggregator as model where model." + propertyName + "= ?";
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

    public List findByThemeName(Object themeName) {
        return findByProperty(THEME_NAME, themeName);
    }

    public List findByLayoutPage(Object layoutPage) {
        return findByProperty(LAYOUT_PAGE, layoutPage);
    }

    public List findByCssIndex(Object cssIndex) {
        return findByProperty(CSS_INDEX, cssIndex);
    }

    public List findByThemeStyleId(Object themeStyleId) {
        return findByProperty(THEME_STYLE_ID, themeStyleId);
    }

    public List findAll() {
        log.debug("finding all ThemeAggregator instances");
        try {
            String queryString = "from ThemeAggregator";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ThemeAggregator merge(ThemeAggregator detachedInstance) {
        log.debug("merging ThemeAggregator instance");
        try {
            ThemeAggregator result = (ThemeAggregator) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ThemeAggregator instance) {
        log.debug("attaching dirty ThemeAggregator instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ThemeAggregator instance) {
        log.debug("attaching clean ThemeAggregator instance");
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
