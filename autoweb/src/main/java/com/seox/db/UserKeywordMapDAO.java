package com.seox.db;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class UserKeywordMap.
 * @see com.seox.db.UserKeywordMap
 * @author MyEclipse - Hibernate Tools
 */
public class UserKeywordMapDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(UserKeywordMapDAO.class);

	//property constants
	public static final String KEYWORD_ID = "keywordId";
	public static final String ACTIVE = "active";
	public static final String CATEGORY_ID = "categoryId";

    
    public void save(UserKeywordMap transientInstance) {
        log.debug("saving UserKeywordMap instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
        finally {
            getSession().flush();
            getSession().close();
        }
    }
    
	public void delete(UserKeywordMap persistentInstance) {
        log.debug("deleting UserKeywordMap instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
        finally {
            getSession().flush();
            getSession().close();
        }
    }
    
    public List findAll() {
        return findAll("UserKeywordMap"); 
    }
    public UserKeywordMap findById( Long id) {
        log.debug("getting UserKeywordMap instance with id: " + id);
        try {
            UserKeywordMap instance = (UserKeywordMap) getSession()
                    .get("com.seox.db.UserKeywordMap", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(UserKeywordMap instance) {
        log.debug("finding UserKeywordMap instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.UserKeywordMap")
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
      log.debug("finding UserKeywordMap instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UserKeywordMap as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByKeywordId(Object keywordId) {
		return findByProperty(KEYWORD_ID, keywordId);
	}
	
	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}
	
	public List findByCategoryId(Object categoryId) {
		return findByProperty(CATEGORY_ID, categoryId);
	}
	
    public UserKeywordMap merge(UserKeywordMap detachedInstance) {
        log.debug("merging UserKeywordMap instance");
        try {
            UserKeywordMap result = (UserKeywordMap) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserKeywordMap instance) {
        log.debug("attaching dirty UserKeywordMap instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
        finally {
            getSession().flush();
            getSession().close();
        }
    }
    
    public void attachClean(UserKeywordMap instance) {
        log.debug("attaching clean UserKeywordMap instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}