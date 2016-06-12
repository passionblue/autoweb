package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SiteFeatureConfigForm extends ActionForm {

	private String _UserRegisterEnabled;             
	private String _EcEnabled;             
	private String _EmailSubsEnabled;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getUserRegisterEnabled() {
        return _UserRegisterEnabled;
    }

    public void setUserRegisterEnabled(String UserRegisterEnabled) {
        this._UserRegisterEnabled = UserRegisterEnabled;
    }    

    public String getEcEnabled() {
        return _EcEnabled;
    }

    public void setEcEnabled(String EcEnabled) {
        this._EcEnabled = EcEnabled;
    }    

    public String getEmailSubsEnabled() {
        return _EmailSubsEnabled;
    }

    public void setEmailSubsEnabled(String EmailSubsEnabled) {
        this._EmailSubsEnabled = EmailSubsEnabled;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}