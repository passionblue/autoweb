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
 * EcOrder entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.EcOrder
 * @author MyEclipse Persistence Tools
 */

public class EcOrderDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcOrderDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String ANONYMOUS_USER_ID = "anonymousUserId";
    public static final String ORDER_NUM = "orderNum";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String ORDER_TOTAL = "orderTotal";
    public static final String RE_PROCESS = "reProcess";
    public static final String ORG_ORDER_ID = "orgOrderId";
    public static final String APPROVED_BY = "approvedBy";
    public static final String FULFILLED_BY = "fulfilledBy";
    public static final String SHIPPED_BY = "shippedBy";

    public void save(EcOrder transientInstance) {
        log.debug("saving EcOrder instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcOrder persistentInstance) {
        log.debug("deleting EcOrder instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcOrder findById(Long id) {
        log.debug("getting EcOrder instance with id: " + id);
        try {
            EcOrder instance = (EcOrder) getSession().get("com.autosite.db.EcOrder", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcOrder instance) {
        log.debug("finding EcOrder instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcOrder").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcOrder instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcOrder as model where model." + propertyName + "= ?";
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

    public List findByAnonymousUserId(Object anonymousUserId) {
        return findByProperty(ANONYMOUS_USER_ID, anonymousUserId);
    }

    public List findByOrderNum(Object orderNum) {
        return findByProperty(ORDER_NUM, orderNum);
    }

    public List findByOrderStatus(Object orderStatus) {
        return findByProperty(ORDER_STATUS, orderStatus);
    }

    public List findByOrderTotal(Object orderTotal) {
        return findByProperty(ORDER_TOTAL, orderTotal);
    }

    public List findByReProcess(Object reProcess) {
        return findByProperty(RE_PROCESS, reProcess);
    }

    public List findByOrgOrderId(Object orgOrderId) {
        return findByProperty(ORG_ORDER_ID, orgOrderId);
    }

    public List findByApprovedBy(Object approvedBy) {
        return findByProperty(APPROVED_BY, approvedBy);
    }

    public List findByFulfilledBy(Object fulfilledBy) {
        return findByProperty(FULFILLED_BY, fulfilledBy);
    }

    public List findByShippedBy(Object shippedBy) {
        return findByProperty(SHIPPED_BY, shippedBy);
    }

    public List findAll() {
        log.debug("finding all EcOrder instances");
        try {
            String queryString = "from EcOrder";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcOrder merge(EcOrder detachedInstance) {
        log.debug("merging EcOrder instance");
        try {
            EcOrder result = (EcOrder) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcOrder instance) {
        log.debug("attaching dirty EcOrder instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcOrder instance) {
        log.debug("attaching clean EcOrder instance");
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
