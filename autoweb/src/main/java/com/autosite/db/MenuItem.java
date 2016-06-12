package com.autosite.db;

import java.sql.Timestamp;

/**
 * MenuItem entity. @author MyEclipse Persistence Tools
 */

public class MenuItem extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long panelId;
    private long parentId;
    private String title;
    private String data;
    private String data2;
    private int targetType;
    private int orderIdx;
    private long pageId;
    private long contentId;
    private int columNum;
    private int hide;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public MenuItem() {
    }

    /** minimal constructor */
    public MenuItem(long siteId, long panelId, long parentId, String title, Timestamp timeCreated) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.parentId = parentId;
        this.title = title;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public MenuItem(long siteId, long panelId, long parentId, String title, String data, String data2, int targetType, int orderIdx, long pageId, long contentId, int columNum, int hide,
            Timestamp timeCreated) {
        this.siteId = siteId;
        this.panelId = panelId;
        this.parentId = parentId;
        this.title = title;
        this.data = data;
        this.data2 = data2;
        this.targetType = targetType;
        this.orderIdx = orderIdx;
        this.pageId = pageId;
        this.contentId = contentId;
        this.columNum = columNum;
        this.hide = hide;
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

    public long getPanelId() {
        return this.panelId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public long getParentId() {
        return this.parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getTargetType() {
        return this.targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int getOrderIdx() {
        return this.orderIdx;
    }

    public void setOrderIdx(int orderIdx) {
        this.orderIdx = orderIdx;
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

    public int getColumNum() {
        return this.columNum;
    }

    public void setColumNum(int columNum) {
        this.columNum = columNum;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
