package com.autosite.db;

import java.sql.Timestamp;

/**
 * StyleConfig entity. @author MyEclipse Persistence Tools
 */

public class StyleConfig extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String styleKey;
    private int styleUse;
    private int isGlobal;
    private String idClass;
    private int isId;
    private String color;
    private String bgColor;
    private String bgImage;
    private String bgRepeat;
    private String bgAttach;
    private String bgPosition;
    private String textAlign;
    private String fontFamily;
    private String fontSize;
    private String fontStyle;
    private String fontVariant;
    private String fontWeight;
    private String borderDirection;
    private String borderWidth;
    private String borderStyle;
    private String borderColor;
    private String margin;
    private String padding;
    private String listStyleType;
    private String listStylePosition;
    private String listStyleImage;
    private String floating;
    private String extraStyleStr;
    private String itemStyleStr;
    private String link;
    private String linkHover;
    private String linkActive;
    private int height;
    private int width;
    private int isTable;
    private String borderCollapse;
    private String borderSpacing;
    private String trStyleIds;
    private String tdStyleIds;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public StyleConfig() {
    }

    /** minimal constructor */
    public StyleConfig(long siteId, String styleKey, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.styleKey = styleKey;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public StyleConfig(long siteId, String styleKey, int styleUse, int isGlobal, String idClass, int isId, String color, String bgColor, String bgImage, String bgRepeat, String bgAttach,
            String bgPosition, String textAlign, String fontFamily, String fontSize, String fontStyle, String fontVariant, String fontWeight, String borderDirection, String borderWidth,
            String borderStyle, String borderColor, String margin, String padding, String listStyleType, String listStylePosition, String listStyleImage, String floating, String extraStyleStr,
            String itemStyleStr, String link, String linkHover, String linkActive, int height, int width, int isTable, String borderCollapse, String borderSpacing, String trStyleIds,
            String tdStyleIds, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.styleKey = styleKey;
        this.styleUse = styleUse;
        this.isGlobal = isGlobal;
        this.idClass = idClass;
        this.isId = isId;
        this.color = color;
        this.bgColor = bgColor;
        this.bgImage = bgImage;
        this.bgRepeat = bgRepeat;
        this.bgAttach = bgAttach;
        this.bgPosition = bgPosition;
        this.textAlign = textAlign;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.fontVariant = fontVariant;
        this.fontWeight = fontWeight;
        this.borderDirection = borderDirection;
        this.borderWidth = borderWidth;
        this.borderStyle = borderStyle;
        this.borderColor = borderColor;
        this.margin = margin;
        this.padding = padding;
        this.listStyleType = listStyleType;
        this.listStylePosition = listStylePosition;
        this.listStyleImage = listStyleImage;
        this.floating = floating;
        this.extraStyleStr = extraStyleStr;
        this.itemStyleStr = itemStyleStr;
        this.link = link;
        this.linkHover = linkHover;
        this.linkActive = linkActive;
        this.height = height;
        this.width = width;
        this.isTable = isTable;
        this.borderCollapse = borderCollapse;
        this.borderSpacing = borderSpacing;
        this.trStyleIds = trStyleIds;
        this.tdStyleIds = tdStyleIds;
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

    public int getStyleUse() {
        return this.styleUse;
    }

    public void setStyleUse(int styleUse) {
        this.styleUse = styleUse;
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

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getBgImage() {
        return this.bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getBgRepeat() {
        return this.bgRepeat;
    }

    public void setBgRepeat(String bgRepeat) {
        this.bgRepeat = bgRepeat;
    }

    public String getBgAttach() {
        return this.bgAttach;
    }

    public void setBgAttach(String bgAttach) {
        this.bgAttach = bgAttach;
    }

    public String getBgPosition() {
        return this.bgPosition;
    }

    public void setBgPosition(String bgPosition) {
        this.bgPosition = bgPosition;
    }

    public String getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getFontFamily() {
        return this.fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontStyle() {
        return this.fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getFontVariant() {
        return this.fontVariant;
    }

    public void setFontVariant(String fontVariant) {
        this.fontVariant = fontVariant;
    }

    public String getFontWeight() {
        return this.fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getBorderDirection() {
        return this.borderDirection;
    }

    public void setBorderDirection(String borderDirection) {
        this.borderDirection = borderDirection;
    }

    public String getBorderWidth() {
        return this.borderWidth;
    }

    public void setBorderWidth(String borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String getBorderStyle() {
        return this.borderStyle;
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
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

    public String getListStyleType() {
        return this.listStyleType;
    }

    public void setListStyleType(String listStyleType) {
        this.listStyleType = listStyleType;
    }

    public String getListStylePosition() {
        return this.listStylePosition;
    }

    public void setListStylePosition(String listStylePosition) {
        this.listStylePosition = listStylePosition;
    }

    public String getListStyleImage() {
        return this.listStyleImage;
    }

    public void setListStyleImage(String listStyleImage) {
        this.listStyleImage = listStyleImage;
    }

    public String getFloating() {
        return this.floating;
    }

    public void setFloating(String floating) {
        this.floating = floating;
    }

    public String getExtraStyleStr() {
        return this.extraStyleStr;
    }

    public void setExtraStyleStr(String extraStyleStr) {
        this.extraStyleStr = extraStyleStr;
    }

    public String getItemStyleStr() {
        return this.itemStyleStr;
    }

    public void setItemStyleStr(String itemStyleStr) {
        this.itemStyleStr = itemStyleStr;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkHover() {
        return this.linkHover;
    }

    public void setLinkHover(String linkHover) {
        this.linkHover = linkHover;
    }

    public String getLinkActive() {
        return this.linkActive;
    }

    public void setLinkActive(String linkActive) {
        this.linkActive = linkActive;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getIsTable() {
        return this.isTable;
    }

    public void setIsTable(int isTable) {
        this.isTable = isTable;
    }

    public String getBorderCollapse() {
        return this.borderCollapse;
    }

    public void setBorderCollapse(String borderCollapse) {
        this.borderCollapse = borderCollapse;
    }

    public String getBorderSpacing() {
        return this.borderSpacing;
    }

    public void setBorderSpacing(String borderSpacing) {
        this.borderSpacing = borderSpacing;
    }

    public String getTrStyleIds() {
        return this.trStyleIds;
    }

    public void setTrStyleIds(String trStyleIds) {
        this.trStyleIds = trStyleIds;
    }

    public String getTdStyleIds() {
        return this.tdStyleIds;
    }

    public void setTdStyleIds(String tdStyleIds) {
        this.tdStyleIds = tdStyleIds;
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
