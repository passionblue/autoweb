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
 * PageStaticConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.PageStaticConfig
 * @author MyEclipse Persistence Tools
 */

public class PageStaticConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PageStaticConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ALIAS = "pageAlias";
    public static final String PAGE_CSS = "pageCss";
    public static final String PAGE_SCRIPT = "pageScript";
    public static final String PAGE_CSS_IMPORTS = "pageCssImports";
    public static final String PAGE_SCRIPT_IMPORTS = "pageScriptImports";
    public static final String HIDE_MENU = "hideMenu";
    public static final String HIDE_MID = "hideMid";
    public static final String HIDE_AD = "hideAd";

    public void save(PageStaticConfig transientInstance) {
        log.debug("saving PageStaticConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PageStaticConfig persistentInstance) {
        log.debug("deleting PageStaticConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PageStaticConfig findById(Long id) {
        log.debug("getting PageStaticConfig instance with id: " + id);
        try {
            PageStaticConfig instance = (PageStaticConfig) getSession().get("com.autosite.db.PageStaticConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PageStaticConfig instance) {
        log.debug("finding PageStaticConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PageStaticConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PageStaticConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PageStaticConfig as model where model." + propertyName + "= ?";
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

    public List findByPageAlias(Object pageAlias) {
        return findByProperty(PAGE_ALIAS, pageAlias);
    }

    public List findByPageCss(Object pageCss) {
        return findByProperty(PAGE_CSS, pageCss);
    }

    public List findByPageScript(Object pageScript) {
        return findByProperty(PAGE_SCRIPT, pageScript);
    }

    public List findByPageCssImports(Object pageCssImports) {
        return findByProperty(PAGE_CSS_IMPORTS, pageCssImports);
    }

    public List findByPageScriptImports(Object pageScriptImports) {
        return findByProperty(PAGE_SCRIPT_IMPORTS, pageScriptImports);
    }

    public List findByHideMenu(Object hideMenu) {
        return findByProperty(HIDE_MENU, hideMenu);
    }

    public List findByHideMid(Object hideMid) {
        return findByProperty(HIDE_MID, hideMid);
    }

    public List findByHideAd(Object hideAd) {
        return findByProperty(HIDE_AD, hideAd);
    }

    public List findAll() {
        log.debug("finding all PageStaticConfig instances");
        try {
            String queryString = "from PageStaticConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PageStaticConfig merge(PageStaticConfig detachedInstance) {
        log.debug("merging PageStaticConfig instance");
        try {
            PageStaticConfig result = (PageStaticConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PageStaticConfig instance) {
        log.debug("attaching dirty PageStaticConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PageStaticConfig instance) {
        log.debug("attaching clean PageStaticConfig instance");
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
