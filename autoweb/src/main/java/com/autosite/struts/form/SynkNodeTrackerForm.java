/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:17 EST 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SynkNodeTrackerForm extends ActionForm {

	private String _Namespace;             
	private String _DeviceId;             
	private String _Remote;             
	private String _Stamp;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

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

    public String getRemote() {
        return _Remote;
    }

    public void setRemote(String Remote) {
        this._Remote = Remote;
    }    

    public String getStamp() {
        return _Stamp;
    }

    public void setStamp(String Stamp) {
        this._Stamp = Stamp;
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