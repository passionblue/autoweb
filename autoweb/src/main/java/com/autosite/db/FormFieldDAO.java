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
 * FormField entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.FormField
 * @author MyEclipse Persistence Tools
 */

public class FormFieldDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(FormFieldDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String FORM_ID = "formId";
    public static final String FIELD_TEXT = "fieldText";
    public static final String FIELD_TYPE = "fieldType";
    public static final String REQUIRED = "required";

    public void save(FormField transientInstance) {
        log.debug("saving FormField instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(FormField persistentInstance) {
        log.debug("deleting FormField instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public FormField findById(Long id) {
        log.debug("getting FormField instance with id: " + id);
        try {
            FormField instance = (FormField) getSession().get("com.autosite.db.FormField", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(FormField instance) {
        log.debug("finding FormField instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.FormField").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding FormField instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from FormField as model where model." + propertyName + "= ?";
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

    public List findByFormId(Object formId) {
        return findByProperty(FORM_ID, formId);
    }

    public List findByFieldText(Object fieldText) {
        return findByProperty(FIELD_TEXT, fieldText);
    }

    public List findByFieldType(Object fieldType) {
        return findByProperty(FIELD_TYPE, fieldType);
    }

    public List findByRequired(Object required) {
        return findByProperty(REQUIRED, required);
    }

    public List findAll() {
        log.debug("finding all FormField instances");
        try {
            String queryString = "from FormField";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public FormField merge(FormField detachedInstance) {
        log.debug("merging FormField instance");
        try {
            FormField result = (FormField) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FormField instance) {
        log.debug("attaching dirty FormField instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(FormField instance) {
        log.debug("attaching clean FormField instance");
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
