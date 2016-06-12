package com.autosite.db;

import java.sql.Timestamp;

/**
 * ChurMember entity. @author MyEclipse Persistence Tools
 */

public class ChurMember extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String title;
    private String otherName;
    private int household;
    private long householdId;
    private int isGroup;
    private int isGuest;
    private int isSpeaker;
    private Timestamp timeCreated;
    private int listIndex;

    // Constructors

    /** default constructor */
    public ChurMember() {
    }

    /** minimal constructor */
    public ChurMember(long siteId, String fullName, Timestamp timeCreated) {
        this.siteId = siteId;
        this.fullName = fullName;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public ChurMember(long siteId, String fullName, String firstName, String lastName, String title, String otherName, int household, long householdId, int isGroup, int isGuest, int isSpeaker,
            Timestamp timeCreated, int listIndex) {
        this.siteId = siteId;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.otherName = otherName;
        this.household = household;
        this.householdId = householdId;
        this.isGroup = isGroup;
        this.isGuest = isGuest;
        this.isSpeaker = isSpeaker;
        this.timeCreated = timeCreated;
        this.listIndex = listIndex;
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

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOtherName() {
        return this.otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public int getHousehold() {
        return this.household;
    }

    public void setHousehold(int household) {
        this.household = household;
    }

    public long getHouseholdId() {
        return this.householdId;
    }

    public void setHouseholdId(long householdId) {
        this.householdId = householdId;
    }

    public int getIsGroup() {
        return this.isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public int getIsGuest() {
        return this.isGuest;
    }

    public void setIsGuest(int isGuest) {
        this.isGuest = isGuest;
    }

    public int getIsSpeaker() {
        return this.isSpeaker;
    }

    public void setIsSpeaker(int isSpeaker) {
        this.isSpeaker = isSpeaker;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int getListIndex() {
        return this.listIndex;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

}
