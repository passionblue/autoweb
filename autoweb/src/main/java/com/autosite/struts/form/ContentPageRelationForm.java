package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContentPageRelationForm extends ActionForm {

	private String _ContentId;             
	private String _PageId;             
	private String _TimeCreated;             

    public String getContentId() {
        return _ContentId;
    }

    public void setContentId(String ContentId) {
        this._ContentId = ContentId;
    }    

    public String getPageId() {
        return _PageId;
    }

    public void setPageId(String PageId) {
        this._PageId = PageId;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}