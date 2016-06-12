package com.autosite.db;

import java.sql.Timestamp;

/**
 * GenTable entity. @author MyEclipse Persistence Tools
 */

public class GenTable extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String country;
    private int age;
    private int disabled;
    private String comments;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public GenTable() {
    }

    /** minimal constructor */
    public GenTable(long siteId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public GenTable(long siteId, String country, int age, int disabled, String comments, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.country = country;
        this.age = age;
        this.disabled = disabled;
        this.comments = comments;
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

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDisabled() {
        return this.disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
