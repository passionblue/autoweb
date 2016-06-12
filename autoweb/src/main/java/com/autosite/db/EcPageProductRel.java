package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcPageProductRel entity. @author MyEclipse Persistence Tools
 */

public class EcPageProductRel extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long productId;
    private long categoryId;
    private int hide;
    private int mainCategory;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EcPageProductRel() {
    }

    /** minimal constructor */
    public EcPageProductRel(long siteId, long productId, long categoryId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public EcPageProductRel(long siteId, long productId, long categoryId, int hide, int mainCategory, Timestamp timeCreated) {
        this.siteId = siteId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.hide = hide;
        this.mainCategory = mainCategory;
        this.timeCreated = timeCreated;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getMainCategory() {
        return this.mainCategory;
    }

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
