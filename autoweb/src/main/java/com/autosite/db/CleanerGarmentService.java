package com.autosite.db;

/**
 * CleanerGarmentService entity. @author MyEclipse Persistence Tools
 */

public class CleanerGarmentService extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private String note;
    private String imagePath;

    // Constructors

    /** default constructor */
    public CleanerGarmentService() {
    }

    /** minimal constructor */
    public CleanerGarmentService(long siteId, String title) {
        this.siteId = siteId;
        this.title = title;
    }

    /** full constructor */
    public CleanerGarmentService(long siteId, String title, String note, String imagePath) {
        this.siteId = siteId;
        this.title = title;
        this.note = note;
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

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
