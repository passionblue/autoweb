package com.autosite.db;

import java.sql.Timestamp;

/**
 * ContentCategory entity. @author MyEclipse Persistence Tools
 */

public class ContentCategory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pageId;
    private String category;
    private String imageUrl;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ContentCategory() {
    }

    /** minimal constructor */
    public ContentCategory(long siteId, long pageId, String category) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.category = category;
    }

    /** full constructor */
    public ContentCategory(long siteId, long pageId, String category, String imageUrl, Timestamp timeCreated) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.category = category;
        this.imageUrl = imageUrl;
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

    public long getPageId() {
        return this.pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
