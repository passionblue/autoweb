package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* DAO is generated by Eclipse. This is by AutoGen
 *
 */
public class SiteObjectDAO2  extends BaseHibernateDAO  {
    private static Logger log = LoggerFactory.getLogger(SiteObjectDAO2.class);

	public static final String ID = "id";
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

    public void save(SiteObject transientInstance) {
        log.debug("saving SiteObject instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SiteObject persistentInstance) {
        log.debug("deleting SiteObject instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SiteObject findById(Long id) {
        log.debug("getting SiteObject instance with id: " + id);
        try {
            SiteObject instance = (SiteObject) getSession().get("com.autosite.db.SiteObject", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SiteObject instance) {
        log.debug("finding SiteObject instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SiteObject").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SiteObject instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SiteObject as model where model." + propertyName + "= ?";
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
        log.debug("finding all SiteObject instances");
        try {
            String queryString = "from SiteObject";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SiteObject merge(SiteObject detachedInstance) {
        log.debug("merging SiteObject instance");
        try {
            SiteObject result = (SiteObject) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SiteObject instance) {
        log.debug("attaching dirty SiteObject instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SiteObject instance) {
        log.debug("attaching clean SiteObject instance");
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