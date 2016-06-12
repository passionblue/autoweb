package com.seox.db;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Keyword.
 * @see com.seox.db.Keyword
 * @author MyEclipse - Hibernate Tools
 */
public class KeywordDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(KeywordDAO.class);

	//property constants
	public static final String KEYWORD = "keyword";
	public static final String LAST_WEEK_NUM = "lastWeekNum";
	public static final String LAST_DAY_NUM = "lastDayNum";

    
    public void save(Keyword transientInstance) {
        log.debug("saving Keyword instance");
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
    
	public void delete(Keyword persistentInstance) {
        log.debug("deleting Keyword instance");
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
    
    public Keyword findById( Long id) {
        log.debug("getting Keyword instance with id: " + id);
        try {
            Keyword instance = (Keyword) getSession()
                    .get("com.seox.db.Keyword", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findAll() {
        return findAll("Keyword"); 
    }

    public List findByExample(Keyword instance) {
        log.debug("finding Keyword instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.Keyword")
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
      log.debug("finding Keyword instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Keyword as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByKeyword(Object keyword) {
		return findByProperty(KEYWORD, keyword);
	}
	
	public List findByLastWeekNum(Object lastWeekNum) {
		return findByProperty(LAST_WEEK_NUM, lastWeekNum);
	}
	
	public List findByLastDayNum(Object lastDayNum) {
		return findByProperty(LAST_DAY_NUM, lastDayNum);
	}
	
    public Keyword merge(Keyword detachedInstance) {
        log.debug("merging Keyword instance");
        try {
            Keyword result = (Keyword) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Keyword instance) {
        log.debug("attaching dirty Keyword instance");
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
    
    public void attachClean(Keyword instance) {
        log.debug("attaching clean Keyword instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}