package com.autosite.db;

import java.sql.Timestamp;

/**
 * StyleSet entity. @author MyEclipse Persistence Tools
 */

public class StyleSet extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String name;
    private long styleId;
    private long dataStyleId;
    private long linkStyleId;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public StyleSet() {
    }

    /** full constructor */
    public StyleSet(long siteId, String name, long styleId, long dataStyleId, long linkStyleId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.name = name;
        this.styleId = styleId;
        this.dataStyleId = dataStyleId;
        this.linkStyleId = linkStyleId;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStyleId() {
        return this.styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public long getDataStyleId() {
        return this.dataStyleId;
    }

    public void setDataStyleId(long dataStyleId) {
        this.dataStyleId = dataStyleId;
    }

    public long getLinkStyleId() {
        return this.linkStyleId;
    }

    public void setLinkStyleId(long linkStyleId) {
        this.linkStyleId = linkStyleId;
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
