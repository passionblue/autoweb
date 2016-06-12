package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SiteSuggestForm extends ActionForm {

	private String _Category;             
	private String _Suggest;             
	private String _Resolved;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getCategory() {
        return _Category;
    }

    public void setCategory(String Category) {
        this._Category = Category;
    }    

    public String getSuggest() {
        return _Suggest;
    }

    public void setSuggest(String Suggest) {
        this._Suggest = Suggest;
    }    

    public String getResolved() {
        return _Resolved;
    }

    public void setResolved(String Resolved) {
        this._Resolved = Resolved;
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