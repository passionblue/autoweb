package com.autosite.db;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class SiteRegPaymentInfo extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

	private long _id;
	private String _targetDomain;
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
	private String _paymentType;
	private String _cardType;
	private String _paymentNum;
	private String _expireMonth;
	private String _expireYear;
	private String _ccv;
	private int _skip;

	public 	SiteRegPaymentInfo(){
	}

	public long getId(){
		return _id;
	}
	public void setId(long id){
		_id = id;
	}
	public String getTargetDomain(){
		return _targetDomain;
	}
	public void setTargetDomain(String targetDomain){
		_targetDomain = targetDomain;
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
	public String getPaymentType(){
		return _paymentType;
	}
	public void setPaymentType(String paymentType){
		_paymentType = paymentType;
	}
	public String getCardType(){
		return _cardType;
	}
	public void setCardType(String cardType){
		_cardType = cardType;
	}
	public String getPaymentNum(){
		return _paymentNum;
	}
	public void setPaymentNum(String paymentNum){
		_paymentNum = paymentNum;
	}
	public String getExpireMonth(){
		return _expireMonth;
	}
	public void setExpireMonth(String expireMonth){
		_expireMonth = expireMonth;
	}
	public String getExpireYear(){
		return _expireYear;
	}
	public void setExpireYear(String expireYear){
		_expireYear = expireYear;
	}
	public String getCcv(){
		return _ccv;
	}
	public void setCcv(String ccv){
		_ccv = ccv;
	}
	public int getSkip(){
		return _skip;
	}
	public void setSkip(int skip){
		_skip = skip;
	}
}