package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BlogCommentForm extends ActionForm {

	private String _BlogMainId;             
	private String _BlogPostId;             
	private String _Comment;             
	private String _Rating;             
	private String _Ipaddress;             
	private String _Name;             
	private String _Password;             
	private String _Website;             
	private String _Email;             
	private String _TimeCreated;             

    public String getBlogMainId() {
        return _BlogMainId;
    }

    public void setBlogMainId(String BlogMainId) {
        this._BlogMainId = BlogMainId;
    }    

    public String getBlogPostId() {
        return _BlogPostId;
    }

    public void setBlogPostId(String BlogPostId) {
        this._BlogPostId = BlogPostId;
    }    

    public String getComment() {
        return _Comment;
    }

    public void setComment(String Comment) {
        this._Comment = Comment;
    }    

    public String getRating() {
        return _Rating;
    }

    public void setRating(String Rating) {
        this._Rating = Rating;
    }    

    public String getIpaddress() {
        return _Ipaddress;
    }

    public void setIpaddress(String Ipaddress) {
        this._Ipaddress = Ipaddress;
    }    

    public String getName() {
        return _Name;
    }

    public void setName(String Name) {
        this._Name = Name;
    }    

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String Password) {
        this._Password = Password;
    }    

    public String getWebsite() {
        return _Website;
    }

    public void setWebsite(String Website) {
        this._Website = Website;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}