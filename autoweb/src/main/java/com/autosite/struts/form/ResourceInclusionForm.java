package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ResourceInclusionForm extends ActionForm {

	private String _Name;             
	private String _Include;             
	private String _ResourceType;             

    public String getName() {
        return _Name;
    }

    public void setName(String Name) {
        this._Name = Name;
    }    

    public String getInclude() {
        return _Include;
    }

    public void setInclude(String Include) {
        this._Include = Include;
    }    

    public String getResourceType() {
        return _ResourceType;
    }

    public void setResourceType(String ResourceType) {
        this._ResourceType = ResourceType;
    }    

}