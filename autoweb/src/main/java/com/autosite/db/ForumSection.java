package com.autosite.db;

import java.util.Date;

/**
 * ForumSection entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ForumSection extends com.autosite.db.BaseAutositeDataObject  implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String sectionTitle;
    private Date timeCreated;

    // Constructors

    /** default constructor */
    public ForumSection() {
    }

    /** full constructor */
    public ForumSection(long siteId, String sectionTitle, Date timeCreated) {
        this.siteId = siteId;
        this.sectionTitle = sectionTitle;
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

    public String getSectionTitle() {
        return this.sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public Date getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

}
