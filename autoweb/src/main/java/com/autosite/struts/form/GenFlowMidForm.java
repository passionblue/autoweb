package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenFlowMidForm extends ActionForm {

	private String _Address;             
	private String _Phone;             

    public String getAddress() {
        return _Address;
    }

    public void setAddress(String Address) {
        this._Address = Address;
    }    

    public String getPhone() {
        return _Phone;
    }

    public void setPhone(String Phone) {
        this._Phone = Phone;
    }    

}