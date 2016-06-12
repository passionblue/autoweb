package com.autosite.db;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class SweepPassword extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    private long id;
    private long siteId;

	private String _sendPasswordEmail;
	private String _oldPassword;
	private String _newPassword;
	private String _passwordRetype;

	public 	SweepPassword(){
	}

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

	public String getSendPasswordEmail(){
		return _sendPasswordEmail;
	}
	public void setSendPasswordEmail(String sendPasswordEmail){
		_sendPasswordEmail = sendPasswordEmail;
	}
	public String getOldPassword(){
		return _oldPassword;
	}
	public void setOldPassword(String oldPassword){
		_oldPassword = oldPassword;
	}
	public String getNewPassword(){
		return _newPassword;
	}
	public void setNewPassword(String newPassword){
		_newPassword = newPassword;
	}
	public String getPasswordRetype(){
		return _passwordRetype;
	}
	public void setPasswordRetype(String passwordRetype){
		_passwordRetype = passwordRetype;
	}
}