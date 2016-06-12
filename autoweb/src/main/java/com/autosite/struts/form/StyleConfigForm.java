package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class StyleConfigForm extends ActionForm {

	private String _StyleKey;             
	private String _StyleUse;             
	private String _IsGlobal;             
	private String _IdClass;             
	private String _IsId;             
	private String _Color;             
	private String _BgColor;             
	private String _BgImage;             
	private String _BgRepeat;             
	private String _BgAttach;             
	private String _BgPosition;             
	private String _TextAlign;             
	private String _FontFamily;             
	private String _FontSize;             
	private String _FontStyle;             
	private String _FontVariant;             
	private String _FontWeight;             
	private String _BorderDirection;             
	private String _BorderWidth;             
	private String _BorderStyle;             
	private String _BorderColor;             
	private String _Margin;             
	private String _Padding;             
	private String _ListStyleType;             
	private String _ListStylePosition;             
	private String _ListStyleImage;             
	private String _Floating;             
	private String _ExtraStyleStr;             
	private String _ItemStyleStr;             
	private String _Link;             
	private String _LinkHover;             
	private String _LinkActive;             
	private String _Height;             
	private String _Width;             
	private String _IsTable;             
	private String _BorderCollapse;             
	private String _BorderSpacing;             
	private String _TrStyleIds;             
	private String _TdStyleIds;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getStyleKey() {
        return _StyleKey;
    }

    public void setStyleKey(String StyleKey) {
        this._StyleKey = StyleKey;
    }    

    public String getStyleUse() {
        return _StyleUse;
    }

    public void setStyleUse(String StyleUse) {
        this._StyleUse = StyleUse;
    }    

    public String getIsGlobal() {
        return _IsGlobal;
    }

    public void setIsGlobal(String IsGlobal) {
        this._IsGlobal = IsGlobal;
    }    

    public String getIdClass() {
        return _IdClass;
    }

    public void setIdClass(String IdClass) {
        this._IdClass = IdClass;
    }    

    public String getIsId() {
        return _IsId;
    }

    public void setIsId(String IsId) {
        this._IsId = IsId;
    }    

    public String getColor() {
        return _Color;
    }

    public void setColor(String Color) {
        this._Color = Color;
    }    

    public String getBgColor() {
        return _BgColor;
    }

    public void setBgColor(String BgColor) {
        this._BgColor = BgColor;
    }    

    public String getBgImage() {
        return _BgImage;
    }

    public void setBgImage(String BgImage) {
        this._BgImage = BgImage;
    }    

    public String getBgRepeat() {
        return _BgRepeat;
    }

    public void setBgRepeat(String BgRepeat) {
        this._BgRepeat = BgRepeat;
    }    

    public String getBgAttach() {
        return _BgAttach;
    }

    public void setBgAttach(String BgAttach) {
        this._BgAttach = BgAttach;
    }    

    public String getBgPosition() {
        return _BgPosition;
    }

    public void setBgPosition(String BgPosition) {
        this._BgPosition = BgPosition;
    }    

    public String getTextAlign() {
        return _TextAlign;
    }

    public void setTextAlign(String TextAlign) {
        this._TextAlign = TextAlign;
    }    

    public String getFontFamily() {
        return _FontFamily;
    }

    public void setFontFamily(String FontFamily) {
        this._FontFamily = FontFamily;
    }    

    public String getFontSize() {
        return _FontSize;
    }

    public void setFontSize(String FontSize) {
        this._FontSize = FontSize;
    }    

    public String getFontStyle() {
        return _FontStyle;
    }

    public void setFontStyle(String FontStyle) {
        this._FontStyle = FontStyle;
    }    

    public String getFontVariant() {
        return _FontVariant;
    }

    public void setFontVariant(String FontVariant) {
        this._FontVariant = FontVariant;
    }    

    public String getFontWeight() {
        return _FontWeight;
    }

    public void setFontWeight(String FontWeight) {
        this._FontWeight = FontWeight;
    }    

    public String getBorderDirection() {
        return _BorderDirection;
    }

    public void setBorderDirection(String BorderDirection) {
        this._BorderDirection = BorderDirection;
    }    

    public String getBorderWidth() {
        return _BorderWidth;
    }

    public void setBorderWidth(String BorderWidth) {
        this._BorderWidth = BorderWidth;
    }    

    public String getBorderStyle() {
        return _BorderStyle;
    }

    public void setBorderStyle(String BorderStyle) {
        this._BorderStyle = BorderStyle;
    }    

    public String getBorderColor() {
        return _BorderColor;
    }

    public void setBorderColor(String BorderColor) {
        this._BorderColor = BorderColor;
    }    

    public String getMargin() {
        return _Margin;
    }

    public void setMargin(String Margin) {
        this._Margin = Margin;
    }    

    public String getPadding() {
        return _Padding;
    }

    public void setPadding(String Padding) {
        this._Padding = Padding;
    }    

    public String getListStyleType() {
        return _ListStyleType;
    }

    public void setListStyleType(String ListStyleType) {
        this._ListStyleType = ListStyleType;
    }    

    public String getListStylePosition() {
        return _ListStylePosition;
    }

    public void setListStylePosition(String ListStylePosition) {
        this._ListStylePosition = ListStylePosition;
    }    

    public String getListStyleImage() {
        return _ListStyleImage;
    }

    public void setListStyleImage(String ListStyleImage) {
        this._ListStyleImage = ListStyleImage;
    }    

    public String getFloating() {
        return _Floating;
    }

    public void setFloating(String Floating) {
        this._Floating = Floating;
    }    

    public String getExtraStyleStr() {
        return _ExtraStyleStr;
    }

    public void setExtraStyleStr(String ExtraStyleStr) {
        this._ExtraStyleStr = ExtraStyleStr;
    }    

    public String getItemStyleStr() {
        return _ItemStyleStr;
    }

    public void setItemStyleStr(String ItemStyleStr) {
        this._ItemStyleStr = ItemStyleStr;
    }    

    public String getLink() {
        return _Link;
    }

    public void setLink(String Link) {
        this._Link = Link;
    }    

    public String getLinkHover() {
        return _LinkHover;
    }

    public void setLinkHover(String LinkHover) {
        this._LinkHover = LinkHover;
    }    

    public String getLinkActive() {
        return _LinkActive;
    }

    public void setLinkActive(String LinkActive) {
        this._LinkActive = LinkActive;
    }    

    public String getHeight() {
        return _Height;
    }

    public void setHeight(String Height) {
        this._Height = Height;
    }    

    public String getWidth() {
        return _Width;
    }

    public void setWidth(String Width) {
        this._Width = Width;
    }    

    public String getIsTable() {
        return _IsTable;
    }

    public void setIsTable(String IsTable) {
        this._IsTable = IsTable;
    }    

    public String getBorderCollapse() {
        return _BorderCollapse;
    }

    public void setBorderCollapse(String BorderCollapse) {
        this._BorderCollapse = BorderCollapse;
    }    

    public String getBorderSpacing() {
        return _BorderSpacing;
    }

    public void setBorderSpacing(String BorderSpacing) {
        this._BorderSpacing = BorderSpacing;
    }    

    public String getTrStyleIds() {
        return _TrStyleIds;
    }

    public void setTrStyleIds(String TrStyleIds) {
        this._TrStyleIds = TrStyleIds;
    }    

    public String getTdStyleIds() {
        return _TdStyleIds;
    }

    public void setTdStyleIds(String TdStyleIds) {
        this._TdStyleIds = TdStyleIds;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}