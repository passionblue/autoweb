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
 * PanelPageConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.PanelPageConfig
 * @author MyEclipse Persistence Tools
 */

public class PanelPageConfigDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(PanelPageConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PANEL_ID = "panelId";
    public static final String PAGE_DISPLAY_SUMMARY = "pageDisplaySummary";

    public void save(PanelPageConfig transientInstance) {
        log.debug("saving PanelPageConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PanelPageConfig persistentInstance) {
        log.debug("deleting PanelPageConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PanelPageConfig findById(Long id) {
        log.debug("getting PanelPageConfig instance with id: " + id);
        try {
            PanelPageConfig instance = (PanelPageConfig) getSession().get("com.autosite.db.PanelPageConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PanelPageConfig instance) {
        log.debug("finding PanelPageConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.PanelPageConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PanelPageConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PanelPageConfig as model where model." + propertyName + "= ?";
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

    public List findByPageDisplaySummary(Object pageDisplaySummary) {
        return findByProperty(PAGE_DISPLAY_SUMMARY, pageDisplaySummary);
    }

    public List findAll() {
        log.debug("finding all PanelPageConfig instances");
        try {
            String queryString = "from PanelPageConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PanelPageConfig merge(PanelPageConfig detachedInstance) {
        log.debug("merging PanelPageConfig instance");
        try {
            PanelPageConfig result = (PanelPageConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PanelPageConfig instance) {
        log.debug("attaching dirty PanelPageConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PanelPageConfig instance) {
        log.debug("attaching clean PanelPageConfig instance");
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
