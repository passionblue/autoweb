package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PageConfigForm extends ActionForm {

	private String _PageId;             
	private String _SortType;             
	private String _ArrangeType;             
	private String _PageCss;             
	private String _PageScript;             
	private String _PageCssImports;             
	private String _PageScriptImports;             
	private String _HideMenu;             
	private String _HideMid;             
	private String _HideAd;             
	private String _StyleId;             
	private String _ContentStyleSetId;             
	private String _HeaderMeta;             
	private String _HeaderLink;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getPageId() {
        return _PageId;
    }

    public void setPageId(String PageId) {
        this._PageId = PageId;
    }    

    public String getSortType() {
        return _SortType;
    }

    public void setSortType(String SortType) {
        this._SortType = SortType;
    }    

    public String getArrangeType() {
        return _ArrangeType;
    }

    public void setArrangeType(String ArrangeType) {
        this._ArrangeType = ArrangeType;
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

    public String getStyleId() {
        return _StyleId;
    }

    public void setStyleId(String StyleId) {
        this._StyleId = StyleId;
    }    

    public String getContentStyleSetId() {
        return _ContentStyleSetId;
    }

    public void setContentStyleSetId(String ContentStyleSetId) {
        this._ContentStyleSetId = ContentStyleSetId;
    }    

    public String getHeaderMeta() {
        return _HeaderMeta;
    }

    public void setHeaderMeta(String HeaderMeta) {
        this._HeaderMeta = HeaderMeta;
    }    

    public String getHeaderLink() {
        return _HeaderLink;
    }

    public void setHeaderLink(String HeaderLink) {
        this._HeaderLink = HeaderLink;
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