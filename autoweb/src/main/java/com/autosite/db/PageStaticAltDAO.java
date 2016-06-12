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
 * PageStaticAlt entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.PageStaticAlt
 * @author MyEclipse Persistence Tools
 */

public class PageStaticAltDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PageStaticAltDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ALIAS = "pageAlias";
    public static final String PAGE_URL = "pageUrl";
    public static final String MENU_PAGE = "menuPage";
    public static final String ERROR_PAGE = "errorPage";
    public static final String LOGIN_REQUIRED = "loginRequired";
    public static final String VIEW_PROC = "viewProc";
    public static final String DYNAMIC_CONTENT = "dynamicContent";
    public static final String HIDE_MENU = "hideMenu";
    public static final String HIDE_MID = "hideMid";
    public static final String HIDE_AD = "hideAd";

    public void save(PageStaticAlt transientInstance) {
        log.debug("saving PageStaticAlt instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PageStaticAlt persistentInstance) {
        log.debug("deleting PageStaticAlt instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PageStaticAlt findById(Long id) {
        log.debug("getting PageStaticAlt instance with id: " + id);
        try {
            PageStaticAlt instance = (PageStaticAlt) getSession().get("com.autosite.db.PageStaticAlt", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PageStaticAlt instance) {
        log.debug("finding PageStaticAlt instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PageStaticAlt").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PageStaticAlt instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PageStaticAlt as model where model." + propertyName + "= ?";
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

    public List findByPageUrl(Object pageUrl) {
        return findByProperty(PAGE_URL, pageUrl);
    }

    public List findByMenuPage(Object menuPage) {
        return findByProperty(MENU_PAGE, menuPage);
    }

    public List findByErrorPage(Object errorPage) {
        return findByProperty(ERROR_PAGE, errorPage);
    }

    public List findByLoginRequired(Object loginRequired) {
        return findByProperty(LOGIN_REQUIRED, loginRequired);
    }

    public List findByViewProc(Object viewProc) {
        return findByProperty(VIEW_PROC, viewProc);
    }

    public List findByDynamicContent(Object dynamicContent) {
        return findByProperty(DYNAMIC_CONTENT, dynamicContent);
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
        log.debug("finding all PageStaticAlt instances");
        try {
            String queryString = "from PageStaticAlt";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PageStaticAlt merge(PageStaticAlt detachedInstance) {
        log.debug("merging PageStaticAlt instance");
        try {
            PageStaticAlt result = (PageStaticAlt) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PageStaticAlt instance) {
        log.debug("attaching dirty PageStaticAlt instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PageStaticAlt instance) {
        log.debug("attaching clean PageStaticAlt instance");
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
