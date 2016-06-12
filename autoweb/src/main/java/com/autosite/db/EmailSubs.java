package com.autosite.db;

import java.sql.Timestamp;

/**
 * EmailSubs entity. @author MyEclipse Persistence Tools
 */

public class EmailSubs extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String subject;
    private String email;
    private String firstName;
    private String lastName;
    private int confirmed;
    private int disabled;
    private int checkOpt1;
    private int checkOpt2;
    private String extraInfo;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EmailSubs() {
    }

    /** minimal constructor */
    public EmailSubs(String subject, String email, String firstName, String lastName, Timestamp timeCreated) {
        this.subject = subject;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public EmailSubs(long siteId, String subject, String email, String firstName, String lastName, int confirmed, int disabled, int checkOpt1, int checkOpt2, String extraInfo, Timestamp timeCreated) {
        this.siteId = siteId;
        this.subject = subject;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.confirmed = confirmed;
        this.disabled = disabled;
        this.checkOpt1 = checkOpt1;
        this.checkOpt2 = checkOpt2;
        this.extraInfo = extraInfo;
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

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDisabled() {
        return this.disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public int getCheckOpt1() {
        return this.checkOpt1;
    }

    public void setCheckOpt1(int checkOpt1) {
        this.checkOpt1 = checkOpt1;
    }

    public int getCheckOpt2() {
        return this.checkOpt2;
    }

    public void setCheckOpt2(int checkOpt2) {
        this.checkOpt2 = checkOpt2;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
