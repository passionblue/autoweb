package com.autosite.db;

import java.sql.Timestamp;

/**
 * Section entity. @author MyEclipse Persistence Tools
 */

public class Section extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private long mainPageId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public Section() {
    }

    /** minimal constructor */
    public Section(long siteId, String title) {
        this.siteId = siteId;
        this.title = title;
    }

    /** full constructor */
    public Section(long siteId, String title, long mainPageId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.title = title;
        this.mainPageId = mainPageId;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMainPageId() {
        return this.mainPageId;
    }

    public void setMainPageId(long mainPageId) {
        this.mainPageId = mainPageId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
