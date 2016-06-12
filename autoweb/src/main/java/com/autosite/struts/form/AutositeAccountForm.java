package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AutositeAccountForm extends ActionForm {

	private String _AccountNum;             
	private String _Enabled;             
	private String _EmailConfirmed;             
	private String _TimeConfirmed;             
	private String _TimeCreated;             

    public String getAccountNum() {
        return _AccountNum;
    }

    public void setAccountNum(String AccountNum) {
        this._AccountNum = AccountNum;
    }    

    public String getEnabled() {
        return _Enabled;
    }

    public void setEnabled(String Enabled) {
        this._Enabled = Enabled;
    }    

    public String getEmailConfirmed() {
        return _EmailConfirmed;
    }

    public void setEmailConfirmed(String EmailConfirmed) {
        this._EmailConfirmed = EmailConfirmed;
    }    

    public String getTimeConfirmed() {
        return _TimeConfirmed;
    }

    public void setTimeConfirmed(String TimeConfirmed) {
        this._TimeConfirmed = TimeConfirmed;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}