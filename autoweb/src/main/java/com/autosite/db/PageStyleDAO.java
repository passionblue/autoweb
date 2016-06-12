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
 * PageStyle entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.PageStyle
 * @author MyEclipse Persistence Tools
 */

public class PageStyleDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PageStyleDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ID = "pageId";
    public static final String STYLE_ID = "styleId";

    public void save(PageStyle transientInstance) {
        log.debug("saving PageStyle instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PageStyle persistentInstance) {
        log.debug("deleting PageStyle instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PageStyle findById(Long id) {
        log.debug("getting PageStyle instance with id: " + id);
        try {
            PageStyle instance = (PageStyle) getSession().get("com.autosite.db.PageStyle", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PageStyle instance) {
        log.debug("finding PageStyle instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PageStyle").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PageStyle instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PageStyle as model where model." + propertyName + "= ?";
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

    public List findByStyleId(Object styleId) {
        return findByProperty(STYLE_ID, styleId);
    }

    public List findAll() {
        log.debug("finding all PageStyle instances");
        try {
            String queryString = "from PageStyle";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PageStyle merge(PageStyle detachedInstance) {
        log.debug("merging PageStyle instance");
        try {
            PageStyle result = (PageStyle) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PageStyle instance) {
        log.debug("attaching dirty PageStyle instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PageStyle instance) {
        log.debug("attaching clean PageStyle instance");
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
