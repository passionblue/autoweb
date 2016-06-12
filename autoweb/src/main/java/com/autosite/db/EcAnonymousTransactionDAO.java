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
 * EcAnonymousTransaction entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcAnonymousTransaction
 * @author MyEclipse Persistence Tools
 */

public class EcAnonymousTransactionDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcAnonymousTransactionDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String ANONYMOUS_USER_ID = "anonymousUserId";
    public static final String ORDER_ID = "orderId";
    public static final String PAYMENT_INFO_ID = "paymentInfoId";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String RESULT = "result";
    public static final String RETURN_CODE = "returnCode";
    public static final String RETURN_MSG = "returnMsg";

    public void save(EcAnonymousTransaction transientInstance) {
        log.debug("saving EcAnonymousTransaction instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcAnonymousTransaction persistentInstance) {
        log.debug("deleting EcAnonymousTransaction instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcAnonymousTransaction findById(Long id) {
        log.debug("getting EcAnonymousTransaction instance with id: " + id);
        try {
            EcAnonymousTransaction instance = (EcAnonymousTransaction) getSession().get("com.autosite.db.EcAnonymousTransaction", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcAnonymousTransaction instance) {
        log.debug("finding EcAnonymousTransaction instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcAnonymousTransaction").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcAnonymousTransaction instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcAnonymousTransaction as model where model." + propertyName + "= ?";
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

    public List findByAnonymousUserId(Object anonymousUserId) {
        return findByProperty(ANONYMOUS_USER_ID, anonymousUserId);
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

    public List findByReturnCode(Object returnCode) {
        return findByProperty(RETURN_CODE, returnCode);
    }

    public List findByReturnMsg(Object returnMsg) {
        return findByProperty(RETURN_MSG, returnMsg);
    }

    public List findAll() {
        log.debug("finding all EcAnonymousTransaction instances");
        try {
            String queryString = "from EcAnonymousTransaction";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcAnonymousTransaction merge(EcAnonymousTransaction detachedInstance) {
        log.debug("merging EcAnonymousTransaction instance");
        try {
            EcAnonymousTransaction result = (EcAnonymousTransaction) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcAnonymousTransaction instance) {
        log.debug("attaching dirty EcAnonymousTransaction instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcAnonymousTransaction instance) {
        log.debug("attaching clean EcAnonymousTransaction instance");
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
