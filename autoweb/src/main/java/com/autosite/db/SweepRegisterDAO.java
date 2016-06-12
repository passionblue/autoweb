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
 * SweepRegister entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SweepRegister
 * @author MyEclipse Persistence Tools
 */

public class SweepRegisterDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SweepRegisterDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String SEX = "sex";
    public static final String AGE_RANGE = "ageRange";
    public static final String AGREE_TERMS = "agreeTerms";

    public void save(SweepRegister transientInstance) {
        log.debug("saving SweepRegister instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SweepRegister persistentInstance) {
        log.debug("deleting SweepRegister instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SweepRegister findById(Long id) {
        log.debug("getting SweepRegister instance with id: " + id);
        try {
            SweepRegister instance = (SweepRegister) getSession().get("com.autosite.db.SweepRegister", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SweepRegister instance) {
        log.debug("finding SweepRegister instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SweepRegister").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SweepRegister instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SweepRegister as model where model." + propertyName + "= ?";
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

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findByPassword(Object password) {
        return findByProperty(PASSWORD, password);
    }

    public List findBySex(Object sex) {
        return findByProperty(SEX, sex);
    }

    public List findByAgeRange(Object ageRange) {
        return findByProperty(AGE_RANGE, ageRange);
    }

    public List findByAgreeTerms(Object agreeTerms) {
        return findByProperty(AGREE_TERMS, agreeTerms);
    }

    public List findAll() {
        log.debug("finding all SweepRegister instances");
        try {
            String queryString = "from SweepRegister";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SweepRegister merge(SweepRegister detachedInstance) {
        log.debug("merging SweepRegister instance");
        try {
            SweepRegister result = (SweepRegister) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SweepRegister instance) {
        log.debug("attaching dirty SweepRegister instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SweepRegister instance) {
        log.debug("attaching clean SweepRegister instance");
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
