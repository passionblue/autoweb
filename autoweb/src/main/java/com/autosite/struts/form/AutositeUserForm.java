package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AutositeUserForm extends ActionForm {

	private String _Username;             
	private String _Password;             
	private String _Email;             
	private String _UserType;             
	private String _FirstName;             
	private String _LastName;             
	private String _Nickname;             
	private String _TimeCreated;             
	private String _TimeUpdated;             
	private String _Disabled;             
	private String _TimeDisabled;             
	private String _Confirmed;             
	private String _TimeConfirmed;             
	private String _PagelessSession;             
	private String _Opt1;             
	private String _Opt2;             

    public String getUsername() {
        return _Username;
    }

    public void setUsername(String Username) {
        this._Username = Username;
    }    

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String Password) {
        this._Password = Password;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getUserType() {
        return _UserType;
    }

    public void setUserType(String UserType) {
        this._UserType = UserType;
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

    public String getNickname() {
        return _Nickname;
    }

    public void setNickname(String Nickname) {
        this._Nickname = Nickname;
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

    public String getDisabled() {
        return _Disabled;
    }

    public void setDisabled(String Disabled) {
        this._Disabled = Disabled;
    }    

    public String getTimeDisabled() {
        return _TimeDisabled;
    }

    public void setTimeDisabled(String TimeDisabled) {
        this._TimeDisabled = TimeDisabled;
    }    

    public String getConfirmed() {
        return _Confirmed;
    }

    public void setConfirmed(String Confirmed) {
        this._Confirmed = Confirmed;
    }    

    public String getTimeConfirmed() {
        return _TimeConfirmed;
    }

    public void setTimeConfirmed(String TimeConfirmed) {
        this._TimeConfirmed = TimeConfirmed;
    }    

    public String getPagelessSession() {
        return _PagelessSession;
    }

    public void setPagelessSession(String PagelessSession) {
        this._PagelessSession = PagelessSession;
    }    

    public String getOpt1() {
        return _Opt1;
    }

    public void setOpt1(String Opt1) {
        this._Opt1 = Opt1;
    }    

    public String getOpt2() {
        return _Opt2;
    }

    public void setOpt2(String Opt2) {
        this._Opt2 = Opt2;
    }    

}