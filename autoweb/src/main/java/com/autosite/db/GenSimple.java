package com.autosite.db;

/**
 * GenSimple entity. @author MyEclipse Persistence Tools
 */

public class GenSimple extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String data;
    private int active;

    // Constructors

    /** default constructor */
    public GenSimple() {
    }

    /** minimal constructor */
    public GenSimple(long siteId) {
        this.siteId = siteId;
    }

    /** full constructor */
    public GenSimple(long siteId, String data, int active) {
        this.siteId = siteId;
        this.data = data;
        this.active = active;
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

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

}
