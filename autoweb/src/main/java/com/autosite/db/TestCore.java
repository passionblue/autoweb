package com.autosite.db;

import java.sql.Timestamp;

/**
 * TestCore entity. @author MyEclipse Persistence Tools
 */

public class TestCore extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String data;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public TestCore() {
    }

    /** full constructor */
    public TestCore(long siteId, String data, Timestamp timeCreated) {
        this.siteId = siteId;
        this.data = data;
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

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
