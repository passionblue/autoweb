package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerUser entity. @author MyEclipse Persistence Tools
 */

public class CleanerUser extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long autositeUserId;
    private int inactivated;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public CleanerUser() {
    }

    /** full constructor */
    public CleanerUser(long siteId, long autositeUserId, int inactivated, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.autositeUserId = autositeUserId;
        this.inactivated = inactivated;
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

    public long getAutositeUserId() {
        return this.autositeUserId;
    }

    public void setAutositeUserId(long autositeUserId) {
        this.autositeUserId = autositeUserId;
    }

    public int getInactivated() {
        return this.inactivated;
    }

    public void setInactivated(int inactivated) {
        this.inactivated = inactivated;
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
