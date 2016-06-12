package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeAccountPaymentInfo entity. @author MyEclipse Persistence Tools
 */

public class AutositeAccountPaymentInfo extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long accountId;
    private long userId;
    private String paymentName;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private int paymentType;
    private String cardType;
    private String paymentNum;
    private int paymentExpireMonth;
    private int paymentExpireYear;
    private String paymentExtraNum;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public AutositeAccountPaymentInfo() {
    }

    /** minimal constructor */
    public AutositeAccountPaymentInfo(long siteId, long accountId, long userId, String paymentName, String firstName, String lastName, String address1, String city, String state, String zip,
            String country, int paymentType, String paymentNum, int paymentExpireMonth, int paymentExpireYear) {
        this.siteId = siteId;
        this.accountId = accountId;
        this.userId = userId;
        this.paymentName = paymentName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.paymentType = paymentType;
        this.paymentNum = paymentNum;
        this.paymentExpireMonth = paymentExpireMonth;
        this.paymentExpireYear = paymentExpireYear;
    }

    /** full constructor */
    public AutositeAccountPaymentInfo(long siteId, long accountId, long userId, String paymentName, String firstName, String middleInitial, String lastName, String address1, String address2,
            String city, String state, String zip, String country, int paymentType, String cardType, String paymentNum, int paymentExpireMonth, int paymentExpireYear, String paymentExtraNum,
            Timestamp timeCreated) {
        this.siteId = siteId;
        this.accountId = accountId;
        this.userId = userId;
        this.paymentName = paymentName;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.paymentType = paymentType;
        this.cardType = cardType;
        this.paymentNum = paymentNum;
        this.paymentExpireMonth = paymentExpireMonth;
        this.paymentExpireYear = paymentExpireYear;
        this.paymentExtraNum = paymentExtraNum;
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

    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPaymentName() {
        return this.paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return this.middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPaymentNum() {
        return this.paymentNum;
    }

    public void setPaymentNum(String paymentNum) {
        this.paymentNum = paymentNum;
    }

    public int getPaymentExpireMonth() {
        return this.paymentExpireMonth;
    }

    public void setPaymentExpireMonth(int paymentExpireMonth) {
        this.paymentExpireMonth = paymentExpireMonth;
    }

    public int getPaymentExpireYear() {
        return this.paymentExpireYear;
    }

    public void setPaymentExpireYear(int paymentExpireYear) {
        this.paymentExpireYear = paymentExpireYear;
    }

    public String getPaymentExtraNum() {
        return this.paymentExtraNum;
    }

    public void setPaymentExtraNum(String paymentExtraNum) {
        this.paymentExtraNum = paymentExtraNum;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
