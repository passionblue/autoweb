package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeSynchLedgerHistory entity. @author MyEclipse Persistence Tools
 */

public class AutositeSynchLedgerHistory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long deviceId;
    private long originalLedgerId;
    private String scope;
    private String target;
    private String remoteToken;
    private long objectId;
    private String synchId;
    private long synchNum;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public AutositeSynchLedgerHistory() {
    }

    /** minimal constructor */
    public AutositeSynchLedgerHistory(long siteId, long deviceId, long originalLedgerId, String scope, String target, long objectId, long synchNum, Timestamp timeCreated) {
        this.siteId = siteId;
        this.deviceId = deviceId;
        this.originalLedgerId = originalLedgerId;
        this.scope = scope;
        this.target = target;
        this.objectId = objectId;
        this.synchNum = synchNum;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public AutositeSynchLedgerHistory(long siteId, long deviceId, long originalLedgerId, String scope, String target, String remoteToken, long objectId, String synchId, long synchNum,
            Timestamp timeCreated) {
        this.siteId = siteId;
        this.deviceId = deviceId;
        this.originalLedgerId = originalLedgerId;
        this.scope = scope;
        this.target = target;
        this.remoteToken = remoteToken;
        this.objectId = objectId;
        this.synchId = synchId;
        this.synchNum = synchNum;
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

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getOriginalLedgerId() {
        return this.originalLedgerId;
    }

    public void setOriginalLedgerId(long originalLedgerId) {
        this.originalLedgerId = originalLedgerId;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRemoteToken() {
        return this.remoteToken;
    }

    public void setRemoteToken(String remoteToken) {
        this.remoteToken = remoteToken;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getSynchId() {
        return this.synchId;
    }

    public void setSynchId(String synchId) {
        this.synchId = synchId;
    }

    public long getSynchNum() {
        return this.synchNum;
    }

    public void setSynchNum(long synchNum) {
        this.synchNum = synchNum;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
