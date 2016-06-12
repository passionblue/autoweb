package com.autosite.db;

/**
 * ChurExpenseCategory entity. @author MyEclipse Persistence Tools
 */

public class ChurExpenseCategory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String expenseCategory;
    private String display;

    // Constructors

    /** default constructor */
    public ChurExpenseCategory() {
    }

    /** minimal constructor */
    public ChurExpenseCategory(long siteId, String expenseCategory) {
        this.siteId = siteId;
        this.expenseCategory = expenseCategory;
    }

    /** full constructor */
    public ChurExpenseCategory(long siteId, String expenseCategory, String display) {
        this.siteId = siteId;
        this.expenseCategory = expenseCategory;
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

    public String getExpenseCategory() {
        return this.expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

}
