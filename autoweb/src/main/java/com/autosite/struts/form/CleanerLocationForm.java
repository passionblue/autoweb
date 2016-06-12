package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerLocationForm extends ActionForm {

	private String _Address;             
	private String _CityZip;             
	private String _Phone;             
	private String _ManagerId;             

    public String getAddress() {
        return _Address;
    }

    public void setAddress(String Address) {
        this._Address = Address;
    }    

    public String getCityZip() {
        return _CityZip;
    }

    public void setCityZip(String CityZip) {
        this._CityZip = CityZip;
    }    

    public String getPhone() {
        return _Phone;
    }

    public void setPhone(String Phone) {
        this._Phone = Phone;
    }    

    public String getManagerId() {
        return _ManagerId;
    }

    public void setManagerId(String ManagerId) {
        this._ManagerId = ManagerId;
    }    

}