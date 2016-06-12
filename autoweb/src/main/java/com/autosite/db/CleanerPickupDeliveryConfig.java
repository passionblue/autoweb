package com.autosite.db;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * CleanerPickupDeliveryConfig entity. @author MyEclipse Persistence Tools
 */

@Document(collection = "CleanerPickupDeliveryConfig")
public class CleanerPickupDeliveryConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long locationId;
    private int applyAllLocations;
    private int disableWebRequest;
    private int disallowAnonymousRequest;
    private int requireCustomerRegister;
    private int requireCustomerLogin;

    // Constructors

    /** default constructor */
    public CleanerPickupDeliveryConfig() {
    }

    /** minimal constructor */
    public CleanerPickupDeliveryConfig(long siteId, long locationId, int disableWebRequest, int disallowAnonymousRequest, int requireCustomerRegister, int requireCustomerLogin) {
        this.siteId = siteId;
        this.locationId = locationId;
        this.disableWebRequest = disableWebRequest;
        this.disallowAnonymousRequest = disallowAnonymousRequest;
        this.requireCustomerRegister = requireCustomerRegister;
        this.requireCustomerLogin = requireCustomerLogin;
    }

    /** full constructor */
    public CleanerPickupDeliveryConfig(long siteId, long locationId, int applyAllLocations, int disableWebRequest, int disallowAnonymousRequest, int requireCustomerRegister, int requireCustomerLogin) {
        this.siteId = siteId;
        this.locationId = locationId;
        this.applyAllLocations = applyAllLocations;
        this.disableWebRequest = disableWebRequest;
        this.disallowAnonymousRequest = disallowAnonymousRequest;
        this.requireCustomerRegister = requireCustomerRegister;
        this.requireCustomerLogin = requireCustomerLogin;
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

    public long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public int getApplyAllLocations() {
        return this.applyAllLocations;
    }

    public void setApplyAllLocations(int applyAllLocations) {
        this.applyAllLocations = applyAllLocations;
    }

    public int getDisableWebRequest() {
        return this.disableWebRequest;
    }

    public void setDisableWebRequest(int disableWebRequest) {
        this.disableWebRequest = disableWebRequest;
    }

    public int getDisallowAnonymousRequest() {
        return this.disallowAnonymousRequest;
    }

    public void setDisallowAnonymousRequest(int disallowAnonymousRequest) {
        this.disallowAnonymousRequest = disallowAnonymousRequest;
    }

    public int getRequireCustomerRegister() {
        return this.requireCustomerRegister;
    }

    public void setRequireCustomerRegister(int requireCustomerRegister) {
        this.requireCustomerRegister = requireCustomerRegister;
    }

    public int getRequireCustomerLogin() {
        return this.requireCustomerLogin;
    }

    public void setRequireCustomerLogin(int requireCustomerLogin) {
        this.requireCustomerLogin = requireCustomerLogin;
    }

}
