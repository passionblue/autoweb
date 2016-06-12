package com.autosite.db;

import java.sql.Timestamp;

/**
 * SiteFeatureConfig entity. @author MyEclipse Persistence Tools
 */

public class SiteFeatureConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private int userRegisterEnabled;
    private int ecEnabled;
    private int emailSubsEnabled;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public SiteFeatureConfig() {
    }

    /** minimal constructor */
    public SiteFeatureConfig(long siteId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public SiteFeatureConfig(long siteId, int userRegisterEnabled, int ecEnabled, int emailSubsEnabled, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.userRegisterEnabled = userRegisterEnabled;
        this.ecEnabled = ecEnabled;
        this.emailSubsEnabled = emailSubsEnabled;
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

    public int getUserRegisterEnabled() {
        return this.userRegisterEnabled;
    }

    public void setUserRegisterEnabled(int userRegisterEnabled) {
        this.userRegisterEnabled = userRegisterEnabled;
    }

    public int getEcEnabled() {
        return this.ecEnabled;
    }

    public void setEcEnabled(int ecEnabled) {
        this.ecEnabled = ecEnabled;
    }

    public int getEmailSubsEnabled() {
        return this.emailSubsEnabled;
    }

    public void setEmailSubsEnabled(int emailSubsEnabled) {
        this.emailSubsEnabled = emailSubsEnabled;
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
