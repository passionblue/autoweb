package com.autosite.db;

import java.sql.Timestamp;

/**
 * ResourceList entity. @author MyEclipse Persistence Tools
 */

public class ResourceList extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String url;
    private String originalName;
    private int size;
    private int resourceType;
    private Timestamp timeCreaeted;
    private Timestamp timeUpdated;
    private int imageWidth;
    private int imageHight;

    // Constructors

    /** default constructor */
    public ResourceList() {
    }

    /** minimal constructor */
    public ResourceList(long siteId, String url, String originalName, int size, int resourceType, Timestamp timeCreaeted, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.url = url;
        this.originalName = originalName;
        this.size = size;
        this.resourceType = resourceType;
        this.timeCreaeted = timeCreaeted;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public ResourceList(long siteId, String url, String originalName, int size, int resourceType, Timestamp timeCreaeted, Timestamp timeUpdated, int imageWidth, int imageHight) {
        this.siteId = siteId;
        this.url = url;
        this.originalName = originalName;
        this.size = size;
        this.resourceType = resourceType;
        this.timeCreaeted = timeCreaeted;
        this.timeUpdated = timeUpdated;
        this.imageWidth = imageWidth;
        this.imageHight = imageHight;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public Timestamp getTimeCreaeted() {
        return this.timeCreaeted;
    }

    public void setTimeCreaeted(Timestamp timeCreaeted) {
        this.timeCreaeted = timeCreaeted;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHight() {
        return this.imageHight;
    }

    public void setImageHight(int imageHight) {
        this.imageHight = imageHight;
    }

}
