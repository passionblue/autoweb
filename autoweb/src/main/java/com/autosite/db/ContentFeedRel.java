package com.autosite.db;

import java.sql.Timestamp;

/**
 * ContentFeedRel entity. @author MyEclipse Persistence Tools
 */

public class ContentFeedRel extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long contentFeedId;
    private long contentId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ContentFeedRel() {
    }

    /** minimal constructor */
    public ContentFeedRel(long siteId, long contentFeedId, long contentId) {
        this.siteId = siteId;
        this.contentFeedId = contentFeedId;
        this.contentId = contentId;
    }

    /** full constructor */
    public ContentFeedRel(long siteId, long contentFeedId, long contentId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.contentFeedId = contentFeedId;
        this.contentId = contentId;
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

    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
