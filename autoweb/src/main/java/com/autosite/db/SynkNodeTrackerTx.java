package com.autosite.db;

import java.sql.Timestamp;

/**
 * SynkNodeTrackerTx entity. @author MyEclipse Persistence Tools
 */

public class SynkNodeTrackerTx extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String namespace;
    private String deviceId;
    private String txToken;
    private long stampAcked;
    private long stampLast;
    private int numRecords;
    private String ip;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public SynkNodeTrackerTx() {
    }

    /** minimal constructor */
    public SynkNodeTrackerTx(long siteId, String deviceId, String txToken, long stampLast, Timestamp timeCreated) {
        this.siteId = siteId;
        this.deviceId = deviceId;
        this.txToken = txToken;
        this.stampLast = stampLast;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public SynkNodeTrackerTx(long siteId, String namespace, String deviceId, String txToken, long stampAcked, long stampLast, int numRecords, String ip, Timestamp timeCreated) {
        this.siteId = siteId;
        this.namespace = namespace;
        this.deviceId = deviceId;
        this.txToken = txToken;
        this.stampAcked = stampAcked;
        this.stampLast = stampLast;
        this.numRecords = numRecords;
        this.ip = ip;
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

    public String getTxToken() {
        return this.txToken;
    }

    public void setTxToken(String txToken) {
        this.txToken = txToken;
    }

    public long getStampAcked() {
        return this.stampAcked;
    }

    public void setStampAcked(long stampAcked) {
        this.stampAcked = stampAcked;
    }

    public long getStampLast() {
        return this.stampLast;
    }

    public void setStampLast(long stampLast) {
        this.stampLast = stampLast;
    }

    public int getNumRecords() {
        return this.numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
