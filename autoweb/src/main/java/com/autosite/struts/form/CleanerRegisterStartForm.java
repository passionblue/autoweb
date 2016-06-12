package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerRegisterStartForm extends ActionForm {

	private String _SiteTitle;             
	private String _SiteName;             
	private String _Username;             
	private String _Email;             
	private String _Password;             
	private String _PasswordRepeat;             
	private String _Location;             
	private String _CreatedSiteUrl;             

    public String getSiteTitle() {
        return _SiteTitle;
    }

    public void setSiteTitle(String SiteTitle) {
        this._SiteTitle = SiteTitle;
    }    

    public String getSiteName() {
        return _SiteName;
    }

    public void setSiteName(String SiteName) {
        this._SiteName = SiteName;
    }    

    public String getUsername() {
        return _Username;
    }

    public void setUsername(String Username) {
        this._Username = Username;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String Password) {
        this._Password = Password;
    }    

    public String getPasswordRepeat() {
        return _PasswordRepeat;
    }

    public void setPasswordRepeat(String PasswordRepeat) {
        this._PasswordRepeat = PasswordRepeat;
    }    

    public String getLocation() {
        return _Location;
    }

    public void setLocation(String Location) {
        this._Location = Location;
    }    

    public String getCreatedSiteUrl() {
        return _CreatedSiteUrl;
    }

    public void setCreatedSiteUrl(String CreatedSiteUrl) {
        this._CreatedSiteUrl = CreatedSiteUrl;
    }    

}