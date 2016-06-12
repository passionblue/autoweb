package com.autosite.db;

import java.sql.Timestamp;

/**
 * SynkNamespaceRecord entity. @author MyEclipse Persistence Tools
 */

public class SynkNamespaceRecord extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String namespace;
    private long recordId;
    private long stamp;
    private long orgStamp;
    private int deleted;
    private String createdByDeviceId;
    private String createdByIp;
    private String createdByUser;
    private String updatedByDeviceId;
    private String updatedByIp;
    private String updatedByUser;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;
    private Timestamp timeDeleted;

    // Constructors

    /** default constructor */
    public SynkNamespaceRecord() {
    }

    /** minimal constructor */
    public SynkNamespaceRecord(long siteId, String namespace, long recordId, long stamp, long orgStamp, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.namespace = namespace;
        this.recordId = recordId;
        this.stamp = stamp;
        this.orgStamp = orgStamp;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public SynkNamespaceRecord(long siteId, String namespace, long recordId, long stamp, long orgStamp, int deleted, String createdByDeviceId, String createdByIp, String createdByUser,
            String updatedByDeviceId, String updatedByIp, String updatedByUser, Timestamp timeCreated, Timestamp timeUpdated, Timestamp timeDeleted) {
        this.siteId = siteId;
        this.namespace = namespace;
        this.recordId = recordId;
        this.stamp = stamp;
        this.orgStamp = orgStamp;
        this.deleted = deleted;
        this.createdByDeviceId = createdByDeviceId;
        this.createdByIp = createdByIp;
        this.createdByUser = createdByUser;
        this.updatedByDeviceId = updatedByDeviceId;
        this.updatedByIp = updatedByIp;
        this.updatedByUser = updatedByUser;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.timeDeleted = timeDeleted;
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

    public long getRecordId() {
        return this.recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public long getStamp() {
        return this.stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public long getOrgStamp() {
        return this.orgStamp;
    }

    public void setOrgStamp(long orgStamp) {
        this.orgStamp = orgStamp;
    }

    public int getDeleted() {
        return this.deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getCreatedByDeviceId() {
        return this.createdByDeviceId;
    }

    public void setCreatedByDeviceId(String createdByDeviceId) {
        this.createdByDeviceId = createdByDeviceId;
    }

    public String getCreatedByIp() {
        return this.createdByIp;
    }

    public void setCreatedByIp(String createdByIp) {
        this.createdByIp = createdByIp;
    }

    public String getCreatedByUser() {
        return this.createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getUpdatedByDeviceId() {
        return this.updatedByDeviceId;
    }

    public void setUpdatedByDeviceId(String updatedByDeviceId) {
        this.updatedByDeviceId = updatedByDeviceId;
    }

    public String getUpdatedByIp() {
        return this.updatedByIp;
    }

    public void setUpdatedByIp(String updatedByIp) {
        this.updatedByIp = updatedByIp;
    }

    public String getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(String updatedByUser) {
        this.updatedByUser = updatedByUser;
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

    public Timestamp getTimeDeleted() {
        return this.timeDeleted;
    }

    public void setTimeDeleted(Timestamp timeDeleted) {
        this.timeDeleted = timeDeleted;
    }

}
