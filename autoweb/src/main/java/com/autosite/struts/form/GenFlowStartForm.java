package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenFlowStartForm extends ActionForm {

	private String _YourName;             
	private String _Email;             

    public String getYourName() {
        return _YourName;
    }

    public void setYourName(String YourName) {
        this._YourName = YourName;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

}