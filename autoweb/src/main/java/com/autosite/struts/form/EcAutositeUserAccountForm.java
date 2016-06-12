package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcAutositeUserAccountForm extends ActionForm {

	private String _UserId;             
	private String _FirstName;             
	private String _LastName;             
	private String _Subscribed;             
	private String _TimeCreated;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getFirstName() {
        return _FirstName;
    }

    public void setFirstName(String FirstName) {
        this._FirstName = FirstName;
    }    

    public String getLastName() {
        return _LastName;
    }

    public void setLastName(String LastName) {
        this._LastName = LastName;
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