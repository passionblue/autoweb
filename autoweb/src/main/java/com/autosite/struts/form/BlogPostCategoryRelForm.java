package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BlogPostCategoryRelForm extends ActionForm {

	private String _BlogPostId;             
	private String _BlogCategoryId;             
	private String _TimeCreated;             

    public String getBlogPostId() {
        return _BlogPostId;
    }

    public void setBlogPostId(String BlogPostId) {
        this._BlogPostId = BlogPostId;
    }    

    public String getBlogCategoryId() {
        return _BlogCategoryId;
    }

    public void setBlogCategoryId(String BlogCategoryId) {
        this._BlogCategoryId = BlogCategoryId;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}