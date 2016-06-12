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
 * Content entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.Content
 * @author MyEclipse Persistence Tools
 */

public class ContentDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ContentDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PAGE_ID = "pageId";
    public static final String SUB_PAGE_ID = "subPageId";
    public static final String PANEL_ID = "panelId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String USER_ID = "userId";
    public static final String CONTENT_SUBJECT = "contentSubject";
    public static final String CONTENT_ABSTRACT = "contentAbstract";
    public static final String CONTENT = "content";
    public static final String ABSTRACT_GENERATE = "abstractGenerate";
    public static final String ABSTRACT_NO = "abstractNo";
    public static final String USE_EXTERNAL_DATA = "useExternalData";
    public static final String AUTHOR_NAME = "authorName";
    public static final String CATEGORY = "category";
    public static final String CONTENT_TYPE = "contentType";
    public static final String SOURCE_NAME = "sourceName";
    public static final String SOURCE_URL = "sourceUrl";
    public static final String HIDE = "hide";
    public static final String SHOW_HOME = "showHome";
    public static final String IMAGE_URL = "imageUrl";
    public static final String IMAGE_HEIGHT = "imageHeight";
    public static final String IMAGE_WIDTH = "imageWidth";
    public static final String IN_HTML = "inHtml";
    public static final String TAGS = "tags";
    public static final String EXTRA_KEYWORDS = "extraKeywords";
    public static final String SHORTCUT_URL = "shortcutUrl";
    public static final String HEADER_META = "headerMeta";
    public static final String HEADER_LINK = "headerLink";
    public static final String COLUMN_NUM = "columnNum";

    public void save(Content transientInstance) {
        log.debug("saving Content instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Content persistentInstance) {
        log.debug("deleting Content instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Content findById(Long id) {
        log.debug("getting Content instance with id: " + id);
        try {
            Content instance = (Content) getSession().get("com.autosite.db.Content", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Content instance) {
        log.debug("finding Content instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.Content").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Content instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Content as model where model." + propertyName + "= ?";
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

    public List findBySubPageId(Object subPageId) {
        return findByProperty(SUB_PAGE_ID, subPageId);
    }

    public List findByPanelId(Object panelId) {
        return findByProperty(PANEL_ID, panelId);
    }

    public List findByCategoryId(Object categoryId) {
        return findByProperty(CATEGORY_ID, categoryId);
    }

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByContentSubject(Object contentSubject) {
        return findByProperty(CONTENT_SUBJECT, contentSubject);
    }

    public List findByContentAbstract(Object contentAbstract) {
        return findByProperty(CONTENT_ABSTRACT, contentAbstract);
    }

    public List findByContent(Object content) {
        return findByProperty(CONTENT, content);
    }

    public List findByAbstractGenerate(Object abstractGenerate) {
        return findByProperty(ABSTRACT_GENERATE, abstractGenerate);
    }

    public List findByAbstractNo(Object abstractNo) {
        return findByProperty(ABSTRACT_NO, abstractNo);
    }

    public List findByUseExternalData(Object useExternalData) {
        return findByProperty(USE_EXTERNAL_DATA, useExternalData);
    }

    public List findByAuthorName(Object authorName) {
        return findByProperty(AUTHOR_NAME, authorName);
    }

    public List findByCategory(Object category) {
        return findByProperty(CATEGORY, category);
    }

    public List findByContentType(Object contentType) {
        return findByProperty(CONTENT_TYPE, contentType);
    }

    public List findBySourceName(Object sourceName) {
        return findByProperty(SOURCE_NAME, sourceName);
    }

    public List findBySourceUrl(Object sourceUrl) {
        return findByProperty(SOURCE_URL, sourceUrl);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findByShowHome(Object showHome) {
        return findByProperty(SHOW_HOME, showHome);
    }

    public List findByImageUrl(Object imageUrl) {
        return findByProperty(IMAGE_URL, imageUrl);
    }

    public List findByImageHeight(Object imageHeight) {
        return findByProperty(IMAGE_HEIGHT, imageHeight);
    }

    public List findByImageWidth(Object imageWidth) {
        return findByProperty(IMAGE_WIDTH, imageWidth);
    }

    public List findByInHtml(Object inHtml) {
        return findByProperty(IN_HTML, inHtml);
    }

    public List findByTags(Object tags) {
        return findByProperty(TAGS, tags);
    }

    public List findByExtraKeywords(Object extraKeywords) {
        return findByProperty(EXTRA_KEYWORDS, extraKeywords);
    }

    public List findByShortcutUrl(Object shortcutUrl) {
        return findByProperty(SHORTCUT_URL, shortcutUrl);
    }

    public List findByHeaderMeta(Object headerMeta) {
        return findByProperty(HEADER_META, headerMeta);
    }

    public List findByHeaderLink(Object headerLink) {
        return findByProperty(HEADER_LINK, headerLink);
    }

    public List findByColumnNum(Object columnNum) {
        return findByProperty(COLUMN_NUM, columnNum);
    }

    public List findAll() {
        log.debug("finding all Content instances");
        try {
            String queryString = "from Content";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Content merge(Content detachedInstance) {
        log.debug("merging Content instance");
        try {
            Content result = (Content) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Content instance) {
        log.debug("attaching dirty Content instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Content instance) {
        log.debug("attaching clean Content instance");
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
