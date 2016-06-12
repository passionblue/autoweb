package com.autosite.db;

import java.sql.Timestamp;

/**
 * BlogMain entity. @author MyEclipse Persistence Tools
 */

public class BlogMain extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private String blogName;
    private String blogTitle;
    private Timestamp timeCreated;
    private int mainDesignSelect;
    private int useCustomDesign;
    private String customDesignFile;

    // Constructors

    /** default constructor */
    public BlogMain() {
    }

    /** minimal constructor */
    public BlogMain(long siteId, String blogName, String blogTitle, Timestamp timeCreated) {
        this.siteId = siteId;
        this.blogName = blogName;
        this.blogTitle = blogTitle;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public BlogMain(long siteId, long userId, String blogName, String blogTitle, Timestamp timeCreated, int mainDesignSelect, int useCustomDesign, String customDesignFile) {
        this.siteId = siteId;
        this.userId = userId;
        this.blogName = blogName;
        this.blogTitle = blogTitle;
        this.timeCreated = timeCreated;
        this.mainDesignSelect = mainDesignSelect;
        this.useCustomDesign = useCustomDesign;
        this.customDesignFile = customDesignFile;
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

    public String getBlogName() {
        return this.blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogTitle() {
        return this.blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int getMainDesignSelect() {
        return this.mainDesignSelect;
    }

    public void setMainDesignSelect(int mainDesignSelect) {
        this.mainDesignSelect = mainDesignSelect;
    }

    public int getUseCustomDesign() {
        return this.useCustomDesign;
    }

    public void setUseCustomDesign(int useCustomDesign) {
        this.useCustomDesign = useCustomDesign;
    }

    public String getCustomDesignFile() {
        return this.customDesignFile;
    }

    public void setCustomDesignFile(String customDesignFile) {
        this.customDesignFile = customDesignFile;
    }

}
