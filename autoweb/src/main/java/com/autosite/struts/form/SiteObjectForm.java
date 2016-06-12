package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SiteObjectForm extends ActionForm {

	private String _SiteUrl;             
	private String _AccountId;             
	private String _CreatedTime;             
	private String _SiteGroup;             
	private String _Registered;             
	private String _OnSale;             
	private String _SuperAdminEnable;             
	private String _SiteRegisterEnable;             
	private String _SubdomainEnable;             
	private String _SiteRegisterSite;             
	private String _BaseSiteId;             
	private String _Subsite;             
	private String _Disabled;             

    public String getSiteUrl() {
        return _SiteUrl;
    }

    public void setSiteUrl(String SiteUrl) {
        this._SiteUrl = SiteUrl;
    }    

    public String getAccountId() {
        return _AccountId;
    }

    public void setAccountId(String AccountId) {
        this._AccountId = AccountId;
    }    

    public String getCreatedTime() {
        return _CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this._CreatedTime = CreatedTime;
    }    

    public String getSiteGroup() {
        return _SiteGroup;
    }

    public void setSiteGroup(String SiteGroup) {
        this._SiteGroup = SiteGroup;
    }    

    public String getRegistered() {
        return _Registered;
    }

    public void setRegistered(String Registered) {
        this._Registered = Registered;
    }    

    public String getOnSale() {
        return _OnSale;
    }

    public void setOnSale(String OnSale) {
        this._OnSale = OnSale;
    }    

    public String getSuperAdminEnable() {
        return _SuperAdminEnable;
    }

    public void setSuperAdminEnable(String SuperAdminEnable) {
        this._SuperAdminEnable = SuperAdminEnable;
    }    

    public String getSiteRegisterEnable() {
        return _SiteRegisterEnable;
    }

    public void setSiteRegisterEnable(String SiteRegisterEnable) {
        this._SiteRegisterEnable = SiteRegisterEnable;
    }    

    public String getSubdomainEnable() {
        return _SubdomainEnable;
    }

    public void setSubdomainEnable(String SubdomainEnable) {
        this._SubdomainEnable = SubdomainEnable;
    }    

    public String getSiteRegisterSite() {
        return _SiteRegisterSite;
    }

    public void setSiteRegisterSite(String SiteRegisterSite) {
        this._SiteRegisterSite = SiteRegisterSite;
    }    

    public String getBaseSiteId() {
        return _BaseSiteId;
    }

    public void setBaseSiteId(String BaseSiteId) {
        this._BaseSiteId = BaseSiteId;
    }    

    public String getSubsite() {
        return _Subsite;
    }

    public void setSubsite(String Subsite) {
        this._Subsite = Subsite;
    }    

    public String getDisabled() {
        return _Disabled;
    }

    public void setDisabled(String Disabled) {
        this._Disabled = Disabled;
    }    

}