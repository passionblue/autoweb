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
 * StyleTheme entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.StyleTheme
 * @author MyEclipse Persistence Tools
 */

public class StyleThemeDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(StyleThemeDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String TITLE = "title";
    public static final String BODY_WIDTH = "bodyWidth";
    public static final String BODY_ALIGN = "bodyAlign";
    public static final String BODY_BG_COLOR = "bodyBgColor";
    public static final String BODY_BG_IMAGE = "bodyBgImage";
    public static final String BODY_BG_ATTACH = "bodyBgAttach";
    public static final String BODY_BG_REPEAT = "bodyBgRepeat";
    public static final String BODY_BG_POSITION = "bodyBgPosition";
    public static final String CONTENT_BG_COLOR = "contentBgColor";
    public static final String USE_ABSOLUTE = "useAbsolute";
    public static final String ABSOLUTE_TOP = "absoluteTop";
    public static final String ABSOLUTE_LEFT = "absoluteLeft";
    public static final String ABSOLUTE_RIGHT = "absoluteRight";
    public static final String ABSOLUTE_BOTTOM = "absoluteBottom";
    public static final String PANEL_STYLE_ID = "panelStyleId";
    public static final String PANEL_DATA_STYLE_ID = "panelDataStyleId";
    public static final String PANEL_LINK_STYLE_ID = "panelLinkStyleId";
    public static final String PANEL_TITLE_STYLE_ID = "panelTitleStyleId";
    public static final String MENU_STYLE_ID = "menuStyleId";
    public static final String MENU_LINK_STYLE_ID = "menuLinkStyleId";
    public static final String HEADER_MENU_STYLE_ID = "headerMenuStyleId";
    public static final String HEADER_MENU_LINK_STYLE_ID = "headerMenuLinkStyleId";
    public static final String LIST_FRAME_STYLE_ID = "listFrameStyleId";
    public static final String LIST_SUBJECT_STYLE_ID = "listSubjectStyleId";
    public static final String LIST_DATA_STYLE_ID = "listDataStyleId";
    public static final String SUBJECT_STYLE_ID = "subjectStyleId";
    public static final String DATA_STYLE_ID = "dataStyleId";
    public static final String SINGLE_FRAME_STYLE_ID = "singleFrameStyleId";
    public static final String SINGLE_SUBJECT_STYLE_ID = "singleSubjectStyleId";
    public static final String SINGLE_DATA_STYLE_ID = "singleDataStyleId";
    public static final String CONTENT_PANEL_STYLE_ID = "contentPanelStyleId";
    public static final String CONTENT_PANEL_TITLE_STYLE_ID = "contentPanelTitleStyleId";
    public static final String GLOBAL = "global";
    public static final String DISABLE = "disable";

    public void save(StyleTheme transientInstance) {
        log.debug("saving StyleTheme instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StyleTheme persistentInstance) {
        log.debug("deleting StyleTheme instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StyleTheme findById(Long id) {
        log.debug("getting StyleTheme instance with id: " + id);
        try {
            StyleTheme instance = (StyleTheme) getSession().get("com.autosite.db.StyleTheme", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StyleTheme instance) {
        log.debug("finding StyleTheme instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.StyleTheme").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StyleTheme instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StyleTheme as model where model." + propertyName + "= ?";
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

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByBodyWidth(Object bodyWidth) {
        return findByProperty(BODY_WIDTH, bodyWidth);
    }

    public List findByBodyAlign(Object bodyAlign) {
        return findByProperty(BODY_ALIGN, bodyAlign);
    }

    public List findByBodyBgColor(Object bodyBgColor) {
        return findByProperty(BODY_BG_COLOR, bodyBgColor);
    }

    public List findByBodyBgImage(Object bodyBgImage) {
        return findByProperty(BODY_BG_IMAGE, bodyBgImage);
    }

    public List findByBodyBgAttach(Object bodyBgAttach) {
        return findByProperty(BODY_BG_ATTACH, bodyBgAttach);
    }

    public List findByBodyBgRepeat(Object bodyBgRepeat) {
        return findByProperty(BODY_BG_REPEAT, bodyBgRepeat);
    }

    public List findByBodyBgPosition(Object bodyBgPosition) {
        return findByProperty(BODY_BG_POSITION, bodyBgPosition);
    }

    public List findByContentBgColor(Object contentBgColor) {
        return findByProperty(CONTENT_BG_COLOR, contentBgColor);
    }

    public List findByUseAbsolute(Object useAbsolute) {
        return findByProperty(USE_ABSOLUTE, useAbsolute);
    }

    public List findByAbsoluteTop(Object absoluteTop) {
        return findByProperty(ABSOLUTE_TOP, absoluteTop);
    }

    public List findByAbsoluteLeft(Object absoluteLeft) {
        return findByProperty(ABSOLUTE_LEFT, absoluteLeft);
    }

    public List findByAbsoluteRight(Object absoluteRight) {
        return findByProperty(ABSOLUTE_RIGHT, absoluteRight);
    }

    public List findByAbsoluteBottom(Object absoluteBottom) {
        return findByProperty(ABSOLUTE_BOTTOM, absoluteBottom);
    }

    public List findByPanelStyleId(Object panelStyleId) {
        return findByProperty(PANEL_STYLE_ID, panelStyleId);
    }

    public List findByPanelDataStyleId(Object panelDataStyleId) {
        return findByProperty(PANEL_DATA_STYLE_ID, panelDataStyleId);
    }

    public List findByPanelLinkStyleId(Object panelLinkStyleId) {
        return findByProperty(PANEL_LINK_STYLE_ID, panelLinkStyleId);
    }

    public List findByPanelTitleStyleId(Object panelTitleStyleId) {
        return findByProperty(PANEL_TITLE_STYLE_ID, panelTitleStyleId);
    }

    public List findByMenuStyleId(Object menuStyleId) {
        return findByProperty(MENU_STYLE_ID, menuStyleId);
    }

    public List findByMenuLinkStyleId(Object menuLinkStyleId) {
        return findByProperty(MENU_LINK_STYLE_ID, menuLinkStyleId);
    }

    public List findByHeaderMenuStyleId(Object headerMenuStyleId) {
        return findByProperty(HEADER_MENU_STYLE_ID, headerMenuStyleId);
    }

    public List findByHeaderMenuLinkStyleId(Object headerMenuLinkStyleId) {
        return findByProperty(HEADER_MENU_LINK_STYLE_ID, headerMenuLinkStyleId);
    }

    public List findByListFrameStyleId(Object listFrameStyleId) {
        return findByProperty(LIST_FRAME_STYLE_ID, listFrameStyleId);
    }

    public List findByListSubjectStyleId(Object listSubjectStyleId) {
        return findByProperty(LIST_SUBJECT_STYLE_ID, listSubjectStyleId);
    }

    public List findByListDataStyleId(Object listDataStyleId) {
        return findByProperty(LIST_DATA_STYLE_ID, listDataStyleId);
    }

    public List findBySubjectStyleId(Object subjectStyleId) {
        return findByProperty(SUBJECT_STYLE_ID, subjectStyleId);
    }

    public List findByDataStyleId(Object dataStyleId) {
        return findByProperty(DATA_STYLE_ID, dataStyleId);
    }

    public List findBySingleFrameStyleId(Object singleFrameStyleId) {
        return findByProperty(SINGLE_FRAME_STYLE_ID, singleFrameStyleId);
    }

    public List findBySingleSubjectStyleId(Object singleSubjectStyleId) {
        return findByProperty(SINGLE_SUBJECT_STYLE_ID, singleSubjectStyleId);
    }

    public List findBySingleDataStyleId(Object singleDataStyleId) {
        return findByProperty(SINGLE_DATA_STYLE_ID, singleDataStyleId);
    }

    public List findByContentPanelStyleId(Object contentPanelStyleId) {
        return findByProperty(CONTENT_PANEL_STYLE_ID, contentPanelStyleId);
    }

    public List findByContentPanelTitleStyleId(Object contentPanelTitleStyleId) {
        return findByProperty(CONTENT_PANEL_TITLE_STYLE_ID, contentPanelTitleStyleId);
    }

    public List findByGlobal(Object global) {
        return findByProperty(GLOBAL, global);
    }

    public List findByDisable(Object disable) {
        return findByProperty(DISABLE, disable);
    }

    public List findAll() {
        log.debug("finding all StyleTheme instances");
        try {
            String queryString = "from StyleTheme";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StyleTheme merge(StyleTheme detachedInstance) {
        log.debug("merging StyleTheme instance");
        try {
            StyleTheme result = (StyleTheme) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StyleTheme instance) {
        log.debug("attaching dirty StyleTheme instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StyleTheme instance) {
        log.debug("attaching clean StyleTheme instance");
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
