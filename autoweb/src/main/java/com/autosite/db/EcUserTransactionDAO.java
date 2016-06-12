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
 * EcUserTransaction entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcUserTransaction
 * @author MyEclipse Persistence Tools
 */

public class EcUserTransactionDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcUserTransactionDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String ORDER_ID = "orderId";
    public static final String PAYMENT_INFO_ID = "paymentInfoId";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String RESULT = "result";

    public void save(EcUserTransaction transientInstance) {
        log.debug("saving EcUserTransaction instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcUserTransaction persistentInstance) {
        log.debug("deleting EcUserTransaction instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcUserTransaction findById(Long id) {
        log.debug("getting EcUserTransaction instance with id: " + id);
        try {
            EcUserTransaction instance = (EcUserTransaction) getSession().get("com.autosite.db.EcUserTransaction", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcUserTransaction instance) {
        log.debug("finding EcUserTransaction instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcUserTransaction").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcUserTransaction instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcUserTransaction as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByOrderId(Object orderId) {
        return findByProperty(ORDER_ID, orderId);
    }

    public List findByPaymentInfoId(Object paymentInfoId) {
        return findByProperty(PAYMENT_INFO_ID, paymentInfoId);
    }

    public List findByAmount(Object amount) {
        return findByProperty(AMOUNT, amount);
    }

    public List findByTransactionType(Object transactionType) {
        return findByProperty(TRANSACTION_TYPE, transactionType);
    }

    public List findByResult(Object result) {
        return findByProperty(RESULT, result);
    }

    public List findAll() {
        log.debug("finding all EcUserTransaction instances");
        try {
            String queryString = "from EcUserTransaction";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcUserTransaction merge(EcUserTransaction detachedInstance) {
        log.debug("merging EcUserTransaction instance");
        try {
            EcUserTransaction result = (EcUserTransaction) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcUserTransaction instance) {
        log.debug("attaching dirty EcUserTransaction instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcUserTransaction instance) {
        log.debug("attaching clean EcUserTransaction instance");
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
