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
 * EcAnonymousPaymentInfo entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.EcAnonymousPaymentInfo
 * @author MyEclipse Persistence Tools
 */

public class EcAnonymousPaymentInfoDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcAnonymousPaymentInfoDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String ANONYMOUS_USER_ID = "anonymousUserId";
    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_INITIAL = "middleInitial";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS1 = "address1";
    public static final String ADDRESS2 = "address2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String COUNTRY = "country";
    public static final String PHONE = "phone";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String CARD_TYPE = "cardType";
    public static final String PAYMENT_NUM = "paymentNum";
    public static final String PAYMENT_EXPIRE_MONTH = "paymentExpireMonth";
    public static final String PAYMENT_EXPIRE_YEAR = "paymentExpireYear";
    public static final String PAYMENT_EXTRA_NUM = "paymentExtraNum";

    public void save(EcAnonymousPaymentInfo transientInstance) {
        log.debug("saving EcAnonymousPaymentInfo instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcAnonymousPaymentInfo persistentInstance) {
        log.debug("deleting EcAnonymousPaymentInfo instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcAnonymousPaymentInfo findById(Long id) {
        log.debug("getting EcAnonymousPaymentInfo instance with id: " + id);
        try {
            EcAnonymousPaymentInfo instance = (EcAnonymousPaymentInfo) getSession().get("com.autosite.db.EcAnonymousPaymentInfo", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcAnonymousPaymentInfo instance) {
        log.debug("finding EcAnonymousPaymentInfo instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcAnonymousPaymentInfo").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcAnonymousPaymentInfo instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcAnonymousPaymentInfo as model where model." + propertyName + "= ?";
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

    public List findByAnonymousUserId(Object anonymousUserId) {
        return findByProperty(ANONYMOUS_USER_ID, anonymousUserId);
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

    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
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
        log.debug("finding all EcAnonymousPaymentInfo instances");
        try {
            String queryString = "from EcAnonymousPaymentInfo";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcAnonymousPaymentInfo merge(EcAnonymousPaymentInfo detachedInstance) {
        log.debug("merging EcAnonymousPaymentInfo instance");
        try {
            EcAnonymousPaymentInfo result = (EcAnonymousPaymentInfo) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcAnonymousPaymentInfo instance) {
        log.debug("attaching dirty EcAnonymousPaymentInfo instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcAnonymousPaymentInfo instance) {
        log.debug("attaching clean EcAnonymousPaymentInfo instance");
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
