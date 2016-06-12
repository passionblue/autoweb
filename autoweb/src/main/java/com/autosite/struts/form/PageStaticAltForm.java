package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PageStaticAltForm extends ActionForm {

	private String _PageAlias;             
	private String _PageUrl;             
	private String _MenuPage;             
	private String _ErrorPage;             
	private String _LoginRequired;             
	private String _ViewProc;             
	private String _DynamicContent;             
	private String _HideMenu;             
	private String _HideMid;             
	private String _HideAd;             
	private String _TimeCreated;             

    public String getPageAlias() {
        return _PageAlias;
    }

    public void setPageAlias(String PageAlias) {
        this._PageAlias = PageAlias;
    }    

    public String getPageUrl() {
        return _PageUrl;
    }

    public void setPageUrl(String PageUrl) {
        this._PageUrl = PageUrl;
    }    

    public String getMenuPage() {
        return _MenuPage;
    }

    public void setMenuPage(String MenuPage) {
        this._MenuPage = MenuPage;
    }    

    public String getErrorPage() {
        return _ErrorPage;
    }

    public void setErrorPage(String ErrorPage) {
        this._ErrorPage = ErrorPage;
    }    

    public String getLoginRequired() {
        return _LoginRequired;
    }

    public void setLoginRequired(String LoginRequired) {
        this._LoginRequired = LoginRequired;
    }    

    public String getViewProc() {
        return _ViewProc;
    }

    public void setViewProc(String ViewProc) {
        this._ViewProc = ViewProc;
    }    

    public String getDynamicContent() {
        return _DynamicContent;
    }

    public void setDynamicContent(String DynamicContent) {
        this._DynamicContent = DynamicContent;
    }    

    public String getHideMenu() {
        return _HideMenu;
    }

    public void setHideMenu(String HideMenu) {
        this._HideMenu = HideMenu;
    }    

    public String getHideMid() {
        return _HideMid;
    }

    public void setHideMid(String HideMid) {
        this._HideMid = HideMid;
    }    

    public String getHideAd() {
        return _HideAd;
    }

    public void setHideAd(String HideAd) {
        this._HideAd = HideAd;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}