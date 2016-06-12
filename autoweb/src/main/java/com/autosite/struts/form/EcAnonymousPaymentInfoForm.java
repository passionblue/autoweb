package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcAnonymousPaymentInfoForm extends ActionForm {

	private String _AnonymousUserId;             
	private String _FirstName;             
	private String _MiddleInitial;             
	private String _LastName;             
	private String _Address1;             
	private String _Address2;             
	private String _City;             
	private String _State;             
	private String _Zip;             
	private String _Country;             
	private String _PaymentType;             
	private String _PaymentNum;             
	private String _PaymentExpireMonth;             
	private String _PaymentExpireYear;             
	private String _PaymentExtraNum;             
	private String _TimeCreated;             

    public String getAnonymousUserId() {
        return _AnonymousUserId;
    }

    public void setAnonymousUserId(String AnonymousUserId) {
        this._AnonymousUserId = AnonymousUserId;
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

    public String getState() {
        return _State;
    }

    public void setState(String State) {
        this._State = State;
    }    

    public String getZip() {
        return _Zip;
    }

    public void setZip(String Zip) {
        this._Zip = Zip;
    }    

    public String getCountry() {
        return _Country;
    }

    public void setCountry(String Country) {
        this._Country = Country;
    }    

    public String getPaymentType() {
        return _PaymentType;
    }

    public void setPaymentType(String PaymentType) {
        this._PaymentType = PaymentType;
    }    

    public String getPaymentNum() {
        return _PaymentNum;
    }

    public void setPaymentNum(String PaymentNum) {
        this._PaymentNum = PaymentNum;
    }    

    public String getPaymentExpireMonth() {
        return _PaymentExpireMonth;
    }

    public void setPaymentExpireMonth(String PaymentExpireMonth) {
        this._PaymentExpireMonth = PaymentExpireMonth;
    }    

    public String getPaymentExpireYear() {
        return _PaymentExpireYear;
    }

    public void setPaymentExpireYear(String PaymentExpireYear) {
        this._PaymentExpireYear = PaymentExpireYear;
    }    

    public String getPaymentExtraNum() {
        return _PaymentExtraNum;
    }

    public void setPaymentExtraNum(String PaymentExtraNum) {
        this._PaymentExtraNum = PaymentExtraNum;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}