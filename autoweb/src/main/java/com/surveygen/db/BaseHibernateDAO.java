package com.surveygen.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.CleanerPickupDelivery;
import com.seox.db.IBaseHibernateDAO;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public abstract class BaseHibernateDAO implements IBaseHibernateDAO {

    public Session getSession() {
        return com.surveygen.db.HibernateSessionFactory.getSession();
    }

    public void closeSession() {
        HibernateSessionFactory.closeSession();
    }

    abstract public List findAll();
    
    protected List findAll(String table) {
        try {
            String queryString = "from " + table;
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            throw re;
        }
    }
    
    public Object findById(Long id, String model) {
        try {
            return getSession().get(model, id);
        }
        catch (RuntimeException re) {
            throw re;
        }
    }
    
    public void add(Object instance) {
//        if ( m_logger.isDebugEnabled()) m_logger.debug("saving instance [" + instance.getClass().getSimpleName() + "] " + instance.getClass().getSimpleName());
        try {
            getSession().save(instance);
            getSession().flush();
            if ( m_logger.isDebugEnabled()) m_logger.debug("SAVED SUCCESS [" + instance.getClass().getSimpleName() + "] " + ((AutositeDataObject)instance).getId());
        }
        catch (RuntimeException re) {
            m_logger.error("SAVE FAILED [" + instance.getClass().getSimpleName() + "] ", re);
            throw re;
        }
        finally {
            getSession().close();
        }
    }

    public void remove(Object instance) {
        //if ( m_logger.isDebugEnabled()) m_logger.debug("deleting instance [" + instance.getClass().getSimpleName() + "] " + ((AutositeDataObject)instance).getId());
        try {
            getSession().delete(instance);
            getSession().flush();
            if ( m_logger.isDebugEnabled()) m_logger.debug("DELETE SUCCESS [" + instance.getClass().getSimpleName() + "] " + ((AutositeDataObject)instance).getId());
        }
        catch (RuntimeException re) {
            m_logger.error("DELETE FAILED [" + instance.getClass().getSimpleName() + "] " + ((AutositeDataObject)instance).getId(), re);
            throw re;
        }
        finally {
            getSession().close();
        }
    }

    public void update(Object instance) {
        //if ( m_logger.isDebugEnabled()) m_logger.debug("update instance [" + instance.getClass().getSimpleName() + "] " + ((AutositeDataObject)instance).getId());
        try {
            getSession().saveOrUpdate(instance);
            getSession().flush();
            if ( m_logger.isDebugEnabled()) m_logger.debug("UPDATE SUCCESS [" + instance.getClass().getSimpleName() + "] "  + ((AutositeDataObject)instance).getId());
        }
        catch (RuntimeException re) {
            m_logger.error("UPDATE FAILED [" + instance.getClass().getSimpleName() + "] " + ((AutositeDataObject)instance).getId(), re);
            throw re;
        }
        finally {
            getSession().close();
        }
    }

    private static Logger m_logger = Logger.getLogger(BaseHibernateDAO.class);
}
