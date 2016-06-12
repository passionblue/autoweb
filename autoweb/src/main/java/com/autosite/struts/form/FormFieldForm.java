package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FormFieldForm extends ActionForm {

	private String _UserId;             
	private String _FormId;             
	private String _FieldText;             
	private String _FieldType;             
	private String _Required;             
	private String _TimeCreated;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getFormId() {
        return _FormId;
    }

    public void setFormId(String FormId) {
        this._FormId = FormId;
    }    

    public String getFieldText() {
        return _FieldText;
    }

    public void setFieldText(String FieldText) {
        this._FieldText = FieldText;
    }    

    public String getFieldType() {
        return _FieldType;
    }

    public void setFieldType(String FieldType) {
        this._FieldType = FieldType;
    }    

    public String getRequired() {
        return _Required;
    }

    public void setRequired(String Required) {
        this._Required = Required;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}