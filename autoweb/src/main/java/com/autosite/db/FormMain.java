package com.autosite.db;

import java.sql.Timestamp;


/**
 * FormMain entity. @author MyEclipse Persistence Tools
 */

public class FormMain extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {


    // Fields    

     private long id;
     private long siteId;
     private long userId;
     private String title;
     private Timestamp timeCreated;
     private Timestamp timeUpdated;


    // Constructors

    /** default constructor */
    public FormMain() {
    }

    
    /** full constructor */
    public FormMain(long siteId, long userId, String title, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.userId = userId;
        this.title = title;
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

    public long getUserId() {
        return this.userId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
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