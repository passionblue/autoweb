package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcCheckoutForm extends ActionForm {

	private String _UserId;             
	private String _Email;             
	private String _EmailRetype;             
	private String _FirstName;             
	private String _LastName;             
	private String _Address1;             
	private String _Address2;             
	private String _City;             
	private String _CountryRegion;             
	private String _StateProvince;             
	private String _Zip;             
	private String _Phone;             
	private String _UseBilling;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
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

    public String getAddress1() {
        return _Address1;
    }

    public void setAddress1(String Address1) {
        this._Address1 = Address1;
    }    

    public String getAddress2() {
        return _Address2;
    }

    public void setAddress2(String Address2) {
        this._Address2 = Address2;
    }    

    public String getCity() {
        return _City;
    }

    public void setCity(String City) {
        this._City = City;
    }    

    public String getCountryRegion() {
        return _CountryRegion;
    }

    public void setCountryRegion(String CountryRegion) {
        this._CountryRegion = CountryRegion;
    }    

    public String getStateProvince() {
        return _StateProvince;
    }

    public void setStateProvince(String StateProvince) {
        this._StateProvince = StateProvince;
    }    

    public String getZip() {
        return _Zip;
    }

    public void setZip(String Zip) {
        this._Zip = Zip;
    }    

    public String getPhone() {
        return _Phone;
    }

    public void setPhone(String Phone) {
        this._Phone = Phone;
    }    

    public String getUseBilling() {
        return _UseBilling;
    }

    public void setUseBilling(String UseBilling) {
        this._UseBilling = UseBilling;
    }    

}