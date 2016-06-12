package com.autosite.db;

import java.sql.Timestamp;

/**
 * PollConfig entity. @author MyEclipse Persistence Tools
 */

public class PollConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pollId;
    private int imageThumbHeight;
    private int imageThumbWidth;
    private int imageAlignVertical;
    private String background;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public PollConfig() {
    }

    /** minimal constructor */
    public PollConfig(long siteId, long pollId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public PollConfig(long siteId, long pollId, int imageThumbHeight, int imageThumbWidth, int imageAlignVertical, String background, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.imageThumbHeight = imageThumbHeight;
        this.imageThumbWidth = imageThumbWidth;
        this.imageAlignVertical = imageAlignVertical;
        this.background = background;
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

    public long getPollId() {
        return this.pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public int getImageThumbHeight() {
        return this.imageThumbHeight;
    }

    public void setImageThumbHeight(int imageThumbHeight) {
        this.imageThumbHeight = imageThumbHeight;
    }

    public int getImageThumbWidth() {
        return this.imageThumbWidth;
    }

    public void setImageThumbWidth(int imageThumbWidth) {
        this.imageThumbWidth = imageThumbWidth;
    }

    public int getImageAlignVertical() {
        return this.imageAlignVertical;
    }

    public void setImageAlignVertical(int imageAlignVertical) {
        this.imageAlignVertical = imageAlignVertical;
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
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
