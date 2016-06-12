package com.autosite.db;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class SiteRegAccountServiceInfo extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

	private long _id;
	private String _targetDomain;
	private String _email;
	private String _emailRetype;
	private String _company;
	private String _firstName;
	private String _lastName;
	private String _phone;

	public 	SiteRegAccountServiceInfo(){
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
	public String getCompany(){
		return _company;
	}
	public void setCompany(String company){
		_company = company;
	}
	public String getFirstName(){
		return _firstName;
	}
	public void setFirstName(String firstName){
		_firstName = firstName;
	}
	public String getLastName(){
		return _lastName;
	}
	public void setLastName(String lastName){
		_lastName = lastName;
	}
	public String getPhone(){
		return _phone;
	}
	public void setPhone(String phone){
		_phone = phone;
	}
}