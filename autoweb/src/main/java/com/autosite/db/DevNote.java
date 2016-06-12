package com.autosite.db;

import java.sql.Timestamp;

/**
 * DevNote entity. @author MyEclipse Persistence Tools
 */

public class DevNote extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private int noteType;
    private int completed;
    private String category;
    private String subject;
    private String note;
    private String tags;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public DevNote() {
    }

    /** minimal constructor */
    public DevNote(long siteId, String note) {
        this.siteId = siteId;
        this.note = note;
    }

    /** full constructor */
    public DevNote(long siteId, int noteType, int completed, String category, String subject, String note, String tags, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.noteType = noteType;
        this.completed = completed;
        this.category = category;
        this.subject = subject;
        this.note = note;
        this.tags = tags;
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

    public int getNoteType() {
        return this.noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    public int getCompleted() {
        return this.completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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
