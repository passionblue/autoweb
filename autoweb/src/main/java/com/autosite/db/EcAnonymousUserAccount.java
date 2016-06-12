package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcAnonymousUserAccount entity. @author MyEclipse Persistence Tools
 */

public class EcAnonymousUserAccount extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String email;
    private int subscribed;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EcAnonymousUserAccount() {
    }

    /** minimal constructor */
    public EcAnonymousUserAccount(long siteId, String email, Timestamp timeCreated) {
        this.siteId = siteId;
        this.email = email;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public EcAnonymousUserAccount(long siteId, String email, int subscribed, Timestamp timeCreated) {
        this.siteId = siteId;
        this.email = email;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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
