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
 * PanelMenuOrder entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.PanelMenuOrder
 * @author MyEclipse Persistence Tools
 */

public class PanelMenuOrderDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PanelMenuOrderDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PANEL_ID = "panelId";
    public static final String ORDERED_IDS = "orderedIds";
    public static final String REVERSE = "reverse";

    public void save(PanelMenuOrder transientInstance) {
        log.debug("saving PanelMenuOrder instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PanelMenuOrder persistentInstance) {
        log.debug("deleting PanelMenuOrder instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PanelMenuOrder findById(Long id) {
        log.debug("getting PanelMenuOrder instance with id: " + id);
        try {
            PanelMenuOrder instance = (PanelMenuOrder) getSession().get("com.autosite.db.PanelMenuOrder", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PanelMenuOrder instance) {
        log.debug("finding PanelMenuOrder instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PanelMenuOrder").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PanelMenuOrder instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PanelMenuOrder as model where model." + propertyName + "= ?";
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

    public List findByPanelId(Object panelId) {
        return findByProperty(PANEL_ID, panelId);
    }

    public List findByOrderedIds(Object orderedIds) {
        return findByProperty(ORDERED_IDS, orderedIds);
    }

    public List findByReverse(Object reverse) {
        return findByProperty(REVERSE, reverse);
    }

    public List findAll() {
        log.debug("finding all PanelMenuOrder instances");
        try {
            String queryString = "from PanelMenuOrder";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PanelMenuOrder merge(PanelMenuOrder detachedInstance) {
        log.debug("merging PanelMenuOrder instance");
        try {
            PanelMenuOrder result = (PanelMenuOrder) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PanelMenuOrder instance) {
        log.debug("attaching dirty PanelMenuOrder instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PanelMenuOrder instance) {
        log.debug("attaching clean PanelMenuOrder instance");
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
