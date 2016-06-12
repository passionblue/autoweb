package com.autosite.db;

import java.sql.Timestamp;

/**
 * StyleTheme entity. @author MyEclipse Persistence Tools
 */

public class StyleTheme extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String title;
    private int bodyWidth;
    private String bodyAlign;
    private String bodyBgColor;
    private String bodyBgImage;
    private String bodyBgAttach;
    private String bodyBgRepeat;
    private String bodyBgPosition;
    private String contentBgColor;
    private int useAbsolute;
    private int absoluteTop;
    private int absoluteLeft;
    private int absoluteRight;
    private int absoluteBottom;
    private long panelStyleId;
    private long panelDataStyleId;
    private long panelLinkStyleId;
    private long panelTitleStyleId;
    private long menuStyleId;
    private long menuLinkStyleId;
    private long headerMenuStyleId;
    private long headerMenuLinkStyleId;
    private long listFrameStyleId;
    private long listSubjectStyleId;
    private long listDataStyleId;
    private long subjectStyleId;
    private long dataStyleId;
    private long singleFrameStyleId;
    private long singleSubjectStyleId;
    private long singleDataStyleId;
    private long contentPanelStyleId;
    private long contentPanelTitleStyleId;
    private int global;
    private int disable;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public StyleTheme() {
    }

    /** minimal constructor */
    public StyleTheme(long siteId, String title, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.title = title;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public StyleTheme(long siteId, String title, int bodyWidth, String bodyAlign, String bodyBgColor, String bodyBgImage, String bodyBgAttach, String bodyBgRepeat, String bodyBgPosition,
            String contentBgColor, int useAbsolute, int absoluteTop, int absoluteLeft, int absoluteRight, int absoluteBottom, long panelStyleId, long panelDataStyleId, long panelLinkStyleId,
            long panelTitleStyleId, long menuStyleId, long menuLinkStyleId, long headerMenuStyleId, long headerMenuLinkStyleId, long listFrameStyleId, long listSubjectStyleId, long listDataStyleId,
            long subjectStyleId, long dataStyleId, long singleFrameStyleId, long singleSubjectStyleId, long singleDataStyleId, long contentPanelStyleId, long contentPanelTitleStyleId, int global,
            int disable, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.title = title;
        this.bodyWidth = bodyWidth;
        this.bodyAlign = bodyAlign;
        this.bodyBgColor = bodyBgColor;
        this.bodyBgImage = bodyBgImage;
        this.bodyBgAttach = bodyBgAttach;
        this.bodyBgRepeat = bodyBgRepeat;
        this.bodyBgPosition = bodyBgPosition;
        this.contentBgColor = contentBgColor;
        this.useAbsolute = useAbsolute;
        this.absoluteTop = absoluteTop;
        this.absoluteLeft = absoluteLeft;
        this.absoluteRight = absoluteRight;
        this.absoluteBottom = absoluteBottom;
        this.panelStyleId = panelStyleId;
        this.panelDataStyleId = panelDataStyleId;
        this.panelLinkStyleId = panelLinkStyleId;
        this.panelTitleStyleId = panelTitleStyleId;
        this.menuStyleId = menuStyleId;
        this.menuLinkStyleId = menuLinkStyleId;
        this.headerMenuStyleId = headerMenuStyleId;
        this.headerMenuLinkStyleId = headerMenuLinkStyleId;
        this.listFrameStyleId = listFrameStyleId;
        this.listSubjectStyleId = listSubjectStyleId;
        this.listDataStyleId = listDataStyleId;
        this.subjectStyleId = subjectStyleId;
        this.dataStyleId = dataStyleId;
        this.singleFrameStyleId = singleFrameStyleId;
        this.singleSubjectStyleId = singleSubjectStyleId;
        this.singleDataStyleId = singleDataStyleId;
        this.contentPanelStyleId = contentPanelStyleId;
        this.contentPanelTitleStyleId = contentPanelTitleStyleId;
        this.global = global;
        this.disable = disable;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBodyWidth() {
        return this.bodyWidth;
    }

    public void setBodyWidth(int bodyWidth) {
        this.bodyWidth = bodyWidth;
    }

    public String getBodyAlign() {
        return this.bodyAlign;
    }

    public void setBodyAlign(String bodyAlign) {
        this.bodyAlign = bodyAlign;
    }

    public String getBodyBgColor() {
        return this.bodyBgColor;
    }

    public void setBodyBgColor(String bodyBgColor) {
        this.bodyBgColor = bodyBgColor;
    }

    public String getBodyBgImage() {
        return this.bodyBgImage;
    }

    public void setBodyBgImage(String bodyBgImage) {
        this.bodyBgImage = bodyBgImage;
    }

    public String getBodyBgAttach() {
        return this.bodyBgAttach;
    }

    public void setBodyBgAttach(String bodyBgAttach) {
        this.bodyBgAttach = bodyBgAttach;
    }

    public String getBodyBgRepeat() {
        return this.bodyBgRepeat;
    }

    public void setBodyBgRepeat(String bodyBgRepeat) {
        this.bodyBgRepeat = bodyBgRepeat;
    }

    public String getBodyBgPosition() {
        return this.bodyBgPosition;
    }

    public void setBodyBgPosition(String bodyBgPosition) {
        this.bodyBgPosition = bodyBgPosition;
    }

    public String getContentBgColor() {
        return this.contentBgColor;
    }

    public void setContentBgColor(String contentBgColor) {
        this.contentBgColor = contentBgColor;
    }

    public int getUseAbsolute() {
        return this.useAbsolute;
    }

    public void setUseAbsolute(int useAbsolute) {
        this.useAbsolute = useAbsolute;
    }

    public int getAbsoluteTop() {
        return this.absoluteTop;
    }

    public void setAbsoluteTop(int absoluteTop) {
        this.absoluteTop = absoluteTop;
    }

    public int getAbsoluteLeft() {
        return this.absoluteLeft;
    }

    public void setAbsoluteLeft(int absoluteLeft) {
        this.absoluteLeft = absoluteLeft;
    }

    public int getAbsoluteRight() {
        return this.absoluteRight;
    }

    public void setAbsoluteRight(int absoluteRight) {
        this.absoluteRight = absoluteRight;
    }

    public int getAbsoluteBottom() {
        return this.absoluteBottom;
    }

    public void setAbsoluteBottom(int absoluteBottom) {
        this.absoluteBottom = absoluteBottom;
    }

    public long getPanelStyleId() {
        return this.panelStyleId;
    }

    public void setPanelStyleId(long panelStyleId) {
        this.panelStyleId = panelStyleId;
    }

    public long getPanelDataStyleId() {
        return this.panelDataStyleId;
    }

    public void setPanelDataStyleId(long panelDataStyleId) {
        this.panelDataStyleId = panelDataStyleId;
    }

    public long getPanelLinkStyleId() {
        return this.panelLinkStyleId;
    }

    public void setPanelLinkStyleId(long panelLinkStyleId) {
        this.panelLinkStyleId = panelLinkStyleId;
    }

    public long getPanelTitleStyleId() {
        return this.panelTitleStyleId;
    }

    public void setPanelTitleStyleId(long panelTitleStyleId) {
        this.panelTitleStyleId = panelTitleStyleId;
    }

    public long getMenuStyleId() {
        return this.menuStyleId;
    }

    public void setMenuStyleId(long menuStyleId) {
        this.menuStyleId = menuStyleId;
    }

    public long getMenuLinkStyleId() {
        return this.menuLinkStyleId;
    }

    public void setMenuLinkStyleId(long menuLinkStyleId) {
        this.menuLinkStyleId = menuLinkStyleId;
    }

    public long getHeaderMenuStyleId() {
        return this.headerMenuStyleId;
    }

    public void setHeaderMenuStyleId(long headerMenuStyleId) {
        this.headerMenuStyleId = headerMenuStyleId;
    }

    public long getHeaderMenuLinkStyleId() {
        return this.headerMenuLinkStyleId;
    }

    public void setHeaderMenuLinkStyleId(long headerMenuLinkStyleId) {
        this.headerMenuLinkStyleId = headerMenuLinkStyleId;
    }

    public long getListFrameStyleId() {
        return this.listFrameStyleId;
    }

    public void setListFrameStyleId(long listFrameStyleId) {
        this.listFrameStyleId = listFrameStyleId;
    }

    public long getListSubjectStyleId() {
        return this.listSubjectStyleId;
    }

    public void setListSubjectStyleId(long listSubjectStyleId) {
        this.listSubjectStyleId = listSubjectStyleId;
    }

    public long getListDataStyleId() {
        return this.listDataStyleId;
    }

    public void setListDataStyleId(long listDataStyleId) {
        this.listDataStyleId = listDataStyleId;
    }

    public long getSubjectStyleId() {
        return this.subjectStyleId;
    }

    public void setSubjectStyleId(long subjectStyleId) {
        this.subjectStyleId = subjectStyleId;
    }

    public long getDataStyleId() {
        return this.dataStyleId;
    }

    public void setDataStyleId(long dataStyleId) {
        this.dataStyleId = dataStyleId;
    }

    public long getSingleFrameStyleId() {
        return this.singleFrameStyleId;
    }

    public void setSingleFrameStyleId(long singleFrameStyleId) {
        this.singleFrameStyleId = singleFrameStyleId;
    }

    public long getSingleSubjectStyleId() {
        return this.singleSubjectStyleId;
    }

    public void setSingleSubjectStyleId(long singleSubjectStyleId) {
        this.singleSubjectStyleId = singleSubjectStyleId;
    }

    public long getSingleDataStyleId() {
        return this.singleDataStyleId;
    }

    public void setSingleDataStyleId(long singleDataStyleId) {
        this.singleDataStyleId = singleDataStyleId;
    }

    public long getContentPanelStyleId() {
        return this.contentPanelStyleId;
    }

    public void setContentPanelStyleId(long contentPanelStyleId) {
        this.contentPanelStyleId = contentPanelStyleId;
    }

    public long getContentPanelTitleStyleId() {
        return this.contentPanelTitleStyleId;
    }

    public void setContentPanelTitleStyleId(long contentPanelTitleStyleId) {
        this.contentPanelTitleStyleId = contentPanelTitleStyleId;
    }

    public int getGlobal() {
        return this.global;
    }

    public void setGlobal(int global) {
        this.global = global;
    }

    public int getDisable() {
        return this.disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
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
