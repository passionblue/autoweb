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
 * DevTable entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.DevTable
 * @author MyEclipse Persistence Tools
 */

public class DevTableDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(DevTableDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String DEV_NOTE_ID = "devNoteId";
    public static final String TITLE = "title";
    public static final String SUBJECT = "subject";
    public static final String DATA = "data";
    public static final String TYPE = "type";
    public static final String DISABLE = "disable";
    public static final String RADIO_VALUE = "radioValue";

    public void save(DevTable transientInstance) {
        log.debug("saving DevTable instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DevTable persistentInstance) {
        log.debug("deleting DevTable instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public DevTable findById(Long id) {
        log.debug("getting DevTable instance with id: " + id);
        try {
            DevTable instance = (DevTable) getSession().get("com.autosite.db.DevTable", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(DevTable instance) {
        log.debug("finding DevTable instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.DevTable").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding DevTable instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from DevTable as model where model." + propertyName + "= ?";
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

    public List findByDevNoteId(Object devNoteId) {
        return findByProperty(DEV_NOTE_ID, devNoteId);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findBySubject(Object subject) {
        return findByProperty(SUBJECT, subject);
    }

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findByDisable(Object disable) {
        return findByProperty(DISABLE, disable);
    }

    public List findByRadioValue(Object radioValue) {
        return findByProperty(RADIO_VALUE, radioValue);
    }

    public List findAll() {
        log.debug("finding all DevTable instances");
        try {
            String queryString = "from DevTable";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public DevTable merge(DevTable detachedInstance) {
        log.debug("merging DevTable instance");
        try {
            DevTable result = (DevTable) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DevTable instance) {
        log.debug("attaching dirty DevTable instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(DevTable instance) {
        log.debug("attaching clean DevTable instance");
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
