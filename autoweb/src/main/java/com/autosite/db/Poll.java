package com.autosite.db;

import java.sql.Timestamp;

/**
 * Poll entity. @author MyEclipse Persistence Tools
 */

public class Poll extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private String serial;
    private int type;
    private String category;
    private String title;
    private String description;
    private String question;
    private String tags;
    private int published;
    private int hide;
    private int disable;
    private int allowMultiple;
    private int allowOwnAnswer;
    private int randomAnswer;
    private int hideComments;
    private int hideResults;
    private int hideHomeLink;
    private int showSponsor;
    private int useCookieForDup;
    private int repeatEveryDay;
    private int maxRepeatVote;
    private int numDaysOpen;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;
    private Timestamp timeExpired;

    // Constructors

    /** default constructor */
    public Poll() {
    }

    /** minimal constructor */
    public Poll(long siteId, String serial, String question, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.serial = serial;
        this.question = question;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public Poll(long siteId, long userId, String serial, int type, String category, String title, String description, String question, String tags, int published, int hide, int disable,
            int allowMultiple, int allowOwnAnswer, int randomAnswer, int hideComments, int hideResults, int hideHomeLink, int showSponsor, int useCookieForDup, int repeatEveryDay, int maxRepeatVote,
            int numDaysOpen, Timestamp timeCreated, Timestamp timeUpdated, Timestamp timeExpired) {
        this.siteId = siteId;
        this.userId = userId;
        this.serial = serial;
        this.type = type;
        this.category = category;
        this.title = title;
        this.description = description;
        this.question = question;
        this.tags = tags;
        this.published = published;
        this.hide = hide;
        this.disable = disable;
        this.allowMultiple = allowMultiple;
        this.allowOwnAnswer = allowOwnAnswer;
        this.randomAnswer = randomAnswer;
        this.hideComments = hideComments;
        this.hideResults = hideResults;
        this.hideHomeLink = hideHomeLink;
        this.showSponsor = showSponsor;
        this.useCookieForDup = useCookieForDup;
        this.repeatEveryDay = repeatEveryDay;
        this.maxRepeatVote = maxRepeatVote;
        this.numDaysOpen = numDaysOpen;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.timeExpired = timeExpired;
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

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getPublished() {
        return this.published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getDisable() {
        return this.disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public int getAllowMultiple() {
        return this.allowMultiple;
    }

    public void setAllowMultiple(int allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public int getAllowOwnAnswer() {
        return this.allowOwnAnswer;
    }

    public void setAllowOwnAnswer(int allowOwnAnswer) {
        this.allowOwnAnswer = allowOwnAnswer;
    }

    public int getRandomAnswer() {
        return this.randomAnswer;
    }

    public void setRandomAnswer(int randomAnswer) {
        this.randomAnswer = randomAnswer;
    }

    public int getHideComments() {
        return this.hideComments;
    }

    public void setHideComments(int hideComments) {
        this.hideComments = hideComments;
    }

    public int getHideResults() {
        return this.hideResults;
    }

    public void setHideResults(int hideResults) {
        this.hideResults = hideResults;
    }

    public int getHideHomeLink() {
        return this.hideHomeLink;
    }

    public void setHideHomeLink(int hideHomeLink) {
        this.hideHomeLink = hideHomeLink;
    }

    public int getShowSponsor() {
        return this.showSponsor;
    }

    public void setShowSponsor(int showSponsor) {
        this.showSponsor = showSponsor;
    }

    public int getUseCookieForDup() {
        return this.useCookieForDup;
    }

    public void setUseCookieForDup(int useCookieForDup) {
        this.useCookieForDup = useCookieForDup;
    }

    public int getRepeatEveryDay() {
        return this.repeatEveryDay;
    }

    public void setRepeatEveryDay(int repeatEveryDay) {
        this.repeatEveryDay = repeatEveryDay;
    }

    public int getMaxRepeatVote() {
        return this.maxRepeatVote;
    }

    public void setMaxRepeatVote(int maxRepeatVote) {
        this.maxRepeatVote = maxRepeatVote;
    }

    public int getNumDaysOpen() {
        return this.numDaysOpen;
    }

    public void setNumDaysOpen(int numDaysOpen) {
        this.numDaysOpen = numDaysOpen;
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

    public Timestamp getTimeExpired() {
        return this.timeExpired;
    }

    public void setTimeExpired(Timestamp timeExpired) {
        this.timeExpired = timeExpired;
    }

}
