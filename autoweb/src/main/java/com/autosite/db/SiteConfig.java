package com.autosite.db;

import java.sql.Timestamp;

/**
 * SiteConfig entity. @author MyEclipse Persistence Tools
 */

public class SiteConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private Timestamp timeCreatd;
    private Timestamp timeUpdated;
    private String keywords;
    private String meta;
    private int width;
    private String siteTrackGoogle;
    private int homeColCount;
    private String colorSubmenuBg;
    private String contactInfo;
    private String colorBorders;
    private int siteGroup;
    private String headerImage;
    private String headerAd;
    private int menuWidth;
    private int adWidth;
    private String menuFrontColor;
    private String menuBackColor;
    private String menuLineColor;
    private int menuReverse;
    private int displayAllHome;
    private int headerImageHeight;
    private int headerImageWidth;
    private String layoutPage;
    private String underlyingHome;
    private int underlyingDynamicPage;
    private String contentForwardSite;
    private int showMidColumn;
    private int showMenuColumn;
    private int showAdColumn;
    private int showHomeMenu;
    private int showHomeMid;
    private int showHomeAd;
    private int showTopMenu;
    private int midColumnWidth;
    private int useWysiwygContent;
    private int useWysiwygPost;
    private String backgroundImage;
    private String backgroundRepeat;
    private String backgroundAttach;
    private String backgroundColor;
    private String backgroundPosition;
    private String contentBgColor;
    private int absolutePosition;
    private int absoluteTop;
    private int absoluteLeft;
    private int functionalType;
    private int ecEnabled;
    private int frontDisplayFeed;
    private String siteIconUrl;

    // Constructors

    /** default constructor */
    public SiteConfig() {
    }

    /** minimal constructor */
    public SiteConfig(long siteId, Timestamp timeCreatd, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.timeCreatd = timeCreatd;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public SiteConfig(long siteId, Timestamp timeCreatd, Timestamp timeUpdated, String keywords, String meta, int width, String siteTrackGoogle, int homeColCount, String colorSubmenuBg,
            String contactInfo, String colorBorders, int siteGroup, String headerImage, String headerAd, int menuWidth, int adWidth, String menuFrontColor, String menuBackColor, String menuLineColor,
            int menuReverse, int displayAllHome, int headerImageHeight, int headerImageWidth, String layoutPage, String underlyingHome, int underlyingDynamicPage, String contentForwardSite,
            int showMidColumn, int showMenuColumn, int showAdColumn, int showHomeMenu, int showHomeMid, int showHomeAd, int showTopMenu, int midColumnWidth, int useWysiwygContent, int useWysiwygPost,
            String backgroundImage, String backgroundRepeat, String backgroundAttach, String backgroundColor, String backgroundPosition, String contentBgColor, int absolutePosition, int absoluteTop,
            int absoluteLeft, int functionalType, int ecEnabled, int frontDisplayFeed, String siteIconUrl) {
        this.siteId = siteId;
        this.timeCreatd = timeCreatd;
        this.timeUpdated = timeUpdated;
        this.keywords = keywords;
        this.meta = meta;
        this.width = width;
        this.siteTrackGoogle = siteTrackGoogle;
        this.homeColCount = homeColCount;
        this.colorSubmenuBg = colorSubmenuBg;
        this.contactInfo = contactInfo;
        this.colorBorders = colorBorders;
        this.siteGroup = siteGroup;
        this.headerImage = headerImage;
        this.headerAd = headerAd;
        this.menuWidth = menuWidth;
        this.adWidth = adWidth;
        this.menuFrontColor = menuFrontColor;
        this.menuBackColor = menuBackColor;
        this.menuLineColor = menuLineColor;
        this.menuReverse = menuReverse;
        this.displayAllHome = displayAllHome;
        this.headerImageHeight = headerImageHeight;
        this.headerImageWidth = headerImageWidth;
        this.layoutPage = layoutPage;
        this.underlyingHome = underlyingHome;
        this.underlyingDynamicPage = underlyingDynamicPage;
        this.contentForwardSite = contentForwardSite;
        this.showMidColumn = showMidColumn;
        this.showMenuColumn = showMenuColumn;
        this.showAdColumn = showAdColumn;
        this.showHomeMenu = showHomeMenu;
        this.showHomeMid = showHomeMid;
        this.showHomeAd = showHomeAd;
        this.showTopMenu = showTopMenu;
        this.midColumnWidth = midColumnWidth;
        this.useWysiwygContent = useWysiwygContent;
        this.useWysiwygPost = useWysiwygPost;
        this.backgroundImage = backgroundImage;
        this.backgroundRepeat = backgroundRepeat;
        this.backgroundAttach = backgroundAttach;
        this.backgroundColor = backgroundColor;
        this.backgroundPosition = backgroundPosition;
        this.contentBgColor = contentBgColor;
        this.absolutePosition = absolutePosition;
        this.absoluteTop = absoluteTop;
        this.absoluteLeft = absoluteLeft;
        this.functionalType = functionalType;
        this.ecEnabled = ecEnabled;
        this.frontDisplayFeed = frontDisplayFeed;
        this.siteIconUrl = siteIconUrl;
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

    public Timestamp getTimeCreatd() {
        return this.timeCreatd;
    }

    public void setTimeCreatd(Timestamp timeCreatd) {
        this.timeCreatd = timeCreatd;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMeta() {
        return this.meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSiteTrackGoogle() {
        return this.siteTrackGoogle;
    }

    public void setSiteTrackGoogle(String siteTrackGoogle) {
        this.siteTrackGoogle = siteTrackGoogle;
    }

    public int getHomeColCount() {
        return this.homeColCount;
    }

    public void setHomeColCount(int homeColCount) {
        this.homeColCount = homeColCount;
    }

    public String getColorSubmenuBg() {
        return this.colorSubmenuBg;
    }

    public void setColorSubmenuBg(String colorSubmenuBg) {
        this.colorSubmenuBg = colorSubmenuBg;
    }

    public String getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getColorBorders() {
        return this.colorBorders;
    }

    public void setColorBorders(String colorBorders) {
        this.colorBorders = colorBorders;
    }

    public int getSiteGroup() {
        return this.siteGroup;
    }

    public void setSiteGroup(int siteGroup) {
        this.siteGroup = siteGroup;
    }

    public String getHeaderImage() {
        return this.headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getHeaderAd() {
        return this.headerAd;
    }

    public void setHeaderAd(String headerAd) {
        this.headerAd = headerAd;
    }

    public int getMenuWidth() {
        return this.menuWidth;
    }

    public void setMenuWidth(int menuWidth) {
        this.menuWidth = menuWidth;
    }

    public int getAdWidth() {
        return this.adWidth;
    }

    public void setAdWidth(int adWidth) {
        this.adWidth = adWidth;
    }

    public String getMenuFrontColor() {
        return this.menuFrontColor;
    }

    public void setMenuFrontColor(String menuFrontColor) {
        this.menuFrontColor = menuFrontColor;
    }

    public String getMenuBackColor() {
        return this.menuBackColor;
    }

    public void setMenuBackColor(String menuBackColor) {
        this.menuBackColor = menuBackColor;
    }

    public String getMenuLineColor() {
        return this.menuLineColor;
    }

    public void setMenuLineColor(String menuLineColor) {
        this.menuLineColor = menuLineColor;
    }

    public int getMenuReverse() {
        return this.menuReverse;
    }

    public void setMenuReverse(int menuReverse) {
        this.menuReverse = menuReverse;
    }

    public int getDisplayAllHome() {
        return this.displayAllHome;
    }

    public void setDisplayAllHome(int displayAllHome) {
        this.displayAllHome = displayAllHome;
    }

    public int getHeaderImageHeight() {
        return this.headerImageHeight;
    }

    public void setHeaderImageHeight(int headerImageHeight) {
        this.headerImageHeight = headerImageHeight;
    }

    public int getHeaderImageWidth() {
        return this.headerImageWidth;
    }

    public void setHeaderImageWidth(int headerImageWidth) {
        this.headerImageWidth = headerImageWidth;
    }

    public String getLayoutPage() {
        return this.layoutPage;
    }

    public void setLayoutPage(String layoutPage) {
        this.layoutPage = layoutPage;
    }

    public String getUnderlyingHome() {
        return this.underlyingHome;
    }

    public void setUnderlyingHome(String underlyingHome) {
        this.underlyingHome = underlyingHome;
    }

    public int getUnderlyingDynamicPage() {
        return this.underlyingDynamicPage;
    }

    public void setUnderlyingDynamicPage(int underlyingDynamicPage) {
        this.underlyingDynamicPage = underlyingDynamicPage;
    }

    public String getContentForwardSite() {
        return this.contentForwardSite;
    }

    public void setContentForwardSite(String contentForwardSite) {
        this.contentForwardSite = contentForwardSite;
    }

    public int getShowMidColumn() {
        return this.showMidColumn;
    }

    public void setShowMidColumn(int showMidColumn) {
        this.showMidColumn = showMidColumn;
    }

    public int getShowMenuColumn() {
        return this.showMenuColumn;
    }

    public void setShowMenuColumn(int showMenuColumn) {
        this.showMenuColumn = showMenuColumn;
    }

    public int getShowAdColumn() {
        return this.showAdColumn;
    }

    public void setShowAdColumn(int showAdColumn) {
        this.showAdColumn = showAdColumn;
    }

    public int getShowHomeMenu() {
        return this.showHomeMenu;
    }

    public void setShowHomeMenu(int showHomeMenu) {
        this.showHomeMenu = showHomeMenu;
    }

    public int getShowHomeMid() {
        return this.showHomeMid;
    }

    public void setShowHomeMid(int showHomeMid) {
        this.showHomeMid = showHomeMid;
    }

    public int getShowHomeAd() {
        return this.showHomeAd;
    }

    public void setShowHomeAd(int showHomeAd) {
        this.showHomeAd = showHomeAd;
    }

    public int getShowTopMenu() {
        return this.showTopMenu;
    }

    public void setShowTopMenu(int showTopMenu) {
        this.showTopMenu = showTopMenu;
    }

    public int getMidColumnWidth() {
        return this.midColumnWidth;
    }

    public void setMidColumnWidth(int midColumnWidth) {
        this.midColumnWidth = midColumnWidth;
    }

    public int getUseWysiwygContent() {
        return this.useWysiwygContent;
    }

    public void setUseWysiwygContent(int useWysiwygContent) {
        this.useWysiwygContent = useWysiwygContent;
    }

    public int getUseWysiwygPost() {
        return this.useWysiwygPost;
    }

    public void setUseWysiwygPost(int useWysiwygPost) {
        this.useWysiwygPost = useWysiwygPost;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundRepeat() {
        return this.backgroundRepeat;
    }

    public void setBackgroundRepeat(String backgroundRepeat) {
        this.backgroundRepeat = backgroundRepeat;
    }

    public String getBackgroundAttach() {
        return this.backgroundAttach;
    }

    public void setBackgroundAttach(String backgroundAttach) {
        this.backgroundAttach = backgroundAttach;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundPosition() {
        return this.backgroundPosition;
    }

    public void setBackgroundPosition(String backgroundPosition) {
        this.backgroundPosition = backgroundPosition;
    }

    public String getContentBgColor() {
        return this.contentBgColor;
    }

    public void setContentBgColor(String contentBgColor) {
        this.contentBgColor = contentBgColor;
    }

    public int getAbsolutePosition() {
        return this.absolutePosition;
    }

    public void setAbsolutePosition(int absolutePosition) {
        this.absolutePosition = absolutePosition;
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

    public int getFunctionalType() {
        return this.functionalType;
    }

    public void setFunctionalType(int functionalType) {
        this.functionalType = functionalType;
    }

    public int getEcEnabled() {
        return this.ecEnabled;
    }

    public void setEcEnabled(int ecEnabled) {
        this.ecEnabled = ecEnabled;
    }

    public int getFrontDisplayFeed() {
        return this.frontDisplayFeed;
    }

    public void setFrontDisplayFeed(int frontDisplayFeed) {
        this.frontDisplayFeed = frontDisplayFeed;
    }

    public String getSiteIconUrl() {
        return this.siteIconUrl;
    }

    public void setSiteIconUrl(String siteIconUrl) {
        this.siteIconUrl = siteIconUrl;
    }

}
