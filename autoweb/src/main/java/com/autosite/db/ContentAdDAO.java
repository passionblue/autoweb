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
 * ContentAd entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.ContentAd
 * @author MyEclipse Persistence Tools
 */

public class ContentAdDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ContentAdDAO.class);
    // property constants
    public static final String CONTENT_ID = "contentId";
    public static final String SITE_ID = "siteId";
    public static final String POSITION_CODE = "positionCode";
    public static final String AD_CONTENT = "adContent";
    public static final String HIDE = "hide";

    public void save(ContentAd transientInstance) {
        log.debug("saving ContentAd instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ContentAd persistentInstance) {
        log.debug("deleting ContentAd instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ContentAd findById(Long id) {
        log.debug("getting ContentAd instance with id: " + id);
        try {
            ContentAd instance = (ContentAd) getSession().get("com.autosite.db.ContentAd", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ContentAd instance) {
        log.debug("finding ContentAd instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ContentAd").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ContentAd instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ContentAd as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByContentId(Object contentId) {
        return findByProperty(CONTENT_ID, contentId);
    }

    public List findBySiteId(Object siteId) {
        return findByProperty(SITE_ID, siteId);
    }

    public List findByPositionCode(Object positionCode) {
        return findByProperty(POSITION_CODE, positionCode);
    }

    public List findByAdContent(Object adContent) {
        return findByProperty(AD_CONTENT, adContent);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findAll() {
        log.debug("finding all ContentAd instances");
        try {
            String queryString = "from ContentAd";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ContentAd merge(ContentAd detachedInstance) {
        log.debug("merging ContentAd instance");
        try {
            ContentAd result = (ContentAd) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ContentAd instance) {
        log.debug("attaching dirty ContentAd instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ContentAd instance) {
        log.debug("attaching clean ContentAd instance");
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
