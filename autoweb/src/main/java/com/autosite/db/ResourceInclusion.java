package com.autosite.db;

/**
 * ResourceInclusion entity. @author MyEclipse Persistence Tools
 */

public class ResourceInclusion extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String name;
    private int include;
    private int resourceType;

    // Constructors

    /** default constructor */
    public ResourceInclusion() {
    }

    /** minimal constructor */
    public ResourceInclusion(long siteId, String name) {
        this.siteId = siteId;
        this.name = name;
    }

    /** full constructor */
    public ResourceInclusion(long siteId, String name, int include, int resourceType) {
        this.siteId = siteId;
        this.name = name;
        this.include = include;
        this.resourceType = resourceType;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInclude() {
        return this.include;
    }

    public void setInclude(int include) {
        this.include = include;
    }

    public int getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

}
