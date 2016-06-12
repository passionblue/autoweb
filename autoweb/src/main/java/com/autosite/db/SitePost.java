package com.autosite.db;

import java.sql.Timestamp;

/**
 * SitePost entity. @author MyEclipse Persistence Tools
 */

public class SitePost extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pageId;
    private long contentId;
    private long panelId;
    private int postScope;
    private int positionCode;
    private int postType;
    private String postData;
    private String postDataExtra;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;
    private int height;
    private int width;
    private String styleString;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private int hide;

    // Constructors

    /** default constructor */
    public SitePost() {
    }

    /** minimal constructor */
    public SitePost(long siteId, long pageId, long contentId, long panelId, int postScope, int positionCode, int postType, String postData, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.contentId = contentId;
        this.panelId = panelId;
        this.postScope = postScope;
        this.positionCode = positionCode;
        this.postType = postType;
        this.postData = postData;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public SitePost(long siteId, long pageId, long contentId, long panelId, int postScope, int positionCode, int postType, String postData, String postDataExtra, Timestamp timeCreated,
            Timestamp timeUpdated, int height, int width, String styleString, String option1, String option2, String option3, String option4, String option5, int hide) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.contentId = contentId;
        this.panelId = panelId;
        this.postScope = postScope;
        this.positionCode = positionCode;
        this.postType = postType;
        this.postData = postData;
        this.postDataExtra = postDataExtra;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.height = height;
        this.width = width;
        this.styleString = styleString;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.hide = hide;
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

    public long getPageId() {
        return this.pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public long getPanelId() {
        return this.panelId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public int getPostScope() {
        return this.postScope;
    }

    public void setPostScope(int postScope) {
        this.postScope = postScope;
    }

    public int getPositionCode() {
        return this.positionCode;
    }

    public void setPositionCode(int positionCode) {
        this.positionCode = positionCode;
    }

    public int getPostType() {
        return this.postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public String getPostData() {
        return this.postData;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public String getPostDataExtra() {
        return this.postDataExtra;
    }

    public void setPostDataExtra(String postDataExtra) {
        this.postDataExtra = postDataExtra;
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

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getStyleString() {
        return this.styleString;
    }

    public void setStyleString(String styleString) {
        this.styleString = styleString;
    }

    public String getOption1() {
        return this.option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return this.option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return this.option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption5() {
        return this.option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

}
