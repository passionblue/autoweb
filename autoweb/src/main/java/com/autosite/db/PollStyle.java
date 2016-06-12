package com.autosite.db;

import java.sql.Timestamp;

/**
 * PollStyle entity. @author MyEclipse Persistence Tools
 */

public class PollStyle extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private long pollId;
    private String color;
    private String background;
    private String border;
    private String font;
    private String margin;
    private String padding;
    private String floating;
    private String textAlign;
    private String textIndent;
    private String height;
    private String width;
    private String extra;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public PollStyle() {
    }

    /** minimal constructor */
    public PollStyle(long siteId, long userId, long pollId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.userId = userId;
        this.pollId = pollId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public PollStyle(long siteId, long userId, long pollId, String color, String background, String border, String font, String margin, String padding, String floating, String textAlign,
            String textIndent, String height, String width, String extra, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.userId = userId;
        this.pollId = pollId;
        this.color = color;
        this.background = background;
        this.border = border;
        this.font = font;
        this.margin = margin;
        this.padding = padding;
        this.floating = floating;
        this.textAlign = textAlign;
        this.textIndent = textIndent;
        this.height = height;
        this.width = width;
        this.extra = extra;
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

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPollId() {
        return this.pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBorder() {
        return this.border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getMargin() {
        return this.margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getPadding() {
        return this.padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getFloating() {
        return this.floating;
    }

    public void setFloating(String floating) {
        this.floating = floating;
    }

    public String getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getTextIndent() {
        return this.textIndent;
    }

    public void setTextIndent(String textIndent) {
        this.textIndent = textIndent;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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
