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
 * Section entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.Section
 * @author MyEclipse Persistence Tools
 */

public class SectionDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SectionDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String TITLE = "title";
    public static final String MAIN_PAGE_ID = "mainPageId";

    public void save(Section transientInstance) {
        log.debug("saving Section instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Section persistentInstance) {
        log.debug("deleting Section instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Section findById(Long id) {
        log.debug("getting Section instance with id: " + id);
        try {
            Section instance = (Section) getSession().get("com.autosite.db.Section", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Section instance) {
        log.debug("finding Section instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Section").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Section instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Section as model where model." + propertyName + "= ?";
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

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByMainPageId(Object mainPageId) {
        return findByProperty(MAIN_PAGE_ID, mainPageId);
    }

    public List findAll() {
        log.debug("finding all Section instances");
        try {
            String queryString = "from Section";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Section merge(Section detachedInstance) {
        log.debug("merging Section instance");
        try {
            Section result = (Section) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Section instance) {
        log.debug("attaching dirty Section instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Section instance) {
        log.debug("attaching clean Section instance");
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
