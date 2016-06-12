package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PanelPositionStyleForm extends ActionForm {

	private String _Col;             
	private String _StyleId;             
	private String _TimeCreated;             

    public String getCol() {
        return _Col;
    }

    public void setCol(String Col) {
        this._Col = Col;
    }    

    public String getStyleId() {
        return _StyleId;
    }

    public void setStyleId(String StyleId) {
        this._StyleId = StyleId;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}