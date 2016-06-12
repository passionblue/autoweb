package com.autosite.db;

import java.sql.Timestamp;

/**
 * SiteConfigStyle entity. @author MyEclipse Persistence Tools
 */

public class SiteConfigStyle extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long themeId;
    private int cssIndex;
    private String cssImport;
    private int layoutIndex;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public SiteConfigStyle() {
    }

    /** minimal constructor */
    public SiteConfigStyle(long siteId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public SiteConfigStyle(long siteId, long themeId, int cssIndex, String cssImport, int layoutIndex, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.themeId = themeId;
        this.cssIndex = cssIndex;
        this.cssImport = cssImport;
        this.layoutIndex = layoutIndex;
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

    public long getThemeId() {
        return this.themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public int getCssIndex() {
        return this.cssIndex;
    }

    public void setCssIndex(int cssIndex) {
        this.cssIndex = cssIndex;
    }

    public String getCssImport() {
        return this.cssImport;
    }

    public void setCssImport(String cssImport) {
        this.cssImport = cssImport;
    }

    public int getLayoutIndex() {
        return this.layoutIndex;
    }

    public void setLayoutIndex(int layoutIndex) {
        this.layoutIndex = layoutIndex;
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
