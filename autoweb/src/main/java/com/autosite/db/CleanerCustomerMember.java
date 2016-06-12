package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerCustomerMember entity. @author MyEclipse Persistence Tools
 */

public class CleanerCustomerMember extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long customerId;
    private String title;
    private String firstName;
    private String lastName;
    private String note;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public CleanerCustomerMember() {
    }

    /** minimal constructor */
    public CleanerCustomerMember(long siteId, String firstName, String lastName, Timestamp timeCreated) {
        this.siteId = siteId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public CleanerCustomerMember(long siteId, long customerId, String title, String firstName, String lastName, String note, Timestamp timeCreated) {
        this.siteId = siteId;
        this.customerId = customerId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.note = note;
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

    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
