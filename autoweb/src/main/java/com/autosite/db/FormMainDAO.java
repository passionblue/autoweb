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
 	* A data access object (DAO) providing persistence and search support for FormMain entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.autosite.db.FormMain
  * @author MyEclipse Persistence Tools 
 */

public class FormMainDAO extends BaseHibernateDAO  {
		 private static final Log log = LogFactory.getLog(FormMainDAO.class);
		//property constants
	public static final String SITE_ID = "siteId";
	public static final String USER_ID = "userId";
	public static final String TITLE = "title";



    
    public void save(FormMain transientInstance) {
        log.debug("saving FormMain instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(FormMain persistentInstance) {
        log.debug("deleting FormMain instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public FormMain findById( Long id) {
        log.debug("getting FormMain instance with id: " + id);
        try {
            FormMain instance = (FormMain) getSession()
                    .get("com.autosite.db.FormMain", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(FormMain instance) {
        log.debug("finding FormMain instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.autosite.db.FormMain")
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
      log.debug("finding FormMain instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from FormMain as model where model." 
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
	
	public List findByUserId(Object userId
	) {
		return findByProperty(USER_ID, userId
		);
	}
	
	public List findByTitle(Object title
	) {
		return findByProperty(TITLE, title
		);
	}
	

	public List findAll() {
		log.debug("finding all FormMain instances");
		try {
			String queryString = "from FormMain";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public FormMain merge(FormMain detachedInstance) {
        log.debug("merging FormMain instance");
        try {
            FormMain result = (FormMain) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FormMain instance) {
        log.debug("attaching dirty FormMain instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(FormMain instance) {
        log.debug("attaching clean FormMain instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}