package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MenuItemForm extends ActionForm {

	private String _PanelId;             
	private String _ParentId;             
	private String _Title;             
	private String _Data;             
	private String _TargetType;             
	private String _OrderIdx;             
	private String _PageId;             
	private String _ContentId;             
	private String _Hide;             
	private String _TimeCreated;             

    public String getPanelId() {
        return _PanelId;
    }

    public void setPanelId(String PanelId) {
        this._PanelId = PanelId;
    }    

    public String getParentId() {
        return _ParentId;
    }

    public void setParentId(String ParentId) {
        this._ParentId = ParentId;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getData() {
        return _Data;
    }

    public void setData(String Data) {
        this._Data = Data;
    }    

    public String getTargetType() {
        return _TargetType;
    }

    public void setTargetType(String TargetType) {
        this._TargetType = TargetType;
    }    

    public String getOrderIdx() {
        return _OrderIdx;
    }

    public void setOrderIdx(String OrderIdx) {
        this._OrderIdx = OrderIdx;
    }    

    public String getPageId() {
        return _PageId;
    }

    public void setPageId(String PageId) {
        this._PageId = PageId;
    }    

    public String getContentId() {
        return _ContentId;
    }

    public void setContentId(String ContentId) {
        this._ContentId = ContentId;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}