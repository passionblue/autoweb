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
 * ResourceList entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ResourceList
 * @author MyEclipse Persistence Tools
 */

public class ResourceListDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ResourceListDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String URL = "url";
    public static final String ORIGINAL_NAME = "originalName";
    public static final String SIZE = "size";
    public static final String RESOURCE_TYPE = "resourceType";
    public static final String IMAGE_WIDTH = "imageWidth";
    public static final String IMAGE_HIGHT = "imageHight";

    public void save(ResourceList transientInstance) {
        log.debug("saving ResourceList instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ResourceList persistentInstance) {
        log.debug("deleting ResourceList instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ResourceList findById(Long id) {
        log.debug("getting ResourceList instance with id: " + id);
        try {
            ResourceList instance = (ResourceList) getSession().get("com.autosite.db.ResourceList", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ResourceList instance) {
        log.debug("finding ResourceList instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ResourceList").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ResourceList instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ResourceList as model where model." + propertyName + "= ?";
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

    public List findByUrl(Object url) {
        return findByProperty(URL, url);
    }

    public List findByOriginalName(Object originalName) {
        return findByProperty(ORIGINAL_NAME, originalName);
    }

    public List findBySize(Object size) {
        return findByProperty(SIZE, size);
    }

    public List findByResourceType(Object resourceType) {
        return findByProperty(RESOURCE_TYPE, resourceType);
    }

    public List findByImageWidth(Object imageWidth) {
        return findByProperty(IMAGE_WIDTH, imageWidth);
    }

    public List findByImageHight(Object imageHight) {
        return findByProperty(IMAGE_HIGHT, imageHight);
    }

    public List findAll() {
        log.debug("finding all ResourceList instances");
        try {
            String queryString = "from ResourceList";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ResourceList merge(ResourceList detachedInstance) {
        log.debug("merging ResourceList instance");
        try {
            ResourceList result = (ResourceList) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ResourceList instance) {
        log.debug("attaching dirty ResourceList instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ResourceList instance) {
        log.debug("attaching clean ResourceList instance");
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
