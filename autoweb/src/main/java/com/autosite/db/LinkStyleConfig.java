package com.autosite.db;

import java.sql.Timestamp;

/**
 * LinkStyleConfig entity. @author MyEclipse Persistence Tools
 */

public class LinkStyleConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String styleKey;
    private int isGlobal;
    private String idClass;
    private int isId;
    private String height;
    private String width;
    private String display;
    private String border;
    private String background;
    private String color;
    private String textDecoration;
    private String textAlign;
    private String verticalAlign;
    private String textIndent;
    private String margin;
    private String padding;
    private String font;
    private String extraStyle;
    private String hovHeight;
    private String hovWidth;
    private String hovDisplay;
    private String hovBorder;
    private String hovBackground;
    private String hovColor;
    private String hovTextDecoration;
    private String hovTextAlign;
    private String hovVerticalAlign;
    private String hovTextIndent;
    private String hovMargin;
    private String hovPadding;
    private String hovFont;
    private String hovExtraStyle;
    private int activeUse;
    private String activeBorder;
    private String activeBackground;
    private String activeColor;
    private String activeTextDecoration;
    private String activeExtraStyle;
    private String activeMargin;
    private String activePadding;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public LinkStyleConfig() {
    }

    /** minimal constructor */
    public LinkStyleConfig(long siteId, String styleKey, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.styleKey = styleKey;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public LinkStyleConfig(long siteId, String styleKey, int isGlobal, String idClass, int isId, String height, String width, String display, String border, String background, String color,
            String textDecoration, String textAlign, String verticalAlign, String textIndent, String margin, String padding, String font, String extraStyle, String hovHeight, String hovWidth,
            String hovDisplay, String hovBorder, String hovBackground, String hovColor, String hovTextDecoration, String hovTextAlign, String hovVerticalAlign, String hovTextIndent, String hovMargin,
            String hovPadding, String hovFont, String hovExtraStyle, int activeUse, String activeBorder, String activeBackground, String activeColor, String activeTextDecoration,
            String activeExtraStyle, String activeMargin, String activePadding, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.styleKey = styleKey;
        this.isGlobal = isGlobal;
        this.idClass = idClass;
        this.isId = isId;
        this.height = height;
        this.width = width;
        this.display = display;
        this.border = border;
        this.background = background;
        this.color = color;
        this.textDecoration = textDecoration;
        this.textAlign = textAlign;
        this.verticalAlign = verticalAlign;
        this.textIndent = textIndent;
        this.margin = margin;
        this.padding = padding;
        this.font = font;
        this.extraStyle = extraStyle;
        this.hovHeight = hovHeight;
        this.hovWidth = hovWidth;
        this.hovDisplay = hovDisplay;
        this.hovBorder = hovBorder;
        this.hovBackground = hovBackground;
        this.hovColor = hovColor;
        this.hovTextDecoration = hovTextDecoration;
        this.hovTextAlign = hovTextAlign;
        this.hovVerticalAlign = hovVerticalAlign;
        this.hovTextIndent = hovTextIndent;
        this.hovMargin = hovMargin;
        this.hovPadding = hovPadding;
        this.hovFont = hovFont;
        this.hovExtraStyle = hovExtraStyle;
        this.activeUse = activeUse;
        this.activeBorder = activeBorder;
        this.activeBackground = activeBackground;
        this.activeColor = activeColor;
        this.activeTextDecoration = activeTextDecoration;
        this.activeExtraStyle = activeExtraStyle;
        this.activeMargin = activeMargin;
        this.activePadding = activePadding;
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

    public String getStyleKey() {
        return this.styleKey;
    }

    public void setStyleKey(String styleKey) {
        this.styleKey = styleKey;
    }

    public int getIsGlobal() {
        return this.isGlobal;
    }

    public void setIsGlobal(int isGlobal) {
        this.isGlobal = isGlobal;
    }

    public String getIdClass() {
        return this.idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public int getIsId() {
        return this.isId;
    }

    public void setIsId(int isId) {
        this.isId = isId;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getBorder() {
        return this.border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextDecoration() {
        return this.textDecoration;
    }

    public void setTextDecoration(String textDecoration) {
        this.textDecoration = textDecoration;
    }

    public String getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getVerticalAlign() {
        return this.verticalAlign;
    }

    public void setVerticalAlign(String verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public String getTextIndent() {
        return this.textIndent;
    }

    public void setTextIndent(String textIndent) {
        this.textIndent = textIndent;
    }

    public String getMargin() {
        return this.margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getPadding() {
        return this.padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getExtraStyle() {
        return this.extraStyle;
    }

    public void setExtraStyle(String extraStyle) {
        this.extraStyle = extraStyle;
    }

    public String getHovHeight() {
        return this.hovHeight;
    }

    public void setHovHeight(String hovHeight) {
        this.hovHeight = hovHeight;
    }

    public String getHovWidth() {
        return this.hovWidth;
    }

    public void setHovWidth(String hovWidth) {
        this.hovWidth = hovWidth;
    }

    public String getHovDisplay() {
        return this.hovDisplay;
    }

    public void setHovDisplay(String hovDisplay) {
        this.hovDisplay = hovDisplay;
    }

    public String getHovBorder() {
        return this.hovBorder;
    }

    public void setHovBorder(String hovBorder) {
        this.hovBorder = hovBorder;
    }

    public String getHovBackground() {
        return this.hovBackground;
    }

    public void setHovBackground(String hovBackground) {
        this.hovBackground = hovBackground;
    }

    public String getHovColor() {
        return this.hovColor;
    }

    public void setHovColor(String hovColor) {
        this.hovColor = hovColor;
    }

    public String getHovTextDecoration() {
        return this.hovTextDecoration;
    }

    public void setHovTextDecoration(String hovTextDecoration) {
        this.hovTextDecoration = hovTextDecoration;
    }

    public String getHovTextAlign() {
        return this.hovTextAlign;
    }

    public void setHovTextAlign(String hovTextAlign) {
        this.hovTextAlign = hovTextAlign;
    }

    public String getHovVerticalAlign() {
        return this.hovVerticalAlign;
    }

    public void setHovVerticalAlign(String hovVerticalAlign) {
        this.hovVerticalAlign = hovVerticalAlign;
    }

    public String getHovTextIndent() {
        return this.hovTextIndent;
    }

    public void setHovTextIndent(String hovTextIndent) {
        this.hovTextIndent = hovTextIndent;
    }

    public String getHovMargin() {
        return this.hovMargin;
    }

    public void setHovMargin(String hovMargin) {
        this.hovMargin = hovMargin;
    }

    public String getHovPadding() {
        return this.hovPadding;
    }

    public void setHovPadding(String hovPadding) {
        this.hovPadding = hovPadding;
    }

    public String getHovFont() {
        return this.hovFont;
    }

    public void setHovFont(String hovFont) {
        this.hovFont = hovFont;
    }

    public String getHovExtraStyle() {
        return this.hovExtraStyle;
    }

    public void setHovExtraStyle(String hovExtraStyle) {
        this.hovExtraStyle = hovExtraStyle;
    }

    public int getActiveUse() {
        return this.activeUse;
    }

    public void setActiveUse(int activeUse) {
        this.activeUse = activeUse;
    }

    public String getActiveBorder() {
        return this.activeBorder;
    }

    public void setActiveBorder(String activeBorder) {
        this.activeBorder = activeBorder;
    }

    public String getActiveBackground() {
        return this.activeBackground;
    }

    public void setActiveBackground(String activeBackground) {
        this.activeBackground = activeBackground;
    }

    public String getActiveColor() {
        return this.activeColor;
    }

    public void setActiveColor(String activeColor) {
        this.activeColor = activeColor;
    }

    public String getActiveTextDecoration() {
        return this.activeTextDecoration;
    }

    public void setActiveTextDecoration(String activeTextDecoration) {
        this.activeTextDecoration = activeTextDecoration;
    }

    public String getActiveExtraStyle() {
        return this.activeExtraStyle;
    }

    public void setActiveExtraStyle(String activeExtraStyle) {
        this.activeExtraStyle = activeExtraStyle;
    }

    public String getActiveMargin() {
        return this.activeMargin;
    }

    public void setActiveMargin(String activeMargin) {
        this.activeMargin = activeMargin;
    }

    public String getActivePadding() {
        return this.activePadding;
    }

    public void setActivePadding(String activePadding) {
        this.activePadding = activePadding;
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
