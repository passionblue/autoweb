package com.autosite.db;

import java.sql.Timestamp;

/**
 * ForumMain entity. @author MyEclipse Persistence Tools
 */

public class ForumMain extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ForumMain() {
    }

    /** full constructor */
    public ForumMain(long siteId, String title, Timestamp timeCreated) {
        this.siteId = siteId;
        this.title = title;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
