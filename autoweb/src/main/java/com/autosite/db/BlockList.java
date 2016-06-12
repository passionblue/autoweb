package com.autosite.db;

import java.sql.Timestamp;

/**
 * BlockList entity. @author MyEclipse Persistence Tools
 */

public class BlockList extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String ipData;
    private int rangeCheck;
    private int reasonCode;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public BlockList() {
    }

    /** minimal constructor */
    public BlockList(long siteId, String ipData, Timestamp timeCreated) {
        this.siteId = siteId;
        this.ipData = ipData;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public BlockList(long siteId, String ipData, int rangeCheck, int reasonCode, Timestamp timeCreated) {
        this.siteId = siteId;
        this.ipData = ipData;
        this.rangeCheck = rangeCheck;
        this.reasonCode = reasonCode;
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

    public String getIpData() {
        return this.ipData;
    }

    public void setIpData(String ipData) {
        this.ipData = ipData;
    }

    public int getRangeCheck() {
        return this.rangeCheck;
    }

    public void setRangeCheck(int rangeCheck) {
        this.rangeCheck = rangeCheck;
    }

    public int getReasonCode() {
        return this.reasonCode;
    }

    public void setReasonCode(int reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
