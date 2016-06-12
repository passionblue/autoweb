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
 * A data access object (DAO) providing persistence and search support for Page
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.autosite.db.Page
 * @author MyEclipse Persistence Tools
 */

public class PageDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PageDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String MENU_PANEL_ID = "menuPanelId";
    public static final String PARENT_ID = "parentId";
    public static final String PAGE_NAME = "pageName";
    public static final String PAGE_MENU_TITLE = "pageMenuTitle";
    public static final String HIDE = "hide";
    public static final String SITE_URL = "siteUrl";
    public static final String PAGE_COL_COUNT = "pageColCount";
    public static final String PAGE_KEYWORDS = "pageKeywords";
    public static final String PAGE_VIEW_TYPE = "pageViewType";
    public static final String UNDERLYING_PAGE = "underlyingPage";
    public static final String HEADER_PAGE = "headerPage";
    public static final String SHOW_PAGE_EXCLUSIVE_ONLY = "showPageExclusiveOnly";
    public static final String ALT1 = "alt1";
    public static final String ALT2 = "alt2";
    public static final String ALT3 = "alt3";

    public void save(Page transientInstance) {
        log.debug("saving Page instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Page persistentInstance) {
        log.debug("deleting Page instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Page findById(Long id) {
        log.debug("getting Page instance with id: " + id);
        try {
            Page instance = (Page) getSession().get("com.autosite.db.Page", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Page instance) {
        log.debug("finding Page instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Page").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Page instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Page as model where model." + propertyName + "= ?";
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

    public List findByMenuPanelId(Object menuPanelId) {
        return findByProperty(MENU_PANEL_ID, menuPanelId);
    }

    public List findByParentId(Object parentId) {
        return findByProperty(PARENT_ID, parentId);
    }

    public List findByPageName(Object pageName) {
        return findByProperty(PAGE_NAME, pageName);
    }

    public List findByPageMenuTitle(Object pageMenuTitle) {
        return findByProperty(PAGE_MENU_TITLE, pageMenuTitle);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findBySiteUrl(Object siteUrl) {
        return findByProperty(SITE_URL, siteUrl);
    }

    public List findByPageColCount(Object pageColCount) {
        return findByProperty(PAGE_COL_COUNT, pageColCount);
    }

    public List findByPageKeywords(Object pageKeywords) {
        return findByProperty(PAGE_KEYWORDS, pageKeywords);
    }

    public List findByPageViewType(Object pageViewType) {
        return findByProperty(PAGE_VIEW_TYPE, pageViewType);
    }

    public List findByUnderlyingPage(Object underlyingPage) {
        return findByProperty(UNDERLYING_PAGE, underlyingPage);
    }

    public List findByHeaderPage(Object headerPage) {
        return findByProperty(HEADER_PAGE, headerPage);
    }

    public List findByShowPageExclusiveOnly(Object showPageExclusiveOnly) {
        return findByProperty(SHOW_PAGE_EXCLUSIVE_ONLY, showPageExclusiveOnly);
    }

    public List findByAlt1(Object alt1) {
        return findByProperty(ALT1, alt1);
    }

    public List findByAlt2(Object alt2) {
        return findByProperty(ALT2, alt2);
    }

    public List findByAlt3(Object alt3) {
        return findByProperty(ALT3, alt3);
    }

    public List findAll() {
        log.debug("finding all Page instances");
        try {
            String queryString = "from Page";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Page merge(Page detachedInstance) {
        log.debug("merging Page instance");
        try {
            Page result = (Page) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Page instance) {
        log.debug("attaching dirty Page instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Page instance) {
        log.debug("attaching clean Page instance");
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
