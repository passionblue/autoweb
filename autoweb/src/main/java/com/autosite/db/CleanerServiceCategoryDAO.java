package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * CleanerServiceCategory entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerServiceCategory
 * @author MyEclipse Persistence Tools
 */
public class CleanerServiceCategoryDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerServiceCategoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String TITLE = "title";
    public static final String IMAGE_PATH = "imagePath";
    public static final String IMAGE_PATH_LOCAL = "imagePathLocal";

    public void save(CleanerServiceCategory transientInstance) {
        log.debug("saving CleanerServiceCategory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerServiceCategory persistentInstance) {
        log.debug("deleting CleanerServiceCategory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerServiceCategory findById(Long id) {
        log.debug("getting CleanerServiceCategory instance with id: " + id);
        try {
            CleanerServiceCategory instance = (CleanerServiceCategory) getSession().get("com.autosite.db.CleanerServiceCategory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerServiceCategory instance) {
        log.debug("finding CleanerServiceCategory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerServiceCategory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerServiceCategory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerServiceCategory as model where model." + propertyName + "= ?";
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

    public List findByImagePath(Object imagePath) {
        return findByProperty(IMAGE_PATH, imagePath);
    }

    public List findByImagePathLocal(Object imagePathLocal) {
        return findByProperty(IMAGE_PATH_LOCAL, imagePathLocal);
    }

    public List findAll() {
        log.debug("finding all CleanerServiceCategory instances");
        try {
            String queryString = "from CleanerServiceCategory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerServiceCategory merge(CleanerServiceCategory detachedInstance) {
        log.debug("merging CleanerServiceCategory instance");
        try {
            CleanerServiceCategory result = (CleanerServiceCategory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerServiceCategory instance) {
        log.debug("attaching dirty CleanerServiceCategory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerServiceCategory instance) {
        log.debug("attaching clean CleanerServiceCategory instance");
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
