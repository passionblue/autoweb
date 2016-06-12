package com.autosite.db;

/**
 * VelocityMain entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VelocityMain extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pageId;
    private String data;
    private String data2;
    private String title;
    private int age;

    // Constructors

    /** default constructor */
    public VelocityMain() {
    }

    /** minimal constructor */
    public VelocityMain(long siteId, long pageId, String data2, String title, int age) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.data2 = data2;
        this.title = title;
        this.age = age;
    }

    /** full constructor */
    public VelocityMain(long siteId, long pageId, String data, String data2, String title, int age) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.data = data;
        this.data2 = data2;
        this.title = title;
        this.age = age;
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

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData2() {
        return this.data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
