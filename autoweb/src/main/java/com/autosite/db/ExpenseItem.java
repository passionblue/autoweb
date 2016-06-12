package com.autosite.db;

import java.sql.Timestamp;

/**
 * ExpenseItem entity. @author MyEclipse Persistence Tools
 */

public class ExpenseItem extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long expenseCategoryId;
    private String expenseItem;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public ExpenseItem() {
    }

    /** full constructor */
    public ExpenseItem(long siteId, long expenseCategoryId, String expenseItem, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.expenseCategoryId = expenseCategoryId;
        this.expenseItem = expenseItem;
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

    public long getExpenseCategoryId() {
        return this.expenseCategoryId;
    }

    public void setExpenseCategoryId(long expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public String getExpenseItem() {
        return this.expenseItem;
    }

    public void setExpenseItem(String expenseItem) {
        this.expenseItem = expenseItem;
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
