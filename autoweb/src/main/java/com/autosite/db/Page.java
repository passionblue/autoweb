package com.autosite.db;

import java.sql.Timestamp;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class Page extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long menuPanelId;
    private long parentId;
    private String pageName;
    private String pageMenuTitle;
    private int hide;
    private Timestamp createdTime;
    private String siteUrl;
    private int pageColCount;
    private String pageKeywords;
    private int pageViewType;
    private String underlyingPage;
    private int headerPage;
    private int showPageExclusiveOnly;
    private String alt1;
    private String alt2;
    private String alt3;

    // Constructors

    /** default constructor */
    public Page() {
    }

    /** minimal constructor */
    public Page(long menuPanelId, long parentId, String pageName, String pageMenuTitle, int hide, String siteUrl) {
        this.menuPanelId = menuPanelId;
        this.parentId = parentId;
        this.pageName = pageName;
        this.pageMenuTitle = pageMenuTitle;
        this.hide = hide;
        this.siteUrl = siteUrl;
    }

    /** full constructor */
    public Page(long siteId, long menuPanelId, long parentId, String pageName, String pageMenuTitle, int hide, Timestamp createdTime, String siteUrl, int pageColCount, String pageKeywords,
            int pageViewType, String underlyingPage, int headerPage, int showPageExclusiveOnly, String alt1, String alt2, String alt3) {
        this.siteId = siteId;
        this.menuPanelId = menuPanelId;
        this.parentId = parentId;
        this.pageName = pageName;
        this.pageMenuTitle = pageMenuTitle;
        this.hide = hide;
        this.createdTime = createdTime;
        this.siteUrl = siteUrl;
        this.pageColCount = pageColCount;
        this.pageKeywords = pageKeywords;
        this.pageViewType = pageViewType;
        this.underlyingPage = underlyingPage;
        this.headerPage = headerPage;
        this.showPageExclusiveOnly = showPageExclusiveOnly;
        this.alt1 = alt1;
        this.alt2 = alt2;
        this.alt3 = alt3;
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

    public long getMenuPanelId() {
        return this.menuPanelId;
    }

    public void setMenuPanelId(long menuPanelId) {
        this.menuPanelId = menuPanelId;
    }

    public long getParentId() {
        return this.parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageMenuTitle() {
        return this.pageMenuTitle;
    }

    public void setPageMenuTitle(String pageMenuTitle) {
        this.pageMenuTitle = pageMenuTitle;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public Timestamp getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getSiteUrl() {
        return this.siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public int getPageColCount() {
        return this.pageColCount;
    }

    public void setPageColCount(int pageColCount) {
        this.pageColCount = pageColCount;
    }

    public String getPageKeywords() {
        return this.pageKeywords;
    }

    public void setPageKeywords(String pageKeywords) {
        this.pageKeywords = pageKeywords;
    }

    public int getPageViewType() {
        return this.pageViewType;
    }

    public void setPageViewType(int pageViewType) {
        this.pageViewType = pageViewType;
    }

    public String getUnderlyingPage() {
        return this.underlyingPage;
    }

    public void setUnderlyingPage(String underlyingPage) {
        this.underlyingPage = underlyingPage;
    }

    public int getHeaderPage() {
        return this.headerPage;
    }

    public void setHeaderPage(int headerPage) {
        this.headerPage = headerPage;
    }

    public int getShowPageExclusiveOnly() {
        return this.showPageExclusiveOnly;
    }

    public void setShowPageExclusiveOnly(int showPageExclusiveOnly) {
        this.showPageExclusiveOnly = showPageExclusiveOnly;
    }

    public String getAlt1() {
        return this.alt1;
    }

    public void setAlt1(String alt1) {
        this.alt1 = alt1;
    }

    public String getAlt2() {
        return this.alt2;
    }

    public void setAlt2(String alt2) {
        this.alt2 = alt2;
    }

    public String getAlt3() {
        return this.alt3;
    }

    public void setAlt3(String alt3) {
        this.alt3 = alt3;
    }

}
