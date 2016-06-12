package com.autosite.db;

import java.sql.Timestamp;

/**
 * TestDataDepot entity. @author MyEclipse Persistence Tools
 */

public class TestDataDepot extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private String data;
    private int type;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public TestDataDepot() {
    }

    /** minimal constructor */
    public TestDataDepot(long siteId, String title, String data, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.title = title;
        this.data = data;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public TestDataDepot(long siteId, String title, String data, int type, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.title = title;
        this.data = data;
        this.type = type;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
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
