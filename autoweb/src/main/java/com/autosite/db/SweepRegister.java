package com.autosite.db;

import java.sql.Timestamp;

/**
 * SweepRegister entity. @author MyEclipse Persistence Tools
 */

public class SweepRegister extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String email;
    private String password;
    private int sex;
    private int ageRange;
    private int agreeTerms;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public SweepRegister() {
    }

    /** minimal constructor */
    public SweepRegister(long siteId, String email, String password) {
        this.siteId = siteId;
        this.email = email;
        this.password = password;
    }

    /** full constructor */
    public SweepRegister(long siteId, String email, String password, int sex, int ageRange, int agreeTerms, Timestamp timeCreated) {
        this.siteId = siteId;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.ageRange = ageRange;
        this.agreeTerms = agreeTerms;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAgeRange() {
        return this.ageRange;
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange;
    }

    public int getAgreeTerms() {
        return this.agreeTerms;
    }

    public void setAgreeTerms(int agreeTerms) {
        this.agreeTerms = agreeTerms;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
