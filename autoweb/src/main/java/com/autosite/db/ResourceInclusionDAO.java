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
 * ResourceInclusion entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ResourceInclusion
 * @author MyEclipse Persistence Tools
 */

public class ResourceInclusionDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ResourceInclusionDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String NAME = "name";
    public static final String INCLUDE = "include";
    public static final String RESOURCE_TYPE = "resourceType";

    public void save(ResourceInclusion transientInstance) {
        log.debug("saving ResourceInclusion instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ResourceInclusion persistentInstance) {
        log.debug("deleting ResourceInclusion instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ResourceInclusion findById(Long id) {
        log.debug("getting ResourceInclusion instance with id: " + id);
        try {
            ResourceInclusion instance = (ResourceInclusion) getSession().get("com.autosite.db.ResourceInclusion", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ResourceInclusion instance) {
        log.debug("finding ResourceInclusion instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ResourceInclusion").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ResourceInclusion instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ResourceInclusion as model where model." + propertyName + "= ?";
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

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByInclude(Object include) {
        return findByProperty(INCLUDE, include);
    }

    public List findByResourceType(Object resourceType) {
        return findByProperty(RESOURCE_TYPE, resourceType);
    }

    public List findAll() {
        log.debug("finding all ResourceInclusion instances");
        try {
            String queryString = "from ResourceInclusion";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ResourceInclusion merge(ResourceInclusion detachedInstance) {
        log.debug("merging ResourceInclusion instance");
        try {
            ResourceInclusion result = (ResourceInclusion) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ResourceInclusion instance) {
        log.debug("attaching dirty ResourceInclusion instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ResourceInclusion instance) {
        log.debug("attaching clean ResourceInclusion instance");
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
