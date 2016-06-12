package com.autosite.db;

import java.sql.Timestamp;

/**
 * FormField entity. @author MyEclipse Persistence Tools
 */

public class FormField extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private long formId;
    private String fieldText;
    private int fieldType;
    private int required;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public FormField() {
    }

    /** minimal constructor */
    public FormField(long siteId, long formId, String fieldText, int fieldType, Timestamp timeCreated) {
        this.siteId = siteId;
        this.formId = formId;
        this.fieldText = fieldText;
        this.fieldType = fieldType;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public FormField(long siteId, long userId, long formId, String fieldText, int fieldType, int required, Timestamp timeCreated) {
        this.siteId = siteId;
        this.userId = userId;
        this.formId = formId;
        this.fieldText = fieldText;
        this.fieldType = fieldType;
        this.required = required;
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

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFormId() {
        return this.formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public String getFieldText() {
        return this.fieldText;
    }

    public void setFieldText(String fieldText) {
        this.fieldText = fieldText;
    }

    public int getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public int getRequired() {
        return this.required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
