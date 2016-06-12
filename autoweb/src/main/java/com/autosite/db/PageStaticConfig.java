package com.autosite.db;

import java.sql.Timestamp;

/**
 * PageStaticConfig entity. @author MyEclipse Persistence Tools
 */

public class PageStaticConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String pageAlias;
    private String pageCss;
    private String pageScript;
    private String pageCssImports;
    private String pageScriptImports;
    private int hideMenu;
    private int hideMid;
    private int hideAd;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public PageStaticConfig() {
    }

    /** minimal constructor */
    public PageStaticConfig(long siteId, String pageAlias, int hideMenu, int hideMid, int hideAd, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.pageAlias = pageAlias;
        this.hideMenu = hideMenu;
        this.hideMid = hideMid;
        this.hideAd = hideAd;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public PageStaticConfig(long siteId, String pageAlias, String pageCss, String pageScript, String pageCssImports, String pageScriptImports, int hideMenu, int hideMid, int hideAd,
            Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.pageAlias = pageAlias;
        this.pageCss = pageCss;
        this.pageScript = pageScript;
        this.pageCssImports = pageCssImports;
        this.pageScriptImports = pageScriptImports;
        this.hideMenu = hideMenu;
        this.hideMid = hideMid;
        this.hideAd = hideAd;
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

    public String getPageAlias() {
        return this.pageAlias;
    }

    public void setPageAlias(String pageAlias) {
        this.pageAlias = pageAlias;
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
