package com.autosite.db;

import java.sql.Timestamp;

/**
 * ThemeAggregator entity. @author MyEclipse Persistence Tools
 */

public class ThemeAggregator extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String themeName;
    private String layoutPage;
    private String cssIndex;
    private long themeStyleId;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public ThemeAggregator() {
    }

    /** minimal constructor */
    public ThemeAggregator(long siteId, String themeName, String layoutPage) {
        this.siteId = siteId;
        this.themeName = themeName;
        this.layoutPage = layoutPage;
    }

    /** full constructor */
    public ThemeAggregator(long siteId, String themeName, String layoutPage, String cssIndex, long themeStyleId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.themeName = themeName;
        this.layoutPage = layoutPage;
        this.cssIndex = cssIndex;
        this.themeStyleId = themeStyleId;
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

    public String getThemeName() {
        return this.themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getLayoutPage() {
        return this.layoutPage;
    }

    public void setLayoutPage(String layoutPage) {
        this.layoutPage = layoutPage;
    }

    public String getCssIndex() {
        return this.cssIndex;
    }

    public void setCssIndex(String cssIndex) {
        this.cssIndex = cssIndex;
    }

    public long getThemeStyleId() {
        return this.themeStyleId;
    }

    public void setThemeStyleId(long themeStyleId) {
        this.themeStyleId = themeStyleId;
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
