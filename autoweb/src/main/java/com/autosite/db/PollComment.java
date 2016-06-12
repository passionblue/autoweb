package com.autosite.db;

import java.sql.Timestamp;

/**
 * PollComment entity. @author MyEclipse Persistence Tools
 */

public class PollComment extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pollId;
    private long userId;
    private String comment;
    private int hide;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public PollComment() {
    }

    /** full constructor */
    public PollComment(long siteId, long pollId, long userId, String comment, int hide, Timestamp timeCreated) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.userId = userId;
        this.comment = comment;
        this.hide = hide;
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

    public long getPollId() {
        return this.pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
