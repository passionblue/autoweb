package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PanelStyleForm extends ActionForm {

	private String _PanelId;             
	private String _StyleId;             

    public String getPanelId() {
        return _PanelId;
    }

    public void setPanelId(String PanelId) {
        this._PanelId = PanelId;
    }    

    public String getStyleId() {
        return _StyleId;
    }

    public void setStyleId(String StyleId) {
        this._StyleId = StyleId;
    }    

}