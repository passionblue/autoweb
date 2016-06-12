package com.autosite.db;

import java.sql.Timestamp;

/**
 * ForumConfig entity. @author MyEclipse Persistence Tools
 */

public class ForumConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long forumMainId;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public ForumConfig() {
    }

    /** full constructor */
    public ForumConfig(long siteId, long forumMainId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.forumMainId = forumMainId;
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

    public long getForumMainId() {
        return this.forumMainId;
    }

    public void setForumMainId(long forumMainId) {
        this.forumMainId = forumMainId;
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
