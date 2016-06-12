package com.autosite.db;

import java.sql.Timestamp;

/**
 * WhoisData entity. @author MyEclipse Persistence Tools
 */

public class WhoisData extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String ip;
    private String whoisData;
    private String server;
    private int forceRequest;
    private Timestamp timeCreated;
    private Timestamp timeExpired;

    // Constructors

    /** default constructor */
    public WhoisData() {
    }

    /** minimal constructor */
    public WhoisData(long siteId, String ip, String server, int forceRequest, Timestamp timeCreated, Timestamp timeExpired) {
        this.siteId = siteId;
        this.ip = ip;
        this.server = server;
        this.forceRequest = forceRequest;
        this.timeCreated = timeCreated;
        this.timeExpired = timeExpired;
    }

    /** full constructor */
    public WhoisData(long siteId, String ip, String whoisData, String server, int forceRequest, Timestamp timeCreated, Timestamp timeExpired) {
        this.siteId = siteId;
        this.ip = ip;
        this.whoisData = whoisData;
        this.server = server;
        this.forceRequest = forceRequest;
        this.timeCreated = timeCreated;
        this.timeExpired = timeExpired;
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

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getWhoisData() {
        return this.whoisData;
    }

    public void setWhoisData(String whoisData) {
        this.whoisData = whoisData;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getForceRequest() {
        return this.forceRequest;
    }

    public void setForceRequest(int forceRequest) {
        this.forceRequest = forceRequest;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeExpired() {
        return this.timeExpired;
    }

    public void setTimeExpired(Timestamp timeExpired) {
        this.timeExpired = timeExpired;
    }

}
