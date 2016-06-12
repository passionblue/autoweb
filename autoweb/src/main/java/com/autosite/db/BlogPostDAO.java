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
 * BlogPost entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.BlogPost
 * @author MyEclipse Persistence Tools
 */

public class BlogPostDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(BlogPostDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String BLOG_MAIN_ID = "blogMainId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String SUBJECT = "subject";
    public static final String CONTENT = "content";
    public static final String POST_TYPE = "postType";
    public static final String AUTHOR = "author";
    public static final String CONTENT_IMAGE = "contentImage";
    public static final String IMAGE_URL1 = "imageUrl1";
    public static final String IMAGE_URL2 = "imageUrl2";
    public static final String TAGS = "tags";
    public static final String SHORCUT_URL = "shorcutUrl";
    public static final String HIDE = "hide";
    public static final String POST_YEAR = "postYear";
    public static final String POST_MONTH = "postMonth";
    public static final String POST_DAY = "postDay";
    public static final String POST_YEARMONTH = "postYearmonth";

    public void save(BlogPost transientInstance) {
        log.debug("saving BlogPost instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BlogPost persistentInstance) {
        log.debug("deleting BlogPost instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BlogPost findById(Long id) {
        log.debug("getting BlogPost instance with id: " + id);
        try {
            BlogPost instance = (BlogPost) getSession().get("com.autosite.db.BlogPost", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BlogPost instance) {
        log.debug("finding BlogPost instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.BlogPost").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BlogPost instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BlogPost as model where model." + propertyName + "= ?";
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

    public List findByBlogMainId(Object blogMainId) {
        return findByProperty(BLOG_MAIN_ID, blogMainId);
    }

    public List findByCategoryId(Object categoryId) {
        return findByProperty(CATEGORY_ID, categoryId);
    }

    public List findBySubject(Object subject) {
        return findByProperty(SUBJECT, subject);
    }

    public List findByContent(Object content) {
        return findByProperty(CONTENT, content);
    }

    public List findByPostType(Object postType) {
        return findByProperty(POST_TYPE, postType);
    }

    public List findByAuthor(Object author) {
        return findByProperty(AUTHOR, author);
    }

    public List findByContentImage(Object contentImage) {
        return findByProperty(CONTENT_IMAGE, contentImage);
    }

    public List findByImageUrl1(Object imageUrl1) {
        return findByProperty(IMAGE_URL1, imageUrl1);
    }

    public List findByImageUrl2(Object imageUrl2) {
        return findByProperty(IMAGE_URL2, imageUrl2);
    }

    public List findByTags(Object tags) {
        return findByProperty(TAGS, tags);
    }

    public List findByShorcutUrl(Object shorcutUrl) {
        return findByProperty(SHORCUT_URL, shorcutUrl);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findByPostYear(Object postYear) {
        return findByProperty(POST_YEAR, postYear);
    }

    public List findByPostMonth(Object postMonth) {
        return findByProperty(POST_MONTH, postMonth);
    }

    public List findByPostDay(Object postDay) {
        return findByProperty(POST_DAY, postDay);
    }

    public List findByPostYearmonth(Object postYearmonth) {
        return findByProperty(POST_YEARMONTH, postYearmonth);
    }

    public List findAll() {
        log.debug("finding all BlogPost instances");
        try {
            String queryString = "from BlogPost";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BlogPost merge(BlogPost detachedInstance) {
        log.debug("merging BlogPost instance");
        try {
            BlogPost result = (BlogPost) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BlogPost instance) {
        log.debug("attaching dirty BlogPost instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BlogPost instance) {
        log.debug("attaching clean BlogPost instance");
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
