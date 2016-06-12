package com.autosite.db;

/**
 * EcDisplayConfig entity. @author MyEclipse Persistence Tools
 */

public class EcDisplayConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private int columnCount;

    // Constructors

    /** default constructor */
    public EcDisplayConfig() {
    }

    /** minimal constructor */
    public EcDisplayConfig(long siteId) {
        this.siteId = siteId;
    }

    /** full constructor */
    public EcDisplayConfig(long siteId, int columnCount) {
        this.siteId = siteId;
        this.columnCount = columnCount;
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

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

}
