package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * CleanerLocationConfig entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerLocationConfig
 * @author MyEclipse Persistence Tools
 */
public class CleanerLocationConfigDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerLocationConfigDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String LOCATION_ID = "locationId";
    public static final String OPEN_HOUR_WEEKDAY = "openHourWeekday";
    public static final String CLOSE_HOUR_WEEKDAY = "closeHourWeekday";
    public static final String OPEN_HOUR_SAT = "openHourSat";
    public static final String CLOSE_HOUR_SAT = "closeHourSat";
    public static final String OPEN_HOUR_SUN = "openHourSun";
    public static final String CLOSE_HOUR_SUN = "closeHourSun";

    public void save(CleanerLocationConfig transientInstance) {
        log.debug("saving CleanerLocationConfig instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerLocationConfig persistentInstance) {
        log.debug("deleting CleanerLocationConfig instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerLocationConfig findById(Long id) {
        log.debug("getting CleanerLocationConfig instance with id: " + id);
        try {
            CleanerLocationConfig instance = (CleanerLocationConfig) getSession().get("com.autosite.db.CleanerLocationConfig", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerLocationConfig instance) {
        log.debug("finding CleanerLocationConfig instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerLocationConfig").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerLocationConfig instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerLocationConfig as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySiteId(Object siteId) {
        return findByProperty(SITE_ID, siteId);
    }

    public List findByLocationId(Object locationId) {
        return findByProperty(LOCATION_ID, locationId);
    }

    public List findByOpenHourWeekday(Object openHourWeekday) {
        return findByProperty(OPEN_HOUR_WEEKDAY, openHourWeekday);
    }

    public List findByCloseHourWeekday(Object closeHourWeekday) {
        return findByProperty(CLOSE_HOUR_WEEKDAY, closeHourWeekday);
    }

    public List findByOpenHourSat(Object openHourSat) {
        return findByProperty(OPEN_HOUR_SAT, openHourSat);
    }

    public List findByCloseHourSat(Object closeHourSat) {
        return findByProperty(CLOSE_HOUR_SAT, closeHourSat);
    }

    public List findByOpenHourSun(Object openHourSun) {
        return findByProperty(OPEN_HOUR_SUN, openHourSun);
    }

    public List findByCloseHourSun(Object closeHourSun) {
        return findByProperty(CLOSE_HOUR_SUN, closeHourSun);
    }

    public List findAll() {
        log.debug("finding all CleanerLocationConfig instances");
        try {
            String queryString = "from CleanerLocationConfig";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerLocationConfig merge(CleanerLocationConfig detachedInstance) {
        log.debug("merging CleanerLocationConfig instance");
        try {
            CleanerLocationConfig result = (CleanerLocationConfig) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerLocationConfig instance) {
        log.debug("attaching dirty CleanerLocationConfig instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerLocationConfig instance) {
        log.debug("attaching clean CleanerLocationConfig instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
