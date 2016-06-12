package com.seox.db;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Innotest.
 * @see com.seox.db.Innotest
 * @author MyEclipse - Hibernate Tools
 */
public class InnotestDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(InnotestDAO.class);

	//property constants
	public static final String VALUE = "value";

    
    public void save(Innotest transientInstance) {
        log.debug("saving Innotest instance");
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
    
	public void delete(Innotest persistentInstance) {
        log.debug("deleting Innotest instance");
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
    
    public Innotest findById( Long id) {
        log.debug("getting Innotest instance with id: " + id);
        try {
            Innotest instance = (Innotest) getSession()
                    .get("com.seox.db.Innotest", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Innotest instance) {
        log.debug("finding Innotest instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.Innotest")
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
      log.debug("finding Innotest instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Innotest as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByValue(Object value) {
		return findByProperty(VALUE, value);
	}
	
    public Innotest merge(Innotest detachedInstance) {
        log.debug("merging Innotest instance");
        try {
            Innotest result = (Innotest) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Innotest instance) {
        log.debug("attaching dirty Innotest instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Innotest instance) {
        log.debug("attaching clean Innotest instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}