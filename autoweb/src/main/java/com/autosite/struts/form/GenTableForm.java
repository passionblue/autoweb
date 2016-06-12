/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenTableForm extends ActionForm {

	private String _Country;             
	private String _Age;             
	private String _Disabled;             
	private String _Comments;             
	private String _TimeCreated;             
	private String _TimeUpdated;             
	private String _ExtString;             
	private String _ExtInt;             

    public String getCountry() {
        return _Country;
    }

    public void setCountry(String Country) {
        this._Country = Country;
    }    

    public String getAge() {
        return _Age;
    }

    public void setAge(String Age) {
        this._Age = Age;
    }    

    public String getDisabled() {
        return _Disabled;
    }

    public void setDisabled(String Disabled) {
        this._Disabled = Disabled;
    }    

    public String getComments() {
        return _Comments;
    }

    public void setComments(String Comments) {
        this._Comments = Comments;
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

    public String getExtString() {
        return _ExtString;
    }

    public void setExtString(String ExtString) {
        this._ExtString = ExtString;
    }    

    public String getExtInt() {
        return _ExtInt;
    }

    public void setExtInt(String ExtInt) {
        this._ExtInt = ExtInt;
    }    

}