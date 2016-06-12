package com.autosite.db;

import java.sql.Timestamp;

/**
 * BlogCategory entity. @author MyEclipse Persistence Tools
 */

public class BlogCategory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long blogMainId;
    private long parentCategoryId;
    private long rootCategoryId;
    private String title;
    private int hide;
    private String imageUrl1;
    private String imageUrl2;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public BlogCategory() {
    }

    /** minimal constructor */
    public BlogCategory(long siteId, long blogMainId, String title) {
        this.siteId = siteId;
        this.blogMainId = blogMainId;
        this.title = title;
    }

    /** full constructor */
    public BlogCategory(long siteId, long blogMainId, long parentCategoryId, long rootCategoryId, String title, int hide, String imageUrl1, String imageUrl2, Timestamp timeCreated) {
        this.siteId = siteId;
        this.blogMainId = blogMainId;
        this.parentCategoryId = parentCategoryId;
        this.rootCategoryId = rootCategoryId;
        this.title = title;
        this.hide = hide;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
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

    public long getBlogMainId() {
        return this.blogMainId;
    }

    public void setBlogMainId(long blogMainId) {
        this.blogMainId = blogMainId;
    }

    public long getParentCategoryId() {
        return this.parentCategoryId;
    }

    public void setParentCategoryId(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public long getRootCategoryId() {
        return this.rootCategoryId;
    }

    public void setRootCategoryId(long rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public String getImageUrl1() {
        return this.imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return this.imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
