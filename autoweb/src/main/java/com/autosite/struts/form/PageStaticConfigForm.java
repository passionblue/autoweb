package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PageStaticConfigForm extends ActionForm {

	private String _PageAlias;             
	private String _PageCss;             
	private String _PageScript;             
	private String _PageCssImports;             
	private String _PageScriptImports;             
	private String _HideMenu;             
	private String _HideMid;             
	private String _HideAd;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getPageAlias() {
        return _PageAlias;
    }

    public void setPageAlias(String PageAlias) {
        this._PageAlias = PageAlias;
    }    

    public String getPageCss() {
        return _PageCss;
    }

    public void setPageCss(String PageCss) {
        this._PageCss = PageCss;
    }    

    public String getPageScript() {
        return _PageScript;
    }

    public void setPageScript(String PageScript) {
        this._PageScript = PageScript;
    }    

    public String getPageCssImports() {
        return _PageCssImports;
    }

    public void setPageCssImports(String PageCssImports) {
        this._PageCssImports = PageCssImports;
    }    

    public String getPageScriptImports() {
        return _PageScriptImports;
    }

    public void setPageScriptImports(String PageScriptImports) {
        this._PageScriptImports = PageScriptImports;
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

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}