package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SweepPasswordForm extends ActionForm {

	private String _SendPasswordEmail;             
	private String _OldPassword;             
	private String _NewPassword;             
	private String _PasswordRetype;             

    public String getSendPasswordEmail() {
        return _SendPasswordEmail;
    }

    public void setSendPasswordEmail(String SendPasswordEmail) {
        this._SendPasswordEmail = SendPasswordEmail;
    }    

    public String getOldPassword() {
        return _OldPassword;
    }

    public void setOldPassword(String OldPassword) {
        this._OldPassword = OldPassword;
    }    

    public String getNewPassword() {
        return _NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this._NewPassword = NewPassword;
    }    

    public String getPasswordRetype() {
        return _PasswordRetype;
    }

    public void setPasswordRetype(String PasswordRetype) {
        this._PasswordRetype = PasswordRetype;
    }    

}