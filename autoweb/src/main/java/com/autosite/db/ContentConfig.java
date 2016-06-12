package com.autosite.db;

import java.sql.Timestamp;


/**
 * ContentConfig entity. @author MyEclipse Persistence Tools
 */

public class ContentConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {


    // Fields    

     private long id;
     private long siteId;
     private long contentId;
     private String keywords;
     private Timestamp timeCreated;
     private Timestamp timeUpdated;


    // Constructors

    /** default constructor */
    public ContentConfig() {
    }

	/** minimal constructor */
    public ContentConfig(long siteId, long contentId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.contentId = contentId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }
    
    /** full constructor */
    public ContentConfig(long siteId, long contentId, String keywords, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.contentId = contentId;
        this.keywords = keywords;
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

    public long getSiteId() {
        return this.siteId;
    }
    
    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getContentId() {
        return this.contentId;
    }
    
    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public String getKeywords() {
        return this.keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
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