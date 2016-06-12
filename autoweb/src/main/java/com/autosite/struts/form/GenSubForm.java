package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenSubForm extends ActionForm {

	private String _MainId;             
	private String _StrKey;             
	private String _SubData;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getMainId() {
        return _MainId;
    }

    public void setMainId(String MainId) {
        this._MainId = MainId;
    }    

    public String getStrKey() {
        return _StrKey;
    }

    public void setStrKey(String StrKey) {
        this._StrKey = StrKey;
    }    

    public String getSubData() {
        return _SubData;
    }

    public void setSubData(String SubData) {
        this._SubData = SubData;
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