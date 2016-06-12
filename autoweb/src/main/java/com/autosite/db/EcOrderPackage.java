package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcOrderPackage entity. @author MyEclipse Persistence Tools
 */

public class EcOrderPackage extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private long orderId;
    private int numOrder;
    private int shipped;
    private Timestamp timeShipped;

    // Constructors

    /** default constructor */
    public EcOrderPackage() {
    }

    /** minimal constructor */
    public EcOrderPackage(long siteId, long userId, long orderId) {
        this.siteId = siteId;
        this.userId = userId;
        this.orderId = orderId;
    }

    /** full constructor */
    public EcOrderPackage(long siteId, long userId, long orderId, int numOrder, int shipped, Timestamp timeShipped) {
        this.siteId = siteId;
        this.userId = userId;
        this.orderId = orderId;
        this.numOrder = numOrder;
        this.shipped = shipped;
        this.timeShipped = timeShipped;
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

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getNumOrder() {
        return this.numOrder;
    }

    public void setNumOrder(int numOrder) {
        this.numOrder = numOrder;
    }

    public int getShipped() {
        return this.shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public Timestamp getTimeShipped() {
        return this.timeShipped;
    }

    public void setTimeShipped(Timestamp timeShipped) {
        this.timeShipped = timeShipped;
    }

}
