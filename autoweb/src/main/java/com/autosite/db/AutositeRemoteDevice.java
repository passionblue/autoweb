package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeRemoteDevice entity. @author MyEclipse Persistence Tools
 */

public class AutositeRemoteDevice extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String deviceId;
    private int deviceType;
    private String title;
    private int disable;
    private int unregistered;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public AutositeRemoteDevice() {
    }

    /** minimal constructor */
    public AutositeRemoteDevice(long siteId, String deviceId, int deviceType, int disable, int unregistered, Timestamp timeCreated) {
        this.siteId = siteId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.disable = disable;
        this.unregistered = unregistered;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public AutositeRemoteDevice(long siteId, String deviceId, int deviceType, String title, int disable, int unregistered, Timestamp timeCreated) {
        this.siteId = siteId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.title = title;
        this.disable = disable;
        this.unregistered = unregistered;
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

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDisable() {
        return this.disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public int getUnregistered() {
        return this.unregistered;
    }

    public void setUnregistered(int unregistered) {
        this.unregistered = unregistered;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
