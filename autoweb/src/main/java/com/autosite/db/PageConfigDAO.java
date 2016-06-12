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
 * PageConfig entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.PageConfig
 * @author MyEclipse Persistence Tools
 */

public class PageConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PageConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ID = "pageId";
    public static final String SORT_TYPE = "sortType";
    public static final String ARRANGE_TYPE = "arrangeType";
    public static final String PAGE_CSS = "pageCss";
    public static final String PAGE_SCRIPT = "pageScript";
    public static final String PAGE_CSS_IMPORTS = "pageCssImports";
    public static final String PAGE_SCRIPT_IMPORTS = "pageScriptImports";
    public static final String HIDE_MENU = "hideMenu";
    public static final String HIDE_MID = "hideMid";
    public static final String HIDE_AD = "hideAd";
    public static final String STYLE_ID = "styleId";
    public static final String CONTENT_STYLE_SET_ID = "contentStyleSetId";
    public static final String HEADER_META = "headerMeta";
    public static final String HEADER_LINK = "headerLink";

    public void save(PageConfig transientInstance) {
        log.debug("saving PageConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PageConfig persistentInstance) {
        log.debug("deleting PageConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PageConfig findById(Long id) {
        log.debug("getting PageConfig instance with id: " + id);
        try {
            PageConfig instance = (PageConfig) getSession().get("com.autosite.db.PageConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PageConfig instance) {
        log.debug("finding PageConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PageConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PageConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PageConfig as model where model." + propertyName + "= ?";
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

    public List findByPageId(Object pageId) {
        return findByProperty(PAGE_ID, pageId);
    }

    public List findBySortType(Object sortType) {
        return findByProperty(SORT_TYPE, sortType);
    }

    public List findByArrangeType(Object arrangeType) {
        return findByProperty(ARRANGE_TYPE, arrangeType);
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

    public List findByStyleId(Object styleId) {
        return findByProperty(STYLE_ID, styleId);
    }

    public List findByContentStyleSetId(Object contentStyleSetId) {
        return findByProperty(CONTENT_STYLE_SET_ID, contentStyleSetId);
    }

    public List findByHeaderMeta(Object headerMeta) {
        return findByProperty(HEADER_META, headerMeta);
    }

    public List findByHeaderLink(Object headerLink) {
        return findByProperty(HEADER_LINK, headerLink);
    }

    public List findAll() {
        log.debug("finding all PageConfig instances");
        try {
            String queryString = "from PageConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PageConfig merge(PageConfig detachedInstance) {
        log.debug("merging PageConfig instance");
        try {
            PageConfig result = (PageConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PageConfig instance) {
        log.debug("attaching dirty PageConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PageConfig instance) {
        log.debug("attaching clean PageConfig instance");
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
