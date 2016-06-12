package com.surveygen.db;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class CheckTable.
 * 
 * @see com.surveygen.db.CheckTable
 * @author MyEclipse Persistence Tools
 */

public class CheckTableDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(CheckTableDAO.class);

	// property constants
	public static final String CHEC_VAL = "checVal";

	public static final String CHECK_LONG = "checkLong";

	public static final String CHECK_BOOL = "checkBool";

	public void save(CheckTable transientInstance) {
		log.debug("saving CheckTable instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CheckTable persistentInstance) {
		log.debug("deleting CheckTable instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CheckTable findById(Long id) {
		log.debug("getting CheckTable instance with id: " + id);
		try {
			CheckTable instance = (CheckTable) getSession().get(
					"com.surveygen.db.CheckTable", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CheckTable instance) {
		log.debug("finding CheckTable instance by example");
		try {
			List results = getSession().createCriteria(
					"com.surveygen.db.CheckTable")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CheckTable instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CheckTable as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByChecVal(Object checVal) {
		return findByProperty(CHEC_VAL, checVal);
	}

	public List findByCheckLong(Object checkLong) {
		return findByProperty(CHECK_LONG, checkLong);
	}

	public List findByCheckBool(Object checkBool) {
		return findByProperty(CHECK_BOOL, checkBool);
	}

	public List findAll() {
		log.debug("finding all CheckTable instances");
		try {
			String queryString = "from CheckTable";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CheckTable merge(CheckTable detachedInstance) {
		log.debug("merging CheckTable instance");
		try {
			CheckTable result = (CheckTable) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CheckTable instance) {
		log.debug("attaching dirty CheckTable instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CheckTable instance) {
		log.debug("attaching clean CheckTable instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}