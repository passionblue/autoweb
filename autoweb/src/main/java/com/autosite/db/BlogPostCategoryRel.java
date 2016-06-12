package com.autosite.db;

import java.sql.Timestamp;

/**
 * BlogPostCategoryRel entity. @author MyEclipse Persistence Tools
 */

public class BlogPostCategoryRel extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long blogPostId;
    private long blogCategoryId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public BlogPostCategoryRel() {
    }

    /** full constructor */
    public BlogPostCategoryRel(long siteId, long blogPostId, long blogCategoryId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.blogPostId = blogPostId;
        this.blogCategoryId = blogCategoryId;
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

    public long getBlogPostId() {
        return this.blogPostId;
    }

    public void setBlogPostId(long blogPostId) {
        this.blogPostId = blogPostId;
    }

    public long getBlogCategoryId() {
        return this.blogCategoryId;
    }

    public void setBlogCategoryId(long blogCategoryId) {
        this.blogCategoryId = blogCategoryId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
