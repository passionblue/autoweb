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
 * CleanerServiceItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerServiceItem
 * @author MyEclipse Persistence Tools
 */
public class CleanerServiceItemDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerServiceItemDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String SERVICE_ID = "serviceId";
    public static final String SERVICE_ITEM_ID = "serviceItemId";
    public static final String ITEM_TYPE = "itemType";
    public static final String TITLE = "title";
    public static final String IMAGE_PATH = "imagePath";
    public static final String IMAGE_PATH_LOCAL = "imagePathLocal";
    public static final String BASE_PRICE = "basePrice";
    public static final String NOTE = "note";

    public void save(CleanerServiceItem transientInstance) {
        log.debug("saving CleanerServiceItem instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerServiceItem persistentInstance) {
        log.debug("deleting CleanerServiceItem instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerServiceItem findById(Long id) {
        log.debug("getting CleanerServiceItem instance with id: " + id);
        try {
            CleanerServiceItem instance = (CleanerServiceItem) getSession().get("com.autosite.db.CleanerServiceItem", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerServiceItem instance) {
        log.debug("finding CleanerServiceItem instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerServiceItem").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerServiceItem instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerServiceItem as model where model." + propertyName + "= ?";
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

    public List findByServiceId(Object serviceId) {
        return findByProperty(SERVICE_ID, serviceId);
    }

    public List findByServiceItemId(Object serviceItemId) {
        return findByProperty(SERVICE_ITEM_ID, serviceItemId);
    }

    public List findByItemType(Object itemType) {
        return findByProperty(ITEM_TYPE, itemType);
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

    public List findByBasePrice(Object basePrice) {
        return findByProperty(BASE_PRICE, basePrice);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findAll() {
        log.debug("finding all CleanerServiceItem instances");
        try {
            String queryString = "from CleanerServiceItem";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerServiceItem merge(CleanerServiceItem detachedInstance) {
        log.debug("merging CleanerServiceItem instance");
        try {
            CleanerServiceItem result = (CleanerServiceItem) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerServiceItem instance) {
        log.debug("attaching dirty CleanerServiceItem instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerServiceItem instance) {
        log.debug("attaching clean CleanerServiceItem instance");
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
