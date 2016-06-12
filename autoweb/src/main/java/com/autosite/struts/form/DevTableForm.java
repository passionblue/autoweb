package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DevTableForm extends ActionForm {

	private String _DevNoteId;             
	private String _Title;             
	private String _Subject;             
	private String _Data;             
	private String _Type;             
	private String _Disable;             
	private String _RadioValue;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getDevNoteId() {
        return _DevNoteId;
    }

    public void setDevNoteId(String DevNoteId) {
        this._DevNoteId = DevNoteId;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getSubject() {
        return _Subject;
    }

    public void setSubject(String Subject) {
        this._Subject = Subject;
    }    

    public String getData() {
        return _Data;
    }

    public void setData(String Data) {
        this._Data = Data;
    }    

    public String getType() {
        return _Type;
    }

    public void setType(String Type) {
        this._Type = Type;
    }    

    public String getDisable() {
        return _Disable;
    }

    public void setDisable(String Disable) {
        this._Disable = Disable;
    }    

    public String getRadioValue() {
        return _RadioValue;
    }

    public void setRadioValue(String RadioValue) {
        this._RadioValue = RadioValue;
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