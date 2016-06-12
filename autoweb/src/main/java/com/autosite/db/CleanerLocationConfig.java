package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerLocationConfig entity. @author MyEclipse Persistence Tools
 */

public class CleanerLocationConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long locationId;
    private String openHourWeekday;
    private String closeHourWeekday;
    private String openHourSat;
    private String closeHourSat;
    private String openHourSun;
    private String closeHourSun;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public CleanerLocationConfig() {
    }

    /** minimal constructor */
    public CleanerLocationConfig(long siteId, long locationId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.locationId = locationId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public CleanerLocationConfig(long siteId, long locationId, String openHourWeekday, String closeHourWeekday, String openHourSat, String closeHourSat, String openHourSun, String closeHourSun,
            Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.locationId = locationId;
        this.openHourWeekday = openHourWeekday;
        this.closeHourWeekday = closeHourWeekday;
        this.openHourSat = openHourSat;
        this.closeHourSat = closeHourSat;
        this.openHourSun = openHourSun;
        this.closeHourSun = closeHourSun;
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

    public long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getOpenHourWeekday() {
        return this.openHourWeekday;
    }

    public void setOpenHourWeekday(String openHourWeekday) {
        this.openHourWeekday = openHourWeekday;
    }

    public String getCloseHourWeekday() {
        return this.closeHourWeekday;
    }

    public void setCloseHourWeekday(String closeHourWeekday) {
        this.closeHourWeekday = closeHourWeekday;
    }

    public String getOpenHourSat() {
        return this.openHourSat;
    }

    public void setOpenHourSat(String openHourSat) {
        this.openHourSat = openHourSat;
    }

    public String getCloseHourSat() {
        return this.closeHourSat;
    }

    public void setCloseHourSat(String closeHourSat) {
        this.closeHourSat = closeHourSat;
    }

    public String getOpenHourSun() {
        return this.openHourSun;
    }

    public void setOpenHourSun(String openHourSun) {
        this.openHourSun = openHourSun;
    }

    public String getCloseHourSun() {
        return this.closeHourSun;
    }

    public void setCloseHourSun(String closeHourSun) {
        this.closeHourSun = closeHourSun;
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
