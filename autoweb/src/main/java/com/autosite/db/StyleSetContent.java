package com.autosite.db;

import java.sql.Timestamp;

/**
 * StyleSetContent entity. @author MyEclipse Persistence Tools
 */

public class StyleSetContent extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String name;
    private String idPrefix;
    private long listFrameStyleId;
    private long listSubjectStyleId;
    private long listDataStyleId;
    private long frameStyleId;
    private long subjectStyleId;
    private long dataStyleId;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public StyleSetContent() {
    }

    /** minimal constructor */
    public StyleSetContent(long siteId, String name, String idPrefix, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.name = name;
        this.idPrefix = idPrefix;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public StyleSetContent(long siteId, String name, String idPrefix, long listFrameStyleId, long listSubjectStyleId, long listDataStyleId, long frameStyleId, long subjectStyleId, long dataStyleId,
            Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.name = name;
        this.idPrefix = idPrefix;
        this.listFrameStyleId = listFrameStyleId;
        this.listSubjectStyleId = listSubjectStyleId;
        this.listDataStyleId = listDataStyleId;
        this.frameStyleId = frameStyleId;
        this.subjectStyleId = subjectStyleId;
        this.dataStyleId = dataStyleId;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdPrefix() {
        return this.idPrefix;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public long getListFrameStyleId() {
        return this.listFrameStyleId;
    }

    public void setListFrameStyleId(long listFrameStyleId) {
        this.listFrameStyleId = listFrameStyleId;
    }

    public long getListSubjectStyleId() {
        return this.listSubjectStyleId;
    }

    public void setListSubjectStyleId(long listSubjectStyleId) {
        this.listSubjectStyleId = listSubjectStyleId;
    }

    public long getListDataStyleId() {
        return this.listDataStyleId;
    }

    public void setListDataStyleId(long listDataStyleId) {
        this.listDataStyleId = listDataStyleId;
    }

    public long getFrameStyleId() {
        return this.frameStyleId;
    }

    public void setFrameStyleId(long frameStyleId) {
        this.frameStyleId = frameStyleId;
    }

    public long getSubjectStyleId() {
        return this.subjectStyleId;
    }

    public void setSubjectStyleId(long subjectStyleId) {
        this.subjectStyleId = subjectStyleId;
    }

    public long getDataStyleId() {
        return this.dataStyleId;
    }

    public void setDataStyleId(long dataStyleId) {
        this.dataStyleId = dataStyleId;
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
