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
 * ContentData entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.ContentData
 * @author MyEclipse Persistence Tools
 */

public class ContentDataDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ContentDataDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CONTENT_ID = "contentId";
    public static final String DATA = "data";
    public static final String OPTION1 = "option1";
    public static final String OPTION2 = "option2";
    public static final String OPTION3 = "option3";

    public void save(ContentData transientInstance) {
        log.debug("saving ContentData instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ContentData persistentInstance) {
        log.debug("deleting ContentData instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ContentData findById(Long id) {
        log.debug("getting ContentData instance with id: " + id);
        try {
            ContentData instance = (ContentData) getSession().get("com.autosite.db.ContentData", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ContentData instance) {
        log.debug("finding ContentData instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ContentData").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ContentData instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ContentData as model where model." + propertyName + "= ?";
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

    public List findByContentId(Object contentId) {
        return findByProperty(CONTENT_ID, contentId);
    }

    public List findByData(Object data) {
        return findByProperty(DATA, data);
    }

    public List findByOption1(Object option1) {
        return findByProperty(OPTION1, option1);
    }

    public List findByOption2(Object option2) {
        return findByProperty(OPTION2, option2);
    }

    public List findByOption3(Object option3) {
        return findByProperty(OPTION3, option3);
    }

    public List findAll() {
        log.debug("finding all ContentData instances");
        try {
            String queryString = "from ContentData";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ContentData merge(ContentData detachedInstance) {
        log.debug("merging ContentData instance");
        try {
            ContentData result = (ContentData) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ContentData instance) {
        log.debug("attaching dirty ContentData instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ContentData instance) {
        log.debug("attaching clean ContentData instance");
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
