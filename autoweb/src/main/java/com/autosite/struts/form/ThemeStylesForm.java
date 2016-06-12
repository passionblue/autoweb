package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ThemeStylesForm extends ActionForm {

	private String _BodyWidth;             
	private String _BodyAlign;             
	private String _BodyBackground;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getBodyWidth() {
        return _BodyWidth;
    }

    public void setBodyWidth(String BodyWidth) {
        this._BodyWidth = BodyWidth;
    }    

    public String getBodyAlign() {
        return _BodyAlign;
    }

    public void setBodyAlign(String BodyAlign) {
        this._BodyAlign = BodyAlign;
    }    

    public String getBodyBackground() {
        return _BodyBackground;
    }

    public void setBodyBackground(String BodyBackground) {
        this._BodyBackground = BodyBackground;
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