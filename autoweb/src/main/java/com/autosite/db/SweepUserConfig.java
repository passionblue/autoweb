package com.autosite.db;

/**
 * SweepUserConfig entity. @author MyEclipse Persistence Tools
 */

public class SweepUserConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private int maxSweepAllowed;

    // Constructors

    /** default constructor */
    public SweepUserConfig() {
    }

    /** full constructor */
    public SweepUserConfig(long siteId, long userId, int maxSweepAllowed) {
        this.siteId = siteId;
        this.userId = userId;
        this.maxSweepAllowed = maxSweepAllowed;
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

    public int getMaxSweepAllowed() {
        return this.maxSweepAllowed;
    }

    public void setMaxSweepAllowed(int maxSweepAllowed) {
        this.maxSweepAllowed = maxSweepAllowed;
    }

}
