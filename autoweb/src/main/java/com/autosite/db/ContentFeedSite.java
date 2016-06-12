package com.autosite.db;

import java.sql.Timestamp;

/**
 * ContentFeedSite entity. @author MyEclipse Persistence Tools
 */

public class ContentFeedSite extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long contentFeedId;
    private int displayType;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ContentFeedSite() {
    }

    /** minimal constructor */
    public ContentFeedSite(long siteId, long contentFeedId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.contentFeedId = contentFeedId;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public ContentFeedSite(long siteId, long contentFeedId, int displayType, Timestamp timeCreated) {
        this.siteId = siteId;
        this.contentFeedId = contentFeedId;
        this.displayType = displayType;
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

    public long getContentFeedId() {
        return this.contentFeedId;
    }

    public void setContentFeedId(long contentFeedId) {
        this.contentFeedId = contentFeedId;
    }

    public int getDisplayType() {
        return this.displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
