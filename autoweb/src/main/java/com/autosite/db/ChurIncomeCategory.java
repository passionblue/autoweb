package com.autosite.db;

import java.sql.Timestamp;

/**
 * ChurIncomeCategory entity. @author MyEclipse Persistence Tools
 */

public class ChurIncomeCategory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String incomeCategory;
    private String display;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ChurIncomeCategory() {
    }

    /** minimal constructor */
    public ChurIncomeCategory(long siteId, String incomeCategory, Timestamp timeCreated) {
        this.siteId = siteId;
        this.incomeCategory = incomeCategory;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public ChurIncomeCategory(long siteId, String incomeCategory, String display, Timestamp timeCreated) {
        this.siteId = siteId;
        this.incomeCategory = incomeCategory;
        this.display = display;
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

    public String getIncomeCategory() {
        return this.incomeCategory;
    }

    public void setIncomeCategory(String incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
