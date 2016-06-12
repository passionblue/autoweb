package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContentFeedRelForm extends ActionForm {

	private String _ContentFeedId;             
	private String _ContentId;             
	private String _TimeCreated;             

    public String getContentFeedId() {
        return _ContentFeedId;
    }

    public void setContentFeedId(String ContentFeedId) {
        this._ContentFeedId = ContentFeedId;
    }    

    public String getContentId() {
        return _ContentId;
    }

    public void setContentId(String ContentId) {
        this._ContentId = ContentId;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}