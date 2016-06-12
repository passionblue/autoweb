package com.autosite.db;

/**
 * CleanerLocation entity. @author MyEclipse Persistence Tools
 */

public class CleanerLocation extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String address;
    private String cityZip;
    private String phone;
    private long managerId;

    // Constructors

    /** default constructor */
    public CleanerLocation() {
    }

    /** minimal constructor */
    public CleanerLocation(long siteId, String address, String cityZip, String phone) {
        this.siteId = siteId;
        this.address = address;
        this.cityZip = cityZip;
        this.phone = phone;
    }

    /** full constructor */
    public CleanerLocation(long siteId, String address, String cityZip, String phone, long managerId) {
        this.siteId = siteId;
        this.address = address;
        this.cityZip = cityZip;
        this.phone = phone;
        this.managerId = managerId;
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityZip() {
        return this.cityZip;
    }

    public void setCityZip(String cityZip) {
        this.cityZip = cityZip;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getManagerId() {
        return this.managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

}
