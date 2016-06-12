package com.autosite.db;

import java.sql.Timestamp;

/**
 * GenMainCompMap entity. @author MyEclipse Persistence Tools
 */

public class GenMainCompMap extends com.autosite.db.BaseAutositeDataObject  implements java.io.Serializable {

    // Fields

    private long id;
    private long mainId;
    private long compId;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public GenMainCompMap() {
    }

    /** full constructor */
    public GenMainCompMap(long mainId, long compId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.mainId = mainId;
        this.compId = compId;
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

    public long getCompId() {
        return this.compId;
    }

    public void setCompId(long compId) {
        this.compId = compId;
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
