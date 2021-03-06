/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:41 EDT 2015
*/

package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* DAO is generated by Eclipse. This is by AutoGen
 *
 */
public class ExpenseCategoryDAO2  extends BaseHibernateDAO  {
    private static Logger log = LoggerFactory.getLogger(ExpenseCategoryDAO2.class);

	public static final String ID = "id";
	public static final String SITE_ID = "siteId";
	public static final String EXPENSE_CATEGORY = "expenseCategory";

    public void save(ExpenseCategory transientInstance) {
        log.debug("saving ExpenseCategory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ExpenseCategory persistentInstance) {
        log.debug("deleting ExpenseCategory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ExpenseCategory findById(Long id) {
        log.debug("getting ExpenseCategory instance with id: " + id);
        try {
            ExpenseCategory instance = (ExpenseCategory) getSession().get("com.autosite.db.ExpenseCategory", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ExpenseCategory instance) {
        log.debug("finding ExpenseCategory instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.ExpenseCategory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ExpenseCategory instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ExpenseCategory as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findById(Object id) {
        return findByProperty(ID, id);
    }
    public List findBySiteId(Object siteId) {
        return findByProperty(SITE_ID, siteId);
    }
    public List findByExpenseCategory(Object expenseCategory) {
        return findByProperty(EXPENSE_CATEGORY, expenseCategory);
    }

    public List findAll() {
        log.debug("finding all ExpenseCategory instances");
        try {
            String queryString = "from ExpenseCategory";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ExpenseCategory merge(ExpenseCategory detachedInstance) {
        log.debug("merging ExpenseCategory instance");
        try {
            ExpenseCategory result = (ExpenseCategory) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ExpenseCategory instance) {
        log.debug("attaching dirty ExpenseCategory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ExpenseCategory instance) {
        log.debug("attaching clean ExpenseCategory instance");
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