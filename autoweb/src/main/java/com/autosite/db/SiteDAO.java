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
 * A data access object (DAO) providing persistence and search support for Site
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.autosite.db.Site
 * @author MyEclipse Persistence Tools
 */

public class SiteDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(SiteDAO.class);
    // property constants
    public static final String SITE_URL = "siteUrl";
    public static final String ACCOUNT_ID = "accountId";
    public static final String SITE_GROUP = "siteGroup";
    public static final String REGISTERED = "registered";
    public static final String ON_SALE = "onSale";
    public static final String SUPER_ADMIN_ENABLE = "superAdminEnable";
    public static final String SITE_REGISTER_ENABLE = "siteRegisterEnable";
    public static final String SUBDOMAIN_ENABLE = "subdomainEnable";
    public static final String SITE_REGISTER_SITE = "siteRegisterSite";
    public static final String BASE_SITE_ID = "baseSiteId";
    public static final String SUBSITE = "subsite";
    public static final String DISABLED = "disabled";

    public void save(Site transientInstance) {
        log.debug("saving Site instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Site persistentInstance) {
        log.debug("deleting Site instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Site findById(Long id) {
        log.debug("getting Site instance with id: " + id);
        try {
            Site instance = (Site) getSession().get("com.autosite.db.Site", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Site instance) {
        log.debug("finding Site instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Site").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Site instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Site as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySiteUrl(Object siteUrl) {
        return findByProperty(SITE_URL, siteUrl);
    }

    public List findByAccountId(Object accountId) {
        return findByProperty(ACCOUNT_ID, accountId);
    }

    public List findBySiteGroup(Object siteGroup) {
        return findByProperty(SITE_GROUP, siteGroup);
    }

    public List findByRegistered(Object registered) {
        return findByProperty(REGISTERED, registered);
    }

    public List findByOnSale(Object onSale) {
        return findByProperty(ON_SALE, onSale);
    }

    public List findBySuperAdminEnable(Object superAdminEnable) {
        return findByProperty(SUPER_ADMIN_ENABLE, superAdminEnable);
    }

    public List findBySiteRegisterEnable(Object siteRegisterEnable) {
        return findByProperty(SITE_REGISTER_ENABLE, siteRegisterEnable);
    }

    public List findBySubdomainEnable(Object subdomainEnable) {
        return findByProperty(SUBDOMAIN_ENABLE, subdomainEnable);
    }

    public List findBySiteRegisterSite(Object siteRegisterSite) {
        return findByProperty(SITE_REGISTER_SITE, siteRegisterSite);
    }

    public List findByBaseSiteId(Object baseSiteId) {
        return findByProperty(BASE_SITE_ID, baseSiteId);
    }

    public List findBySubsite(Object subsite) {
        return findByProperty(SUBSITE, subsite);
    }

    public List findByDisabled(Object disabled) {
        return findByProperty(DISABLED, disabled);
    }

    public List findAll() {
        log.debug("finding all Site instances");
        try {
            String queryString = "from Site";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Site merge(Site detachedInstance) {
        log.debug("merging Site instance");
        try {
            Site result = (Site) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Site instance) {
        log.debug("attaching dirty Site instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Site instance) {
        log.debug("attaching clean Site instance");
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
