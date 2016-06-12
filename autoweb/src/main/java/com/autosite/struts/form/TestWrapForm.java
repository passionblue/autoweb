package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class TestWrapForm extends ActionForm {

	private String _Data;             
	private String _TimeCreated;             
	private String _WrapData;             

    public String getData() {
        return _Data;
    }

    public void setData(String Data) {
        this._Data = Data;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getWrapData() {
        return _WrapData;
    }

    public void setWrapData(String WrapData) {
        this._WrapData = WrapData;
    }    

}