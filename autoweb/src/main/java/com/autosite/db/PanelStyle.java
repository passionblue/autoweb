package com.autosite.db;



/**
 * PanelStyle entity. @author MyEclipse Persistence Tools
 */

public class PanelStyle extends com.autosite.db.BaseAutositeDataObject  implements java.io.Serializable {


    // Fields    

     private long id;
     private long siteId;
     private long panelId;
     private long styleId;


    // Constructors

    /** default constructor */
    public PanelStyle() {
    }

	/** minimal constructor */
    public PanelStyle(long panelId, long styleId) {
        this.panelId = panelId;
        this.styleId = styleId;
    }
    
    /** full constructor */
    public PanelStyle(long siteId, long panelId, long styleId) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.styleId = styleId;
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

    public long getPanelId() {
        return this.panelId;
    }
    
    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public long getStyleId() {
        return this.styleId;
    }
    
    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }
   








}