package com.autosite.db;

/**
 * ChurIncomeItem entity. @author MyEclipse Persistence Tools
 */

public class ChurIncomeItem extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long categoryId;
    private String incomeItem;
    private String display;

    // Constructors

    /** default constructor */
    public ChurIncomeItem() {
    }

    /** minimal constructor */
    public ChurIncomeItem(long siteId, long categoryId, String incomeItem) {
        this.siteId = siteId;
        this.categoryId = categoryId;
        this.incomeItem = incomeItem;
    }

    /** full constructor */
    public ChurIncomeItem(long siteId, long categoryId, String incomeItem, String display) {
        this.siteId = siteId;
        this.categoryId = categoryId;
        this.incomeItem = incomeItem;
        this.display = display;
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

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getIncomeItem() {
        return this.incomeItem;
    }

    public void setIncomeItem(String incomeItem) {
        this.incomeItem = incomeItem;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

}
