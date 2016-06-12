package com.autosite.db;

import java.sql.Timestamp;

/**
 * DisplayOrder entity. @author MyEclipse Persistence Tools
 */

public class DisplayOrder extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String orderKey;
    private String orderList;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public DisplayOrder() {
    }

    /** minimal constructor */
    public DisplayOrder(long siteId, String orderKey, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.orderKey = orderKey;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public DisplayOrder(long siteId, String orderKey, String orderList, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.orderKey = orderKey;
        this.orderList = orderList;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
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

    public String getOrderKey() {
        return this.orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getOrderList() {
        return this.orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

}
