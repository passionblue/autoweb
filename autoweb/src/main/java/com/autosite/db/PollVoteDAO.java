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
 * PollVote entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.PollVote
 * @author MyEclipse Persistence Tools
 */

public class PollVoteDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PollVoteDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String POLL_ID = "pollId";
    public static final String USER_ID = "userId";
    public static final String ANSWER = "answer";
    public static final String MULTIPLE_ANSWER = "multipleAnswer";
    public static final String BY_GUEST = "byGuest";
    public static final String IP_ADDRESS = "ipAddress";
    public static final String PCID = "pcid";
    public static final String DUP_CHECK_KEY = "dupCheckKey";
    public static final String NOTE = "note";
    public static final String OWN_ANSWER = "ownAnswer";

    public void save(PollVote transientInstance) {
        log.debug("saving PollVote instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PollVote persistentInstance) {
        log.debug("deleting PollVote instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PollVote findById(Long id) {
        log.debug("getting PollVote instance with id: " + id);
        try {
            PollVote instance = (PollVote) getSession().get("com.autosite.db.PollVote", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PollVote instance) {
        log.debug("finding PollVote instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PollVote").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PollVote instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PollVote as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByAnswer(Object answer) {
        return findByProperty(ANSWER, answer);
    }

    public List findByMultipleAnswer(Object multipleAnswer) {
        return findByProperty(MULTIPLE_ANSWER, multipleAnswer);
    }

    public List findByByGuest(Object byGuest) {
        return findByProperty(BY_GUEST, byGuest);
    }

    public List findByIpAddress(Object ipAddress) {
        return findByProperty(IP_ADDRESS, ipAddress);
    }

    public List findByPcid(Object pcid) {
        return findByProperty(PCID, pcid);
    }

    public List findByDupCheckKey(Object dupCheckKey) {
        return findByProperty(DUP_CHECK_KEY, dupCheckKey);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByOwnAnswer(Object ownAnswer) {
        return findByProperty(OWN_ANSWER, ownAnswer);
    }

    public List findAll() {
        log.debug("finding all PollVote instances");
        try {
            String queryString = "from PollVote";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PollVote merge(PollVote detachedInstance) {
        log.debug("merging PollVote instance");
        try {
            PollVote result = (PollVote) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PollVote instance) {
        log.debug("attaching dirty PollVote instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PollVote instance) {
        log.debug("attaching clean PollVote instance");
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
