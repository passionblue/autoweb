package com.autosite.db;

import java.sql.Timestamp;

/**
 * SiteHeader entity. @author MyEclipse Persistence Tools
 */

public class SiteHeader extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private int asIs;
    private int includeType;
    private String includeText;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public SiteHeader() {
    }

    /** minimal constructor */
    public SiteHeader(long siteId) {
        this.siteId = siteId;
    }

    /** full constructor */
    public SiteHeader(long siteId, int asIs, int includeType, String includeText, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.asIs = asIs;
        this.includeType = includeType;
        this.includeText = includeText;
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

    public int getAsIs() {
        return this.asIs;
    }

    public void setAsIs(int asIs) {
        this.asIs = asIs;
    }

    public int getIncludeType() {
        return this.includeType;
    }

    public void setIncludeType(int includeType) {
        this.includeType = includeType;
    }

    public String getIncludeText() {
        return this.includeText;
    }

    public void setIncludeText(String includeText) {
        this.includeText = includeText;
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
