/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:16 EST 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SynkNamespaceRecordForm extends ActionForm {

	private String _Namespace;             
	private String _RecordId;             
	private String _Stamp;             
	private String _OrgStamp;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getNamespace() {
        return _Namespace;
    }

    public void setNamespace(String Namespace) {
        this._Namespace = Namespace;
    }    

    public String getRecordId() {
        return _RecordId;
    }

    public void setRecordId(String RecordId) {
        this._RecordId = RecordId;
    }    

    public String getStamp() {
        return _Stamp;
    }

    public void setStamp(String Stamp) {
        this._Stamp = Stamp;
    }    

    public String getOrgStamp() {
        return _OrgStamp;
    }

    public void setOrgStamp(String OrgStamp) {
        this._OrgStamp = OrgStamp;
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