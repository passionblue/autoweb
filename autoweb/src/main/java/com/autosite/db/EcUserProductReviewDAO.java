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
 * EcUserProductReview entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcUserProductReview
 * @author MyEclipse Persistence Tools
 */

public class EcUserProductReviewDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcUserProductReviewDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PRODUCT_ID = "productId";
    public static final String USER_ID = "userId";
    public static final String RATE = "rate";
    public static final String REVIEW = "review";
    public static final String TRACK_BACK = "trackBack";
    public static final String NUM_VOTE_YES = "numVoteYes";
    public static final String NUM_VOTE_NO = "numVoteNo";

    public void save(EcUserProductReview transientInstance) {
        log.debug("saving EcUserProductReview instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcUserProductReview persistentInstance) {
        log.debug("deleting EcUserProductReview instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcUserProductReview findById(Long id) {
        log.debug("getting EcUserProductReview instance with id: " + id);
        try {
            EcUserProductReview instance = (EcUserProductReview) getSession().get("com.autosite.db.EcUserProductReview", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcUserProductReview instance) {
        log.debug("finding EcUserProductReview instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcUserProductReview").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcUserProductReview instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcUserProductReview as model where model." + propertyName + "= ?";
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

    public List findByProductId(Object productId) {
        return findByProperty(PRODUCT_ID, productId);
    }

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByRate(Object rate) {
        return findByProperty(RATE, rate);
    }

    public List findByReview(Object review) {
        return findByProperty(REVIEW, review);
    }

    public List findByTrackBack(Object trackBack) {
        return findByProperty(TRACK_BACK, trackBack);
    }

    public List findByNumVoteYes(Object numVoteYes) {
        return findByProperty(NUM_VOTE_YES, numVoteYes);
    }

    public List findByNumVoteNo(Object numVoteNo) {
        return findByProperty(NUM_VOTE_NO, numVoteNo);
    }

    public List findAll() {
        log.debug("finding all EcUserProductReview instances");
        try {
            String queryString = "from EcUserProductReview";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcUserProductReview merge(EcUserProductReview detachedInstance) {
        log.debug("merging EcUserProductReview instance");
        try {
            EcUserProductReview result = (EcUserProductReview) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcUserProductReview instance) {
        log.debug("attaching dirty EcUserProductReview instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcUserProductReview instance) {
        log.debug("attaching clean EcUserProductReview instance");
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
