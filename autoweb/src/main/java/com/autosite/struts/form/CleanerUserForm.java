package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerUserForm extends ActionForm {

	private String _AutositeUserId;             
	private String _Inactivated;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getAutositeUserId() {
        return _AutositeUserId;
    }

    public void setAutositeUserId(String AutositeUserId) {
        this._AutositeUserId = AutositeUserId;
    }    

    public String getInactivated() {
        return _Inactivated;
    }

    public void setInactivated(String Inactivated) {
        this._Inactivated = Inactivated;
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