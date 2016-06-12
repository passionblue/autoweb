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
 * SitePost entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.SitePost
 * @author MyEclipse Persistence Tools
 */

public class SitePostDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SitePostDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ID = "pageId";
    public static final String CONTENT_ID = "contentId";
    public static final String PANEL_ID = "panelId";
    public static final String POST_SCOPE = "postScope";
    public static final String POSITION_CODE = "positionCode";
    public static final String POST_TYPE = "postType";
    public static final String POST_DATA = "postData";
    public static final String POST_DATA_EXTRA = "postDataExtra";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String STYLE_STRING = "styleString";
    public static final String OPTION1 = "option1";
    public static final String OPTION2 = "option2";
    public static final String OPTION3 = "option3";
    public static final String OPTION4 = "option4";
    public static final String OPTION5 = "option5";
    public static final String HIDE = "hide";

    public void save(SitePost transientInstance) {
        log.debug("saving SitePost instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SitePost persistentInstance) {
        log.debug("deleting SitePost instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SitePost findById(Long id) {
        log.debug("getting SitePost instance with id: " + id);
        try {
            SitePost instance = (SitePost) getSession().get("com.autosite.db.SitePost", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SitePost instance) {
        log.debug("finding SitePost instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SitePost").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SitePost instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SitePost as model where model." + propertyName + "= ?";
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

    public List findByPageId(Object pageId) {
        return findByProperty(PAGE_ID, pageId);
    }

    public List findByContentId(Object contentId) {
        return findByProperty(CONTENT_ID, contentId);
    }

    public List findByPanelId(Object panelId) {
        return findByProperty(PANEL_ID, panelId);
    }

    public List findByPostScope(Object postScope) {
        return findByProperty(POST_SCOPE, postScope);
    }

    public List findByPositionCode(Object positionCode) {
        return findByProperty(POSITION_CODE, positionCode);
    }

    public List findByPostType(Object postType) {
        return findByProperty(POST_TYPE, postType);
    }

    public List findByPostData(Object postData) {
        return findByProperty(POST_DATA, postData);
    }

    public List findByPostDataExtra(Object postDataExtra) {
        return findByProperty(POST_DATA_EXTRA, postDataExtra);
    }

    public List findByHeight(Object height) {
        return findByProperty(HEIGHT, height);
    }

    public List findByWidth(Object width) {
        return findByProperty(WIDTH, width);
    }

    public List findByStyleString(Object styleString) {
        return findByProperty(STYLE_STRING, styleString);
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

    public List findByOption4(Object option4) {
        return findByProperty(OPTION4, option4);
    }

    public List findByOption5(Object option5) {
        return findByProperty(OPTION5, option5);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findAll() {
        log.debug("finding all SitePost instances");
        try {
            String queryString = "from SitePost";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SitePost merge(SitePost detachedInstance) {
        log.debug("merging SitePost instance");
        try {
            SitePost result = (SitePost) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SitePost instance) {
        log.debug("attaching dirty SitePost instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SitePost instance) {
        log.debug("attaching clean SitePost instance");
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
