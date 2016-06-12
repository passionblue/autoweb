package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AutositeSessionContextEntityForm extends ActionForm {

	private String _Serial;             
	private String _IsLogin;             
	private String _TimeLogin;             
	private String _TimeLastAccess;             
	private String _LoginUserId;             
	private String _SessionType;             
	private String _RemoteDeviceId;             
	private String _RemoteIp;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getSerial() {
        return _Serial;
    }

    public void setSerial(String Serial) {
        this._Serial = Serial;
    }    

    public String getIsLogin() {
        return _IsLogin;
    }

    public void setIsLogin(String IsLogin) {
        this._IsLogin = IsLogin;
    }    

    public String getTimeLogin() {
        return _TimeLogin;
    }

    public void setTimeLogin(String TimeLogin) {
        this._TimeLogin = TimeLogin;
    }    

    public String getTimeLastAccess() {
        return _TimeLastAccess;
    }

    public void setTimeLastAccess(String TimeLastAccess) {
        this._TimeLastAccess = TimeLastAccess;
    }    

    public String getLoginUserId() {
        return _LoginUserId;
    }

    public void setLoginUserId(String LoginUserId) {
        this._LoginUserId = LoginUserId;
    }    

    public String getSessionType() {
        return _SessionType;
    }

    public void setSessionType(String SessionType) {
        this._SessionType = SessionType;
    }    

    public String getRemoteDeviceId() {
        return _RemoteDeviceId;
    }

    public void setRemoteDeviceId(String RemoteDeviceId) {
        this._RemoteDeviceId = RemoteDeviceId;
    }    

    public String getRemoteIp() {
        return _RemoteIp;
    }

    public void setRemoteIp(String RemoteIp) {
        this._RemoteIp = RemoteIp;
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

}