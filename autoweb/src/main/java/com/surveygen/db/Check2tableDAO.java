package com.surveygen.db;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Check2table.
 * 
 * @see com.surveygen.db.Check2table
 * @author MyEclipse Persistence Tools
 */

public class Check2tableDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(Check2tableDAO.class);

	// property constants
	public static final String CHECK2_VAL = "check2Val";

	public void save(Check2table transientInstance) {
		log.debug("saving Check2table instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Check2table persistentInstance) {
		log.debug("deleting Check2table instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Check2table findById(java.lang.Long id) {
		log.debug("getting Check2table instance with id: " + id);
		try {
			Check2table instance = (Check2table) getSession().get(
					"com.surveygen.db.Check2table", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Check2table instance) {
		log.debug("finding Check2table instance by example");
		try {
			List results = getSession().createCriteria(
					"com.surveygen.db.Check2table").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Check2table instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Check2table as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCheck2Val(Object check2Val) {
		return findByProperty(CHECK2_VAL, check2Val);
	}

	public List findAll() {
		log.debug("finding all Check2table instances");
		try {
			String queryString = "from Check2table";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Check2table merge(Check2table detachedInstance) {
		log.debug("merging Check2table instance");
		try {
			Check2table result = (Check2table) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Check2table instance) {
		log.debug("attaching dirty Check2table instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Check2table instance) {
		log.debug("attaching clean Check2table instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}