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
 * EcOrderItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcOrderItem
 * @author MyEclipse Persistence Tools
 */

public class EcOrderItemDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcOrderItemDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String ORDER_ID = "orderId";
    public static final String PRODUCT_ID = "productId";
    public static final String SITE_SKU = "siteSku";
    public static final String SIZE_VARIATION = "sizeVariation";
    public static final String COLOR_VARIATION = "colorVariation";
    public static final String QTY = "qty";
    public static final String UNIT_PRICE = "unitPrice";
    public static final String ORDER_PRICE = "orderPrice";
    public static final String PACKAGE_ID = "packageId";

    public void save(EcOrderItem transientInstance) {
        log.debug("saving EcOrderItem instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcOrderItem persistentInstance) {
        log.debug("deleting EcOrderItem instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcOrderItem findById(Long id) {
        log.debug("getting EcOrderItem instance with id: " + id);
        try {
            EcOrderItem instance = (EcOrderItem) getSession().get("com.autosite.db.EcOrderItem", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcOrderItem instance) {
        log.debug("finding EcOrderItem instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcOrderItem").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcOrderItem instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcOrderItem as model where model." + propertyName + "= ?";
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

    public List findByOrderId(Object orderId) {
        return findByProperty(ORDER_ID, orderId);
    }

    public List findByProductId(Object productId) {
        return findByProperty(PRODUCT_ID, productId);
    }

    public List findBySiteSku(Object siteSku) {
        return findByProperty(SITE_SKU, siteSku);
    }

    public List findBySizeVariation(Object sizeVariation) {
        return findByProperty(SIZE_VARIATION, sizeVariation);
    }

    public List findByColorVariation(Object colorVariation) {
        return findByProperty(COLOR_VARIATION, colorVariation);
    }

    public List findByQty(Object qty) {
        return findByProperty(QTY, qty);
    }

    public List findByUnitPrice(Object unitPrice) {
        return findByProperty(UNIT_PRICE, unitPrice);
    }

    public List findByOrderPrice(Object orderPrice) {
        return findByProperty(ORDER_PRICE, orderPrice);
    }

    public List findByPackageId(Object packageId) {
        return findByProperty(PACKAGE_ID, packageId);
    }

    public List findAll() {
        log.debug("finding all EcOrderItem instances");
        try {
            String queryString = "from EcOrderItem";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcOrderItem merge(EcOrderItem detachedInstance) {
        log.debug("merging EcOrderItem instance");
        try {
            EcOrderItem result = (EcOrderItem) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcOrderItem instance) {
        log.debug("attaching dirty EcOrderItem instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcOrderItem instance) {
        log.debug("attaching clean EcOrderItem instance");
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
