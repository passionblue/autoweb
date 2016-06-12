package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MetaHeaderForm extends ActionForm {

	private String _Source;             
	private String _DetailId;             
	private String _Name;             
	private String _Value;             
	private String _HttpEquiv;             
	private String _TimeCreated;             

    public String getSource() {
        return _Source;
    }

    public void setSource(String Source) {
        this._Source = Source;
    }    

    public String getDetailId() {
        return _DetailId;
    }

    public void setDetailId(String DetailId) {
        this._DetailId = DetailId;
    }    

    public String getName() {
        return _Name;
    }

    public void setName(String Name) {
        this._Name = Name;
    }    

    public String getValue() {
        return _Value;
    }

    public void setValue(String Value) {
        this._Value = Value;
    }    

    public String getHttpEquiv() {
        return _HttpEquiv;
    }

    public void setHttpEquiv(String HttpEquiv) {
        this._HttpEquiv = HttpEquiv;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}