package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcWishList entity. @author MyEclipse Persistence Tools
 */

public class EcWishList extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private long productId;
    private String sizeVariation;
    private String colorVariation;
    private double savedPrice;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EcWishList() {
    }

    /** minimal constructor */
    public EcWishList(long siteId, long userId, double savedPrice, Timestamp timeCreated) {
        this.siteId = siteId;
        this.userId = userId;
        this.savedPrice = savedPrice;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public EcWishList(long siteId, long userId, long productId, String sizeVariation, String colorVariation, double savedPrice, Timestamp timeCreated) {
        this.siteId = siteId;
        this.userId = userId;
        this.productId = productId;
        this.sizeVariation = sizeVariation;
        this.colorVariation = colorVariation;
        this.savedPrice = savedPrice;
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

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getSizeVariation() {
        return this.sizeVariation;
    }

    public void setSizeVariation(String sizeVariation) {
        this.sizeVariation = sizeVariation;
    }

    public String getColorVariation() {
        return this.colorVariation;
    }

    public void setColorVariation(String colorVariation) {
        this.colorVariation = colorVariation;
    }

    public double getSavedPrice() {
        return this.savedPrice;
    }

    public void setSavedPrice(double savedPrice) {
        this.savedPrice = savedPrice;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
