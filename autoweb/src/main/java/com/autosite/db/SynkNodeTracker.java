package com.autosite.db;

import java.sql.Timestamp;

/**
 * SynkNodeTracker entity. @author MyEclipse Persistence Tools
 */

public class SynkNodeTracker extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String namespace;
    private String deviceId;
    private int remote;
    private long stamp;
    private long stampRetrieved;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;
    private Timestamp timeRetrieved;
    private String remark;

    // Constructors

    /** default constructor */
    public SynkNodeTracker() {
    }

    /** minimal constructor */
    public SynkNodeTracker(long siteId, String namespace, String deviceId, int remote, long stamp, long stampRetrieved, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.namespace = namespace;
        this.deviceId = deviceId;
        this.remote = remote;
        this.stamp = stamp;
        this.stampRetrieved = stampRetrieved;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public SynkNodeTracker(long siteId, String namespace, String deviceId, int remote, long stamp, long stampRetrieved, Timestamp timeCreated, Timestamp timeUpdated, Timestamp timeRetrieved,
            String remark) {
        this.siteId = siteId;
        this.namespace = namespace;
        this.deviceId = deviceId;
        this.remote = remote;
        this.stamp = stamp;
        this.stampRetrieved = stampRetrieved;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.timeRetrieved = timeRetrieved;
        this.remark = remark;
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

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getRemote() {
        return this.remote;
    }

    public void setRemote(int remote) {
        this.remote = remote;
    }

    public long getStamp() {
        return this.stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public long getStampRetrieved() {
        return this.stampRetrieved;
    }

    public void setStampRetrieved(long stampRetrieved) {
        this.stampRetrieved = stampRetrieved;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public Timestamp getTimeRetrieved() {
        return this.timeRetrieved;
    }

    public void setTimeRetrieved(Timestamp timeRetrieved) {
        this.timeRetrieved = timeRetrieved;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
