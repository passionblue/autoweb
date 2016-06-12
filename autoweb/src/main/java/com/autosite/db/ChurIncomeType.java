package com.autosite.db;

/**
 * ChurIncomeType entity. @author MyEclipse Persistence Tools
 */

public class ChurIncomeType extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String incomeType;

    // Constructors

    /** default constructor */
    public ChurIncomeType() {
    }

    /** full constructor */
    public ChurIncomeType(long siteId, String incomeType) {
        this.siteId = siteId;
        this.incomeType = incomeType;
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

    public String getIncomeType() {
        return this.incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

}
