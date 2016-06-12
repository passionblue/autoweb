package com.autosite.db;

import java.sql.Timestamp;

/**
 * ContentFeedConfig entity. @author MyEclipse Persistence Tools
 */

public class ContentFeedConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String feedCategory;
    private int feedType;
    private int displayType;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ContentFeedConfig() {
    }

    /** minimal constructor */
    public ContentFeedConfig(long siteId, String feedCategory, int displayType) {
        this.siteId = siteId;
        this.feedCategory = feedCategory;
        this.displayType = displayType;
    }

    /** full constructor */
    public ContentFeedConfig(long siteId, String feedCategory, int feedType, int displayType, Timestamp timeCreated) {
        this.siteId = siteId;
        this.feedCategory = feedCategory;
        this.feedType = feedType;
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

    public String getFeedCategory() {
        return this.feedCategory;
    }

    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }

    public int getFeedType() {
        return this.feedType;
    }

    public void setFeedType(int feedType) {
        this.feedType = feedType;
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
