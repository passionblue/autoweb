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
 * RequestHistory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.RequestHistory
 * @author MyEclipse Persistence Tools
 */
public class RequestHistoryDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(RequestHistoryDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String FORWARD_SITE_ID = "forwardSiteId";
    public static final String IS_DROPPED = "isDropped";
    public static final String IS_PAGELESS = "isPageless";
    public static final String IS_LOGIN = "isLogin";
    public static final String IS_AJAX = "isAjax";
    public static final String IS_ROBOT = "isRobot";
    public static final String USERID = "userid";
    public static final String USER_AGENT = "userAgent";
    public static final String REFER = "refer";
    public static final String ROBOT = "robot";
    public static final String REMOTE_IP = "remoteIp";
    public static final String SITE_URL = "siteUrl";
    public static final String URI = "uri";
    public static final String QUERY = "query";
    public static final String RPCI = "rpci";
    public static final String SESSION_ID = "sessionId";

    public void save(RequestHistory transientInstance) {
        log.debug("saving RequestHistory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(RequestHistory persistentInstance) {
        log.debug("deleting RequestHistory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public RequestHistory findById(Long id) {
        log.debug("getting RequestHistory instance with id: " + id);
        try {
            RequestHistory instance = (RequestHistory) getSession().get("com.autosite.db.RequestHistory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(RequestHistory instance) {
        log.debug("finding RequestHistory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.RequestHistory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding RequestHistory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from RequestHistory as model where model." + propertyName + "= ?";
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

    public List findByForwardSiteId(Object forwardSiteId) {
        return findByProperty(FORWARD_SITE_ID, forwardSiteId);
    }

    public List findByIsDropped(Object isDropped) {
        return findByProperty(IS_DROPPED, isDropped);
    }

    public List findByIsPageless(Object isPageless) {
        return findByProperty(IS_PAGELESS, isPageless);
    }

    public List findByIsLogin(Object isLogin) {
        return findByProperty(IS_LOGIN, isLogin);
    }

    public List findByIsAjax(Object isAjax) {
        return findByProperty(IS_AJAX, isAjax);
    }

    public List findByIsRobot(Object isRobot) {
        return findByProperty(IS_ROBOT, isRobot);
    }

    public List findByUserid(Object userid) {
        return findByProperty(USERID, userid);
    }

    public List findByUserAgent(Object userAgent) {
        return findByProperty(USER_AGENT, userAgent);
    }

    public List findByRefer(Object refer) {
        return findByProperty(REFER, refer);
    }

    public List findByRobot(Object robot) {
        return findByProperty(ROBOT, robot);
    }

    public List findByRemoteIp(Object remoteIp) {
        return findByProperty(REMOTE_IP, remoteIp);
    }

    public List findBySiteUrl(Object siteUrl) {
        return findByProperty(SITE_URL, siteUrl);
    }

    public List findByUri(Object uri) {
        return findByProperty(URI, uri);
    }

    public List findByQuery(Object query) {
        return findByProperty(QUERY, query);
    }

    public List findByRpci(Object rpci) {
        return findByProperty(RPCI, rpci);
    }

    public List findBySessionId(Object sessionId) {
        return findByProperty(SESSION_ID, sessionId);
    }

    public List findAll() {
        log.debug("finding all RequestHistory instances");
        try {
            String queryString = "from RequestHistory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public RequestHistory merge(RequestHistory detachedInstance) {
        log.debug("merging RequestHistory instance");
        try {
            RequestHistory result = (RequestHistory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(RequestHistory instance) {
        log.debug("attaching dirty RequestHistory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(RequestHistory instance) {
        log.debug("attaching clean RequestHistory instance");
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
