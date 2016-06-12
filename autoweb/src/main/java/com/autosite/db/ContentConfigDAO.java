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
 	* A data access object (DAO) providing persistence and search support for ContentConfig entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.autosite.db.ContentConfig
  * @author MyEclipse Persistence Tools 
 */

public class ContentConfigDAO extends BaseHibernateDAO  {
		 private static final Log log = LogFactory.getLog(ContentConfigDAO.class);
		//property constants
	public static final String SITE_ID = "siteId";
	public static final String CONTENT_ID = "contentId";
	public static final String KEYWORDS = "keywords";



    
    public void save(ContentConfig transientInstance) {
        log.debug("saving ContentConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(ContentConfig persistentInstance) {
        log.debug("deleting ContentConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public ContentConfig findById( Long id) {
        log.debug("getting ContentConfig instance with id: " + id);
        try {
            ContentConfig instance = (ContentConfig) getSession()
                    .get("com.autosite.db.ContentConfig", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(ContentConfig instance) {
        log.debug("finding ContentConfig instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.autosite.db.ContentConfig")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding ContentConfig instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from ContentConfig as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findBySiteId(Object siteId
	) {
		return findByProperty(SITE_ID, siteId
		);
	}
	
	public List findByContentId(Object contentId
	) {
		return findByProperty(CONTENT_ID, contentId
		);
	}
	
	public List findByKeywords(Object keywords
	) {
		return findByProperty(KEYWORDS, keywords
		);
	}
	

	public List findAll() {
		log.debug("finding all ContentConfig instances");
		try {
			String queryString = "from ContentConfig";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public ContentConfig merge(ContentConfig detachedInstance) {
        log.debug("merging ContentConfig instance");
        try {
            ContentConfig result = (ContentConfig) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ContentConfig instance) {
        log.debug("attaching dirty ContentConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(ContentConfig instance) {
        log.debug("attaching clean ContentConfig instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}