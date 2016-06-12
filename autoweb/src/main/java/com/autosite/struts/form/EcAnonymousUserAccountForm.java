package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcAnonymousUserAccountForm extends ActionForm {

	private String _Email;             
	private String _Subscribed;             
	private String _TimeCreated;             

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getSubscribed() {
        return _Subscribed;
    }

    public void setSubscribed(String Subscribed) {
        this._Subscribed = Subscribed;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}