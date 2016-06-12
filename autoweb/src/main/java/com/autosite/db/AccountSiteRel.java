package com.autosite.db;

import java.sql.Timestamp;

/**
 * AccountSiteRel entity. @author MyEclipse Persistence Tools
 */

public class AccountSiteRel extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long accountId;
    private long siteId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public AccountSiteRel() {
    }

    /** full constructor */
    public AccountSiteRel(long accountId, long siteId, Timestamp timeCreated) {
        this.accountId = accountId;
        this.siteId = siteId;
        this.timeCreated = timeCreated;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
