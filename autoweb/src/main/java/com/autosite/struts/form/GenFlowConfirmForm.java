package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenFlowConfirmForm extends ActionForm {

	private String _Agree;             

    public String getAgree() {
        return _Agree;
    }

    public void setAgree(String Agree) {
        this._Agree = Agree;
    }    

}