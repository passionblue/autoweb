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
 * CleanerCustomer entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerCustomer
 * @author MyEclipse Persistence Tools
 */
public class CleanerCustomerDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerCustomerDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String AUTOSITE_USER_ID = "autositeUserId";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PHONE2 = "phone2";
    public static final String PHONE_PREFERRED = "phonePreferred";
    public static final String TITLE = "title";
    public static final String LAST_NAME = "lastName";
    public static final String FIRST_NAME = "firstName";
    public static final String ADDRESS = "address";
    public static final String APT = "apt";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String CUSTOMER_TYPE = "customerType";
    public static final String PAY_TYPE = "payType";
    public static final String REMOTE_IP = "remoteIp";
    public static final String NOTE = "note";
    public static final String PICKUP_NOTE = "pickupNote";
    public static final String DELIVERY_NOTE = "deliveryNote";
    public static final String DISABLED = "disabled";
    public static final String PICKUP_DELIVERY_DISALLOWED = "pickupDeliveryDisallowed";
    public static final String HANDLE_INST = "handleInst";

    public void save(CleanerCustomer transientInstance) {
        log.debug("saving CleanerCustomer instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerCustomer persistentInstance) {
        log.debug("deleting CleanerCustomer instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerCustomer findById(Long id) {
        log.debug("getting CleanerCustomer instance with id: " + id);
        try {
            CleanerCustomer instance = (CleanerCustomer) getSession().get("com.autosite.db.CleanerCustomer", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerCustomer instance) {
        log.debug("finding CleanerCustomer instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerCustomer").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerCustomer instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerCustomer as model where model." + propertyName + "= ?";
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

    public List findByAutositeUserId(Object autositeUserId) {
        return findByProperty(AUTOSITE_USER_ID, autositeUserId);
    }

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
    }

    public List findByPhone2(Object phone2) {
        return findByProperty(PHONE2, phone2);
    }

    public List findByPhonePreferred(Object phonePreferred) {
        return findByProperty(PHONE_PREFERRED, phonePreferred);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByLastName(Object lastName) {
        return findByProperty(LAST_NAME, lastName);
    }

    public List findByFirstName(Object firstName) {
        return findByProperty(FIRST_NAME, firstName);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByApt(Object apt) {
        return findByProperty(APT, apt);
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

    public List findByCustomerType(Object customerType) {
        return findByProperty(CUSTOMER_TYPE, customerType);
    }

    public List findByPayType(Object payType) {
        return findByProperty(PAY_TYPE, payType);
    }

    public List findByRemoteIp(Object remoteIp) {
        return findByProperty(REMOTE_IP, remoteIp);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByPickupNote(Object pickupNote) {
        return findByProperty(PICKUP_NOTE, pickupNote);
    }

    public List findByDeliveryNote(Object deliveryNote) {
        return findByProperty(DELIVERY_NOTE, deliveryNote);
    }

    public List findByDisabled(Object disabled) {
        return findByProperty(DISABLED, disabled);
    }

    public List findByPickupDeliveryDisallowed(Object pickupDeliveryDisallowed) {
        return findByProperty(PICKUP_DELIVERY_DISALLOWED, pickupDeliveryDisallowed);
    }

    public List findByHandleInst(Object handleInst) {
        return findByProperty(HANDLE_INST, handleInst);
    }

    public List findAll() {
        log.debug("finding all CleanerCustomer instances");
        try {
            String queryString = "from CleanerCustomer";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerCustomer merge(CleanerCustomer detachedInstance) {
        log.debug("merging CleanerCustomer instance");
        try {
            CleanerCustomer result = (CleanerCustomer) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerCustomer instance) {
        log.debug("attaching dirty CleanerCustomer instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerCustomer instance) {
        log.debug("attaching clean CleanerCustomer instance");
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
