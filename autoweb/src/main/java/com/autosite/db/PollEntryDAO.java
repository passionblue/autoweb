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
 * PollEntry entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.PollEntry
 * @author MyEclipse Persistence Tools
 */

public class PollEntryDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PollEntryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String POLL_ID = "pollId";
    public static final String INDEX = "index";
    public static final String TEXT = "text";
    public static final String IMAGE_URL = "imageUrl";

    public void save(PollEntry transientInstance) {
        log.debug("saving PollEntry instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PollEntry persistentInstance) {
        log.debug("deleting PollEntry instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PollEntry findById(Long id) {
        log.debug("getting PollEntry instance with id: " + id);
        try {
            PollEntry instance = (PollEntry) getSession().get("com.autosite.db.PollEntry", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PollEntry instance) {
        log.debug("finding PollEntry instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PollEntry").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PollEntry instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PollEntry as model where model." + propertyName + "= ?";
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

    public List findByPollId(Object pollId) {
        return findByProperty(POLL_ID, pollId);
    }

    public List findByIndex(Object index) {
        return findByProperty(INDEX, index);
    }

    public List findByText(Object text) {
        return findByProperty(TEXT, text);
    }

    public List findByImageUrl(Object imageUrl) {
        return findByProperty(IMAGE_URL, imageUrl);
    }

    public List findAll() {
        log.debug("finding all PollEntry instances");
        try {
            String queryString = "from PollEntry";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PollEntry merge(PollEntry detachedInstance) {
        log.debug("merging PollEntry instance");
        try {
            PollEntry result = (PollEntry) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PollEntry instance) {
        log.debug("attaching dirty PollEntry instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PollEntry instance) {
        log.debug("attaching clean PollEntry instance");
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
