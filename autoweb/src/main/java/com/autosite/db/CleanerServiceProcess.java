package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerServiceProcess entity. @author MyEclipse Persistence Tools
 */

public class CleanerServiceProcess extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long ticketId;
    private long processUserId;
    private long processType;
    private Timestamp timeStarted;
    private Timestamp timeEnded;
    private String note;

    // Constructors

    /** default constructor */
    public CleanerServiceProcess() {
    }

    /** minimal constructor */
    public CleanerServiceProcess(long siteId, long ticketId, long processUserId, long processType, Timestamp timeStarted, Timestamp timeEnded) {
        this.siteId = siteId;
        this.ticketId = ticketId;
        this.processUserId = processUserId;
        this.processType = processType;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
    }

    /** full constructor */
    public CleanerServiceProcess(long siteId, long ticketId, long processUserId, long processType, Timestamp timeStarted, Timestamp timeEnded, String note) {
        this.siteId = siteId;
        this.ticketId = ticketId;
        this.processUserId = processUserId;
        this.processType = processType;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.note = note;
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

    public long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getProcessUserId() {
        return this.processUserId;
    }

    public void setProcessUserId(long processUserId) {
        this.processUserId = processUserId;
    }

    public long getProcessType() {
        return this.processType;
    }

    public void setProcessType(long processType) {
        this.processType = processType;
    }

    public Timestamp getTimeStarted() {
        return this.timeStarted;
    }

    public void setTimeStarted(Timestamp timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Timestamp getTimeEnded() {
        return this.timeEnded;
    }

    public void setTimeEnded(Timestamp timeEnded) {
        this.timeEnded = timeEnded;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
