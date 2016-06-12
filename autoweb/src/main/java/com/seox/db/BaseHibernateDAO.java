package com.seox.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.seox.hibernate.HibernateSessionFactory;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse - Hibernate Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}
    
    public void closeSession() {
        HibernateSessionFactory.closeSession();
    }

    protected List findAll(String table) {
        try {
            String queryString = "from "+table;
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            throw re;
        }
    }
}