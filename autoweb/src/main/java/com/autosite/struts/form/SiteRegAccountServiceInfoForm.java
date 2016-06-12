package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SiteRegAccountServiceInfoForm extends ActionForm {

	private String _TargetDomain;             
	private String _Email;             
	private String _EmailRetype;             
	private String _Company;             
	private String _FirstName;             
	private String _LastName;             
	private String _Phone;             

    public String getTargetDomain() {
        return _TargetDomain;
    }

    public void setTargetDomain(String TargetDomain) {
        this._TargetDomain = TargetDomain;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getEmailRetype() {
        return _EmailRetype;
    }

    public void setEmailRetype(String EmailRetype) {
        this._EmailRetype = EmailRetype;
    }    

    public String getCompany() {
        return _Company;
    }

    public void setCompany(String Company) {
        this._Company = Company;
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

    public String getPhone() {
        return _Phone;
    }

    public void setPhone(String Phone) {
        this._Phone = Phone;
    }    

}