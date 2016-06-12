package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmailSubsForm extends ActionForm {

	private String _Subject;             
	private String _Email;             
	private String _FirstName;             
	private String _LastName;             
	private String _Confirmed;             
	private String _Disabled;             
	private String _CheckOpt1;             
	private String _CheckOpt2;             
	private String _ExtraInfo;             
	private String _TimeCreated;             

    public String getSubject() {
        return _Subject;
    }

    public void setSubject(String Subject) {
        this._Subject = Subject;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getFirstName() {
        return _FirstName;
    }

    public void setFirstName(String FirstName) {
        this._FirstName = FirstName;
    }    

    public String getLastName() {
        return _LastName;
    }

    public void setLastName(String LastName) {
        this._LastName = LastName;
    }    

    public String getConfirmed() {
        return _Confirmed;
    }

    public void setConfirmed(String Confirmed) {
        this._Confirmed = Confirmed;
    }    

    public String getDisabled() {
        return _Disabled;
    }

    public void setDisabled(String Disabled) {
        this._Disabled = Disabled;
    }    

    public String getCheckOpt1() {
        return _CheckOpt1;
    }

    public void setCheckOpt1(String CheckOpt1) {
        this._CheckOpt1 = CheckOpt1;
    }    

    public String getCheckOpt2() {
        return _CheckOpt2;
    }

    public void setCheckOpt2(String CheckOpt2) {
        this._CheckOpt2 = CheckOpt2;
    }    

    public String getExtraInfo() {
        return _ExtraInfo;
    }

    public void setExtraInfo(String ExtraInfo) {
        this._ExtraInfo = ExtraInfo;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}