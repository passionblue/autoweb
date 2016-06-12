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
 * CleanerServiceProcess entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerServiceProcess
 * @author MyEclipse Persistence Tools
 */
public class CleanerServiceProcessDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerServiceProcessDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String TICKET_ID = "ticketId";
    public static final String PROCESS_USER_ID = "processUserId";
    public static final String PROCESS_TYPE = "processType";
    public static final String NOTE = "note";

    public void save(CleanerServiceProcess transientInstance) {
        log.debug("saving CleanerServiceProcess instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerServiceProcess persistentInstance) {
        log.debug("deleting CleanerServiceProcess instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerServiceProcess findById(Long id) {
        log.debug("getting CleanerServiceProcess instance with id: " + id);
        try {
            CleanerServiceProcess instance = (CleanerServiceProcess) getSession().get("com.autosite.db.CleanerServiceProcess", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerServiceProcess instance) {
        log.debug("finding CleanerServiceProcess instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerServiceProcess").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerServiceProcess instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerServiceProcess as model where model." + propertyName + "= ?";
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

    public List findByTicketId(Object ticketId) {
        return findByProperty(TICKET_ID, ticketId);
    }

    public List findByProcessUserId(Object processUserId) {
        return findByProperty(PROCESS_USER_ID, processUserId);
    }

    public List findByProcessType(Object processType) {
        return findByProperty(PROCESS_TYPE, processType);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findAll() {
        log.debug("finding all CleanerServiceProcess instances");
        try {
            String queryString = "from CleanerServiceProcess";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerServiceProcess merge(CleanerServiceProcess detachedInstance) {
        log.debug("merging CleanerServiceProcess instance");
        try {
            CleanerServiceProcess result = (CleanerServiceProcess) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerServiceProcess instance) {
        log.debug("attaching dirty CleanerServiceProcess instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerServiceProcess instance) {
        log.debug("attaching clean CleanerServiceProcess instance");
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
