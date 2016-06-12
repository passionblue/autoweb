package com.autosite.db;

import java.util.Date;

/**
 * ForumCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ForumCategory implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String category;
    private Date timeCreated;

    // Constructors

    /** default constructor */
    public ForumCategory() {
    }

    /** minimal constructor */
    public ForumCategory(long siteId, String category) {
        this.siteId = siteId;
        this.category = category;
    }

    /** full constructor */
    public ForumCategory(long siteId, String category, Date timeCreated) {
        this.siteId = siteId;
        this.category = category;
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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

}
