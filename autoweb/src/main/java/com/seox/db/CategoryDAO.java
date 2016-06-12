package com.seox.db;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Category.
 * @see com.seox.db.Category
 * @author MyEclipse - Hibernate Tools
 */
public class CategoryDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(CategoryDAO.class);

	//property constants
	public static final String CATEGORY_NAME = "categoryName";
	public static final String USER_ID = "userId";

    
    public void save(Category transientInstance) {
        log.debug("saving Category instance");
        try {
            Serializable id = getSession().save(transientInstance);
            log.debug("save successful "  +id.getClass().getName());
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
        finally {
            getSession().flush();
            getSession().close();
        }
    }
    
	public void delete(Category persistentInstance) {
        log.debug("deleting Category instance");
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
        return findAll("Category"); 
    }
    
    public Category findById( Long id) {
        log.debug("getting Category instance with id: " + id);
        try {
            Category instance = (Category) getSession()
                    .get("com.seox.db.Category", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Category instance) {
        log.debug("finding Category instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.Category")
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
      log.debug("finding Category instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Category as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByCategoryName(Object categoryName) {
		return findByProperty(CATEGORY_NAME, categoryName);
	}
	
	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}
	
    public Category merge(Category detachedInstance) {
        log.debug("merging Category instance");
        try {
            Category result = (Category) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Category instance) {
        log.debug("attaching dirty Category instance");
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
    
    public void attachClean(Category instance) {
        log.debug("attaching clean Category instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}