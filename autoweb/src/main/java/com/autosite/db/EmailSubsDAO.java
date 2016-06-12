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
 * EmailSubs entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.EmailSubs
 * @author MyEclipse Persistence Tools
 */

public class EmailSubsDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EmailSubsDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String SUBJECT = "subject";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String CONFIRMED = "confirmed";
    public static final String DISABLED = "disabled";
    public static final String CHECK_OPT1 = "checkOpt1";
    public static final String CHECK_OPT2 = "checkOpt2";
    public static final String EXTRA_INFO = "extraInfo";

    public void save(EmailSubs transientInstance) {
        log.debug("saving EmailSubs instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EmailSubs persistentInstance) {
        log.debug("deleting EmailSubs instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EmailSubs findById(Long id) {
        log.debug("getting EmailSubs instance with id: " + id);
        try {
            EmailSubs instance = (EmailSubs) getSession().get("com.autosite.db.EmailSubs", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EmailSubs instance) {
        log.debug("finding EmailSubs instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EmailSubs").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EmailSubs instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EmailSubs as model where model." + propertyName + "= ?";
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

    public List findBySubject(Object subject) {
        return findByProperty(SUBJECT, subject);
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

    public List findByConfirmed(Object confirmed) {
        return findByProperty(CONFIRMED, confirmed);
    }

    public List findByDisabled(Object disabled) {
        return findByProperty(DISABLED, disabled);
    }

    public List findByCheckOpt1(Object checkOpt1) {
        return findByProperty(CHECK_OPT1, checkOpt1);
    }

    public List findByCheckOpt2(Object checkOpt2) {
        return findByProperty(CHECK_OPT2, checkOpt2);
    }

    public List findByExtraInfo(Object extraInfo) {
        return findByProperty(EXTRA_INFO, extraInfo);
    }

    public List findAll() {
        log.debug("finding all EmailSubs instances");
        try {
            String queryString = "from EmailSubs";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EmailSubs merge(EmailSubs detachedInstance) {
        log.debug("merging EmailSubs instance");
        try {
            EmailSubs result = (EmailSubs) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EmailSubs instance) {
        log.debug("attaching dirty EmailSubs instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EmailSubs instance) {
        log.debug("attaching clean EmailSubs instance");
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
