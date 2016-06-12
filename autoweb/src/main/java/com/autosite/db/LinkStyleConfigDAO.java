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
 * LinkStyleConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.LinkStyleConfig
 * @author MyEclipse Persistence Tools
 */

public class LinkStyleConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(LinkStyleConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String STYLE_KEY = "styleKey";
    public static final String IS_GLOBAL = "isGlobal";
    public static final String ID_CLASS = "idClass";
    public static final String IS_ID = "isId";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String DISPLAY = "display";
    public static final String BORDER = "border";
    public static final String BACKGROUND = "background";
    public static final String COLOR = "color";
    public static final String TEXT_DECORATION = "textDecoration";
    public static final String TEXT_ALIGN = "textAlign";
    public static final String VERTICAL_ALIGN = "verticalAlign";
    public static final String TEXT_INDENT = "textIndent";
    public static final String MARGIN = "margin";
    public static final String PADDING = "padding";
    public static final String FONT = "font";
    public static final String EXTRA_STYLE = "extraStyle";
    public static final String HOV_HEIGHT = "hovHeight";
    public static final String HOV_WIDTH = "hovWidth";
    public static final String HOV_DISPLAY = "hovDisplay";
    public static final String HOV_BORDER = "hovBorder";
    public static final String HOV_BACKGROUND = "hovBackground";
    public static final String HOV_COLOR = "hovColor";
    public static final String HOV_TEXT_DECORATION = "hovTextDecoration";
    public static final String HOV_TEXT_ALIGN = "hovTextAlign";
    public static final String HOV_VERTICAL_ALIGN = "hovVerticalAlign";
    public static final String HOV_TEXT_INDENT = "hovTextIndent";
    public static final String HOV_MARGIN = "hovMargin";
    public static final String HOV_PADDING = "hovPadding";
    public static final String HOV_FONT = "hovFont";
    public static final String HOV_EXTRA_STYLE = "hovExtraStyle";
    public static final String ACTIVE_USE = "activeUse";
    public static final String ACTIVE_BORDER = "activeBorder";
    public static final String ACTIVE_BACKGROUND = "activeBackground";
    public static final String ACTIVE_COLOR = "activeColor";
    public static final String ACTIVE_TEXT_DECORATION = "activeTextDecoration";
    public static final String ACTIVE_EXTRA_STYLE = "activeExtraStyle";
    public static final String ACTIVE_MARGIN = "activeMargin";
    public static final String ACTIVE_PADDING = "activePadding";

    public void save(LinkStyleConfig transientInstance) {
        log.debug("saving LinkStyleConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(LinkStyleConfig persistentInstance) {
        log.debug("deleting LinkStyleConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public LinkStyleConfig findById(Long id) {
        log.debug("getting LinkStyleConfig instance with id: " + id);
        try {
            LinkStyleConfig instance = (LinkStyleConfig) getSession().get("com.autosite.db.LinkStyleConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(LinkStyleConfig instance) {
        log.debug("finding LinkStyleConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.LinkStyleConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding LinkStyleConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from LinkStyleConfig as model where model." + propertyName + "= ?";
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

    public List findByStyleKey(Object styleKey) {
        return findByProperty(STYLE_KEY, styleKey);
    }

    public List findByIsGlobal(Object isGlobal) {
        return findByProperty(IS_GLOBAL, isGlobal);
    }

    public List findByIdClass(Object idClass) {
        return findByProperty(ID_CLASS, idClass);
    }

    public List findByIsId(Object isId) {
        return findByProperty(IS_ID, isId);
    }

    public List findByHeight(Object height) {
        return findByProperty(HEIGHT, height);
    }

    public List findByWidth(Object width) {
        return findByProperty(WIDTH, width);
    }

    public List findByDisplay(Object display) {
        return findByProperty(DISPLAY, display);
    }

    public List findByBorder(Object border) {
        return findByProperty(BORDER, border);
    }

    public List findByBackground(Object background) {
        return findByProperty(BACKGROUND, background);
    }

    public List findByColor(Object color) {
        return findByProperty(COLOR, color);
    }

    public List findByTextDecoration(Object textDecoration) {
        return findByProperty(TEXT_DECORATION, textDecoration);
    }

    public List findByTextAlign(Object textAlign) {
        return findByProperty(TEXT_ALIGN, textAlign);
    }

    public List findByVerticalAlign(Object verticalAlign) {
        return findByProperty(VERTICAL_ALIGN, verticalAlign);
    }

    public List findByTextIndent(Object textIndent) {
        return findByProperty(TEXT_INDENT, textIndent);
    }

    public List findByMargin(Object margin) {
        return findByProperty(MARGIN, margin);
    }

    public List findByPadding(Object padding) {
        return findByProperty(PADDING, padding);
    }

    public List findByFont(Object font) {
        return findByProperty(FONT, font);
    }

    public List findByExtraStyle(Object extraStyle) {
        return findByProperty(EXTRA_STYLE, extraStyle);
    }

    public List findByHovHeight(Object hovHeight) {
        return findByProperty(HOV_HEIGHT, hovHeight);
    }

    public List findByHovWidth(Object hovWidth) {
        return findByProperty(HOV_WIDTH, hovWidth);
    }

    public List findByHovDisplay(Object hovDisplay) {
        return findByProperty(HOV_DISPLAY, hovDisplay);
    }

    public List findByHovBorder(Object hovBorder) {
        return findByProperty(HOV_BORDER, hovBorder);
    }

    public List findByHovBackground(Object hovBackground) {
        return findByProperty(HOV_BACKGROUND, hovBackground);
    }

    public List findByHovColor(Object hovColor) {
        return findByProperty(HOV_COLOR, hovColor);
    }

    public List findByHovTextDecoration(Object hovTextDecoration) {
        return findByProperty(HOV_TEXT_DECORATION, hovTextDecoration);
    }

    public List findByHovTextAlign(Object hovTextAlign) {
        return findByProperty(HOV_TEXT_ALIGN, hovTextAlign);
    }

    public List findByHovVerticalAlign(Object hovVerticalAlign) {
        return findByProperty(HOV_VERTICAL_ALIGN, hovVerticalAlign);
    }

    public List findByHovTextIndent(Object hovTextIndent) {
        return findByProperty(HOV_TEXT_INDENT, hovTextIndent);
    }

    public List findByHovMargin(Object hovMargin) {
        return findByProperty(HOV_MARGIN, hovMargin);
    }

    public List findByHovPadding(Object hovPadding) {
        return findByProperty(HOV_PADDING, hovPadding);
    }

    public List findByHovFont(Object hovFont) {
        return findByProperty(HOV_FONT, hovFont);
    }

    public List findByHovExtraStyle(Object hovExtraStyle) {
        return findByProperty(HOV_EXTRA_STYLE, hovExtraStyle);
    }

    public List findByActiveUse(Object activeUse) {
        return findByProperty(ACTIVE_USE, activeUse);
    }

    public List findByActiveBorder(Object activeBorder) {
        return findByProperty(ACTIVE_BORDER, activeBorder);
    }

    public List findByActiveBackground(Object activeBackground) {
        return findByProperty(ACTIVE_BACKGROUND, activeBackground);
    }

    public List findByActiveColor(Object activeColor) {
        return findByProperty(ACTIVE_COLOR, activeColor);
    }

    public List findByActiveTextDecoration(Object activeTextDecoration) {
        return findByProperty(ACTIVE_TEXT_DECORATION, activeTextDecoration);
    }

    public List findByActiveExtraStyle(Object activeExtraStyle) {
        return findByProperty(ACTIVE_EXTRA_STYLE, activeExtraStyle);
    }

    public List findByActiveMargin(Object activeMargin) {
        return findByProperty(ACTIVE_MARGIN, activeMargin);
    }

    public List findByActivePadding(Object activePadding) {
        return findByProperty(ACTIVE_PADDING, activePadding);
    }

    public List findAll() {
        log.debug("finding all LinkStyleConfig instances");
        try {
            String queryString = "from LinkStyleConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public LinkStyleConfig merge(LinkStyleConfig detachedInstance) {
        log.debug("merging LinkStyleConfig instance");
        try {
            LinkStyleConfig result = (LinkStyleConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(LinkStyleConfig instance) {
        log.debug("attaching dirty LinkStyleConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(LinkStyleConfig instance) {
        log.debug("attaching clean LinkStyleConfig instance");
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
