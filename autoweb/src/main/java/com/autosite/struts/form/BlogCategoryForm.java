package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BlogCategoryForm extends ActionForm {

	private String _BlogMainId;             
	private String _ParentCategoryId;             
	private String _RootCategoryId;             
	private String _Title;             
	private String _Hide;             
	private String _ImageUrl1;             
	private String _ImageUrl2;             
	private String _TimeCreated;             

    public String getBlogMainId() {
        return _BlogMainId;
    }

    public void setBlogMainId(String BlogMainId) {
        this._BlogMainId = BlogMainId;
    }    

    public String getParentCategoryId() {
        return _ParentCategoryId;
    }

    public void setParentCategoryId(String ParentCategoryId) {
        this._ParentCategoryId = ParentCategoryId;
    }    

    public String getRootCategoryId() {
        return _RootCategoryId;
    }

    public void setRootCategoryId(String RootCategoryId) {
        this._RootCategoryId = RootCategoryId;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
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

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}