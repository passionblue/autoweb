package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContentFeedSiteForm extends ActionForm {

	private String _ContentFeedId;             
	private String _DisplayType;             
	private String _TimeCreated;             

    public String getContentFeedId() {
        return _ContentFeedId;
    }

    public void setContentFeedId(String ContentFeedId) {
        this._ContentFeedId = ContentFeedId;
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