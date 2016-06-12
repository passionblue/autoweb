package com.autosite.db;

import java.sql.Timestamp;

/**
 * BlogComment entity. @author MyEclipse Persistence Tools
 */

public class BlogComment extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long blogMainId;
    private long blogPostId;
    private String comment;
    private int rating;
    private String ipaddress;
    private String name;
    private String password;
    private String website;
    private String email;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public BlogComment() {
    }

    /** minimal constructor */
    public BlogComment(long siteId, long blogMainId, long blogPostId, String comment, String password, Timestamp timeCreated) {
        this.siteId = siteId;
        this.blogMainId = blogMainId;
        this.blogPostId = blogPostId;
        this.comment = comment;
        this.password = password;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public BlogComment(long siteId, long blogMainId, long blogPostId, String comment, int rating, String ipaddress, String name, String password, String website, String email, Timestamp timeCreated) {
        this.siteId = siteId;
        this.blogMainId = blogMainId;
        this.blogPostId = blogPostId;
        this.comment = comment;
        this.rating = rating;
        this.ipaddress = ipaddress;
        this.name = name;
        this.password = password;
        this.website = website;
        this.email = email;
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

    public long getBlogMainId() {
        return this.blogMainId;
    }

    public void setBlogMainId(long blogMainId) {
        this.blogMainId = blogMainId;
    }

    public long getBlogPostId() {
        return this.blogPostId;
    }

    public void setBlogPostId(long blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getIpaddress() {
        return this.ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
