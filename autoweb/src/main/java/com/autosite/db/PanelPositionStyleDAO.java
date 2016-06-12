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
 * PanelPositionStyle entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.PanelPositionStyle
 * @author MyEclipse Persistence Tools
 */
public class PanelPositionStyleDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(PanelPositionStyleDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String COL = "col";
    public static final String STYLE_ID = "styleId";

    public void save(PanelPositionStyle transientInstance) {
        log.debug("saving PanelPositionStyle instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PanelPositionStyle persistentInstance) {
        log.debug("deleting PanelPositionStyle instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PanelPositionStyle findById(Long id) {
        log.debug("getting PanelPositionStyle instance with id: " + id);
        try {
            PanelPositionStyle instance = (PanelPositionStyle) getSession().get("com.autosite.db.PanelPositionStyle", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PanelPositionStyle instance) {
        log.debug("finding PanelPositionStyle instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PanelPositionStyle").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PanelPositionStyle instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PanelPositionStyle as model where model." + propertyName + "= ?";
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

    public List findByCol(Object col) {
        return findByProperty(COL, col);
    }

    public List findByStyleId(Object styleId) {
        return findByProperty(STYLE_ID, styleId);
    }

    public List findAll() {
        log.debug("finding all PanelPositionStyle instances");
        try {
            String queryString = "from PanelPositionStyle";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PanelPositionStyle merge(PanelPositionStyle detachedInstance) {
        log.debug("merging PanelPositionStyle instance");
        try {
            PanelPositionStyle result = (PanelPositionStyle) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PanelPositionStyle instance) {
        log.debug("attaching dirty PanelPositionStyle instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PanelPositionStyle instance) {
        log.debug("attaching clean PanelPositionStyle instance");
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
