package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PageForm extends ActionForm {

	private String _MenuPanelId;             
	private String _ParentId;             
	private String _PageName;             
	private String _PageMenuTitle;             
	private String _Hide;             
	private String _CreatedTime;             
	private String _SiteUrl;             
	private String _PageColCount;             
	private String _PageKeywords;             
	private String _PageViewType;             
	private String _UnderlyingPage;             
	private String _HeaderPage;             
	private String _ShowPageExclusiveOnly;             
	private String _Alt1;             
	private String _Alt2;             
	private String _Alt3;             

    public String getMenuPanelId() {
        return _MenuPanelId;
    }

    public void setMenuPanelId(String MenuPanelId) {
        this._MenuPanelId = MenuPanelId;
    }    

    public String getParentId() {
        return _ParentId;
    }

    public void setParentId(String ParentId) {
        this._ParentId = ParentId;
    }    

    public String getPageName() {
        return _PageName;
    }

    public void setPageName(String PageName) {
        this._PageName = PageName;
    }    

    public String getPageMenuTitle() {
        return _PageMenuTitle;
    }

    public void setPageMenuTitle(String PageMenuTitle) {
        this._PageMenuTitle = PageMenuTitle;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getCreatedTime() {
        return _CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this._CreatedTime = CreatedTime;
    }    

    public String getSiteUrl() {
        return _SiteUrl;
    }

    public void setSiteUrl(String SiteUrl) {
        this._SiteUrl = SiteUrl;
    }    

    public String getPageColCount() {
        return _PageColCount;
    }

    public void setPageColCount(String PageColCount) {
        this._PageColCount = PageColCount;
    }    

    public String getPageKeywords() {
        return _PageKeywords;
    }

    public void setPageKeywords(String PageKeywords) {
        this._PageKeywords = PageKeywords;
    }    

    public String getPageViewType() {
        return _PageViewType;
    }

    public void setPageViewType(String PageViewType) {
        this._PageViewType = PageViewType;
    }    

    public String getUnderlyingPage() {
        return _UnderlyingPage;
    }

    public void setUnderlyingPage(String UnderlyingPage) {
        this._UnderlyingPage = UnderlyingPage;
    }    

    public String getHeaderPage() {
        return _HeaderPage;
    }

    public void setHeaderPage(String HeaderPage) {
        this._HeaderPage = HeaderPage;
    }    

    public String getShowPageExclusiveOnly() {
        return _ShowPageExclusiveOnly;
    }

    public void setShowPageExclusiveOnly(String ShowPageExclusiveOnly) {
        this._ShowPageExclusiveOnly = ShowPageExclusiveOnly;
    }    

    public String getAlt1() {
        return _Alt1;
    }

    public void setAlt1(String Alt1) {
        this._Alt1 = Alt1;
    }    

    public String getAlt2() {
        return _Alt2;
    }

    public void setAlt2(String Alt2) {
        this._Alt2 = Alt2;
    }    

    public String getAlt3() {
        return _Alt3;
    }

    public void setAlt3(String Alt3) {
        this._Alt3 = Alt3;
    }    

}