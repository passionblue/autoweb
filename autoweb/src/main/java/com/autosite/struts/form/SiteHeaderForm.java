package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SiteHeaderForm extends ActionForm {

	private String _AsIs;             
	private String _IncludeType;             
	private String _IncludeText;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getAsIs() {
        return _AsIs;
    }

    public void setAsIs(String AsIs) {
        this._AsIs = AsIs;
    }    

    public String getIncludeType() {
        return _IncludeType;
    }

    public void setIncludeType(String IncludeType) {
        this._IncludeType = IncludeType;
    }    

    public String getIncludeText() {
        return _IncludeText;
    }

    public void setIncludeText(String IncludeText) {
        this._IncludeText = IncludeText;
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