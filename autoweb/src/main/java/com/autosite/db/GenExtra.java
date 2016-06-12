package com.autosite.db;

import java.sql.Timestamp;

/**
 * GenExtra entity. @author MyEclipse Persistence Tools
 */

public class GenExtra extends com.autosite.db.BaseAutositeDataObject  implements java.io.Serializable {

    // Fields

    private long id;
    private long mainId;
    private long subId;
    private int extraValue;
    private String extraData;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public GenExtra() {
    }

    /** minimal constructor */
    public GenExtra(long mainId, long subId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.mainId = mainId;
        this.subId = subId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public GenExtra(long mainId, long subId, int extraValue, String extraData, Timestamp timeCreated, Timestamp timeUpdated) {
        this.mainId = mainId;
        this.subId = subId;
        this.extraValue = extraValue;
        this.extraData = extraData;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMainId() {
        return this.mainId;
    }

    public void setMainId(long mainId) {
        this.mainId = mainId;
    }

    public long getSubId() {
        return this.subId;
    }

    public void setSubId(long subId) {
        this.subId = subId;
    }

    public int getExtraValue() {
        return this.extraValue;
    }

    public void setExtraValue(int extraValue) {
        this.extraValue = extraValue;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
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

}
