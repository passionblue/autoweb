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
 * EcUserPaymentInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcUserPaymentInfo
 * @author MyEclipse Persistence Tools
 */

public class EcUserPaymentInfoDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcUserPaymentInfoDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String PAYMENT_NAME = "paymentName";
    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_INITIAL = "middleInitial";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS1 = "address1";
    public static final String ADDRESS2 = "address2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String COUNTRY = "country";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String CARD_TYPE = "cardType";
    public static final String PAYMENT_NUM = "paymentNum";
    public static final String PAYMENT_EXPIRE_MONTH = "paymentExpireMonth";
    public static final String PAYMENT_EXPIRE_YEAR = "paymentExpireYear";
    public static final String PAYMENT_EXTRA_NUM = "paymentExtraNum";

    public void save(EcUserPaymentInfo transientInstance) {
        log.debug("saving EcUserPaymentInfo instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcUserPaymentInfo persistentInstance) {
        log.debug("deleting EcUserPaymentInfo instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcUserPaymentInfo findById(Long id) {
        log.debug("getting EcUserPaymentInfo instance with id: " + id);
        try {
            EcUserPaymentInfo instance = (EcUserPaymentInfo) getSession().get("com.autosite.db.EcUserPaymentInfo", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcUserPaymentInfo instance) {
        log.debug("finding EcUserPaymentInfo instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcUserPaymentInfo").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcUserPaymentInfo instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcUserPaymentInfo as model where model." + propertyName + "= ?";
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

    public List findByPaymentName(Object paymentName) {
        return findByProperty(PAYMENT_NAME, paymentName);
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

    public List findByPaymentType(Object paymentType) {
        return findByProperty(PAYMENT_TYPE, paymentType);
    }

    public List findByCardType(Object cardType) {
        return findByProperty(CARD_TYPE, cardType);
    }

    public List findByPaymentNum(Object paymentNum) {
        return findByProperty(PAYMENT_NUM, paymentNum);
    }

    public List findByPaymentExpireMonth(Object paymentExpireMonth) {
        return findByProperty(PAYMENT_EXPIRE_MONTH, paymentExpireMonth);
    }

    public List findByPaymentExpireYear(Object paymentExpireYear) {
        return findByProperty(PAYMENT_EXPIRE_YEAR, paymentExpireYear);
    }

    public List findByPaymentExtraNum(Object paymentExtraNum) {
        return findByProperty(PAYMENT_EXTRA_NUM, paymentExtraNum);
    }

    public List findAll() {
        log.debug("finding all EcUserPaymentInfo instances");
        try {
            String queryString = "from EcUserPaymentInfo";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcUserPaymentInfo merge(EcUserPaymentInfo detachedInstance) {
        log.debug("merging EcUserPaymentInfo instance");
        try {
            EcUserPaymentInfo result = (EcUserPaymentInfo) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcUserPaymentInfo instance) {
        log.debug("attaching dirty EcUserPaymentInfo instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcUserPaymentInfo instance) {
        log.debug("attaching clean EcUserPaymentInfo instance");
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
