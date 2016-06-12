package com.autosite.db;

/**
 * CleanerServiceCategory entity. @author MyEclipse Persistence Tools
 */

public class CleanerServiceCategory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private String imagePath;
    private String imagePathLocal;

    // Constructors

    /** default constructor */
    public CleanerServiceCategory() {
    }

    /** minimal constructor */
    public CleanerServiceCategory(long siteId, String title) {
        this.siteId = siteId;
        this.title = title;
    }

    /** full constructor */
    public CleanerServiceCategory(long siteId, String title, String imagePath, String imagePathLocal) {
        this.siteId = siteId;
        this.title = title;
        this.imagePath = imagePath;
        this.imagePathLocal = imagePathLocal;
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

    public String getImagePathLocal() {
        return this.imagePathLocal;
    }

    public void setImagePathLocal(String imagePathLocal) {
        this.imagePathLocal = imagePathLocal;
    }

}
