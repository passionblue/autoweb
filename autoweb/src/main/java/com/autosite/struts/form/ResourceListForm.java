package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ResourceListForm extends ActionForm {

	private String _Url;             
	private String _OriginalName;             
	private String _Size;             
	private String _ResourceType;             
	private String _TimeCreaeted;             
	private String _TimeUpdated;             

    public String getUrl() {
        return _Url;
    }

    public void setUrl(String Url) {
        this._Url = Url;
    }    

    public String getOriginalName() {
        return _OriginalName;
    }

    public void setOriginalName(String OriginalName) {
        this._OriginalName = OriginalName;
    }    

    public String getSize() {
        return _Size;
    }

    public void setSize(String Size) {
        this._Size = Size;
    }    

    public String getResourceType() {
        return _ResourceType;
    }

    public void setResourceType(String ResourceType) {
        this._ResourceType = ResourceType;
    }    

    public String getTimeCreaeted() {
        return _TimeCreaeted;
    }

    public void setTimeCreaeted(String TimeCreaeted) {
        this._TimeCreaeted = TimeCreaeted;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}