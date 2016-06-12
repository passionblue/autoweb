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
 * A data access object (DAO) providing persistence and search support for
 * RegisterSimple entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.RegisterSimple
 * @author MyEclipse Persistence Tools
 */

public class RegisterSimpleDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(RegisterSimpleDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_TYPE = "userType";
    public static final String BIRTH_YEAR = "birthYear";
    public static final String BIRTH_MONTH = "birthMonth";
    public static final String BIRTH_DAY = "birthDay";

    public void save(RegisterSimple transientInstance) {
        log.debug("saving RegisterSimple instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(RegisterSimple persistentInstance) {
        log.debug("deleting RegisterSimple instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public RegisterSimple findById(Long id) {
        log.debug("getting RegisterSimple instance with id: " + id);
        try {
            RegisterSimple instance = (RegisterSimple) getSession().get("com.autosite.db.RegisterSimple", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(RegisterSimple instance) {
        log.debug("finding RegisterSimple instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.RegisterSimple").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding RegisterSimple instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from RegisterSimple as model where model." + propertyName + "= ?";
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

    public List findByFirstName(Object firstName) {
        return findByProperty(FIRST_NAME, firstName);
    }

    public List findByLastName(Object lastName) {
        return findByProperty(LAST_NAME, lastName);
    }

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findByUsername(Object username) {
        return findByProperty(USERNAME, username);
    }

    public List findByPassword(Object password) {
        return findByProperty(PASSWORD, password);
    }

    public List findByUserType(Object userType) {
        return findByProperty(USER_TYPE, userType);
    }

    public List findByBirthYear(Object birthYear) {
        return findByProperty(BIRTH_YEAR, birthYear);
    }

    public List findByBirthMonth(Object birthMonth) {
        return findByProperty(BIRTH_MONTH, birthMonth);
    }

    public List findByBirthDay(Object birthDay) {
        return findByProperty(BIRTH_DAY, birthDay);
    }

    public List findAll() {
        log.debug("finding all RegisterSimple instances");
        try {
            String queryString = "from RegisterSimple";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public RegisterSimple merge(RegisterSimple detachedInstance) {
        log.debug("merging RegisterSimple instance");
        try {
            RegisterSimple result = (RegisterSimple) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(RegisterSimple instance) {
        log.debug("attaching dirty RegisterSimple instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(RegisterSimple instance) {
        log.debug("attaching clean RegisterSimple instance");
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
