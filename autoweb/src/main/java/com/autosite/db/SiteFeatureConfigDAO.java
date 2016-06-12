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
 * SiteFeatureConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SiteFeatureConfig
 * @author MyEclipse Persistence Tools
 */

public class SiteFeatureConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SiteFeatureConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_REGISTER_ENABLED = "userRegisterEnabled";
    public static final String EC_ENABLED = "ecEnabled";
    public static final String EMAIL_SUBS_ENABLED = "emailSubsEnabled";

    public void save(SiteFeatureConfig transientInstance) {
        log.debug("saving SiteFeatureConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SiteFeatureConfig persistentInstance) {
        log.debug("deleting SiteFeatureConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SiteFeatureConfig findById(Long id) {
        log.debug("getting SiteFeatureConfig instance with id: " + id);
        try {
            SiteFeatureConfig instance = (SiteFeatureConfig) getSession().get("com.autosite.db.SiteFeatureConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SiteFeatureConfig instance) {
        log.debug("finding SiteFeatureConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SiteFeatureConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SiteFeatureConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SiteFeatureConfig as model where model." + propertyName + "= ?";
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

    public List findByUserRegisterEnabled(Object userRegisterEnabled) {
        return findByProperty(USER_REGISTER_ENABLED, userRegisterEnabled);
    }

    public List findByEcEnabled(Object ecEnabled) {
        return findByProperty(EC_ENABLED, ecEnabled);
    }

    public List findByEmailSubsEnabled(Object emailSubsEnabled) {
        return findByProperty(EMAIL_SUBS_ENABLED, emailSubsEnabled);
    }

    public List findAll() {
        log.debug("finding all SiteFeatureConfig instances");
        try {
            String queryString = "from SiteFeatureConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SiteFeatureConfig merge(SiteFeatureConfig detachedInstance) {
        log.debug("merging SiteFeatureConfig instance");
        try {
            SiteFeatureConfig result = (SiteFeatureConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SiteFeatureConfig instance) {
        log.debug("attaching dirty SiteFeatureConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SiteFeatureConfig instance) {
        log.debug("attaching clean SiteFeatureConfig instance");
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
