package com.autosite.db;

import java.sql.Timestamp;

/**
 * SiteSuggest entity. @author MyEclipse Persistence Tools
 */

public class SiteSuggest extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String category;
    private String suggest;
    private int resolved;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public SiteSuggest() {
    }

    /** minimal constructor */
    public SiteSuggest(long siteId, String category, String suggest, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.category = category;
        this.suggest = suggest;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public SiteSuggest(long siteId, String category, String suggest, int resolved, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.category = category;
        this.suggest = suggest;
        this.resolved = resolved;
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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSuggest() {
        return this.suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public int getResolved() {
        return this.resolved;
    }

    public void setResolved(int resolved) {
        this.resolved = resolved;
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
