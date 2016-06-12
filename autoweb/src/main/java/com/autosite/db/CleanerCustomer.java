package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerCustomer entity. @author MyEclipse Persistence Tools
 */

public class CleanerCustomer extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long autositeUserId;
    private String email;
    private String phone;
    private String phone2;
    private int phonePreferred;
    private String title;
    private String lastName;
    private String firstName;
    private String address;
    private String apt;
    private String city;
    private String state;
    private String zip;
    private String customerType;
    private String payType;
    private Timestamp timeCreated;
    private String remoteIp;
    private String note;
    private String pickupNote;
    private String deliveryNote;
    private int disabled;
    private Timestamp timeUpdated;
    private int pickupDeliveryDisallowed;
    private String handleInst;

    // Constructors

    /** default constructor */
    public CleanerCustomer() {
    }

    /** minimal constructor */
    public CleanerCustomer(long siteId, String lastName, String firstName, Timestamp timeCreated, int disabled, Timestamp timeUpdated, int pickupDeliveryDisallowed) {
        this.siteId = siteId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.timeCreated = timeCreated;
        this.disabled = disabled;
        this.timeUpdated = timeUpdated;
        this.pickupDeliveryDisallowed = pickupDeliveryDisallowed;
    }

    /** full constructor */
    public CleanerCustomer(long siteId, long autositeUserId, String email, String phone, String phone2, int phonePreferred, String title, String lastName, String firstName, String address,
            String apt, String city, String state, String zip, String customerType, String payType, Timestamp timeCreated, String remoteIp, String note, String pickupNote, String deliveryNote,
            int disabled, Timestamp timeUpdated, int pickupDeliveryDisallowed, String handleInst) {
        this.siteId = siteId;
        this.autositeUserId = autositeUserId;
        this.email = email;
        this.phone = phone;
        this.phone2 = phone2;
        this.phonePreferred = phonePreferred;
        this.title = title;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.apt = apt;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.customerType = customerType;
        this.payType = payType;
        this.timeCreated = timeCreated;
        this.remoteIp = remoteIp;
        this.note = note;
        this.pickupNote = pickupNote;
        this.deliveryNote = deliveryNote;
        this.disabled = disabled;
        this.timeUpdated = timeUpdated;
        this.pickupDeliveryDisallowed = pickupDeliveryDisallowed;
        this.handleInst = handleInst;
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

    public long getAutositeUserId() {
        return this.autositeUserId;
    }

    public void setAutositeUserId(long autositeUserId) {
        this.autositeUserId = autositeUserId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public int getPhonePreferred() {
        return this.phonePreferred;
    }

    public void setPhonePreferred(int phonePreferred) {
        this.phonePreferred = phonePreferred;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApt() {
        return this.apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
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

    public String getCustomerType() {
        return this.customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPayType() {
        return this.payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPickupNote() {
        return this.pickupNote;
    }

    public void setPickupNote(String pickupNote) {
        this.pickupNote = pickupNote;
    }

    public String getDeliveryNote() {
        return this.deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public int getDisabled() {
        return this.disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public int getPickupDeliveryDisallowed() {
        return this.pickupDeliveryDisallowed;
    }

    public void setPickupDeliveryDisallowed(int pickupDeliveryDisallowed) {
        this.pickupDeliveryDisallowed = pickupDeliveryDisallowed;
    }

    public String getHandleInst() {
        return this.handleInst;
    }

    public void setHandleInst(String handleInst) {
        this.handleInst = handleInst;
    }

}
