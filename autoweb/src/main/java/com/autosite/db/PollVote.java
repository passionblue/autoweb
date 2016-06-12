package com.autosite.db;

import java.sql.Timestamp;

/**
 * PollVote entity. @author MyEclipse Persistence Tools
 */

public class PollVote extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pollId;
    private long userId;
    private long answer;
    private String multipleAnswer;
    private int byGuest;
    private String ipAddress;
    private String pcid;
    private String dupCheckKey;
    private String note;
    private String ownAnswer;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public PollVote() {
    }

    /** minimal constructor */
    public PollVote(long siteId, long pollId, long userId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.userId = userId;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public PollVote(long siteId, long pollId, long userId, long answer, String multipleAnswer, int byGuest, String ipAddress, String pcid, String dupCheckKey, String note, String ownAnswer,
            Timestamp timeCreated) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.userId = userId;
        this.answer = answer;
        this.multipleAnswer = multipleAnswer;
        this.byGuest = byGuest;
        this.ipAddress = ipAddress;
        this.pcid = pcid;
        this.dupCheckKey = dupCheckKey;
        this.note = note;
        this.ownAnswer = ownAnswer;
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

    public long getPollId() {
        return this.pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAnswer() {
        return this.answer;
    }

    public void setAnswer(long answer) {
        this.answer = answer;
    }

    public String getMultipleAnswer() {
        return this.multipleAnswer;
    }

    public void setMultipleAnswer(String multipleAnswer) {
        this.multipleAnswer = multipleAnswer;
    }

    public int getByGuest() {
        return this.byGuest;
    }

    public void setByGuest(int byGuest) {
        this.byGuest = byGuest;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPcid() {
        return this.pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    public String getDupCheckKey() {
        return this.dupCheckKey;
    }

    public void setDupCheckKey(String dupCheckKey) {
        this.dupCheckKey = dupCheckKey;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOwnAnswer() {
        return this.ownAnswer;
    }

    public void setOwnAnswer(String ownAnswer) {
        this.ownAnswer = ownAnswer;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
