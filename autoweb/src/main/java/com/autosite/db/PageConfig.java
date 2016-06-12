package com.autosite.db;

import java.sql.Timestamp;

/**
 * PageConfig entity. @author MyEclipse Persistence Tools
 */

public class PageConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pageId;
    private int sortType;
    private int arrangeType;
    private String pageCss;
    private String pageScript;
    private String pageCssImports;
    private String pageScriptImports;
    private int hideMenu;
    private int hideMid;
    private int hideAd;
    private long styleId;
    private long contentStyleSetId;
    private String headerMeta;
    private String headerLink;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public PageConfig() {
    }

    /** minimal constructor */
    public PageConfig(long siteId, long pageId, int hideMenu, int hideMid, int hideAd, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.hideMenu = hideMenu;
        this.hideMid = hideMid;
        this.hideAd = hideAd;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public PageConfig(long siteId, long pageId, int sortType, int arrangeType, String pageCss, String pageScript, String pageCssImports, String pageScriptImports, int hideMenu, int hideMid,
            int hideAd, long styleId, long contentStyleSetId, String headerMeta, String headerLink, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.sortType = sortType;
        this.arrangeType = arrangeType;
        this.pageCss = pageCss;
        this.pageScript = pageScript;
        this.pageCssImports = pageCssImports;
        this.pageScriptImports = pageScriptImports;
        this.hideMenu = hideMenu;
        this.hideMid = hideMid;
        this.hideAd = hideAd;
        this.styleId = styleId;
        this.contentStyleSetId = contentStyleSetId;
        this.headerMeta = headerMeta;
        this.headerLink = headerLink;
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

    public long getPageId() {
        return this.pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public int getSortType() {
        return this.sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getArrangeType() {
        return this.arrangeType;
    }

    public void setArrangeType(int arrangeType) {
        this.arrangeType = arrangeType;
    }

    public String getPageCss() {
        return this.pageCss;
    }

    public void setPageCss(String pageCss) {
        this.pageCss = pageCss;
    }

    public String getPageScript() {
        return this.pageScript;
    }

    public void setPageScript(String pageScript) {
        this.pageScript = pageScript;
    }

    public String getPageCssImports() {
        return this.pageCssImports;
    }

    public void setPageCssImports(String pageCssImports) {
        this.pageCssImports = pageCssImports;
    }

    public String getPageScriptImports() {
        return this.pageScriptImports;
    }

    public void setPageScriptImports(String pageScriptImports) {
        this.pageScriptImports = pageScriptImports;
    }

    public int getHideMenu() {
        return this.hideMenu;
    }

    public void setHideMenu(int hideMenu) {
        this.hideMenu = hideMenu;
    }

    public int getHideMid() {
        return this.hideMid;
    }

    public void setHideMid(int hideMid) {
        this.hideMid = hideMid;
    }

    public int getHideAd() {
        return this.hideAd;
    }

    public void setHideAd(int hideAd) {
        this.hideAd = hideAd;
    }

    public long getStyleId() {
        return this.styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public long getContentStyleSetId() {
        return this.contentStyleSetId;
    }

    public void setContentStyleSetId(long contentStyleSetId) {
        this.contentStyleSetId = contentStyleSetId;
    }

    public String getHeaderMeta() {
        return this.headerMeta;
    }

    public void setHeaderMeta(String headerMeta) {
        this.headerMeta = headerMeta;
    }

    public String getHeaderLink() {
        return this.headerLink;
    }

    public void setHeaderLink(String headerLink) {
        this.headerLink = headerLink;
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
