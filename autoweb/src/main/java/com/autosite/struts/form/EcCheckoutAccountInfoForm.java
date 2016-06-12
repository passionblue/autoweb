package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcCheckoutAccountInfoForm extends ActionForm {

	private String _UserId;             
	private String _CartSerial;             
	private String _Email;             
	private String _EmailRetype;             
	private String _FirstName;             
	private String _MiddleInitial;             
	private String _LastName;             
	private String _Address1;             
	private String _Address2;             
	private String _City;             
	private String _CountryRegion;             
	private String _StateProvince;             
	private String _Zip;             
	private String _Phone;             
	private String _UseBilling;             
	private String _BillingFirstName;             
	private String _BillingMiddleInitial;             
	private String _BillingLastName;             
	private String _BillingAddress1;             
	private String _BillingAddress2;             
	private String _BillingCity;             
	private String _BillingCountry;             
	private String _BillingState;             
	private String _BillingZip;             
	private String _BillingPhone;             
	private String _TermAgree;             
	private String _SubsEmail;             
	private String _Password;             
	private String _PasswordRetype;             
	private String _ReturnEmail;             
	private String _ReturnPassword;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getCartSerial() {
        return _CartSerial;
    }

    public void setCartSerial(String CartSerial) {
        this._CartSerial = CartSerial;
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

    public String getMiddleInitial() {
        return _MiddleInitial;
    }

    public void setMiddleInitial(String MiddleInitial) {
        this._MiddleInitial = MiddleInitial;
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

    public String getBillingFirstName() {
        return _BillingFirstName;
    }

    public void setBillingFirstName(String BillingFirstName) {
        this._BillingFirstName = BillingFirstName;
    }    

    public String getBillingMiddleInitial() {
        return _BillingMiddleInitial;
    }

    public void setBillingMiddleInitial(String BillingMiddleInitial) {
        this._BillingMiddleInitial = BillingMiddleInitial;
    }    

    public String getBillingLastName() {
        return _BillingLastName;
    }

    public void setBillingLastName(String BillingLastName) {
        this._BillingLastName = BillingLastName;
    }    

    public String getBillingAddress1() {
        return _BillingAddress1;
    }

    public void setBillingAddress1(String BillingAddress1) {
        this._BillingAddress1 = BillingAddress1;
    }    

    public String getBillingAddress2() {
        return _BillingAddress2;
    }

    public void setBillingAddress2(String BillingAddress2) {
        this._BillingAddress2 = BillingAddress2;
    }    

    public String getBillingCity() {
        return _BillingCity;
    }

    public void setBillingCity(String BillingCity) {
        this._BillingCity = BillingCity;
    }    

    public String getBillingCountry() {
        return _BillingCountry;
    }

    public void setBillingCountry(String BillingCountry) {
        this._BillingCountry = BillingCountry;
    }    

    public String getBillingState() {
        return _BillingState;
    }

    public void setBillingState(String BillingState) {
        this._BillingState = BillingState;
    }    

    public String getBillingZip() {
        return _BillingZip;
    }

    public void setBillingZip(String BillingZip) {
        this._BillingZip = BillingZip;
    }    

    public String getBillingPhone() {
        return _BillingPhone;
    }

    public void setBillingPhone(String BillingPhone) {
        this._BillingPhone = BillingPhone;
    }    

    public String getTermAgree() {
        return _TermAgree;
    }

    public void setTermAgree(String TermAgree) {
        this._TermAgree = TermAgree;
    }    

    public String getSubsEmail() {
        return _SubsEmail;
    }

    public void setSubsEmail(String SubsEmail) {
        this._SubsEmail = SubsEmail;
    }    

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String Password) {
        this._Password = Password;
    }    

    public String getPasswordRetype() {
        return _PasswordRetype;
    }

    public void setPasswordRetype(String PasswordRetype) {
        this._PasswordRetype = PasswordRetype;
    }    

    public String getReturnEmail() {
        return _ReturnEmail;
    }

    public void setReturnEmail(String ReturnEmail) {
        this._ReturnEmail = ReturnEmail;
    }    

    public String getReturnPassword() {
        return _ReturnPassword;
    }

    public void setReturnPassword(String ReturnPassword) {
        this._ReturnPassword = ReturnPassword;
    }    

}