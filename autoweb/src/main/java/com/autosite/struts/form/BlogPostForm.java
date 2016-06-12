package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BlogPostForm extends ActionForm {

	private String _BlogMainId;             
	private String _CategoryId;             
	private String _Subject;             
	private String _Content;             
	private String _PostType;             
	private String _Author;             
	private String _ContentImage;             
	private String _ImageUrl1;             
	private String _ImageUrl2;             
	private String _Tags;             
	private String _ShorcutUrl;             
	private String _Hide;             
	private String _PostYear;             
	private String _PostMonth;             
	private String _PostDay;             
	private String _PostYearmonth;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getBlogMainId() {
        return _BlogMainId;
    }

    public void setBlogMainId(String BlogMainId) {
        this._BlogMainId = BlogMainId;
    }    

    public String getCategoryId() {
        return _CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this._CategoryId = CategoryId;
    }    

    public String getSubject() {
        return _Subject;
    }

    public void setSubject(String Subject) {
        this._Subject = Subject;
    }    

    public String getContent() {
        return _Content;
    }

    public void setContent(String Content) {
        this._Content = Content;
    }    

    public String getPostType() {
        return _PostType;
    }

    public void setPostType(String PostType) {
        this._PostType = PostType;
    }    

    public String getAuthor() {
        return _Author;
    }

    public void setAuthor(String Author) {
        this._Author = Author;
    }    

    public String getContentImage() {
        return _ContentImage;
    }

    public void setContentImage(String ContentImage) {
        this._ContentImage = ContentImage;
    }    

    public String getImageUrl1() {
        return _ImageUrl1;
    }

    public void setImageUrl1(String ImageUrl1) {
        this._ImageUrl1 = ImageUrl1;
    }    

    public String getImageUrl2() {
        return _ImageUrl2;
    }

    public void setImageUrl2(String ImageUrl2) {
        this._ImageUrl2 = ImageUrl2;
    }    

    public String getTags() {
        return _Tags;
    }

    public void setTags(String Tags) {
        this._Tags = Tags;
    }    

    public String getShorcutUrl() {
        return _ShorcutUrl;
    }

    public void setShorcutUrl(String ShorcutUrl) {
        this._ShorcutUrl = ShorcutUrl;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getPostYear() {
        return _PostYear;
    }

    public void setPostYear(String PostYear) {
        this._PostYear = PostYear;
    }    

    public String getPostMonth() {
        return _PostMonth;
    }

    public void setPostMonth(String PostMonth) {
        this._PostMonth = PostMonth;
    }    

    public String getPostDay() {
        return _PostDay;
    }

    public void setPostDay(String PostDay) {
        this._PostDay = PostDay;
    }    

    public String getPostYearmonth() {
        return _PostYearmonth;
    }

    public void setPostYearmonth(String PostYearmonth) {
        this._PostYearmonth = PostYearmonth;
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