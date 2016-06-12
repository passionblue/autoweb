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
 * AutositeSessionContextEntity entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.autosite.db.AutositeSessionContextEntity
 * @author MyEclipse Persistence Tools
 */
public class AutositeSessionContextEntityDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(AutositeSessionContextEntityDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String SERIAL = "serial";
    public static final String IS_LOGIN = "isLogin";
    public static final String LOGIN_USER_ID = "loginUserId";
    public static final String SESSION_TYPE = "sessionType";
    public static final String REMOTE_DEVICE_ID = "remoteDeviceId";
    public static final String REMOTE_IP = "remoteIp";

    public void save(AutositeSessionContextEntity transientInstance) {
        log.debug("saving AutositeSessionContextEntity instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AutositeSessionContextEntity persistentInstance) {
        log.debug("deleting AutositeSessionContextEntity instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AutositeSessionContextEntity findById(Long id) {
        log.debug("getting AutositeSessionContextEntity instance with id: " + id);
        try {
            AutositeSessionContextEntity instance = (AutositeSessionContextEntity) getSession().get("com.autosite.db.AutositeSessionContextEntity", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AutositeSessionContextEntity instance) {
        log.debug("finding AutositeSessionContextEntity instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.AutositeSessionContextEntity").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AutositeSessionContextEntity instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AutositeSessionContextEntity as model where model." + propertyName + "= ?";
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

    public List findBySerial(Object serial) {
        return findByProperty(SERIAL, serial);
    }

    public List findByIsLogin(Object isLogin) {
        return findByProperty(IS_LOGIN, isLogin);
    }

    public List findByLoginUserId(Object loginUserId) {
        return findByProperty(LOGIN_USER_ID, loginUserId);
    }

    public List findBySessionType(Object sessionType) {
        return findByProperty(SESSION_TYPE, sessionType);
    }

    public List findByRemoteDeviceId(Object remoteDeviceId) {
        return findByProperty(REMOTE_DEVICE_ID, remoteDeviceId);
    }

    public List findByRemoteIp(Object remoteIp) {
        return findByProperty(REMOTE_IP, remoteIp);
    }

    public List findAll() {
        log.debug("finding all AutositeSessionContextEntity instances");
        try {
            String queryString = "from AutositeSessionContextEntity";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AutositeSessionContextEntity merge(AutositeSessionContextEntity detachedInstance) {
        log.debug("merging AutositeSessionContextEntity instance");
        try {
            AutositeSessionContextEntity result = (AutositeSessionContextEntity) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AutositeSessionContextEntity instance) {
        log.debug("attaching dirty AutositeSessionContextEntity instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AutositeSessionContextEntity instance) {
        log.debug("attaching clean AutositeSessionContextEntity instance");
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
