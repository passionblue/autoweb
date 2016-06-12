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
 * MenuItem entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.MenuItem
 * @author MyEclipse Persistence Tools
 */

public class MenuItemDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(MenuItemDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PANEL_ID = "panelId";
    public static final String PARENT_ID = "parentId";
    public static final String TITLE = "title";
    public static final String DATA = "data";
    public static final String DATA2 = "data2";
    public static final String TARGET_TYPE = "targetType";
    public static final String ORDER_IDX = "orderIdx";
    public static final String PAGE_ID = "pageId";
    public static final String CONTENT_ID = "contentId";
    public static final String COLUM_NUM = "columNum";
    public static final String HIDE = "hide";

    public void save(MenuItem transientInstance) {
        log.debug("saving MenuItem instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(MenuItem persistentInstance) {
        log.debug("deleting MenuItem instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public MenuItem findById(Long id) {
        log.debug("getting MenuItem instance with id: " + id);
        try {
            MenuItem instance = (MenuItem) getSession().get("com.autosite.db.MenuItem", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(MenuItem instance) {
        log.debug("finding MenuItem instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.MenuItem").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding MenuItem instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from MenuItem as model where model." + propertyName + "= ?";
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

    public List findByParentId(Object parentId) {
        return findByProperty(PARENT_ID, parentId);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findByData2(Object data2) {
        return findByProperty(DATA2, data2);
    }

    public List findByTargetType(Object targetType) {
        return findByProperty(TARGET_TYPE, targetType);
    }

    public List findByOrderIdx(Object orderIdx) {
        return findByProperty(ORDER_IDX, orderIdx);
    }

    public List findByPageId(Object pageId) {
        return findByProperty(PAGE_ID, pageId);
    }

    public List findByContentId(Object contentId) {
        return findByProperty(CONTENT_ID, contentId);
    }

    public List findByColumNum(Object columNum) {
        return findByProperty(COLUM_NUM, columNum);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findAll() {
        log.debug("finding all MenuItem instances");
        try {
            String queryString = "from MenuItem";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public MenuItem merge(MenuItem detachedInstance) {
        log.debug("merging MenuItem instance");
        try {
            MenuItem result = (MenuItem) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MenuItem instance) {
        log.debug("attaching dirty MenuItem instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(MenuItem instance) {
        log.debug("attaching clean MenuItem instance");
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
