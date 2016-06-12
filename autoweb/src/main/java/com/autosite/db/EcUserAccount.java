package com.autosite.db;

/**
 * EcUserAccount entity. @author MyEclipse Persistence Tools
 */

public class EcUserAccount extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private String firstName;
    private String lastName;

    // Constructors

    /** default constructor */
    public EcUserAccount() {
    }

    /** minimal constructor */
    public EcUserAccount(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /** full constructor */
    public EcUserAccount(long siteId, long userId, String firstName, String lastName) {
        this.siteId = siteId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
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

}
