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
 * EcPageProductRel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcPageProductRel
 * @author MyEclipse Persistence Tools
 */

public class EcPageProductRelDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcPageProductRelDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PRODUCT_ID = "productId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String HIDE = "hide";
    public static final String MAIN_CATEGORY = "mainCategory";

    public void save(EcPageProductRel transientInstance) {
        log.debug("saving EcPageProductRel instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcPageProductRel persistentInstance) {
        log.debug("deleting EcPageProductRel instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcPageProductRel findById(Long id) {
        log.debug("getting EcPageProductRel instance with id: " + id);
        try {
            EcPageProductRel instance = (EcPageProductRel) getSession().get("com.autosite.db.EcPageProductRel", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcPageProductRel instance) {
        log.debug("finding EcPageProductRel instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcPageProductRel").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcPageProductRel instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcPageProductRel as model where model." + propertyName + "= ?";
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

    public List findByProductId(Object productId) {
        return findByProperty(PRODUCT_ID, productId);
    }

    public List findByCategoryId(Object categoryId) {
        return findByProperty(CATEGORY_ID, categoryId);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findByMainCategory(Object mainCategory) {
        return findByProperty(MAIN_CATEGORY, mainCategory);
    }

    public List findAll() {
        log.debug("finding all EcPageProductRel instances");
        try {
            String queryString = "from EcPageProductRel";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcPageProductRel merge(EcPageProductRel detachedInstance) {
        log.debug("merging EcPageProductRel instance");
        try {
            EcPageProductRel result = (EcPageProductRel) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcPageProductRel instance) {
        log.debug("attaching dirty EcPageProductRel instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcPageProductRel instance) {
        log.debug("attaching clean EcPageProductRel instance");
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
