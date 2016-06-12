package com.autosite.dbmodel;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ChurExpense entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="tb_ChurExpense")
public class ChurExpense extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    @Id
    private long id;
    private long siteId;
    private int year;
    private String week;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getSiteId() {
        return siteId;
    }
    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }


}
