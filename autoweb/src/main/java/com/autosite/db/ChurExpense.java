package com.autosite.db;

import java.sql.Timestamp;

/**
 * ChurExpense entity. @author MyEclipse Persistence Tools
 */

public class ChurExpense extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private int year;
    private String week;
    private long expenseItemId;
    private long payeeId;
    private double amount;
    private int isCash;
    private String checkNumber;
    private int checkCleared;
    private String comment;
    private int cancelled;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public ChurExpense() {
    }

    /** minimal constructor */
    public ChurExpense(long siteId, int year, String week, long expenseItemId, long payeeId) {
        this.siteId = siteId;
        this.year = year;
        this.week = week;
        this.expenseItemId = expenseItemId;
        this.payeeId = payeeId;
    }

    /** full constructor */
    public ChurExpense(long siteId, int year, String week, long expenseItemId, long payeeId, double amount, int isCash, String checkNumber, int checkCleared, String comment, int cancelled,
            Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.year = year;
        this.week = week;
        this.expenseItemId = expenseItemId;
        this.payeeId = payeeId;
        this.amount = amount;
        this.isCash = isCash;
        this.checkNumber = checkNumber;
        this.checkCleared = checkCleared;
        this.comment = comment;
        this.cancelled = cancelled;
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

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public long getExpenseItemId() {
        return this.expenseItemId;
    }

    public void setExpenseItemId(long expenseItemId) {
        this.expenseItemId = expenseItemId;
    }

    public long getPayeeId() {
        return this.payeeId;
    }

    public void setPayeeId(long payeeId) {
        this.payeeId = payeeId;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIsCash() {
        return this.isCash;
    }

    public void setIsCash(int isCash) {
        this.isCash = isCash;
    }

    public String getCheckNumber() {
        return this.checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public int getCheckCleared() {
        return this.checkCleared;
    }

    public void setCheckCleared(int checkCleared) {
        this.checkCleared = checkCleared;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
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
