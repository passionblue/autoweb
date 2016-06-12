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
 * DevNote entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.DevNote
 * @author MyEclipse Persistence Tools
 */

public class DevNoteDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(DevNoteDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String NOTE_TYPE = "noteType";
    public static final String COMPLETED = "completed";
    public static final String CATEGORY = "category";
    public static final String SUBJECT = "subject";
    public static final String NOTE = "note";
    public static final String TAGS = "tags";

    public void save(DevNote transientInstance) {
        log.debug("saving DevNote instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DevNote persistentInstance) {
        log.debug("deleting DevNote instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public DevNote findById(Long id) {
        log.debug("getting DevNote instance with id: " + id);
        try {
            DevNote instance = (DevNote) getSession().get("com.autosite.db.DevNote", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(DevNote instance) {
        log.debug("finding DevNote instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.DevNote").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding DevNote instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from DevNote as model where model." + propertyName + "= ?";
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

    public List findByNoteType(Object noteType) {
        return findByProperty(NOTE_TYPE, noteType);
    }

    public List findByCompleted(Object completed) {
        return findByProperty(COMPLETED, completed);
    }

    public List findByCategory(Object category) {
        return findByProperty(CATEGORY, category);
    }

    public List findBySubject(Object subject) {
        return findByProperty(SUBJECT, subject);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByTags(Object tags) {
        return findByProperty(TAGS, tags);
    }

    public List findAll() {
        log.debug("finding all DevNote instances");
        try {
            String queryString = "from DevNote";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public DevNote merge(DevNote detachedInstance) {
        log.debug("merging DevNote instance");
        try {
            DevNote result = (DevNote) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DevNote instance) {
        log.debug("attaching dirty DevNote instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(DevNote instance) {
        log.debug("attaching clean DevNote instance");
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
