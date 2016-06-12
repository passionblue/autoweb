package com.autosite.db;

/**
 * CleanerGarmentType entity. @author MyEclipse Persistence Tools
 */

public class CleanerGarmentType extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private String imagePath;

    // Constructors

    /** default constructor */
    public CleanerGarmentType() {
    }

    /** minimal constructor */
    public CleanerGarmentType(long siteId) {
        this.siteId = siteId;
    }

    /** full constructor */
    public CleanerGarmentType(long siteId, String title, String imagePath) {
        this.siteId = siteId;
        this.title = title;
        this.imagePath = imagePath;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
