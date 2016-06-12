package com.autosite.db;

/**
 * EcCategory entity. @author MyEclipse Persistence Tools
 */

public class EcCategory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long parentId;
    private long pageId;
    private String categoryName;
    private String imageUrl;
    private String categoryDescription;
    private String alt1;
    private String alt2;

    // Constructors

    /** default constructor */
    public EcCategory() {
    }

    /** minimal constructor */
    public EcCategory(long siteId, long parentId, long pageId, String categoryName) {
        this.siteId = siteId;
        this.parentId = parentId;
        this.pageId = pageId;
        this.categoryName = categoryName;
    }

    /** full constructor */
    public EcCategory(long siteId, long parentId, long pageId, String categoryName, String imageUrl, String categoryDescription, String alt1, String alt2) {
        this.siteId = siteId;
        this.parentId = parentId;
        this.pageId = pageId;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.categoryDescription = categoryDescription;
        this.alt1 = alt1;
        this.alt2 = alt2;
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

    public long getParentId() {
        return this.parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getPageId() {
        return this.pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryDescription() {
        return this.categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getAlt1() {
        return this.alt1;
    }

    public void setAlt1(String alt1) {
        this.alt1 = alt1;
    }

    public String getAlt2() {
        return this.alt2;
    }

    public void setAlt2(String alt2) {
        this.alt2 = alt2;
    }

}
