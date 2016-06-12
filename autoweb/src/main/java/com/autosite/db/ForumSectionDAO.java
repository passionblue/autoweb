package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ForumSection entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ForumSection
 * @author MyEclipse Persistence Tools
 */

public class ForumSectionDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ForumSectionDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String SECTION_TITLE = "sectionTitle";

    public void save(ForumSection transientInstance) {
        log.debug("saving ForumSection instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ForumSection persistentInstance) {
        log.debug("deleting ForumSection instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ForumSection findById(Long id) {
        log.debug("getting ForumSection instance with id: " + id);
        try {
            ForumSection instance = (ForumSection) getSession().get("com.autosite.db.ForumSection", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ForumSection instance) {
        log.debug("finding ForumSection instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ForumSection").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ForumSection instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ForumSection as model where model." + propertyName + "= ?";
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

    public List findBySectionTitle(Object sectionTitle) {
        return findByProperty(SECTION_TITLE, sectionTitle);
    }

    public List findAll() {
        log.debug("finding all ForumSection instances");
        try {
            String queryString = "from ForumSection";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ForumSection merge(ForumSection detachedInstance) {
        log.debug("merging ForumSection instance");
        try {
            ForumSection result = (ForumSection) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ForumSection instance) {
        log.debug("attaching dirty ForumSection instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ForumSection instance) {
        log.debug("attaching clean ForumSection instance");
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
