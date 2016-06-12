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
 * ChurMember entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.ChurMember
 * @author MyEclipse Persistence Tools
 */

public class ChurMemberDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(ChurMemberDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String FULL_NAME = "fullName";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String TITLE = "title";
    public static final String OTHER_NAME = "otherName";
    public static final String HOUSEHOLD = "household";
    public static final String HOUSEHOLD_ID = "householdId";
    public static final String IS_GROUP = "isGroup";
    public static final String IS_GUEST = "isGuest";
    public static final String IS_SPEAKER = "isSpeaker";
    public static final String LIST_INDEX = "listIndex";

    public void save(ChurMember transientInstance) {
        log.debug("saving ChurMember instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChurMember persistentInstance) {
        log.debug("deleting ChurMember instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChurMember findById(Long id) {
        log.debug("getting ChurMember instance with id: " + id);
        try {
            ChurMember instance = (ChurMember) getSession().get("com.autosite.db.ChurMember", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChurMember instance) {
        log.debug("finding ChurMember instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ChurMember").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChurMember instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ChurMember as model where model." + propertyName + "= ?";
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

    public List findByFullName(Object fullName) {
        return findByProperty(FULL_NAME, fullName);
    }

    public List findByFirstName(Object firstName) {
        return findByProperty(FIRST_NAME, firstName);
    }

    public List findByLastName(Object lastName) {
        return findByProperty(LAST_NAME, lastName);
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByOtherName(Object otherName) {
        return findByProperty(OTHER_NAME, otherName);
    }

    public List findByHousehold(Object household) {
        return findByProperty(HOUSEHOLD, household);
    }

    public List findByHouseholdId(Object householdId) {
        return findByProperty(HOUSEHOLD_ID, householdId);
    }

    public List findByIsGroup(Object isGroup) {
        return findByProperty(IS_GROUP, isGroup);
    }

    public List findByIsGuest(Object isGuest) {
        return findByProperty(IS_GUEST, isGuest);
    }

    public List findByIsSpeaker(Object isSpeaker) {
        return findByProperty(IS_SPEAKER, isSpeaker);
    }

    public List findByListIndex(Object listIndex) {
        return findByProperty(LIST_INDEX, listIndex);
    }

    public List findAll() {
        log.debug("finding all ChurMember instances");
        try {
            String queryString = "from ChurMember";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ChurMember merge(ChurMember detachedInstance) {
        log.debug("merging ChurMember instance");
        try {
            ChurMember result = (ChurMember) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ChurMember instance) {
        log.debug("attaching dirty ChurMember instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ChurMember instance) {
        log.debug("attaching clean ChurMember instance");
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
