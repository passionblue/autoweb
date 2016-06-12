package com.autosite.db;

/**
 * GenComp entity. @author MyEclipse Persistence Tools
 */

public class GenComp extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private String compData;

    // Constructors

    /** default constructor */
    public GenComp() {
    }

    /** full constructor */
    public GenComp(String compData) {
        this.compData = compData;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompData() {
        return this.compData;
    }

    public void setCompData(String compData) {
        this.compData = compData;
    }

}
