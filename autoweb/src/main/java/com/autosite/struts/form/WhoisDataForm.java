package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WhoisDataForm extends ActionForm {

	private String _Ip;             
	private String _WhoisData;             
	private String _Server;             
	private String _ForceRequest;             
	private String _TimeCreated;             
	private String _TimeExpired;             

    public String getIp() {
        return _Ip;
    }

    public void setIp(String Ip) {
        this._Ip = Ip;
    }    

    public String getWhoisData() {
        return _WhoisData;
    }

    public void setWhoisData(String WhoisData) {
        this._WhoisData = WhoisData;
    }    

    public String getServer() {
        return _Server;
    }

    public void setServer(String Server) {
        this._Server = Server;
    }    

    public String getForceRequest() {
        return _ForceRequest;
    }

    public void setForceRequest(String ForceRequest) {
        this._ForceRequest = ForceRequest;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeExpired() {
        return _TimeExpired;
    }

    public void setTimeExpired(String TimeExpired) {
        this._TimeExpired = TimeExpired;
    }    

}