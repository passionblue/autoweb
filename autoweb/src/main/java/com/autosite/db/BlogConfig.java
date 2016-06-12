package com.autosite.db;

/**
 * BlogConfig entity. @author MyEclipse Persistence Tools
 */

public class BlogConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long blogMainId;

    // Constructors

    /** default constructor */
    public BlogConfig() {
    }

    /** full constructor */
    public BlogConfig(long siteId, long blogMainId) {
        this.siteId = siteId;
        this.blogMainId = blogMainId;
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

    public long getBlogMainId() {
        return this.blogMainId;
    }

    public void setBlogMainId(long blogMainId) {
        this.blogMainId = blogMainId;
    }

}
