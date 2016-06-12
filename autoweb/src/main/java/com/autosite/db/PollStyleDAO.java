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
 * PollStyle entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.PollStyle
 * @author MyEclipse Persistence Tools
 */

public class PollStyleDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PollStyleDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String POLL_ID = "pollId";
    public static final String COLOR = "color";
    public static final String BACKGROUND = "background";
    public static final String BORDER = "border";
    public static final String FONT = "font";
    public static final String MARGIN = "margin";
    public static final String PADDING = "padding";
    public static final String FLOATING = "floating";
    public static final String TEXT_ALIGN = "textAlign";
    public static final String TEXT_INDENT = "textIndent";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String EXTRA = "extra";

    public void save(PollStyle transientInstance) {
        log.debug("saving PollStyle instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PollStyle persistentInstance) {
        log.debug("deleting PollStyle instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PollStyle findById(Long id) {
        log.debug("getting PollStyle instance with id: " + id);
        try {
            PollStyle instance = (PollStyle) getSession().get("com.autosite.db.PollStyle", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PollStyle instance) {
        log.debug("finding PollStyle instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PollStyle").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PollStyle instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PollStyle as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByPollId(Object pollId) {
        return findByProperty(POLL_ID, pollId);
    }

    public List findByColor(Object color) {
        return findByProperty(COLOR, color);
    }

    public List findByBackground(Object background) {
        return findByProperty(BACKGROUND, background);
    }

    public List findByBorder(Object border) {
        return findByProperty(BORDER, border);
    }

    public List findByFont(Object font) {
        return findByProperty(FONT, font);
    }

    public List findByMargin(Object margin) {
        return findByProperty(MARGIN, margin);
    }

    public List findByPadding(Object padding) {
        return findByProperty(PADDING, padding);
    }

    public List findByFloating(Object floating) {
        return findByProperty(FLOATING, floating);
    }

    public List findByTextAlign(Object textAlign) {
        return findByProperty(TEXT_ALIGN, textAlign);
    }

    public List findByTextIndent(Object textIndent) {
        return findByProperty(TEXT_INDENT, textIndent);
    }

    public List findByHeight(Object height) {
        return findByProperty(HEIGHT, height);
    }

    public List findByWidth(Object width) {
        return findByProperty(WIDTH, width);
    }

    public List findByExtra(Object extra) {
        return findByProperty(EXTRA, extra);
    }

    public List findAll() {
        log.debug("finding all PollStyle instances");
        try {
            String queryString = "from PollStyle";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PollStyle merge(PollStyle detachedInstance) {
        log.debug("merging PollStyle instance");
        try {
            PollStyle result = (PollStyle) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PollStyle instance) {
        log.debug("attaching dirty PollStyle instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PollStyle instance) {
        log.debug("attaching clean PollStyle instance");
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
