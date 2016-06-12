package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SiteConfigStyleForm extends ActionForm {

	private String _ThemeId;             
	private String _CssIndex;             
	private String _CssImport;             
	private String _LayoutIndex;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getThemeId() {
        return _ThemeId;
    }

    public void setThemeId(String ThemeId) {
        this._ThemeId = ThemeId;
    }    

    public String getCssIndex() {
        return _CssIndex;
    }

    public void setCssIndex(String CssIndex) {
        this._CssIndex = CssIndex;
    }    

    public String getCssImport() {
        return _CssImport;
    }

    public void setCssImport(String CssImport) {
        this._CssImport = CssImport;
    }    

    public String getLayoutIndex() {
        return _LayoutIndex;
    }

    public void setLayoutIndex(String LayoutIndex) {
        this._LayoutIndex = LayoutIndex;
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