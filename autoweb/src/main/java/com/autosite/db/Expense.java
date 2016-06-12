package com.autosite.db;

import java.sql.Timestamp;

/**
 * Expense entity. @author MyEclipse Persistence Tools
 */

public class Expense extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long expenseItemId;
    private long amount;
    private String description;
    private String payMethod;
    private int notExpense;
    private String reference;
    private String dateExpense;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public Expense() {
    }

    /** minimal constructor */
    public Expense(long siteId, long expenseItemId, long amount) {
        this.siteId = siteId;
        this.expenseItemId = expenseItemId;
        this.amount = amount;
    }

    /** full constructor */
    public Expense(long siteId, long expenseItemId, long amount, String description, String payMethod, int notExpense, String reference, String dateExpense, Timestamp timeCreated,
            Timestamp timeUpdated) {
        this.siteId = siteId;
        this.expenseItemId = expenseItemId;
        this.amount = amount;
        this.description = description;
        this.payMethod = payMethod;
        this.notExpense = notExpense;
        this.reference = reference;
        this.dateExpense = dateExpense;
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

    public long getExpenseItemId() {
        return this.expenseItemId;
    }

    public void setExpenseItemId(long expenseItemId) {
        this.expenseItemId = expenseItemId;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayMethod() {
        return this.payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public int getNotExpense() {
        return this.notExpense;
    }

    public void setNotExpense(int notExpense) {
        this.notExpense = notExpense;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDateExpense() {
        return this.dateExpense;
    }

    public void setDateExpense(String dateExpense) {
        this.dateExpense = dateExpense;
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
