package com.autosite.db;

/**
 * ChurPayee entity. @author MyEclipse Persistence Tools
 */

public class ChurPayee extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private String remark;

    // Constructors

    /** default constructor */
    public ChurPayee() {
    }

    /** minimal constructor */
    public ChurPayee(long siteId, String title) {
        this.siteId = siteId;
        this.title = title;
    }

    /** full constructor */
    public ChurPayee(long siteId, String title, String remark) {
        this.siteId = siteId;
        this.title = title;
        this.remark = remark;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
