package com.autosite.db;

import java.sql.Timestamp;

/**
 * BlogPost entity. @author MyEclipse Persistence Tools
 */

public class BlogPost extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long blogMainId;
    private long categoryId;
    private String subject;
    private String content;
    private int postType;
    private String author;
    private String contentImage;
    private String imageUrl1;
    private String imageUrl2;
    private String tags;
    private String shorcutUrl;
    private int hide;
    private int postYear;
    private int postMonth;
    private int postDay;
    private int postYearmonth;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public BlogPost() {
    }

    /** minimal constructor */
    public BlogPost(long siteId, long blogMainId, String subject, String content, int postYear, int postMonth, int postDay, int postYearmonth) {
        this.siteId = siteId;
        this.blogMainId = blogMainId;
        this.subject = subject;
        this.content = content;
        this.postYear = postYear;
        this.postMonth = postMonth;
        this.postDay = postDay;
        this.postYearmonth = postYearmonth;
    }

    /** full constructor */
    public BlogPost(long siteId, long blogMainId, long categoryId, String subject, String content, int postType, String author, String contentImage, String imageUrl1, String imageUrl2, String tags,
            String shorcutUrl, int hide, int postYear, int postMonth, int postDay, int postYearmonth, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.blogMainId = blogMainId;
        this.categoryId = categoryId;
        this.subject = subject;
        this.content = content;
        this.postType = postType;
        this.author = author;
        this.contentImage = contentImage;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.tags = tags;
        this.shorcutUrl = shorcutUrl;
        this.hide = hide;
        this.postYear = postYear;
        this.postMonth = postMonth;
        this.postDay = postDay;
        this.postYearmonth = postYearmonth;
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

    public long getBlogMainId() {
        return this.blogMainId;
    }

    public void setBlogMainId(long blogMainId) {
        this.blogMainId = blogMainId;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostType() {
        return this.postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContentImage() {
        return this.contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getImageUrl1() {
        return this.imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return this.imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getShorcutUrl() {
        return this.shorcutUrl;
    }

    public void setShorcutUrl(String shorcutUrl) {
        this.shorcutUrl = shorcutUrl;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getPostYear() {
        return this.postYear;
    }

    public void setPostYear(int postYear) {
        this.postYear = postYear;
    }

    public int getPostMonth() {
        return this.postMonth;
    }

    public void setPostMonth(int postMonth) {
        this.postMonth = postMonth;
    }

    public int getPostDay() {
        return this.postDay;
    }

    public void setPostDay(int postDay) {
        this.postDay = postDay;
    }

    public int getPostYearmonth() {
        return this.postYearmonth;
    }

    public void setPostYearmonth(int postYearmonth) {
        this.postYearmonth = postYearmonth;
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
