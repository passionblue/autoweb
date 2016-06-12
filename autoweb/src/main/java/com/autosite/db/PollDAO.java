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
 * A data access object (DAO) providing persistence and search support for Poll
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.autosite.db.Poll
 * @author MyEclipse Persistence Tools
 */

public class PollDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PollDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String SERIAL = "serial";
    public static final String TYPE = "type";
    public static final String CATEGORY = "category";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String QUESTION = "question";
    public static final String TAGS = "tags";
    public static final String PUBLISHED = "published";
    public static final String HIDE = "hide";
    public static final String DISABLE = "disable";
    public static final String ALLOW_MULTIPLE = "allowMultiple";
    public static final String ALLOW_OWN_ANSWER = "allowOwnAnswer";
    public static final String RANDOM_ANSWER = "randomAnswer";
    public static final String HIDE_COMMENTS = "hideComments";
    public static final String HIDE_RESULTS = "hideResults";
    public static final String HIDE_HOME_LINK = "hideHomeLink";
    public static final String SHOW_SPONSOR = "showSponsor";
    public static final String USE_COOKIE_FOR_DUP = "useCookieForDup";
    public static final String REPEAT_EVERY_DAY = "repeatEveryDay";
    public static final String MAX_REPEAT_VOTE = "maxRepeatVote";
    public static final String NUM_DAYS_OPEN = "numDaysOpen";

    public void save(Poll transientInstance) {
        log.debug("saving Poll instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Poll persistentInstance) {
        log.debug("deleting Poll instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Poll findById(Long id) {
        log.debug("getting Poll instance with id: " + id);
        try {
            Poll instance = (Poll) getSession().get("com.autosite.db.Poll", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Poll instance) {
        log.debug("finding Poll instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Poll").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Poll instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Poll as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findBySerial(Object serial) {
        return findByProperty(SERIAL, serial);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findByCategory(Object category) {
        return findByProperty(CATEGORY, category);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByQuestion(Object question) {
        return findByProperty(QUESTION, question);
    }

    public List findByTags(Object tags) {
        return findByProperty(TAGS, tags);
    }

    public List findByPublished(Object published) {
        return findByProperty(PUBLISHED, published);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findByDisable(Object disable) {
        return findByProperty(DISABLE, disable);
    }

    public List findByAllowMultiple(Object allowMultiple) {
        return findByProperty(ALLOW_MULTIPLE, allowMultiple);
    }

    public List findByAllowOwnAnswer(Object allowOwnAnswer) {
        return findByProperty(ALLOW_OWN_ANSWER, allowOwnAnswer);
    }

    public List findByRandomAnswer(Object randomAnswer) {
        return findByProperty(RANDOM_ANSWER, randomAnswer);
    }

    public List findByHideComments(Object hideComments) {
        return findByProperty(HIDE_COMMENTS, hideComments);
    }

    public List findByHideResults(Object hideResults) {
        return findByProperty(HIDE_RESULTS, hideResults);
    }

    public List findByHideHomeLink(Object hideHomeLink) {
        return findByProperty(HIDE_HOME_LINK, hideHomeLink);
    }

    public List findByShowSponsor(Object showSponsor) {
        return findByProperty(SHOW_SPONSOR, showSponsor);
    }

    public List findByUseCookieForDup(Object useCookieForDup) {
        return findByProperty(USE_COOKIE_FOR_DUP, useCookieForDup);
    }

    public List findByRepeatEveryDay(Object repeatEveryDay) {
        return findByProperty(REPEAT_EVERY_DAY, repeatEveryDay);
    }

    public List findByMaxRepeatVote(Object maxRepeatVote) {
        return findByProperty(MAX_REPEAT_VOTE, maxRepeatVote);
    }

    public List findByNumDaysOpen(Object numDaysOpen) {
        return findByProperty(NUM_DAYS_OPEN, numDaysOpen);
    }

    public List findAll() {
        log.debug("finding all Poll instances");
        try {
            String queryString = "from Poll";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Poll merge(Poll detachedInstance) {
        log.debug("merging Poll instance");
        try {
            Poll result = (Poll) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Poll instance) {
        log.debug("attaching dirty Poll instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Poll instance) {
        log.debug("attaching clean Poll instance");
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
