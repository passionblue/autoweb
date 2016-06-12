package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AutositeSynchLedgerForm extends ActionForm {

	private String _DeviceId;             
	private String _OriginalLedgerId;             
	private String _Scope;             
	private String _Target;             
	private String _RemoteToken;             
	private String _ObjectId;             
	private String _SynchId;             
	private String _TimeCreated;             

    public String getDeviceId() {
        return _DeviceId;
    }

    public void setDeviceId(String DeviceId) {
        this._DeviceId = DeviceId;
    }    

    public String getOriginalLedgerId() {
        return _OriginalLedgerId;
    }

    public void setOriginalLedgerId(String OriginalLedgerId) {
        this._OriginalLedgerId = OriginalLedgerId;
    }    

    public String getScope() {
        return _Scope;
    }

    public void setScope(String Scope) {
        this._Scope = Scope;
    }    

    public String getTarget() {
        return _Target;
    }

    public void setTarget(String Target) {
        this._Target = Target;
    }    

    public String getRemoteToken() {
        return _RemoteToken;
    }

    public void setRemoteToken(String RemoteToken) {
        this._RemoteToken = RemoteToken;
    }    

    public String getObjectId() {
        return _ObjectId;
    }

    public void setObjectId(String ObjectId) {
        this._ObjectId = ObjectId;
    }    

    public String getSynchId() {
        return _SynchId;
    }

    public void setSynchId(String SynchId) {
        this._SynchId = SynchId;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}