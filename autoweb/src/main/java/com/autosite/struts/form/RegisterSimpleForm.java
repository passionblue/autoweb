package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RegisterSimpleForm extends ActionForm {

    private String _FirstName;             
    private String _LastName;             
    private String _Email;             
    private String _Username;             
    private String _Password;             
    private String _UserType;             
    private String _BirthYear;             
    private String _BirthMonth;             
    private String _BirthDay;             
    private String _TimeCreated;             
    private String _TimeUpdated;             

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

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

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

    public String getUserType() {
        return _UserType;
    }

    public void setUserType(String UserType) {
        this._UserType = UserType;
    }    

    public String getBirthYear() {
        return _BirthYear;
    }

    public void setBirthYear(String BirthYear) {
        this._BirthYear = BirthYear;
    }    

    public String getBirthMonth() {
        return _BirthMonth;
    }

    public void setBirthMonth(String BirthMonth) {
        this._BirthMonth = BirthMonth;
    }    

    public String getBirthDay() {
        return _BirthDay;
    }

    public void setBirthDay(String BirthDay) {
        this._BirthDay = BirthDay;
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