package com.autosite.db;

import java.util.Date;

/**
 * ForumSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ForumSubject extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private long categoryId;
    private String subject;
    private Date timeCreated;

    // Constructors

    /** default constructor */
    public ForumSubject() {
    }

    /** full constructor */
    public ForumSubject(long siteId, long userId, long categoryId, String subject, Date timeCreated) {
        this.siteId = siteId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.subject = subject;
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

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

}
