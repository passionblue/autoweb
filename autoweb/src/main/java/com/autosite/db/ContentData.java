package com.autosite.db;

import java.sql.Timestamp;

/**
 * ContentData entity. @author MyEclipse Persistence Tools
 */

public class ContentData extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long contentId;
    private String data;
    private String option1;
    private String option2;
    private String option3;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public ContentData() {
    }

    /** minimal constructor */
    public ContentData(long siteId, long contentId, String data, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.contentId = contentId;
        this.data = data;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public ContentData(long siteId, long contentId, String data, String option1, String option2, String option3, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.contentId = contentId;
        this.data = data;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
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

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOption1() {
        return this.option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return this.option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
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
