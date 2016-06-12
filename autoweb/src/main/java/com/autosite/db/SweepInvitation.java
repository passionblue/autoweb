package com.autosite.db;

import java.sql.Timestamp;

/**
 * SweepInvitation entity. @author MyEclipse Persistence Tools
 */

public class SweepInvitation extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private String email;
    private String message;
    private int invitationSent;
    private Timestamp timeCreated;
    private Timestamp timeSent;

    // Constructors

    /** default constructor */
    public SweepInvitation() {
    }

    /** minimal constructor */
    public SweepInvitation(long siteId, long userId, String email, Timestamp timeCreated, Timestamp timeSent) {
        this.siteId = siteId;
        this.userId = userId;
        this.email = email;
        this.timeCreated = timeCreated;
        this.timeSent = timeSent;
    }

    /** full constructor */
    public SweepInvitation(long siteId, long userId, String email, String message, int invitationSent, Timestamp timeCreated, Timestamp timeSent) {
        this.siteId = siteId;
        this.userId = userId;
        this.email = email;
        this.message = message;
        this.invitationSent = invitationSent;
        this.timeCreated = timeCreated;
        this.timeSent = timeSent;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getInvitationSent() {
        return this.invitationSent;
    }

    public void setInvitationSent(int invitationSent) {
        this.invitationSent = invitationSent;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeSent() {
        return this.timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

}
