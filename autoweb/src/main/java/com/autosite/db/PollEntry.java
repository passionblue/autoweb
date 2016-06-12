package com.autosite.db;

import java.sql.Timestamp;

/**
 * PollEntry entity. @author MyEclipse Persistence Tools
 */

public class PollEntry extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pollId;
    private int index;
    private String text;
    private String imageUrl;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public PollEntry() {
    }

    /** minimal constructor */
    public PollEntry(long siteId, long pollId, int index, String text, Timestamp timeCreated) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.index = index;
        this.text = text;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public PollEntry(long siteId, long pollId, int index, String text, String imageUrl, Timestamp timeCreated) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.index = index;
        this.text = text;
        this.imageUrl = imageUrl;
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

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
