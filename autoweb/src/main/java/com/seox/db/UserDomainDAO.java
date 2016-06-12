package com.seox.db;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class UserDomain.
 * @see com.seox.db.UserDomain
 * @author MyEclipse - Hibernate Tools
 */
public class UserDomainDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(UserDomainDAO.class);

	//property constants
	public static final String DOMAIN = "domain";
	public static final String ACTIVE = "active";
	public static final String CATEGORY_ID = "categoryId";

    
    public void save(UserDomain transientInstance) {
        log.debug("saving UserDomain instance");
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
    
	public void delete(UserDomain persistentInstance) {
        log.debug("deleting UserDomain instance");
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
        return findAll("UserDomain"); 
    }
    
    public UserDomain findById( Long id) {
        log.debug("getting UserDomain instance with id: " + id);
        try {
            UserDomain instance = (UserDomain) getSession()
                    .get("com.seox.db.UserDomain", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(UserDomain instance) {
        log.debug("finding UserDomain instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.UserDomain")
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
      log.debug("finding UserDomain instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UserDomain as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByDomain(Object domain) {
		return findByProperty(DOMAIN, domain);
	}
	
	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}
	
	public List findByCategoryId(Object categoryId) {
		return findByProperty(CATEGORY_ID, categoryId);
	}
	
    public UserDomain merge(UserDomain detachedInstance) {
        log.debug("merging UserDomain instance");
        try {
            UserDomain result = (UserDomain) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserDomain instance) {
        log.debug("attaching dirty UserDomain instance");
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
    
    public void attachClean(UserDomain instance) {
        log.debug("attaching clean UserDomain instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}