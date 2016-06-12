package com.autosite.db;

import java.sql.Timestamp;

/**
 * Content entity. @author MyEclipse Persistence Tools
 */

public class Content extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long pageId;
    private long subPageId;
    private long panelId;
    private long categoryId;
    private long userId;
    private String contentSubject;
    private String contentAbstract;
    private String content;
    private int abstractGenerate;
    private int abstractNo;
    private int useExternalData;
    private String authorName;
    private String category;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private int contentType;
    private String sourceName;
    private String sourceUrl;
    private int hide;
    private int showHome;
    private String imageUrl;
    private int imageHeight;
    private int imageWidth;
    private int inHtml;
    private String tags;
    private String extraKeywords;
    private String shortcutUrl;
    private String headerMeta;
    private String headerLink;
    private int columnNum;

    // Constructors

    /** default constructor */
    public Content() {
    }

    /** minimal constructor */
    public Content(long siteId, long pageId, long subPageId, long panelId, String contentSubject, String content, Timestamp createdTime, int contentType) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.subPageId = subPageId;
        this.panelId = panelId;
        this.contentSubject = contentSubject;
        this.content = content;
        this.createdTime = createdTime;
        this.contentType = contentType;
    }

    /** full constructor */
    public Content(long siteId, long pageId, long subPageId, long panelId, long categoryId, long userId, String contentSubject, String contentAbstract, String content, int abstractGenerate,
            int abstractNo, int useExternalData, String authorName, String category, Timestamp createdTime, Timestamp updatedTime, int contentType, String sourceName, String sourceUrl, int hide,
            int showHome, String imageUrl, int imageHeight, int imageWidth, int inHtml, String tags, String extraKeywords, String shortcutUrl, String headerMeta, String headerLink, int columnNum) {
        this.siteId = siteId;
        this.pageId = pageId;
        this.subPageId = subPageId;
        this.panelId = panelId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.contentSubject = contentSubject;
        this.contentAbstract = contentAbstract;
        this.content = content;
        this.abstractGenerate = abstractGenerate;
        this.abstractNo = abstractNo;
        this.useExternalData = useExternalData;
        this.authorName = authorName;
        this.category = category;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.contentType = contentType;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.hide = hide;
        this.showHome = showHome;
        this.imageUrl = imageUrl;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.inHtml = inHtml;
        this.tags = tags;
        this.extraKeywords = extraKeywords;
        this.shortcutUrl = shortcutUrl;
        this.headerMeta = headerMeta;
        this.headerLink = headerLink;
        this.columnNum = columnNum;
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

    public long getSubPageId() {
        return this.subPageId;
    }

    public void setSubPageId(long subPageId) {
        this.subPageId = subPageId;
    }

    public long getPanelId() {
        return this.panelId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContentSubject() {
        return this.contentSubject;
    }

    public void setContentSubject(String contentSubject) {
        this.contentSubject = contentSubject;
    }

    public String getContentAbstract() {
        return this.contentAbstract;
    }

    public void setContentAbstract(String contentAbstract) {
        this.contentAbstract = contentAbstract;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAbstractGenerate() {
        return this.abstractGenerate;
    }

    public void setAbstractGenerate(int abstractGenerate) {
        this.abstractGenerate = abstractGenerate;
    }

    public int getAbstractNo() {
        return this.abstractNo;
    }

    public void setAbstractNo(int abstractNo) {
        this.abstractNo = abstractNo;
    }

    public int getUseExternalData() {
        return this.useExternalData;
    }

    public void setUseExternalData(int useExternalData) {
        this.useExternalData = useExternalData;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getContentType() {
        return this.contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getShowHome() {
        return this.showHome;
    }

    public void setShowHome(int showHome) {
        this.showHome = showHome;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getInHtml() {
        return this.inHtml;
    }

    public void setInHtml(int inHtml) {
        this.inHtml = inHtml;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getExtraKeywords() {
        return this.extraKeywords;
    }

    public void setExtraKeywords(String extraKeywords) {
        this.extraKeywords = extraKeywords;
    }

    public String getShortcutUrl() {
        return this.shortcutUrl;
    }

    public void setShortcutUrl(String shortcutUrl) {
        this.shortcutUrl = shortcutUrl;
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

    public int getColumnNum() {
        return this.columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

}
