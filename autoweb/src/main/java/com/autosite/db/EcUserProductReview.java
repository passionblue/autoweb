package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcUserProductReview entity. @author MyEclipse Persistence Tools
 */

public class EcUserProductReview extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long productId;
    private long userId;
    private int rate;
    private String review;
    private String trackBack;
    private int numVoteYes;
    private int numVoteNo;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public EcUserProductReview() {
    }

    /** minimal constructor */
    public EcUserProductReview(long userId, int rate, String review, Timestamp timeCreated) {
        this.userId = userId;
        this.rate = rate;
        this.review = review;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public EcUserProductReview(long siteId, long productId, long userId, int rate, String review, String trackBack, int numVoteYes, int numVoteNo, Timestamp timeCreated) {
        this.siteId = siteId;
        this.productId = productId;
        this.userId = userId;
        this.rate = rate;
        this.review = review;
        this.trackBack = trackBack;
        this.numVoteYes = numVoteYes;
        this.numVoteNo = numVoteNo;
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

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTrackBack() {
        return this.trackBack;
    }

    public void setTrackBack(String trackBack) {
        this.trackBack = trackBack;
    }

    public int getNumVoteYes() {
        return this.numVoteYes;
    }

    public void setNumVoteYes(int numVoteYes) {
        this.numVoteYes = numVoteYes;
    }

    public int getNumVoteNo() {
        return this.numVoteNo;
    }

    public void setNumVoteNo(int numVoteNo) {
        this.numVoteNo = numVoteNo;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
