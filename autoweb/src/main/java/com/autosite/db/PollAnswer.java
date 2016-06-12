package com.autosite.db;

/**
 * PollAnswer entity. @author MyEclipse Persistence Tools
 */

public class PollAnswer extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pollId;
    private int answerNum;
    private String text;
    private String imageUrl;
    private int imageOnly;
    private int ownAnswer;

    // Constructors

    /** default constructor */
    public PollAnswer() {
    }

    /** minimal constructor */
    public PollAnswer(long siteId, long pollId, int answerNum, String text) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.answerNum = answerNum;
        this.text = text;
    }

    /** full constructor */
    public PollAnswer(long siteId, long pollId, int answerNum, String text, String imageUrl, int imageOnly, int ownAnswer) {
        this.siteId = siteId;
        this.pollId = pollId;
        this.answerNum = answerNum;
        this.text = text;
        this.imageUrl = imageUrl;
        this.imageOnly = imageOnly;
        this.ownAnswer = ownAnswer;
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

    public int getAnswerNum() {
        return this.answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
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

    public int getImageOnly() {
        return this.imageOnly;
    }

    public void setImageOnly(int imageOnly) {
        this.imageOnly = imageOnly;
    }

    public int getOwnAnswer() {
        return this.ownAnswer;
    }

    public void setOwnAnswer(int ownAnswer) {
        this.ownAnswer = ownAnswer;
    }

}
