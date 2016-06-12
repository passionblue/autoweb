package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcUserShippingInfoId entity. @author MyEclipse Persistence Tools
 */

public class EcUserShippingInfoId extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private String shippingName;
    private long firstName;
    private long lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String specialInstruction;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EcUserShippingInfoId() {
    }

    /** minimal constructor */
    public EcUserShippingInfoId(long id, long siteId, long userId, long firstName, long lastName, String address1, String city, String state, String zip, String country) {
        this.id = id;
        this.siteId = siteId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /** full constructor */
    public EcUserShippingInfoId(long id, long siteId, long userId, String shippingName, long firstName, long lastName, String address1, String address2, String city, String state, String zip,
            String country, String specialInstruction, Timestamp timeCreated) {
        this.id = id;
        this.siteId = siteId;
        this.userId = userId;
        this.shippingName = shippingName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.specialInstruction = specialInstruction;
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

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getShippingName() {
        return this.shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public long getFirstName() {
        return this.firstName;
    }

    public void setFirstName(long firstName) {
        this.firstName = firstName;
    }

    public long getLastName() {
        return this.lastName;
    }

    public void setLastName(long lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSpecialInstruction() {
        return this.specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof EcUserShippingInfoId))
            return false;
        EcUserShippingInfoId castOther = (EcUserShippingInfoId) other;

        return (this.getId() == castOther.getId())
                && (this.getSiteId() == castOther.getSiteId())
                && (this.getUserId() == castOther.getUserId())
                && ((this.getShippingName() == castOther.getShippingName()) || (this.getShippingName() != null && castOther.getShippingName() != null && this.getShippingName().equals(
                        castOther.getShippingName())))
                && (this.getFirstName() == castOther.getFirstName())
                && (this.getLastName() == castOther.getLastName())
                && ((this.getAddress1() == castOther.getAddress1()) || (this.getAddress1() != null && castOther.getAddress1() != null && this.getAddress1().equals(castOther.getAddress1())))
                && ((this.getAddress2() == castOther.getAddress2()) || (this.getAddress2() != null && castOther.getAddress2() != null && this.getAddress2().equals(castOther.getAddress2())))
                && ((this.getCity() == castOther.getCity()) || (this.getCity() != null && castOther.getCity() != null && this.getCity().equals(castOther.getCity())))
                && ((this.getState() == castOther.getState()) || (this.getState() != null && castOther.getState() != null && this.getState().equals(castOther.getState())))
                && ((this.getZip() == castOther.getZip()) || (this.getZip() != null && castOther.getZip() != null && this.getZip().equals(castOther.getZip())))
                && ((this.getCountry() == castOther.getCountry()) || (this.getCountry() != null && castOther.getCountry() != null && this.getCountry().equals(castOther.getCountry())))
                && ((this.getSpecialInstruction() == castOther.getSpecialInstruction()) || (this.getSpecialInstruction() != null && castOther.getSpecialInstruction() != null && this
                        .getSpecialInstruction().equals(castOther.getSpecialInstruction())))
                && ((this.getTimeCreated() == castOther.getTimeCreated()) || (this.getTimeCreated() != null && castOther.getTimeCreated() != null && this.getTimeCreated().equals(
                        castOther.getTimeCreated())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (int) this.getId();
        result = 37 * result + (int) this.getSiteId();
        result = 37 * result + (int) this.getUserId();
        result = 37 * result + (getShippingName() == null ? 0 : this.getShippingName().hashCode());
        result = 37 * result + (int) this.getFirstName();
        result = 37 * result + (int) this.getLastName();
        result = 37 * result + (getAddress1() == null ? 0 : this.getAddress1().hashCode());
        result = 37 * result + (getAddress2() == null ? 0 : this.getAddress2().hashCode());
        result = 37 * result + (getCity() == null ? 0 : this.getCity().hashCode());
        result = 37 * result + (getState() == null ? 0 : this.getState().hashCode());
        result = 37 * result + (getZip() == null ? 0 : this.getZip().hashCode());
        result = 37 * result + (getCountry() == null ? 0 : this.getCountry().hashCode());
        result = 37 * result + (getSpecialInstruction() == null ? 0 : this.getSpecialInstruction().hashCode());
        result = 37 * result + (getTimeCreated() == null ? 0 : this.getTimeCreated().hashCode());
        return result;
    }

}
