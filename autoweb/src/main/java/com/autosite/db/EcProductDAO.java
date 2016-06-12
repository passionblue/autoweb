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
 * EcProduct entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autosite.db.EcProduct
 * @author MyEclipse Persistence Tools
 */

public class EcProductDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(EcProductDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String FACTORY_SKU = "factorySku";
    public static final String SITE_SKU = "siteSku";
    public static final String ALT_SKU = "altSku";
    public static final String ITEM_CODE = "itemCode";
    public static final String MAKER = "maker";
    public static final String BRAND = "brand";
    public static final String NAME = "name";
    public static final String SUB_NAME = "subName";
    public static final String SHORT_DESCRIPTION = "shortDescription";
    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTION2 = "description2";
    public static final String IMAGE_URL = "imageUrl";
    public static final String COLOR = "color";
    public static final String SIZE = "size";
    public static final String MSRP = "msrp";
    public static final String MSRP_CURRENCY = "msrpCurrency";
    public static final String SALE_PRICE = "salePrice";
    public static final String SALE_ENDS = "saleEnds";
    public static final String SOLDOUT = "soldout";
    public static final String HIDE = "hide";
    public static final String PROMO_NOTE = "promoNote";

    public void save(EcProduct transientInstance) {
        log.debug("saving EcProduct instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(EcProduct persistentInstance) {
        log.debug("deleting EcProduct instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public EcProduct findById(Long id) {
        log.debug("getting EcProduct instance with id: " + id);
        try {
            EcProduct instance = (EcProduct) getSession().get("com.autosite.db.EcProduct", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(EcProduct instance) {
        log.debug("finding EcProduct instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.EcProduct").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding EcProduct instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from EcProduct as model where model." + propertyName + "= ?";
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

    public List findByCategoryId(Object categoryId) {
        return findByProperty(CATEGORY_ID, categoryId);
    }

    public List findByFactorySku(Object factorySku) {
        return findByProperty(FACTORY_SKU, factorySku);
    }

    public List findBySiteSku(Object siteSku) {
        return findByProperty(SITE_SKU, siteSku);
    }

    public List findByAltSku(Object altSku) {
        return findByProperty(ALT_SKU, altSku);
    }

    public List findByItemCode(Object itemCode) {
        return findByProperty(ITEM_CODE, itemCode);
    }

    public List findByMaker(Object maker) {
        return findByProperty(MAKER, maker);
    }

    public List findByBrand(Object brand) {
        return findByProperty(BRAND, brand);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findBySubName(Object subName) {
        return findByProperty(SUB_NAME, subName);
    }

    public List findByShortDescription(Object shortDescription) {
        return findByProperty(SHORT_DESCRIPTION, shortDescription);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByDescription2(Object description2) {
        return findByProperty(DESCRIPTION2, description2);
    }

    public List findByImageUrl(Object imageUrl) {
        return findByProperty(IMAGE_URL, imageUrl);
    }

    public List findByColor(Object color) {
        return findByProperty(COLOR, color);
    }

    public List findBySize(Object size) {
        return findByProperty(SIZE, size);
    }

    public List findByMsrp(Object msrp) {
        return findByProperty(MSRP, msrp);
    }

    public List findByMsrpCurrency(Object msrpCurrency) {
        return findByProperty(MSRP_CURRENCY, msrpCurrency);
    }

    public List findBySalePrice(Object salePrice) {
        return findByProperty(SALE_PRICE, salePrice);
    }

    public List findBySaleEnds(Object saleEnds) {
        return findByProperty(SALE_ENDS, saleEnds);
    }

    public List findBySoldout(Object soldout) {
        return findByProperty(SOLDOUT, soldout);
    }

    public List findByHide(Object hide) {
        return findByProperty(HIDE, hide);
    }

    public List findByPromoNote(Object promoNote) {
        return findByProperty(PROMO_NOTE, promoNote);
    }

    public List findAll() {
        log.debug("finding all EcProduct instances");
        try {
            String queryString = "from EcProduct";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public EcProduct merge(EcProduct detachedInstance) {
        log.debug("merging EcProduct instance");
        try {
            EcProduct result = (EcProduct) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EcProduct instance) {
        log.debug("attaching dirty EcProduct instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(EcProduct instance) {
        log.debug("attaching clean EcProduct instance");
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
