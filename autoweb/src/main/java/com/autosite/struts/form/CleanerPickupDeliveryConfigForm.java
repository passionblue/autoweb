/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:46 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerPickupDeliveryConfigForm extends ActionForm {

	private String _LocationId;             
	private String _ApplyAllLocations;             
	private String _DisableWebRequest;             
	private String _DisallowAnonymousRequest;             
	private String _RequireCustomerRegister;             
	private String _RequireCustomerLogin;             

    public String getLocationId() {
        return _LocationId;
    }

    public void setLocationId(String LocationId) {
        this._LocationId = LocationId;
    }    

    public String getApplyAllLocations() {
        return _ApplyAllLocations;
    }

    public void setApplyAllLocations(String ApplyAllLocations) {
        this._ApplyAllLocations = ApplyAllLocations;
    }    

    public String getDisableWebRequest() {
        return _DisableWebRequest;
    }

    public void setDisableWebRequest(String DisableWebRequest) {
        this._DisableWebRequest = DisableWebRequest;
    }    

    public String getDisallowAnonymousRequest() {
        return _DisallowAnonymousRequest;
    }

    public void setDisallowAnonymousRequest(String DisallowAnonymousRequest) {
        this._DisallowAnonymousRequest = DisallowAnonymousRequest;
    }    

    public String getRequireCustomerRegister() {
        return _RequireCustomerRegister;
    }

    public void setRequireCustomerRegister(String RequireCustomerRegister) {
        this._RequireCustomerRegister = RequireCustomerRegister;
    }    

    public String getRequireCustomerLogin() {
        return _RequireCustomerLogin;
    }

    public void setRequireCustomerLogin(String RequireCustomerLogin) {
        this._RequireCustomerLogin = RequireCustomerLogin;
    }    

}