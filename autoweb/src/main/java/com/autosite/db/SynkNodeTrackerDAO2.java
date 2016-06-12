/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:17 EST 2015
*/

package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* DAO is generated by Eclipse. This is by AutoGen
 *
 */
public class SynkNodeTrackerDAO2  extends BaseHibernateDAO  {
    private static Logger log = LoggerFactory.getLogger(SynkNodeTrackerDAO2.class);

	public static final String ID = "id";
	public static final String SITE_ID = "siteId";
	public static final String NAMESPACE = "namespace";
	public static final String DEVICE_ID = "deviceId";
	public static final String REMOTE = "remote";
	public static final String STAMP = "stamp";

    public void save(SynkNodeTracker transientInstance) {
        log.debug("saving SynkNodeTracker instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SynkNodeTracker persistentInstance) {
        log.debug("deleting SynkNodeTracker instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SynkNodeTracker findById(Long id) {
        log.debug("getting SynkNodeTracker instance with id: " + id);
        try {
            SynkNodeTracker instance = (SynkNodeTracker) getSession().get("com.autosite.db.SynkNodeTracker", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SynkNodeTracker instance) {
        log.debug("finding SynkNodeTracker instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SynkNodeTracker").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SynkNodeTracker instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SynkNodeTracker as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findById(Object id) {
        return findByProperty(ID, id);
    }
    public List findBySiteId(Object siteId) {
        return findByProperty(SITE_ID, siteId);
    }
    public List findByNamespace(Object namespace) {
        return findByProperty(NAMESPACE, namespace);
    }
    public List findByDeviceId(Object deviceId) {
        return findByProperty(DEVICE_ID, deviceId);
    }
    public List findByRemote(Object remote) {
        return findByProperty(REMOTE, remote);
    }
    public List findByStamp(Object stamp) {
        return findByProperty(STAMP, stamp);
    }

    public List findAll() {
        log.debug("finding all SynkNodeTracker instances");
        try {
            String queryString = "from SynkNodeTracker";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SynkNodeTracker merge(SynkNodeTracker detachedInstance) {
        log.debug("merging SynkNodeTracker instance");
        try {
            SynkNodeTracker result = (SynkNodeTracker) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SynkNodeTracker instance) {
        log.debug("attaching dirty SynkNodeTracker instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SynkNodeTracker instance) {
        log.debug("attaching clean SynkNodeTracker instance");
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