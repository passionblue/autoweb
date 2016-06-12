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
 * A data access object (DAO) providing persistence and search support for
 * StyleConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.StyleConfig
 * @author MyEclipse Persistence Tools
 */
public class StyleConfigDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(StyleConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String STYLE_KEY = "styleKey";
    public static final String STYLE_USE = "styleUse";
    public static final String IS_GLOBAL = "isGlobal";
    public static final String ID_CLASS = "idClass";
    public static final String IS_ID = "isId";
    public static final String COLOR = "color";
    public static final String BG_COLOR = "bgColor";
    public static final String BG_IMAGE = "bgImage";
    public static final String BG_REPEAT = "bgRepeat";
    public static final String BG_ATTACH = "bgAttach";
    public static final String BG_POSITION = "bgPosition";
    public static final String TEXT_ALIGN = "textAlign";
    public static final String FONT_FAMILY = "fontFamily";
    public static final String FONT_SIZE = "fontSize";
    public static final String FONT_STYLE = "fontStyle";
    public static final String FONT_VARIANT = "fontVariant";
    public static final String FONT_WEIGHT = "fontWeight";
    public static final String BORDER_DIRECTION = "borderDirection";
    public static final String BORDER_WIDTH = "borderWidth";
    public static final String BORDER_STYLE = "borderStyle";
    public static final String BORDER_COLOR = "borderColor";
    public static final String MARGIN = "margin";
    public static final String PADDING = "padding";
    public static final String LIST_STYLE_TYPE = "listStyleType";
    public static final String LIST_STYLE_POSITION = "listStylePosition";
    public static final String LIST_STYLE_IMAGE = "listStyleImage";
    public static final String FLOATING = "floating";
    public static final String EXTRA_STYLE_STR = "extraStyleStr";
    public static final String ITEM_STYLE_STR = "itemStyleStr";
    public static final String LINK = "link";
    public static final String LINK_HOVER = "linkHover";
    public static final String LINK_ACTIVE = "linkActive";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String IS_TABLE = "isTable";
    public static final String BORDER_COLLAPSE = "borderCollapse";
    public static final String BORDER_SPACING = "borderSpacing";
    public static final String TR_STYLE_IDS = "trStyleIds";
    public static final String TD_STYLE_IDS = "tdStyleIds";

    public void save(StyleConfig transientInstance) {
        log.debug("saving StyleConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StyleConfig persistentInstance) {
        log.debug("deleting StyleConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StyleConfig findById(Long id) {
        log.debug("getting StyleConfig instance with id: " + id);
        try {
            StyleConfig instance = (StyleConfig) getSession().get("com.autosite.db.StyleConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StyleConfig instance) {
        log.debug("finding StyleConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.StyleConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StyleConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StyleConfig as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setCacheable(true);
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

    public List findByStyleUse(Object styleUse) {
        return findByProperty(STYLE_USE, styleUse);
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

    public List findByColor(Object color) {
        return findByProperty(COLOR, color);
    }

    public List findByBgColor(Object bgColor) {
        return findByProperty(BG_COLOR, bgColor);
    }

    public List findByBgImage(Object bgImage) {
        return findByProperty(BG_IMAGE, bgImage);
    }

    public List findByBgRepeat(Object bgRepeat) {
        return findByProperty(BG_REPEAT, bgRepeat);
    }

    public List findByBgAttach(Object bgAttach) {
        return findByProperty(BG_ATTACH, bgAttach);
    }

    public List findByBgPosition(Object bgPosition) {
        return findByProperty(BG_POSITION, bgPosition);
    }

    public List findByTextAlign(Object textAlign) {
        return findByProperty(TEXT_ALIGN, textAlign);
    }

    public List findByFontFamily(Object fontFamily) {
        return findByProperty(FONT_FAMILY, fontFamily);
    }

    public List findByFontSize(Object fontSize) {
        return findByProperty(FONT_SIZE, fontSize);
    }

    public List findByFontStyle(Object fontStyle) {
        return findByProperty(FONT_STYLE, fontStyle);
    }

    public List findByFontVariant(Object fontVariant) {
        return findByProperty(FONT_VARIANT, fontVariant);
    }

    public List findByFontWeight(Object fontWeight) {
        return findByProperty(FONT_WEIGHT, fontWeight);
    }

    public List findByBorderDirection(Object borderDirection) {
        return findByProperty(BORDER_DIRECTION, borderDirection);
    }

    public List findByBorderWidth(Object borderWidth) {
        return findByProperty(BORDER_WIDTH, borderWidth);
    }

    public List findByBorderStyle(Object borderStyle) {
        return findByProperty(BORDER_STYLE, borderStyle);
    }

    public List findByBorderColor(Object borderColor) {
        return findByProperty(BORDER_COLOR, borderColor);
    }

    public List findByMargin(Object margin) {
        return findByProperty(MARGIN, margin);
    }

    public List findByPadding(Object padding) {
        return findByProperty(PADDING, padding);
    }

    public List findByListStyleType(Object listStyleType) {
        return findByProperty(LIST_STYLE_TYPE, listStyleType);
    }

    public List findByListStylePosition(Object listStylePosition) {
        return findByProperty(LIST_STYLE_POSITION, listStylePosition);
    }

    public List findByListStyleImage(Object listStyleImage) {
        return findByProperty(LIST_STYLE_IMAGE, listStyleImage);
    }

    public List findByFloating(Object floating) {
        return findByProperty(FLOATING, floating);
    }

    public List findByExtraStyleStr(Object extraStyleStr) {
        return findByProperty(EXTRA_STYLE_STR, extraStyleStr);
    }

    public List findByItemStyleStr(Object itemStyleStr) {
        return findByProperty(ITEM_STYLE_STR, itemStyleStr);
    }

    public List findByLink(Object link) {
        return findByProperty(LINK, link);
    }

    public List findByLinkHover(Object linkHover) {
        return findByProperty(LINK_HOVER, linkHover);
    }

    public List findByLinkActive(Object linkActive) {
        return findByProperty(LINK_ACTIVE, linkActive);
    }

    public List findByHeight(Object height) {
        return findByProperty(HEIGHT, height);
    }

    public List findByWidth(Object width) {
        return findByProperty(WIDTH, width);
    }

    public List findByIsTable(Object isTable) {
        return findByProperty(IS_TABLE, isTable);
    }

    public List findByBorderCollapse(Object borderCollapse) {
        return findByProperty(BORDER_COLLAPSE, borderCollapse);
    }

    public List findByBorderSpacing(Object borderSpacing) {
        return findByProperty(BORDER_SPACING, borderSpacing);
    }

    public List findByTrStyleIds(Object trStyleIds) {
        return findByProperty(TR_STYLE_IDS, trStyleIds);
    }

    public List findByTdStyleIds(Object tdStyleIds) {
        return findByProperty(TD_STYLE_IDS, tdStyleIds);
    }

    public List findAll() {
        log.debug("finding all StyleConfig instances");
        try {
            String queryString = "from StyleConfig";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setCacheable(true);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StyleConfig merge(StyleConfig detachedInstance) {
        log.debug("merging StyleConfig instance");
        try {
            StyleConfig result = (StyleConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StyleConfig instance) {
        log.debug("attaching dirty StyleConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StyleConfig instance) {
        log.debug("attaching clean StyleConfig instance");
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
