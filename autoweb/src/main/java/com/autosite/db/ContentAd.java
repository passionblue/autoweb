package com.autosite.db;

import java.sql.Timestamp;

/**
 * ContentAd entity. @author MyEclipse Persistence Tools
 */

public class ContentAd extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long contentId;
    private long siteId;
    private int positionCode;
    private String adContent;
    private int hide;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public ContentAd() {
    }

    /** minimal constructor */
    public ContentAd(long contentId, long siteId, int positionCode, String adContent, Timestamp timeCreated, Timestamp timeUpdated) {
        this.contentId = contentId;
        this.siteId = siteId;
        this.positionCode = positionCode;
        this.adContent = adContent;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public ContentAd(long contentId, long siteId, int positionCode, String adContent, int hide, Timestamp timeCreated, Timestamp timeUpdated) {
        this.contentId = contentId;
        this.siteId = siteId;
        this.positionCode = positionCode;
        this.adContent = adContent;
        this.hide = hide;
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

    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public int getPositionCode() {
        return this.positionCode;
    }

    public void setPositionCode(int positionCode) {
        this.positionCode = positionCode;
    }

    public String getAdContent() {
        return this.adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
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
