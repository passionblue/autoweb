package com.seox.db;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class DevNote.
 * @see com.seox.db.DevNote
 * @author MyEclipse - Hibernate Tools
 */
public class DevNoteDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(DevNoteDAO.class);

	//property constants
	public static final String DEV_NOTE_STR = "devNoteStr";

    
    public void save(DevNote transientInstance) {
        log.debug("saving DevNote instance");
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
    
	public void delete(DevNote persistentInstance) {
        log.debug("deleting DevNote instance");
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
        return findAll("DevNote"); 
    }    
    public DevNote findById( Long id) {
        log.debug("getting DevNote instance with id: " + id);
        try {
            DevNote instance = (DevNote) getSession()
                    .get("com.seox.db.DevNote", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(DevNote instance) {
        log.debug("finding DevNote instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.DevNote")
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
      log.debug("finding DevNote instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from DevNote as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByDevNoteStr(Object devNoteStr) {
		return findByProperty(DEV_NOTE_STR, devNoteStr);
	}
	
    public DevNote merge(DevNote detachedInstance) {
        log.debug("merging DevNote instance");
        try {
            DevNote result = (DevNote) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DevNote instance) {
        log.debug("attaching dirty DevNote instance");
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
    
    public void attachClean(DevNote instance) {
        log.debug("attaching clean DevNote instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}