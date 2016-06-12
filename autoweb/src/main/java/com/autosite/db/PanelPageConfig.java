package com.autosite.db;

import java.sql.Timestamp;

/**
 * PanelPageConfig entity. @author MyEclipse Persistence Tools
 */

public class PanelPageConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long panelId;
    private int pageDisplaySummary;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public PanelPageConfig() {
    }

    /** minimal constructor */
    public PanelPageConfig(long siteId, long panelId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public PanelPageConfig(long siteId, long panelId, int pageDisplaySummary, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.pageDisplaySummary = pageDisplaySummary;
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

    public int getPageDisplaySummary() {
        return this.pageDisplaySummary;
    }

    public void setPageDisplaySummary(int pageDisplaySummary) {
        this.pageDisplaySummary = pageDisplaySummary;
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
