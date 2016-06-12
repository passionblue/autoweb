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
 * EcUserShippingInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcUserShippingInfo
 * @author MyEclipse Persistence Tools
 */

public class EcUserShippingInfoDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcUserShippingInfoDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String SHIPPING_NAME = "shippingName";
    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_INITIAL = "middleInitial";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS1 = "address1";
    public static final String ADDRESS2 = "address2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String COUNTRY = "country";
    public static final String SPECIAL_INSTRUCTION = "specialInstruction";

    public void save(EcUserShippingInfo transientInstance) {
        log.debug("saving EcUserShippingInfo instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcUserShippingInfo persistentInstance) {
        log.debug("deleting EcUserShippingInfo instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcUserShippingInfo findById(Long id) {
        log.debug("getting EcUserShippingInfo instance with id: " + id);
        try {
            EcUserShippingInfo instance = (EcUserShippingInfo) getSession().get("com.autosite.db.EcUserShippingInfo", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcUserShippingInfo instance) {
        log.debug("finding EcUserShippingInfo instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcUserShippingInfo").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcUserShippingInfo instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcUserShippingInfo as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByShippingName(Object shippingName) {
        return findByProperty(SHIPPING_NAME, shippingName);
    }

    public List findByFirstName(Object firstName) {
        return findByProperty(FIRST_NAME, firstName);
    }

    public List findByMiddleInitial(Object middleInitial) {
        return findByProperty(MIDDLE_INITIAL, middleInitial);
    }

    public List findByLastName(Object lastName) {
        return findByProperty(LAST_NAME, lastName);
    }

    public List findByAddress1(Object address1) {
        return findByProperty(ADDRESS1, address1);
    }

    public List findByAddress2(Object address2) {
        return findByProperty(ADDRESS2, address2);
    }

    public List findByCity(Object city) {
        return findByProperty(CITY, city);
    }

    public List findByState(Object state) {
        return findByProperty(STATE, state);
    }

    public List findByZip(Object zip) {
        return findByProperty(ZIP, zip);
    }

    public List findByCountry(Object country) {
        return findByProperty(COUNTRY, country);
    }

    public List findBySpecialInstruction(Object specialInstruction) {
        return findByProperty(SPECIAL_INSTRUCTION, specialInstruction);
    }

    public List findAll() {
        log.debug("finding all EcUserShippingInfo instances");
        try {
            String queryString = "from EcUserShippingInfo";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcUserShippingInfo merge(EcUserShippingInfo detachedInstance) {
        log.debug("merging EcUserShippingInfo instance");
        try {
            EcUserShippingInfo result = (EcUserShippingInfo) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcUserShippingInfo instance) {
        log.debug("attaching dirty EcUserShippingInfo instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcUserShippingInfo instance) {
        log.debug("attaching clean EcUserShippingInfo instance");
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
