package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenExtraForm extends ActionForm {

	private String _MainId;             
	private String _SubId;             
	private String _ExtraValue;             
	private String _ExtraData;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getMainId() {
        return _MainId;
    }

    public void setMainId(String MainId) {
        this._MainId = MainId;
    }    

    public String getSubId() {
        return _SubId;
    }

    public void setSubId(String SubId) {
        this._SubId = SubId;
    }    

    public String getExtraValue() {
        return _ExtraValue;
    }

    public void setExtraValue(String ExtraValue) {
        this._ExtraValue = ExtraValue;
    }    

    public String getExtraData() {
        return _ExtraData;
    }

    public void setExtraData(String ExtraData) {
        this._ExtraData = ExtraData;
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