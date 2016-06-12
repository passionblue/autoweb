package com.autosite.db;

import java.sql.Timestamp;

/**
 * PageStaticAlt entity. @author MyEclipse Persistence Tools
 */

public class PageStaticAlt extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String pageAlias;
    private String pageUrl;
    private String menuPage;
    private String errorPage;
    private int loginRequired;
    private String viewProc;
    private int dynamicContent;
    private int hideMenu;
    private int hideMid;
    private int hideAd;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public PageStaticAlt() {
    }

    /** minimal constructor */
    public PageStaticAlt(long siteId, String pageAlias, String pageUrl, Timestamp timeCreated) {
        this.siteId = siteId;
        this.pageAlias = pageAlias;
        this.pageUrl = pageUrl;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public PageStaticAlt(long siteId, String pageAlias, String pageUrl, String menuPage, String errorPage, int loginRequired, String viewProc, int dynamicContent, int hideMenu, int hideMid,
            int hideAd, Timestamp timeCreated) {
        this.siteId = siteId;
        this.pageAlias = pageAlias;
        this.pageUrl = pageUrl;
        this.menuPage = menuPage;
        this.errorPage = errorPage;
        this.loginRequired = loginRequired;
        this.viewProc = viewProc;
        this.dynamicContent = dynamicContent;
        this.hideMenu = hideMenu;
        this.hideMid = hideMid;
        this.hideAd = hideAd;
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

    public String getPageAlias() {
        return this.pageAlias;
    }

    public void setPageAlias(String pageAlias) {
        this.pageAlias = pageAlias;
    }

    public String getPageUrl() {
        return this.pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getMenuPage() {
        return this.menuPage;
    }

    public void setMenuPage(String menuPage) {
        this.menuPage = menuPage;
    }

    public String getErrorPage() {
        return this.errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public int getLoginRequired() {
        return this.loginRequired;
    }

    public void setLoginRequired(int loginRequired) {
        this.loginRequired = loginRequired;
    }

    public String getViewProc() {
        return this.viewProc;
    }

    public void setViewProc(String viewProc) {
        this.viewProc = viewProc;
    }

    public int getDynamicContent() {
        return this.dynamicContent;
    }

    public void setDynamicContent(int dynamicContent) {
        this.dynamicContent = dynamicContent;
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

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
