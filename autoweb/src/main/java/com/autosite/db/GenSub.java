package com.autosite.db;

import java.sql.Timestamp;

/**
 * GenSub entity. @author MyEclipse Persistence Tools
 */

public class GenSub extends com.autosite.db.BaseAutositeDataObject  implements java.io.Serializable {

    // Fields

    private long id;
    private long mainId;
    private String strKey;
    private String subData;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public GenSub() {
    }

    /** full constructor */
    public GenSub(long mainId, String strKey, String subData, Timestamp timeCreated, Timestamp timeUpdated) {
        this.mainId = mainId;
        this.strKey = strKey;
        this.subData = subData;
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

    public String getStrKey() {
        return this.strKey;
    }

    public void setStrKey(String strKey) {
        this.strKey = strKey;
    }

    public String getSubData() {
        return this.subData;
    }

    public void setSubData(String subData) {
        this.subData = subData;
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
