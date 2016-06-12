package com.autosite.db;

import java.sql.Timestamp;

/**
 * Linkto entity. @author MyEclipse Persistence Tools
 */

public class Linkto extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String linkKey;
    private String linkTarget;
    private int disable;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public Linkto() {
    }

    /** minimal constructor */
    public Linkto(long siteId, String linkKey, String linkTarget, Timestamp timeCreated) {
        this.siteId = siteId;
        this.linkKey = linkKey;
        this.linkTarget = linkTarget;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public Linkto(long siteId, String linkKey, String linkTarget, int disable, Timestamp timeCreated) {
        this.siteId = siteId;
        this.linkKey = linkKey;
        this.linkTarget = linkTarget;
        this.disable = disable;
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

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public String getLinkTarget() {
        return this.linkTarget;
    }

    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    public int getDisable() {
        return this.disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
