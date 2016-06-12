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
 * A data access object (DAO) providing persistence and search support for Panel
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.autosite.db.Panel
 * @author MyEclipse Persistence Tools
 */

public class PanelDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PanelDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ID = "pageId";
    public static final String PAGE_ONLY = "pageOnly";
    public static final String PAGE_ONLY_GROUP = "pageOnlyGroup";
    public static final String COLUMN_NUM = "columnNum";
    public static final String CONTENT_ONLY = "contentOnly";
    public static final String PANEL_TYPE = "panelType";
    public static final String PANEL_SUB_TYPE = "panelSubType";
    public static final String PANEL_TITLE = "panelTitle";
    public static final String PANEL_HEIGHT = "panelHeight";
    public static final String HIDE = "hide";
    public static final String TOP_SPACE = "topSpace";
    public static final String BOTTOM_SPACE = "bottomSpace";
    public static final String LEFT_SPACE = "leftSpace";
    public static final String RIGHT_SPACE = "rightSpace";
    public static final String STYLE_STRING = "styleString";
    public static final String TITLE_STYLE_STRING = "titleStyleString";
    public static final String STYLE_STRING2 = "styleString2";
    public static final String STYLE_DEFAULT_CODE = "styleDefaultCode";
    public static final String ALIGN = "align";
    public static final String COLUMN_COUNT = "columnCount";
    public static final String PAGE_DISPLAY_SUMMARY = "pageDisplaySummary";
    public static final String SHOW_IN_PRINT = "showInPrint";
    public static final String SHOW_ONLY_PRINT = "showOnlyPrint";
    public static final String ADMIN_ONLY = "adminOnly";
    public static final String FEED_ID = "feedId";
    public static final String OPTION1 = "option1";
    public static final String OPTION2 = "option2";
    public static final String OPTION3 = "option3";

    public void save(Panel transientInstance) {
        log.debug("saving Panel instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Panel persistentInstance) {
        log.debug("deleting Panel instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Panel findById(Long id) {
        log.debug("getting Panel instance with id: " + id);
        try {
            Panel instance = (Panel) getSession().get("com.autosite.db.Panel", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Panel instance) {
        log.debug("finding Panel instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Panel").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Panel instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Panel as model where model." + propertyName + "= ?";
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

    public List findByPageOnly(Object pageOnly) {
        return findByProperty(PAGE_ONLY, pageOnly);
    }

    public List findByPageOnlyGroup(Object pageOnlyGroup) {
        return findByProperty(PAGE_ONLY_GROUP, pageOnlyGroup);
    }

    public List findByColumnNum(Object columnNum) {
        return findByProperty(COLUMN_NUM, columnNum);
    }

    public List findByContentOnly(Object contentOnly) {
        return findByProperty(CONTENT_ONLY, contentOnly);
    }

    public List findByPanelType(Object panelType) {
        return findByProperty(PANEL_TYPE, panelType);
    }

    public List findByPanelSubType(Object panelSubType) {
        return findByProperty(PANEL_SUB_TYPE, panelSubType);
    }

    public List findByPanelTitle(Object panelTitle) {
        return findByProperty(PANEL_TITLE, panelTitle);
    }

    public List findByPanelHeight(Object panelHeight) {
        return findByProperty(PANEL_HEIGHT, panelHeight);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findByTopSpace(Object topSpace) {
        return findByProperty(TOP_SPACE, topSpace);
    }

    public List findByBottomSpace(Object bottomSpace) {
        return findByProperty(BOTTOM_SPACE, bottomSpace);
    }

    public List findByLeftSpace(Object leftSpace) {
        return findByProperty(LEFT_SPACE, leftSpace);
    }

    public List findByRightSpace(Object rightSpace) {
        return findByProperty(RIGHT_SPACE, rightSpace);
    }

    public List findByStyleString(Object styleString) {
        return findByProperty(STYLE_STRING, styleString);
    }

    public List findByTitleStyleString(Object titleStyleString) {
        return findByProperty(TITLE_STYLE_STRING, titleStyleString);
    }

    public List findByStyleString2(Object styleString2) {
        return findByProperty(STYLE_STRING2, styleString2);
    }

    public List findByStyleDefaultCode(Object styleDefaultCode) {
        return findByProperty(STYLE_DEFAULT_CODE, styleDefaultCode);
    }

    public List findByAlign(Object align) {
        return findByProperty(ALIGN, align);
    }

    public List findByColumnCount(Object columnCount) {
        return findByProperty(COLUMN_COUNT, columnCount);
    }

    public List findByPageDisplaySummary(Object pageDisplaySummary) {
        return findByProperty(PAGE_DISPLAY_SUMMARY, pageDisplaySummary);
    }

    public List findByShowInPrint(Object showInPrint) {
        return findByProperty(SHOW_IN_PRINT, showInPrint);
    }

    public List findByShowOnlyPrint(Object showOnlyPrint) {
        return findByProperty(SHOW_ONLY_PRINT, showOnlyPrint);
    }

    public List findByAdminOnly(Object adminOnly) {
        return findByProperty(ADMIN_ONLY, adminOnly);
    }

    public List findByFeedId(Object feedId) {
        return findByProperty(FEED_ID, feedId);
    }

    public List findByOption1(Object option1) {
        return findByProperty(OPTION1, option1);
    }

    public List findByOption2(Object option2) {
        return findByProperty(OPTION2, option2);
    }

    public List findByOption3(Object option3) {
        return findByProperty(OPTION3, option3);
    }

    public List findAll() {
        log.debug("finding all Panel instances");
        try {
            String queryString = "from Panel";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Panel merge(Panel detachedInstance) {
        log.debug("merging Panel instance");
        try {
            Panel result = (Panel) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Panel instance) {
        log.debug("attaching dirty Panel instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Panel instance) {
        log.debug("attaching clean Panel instance");
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
