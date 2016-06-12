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
 * CleanerTicketItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.CleanerTicketItem
 * @author MyEclipse Persistence Tools
 */
public class CleanerTicketItemDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(CleanerTicketItemDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String TICKET_ID = "ticketId";
    public static final String PARENT_TICKET_ID = "parentTicketId";
    public static final String PRODUCT_ID = "productId";
    public static final String SUBTOTAL_AMOUNT = "subtotalAmount";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String DISCOUNT_ID = "discountId";
    public static final String TOTAL_DISCOUNT_AMOUNT = "totalDiscountAmount";
    public static final String SPECIAL_DISCOUNT_AMOUNT = "specialDiscountAmount";
    public static final String NOTE = "note";

    public void save(CleanerTicketItem transientInstance) {
        log.debug("saving CleanerTicketItem instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CleanerTicketItem persistentInstance) {
        log.debug("deleting CleanerTicketItem instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CleanerTicketItem findById(Long id) {
        log.debug("getting CleanerTicketItem instance with id: " + id);
        try {
            CleanerTicketItem instance = (CleanerTicketItem) getSession().get("com.autosite.db.CleanerTicketItem", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CleanerTicketItem instance) {
        log.debug("finding CleanerTicketItem instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.CleanerTicketItem").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CleanerTicketItem instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CleanerTicketItem as model where model." + propertyName + "= ?";
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

    public List findByTicketId(Object ticketId) {
        return findByProperty(TICKET_ID, ticketId);
    }

    public List findByParentTicketId(Object parentTicketId) {
        return findByProperty(PARENT_TICKET_ID, parentTicketId);
    }

    public List findByProductId(Object productId) {
        return findByProperty(PRODUCT_ID, productId);
    }

    public List findBySubtotalAmount(Object subtotalAmount) {
        return findByProperty(SUBTOTAL_AMOUNT, subtotalAmount);
    }

    public List findByTotalAmount(Object totalAmount) {
        return findByProperty(TOTAL_AMOUNT, totalAmount);
    }

    public List findByDiscountId(Object discountId) {
        return findByProperty(DISCOUNT_ID, discountId);
    }

    public List findByTotalDiscountAmount(Object totalDiscountAmount) {
        return findByProperty(TOTAL_DISCOUNT_AMOUNT, totalDiscountAmount);
    }

    public List findBySpecialDiscountAmount(Object specialDiscountAmount) {
        return findByProperty(SPECIAL_DISCOUNT_AMOUNT, specialDiscountAmount);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findAll() {
        log.debug("finding all CleanerTicketItem instances");
        try {
            String queryString = "from CleanerTicketItem";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CleanerTicketItem merge(CleanerTicketItem detachedInstance) {
        log.debug("merging CleanerTicketItem instance");
        try {
            CleanerTicketItem result = (CleanerTicketItem) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CleanerTicketItem instance) {
        log.debug("attaching dirty CleanerTicketItem instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CleanerTicketItem instance) {
        log.debug("attaching clean CleanerTicketItem instance");
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
