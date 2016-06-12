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
 * Page entity. @author MyEclipse Persistence Tools
 */

public class PollConfigDAO  extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(PollConfigDAO.class);

    private static final String ID = "id";
    private static final String SITE_ID = "siteId";
    private static final String POLL_ID = "pollId";
    private static final String IMAGE_THUMB_HEIGHT = "imageThumbHeight";
    private static final String IMAGE_THUMB_WIDTH = "imageThumbWidth";
    private static final String IMAGE_ALIGN_VERTICAL = "imageAlignVertical";
    private static final String BACKGROUND = "background";
    private static final String TIME_CREATED = "timeCreated";
    private static final String TIME_UPDATED = "timeUpdated";

    public void save(PollConfig transientInstance) {
        log.debug("saving PollConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PollConfig persistentInstance) {
        log.debug("deleting PollConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PollConfig findById(Long id) {
        log.debug("getting PollConfig instance with id: " + id);
        try {
            PollConfig instance = (PollConfig) getSession().get("com.autosite.db.PollConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PollConfig instance) {
        log.debug("finding PollConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PollConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PollConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PollConfig as model where model." + propertyName + "= ?";
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
    public List findByPollId(Object pollId) {
        return findByProperty(POLL_ID, pollId);
    }
    public List findByImageThumbHeight(Object imageThumbHeight) {
        return findByProperty(IMAGE_THUMB_HEIGHT, imageThumbHeight);
    }
    public List findByImageThumbWidth(Object imageThumbWidth) {
        return findByProperty(IMAGE_THUMB_WIDTH, imageThumbWidth);
    }
    public List findByImageAlignVertical(Object imageAlignVertical) {
        return findByProperty(IMAGE_ALIGN_VERTICAL, imageAlignVertical);
    }
    public List findByBackground(Object background) {
        return findByProperty(BACKGROUND, background);
    }

    public List findAll() {
        log.debug("finding all PollConfig instances");
        try {
            String queryString = "from PollConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PollConfig merge(PollConfig detachedInstance) {
        log.debug("merging PollConfig instance");
        try {
            PollConfig result = (PollConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PollConfig instance) {
        log.debug("attaching dirty PollConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PollConfig instance) {
        log.debug("attaching clean PollConfig instance");
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