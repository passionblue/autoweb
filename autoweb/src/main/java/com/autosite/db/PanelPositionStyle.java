package com.autosite.db;

import java.sql.Timestamp;

/**
 * PanelPositionStyle entity. @author MyEclipse Persistence Tools
 */

public class PanelPositionStyle extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String col;
    private long styleId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public PanelPositionStyle() {
    }

    /** full constructor */
    public PanelPositionStyle(long siteId, String col, long styleId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.col = col;
        this.styleId = styleId;
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

    public String getCol() {
        return this.col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public long getStyleId() {
        return this.styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
