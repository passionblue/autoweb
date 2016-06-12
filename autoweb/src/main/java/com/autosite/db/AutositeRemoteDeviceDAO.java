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
 * AutositeRemoteDevice entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.AutositeRemoteDevice
 * @author MyEclipse Persistence Tools
 */
public class AutositeRemoteDeviceDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(AutositeRemoteDeviceDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String DEVICE_ID = "deviceId";
    public static final String DEVICE_TYPE = "deviceType";
    public static final String TITLE = "title";
    public static final String DISABLE = "disable";
    public static final String UNREGISTERED = "unregistered";

    public void save(AutositeRemoteDevice transientInstance) {
        log.debug("saving AutositeRemoteDevice instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AutositeRemoteDevice persistentInstance) {
        log.debug("deleting AutositeRemoteDevice instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AutositeRemoteDevice findById(Long id) {
        log.debug("getting AutositeRemoteDevice instance with id: " + id);
        try {
            AutositeRemoteDevice instance = (AutositeRemoteDevice) getSession().get("com.autosite.db.AutositeRemoteDevice", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AutositeRemoteDevice instance) {
        log.debug("finding AutositeRemoteDevice instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.AutositeRemoteDevice").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AutositeRemoteDevice instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AutositeRemoteDevice as model where model." + propertyName + "= ?";
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

    public List findByDeviceId(Object deviceId) {
        return findByProperty(DEVICE_ID, deviceId);
    }

    public List findByDeviceType(Object deviceType) {
        return findByProperty(DEVICE_TYPE, deviceType);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByDisable(Object disable) {
        return findByProperty(DISABLE, disable);
    }

    public List findByUnregistered(Object unregistered) {
        return findByProperty(UNREGISTERED, unregistered);
    }

    public List findAll() {
        log.debug("finding all AutositeRemoteDevice instances");
        try {
            String queryString = "from AutositeRemoteDevice";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AutositeRemoteDevice merge(AutositeRemoteDevice detachedInstance) {
        log.debug("merging AutositeRemoteDevice instance");
        try {
            AutositeRemoteDevice result = (AutositeRemoteDevice) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AutositeRemoteDevice instance) {
        log.debug("attaching dirty AutositeRemoteDevice instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AutositeRemoteDevice instance) {
        log.debug("attaching clean AutositeRemoteDevice instance");
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
