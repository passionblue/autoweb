package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * PollAnswer entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.PollAnswer
 * @author MyEclipse Persistence Tools
 */

public class PollAnswerDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PollAnswerDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String POLL_ID = "pollId";
    public static final String ANSWER_NUM = "answerNum";
    public static final String TEXT = "text";
    public static final String IMAGE_URL = "imageUrl";
    public static final String IMAGE_ONLY = "imageOnly";
    public static final String OWN_ANSWER = "ownAnswer";

    public void save(PollAnswer transientInstance) {
        log.debug("saving PollAnswer instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PollAnswer persistentInstance) {
        log.debug("deleting PollAnswer instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PollAnswer findById(Long id) {
        log.debug("getting PollAnswer instance with id: " + id);
        try {
            PollAnswer instance = (PollAnswer) getSession().get("com.autosite.db.PollAnswer", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PollAnswer instance) {
        log.debug("finding PollAnswer instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PollAnswer").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PollAnswer instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PollAnswer as model where model." + propertyName + "= ?";
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

    public List findByAnswerNum(Object answerNum) {
        return findByProperty(ANSWER_NUM, answerNum);
    }

    public List findByText(Object text) {
        return findByProperty(TEXT, text);
    }

    public List findByImageUrl(Object imageUrl) {
        return findByProperty(IMAGE_URL, imageUrl);
    }

    public List findByImageOnly(Object imageOnly) {
        return findByProperty(IMAGE_ONLY, imageOnly);
    }

    public List findByOwnAnswer(Object ownAnswer) {
        return findByProperty(OWN_ANSWER, ownAnswer);
    }

    public List findAll() {
        log.debug("finding all PollAnswer instances");
        try {
            String queryString = "from PollAnswer";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PollAnswer merge(PollAnswer detachedInstance) {
        log.debug("merging PollAnswer instance");
        try {
            PollAnswer result = (PollAnswer) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PollAnswer instance) {
        log.debug("attaching dirty PollAnswer instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PollAnswer instance) {
        log.debug("attaching clean PollAnswer instance");
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
