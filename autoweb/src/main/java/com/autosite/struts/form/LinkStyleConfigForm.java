package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LinkStyleConfigForm extends ActionForm {

	private String _StyleKey;             
	private String _IsGlobal;             
	private String _IdClass;             
	private String _IsId;             
	private String _Height;             
	private String _Width;             
	private String _Display;             
	private String _Border;             
	private String _Background;             
	private String _Color;             
	private String _TextDecoration;             
	private String _TextAlign;             
	private String _VerticalAlign;             
	private String _TextIndent;             
	private String _Margin;             
	private String _Padding;             
	private String _Font;             
	private String _ExtraStyle;             
	private String _HovHeight;             
	private String _HovWidth;             
	private String _HovDisplay;             
	private String _HovBorder;             
	private String _HovBackground;             
	private String _HovColor;             
	private String _HovTextDecoration;             
	private String _HovTextAlign;             
	private String _HovVerticalAlign;             
	private String _HovTextIndent;             
	private String _HovMargin;             
	private String _HovPadding;             
	private String _HovFont;             
	private String _HovExtraStyle;             
	private String _ActiveUse;             
	private String _ActiveBorder;             
	private String _ActiveBackground;             
	private String _ActiveColor;             
	private String _ActiveTextDecoration;             
	private String _ActiveExtraStyle;             
	private String _ActiveMargin;             
	private String _ActivePadding;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getStyleKey() {
        return _StyleKey;
    }

    public void setStyleKey(String StyleKey) {
        this._StyleKey = StyleKey;
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

    public String getDisplay() {
        return _Display;
    }

    public void setDisplay(String Display) {
        this._Display = Display;
    }    

    public String getBorder() {
        return _Border;
    }

    public void setBorder(String Border) {
        this._Border = Border;
    }    

    public String getBackground() {
        return _Background;
    }

    public void setBackground(String Background) {
        this._Background = Background;
    }    

    public String getColor() {
        return _Color;
    }

    public void setColor(String Color) {
        this._Color = Color;
    }    

    public String getTextDecoration() {
        return _TextDecoration;
    }

    public void setTextDecoration(String TextDecoration) {
        this._TextDecoration = TextDecoration;
    }    

    public String getTextAlign() {
        return _TextAlign;
    }

    public void setTextAlign(String TextAlign) {
        this._TextAlign = TextAlign;
    }    

    public String getVerticalAlign() {
        return _VerticalAlign;
    }

    public void setVerticalAlign(String VerticalAlign) {
        this._VerticalAlign = VerticalAlign;
    }    

    public String getTextIndent() {
        return _TextIndent;
    }

    public void setTextIndent(String TextIndent) {
        this._TextIndent = TextIndent;
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

    public String getFont() {
        return _Font;
    }

    public void setFont(String Font) {
        this._Font = Font;
    }    

    public String getExtraStyle() {
        return _ExtraStyle;
    }

    public void setExtraStyle(String ExtraStyle) {
        this._ExtraStyle = ExtraStyle;
    }    

    public String getHovHeight() {
        return _HovHeight;
    }

    public void setHovHeight(String HovHeight) {
        this._HovHeight = HovHeight;
    }    

    public String getHovWidth() {
        return _HovWidth;
    }

    public void setHovWidth(String HovWidth) {
        this._HovWidth = HovWidth;
    }    

    public String getHovDisplay() {
        return _HovDisplay;
    }

    public void setHovDisplay(String HovDisplay) {
        this._HovDisplay = HovDisplay;
    }    

    public String getHovBorder() {
        return _HovBorder;
    }

    public void setHovBorder(String HovBorder) {
        this._HovBorder = HovBorder;
    }    

    public String getHovBackground() {
        return _HovBackground;
    }

    public void setHovBackground(String HovBackground) {
        this._HovBackground = HovBackground;
    }    

    public String getHovColor() {
        return _HovColor;
    }

    public void setHovColor(String HovColor) {
        this._HovColor = HovColor;
    }    

    public String getHovTextDecoration() {
        return _HovTextDecoration;
    }

    public void setHovTextDecoration(String HovTextDecoration) {
        this._HovTextDecoration = HovTextDecoration;
    }    

    public String getHovTextAlign() {
        return _HovTextAlign;
    }

    public void setHovTextAlign(String HovTextAlign) {
        this._HovTextAlign = HovTextAlign;
    }    

    public String getHovVerticalAlign() {
        return _HovVerticalAlign;
    }

    public void setHovVerticalAlign(String HovVerticalAlign) {
        this._HovVerticalAlign = HovVerticalAlign;
    }    

    public String getHovTextIndent() {
        return _HovTextIndent;
    }

    public void setHovTextIndent(String HovTextIndent) {
        this._HovTextIndent = HovTextIndent;
    }    

    public String getHovMargin() {
        return _HovMargin;
    }

    public void setHovMargin(String HovMargin) {
        this._HovMargin = HovMargin;
    }    

    public String getHovPadding() {
        return _HovPadding;
    }

    public void setHovPadding(String HovPadding) {
        this._HovPadding = HovPadding;
    }    

    public String getHovFont() {
        return _HovFont;
    }

    public void setHovFont(String HovFont) {
        this._HovFont = HovFont;
    }    

    public String getHovExtraStyle() {
        return _HovExtraStyle;
    }

    public void setHovExtraStyle(String HovExtraStyle) {
        this._HovExtraStyle = HovExtraStyle;
    }    

    public String getActiveUse() {
        return _ActiveUse;
    }

    public void setActiveUse(String ActiveUse) {
        this._ActiveUse = ActiveUse;
    }    

    public String getActiveBorder() {
        return _ActiveBorder;
    }

    public void setActiveBorder(String ActiveBorder) {
        this._ActiveBorder = ActiveBorder;
    }    

    public String getActiveBackground() {
        return _ActiveBackground;
    }

    public void setActiveBackground(String ActiveBackground) {
        this._ActiveBackground = ActiveBackground;
    }    

    public String getActiveColor() {
        return _ActiveColor;
    }

    public void setActiveColor(String ActiveColor) {
        this._ActiveColor = ActiveColor;
    }    

    public String getActiveTextDecoration() {
        return _ActiveTextDecoration;
    }

    public void setActiveTextDecoration(String ActiveTextDecoration) {
        this._ActiveTextDecoration = ActiveTextDecoration;
    }    

    public String getActiveExtraStyle() {
        return _ActiveExtraStyle;
    }

    public void setActiveExtraStyle(String ActiveExtraStyle) {
        this._ActiveExtraStyle = ActiveExtraStyle;
    }    

    public String getActiveMargin() {
        return _ActiveMargin;
    }

    public void setActiveMargin(String ActiveMargin) {
        this._ActiveMargin = ActiveMargin;
    }    

    public String getActivePadding() {
        return _ActivePadding;
    }

    public void setActivePadding(String ActivePadding) {
        this._ActivePadding = ActivePadding;
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