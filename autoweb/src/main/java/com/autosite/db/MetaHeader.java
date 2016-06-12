package com.autosite.db;

import java.sql.Timestamp;

/**
 * MetaHeader entity. @author MyEclipse Persistence Tools
 */

public class MetaHeader extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String source;
    private long detailId;
    private String name;
    private String value;
    private int httpEquiv;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public MetaHeader() {
    }

    /** minimal constructor */
    public MetaHeader(long siteId, String source, long detailId, String name, Timestamp timeCreated) {
        this.siteId = siteId;
        this.source = source;
        this.detailId = detailId;
        this.name = name;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public MetaHeader(long siteId, String source, long detailId, String name, String value, int httpEquiv, Timestamp timeCreated) {
        this.siteId = siteId;
        this.source = source;
        this.detailId = detailId;
        this.name = name;
        this.value = value;
        this.httpEquiv = httpEquiv;
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

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getDetailId() {
        return this.detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getHttpEquiv() {
        return this.httpEquiv;
    }

    public void setHttpEquiv(int httpEquiv) {
        this.httpEquiv = httpEquiv;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
