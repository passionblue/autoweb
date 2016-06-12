package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeDefaults entity. @author MyEclipse Persistence Tools
 */

public class AutositeDefaults extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long styleId;
    private long linkStyleId;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public AutositeDefaults() {
    }

    /** minimal constructor */
    public AutositeDefaults(long siteId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public AutositeDefaults(long siteId, long styleId, long linkStyleId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.styleId = styleId;
        this.linkStyleId = linkStyleId;
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

    public long getStyleId() {
        return this.styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public long getLinkStyleId() {
        return this.linkStyleId;
    }

    public void setLinkStyleId(long linkStyleId) {
        this.linkStyleId = linkStyleId;
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
