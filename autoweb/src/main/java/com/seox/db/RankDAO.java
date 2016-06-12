package com.seox.db;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Rank.
 * @see com.seox.db.Rank
 * @author MyEclipse - Hibernate Tools
 */
public class RankDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(RankDAO.class);

	//property constants
	public static final String DOMAIN = "domain";
	public static final String LINK = "link";
	public static final String WEEK_NUM = "weekNum";
	public static final String DAY_NUM = "dayNum";
	public static final String RANK = "rank";
	public static final String SEARCH_ENGINE = "searchEngine";

    
    public void save(Rank transientInstance) {
        log.debug("saving Rank instance");
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
    
	public void delete(Rank persistentInstance) {
        log.debug("deleting Rank instance");
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
        return findAll("Rank"); 
    }

    public Rank findById( Long id) {
        log.debug("getting Rank instance with id: " + id);
        try {
            Rank instance = (Rank) getSession()
                    .get("com.seox.db.Rank", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Rank instance) {
        log.debug("finding Rank instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.seox.db.Rank")
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
      log.debug("finding Rank instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Rank as model where model." 
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
	
	public List findByLink(Object link) {
		return findByProperty(LINK, link);
	}
	
	public List findByWeekNum(Object weekNum) {
		return findByProperty(WEEK_NUM, weekNum);
	}
	
	public List findByDayNum(Object dayNum) {
		return findByProperty(DAY_NUM, dayNum);
	}
	
	public List findByRank(Object rank) {
		return findByProperty(RANK, rank);
	}
	
	public List findBySearchEngine(Object searchEngine) {
		return findByProperty(SEARCH_ENGINE, searchEngine);
	}
	
    public Rank merge(Rank detachedInstance) {
        log.debug("merging Rank instance");
        try {
            Rank result = (Rank) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Rank instance) {
        log.debug("attaching dirty Rank instance");
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
    
    public void attachClean(Rank instance) {
        log.debug("attaching clean Rank instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}