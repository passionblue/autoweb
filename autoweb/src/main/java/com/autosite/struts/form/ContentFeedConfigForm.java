package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContentFeedConfigForm extends ActionForm {

	private String _FeedCategory;             
	private String _FeedType;             
	private String _DisplayType;             
	private String _TimeCreated;             

    public String getFeedCategory() {
        return _FeedCategory;
    }

    public void setFeedCategory(String FeedCategory) {
        this._FeedCategory = FeedCategory;
    }    

    public String getFeedType() {
        return _FeedType;
    }

    public void setFeedType(String FeedType) {
        this._FeedType = FeedType;
    }    

    public String getDisplayType() {
        return _DisplayType;
    }

    public void setDisplayType(String DisplayType) {
        this._DisplayType = DisplayType;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}