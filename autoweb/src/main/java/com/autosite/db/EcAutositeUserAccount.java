package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcAutositeUserAccount entity. @author MyEclipse Persistence Tools
 */

public class EcAutositeUserAccount extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private String firstName;
    private String lastName;
    private int subscribed;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EcAutositeUserAccount() {
    }

    /** minimal constructor */
    public EcAutositeUserAccount(String firstName, String lastName, Timestamp timeCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public EcAutositeUserAccount(long siteId, long userId, String firstName, String lastName, int subscribed, Timestamp timeCreated) {
        this.siteId = siteId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subscribed = subscribed;
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

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSubscribed() {
        return this.subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
