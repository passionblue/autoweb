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
 * SiteConfig entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.SiteConfig
 * @author MyEclipse Persistence Tools
 */

public class SiteConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SiteConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String KEYWORDS = "keywords";
    public static final String META = "meta";
    public static final String WIDTH = "width";
    public static final String SITE_TRACK_GOOGLE = "siteTrackGoogle";
    public static final String HOME_COL_COUNT = "homeColCount";
    public static final String COLOR_SUBMENU_BG = "colorSubmenuBg";
    public static final String CONTACT_INFO = "contactInfo";
    public static final String COLOR_BORDERS = "colorBorders";
    public static final String SITE_GROUP = "siteGroup";
    public static final String HEADER_IMAGE = "headerImage";
    public static final String HEADER_AD = "headerAd";
    public static final String MENU_WIDTH = "menuWidth";
    public static final String AD_WIDTH = "adWidth";
    public static final String MENU_FRONT_COLOR = "menuFrontColor";
    public static final String MENU_BACK_COLOR = "menuBackColor";
    public static final String MENU_LINE_COLOR = "menuLineColor";
    public static final String MENU_REVERSE = "menuReverse";
    public static final String DISPLAY_ALL_HOME = "displayAllHome";
    public static final String HEADER_IMAGE_HEIGHT = "headerImageHeight";
    public static final String HEADER_IMAGE_WIDTH = "headerImageWidth";
    public static final String LAYOUT_PAGE = "layoutPage";
    public static final String UNDERLYING_HOME = "underlyingHome";
    public static final String UNDERLYING_DYNAMIC_PAGE = "underlyingDynamicPage";
    public static final String CONTENT_FORWARD_SITE = "contentForwardSite";
    public static final String SHOW_MID_COLUMN = "showMidColumn";
    public static final String SHOW_MENU_COLUMN = "showMenuColumn";
    public static final String SHOW_AD_COLUMN = "showAdColumn";
    public static final String SHOW_HOME_MENU = "showHomeMenu";
    public static final String SHOW_HOME_MID = "showHomeMid";
    public static final String SHOW_HOME_AD = "showHomeAd";
    public static final String SHOW_TOP_MENU = "showTopMenu";
    public static final String MID_COLUMN_WIDTH = "midColumnWidth";
    public static final String USE_WYSIWYG_CONTENT = "useWysiwygContent";
    public static final String USE_WYSIWYG_POST = "useWysiwygPost";
    public static final String BACKGROUND_IMAGE = "backgroundImage";
    public static final String BACKGROUND_REPEAT = "backgroundRepeat";
    public static final String BACKGROUND_ATTACH = "backgroundAttach";
    public static final String BACKGROUND_COLOR = "backgroundColor";
    public static final String BACKGROUND_POSITION = "backgroundPosition";
    public static final String CONTENT_BG_COLOR = "contentBgColor";
    public static final String ABSOLUTE_POSITION = "absolutePosition";
    public static final String ABSOLUTE_TOP = "absoluteTop";
    public static final String ABSOLUTE_LEFT = "absoluteLeft";
    public static final String FUNCTIONAL_TYPE = "functionalType";
    public static final String EC_ENABLED = "ecEnabled";
    public static final String FRONT_DISPLAY_FEED = "frontDisplayFeed";
    public static final String SITE_ICON_URL = "siteIconUrl";

    public void save(SiteConfig transientInstance) {
        log.debug("saving SiteConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SiteConfig persistentInstance) {
        log.debug("deleting SiteConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SiteConfig findById(Long id) {
        log.debug("getting SiteConfig instance with id: " + id);
        try {
            SiteConfig instance = (SiteConfig) getSession().get("com.autosite.db.SiteConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SiteConfig instance) {
        log.debug("finding SiteConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SiteConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SiteConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SiteConfig as model where model." + propertyName + "= ?";
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

    public List findByKeywords(Object keywords) {
        return findByProperty(KEYWORDS, keywords);
    }

    public List findByMeta(Object meta) {
        return findByProperty(META, meta);
    }

    public List findByWidth(Object width) {
        return findByProperty(WIDTH, width);
    }

    public List findBySiteTrackGoogle(Object siteTrackGoogle) {
        return findByProperty(SITE_TRACK_GOOGLE, siteTrackGoogle);
    }

    public List findByHomeColCount(Object homeColCount) {
        return findByProperty(HOME_COL_COUNT, homeColCount);
    }

    public List findByColorSubmenuBg(Object colorSubmenuBg) {
        return findByProperty(COLOR_SUBMENU_BG, colorSubmenuBg);
    }

    public List findByContactInfo(Object contactInfo) {
        return findByProperty(CONTACT_INFO, contactInfo);
    }

    public List findByColorBorders(Object colorBorders) {
        return findByProperty(COLOR_BORDERS, colorBorders);
    }

    public List findBySiteGroup(Object siteGroup) {
        return findByProperty(SITE_GROUP, siteGroup);
    }

    public List findByHeaderImage(Object headerImage) {
        return findByProperty(HEADER_IMAGE, headerImage);
    }

    public List findByHeaderAd(Object headerAd) {
        return findByProperty(HEADER_AD, headerAd);
    }

    public List findByMenuWidth(Object menuWidth) {
        return findByProperty(MENU_WIDTH, menuWidth);
    }

    public List findByAdWidth(Object adWidth) {
        return findByProperty(AD_WIDTH, adWidth);
    }

    public List findByMenuFrontColor(Object menuFrontColor) {
        return findByProperty(MENU_FRONT_COLOR, menuFrontColor);
    }

    public List findByMenuBackColor(Object menuBackColor) {
        return findByProperty(MENU_BACK_COLOR, menuBackColor);
    }

    public List findByMenuLineColor(Object menuLineColor) {
        return findByProperty(MENU_LINE_COLOR, menuLineColor);
    }

    public List findByMenuReverse(Object menuReverse) {
        return findByProperty(MENU_REVERSE, menuReverse);
    }

    public List findByDisplayAllHome(Object displayAllHome) {
        return findByProperty(DISPLAY_ALL_HOME, displayAllHome);
    }

    public List findByHeaderImageHeight(Object headerImageHeight) {
        return findByProperty(HEADER_IMAGE_HEIGHT, headerImageHeight);
    }

    public List findByHeaderImageWidth(Object headerImageWidth) {
        return findByProperty(HEADER_IMAGE_WIDTH, headerImageWidth);
    }

    public List findByLayoutPage(Object layoutPage) {
        return findByProperty(LAYOUT_PAGE, layoutPage);
    }

    public List findByUnderlyingHome(Object underlyingHome) {
        return findByProperty(UNDERLYING_HOME, underlyingHome);
    }

    public List findByUnderlyingDynamicPage(Object underlyingDynamicPage) {
        return findByProperty(UNDERLYING_DYNAMIC_PAGE, underlyingDynamicPage);
    }

    public List findByContentForwardSite(Object contentForwardSite) {
        return findByProperty(CONTENT_FORWARD_SITE, contentForwardSite);
    }

    public List findByShowMidColumn(Object showMidColumn) {
        return findByProperty(SHOW_MID_COLUMN, showMidColumn);
    }

    public List findByShowMenuColumn(Object showMenuColumn) {
        return findByProperty(SHOW_MENU_COLUMN, showMenuColumn);
    }

    public List findByShowAdColumn(Object showAdColumn) {
        return findByProperty(SHOW_AD_COLUMN, showAdColumn);
    }

    public List findByShowHomeMenu(Object showHomeMenu) {
        return findByProperty(SHOW_HOME_MENU, showHomeMenu);
    }

    public List findByShowHomeMid(Object showHomeMid) {
        return findByProperty(SHOW_HOME_MID, showHomeMid);
    }

    public List findByShowHomeAd(Object showHomeAd) {
        return findByProperty(SHOW_HOME_AD, showHomeAd);
    }

    public List findByShowTopMenu(Object showTopMenu) {
        return findByProperty(SHOW_TOP_MENU, showTopMenu);
    }

    public List findByMidColumnWidth(Object midColumnWidth) {
        return findByProperty(MID_COLUMN_WIDTH, midColumnWidth);
    }

    public List findByUseWysiwygContent(Object useWysiwygContent) {
        return findByProperty(USE_WYSIWYG_CONTENT, useWysiwygContent);
    }

    public List findByUseWysiwygPost(Object useWysiwygPost) {
        return findByProperty(USE_WYSIWYG_POST, useWysiwygPost);
    }

    public List findByBackgroundImage(Object backgroundImage) {
        return findByProperty(BACKGROUND_IMAGE, backgroundImage);
    }

    public List findByBackgroundRepeat(Object backgroundRepeat) {
        return findByProperty(BACKGROUND_REPEAT, backgroundRepeat);
    }

    public List findByBackgroundAttach(Object backgroundAttach) {
        return findByProperty(BACKGROUND_ATTACH, backgroundAttach);
    }

    public List findByBackgroundColor(Object backgroundColor) {
        return findByProperty(BACKGROUND_COLOR, backgroundColor);
    }

    public List findByBackgroundPosition(Object backgroundPosition) {
        return findByProperty(BACKGROUND_POSITION, backgroundPosition);
    }

    public List findByContentBgColor(Object contentBgColor) {
        return findByProperty(CONTENT_BG_COLOR, contentBgColor);
    }

    public List findByAbsolutePosition(Object absolutePosition) {
        return findByProperty(ABSOLUTE_POSITION, absolutePosition);
    }

    public List findByAbsoluteTop(Object absoluteTop) {
        return findByProperty(ABSOLUTE_TOP, absoluteTop);
    }

    public List findByAbsoluteLeft(Object absoluteLeft) {
        return findByProperty(ABSOLUTE_LEFT, absoluteLeft);
    }

    public List findByFunctionalType(Object functionalType) {
        return findByProperty(FUNCTIONAL_TYPE, functionalType);
    }

    public List findByEcEnabled(Object ecEnabled) {
        return findByProperty(EC_ENABLED, ecEnabled);
    }

    public List findByFrontDisplayFeed(Object frontDisplayFeed) {
        return findByProperty(FRONT_DISPLAY_FEED, frontDisplayFeed);
    }

    public List findBySiteIconUrl(Object siteIconUrl) {
        return findByProperty(SITE_ICON_URL, siteIconUrl);
    }

    public List findAll() {
        log.debug("finding all SiteConfig instances");
        try {
            String queryString = "from SiteConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SiteConfig merge(SiteConfig detachedInstance) {
        log.debug("merging SiteConfig instance");
        try {
            SiteConfig result = (SiteConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SiteConfig instance) {
        log.debug("attaching dirty SiteConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SiteConfig instance) {
        log.debug("attaching clean SiteConfig instance");
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
