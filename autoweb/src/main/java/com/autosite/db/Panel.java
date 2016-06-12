package com.autosite.db;

import java.sql.Timestamp;

/**
 * Panel entity. @author MyEclipse Persistence Tools
 */

public class Panel extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pageId;
    private int pageOnly;
    private int pageOnlyGroup;
    private int columnNum;
    private int contentOnly;
    private int panelType;
    private int panelSubType;
    private String panelTitle;
    private int panelHeight;
    private int hide;
    private Timestamp timeCreated;
    private int topSpace;
    private int bottomSpace;
    private int leftSpace;
    private int rightSpace;
    private String styleString;
    private String titleStyleString;
    private String styleString2;
    private String styleDefaultCode;
    private int align;
    private int columnCount;
    private int pageDisplaySummary;
    private int showInPrint;
    private int showOnlyPrint;
    private int adminOnly;
    private long feedId;
    private String option1;
    private String option2;
    private String option3;

    // Constructors

    /** default constructor */
    public Panel() {
    }

    /** minimal constructor */
    public Panel(long siteId, long pageId, int pageOnly, int columnNum, int contentOnly, int panelType, int panelSubType, int panelHeight, int hide, Timestamp timeCreated, int topSpace,
            int bottomSpace, int leftSpace, int rightSpace) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.pageOnly = pageOnly;
        this.columnNum = columnNum;
        this.contentOnly = contentOnly;
        this.panelType = panelType;
        this.panelSubType = panelSubType;
        this.panelHeight = panelHeight;
        this.hide = hide;
        this.timeCreated = timeCreated;
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
    }

    /** full constructor */
    public Panel(long siteId, long pageId, int pageOnly, int pageOnlyGroup, int columnNum, int contentOnly, int panelType, int panelSubType, String panelTitle, int panelHeight, int hide,
            Timestamp timeCreated, int topSpace, int bottomSpace, int leftSpace, int rightSpace, String styleString, String titleStyleString, String styleString2, String styleDefaultCode, int align,
            int columnCount, int pageDisplaySummary, int showInPrint, int showOnlyPrint, int adminOnly, long feedId, String option1, String option2, String option3) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.pageOnly = pageOnly;
        this.pageOnlyGroup = pageOnlyGroup;
        this.columnNum = columnNum;
        this.contentOnly = contentOnly;
        this.panelType = panelType;
        this.panelSubType = panelSubType;
        this.panelTitle = panelTitle;
        this.panelHeight = panelHeight;
        this.hide = hide;
        this.timeCreated = timeCreated;
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
        this.styleString = styleString;
        this.titleStyleString = titleStyleString;
        this.styleString2 = styleString2;
        this.styleDefaultCode = styleDefaultCode;
        this.align = align;
        this.columnCount = columnCount;
        this.pageDisplaySummary = pageDisplaySummary;
        this.showInPrint = showInPrint;
        this.showOnlyPrint = showOnlyPrint;
        this.adminOnly = adminOnly;
        this.feedId = feedId;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
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

    public int getPageOnly() {
        return this.pageOnly;
    }

    public void setPageOnly(int pageOnly) {
        this.pageOnly = pageOnly;
    }

    public int getPageOnlyGroup() {
        return this.pageOnlyGroup;
    }

    public void setPageOnlyGroup(int pageOnlyGroup) {
        this.pageOnlyGroup = pageOnlyGroup;
    }

    public int getColumnNum() {
        return this.columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public int getContentOnly() {
        return this.contentOnly;
    }

    public void setContentOnly(int contentOnly) {
        this.contentOnly = contentOnly;
    }

    public int getPanelType() {
        return this.panelType;
    }

    public void setPanelType(int panelType) {
        this.panelType = panelType;
    }

    public int getPanelSubType() {
        return this.panelSubType;
    }

    public void setPanelSubType(int panelSubType) {
        this.panelSubType = panelSubType;
    }

    public String getPanelTitle() {
        return this.panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public int getPanelHeight() {
        return this.panelHeight;
    }

    public void setPanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
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

    public int getTopSpace() {
        return this.topSpace;
    }

    public void setTopSpace(int topSpace) {
        this.topSpace = topSpace;
    }

    public int getBottomSpace() {
        return this.bottomSpace;
    }

    public void setBottomSpace(int bottomSpace) {
        this.bottomSpace = bottomSpace;
    }

    public int getLeftSpace() {
        return this.leftSpace;
    }

    public void setLeftSpace(int leftSpace) {
        this.leftSpace = leftSpace;
    }

    public int getRightSpace() {
        return this.rightSpace;
    }

    public void setRightSpace(int rightSpace) {
        this.rightSpace = rightSpace;
    }

    public String getStyleString() {
        return this.styleString;
    }

    public void setStyleString(String styleString) {
        this.styleString = styleString;
    }

    public String getTitleStyleString() {
        return this.titleStyleString;
    }

    public void setTitleStyleString(String titleStyleString) {
        this.titleStyleString = titleStyleString;
    }

    public String getStyleString2() {
        return this.styleString2;
    }

    public void setStyleString2(String styleString2) {
        this.styleString2 = styleString2;
    }

    public String getStyleDefaultCode() {
        return this.styleDefaultCode;
    }

    public void setStyleDefaultCode(String styleDefaultCode) {
        this.styleDefaultCode = styleDefaultCode;
    }

    public int getAlign() {
        return this.align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getPageDisplaySummary() {
        return this.pageDisplaySummary;
    }

    public void setPageDisplaySummary(int pageDisplaySummary) {
        this.pageDisplaySummary = pageDisplaySummary;
    }

    public int getShowInPrint() {
        return this.showInPrint;
    }

    public void setShowInPrint(int showInPrint) {
        this.showInPrint = showInPrint;
    }

    public int getShowOnlyPrint() {
        return this.showOnlyPrint;
    }

    public void setShowOnlyPrint(int showOnlyPrint) {
        this.showOnlyPrint = showOnlyPrint;
    }

    public int getAdminOnly() {
        return this.adminOnly;
    }

    public void setAdminOnly(int adminOnly) {
        this.adminOnly = adminOnly;
    }

    public long getFeedId() {
        return this.feedId;
    }

    public void setFeedId(long feedId) {
        this.feedId = feedId;
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

}
