package com.autosite.db;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class EcCheckoutPaymentWithoutRegister extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

	private long _id;
	private long _siteId;
	private long _userId;
	private String _cartSerial;
	private String _paymentType;
	private String _cardType;
	private String _paymentNum;
	private String _expireMonth;
	private String _expireYear;
	private String _ccv;

	public 	EcCheckoutPaymentWithoutRegister(){
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
}