package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenSimpleForm extends ActionForm {

	private String _Data;             
	private String _Active;             
	private String _ExtString;             
	private String _ExtInt;             

    public String getData() {
        return _Data;
    }

    public void setData(String Data) {
        this._Data = Data;
    }    

    public String getActive() {
        return _Active;
    }

    public void setActive(String Active) {
        this._Active = Active;
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