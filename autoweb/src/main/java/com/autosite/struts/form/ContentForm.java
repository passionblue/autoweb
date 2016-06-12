package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContentForm extends ActionForm {

	private String _PageId;             
	private String _SubPageId;             
	private String _PanelId;             
	private String _CategoryId;             
	private String _UserId;             
	private String _ContentSubject;             
	private String _ContentAbstract;             
	private String _Content;             
	private String _AbstractGenerate;             
	private String _AbstractNo;             
	private String _UseExternalData;             
	private String _AuthorName;             
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
	private String _InHtml;             
	private String _Tags;             
	private String _ExtraKeywords;             
	private String _ShortcutUrl;             
	private String _HeaderMeta;             
	private String _HeaderLink;             
	private String _ColumnNum;             

    public String getPageId() {
        return _PageId;
    }

    public void setPageId(String PageId) {
        this._PageId = PageId;
    }    

    public String getSubPageId() {
        return _SubPageId;
    }

    public void setSubPageId(String SubPageId) {
        this._SubPageId = SubPageId;
    }    

    public String getPanelId() {
        return _PanelId;
    }

    public void setPanelId(String PanelId) {
        this._PanelId = PanelId;
    }    

    public String getCategoryId() {
        return _CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this._CategoryId = CategoryId;
    }    

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getContentSubject() {
        return _ContentSubject;
    }

    public void setContentSubject(String ContentSubject) {
        this._ContentSubject = ContentSubject;
    }    

    public String getContentAbstract() {
        return _ContentAbstract;
    }

    public void setContentAbstract(String ContentAbstract) {
        this._ContentAbstract = ContentAbstract;
    }    

    public String getContent() {
        return _Content;
    }

    public void setContent(String Content) {
        this._Content = Content;
    }    

    public String getAbstractGenerate() {
        return _AbstractGenerate;
    }

    public void setAbstractGenerate(String AbstractGenerate) {
        this._AbstractGenerate = AbstractGenerate;
    }    

    public String getAbstractNo() {
        return _AbstractNo;
    }

    public void setAbstractNo(String AbstractNo) {
        this._AbstractNo = AbstractNo;
    }    

    public String getUseExternalData() {
        return _UseExternalData;
    }

    public void setUseExternalData(String UseExternalData) {
        this._UseExternalData = UseExternalData;
    }    

    public String getAuthorName() {
        return _AuthorName;
    }

    public void setAuthorName(String AuthorName) {
        this._AuthorName = AuthorName;
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

    public String getInHtml() {
        return _InHtml;
    }

    public void setInHtml(String InHtml) {
        this._InHtml = InHtml;
    }    

    public String getTags() {
        return _Tags;
    }

    public void setTags(String Tags) {
        this._Tags = Tags;
    }    

    public String getExtraKeywords() {
        return _ExtraKeywords;
    }

    public void setExtraKeywords(String ExtraKeywords) {
        this._ExtraKeywords = ExtraKeywords;
    }    

    public String getShortcutUrl() {
        return _ShortcutUrl;
    }

    public void setShortcutUrl(String ShortcutUrl) {
        this._ShortcutUrl = ShortcutUrl;
    }    

    public String getHeaderMeta() {
        return _HeaderMeta;
    }

    public void setHeaderMeta(String HeaderMeta) {
        this._HeaderMeta = HeaderMeta;
    }    

    public String getHeaderLink() {
        return _HeaderLink;
    }

    public void setHeaderLink(String HeaderLink) {
        this._HeaderLink = HeaderLink;
    }    

    public String getColumnNum() {
        return _ColumnNum;
    }

    public void setColumnNum(String ColumnNum) {
        this._ColumnNum = ColumnNum;
    }    

}