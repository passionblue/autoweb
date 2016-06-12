package com.autosite.db;

/**
 * CleanerServiceItem entity. @author MyEclipse Persistence Tools
 */

public class CleanerServiceItem extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long serviceId;
    private long serviceItemId;
    private int itemType;
    private String title;
    private String imagePath;
    private String imagePathLocal;
    private double basePrice;
    private String note;

    // Constructors

    /** default constructor */
    public CleanerServiceItem() {
    }

    /** minimal constructor */
    public CleanerServiceItem(long siteId, long serviceId, long serviceItemId, int itemType, String title, double basePrice) {
        this.siteId = siteId;
        this.serviceId = serviceId;
        this.serviceItemId = serviceItemId;
        this.itemType = itemType;
        this.title = title;
        this.basePrice = basePrice;
    }

    /** full constructor */
    public CleanerServiceItem(long siteId, long serviceId, long serviceItemId, int itemType, String title, String imagePath, String imagePathLocal, double basePrice, String note) {
        this.siteId = siteId;
        this.serviceId = serviceId;
        this.serviceItemId = serviceItemId;
        this.itemType = itemType;
        this.title = title;
        this.imagePath = imagePath;
        this.imagePathLocal = imagePathLocal;
        this.basePrice = basePrice;
        this.note = note;
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

    public long getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public long getServiceItemId() {
        return this.serviceItemId;
    }

    public void setServiceItemId(long serviceItemId) {
        this.serviceItemId = serviceItemId;
    }

    public int getItemType() {
        return this.itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
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

    public double getBasePrice() {
        return this.basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
