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
 * SynkNamespaceRecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SynkNamespaceRecord
 * @author MyEclipse Persistence Tools
 */
public class SynkNamespaceRecordDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(SynkNamespaceRecordDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String NAMESPACE = "namespace";
    public static final String RECORD_ID = "recordId";
    public static final String STAMP = "stamp";
    public static final String ORG_STAMP = "orgStamp";
    public static final String DELETED = "deleted";
    public static final String CREATED_BY_DEVICE_ID = "createdByDeviceId";
    public static final String CREATED_BY_IP = "createdByIp";
    public static final String CREATED_BY_USER = "createdByUser";
    public static final String UPDATED_BY_DEVICE_ID = "updatedByDeviceId";
    public static final String UPDATED_BY_IP = "updatedByIp";
    public static final String UPDATED_BY_USER = "updatedByUser";

    public void save(SynkNamespaceRecord transientInstance) {
        log.debug("saving SynkNamespaceRecord instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SynkNamespaceRecord persistentInstance) {
        log.debug("deleting SynkNamespaceRecord instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SynkNamespaceRecord findById(Long id) {
        log.debug("getting SynkNamespaceRecord instance with id: " + id);
        try {
            SynkNamespaceRecord instance = (SynkNamespaceRecord) getSession().get("com.autosite.db.SynkNamespaceRecord", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SynkNamespaceRecord instance) {
        log.debug("finding SynkNamespaceRecord instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SynkNamespaceRecord").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SynkNamespaceRecord instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SynkNamespaceRecord as model where model." + propertyName + "= ?";
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

    public List findByNamespace(Object namespace) {
        return findByProperty(NAMESPACE, namespace);
    }

    public List findByRecordId(Object recordId) {
        return findByProperty(RECORD_ID, recordId);
    }

    public List findByStamp(Object stamp) {
        return findByProperty(STAMP, stamp);
    }

    public List findByOrgStamp(Object orgStamp) {
        return findByProperty(ORG_STAMP, orgStamp);
    }

    public List findByDeleted(Object deleted) {
        return findByProperty(DELETED, deleted);
    }

    public List findByCreatedByDeviceId(Object createdByDeviceId) {
        return findByProperty(CREATED_BY_DEVICE_ID, createdByDeviceId);
    }

    public List findByCreatedByIp(Object createdByIp) {
        return findByProperty(CREATED_BY_IP, createdByIp);
    }

    public List findByCreatedByUser(Object createdByUser) {
        return findByProperty(CREATED_BY_USER, createdByUser);
    }

    public List findByUpdatedByDeviceId(Object updatedByDeviceId) {
        return findByProperty(UPDATED_BY_DEVICE_ID, updatedByDeviceId);
    }

    public List findByUpdatedByIp(Object updatedByIp) {
        return findByProperty(UPDATED_BY_IP, updatedByIp);
    }

    public List findByUpdatedByUser(Object updatedByUser) {
        return findByProperty(UPDATED_BY_USER, updatedByUser);
    }

    public List findAll() {
        log.debug("finding all SynkNamespaceRecord instances");
        try {
            String queryString = "from SynkNamespaceRecord";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SynkNamespaceRecord merge(SynkNamespaceRecord detachedInstance) {
        log.debug("merging SynkNamespaceRecord instance");
        try {
            SynkNamespaceRecord result = (SynkNamespaceRecord) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SynkNamespaceRecord instance) {
        log.debug("attaching dirty SynkNamespaceRecord instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SynkNamespaceRecord instance) {
        log.debug("attaching clean SynkNamespaceRecord instance");
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
