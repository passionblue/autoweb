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
 * CleanerCustomerMember entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerCustomerMember
 * @author MyEclipse Persistence Tools
 */
public class CleanerCustomerMemberDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerCustomerMemberDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String TITLE = "title";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String NOTE = "note";

    public void save(CleanerCustomerMember transientInstance) {
        log.debug("saving CleanerCustomerMember instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerCustomerMember persistentInstance) {
        log.debug("deleting CleanerCustomerMember instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerCustomerMember findById(Long id) {
        log.debug("getting CleanerCustomerMember instance with id: " + id);
        try {
            CleanerCustomerMember instance = (CleanerCustomerMember) getSession().get("com.autosite.db.CleanerCustomerMember", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerCustomerMember instance) {
        log.debug("finding CleanerCustomerMember instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerCustomerMember").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerCustomerMember instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerCustomerMember as model where model." + propertyName + "= ?";
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

    public List findByCustomerId(Object customerId) {
        return findByProperty(CUSTOMER_ID, customerId);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByFirstName(Object firstName) {
        return findByProperty(FIRST_NAME, firstName);
    }

    public List findByLastName(Object lastName) {
        return findByProperty(LAST_NAME, lastName);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findAll() {
        log.debug("finding all CleanerCustomerMember instances");
        try {
            String queryString = "from CleanerCustomerMember";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerCustomerMember merge(CleanerCustomerMember detachedInstance) {
        log.debug("merging CleanerCustomerMember instance");
        try {
            CleanerCustomerMember result = (CleanerCustomerMember) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerCustomerMember instance) {
        log.debug("attaching dirty CleanerCustomerMember instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerCustomerMember instance) {
        log.debug("attaching clean CleanerCustomerMember instance");
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
