/* 
Template last modification history:


Source Generated: Sun Mar 15 01:49:44 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenMainForm extends ActionForm {

	private String _Active;             
	private String _Value;             
	private String _Data;             
	private String _Required;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getActive() {
        return _Active;
    }

    public void setActive(String Active) {
        this._Active = Active;
    }    

    public String getValue() {
        return _Value;
    }

    public void setValue(String Value) {
        this._Value = Value;
    }    

    public String getData() {
        return _Data;
    }

    public void setData(String Data) {
        this._Data = Data;
    }    

    public String getRequired() {
        return _Required;
    }

    public void setRequired(String Required) {
        this._Required = Required;
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