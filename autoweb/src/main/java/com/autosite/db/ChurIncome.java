package com.autosite.db;

import java.sql.Timestamp;

/**
 * ChurIncome entity. @author MyEclipse Persistence Tools
 */

public class ChurIncome extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private int year;
    private String week;
    private long churMemberId;
    private long incomeItemId;
    private double ammount;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ChurIncome() {
    }

    /** minimal constructor */
    public ChurIncome(long siteId, int year, String week, long churMemberId, long incomeItemId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.year = year;
        this.week = week;
        this.churMemberId = churMemberId;
        this.incomeItemId = incomeItemId;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public ChurIncome(long siteId, int year, String week, long churMemberId, long incomeItemId, double ammount, Timestamp timeCreated) {
        this.siteId = siteId;
        this.year = year;
        this.week = week;
        this.churMemberId = churMemberId;
        this.incomeItemId = incomeItemId;
        this.ammount = ammount;
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

    public long getChurMemberId() {
        return this.churMemberId;
    }

    public void setChurMemberId(long churMemberId) {
        this.churMemberId = churMemberId;
    }

    public long getIncomeItemId() {
        return this.incomeItemId;
    }

    public void setIncomeItemId(long incomeItemId) {
        this.incomeItemId = incomeItemId;
    }

    public double getAmmount() {
        return this.ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
