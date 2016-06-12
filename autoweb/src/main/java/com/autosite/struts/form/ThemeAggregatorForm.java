package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ThemeAggregatorForm extends ActionForm {

	private String _ThemeName;             
	private String _LayoutPage;             
	private String _CssIndex;             
	private String _ThemeStyleId;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getThemeName() {
        return _ThemeName;
    }

    public void setThemeName(String ThemeName) {
        this._ThemeName = ThemeName;
    }    

    public String getLayoutPage() {
        return _LayoutPage;
    }

    public void setLayoutPage(String LayoutPage) {
        this._LayoutPage = LayoutPage;
    }    

    public String getCssIndex() {
        return _CssIndex;
    }

    public void setCssIndex(String CssIndex) {
        this._CssIndex = CssIndex;
    }    

    public String getThemeStyleId() {
        return _ThemeStyleId;
    }

    public void setThemeStyleId(String ThemeStyleId) {
        this._ThemeStyleId = ThemeStyleId;
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