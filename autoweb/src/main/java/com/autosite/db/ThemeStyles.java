package com.autosite.db;

import java.sql.Timestamp;

/**
 * ThemeStyles entity. @author MyEclipse Persistence Tools
 */

public class ThemeStyles extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private int bodyWidth;
    private String bodyAlign;
    private String bodyBackground;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public ThemeStyles() {
    }

    /** minimal constructor */
    public ThemeStyles(long siteId, String bodyAlign, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.bodyAlign = bodyAlign;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public ThemeStyles(long siteId, int bodyWidth, String bodyAlign, String bodyBackground, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.bodyWidth = bodyWidth;
        this.bodyAlign = bodyAlign;
        this.bodyBackground = bodyBackground;
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

    public int getBodyWidth() {
        return this.bodyWidth;
    }

    public void setBodyWidth(int bodyWidth) {
        this.bodyWidth = bodyWidth;
    }

    public String getBodyAlign() {
        return this.bodyAlign;
    }

    public void setBodyAlign(String bodyAlign) {
        this.bodyAlign = bodyAlign;
    }

    public String getBodyBackground() {
        return this.bodyBackground;
    }

    public void setBodyBackground(String bodyBackground) {
        this.bodyBackground = bodyBackground;
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
