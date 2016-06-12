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
 * StyleSetContent entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.StyleSetContent
 * @author MyEclipse Persistence Tools
 */

public class StyleSetContentDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(StyleSetContentDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String NAME = "name";
    public static final String ID_PREFIX = "idPrefix";
    public static final String LIST_FRAME_STYLE_ID = "listFrameStyleId";
    public static final String LIST_SUBJECT_STYLE_ID = "listSubjectStyleId";
    public static final String LIST_DATA_STYLE_ID = "listDataStyleId";
    public static final String FRAME_STYLE_ID = "frameStyleId";
    public static final String SUBJECT_STYLE_ID = "subjectStyleId";
    public static final String DATA_STYLE_ID = "dataStyleId";

    public void save(StyleSetContent transientInstance) {
        log.debug("saving StyleSetContent instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StyleSetContent persistentInstance) {
        log.debug("deleting StyleSetContent instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StyleSetContent findById(Long id) {
        log.debug("getting StyleSetContent instance with id: " + id);
        try {
            StyleSetContent instance = (StyleSetContent) getSession().get("com.autosite.db.StyleSetContent", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StyleSetContent instance) {
        log.debug("finding StyleSetContent instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.StyleSetContent").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StyleSetContent instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StyleSetContent as model where model." + propertyName + "= ?";
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

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByIdPrefix(Object idPrefix) {
        return findByProperty(ID_PREFIX, idPrefix);
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

    public List findByFrameStyleId(Object frameStyleId) {
        return findByProperty(FRAME_STYLE_ID, frameStyleId);
    }

    public List findBySubjectStyleId(Object subjectStyleId) {
        return findByProperty(SUBJECT_STYLE_ID, subjectStyleId);
    }

    public List findByDataStyleId(Object dataStyleId) {
        return findByProperty(DATA_STYLE_ID, dataStyleId);
    }

    public List findAll() {
        log.debug("finding all StyleSetContent instances");
        try {
            String queryString = "from StyleSetContent";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StyleSetContent merge(StyleSetContent detachedInstance) {
        log.debug("merging StyleSetContent instance");
        try {
            StyleSetContent result = (StyleSetContent) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StyleSetContent instance) {
        log.debug("attaching dirty StyleSetContent instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StyleSetContent instance) {
        log.debug("attaching clean StyleSetContent instance");
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
