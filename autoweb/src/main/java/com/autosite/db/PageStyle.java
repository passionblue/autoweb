package com.autosite.db;

/**
 * PageStyle entity. @author MyEclipse Persistence Tools
 */

public class PageStyle extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pageId;
    private long styleId;

    // Constructors

    /** default constructor */
    public PageStyle() {
    }

    /** full constructor */
    public PageStyle(long siteId, long pageId, long styleId) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.styleId = styleId;
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

    public long getPageId() {
        return this.pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public long getStyleId() {
        return this.styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

}
