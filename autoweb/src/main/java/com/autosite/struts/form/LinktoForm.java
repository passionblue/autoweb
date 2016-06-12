package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LinktoForm extends ActionForm {

	private String _LinkKey;             
	private String _LinkTarget;             
	private String _Disable;             
	private String _TimeCreated;             

    public String getLinkKey() {
        return _LinkKey;
    }

    public void setLinkKey(String LinkKey) {
        this._LinkKey = LinkKey;
    }    

    public String getLinkTarget() {
        return _LinkTarget;
    }

    public void setLinkTarget(String LinkTarget) {
        this._LinkTarget = LinkTarget;
    }    

    public String getDisable() {
        return _Disable;
    }

    public void setDisable(String Disable) {
        this._Disable = Disable;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}