package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class StyleThemeForm extends ActionForm {

	private String _Title;             
	private String _BodyWidth;             
	private String _BodyAlign;             
	private String _BodyBgColor;             
	private String _BodyBgImage;             
	private String _BodyBgAttach;             
	private String _BodyBgRepeat;             
	private String _BodyBgPosition;             
	private String _ContentBgColor;             
	private String _UseAbsolute;             
	private String _AbsoluteTop;             
	private String _AbsoluteLeft;             
	private String _AbsoluteRight;             
	private String _AbsoluteBottom;             
	private String _PanelStyleId;             
	private String _PanelDataStyleId;             
	private String _PanelLinkStyleId;             
	private String _PanelTitleStyleId;             
	private String _MenuStyleId;             
	private String _MenuLinkStyleId;             
	private String _HeaderMenuStyleId;             
	private String _HeaderMenuLinkStyleId;             
	private String _ListFrameStyleId;             
	private String _ListSubjectStyleId;             
	private String _ListDataStyleId;             
	private String _SubjectStyleId;             
	private String _DataStyleId;             
	private String _SingleFrameStyleId;             
	private String _SingleSubjectStyleId;             
	private String _SingleDataStyleId;             
	private String _ContentPanelStyleId;             
	private String _ContentPanelTitleStyleId;             
	private String _Global;             
	private String _Disable;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getBodyWidth() {
        return _BodyWidth;
    }

    public void setBodyWidth(String BodyWidth) {
        this._BodyWidth = BodyWidth;
    }    

    public String getBodyAlign() {
        return _BodyAlign;
    }

    public void setBodyAlign(String BodyAlign) {
        this._BodyAlign = BodyAlign;
    }    

    public String getBodyBgColor() {
        return _BodyBgColor;
    }

    public void setBodyBgColor(String BodyBgColor) {
        this._BodyBgColor = BodyBgColor;
    }    

    public String getBodyBgImage() {
        return _BodyBgImage;
    }

    public void setBodyBgImage(String BodyBgImage) {
        this._BodyBgImage = BodyBgImage;
    }    

    public String getBodyBgAttach() {
        return _BodyBgAttach;
    }

    public void setBodyBgAttach(String BodyBgAttach) {
        this._BodyBgAttach = BodyBgAttach;
    }    

    public String getBodyBgRepeat() {
        return _BodyBgRepeat;
    }

    public void setBodyBgRepeat(String BodyBgRepeat) {
        this._BodyBgRepeat = BodyBgRepeat;
    }    

    public String getBodyBgPosition() {
        return _BodyBgPosition;
    }

    public void setBodyBgPosition(String BodyBgPosition) {
        this._BodyBgPosition = BodyBgPosition;
    }    

    public String getContentBgColor() {
        return _ContentBgColor;
    }

    public void setContentBgColor(String ContentBgColor) {
        this._ContentBgColor = ContentBgColor;
    }    

    public String getUseAbsolute() {
        return _UseAbsolute;
    }

    public void setUseAbsolute(String UseAbsolute) {
        this._UseAbsolute = UseAbsolute;
    }    

    public String getAbsoluteTop() {
        return _AbsoluteTop;
    }

    public void setAbsoluteTop(String AbsoluteTop) {
        this._AbsoluteTop = AbsoluteTop;
    }    

    public String getAbsoluteLeft() {
        return _AbsoluteLeft;
    }

    public void setAbsoluteLeft(String AbsoluteLeft) {
        this._AbsoluteLeft = AbsoluteLeft;
    }    

    public String getAbsoluteRight() {
        return _AbsoluteRight;
    }

    public void setAbsoluteRight(String AbsoluteRight) {
        this._AbsoluteRight = AbsoluteRight;
    }    

    public String getAbsoluteBottom() {
        return _AbsoluteBottom;
    }

    public void setAbsoluteBottom(String AbsoluteBottom) {
        this._AbsoluteBottom = AbsoluteBottom;
    }    

    public String getPanelStyleId() {
        return _PanelStyleId;
    }

    public void setPanelStyleId(String PanelStyleId) {
        this._PanelStyleId = PanelStyleId;
    }    

    public String getPanelDataStyleId() {
        return _PanelDataStyleId;
    }

    public void setPanelDataStyleId(String PanelDataStyleId) {
        this._PanelDataStyleId = PanelDataStyleId;
    }    

    public String getPanelLinkStyleId() {
        return _PanelLinkStyleId;
    }

    public void setPanelLinkStyleId(String PanelLinkStyleId) {
        this._PanelLinkStyleId = PanelLinkStyleId;
    }    

    public String getPanelTitleStyleId() {
        return _PanelTitleStyleId;
    }

    public void setPanelTitleStyleId(String PanelTitleStyleId) {
        this._PanelTitleStyleId = PanelTitleStyleId;
    }    

    public String getMenuStyleId() {
        return _MenuStyleId;
    }

    public void setMenuStyleId(String MenuStyleId) {
        this._MenuStyleId = MenuStyleId;
    }    

    public String getMenuLinkStyleId() {
        return _MenuLinkStyleId;
    }

    public void setMenuLinkStyleId(String MenuLinkStyleId) {
        this._MenuLinkStyleId = MenuLinkStyleId;
    }    

    public String getHeaderMenuStyleId() {
        return _HeaderMenuStyleId;
    }

    public void setHeaderMenuStyleId(String HeaderMenuStyleId) {
        this._HeaderMenuStyleId = HeaderMenuStyleId;
    }    

    public String getHeaderMenuLinkStyleId() {
        return _HeaderMenuLinkStyleId;
    }

    public void setHeaderMenuLinkStyleId(String HeaderMenuLinkStyleId) {
        this._HeaderMenuLinkStyleId = HeaderMenuLinkStyleId;
    }    

    public String getListFrameStyleId() {
        return _ListFrameStyleId;
    }

    public void setListFrameStyleId(String ListFrameStyleId) {
        this._ListFrameStyleId = ListFrameStyleId;
    }    

    public String getListSubjectStyleId() {
        return _ListSubjectStyleId;
    }

    public void setListSubjectStyleId(String ListSubjectStyleId) {
        this._ListSubjectStyleId = ListSubjectStyleId;
    }    

    public String getListDataStyleId() {
        return _ListDataStyleId;
    }

    public void setListDataStyleId(String ListDataStyleId) {
        this._ListDataStyleId = ListDataStyleId;
    }    

    public String getSubjectStyleId() {
        return _SubjectStyleId;
    }

    public void setSubjectStyleId(String SubjectStyleId) {
        this._SubjectStyleId = SubjectStyleId;
    }    

    public String getDataStyleId() {
        return _DataStyleId;
    }

    public void setDataStyleId(String DataStyleId) {
        this._DataStyleId = DataStyleId;
    }    

    public String getSingleFrameStyleId() {
        return _SingleFrameStyleId;
    }

    public void setSingleFrameStyleId(String SingleFrameStyleId) {
        this._SingleFrameStyleId = SingleFrameStyleId;
    }    

    public String getSingleSubjectStyleId() {
        return _SingleSubjectStyleId;
    }

    public void setSingleSubjectStyleId(String SingleSubjectStyleId) {
        this._SingleSubjectStyleId = SingleSubjectStyleId;
    }    

    public String getSingleDataStyleId() {
        return _SingleDataStyleId;
    }

    public void setSingleDataStyleId(String SingleDataStyleId) {
        this._SingleDataStyleId = SingleDataStyleId;
    }    

    public String getContentPanelStyleId() {
        return _ContentPanelStyleId;
    }

    public void setContentPanelStyleId(String ContentPanelStyleId) {
        this._ContentPanelStyleId = ContentPanelStyleId;
    }    

    public String getContentPanelTitleStyleId() {
        return _ContentPanelTitleStyleId;
    }

    public void setContentPanelTitleStyleId(String ContentPanelTitleStyleId) {
        this._ContentPanelTitleStyleId = ContentPanelTitleStyleId;
    }    

    public String getGlobal() {
        return _Global;
    }

    public void setGlobal(String Global) {
        this._Global = Global;
    }    

    public String getDisable() {
        return _Disable;
    }

    public void setDisable(String Disable) {
        this._Disable = Disable;
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