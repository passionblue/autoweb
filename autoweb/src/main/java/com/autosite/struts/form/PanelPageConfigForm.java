package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PanelPageConfigForm extends ActionForm {

	private String _PanelId;             
	private String _PageDisplaySummary;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getPanelId() {
        return _PanelId;
    }

    public void setPanelId(String PanelId) {
        this._PanelId = PanelId;
    }    

    public String getPageDisplaySummary() {
        return _PageDisplaySummary;
    }

    public void setPageDisplaySummary(String PageDisplaySummary) {
        this._PageDisplaySummary = PageDisplaySummary;
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