package com.autosite.db;

import java.sql.Timestamp;

/**
 * PanelMenuOrder entity. @author MyEclipse Persistence Tools
 */

public class PanelMenuOrder extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long panelId;
    private String orderedIds;
    private int reverse;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public PanelMenuOrder() {
    }

    /** minimal constructor */
    public PanelMenuOrder(long siteId, long panelId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public PanelMenuOrder(long siteId, long panelId, String orderedIds, int reverse, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.orderedIds = orderedIds;
        this.reverse = reverse;
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

    public long getPanelId() {
        return this.panelId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public String getOrderedIds() {
        return this.orderedIds;
    }

    public void setOrderedIds(String orderedIds) {
        this.orderedIds = orderedIds;
    }

    public int getReverse() {
        return this.reverse;
    }

    public void setReverse(int reverse) {
        this.reverse = reverse;
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
