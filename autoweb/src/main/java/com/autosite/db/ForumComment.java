package com.autosite.db;

import java.util.Date;

/**
 * ForumComment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ForumComment implements java.io.Serializable {

    // Fields

    private long id;
    private long postId;
    private long userId;
    private String comment;
    private Date timeCreated;

    // Constructors

    /** default constructor */
    public ForumComment() {
    }

    /** full constructor */
    public ForumComment(long postId, long userId, String comment, Date timeCreated) {
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.timeCreated = timeCreated;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return this.postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
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

    public Date getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

}
