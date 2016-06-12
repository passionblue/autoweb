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
 * ForumConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ForumConfig
 * @author MyEclipse Persistence Tools
 */

public class ForumConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ForumConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String FORUM_MAIN_ID = "forumMainId";

    public void save(ForumConfig transientInstance) {
        log.debug("saving ForumConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ForumConfig persistentInstance) {
        log.debug("deleting ForumConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ForumConfig findById(Long id) {
        log.debug("getting ForumConfig instance with id: " + id);
        try {
            ForumConfig instance = (ForumConfig) getSession().get("com.autosite.db.ForumConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ForumConfig instance) {
        log.debug("finding ForumConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ForumConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ForumConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ForumConfig as model where model." + propertyName + "= ?";
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

    public List findByForumMainId(Object forumMainId) {
        return findByProperty(FORUM_MAIN_ID, forumMainId);
    }

    public List findAll() {
        log.debug("finding all ForumConfig instances");
        try {
            String queryString = "from ForumConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ForumConfig merge(ForumConfig detachedInstance) {
        log.debug("merging ForumConfig instance");
        try {
            ForumConfig result = (ForumConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ForumConfig instance) {
        log.debug("attaching dirty ForumConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ForumConfig instance) {
        log.debug("attaching clean ForumConfig instance");
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
