package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class Content2ActionForm extends ActionForm {

    private String _ContentSubject;             
    private String _Content;             
    private String _Category;             
    private String _CreatedTime;             
    private String _UpdatedTime;             
    private String _ContentType;             
    private String _SourceName;             
    private String _SourceUrl;             
    private String _Hide;             
    private String _ShowHome;             
    private String _ImageUrl;             
    private String _ImageHeight;             
    private String _ImageWidth;             

    public String getContentSubject() {
        return _ContentSubject;
    }

    public void setContentSubject(String ContentSubject) {
        this._ContentSubject = ContentSubject;
    }    

    public String getContent() {
        return _Content;
    }

    public void setContent(String Content) {
        this._Content = Content;
    }    

    public String getCategory() {
        return _Category;
    }

    public void setCategory(String Category) {
        this._Category = Category;
    }    

    public String getCreatedTime() {
        return _CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this._CreatedTime = CreatedTime;
    }    

    public String getUpdatedTime() {
        return _UpdatedTime;
    }

    public void setUpdatedTime(String UpdatedTime) {
        this._UpdatedTime = UpdatedTime;
    }    

    public String getContentType() {
        return _ContentType;
    }

    public void setContentType(String ContentType) {
        this._ContentType = ContentType;
    }    

    public String getSourceName() {
        return _SourceName;
    }

    public void setSourceName(String SourceName) {
        this._SourceName = SourceName;
    }    

    public String getSourceUrl() {
        return _SourceUrl;
    }

    public void setSourceUrl(String SourceUrl) {
        this._SourceUrl = SourceUrl;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getShowHome() {
        return _ShowHome;
    }

    public void setShowHome(String ShowHome) {
        this._ShowHome = ShowHome;
    }    

    public String getImageUrl() {
        return _ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this._ImageUrl = ImageUrl;
    }    

    public String getImageHeight() {
        return _ImageHeight;
    }

    public void setImageHeight(String ImageHeight) {
        this._ImageHeight = ImageHeight;
    }    

    public String getImageWidth() {
        return _ImageWidth;
    }

    public void setImageWidth(String ImageWidth) {
        this._ImageWidth = ImageWidth;
    }    

}