package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeLoginExtent entity. @author MyEclipse Persistence Tools
 */

public class AutositeLoginExtent extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String className;
    private int inactive;
    private Timestamp dateCreated;

    // Constructors

    /** default constructor */
    public AutositeLoginExtent() {
    }

    /** minimal constructor */
    public AutositeLoginExtent(long siteId, String className, Timestamp dateCreated) {
        this.siteId = siteId;
        this.className = className;
        this.dateCreated = dateCreated;
    }

    /** full constructor */
    public AutositeLoginExtent(long siteId, String className, int inactive, Timestamp dateCreated) {
        this.siteId = siteId;
        this.className = className;
        this.inactive = inactive;
        this.dateCreated = dateCreated;
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

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getInactive() {
        return this.inactive;
    }

    public void setInactive(int inactive) {
        this.inactive = inactive;
    }

    public Timestamp getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

}
