/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SynkNodeTrackerTxForm extends ActionForm {

	private String _Namespace;             
	private String _DeviceId;             
	private String _TxToken;             
	private String _StampAcked;             
	private String _StampLast;             
	private String _NumRecords;             
	private String _Ip;             
	private String _TimeCreated;             

    public String getNamespace() {
        return _Namespace;
    }

    public void setNamespace(String Namespace) {
        this._Namespace = Namespace;
    }    

    public String getDeviceId() {
        return _DeviceId;
    }

    public void setDeviceId(String DeviceId) {
        this._DeviceId = DeviceId;
    }    

    public String getTxToken() {
        return _TxToken;
    }

    public void setTxToken(String TxToken) {
        this._TxToken = TxToken;
    }    

    public String getStampAcked() {
        return _StampAcked;
    }

    public void setStampAcked(String StampAcked) {
        this._StampAcked = StampAcked;
    }    

    public String getStampLast() {
        return _StampLast;
    }

    public void setStampLast(String StampLast) {
        this._StampLast = StampLast;
    }    

    public String getNumRecords() {
        return _NumRecords;
    }

    public void setNumRecords(String NumRecords) {
        this._NumRecords = NumRecords;
    }    

    public String getIp() {
        return _Ip;
    }

    public void setIp(String Ip) {
        this._Ip = Ip;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}