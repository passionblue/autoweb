package com.autosite.db;

/**
 * ChurExpenseItem entity. @author MyEclipse Persistence Tools
 */

public class ChurExpenseItem extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long categoryId;
    private String expenseItem;
    private String display;

    // Constructors

    /** default constructor */
    public ChurExpenseItem() {
    }

    /** minimal constructor */
    public ChurExpenseItem(long siteId, long categoryId, String expenseItem) {
        this.siteId = siteId;
        this.categoryId = categoryId;
        this.expenseItem = expenseItem;
    }

    /** full constructor */
    public ChurExpenseItem(long siteId, long categoryId, String expenseItem, String display) {
        this.siteId = siteId;
        this.categoryId = categoryId;
        this.expenseItem = expenseItem;
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

    public String getExpenseItem() {
        return this.expenseItem;
    }

    public void setExpenseItem(String expenseItem) {
        this.expenseItem = expenseItem;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

}
