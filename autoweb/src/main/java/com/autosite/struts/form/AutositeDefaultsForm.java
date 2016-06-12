package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AutositeDefaultsForm extends ActionForm {

	private String _StyleId;             
	private String _LinkStyleId;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getStyleId() {
        return _StyleId;
    }

    public void setStyleId(String StyleId) {
        this._StyleId = StyleId;
    }    

    public String getLinkStyleId() {
        return _LinkStyleId;
    }

    public void setLinkStyleId(String LinkStyleId) {
        this._LinkStyleId = LinkStyleId;
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