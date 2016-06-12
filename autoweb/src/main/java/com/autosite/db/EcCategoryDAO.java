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
 * EcCategory entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.EcCategory
 * @author MyEclipse Persistence Tools
 */

public class EcCategoryDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcCategoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PARENT_ID = "parentId";
    public static final String PAGE_ID = "pageId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String IMAGE_URL = "imageUrl";
    public static final String CATEGORY_DESCRIPTION = "categoryDescription";
    public static final String ALT1 = "alt1";
    public static final String ALT2 = "alt2";

    public void save(EcCategory transientInstance) {
        log.debug("saving EcCategory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcCategory persistentInstance) {
        log.debug("deleting EcCategory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcCategory findById(Long id) {
        log.debug("getting EcCategory instance with id: " + id);
        try {
            EcCategory instance = (EcCategory) getSession().get("com.autosite.db.EcCategory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcCategory instance) {
        log.debug("finding EcCategory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcCategory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcCategory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcCategory as model where model." + propertyName + "= ?";
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

    public List findByParentId(Object parentId) {
        return findByProperty(PARENT_ID, parentId);
    }

    public List findByPageId(Object pageId) {
        return findByProperty(PAGE_ID, pageId);
    }

    public List findByCategoryName(Object categoryName) {
        return findByProperty(CATEGORY_NAME, categoryName);
    }

    public List findByImageUrl(Object imageUrl) {
        return findByProperty(IMAGE_URL, imageUrl);
    }

    public List findByCategoryDescription(Object categoryDescription) {
        return findByProperty(CATEGORY_DESCRIPTION, categoryDescription);
    }

    public List findByAlt1(Object alt1) {
        return findByProperty(ALT1, alt1);
    }

    public List findByAlt2(Object alt2) {
        return findByProperty(ALT2, alt2);
    }

    public List findAll() {
        log.debug("finding all EcCategory instances");
        try {
            String queryString = "from EcCategory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcCategory merge(EcCategory detachedInstance) {
        log.debug("merging EcCategory instance");
        try {
            EcCategory result = (EcCategory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcCategory instance) {
        log.debug("attaching dirty EcCategory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcCategory instance) {
        log.debug("attaching clean EcCategory instance");
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
