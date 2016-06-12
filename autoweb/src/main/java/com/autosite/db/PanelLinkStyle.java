package com.autosite.db;

import java.sql.Timestamp;

/**
 * PanelLinkStyle entity. @author MyEclipse Persistence Tools
 */

public class PanelLinkStyle extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long panelId;
    private long styleId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public PanelLinkStyle() {
    }

    /** full constructor */
    public PanelLinkStyle(long siteId, long panelId, long styleId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.styleId = styleId;
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

    public long getPanelId() {
        return this.panelId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public long getStyleId() {
        return this.styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
