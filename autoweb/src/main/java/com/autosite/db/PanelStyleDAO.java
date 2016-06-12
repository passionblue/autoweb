package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for PanelStyle entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.autosite.db.PanelStyle
  * @author MyEclipse Persistence Tools 
 */

public class PanelStyleDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(PanelStyleDAO.class);
	//property constants
	public static final String SITE_ID = "siteId";
	public static final String PANEL_ID = "panelId";
	public static final String STYLE_ID = "styleId";



    
    public void save(PanelStyle transientInstance) {
        log.debug("saving PanelStyle instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(PanelStyle persistentInstance) {
        log.debug("deleting PanelStyle instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public PanelStyle findById( Long id) {
        log.debug("getting PanelStyle instance with id: " + id);
        try {
            PanelStyle instance = (PanelStyle) getSession()
                    .get("com.autosite.db.PanelStyle", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(PanelStyle instance) {
        log.debug("finding PanelStyle instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.autosite.db.PanelStyle")
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
      log.debug("finding PanelStyle instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from PanelStyle as model where model." 
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
	
	public List findByPanelId(Object panelId
	) {
		return findByProperty(PANEL_ID, panelId
		);
	}
	
	public List findByStyleId(Object styleId
	) {
		return findByProperty(STYLE_ID, styleId
		);
	}
	

	public List findAll() {
		log.debug("finding all PanelStyle instances");
		try {
			String queryString = "from PanelStyle";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public PanelStyle merge(PanelStyle detachedInstance) {
        log.debug("merging PanelStyle instance");
        try {
            PanelStyle result = (PanelStyle) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PanelStyle instance) {
        log.debug("attaching dirty PanelStyle instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(PanelStyle instance) {
        log.debug("attaching clean PanelStyle instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}