package com.autosite.db;

import java.util.Date;

/**
 * ForumPost entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ForumPost  extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long userId;
    private long subjectId;
    private long postId;
    private String data;
    private Date timeCreated;
    private Date timeUpdated;

    // Constructors

    /** default constructor */
    public ForumPost() {
    }

    /** minimal constructor */
    public ForumPost(long userId, long subjectId, Date timeCreated, Date timeUpdated) {
        this.userId = userId;
        this.subjectId = subjectId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public ForumPost(long userId, long subjectId, long postId, String data, Date timeCreated, Date timeUpdated) {
        this.userId = userId;
        this.subjectId = subjectId;
        this.postId = postId;
        this.data = data;
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

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getPostId() {
        return this.postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Date timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

}
