package com.seox.db;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Note.
 * @see com.seox.db.Note
 * @author MyEclipse - Hibernate Tools
 */
public class NoteDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(NoteDAO.class);

	//property constants
	public static final String NOTE_STR = "noteStr";
	public static final String USER_ID = "userId";

    
    public void save(Note transientInstance) {
        log.debug("saving Note instance");
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
    
	public void delete(Note persistentInstance) {
        log.debug("deleting Note instance");
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
        return findAll("Note"); 
    }    
    public Note findById( Long id) {
        log.debug("getting Note instance with id: " + id);
        try {
            Note instance = (Note) getSession()
                    .get("com.seox.db.Note", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Note instance) {
        log.debug("finding Note instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.Note")
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
      log.debug("finding Note instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Note as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByNoteStr(Object noteStr) {
		return findByProperty(NOTE_STR, noteStr);
	}
	
	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}
	
    public Note merge(Note detachedInstance) {
        log.debug("merging Note instance");
        try {
            Note result = (Note) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Note instance) {
        log.debug("attaching dirty Note instance");
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
    
    public void attachClean(Note instance) {
        log.debug("attaching clean Note instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}