package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SweepRegisterForm extends ActionForm {

	private String _Email;             
	private String _Password;             
	private String _Sex;             
	private String _AgeRange;             
	private String _AgreeTerms;             
	private String _TimeCreated;             

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String Password) {
        this._Password = Password;
    }    

    public String getSex() {
        return _Sex;
    }

    public void setSex(String Sex) {
        this._Sex = Sex;
    }    

    public String getAgeRange() {
        return _AgeRange;
    }

    public void setAgeRange(String AgeRange) {
        this._AgeRange = AgeRange;
    }    

    public String getAgreeTerms() {
        return _AgreeTerms;
    }

    public void setAgreeTerms(String AgreeTerms) {
        this._AgreeTerms = AgreeTerms;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}