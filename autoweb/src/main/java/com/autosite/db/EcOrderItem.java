package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcOrderItem entity. @author MyEclipse Persistence Tools
 */

public class EcOrderItem extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long orderId;
    private long productId;
    private String siteSku;
    private String sizeVariation;
    private String colorVariation;
    private int qty;
    private double unitPrice;
    private double orderPrice;
    private Timestamp timeCreated;
    private long packageId;

    // Constructors

    /** default constructor */
    public EcOrderItem() {
    }

    /** minimal constructor */
    public EcOrderItem(long siteId, long orderId, long productId, String siteSku, Timestamp timeCreated) {
        this.siteId = siteId;
        this.orderId = orderId;
        this.productId = productId;
        this.siteSku = siteSku;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public EcOrderItem(long siteId, long orderId, long productId, String siteSku, String sizeVariation, String colorVariation, int qty, double unitPrice, double orderPrice, Timestamp timeCreated,
            long packageId) {
        this.siteId = siteId;
        this.orderId = orderId;
        this.productId = productId;
        this.siteSku = siteSku;
        this.sizeVariation = sizeVariation;
        this.colorVariation = colorVariation;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.orderPrice = orderPrice;
        this.timeCreated = timeCreated;
        this.packageId = packageId;
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

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getSiteSku() {
        return this.siteSku;
    }

    public void setSiteSku(String siteSku) {
        this.siteSku = siteSku;
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

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getPackageId() {
        return this.packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

}
