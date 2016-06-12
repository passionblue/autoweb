package com.autosite.db;

/**
 * CleanerProduct entity. @author MyEclipse Persistence Tools
 */

public class CleanerProduct extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long garmentTypeId;
    private long garmentServiceId;
    private double regularPrice;
    private String note;

    // Constructors

    /** default constructor */
    public CleanerProduct() {
    }

    /** minimal constructor */
    public CleanerProduct(long siteId, long garmentTypeId, long garmentServiceId, double regularPrice) {
        this.siteId = siteId;
        this.garmentTypeId = garmentTypeId;
        this.garmentServiceId = garmentServiceId;
        this.regularPrice = regularPrice;
    }

    /** full constructor */
    public CleanerProduct(long siteId, long garmentTypeId, long garmentServiceId, double regularPrice, String note) {
        this.siteId = siteId;
        this.garmentTypeId = garmentTypeId;
        this.garmentServiceId = garmentServiceId;
        this.regularPrice = regularPrice;
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

    public long getGarmentTypeId() {
        return this.garmentTypeId;
    }

    public void setGarmentTypeId(long garmentTypeId) {
        this.garmentTypeId = garmentTypeId;
    }

    public long getGarmentServiceId() {
        return this.garmentServiceId;
    }

    public void setGarmentServiceId(long garmentServiceId) {
        this.garmentServiceId = garmentServiceId;
    }

    public double getRegularPrice() {
        return this.regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
