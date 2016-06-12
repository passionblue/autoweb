package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * CleanerCustomerNotification entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.autosite.db.CleanerCustomerNotification
 * @author MyEclipse Persistence Tools
 */
public class CleanerCustomerNotificationDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerCustomerNotificationDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String REASONFOR_ID = "reasonforId";
    public static final String REASONFOR_TARGET = "reasonforTarget";
    public static final String NOTIFICATION_TYPE = "notificationType";
    public static final String SOURCE_TYPE = "sourceType";
    public static final String TRIGGER_TYPE = "triggerType";
    public static final String IS_RETRANSMIT = "isRetransmit";
    public static final String METHOD_TYPE = "methodType";
    public static final String TEMPLATE_TYPE = "templateType";
    public static final String CONTENT = "content";
    public static final String DESTINATION = "destination";
    public static final String REFERENCE = "reference";

    public void save(CleanerCustomerNotification transientInstance) {
        log.debug("saving CleanerCustomerNotification instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerCustomerNotification persistentInstance) {
        log.debug("deleting CleanerCustomerNotification instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerCustomerNotification findById(Long id) {
        log.debug("getting CleanerCustomerNotification instance with id: " + id);
        try {
            CleanerCustomerNotification instance = (CleanerCustomerNotification) getSession().get("com.autosite.db.CleanerCustomerNotification", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerCustomerNotification instance) {
        log.debug("finding CleanerCustomerNotification instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerCustomerNotification").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerCustomerNotification instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerCustomerNotification as model where model." + propertyName + "= ?";
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

    public List findByCustomerId(Object customerId) {
        return findByProperty(CUSTOMER_ID, customerId);
    }

    public List findByReasonforId(Object reasonforId) {
        return findByProperty(REASONFOR_ID, reasonforId);
    }

    public List findByReasonforTarget(Object reasonforTarget) {
        return findByProperty(REASONFOR_TARGET, reasonforTarget);
    }

    public List findByNotificationType(Object notificationType) {
        return findByProperty(NOTIFICATION_TYPE, notificationType);
    }

    public List findBySourceType(Object sourceType) {
        return findByProperty(SOURCE_TYPE, sourceType);
    }

    public List findByTriggerType(Object triggerType) {
        return findByProperty(TRIGGER_TYPE, triggerType);
    }

    public List findByIsRetransmit(Object isRetransmit) {
        return findByProperty(IS_RETRANSMIT, isRetransmit);
    }

    public List findByMethodType(Object methodType) {
        return findByProperty(METHOD_TYPE, methodType);
    }

    public List findByTemplateType(Object templateType) {
        return findByProperty(TEMPLATE_TYPE, templateType);
    }

    public List findByContent(Object content) {
        return findByProperty(CONTENT, content);
    }

    public List findByDestination(Object destination) {
        return findByProperty(DESTINATION, destination);
    }

    public List findByReference(Object reference) {
        return findByProperty(REFERENCE, reference);
    }

    public List findAll() {
        log.debug("finding all CleanerCustomerNotification instances");
        try {
            String queryString = "from CleanerCustomerNotification";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerCustomerNotification merge(CleanerCustomerNotification detachedInstance) {
        log.debug("merging CleanerCustomerNotification instance");
        try {
            CleanerCustomerNotification result = (CleanerCustomerNotification) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerCustomerNotification instance) {
        log.debug("attaching dirty CleanerCustomerNotification instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerCustomerNotification instance) {
        log.debug("attaching clean CleanerCustomerNotification instance");
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
