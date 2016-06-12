package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AutositeRemoteDeviceForm extends ActionForm {

	private String _DeviceId;             
	private String _DeviceType;             
	private String _TimeCreated;             

    public String getDeviceId() {
        return _DeviceId;
    }

    public void setDeviceId(String DeviceId) {
        this._DeviceId = DeviceId;
    }    

    public String getDeviceType() {
        return _DeviceType;
    }

    public void setDeviceType(String DeviceType) {
        this._DeviceType = DeviceType;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}