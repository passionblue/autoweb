package com.autosite.db;

import java.sql.Timestamp;

/**
 * DevTable entity. @author MyEclipse Persistence Tools
 */

public class DevTable extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long devNoteId;
    private String title;
    private String subject;
    private String data;
    private int type;
    private int disable;
    private int radioValue;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public DevTable() {
    }

    /** minimal constructor */
    public DevTable(long siteId, long devNoteId, String title, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.devNoteId = devNoteId;
        this.title = title;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public DevTable(long siteId, long devNoteId, String title, String subject, String data, int type, int disable, int radioValue, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.devNoteId = devNoteId;
        this.title = title;
        this.subject = subject;
        this.data = data;
        this.type = type;
        this.disable = disable;
        this.radioValue = radioValue;
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

    public long getDevNoteId() {
        return this.devNoteId;
    }

    public void setDevNoteId(long devNoteId) {
        this.devNoteId = devNoteId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDisable() {
        return this.disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public int getRadioValue() {
        return this.radioValue;
    }

    public void setRadioValue(int radioValue) {
        this.radioValue = radioValue;
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
