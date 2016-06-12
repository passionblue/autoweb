package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeAccount entity. @author MyEclipse Persistence Tools
 */

public class AutositeAccount extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String accountNum;
    private int enabled;
    private String email;
    private String firstName;
    private String lastName;
    private String company;
    private String phone;
    private int emailConfirmed;
    private int inBetaTest;
    private int inEvalution;
    private Timestamp timeEvalEnds;
    private Timestamp timeConfirmed;
    private Timestamp timeCreated;
    private long accountOwnerId;

    // Constructors

    /** default constructor */
    public AutositeAccount() {
    }

    /** minimal constructor */
    public AutositeAccount(long siteId, String accountNum, String email, String firstName, String lastName, Timestamp timeCreated, long accountOwnerId) {
        this.siteId = siteId;
        this.accountNum = accountNum;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
        this.accountOwnerId = accountOwnerId;
    }

    /** full constructor */
    public AutositeAccount(long siteId, String accountNum, int enabled, String email, String firstName, String lastName, String company, String phone, int emailConfirmed, int inBetaTest,
            int inEvalution, Timestamp timeEvalEnds, Timestamp timeConfirmed, Timestamp timeCreated, long accountOwnerId) {
        this.siteId = siteId;
        this.accountNum = accountNum;
        this.enabled = enabled;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.phone = phone;
        this.emailConfirmed = emailConfirmed;
        this.inBetaTest = inBetaTest;
        this.inEvalution = inEvalution;
        this.timeEvalEnds = timeEvalEnds;
        this.timeConfirmed = timeConfirmed;
        this.timeCreated = timeCreated;
        this.accountOwnerId = accountOwnerId;
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

    public String getAccountNum() {
        return this.accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public int getEnabled() {
        return this.enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
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

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getEmailConfirmed() {
        return this.emailConfirmed;
    }

    public void setEmailConfirmed(int emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public int getInBetaTest() {
        return this.inBetaTest;
    }

    public void setInBetaTest(int inBetaTest) {
        this.inBetaTest = inBetaTest;
    }

    public int getInEvalution() {
        return this.inEvalution;
    }

    public void setInEvalution(int inEvalution) {
        this.inEvalution = inEvalution;
    }

    public Timestamp getTimeEvalEnds() {
        return this.timeEvalEnds;
    }

    public void setTimeEvalEnds(Timestamp timeEvalEnds) {
        this.timeEvalEnds = timeEvalEnds;
    }

    public Timestamp getTimeConfirmed() {
        return this.timeConfirmed;
    }

    public void setTimeConfirmed(Timestamp timeConfirmed) {
        this.timeConfirmed = timeConfirmed;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getAccountOwnerId() {
        return this.accountOwnerId;
    }

    public void setAccountOwnerId(long accountOwnerId) {
        this.accountOwnerId = accountOwnerId;
    }

}
