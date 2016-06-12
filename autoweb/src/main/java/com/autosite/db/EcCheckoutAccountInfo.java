package com.autosite.db;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class EcCheckoutAccountInfo extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

	private long _id;
	private long _siteId;
	private long _userId;
	private String _cartSerial;
	private String _email;
	private String _emailRetype;
	private String _firstName;
	private String _middleInitial;
	private String _lastName;
	private String _address1;
	private String _address2;
	private String _city;
	private String _countryRegion;
	private String _stateProvince;
	private String _zip;
	private String _phone;
	private int _useBilling;
	private String _billingFirstName;
	private String _billingMiddleInitial;
	private String _billingLastName;
	private String _billingAddress1;
	private String _billingAddress2;
	private String _billingCity;
	private String _billingCountry;
	private String _billingState;
	private String _billingZip;
	private String _billingPhone;
	private int _termAgree;
	private int _subsEmail;
	private String _password;
	private String _passwordRetype;
	private String _returnEmail;
	private String _returnPassword;

	public 	EcCheckoutAccountInfo(){
	}

	public long getId(){
		return _id;
	}
	public void setId(long id){
		_id = id;
	}
	public long getSiteId(){
		return _siteId;
	}
	public void setSiteId(long siteId){
		_siteId = siteId;
	}
	public long getUserId(){
		return _userId;
	}
	public void setUserId(long userId){
		_userId = userId;
	}
	public String getCartSerial(){
		return _cartSerial;
	}
	public void setCartSerial(String cartSerial){
		_cartSerial = cartSerial;
	}
	public String getEmail(){
		return _email;
	}
	public void setEmail(String email){
		_email = email;
	}
	public String getEmailRetype(){
		return _emailRetype;
	}
	public void setEmailRetype(String emailRetype){
		_emailRetype = emailRetype;
	}
	public String getFirstName(){
		return _firstName;
	}
	public void setFirstName(String firstName){
		_firstName = firstName;
	}
	public String getMiddleInitial(){
		return _middleInitial;
	}
	public void setMiddleInitial(String middleInitial){
		_middleInitial = middleInitial;
	}
	public String getLastName(){
		return _lastName;
	}
	public void setLastName(String lastName){
		_lastName = lastName;
	}
	public String getAddress1(){
		return _address1;
	}
	public void setAddress1(String address1){
		_address1 = address1;
	}
	public String getAddress2(){
		return _address2;
	}
	public void setAddress2(String address2){
		_address2 = address2;
	}
	public String getCity(){
		return _city;
	}
	public void setCity(String city){
		_city = city;
	}
	public String getCountryRegion(){
		return _countryRegion;
	}
	public void setCountryRegion(String countryRegion){
		_countryRegion = countryRegion;
	}
	public String getStateProvince(){
		return _stateProvince;
	}
	public void setStateProvince(String stateProvince){
		_stateProvince = stateProvince;
	}
	public String getZip(){
		return _zip;
	}
	public void setZip(String zip){
		_zip = zip;
	}
	public String getPhone(){
		return _phone;
	}
	public void setPhone(String phone){
		_phone = phone;
	}
	public int getUseBilling(){
		return _useBilling;
	}
	public void setUseBilling(int useBilling){
		_useBilling = useBilling;
	}
	public String getBillingFirstName(){
		return _billingFirstName;
	}
	public void setBillingFirstName(String billingFirstName){
		_billingFirstName = billingFirstName;
	}
	public String getBillingMiddleInitial(){
		return _billingMiddleInitial;
	}
	public void setBillingMiddleInitial(String billingMiddleInitial){
		_billingMiddleInitial = billingMiddleInitial;
	}
	public String getBillingLastName(){
		return _billingLastName;
	}
	public void setBillingLastName(String billingLastName){
		_billingLastName = billingLastName;
	}
	public String getBillingAddress1(){
		return _billingAddress1;
	}
	public void setBillingAddress1(String billingAddress1){
		_billingAddress1 = billingAddress1;
	}
	public String getBillingAddress2(){
		return _billingAddress2;
	}
	public void setBillingAddress2(String billingAddress2){
		_billingAddress2 = billingAddress2;
	}
	public String getBillingCity(){
		return _billingCity;
	}
	public void setBillingCity(String billingCity){
		_billingCity = billingCity;
	}
	public String getBillingCountry(){
		return _billingCountry;
	}
	public void setBillingCountry(String billingCountry){
		_billingCountry = billingCountry;
	}
	public String getBillingState(){
		return _billingState;
	}
	public void setBillingState(String billingState){
		_billingState = billingState;
	}
	public String getBillingZip(){
		return _billingZip;
	}
	public void setBillingZip(String billingZip){
		_billingZip = billingZip;
	}
	public String getBillingPhone(){
		return _billingPhone;
	}
	public void setBillingPhone(String billingPhone){
		_billingPhone = billingPhone;
	}
	public int getTermAgree(){
		return _termAgree;
	}
	public void setTermAgree(int termAgree){
		_termAgree = termAgree;
	}
	public int getSubsEmail(){
		return _subsEmail;
	}
	public void setSubsEmail(int subsEmail){
		_subsEmail = subsEmail;
	}
	public String getPassword(){
		return _password;
	}
	public void setPassword(String password){
		_password = password;
	}
	public String getPasswordRetype(){
		return _passwordRetype;
	}
	public void setPasswordRetype(String passwordRetype){
		_passwordRetype = passwordRetype;
	}
	public String getReturnEmail(){
		return _returnEmail;
	}
	public void setReturnEmail(String returnEmail){
		_returnEmail = returnEmail;
	}
	public String getReturnPassword(){
		return _returnPassword;
	}
	public void setReturnPassword(String returnPassword){
		_returnPassword = returnPassword;
	}
}