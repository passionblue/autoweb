package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenFlowFinalForm extends ActionForm {

	private String _Accept;             

    public String getAccept() {
        return _Accept;
    }

    public void setAccept(String Accept) {
        this._Accept = Accept;
    }    

}