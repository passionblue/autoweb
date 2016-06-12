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
 * AutositeAccount entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.AutositeAccount
 * @author MyEclipse Persistence Tools
 */

public class AutositeAccountDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(AutositeAccountDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String ACCOUNT_NUM = "accountNum";
    public static final String ENABLED = "enabled";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String COMPANY = "company";
    public static final String PHONE = "phone";
    public static final String EMAIL_CONFIRMED = "emailConfirmed";
    public static final String IN_BETA_TEST = "inBetaTest";
    public static final String IN_EVALUTION = "inEvalution";
    public static final String ACCOUNT_OWNER_ID = "accountOwnerId";

    public void save(AutositeAccount transientInstance) {
        log.debug("saving AutositeAccount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AutositeAccount persistentInstance) {
        log.debug("deleting AutositeAccount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AutositeAccount findById(Long id) {
        log.debug("getting AutositeAccount instance with id: " + id);
        try {
            AutositeAccount instance = (AutositeAccount) getSession().get("com.autosite.db.AutositeAccount", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AutositeAccount instance) {
        log.debug("finding AutositeAccount instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.AutositeAccount").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AutositeAccount instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AutositeAccount as model where model." + propertyName + "= ?";
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

    public List findByAccountNum(Object accountNum) {
        return findByProperty(ACCOUNT_NUM, accountNum);
    }

    public List findByEnabled(Object enabled) {
        return findByProperty(ENABLED, enabled);
    }

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findByFirstName(Object firstName) {
        return findByProperty(FIRST_NAME, firstName);
    }

    public List findByLastName(Object lastName) {
        return findByProperty(LAST_NAME, lastName);
    }

    public List findByCompany(Object company) {
        return findByProperty(COMPANY, company);
    }

    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
    }

    public List findByEmailConfirmed(Object emailConfirmed) {
        return findByProperty(EMAIL_CONFIRMED, emailConfirmed);
    }

    public List findByInBetaTest(Object inBetaTest) {
        return findByProperty(IN_BETA_TEST, inBetaTest);
    }

    public List findByInEvalution(Object inEvalution) {
        return findByProperty(IN_EVALUTION, inEvalution);
    }

    public List findByAccountOwnerId(Object accountOwnerId) {
        return findByProperty(ACCOUNT_OWNER_ID, accountOwnerId);
    }

    public List findAll() {
        log.debug("finding all AutositeAccount instances");
        try {
            String queryString = "from AutositeAccount";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AutositeAccount merge(AutositeAccount detachedInstance) {
        log.debug("merging AutositeAccount instance");
        try {
            AutositeAccount result = (AutositeAccount) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AutositeAccount instance) {
        log.debug("attaching dirty AutositeAccount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AutositeAccount instance) {
        log.debug("attaching clean AutositeAccount instance");
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
