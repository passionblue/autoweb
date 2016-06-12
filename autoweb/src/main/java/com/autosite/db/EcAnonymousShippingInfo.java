package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcAnonymousShippingInfo entity. @author MyEclipse Persistence Tools
 */

public class EcAnonymousShippingInfo extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long anonymousUserId;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String specialInstruction;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EcAnonymousShippingInfo() {
    }

    /** minimal constructor */
    public EcAnonymousShippingInfo(long siteId, long anonymousUserId, String firstName, String lastName, String address1, String city, String state, String zip, String country, String phone) {
        this.siteId = siteId;
        this.anonymousUserId = anonymousUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.phone = phone;
    }

    /** full constructor */
    public EcAnonymousShippingInfo(long siteId, long anonymousUserId, String firstName, String middleInitial, String lastName, String address1, String address2, String city, String state, String zip,
            String country, String phone, String specialInstruction, Timestamp timeCreated) {
        this.siteId = siteId;
        this.anonymousUserId = anonymousUserId;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.phone = phone;
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

    public long getAnonymousUserId() {
        return this.anonymousUserId;
    }

    public void setAnonymousUserId(long anonymousUserId) {
        this.anonymousUserId = anonymousUserId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return this.middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}
